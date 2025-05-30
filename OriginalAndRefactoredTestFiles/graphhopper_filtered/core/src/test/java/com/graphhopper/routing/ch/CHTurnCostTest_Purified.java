package com.graphhopper.routing.ch;

import com.carrotsearch.hppc.IntArrayList;
import com.graphhopper.routing.Dijkstra;
import com.graphhopper.routing.DijkstraBidirectionEdgeCHNoSOD;
import com.graphhopper.routing.Path;
import com.graphhopper.routing.RoutingAlgorithm;
import com.graphhopper.routing.ev.DecimalEncodedValue;
import com.graphhopper.routing.ev.DecimalEncodedValueImpl;
import com.graphhopper.routing.ev.TurnCost;
import com.graphhopper.routing.querygraph.QueryGraph;
import com.graphhopper.routing.querygraph.QueryRoutingCHGraph;
import com.graphhopper.routing.util.EdgeFilter;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.routing.util.TraversalMode;
import com.graphhopper.routing.weighting.SpeedWeighting;
import com.graphhopper.routing.weighting.Weighting;
import com.graphhopper.storage.*;
import com.graphhopper.storage.index.LocationIndexTree;
import com.graphhopper.storage.index.Snap;
import com.graphhopper.util.*;
import com.graphhopper.util.shapes.GHPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;
import static com.graphhopper.routing.ch.CHParameters.*;
import static com.graphhopper.util.GHUtility.updateDistancesFor;
import static com.graphhopper.util.Parameters.Algorithms.ASTAR_BI;
import static com.graphhopper.util.Parameters.Algorithms.DIJKSTRA_BI;
import static com.graphhopper.util.Parameters.Routing.ALGORITHM;
import static org.junit.jupiter.api.Assertions.*;

public class CHTurnCostTest_Purified {

    private static final Logger LOGGER = LoggerFactory.getLogger(CHTurnCostTest.class);

    private int maxCost;

    private DecimalEncodedValue speedEnc;

    private DecimalEncodedValue turnCostEnc;

    private EncodingManager encodingManager;

    private BaseGraph graph;

    private TurnCostStorage turnCostStorage;

    private List<CHConfig> chConfigs;

    private CHConfig chConfig;

    private RoutingCHGraph chGraph;

    private boolean checkStrict;

    @BeforeEach
    public void init() {
        maxCost = 10;
        speedEnc = new DecimalEncodedValueImpl("speed", 5, 5, true);
        turnCostEnc = TurnCost.create("car", maxCost);
        encodingManager = EncodingManager.start().add(speedEnc).addTurnCostEncodedValue(turnCostEnc).build();
        graph = new BaseGraph.Builder(encodingManager).withTurnCosts(true).build();
        turnCostStorage = graph.getTurnCostStorage();
        chConfigs = createCHConfigs();
        chConfig = chConfigs.get(0);
        checkStrict = true;
    }

    private List<CHConfig> createCHConfigs() {
        Set<CHConfig> configs = new LinkedHashSet<>(5);
        configs.add(CHConfig.edgeBased("p0", new SpeedWeighting(speedEnc, turnCostEnc, turnCostStorage, Double.POSITIVE_INFINITY)));
        configs.add(CHConfig.edgeBased("p1", new SpeedWeighting(speedEnc, turnCostEnc, turnCostStorage, 0)));
        configs.add(CHConfig.edgeBased("p2", new SpeedWeighting(speedEnc, turnCostEnc, turnCostStorage, 50)));
        long seed = System.nanoTime();
        Random rnd = new Random(seed);
        while (configs.size() < 6) {
            int uTurnCosts = 10 + rnd.nextInt(90);
            configs.add(CHConfig.edgeBased("p" + configs.size(), new SpeedWeighting(speedEnc, turnCostEnc, turnCostStorage, uTurnCosts)));
        }
        return new ArrayList<>(configs);
    }

