package com.graphhopper.reader.dem;

import com.graphhopper.util.Helper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ElevationInterpolatorTest_Purified {

    private static final double PRECISION = ElevationInterpolator.EPSILON2;

    private ElevationInterpolator elevationInterpolator = new ElevationInterpolator();

    @Test
    public void calculatesElevationOnTwoPoints_1() {
        assertEquals(15, elevationInterpolator.calculateElevationBasedOnTwoPoints(0, 0, -10, -10, 10, 10, 10, 20), PRECISION);
    }

    @Test
    public void calculatesElevationOnTwoPoints_2() {
        assertEquals(15, elevationInterpolator.calculateElevationBasedOnTwoPoints(-10, 10, -10, -10, 10, 10, 10, 20), PRECISION);
    }

    @Test
    public void calculatesElevationOnTwoPoints_3() {
        assertEquals(15, elevationInterpolator.calculateElevationBasedOnTwoPoints(-5, 5, -10, -10, 10, 10, 10, 20), PRECISION);
    }

    @Test
    public void calculatesElevationOnTwoPoints_4() {
        assertEquals(19, elevationInterpolator.calculateElevationBasedOnTwoPoints(8, 8, -10, -10, 10, 10, 10, 20), PRECISION);
    }

    @Test
    public void calculatesElevationOnTwoPoints_5() {
        assertEquals(10, elevationInterpolator.calculateElevationBasedOnTwoPoints(0, 0, -ElevationInterpolator.EPSILON / 3, 0, 10, ElevationInterpolator.EPSILON / 2, 0, 20), PRECISION);
    }

    @Test
    public void calculatesElevationOnTwoPoints_6() {
        assertEquals(20, elevationInterpolator.calculateElevationBasedOnTwoPoints(0, 0, -ElevationInterpolator.EPSILON / 2, 0, 10, ElevationInterpolator.EPSILON / 3, 0, 20), PRECISION);
    }

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
    public void calculatesElevationOnNPoints_1() {
        assertEquals(0, elevationInterpolator.calculateElevationBasedOnPointList(5, 5, Helper.createPointList3D(0, 0, 0, 10, 0, 0, 10, 10, 0, 0, 10, 0)), PRECISION);
    }

    @Test
    public void calculatesElevationOnNPoints_2() {
        assertEquals(10, elevationInterpolator.calculateElevationBasedOnPointList(5, 5, Helper.createPointList3D(0, 0, 0, 10, 0, 10, 10, 10, 20, 0, 10, 10)), PRECISION);
    }

    @Test
    public void calculatesElevationOnNPoints_3() {
        assertEquals(5, elevationInterpolator.calculateElevationBasedOnPointList(5, 5, Helper.createPointList3D(0, 0, 0, 10, 0, 10, 10, 10, 0, 0, 10, 10)), PRECISION);
    }

    @Test
    public void calculatesElevationOnNPoints_4() {
        assertEquals(2.65, elevationInterpolator.calculateElevationBasedOnPointList(2.5, 2.5, Helper.createPointList3D(0, 0, 0, 10, 0, 10, 10, 10, 0, 0, 10, 10)), PRECISION);
    }

    @Test
    public void calculatesElevationOnNPoints_5() {
        assertEquals(0, elevationInterpolator.calculateElevationBasedOnPointList(0.1, 0.1, Helper.createPointList3D(0, 0, 0, 10, 0, 10, 10, 10, 0, 0, 10, 10)), PRECISION);
    }

    @Test
    public void calculatesElevationOnNPoints_6() {
        assertEquals(0, elevationInterpolator.calculateElevationBasedOnPointList(ElevationInterpolator.EPSILON / 2, ElevationInterpolator.EPSILON / 2, Helper.createPointList3D(0, 0, 0, 10, 0, 10, 10, 10, 0, 0, 10, 10)), PRECISION);
    }

    @Test
    public void calculatesElevationOnNPoints_7() {
        assertEquals(0, elevationInterpolator.calculateElevationBasedOnPointList(5, 0, Helper.createPointList3D(0, 0, 0, 10, 1, 10, 10, -1, -10, 20, 0, 0)), PRECISION);
    }
}
