package com.graphhopper.routing.util.parsers;

import com.carrotsearch.hppc.BitSet;
import com.carrotsearch.hppc.IntArrayList;
import com.carrotsearch.hppc.IntIndexedContainer;
import com.graphhopper.routing.Dijkstra;
import com.graphhopper.routing.Path;
import com.graphhopper.routing.ev.BooleanEncodedValue;
import com.graphhopper.routing.ev.DecimalEncodedValue;
import com.graphhopper.routing.ev.DecimalEncodedValueImpl;
import com.graphhopper.routing.ev.TurnRestriction;
import com.graphhopper.routing.querygraph.QueryGraph;
import com.graphhopper.routing.util.EdgeFilter;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.routing.util.TraversalMode;
import com.graphhopper.routing.weighting.SpeedWeighting;
import com.graphhopper.routing.weighting.TurnCostProvider;
import com.graphhopper.storage.BaseGraph;
import com.graphhopper.storage.Graph;
import com.graphhopper.storage.NodeAccess;
import com.graphhopper.storage.index.LocationIndex;
import com.graphhopper.storage.index.LocationIndexTree;
import com.graphhopper.storage.index.Snap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

public class RestrictionSetterTest_Purified {

    private DecimalEncodedValue speedEnc;

    private BooleanEncodedValue turnRestrictionEnc;

    private BooleanEncodedValue turnRestrictionEnc2;

    private BaseGraph graph;

    private RestrictionSetter r;

    @BeforeEach
    void setup() {
        speedEnc = new DecimalEncodedValueImpl("speed", 5, 5, true);
        turnRestrictionEnc = TurnRestriction.create("car1");
        turnRestrictionEnc2 = TurnRestriction.create("car2");
        EncodingManager encodingManager = EncodingManager.start().add(speedEnc).add(turnRestrictionEnc).add(turnRestrictionEnc2).build();
        graph = new BaseGraph.Builder(encodingManager).withTurnCosts(true).create();
        r = new RestrictionSetter(graph, List.of(turnRestrictionEnc, turnRestrictionEnc2));
    }

    private RestrictionSetter.Restriction createViaNodeRestriction(int fromEdge, int viaNode, int toEdge) {
        return RestrictionSetter.createViaNodeRestriction(fromEdge, viaNode, toEdge);
    }

    private RestrictionSetter.Restriction createViaEdgeRestriction(int... edges) {
        return RestrictionSetter.createViaEdgeRestriction(IntArrayList.from(edges));
    }

    private void setRestrictions(RestrictionSetter.Restriction... restrictions) {
        setRestrictions(List.of(restrictions), Stream.of(restrictions).map(r -> encBits(1, 0)).toList());
    }

    private void setRestrictions(List<RestrictionSetter.Restriction> restrictions, List<BitSet> encBits) {
        r.setRestrictions(restrictions, encBits);
    }

    private void assertPath(int from, int to, IntArrayList expectedNodes) {
        assertPath(graph, from, to, turnRestrictionEnc, expectedNodes);
    }

    private void assertPath(int from, int to, BooleanEncodedValue turnRestrictionEnc, IntArrayList expectedNodes) {
        assertPath(graph, from, to, turnRestrictionEnc, expectedNodes);
    }

    private void assertPath(Graph graph, int from, int to, IntArrayList expectedNodes) {
        assertPath(graph, from, to, turnRestrictionEnc, expectedNodes);
    }

    private void assertPath(Graph graph, int from, int to, BooleanEncodedValue turnRestrictionEnc, IntArrayList expectedNodes) {
        Path path = calcPath(graph, from, to, turnRestrictionEnc);
        if (expectedNodes == null)
            assertFalse(path.isFound(), "Did not expect to find a path, but found: " + path.calcNodes() + ", edges: " + path.calcEdges());
        else {
            assertTrue(path.isFound(), "Expected path: " + expectedNodes + ", but did not find it");
            IntIndexedContainer nodes = path.calcNodes();
            assertEquals(expectedNodes, nodes);
        }
    }

