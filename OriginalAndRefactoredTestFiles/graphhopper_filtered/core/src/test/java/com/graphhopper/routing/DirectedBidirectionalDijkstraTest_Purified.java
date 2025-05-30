package com.graphhopper.routing;

import com.carrotsearch.hppc.IntArrayList;
import com.carrotsearch.hppc.IntHashSet;
import com.graphhopper.routing.ev.DecimalEncodedValue;
import com.graphhopper.routing.ev.DecimalEncodedValueImpl;
import com.graphhopper.routing.ev.TurnCost;
import com.graphhopper.routing.querygraph.QueryGraph;
import com.graphhopper.routing.util.EdgeFilter;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.routing.util.TraversalMode;
import com.graphhopper.routing.weighting.AvoidEdgesWeighting;
import com.graphhopper.routing.weighting.SpeedWeighting;
import com.graphhopper.routing.weighting.Weighting;
import com.graphhopper.storage.BaseGraph;
import com.graphhopper.storage.Graph;
import com.graphhopper.storage.NodeAccess;
import com.graphhopper.storage.TurnCostStorage;
import com.graphhopper.storage.index.LocationIndexTree;
import com.graphhopper.storage.index.Snap;
import com.graphhopper.util.EdgeIteratorState;
import com.graphhopper.util.GHUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import static com.graphhopper.util.EdgeIterator.ANY_EDGE;
import static com.graphhopper.util.EdgeIterator.NO_EDGE;
import static org.junit.jupiter.api.Assertions.*;

public class DirectedBidirectionalDijkstraTest_Purified {

    private TurnCostStorage turnCostStorage;

    private int maxTurnCosts;

    private BaseGraph graph;

    private DecimalEncodedValue speedEnc;

    private Weighting weighting;

    private DecimalEncodedValue turnCostEnc;

    @BeforeEach
    public void setup() {
        maxTurnCosts = 10;
        speedEnc = new DecimalEncodedValueImpl("speed", 5, 5, true);
        turnCostEnc = TurnCost.create("car", maxTurnCosts);
        EncodingManager encodingManager = EncodingManager.start().add(speedEnc).addTurnCostEncodedValue(turnCostEnc).build();
        graph = new BaseGraph.Builder(encodingManager).withTurnCosts(true).create();
        turnCostStorage = graph.getTurnCostStorage();
        weighting = createWeighting(Double.POSITIVE_INFINITY);
    }

    private Weighting createWeighting(double uTurnCosts) {
        return new SpeedWeighting(speedEnc, turnCostEnc, turnCostStorage, uTurnCosts);
    }

    @RepeatedTest(10)
    public void compare_standard_dijkstra() {
        compare_with_dijkstra(weighting);
    }

    @RepeatedTest(10)
    public void compare_standard_dijkstra_finite_uturn_costs() {
        compare_with_dijkstra(createWeighting(40));
    }

    private void compare_with_dijkstra(Weighting w) {
        final long seed = System.nanoTime();
        final int numQueries = 1000;
        Random rnd = new Random(seed);
        int numNodes = 100;
        GHUtility.buildRandomGraph(graph, rnd, numNodes, 2.2, true, speedEnc, null, 0.8, 0.8);
        GHUtility.addRandomTurnCosts(graph, seed, null, turnCostEnc, maxTurnCosts, turnCostStorage);
        long numStrictViolations = 0;
        for (int i = 0; i < numQueries; i++) {
            int source = rnd.nextInt(numNodes);
            int target = rnd.nextInt(numNodes);
            Path dijkstraPath = new Dijkstra(graph, w, TraversalMode.EDGE_BASED).calcPath(source, target);
            Path path = calcPath(source, target, ANY_EDGE, ANY_EDGE, w);
            assertEquals(dijkstraPath.isFound(), path.isFound(), "dijkstra found/did not find a path, from: " + source + ", to: " + target + ", seed: " + seed);
            assertEquals(dijkstraPath.getWeight(), path.getWeight(), 1.e-6, "weight does not match dijkstra, from: " + source + ", to: " + target + ", seed: " + seed);
            if (Math.abs(dijkstraPath.getDistance() - path.getDistance()) > 1.e-6 || Math.abs(dijkstraPath.getTime() - path.getTime()) > 10 || !dijkstraPath.calcNodes().equals(path.calcNodes())) {
                numStrictViolations++;
            }
        }
        if (numStrictViolations > Math.max(1, 0.05 * numQueries)) {
            fail("Too many strict violations, seed: " + seed + " - " + numStrictViolations + " / " + numQueries);
        }
    }

    private AvoidEdgesWeighting createAvoidEdgeWeighting(EdgeIteratorState edgeOut) {
        AvoidEdgesWeighting avoidEdgesWeighting = new AvoidEdgesWeighting(weighting);
        avoidEdgesWeighting.setEdgePenaltyFactor(Double.POSITIVE_INFINITY);
        avoidEdgesWeighting.setAvoidedEdges(IntHashSet.from(edgeOut.getEdge()));
        return avoidEdgesWeighting;
    }

