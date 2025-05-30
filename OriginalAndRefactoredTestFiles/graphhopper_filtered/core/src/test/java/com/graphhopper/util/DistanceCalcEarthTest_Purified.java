package com.graphhopper.util;

import com.graphhopper.util.shapes.GHPoint;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DistanceCalcEarthTest_Purified {

    private DistanceCalc dc = new DistanceCalcEarth();

    @Test
    public void testValidEdgeDistance_1() {
        assertTrue(dc.validEdgeDistance(49.94241, 11.544356, 49.937964, 11.541824, 49.942272, 11.555643));
    }

    @Test
    public void testValidEdgeDistance_2() {
        assertTrue(dc.validEdgeDistance(49.936624, 11.547636, 49.937964, 11.541824, 49.942272, 11.555643));
    }

    @Test
    public void testValidEdgeDistance_3() {
        assertTrue(dc.validEdgeDistance(49.940712, 11.556069, 49.937964, 11.541824, 49.942272, 11.555643));
    }

    @Test
    public void testValidEdgeDistance_4() {
        assertFalse(dc.validEdgeDistance(49.935119, 11.541649, 49.937964, 11.541824, 49.942272, 11.555643));
    }

    @Test
    public void testValidEdgeDistance_5() {
        assertFalse(dc.validEdgeDistance(49.939317, 11.539675, 49.937964, 11.541824, 49.942272, 11.555643));
    }

    @Test
    public void testValidEdgeDistance_6() {
        assertFalse(dc.validEdgeDistance(49.944482, 11.555446, 49.937964, 11.541824, 49.942272, 11.555643));
    }

    @Test
    public void testValidEdgeDistance_7() {
        assertFalse(dc.validEdgeDistance(49.94085, 11.557356, 49.937964, 11.541824, 49.942272, 11.555643));
    }
}