    private void compareWithDijkstraOnRandomGraph(long seed) {
        final Random rnd = new Random(seed);
        GHUtility.buildRandomGraph(graph, rnd, 20, 3.0, true, speedEnc, null, 0.9, 0.8);
        GHUtility.addRandomTurnCosts(graph, seed, null, turnCostEnc, maxCost, turnCostStorage);
        graph.freeze();
        checkStrict = false;
        IntArrayList contractionOrder = getRandomIntegerSequence(graph.getNodes(), rnd);
        compareCHWithDijkstra(100, contractionOrder.toArray());
    }

    private void compareWithDijkstraOnRandomGraph_heuristic(long seed) {
        GHUtility.buildRandomGraph(graph, new Random(seed), 20, 3.0, true, speedEnc, null, 0.9, 0.8);
        GHUtility.addRandomTurnCosts(graph, seed, null, turnCostEnc, maxCost, turnCostStorage);
        graph.freeze();
        checkStrict = false;
        automaticCompareCHWithDijkstra(100);
    }

    private int nextCost(Random rnd) {
        return rnd.nextInt(3 * maxCost);
    }

    private double nextDist(int maxDist, Random rnd) {
        return rnd.nextDouble() * maxDist;
    }

    private void checkPathUsingRandomContractionOrder(IntArrayList expectedPath, int expectedWeight, int expectedTurnCosts, int from, int to) {
        IntArrayList contractionOrder = getRandomIntegerSequence(graph.getNodes(), new Random());
        checkPath(expectedPath, expectedWeight, expectedTurnCosts, from, to, contractionOrder.toArray());
    }

    private void checkPath(IntArrayList expectedPath, int expectedEdgeWeight, int expectedTurnCosts, int from, int to, int[] contractionOrder) {
        checkPathUsingDijkstra(expectedPath, expectedEdgeWeight, expectedTurnCosts, from, to);
        checkPathUsingCH(expectedPath, expectedEdgeWeight, expectedTurnCosts, from, to, contractionOrder);
    }

    private void checkPathUsingDijkstra(IntArrayList expectedPath, int expectedEdgeWeight, int expectedTurnCosts, int from, int to) {
        Path dijkstraPath = findPathUsingDijkstra(from, to);
        int expectedWeight = expectedEdgeWeight + expectedTurnCosts;
        int expectedDistance = expectedEdgeWeight * 10;
        int expectedTime = (expectedEdgeWeight + expectedTurnCosts) * 1000;
        assertEquals(expectedPath, dijkstraPath.calcNodes(), "Normal Dijkstra did not find expected path.");
        assertEquals(expectedWeight, dijkstraPath.getWeight(), 1.e-6, "Normal Dijkstra did not calculate expected weight.");
        assertEquals(expectedDistance, dijkstraPath.getDistance(), 1.e-6, "Normal Dijkstra did not calculate expected distance.");
        assertEquals(expectedTime, dijkstraPath.getTime(), 2, "Normal Dijkstra did not calculate expected time.");
    }

    private void checkPathUsingCH(IntArrayList expectedPath, int expectedEdgeWeight, int expectedTurnCosts, int from, int to, int[] contractionOrder) {
        Path chPath = findPathUsingCH(from, to, contractionOrder);
        int expectedWeight = expectedEdgeWeight + expectedTurnCosts;
        int expectedDistance = expectedEdgeWeight * 10;
        int expectedTime = (expectedEdgeWeight + expectedTurnCosts) * 1000;
        assertEquals(expectedPath, chPath.calcNodes(), "Contraction Hierarchies did not find expected path. contraction order=" + Arrays.toString(contractionOrder));
        assertEquals(expectedWeight, chPath.getWeight(), 1.e-6, "Contraction Hierarchies did not calculate expected weight.");
        assertEquals(expectedDistance, chPath.getDistance(), 1.e-6, "Contraction Hierarchies did not calculate expected distance.");
        assertEquals(expectedTime, chPath.getTime(), 2, "Contraction Hierarchies did not calculate expected time.");
    }

    private Path findPathUsingDijkstra(int from, int to) {
        Weighting w = graph.wrapWeighting(chConfig.getWeighting());
        Dijkstra dijkstra = new Dijkstra(graph, w, TraversalMode.EDGE_BASED);
        return dijkstra.calcPath(from, to);
    }