    private Path calcPath(int source, int target, int sourceOutEdge, int targetInEdge) {
        return calcPath(source, target, sourceOutEdge, targetInEdge, weighting);
    }

    private Path calcPath(int source, int target, int sourceOutEdge, int targetInEdge, Weighting w) {
        EdgeToEdgeRoutingAlgorithm algo = createAlgo(graph, w);
        return algo.calcPath(source, target, sourceOutEdge, targetInEdge);
    }

    private EdgeToEdgeRoutingAlgorithm createAlgo(Graph graph, Weighting weighting) {
        return new DijkstraBidirectionRef(graph, weighting, TraversalMode.EDGE_BASED);
    }

    private IntArrayList nodes(int... nodes) {
        return IntArrayList.from(nodes);
    }

    private void assertPath(Path path, double weight, double distance, long time, IntArrayList nodes) {
        assertTrue(path.isFound(), "expected a path, but no path was found");
        assertEquals(nodes, path.calcNodes(), "unexpected nodes");
        assertEquals(weight, path.getWeight(), 1.e-6, "unexpected weight");
        assertEquals(distance, path.getDistance(), 1.e-6, "unexpected distance");
        assertEquals(time, path.getTime(), "unexpected time");
    }

    private void assertNotFound(Path path) {
        assertFalse(path.isFound(), "expected no path, but a path was found");
        assertEquals(Double.MAX_VALUE, path.getWeight(), 1.e-6);
        assertEquals(0, path.getDistance(), 1.e-6);
        assertEquals(0, path.getTime());
        assertEquals(nodes(), path.calcNodes());
    }

    private void setRestriction(int fromNode, int node, int toNode) {
        setTurnCost(fromNode, node, toNode, Double.POSITIVE_INFINITY);
    }

    private void setTurnCost(int fromNode, int node, int toNode, double turnCost) {
        turnCostStorage.set(turnCostEnc, GHUtility.getEdge(graph, fromNode, node).getEdge(), node, GHUtility.getEdge(graph, node, toNode).getEdge(), turnCost);
    }

    @Test
    public void singleEdge_1() {
        assertNotFound(calcPath(0, 1, 5, 0));
    }

    @Test
    public void singleEdge_2() {
        assertNotFound(calcPath(0, 1, 0, 5));
    }

    @Test
    public void singleEdge_3() {
        assertNotFound(calcPath(0, 1, NO_EDGE, 0));
    }

    @Test
    public void singleEdge_4() {
        assertNotFound(calcPath(0, 1, 0, NO_EDGE));
    }

    @Test
    public void singleEdge_5() {
        assertPath(calcPath(0, 1, ANY_EDGE, 0), 0.1, 1, 100, nodes(0, 1));
    }

    @Test
    public void singleEdge_6() {
        assertPath(calcPath(0, 1, 0, ANY_EDGE), 0.1, 1, 100, nodes(0, 1));
    }

    @Test
    public void singleEdge_7() {
        assertPath(calcPath(0, 1, 0, 0), 0.1, 1, 100, nodes(0, 1));
    }

    @Test
    public void simpleGraph_1() {
        assertNotFound(calcPath(0, 2, 5, 0));
    }

    @Test
    public void simpleGraph_2() {
        assertNotFound(calcPath(0, 2, 0, 5));
    }

    @Test
    public void simpleGraph_3() {
        assertNotFound(calcPath(0, 2, NO_EDGE, 0));
    }

    @Test
    public void simpleGraph_4() {
        assertNotFound(calcPath(0, 2, 0, NO_EDGE));
    }

    @Test
    public void simpleGraph_5() {
        assertPath(calcPath(0, 2, ANY_EDGE, 1), 0.2, 2, 200, nodes(0, 1, 2));
    }

    @Test
    public void simpleGraph_6() {
        assertPath(calcPath(0, 2, 0, ANY_EDGE), 0.2, 2, 200, nodes(0, 1, 2));
    }

    @Test
    public void simpleGraph_7() {
        assertPath(calcPath(0, 2, 0, 1), 0.2, 2, 200, nodes(0, 1, 2));
    }

    @Test
    public void sourceEqualsTarget_1() {
        assertPath(calcPath(0, 0, 0, 1), 0.3, 3, 300, nodes(0, 1, 2, 0));
    }

    @Test
    public void sourceEqualsTarget_2() {
        assertPath(calcPath(0, 0, 1, 0), 0.3, 3, 300, nodes(0, 2, 1, 0));
    }

    @Test
    public void sourceEqualsTarget_3() {
        assertPath(calcPath(0, 0, ANY_EDGE, ANY_EDGE), 0, 0, 0, nodes(0));
    }

    @Test
    public void sourceEqualsTarget_4() {
        assertNotFound(calcPath(0, 0, 1, 1));
    }

    @Test
    public void sourceEqualsTarget_5() {
        assertNotFound(calcPath(0, 0, 5, 1));
    }

