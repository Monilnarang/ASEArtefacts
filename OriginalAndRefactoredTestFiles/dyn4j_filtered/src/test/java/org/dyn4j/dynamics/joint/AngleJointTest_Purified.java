package org.dyn4j.dynamics.joint;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Vector2;
import org.junit.Test;
import junit.framework.TestCase;

public class AngleJointTest_Purified extends BaseJointTest {

    @Test
    public void setLimitEnabledSleep_1_testMerged_1() {
        AngleJoint<Body> aj = new AngleJoint<Body>(b1, b2);
        TestCase.assertTrue(aj.isLimitsEnabled());
        aj.setLimitsEnabled(false);
        TestCase.assertFalse(aj.isLimitsEnabled());
    }

    @Test
    public void setLimitEnabledSleep_3_testMerged_2() {
        TestCase.assertFalse(b1.isAtRest());
    }

    @Test
    public void setLimitEnabledSleep_4_testMerged_3() {
        TestCase.assertFalse(b2.isAtRest());
    }

    @Test
    public void setLimitEnabledSleep_6_testMerged_4() {
        b1.setAtRest(true);
        TestCase.assertTrue(b1.isAtRest());
    }

    @Test
    public void setLimitEnabledSleep_7_testMerged_5() {
        b2.setAtRest(true);
        TestCase.assertTrue(b2.isAtRest());
    }

    @Test
    public void setLimitsSameSleep_1_testMerged_1() {
        AngleJoint<Body> aj = new AngleJoint<Body>(b1, b2);
        TestCase.assertTrue(aj.isLimitsEnabled());
        double defaultLowerLimit = aj.getLowerLimit();
        double defaultUpperLimit = aj.getUpperLimit();
        TestCase.assertEquals(defaultLowerLimit, defaultUpperLimit);
        aj.setLimits(defaultLowerLimit);
        TestCase.assertEquals(defaultLowerLimit, aj.getLowerLimit());
        TestCase.assertEquals(defaultUpperLimit, aj.getUpperLimit());
        aj.setLimits(Math.PI);
        TestCase.assertEquals(Math.PI, aj.getLowerLimit());
        TestCase.assertEquals(Math.PI, aj.getUpperLimit());
        aj.setLowerLimit(-Math.PI);
        TestCase.assertEquals(-Math.PI, aj.getLowerLimit());
        aj.setUpperLimit(2 * Math.PI);
        TestCase.assertEquals(2 * Math.PI, aj.getUpperLimit());
        aj.setLimitsEnabled(false);
        aj.setLimits(-Math.PI);
        TestCase.assertFalse(aj.isLimitsEnabled());
        TestCase.assertEquals(-Math.PI, aj.getUpperLimit());
    }

    @Test
    public void setLimitsSameSleep_3_testMerged_2() {
        TestCase.assertFalse(b1.isAtRest());
    }

    @Test
    public void setLimitsSameSleep_4_testMerged_3() {
        TestCase.assertFalse(b2.isAtRest());
    }

    @Test
    public void setLimitsSameSleep_6_testMerged_4() {
        b1.setAtRest(true);
        TestCase.assertTrue(b1.isAtRest());
    }

    @Test
    public void setLimitsSameSleep_7_testMerged_5() {
        b2.setAtRest(true);
        TestCase.assertTrue(b2.isAtRest());
    }

    @Test
    public void setLimitsDifferentSleep_1_testMerged_1() {
        AngleJoint<Body> aj = new AngleJoint<Body>(b1, b2);
        TestCase.assertTrue(aj.isLimitsEnabled());
        double defaultLowerLimit = aj.getLowerLimit();
        double defaultUpperLimit = aj.getUpperLimit();
        TestCase.assertEquals(defaultLowerLimit, defaultUpperLimit);
        aj.setLimits(defaultLowerLimit, defaultUpperLimit);
        TestCase.assertEquals(defaultLowerLimit, aj.getLowerLimit());
        TestCase.assertEquals(defaultUpperLimit, aj.getUpperLimit());
        aj.setLimits(-Math.PI, Math.PI);
        TestCase.assertEquals(-Math.PI, aj.getLowerLimit());
        TestCase.assertEquals(Math.PI, aj.getUpperLimit());
        aj.setLowerLimit(-2 * Math.PI);
        TestCase.assertEquals(-2 * Math.PI, aj.getLowerLimit());
        aj.setUpperLimit(2 * Math.PI);
        TestCase.assertEquals(2 * Math.PI, aj.getUpperLimit());
        aj.setLimitsEnabled(false);
        aj.setLimits(Math.PI, 2 * Math.PI);
        TestCase.assertFalse(aj.isLimitsEnabled());
        TestCase.assertEquals(Math.PI, aj.getLowerLimit());
    }

