package org.dyn4j.dynamics.joint;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.dyn4j.dynamics.Body;
import org.junit.Before;
import org.junit.Test;
import junit.framework.TestCase;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class AbstractJointTest_Parameterized {

    protected Body b1;

    protected Body b2;

    protected AbstractJoint<Body> aj;

    @Before
    public void setup() {
        this.b1 = new Body();
        this.b2 = new Body();
        this.aj = new TestAbstractJoint(Arrays.asList(b1, b2));
    }

    @Test
    public void create_1() {
        TestCase.assertEquals(2, aj.getBodyCount());
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
        TestCase.assertTrue(aj.isMember(b2));
    }

    @Test
    public void isMember_3() {
        TestCase.assertFalse(aj.isMember(null));
    }

    @Test
    public void isMember_4() {
        TestCase.assertFalse(aj.isMember(new Body()));
    }

    @Test
    public void isEnabled_1_testMerged_1() {
        TestCase.assertTrue(aj.isEnabled());
    }

    @Test
    public void isEnabled_2_testMerged_2() {
        TestCase.assertFalse(aj.isEnabled());
    }

    @ParameterizedTest
    @MethodSource("Provider_create_1_8")
    public void create_1_8(int param1) {
        TestCase.assertEquals(b1, aj.getBody(param1));
    }

    static public Stream<Arguments> Provider_create_1_8() {
        return Stream.of(arguments(0), arguments(0));
    }

    @ParameterizedTest
    @MethodSource("Provider_create_2_9")
    public void create_2_9(int param1) {
        TestCase.assertEquals(b2, aj.getBody(param1));
    }

    static public Stream<Arguments> Provider_create_2_9() {
        return Stream.of(arguments(1), arguments(1));
    }
}