    private Path findPathUsingCH(int from, int to, int[] contractionOrder) {
        prepareCH(contractionOrder);
        RoutingAlgorithm chAlgo = createAlgo();
        return chAlgo.calcPath(from, to);
    }

    private void prepareCH(int... contractionOrder) {
        LOGGER.debug("Calculating CH with contraction order {}", contractionOrder);
        if (!graph.isFrozen())
            graph.freeze();
        NodeOrderingProvider nodeOrderingProvider = NodeOrderingProvider.fromArray(contractionOrder);
        PrepareContractionHierarchies ch = PrepareContractionHierarchies.fromGraph(graph, chConfig).useFixedNodeOrdering(nodeOrderingProvider);
        PrepareContractionHierarchies.Result res = ch.doWork();
        chGraph = RoutingCHGraphImpl.fromGraph(graph, res.getCHStorage(), res.getCHConfig());
    }

    private void automaticPrepareCH() {
        PMap pMap = new PMap();
        pMap.putObject(PERIODIC_UPDATES, 20);
        pMap.putObject(LAST_LAZY_NODES_UPDATES, 100);
        pMap.putObject(NEIGHBOR_UPDATES, 4);
        pMap.putObject(LOG_MESSAGES, 10);
        PrepareContractionHierarchies ch = PrepareContractionHierarchies.fromGraph(graph, chConfig);
        ch.setParams(pMap);
        PrepareContractionHierarchies.Result res = ch.doWork();
        chGraph = RoutingCHGraphImpl.fromGraph(graph, res.getCHStorage(), res.getCHConfig());
    }

    private void automaticCompareCHWithDijkstra(int numQueries) {
        long seed = System.nanoTime();
        LOGGER.info("Seed used to create random routing queries: {}", seed);
        final Random rnd = new Random(seed);
        automaticPrepareCH();
        for (int i = 0; i < numQueries; ++i) {
            compareCHQueryWithDijkstra(rnd.nextInt(graph.getNodes()), rnd.nextInt(graph.getNodes()));
        }
    }

    private void compareCHWithDijkstra(int numQueries, int[] contractionOrder) {
        long seed = System.nanoTime();
        LOGGER.info("Seed used to create random routing queries: {}", seed);
        final Random rnd = new Random(seed);
        prepareCH(contractionOrder);
        for (int i = 0; i < numQueries; ++i) {
            compareCHQueryWithDijkstra(rnd.nextInt(graph.getNodes()), rnd.nextInt(graph.getNodes()));
        }
    }

    private void compareCHQueryWithDijkstra(int from, int to) {
        Path dijkstraPath = findPathUsingDijkstra(from, to);
        RoutingAlgorithm chAlgo = createAlgo();
        Path chPath = chAlgo.calcPath(from, to);
        boolean algosDisagree = Math.abs(dijkstraPath.getWeight() - chPath.getWeight()) > 1.e-2;
        if (checkStrict) {
            algosDisagree = algosDisagree || Math.abs(dijkstraPath.getDistance() - chPath.getDistance()) > 1.e-2 || Math.abs(dijkstraPath.getTime() - chPath.getTime()) > 1;
        }
        if (algosDisagree) {
            System.out.println("Graph that produced error:");
            GHUtility.printGraphForUnitTest(graph, speedEnc);
            fail("Dijkstra and CH did not find equal shortest paths for route from " + from + " to " + to + "\n" + " dijkstra: weight: " + dijkstraPath.getWeight() + ", distance: " + dijkstraPath.getDistance() + ", time: " + dijkstraPath.getTime() + ", nodes: " + dijkstraPath.calcNodes() + "\n" + "       ch: weight: " + chPath.getWeight() + ", distance: " + chPath.getDistance() + ", time: " + chPath.getTime() + ", nodes: " + chPath.calcNodes());
        }
    }

    private RoutingAlgorithm createAlgo() {
        return new CHRoutingAlgorithmFactory(chGraph).createAlgo(new PMap().putObject(ALGORITHM, DIJKSTRA_BI));
    }

