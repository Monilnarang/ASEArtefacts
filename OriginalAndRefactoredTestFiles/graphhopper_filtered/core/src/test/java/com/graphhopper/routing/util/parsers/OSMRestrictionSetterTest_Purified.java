package com.graphhopper.routing.util.parsers;

import com.carrotsearch.hppc.BitSet;
import com.carrotsearch.hppc.IntArrayList;
import com.graphhopper.reader.osm.Pair;
import com.graphhopper.reader.osm.RestrictionTopology;
import com.graphhopper.reader.osm.RestrictionType;
import com.graphhopper.routing.Dijkstra;
import com.graphhopper.routing.ev.BooleanEncodedValue;
import com.graphhopper.routing.ev.DecimalEncodedValue;
import com.graphhopper.routing.ev.DecimalEncodedValueImpl;
import com.graphhopper.routing.ev.TurnRestriction;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.routing.util.TraversalMode;
import com.graphhopper.routing.weighting.SpeedWeighting;
import com.graphhopper.routing.weighting.TurnCostProvider;
import com.graphhopper.storage.BaseGraph;
import com.graphhopper.storage.Graph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static com.graphhopper.reader.osm.OSMRestrictionConverter.buildRestrictionsForOSMRestriction;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OSMRestrictionSetterTest_Purified {

    private static final IntArrayList NO_PATH = IntArrayList.from();

    private DecimalEncodedValue speedEnc;

    private BooleanEncodedValue turnRestrictionEnc;

    private BaseGraph graph;

    private RestrictionSetter r;

    @BeforeEach
    void setup() {
        speedEnc = new DecimalEncodedValueImpl("speed", 5, 5, true);
        turnRestrictionEnc = TurnRestriction.create("car1");
        EncodingManager encodingManager = EncodingManager.start().add(speedEnc).add(turnRestrictionEnc).build();
        graph = new BaseGraph.Builder(encodingManager).withTurnCosts(true).create();
        r = new RestrictionSetter(graph, List.of(turnRestrictionEnc));
    }

    private void setRestrictions(List<Pair<RestrictionTopology, RestrictionType>> osmRestrictions) {
        setRestrictions(osmRestrictions, osmRestrictions.stream().map(r -> encBits(1)).toList());
    }

    private void setRestrictions(List<Pair<RestrictionTopology, RestrictionType>> osmRestrictions, List<BitSet> osmEncBits) {
        List<RestrictionSetter.Restriction> restrictions = new ArrayList<>();
        List<BitSet> encBits = new ArrayList<>();
        for (int i = 0; i < osmRestrictions.size(); i++) {
            Pair<RestrictionTopology, RestrictionType> p = osmRestrictions.get(i);
            List<RestrictionSetter.Restriction> tmpRestrictions = buildRestrictionsForOSMRestriction(graph, p.first, p.second);
            restrictions.addAll(tmpRestrictions);
            final BitSet e = osmEncBits.get(i);
            tmpRestrictions.forEach(__ -> encBits.add(RestrictionSetter.copyEncBits(e)));
        }
        r.setRestrictions(restrictions, encBits);
    }

    private IntArrayList calcPath(int from, int to) {
        return calcPath(from, to, turnRestrictionEnc);
    }

    private IntArrayList calcPath(int from, int to, BooleanEncodedValue turnRestrictionEnc) {
        return calcPath(this.graph, from, to, turnRestrictionEnc);
    }

    private IntArrayList calcPath(Graph graph, int from, int to) {
        return calcPath(graph, from, to, turnRestrictionEnc);
    }

    private IntArrayList calcPath(Graph graph, int from, int to, BooleanEncodedValue turnRestrictionEnc) {
        return new IntArrayList(new Dijkstra(graph, graph.wrapWeighting(new SpeedWeighting(speedEnc, new TurnCostProvider() {

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
        })), TraversalMode.EDGE_BASED).calcPath(from, to).calcNodes());
    }

    private IntArrayList edges(int... edges) {
        return IntArrayList.from(edges);
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
    void viaWay_no_1() {
        assertEquals(nodes(0, 1, 5, 8, 9, 6, 2, 3), calcPath(0, 3));
    }

    @Test
    void viaWay_no_2() {
        assertEquals(nodes(0, 1, 2, 4), calcPath(0, 4));
    }

    @Test
    void viaWay_no_3() {
        assertEquals(nodes(5, 1, 2, 3), calcPath(5, 3));
    }

    @Test
    void viaWay_no_withOverlap_1() {
        assertEquals(NO_PATH, calcPath(0, 3));
    }

    @Test
    void viaWay_no_withOverlap_2() {
        assertEquals(nodes(0, 1, 2, 6), calcPath(0, 6));
    }

    @Test
    void viaWay_no_withOverlap_3() {
        assertEquals(nodes(5, 1, 2, 3), calcPath(5, 3));
    }

    @Test
    void viaWay_no_withOverlap_4() {
        assertEquals(nodes(5, 1, 2, 6), calcPath(5, 6));
    }

    @Test
    void viaWay_no_withOverlap_5() {
        assertEquals(NO_PATH, calcPath(1, 4));
    }

    @Test
    void viaWay_no_withOverlap_6() {
        assertEquals(nodes(1, 2, 3, 7), calcPath(1, 7));
    }

    @Test
    void viaWay_no_withOverlap_7() {
        assertEquals(nodes(6, 2, 3, 4), calcPath(6, 4));
    }

    @Test
    void viaWay_no_withOverlap_8() {
        assertEquals(nodes(6, 2, 3, 7), calcPath(6, 7));
    }

    @Test
    void viaWay_no_withOverlap_more_complex_1() {
        assertEquals(nodes(0, 3, 7, 8, 9), calcPath(0, 9));
    }

    @Test
    void viaWay_no_withOverlap_more_complex_2() {
        assertEquals(nodes(5, 4, 3, 7, 10, 11, 8, 9), calcPath(5, 9));
    }

    @Test
    void viaWay_no_withOverlap_more_complex_3() {
        assertEquals(nodes(5, 4, 3, 2), calcPath(5, 2));
    }

    @Test
    void viaWay_no_withOverlap_more_complex_4() {
        assertEquals(nodes(0, 3, 7, 10), calcPath(0, 10));
    }

    @Test
    void viaWay_no_withOverlap_more_complex_5() {
        assertEquals(nodes(6, 7, 8, 9), calcPath(6, 9));
    }

    @Test
    void viaWay_common_via_edge_opposite_direction_1() {
        assertEquals(nodes(0, 1, 2), calcPath(0, 2));
    }

    @Test
    void viaWay_common_via_edge_opposite_direction_2() {
        assertEquals(nodes(0, 1, 4, 5), calcPath(0, 5));
    }

    @Test
    void viaWay_common_via_edge_opposite_direction_3() {
        assertEquals(nodes(0, 1, 4, 3), calcPath(0, 3));
    }

    @Test
    void viaWay_common_via_edge_opposite_direction_4() {
        assertEquals(nodes(2, 1, 0), calcPath(2, 0));
    }

    @Test
    void viaWay_common_via_edge_opposite_direction_5() {
        assertEquals(nodes(2, 1, 4, 3), calcPath(2, 3));
    }

    @Test
    void viaWay_common_via_edge_opposite_direction_6() {
        assertEquals(NO_PATH, calcPath(2, 5));
    }

    @Test
    void viaWay_common_via_edge_opposite_direction_7() {
        assertEquals(NO_PATH, calcPath(3, 0));
    }

    @Test
    void viaWay_common_via_edge_opposite_direction_8() {
        assertEquals(nodes(3, 4, 1, 2), calcPath(3, 2));
    }

    @Test
    void viaWay_common_via_edge_opposite_direction_9() {
        assertEquals(nodes(3, 4, 5), calcPath(3, 5));
    }

    @Test
    void viaWay_common_via_edge_opposite_direction_10() {
        assertEquals(nodes(5, 4, 1, 0), calcPath(5, 0));
    }

    @Test
    void viaWay_common_via_edge_opposite_direction_11() {
        assertEquals(nodes(5, 4, 1, 2), calcPath(5, 2));
    }

    @Test
    void viaWay_common_via_edge_opposite_direction_12() {
        assertEquals(nodes(5, 4, 3), calcPath(5, 3));
    }

    @Test
    void viaWay_common_via_edge_same_direction_1() {
        assertEquals(nodes(0, 1, 4, 3), calcPath(0, 3));
    }

    @Test
    void viaWay_common_via_edge_same_direction_2() {
        assertEquals(nodes(2, 1, 4, 5), calcPath(2, 5));
    }

    @Test
    void viaWay_common_via_edge_same_direction_3() {
        assertEquals(NO_PATH, calcPath(0, 3));
    }

    @Test
    void viaWay_common_via_edge_same_direction_4() {
        assertEquals(nodes(3, 4, 1, 0), calcPath(3, 0));
    }

    @Test
    void viaWay_common_via_edge_same_direction_5() {
        assertEquals(NO_PATH, calcPath(2, 5));
    }

    @Test
    void viaWay_common_via_edge_same_direction_6() {
        assertEquals(nodes(5, 4, 1, 2), calcPath(5, 2));
    }

    @Test
    void viaWay_common_via_edge_same_direction_7() {
        assertEquals(nodes(0, 1, 2), calcPath(0, 2));
    }

    @Test
    void viaWay_common_via_edge_same_direction_8() {
        assertEquals(nodes(1, 4, 3), calcPath(1, 3));
    }

    @Test
    void viaWay_common_via_edge_same_direction_9() {
        assertEquals(nodes(0, 1, 4, 5), calcPath(0, 5));
    }

    @Test
    void viaWay_common_via_edge_same_direction_10() {
        assertEquals(nodes(2, 1, 4, 3), calcPath(2, 3));
    }

    @Test
    void viaWay_only_1() {
        assertEquals(nodes(1, 2, 5, 7), calcPath(1, 7));
    }

    @Test
    void viaWay_only_2() {
        assertEquals(NO_PATH, calcPath(1, 3));
    }

    @Test
    void viaWay_only_3() {
        assertEquals(NO_PATH, calcPath(1, 4));
    }

    @Test
    void viaWay_only_4() {
        assertEquals(nodes(0, 2, 5, 6), calcPath(0, 6));
    }

    @Test
    void viaWay_only_5() {
        assertEquals(nodes(0, 2, 5, 7), calcPath(0, 7));
    }

    @Test
    void viaWay_only_twoRestrictionsSharingSameVia_1() {
        assertEquals(nodes(0, 1, 2, 4), calcPath(0, 4));
    }

    @Test
    void viaWay_only_twoRestrictionsSharingSameVia_2() {
        assertEquals(nodes(5, 1, 2, 3), calcPath(5, 3));
    }

    @Test
    void viaWay_only_twoRestrictionsSharingSameVia_3() {
        assertEquals(nodes(0, 1, 2, 3), calcPath(0, 3));
    }

    @Test
    void viaWay_only_twoRestrictionsSharingSameVia_4() {
        assertEquals(NO_PATH, calcPath(5, 3));
    }

    @Test
    void viaWay_only_twoRestrictionsSharingSameVia_5() {
        assertEquals(nodes(5, 1, 2, 4), calcPath(5, 4));
    }

    @Test
    void viaWay_only_twoRestrictionsSharingSameVia_6() {
        assertEquals(NO_PATH, calcPath(0, 4));
    }

    @Test
    void viaWay_only_twoRestrictionsSharingSameVia_7() {
        assertEquals(nodes(3, 2, 1, 0), calcPath(3, 0));
    }

    @Test
    void viaWay_only_twoRestrictionsSharingSameVia_8() {
        assertEquals(nodes(3, 2, 1, 5), calcPath(3, 5));
    }

    @Test
    void viaWay_only_twoRestrictionsSharingSameVia_9() {
        assertEquals(nodes(4, 2, 1, 0), calcPath(4, 0));
    }

    @Test
    void viaWay_only_twoRestrictionsSharingSameVia_10() {
        assertEquals(nodes(4, 2, 1, 5), calcPath(4, 5));
    }

    @Test
    void viaWay_only_twoRestrictionsSharingSameVia_different_directions_1() {
        assertEquals(nodes(0, 1, 2, 3), calcPath(0, 3));
    }

    @Test
    void viaWay_only_twoRestrictionsSharingSameVia_different_directions_2() {
        assertEquals(NO_PATH, calcPath(0, 4));
    }

    @Test
    void viaWay_only_twoRestrictionsSharingSameVia_different_directions_3() {
        assertEquals(NO_PATH, calcPath(0, 5));
    }

    @Test
    void viaWay_only_twoRestrictionsSharingSameVia_different_directions_4() {
        assertEquals(nodes(3, 2, 1, 0), calcPath(3, 0));
    }

    @Test
    void viaWay_only_twoRestrictionsSharingSameVia_different_directions_5() {
        assertEquals(nodes(3, 2, 4), calcPath(3, 4));
    }

    @Test
    void viaWay_only_twoRestrictionsSharingSameVia_different_directions_6() {
        assertEquals(nodes(3, 2, 1, 5), calcPath(3, 5));
    }

    @Test
    void viaWay_only_twoRestrictionsSharingSameVia_different_directions_7() {
        assertEquals(NO_PATH, calcPath(4, 0));
    }

    @Test
    void viaWay_only_twoRestrictionsSharingSameVia_different_directions_8() {
        assertEquals(NO_PATH, calcPath(4, 3));
    }

    @Test
    void viaWay_only_twoRestrictionsSharingSameVia_different_directions_9() {
        assertEquals(nodes(4, 2, 1, 5), calcPath(4, 5));
    }

    @Test
    void viaWay_only_twoRestrictionsSharingSameVia_different_directions_10() {
        assertEquals(nodes(5, 1, 0), calcPath(5, 0));
    }

    @Test
    void viaWay_only_twoRestrictionsSharingSameVia_different_directions_11() {
        assertEquals(nodes(5, 1, 2, 3), calcPath(5, 3));
    }

    @Test
    void viaWay_only_twoRestrictionsSharingSameVia_different_directions_12() {
        assertEquals(nodes(5, 1, 2, 4), calcPath(5, 4));
    }

    @Test
    void viaWayAndNode_1() {
        assertEquals(nodes(0, 1, 3), calcPath(0, 3));
    }

    @Test
    void viaWayAndNode_2() {
        assertEquals(nodes(4, 0, 1, 2), calcPath(4, 2));
    }

    @Test
    void viaWayAndNode_3() {
        assertEquals(NO_PATH, calcPath(4, 2));
    }

    @Test
    void viaWayAndNode_4() {
        assertEquals(NO_PATH, calcPath(0, 3));
    }

    @Test
    void multiViaWay_only_1() {
        assertEquals(nodes(0, 1, 4), calcPath(0, 4));
    }

    @Test
    void multiViaWay_only_2() {
        assertEquals(nodes(0, 1, 2, 3, 6, 5, 4), calcPath(0, 4));
    }

    @Test
    void loop_only_1() {
        assertEquals(nodes(3, 1, 0, 1, 2), calcPath(3, 2));
    }

    @Test
    void loop_only_2() {
        assertEquals(NO_PATH, calcPath(3, 4));
    }
}
