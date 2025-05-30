package org.dyn4j.dynamics.joint;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Vector2;
import org.junit.Test;
import junit.framework.TestCase;

public class PrismaticJointTest_Purified extends BaseJointTest {

    @Test
    public void setMotorMaximumForceSleep_1_testMerged_1() {
        PrismaticJoint<Body> pj = new PrismaticJoint<Body>(b1, b2, new Vector2(), new Vector2(0.0, 1.0));
        TestCase.assertFalse(pj.isMotorEnabled());
        TestCase.assertEquals(1000.0, pj.getMaximumMotorForce());
        TestCase.assertFalse(pj.isMaximumMotorForceEnabled());
        pj.setMotorEnabled(true);
        pj.setMaximumMotorForce(1000.0);
        pj.setMaximumMotorForce(2.0);
        TestCase.assertEquals(2.0, pj.getMaximumMotorForce());
        pj.setMaximumMotorForceEnabled(true);
        TestCase.assertTrue(pj.isMaximumMotorForceEnabled());
        pj.setMaximumMotorForce(4.0);
        TestCase.assertEquals(4.0, pj.getMaximumMotorForce());
        pj.setMotorEnabled(false);
        pj.setMaximumMotorForce(1.0);
        TestCase.assertEquals(1.0, pj.getMaximumMotorForce());
    }

    @Test
    public void setMotorMaximumForceSleep_4_testMerged_2() {
        TestCase.assertFalse(b1.isAtRest());
    }

    @Test
    public void setMotorMaximumForceSleep_5_testMerged_3() {
        TestCase.assertFalse(b2.isAtRest());
    }

    @Test
    public void setMotorMaximumForceSleep_7_testMerged_4() {
        b1.setAtRest(true);
        TestCase.assertTrue(b1.isAtRest());
    }

    @Test
    public void setMotorMaximumForceSleep_8_testMerged_5() {
        b2.setAtRest(true);
        TestCase.assertTrue(b2.isAtRest());
    }

    @Test
    public void setMotorEnabledSleep_1_testMerged_1() {
        PrismaticJoint<Body> pj = new PrismaticJoint<Body>(b1, b2, new Vector2(), new Vector2(0.0, 1.0));
        TestCase.assertFalse(pj.isMotorEnabled());
        pj.setMotorEnabled(false);
        pj.setMotorEnabled(true);
        TestCase.assertTrue(pj.isMotorEnabled());
    }

    @Test
    public void setMotorEnabledSleep_2_testMerged_2() {
        TestCase.assertFalse(b1.isAtRest());
    }

    @Test
    public void setMotorEnabledSleep_3_testMerged_3() {
        TestCase.assertFalse(b2.isAtRest());
    }

    @Test
    public void setMotorEnabledSleep_5_testMerged_4() {
        b1.setAtRest(true);
        TestCase.assertTrue(b1.isAtRest());
    }

    @Test
    public void setMotorEnabledSleep_6_testMerged_5() {
        b2.setAtRest(true);
        TestCase.assertTrue(b2.isAtRest());
    }

    @Test
    public void setMotorSpeedSleep_1_testMerged_1() {
        PrismaticJoint<Body> pj = new PrismaticJoint<Body>(b1, b2, new Vector2(), new Vector2(0.0, 1.0));
        TestCase.assertFalse(pj.isMotorEnabled());
        TestCase.assertEquals(0.0, pj.getMotorSpeed());
        pj.setMotorEnabled(true);
        pj.setMotorSpeed(0.0);
        pj.setMotorSpeed(2.0);
        TestCase.assertEquals(2.0, pj.getMotorSpeed());
        pj.setMotorEnabled(false);
        pj.setMotorSpeed(-1.0);
        TestCase.assertEquals(-1.0, pj.getMotorSpeed());
    }

    @Test
    public void setMotorSpeedSleep_3_testMerged_2() {
        TestCase.assertFalse(b1.isAtRest());
    }

    @Test
    public void setMotorSpeedSleep_4_testMerged_3() {
        TestCase.assertFalse(b2.isAtRest());
    }

    @Test
    public void setMotorSpeedSleep_6_testMerged_4() {
        b1.setAtRest(true);
        TestCase.assertTrue(b1.isAtRest());
    }

    @Test
    public void setMotorSpeedSleep_7_testMerged_5() {
        b2.setAtRest(true);
        TestCase.assertTrue(b2.isAtRest());
    }

