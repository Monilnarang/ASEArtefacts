package com.graphhopper.util.shapes;

import com.graphhopper.util.DistanceCalc;
import com.graphhopper.util.DistanceCalcEarth;
import com.graphhopper.util.PointList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class BBoxTest_Parameterized {

    @Test
    public void testContains_3() {
        assertFalse(new BBox(1, 2, 0, 0.5).contains(new BBox(1.5, 2, 0.5, 1)));
    }

    @ParameterizedTest
    @MethodSource("Provider_testContains_1to2")
    public void testContains_1to2(int param1, int param2, int param3, int param4, int param5, int param6, int param7, int param8) {
        assertTrue(new BBox(param1, param2, param3, param4).contains(new BBox(param5, param6, param7, param8)));
    }

    static public Stream<Arguments> Provider_testContains_1to2() {
        return Stream.of(arguments(1, 2, 0, 1, 1, 2, 0, 1), arguments(1, 2, 0, 1, 1.5, 2, 0.5, 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIntersect_1to3")
    public void testIntersect_1to3(int param1, int param2, int param3, int param4, int param5, int param6, int param7, int param8) {
        assertTrue(new BBox(param1, param2, param3, param4).intersects(new BBox(param5, param6, param7, param8)));
    }

    static public Stream<Arguments> Provider_testIntersect_1to3() {
        return Stream.of(arguments(12, 15, 12, 15, 13, 14, 11, 16), arguments(2, 6, 6, 11, 3, 5, 5, 12), arguments(6, 11, 6, 11, 7, 10, 5, 12));
    }

    @ParameterizedTest
    @MethodSource("Provider_testParseTwoPoints_1to2")
    public void testParseTwoPoints_1to2(int param1, int param2, int param3, int param4, String param5) {
        assertEquals(new BBox(param1, param2, param3, param4), BBox.parseTwoPoints(param5));
    }

    static public Stream<Arguments> Provider_testParseTwoPoints_1to2() {
        return Stream.of(arguments(2, 4, 1, 3, "1,2,3,4"), arguments(2, 4, 1, 3, "3,2,1,4"));
    }
}