    @Test
    public void setLimitsDifferentSleep_3_testMerged_2() {
        TestCase.assertFalse(b1.isAtRest());
    }

    @Test
    public void setLimitsDifferentSleep_4_testMerged_3() {
        TestCase.assertFalse(b2.isAtRest());
    }

    @Test
    public void setLimitsDifferentSleep_6_testMerged_4() {
        b1.setAtRest(true);
        TestCase.assertTrue(b1.isAtRest());
    }

    @Test
    public void setLimitsDifferentSleep_7_testMerged_5() {
        b2.setAtRest(true);
        TestCase.assertTrue(b2.isAtRest());
    }

    @Test
    public void setLowerLimitSleep_1_testMerged_1() {
        AngleJoint<Body> aj = new AngleJoint<Body>(b1, b2);
        TestCase.assertTrue(aj.isLimitsEnabled());
        double defaultLowerLimit = aj.getLowerLimit();
        double defaultUpperLimit = aj.getUpperLimit();
        aj.setLowerLimit(defaultLowerLimit);
        TestCase.assertEquals(defaultLowerLimit, aj.getLowerLimit());
        TestCase.assertEquals(defaultUpperLimit, aj.getUpperLimit());
        aj.setLowerLimit(-Math.PI);
        TestCase.assertEquals(-Math.PI, aj.getLowerLimit());
        aj.setLimitsEnabled(false);
        aj.setLowerLimit(-2 * Math.PI);
        TestCase.assertEquals(-2 * Math.PI, aj.getLowerLimit());
    }

    @Test
    public void setLowerLimitSleep_2_testMerged_2() {
        TestCase.assertFalse(b1.isAtRest());
    }

    @Test
    public void setLowerLimitSleep_3_testMerged_3() {
        TestCase.assertFalse(b2.isAtRest());
    }

    @Test
    public void setLowerLimitSleep_5_testMerged_4() {
        b1.setAtRest(true);
        TestCase.assertTrue(b1.isAtRest());
    }

    @Test
    public void setLowerLimitSleep_6_testMerged_5() {
        b2.setAtRest(true);
        TestCase.assertTrue(b2.isAtRest());
    }

    @Test
    public void setUpperLimitSleep_1_testMerged_1() {
        AngleJoint<Body> aj = new AngleJoint<Body>(b1, b2);
        TestCase.assertTrue(aj.isLimitsEnabled());
        double defaultLowerLimit = aj.getLowerLimit();
        double defaultUpperLimit = aj.getUpperLimit();
        aj.setUpperLimit(defaultUpperLimit);
        TestCase.assertEquals(defaultLowerLimit, aj.getLowerLimit());
        TestCase.assertEquals(defaultUpperLimit, aj.getUpperLimit());
        aj.setUpperLimit(Math.PI);
        TestCase.assertEquals(Math.PI, aj.getUpperLimit());
        aj.setLimitsEnabled(false);
        aj.setUpperLimit(2 * Math.PI);
        TestCase.assertEquals(2 * Math.PI, aj.getUpperLimit());
    }

    @Test
    public void setUpperLimitSleep_2_testMerged_2() {
        TestCase.assertFalse(b1.isAtRest());
    }

    @Test
    public void setUpperLimitSleep_3_testMerged_3() {
        TestCase.assertFalse(b2.isAtRest());
    }

