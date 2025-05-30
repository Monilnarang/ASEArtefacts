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

public class CostBalancerStrategyTest_Purified {

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
    public void testIntervalCostAdditivity_1() {
        Assert.assertEquals(intervalCost(1, 1, 3), intervalCost(1, 1, 2) + intervalCost(1, 2, 3), DELTA);
    }

    @Test
    public void testIntervalCostAdditivity_2() {
        Assert.assertEquals(intervalCost(2, 1, 3), intervalCost(2, 1, 2) + intervalCost(2, 2, 3), DELTA);
    }

    @Test
    public void testIntervalCostAdditivity_3() {
        Assert.assertEquals(intervalCost(3, 1, 2), intervalCost(1, 0, 1) + intervalCost(1, 1, 2) + intervalCost(1, 1, 2), DELTA);
    }

    @Test
    public void testIntervalCost_1() {
        Assert.assertEquals(0.3995764, intervalCost(1, 1, 2), DELTA);
    }

    @Test
    public void testIntervalCost_2() {
        Assert.assertEquals(0.3995764, intervalCost(1, -1, 0), DELTA);
    }

    @Test
    public void testIntervalCost_3() {
        Assert.assertEquals(0.7357589, intervalCost(1, 0, 1), DELTA);
    }

    @Test
    public void testIntervalCost_4() {
        Assert.assertEquals(2.270671, intervalCost(2, 0, 2), DELTA);
    }

    @Test
    public void testIntervalCost_5() {
        Assert.assertEquals(1.681908, intervalCost(2, 1, 3), DELTA);
    }

    @Test
    public void testIntervalCost_6() {
        Assert.assertEquals(1.135335, intervalCost(2, 1, 2), DELTA);
    }

    @Test
    public void testIntervalCost_7() {
        Assert.assertEquals(1.135335, intervalCost(2, 0, 1), DELTA);
    }

    @Test
    public void testIntervalCost_8() {
        Assert.assertEquals(1.534912, intervalCost(3, 1, 2), DELTA);
    }
}
