package com.graphhopper.util.shapes;

import com.graphhopper.util.DistanceCalc;
import com.graphhopper.util.DistanceCalcEarth;
import com.graphhopper.util.PointList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BBoxTest_Purified {

    @Test
    public void testContains_1() {
        assertTrue(new BBox(1, 2, 0, 1).contains(new BBox(1, 2, 0, 1)));
    }

    @Test
    public void testContains_2() {
        assertTrue(new BBox(1, 2, 0, 1).contains(new BBox(1.5, 2, 0.5, 1)));
    }

    @Test
    public void testContains_3() {
        assertFalse(new BBox(1, 2, 0, 0.5).contains(new BBox(1.5, 2, 0.5, 1)));
    }

    @Test
    public void testIntersect_1() {
        assertTrue(new BBox(12, 15, 12, 15).intersects(new BBox(13, 14, 11, 16)));
    }

    @Test
    public void testIntersect_2() {
        assertTrue(new BBox(2, 6, 6, 11).intersects(new BBox(3, 5, 5, 12)));
    }

    @Test
    public void testIntersect_3() {
        assertTrue(new BBox(6, 11, 6, 11).intersects(new BBox(7, 10, 5, 12)));
    }

    @Test
    public void testParseTwoPoints_1() {
        assertEquals(new BBox(2, 4, 1, 3), BBox.parseTwoPoints("1,2,3,4"));
    }

    @Test
    public void testParseTwoPoints_2() {
        assertEquals(new BBox(2, 4, 1, 3), BBox.parseTwoPoints("3,2,1,4"));
    }
}