    private IntArrayList getRandomIntegerSequence(int nodes, Random rnd) {
        return ArrayUtil.shuffle(ArrayUtil.iota(nodes), rnd);
    }

    private void setRandomCostOrRestriction(int from, int via, int to, Random rnd) {
        final double chance = 0.7;
        if (rnd.nextDouble() < chance) {
            setRestriction(from, via, to);
            LOGGER.trace("setRestriction({}, {}, {});", from, via, to);
        } else {
            setRandomCost(from, via, to, rnd);
        }
    }

    private void setRandomCost(int from, int via, int to, Random rnd) {
        int cost = (int) (rnd.nextDouble() * maxCost / 2);
        setTurnCost(from, via, to, cost);
        LOGGER.trace("setTurnCost({}, {}, {}, {});", from, via, to, cost);
    }

    private void setRestriction(int from, int via, int to) {
        setRestriction(getEdge(from, via), getEdge(via, to), via);
    }

    private void setRestriction(EdgeIteratorState inEdge, EdgeIteratorState outEdge, int viaNode) {
        graph.getTurnCostStorage().set(encodingManager.getTurnDecimalEncodedValue(TurnCost.key("car")), inEdge.getEdge(), viaNode, outEdge.getEdge(), Double.POSITIVE_INFINITY);
    }

    private void setTurnCost(int from, int via, int to, double cost) {
        setTurnCost(getEdge(from, via), getEdge(via, to), via, cost);
    }

    private void setTurnCost(EdgeIteratorState inEdge, EdgeIteratorState outEdge, int viaNode, double costs) {
        graph.getTurnCostStorage().set(encodingManager.getTurnDecimalEncodedValue(TurnCost.key("car")), inEdge.getEdge(), viaNode, outEdge.getEdge(), costs);
    }

    private void setCostOrRestriction(EdgeIteratorState inEdge, EdgeIteratorState outEdge, int viaNode, int cost) {
        if (cost >= maxCost) {
            setRestriction(inEdge, outEdge, viaNode);
            LOGGER.trace("setRestriction(edge{}, edge{}, {});", inEdge.getEdge(), outEdge.getEdge(), viaNode);
        } else {
            setTurnCost(inEdge, outEdge, viaNode, cost);
            LOGGER.trace("setTurnCost(edge{}, edge{}, {}, {});", inEdge.getEdge(), outEdge.getEdge(), viaNode, cost);
        }
    }

    private EdgeIteratorState getEdge(int from, int to) {
        return GHUtility.getEdge(graph, from, to);
    }

    @Test
    public void testFindPath_bidir_chain_1_testMerged_1() {
        Path pathFwd = createAlgo().calcPath(0, 6);
        assertEquals(IntArrayList.from(0, 1, 2, 3, 4, 5, 6), pathFwd.calcNodes());
        assertEquals(6 + 15, pathFwd.getWeight(), 1.e-6);
    }

    @Test
    public void testFindPath_bidir_chain_3_testMerged_2() {
        Path pathBwd = createAlgo().calcPath(6, 0);
        assertEquals(IntArrayList.from(6, 5, 4, 3, 2, 1, 0), pathBwd.calcNodes());
        assertEquals(6 + 10, pathBwd.getWeight(), 1.e-6);
    }

    @Test
    void testEdgeKeyBug_1() {
        assertEquals(2, chGraph.getShortcuts());
    }

    @Test
    void testEdgeKeyBug_2_testMerged_2() {
        RoutingCHEdgeIteratorState chEdge = chGraph.getEdgeIteratorState(6, 4);
        assertEquals(3, chEdge.getBaseNode());
        assertEquals(4, chEdge.getAdjNode());
        assertEquals(2, chEdge.getSkippedEdge1());
        assertEquals(0, chEdge.getSkippedEdge2());
        assertEquals(5, chEdge.getOrigEdgeKeyFirst());
        assertEquals(0, chEdge.getOrigEdgeKeyLast());
    }
}