    private Path calcPath(Graph graph, int from, int to, BooleanEncodedValue turnRestrictionEnc) {
        Dijkstra dijkstra = new Dijkstra(graph, graph.wrapWeighting(new SpeedWeighting(speedEnc, new TurnCostProvider() {

            @Override
            public double calcTurnWeight(int inEdge, int viaNode, int outEdge) {
                if (inEdge == outEdge)
                    return Double.POSITIVE_INFINITY;
                return graph.getTurnCostStorage().get(turnRestrictionEnc, inEdge, viaNode, outEdge) ? Double.POSITIVE_INFINITY : 0;
            }

            @Override
            public long calcTurnMillis(int inEdge, int viaNode, int outEdge) {
                return Double.isInfinite(calcTurnWeight(inEdge, viaNode, outEdge)) ? Long.MAX_VALUE : 0L;
            }
        })), TraversalMode.EDGE_BASED);
        return dijkstra.calcPath(from, to);
    }

    private IntArrayList nodes(int... nodes) {
        return IntArrayList.from(nodes);
    }

    private BitSet encBits(int... bits) {
        BitSet b = new BitSet(bits.length);
        for (int i = 0; i < bits.length; i++) {
            if (bits[i] != 0 && bits[i] != 1)
                throw new IllegalArgumentException("bits must be 0 or 1");
            if (bits[i] > 0)
                b.set(i);
        }
        return b;
    }

    private int edge(int from, int to) {
        return edge(from, to, true);
    }

    private int edge(int from, int to, boolean bothDir) {
        return graph.edge(from, to).setDistance(100).set(speedEnc, 10, bothDir ? 10 : 0).getEdge();
    }

    @Test
    void viaEdge_no_1() {
        assertPath(0, 3, nodes(0, 1, 5, 8, 9, 6, 2, 3));
    }

    @Test
    void viaEdge_no_2() {
        assertPath(0, 4, nodes(0, 1, 2, 4));
    }

    @Test
    void viaEdge_no_3() {
        assertPath(5, 3, nodes(5, 1, 2, 3));
    }

    @Test
    void viaEdge_withOverlap_1() {
        assertPath(0, 3, null);
    }

    @Test
    void viaEdge_withOverlap_2() {
        assertPath(0, 6, nodes(0, 1, 2, 6));
    }

    @Test
    void viaEdge_withOverlap_3() {
        assertPath(5, 3, nodes(5, 1, 2, 3));
    }

    @Test
    void viaEdge_withOverlap_4() {
        assertPath(5, 6, nodes(5, 1, 2, 6));
    }

    @Test
    void viaEdge_withOverlap_5() {
        assertPath(1, 4, null);
    }

    @Test
    void viaEdge_withOverlap_6() {
        assertPath(1, 7, nodes(1, 2, 3, 7));
    }

    @Test
    void viaEdge_withOverlap_7() {
        assertPath(6, 4, nodes(6, 2, 3, 4));
    }

    @Test
    void viaEdge_withOverlap_8() {
        assertPath(6, 7, nodes(6, 2, 3, 7));
    }

    @Test
    void viaEdge_no_withOverlap_more_complex_1() {
        assertPath(0, 9, nodes(0, 3, 7, 8, 9));
    }

    @Test
    void viaEdge_no_withOverlap_more_complex_2() {
        assertPath(5, 9, nodes(5, 4, 3, 7, 10, 11, 8, 9));
    }

    @Test
    void viaEdge_no_withOverlap_more_complex_3() {
        assertPath(5, 2, nodes(5, 4, 3, 2));
    }

    @Test
    void viaEdge_no_withOverlap_more_complex_4() {
        assertPath(0, 10, nodes(0, 3, 7, 10));
    }

    @Test
    void viaEdge_no_withOverlap_more_complex_5() {
        assertPath(6, 9, nodes(6, 7, 8, 9));
    }

    @Test
    void common_via_edge_opposite_direction_1() {
        assertPath(0, 2, nodes(0, 1, 2));
    }

    @Test
    void common_via_edge_opposite_direction_2() {
        assertPath(0, 5, nodes(0, 1, 4, 5));
    }

    @Test
    void common_via_edge_opposite_direction_3() {
        assertPath(0, 3, nodes(0, 1, 4, 3));
    }

    @Test
    void common_via_edge_opposite_direction_4() {
        assertPath(2, 0, nodes(2, 1, 0));
    }

    @Test
    void common_via_edge_opposite_direction_5() {
        assertPath(2, 3, nodes(2, 1, 4, 3));
    }

