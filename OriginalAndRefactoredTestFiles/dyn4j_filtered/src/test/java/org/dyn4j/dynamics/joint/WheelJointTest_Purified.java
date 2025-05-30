package org.dyn4j.dynamics.joint;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Vector2;
import org.junit.Test;
import junit.framework.TestCase;

public class WheelJointTest_Purified extends BaseJointTest {

    @Test
    public void setSpringDampingRatio_1_testMerged_1() {
        WheelJoint<Body> wj = new WheelJoint<Body>(b1, b2, new Vector2(1.0, 2.0), new Vector2(-3.0, 0.5));
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
        WheelJoint<Body> wj = new WheelJoint<Body>(b1, b2, new Vector2(1.0, 2.0), new Vector2(-3.0, 0.5));
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
        WheelJoint<Body> wj = new WheelJoint<Body>(b1, b2, new Vector2(1.0, 2.0), new Vector2(-3.0, 0.5));
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
        WheelJoint<Body> wj = new WheelJoint<Body>(b1, b2, new Vector2(1.0, 2.0), new Vector2(-3.0, 0.5));
        wj.setMaximumSpringForce(0.0);
        TestCase.assertEquals(0.0, wj.getMaximumSpringForce());
        wj.setMaximumSpringForce(0.001);
        TestCase.assertEquals(0.001, wj.getMaximumSpringForce());
        wj.setMaximumSpringForce(1.0);
        TestCase.assertEquals(1.0, wj.getMaximumSpringForce());
        wj.setMaximumSpringForce(1000);
        TestCase.assertEquals(1000.0, wj.getMaximumSpringForce());
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
    public void setSpringRestOffsetAtRest_1_testMerged_1() {
        TestCase.assertFalse(b1.isAtRest());
    }

    @Test
    public void setSpringRestOffsetAtRest_2_testMerged_2() {
        TestCase.assertFalse(b2.isAtRest());
    }

    @Test
    public void setSpringRestOffsetAtRest_3_testMerged_3() {
        WheelJoint<Body> wj = new WheelJoint<Body>(b1, b2, new Vector2(1.0, 2.0), new Vector2(-3.0, 0.5));
        double distance = wj.getSpringRestOffset();
        TestCase.assertEquals(distance, wj.getSpringRestOffset());
        wj.setSpringRestOffset(distance);
        wj.setSpringRestOffset(10);
        TestCase.assertEquals(10.0, wj.getSpringRestOffset());
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

    @Test
    public void setMaximumMotorTorqueSleep_1_testMerged_1() {
        WheelJoint<Body> wj = new WheelJoint<Body>(b1, b2, new Vector2(1.0, 2.0), new Vector2(-3.0, 0.5));
        TestCase.assertFalse(wj.isMotorEnabled());
        TestCase.assertEquals(1000.0, wj.getMaximumMotorTorque());
        wj.setMotorEnabled(true);
        wj.setMaximumMotorTorque(1000.0);
        wj.setMaximumMotorTorque(2.0);
        TestCase.assertEquals(2.0, wj.getMaximumMotorTorque());
        wj.setMotorEnabled(false);
        wj.setMaximumMotorTorque(1.0);
        TestCase.assertEquals(1.0, wj.getMaximumMotorTorque());
    }

    @Test
    public void setMaximumMotorTorqueSleep_3_testMerged_2() {
        TestCase.assertFalse(b1.isAtRest());
    }

    @Test
    public void setMaximumMotorTorqueSleep_4_testMerged_3() {
        TestCase.assertFalse(b2.isAtRest());
    }

    @Test
    public void setMaximumMotorTorqueSleep_6_testMerged_4() {
        b1.setAtRest(true);
        TestCase.assertTrue(b1.isAtRest());
    }

    @Test
    public void setMaximumMotorTorqueSleep_7_testMerged_5() {
        b2.setAtRest(true);
        TestCase.assertTrue(b2.isAtRest());
    }

    @Test
    public void setMaximumMotorTorqueEnabledSleep_1_testMerged_1() {
        WheelJoint<Body> wj = new WheelJoint<Body>(b1, b2, new Vector2(1.0, 2.0), new Vector2(-3.0, 0.5));
        TestCase.assertFalse(wj.isMotorEnabled());
        TestCase.assertEquals(1000.0, wj.getMaximumMotorTorque());
        TestCase.assertFalse(wj.isMaximumMotorTorqueEnabled());
        wj.setMotorEnabled(true);
        wj.setMaximumMotorTorqueEnabled(true);
        TestCase.assertTrue(wj.isMaximumMotorTorqueEnabled());
    }

    @Test
    public void setMaximumMotorTorqueEnabledSleep_4_testMerged_2() {
        TestCase.assertFalse(b1.isAtRest());
    }

    @Test
    public void setMaximumMotorTorqueEnabledSleep_5_testMerged_3() {
        TestCase.assertFalse(b2.isAtRest());
    }

    @Test
    public void setMaximumMotorTorqueEnabledSleep_7_testMerged_4() {
        b1.setAtRest(true);
        TestCase.assertTrue(b1.isAtRest());
    }

    @Test
    public void setMaximumMotorTorqueEnabledSleep_8_testMerged_5() {
        b2.setAtRest(true);
        TestCase.assertTrue(b2.isAtRest());
    }

    @Test
    public void setMotorEnabledSleep_1_testMerged_1() {
        WheelJoint<Body> wj = new WheelJoint<Body>(b1, b2, new Vector2(1.0, 2.0), new Vector2(-3.0, 0.5));
        TestCase.assertFalse(wj.isMotorEnabled());
        wj.setMotorEnabled(false);
        wj.setMotorEnabled(true);
        TestCase.assertTrue(wj.isMotorEnabled());
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
        WheelJoint<Body> wj = new WheelJoint<Body>(b1, b2, new Vector2(1.0, 2.0), new Vector2(-3.0, 0.5));
        TestCase.assertFalse(wj.isMotorEnabled());
        TestCase.assertEquals(0.0, wj.getMotorSpeed());
        wj.setMotorEnabled(true);
        wj.setMotorSpeed(0.0);
        wj.setMotorSpeed(2.0);
        TestCase.assertEquals(2.0, wj.getMotorSpeed());
        wj.setMotorEnabled(false);
        wj.setMotorSpeed(-1.0);
        TestCase.assertEquals(-1.0, wj.getMotorSpeed());
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
        WheelJoint<Body> wj = new WheelJoint<Body>(b1, b2, new Vector2(1.0, 2.0), new Vector2(-3.0, 0.5));
        wj.setLowerLimitEnabled(true);
        wj.setUpperLimitEnabled(true);
        TestCase.assertTrue(wj.isUpperLimitEnabled());
        TestCase.assertTrue(wj.isLowerLimitEnabled());
        wj.setLimitsEnabled(false);
        TestCase.assertFalse(wj.isUpperLimitEnabled());
        TestCase.assertFalse(wj.isLowerLimitEnabled());
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
        WheelJoint<Body> wj = new WheelJoint<Body>(b1, b2, new Vector2(1.0, 2.0), new Vector2(-3.0, 0.5));
        wj.setLowerLimitEnabled(true);
        wj.setUpperLimitEnabled(true);
        TestCase.assertTrue(wj.isUpperLimitEnabled());
        TestCase.assertTrue(wj.isLowerLimitEnabled());
        double defaultLowerLimit = wj.getLowerLimit();
        double defaultUpperLimit = wj.getUpperLimit();
        TestCase.assertEquals(defaultLowerLimit, defaultUpperLimit);
        wj.setLimits(defaultLowerLimit, defaultUpperLimit);
        TestCase.assertEquals(defaultLowerLimit, wj.getLowerLimit());
        TestCase.assertEquals(defaultUpperLimit, wj.getUpperLimit());
        wj.setLimits(2.0);
        TestCase.assertEquals(2.0, wj.getLowerLimit());
        TestCase.assertEquals(2.0, wj.getUpperLimit());
        wj.setLowerLimit(0.0);
        TestCase.assertEquals(0.0, wj.getLowerLimit());
        wj.setUpperLimit(3.0);
        TestCase.assertEquals(3.0, wj.getUpperLimit());
        wj.setLimitsEnabled(false);
        wj.setLimits(1.0);
        TestCase.assertFalse(wj.isUpperLimitEnabled());
        TestCase.assertFalse(wj.isLowerLimitEnabled());
        TestCase.assertEquals(1.0, wj.getLowerLimit());
        TestCase.assertEquals(1.0, wj.getUpperLimit());
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
        WheelJoint<Body> wj = new WheelJoint<Body>(b1, b2, new Vector2(1.0, 2.0), new Vector2(-3.0, 0.5));
        wj.setLowerLimitEnabled(true);
        wj.setUpperLimitEnabled(true);
        TestCase.assertTrue(wj.isUpperLimitEnabled());
        TestCase.assertTrue(wj.isLowerLimitEnabled());
        double defaultLowerLimit = wj.getLowerLimit();
        double defaultUpperLimit = wj.getUpperLimit();
        TestCase.assertEquals(defaultLowerLimit, defaultUpperLimit);
        wj.setLimits(defaultLowerLimit, defaultUpperLimit);
        TestCase.assertEquals(defaultLowerLimit, wj.getLowerLimit());
        TestCase.assertEquals(defaultUpperLimit, wj.getUpperLimit());
        wj.setLimits(2.0, 3.0);
        TestCase.assertEquals(2.0, wj.getLowerLimit());
        TestCase.assertEquals(3.0, wj.getUpperLimit());
        wj.setLowerLimit(1.0);
        TestCase.assertEquals(1.0, wj.getLowerLimit());
        wj.setLimits(0.0, 3.0);
        TestCase.assertEquals(0.0, wj.getLowerLimit());
        wj.setUpperLimit(2.0);
        TestCase.assertEquals(2.0, wj.getUpperLimit());
        wj.setLimits(0.0, 1.0);
        TestCase.assertEquals(1.0, wj.getUpperLimit());
        wj.setLimitsEnabled(false);
        wj.setLimits(1.0, 2.0);
        TestCase.assertFalse(wj.isUpperLimitEnabled());
        TestCase.assertFalse(wj.isLowerLimitEnabled());
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
        WheelJoint<Body> wj = new WheelJoint<Body>(b1, b2, new Vector2(1.0, 2.0), new Vector2(-3.0, 0.5));
        wj.setLowerLimitEnabled(true);
        wj.setUpperLimitEnabled(true);
        TestCase.assertTrue(wj.isLowerLimitEnabled());
        double defaultLowerLimit = wj.getLowerLimit();
        double defaultUpperLimit = wj.getUpperLimit();
        wj.setLowerLimit(defaultLowerLimit);
        TestCase.assertEquals(defaultLowerLimit, wj.getLowerLimit());
        TestCase.assertEquals(defaultUpperLimit, wj.getUpperLimit());
        wj.setLowerLimit(-0.5);
        TestCase.assertEquals(-0.5, wj.getLowerLimit());
        wj.setLimitsEnabled(false);
        wj.setLowerLimit(-0.2);
        TestCase.assertEquals(-0.2, wj.getLowerLimit());
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
        WheelJoint<Body> wj = new WheelJoint<Body>(b1, b2, new Vector2(1.0, 2.0), new Vector2(-3.0, 0.5));
        wj.setLowerLimitEnabled(true);
        wj.setUpperLimitEnabled(true);
        TestCase.assertTrue(wj.isUpperLimitEnabled());
        double defaultLowerLimit = wj.getLowerLimit();
        double defaultUpperLimit = wj.getUpperLimit();
        wj.setUpperLimit(defaultUpperLimit);
        TestCase.assertEquals(defaultLowerLimit, wj.getLowerLimit());
        TestCase.assertEquals(defaultUpperLimit, wj.getUpperLimit());
        wj.setUpperLimit(2.0);
        TestCase.assertEquals(2.0, wj.getUpperLimit());
        wj.setLimitsEnabled(false);
        wj.setUpperLimit(3.0);
        TestCase.assertEquals(3.0, wj.getUpperLimit());
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
        WheelJoint<Body> wj = new WheelJoint<Body>(b1, b2, new Vector2(1.0, 2.0), new Vector2(-3.0, 0.5));
        wj.setLowerLimitEnabled(true);
        wj.setUpperLimitEnabled(true);
        TestCase.assertTrue(wj.isUpperLimitEnabled());
        TestCase.assertTrue(wj.isLowerLimitEnabled());
        double defaultLowerLimit = wj.getLowerLimit();
        double defaultUpperLimit = wj.getUpperLimit();
        TestCase.assertEquals(defaultLowerLimit, defaultUpperLimit);
        wj.setLimitsEnabled(defaultLowerLimit, defaultUpperLimit);
        TestCase.assertEquals(defaultLowerLimit, wj.getLowerLimit());
        TestCase.assertEquals(defaultUpperLimit, wj.getUpperLimit());
        wj.setLimitsEnabled(2.0);
        TestCase.assertEquals(2.0, wj.getLowerLimit());
        TestCase.assertEquals(2.0, wj.getUpperLimit());
        wj.setLimitsEnabled(false);
        wj.setLimitsEnabled(1.0);
        TestCase.assertEquals(1.0, wj.getLowerLimit());
        TestCase.assertEquals(1.0, wj.getUpperLimit());
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
        WheelJoint<Body> wj = new WheelJoint<Body>(b1, b2, new Vector2(1.0, 2.0), new Vector2(-3.0, 0.5));
        wj.setLowerLimitEnabled(true);
        wj.setUpperLimitEnabled(true);
        TestCase.assertTrue(wj.isUpperLimitEnabled());
        TestCase.assertTrue(wj.isLowerLimitEnabled());
        double defaultLowerLimit = wj.getLowerLimit();
        double defaultUpperLimit = wj.getUpperLimit();
        TestCase.assertEquals(defaultLowerLimit, defaultUpperLimit);
        wj.setLimitsEnabled(defaultLowerLimit, defaultUpperLimit);
        TestCase.assertEquals(defaultLowerLimit, wj.getLowerLimit());
        TestCase.assertEquals(defaultUpperLimit, wj.getUpperLimit());
        wj.setLimitsEnabled(0.0, 2.0);
        TestCase.assertEquals(0.0, wj.getLowerLimit());
        TestCase.assertEquals(2.0, wj.getUpperLimit());
        wj.setLowerLimit(0.5);
        TestCase.assertEquals(0.5, wj.getLowerLimit());
        wj.setUpperLimit(3.0);
        TestCase.assertEquals(3.0, wj.getUpperLimit());
        wj.setLimitsEnabled(false);
        wj.setLimitsEnabled(0.5, 4.0);
        TestCase.assertEquals(4.0, wj.getUpperLimit());
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
