package org.apache.hadoop.net;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestNetworkTopologyWithNodeGroup_Parameterized {

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

    @ParameterizedTest
    @MethodSource("Provider_testRacks_1_1")
    public void testRacks_1_1(int param1) throws Exception {
        assertEquals(param1, cluster.getNumOfRacks());
    }

    static public Stream<Arguments> Provider_testRacks_1_1() {
        return Stream.of(arguments(3), arguments(3));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRacks_2to3_5to6_8")
    public void testRacks_2to3_5to6_8(int param1, int param2) throws Exception {
        assertTrue(cluster.isOnSameRack(dataNodes[param1], dataNodes[param2]));
    }

    static public Stream<Arguments> Provider_testRacks_2to3_5to6_8() {
        return Stream.of(arguments(0, 1), arguments(1, 2), arguments(3, 4), arguments(4, 5), arguments(6, 7));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRacks_4_7")
    public void testRacks_4_7(int param1, int param2) throws Exception {
        assertFalse(cluster.isOnSameRack(dataNodes[param1], dataNodes[param2]));
    }

    static public Stream<Arguments> Provider_testRacks_4_7() {
        return Stream.of(arguments(2, 3), arguments(5, 6));
    }

    @ParameterizedTest
    @MethodSource("Provider_testNodeGroups_2_5")
    public void testNodeGroups_2_5(int param1, int param2) throws Exception {
        assertTrue(cluster.isOnSameNodeGroup(dataNodes[param1], dataNodes[param2]));
    }

    static public Stream<Arguments> Provider_testNodeGroups_2_5() {
        return Stream.of(arguments(0, 1), arguments(3, 4));
    }

    @ParameterizedTest
    @MethodSource("Provider_testNodeGroups_3to4_6to8")
    public void testNodeGroups_3to4_6to8(int param1, int param2) throws Exception {
        assertFalse(cluster.isOnSameNodeGroup(dataNodes[param1], dataNodes[param2]));
    }

    static public Stream<Arguments> Provider_testNodeGroups_3to4_6to8() {
        return Stream.of(arguments(1, 2), arguments(2, 3), arguments(4, 5), arguments(5, 6), arguments(6, 7));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetDistance_1to5")
    public void testGetDistance_1to5(int param1, int param2, int param3) throws Exception {
        assertEquals(param1, cluster.getDistance(dataNodes[param2], dataNodes[param3]));
    }

    static public Stream<Arguments> Provider_testGetDistance_1to5() {
        return Stream.of(arguments(0, 0, 0), arguments(2, 0, 1), arguments(4, 0, 2), arguments(6, 0, 3), arguments(8, 0, 6));
    }
}
