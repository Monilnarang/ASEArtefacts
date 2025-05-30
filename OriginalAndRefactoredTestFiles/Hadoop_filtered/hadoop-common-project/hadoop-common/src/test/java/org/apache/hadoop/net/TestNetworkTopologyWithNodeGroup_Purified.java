package org.apache.hadoop.net;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class TestNetworkTopologyWithNodeGroup_Purified {

    private final static NetworkTopologyWithNodeGroup cluster = new NetworkTopologyWithNodeGroup();

    private final static NodeBase[] dataNodes = new NodeBase[] { new NodeBase("h1", "/d1/r1/s1"), new NodeBase("h2", "/d1/r1/s1"), new NodeBase("h3", "/d1/r1/s2"), new NodeBase("h4", "/d1/r2/s3"), new NodeBase("h5", "/d1/r2/s3"), new NodeBase("h6", "/d1/r2/s4"), new NodeBase("h7", "/d2/r3/s5"), new NodeBase("h8", "/d2/r3/s6") };

    private final static NodeBase computeNode = new NodeBase("/d1/r1/s1/h9");

    private final static NodeBase rackOnlyNode = new NodeBase("h10", "/r2");

    static {
        for (int i = 0; i < dataNodes.length; i++) {
            cluster.add(dataNodes[i]);
        }
    }

    private Map<Node, Integer> pickNodesAtRandom(int numNodes, String excludedScope) {
        Map<Node, Integer> frequency = new HashMap<Node, Integer>();
        for (NodeBase dnd : dataNodes) {
            frequency.put(dnd, 0);
        }
        for (int j = 0; j < numNodes; j++) {
            Node random = cluster.chooseRandom(excludedScope);
            frequency.put(random, frequency.get(random) + 1);
        }
        return frequency;
    }

    @Test
    public void testRacks_1() throws Exception {
        assertEquals(3, cluster.getNumOfRacks());
    }

    @Test
    public void testRacks_2() throws Exception {
        assertTrue(cluster.isOnSameRack(dataNodes[0], dataNodes[1]));
    }

    @Test
    public void testRacks_3() throws Exception {
        assertTrue(cluster.isOnSameRack(dataNodes[1], dataNodes[2]));
    }

    @Test
    public void testRacks_4() throws Exception {
        assertFalse(cluster.isOnSameRack(dataNodes[2], dataNodes[3]));
    }

    @Test
    public void testRacks_5() throws Exception {
        assertTrue(cluster.isOnSameRack(dataNodes[3], dataNodes[4]));
    }

    @Test
    public void testRacks_6() throws Exception {
        assertTrue(cluster.isOnSameRack(dataNodes[4], dataNodes[5]));
    }

    @Test
    public void testRacks_7() throws Exception {
        assertFalse(cluster.isOnSameRack(dataNodes[5], dataNodes[6]));
    }

    @Test
    public void testRacks_8() throws Exception {
        assertTrue(cluster.isOnSameRack(dataNodes[6], dataNodes[7]));
    }

    @Test
    public void testNodeGroups_1() throws Exception {
        assertEquals(3, cluster.getNumOfRacks());
    }

    @Test
    public void testNodeGroups_2() throws Exception {
        assertTrue(cluster.isOnSameNodeGroup(dataNodes[0], dataNodes[1]));
    }

    @Test
    public void testNodeGroups_3() throws Exception {
        assertFalse(cluster.isOnSameNodeGroup(dataNodes[1], dataNodes[2]));
    }

    @Test
    public void testNodeGroups_4() throws Exception {
        assertFalse(cluster.isOnSameNodeGroup(dataNodes[2], dataNodes[3]));
    }

    @Test
    public void testNodeGroups_5() throws Exception {
        assertTrue(cluster.isOnSameNodeGroup(dataNodes[3], dataNodes[4]));
    }

    @Test
    public void testNodeGroups_6() throws Exception {
        assertFalse(cluster.isOnSameNodeGroup(dataNodes[4], dataNodes[5]));
    }

    @Test
    public void testNodeGroups_7() throws Exception {
        assertFalse(cluster.isOnSameNodeGroup(dataNodes[5], dataNodes[6]));
    }

    @Test
    public void testNodeGroups_8() throws Exception {
        assertFalse(cluster.isOnSameNodeGroup(dataNodes[6], dataNodes[7]));
    }

    @Test
    public void testGetDistance_1() throws Exception {
        assertEquals(0, cluster.getDistance(dataNodes[0], dataNodes[0]));
    }

    @Test
    public void testGetDistance_2() throws Exception {
        assertEquals(2, cluster.getDistance(dataNodes[0], dataNodes[1]));
    }

    @Test
    public void testGetDistance_3() throws Exception {
        assertEquals(4, cluster.getDistance(dataNodes[0], dataNodes[2]));
    }

    @Test
    public void testGetDistance_4() throws Exception {
        assertEquals(6, cluster.getDistance(dataNodes[0], dataNodes[3]));
    }

    @Test
    public void testGetDistance_5() throws Exception {
        assertEquals(8, cluster.getDistance(dataNodes[0], dataNodes[6]));
    }
}
