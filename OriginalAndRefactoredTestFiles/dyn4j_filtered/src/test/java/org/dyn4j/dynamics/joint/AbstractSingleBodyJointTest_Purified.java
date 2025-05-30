package org.dyn4j.dynamics.joint;

import java.util.Iterator;
import java.util.List;
import org.dyn4j.dynamics.Body;
import org.junit.Before;
import org.junit.Test;
import junit.framework.TestCase;

public class AbstractSingleBodyJointTest_Purified {

    protected Body b1;

    protected AbstractSingleBodyJoint<Body> aj;

    @Before
    public void setup() {
        this.b1 = new Body();
        this.aj = new TestAbstractSingleBodyJoint(this.b1);
    }

    @Test
    public void create_1() {
        TestCase.assertEquals(1, aj.getBodyCount());
    }

    @Test
    public void create_2() {
        TestCase.assertNull(aj.owner);
    }

    @Test
    public void create_3() {
        TestCase.assertNull(aj.getOwner());
    }

    @Test
    public void create_4() {
        TestCase.assertNull(aj.userData);
    }

    @Test
    public void create_5() {
        TestCase.assertNull(aj.getUserData());
    }

    @Test
    public void create_6() {
        TestCase.assertFalse(aj.collisionAllowed);
    }

    @Test
    public void create_7() {
        TestCase.assertFalse(aj.isCollisionAllowed());
    }

    @Test
    public void create_8() {
        TestCase.assertEquals(b1, aj.getBody(0));
    }

    @Test
    public void create_9() {
        TestCase.assertEquals(b1, aj.getBody());
    }

    @Test
    public void create_10() {
        TestCase.assertNotNull(aj.getBodies());
    }

    @Test
    public void create_11() {
        TestCase.assertNotNull(aj.getBodyIterator());
    }

    @Test
    public void create_12() {
        TestCase.assertNotNull(aj.toString());
    }

    @Test
    public void isMember_1() {
        TestCase.assertTrue(aj.isMember(b1));
    }

    @Test
    public void isMember_2() {
        TestCase.assertFalse(aj.isMember(null));
    }

    @Test
    public void isMember_3() {
        TestCase.assertFalse(aj.isMember(new Body()));
    }

    @Test
    public void isEnabled_1_testMerged_1() {
        TestCase.assertTrue(aj.isEnabled());
    }

    @Test
    public void isEnabled_2() {
        TestCase.assertFalse(aj.isEnabled());
    }
}
