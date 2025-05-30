package org.dyn4j.geometry;

import org.dyn4j.Epsilon;
import org.junit.Test;
import junit.framework.TestCase;

public class Vector2Test_Purified {

    @Test
    public void create_1_testMerged_1() {
        Vector2 v1 = new Vector2();
        TestCase.assertEquals(0.0, v1.x);
        TestCase.assertEquals(0.0, v1.y);
        Vector2 v2 = new Vector2(1.0, 2.0);
        TestCase.assertEquals(1.0, v2.x);
        TestCase.assertEquals(2.0, v2.y);
        Vector2 v3 = new Vector2(v2);
        TestCase.assertFalse(v3 == v2);
        TestCase.assertEquals(1.0, v3.x);
        TestCase.assertEquals(2.0, v3.y);
        Vector2 v5 = new Vector2(v2, v1);
        TestCase.assertEquals(-1.0, v5.x);
        TestCase.assertEquals(-2.0, v5.y);
    }

    @Test
    public void create_8_testMerged_2() {
        Vector2 v4 = new Vector2(0.0, 1.0, 2.0, 3.0);
        TestCase.assertEquals(2.0, v4.x);
        TestCase.assertEquals(2.0, v4.y);
    }

    @Test
    public void create_12_testMerged_3() {
        Vector2 v7 = new Vector2(Math.toRadians(30.0));
        TestCase.assertEquals(1.000, v7.getMagnitude(), 1.0E-4);
        TestCase.assertEquals(30.000, Math.toDegrees(v7.getDirection()), 1.0E-4);
        Vector2 v6 = Vector2.create(1.0, Math.toRadians(90));
        TestCase.assertEquals(0.000, v6.x, 1.0e-3);
        TestCase.assertEquals(1.000, v6.y, 1.0e-3);
    }
}
