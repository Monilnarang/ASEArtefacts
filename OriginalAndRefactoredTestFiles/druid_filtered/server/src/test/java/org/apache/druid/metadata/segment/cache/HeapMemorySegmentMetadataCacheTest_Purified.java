package org.apache.druid.metadata.segment.cache;

import org.apache.druid.error.DruidExceptionMatcher;
import org.apache.druid.java.util.common.DateTimes;
import org.apache.druid.java.util.common.ISE;
import org.apache.druid.java.util.common.Intervals;
import org.apache.druid.java.util.common.StringUtils;
import org.apache.druid.java.util.common.concurrent.ScheduledExecutorFactory;
import org.apache.druid.java.util.emitter.EmittingLogger;
import org.apache.druid.java.util.emitter.service.AlertEvent;
import org.apache.druid.java.util.metrics.StubServiceEmitter;
import org.apache.druid.metadata.IndexerSqlMetadataStorageCoordinatorTestBase;
import org.apache.druid.metadata.PendingSegmentRecord;
import org.apache.druid.metadata.SegmentsMetadataManagerConfig;
import org.apache.druid.metadata.TestDerbyConnector;
import org.apache.druid.segment.SchemaPayload;
import org.apache.druid.segment.SegmentMetadata;
import org.apache.druid.segment.TestDataSource;
import org.apache.druid.segment.TestHelper;
import org.apache.druid.segment.column.RowSignature;
import org.apache.druid.segment.metadata.CentralizedDatasourceSchemaConfig;
import org.apache.druid.segment.metadata.FingerprintGenerator;
import org.apache.druid.segment.metadata.NoopSegmentSchemaCache;
import org.apache.druid.segment.metadata.SegmentSchemaCache;
import org.apache.druid.segment.metadata.SegmentSchemaTestUtils;
import org.apache.druid.segment.realtime.appenderator.SegmentIdWithShardSpec;
import org.apache.druid.server.coordinator.CreateDataSegments;
import org.apache.druid.server.coordinator.simulate.BlockingExecutorService;
import org.apache.druid.server.coordinator.simulate.WrappingScheduledExecutorService;
import org.apache.druid.server.http.DataSegmentPlus;
import org.apache.druid.timeline.DataSegment;
import org.apache.druid.timeline.SegmentId;
import org.apache.druid.timeline.partition.NumberedShardSpec;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HeapMemorySegmentMetadataCacheTest_Purified {

    @Rule
    public final TestDerbyConnector.DerbyConnectorRule derbyConnectorRule = new TestDerbyConnector.DerbyConnectorRule(CentralizedDatasourceSchemaConfig.enabled(true));

    private BlockingExecutorService pollExecutor;

    private ScheduledExecutorFactory executorFactory;

    private TestDerbyConnector derbyConnector;

    private StubServiceEmitter serviceEmitter;

    private HeapMemorySegmentMetadataCache cache;

    private SegmentSchemaCache schemaCache;

    private SegmentSchemaTestUtils schemaTestUtils;

    @Before
    public void setup() {
        pollExecutor = new BlockingExecutorService("test-poll-exec");
        executorFactory = (poolSize, name) -> new WrappingScheduledExecutorService(name, pollExecutor, false);
        derbyConnector = derbyConnectorRule.getConnector();
        serviceEmitter = new StubServiceEmitter();
        derbyConnector.createSegmentTable();
        derbyConnector.createSegmentSchemasTable();
        derbyConnector.createPendingSegmentsTable();
        schemaTestUtils = new SegmentSchemaTestUtils(derbyConnectorRule, derbyConnector, TestHelper.JSON_MAPPER);
        EmittingLogger.registerEmitter(serviceEmitter);
    }

    @After
    public void tearDown() {
        if (cache != null) {
            cache.stopBeingLeader();
            cache.stop();
        }
    }

    private void setupTargetWithCaching(SegmentMetadataCache.UsageMode cacheMode) {
        setupTargetWithCaching(cacheMode, false);
    }

    private void setupTargetWithCaching(SegmentMetadataCache.UsageMode cacheMode, boolean useSchemaCache) {
        if (cache != null) {
            throw new ISE("Test target has already been initialized with caching[%s]", cache.isEnabled());
        }
        final SegmentsMetadataManagerConfig metadataManagerConfig = new SegmentsMetadataManagerConfig(null, cacheMode);
        schemaCache = useSchemaCache ? new SegmentSchemaCache() : new NoopSegmentSchemaCache();
        cache = new HeapMemorySegmentMetadataCache(TestHelper.JSON_MAPPER, () -> metadataManagerConfig, derbyConnectorRule.metadataTablesConfigSupplier(), schemaCache, derbyConnector, executorFactory, serviceEmitter);
    }

    private void setupAndSyncCacheWithSchema() {
        setupTargetWithCaching(SegmentMetadataCache.UsageMode.ALWAYS, true);
        cache.start();
        cache.becomeLeader();
        syncCacheAfterBecomingLeader();
    }

    private void setupAndSyncCache() {
        setupTargetWithCaching(SegmentMetadataCache.UsageMode.ALWAYS);
        cache.start();
        cache.becomeLeader();
        syncCacheAfterBecomingLeader();
    }

    private void syncCacheAfterBecomingLeader() {
        syncCache();
        syncCache();
    }

    private void syncCache() {
        serviceEmitter.flush();
        pollExecutor.finishNextPendingTasks(2);
    }

    private static String getSchemaFingerprint(SchemaPayload payload) {
        return new FingerprintGenerator(TestHelper.JSON_MAPPER).generateFingerprint(payload, TestDataSource.WIKI, CentralizedDatasourceSchemaConfig.SCHEMA_VERSION);
    }

    private void insertSegmentsInMetadataStore(Set<DataSegmentPlus> segments) {
        IndexerSqlMetadataStorageCoordinatorTestBase.insertSegments(segments, false, derbyConnectorRule, TestHelper.JSON_MAPPER);
    }

    private void insertSegmentsInMetadataStoreWithSchema(DataSegmentPlus... segments) {
        IndexerSqlMetadataStorageCoordinatorTestBase.insertSegments(Set.of(segments), true, derbyConnectorRule, TestHelper.JSON_MAPPER);
    }

    private void updateSegmentInMetadataStore(DataSegmentPlus segment) {
        int updatedRows = derbyConnectorRule.segments().update("UPDATE %1$s SET used = ?, used_status_last_updated = ? WHERE id = ?", Boolean.TRUE.equals(segment.getUsed()), segment.getUsedStatusLastUpdatedDate().toString(), segment.getDataSegment().getId().toString());
        Assert.assertEquals(1, updatedRows);
    }

    private static PendingSegmentRecord createPendingSegment(DateTime createdTime) {
        SegmentIdWithShardSpec segmentId = new SegmentIdWithShardSpec(TestDataSource.WIKI, Intervals.of("2021-01-01/P1D"), "v1", new NumberedShardSpec(0, 1));
        return new PendingSegmentRecord(segmentId, "sequence1", null, null, "allocator1", createdTime);
    }

    @Test
    public void testStart_schedulesDbPoll_ifCacheIsEnabled_1() {
        Assert.assertTrue(cache.isEnabled());
    }

    @Test
    public void testStart_schedulesDbPoll_ifCacheIsEnabled_2_testMerged_2() {
        Assert.assertTrue(pollExecutor.hasPendingTasks());
    }

    @Test
    public void testStart_doesNotScheduleDbPoll_ifCacheIsDisabled_1_testMerged_1() {
        Assert.assertFalse(cache.isEnabled());
    }

    @Test
    public void testStart_doesNotScheduleDbPoll_ifCacheIsDisabled_3() {
        Assert.assertFalse(pollExecutor.hasPendingTasks());
    }

    @Test
    public void testStop_stopsDbPoll_ifCacheIsEnabled_1() {
        Assert.assertTrue(cache.isEnabled());
    }

    @Test
    public void testStop_stopsDbPoll_ifCacheIsEnabled_2() {
        Assert.assertTrue(pollExecutor.hasPendingTasks());
    }

    @Test
    public void testStop_stopsDbPoll_ifCacheIsEnabled_3() {
        Assert.assertTrue(pollExecutor.isShutdown());
    }

    @Test
    public void testStop_stopsDbPoll_ifCacheIsEnabled_4() {
        Assert.assertFalse(pollExecutor.hasPendingTasks());
    }

    @Test
    public void test_sync_addsUsedSegmentSchema_ifNotPresentInCache_1() {
        Assert.assertTrue(schemaCache.getPublishedSchemaPayloadMap().isEmpty());
    }

    @Test
    public void test_sync_addsUsedSegmentSchema_ifNotPresentInCache_2() {
        final SchemaPayload payload = new SchemaPayload(RowSignature.builder().add("col1", null).build());
        final String fingerprint = getSchemaFingerprint(payload);
        schemaTestUtils.insertSegmentSchema(TestDataSource.WIKI, Map.of(fingerprint, payload), Set.of(fingerprint));
        Assert.assertEquals(Map.of(fingerprint, payload), schemaCache.getPublishedSchemaPayloadMap());
    }

    @Test
    public void test_sync_addsUsedSegmentMetadata_ifNotPresentInCache_1() {
        Assert.assertTrue(schemaCache.getPublishedSegmentMetadataMap().isEmpty());
    }

    @Test
    public void test_sync_addsUsedSegmentMetadata_ifNotPresentInCache_2() {
        final SchemaPayload payload = new SchemaPayload(RowSignature.builder().add("col1", null).build());
        final String fingerprint = getSchemaFingerprint(payload);
        final DataSegmentPlus usedSegmentPlus = CreateDataSegments.ofDatasource(TestDataSource.WIKI).withNumRows(10L).withSchemaFingerprint(fingerprint).updatedNow().markUsed().asPlus();
        insertSegmentsInMetadataStoreWithSchema(usedSegmentPlus);
        Assert.assertEquals(Map.of(usedSegmentPlus.getDataSegment().getId(), new SegmentMetadata(10L, fingerprint)), schemaCache.getPublishedSegmentMetadataMap());
    }
}
