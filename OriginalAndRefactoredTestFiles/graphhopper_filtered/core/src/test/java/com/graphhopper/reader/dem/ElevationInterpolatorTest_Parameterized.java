package com.graphhopper.reader.dem;

import com.graphhopper.util.Helper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ElevationInterpolatorTest_Parameterized {

    private static final double PRECISION = ElevationInterpolator.EPSILON2;

    private ElevationInterpolator elevationInterpolator = new ElevationInterpolator();

    @Test
    public void calculatesElevationOnTwoPoints_7() {
        assertEquals(10, elevationInterpolator.calculateElevationBasedOnTwoPoints(0, 0, 0, 0, 10, 0, 0, 20), PRECISION);
    }

    @Test
    public void calculatesElevationOnThreePoints_1() {
        assertEquals(-0.88, elevationInterpolator.calculateElevationBasedOnThreePoints(0, 0, 1, 2, 3, 4, 6, 9, 12, 11, 9), PRECISION);
    }

    @Test
    public void calculatesElevationOnThreePoints_2() {
        assertEquals(15, elevationInterpolator.calculateElevationBasedOnThreePoints(10, 0, 0, 0, 0, 10, 10, 10, 10, -10, 20), PRECISION);
    }

    @Test
    public void calculatesElevationOnThreePoints_3() {
        assertEquals(5, elevationInterpolator.calculateElevationBasedOnThreePoints(5, 5, 0, 0, 0, 10, 10, 10, 20, 20, 20), PRECISION);
    }

    @Test
    public void calculatesElevationOnNPoints_6() {
        assertEquals(0, elevationInterpolator.calculateElevationBasedOnPointList(ElevationInterpolator.EPSILON / 2, ElevationInterpolator.EPSILON / 2, Helper.createPointList3D(0, 0, 0, 10, 0, 10, 10, 10, 0, 0, 10, 10)), PRECISION);
    }

    @Test
    public void calculatesElevationOnNPoints_7() {
        assertEquals(0, elevationInterpolator.calculateElevationBasedOnPointList(5, 0, Helper.createPointList3D(0, 0, 0, 10, 1, 10, 10, -1, -10, 20, 0, 0)), PRECISION);
    }

    @ParameterizedTest
    @MethodSource("Provider_calculatesElevationOnTwoPoints_1_4")
    public void calculatesElevationOnTwoPoints_1_4(int param1, int param2, int param3, int param4, int param5, int param6, int param7, int param8, int param9) {
        assertEquals(param1, elevationInterpolator.calculateElevationBasedOnTwoPoints(param2, param3, -param8, -param9, param4, param5, param6, param7), PRECISION);
    }

    static public Stream<Arguments> Provider_calculatesElevationOnTwoPoints_1_4() {
        return Stream.of(arguments(15, 0, 0, 10, 10, 10, 20, 10, 10), arguments(19, 8, 8, 10, 10, 10, 20, 10, 10));
    }

    @ParameterizedTest
    @MethodSource("Provider_calculatesElevationOnTwoPoints_2to3")
    public void calculatesElevationOnTwoPoints_2to3(int param1, int param2, int param3, int param4, int param5, int param6, int param7, int param8, int param9) {
        assertEquals(param1, elevationInterpolator.calculateElevationBasedOnTwoPoints(-param7, param2, -param8, -param9, param3, param4, param5, param6), PRECISION);
    }

    static public Stream<Arguments> Provider_calculatesElevationOnTwoPoints_2to3() {
        return Stream.of(arguments(15, 10, 10, 10, 10, 20, 10, 10, 10), arguments(15, 5, 10, 10, 10, 20, 5, 10, 10));
    }

    @ParameterizedTest
    @MethodSource("Provider_calculatesElevationOnTwoPoints_5to6")
    public void calculatesElevationOnTwoPoints_5to6(int param1, int param2, int param3, int param4, int param5, int param6, int param7, int param8, int param9) {
        assertEquals(param1, elevationInterpolator.calculateElevationBasedOnTwoPoints(param2, param3, -ElevationInterpolator.EPSILON / param8, param4, param5, ElevationInterpolator.EPSILON / param9, param6, param7), PRECISION);
    }

    static public Stream<Arguments> Provider_calculatesElevationOnTwoPoints_5to6() {
        return Stream.of(arguments(10, 0, 0, 0, 10, 0, 20, 3, 2), arguments(20, 0, 0, 0, 10, 0, 20, 2, 3));
    }

    @ParameterizedTest
    @MethodSource("Provider_calculatesElevationOnNPoints_1to5")
    public void calculatesElevationOnNPoints_1to5(int param1, int param2, int param3, int param4, int param5, int param6, int param7, int param8, int param9, int param10, int param11, int param12, int param13, int param14, int param15) {
        assertEquals(param1, elevationInterpolator.calculateElevationBasedOnPointList(param2, param3, Helper.createPointList3D(param4, param5, param6, param7, param8, param9, param10, param11, param12, param13, param14, param15)), PRECISION);
    }

    static public Stream<Arguments> Provider_calculatesElevationOnNPoints_1to5() {
        return Stream.of(arguments(0, 5, 5, 0, 0, 0, 10, 0, 0, 10, 10, 0, 0, 10, 0), arguments(10, 5, 5, 0, 0, 0, 10, 0, 10, 10, 10, 20, 0, 10, 10), arguments(5, 5, 5, 0, 0, 0, 10, 0, 10, 10, 10, 0, 0, 10, 10), arguments(2.65, 2.5, 2.5, 0, 0, 0, 10, 0, 10, 10, 10, 0, 0, 10, 10), arguments(0, 0.1, 0.1, 0, 0, 0, 10, 0, 10, 10, 10, 0, 0, 10, 10));
    }
}
