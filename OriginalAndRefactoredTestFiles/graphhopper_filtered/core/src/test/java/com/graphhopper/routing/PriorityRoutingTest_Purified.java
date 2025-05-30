package com.graphhopper.routing;

import com.carrotsearch.hppc.IntArrayList;
import com.graphhopper.json.Statement;
import com.graphhopper.routing.ev.*;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.routing.util.PriorityCode;
import com.graphhopper.routing.util.TraversalMode;
import com.graphhopper.routing.weighting.TurnCostProvider;
import com.graphhopper.routing.weighting.custom.CustomModelParser;
import com.graphhopper.routing.weighting.custom.CustomWeighting;
import com.graphhopper.storage.BaseGraph;
import com.graphhopper.storage.NodeAccess;
import com.graphhopper.util.CustomModel;
import com.graphhopper.util.DistanceCalcEarth;
import com.graphhopper.util.EdgeIteratorState;
import org.junit.jupiter.api.Test;
import static com.graphhopper.json.Statement.If;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PriorityRoutingTest_Purified {

    private EdgeIteratorState addEdge(EncodingManager em, BaseGraph graph, int p, int q, double prio, DecimalEncodedValue speedEnc, DecimalEncodedValue priorityEnc, double speed) {
        EnumEncodedValue<RoadClass> roadClassEnc = em.getEnumEncodedValue(RoadClass.KEY, RoadClass.class);
        return graph.edge(p, q).set(speedEnc, speed).set(priorityEnc, prio).set(roadClassEnc, RoadClass.MOTORWAY).setDistance(calcDist(graph, p, q));
    }

    private double calcDist(BaseGraph graph, int p, int q) {
        NodeAccess na = graph.getNodeAccess();
        return DistanceCalcEarth.DIST_EARTH.calcDist(na.getLat(p), na.getLon(p), na.getLat(q), na.getLon(q));
    }

    @Test
    void testMaxPriority_1_testMerged_1() {
        DecimalEncodedValue speedEnc = new DecimalEncodedValueImpl("speed", 4, 2, false);
        DecimalEncodedValue priorityEnc = new DecimalEncodedValueImpl("priority", 4, PriorityCode.getFactor(1), false);
        EncodingManager em = EncodingManager.start().add(speedEnc).add(priorityEnc).add(RoadClass.create()).build();
        BaseGraph graph = new BaseGraph.Builder(em).create();
        NodeAccess na = graph.getNodeAccess();
        double speed = speedEnc.getNextStorableValue(30);
        double dist1 = 0;
        dist1 += addEdge(em, graph, 0, 1, 1.0, speedEnc, priorityEnc, speed).getDistance();
        dist1 += addEdge(em, graph, 1, 2, 1.0, speedEnc, priorityEnc, speed).getDistance();
        dist1 += addEdge(em, graph, 2, 3, 1.0, speedEnc, priorityEnc, speed).getDistance();
        final double maxPrio = PriorityCode.getFactor(PriorityCode.BEST.getValue());
        double dist2 = 0;
        dist2 += addEdge(em, graph, 0, 4, maxPrio, speedEnc, priorityEnc, speed).getDistance();
        dist2 += addEdge(em, graph, 4, 5, maxPrio, speedEnc, priorityEnc, speed).getDistance();
        dist2 += addEdge(em, graph, 5, 3, maxPrio, speedEnc, priorityEnc, speed).getDistance();
        assertEquals(40101, dist1, 1);
        assertEquals(43005, dist2, 1);
    }

    @Test
    void testMaxPriority_3_testMerged_2() {
        CustomModel customModel = new CustomModel();
        customModel.addToPriority(If("true", Statement.Op.MULTIPLY, priorityEnc.getName()));
        customModel.addToSpeed(If("true", Statement.Op.LIMIT, speedEnc.getName()));
        CustomWeighting weighting = CustomModelParser.createWeighting(em, TurnCostProvider.NO_TURN_COST_PROVIDER, customModel);
        Path pathDijkstra = new Dijkstra(graph, weighting, TraversalMode.NODE_BASED).calcPath(0, 3);
        Path pathAStar = new AStar(graph, weighting, TraversalMode.NODE_BASED).calcPath(0, 3);
        assertEquals(pathDijkstra.calcNodes(), pathAStar.calcNodes());
        assertEquals(IntArrayList.from(0, 4, 5, 3), pathAStar.calcNodes());
        customModel.addToPriority(If("road_class == MOTORWAY", Statement.Op.MULTIPLY, "3"));
    }
}
