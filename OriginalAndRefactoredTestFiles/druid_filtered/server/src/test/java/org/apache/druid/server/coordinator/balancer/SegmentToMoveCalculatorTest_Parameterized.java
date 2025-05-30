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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SegmentToMoveCalculatorTest_Parameterized {

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

    @ParameterizedTest
    @MethodSource("Provider_testMaxSegmentsToMove1Thread_1_1to2_2to3_3to4_4to5_5to6_6to7_7to8_8to9_9to10_10to11_11to12")
    public void testMaxSegmentsToMove1Thread_1_1to2_2to3_3to4_4to5_5to6_6to7_7to8_8to9_9to10_10to11_11to12(int param1, int param2, int param3) {
        Assert.assertEquals(param1, computeMaxSegmentsToMove(param2, param3));
    }

    static public Stream<Arguments> Provider_testMaxSegmentsToMove1Thread_1_1to2_2to3_3to4_4to5_5to6_6to7_7to8_8to9_9to10_10to11_11to12() {
        return Stream.of(arguments(0, 0, 1), arguments(50, 50, 1), arguments(100, 100, 1), arguments(100, 512, 1), arguments(200, "1_024", 1), arguments(300, "1_536", 1), arguments("1_900", "10_000", 1), arguments("9_700", "50_000", 1), arguments("19_500", "100_000", 1), arguments("10_000", "200_000", 1), arguments("4_000", "500_000", 1), arguments("2_000", "1_000_000", 1), arguments(0, 0, 8), arguments(50, 50, 8), arguments(100, 100, 8), arguments(100, 512, 8), arguments(200, "1_024", 8), arguments(300, "1_536", 8), arguments("33_000", "500_000", 8), arguments("16_000", "1_000_000", 8), arguments("8_000", "2_000_000", 8), arguments("3_000", "5_000_000", 8), arguments("1_000", "10_000_000", 8));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMaxSegmentsToMoveIncreasesWithCoordinatorPeriod_1to11")
    public void testMaxSegmentsToMoveIncreasesWithCoordinatorPeriod_1to11(int param1, String param2, int param3) {
        Assert.assertEquals(param1, computeMaxSegmentsToMoveInPeriod(param2, Duration.millis(param3)));
    }

    static public Stream<Arguments> Provider_testMaxSegmentsToMoveIncreasesWithCoordinatorPeriod_1to11() {
        return Stream.of(arguments(100, "200_000", 0), arguments(100, "200_000", "10_000"), arguments(100, "200_000", "20_000"), arguments("5_000", "200_000", "30_000"), arguments("10_000", "200_000", "60_000"), arguments("15_000", "200_000", "90_000"), arguments("20_000", "200_000", "120_000"), arguments("2_000", "500_000", "30_000"), arguments("4_000", "500_000", "60_000"), arguments("6_000", "500_000", "90_000"), arguments("8_000", "500_000", "120_000"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMinSegmentsToMove_1_1to2_2to3_3to4_4to5_5to6_6to7_7to8_8to11")
    public void testMinSegmentsToMove_1_1to2_2to3_3to4_4to5_5to6_6to7_7to8_8to11(int param1, int param2) {
        Assert.assertEquals(param1, computeMinSegmentsToMove(param2));
    }

    static public Stream<Arguments> Provider_testMinSegmentsToMove_1_1to2_2to3_3to4_4to5_5to6_6to7_7to8_8to11() {
        return Stream.of(arguments(0, 0), arguments(50, 50), arguments(100, 100), arguments(100, "1_000"), arguments(100, "20_000"), arguments(100, "50_000"), arguments(100, "100_000"), arguments(300, "200_000"), arguments(700, "500_000"), arguments("1_500", "1_000_000"), arguments("15_200", "10_000_000"), arguments(100, "131_071"), arguments(200, "131_072"), arguments(500, "393_215"), arguments(600, "393_216"), arguments(900, "655_359"), arguments(1000, "655_360"), arguments("9_900", "6_553_599"), arguments("10_000", "6_553_600"));
    }
}
