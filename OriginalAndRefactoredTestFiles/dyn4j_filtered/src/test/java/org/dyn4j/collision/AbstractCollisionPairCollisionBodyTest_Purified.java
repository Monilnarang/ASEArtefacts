package org.dyn4j.collision;

import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;
import junit.framework.TestCase;

public class AbstractCollisionPairCollisionBodyTest_Purified {

    private TestCollisionBody cb1;

    private TestCollisionBody cb2;

    private TestCollisionBody cb3;

    private CollisionPair<TestCollisionBody> pair_1_to_2;

    private CollisionPair<TestCollisionBody> pair_1_to_3;

    private CollisionPair<TestCollisionBody> pair_2_to_1;

    private CollisionPair<TestCollisionBody> pair_2_to_3;

    private CollisionPair<TestCollisionBody> pair_3_to_1;

    private CollisionPair<TestCollisionBody> pair_3_to_2;

    @Before
    public void setup() {
        this.cb1 = new TestCollisionBody();
        this.cb2 = new TestCollisionBody();
        this.cb3 = new TestCollisionBody();
        this.pair_1_to_2 = new BasicCollisionPair<TestCollisionBody>(this.cb1, this.cb2);
        this.pair_1_to_3 = new BasicCollisionPair<TestCollisionBody>(this.cb1, this.cb3);
        this.pair_2_to_1 = new BasicCollisionPair<TestCollisionBody>(this.cb2, this.cb1);
        this.pair_2_to_3 = new BasicCollisionPair<TestCollisionBody>(this.cb2, this.cb3);
        this.pair_3_to_1 = new BasicCollisionPair<TestCollisionBody>(this.cb3, this.cb1);
        this.pair_3_to_2 = new BasicCollisionPair<TestCollisionBody>(this.cb3, this.cb2);
    }

    @Test
    public void equalsTest_1() {
        TestCase.assertTrue(AbstractCollisionPair.equals(this.pair_1_to_2, this.pair_1_to_2));
    }

    @Test
    public void equalsTest_2() {
        TestCase.assertTrue(AbstractCollisionPair.equals(this.pair_1_to_2, this.pair_2_to_1));
    }

    @Test
    public void equalsTest_3() {
        TestCase.assertTrue(AbstractCollisionPair.equals(this.pair_2_to_1, this.pair_1_to_2));
    }

    @Test
    public void equalsTest_4() {
        TestCase.assertTrue(AbstractCollisionPair.equals(this.pair_2_to_1, this.pair_2_to_1));
    }

    @Test
    public void equalsTest_5() {
        TestCase.assertTrue(AbstractCollisionPair.equals(this.pair_2_to_3, this.pair_2_to_3));
    }

    @Test
    public void equalsTest_6() {
        TestCase.assertTrue(AbstractCollisionPair.equals(this.pair_2_to_3, this.pair_3_to_2));
    }

    @Test
    public void equalsTest_7() {
        TestCase.assertTrue(AbstractCollisionPair.equals(this.pair_3_to_2, this.pair_2_to_3));
    }

    @Test
    public void equalsTest_8() {
        TestCase.assertTrue(AbstractCollisionPair.equals(this.pair_3_to_2, this.pair_3_to_2));
    }

    @Test
    public void equalsTest_9() {
        TestCase.assertTrue(AbstractCollisionPair.equals(this.pair_1_to_3, this.pair_1_to_3));
    }

    @Test
    public void equalsTest_10() {
        TestCase.assertTrue(AbstractCollisionPair.equals(this.pair_1_to_3, this.pair_3_to_1));
    }

    @Test
    public void equalsTest_11() {
        TestCase.assertTrue(AbstractCollisionPair.equals(this.pair_3_to_1, this.pair_1_to_3));
    }

    @Test
    public void equalsTest_12() {
        TestCase.assertTrue(AbstractCollisionPair.equals(this.pair_3_to_1, this.pair_3_to_1));
    }

    @Test
    public void equalsTest_13() {
        TestCase.assertFalse(AbstractCollisionPair.equals(this.pair_1_to_2, null));
    }

    @Test
    public void equalsTest_14() {
        TestCase.assertFalse(AbstractCollisionPair.equals(null, this.pair_1_to_2));
    }

    @Test
    public void equalsTest_15() {
        TestCase.assertTrue(AbstractCollisionPair.equals(null, null));
    }

    @Test
    public void equalsTest_16() {
        TestCase.assertFalse(AbstractCollisionPair.equals(this.pair_1_to_2, new Object()));
    }

    @Test
    public void equalsTest_17() {
        TestCase.assertFalse(AbstractCollisionPair.equals(null, new Object()));
    }

    @Test
    public void equalsTest_18() {
        TestCase.assertFalse(AbstractCollisionPair.equals(this.pair_1_to_2, this.pair_2_to_3));
    }

    @Test
    public void equalsTest_19() {
        TestCase.assertFalse(AbstractCollisionPair.equals(this.pair_1_to_2, this.pair_3_to_2));
    }

    @Test
    public void equalsTest_20() {
        TestCase.assertFalse(AbstractCollisionPair.equals(this.pair_1_to_2, this.pair_1_to_3));
    }

    @Test
    public void equalsTest_21() {
        TestCase.assertFalse(AbstractCollisionPair.equals(this.pair_1_to_2, this.pair_3_to_1));
    }

    @Test
    public void equalsTest_22() {
        TestCase.assertFalse(AbstractCollisionPair.equals(this.pair_2_to_3, this.pair_1_to_2));
    }

    @Test
    public void equalsTest_23() {
        TestCase.assertFalse(AbstractCollisionPair.equals(this.pair_2_to_3, this.pair_2_to_1));
    }

    @Test
    public void equalsTest_24() {
        TestCase.assertFalse(AbstractCollisionPair.equals(this.pair_2_to_3, this.pair_1_to_3));
    }

    @Test
    public void equalsTest_25() {
        TestCase.assertFalse(AbstractCollisionPair.equals(this.pair_2_to_3, this.pair_3_to_1));
    }

    @Test
    public void equalsTest_26() {
        TestCase.assertFalse(AbstractCollisionPair.equals(this.pair_3_to_1, this.pair_2_to_3));
    }

    @Test
    public void equalsTest_27() {
        TestCase.assertFalse(AbstractCollisionPair.equals(this.pair_3_to_1, this.pair_3_to_2));
    }

    @Test
    public void equalsTest_28() {
        TestCase.assertFalse(AbstractCollisionPair.equals(this.pair_3_to_1, this.pair_1_to_2));
    }

    @Test
    public void equalsTest_29() {
        TestCase.assertFalse(AbstractCollisionPair.equals(this.pair_3_to_1, this.pair_2_to_1));
    }
}
