package org.dyn4j.dynamics.joint;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Vector2;
import org.junit.Test;
import junit.framework.TestCase;

public class DistanceJointTest_Purified extends BaseJointTest {

    @Test
    public void setSpringDampingRatio_1_testMerged_1() {
        DistanceJoint<Body> dj = new DistanceJoint<Body>(b1, b2, new Vector2(1.0, 2.0), new Vector2(-3.0, 0.5));
        dj.setSpringDampingRatio(0.0);
        TestCase.assertEquals(0.0, dj.getSpringDampingRatio());
        dj.setSpringDampingRatio(0.001);
        TestCase.assertEquals(0.001, dj.getSpringDampingRatio());
        dj.setSpringDampingRatio(1.0);
        TestCase.assertEquals(1.0, dj.getSpringDampingRatio());
        dj.setSpringDampingRatio(0.2);
        TestCase.assertEquals(0.2, dj.getSpringDampingRatio());
    }

    @Test
    public void setSpringDampingRatio_5_testMerged_2() {
        TestCase.assertTrue(this.b1.isAtRest());
    }

    @Test
    public void setSpringDampingRatio_6_testMerged_3() {
        TestCase.assertTrue(this.b2.isAtRest());
    }

    @Test
    public void setSpringDampingRatio_11() {
        TestCase.assertFalse(this.b1.isAtRest());
    }

    @Test
    public void setSpringDampingRatio_12() {
        TestCase.assertFalse(this.b2.isAtRest());
    }

    @Test
    public void setSpringFrequency_1_testMerged_1() {
        DistanceJoint<Body> dj = new DistanceJoint<Body>(b1, b2, new Vector2(1.0, 2.0), new Vector2(-3.0, 0.5));
        dj.setSpringFrequency(0.0);
        TestCase.assertEquals(0.0, dj.getSpringFrequency());
        dj.setSpringFrequency(0.001);
        TestCase.assertEquals(0.001, dj.getSpringFrequency());
        TestCase.assertEquals(AbstractJoint.SPRING_MODE_FREQUENCY, dj.getSpringMode());
        dj.setSpringFrequency(1.0);
        TestCase.assertEquals(1.0, dj.getSpringFrequency());
        dj.setSpringFrequency(29.0);
        TestCase.assertEquals(29.0, dj.getSpringFrequency());
    }

    @Test
    public void setSpringFrequency_6_testMerged_2() {
        TestCase.assertTrue(this.b1.isAtRest());
    }

    @Test
    public void setSpringFrequency_7_testMerged_3() {
        TestCase.assertTrue(this.b2.isAtRest());
    }

    @Test
    public void setSpringFrequency_8_testMerged_4() {
        TestCase.assertFalse(this.b1.isAtRest());
    }

    @Test
    public void setSpringFrequency_9_testMerged_5() {
        TestCase.assertFalse(this.b2.isAtRest());
    }

    @Test
    public void setSpringStiffness_1_testMerged_1() {
        DistanceJoint<Body> dj = new DistanceJoint<Body>(b1, b2, new Vector2(1.0, 2.0), new Vector2(-3.0, 0.5));
        dj.setSpringStiffness(0.0);
        TestCase.assertEquals(0.0, dj.getSpringStiffness());
        dj.setSpringStiffness(0.001);
        TestCase.assertEquals(0.001, dj.getSpringStiffness());
        dj.setSpringStiffness(1.0);
        TestCase.assertEquals(1.0, dj.getSpringStiffness());
        dj.setSpringStiffness(29.0);
        TestCase.assertEquals(29.0, dj.getSpringStiffness());
    }

    @Test
    public void setSpringStiffness_5_testMerged_2() {
        TestCase.assertTrue(this.b1.isAtRest());
    }

    @Test
    public void setSpringStiffness_6_testMerged_3() {
        TestCase.assertTrue(this.b2.isAtRest());
    }

    @Test
    public void setSpringStiffness_7_testMerged_4() {
        TestCase.assertFalse(this.b1.isAtRest());
    }

    @Test
    public void setSpringStiffness_8_testMerged_5() {
        TestCase.assertFalse(this.b2.isAtRest());
    }

    @Test
    public void setSpringMaximumForce_1_testMerged_1() {
        DistanceJoint<Body> dj = new DistanceJoint<Body>(b1, b2, new Vector2(1.0, 2.0), new Vector2(-3.0, 0.5));
        dj.setMaximumSpringForce(0.0);
        TestCase.assertEquals(0.0, dj.getMaximumSpringForce());
        dj.setMaximumSpringForce(0.001);
        TestCase.assertEquals(0.001, dj.getMaximumSpringForce());
        dj.setMaximumSpringForce(1.0);
        TestCase.assertEquals(1.0, dj.getMaximumSpringForce());
        dj.setMaximumSpringForce(1000);
        TestCase.assertEquals(1000.0, dj.getMaximumSpringForce());
    }

