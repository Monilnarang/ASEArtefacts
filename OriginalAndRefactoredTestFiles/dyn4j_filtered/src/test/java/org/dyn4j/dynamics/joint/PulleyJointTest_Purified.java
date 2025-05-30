package org.dyn4j.dynamics.joint;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Vector2;
import org.junit.Test;
import junit.framework.TestCase;

public class PulleyJointTest_Purified extends BaseJointTest {

    @Test
    public void setRatioSleep_1_testMerged_1() {
        PulleyJoint<Body> pj = new PulleyJoint<Body>(b1, b2, new Vector2(), new Vector2(), new Vector2(), new Vector2());
        double ratio = pj.getRatio();
        TestCase.assertEquals(1.0, ratio);
        pj.setRatio(ratio);
        TestCase.assertEquals(ratio, pj.getRatio());
        pj.setRatio(2.0);
        TestCase.assertEquals(2.0, pj.getRatio());
    }

    @Test
    public void setRatioSleep_2_testMerged_2() {
        TestCase.assertFalse(b1.isAtRest());
    }

    @Test
    public void setRatioSleep_3_testMerged_3() {
        TestCase.assertFalse(b2.isAtRest());
    }

    @Test
    public void setRatioSleep_5() {
        b1.setAtRest(true);
        TestCase.assertTrue(b1.isAtRest());
    }

    @Test
    public void setRatioSleep_6() {
        b2.setAtRest(true);
        TestCase.assertTrue(b2.isAtRest());
    }

    @Test
    public void setLengthSleep_1_testMerged_1() {
        PulleyJoint<Body> pj = new PulleyJoint<Body>(b1, b2, new Vector2(), new Vector2(), new Vector2(), new Vector2());
        double length = pj.getLength();
        TestCase.assertEquals(0.0, length);
        pj.setLength(length);
        TestCase.assertEquals(length, pj.getLength());
        pj.setLength(2.0);
        TestCase.assertEquals(2.0, pj.getLength());
    }

    @Test
    public void setLengthSleep_2_testMerged_2() {
        TestCase.assertFalse(b1.isAtRest());
    }

    @Test
    public void setLengthSleep_3_testMerged_3() {
        TestCase.assertFalse(b2.isAtRest());
    }

    @Test
    public void setLengthSleep_5() {
        b1.setAtRest(true);
        TestCase.assertTrue(b1.isAtRest());
    }

    @Test
    public void setLengthSleep_6() {
        b2.setAtRest(true);
        TestCase.assertTrue(b2.isAtRest());
    }
}