    @Test
    void common_via_edge_opposite_direction_6() {
        assertPath(2, 5, null);
    }

    @Test
    void common_via_edge_opposite_direction_7() {
        assertPath(3, 0, null);
    }

    @Test
    void common_via_edge_opposite_direction_8() {
        assertPath(3, 2, nodes(3, 4, 1, 2));
    }

    @Test
    void common_via_edge_opposite_direction_9() {
        assertPath(3, 5, nodes(3, 4, 5));
    }

    @Test
    void common_via_edge_opposite_direction_10() {
        assertPath(5, 0, nodes(5, 4, 1, 0));
    }

    @Test
    void common_via_edge_opposite_direction_11() {
        assertPath(5, 2, nodes(5, 4, 1, 2));
    }

    @Test
    void common_via_edge_opposite_direction_12() {
        assertPath(5, 3, nodes(5, 4, 3));
    }

    @Test
    void viaEdge_common_via_edge_opposite_direction_edge0_1() {
        assertPath(0, 3, null);
    }

    @Test
    void viaEdge_common_via_edge_opposite_direction_edge0_2() {
        assertPath(1, 3, nodes(1, 2, 3));
    }

    @Test
    void viaEdge_common_via_edge_opposite_direction_edge0_3() {
        assertPath(3, 0, null);
    }

    @Test
    void viaEdge_common_via_edge_opposite_direction_edge0_4() {
        assertPath(2, 0, nodes(2, 1, 0));
    }

    @Test
    void common_via_edge_same_direction_1() {
        assertPath(0, 3, nodes(0, 1, 4, 3));
    }

    @Test
    void common_via_edge_same_direction_2() {
        assertPath(2, 5, nodes(2, 1, 4, 5));
    }

    @Test
    void common_via_edge_same_direction_3() {
        assertPath(0, 3, null);
    }

    @Test
    void common_via_edge_same_direction_4() {
        assertPath(3, 0, nodes(3, 4, 1, 0));
    }

    @Test
    void common_via_edge_same_direction_5() {
        assertPath(2, 5, null);
    }

    @Test
    void common_via_edge_same_direction_6() {
        assertPath(5, 2, nodes(5, 4, 1, 2));
    }

    @Test
    void common_via_edge_same_direction_7() {
        assertPath(0, 2, nodes(0, 1, 2));
    }

    @Test
    void common_via_edge_same_direction_8() {
        assertPath(1, 3, nodes(1, 4, 3));
    }

    @Test
    void common_via_edge_same_direction_9() {
        assertPath(0, 5, nodes(0, 1, 4, 5));
    }

    @Test
    void common_via_edge_same_direction_10() {
        assertPath(2, 3, nodes(2, 1, 4, 3));
    }

    @Test
    void viaEdgeAndNode_1() {
        assertPath(0, 3, nodes(0, 1, 3));
    }

    @Test
    void viaEdgeAndNode_2() {
        assertPath(4, 2, nodes(4, 0, 1, 2));
    }

    @Test
    void viaEdgeAndNode_3() {
        assertPath(4, 2, null);
    }

    @Test
    void viaEdgeAndNode_4() {
        assertPath(0, 3, null);
    }

    @Test
    void multiViaEdge_no_1() {
        assertPath(0, 5, nodes(0, 1, 2, 4, 3, 6, 5));
    }

    @Test
    void multiViaEdge_no_2() {
        assertPath(1, 5, nodes(1, 3, 6, 5));
    }

    @Test
    void multiViaEdge_no_3() {
        assertPath(0, 7, nodes(0, 1, 3, 6, 7));
    }

    @Test
    void multiViaEdge_overlapping_1() {
        assertPath(5, 6, nodes(5, 1, 2, 6));
    }

    @Test
    void multiViaEdge_overlapping_2() {
        assertPath(0, 4, nodes(0, 1, 2, 3, 4));
    }

    @Test
    void multiViaEdge_overlapping_3() {
        assertPath(5, 6, null);
    }

    @Test
    void multiViaEdge_overlapping_4() {
        assertPath(0, 4, null);
    }

    @Test
    void multiViaEdge_overlapping_5() {
        assertPath(0, 6, nodes(0, 1, 2, 6));
    }

