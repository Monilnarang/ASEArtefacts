package com.graphhopper.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AngleCalcTest_Purified {

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
    public void testOrientationExact_1() {
        assertEquals(90.0, Math.toDegrees(AC.calcOrientation(0, 0, 1, 0)), 0.01);
    }

    @Test
    public void testOrientationExact_2() {
        assertEquals(45.0, Math.toDegrees(AC.calcOrientation(0, 0, 1, 1)), 0.01);
    }

    @Test
    public void testOrientationExact_3() {
        assertEquals(0.0, Math.toDegrees(AC.calcOrientation(0, 0, 0, 1)), 0.01);
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
    public void testOrientationFast_1() {
        assertEquals(90.0, Math.toDegrees(AC.calcOrientation(0, 0, 1, 0, false)), 0.01);
    }

    @Test
    public void testOrientationFast_2() {
        assertEquals(45.0, Math.toDegrees(AC.calcOrientation(0, 0, 1, 1, false)), 0.01);
    }

    @Test
    public void testOrientationFast_3() {
        assertEquals(0.0, Math.toDegrees(AC.calcOrientation(0, 0, 0, 1, false)), 0.01);
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
    public void testCalcAzimuth_1() {
        assertEquals(45.0, AC.calcAzimuth(0, 0, 1, 1), 0.001);
    }

    @Test
    public void testCalcAzimuth_2() {
        assertEquals(90.0, AC.calcAzimuth(0, 0, 0, 1), 0.001);
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
    public void testCalcAzimuth_5() {
        assertEquals(0.0, AC.calcAzimuth(49.942, 11.580, 49.944, 11.580), 0.001);
    }

    @Test
    public void testAtan2_1() {
        assertEquals(45, AngleCalc.atan2(5, 5) * 180 / Math.PI, 1e-2);
    }

    @Test
    public void testAtan2_2() {
        assertEquals(-45, AngleCalc.atan2(-5, 5) * 180 / Math.PI, 1e-2);
    }

    @Test
    public void testAtan2_3() {
        assertEquals(11.14, AngleCalc.atan2(1, 5) * 180 / Math.PI, 1);
    }

    @Test
    public void testAtan2_4() {
        assertEquals(180, AngleCalc.atan2(0, -5) * 180 / Math.PI, 1e-2);
    }

    @Test
    public void testAtan2_5() {
        assertEquals(-90, AngleCalc.atan2(-5, 0) * 180 / Math.PI, 1e-2);
    }

    @Test
    public void testAtan2_6() {
        assertEquals(90, Math.atan2(1, 0) * 180 / Math.PI, 1e-2);
    }

    @Test
    public void testAtan2_7() {
        assertEquals(90, AngleCalc.atan2(1, 0) * 180 / Math.PI, 1e-2);
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
}
