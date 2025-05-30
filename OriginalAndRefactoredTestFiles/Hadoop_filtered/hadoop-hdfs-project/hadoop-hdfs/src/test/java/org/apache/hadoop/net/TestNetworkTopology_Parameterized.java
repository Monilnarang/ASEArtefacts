package org.apache.hadoop.net;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.DFSTestUtil;
import org.apache.hadoop.hdfs.HdfsConfiguration;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;
import org.apache.hadoop.hdfs.protocol.HdfsConstants.DatanodeReportType;
import org.apache.hadoop.hdfs.server.blockmanagement.DatanodeDescriptor;
import org.apache.hadoop.hdfs.server.protocol.NamenodeProtocols;
import org.apache.hadoop.test.GenericTestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestNetworkTopology_Parameterized {

    private static final Logger LOG = LoggerFactory.getLogger(TestNetworkTopology.class);

    private final static NetworkTopology cluster = NetworkTopology.getInstance(new Configuration());

    private DatanodeDescriptor[] dataNodes;

    @Rule
    public Timeout testTimeout = new Timeout(30000, TimeUnit.MILLISECONDS);

    @Before
    public void setupDatanodes() {
        dataNodes = new DatanodeDescriptor[] { DFSTestUtil.getDatanodeDescriptor("1.1.1.1", "/d1/r1"), DFSTestUtil.getDatanodeDescriptor("2.2.2.2", "/d1/r1"), DFSTestUtil.getDatanodeDescriptor("3.3.3.3", "/d1/r2"), DFSTestUtil.getDatanodeDescriptor("4.4.4.4", "/d1/r2"), DFSTestUtil.getDatanodeDescriptor("5.5.5.5", "/d1/r2"), DFSTestUtil.getDatanodeDescriptor("6.6.6.6", "/d2/r3"), DFSTestUtil.getDatanodeDescriptor("7.7.7.7", "/d2/r3"), DFSTestUtil.getDatanodeDescriptor("8.8.8.8", "/d2/r3"), DFSTestUtil.getDatanodeDescriptor("9.9.9.9", "/d3/r1"), DFSTestUtil.getDatanodeDescriptor("10.10.10.10", "/d3/r1"), DFSTestUtil.getDatanodeDescriptor("11.11.11.11", "/d3/r1"), DFSTestUtil.getDatanodeDescriptor("12.12.12.12", "/d3/r2"), DFSTestUtil.getDatanodeDescriptor("13.13.13.13", "/d3/r2"), DFSTestUtil.getDatanodeDescriptor("14.14.14.14", "/d4/r1"), DFSTestUtil.getDatanodeDescriptor("15.15.15.15", "/d4/r1"), DFSTestUtil.getDatanodeDescriptor("16.16.16.16", "/d4/r1"), DFSTestUtil.getDatanodeDescriptor("17.17.17.17", "/d4/r1"), DFSTestUtil.getDatanodeDescriptor("18.18.18.18", "/d4/r1"), DFSTestUtil.getDatanodeDescriptor("19.19.19.19", "/d4/r1"), DFSTestUtil.getDatanodeDescriptor("20.20.20.20", "/d4/r1") };
        for (int i = 0; i < dataNodes.length; i++) {
            cluster.add(dataNodes[i]);
        }
        dataNodes[9].setDecommissioned();
        dataNodes[10].setDecommissioned();
        GenericTestUtils.setLogLevel(NetworkTopology.LOG, Level.TRACE);
    }

    private Map<Node, Integer> pickNodesAtRandom(int numNodes, String excludedScope, Collection<Node> excludedNodes) {
        Map<Node, Integer> frequency = new HashMap<Node, Integer>();
        for (DatanodeDescriptor dnd : dataNodes) {
            frequency.put(dnd, 0);
        }
        for (int j = 0; j < numNodes; j++) {
            Node random = cluster.chooseRandom(excludedScope, excludedNodes);
            if (random != null) {
                frequency.put(random, frequency.get(random) + 1);
            }
        }
        LOG.info("Result:" + frequency);
        return frequency;
    }

    private void verifyResults(int upperbound, Set<Node> excludedNodes, Map<Node, Integer> frequency) {
        LOG.info("Excluded nodes are: {}", excludedNodes);
        for (int i = 0; i < upperbound; ++i) {
            final Node n = dataNodes[i];
            LOG.info("Verifying node {}", n);
            if (excludedNodes.contains(n)) {
                assertEquals(n + " should not have been chosen.", 0, (int) frequency.get(n));
            } else {
                assertTrue(n + " should have been chosen", frequency.get(n) > 0);
            }
        }
    }

    @Test
    public void testRacks_1() throws Exception {
        assertEquals(cluster.getNumOfRacks(), 6);
    }

    @Test
    public void testGetWeight_1_testMerged_1() throws Exception {
        DatanodeDescriptor nodeInMap = dataNodes[0];
        assertEquals(0, cluster.getWeight(nodeInMap, dataNodes[0]));
        assertEquals(2, cluster.getWeight(nodeInMap, dataNodes[1]));
        assertEquals(4, cluster.getWeight(nodeInMap, dataNodes[2]));
    }

    @Test
    public void testGetWeight_4_testMerged_2() throws Exception {
        DatanodeDescriptor nodeNotInMap = DFSTestUtil.getDatanodeDescriptor("21.21.21.21", "/d1/r2");
        assertEquals(4, cluster.getWeightUsingNetworkLocation(nodeNotInMap, dataNodes[0]));
        assertEquals(4, cluster.getWeightUsingNetworkLocation(nodeNotInMap, dataNodes[1]));
        assertEquals(2, cluster.getWeightUsingNetworkLocation(nodeNotInMap, dataNodes[2]));
    }

    @Test
    public void testGetDistance_5_testMerged_5() throws Exception {
        NodeBase node1 = new NodeBase(dataNodes[0].getHostName(), dataNodes[0].getNetworkLocation());
        NodeBase node2 = new NodeBase(dataNodes[0].getHostName(), dataNodes[0].getNetworkLocation());
        assertEquals(0, cluster.getDistance(node1, node2));
        NodeBase node3 = new NodeBase(dataNodes[3].getHostName(), dataNodes[3].getNetworkLocation());
        NodeBase node4 = new NodeBase(dataNodes[6].getHostName(), dataNodes[6].getNetworkLocation());
        assertEquals(0, NetworkTopology.getDistanceByPath(node1, node2));
        assertEquals(4, NetworkTopology.getDistanceByPath(node2, node3));
        assertEquals(6, NetworkTopology.getDistanceByPath(node2, node4));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRacks_2_4to5_7")
    public void testRacks_2_4to5_7(int param1, int param2) throws Exception {
        assertTrue(cluster.isOnSameRack(dataNodes[param1], dataNodes[param2]));
    }

    static public Stream<Arguments> Provider_testRacks_2_4to5_7() {
        return Stream.of(arguments(0, 1), arguments(2, 3), arguments(3, 4), arguments(5, 6));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRacks_3_6")
    public void testRacks_3_6(int param1, int param2) throws Exception {
        assertFalse(cluster.isOnSameRack(dataNodes[param1], dataNodes[param2]));
    }

    static public Stream<Arguments> Provider_testRacks_3_6() {
        return Stream.of(arguments(1, 2), arguments(4, 5));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetDistance_1to4")
    public void testGetDistance_1to4(int param1, int param2, int param3) throws Exception {
        assertEquals(cluster.getDistance(dataNodes[param2], dataNodes[param3]), param1);
    }

    static public Stream<Arguments> Provider_testGetDistance_1to4() {
        return Stream.of(arguments(0, 0, 0), arguments(2, 0, 1), arguments(4, 0, 3), arguments(6, 0, 6));
    }
}