    @Test
    public void setLimitsEnabledSleep_1_testMerged_1() {
        PrismaticJoint<Body> pj = new PrismaticJoint<Body>(b1, b2, new Vector2(), new Vector2(0.0, 1.0));
        pj.setLowerLimitEnabled(true);
        pj.setUpperLimitEnabled(true);
        TestCase.assertTrue(pj.isUpperLimitEnabled());
        TestCase.assertTrue(pj.isLowerLimitEnabled());
        pj.setLimitsEnabled(false);
        TestCase.assertFalse(pj.isUpperLimitEnabled());
        TestCase.assertFalse(pj.isLowerLimitEnabled());
    }

    @Test
    public void setLimitsEnabledSleep_5_testMerged_2() {
        TestCase.assertFalse(b1.isAtRest());
    }

    @Test
    public void setLimitsEnabledSleep_6_testMerged_3() {
        TestCase.assertFalse(b2.isAtRest());
    }

    @Test
    public void setLimitsEnabledSleep_9_testMerged_4() {
        b1.setAtRest(true);
        TestCase.assertTrue(b1.isAtRest());
    }

    @Test
    public void setLimitsEnabledSleep_10_testMerged_5() {
        b2.setAtRest(true);
        TestCase.assertTrue(b2.isAtRest());
    }

