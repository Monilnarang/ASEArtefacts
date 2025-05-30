package com.graphhopper.storage;

import com.graphhopper.routing.ev.*;
import com.graphhopper.routing.util.AccessFilter;
import com.graphhopper.routing.util.EdgeFilter;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.util.*;
import com.graphhopper.util.shapes.BBox;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.util.Map;
import static com.graphhopper.search.KVStorage.KValue;
import static com.graphhopper.util.Parameters.Details.STREET_NAME;
import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractGraphStorageTester_Purified {

    private final String locationParent = "./target/graphstorage";

    protected int defaultSize = 100;

    protected String defaultGraphLoc = "./target/graphstorage/default";

    protected BooleanEncodedValue carAccessEnc = new SimpleBooleanEncodedValue("car_access", true);

    protected DecimalEncodedValue carSpeedEnc = new DecimalEncodedValueImpl("car_speed", 5, 5, false);

    protected BooleanEncodedValue footAccessEnc = new SimpleBooleanEncodedValue("foot_access", true);

    protected DecimalEncodedValue footSpeedEnc = new DecimalEncodedValueImpl("foot_speed", 4, 1, true);

    protected EncodingManager encodingManager = createEncodingManager();

    protected EncodingManager createEncodingManager() {
        return new EncodingManager.Builder().add(carAccessEnc).add(carSpeedEnc).add(footAccessEnc).add(footSpeedEnc).add(RoadClass.create()).build();
    }

    protected BaseGraph graph;

    EdgeFilter carOutFilter = AccessFilter.outEdges(carAccessEnc);

    EdgeFilter carInFilter = AccessFilter.inEdges(carAccessEnc);

    EdgeExplorer carOutExplorer;

    EdgeExplorer carInExplorer;

    EdgeExplorer carAllExplorer;

    public static void assertPList(PointList expected, PointList list) {
        assertEquals(expected.size(), list.size(), "size of point lists is not equal");
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.getLat(i), list.getLat(i), 1e-4);
            assertEquals(expected.getLon(i), list.getLon(i), 1e-4);
        }
    }

    public static int getIdOf(Graph g, double latitude) {
        int s = g.getNodes();
        NodeAccess na = g.getNodeAccess();
        for (int i = 0; i < s; i++) {
            if (Math.abs(na.getLat(i) - latitude) < 1e-4) {
                return i;
            }
        }
        return -1;
    }

    public static int getIdOf(Graph g, double latitude, double longitude) {
        int s = g.getNodes();
        NodeAccess na = g.getNodeAccess();
        for (int i = 0; i < s; i++) {
            if (Math.abs(na.getLat(i) - latitude) < 1e-4 && Math.abs(na.getLon(i) - longitude) < 1e-4) {
                return i;
            }
        }
        throw new IllegalArgumentException("did not find node with location " + (float) latitude + "," + (float) longitude);
    }

    protected BaseGraph createGHStorage() {
        BaseGraph g = createGHStorage(defaultGraphLoc, false);
        carOutExplorer = g.createEdgeExplorer(carOutFilter);
        carInExplorer = g.createEdgeExplorer(carInFilter);
        carAllExplorer = g.createEdgeExplorer();
        return g;
    }

    abstract BaseGraph createGHStorage(String location, boolean is3D);

    @BeforeEach
    public void setUp() {
        Helper.removeDir(new File(locationParent));
    }

    @AfterEach
    public void tearDown() {
        Helper.close(graph);
        Helper.removeDir(new File(locationParent));
    }

    protected void initExampleGraph(Graph g) {
        NodeAccess na = g.getNodeAccess();
        na.setNode(0, 12, 23);
        na.setNode(1, 38.33f, 135.3f);
        na.setNode(2, 6, 139);
        na.setNode(3, 78, 89);
        na.setNode(4, 2, 1);
        na.setNode(5, 7, 5);
        g.edge(0, 1).setDistance((12)).set(carAccessEnc, true, true);
        g.edge(0, 2).setDistance((212)).set(carAccessEnc, true, true);
        g.edge(0, 3).setDistance((212)).set(carAccessEnc, true, true);
        g.edge(0, 4).setDistance((212)).set(carAccessEnc, true, true);
        g.edge(0, 5).setDistance((212)).set(carAccessEnc, true, true);
    }

    private void checkExampleGraph(Graph graph) {
        NodeAccess na = graph.getNodeAccess();
        assertEquals(12f, na.getLat(0), 1e-6);
        assertEquals(23f, na.getLon(0), 1e-6);
        assertEquals(38.33f, na.getLat(1), 1e-6);
        assertEquals(135.3f, na.getLon(1), 1e-6);
        assertEquals(6, na.getLat(2), 1e-6);
        assertEquals(139, na.getLon(2), 1e-6);
        assertEquals(78, na.getLat(3), 1e-6);
        assertEquals(89, na.getLon(3), 1e-6);
        assertEquals(GHUtility.asSet(0), GHUtility.getNeighbors(carOutExplorer.setBaseNode((1))));
        assertEquals(GHUtility.asSet(5, 4, 3, 2, 1), GHUtility.getNeighbors(carOutExplorer.setBaseNode(0)));
        try {
            assertEquals(0, getCountOut(6));
        } catch (Exception ex) {
        }
    }

    private int getCountOut(int node) {
        return GHUtility.count(carOutExplorer.setBaseNode(node));
    }

    private int getCountIn(int node) {
        return GHUtility.count(carInExplorer.setBaseNode(node));
    }

    private int getCountAll(int node) {
        return GHUtility.count(carAllExplorer.setBaseNode(node));
    }

    @Test
    public void testCreateLocation_1() {
        assertEquals(1, getCountOut(1));
    }

    @Test
    public void testCreateLocation_2() {
        assertEquals(2, getCountOut(1));
    }

    @Test
    public void testEdges_1() {
        assertEquals(1, getCountOut(2));
    }

    @Test
    public void testEdges_2() {
        assertEquals(1, getCountOut(1));
    }

    @Test
    public void testEdges_3() {
        assertEquals(2, getCountOut(2));
    }

    @Test
    public void testEdges_4() {
        assertEquals(1, getCountOut(3));
    }

    @Test
    public void testUnidirectional_1_testMerged_1() {
        EdgeIterator i = carOutExplorer.setBaseNode(2);
        assertFalse(i.next());
        i = carOutExplorer.setBaseNode(3);
        i.next();
        assertEquals(2, i.getAdjNode());
        i = carOutExplorer.setBaseNode(1);
        assertTrue(i.next());
        assertEquals(12, i.getAdjNode());
        assertEquals(11, i.getAdjNode());
    }

    @Test
    public void testUnidirectional_2() {
        assertEquals(1, getCountIn(1));
    }

    @Test
    public void testUnidirectional_3() {
        assertEquals(2, getCountIn(2));
    }

    @Test
    public void testUnidirectional_4() {
        assertEquals(0, getCountIn(3));
    }

    @Test
    public void testUnidirectional_5() {
        assertEquals(3, getCountOut(1));
    }

    @Test
    public void testUnidirectional_6() {
        assertEquals(0, getCountOut(2));
    }

    @Test
    public void testUnidirectional_7() {
        assertEquals(1, getCountOut(3));
    }

    @Test
    public void testUnidirectionalEdgeFilter_1_testMerged_1() {
        EdgeIterator i = carOutExplorer.setBaseNode(2);
        assertFalse(i.next());
        i = carOutExplorer.setBaseNode(3);
        i.next();
        assertEquals(2, i.getAdjNode());
        i = carOutExplorer.setBaseNode(1);
        assertTrue(i.next());
        assertEquals(12, i.getAdjNode());
        assertEquals(11, i.getAdjNode());
    }

    @Test
    public void testUnidirectionalEdgeFilter_2() {
        assertEquals(4, getCountAll(1));
    }

    @Test
    public void testUnidirectionalEdgeFilter_3() {
        assertEquals(1, getCountIn(1));
    }

    @Test
    public void testUnidirectionalEdgeFilter_4() {
        assertEquals(2, getCountIn(2));
    }

    @Test
    public void testUnidirectionalEdgeFilter_5() {
        assertEquals(0, getCountIn(3));
    }

    @Test
    public void testUnidirectionalEdgeFilter_6() {
        assertEquals(3, getCountOut(1));
    }

    @Test
    public void testUnidirectionalEdgeFilter_7() {
        assertEquals(0, getCountOut(2));
    }

    @Test
    public void testUnidirectionalEdgeFilter_8() {
        assertEquals(1, getCountOut(3));
    }

    @Test
    public void testDirectional_1() {
        assertEquals(1, getCountAll(1));
    }

    @Test
    public void testDirectional_2() {
        assertEquals(1, getCountIn(1));
    }

    @Test
    public void testDirectional_3() {
        assertEquals(1, getCountOut(1));
    }

    @Test
    public void testDirectional_4() {
        assertEquals(2, getCountAll(2));
    }

    @Test
    public void testDirectional_5() {
        assertEquals(1, getCountIn(2));
    }

    @Test
    public void testDirectional_6() {
        assertEquals(2, getCountOut(2));
    }

    @Test
    public void testDirectional_7() {
        assertEquals(4, getCountAll(3));
    }

    @Test
    public void testDirectional_8() {
        assertEquals(3, getCountIn(3));
    }

    @Test
    public void testDirectional_9() {
        assertEquals(2, getCountOut(3));
    }

    @Test
    public void testDirectional_10() {
        assertEquals(1, getCountAll(4));
    }

    @Test
    public void testDirectional_11() {
        assertEquals(1, getCountIn(4));
    }

    @Test
    public void testDirectional_12() {
        assertEquals(0, getCountOut(4));
    }

    @Test
    public void testDirectional_13() {
        assertEquals(1, getCountAll(5));
    }

    @Test
    public void testDirectional_14() {
        assertEquals(1, getCountIn(5));
    }

    @Test
    public void testDirectional_15() {
        assertEquals(1, getCountOut(5));
    }

    @Test
    public void testDozendEdges_1() {
        int nn = 1;
        assertEquals(1, getCountAll(nn));
    }

    @Test
    public void testDozendEdges_2() {
        assertEquals(2, getCountAll(1));
    }

    @Test
    public void testDozendEdges_3() {
        assertEquals(3, getCountAll(1));
    }

    @Test
    public void testDozendEdges_4() {
        assertEquals(4, getCountAll(1));
    }

    @Test
    public void testDozendEdges_5() {
        assertEquals(5, getCountAll(1));
    }

    @Test
    public void testDozendEdges_6() {
        assertEquals(6, getCountAll(1));
    }

    @Test
    public void testDozendEdges_7() {
        assertEquals(7, getCountAll(1));
    }

    @Test
    public void testDozendEdges_8() {
        assertEquals(8, getCountAll(1));
    }

    @Test
    public void testDozendEdges_9() {
        assertEquals(8, getCountOut(1));
    }

    @Test
    public void testDozendEdges_10() {
        assertEquals(1, getCountIn(1));
    }

    @Test
    public void testDozendEdges_11() {
        assertEquals(1, getCountIn(2));
    }

    @Test
    public void testFootMix_1() {
        graph = createGHStorage();
        graph.edge(0, 1).setDistance((10)).set(footAccessEnc, true, true);
        graph.edge(0, 2).setDistance((10)).set(carAccessEnc, true, true);
        EdgeIteratorState edge = graph.edge(0, 3).setDistance(10);
        edge.set(footAccessEnc, true, true);
        EdgeExplorer footOutExplorer = graph.createEdgeExplorer(AccessFilter.outEdges(footAccessEnc));
        assertEquals(GHUtility.asSet(3, 1), GHUtility.getNeighbors(footOutExplorer.setBaseNode(0)));
    }

    @Test
    public void testFootMix_2() {
        assertEquals(GHUtility.asSet(3, 2), GHUtility.getNeighbors(carOutExplorer.setBaseNode(0)));
    }
}
