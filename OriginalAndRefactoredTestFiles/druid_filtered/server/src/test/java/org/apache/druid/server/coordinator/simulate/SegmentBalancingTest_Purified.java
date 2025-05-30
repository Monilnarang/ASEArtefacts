package org.apache.druid.server.coordinator.simulate;

import org.apache.druid.client.DruidServer;
import org.apache.druid.segment.TestDataSource;
import org.apache.druid.server.coordinator.CoordinatorDynamicConfig;
import org.apache.druid.timeline.DataSegment;
import org.junit.Assert;
import org.junit.Test;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SegmentBalancingTest_Purified extends CoordinatorSimulationBaseTest {

    private DruidServer historicalT11;

    private DruidServer historicalT12;

    private final String datasource = TestDataSource.WIKI;

    private final List<DataSegment> segments = Segments.WIKI_10X1D;

    @Override
    public void setUp() {
        historicalT11 = createHistorical(1, Tier.T1, 10_000);
        historicalT12 = createHistorical(2, Tier.T1, 10_000);
    }

    @Test
    public void testBalancingMovesSegmentsInLoadQueue_1() {
        CoordinatorSimulation sim = CoordinatorSimulation.builder().withSegments(segments).withServers(historicalT11).withRules(datasource, Load.on(Tier.T1, 1).forever()).build();
        verifyValue(Metric.LOAD_QUEUE_COUNT, filterByServer(historicalT11), 10L);
        verifyValue(Metric.LOAD_QUEUE_COUNT, filterByServer(historicalT11), 5L);
        Assert.assertEquals(5, historicalT11.getTotalSegments());
    }

    @Test
    public void testBalancingMovesSegmentsInLoadQueue_2() {
        addServer(historicalT12);
        verifyValue(Metric.LOAD_QUEUE_COUNT, filterByServer(historicalT12), 5L);
        Assert.assertEquals(5, historicalT12.getTotalSegments());
    }
}
