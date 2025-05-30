package org.apache.druid.metadata.segment.cache;

import org.apache.druid.error.DruidExceptionMatcher;
import org.apache.druid.java.util.common.DateTimes;
import org.apache.druid.java.util.common.Intervals;
import org.apache.druid.metadata.PendingSegmentRecord;
import org.apache.druid.segment.realtime.appenderator.SegmentIdWithShardSpec;
import org.apache.druid.server.coordinator.CreateDataSegments;
import org.apache.druid.server.http.DataSegmentPlus;
import org.apache.druid.timeline.DataSegment;
import org.apache.druid.timeline.SegmentId;
import org.apache.druid.timeline.partition.NumberedShardSpec;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import java.util.Set;

public class HeapMemoryDatasourceSegmentCacheTest_Purified {

    private static final String WIKI = "wiki";

    private static final Interval FIRST_WEEK_OF_JAN = Intervals.of("2024-01-01/P1W");

    private static final Interval FIRST_DAY_OF_JAN = FIRST_WEEK_OF_JAN.withDurationAfterStart(Duration.standardDays(1));

    private static final DataSegmentPlus JAN_1_SEGMENT = createUsedSegment().startingAt(FIRST_DAY_OF_JAN.getStart()).withVersion("v1").asPlus();

    private static final DataSegmentPlus JAN_2_SEGMENT = createUsedSegment().startingAt(FIRST_DAY_OF_JAN.getEnd()).withVersion("v2").asPlus();

    private static final DataSegmentPlus JAN_3_SEGMENT = createUsedSegment().startingAt(FIRST_DAY_OF_JAN.getEnd().plusDays(1)).withVersion("v3").asPlus();

    private HeapMemoryDatasourceSegmentCache cache;

    @Before
    public void setup() {
        cache = new HeapMemoryDatasourceSegmentCache(WIKI);
    }

    private static CreateDataSegments createUsedSegment() {
        return CreateDataSegments.ofDatasource(WIKI).markUsed();
    }

    private static CreateDataSegments createUnusedSegment() {
        return CreateDataSegments.ofDatasource(WIKI).markUnused();
    }

    private static DataSegmentPlus updateSegment(DataSegmentPlus segment, DateTime newUpdatedTime) {
        return new DataSegmentPlus(segment.getDataSegment(), segment.getCreatedDate(), newUpdatedTime, segment.getUsed(), segment.getSchemaFingerprint(), segment.getNumRows(), segment.getUpgradedFromSegmentId());
    }

    private static SegmentRecord asRecord(DataSegmentPlus segment) {
        return new SegmentRecord(segment.getDataSegment().getId(), Boolean.TRUE.equals(segment.getUsed()), segment.getUsedStatusLastUpdatedDate());
    }

    @Test
    public void testEmptyCache_1() {
        Assert.assertNull(cache.findUsedSegment(SegmentId.dummy(WIKI)));
    }

    @Test
    public void testEmptyCache_2() {
        Assert.assertTrue(cache.findUsedSegmentsPlusOverlappingAnyOf(List.of()).isEmpty());
    }

    @Test
    public void testEmptyCache_3() {
        Assert.assertTrue(cache.findPendingSegmentsOverlapping(Intervals.ETERNITY).isEmpty());
    }

    @Test
    public void testDeleteSegments_forEmptyOrAbsentIdsReturnsZero_1() {
        Assert.assertEquals(0, cache.deleteSegments(Set.of()));
    }

    @Test
    public void testDeleteSegments_forEmptyOrAbsentIdsReturnsZero_2() {
        Assert.assertEquals(0, cache.deleteSegments(Set.of(SegmentId.dummy(WIKI))));
    }
}
