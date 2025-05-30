package com.graphhopper.util.shapes;

import com.graphhopper.util.DistanceCalcEarth;
import com.graphhopper.util.PointList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CircleTest_Purified {

    @Test
    public void testIntersectCircleBBox_1() {
        assertTrue(new Circle(10, 10, 120000).intersects(new BBox(9, 11, 8, 9)));
    }

    @Test
    public void testIntersectCircleBBox_2() {
        assertFalse(new Circle(10, 10, 110000).intersects(new BBox(9, 11, 8, 9)));
    }
}
