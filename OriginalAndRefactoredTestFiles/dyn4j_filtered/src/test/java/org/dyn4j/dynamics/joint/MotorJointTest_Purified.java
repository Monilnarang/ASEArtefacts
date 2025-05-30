package org.dyn4j.dynamics.joint;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Vector2;
import org.junit.Test;
import junit.framework.TestCase;

public class MotorJointTest_Purified extends BaseJointTest {

    @Test
    public void setLinearTargetSleep_1_testMerged_1() {
        TestCase.assertFalse(b1.isAtRest());
    }

    @Test
    public void setLinearTargetSleep_2_testMerged_2() {
        TestCase.assertFalse(b2.isAtRest());
    }

    @Test
    public void setLinearTargetSleep_3_testMerged_3() {
        MotorJoint<Body> mj = new MotorJoint<Body>(b1, b2);
        Vector2 defaultLinearTarget = mj.getLinearTarget();
        TestCase.assertTrue(defaultLinearTarget.equals(mj.getLinearTarget()));
        mj.setLinearTarget(defaultLinearTarget);
        Vector2 target = new Vector2(1.0, 1.0);
        mj.setLinearTarget(target);
        TestCase.assertTrue(target.equals(mj.getLinearTarget()));
    }

    @Test
    public void setLinearTargetSleep_4_testMerged_4() {
        b1.setAtRest(true);
        TestCase.assertTrue(b1.isAtRest());
    }

    @Test
    public void setLinearTargetSleep_5_testMerged_5() {
        b2.setAtRest(true);
        TestCase.assertTrue(b2.isAtRest());
    }

    @Test
    public void setAngularTargetSleep_1_testMerged_1() {
        TestCase.assertFalse(b1.isAtRest());
    }

    @Test
    public void setAngularTargetSleep_2_testMerged_2() {
        TestCase.assertFalse(b2.isAtRest());
    }

    @Test
    public void setAngularTargetSleep_3_testMerged_3() {
        MotorJoint<Body> mj = new MotorJoint<Body>(b1, b2);
        double defaultAngularTarget = mj.getAngularTarget();
        TestCase.assertEquals(defaultAngularTarget, mj.getAngularTarget());
        mj.setAngularTarget(defaultAngularTarget);
        mj.setAngularTarget(1.0);
        TestCase.assertEquals(1.0, mj.getAngularTarget());
    }

    @Test
    public void setAngularTargetSleep_4_testMerged_4() {
        b1.setAtRest(true);
        TestCase.assertTrue(b1.isAtRest());
    }

    @Test
    public void setAngularTargetSleep_5_testMerged_5() {
        b2.setAtRest(true);
        TestCase.assertTrue(b2.isAtRest());
    }
}
