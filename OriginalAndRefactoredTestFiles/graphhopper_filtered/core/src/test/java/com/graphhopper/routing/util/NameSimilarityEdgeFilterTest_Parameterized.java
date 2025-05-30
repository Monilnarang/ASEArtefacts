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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class NameSimilarityEdgeFilterTest_Parameterized {

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

    @ParameterizedTest
    @MethodSource("Provider_testAcceptFromNominatim_1_1to2_2_5_7to12")
    public void testAcceptFromNominatim_1_1to2_2_5_7to12(String param1, String param2) {
        assertTrue(createNameSimilarityEdgeFilter(param1).accept(createTestEdgeIterator(param2)));
    }

    static public Stream<Arguments> Provider_testAcceptFromNominatim_1_1to2_2_5_7to12() {
        return Stream.of(arguments("Wentworth Street, Caringbah South", "Wentworth Street"), arguments("Zum Toffental, Altdorf bei Nürnnberg", "Zum Toffental"), arguments("Main Rd", "Main Road"), arguments("Main Road", "Main Rd"), arguments("Main Rd", "Main Road, New York"), arguments("Cape Point Rd", "Cape Point"), arguments("Cape Point Rd", "Cape Point Road"), arguments("Av. Juan Ramón Ramírez", "Avenida Juan Ramón Ramírez"), arguments("North Derby Lane", "N Derby Ln"), arguments("N Derby Ln", "North Derby Lane"), arguments("George Street", "George St"));
    }

    @ParameterizedTest
    @MethodSource("Provider_normalization_1to7")
    public void normalization_1to7(String param1, String param2) {
        assertEquals(param1, createNameSimilarityEdgeFilter(param2).getNormalizedPointHint());
    }

    static public Stream<Arguments> Provider_normalization_1to7() {
        return Stream.of(arguments("northderby", "North Derby Lane"), arguments("28north", "I-28 N"), arguments("28north", " I-28    N  "), arguments("south23rd", "S 23rd St"), arguments(66, "Route 66"), arguments("fayettecounty1", "Fayette County Rd 1"), arguments(112, "A B C 1 12"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testServiceMix_3to4")
    public void testServiceMix_3to4(String param1, String param2) {
        assertFalse(createNameSimilarityEdgeFilter(param1).accept(createTestEdgeIterator(param2)));
    }

    static public Stream<Arguments> Provider_testServiceMix_3to4() {
        return Stream.of(arguments("North Derby Lane", "I-29 N"), arguments("I-29 N", "North Derby Lane"));
    }
}