    @Test
    void multiViaEdge_overlapping_6() {
        assertPath(5, 4, nodes(5, 1, 2, 3, 4));
    }

    @Test
    void singleViaEdgeRestriction_1() {
        assertPath(0, 3, nodes(0, 1, 2, 3));
    }

    @Test
    void singleViaEdgeRestriction_2() {
        assertPath(0, 4, nodes(0, 1, 2, 4));
    }

    @Test
    void singleViaEdgeRestriction_3() {
        assertPath(5, 3, nodes(5, 1, 2, 3));
    }

    @Test
    void singleViaEdgeRestriction_4() {
        assertPath(5, 4, nodes(5, 1, 2, 4));
    }

    @Test
    void singleViaEdgeRestriction_5() {
        assertPath(0, 3, nodes(0, 1, 2, 3));
    }

    @Test
    void singleViaEdgeRestriction_6() {
        assertPath(0, 4, null);
    }

    @Test
    void singleViaEdgeRestriction_7() {
        assertPath(5, 3, nodes(5, 1, 2, 3));
    }

    @Test
    void singleViaEdgeRestriction_8() {
        assertPath(5, 4, nodes(5, 1, 2, 4));
    }

    @Test
    void singleViaEdgeRestriction_9() {
        assertEquals(6, graph.getTurnCostStorage().getTurnCostsCount());
    }

    @Test
    void multiViaEdgeRestriction_1() {
        assertPath(0, 3, nodes(0, 1, 6, 2, 3));
    }

    @Test
    void multiViaEdgeRestriction_2() {
        assertPath(0, 4, nodes(0, 1, 6, 2, 4));
    }

    @Test
    void multiViaEdgeRestriction_3() {
        assertPath(5, 3, nodes(5, 1, 6, 2, 3));
    }

    @Test
    void multiViaEdgeRestriction_4() {
        assertPath(5, 4, nodes(5, 1, 6, 2, 4));
    }

    @Test
    void multiViaEdgeRestriction_5() {
        assertPath(0, 3, nodes(0, 1, 6, 2, 3));
    }

    @Test
    void multiViaEdgeRestriction_6() {
        assertPath(0, 4, null);
    }

    @Test
    void multiViaEdgeRestriction_7() {
        assertPath(5, 3, nodes(5, 1, 6, 2, 3));
    }

    @Test
    void multiViaEdgeRestriction_8() {
        assertPath(5, 4, nodes(5, 1, 6, 2, 4));
    }

    @Test
    void multiViaEdgeRestriction_9() {
        assertEquals(11, graph.getTurnCostStorage().getTurnCostsCount());
    }

    @Test
    void viaNode_1() {
        assertPath(0, 3, nodes(0, 1, 3));
    }

    @Test
    void viaNode_2() {
        assertPath(4, 2, nodes(4, 0, 1, 2));
    }

    @Test
    void viaNode_3() {
        assertPath(4, 2, null);
    }

    @Test
    void viaNode_4() {
        assertPath(0, 3, null);
    }

    @Test
    void viaNode_5() {
        assertEquals(8, graph.getTurnCostStorage().getTurnCostsCount());
    }

    @Test
    void circle_1() {
        assertPath(4, 3, nodes(4, 1, 0, 2, 3));
    }

    @Test
    void circle_2() {
        assertPath(4, 3, nodes(4, 1, 0, 2, 1, 0, 2, 3));
    }

    @Test
    void circle_3() {
        assertEquals(11, graph.getTurnCostStorage().getTurnCostsCount());
    }

    @Test
    void duplicateRestrictions_1() {
        assertPath(0, 2, nodes(0, 1, 2));
    }

    @Test
    void duplicateRestrictions_2() {
        assertPath(0, 2, null);
    }

    @Test
    void duplicateRestrictions_3() {
        assertEquals(1, graph.getTurnCostStorage().getTurnCostsCount());
    }

    @Test
    void duplicateViaEdgeRestrictions_1() {
        assertPath(0, 3, nodes(0, 1, 2, 3));
    }

    @Test
    void duplicateViaEdgeRestrictions_2() {
        assertPath(0, 3, null);
    }

    @Test
    void duplicateViaEdgeRestrictions_3() {
        assertEquals(6, graph.getTurnCostStorage().getTurnCostsCount());
    }

