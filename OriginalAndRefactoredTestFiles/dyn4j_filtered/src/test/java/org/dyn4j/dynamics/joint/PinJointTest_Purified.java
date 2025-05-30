package org.dyn4j.dynamics.joint;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Vector2;
import org.junit.Test;
import junit.framework.TestCase;

public class PinJointTest_Purified extends BaseJointTest {

    @Test
    public void setSpringDampingRatio_1_testMerged_1() {
        PinJoint<Body> pj = new PinJoint<Body>(b1, new Vector2());
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
    public void setSpringDampingRatio_8() {
        TestCase.assertFalse(this.b1.isAtRest());
    }

    @Test
    public void setSpringFrequency_1_testMerged_1() {
        PinJoint<Body> pj = new PinJoint<Body>(b1, new Vector2());
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
        TestCase.assertFalse(this.b1.isAtRest());
    }

    @Test
    public void setSpringStiffness_1_testMerged_1() {
        PinJoint<Body> pj = new PinJoint<Body>(b1, new Vector2());
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
        TestCase.assertFalse(this.b1.isAtRest());
    }

    @Test
    public void setSpringMaximumForce_1_testMerged_1() {
        PinJoint<Body> pj = new PinJoint<Body>(b1, new Vector2());
        pj.setMaximumSpringForce(0.0);
        TestCase.assertEquals(0.0, pj.getMaximumSpringForce());
        pj.setMaximumSpringForce(0.001);
        TestCase.assertEquals(0.001, pj.getMaximumSpringForce());
        pj.setMaximumSpringForce(1.0);
        TestCase.assertEquals(1.0, pj.getMaximumSpringForce());
        pj.setMaximumSpringForce(1000);
        TestCase.assertEquals(1000.0, pj.getMaximumSpringForce());
    }

    @Test
    public void setSpringMaximumForce_5_testMerged_2() {
        TestCase.assertTrue(this.b1.isAtRest());
    }

    @Test
    public void setSpringMaximumForce_8_testMerged_3() {
        TestCase.assertFalse(this.b1.isAtRest());
    }

    @Test
    public void setTargetSleep_1_testMerged_1() {
        TestCase.assertFalse(b1.isAtRest());
    }

    @Test
    public void setTargetSleep_2_testMerged_2() {
        PinJoint<Body> pj = new PinJoint<Body>(b1, new Vector2());
        Vector2 defaultTarget = pj.getTarget();
        TestCase.assertTrue(defaultTarget.equals(pj.getTarget()));
        pj.setTarget(defaultTarget);
        Vector2 target = new Vector2(1.0, 1.0);
        pj.setTarget(target);
        TestCase.assertTrue(target.equals(pj.getTarget()));
    }

    @Test
    public void setTargetSleep_3_testMerged_3() {
        b1.setAtRest(true);
        TestCase.assertTrue(b1.isAtRest());
    }
}
