package org.dyn4j.collision.narrowphase;

import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.Ellipse;
import org.dyn4j.geometry.Polygon;
import org.dyn4j.geometry.Rectangle;
import org.junit.Test;
import junit.framework.TestCase;

public class SingleTypedFallbackConditionTest_Purified {

    @Test
    public void isMatch_1_testMerged_1() {
        SingleTypedFallbackCondition cond1 = new SingleTypedFallbackCondition(Polygon.class);
        TestCase.assertTrue(cond1.isMatch(Polygon.class, Polygon.class));
        TestCase.assertFalse(cond1.isMatch(Rectangle.class, Rectangle.class));
        TestCase.assertTrue(cond1.isMatch(Rectangle.class, Polygon.class));
        TestCase.assertTrue(cond1.isMatch(Polygon.class, Rectangle.class));
        TestCase.assertTrue(cond1.isMatch(Polygon.class, Circle.class));
        TestCase.assertTrue(cond1.isMatch(Circle.class, Polygon.class));
        TestCase.assertFalse(cond1.isMatch(Circle.class, Rectangle.class));
        TestCase.assertFalse(cond1.isMatch(Rectangle.class, Circle.class));
        TestCase.assertFalse(cond1.isMatch(Circle.class, Circle.class));
    }

    @Test
    public void isMatch_19_testMerged_2() {
        SingleTypedFallbackCondition cond3 = new SingleTypedFallbackCondition(Polygon.class, true);
        TestCase.assertTrue(cond3.isMatch(Polygon.class, Polygon.class));
        TestCase.assertFalse(cond3.isMatch(Rectangle.class, Rectangle.class));
        TestCase.assertTrue(cond3.isMatch(Rectangle.class, Polygon.class));
        TestCase.assertTrue(cond3.isMatch(Polygon.class, Rectangle.class));
        TestCase.assertTrue(cond3.isMatch(Polygon.class, Circle.class));
        TestCase.assertTrue(cond3.isMatch(Circle.class, Polygon.class));
        TestCase.assertFalse(cond3.isMatch(Circle.class, Rectangle.class));
        TestCase.assertFalse(cond3.isMatch(Rectangle.class, Circle.class));
        TestCase.assertFalse(cond3.isMatch(Circle.class, Circle.class));
    }

    @Test
    public void isMatch_10_testMerged_3() {
        SingleTypedFallbackCondition cond2 = new SingleTypedFallbackCondition(Polygon.class, false);
        TestCase.assertTrue(cond2.isMatch(Polygon.class, Polygon.class));
        TestCase.assertTrue(cond2.isMatch(Rectangle.class, Rectangle.class));
        TestCase.assertTrue(cond2.isMatch(Rectangle.class, Polygon.class));
        TestCase.assertTrue(cond2.isMatch(Polygon.class, Rectangle.class));
        TestCase.assertTrue(cond2.isMatch(Polygon.class, Circle.class));
        TestCase.assertTrue(cond2.isMatch(Circle.class, Polygon.class));
        TestCase.assertTrue(cond2.isMatch(Circle.class, Rectangle.class));
        TestCase.assertTrue(cond2.isMatch(Rectangle.class, Circle.class));
        TestCase.assertFalse(cond2.isMatch(Circle.class, Circle.class));
    }
}
