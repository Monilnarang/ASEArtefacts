package com.graphhopper.routing.util;

import com.graphhopper.routing.ev.DecimalEncodedValue;
import com.graphhopper.routing.ev.DecimalEncodedValueImpl;
import com.graphhopper.routing.ev.SimpleBooleanEncodedValue;
import com.graphhopper.storage.BaseGraph;
import com.graphhopper.storage.NodeAccess;
import com.graphhopper.util.EdgeIteratorState;
import com.graphhopper.util.PointList;
import com.graphhopper.util.shapes.GHPoint;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static com.graphhopper.search.KVStorage.KValue;
import static com.graphhopper.util.Parameters.Details.STREET_NAME;
import static org.junit.jupiter.api.Assertions.*;

public class NameSimilarityEdgeFilterTest_Purified {

    private final GHPoint basePoint = new GHPoint(49.4652132, 11.1435159);

    private NameSimilarityEdgeFilter createNameSimilarityEdgeFilter(String pointHint) {
        return new NameSimilarityEdgeFilter(edgeState -> true, pointHint, basePoint, 100);
    }

    private EdgeIteratorState createTestEdgeIterator(String name) {
        PointList pointList = new PointList();
        pointList.add(basePoint);
        EdgeIteratorState edge = new BaseGraph.Builder(1).create().edge(0, 1).setWayGeometry(pointList);
        if (name != null)
            edge.setKeyValues(Map.of(STREET_NAME, new KValue(name)));
        return edge;
    }

    @Test
    public void testAcceptFromNominatim_1() {
        assertTrue(createNameSimilarityEdgeFilter("Wentworth Street, Caringbah South").accept(createTestEdgeIterator("Wentworth Street")));
    }

    @Test
    public void testAcceptFromNominatim_2() {
        assertTrue(createNameSimilarityEdgeFilter("Zum Toffental, Altdorf bei Nürnnberg").accept(createTestEdgeIterator("Zum Toffental")));
    }

    @Test
    public void testAcceptFromGoogleMapsGeocoding_1_testMerged_1() {
        EdgeFilter edgeFilter = createNameSimilarityEdgeFilter("Rue Notre-Dame O Montréal");
        assertFalse(edgeFilter.accept(createTestEdgeIterator("Rue Dupré")));
        assertTrue(edgeFilter.accept(createTestEdgeIterator("Rue Notre-Dame Ouest")));
        edgeFilter = createNameSimilarityEdgeFilter("Rue Saint-Antoine O, Montréal");
        assertTrue(edgeFilter.accept(createTestEdgeIterator("Rue Saint-Antoine O")));
        assertFalse(edgeFilter.accept(createTestEdgeIterator("Rue Saint-Jacques")));
        edgeFilter = createNameSimilarityEdgeFilter("Rue de Bleury");
        assertTrue(edgeFilter.accept(createTestEdgeIterator("Rue de Bleury")));
        assertFalse(edgeFilter.accept(createTestEdgeIterator("Rue Balmoral")));
    }

    @Test
    public void testAcceptFromGoogleMapsGeocoding_7() {
        assertTrue(createNameSimilarityEdgeFilter("Main Rd").accept(createTestEdgeIterator("Main Road")));
    }

    @Test
    public void testAcceptFromGoogleMapsGeocoding_8() {
        assertTrue(createNameSimilarityEdgeFilter("Main Road").accept(createTestEdgeIterator("Main Rd")));
    }

    @Test
    public void testAcceptFromGoogleMapsGeocoding_9() {
        assertTrue(createNameSimilarityEdgeFilter("Main Rd").accept(createTestEdgeIterator("Main Road, New York")));
    }

    @Test
    public void testAcceptFromGoogleMapsGeocoding_10() {
        assertTrue(createNameSimilarityEdgeFilter("Cape Point Rd").accept(createTestEdgeIterator("Cape Point")));
    }

    @Test
    public void testAcceptFromGoogleMapsGeocoding_11() {
        assertTrue(createNameSimilarityEdgeFilter("Cape Point Rd").accept(createTestEdgeIterator("Cape Point Road")));
    }

    @Test
    public void testAcceptFromGoogleMapsGeocoding_12() {
        assertTrue(createNameSimilarityEdgeFilter("Av. Juan Ramón Ramírez").accept(createTestEdgeIterator("Avenida Juan Ramón Ramírez")));
    }

    @Test
    public void normalization_1() {
        assertEquals("northderby", createNameSimilarityEdgeFilter("North Derby Lane").getNormalizedPointHint());
    }

    @Test
    public void normalization_2() {
        assertEquals("28north", createNameSimilarityEdgeFilter("I-28 N").getNormalizedPointHint());
    }

    @Test
    public void normalization_3() {
        assertEquals("28north", createNameSimilarityEdgeFilter(" I-28    N  ").getNormalizedPointHint());
    }

    @Test
    public void normalization_4() {
        assertEquals("south23rd", createNameSimilarityEdgeFilter("S 23rd St").getNormalizedPointHint());
    }

    @Test
    public void normalization_5() {
        assertEquals("66", createNameSimilarityEdgeFilter("Route 66").getNormalizedPointHint());
    }

    @Test
    public void normalization_6() {
        assertEquals("fayettecounty1", createNameSimilarityEdgeFilter("Fayette County Rd 1").getNormalizedPointHint());
    }

    @Test
    public void normalization_7() {
        assertEquals("112", createNameSimilarityEdgeFilter("A B C 1 12").getNormalizedPointHint());
    }

    @Test
    public void testServiceMix_1() {
        assertTrue(createNameSimilarityEdgeFilter("North Derby Lane").accept(createTestEdgeIterator("N Derby Ln")));
    }

    @Test
    public void testServiceMix_2() {
        assertTrue(createNameSimilarityEdgeFilter("N Derby Ln").accept(createTestEdgeIterator("North Derby Lane")));
    }

    @Test
    public void testServiceMix_3() {
        assertFalse(createNameSimilarityEdgeFilter("North Derby Lane").accept(createTestEdgeIterator("I-29 N")));
    }

    @Test
    public void testServiceMix_4() {
        assertFalse(createNameSimilarityEdgeFilter("I-29 N").accept(createTestEdgeIterator("North Derby Lane")));
    }

    @Test
    public void testServiceMix_5() {
        assertTrue(createNameSimilarityEdgeFilter("George Street").accept(createTestEdgeIterator("George St")));
    }
}
