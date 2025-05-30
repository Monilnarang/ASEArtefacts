package org.apache.druid.server.coordinator.duty;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.apache.druid.client.indexing.IndexingTotalWorkerCapacityInfo;
import org.apache.druid.client.indexing.IndexingWorker;
import org.apache.druid.client.indexing.IndexingWorkerInfo;
import org.apache.druid.indexer.RunnerTaskState;
import org.apache.druid.indexer.TaskLocation;
import org.apache.druid.indexer.TaskState;
import org.apache.druid.indexer.TaskStatusPlus;
import org.apache.druid.indexing.overlord.IndexerMetadataStorageCoordinator;
import org.apache.druid.java.util.common.CloseableIterators;
import org.apache.druid.java.util.common.DateTimes;
import org.apache.druid.java.util.common.Intervals;
import org.apache.druid.java.util.common.JodaUtils;
import org.apache.druid.java.util.common.parsers.CloseableIterator;
import org.apache.druid.metadata.IndexerSQLMetadataStorageCoordinator;
import org.apache.druid.metadata.MetadataStorageTablesConfig;
import org.apache.druid.metadata.SQLMetadataConnector;
import org.apache.druid.metadata.SqlSegmentsMetadataManagerTestBase;
import org.apache.druid.metadata.TestDerbyConnector;
import org.apache.druid.rpc.indexing.NoopOverlordClient;
import org.apache.druid.segment.TestHelper;
import org.apache.druid.segment.metadata.CentralizedDatasourceSchemaConfig;
import org.apache.druid.server.coordinator.CoordinatorDynamicConfig;
import org.apache.druid.server.coordinator.DruidCoordinatorRuntimeParams;
import org.apache.druid.server.coordinator.config.KillUnusedSegmentsConfig;
import org.apache.druid.server.coordinator.stats.CoordinatorRunStats;
import org.apache.druid.server.coordinator.stats.Dimension;
import org.apache.druid.server.coordinator.stats.RowKey;
import org.apache.druid.server.coordinator.stats.Stats;
import org.apache.druid.timeline.DataSegment;
import org.apache.druid.timeline.partition.NoneShardSpec;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class KillUnusedSegmentsTest_Purified {

    private static final DateTime NOW = DateTimes.nowUtc();

    private static final Interval YEAR_OLD = new Interval(Period.days(1), NOW.minusDays(365));

    private static final Interval MONTH_OLD = new Interval(Period.days(1), NOW.minusDays(30));

    private static final Interval FIFTEEN_DAY_OLD = new Interval(Period.days(1), NOW.minusDays(15));

    private static final Interval DAY_OLD = new Interval(Period.days(1), NOW.minusDays(1));

    private static final Interval HOUR_OLD = new Interval(Period.days(1), NOW.minusHours(1));

    private static final Interval NEXT_DAY = new Interval(Period.days(1), NOW.plusDays(1));

    private static final Interval NEXT_MONTH = new Interval(Period.days(1), NOW.plusDays(30));

    private static final String DS1 = "DS1";

    private static final String DS2 = "DS2";

    private static final String DS3 = "DS3";

    private static final RowKey DS1_STAT_KEY = RowKey.of(Dimension.DATASOURCE, DS1);

    private static final RowKey DS2_STAT_KEY = RowKey.of(Dimension.DATASOURCE, DS2);

    private static final RowKey DS3_STAT_KEY = RowKey.of(Dimension.DATASOURCE, DS3);

    private static final String VERSION = "v1";

    private CoordinatorDynamicConfig.Builder dynamicConfigBuilder;

    private TestOverlordClient overlordClient;

    private KillUnusedSegmentsConfig.Builder configBuilder;

    private DruidCoordinatorRuntimeParams.Builder paramsBuilder;

    private KillUnusedSegments killDuty;

    @Rule
    public final TestDerbyConnector.DerbyConnectorRule derbyConnectorRule = new TestDerbyConnector.DerbyConnectorRule();

    private IndexerMetadataStorageCoordinator storageCoordinator;

    private SQLMetadataConnector connector;

    private MetadataStorageTablesConfig config;

    @Before
    public void setup() {
        connector = derbyConnectorRule.getConnector();
        storageCoordinator = new IndexerSQLMetadataStorageCoordinator(null, TestHelper.JSON_MAPPER, derbyConnectorRule.metadataTablesConfigSupplier().get(), connector, null, CentralizedDatasourceSchemaConfig.create());
        this.config = derbyConnectorRule.metadataTablesConfigSupplier().get();
        connector.createSegmentTable();
        overlordClient = new TestOverlordClient();
        configBuilder = KillUnusedSegmentsConfig.builder().withCleanupPeriod(Duration.standardSeconds(0)).withDurationToRetain(Duration.standardHours(36)).withMaxSegmentsToKill(10).withMaxIntervalToKill(Period.ZERO).withBufferPeriod(Duration.standardSeconds(1));
        dynamicConfigBuilder = CoordinatorDynamicConfig.builder().withKillTaskSlotRatio(1.0);
        paramsBuilder = DruidCoordinatorRuntimeParams.builder().withUsedSegments(Collections.emptySet());
    }

    private void validateLastKillStateAndReset(final String dataSource, @Nullable final Interval expectedKillInterval) {
        final Interval observedLastKillInterval = overlordClient.getLastKillInterval(dataSource);
        final String observedLastKillTaskId = overlordClient.getLastKillTaskId(dataSource);
        Assert.assertEquals(expectedKillInterval, observedLastKillInterval);
        String expectedKillTaskId = null;
        if (expectedKillInterval != null) {
            expectedKillTaskId = TestOverlordClient.getTaskId(KillUnusedSegments.TASK_ID_PREFIX, dataSource, expectedKillInterval);
        }
        Assert.assertEquals(expectedKillTaskId, observedLastKillTaskId);
        overlordClient.deleteLastKillTaskId(dataSource);
        overlordClient.deleteLastKillInterval(dataSource);
    }

    private DataSegment createAndAddUsedSegment(final String dataSource, final Interval interval, final String version) {
        final DataSegment segment = createSegment(dataSource, interval, version);
        SqlSegmentsMetadataManagerTestBase.publishSegment(connector, config, TestHelper.makeJsonMapper(), segment);
        return segment;
    }

    private void createAndAddUnusedSegment(final String dataSource, final Interval interval, final String version, final DateTime lastUpdatedTime) {
        final DataSegment segment = createAndAddUsedSegment(dataSource, interval, version);
        int numUpdatedSegments = SqlSegmentsMetadataManagerTestBase.markSegmentsAsUnused(Set.of(segment.getId()), derbyConnectorRule.getConnector(), derbyConnectorRule.metadataTablesConfigSupplier().get(), TestHelper.JSON_MAPPER, lastUpdatedTime);
        Assert.assertEquals(1, numUpdatedSegments);
    }

    private DataSegment createSegment(final String dataSource, final Interval interval, final String version) {
        return new DataSegment(dataSource, interval, version, new HashMap<>(), new ArrayList<>(), new ArrayList<>(), NoneShardSpec.instance(), 1, 0);
    }

    private void initDuty() {
        killDuty = new KillUnusedSegments(storageCoordinator, overlordClient, configBuilder.build());
    }

    private CoordinatorRunStats runDutyAndGetStats() {
        paramsBuilder.withDynamicConfigs(dynamicConfigBuilder.build());
        final DruidCoordinatorRuntimeParams params = killDuty.run(paramsBuilder.build());
        return params.getCoordinatorStats();
    }

    private static class TestOverlordClient extends NoopOverlordClient {

        private final List<TaskStatusPlus> taskStatuses = new ArrayList<>();

        private final Map<String, Interval> observedDatasourceToLastKillInterval = new HashMap<>();

        private final Map<String, String> observedDatasourceToLastKillTaskId = new HashMap<>();

        private final IndexingTotalWorkerCapacityInfo capcityInfo;

        private int taskIdSuffix = 0;

        TestOverlordClient() {
            capcityInfo = new IndexingTotalWorkerCapacityInfo(5, 10);
        }

        TestOverlordClient(final int currentClusterCapacity, final int maxWorkerCapacity) {
            capcityInfo = new IndexingTotalWorkerCapacityInfo(currentClusterCapacity, maxWorkerCapacity);
        }

        static String getTaskId(final String idPrefix, final String dataSource, final Interval interval) {
            return idPrefix + "-" + dataSource + "-" + interval;
        }

        void addTask(final String datasource) {
            taskStatuses.add(new TaskStatusPlus(KillUnusedSegments.TASK_ID_PREFIX + "__" + datasource + "__" + taskIdSuffix++, null, KillUnusedSegments.KILL_TASK_TYPE, DateTimes.EPOCH, DateTimes.EPOCH, TaskState.RUNNING, RunnerTaskState.RUNNING, 100L, TaskLocation.unknown(), datasource, null));
        }

        @Override
        public ListenableFuture<CloseableIterator<TaskStatusPlus>> taskStatuses(@Nullable String state, @Nullable String dataSource, @Nullable Integer maxCompletedTasks) {
            return Futures.immediateFuture(CloseableIterators.wrap(taskStatuses.iterator(), null));
        }

        @Override
        public ListenableFuture<String> runKillTask(String idPrefix, String dataSource, Interval interval, @Nullable List<String> versions, @Nullable Integer maxSegmentsToKill, @Nullable DateTime maxUsedStatusLastUpdatedTime) {
            final String taskId = getTaskId(idPrefix, dataSource, interval);
            observedDatasourceToLastKillInterval.put(dataSource, interval);
            observedDatasourceToLastKillTaskId.put(dataSource, taskId);
            return Futures.immediateFuture(taskId);
        }

        @Override
        public ListenableFuture<IndexingTotalWorkerCapacityInfo> getTotalWorkerCapacity() {
            return Futures.immediateFuture(capcityInfo);
        }

        @Override
        public ListenableFuture<List<IndexingWorkerInfo>> getWorkers() {
            return Futures.immediateFuture(ImmutableList.of(new IndexingWorkerInfo(new IndexingWorker("http", "localhost", "1.2.3.4", 3, "2"), 0, Collections.emptySet(), Collections.emptyList(), DateTimes.of("2000"), null)));
        }

        Interval getLastKillInterval(final String dataSource) {
            return observedDatasourceToLastKillInterval.get(dataSource);
        }

        void deleteLastKillInterval(final String dataSource) {
            observedDatasourceToLastKillInterval.remove(dataSource);
        }

        String getLastKillTaskId(final String dataSource) {
            final String lastKillTaskId = observedDatasourceToLastKillTaskId.get(dataSource);
            observedDatasourceToLastKillTaskId.remove(dataSource);
            return lastKillTaskId;
        }

        void deleteLastKillTaskId(final String dataSource) {
            observedDatasourceToLastKillTaskId.remove(dataSource);
        }
    }

    @Test
    public void testMaxIntervalToKillOverridesDurationToRetain_1() {
        CoordinatorRunStats newDatasourceStats = runDutyAndGetStats();
        Assert.assertEquals(1, newDatasourceStats.get(Stats.Kill.ELIGIBLE_UNUSED_SEGMENTS, DS1_STAT_KEY));
    }

    @Test
    public void testMaxIntervalToKillOverridesDurationToRetain_2() {
        CoordinatorRunStats oldDatasourceStats = runDutyAndGetStats();
        Assert.assertEquals(2, oldDatasourceStats.get(Stats.Kill.ELIGIBLE_UNUSED_SEGMENTS, DS1_STAT_KEY));
    }

    @Test
    public void testDurationToRetainOverridesMaxIntervalToKill_1() {
        CoordinatorRunStats newDatasourceStats = runDutyAndGetStats();
        Assert.assertEquals(1, newDatasourceStats.get(Stats.Kill.ELIGIBLE_UNUSED_SEGMENTS, DS1_STAT_KEY));
    }

    @Test
    public void testDurationToRetainOverridesMaxIntervalToKill_2() {
        CoordinatorRunStats oldDatasourceStats = runDutyAndGetStats();
        Assert.assertEquals(2, oldDatasourceStats.get(Stats.Kill.ELIGIBLE_UNUSED_SEGMENTS, DS1_STAT_KEY));
    }
}
