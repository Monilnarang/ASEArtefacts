package org.dyn4j.world.result;

import org.dyn4j.collision.continuous.TimeOfImpact;
import org.dyn4j.collision.narrowphase.Penetration;
import org.dyn4j.collision.narrowphase.Raycast;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Geometry;
import org.junit.Test;
import junit.framework.TestCase;

public class DetectResultTest_Purified {

    @Test
    public void createSuccess_1_testMerged_1() {
        DetectResult<Body, BodyFixture> dr = new DetectResult<Body, BodyFixture>();
        TestCase.assertNull(dr.getBody());
        TestCase.assertNull(dr.getFixture());
    }

    @Test
    public void createSuccess_3_testMerged_2() {
        ConvexCastResult<Body, BodyFixture> ccr = new ConvexCastResult<Body, BodyFixture>();
        TestCase.assertNull(ccr.getBody());
        TestCase.assertNull(ccr.getFixture());
        TestCase.assertNotNull(ccr.getTimeOfImpact());
    }

    @Test
    public void createSuccess_6_testMerged_3() {
        ConvexDetectResult<Body, BodyFixture> cdr = new ConvexDetectResult<Body, BodyFixture>();
        TestCase.assertNull(cdr.getBody());
        TestCase.assertNull(cdr.getFixture());
        TestCase.assertNotNull(cdr.getPenetration());
    }

    @Test
    public void createSuccess_9_testMerged_4() {
        RaycastResult<Body, BodyFixture> rr = new RaycastResult<Body, BodyFixture>();
        TestCase.assertNull(rr.getBody());
        TestCase.assertNull(rr.getFixture());
        TestCase.assertNotNull(rr.getRaycast());
    }
}
