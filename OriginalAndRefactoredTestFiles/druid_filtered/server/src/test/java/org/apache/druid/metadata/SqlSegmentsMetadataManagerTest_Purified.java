package org.apache.druid.metadata;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import org.apache.druid.client.DataSourcesSnapshot;
import org.apache.druid.client.ImmutableDruidDataSource;
import org.apache.druid.java.util.common.Intervals;
import org.apache.druid.java.util.common.StringUtils;
import org.apache.druid.java.util.emitter.EmittingLogger;
import org.apache.druid.segment.TestDataSource;
import org.apache.druid.segment.metadata.CentralizedDatasourceSchemaConfig;
import org.apache.druid.server.coordinator.CreateDataSegments;
import org.apache.druid.server.metrics.NoopServiceEmitter;
import org.apache.druid.timeline.DataSegment;
import org.apache.druid.timeline.partition.NoneShardSpec;
import org.assertj.core.util.Sets;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import java.util.Set;
import java.util.stream.Collectors;

public class SqlSegmentsMetadataManagerTest_Purified extends SqlSegmentsMetadataManagerTestBase {

    private static DataSegment createSegment(String dataSource, String interval, String version) {
        return new DataSegment(dataSource, Intervals.of(interval), version, ImmutableMap.of(), ImmutableList.of(), ImmutableList.of(), NoneShardSpec.instance(), 9, 1234L);
    }

    @Rule
    public final TestDerbyConnector.DerbyConnectorRule derbyConnectorRule = new TestDerbyConnector.DerbyConnectorRule();

    private final DataSegment wikiSegment1 = CreateDataSegments.ofDatasource(TestDataSource.WIKI).startingAt("2012-03-15").eachOfSizeInMb(500).get(0);

    private final DataSegment wikiSegment2 = CreateDataSegments.ofDatasource(TestDataSource.WIKI).startingAt("2012-01-05").eachOfSizeInMb(500).get(0);

    private void publishUnusedSegments(DataSegment... segments) {
        for (DataSegment segment : segments) {
            publishSegment(segment);
            markSegmentsAsUnused(segment.getId());
        }
    }

    private void publishWikiSegments() {
        publishSegment(wikiSegment1);
        publishSegment(wikiSegment2);
    }

    @Before
    public void setUp() throws Exception {
        setUp(derbyConnectorRule);
    }

    @After
    public void tearDown() {
        teardownManager();
    }

    private DataSegment pollThenStopThenPublishKoalaSegment() {
        sqlSegmentsMetadataManager.startPollingDatabasePeriodically();
        sqlSegmentsMetadataManager.poll();
        sqlSegmentsMetadataManager.stopPollingDatabasePeriodically();
        Assert.assertFalse(sqlSegmentsMetadataManager.isPollingDatabasePeriodically());
        final DataSegment koalaSegment = createNewSegment1(TestDataSource.KOALA);
        publishSegment(koalaSegment);
        sqlSegmentsMetadataManager.startPollingDatabasePeriodically();
        return koalaSegment;
    }

    private static DataSegment createNewSegment1(String datasource) {
        return createSegment(datasource, "2017-10-15T00:00:00.000/2017-10-16T00:00:00.000", "2017-10-15T20:19:12.565Z");
    }

    private static DataSegment createNewSegment2(String datasource) {
        return createSegment(datasource, "2017-10-17T00:00:00.000/2017-10-18T00:00:00.000", "2017-10-15T20:19:12.565Z");
    }

    private int getCountOfRowsWithLastUsedNull() {
        return derbyConnectorRule.getConnector().retryWithHandle(handle -> handle.select(StringUtils.format("SELECT ID FROM %1$s WHERE USED_STATUS_LAST_UPDATED IS NULL", derbyConnectorRule.segments().getTableName())).size());
    }

    private void updateSegmentPayload(DataSegment segment, byte[] payload) {
        derbyConnectorRule.segments().update("UPDATE %1$s SET PAYLOAD = ? WHERE ID = ?", payload, segment.getId().toString());
    }

    private void updateUsedStatusLastUpdatedToNull(DataSegment segment) {
        derbyConnectorRule.segments().update("UPDATE %1$s SET USED_STATUS_LAST_UPDATED = NULL WHERE ID = ?", segment.getId().toString());
    }

    private void allowUsedFlagLastUpdatedToBeNullable() {
        derbyConnectorRule.segments().update("ALTER TABLE %1$s ALTER COLUMN USED_STATUS_LAST_UPDATED NULL");
    }

    private Set<DataSegment> retrieveAllUsedSegments() {
        return Sets.newHashSet(sqlSegmentsMetadataManager.getRecentDataSourcesSnapshot().iterateAllUsedSegmentsInSnapshot());
    }

    @Test
    public void test_poll_doesNotRetrieveUnusedSegments_1() {
        sqlSegmentsMetadataManager.startPollingDatabasePeriodically();
        sqlSegmentsMetadataManager.poll();
        Assert.assertTrue(sqlSegmentsMetadataManager.isPollingDatabasePeriodically());
    }

    @Test
    public void test_poll_doesNotRetrieveUnusedSegments_2_testMerged_2() {
        final DataSegment koalaSegment1 = createNewSegment1(TestDataSource.KOALA);
        final DataSegment koalaSegment2 = createNewSegment2(TestDataSource.KOALA);
        publishSegment(koalaSegment1);
        publishSegment(koalaSegment2);
        Assert.assertEquals(Set.of(wikiSegment1, wikiSegment2, koalaSegment1, koalaSegment2), retrieveAllUsedSegments());
        Assert.assertEquals(2, markSegmentsAsUnused(koalaSegment1.getId(), koalaSegment2.getId()));
    }

    @Test
    public void test_poll_doesNotRetrieveUnusedSegments_4() {
        Assert.assertEquals(Set.of(wikiSegment1, wikiSegment2), retrieveAllUsedSegments());
    }

    @Test
    public void testPopulateUsedFlagLastUpdated_1() {
        Assert.assertEquals(1, getCountOfRowsWithLastUsedNull());
    }

    @Test
    public void testPopulateUsedFlagLastUpdated_2() {
        Assert.assertEquals(0, getCountOfRowsWithLastUsedNull());
    }
}
