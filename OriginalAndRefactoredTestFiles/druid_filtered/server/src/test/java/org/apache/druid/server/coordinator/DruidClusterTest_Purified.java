package org.apache.druid.server.coordinator;

import org.apache.druid.client.DruidServer;
import org.apache.druid.java.util.common.granularity.Granularities;
import org.apache.druid.server.coordination.ServerType;
import org.apache.druid.server.coordinator.loading.TestLoadQueuePeon;
import org.apache.druid.timeline.DataSegment;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.stream.Collectors;

public class DruidClusterTest_Purified {

    private static final List<DataSegment> SEGMENTS = CreateDataSegments.ofDatasource("test").forIntervals(2, Granularities.DAY).startingAt("2015-04-12").withNumPartitions(1).eachOfSizeInMb(100);

    private static final ServerHolder NEW_REALTIME = new ServerHolder(new DruidServer("name1", "host2", null, 100L, ServerType.REALTIME, "tier1", 0).addDataSegment(SEGMENTS.get(0)).toImmutableDruidServer(), new TestLoadQueuePeon());

    private static final ServerHolder NEW_HISTORICAL = new ServerHolder(new DruidServer("name1", "host2", null, 100L, ServerType.HISTORICAL, "tier1", 0).addDataSegment(SEGMENTS.get(0)).toImmutableDruidServer(), new TestLoadQueuePeon());

    private DruidCluster.Builder clusterBuilder;

    @Before
    public void setup() {
        clusterBuilder = DruidCluster.builder().add(new ServerHolder(new DruidServer("name1", "host1", null, 100L, ServerType.REALTIME, "tier1", 0).addDataSegment(SEGMENTS.get(0)).toImmutableDruidServer(), new TestLoadQueuePeon())).add(new ServerHolder(new DruidServer("name1", "host1", null, 100L, ServerType.HISTORICAL, "tier1", 0).addDataSegment(SEGMENTS.get(0)).toImmutableDruidServer(), new TestLoadQueuePeon()));
    }

    @Test
    public void testIsEmpty_1() {
        Assert.assertFalse(clusterBuilder.build().isEmpty());
    }

    @Test
    public void testIsEmpty_2() {
        final DruidCluster emptyCluster = DruidCluster.EMPTY;
        Assert.assertTrue(emptyCluster.isEmpty());
    }
}
