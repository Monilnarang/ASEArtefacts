package com.graphhopper.util;

import com.graphhopper.util.shapes.GHPoint;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class DistanceCalcEarthTest_Parameterized {

    private DistanceCalc dc = new DistanceCalcEarth();

    @ParameterizedTest
    @MethodSource("Provider_testValidEdgeDistance_1to3")
    public void testValidEdgeDistance_1to3(double param1, double param2, double param3, double param4, double param5, double param6) {
        assertTrue(dc.validEdgeDistance(param1, param2, param3, param4, param5, param6));
    }

    static public Stream<Arguments> Provider_testValidEdgeDistance_1to3() {
        return Stream.of(arguments(49.94241, 11.544356, 49.937964, 11.541824, 49.942272, 11.555643), arguments(49.936624, 11.547636, 49.937964, 11.541824, 49.942272, 11.555643), arguments(49.940712, 11.556069, 49.937964, 11.541824, 49.942272, 11.555643));
    }

    @ParameterizedTest
    @MethodSource("Provider_testValidEdgeDistance_4to7")
    public void testValidEdgeDistance_4to7(double param1, double param2, double param3, double param4, double param5, double param6) {
        assertFalse(dc.validEdgeDistance(param1, param2, param3, param4, param5, param6));
    }

    static public Stream<Arguments> Provider_testValidEdgeDistance_4to7() {
        return Stream.of(arguments(49.935119, 11.541649, 49.937964, 11.541824, 49.942272, 11.555643), arguments(49.939317, 11.539675, 49.937964, 11.541824, 49.942272, 11.555643), arguments(49.944482, 11.555446, 49.937964, 11.541824, 49.942272, 11.555643), arguments(49.94085, 11.557356, 49.937964, 11.541824, 49.942272, 11.555643));
    }
}
