package org.apache.hadoop.hdfs.server.diskbalancer;

import org.apache.hadoop.fs.StorageType;
import org.apache.hadoop.hdfs.server.diskbalancer.connectors.ClusterConnector;
import org.apache.hadoop.hdfs.server.diskbalancer.connectors.ConnectorFactory;
import org.apache.hadoop.hdfs.server.diskbalancer.connectors.NullConnector;
import org.apache.hadoop.hdfs.server.diskbalancer.datamodel.DiskBalancerCluster;
import org.apache.hadoop.hdfs.server.diskbalancer.datamodel.DiskBalancerDataNode;
import org.apache.hadoop.hdfs.server.diskbalancer.datamodel.DiskBalancerVolume;
import org.apache.hadoop.hdfs.server.diskbalancer.datamodel.DiskBalancerVolumeSet;
import org.apache.hadoop.hdfs.server.diskbalancer.planner.GreedyPlanner;
import org.apache.hadoop.hdfs.server.diskbalancer.planner.NodePlan;
import org.apache.hadoop.hdfs.server.diskbalancer.planner.Step;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestPlanner_Purified {

    static final Logger LOG = LoggerFactory.getLogger(TestPlanner.class);

    private DiskBalancerVolume createVolume(String path, int capacityInGB, int usedInGB) {
        DiskBalancerTestUtil util = new DiskBalancerTestUtil();
        DiskBalancerVolume volume = util.createRandomVolume(StorageType.SSD);
        volume.setPath(path);
        volume.setCapacity(capacityInGB * DiskBalancerTestUtil.GB);
        volume.setReserved(0);
        volume.setUsed(usedInGB * DiskBalancerTestUtil.GB);
        return volume;
    }

    @Test
    public void testGreedyPlannerOneVolumeNoPlanTest_1() throws Exception {
        NullConnector nullConnector = new NullConnector();
        DiskBalancerCluster cluster = new DiskBalancerCluster(nullConnector);
        cluster.readClusterInfo();
        Assert.assertEquals(1, cluster.getNodes().size());
    }

    @Test
    public void testGreedyPlannerOneVolumeNoPlanTest_2() throws Exception {
        DiskBalancerDataNode node = new DiskBalancerDataNode(UUID.randomUUID().toString());
        DiskBalancerVolume volume30 = createVolume("volume30", 100, 30);
        node.addVolume(volume30);
        nullConnector.addNode(node);
        NodePlan plan = new NodePlan(node.getDataNodeName(), node.getDataNodePort());
        planner.balanceVolumeSet(node, node.getVolumeSets().get("SSD"), plan);
        assertEquals(0, plan.getVolumeSetPlans().size());
    }

    @Test
    public void testGreedyPlannerTwoVolume_1() throws Exception {
        NullConnector nullConnector = new NullConnector();
        DiskBalancerCluster cluster = new DiskBalancerCluster(nullConnector);
        cluster.readClusterInfo();
        Assert.assertEquals(1, cluster.getNodes().size());
    }

    @Test
    public void testGreedyPlannerTwoVolume_2_testMerged_2() throws Exception {
        DiskBalancerDataNode node = new DiskBalancerDataNode(UUID.randomUUID().toString());
        DiskBalancerVolume volume30 = createVolume("volume30", 100, 30);
        DiskBalancerVolume volume10 = createVolume("volume10", 100, 10);
        node.addVolume(volume10);
        node.addVolume(volume30);
        nullConnector.addNode(node);
        NodePlan plan = new NodePlan(node.getDataNodeUUID(), node.getDataNodePort());
        planner.balanceVolumeSet(node, node.getVolumeSets().get("SSD"), plan);
        assertEquals(1, plan.getVolumeSetPlans().size());
        Step step = plan.getVolumeSetPlans().get(0);
        assertEquals("volume30", step.getSourceVolume().getPath());
        assertEquals("volume10", step.getDestinationVolume().getPath());
        assertEquals("10 G", step.getSizeString(step.getBytesToMove()));
    }

    @Test
    public void testGreedyPlannerEqualizeData_1() throws Exception {
        NullConnector nullConnector = new NullConnector();
        DiskBalancerCluster cluster = new DiskBalancerCluster(nullConnector);
        cluster.readClusterInfo();
        Assert.assertEquals(1, cluster.getNodes().size());
    }

    @Test
    public void testGreedyPlannerEqualizeData_2_testMerged_2() throws Exception {
        DiskBalancerDataNode node = new DiskBalancerDataNode(UUID.randomUUID().toString());
        DiskBalancerVolume volume30 = createVolume("volume30", 100, 30);
        DiskBalancerVolume volume20 = createVolume("volume20", 100, 20);
        DiskBalancerVolume volume10 = createVolume("volume10", 100, 10);
        node.addVolume(volume10);
        node.addVolume(volume20);
        node.addVolume(volume30);
        nullConnector.addNode(node);
        NodePlan plan = new NodePlan(node.getDataNodeUUID(), node.getDataNodePort());
        planner.balanceVolumeSet(node, node.getVolumeSets().get("SSD"), plan);
        assertEquals(1, plan.getVolumeSetPlans().size());
        Step step = plan.getVolumeSetPlans().get(0);
        assertEquals("volume30", step.getSourceVolume().getPath());
        assertEquals("volume10", step.getDestinationVolume().getPath());
        assertEquals("10 G", step.getSizeString(step.getBytesToMove()));
    }

    @Test
    public void testGreedyPlannerEqualDisksNoMoves_1() throws Exception {
        NullConnector nullConnector = new NullConnector();
        DiskBalancerCluster cluster = new DiskBalancerCluster(nullConnector);
        cluster.readClusterInfo();
        Assert.assertEquals(1, cluster.getNodes().size());
    }

    @Test
    public void testGreedyPlannerEqualDisksNoMoves_2() throws Exception {
        DiskBalancerDataNode node = new DiskBalancerDataNode(UUID.randomUUID().toString());
        DiskBalancerVolume volume1 = createVolume("volume1", 100, 30);
        DiskBalancerVolume volume2 = createVolume("volume2", 100, 30);
        DiskBalancerVolume volume3 = createVolume("volume3", 100, 30);
        node.addVolume(volume1);
        node.addVolume(volume2);
        node.addVolume(volume3);
        nullConnector.addNode(node);
        NodePlan plan = new NodePlan(node.getDataNodeName(), node.getDataNodePort());
        planner.balanceVolumeSet(node, node.getVolumeSets().get("SSD"), plan);
        assertEquals(0, plan.getVolumeSetPlans().size());
    }

    @Test
    public void testGreedyPlannerMoveFromSingleDisk_1() throws Exception {
        NullConnector nullConnector = new NullConnector();
        DiskBalancerCluster cluster = new DiskBalancerCluster(nullConnector);
        cluster.readClusterInfo();
        Assert.assertEquals(1, cluster.getNodes().size());
    }

    @Test
    public void testGreedyPlannerMoveFromSingleDisk_2_testMerged_2() throws Exception {
        DiskBalancerDataNode node = new DiskBalancerDataNode(UUID.randomUUID().toString());
        DiskBalancerVolume volume1 = createVolume("volume100", 200, 100);
        DiskBalancerVolume volume2 = createVolume("volume0-1", 200, 0);
        DiskBalancerVolume volume3 = createVolume("volume0-2", 200, 0);
        node.addVolume(volume1);
        node.addVolume(volume2);
        node.addVolume(volume3);
        nullConnector.addNode(node);
        NodePlan plan = new NodePlan(node.getDataNodeName(), node.getDataNodePort());
        planner.balanceVolumeSet(node, node.getVolumeSets().get("SSD"), plan);
        assertEquals(2, plan.getVolumeSetPlans().size());
        Step step = plan.getVolumeSetPlans().get(0);
        assertEquals("volume100", step.getSourceVolume().getPath());
        assertTrue(step.getSizeString(step.getBytesToMove()).matches("33.[2|3|4] G"));
    }

    @Test
    public void testGreedyPlannerThresholdTest_1() throws Exception {
        NullConnector nullConnector = new NullConnector();
        DiskBalancerCluster cluster = new DiskBalancerCluster(nullConnector);
        cluster.readClusterInfo();
        Assert.assertEquals(1, cluster.getNodes().size());
    }

    @Test
    public void testGreedyPlannerThresholdTest_2_testMerged_2() throws Exception {
        DiskBalancerDataNode node = new DiskBalancerDataNode(UUID.randomUUID().toString());
        DiskBalancerVolume volume1 = createVolume("volume100", 1000, 100);
        DiskBalancerVolume volume2 = createVolume("volume0-1", 300, 0);
        DiskBalancerVolume volume3 = createVolume("volume0-2", 300, 0);
        node.addVolume(volume1);
        node.addVolume(volume2);
        node.addVolume(volume3);
        nullConnector.addNode(node);
        NodePlan plan = new NodePlan(node.getDataNodeName(), node.getDataNodePort());
        planner.balanceVolumeSet(node, node.getVolumeSets().get("SSD"), plan);
        assertEquals(0, plan.getVolumeSetPlans().size());
        NodePlan newPlan = new NodePlan(node.getDataNodeName(), node.getDataNodePort());
        newPlanner.balanceVolumeSet(node, node.getVolumeSets().get("SSD"), newPlan);
        assertEquals(2, newPlan.getVolumeSetPlans().size());
        Step step = newPlan.getVolumeSetPlans().get(0);
        assertEquals("volume100", step.getSourceVolume().getPath());
        assertTrue(step.getSizeString(step.getBytesToMove()).matches("18.[6|7|8] G"));
    }

    @Test
    public void testGreedyPlannerLargeDisksWithData_1() throws Exception {
        NullConnector nullConnector = new NullConnector();
        DiskBalancerCluster cluster = new DiskBalancerCluster(nullConnector);
        cluster.readClusterInfo();
        Assert.assertEquals(1, cluster.getNodes().size());
    }

    @Test
    public void testGreedyPlannerLargeDisksWithData_2() throws Exception {
        DiskBalancerDataNode node = new DiskBalancerDataNode(UUID.randomUUID().toString());
        DiskBalancerVolume volume1 = createVolume("volume1", 1968, 88);
        DiskBalancerVolume volume2 = createVolume("volume2", 1968, 88);
        DiskBalancerVolume volume3 = createVolume("volume3", 1968, 111);
        DiskBalancerVolume volume4 = createVolume("volume4", 1968, 111);
        DiskBalancerVolume volume5 = createVolume("volume5", 1968, 30);
        DiskBalancerVolume volume6 = createVolume("volume6", 1563, 30);
        DiskBalancerVolume volume7 = createVolume("volume7", 1563, 30);
        DiskBalancerVolume volume8 = createVolume("volume8", 1563, 30);
        DiskBalancerVolume volume9 = createVolume("volume9", 1563, 210);
        node.addVolume(volume1);
        node.addVolume(volume2);
        node.addVolume(volume3);
        node.addVolume(volume4);
        node.addVolume(volume5);
        node.addVolume(volume6);
        node.addVolume(volume7);
        node.addVolume(volume8);
        node.addVolume(volume9);
        nullConnector.addNode(node);
        NodePlan plan = new NodePlan(node.getDataNodeName(), node.getDataNodePort());
        planner.balanceVolumeSet(node, node.getVolumeSets().get("SSD"), plan);
        assertTrue(plan.getVolumeSetPlans().size() > 2);
    }
}
