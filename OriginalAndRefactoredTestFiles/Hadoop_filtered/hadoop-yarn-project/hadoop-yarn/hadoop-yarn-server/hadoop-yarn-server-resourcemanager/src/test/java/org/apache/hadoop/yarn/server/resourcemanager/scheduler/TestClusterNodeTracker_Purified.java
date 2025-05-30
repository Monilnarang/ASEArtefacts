package org.apache.hadoop.yarn.server.resourcemanager.scheduler;

import java.util.Collections;
import java.util.List;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.api.records.Resource;
import org.apache.hadoop.yarn.api.records.ResourceRequest;
import org.apache.hadoop.yarn.server.resourcemanager.ClusterMetrics;
import org.apache.hadoop.yarn.server.resourcemanager.MockNodes;
import org.apache.hadoop.yarn.server.resourcemanager.rmnode.RMNode;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.fair.FSSchedulerNode;
import org.apache.hadoop.yarn.util.resource.ResourceUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestClusterNodeTracker_Purified {

    private ClusterNodeTracker<FSSchedulerNode> nodeTracker;

    private ClusterMetrics metrics;

    @Before
    public void setup() {
        metrics = ClusterMetrics.getMetrics();
        nodeTracker = new ClusterNodeTracker<>();
    }

    @After
    public void teardown() {
        ClusterMetrics.destroy();
    }

    private void addEight4x4Nodes() {
        MockNodes.resetHostIds();
        List<RMNode> rmNodes = MockNodes.newNodes(2, 4, Resource.newInstance(4096, 4));
        for (RMNode rmNode : rmNodes) {
            nodeTracker.addNode(new FSSchedulerNode(rmNode, false));
        }
    }

    @Test
    public void testGetNodeCount_1() {
        assertEquals("Incorrect number of nodes in the cluster", 8, nodeTracker.nodeCount());
    }

    @Test
    public void testGetNodeCount_2() {
        assertEquals("Incorrect number of nodes in each rack", 4, nodeTracker.nodeCount("rack0"));
    }

    @Test
    public void testIncrCapability_1() {
        assertEquals("Cluster Capability Memory incorrect", metrics.getCapabilityMB(), (4096 * 8));
    }

    @Test
    public void testIncrCapability_2() {
        assertEquals("Cluster Capability Vcores incorrect", metrics.getCapabilityVirtualCores(), 4 * 8);
    }

    @Test
    public void testGetNodesForResourceName_1() throws Exception {
        assertEquals("Incorrect number of nodes matching ANY", 8, nodeTracker.getNodesByResourceName(ResourceRequest.ANY).size());
    }

    @Test
    public void testGetNodesForResourceName_2() throws Exception {
        assertEquals("Incorrect number of nodes matching rack", 4, nodeTracker.getNodesByResourceName("rack0").size());
    }

    @Test
    public void testGetNodesForResourceName_3() throws Exception {
        assertEquals("Incorrect number of nodes matching node", 1, nodeTracker.getNodesByResourceName("host0").size());
    }
}