    @Test
    void duplicateEdgesInViaEdgeRestriction_1() {
        assertPath(3, 0, null);
    }

    @Test
    void duplicateEdgesInViaEdgeRestriction_2() {
        assertEquals(8, graph.getTurnCostStorage().getTurnCostsCount());
    }

    @Test
    void circleEdgesInViaEdgeRestriction_1() {
        assertPath(3, 2, nodes(3, 1, 2));
    }

    @Test
    void circleEdgesInViaEdgeRestriction_2() {
        assertPath(3, 2, null);
    }

    @Test
    void circleEdgesInViaEdgeRestriction_3() {
        assertPath(0, 2, nodes(0, 1, 2));
    }

    @Test
    void circleEdgesInViaEdgeRestriction_4() {
        assertPath(4, 2, nodes(4, 1, 0, 1, 2));
    }

    @Test
    void circleEdgesInViaEdgeRestriction_5() {
        assertEquals(11, graph.getTurnCostStorage().getTurnCostsCount());
    }

    @Test
    void similarRestrictions_1() {
        assertPath(4, 0, nodes(4, 1, 0));
    }

    @Test
    void similarRestrictions_2() {
        assertPath(4, 0, null);
    }

    @Test
    void similarRestrictions_3() {
        assertEquals(4, graph.getTurnCostStorage().getTurnCostsCount());
    }

    @Test
    void similarRestrictions_with_artificial_edges_1() {
        assertPath(0, 5, null);
    }

    @Test
    void similarRestrictions_with_artificial_edges_2() {
        assertEquals(25, graph.getTurnCostStorage().getTurnCostsCount());
    }

    @Test
    void restrictTurnsBetweenArtificialEdges_1() {
        assertPath(3, 4, nodes(3, 1, 2, 4));
    }

    @Test
    void restrictTurnsBetweenArtificialEdges_2() {
        assertPath(0, 4, nodes(0, 1, 2, 4));
    }

    @Test
    void restrictTurnsBetweenArtificialEdges_3() {
        assertPath(5, 4, nodes(5, 1, 2, 4));
    }

    @Test
    void restrictTurnsBetweenArtificialEdges_4() {
        assertPath(3, 4, null);
    }

    @Test
    void restrictTurnsBetweenArtificialEdges_5() {
        assertPath(0, 4, null);
    }

    @Test
    void restrictTurnsBetweenArtificialEdges_6() {
        assertPath(5, 4, nodes(5, 1, 2, 4));
    }

    @Test
    void artificialEdgeSnapping_1() {
        assertPath(1, 0, nodes(1, 2, 3, 0));
    }

    @Test
    void artificialEdgeSnapping_2() {
        assertPath(1, 4, nodes(1, 2, 3, 4));
    }

    @Test
    void artificialEdgeSnapping_3() {
        assertPath(5, 0, nodes(5, 2, 3, 0));
    }

    @Test
    void artificialEdgeSnapping_4() {
        assertPath(6, 3, nodes(6, 2, 3));
    }

    @Test
    void artificialEdgeSnapping_5() {
        assertPath(2, 7, nodes(2, 3, 7));
    }

    @Test
    void artificialEdgeSnapping_6() {
        assertPath(1, 0, null);
    }

    @Test
    void artificialEdgeSnapping_7() {
        assertPath(1, 4, nodes(1, 2, 3, 4));
    }

    @Test
    void artificialEdgeSnapping_8() {
        assertPath(5, 0, nodes(5, 2, 3, 0));
    }

    @Test
    void artificialEdgeSnapping_9() {
        assertPath(6, 3, null);
    }

    @Test
    void artificialEdgeSnapping_10() {
        assertPath(2, 7, null);
    }

