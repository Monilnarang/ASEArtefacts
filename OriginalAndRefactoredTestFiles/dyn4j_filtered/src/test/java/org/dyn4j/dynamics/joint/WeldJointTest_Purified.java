package org.dyn4j.dynamics.joint;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Vector2;
import org.junit.Test;
import junit.framework.TestCase;

public class WeldJointTest_Purified extends BaseJointTest {

    @Test
    public void setSpringDampingRatio_1_testMerged_1() {
        WeldJoint<Body> wj = new WeldJoint<Body>(b1, b2, new Vector2());
        wj.setSpringDampingRatio(0.0);
        TestCase.assertEquals(0.0, wj.getSpringDampingRatio());
        wj.setSpringDampingRatio(0.001);
        TestCase.assertEquals(0.001, wj.getSpringDampingRatio());
        wj.setSpringDampingRatio(1.0);
        TestCase.assertEquals(1.0, wj.getSpringDampingRatio());
        wj.setSpringDampingRatio(0.2);
        TestCase.assertEquals(0.2, wj.getSpringDampingRatio());
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
        WeldJoint<Body> wj = new WeldJoint<Body>(b1, b2, new Vector2());
        wj.setSpringFrequency(0.0);
        TestCase.assertEquals(0.0, wj.getSpringFrequency());
        wj.setSpringFrequency(0.001);
        TestCase.assertEquals(0.001, wj.getSpringFrequency());
        TestCase.assertEquals(AbstractJoint.SPRING_MODE_FREQUENCY, wj.getSpringMode());
        wj.setSpringFrequency(1.0);
        TestCase.assertEquals(1.0, wj.getSpringFrequency());
        wj.setSpringFrequency(29.0);
        TestCase.assertEquals(29.0, wj.getSpringFrequency());
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
        WeldJoint<Body> wj = new WeldJoint<Body>(b1, b2, new Vector2());
        wj.setSpringStiffness(0.0);
        TestCase.assertEquals(0.0, wj.getSpringStiffness());
        wj.setSpringStiffness(0.001);
        TestCase.assertEquals(0.001, wj.getSpringStiffness());
        wj.setSpringStiffness(1.0);
        TestCase.assertEquals(1.0, wj.getSpringStiffness());
        wj.setSpringStiffness(29.0);
        TestCase.assertEquals(29.0, wj.getSpringStiffness());
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
        WeldJoint<Body> wj = new WeldJoint<Body>(b1, b2, new Vector2());
        wj.setMaximumSpringTorque(0.0);
        TestCase.assertEquals(0.0, wj.getMaximumSpringTorque());
        wj.setMaximumSpringTorque(0.001);
        TestCase.assertEquals(0.001, wj.getMaximumSpringTorque());
        wj.setMaximumSpringTorque(1.0);
        TestCase.assertEquals(1.0, wj.getMaximumSpringTorque());
        wj.setMaximumSpringTorque(1000);
        TestCase.assertEquals(1000.0, wj.getMaximumSpringTorque());
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
    public void setLimitsReferenceAngleSleep_1_testMerged_1() {
        WeldJoint<Body> wj = new WeldJoint<Body>(b1, b2, new Vector2());
        TestCase.assertFalse(wj.isLimitsEnabled());
        wj.setLimitsEnabled(true);
        TestCase.assertTrue(wj.isLimitsEnabled());
    }

    @Test
    public void setLimitsReferenceAngleSleep_3_testMerged_2() {
        TestCase.assertFalse(b1.isAtRest());
    }

    @Test
    public void setLimitsReferenceAngleSleep_4_testMerged_3() {
        TestCase.assertFalse(b2.isAtRest());
    }

    @Test
    public void setLimitsReferenceAngleSleep_6() {
        b1.setAtRest(true);
        TestCase.assertTrue(b1.isAtRest());
    }

    @Test
    public void setLimitsReferenceAngleSleep_7() {
        b2.setAtRest(true);
        TestCase.assertTrue(b2.isAtRest());
    }

    @Test
    public void setLimitEnabledSleep_1_testMerged_1() {
        WeldJoint<Body> wj = new WeldJoint<Body>(b1, b2, new Vector2());
        TestCase.assertFalse(wj.isLimitsEnabled());
        wj.setLimitsEnabled(false);
        wj.setLimitsEnabled(true);
        TestCase.assertTrue(wj.isLimitsEnabled());
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
        WeldJoint<Body> wj = new WeldJoint<Body>(b1, b2, new Vector2());
        TestCase.assertFalse(wj.isLimitsEnabled());
        double defaultLowerLimit = wj.getLowerLimit();
        double defaultUpperLimit = wj.getUpperLimit();
        TestCase.assertEquals(defaultLowerLimit, defaultUpperLimit);
        wj.setLimitsEnabled(true);
        wj.setLimits(defaultLowerLimit);
        TestCase.assertTrue(wj.isLimitsEnabled());
        TestCase.assertEquals(defaultLowerLimit, wj.getLowerLimit());
        TestCase.assertEquals(defaultUpperLimit, wj.getUpperLimit());
        wj.setLimits(Math.PI);
        TestCase.assertEquals(Math.PI, wj.getLowerLimit());
        TestCase.assertEquals(Math.PI, wj.getUpperLimit());
        wj.setLowerLimit(-Math.PI);
        TestCase.assertEquals(-Math.PI, wj.getLowerLimit());
        wj.setUpperLimit(2 * Math.PI);
        TestCase.assertEquals(2 * Math.PI, wj.getUpperLimit());
        wj.setLimitsEnabled(false);
        wj.setLimits(-Math.PI);
        TestCase.assertEquals(-Math.PI, wj.getUpperLimit());
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
        WeldJoint<Body> wj = new WeldJoint<Body>(b1, b2, new Vector2());
        TestCase.assertFalse(wj.isLimitsEnabled());
        double defaultLowerLimit = wj.getLowerLimit();
        double defaultUpperLimit = wj.getUpperLimit();
        TestCase.assertEquals(defaultLowerLimit, defaultUpperLimit);
        wj.setLimitsEnabled(true);
        wj.setLimits(defaultLowerLimit, defaultUpperLimit);
        TestCase.assertTrue(wj.isLimitsEnabled());
        TestCase.assertEquals(defaultLowerLimit, wj.getLowerLimit());
        TestCase.assertEquals(defaultUpperLimit, wj.getUpperLimit());
        wj.setLimits(-Math.PI, Math.PI);
        TestCase.assertEquals(-Math.PI, wj.getLowerLimit());
        TestCase.assertEquals(Math.PI, wj.getUpperLimit());
        wj.setLowerLimit(-2 * Math.PI);
        TestCase.assertEquals(-2 * Math.PI, wj.getLowerLimit());
        wj.setUpperLimit(2 * Math.PI);
        TestCase.assertEquals(2 * Math.PI, wj.getUpperLimit());
        wj.setLimitsEnabled(false);
        wj.setLimits(Math.PI, 2 * Math.PI);
        TestCase.assertEquals(Math.PI, wj.getLowerLimit());
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
        WeldJoint<Body> wj = new WeldJoint<Body>(b1, b2, new Vector2());
        TestCase.assertFalse(wj.isLimitsEnabled());
        double defaultLowerLimit = wj.getLowerLimit();
        double defaultUpperLimit = wj.getUpperLimit();
        wj.setLimitsEnabled(true);
        wj.setLowerLimit(defaultLowerLimit);
        TestCase.assertTrue(wj.isLimitsEnabled());
        TestCase.assertEquals(defaultLowerLimit, wj.getLowerLimit());
        TestCase.assertEquals(defaultUpperLimit, wj.getUpperLimit());
        wj.setLowerLimit(-Math.PI);
        TestCase.assertEquals(-Math.PI, wj.getLowerLimit());
        wj.setLimitsEnabled(false);
        wj.setLowerLimit(-2 * Math.PI);
        TestCase.assertEquals(-2 * Math.PI, wj.getLowerLimit());
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
        WeldJoint<Body> wj = new WeldJoint<Body>(b1, b2, new Vector2());
        TestCase.assertFalse(wj.isLimitsEnabled());
        double defaultLowerLimit = wj.getLowerLimit();
        double defaultUpperLimit = wj.getUpperLimit();
        wj.setLimitsEnabled(true);
        wj.setUpperLimit(defaultUpperLimit);
        TestCase.assertTrue(wj.isLimitsEnabled());
        TestCase.assertEquals(defaultLowerLimit, wj.getLowerLimit());
        TestCase.assertEquals(defaultUpperLimit, wj.getUpperLimit());
        wj.setUpperLimit(Math.PI);
        TestCase.assertEquals(Math.PI, wj.getUpperLimit());
        wj.setLimitsEnabled(false);
        wj.setUpperLimit(2 * Math.PI);
        TestCase.assertEquals(2 * Math.PI, wj.getUpperLimit());
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
        WeldJoint<Body> wj = new WeldJoint<Body>(b1, b2, new Vector2());
        TestCase.assertFalse(wj.isLimitsEnabled());
        double defaultLowerLimit = wj.getLowerLimit();
        double defaultUpperLimit = wj.getUpperLimit();
        TestCase.assertEquals(defaultLowerLimit, defaultUpperLimit);
        wj.setLimitsEnabled(true);
        wj.setLimitsEnabled(defaultLowerLimit);
        TestCase.assertTrue(wj.isLimitsEnabled());
        TestCase.assertEquals(defaultLowerLimit, wj.getLowerLimit());
        TestCase.assertEquals(defaultUpperLimit, wj.getUpperLimit());
        wj.setLimitsEnabled(Math.PI);
        TestCase.assertEquals(Math.PI, wj.getLowerLimit());
        TestCase.assertEquals(Math.PI, wj.getUpperLimit());
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
        WeldJoint<Body> wj = new WeldJoint<Body>(b1, b2, new Vector2());
        TestCase.assertFalse(wj.isLimitsEnabled());
        double defaultLowerLimit = wj.getLowerLimit();
        double defaultUpperLimit = wj.getUpperLimit();
        TestCase.assertEquals(defaultLowerLimit, defaultUpperLimit);
        wj.setLimitsEnabled(true);
        wj.setLimitsEnabled(defaultLowerLimit, defaultUpperLimit);
        TestCase.assertTrue(wj.isLimitsEnabled());
        TestCase.assertEquals(defaultLowerLimit, wj.getLowerLimit());
        TestCase.assertEquals(defaultUpperLimit, wj.getUpperLimit());
        wj.setLimitsEnabled(-Math.PI, Math.PI);
        TestCase.assertEquals(-Math.PI, wj.getLowerLimit());
        TestCase.assertEquals(Math.PI, wj.getUpperLimit());
        wj.setLowerLimit(-2 * Math.PI);
        TestCase.assertEquals(-2 * Math.PI, wj.getLowerLimit());
        wj.setUpperLimit(2 * Math.PI);
        TestCase.assertEquals(2 * Math.PI, wj.getUpperLimit());
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
    public void setLimitsEnabledSleep_1_testMerged_1() {
        WeldJoint<Body> wj = new WeldJoint<Body>(b1, b2, new Vector2());
        TestCase.assertFalse(wj.isLimitsEnabled());
        wj.setLimitsEnabled(true);
        wj.setLimitsEnabled(false);
        TestCase.assertTrue(wj.isLimitsEnabled());
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
    public void setLimitsEnabledSleep_6_testMerged_4() {
        b1.setAtRest(true);
        TestCase.assertTrue(b1.isAtRest());
    }

    @Test
    public void setLimitsEnabledSleep_7_testMerged_5() {
        b2.setAtRest(true);
        TestCase.assertTrue(b2.isAtRest());
    }
}