    @Test
    public void restrictions_one_ways_1() {
        assertPath(calcPath(0, 2, 0, 2), 0.2, 2, 200, nodes(0, 3, 2));
    }

    @Test
    public void restrictions_one_ways_2() {
        assertNotFound(calcPath(0, 2, 1, 2));
    }

    @Test
    public void restrictions_one_ways_3() {
        assertNotFound(calcPath(0, 2, 0, 3));
    }

    @Test
    public void restrictions_one_ways_4() {
        assertNotFound(calcPath(0, 2, 1, 3));
    }

    @Test
    public void sourceAndTargetAreNeighbors_1() {
        assertPath(calcPath(1, 2, ANY_EDGE, ANY_EDGE), 10, 100, 10000, nodes(1, 2));
    }

    @Test
    public void sourceAndTargetAreNeighbors_2() {
        assertPath(calcPath(1, 2, 1, ANY_EDGE), 10, 100, 10000, nodes(1, 2));
    }

    @Test
    public void sourceAndTargetAreNeighbors_3() {
        assertPath(calcPath(1, 2, ANY_EDGE, 1), 10, 100, 10000, nodes(1, 2));
    }

    @Test
    public void sourceAndTargetAreNeighbors_4() {
        assertPath(calcPath(1, 2, 1, 1), 10, 100, 10000, nodes(1, 2));
    }

    @Test
    public void sourceAndTargetAreNeighbors_5() {
        assertNotFound(calcPath(1, 2, 1, 2));
    }

    @Test
    public void sourceAndTargetAreNeighbors_6() {
        assertNotFound(calcPath(1, 2, 0, 1));
    }

    @Test
    public void sourceAndTargetAreNeighbors_7() {
        assertNotFound(calcPath(1, 2, 0, 2));
    }

    @Test
    public void sourceAndTargetAreNeighbors_8() {
        assertPath(calcPath(1, 2, 1, 2, createWeighting(100)), 30 + 100, 300, 130000, nodes(1, 2, 3, 2));
    }

    @Test
    public void sourceAndTargetAreNeighbors_9() {
        assertPath(calcPath(1, 2, 0, 1, createWeighting(100)), 30 + 100, 300, 130000, nodes(1, 0, 1, 2));
    }

    @Test
    public void sourceAndTargetAreNeighbors_10() {
        assertPath(calcPath(1, 2, 0, 2, createWeighting(100)), 50 + 200, 500, 250000, nodes(1, 0, 1, 2, 3, 2));
    }

    @Test
    public void worksWithTurnCosts_1() {
        assertPath(calcPath(0, 2, 0, 6), 6.4, 4, 6400, nodes(0, 1, 4, 5, 2));
    }

    @Test
    public void worksWithTurnCosts_2() {
        assertNotFound(calcPath(0, 2, 3, ANY_EDGE));
    }

    @Test
    public void worksWithTurnCosts_3() {
        assertPath(calcPath(0, 2, ANY_EDGE, ANY_EDGE), 0.2, 2, 200, nodes(0, 1, 2));
    }

    @Test
    public void blockArea_1() {
        assertPath(calcPath(0, 3, ANY_EDGE, ANY_EDGE), 3, 30, 3000, nodes(0, 1, 2, 3));
    }

    @Test
    public void blockArea_2() {
        assertPath(calcPath(0, 3, 3, ANY_EDGE), 40, 400, 40000, nodes(0, 4, 5, 6, 3));
    }

    @Test
    public void blockArea_3() {
        assertPath(calcPath(0, 3, ANY_EDGE, 6), 40, 400, 40000, nodes(0, 4, 5, 6, 3));
    }

    @Test
    public void blockArea_4_testMerged_4() {
        EdgeIteratorState edge1 = graph.edge(0, 1).setDistance(10).set(speedEnc, 10, 10);
        graph.edge(1, 2).setDistance(10).set(speedEnc, 10, 10);
        EdgeIteratorState edge2 = graph.edge(2, 3).setDistance(10).set(speedEnc, 10, 10);
        assertPath(calcPath(0, 3, ANY_EDGE, ANY_EDGE, createAvoidEdgeWeighting(edge1)), 40, 400, 40000, nodes(0, 4, 5, 6, 3));
        assertPath(calcPath(0, 3, ANY_EDGE, ANY_EDGE, createAvoidEdgeWeighting(edge2)), 40, 400, 40000, nodes(0, 4, 5, 6, 3));
        assertNotFound(calcPath(0, 3, edge1.getEdge(), edge2.getEdge(), createAvoidEdgeWeighting(edge1)));
        assertNotFound(calcPath(0, 3, edge1.getEdge(), edge2.getEdge(), createAvoidEdgeWeighting(edge2)));
        assertNotFound(calcPath(0, 1, edge1.getEdge(), ANY_EDGE, createAvoidEdgeWeighting(edge1)));
        assertNotFound(calcPath(0, 1, ANY_EDGE, edge2.getEdge(), createAvoidEdgeWeighting(edge2)));
    }
}