    @Test
    void artificialEdgeSnapping_11_testMerged_11() {
        NodeAccess na = graph.getNodeAccess();
        LocationIndex locationIndex = new LocationIndexTree(graph, graph.getDirectory()).prepareIndex();
        Snap snap = locationIndex.findClosest(40.02, 5.025, EdgeFilter.ALL_EDGES);
        QueryGraph queryGraph = QueryGraph.create(graph, snap);
        final int x = 8;
        assertPath(queryGraph, 1, 0, nodes(1, 2, x, 3, 0));
        assertPath(queryGraph, 1, 4, nodes(1, 2, 3, 4));
        assertPath(queryGraph, 1, x, nodes(1, 2, x));
        assertPath(queryGraph, 5, x, nodes(5, 2, x));
        assertPath(queryGraph, x, 0, nodes(x, 3, 0));
        assertPath(queryGraph, x, 4, nodes(x, 3, 4));
        assertPath(queryGraph, 6, 3, null);
        assertPath(queryGraph, 2, 7, null);
        assertEquals(10, graph.getTurnCostStorage().getTurnCostsCount());
    }

    @Test
    void artificialEdgeSnapping_twoVirtualNodes_1() {
        assertPath(1, 4, nodes(1, 2, 3, 4));
    }

    @Test
    void artificialEdgeSnapping_twoVirtualNodes_2() {
        assertPath(2, 4, nodes(2, 3, 4));
    }

    @Test
    void artificialEdgeSnapping_twoVirtualNodes_3() {
        assertPath(2, 5, nodes(2, 3, 4, 5));
    }

    @Test
    void artificialEdgeSnapping_twoVirtualNodes_4() {
        assertPath(3, 5, nodes(3, 4, 5));
    }

    @Test
    void artificialEdgeSnapping_twoVirtualNodes_5() {
        assertPath(3, 6, nodes(3, 4, 5, 6));
    }

    @Test
    void artificialEdgeSnapping_twoVirtualNodes_6() {
        assertPath(4, 6, nodes(4, 5, 6));
    }

    @Test
    void artificialEdgeSnapping_twoVirtualNodes_7() {
        assertPath(1, 4, null);
    }

    @Test
    void artificialEdgeSnapping_twoVirtualNodes_8() {
        assertPath(2, 4, nodes(2, 3, 4));
    }

    @Test
    void artificialEdgeSnapping_twoVirtualNodes_9() {
        assertPath(2, 5, null);
    }

    @Test
    void artificialEdgeSnapping_twoVirtualNodes_10() {
        assertPath(3, 5, nodes(3, 4, 5));
    }

    @Test
    void artificialEdgeSnapping_twoVirtualNodes_11() {
        assertPath(3, 6, null);
    }

    @Test
    void artificialEdgeSnapping_twoVirtualNodes_12() {
        assertPath(4, 6, nodes(4, 5, 6));
    }

    @Test
    void artificialEdgeSnapping_twoVirtualNodes_13_testMerged_13() {
        NodeAccess na = graph.getNodeAccess();
        LocationIndex locationIndex = new LocationIndexTree(graph, graph.getDirectory()).prepareIndex();
        Snap snapX = locationIndex.findClosest(40.02, 5.025, EdgeFilter.ALL_EDGES);
        Snap snapY = locationIndex.findClosest(40.02, 5.035, EdgeFilter.ALL_EDGES);
        Snap snapZ = locationIndex.findClosest(40.02, 5.045, EdgeFilter.ALL_EDGES);
        QueryGraph queryGraph = QueryGraph.create(graph, List.of(snapX, snapY, snapZ));
        final int x = 8;
        final int y = 7;
        final int z = 9;
        assertEquals(x, snapX.getClosestNode());
        assertEquals(y, snapY.getClosestNode());
        assertEquals(z, snapZ.getClosestNode());
        assertPath(queryGraph, 1, 4, nodes(1, 2, x, 3, 4));
        assertPath(queryGraph, 2, 4, nodes(2, x, 3, 4));
        assertPath(queryGraph, 2, 5, nodes(2, x, 3, y, 4, 5));
        assertPath(queryGraph, 3, 5, nodes(3, y, 4, 5));
        assertPath(queryGraph, 3, 6, nodes(3, y, 4, z, 5, 6));
        assertPath(queryGraph, 4, 6, nodes(4, z, 5, 6));
        assertPath(queryGraph, x, y, nodes(x, 3, y));
        assertPath(queryGraph, y, x, nodes(y, 3, x));
        assertPath(queryGraph, y, z, nodes(y, 4, z));
        assertPath(queryGraph, z, y, nodes(z, 4, y));
        assertEquals(20, graph.getTurnCostStorage().getTurnCostsCount());
    }
}
