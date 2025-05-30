package org.dyn4j.dynamics.joint;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Vector2;
import org.junit.Test;
import junit.framework.TestCase;

public class RevoluteJointTest_Purified extends BaseJointTest {

    @Test
    public void setMaximumMotorTorqueSleep_1_testMerged_1() {
        RevoluteJoint<Body> rj = new RevoluteJoint<Body>(b1, b2, new Vector2());
        TestCase.assertFalse(rj.isMotorEnabled());
        TestCase.assertEquals(1000.0, rj.getMaximumMotorTorque());
        rj.setMotorEnabled(true);
        rj.setMaximumMotorTorque(1000.0);
        rj.setMaximumMotorTorque(2.0);
        TestCase.assertEquals(2.0, rj.getMaximumMotorTorque());
        rj.setMotorEnabled(false);
        rj.setMaximumMotorTorque(1.0);
        TestCase.assertEquals(1.0, rj.getMaximumMotorTorque());
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
        RevoluteJoint<Body> rj = new RevoluteJoint<Body>(b1, b2, new Vector2());
        TestCase.assertFalse(rj.isMotorEnabled());
        TestCase.assertEquals(1000.0, rj.getMaximumMotorTorque());
        TestCase.assertFalse(rj.isMaximumMotorTorqueEnabled());
        rj.setMotorEnabled(true);
        rj.setMaximumMotorTorqueEnabled(true);
        TestCase.assertTrue(rj.isMaximumMotorTorqueEnabled());
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
        RevoluteJoint<Body> rj = new RevoluteJoint<Body>(b1, b2, new Vector2());
        TestCase.assertFalse(rj.isMotorEnabled());
        rj.setMotorEnabled(false);
        rj.setMotorEnabled(true);
        TestCase.assertTrue(rj.isMotorEnabled());
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
        RevoluteJoint<Body> rj = new RevoluteJoint<Body>(b1, b2, new Vector2());
        TestCase.assertFalse(rj.isMotorEnabled());
        TestCase.assertEquals(0.0, rj.getMotorSpeed());
        rj.setMotorEnabled(true);
        rj.setMotorSpeed(0.0);
        rj.setMotorSpeed(2.0);
        TestCase.assertEquals(2.0, rj.getMotorSpeed());
        rj.setMotorEnabled(false);
        rj.setMotorSpeed(-1.0);
        TestCase.assertEquals(-1.0, rj.getMotorSpeed());
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
    public void setLimitsReferenceAngleSleep_1_testMerged_1() {
        RevoluteJoint<Body> rj = new RevoluteJoint<Body>(b1, b2, new Vector2());
        TestCase.assertFalse(rj.isLimitsEnabled());
        rj.setLimitsEnabled(true);
        TestCase.assertTrue(rj.isLimitsEnabled());
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
        RevoluteJoint<Body> rj = new RevoluteJoint<Body>(b1, b2, new Vector2());
        TestCase.assertFalse(rj.isLimitsEnabled());
        rj.setLimitsEnabled(false);
        rj.setLimitsEnabled(true);
        TestCase.assertTrue(rj.isLimitsEnabled());
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
        RevoluteJoint<Body> rj = new RevoluteJoint<Body>(b1, b2, new Vector2());
        TestCase.assertFalse(rj.isLimitsEnabled());
        double defaultLowerLimit = rj.getLowerLimit();
        double defaultUpperLimit = rj.getUpperLimit();
        TestCase.assertEquals(defaultLowerLimit, defaultUpperLimit);
        rj.setLimitsEnabled(true);
        rj.setLimits(defaultLowerLimit);
        TestCase.assertTrue(rj.isLimitsEnabled());
        TestCase.assertEquals(defaultLowerLimit, rj.getLowerLimit());
        TestCase.assertEquals(defaultUpperLimit, rj.getUpperLimit());
        rj.setLimits(Math.PI);
        TestCase.assertEquals(Math.PI, rj.getLowerLimit());
        TestCase.assertEquals(Math.PI, rj.getUpperLimit());
        rj.setLowerLimit(-Math.PI);
        TestCase.assertEquals(-Math.PI, rj.getLowerLimit());
        rj.setUpperLimit(2 * Math.PI);
        TestCase.assertEquals(2 * Math.PI, rj.getUpperLimit());
        rj.setLimitsEnabled(false);
        rj.setLimits(-Math.PI);
        TestCase.assertEquals(-Math.PI, rj.getUpperLimit());
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
        RevoluteJoint<Body> rj = new RevoluteJoint<Body>(b1, b2, new Vector2());
        TestCase.assertFalse(rj.isLimitsEnabled());
        double defaultLowerLimit = rj.getLowerLimit();
        double defaultUpperLimit = rj.getUpperLimit();
        TestCase.assertEquals(defaultLowerLimit, defaultUpperLimit);
        rj.setLimitsEnabled(true);
        rj.setLimits(defaultLowerLimit, defaultUpperLimit);
        TestCase.assertTrue(rj.isLimitsEnabled());
        TestCase.assertEquals(defaultLowerLimit, rj.getLowerLimit());
        TestCase.assertEquals(defaultUpperLimit, rj.getUpperLimit());
        rj.setLimits(-Math.PI, Math.PI);
        TestCase.assertEquals(-Math.PI, rj.getLowerLimit());
        TestCase.assertEquals(Math.PI, rj.getUpperLimit());
        rj.setLowerLimit(-2 * Math.PI);
        TestCase.assertEquals(-2 * Math.PI, rj.getLowerLimit());
        rj.setUpperLimit(2 * Math.PI);
        TestCase.assertEquals(2 * Math.PI, rj.getUpperLimit());
        rj.setLimitsEnabled(false);
        rj.setLimits(Math.PI, 2 * Math.PI);
        TestCase.assertEquals(Math.PI, rj.getLowerLimit());
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
        RevoluteJoint<Body> rj = new RevoluteJoint<Body>(b1, b2, new Vector2());
        TestCase.assertFalse(rj.isLimitsEnabled());
        double defaultLowerLimit = rj.getLowerLimit();
        double defaultUpperLimit = rj.getUpperLimit();
        rj.setLimitsEnabled(true);
        rj.setLowerLimit(defaultLowerLimit);
        TestCase.assertTrue(rj.isLimitsEnabled());
        TestCase.assertEquals(defaultLowerLimit, rj.getLowerLimit());
        TestCase.assertEquals(defaultUpperLimit, rj.getUpperLimit());
        rj.setLowerLimit(-Math.PI);
        TestCase.assertEquals(-Math.PI, rj.getLowerLimit());
        rj.setLimitsEnabled(false);
        rj.setLowerLimit(-2 * Math.PI);
        TestCase.assertEquals(-2 * Math.PI, rj.getLowerLimit());
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
        RevoluteJoint<Body> rj = new RevoluteJoint<Body>(b1, b2, new Vector2());
        TestCase.assertFalse(rj.isLimitsEnabled());
        double defaultLowerLimit = rj.getLowerLimit();
        double defaultUpperLimit = rj.getUpperLimit();
        rj.setLimitsEnabled(true);
        rj.setUpperLimit(defaultUpperLimit);
        TestCase.assertTrue(rj.isLimitsEnabled());
        TestCase.assertEquals(defaultLowerLimit, rj.getLowerLimit());
        TestCase.assertEquals(defaultUpperLimit, rj.getUpperLimit());
        rj.setUpperLimit(Math.PI);
        TestCase.assertEquals(Math.PI, rj.getUpperLimit());
        rj.setLimitsEnabled(false);
        rj.setUpperLimit(2 * Math.PI);
        TestCase.assertEquals(2 * Math.PI, rj.getUpperLimit());
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
        RevoluteJoint<Body> rj = new RevoluteJoint<Body>(b1, b2, new Vector2());
        TestCase.assertFalse(rj.isLimitsEnabled());
        double defaultLowerLimit = rj.getLowerLimit();
        double defaultUpperLimit = rj.getUpperLimit();
        TestCase.assertEquals(defaultLowerLimit, defaultUpperLimit);
        rj.setLimitsEnabled(true);
        rj.setLimitsEnabled(defaultLowerLimit);
        TestCase.assertTrue(rj.isLimitsEnabled());
        TestCase.assertEquals(defaultLowerLimit, rj.getLowerLimit());
        TestCase.assertEquals(defaultUpperLimit, rj.getUpperLimit());
        rj.setLimitsEnabled(Math.PI);
        TestCase.assertEquals(Math.PI, rj.getLowerLimit());
        TestCase.assertEquals(Math.PI, rj.getUpperLimit());
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
        RevoluteJoint<Body> rj = new RevoluteJoint<Body>(b1, b2, new Vector2());
        TestCase.assertFalse(rj.isLimitsEnabled());
        double defaultLowerLimit = rj.getLowerLimit();
        double defaultUpperLimit = rj.getUpperLimit();
        TestCase.assertEquals(defaultLowerLimit, defaultUpperLimit);
        rj.setLimitsEnabled(true);
        rj.setLimitsEnabled(defaultLowerLimit, defaultUpperLimit);
        TestCase.assertTrue(rj.isLimitsEnabled());
        TestCase.assertEquals(defaultLowerLimit, rj.getLowerLimit());
        TestCase.assertEquals(defaultUpperLimit, rj.getUpperLimit());
        rj.setLimitsEnabled(-Math.PI, Math.PI);
        TestCase.assertEquals(-Math.PI, rj.getLowerLimit());
        TestCase.assertEquals(Math.PI, rj.getUpperLimit());
        rj.setLowerLimit(-2 * Math.PI);
        TestCase.assertEquals(-2 * Math.PI, rj.getLowerLimit());
        rj.setUpperLimit(2 * Math.PI);
        TestCase.assertEquals(2 * Math.PI, rj.getUpperLimit());
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
        RevoluteJoint<Body> rj = new RevoluteJoint<Body>(b1, b2, new Vector2());
        TestCase.assertFalse(rj.isLimitsEnabled());
        rj.setLimitsEnabled(true);
        rj.setLimitsEnabled(false);
        TestCase.assertTrue(rj.isLimitsEnabled());
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
