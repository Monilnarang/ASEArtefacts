package com.iluwatar.doubledispatch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class RectangleTest_Purified {

    @Test
    void testIntersection_1() {
        assertTrue(new Rectangle(0, 0, 1, 1).intersectsWith(new Rectangle(0, 0, 1, 1)));
    }

    @Test
    void testIntersection_2() {
        assertTrue(new Rectangle(0, 0, 1, 1).intersectsWith(new Rectangle(-1, -5, 7, 8)));
    }

    @Test
    void testIntersection_3() {
        assertFalse(new Rectangle(0, 0, 1, 1).intersectsWith(new Rectangle(2, 2, 3, 3)));
    }

    @Test
    void testIntersection_4() {
        assertFalse(new Rectangle(0, 0, 1, 1).intersectsWith(new Rectangle(-2, -2, -1, -1)));
    }
}