    @Test
    public void setSpringMaximumForce_5_testMerged_2() {
        TestCase.assertTrue(this.b1.isAtRest());
    }

    @Test
    public void setSpringMaximumForce_6_testMerged_3() {
        TestCase.assertTrue(this.b2.isAtRest());
    }

    @Test
    public void setSpringMaximumForce_11_testMerged_4() {
        TestCase.assertFalse(this.b1.isAtRest());
    }

    @Test
    public void setSpringMaximumForce_12_testMerged_5() {
        TestCase.assertFalse(this.b2.isAtRest());
    }

    @Test
    public void setDistanceAtRest_1_testMerged_1() {
        TestCase.assertFalse(b1.isAtRest());
    }

    @Test
    public void setDistanceAtRest_2_testMerged_2() {
        TestCase.assertFalse(b2.isAtRest());
    }

    @Test
    public void setDistanceAtRest_3_testMerged_3() {
        DistanceJoint<Body> dj = new DistanceJoint<Body>(b1, b2, new Vector2(1.0, 2.0), new Vector2(-3.0, 0.5));
        double distance = dj.getRestDistance();
        TestCase.assertEquals(distance, dj.getRestDistance());
        dj.setRestDistance(distance);
        dj.setRestDistance(10);
        TestCase.assertEquals(10.0, dj.getRestDistance());
    }

    @Test
    public void setDistanceAtRest_4() {
        b1.setAtRest(true);
        TestCase.assertTrue(b1.isAtRest());
    }

    @Test
    public void setDistanceAtRest_5() {
        b2.setAtRest(true);
        TestCase.assertTrue(b2.isAtRest());
    }

    @Test
    public void setLimitsEnabledSleep_1_testMerged_1() {
        DistanceJoint<Body> dj = new DistanceJoint<Body>(b1, b2, new Vector2(), new Vector2(0.0, 1.0));
        dj.setLowerLimitEnabled(true);
        dj.setUpperLimitEnabled(true);
        TestCase.assertTrue(dj.isUpperLimitEnabled());
        TestCase.assertTrue(dj.isLowerLimitEnabled());
        dj.setLimitsEnabled(false);
        TestCase.assertFalse(dj.isUpperLimitEnabled());
        TestCase.assertFalse(dj.isLowerLimitEnabled());
    }

    @Test
    public void setLimitsEnabledSleep_3_testMerged_2() {
        TestCase.assertFalse(b1.isAtRest());
    }

    @Test
    public void setLimitsEnabledSleep_4_testMerged_3() {
        TestCase.assertFalse(b2.isAtRest());
    }

    @Test
    public void setLimitsEnabledSleep_11_testMerged_4() {
        b1.setAtRest(true);
        TestCase.assertTrue(b1.isAtRest());
    }

    @Test
    public void setLimitsEnabledSleep_12_testMerged_5() {
        b2.setAtRest(true);
        TestCase.assertTrue(b2.isAtRest());
    }

    @Test
    public void setLimitsSameSleep_1_testMerged_1() {
        DistanceJoint<Body> dj = new DistanceJoint<Body>(b1, b2, new Vector2(), new Vector2(0.0, 1.0));
        dj.setLowerLimitEnabled(true);
        dj.setUpperLimitEnabled(true);
        TestCase.assertTrue(dj.isUpperLimitEnabled());
        TestCase.assertTrue(dj.isLowerLimitEnabled());
        double defaultLowerLimit = dj.getLowerLimit();
        double defaultUpperLimit = dj.getUpperLimit();
        TestCase.assertEquals(defaultLowerLimit, defaultUpperLimit);
        dj.setLimits(defaultLowerLimit, defaultUpperLimit);
        TestCase.assertEquals(defaultLowerLimit, dj.getLowerLimit());
        TestCase.assertEquals(defaultUpperLimit, dj.getUpperLimit());
        dj.setLimits(2.0);
        TestCase.assertEquals(2.0, dj.getLowerLimit());
        TestCase.assertEquals(2.0, dj.getUpperLimit());
        dj.setLowerLimit(0.0);
        TestCase.assertEquals(0.0, dj.getLowerLimit());
        dj.setUpperLimit(3.0);
        TestCase.assertEquals(3.0, dj.getUpperLimit());
        dj.setLimitsEnabled(false);
        dj.setLimits(1.0);
        TestCase.assertFalse(dj.isUpperLimitEnabled());
        TestCase.assertFalse(dj.isLowerLimitEnabled());
        TestCase.assertEquals(1.0, dj.getLowerLimit());
        TestCase.assertEquals(1.0, dj.getUpperLimit());
    }

