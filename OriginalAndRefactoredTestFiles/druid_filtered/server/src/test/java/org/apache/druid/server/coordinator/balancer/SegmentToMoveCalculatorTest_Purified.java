package org.apache.druid.server.coordinator.balancer;

import org.apache.druid.client.DruidServer;
import org.apache.druid.java.util.common.granularity.Granularities;
import org.apache.druid.segment.TestDataSource;
import org.apache.druid.server.coordination.ServerType;
import org.apache.druid.server.coordinator.CreateDataSegments;
import org.apache.druid.server.coordinator.ServerHolder;
import org.apache.druid.server.coordinator.loading.TestLoadQueuePeon;
import org.apache.druid.timeline.DataSegment;
import org.joda.time.Duration;
import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SegmentToMoveCalculatorTest_Purified {

    private static final Duration DEFAULT_COORDINATOR_PERIOD = Duration.standardMinutes(1);

    private static final List<DataSegment> WIKI_SEGMENTS = CreateDataSegments.ofDatasource(TestDataSource.WIKI).forIntervals(100, Granularities.DAY).withNumPartitions(100).eachOfSizeInMb(500);

    private static final List<DataSegment> KOALA_SEGMENTS = CreateDataSegments.ofDatasource(TestDataSource.KOALA).forIntervals(10, Granularities.DAY).eachOfSizeInMb(500);

    private static final String TIER = "tier1";

    private static int computeMaxSegmentsToMove(int totalSegments, int numThreads) {
        return SegmentToMoveCalculator.computeMaxSegmentsToMovePerTier(totalSegments, numThreads, DEFAULT_COORDINATOR_PERIOD);
    }

    private static int computeMaxSegmentsToMoveInPeriod(int totalSegments, Duration coordinatorPeriod) {
        return SegmentToMoveCalculator.computeMaxSegmentsToMovePerTier(totalSegments, 1, coordinatorPeriod);
    }

    private static int computeMinSegmentsToMove(int totalSegmentsInTier) {
        return SegmentToMoveCalculator.computeMinSegmentsToMoveInTier(totalSegmentsInTier);
    }

    private static ServerHolder createServer(String name, List<DataSegment> segments) {
        final DruidServer server = new DruidServer(name, name, null, 10L << 30, ServerType.HISTORICAL, "tier1", 1);
        segments.forEach(server::addDataSegment);
        return new ServerHolder(server.toImmutableDruidServer(), new TestLoadQueuePeon());
    }

    @Test
    public void testMaxSegmentsToMove1Thread_1() {
        Assert.assertEquals(0, computeMaxSegmentsToMove(0, 1));
    }

    @Test
    public void testMaxSegmentsToMove1Thread_2() {
        Assert.assertEquals(50, computeMaxSegmentsToMove(50, 1));
    }

    @Test
    public void testMaxSegmentsToMove1Thread_3() {
        Assert.assertEquals(100, computeMaxSegmentsToMove(100, 1));
    }

    @Test
    public void testMaxSegmentsToMove1Thread_4() {
        Assert.assertEquals(100, computeMaxSegmentsToMove(512, 1));
    }

    @Test
    public void testMaxSegmentsToMove1Thread_5() {
        Assert.assertEquals(200, computeMaxSegmentsToMove(1_024, 1));
    }

    @Test
    public void testMaxSegmentsToMove1Thread_6() {
        Assert.assertEquals(300, computeMaxSegmentsToMove(1_536, 1));
    }

    @Test
    public void testMaxSegmentsToMove1Thread_7() {
        Assert.assertEquals(1_900, computeMaxSegmentsToMove(10_000, 1));
    }

    @Test
    public void testMaxSegmentsToMove1Thread_8() {
        Assert.assertEquals(9_700, computeMaxSegmentsToMove(50_000, 1));
    }

    @Test
    public void testMaxSegmentsToMove1Thread_9() {
        Assert.assertEquals(19_500, computeMaxSegmentsToMove(100_000, 1));
    }

    @Test
    public void testMaxSegmentsToMove1Thread_10() {
        Assert.assertEquals(10_000, computeMaxSegmentsToMove(200_000, 1));
    }

    @Test
    public void testMaxSegmentsToMove1Thread_11() {
        Assert.assertEquals(4_000, computeMaxSegmentsToMove(500_000, 1));
    }

    @Test
    public void testMaxSegmentsToMove1Thread_12() {
        Assert.assertEquals(2_000, computeMaxSegmentsToMove(1_000_000, 1));
    }

    @Test
    public void testMaxSegmentsToMoveIncreasesWithCoordinatorPeriod_1() {
        Assert.assertEquals(100, computeMaxSegmentsToMoveInPeriod(200_000, Duration.millis(0)));
    }

    @Test
    public void testMaxSegmentsToMoveIncreasesWithCoordinatorPeriod_2() {
        Assert.assertEquals(100, computeMaxSegmentsToMoveInPeriod(200_000, Duration.millis(10_000)));
    }

    @Test
    public void testMaxSegmentsToMoveIncreasesWithCoordinatorPeriod_3() {
        Assert.assertEquals(100, computeMaxSegmentsToMoveInPeriod(200_000, Duration.millis(20_000)));
    }

    @Test
    public void testMaxSegmentsToMoveIncreasesWithCoordinatorPeriod_4() {
        Assert.assertEquals(5_000, computeMaxSegmentsToMoveInPeriod(200_000, Duration.millis(30_000)));
    }

    @Test
    public void testMaxSegmentsToMoveIncreasesWithCoordinatorPeriod_5() {
        Assert.assertEquals(10_000, computeMaxSegmentsToMoveInPeriod(200_000, Duration.millis(60_000)));
    }

    @Test
    public void testMaxSegmentsToMoveIncreasesWithCoordinatorPeriod_6() {
        Assert.assertEquals(15_000, computeMaxSegmentsToMoveInPeriod(200_000, Duration.millis(90_000)));
    }

    @Test
    public void testMaxSegmentsToMoveIncreasesWithCoordinatorPeriod_7() {
        Assert.assertEquals(20_000, computeMaxSegmentsToMoveInPeriod(200_000, Duration.millis(120_000)));
    }

    @Test
    public void testMaxSegmentsToMoveIncreasesWithCoordinatorPeriod_8() {
        Assert.assertEquals(2_000, computeMaxSegmentsToMoveInPeriod(500_000, Duration.millis(30_000)));
    }

    @Test
    public void testMaxSegmentsToMoveIncreasesWithCoordinatorPeriod_9() {
        Assert.assertEquals(4_000, computeMaxSegmentsToMoveInPeriod(500_000, Duration.millis(60_000)));
    }

    @Test
    public void testMaxSegmentsToMoveIncreasesWithCoordinatorPeriod_10() {
        Assert.assertEquals(6_000, computeMaxSegmentsToMoveInPeriod(500_000, Duration.millis(90_000)));
    }

    @Test
    public void testMaxSegmentsToMoveIncreasesWithCoordinatorPeriod_11() {
        Assert.assertEquals(8_000, computeMaxSegmentsToMoveInPeriod(500_000, Duration.millis(120_000)));
    }

    @Test
    public void testMaxSegmentsToMove8Threads_1() {
        Assert.assertEquals(0, computeMaxSegmentsToMove(0, 8));
    }

    @Test
    public void testMaxSegmentsToMove8Threads_2() {
        Assert.assertEquals(50, computeMaxSegmentsToMove(50, 8));
    }

    @Test
    public void testMaxSegmentsToMove8Threads_3() {
        Assert.assertEquals(100, computeMaxSegmentsToMove(100, 8));
    }

    @Test
    public void testMaxSegmentsToMove8Threads_4() {
        Assert.assertEquals(100, computeMaxSegmentsToMove(512, 8));
    }

    @Test
    public void testMaxSegmentsToMove8Threads_5() {
        Assert.assertEquals(200, computeMaxSegmentsToMove(1_024, 8));
    }

    @Test
    public void testMaxSegmentsToMove8Threads_6() {
        Assert.assertEquals(300, computeMaxSegmentsToMove(1_536, 8));
    }

    @Test
    public void testMaxSegmentsToMove8Threads_7() {
        Assert.assertEquals(33_000, computeMaxSegmentsToMove(500_000, 8));
    }

    @Test
    public void testMaxSegmentsToMove8Threads_8() {
        Assert.assertEquals(16_000, computeMaxSegmentsToMove(1_000_000, 8));
    }

    @Test
    public void testMaxSegmentsToMove8Threads_9() {
        Assert.assertEquals(8_000, computeMaxSegmentsToMove(2_000_000, 8));
    }

    @Test
    public void testMaxSegmentsToMove8Threads_10() {
        Assert.assertEquals(3_000, computeMaxSegmentsToMove(5_000_000, 8));
    }

    @Test
    public void testMaxSegmentsToMove8Threads_11() {
        Assert.assertEquals(1_000, computeMaxSegmentsToMove(10_000_000, 8));
    }

    @Test
    public void testMinSegmentsToMove_1() {
        Assert.assertEquals(0, computeMinSegmentsToMove(0));
    }

    @Test
    public void testMinSegmentsToMove_2() {
        Assert.assertEquals(50, computeMinSegmentsToMove(50));
    }

    @Test
    public void testMinSegmentsToMove_3() {
        Assert.assertEquals(100, computeMinSegmentsToMove(100));
    }

    @Test
    public void testMinSegmentsToMove_4() {
        Assert.assertEquals(100, computeMinSegmentsToMove(1_000));
    }

    @Test
    public void testMinSegmentsToMove_5() {
        Assert.assertEquals(100, computeMinSegmentsToMove(20_000));
    }

    @Test
    public void testMinSegmentsToMove_6() {
        Assert.assertEquals(100, computeMinSegmentsToMove(50_000));
    }

    @Test
    public void testMinSegmentsToMove_7() {
        Assert.assertEquals(100, computeMinSegmentsToMove(100_000));
    }

    @Test
    public void testMinSegmentsToMove_8() {
        Assert.assertEquals(300, computeMinSegmentsToMove(200_000));
    }

    @Test
    public void testMinSegmentsToMove_9() {
        Assert.assertEquals(700, computeMinSegmentsToMove(500_000));
    }

    @Test
    public void testMinSegmentsToMove_10() {
        Assert.assertEquals(1_500, computeMinSegmentsToMove(1_000_000));
    }

    @Test
    public void testMinSegmentsToMove_11() {
        Assert.assertEquals(15_200, computeMinSegmentsToMove(10_000_000));
    }

    @Test
    public void testMinSegmentsToMoveIncreasesInSteps_1() {
        Assert.assertEquals(100, computeMinSegmentsToMove(131_071));
    }

    @Test
    public void testMinSegmentsToMoveIncreasesInSteps_2() {
        Assert.assertEquals(200, computeMinSegmentsToMove(131_072));
    }

    @Test
    public void testMinSegmentsToMoveIncreasesInSteps_3() {
        Assert.assertEquals(500, computeMinSegmentsToMove(393_215));
    }

    @Test
    public void testMinSegmentsToMoveIncreasesInSteps_4() {
        Assert.assertEquals(600, computeMinSegmentsToMove(393_216));
    }

    @Test
    public void testMinSegmentsToMoveIncreasesInSteps_5() {
        Assert.assertEquals(900, computeMinSegmentsToMove(655_359));
    }

    @Test
    public void testMinSegmentsToMoveIncreasesInSteps_6() {
        Assert.assertEquals(1000, computeMinSegmentsToMove(655_360));
    }

    @Test
    public void testMinSegmentsToMoveIncreasesInSteps_7() {
        Assert.assertEquals(9_900, computeMinSegmentsToMove(6_553_599));
    }

    @Test
    public void testMinSegmentsToMoveIncreasesInSteps_8() {
        Assert.assertEquals(10_000, computeMinSegmentsToMove(6_553_600));
    }
}
