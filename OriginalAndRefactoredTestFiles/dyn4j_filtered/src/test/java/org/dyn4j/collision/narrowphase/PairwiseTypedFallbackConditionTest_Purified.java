package org.dyn4j.collision.narrowphase;

import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.Ellipse;
import org.dyn4j.geometry.Polygon;
import org.dyn4j.geometry.Rectangle;
import org.junit.Test;
import junit.framework.TestCase;

public class PairwiseTypedFallbackConditionTest_Purified {

    @Test
    public void isMatch_1_testMerged_1() {
        PairwiseTypedFallbackCondition cond1 = new PairwiseTypedFallbackCondition(Polygon.class, Polygon.class);
        TestCase.assertTrue(cond1.isMatch(Polygon.class, Polygon.class));
        TestCase.assertFalse(cond1.isMatch(Rectangle.class, Rectangle.class));
        TestCase.assertFalse(cond1.isMatch(Rectangle.class, Polygon.class));
        TestCase.assertFalse(cond1.isMatch(Polygon.class, Rectangle.class));
        TestCase.assertFalse(cond1.isMatch(Polygon.class, Circle.class));
        TestCase.assertFalse(cond1.isMatch(Circle.class, Polygon.class));
    }

    @Test
    public void isMatch_19_testMerged_2() {
        PairwiseTypedFallbackCondition cond4 = new PairwiseTypedFallbackCondition(Polygon.class, true, Polygon.class, false);
        TestCase.assertTrue(cond4.isMatch(Polygon.class, Polygon.class));
        TestCase.assertTrue(cond4.isMatch(Polygon.class, Rectangle.class));
        TestCase.assertTrue(cond4.isMatch(Rectangle.class, Polygon.class));
        TestCase.assertFalse(cond4.isMatch(Rectangle.class, Rectangle.class));
        TestCase.assertFalse(cond4.isMatch(Polygon.class, Circle.class));
        TestCase.assertFalse(cond4.isMatch(Circle.class, Polygon.class));
    }

    @Test
    public void isMatch_7_testMerged_3() {
        PairwiseTypedFallbackCondition cond2 = new PairwiseTypedFallbackCondition(Polygon.class, false, Polygon.class, false);
        TestCase.assertTrue(cond2.isMatch(Polygon.class, Polygon.class));
        TestCase.assertTrue(cond2.isMatch(Rectangle.class, Rectangle.class));
        TestCase.assertTrue(cond2.isMatch(Rectangle.class, Polygon.class));
        TestCase.assertTrue(cond2.isMatch(Polygon.class, Rectangle.class));
        TestCase.assertFalse(cond2.isMatch(Polygon.class, Circle.class));
        TestCase.assertFalse(cond2.isMatch(Circle.class, Polygon.class));
    }

    @Test
    public void isMatch_13_testMerged_4() {
        PairwiseTypedFallbackCondition cond3 = new PairwiseTypedFallbackCondition(Polygon.class, false, Polygon.class, true);
        TestCase.assertTrue(cond3.isMatch(Polygon.class, Polygon.class));
        TestCase.assertTrue(cond3.isMatch(Rectangle.class, Polygon.class));
        TestCase.assertTrue(cond3.isMatch(Polygon.class, Rectangle.class));
        TestCase.assertFalse(cond3.isMatch(Rectangle.class, Rectangle.class));
        TestCase.assertFalse(cond3.isMatch(Polygon.class, Circle.class));
        TestCase.assertFalse(cond3.isMatch(Circle.class, Polygon.class));
    }
}