    @Test
    public void setLimitsSameSleep_1_testMerged_1() {
        PrismaticJoint<Body> pj = new PrismaticJoint<Body>(b1, b2, new Vector2(), new Vector2(0.0, 1.0));
        pj.setLowerLimitEnabled(true);
        pj.setUpperLimitEnabled(true);
        TestCase.assertTrue(pj.isUpperLimitEnabled());
        TestCase.assertTrue(pj.isLowerLimitEnabled());
        double defaultLowerLimit = pj.getLowerLimit();
        double defaultUpperLimit = pj.getUpperLimit();
        TestCase.assertEquals(defaultLowerLimit, defaultUpperLimit);
        pj.setLimits(defaultLowerLimit, defaultUpperLimit);
        TestCase.assertEquals(defaultLowerLimit, pj.getLowerLimit());
        TestCase.assertEquals(defaultUpperLimit, pj.getUpperLimit());
        pj.setLimits(2.0);
        TestCase.assertEquals(2.0, pj.getLowerLimit());
        TestCase.assertEquals(2.0, pj.getUpperLimit());
        pj.setLowerLimit(0.0);
        TestCase.assertEquals(0.0, pj.getLowerLimit());
        pj.setUpperLimit(3.0);
        TestCase.assertEquals(3.0, pj.getUpperLimit());
        pj.setLimitsEnabled(false);
        pj.setLimits(1.0);
        TestCase.assertFalse(pj.isUpperLimitEnabled());
        TestCase.assertFalse(pj.isLowerLimitEnabled());
        TestCase.assertEquals(1.0, pj.getLowerLimit());
        TestCase.assertEquals(1.0, pj.getUpperLimit());
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
        PrismaticJoint<Body> pj = new PrismaticJoint<Body>(b1, b2, new Vector2(), new Vector2(0.0, 1.0));
        pj.setLowerLimitEnabled(true);
        pj.setUpperLimitEnabled(true);
        TestCase.assertTrue(pj.isUpperLimitEnabled());
        TestCase.assertTrue(pj.isLowerLimitEnabled());
        double defaultLowerLimit = pj.getLowerLimit();
        double defaultUpperLimit = pj.getUpperLimit();
        TestCase.assertEquals(defaultLowerLimit, defaultUpperLimit);
        pj.setLimits(defaultLowerLimit, defaultUpperLimit);
        TestCase.assertEquals(defaultLowerLimit, pj.getLowerLimit());
        TestCase.assertEquals(defaultUpperLimit, pj.getUpperLimit());
        pj.setLimits(2.0, 3.0);
        TestCase.assertEquals(2.0, pj.getLowerLimit());
        TestCase.assertEquals(3.0, pj.getUpperLimit());
        pj.setLowerLimit(1.0);
        TestCase.assertEquals(1.0, pj.getLowerLimit());
        pj.setLimits(0.0, 3.0);
        TestCase.assertEquals(0.0, pj.getLowerLimit());
        pj.setUpperLimit(2.0);
        TestCase.assertEquals(2.0, pj.getUpperLimit());
        pj.setLimits(0.0, 1.0);
        TestCase.assertEquals(1.0, pj.getUpperLimit());
        pj.setLimitsEnabled(false);
        pj.setLimits(1.0, 2.0);
        TestCase.assertFalse(pj.isUpperLimitEnabled());
        TestCase.assertFalse(pj.isLowerLimitEnabled());
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
        PrismaticJoint<Body> pj = new PrismaticJoint<Body>(b1, b2, new Vector2(), new Vector2(0.0, 1.0));
        pj.setLowerLimitEnabled(true);
        pj.setUpperLimitEnabled(true);
        TestCase.assertTrue(pj.isLowerLimitEnabled());
        double defaultLowerLimit = pj.getLowerLimit();
        double defaultUpperLimit = pj.getUpperLimit();
        pj.setLowerLimit(defaultLowerLimit);
        TestCase.assertEquals(defaultLowerLimit, pj.getLowerLimit());
        TestCase.assertEquals(defaultUpperLimit, pj.getUpperLimit());
        pj.setLowerLimit(-0.5);
        TestCase.assertEquals(-0.5, pj.getLowerLimit());
        pj.setLimitsEnabled(false);
        pj.setLowerLimit(-0.2);
        TestCase.assertEquals(-0.2, pj.getLowerLimit());
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
        PrismaticJoint<Body> pj = new PrismaticJoint<Body>(b1, b2, new Vector2(), new Vector2(0.0, 1.0));
        pj.setLowerLimitEnabled(true);
        pj.setUpperLimitEnabled(true);
        TestCase.assertTrue(pj.isUpperLimitEnabled());
        double defaultLowerLimit = pj.getLowerLimit();
        double defaultUpperLimit = pj.getUpperLimit();
        pj.setUpperLimit(defaultUpperLimit);
        TestCase.assertEquals(defaultLowerLimit, pj.getLowerLimit());
        TestCase.assertEquals(defaultUpperLimit, pj.getUpperLimit());
        pj.setUpperLimit(2.0);
        TestCase.assertEquals(2.0, pj.getUpperLimit());
        pj.setLimitsEnabled(false);
        pj.setUpperLimit(3.0);
        TestCase.assertEquals(3.0, pj.getUpperLimit());
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
        PrismaticJoint<Body> pj = new PrismaticJoint<Body>(b1, b2, new Vector2(), new Vector2(0.0, 1.0));
        pj.setLowerLimitEnabled(true);
        pj.setUpperLimitEnabled(true);
        TestCase.assertTrue(pj.isUpperLimitEnabled());
        TestCase.assertTrue(pj.isLowerLimitEnabled());
        double defaultLowerLimit = pj.getLowerLimit();
        double defaultUpperLimit = pj.getUpperLimit();
        TestCase.assertEquals(defaultLowerLimit, defaultUpperLimit);
        pj.setLimitsEnabled(defaultLowerLimit, defaultUpperLimit);
        TestCase.assertEquals(defaultLowerLimit, pj.getLowerLimit());
        TestCase.assertEquals(defaultUpperLimit, pj.getUpperLimit());
        pj.setLimitsEnabled(2.0);
        TestCase.assertEquals(2.0, pj.getLowerLimit());
        TestCase.assertEquals(2.0, pj.getUpperLimit());
        pj.setLimitsEnabled(false);
        pj.setLimitsEnabled(1.0);
        TestCase.assertEquals(1.0, pj.getLowerLimit());
        TestCase.assertEquals(1.0, pj.getUpperLimit());
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
        PrismaticJoint<Body> pj = new PrismaticJoint<Body>(b1, b2, new Vector2(), new Vector2(0.0, 1.0));
        pj.setLowerLimitEnabled(true);
        pj.setUpperLimitEnabled(true);
        TestCase.assertTrue(pj.isUpperLimitEnabled());
        TestCase.assertTrue(pj.isLowerLimitEnabled());
        double defaultLowerLimit = pj.getLowerLimit();
        double defaultUpperLimit = pj.getUpperLimit();
        TestCase.assertEquals(defaultLowerLimit, defaultUpperLimit);
        pj.setLimitsEnabled(defaultLowerLimit, defaultUpperLimit);
        TestCase.assertEquals(defaultLowerLimit, pj.getLowerLimit());
        TestCase.assertEquals(defaultUpperLimit, pj.getUpperLimit());
        pj.setLimitsEnabled(0.0, 2.0);
        TestCase.assertEquals(0.0, pj.getLowerLimit());
        TestCase.assertEquals(2.0, pj.getUpperLimit());
        pj.setLowerLimit(0.5);
        TestCase.assertEquals(0.5, pj.getLowerLimit());
        pj.setUpperLimit(3.0);
        TestCase.assertEquals(3.0, pj.getUpperLimit());
        pj.setLimitsEnabled(false);
        pj.setLimitsEnabled(0.5, 4.0);
        TestCase.assertEquals(4.0, pj.getUpperLimit());
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

    @Test
    public void setSpringDampingRatio_1_testMerged_1() {
        PrismaticJoint<Body> pj = new PrismaticJoint<Body>(b1, b2, new Vector2(), new Vector2(0.0, 1.0));
        pj.setSpringDampingRatio(0.0);
        TestCase.assertEquals(0.0, pj.getSpringDampingRatio());
        pj.setSpringDampingRatio(0.001);
        TestCase.assertEquals(0.001, pj.getSpringDampingRatio());
        pj.setSpringDampingRatio(1.0);
        TestCase.assertEquals(1.0, pj.getSpringDampingRatio());
        pj.setSpringDampingRatio(0.2);
        TestCase.assertEquals(0.2, pj.getSpringDampingRatio());
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
        PrismaticJoint<Body> pj = new PrismaticJoint<Body>(b1, b2, new Vector2(), new Vector2(0.0, 1.0));
        pj.setSpringFrequency(0.0);
        TestCase.assertEquals(0.0, pj.getSpringFrequency());
        pj.setSpringFrequency(0.001);
        TestCase.assertEquals(0.001, pj.getSpringFrequency());
        TestCase.assertEquals(AbstractJoint.SPRING_MODE_FREQUENCY, pj.getSpringMode());
        pj.setSpringFrequency(1.0);
        TestCase.assertEquals(1.0, pj.getSpringFrequency());
        pj.setSpringFrequency(29.0);
        TestCase.assertEquals(29.0, pj.getSpringFrequency());
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
        PrismaticJoint<Body> pj = new PrismaticJoint<Body>(b1, b2, new Vector2(), new Vector2(0.0, 1.0));
        pj.setSpringStiffness(0.0);
        TestCase.assertEquals(0.0, pj.getSpringStiffness());
        pj.setSpringStiffness(0.001);
        TestCase.assertEquals(0.001, pj.getSpringStiffness());
        pj.setSpringStiffness(1.0);
        TestCase.assertEquals(1.0, pj.getSpringStiffness());
        pj.setSpringStiffness(29.0);
        TestCase.assertEquals(29.0, pj.getSpringStiffness());
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
        PrismaticJoint<Body> pj = new PrismaticJoint<Body>(b1, b2, new Vector2(), new Vector2(0.0, 1.0));
        pj.setMaximumSpringForce(0.0);
        TestCase.assertEquals(0.0, pj.getMaximumSpringForce());
        pj.setMaximumSpringForce(0.001);
        TestCase.assertEquals(0.001, pj.getMaximumSpringForce());
        pj.setMaximumSpringForce(1.0);
        TestCase.assertEquals(1.0, pj.getMaximumSpringForce());
        pj.setMaximumSpringForce(1000);
        TestCase.assertEquals(1000.0, pj.getMaximumSpringForce());
        pj.setSpringEnabled(false);
        pj.setMaximumSpringForceEnabled(false);
        TestCase.assertFalse(pj.isMaximumSpringForceEnabled());
        pj.setMaximumSpringForce(0.5);
        pj.setMaximumSpringForceEnabled(true);
        TestCase.assertTrue(pj.isMaximumSpringForceEnabled());
    }

    @Test
    public void setSpringMaximumForce_6_testMerged_2() {
        TestCase.assertTrue(this.b1.isAtRest());
    }

    @Test
    public void setSpringMaximumForce_7_testMerged_3() {
        TestCase.assertTrue(this.b2.isAtRest());
    }

    @Test
    public void setSpringMaximumForce_13_testMerged_4() {
        TestCase.assertFalse(this.b1.isAtRest());
    }

    @Test
    public void setSpringMaximumForce_14_testMerged_5() {
        TestCase.assertFalse(this.b2.isAtRest());
    }

    @Test
    public void setSpringRestOffsetAtRest_1_testMerged_1() {
        TestCase.assertFalse(b1.isAtRest());
    }

    @Test
    public void setSpringRestOffsetAtRest_2_testMerged_2() {
        TestCase.assertFalse(b2.isAtRest());
    }

    @Test
    public void setSpringRestOffsetAtRest_3_testMerged_3() {
        PrismaticJoint<Body> pj = new PrismaticJoint<Body>(b1, b2, new Vector2(), new Vector2(0.0, 1.0));
        double distance = pj.getSpringRestOffset();
        pj.setSpringEnabled(true);
        TestCase.assertEquals(distance, pj.getSpringRestOffset());
        pj.setSpringRestOffset(distance);
        pj.setSpringRestOffset(10);
        TestCase.assertEquals(10.0, pj.getSpringRestOffset());
    }

    @Test
    public void setSpringRestOffsetAtRest_4() {
        b1.setAtRest(true);
        TestCase.assertTrue(b1.isAtRest());
    }

    @Test
    public void setSpringRestOffsetAtRest_5() {
        b2.setAtRest(true);
        TestCase.assertTrue(b2.isAtRest());
    }
}