    @Test
    public void setLimitsSameSleep_4_testMerged_2() {
        TestCase.assertFalse(b1.isAtRest());
    }

    @Test
    public void setLimitsSameSleep_5_testMerged_3() {
        TestCase.assertFalse(b2.isAtRest());
    }

    @Test
    public void setLimitsSameSleep_8_testMerged_4() {
        b1.setAtRest(true);
        TestCase.assertTrue(b1.isAtRest());
    }

    @Test
    public void setLimitsSameSleep_9_testMerged_5() {
        b2.setAtRest(true);
        TestCase.assertTrue(b2.isAtRest());
    }

    @Test
    public void setLimitsDifferentSleep_1_testMerged_1() {
        DistanceJoint<Body> dj = new DistanceJoint<Body>(b1, b2, new Vector2(), new Vector2(0.0, 1.0));
        dj.setLowerLimitEnabled(true);
        dj.setUpperLimitEnabled(true);
        TestCase.assertTrue(dj.isUpperLimitEnabled());
        TestCase.assertTrue(dj.isLowerLimitEnabled());
        double defaultLowerLimit = dj.getLowerLimit();
        double defaultUpperLimit = dj.getUpperLimit();
        TestCase.assertEquals(defaultLowerLimit, defaultUpperLimit);
        dj.setLimits(defaultLowerLimit, defaultUpperLimit);
        TestCase.assertEquals(defaultLowerLimit, dj.getLowerLimit());
        TestCase.assertEquals(defaultUpperLimit, dj.getUpperLimit());
        dj.setLimits(2.0, 3.0);
        TestCase.assertEquals(2.0, dj.getLowerLimit());
        TestCase.assertEquals(3.0, dj.getUpperLimit());
        dj.setLowerLimit(1.0);
        TestCase.assertEquals(1.0, dj.getLowerLimit());
        dj.setLimits(0.0, 3.0);
        TestCase.assertEquals(0.0, dj.getLowerLimit());
        dj.setUpperLimit(2.0);
        TestCase.assertEquals(2.0, dj.getUpperLimit());
        dj.setLimits(0.0, 1.0);
        TestCase.assertEquals(1.0, dj.getUpperLimit());
        dj.setLimitsEnabled(false);
        dj.setLimits(1.0, 2.0);
        TestCase.assertFalse(dj.isUpperLimitEnabled());
        TestCase.assertFalse(dj.isLowerLimitEnabled());
    }

    @Test
    public void setLimitsDifferentSleep_4_testMerged_2() {
        TestCase.assertFalse(b1.isAtRest());
    }

    @Test
    public void setLimitsDifferentSleep_5_testMerged_3() {
        TestCase.assertFalse(b2.isAtRest());
    }

    @Test
    public void setLimitsDifferentSleep_8_testMerged_4() {
        b1.setAtRest(true);
        TestCase.assertTrue(b1.isAtRest());
    }

    @Test
    public void setLimitsDifferentSleep_9_testMerged_5() {
        b2.setAtRest(true);
        TestCase.assertTrue(b2.isAtRest());
    }

