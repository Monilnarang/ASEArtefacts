package com.graphhopper.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class AngleCalcTest_Parameterized {

    private final AngleCalc AC = AngleCalc.ANGLE_CALC;

    private boolean isClockwise(Coordinate a, Coordinate b, Coordinate c) {
        return AC.isClockwise(a.x, a.y, b.x, b.y, c.x, c.y);
    }

    private static class Coordinate {

        final double x;

        final double y;

        Coordinate(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    @Test
    public void testOrientationExact_4() {
        assertEquals(-45.0, Math.toDegrees(AC.calcOrientation(0, 0, -1, 1)), 0.01);
    }

    @Test
    public void testOrientationExact_5() {
        assertEquals(-135.0, Math.toDegrees(AC.calcOrientation(0, 0, -1, -1)), 0.01);
    }

    @Test
    public void testOrientationExact_6() {
        assertEquals(90 - 32.76, Math.toDegrees(AC.calcOrientation(49.942, 11.580, 49.944, 11.582)), 0.01);
    }

    @Test
    public void testOrientationExact_7() {
        assertEquals(-90 - 32.76, Math.toDegrees(AC.calcOrientation(49.944, 11.582, 49.942, 11.580)), 0.01);
    }

    @Test
    public void testOrientationFast_4() {
        assertEquals(-45.0, Math.toDegrees(AC.calcOrientation(0, 0, -1, 1, false)), 0.01);
    }

    @Test
    public void testOrientationFast_5() {
        assertEquals(-135.0, Math.toDegrees(AC.calcOrientation(0, 0, -1, -1, false)), 0.01);
    }

    @Test
    public void testOrientationFast_6() {
        assertEquals(90 - 32.92, Math.toDegrees(AC.calcOrientation(49.942, 11.580, 49.944, 11.582, false)), 0.01);
    }

    @Test
    public void testOrientationFast_7() {
        assertEquals(-90 - 32.92, Math.toDegrees(AC.calcOrientation(49.944, 11.582, 49.942, 11.580, false)), 0.01);
    }

    @Test
    public void testAlignOrientation_1() {
        assertEquals(90.0, Math.toDegrees(AC.alignOrientation(Math.toRadians(90), Math.toRadians(90))), 0.001);
    }

    @Test
    public void testAlignOrientation_2() {
        assertEquals(225.0, Math.toDegrees(AC.alignOrientation(Math.toRadians(90), Math.toRadians(-135))), 0.001);
    }

    @Test
    public void testAlignOrientation_3() {
        assertEquals(-45.0, Math.toDegrees(AC.alignOrientation(Math.toRadians(-135), Math.toRadians(-45))), 0.001);
    }

    @Test
    public void testAlignOrientation_4() {
        assertEquals(-270.0, Math.toDegrees(AC.alignOrientation(Math.toRadians(-135), Math.toRadians(90))), 0.001);
    }

    @Test
    public void testCalcAzimuth_3() {
        assertEquals(180.0, AC.calcAzimuth(0, 0, -1, 0), 0.001);
    }

    @Test
    public void testCalcAzimuth_4() {
        assertEquals(270.0, AC.calcAzimuth(0, 0, 0, -1), 0.001);
    }

    @Test
    public void testAtan2_4() {
        assertEquals(180, AngleCalc.atan2(0, -5) * 180 / Math.PI, 1e-2);
    }

    @Test
    public void testAtan2_6() {
        assertEquals(90, Math.atan2(1, 0) * 180 / Math.PI, 1e-2);
    }

    @Test
    public void testConvertAzimuth2xAxisAngle_1() {
        assertEquals(Math.PI / 2, AC.convertAzimuth2xaxisAngle(0), 1E-6);
    }

    @Test
    public void testConvertAzimuth2xAxisAngle_2() {
        assertEquals(Math.PI / 2, Math.abs(AC.convertAzimuth2xaxisAngle(360)), 1E-6);
    }

    @Test
    public void testConvertAzimuth2xAxisAngle_3() {
        assertEquals(0, AC.convertAzimuth2xaxisAngle(90), 1E-6);
    }

    @Test
    public void testConvertAzimuth2xAxisAngle_4() {
        assertEquals(-Math.PI / 2, AC.convertAzimuth2xaxisAngle(180), 1E-6);
    }

    @Test
    public void testConvertAzimuth2xAxisAngle_5() {
        assertEquals(Math.PI, Math.abs(AC.convertAzimuth2xaxisAngle(270)), 1E-6);
    }

    @Test
    public void testConvertAzimuth2xAxisAngle_6() {
        assertEquals(-3 * Math.PI / 4, AC.convertAzimuth2xaxisAngle(225), 1E-6);
    }

    @Test
    public void testConvertAzimuth2xAxisAngle_7() {
        assertEquals(3 * Math.PI / 4, AC.convertAzimuth2xaxisAngle(315), 1E-6);
    }

    @ParameterizedTest
    @MethodSource("Provider_testOrientationExact_1to3")
    public void testOrientationExact_1to3(double param1, double param2, int param3, int param4, int param5, int param6) {
        assertEquals(param1, Math.toDegrees(AC.calcOrientation(param3, param4, param5, param6)), param2);
    }

    static public Stream<Arguments> Provider_testOrientationExact_1to3() {
        return Stream.of(arguments(90.0, 0.01, 0, 0, 1, 0), arguments(45.0, 0.01, 0, 0, 1, 1), arguments(0.0, 0.01, 0, 0, 0, 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testOrientationFast_1to3")
    public void testOrientationFast_1to3(double param1, double param2, int param3, int param4, int param5, int param6) {
        assertEquals(param1, Math.toDegrees(AC.calcOrientation(param3, param4, param5, param6, false)), param2);
    }

    static public Stream<Arguments> Provider_testOrientationFast_1to3() {
        return Stream.of(arguments(90.0, 0.01, 0, 0, 1, 0), arguments(45.0, 0.01, 0, 0, 1, 1), arguments(0.0, 0.01, 0, 0, 0, 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCalcAzimuth_1to2_5")
    public void testCalcAzimuth_1to2_5(double param1, double param2, int param3, int param4, int param5, int param6) {
        assertEquals(param1, AC.calcAzimuth(param3, param4, param5, param6), param2);
    }

    static public Stream<Arguments> Provider_testCalcAzimuth_1to2_5() {
        return Stream.of(arguments(45.0, 0.001, 0, 0, 1, 1), arguments(90.0, 0.001, 0, 0, 0, 1), arguments(0.0, 0.001, 49.942, 11.580, 49.944, 11.580));
    }

    @ParameterizedTest
    @MethodSource("Provider_testAtan2_1_3_7")
    public void testAtan2_1_3_7(int param1, double param2, int param3, int param4, int param5) {
        assertEquals(param1, AngleCalc.atan2(param4, param5) * param3 / Math.PI, param2);
    }

    static public Stream<Arguments> Provider_testAtan2_1_3_7() {
        return Stream.of(arguments(45, 1e-2, 180, 5, 5), arguments(11.14, 1, 180, 1, 5), arguments(90, 1e-2, 180, 1, 0));
    }

    @ParameterizedTest
    @MethodSource("Provider_testAtan2_2_5")
    public void testAtan2_2_5(double param1, int param2, int param3, int param4, int param5) {
        assertEquals(-param2, AngleCalc.atan2(-param5, param4) * param3 / Math.PI, param1);
    }

    static public Stream<Arguments> Provider_testAtan2_2_5() {
        return Stream.of(arguments(1e-2, 45, 180, 5, 5), arguments(1e-2, 90, 180, 0, 5));
    }
}