    @Test
    public void setUpperLimitSleep_5_testMerged_4() {
        b1.setAtRest(true);
        TestCase.assertTrue(b1.isAtRest());
    }

    @Test
    public void setUpperLimitSleep_6_testMerged_5() {
        b2.setAtRest(true);
        TestCase.assertTrue(b2.isAtRest());
    }

    @Test
    public void setLimitsEnabledSameSleep_1_testMerged_1() {
        AngleJoint<Body> aj = new AngleJoint<Body>(b1, b2);
        TestCase.assertTrue(aj.isLimitsEnabled());
        double defaultLowerLimit = aj.getLowerLimit();
        double defaultUpperLimit = aj.getUpperLimit();
        TestCase.assertEquals(defaultLowerLimit, defaultUpperLimit);
        aj.setLimitsEnabled(defaultLowerLimit);
        TestCase.assertEquals(defaultLowerLimit, aj.getLowerLimit());
        TestCase.assertEquals(defaultUpperLimit, aj.getUpperLimit());
        aj.setLimitsEnabled(Math.PI);
        TestCase.assertEquals(Math.PI, aj.getLowerLimit());
        TestCase.assertEquals(Math.PI, aj.getUpperLimit());
    }

    @Test
    public void setLimitsEnabledSameSleep_3_testMerged_2() {
        TestCase.assertFalse(b1.isAtRest());
    }

    @Test
    public void setLimitsEnabledSameSleep_4_testMerged_3() {
        TestCase.assertFalse(b2.isAtRest());
    }

    @Test
    public void setLimitsEnabledSameSleep_6() {
        b1.setAtRest(true);
        TestCase.assertTrue(b1.isAtRest());
    }

    @Test
    public void setLimitsEnabledSameSleep_7() {
        b2.setAtRest(true);
        TestCase.assertTrue(b2.isAtRest());
    }

    @Test
    public void setLimitsEnabledDifferentSleep_1_testMerged_1() {
        AngleJoint<Body> aj = new AngleJoint<Body>(b1, b2);
        TestCase.assertTrue(aj.isLimitsEnabled());
        double defaultLowerLimit = aj.getLowerLimit();
        double defaultUpperLimit = aj.getUpperLimit();
        TestCase.assertEquals(defaultLowerLimit, defaultUpperLimit);
        aj.setLimitsEnabled(defaultLowerLimit, defaultUpperLimit);
        TestCase.assertEquals(defaultLowerLimit, aj.getLowerLimit());
        TestCase.assertEquals(defaultUpperLimit, aj.getUpperLimit());
        aj.setLimitsEnabled(-Math.PI, Math.PI);
        TestCase.assertEquals(-Math.PI, aj.getLowerLimit());
        TestCase.assertEquals(Math.PI, aj.getUpperLimit());
        aj.setLowerLimit(-2 * Math.PI);
        TestCase.assertEquals(-2 * Math.PI, aj.getLowerLimit());
        aj.setUpperLimit(2 * Math.PI);
        TestCase.assertEquals(2 * Math.PI, aj.getUpperLimit());
    }

    @Test
    public void setLimitsEnabledDifferentSleep_3_testMerged_2() {
        TestCase.assertFalse(b1.isAtRest());
    }

    @Test
    public void setLimitsEnabledDifferentSleep_4_testMerged_3() {
        TestCase.assertFalse(b2.isAtRest());
    }

    @Test
    public void setLimitsEnabledDifferentSleep_6() {
        b1.setAtRest(true);
        TestCase.assertTrue(b1.isAtRest());
    }

    @Test
    public void setLimitsEnabledDifferentSleep_7() {
        b2.setAtRest(true);
        TestCase.assertTrue(b2.isAtRest());
    }

    @Test
    public void shift_1() {
        Vector2 p1 = b1.getWorldCenter();
        TestCase.assertEquals(p1, b1.getWorldCenter());
    }

    @Test
    public void shift_2() {
        Vector2 p2 = b2.getWorldCenter();
        TestCase.assertEquals(p2, b2.getWorldCenter());
    }
}