    @Test
    public void setLowerLimitSleep_1_testMerged_1() {
        DistanceJoint<Body> dj = new DistanceJoint<Body>(b1, b2, new Vector2(), new Vector2(0.0, 1.0));
        dj.setLowerLimitEnabled(true);
        dj.setUpperLimitEnabled(true);
        TestCase.assertTrue(dj.isLowerLimitEnabled());
        double defaultLowerLimit = dj.getLowerLimit();
        double defaultUpperLimit = dj.getUpperLimit();
        dj.setLowerLimit(defaultLowerLimit);
        TestCase.assertEquals(defaultLowerLimit, dj.getLowerLimit());
        TestCase.assertEquals(defaultUpperLimit, dj.getUpperLimit());
        dj.setLowerLimit(0.5);
        TestCase.assertEquals(0.5, dj.getLowerLimit());
        dj.setLimitsEnabled(false);
        dj.setLowerLimit(0.2);
        TestCase.assertEquals(0.2, dj.getLowerLimit());
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
        DistanceJoint<Body> dj = new DistanceJoint<Body>(b1, b2, new Vector2(), new Vector2(0.0, 1.0));
        dj.setLowerLimitEnabled(true);
        dj.setUpperLimitEnabled(true);
        TestCase.assertTrue(dj.isUpperLimitEnabled());
        double defaultLowerLimit = dj.getLowerLimit();
        double defaultUpperLimit = dj.getUpperLimit();
        dj.setUpperLimit(defaultUpperLimit);
        TestCase.assertEquals(defaultLowerLimit, dj.getLowerLimit());
        TestCase.assertEquals(defaultUpperLimit, dj.getUpperLimit());
        dj.setUpperLimit(2.0);
        TestCase.assertEquals(2.0, dj.getUpperLimit());
        dj.setLimitsEnabled(false);
        dj.setUpperLimit(3.0);
        TestCase.assertEquals(3.0, dj.getUpperLimit());
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
        DistanceJoint<Body> dj = new DistanceJoint<Body>(b1, b2, new Vector2(), new Vector2(0.0, 1.0));
        dj.setLowerLimitEnabled(true);
        dj.setUpperLimitEnabled(true);
        TestCase.assertTrue(dj.isUpperLimitEnabled());
        TestCase.assertTrue(dj.isLowerLimitEnabled());
        double defaultLowerLimit = dj.getLowerLimit();
        double defaultUpperLimit = dj.getUpperLimit();
        TestCase.assertEquals(defaultLowerLimit, defaultUpperLimit);
        dj.setLimitsEnabled(defaultLowerLimit, defaultUpperLimit);
        TestCase.assertEquals(defaultLowerLimit, dj.getLowerLimit());
        TestCase.assertEquals(defaultUpperLimit, dj.getUpperLimit());
        dj.setLimitsEnabled(2.0);
        TestCase.assertEquals(2.0, dj.getLowerLimit());
        TestCase.assertEquals(2.0, dj.getUpperLimit());
        dj.setLimitsEnabled(false);
        dj.setLimitsEnabled(1.0);
        TestCase.assertEquals(1.0, dj.getLowerLimit());
        TestCase.assertEquals(1.0, dj.getUpperLimit());
    }

    @Test
    public void setLimitsEnabledSameSleep_4_testMerged_2() {
        TestCase.assertFalse(b1.isAtRest());
    }

    @Test
    public void setLimitsEnabledSameSleep_5_testMerged_3() {
        TestCase.assertFalse(b2.isAtRest());
    }

    @Test
    public void setLimitsEnabledSameSleep_8() {
        b1.setAtRest(true);
        TestCase.assertTrue(b1.isAtRest());
    }

    @Test
    public void setLimitsEnabledSameSleep_9() {
        b2.setAtRest(true);
        TestCase.assertTrue(b2.isAtRest());
    }

    @Test
    public void setLimitsEnabledDifferentSleep_1_testMerged_1() {
        DistanceJoint<Body> dj = new DistanceJoint<Body>(b1, b2, new Vector2(), new Vector2(0.0, 1.0));
        dj.setLowerLimitEnabled(true);
        dj.setUpperLimitEnabled(true);
        TestCase.assertTrue(dj.isUpperLimitEnabled());
        TestCase.assertTrue(dj.isLowerLimitEnabled());
        double defaultLowerLimit = dj.getLowerLimit();
        double defaultUpperLimit = dj.getUpperLimit();
        TestCase.assertEquals(defaultLowerLimit, defaultUpperLimit);
        dj.setLimitsEnabled(defaultLowerLimit, defaultUpperLimit);
        TestCase.assertEquals(defaultLowerLimit, dj.getLowerLimit());
        TestCase.assertEquals(defaultUpperLimit, dj.getUpperLimit());
        dj.setLimitsEnabled(0.0, 2.0);
        TestCase.assertEquals(0.0, dj.getLowerLimit());
        TestCase.assertEquals(2.0, dj.getUpperLimit());
        dj.setLowerLimit(0.5);
        TestCase.assertEquals(0.5, dj.getLowerLimit());
        dj.setUpperLimit(3.0);
        TestCase.assertEquals(3.0, dj.getUpperLimit());
        dj.setLimitsEnabled(false);
        dj.setLimitsEnabled(0.5, 4.0);
        TestCase.assertEquals(4.0, dj.getUpperLimit());
    }

    @Test
    public void setLimitsEnabledDifferentSleep_4_testMerged_2() {
        TestCase.assertFalse(b1.isAtRest());
    }

    @Test
    public void setLimitsEnabledDifferentSleep_5_testMerged_3() {
        TestCase.assertFalse(b2.isAtRest());
    }

    @Test
    public void setLimitsEnabledDifferentSleep_8() {
        b1.setAtRest(true);
        TestCase.assertTrue(b1.isAtRest());
    }

    @Test
    public void setLimitsEnabledDifferentSleep_9() {
        b2.setAtRest(true);
        TestCase.assertTrue(b2.isAtRest());
    }
}
