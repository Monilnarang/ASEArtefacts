package org.apache.druid.server.coordinator.balancer;

import com.google.common.util.concurrent.MoreExecutors;
import org.apache.druid.client.DruidServer;
import org.apache.druid.java.util.common.concurrent.Execs;
import org.apache.druid.java.util.common.granularity.Granularities;
import org.apache.druid.java.util.common.granularity.GranularityType;
import org.apache.druid.java.util.emitter.EmittingLogger;
import org.apache.druid.java.util.metrics.StubServiceEmitter;
import org.apache.druid.segment.TestDataSource;
import org.apache.druid.server.coordination.ServerType;
import org.apache.druid.server.coordinator.CreateDataSegments;
import org.apache.druid.server.coordinator.ServerHolder;
import org.apache.druid.server.coordinator.loading.TestLoadQueuePeon;
import org.apache.druid.server.coordinator.stats.CoordinatorRunStats;
import org.apache.druid.server.coordinator.stats.Stats;
import org.apache.druid.timeline.DataSegment;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class CostBalancerStrategyTest_Parameterized {

    private static final double DELTA = 1e-6;

    private ExecutorService balancerExecutor;

    private CostBalancerStrategy strategy;

    private int uniqueServerId;

    @Before
    public void setup() {
        balancerExecutor = Execs.singleThreaded("test-balance-exec-%d");
        strategy = new CostBalancerStrategy(MoreExecutors.listeningDecorator(balancerExecutor));
        StubServiceEmitter serviceEmitter = new StubServiceEmitter("test-service", "host");
        EmittingLogger.registerEmitter(serviceEmitter);
    }

    @After
    public void tearDown() {
        if (balancerExecutor != null) {
            balancerExecutor.shutdownNow();
        }
    }

    private double intervalCost(double x1, double y0, double y1) {
        return CostBalancerStrategy.intervalCost(x1, y0, y1);
    }

    private void verifyPlacementCost(DataSegment segment, ServerHolder server, double expectedCost) {
        double observedCost = strategy.computePlacementCost(segment, server);
        Assert.assertEquals(expectedCost, observedCost, DELTA);
        double totalJointSegmentCost = 0;
        for (DataSegment segmentOnServer : server.getServer().iterateAllSegments()) {
            totalJointSegmentCost += CostBalancerStrategy.computeJointSegmentsCost(segment, segmentOnServer);
        }
        if (server.isServingSegment(segment)) {
            totalJointSegmentCost -= CostBalancerStrategy.computeJointSegmentsCost(segment, segment);
        }
        Assert.assertEquals(totalJointSegmentCost, observedCost, DELTA);
    }

    private void verifyJointSegmentsCost(GranularityType granularityX, GranularityType granularityY, long startGapMillis, double expectedCost) {
        final DataSegment segmentX = CreateDataSegments.ofDatasource(TestDataSource.WIKI).forIntervals(1, granularityX.getDefaultGranularity()).startingAt("2012-10-24").eachOfSizeInMb(100).get(0);
        DateTime startTimeY = segmentX.getInterval().getStart().plus(startGapMillis);
        final DataSegment segmentY = CreateDataSegments.ofDatasource(TestDataSource.WIKI).forIntervals(1, granularityY.getDefaultGranularity()).startingAt(startTimeY).eachOfSizeInMb(100).get(0);
        double observedCost = CostBalancerStrategy.computeJointSegmentsCost(segmentX, segmentY);
        Assert.assertEquals(expectedCost, observedCost, DELTA);
    }

    private DruidServer createHistorical() {
        String serverName = "hist_" + uniqueServerId++;
        return new DruidServer(serverName, serverName, null, 10L << 30, ServerType.HISTORICAL, "hot", 1);
    }

    @Test
    public void testIntervalCostAdditivity_3() {
        Assert.assertEquals(intervalCost(3, 1, 2), intervalCost(1, 0, 1) + intervalCost(1, 1, 2) + intervalCost(1, 1, 2), DELTA);
    }

    @Test
    public void testIntervalCost_2() {
        Assert.assertEquals(0.3995764, intervalCost(1, -1, 0), DELTA);
    }

    @ParameterizedTest
    @MethodSource("Provider_testIntervalCostAdditivity_1to2")
    public void testIntervalCostAdditivity_1to2(int param1, int param2, int param3, int param4, int param5, int param6, int param7, int param8, int param9) {
        Assert.assertEquals(intervalCost(param1, param2, param3), intervalCost(param4, param5, param6) + intervalCost(param7, param8, param9), DELTA);
    }

    static public Stream<Arguments> Provider_testIntervalCostAdditivity_1to2() {
        return Stream.of(arguments(1, 1, 3, 1, 1, 2, 1, 2, 3), arguments(2, 1, 3, 2, 1, 2, 2, 2, 3));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIntervalCost_1_3to8")
    public void testIntervalCost_1_3to8(double param1, int param2, int param3, int param4) {
        Assert.assertEquals(param1, intervalCost(param2, param3, param4), DELTA);
    }

    static public Stream<Arguments> Provider_testIntervalCost_1_3to8() {
        return Stream.of(arguments(0.3995764, 1, 1, 2), arguments(0.7357589, 1, 0, 1), arguments(2.270671, 2, 0, 2), arguments(1.681908, 2, 1, 3), arguments(1.135335, 2, 1, 2), arguments(1.135335, 2, 0, 1), arguments(1.534912, 3, 1, 2));
    }
}
