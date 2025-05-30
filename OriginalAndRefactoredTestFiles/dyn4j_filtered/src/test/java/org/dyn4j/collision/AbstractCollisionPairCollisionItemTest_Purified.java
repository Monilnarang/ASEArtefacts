package org.dyn4j.collision;

import java.util.HashMap;
import org.dyn4j.geometry.Geometry;
import org.junit.Before;
import org.junit.Test;
import junit.framework.TestCase;

public class AbstractCollisionPairCollisionItemTest_Purified {

    private TestCollisionBody cb1;

    private TestCollisionBody cb2;

    private TestCollisionBody cb3;

    private Fixture f1a;

    private Fixture f1b;

    private Fixture f2;

    private Fixture f3;

    private CollisionPair<CollisionItem<TestCollisionBody, Fixture>> pair_1a_to_2;

    private CollisionPair<CollisionItem<TestCollisionBody, Fixture>> pair_2_to_1a;

    private CollisionPair<CollisionItem<TestCollisionBody, Fixture>> pair_1b_to_2;

    private CollisionPair<CollisionItem<TestCollisionBody, Fixture>> pair_2_to_1b;

    private CollisionPair<CollisionItem<TestCollisionBody, Fixture>> pair_2_to_3;

    private CollisionPair<CollisionItem<TestCollisionBody, Fixture>> pair_3_to_2;

    @Before
    public void setup() {
        this.f1a = new Fixture(Geometry.createCircle(1.0));
        this.f1b = new Fixture(Geometry.createCircle(1.0));
        this.f2 = new Fixture(Geometry.createCircle(2.0));
        this.f3 = new Fixture(Geometry.createCircle(3.0));
        this.cb1 = new TestCollisionBody(this.f1a);
        cb1.addFixture(this.f1b);
        this.cb2 = new TestCollisionBody(this.f2);
        this.cb3 = new TestCollisionBody(this.f3);
        this.pair_1a_to_2 = new BasicCollisionPair<CollisionItem<TestCollisionBody, Fixture>>(new BasicCollisionItem<TestCollisionBody, Fixture>(this.cb1, this.f1a), new BasicCollisionItem<TestCollisionBody, Fixture>(this.cb2, this.f2));
        this.pair_2_to_1a = new BasicCollisionPair<CollisionItem<TestCollisionBody, Fixture>>(new BasicCollisionItem<TestCollisionBody, Fixture>(this.cb2, this.f2), new BasicCollisionItem<TestCollisionBody, Fixture>(this.cb1, this.f1a));
        this.pair_1b_to_2 = new BasicCollisionPair<CollisionItem<TestCollisionBody, Fixture>>(new BasicCollisionItem<TestCollisionBody, Fixture>(this.cb1, this.f1b), new BasicCollisionItem<TestCollisionBody, Fixture>(this.cb2, this.f2));
        this.pair_2_to_1b = new BasicCollisionPair<CollisionItem<TestCollisionBody, Fixture>>(new BasicCollisionItem<TestCollisionBody, Fixture>(this.cb2, this.f2), new BasicCollisionItem<TestCollisionBody, Fixture>(this.cb1, this.f1b));
        this.pair_2_to_3 = new BasicCollisionPair<CollisionItem<TestCollisionBody, Fixture>>(new BasicCollisionItem<TestCollisionBody, Fixture>(this.cb2, this.f2), new BasicCollisionItem<TestCollisionBody, Fixture>(this.cb3, this.f3));
        this.pair_3_to_2 = new BasicCollisionPair<CollisionItem<TestCollisionBody, Fixture>>(new BasicCollisionItem<TestCollisionBody, Fixture>(this.cb3, this.f3), new BasicCollisionItem<TestCollisionBody, Fixture>(this.cb2, this.f2));
    }

    @Test
    public void equalsTest_1() {
        TestCase.assertTrue(AbstractCollisionPair.equals(this.pair_1a_to_2, this.pair_1a_to_2));
    }

    @Test
    public void equalsTest_2() {
        TestCase.assertTrue(AbstractCollisionPair.equals(this.pair_1a_to_2, this.pair_2_to_1a));
    }

    @Test
    public void equalsTest_3() {
        TestCase.assertFalse(AbstractCollisionPair.equals(this.pair_1a_to_2, this.pair_1b_to_2));
    }

    @Test
    public void equalsTest_4() {
        TestCase.assertFalse(AbstractCollisionPair.equals(this.pair_1b_to_2, this.pair_1a_to_2));
    }

    @Test
    public void equalsTest_5() {
        TestCase.assertFalse(AbstractCollisionPair.equals(this.pair_1a_to_2, this.pair_2_to_1b));
    }

    @Test
    public void equalsTest_6() {
        TestCase.assertFalse(AbstractCollisionPair.equals(this.pair_2_to_1b, this.pair_1a_to_2));
    }

    @Test
    public void equalsTest_7() {
        TestCase.assertTrue(AbstractCollisionPair.equals(this.pair_2_to_3, this.pair_2_to_3));
    }

    @Test
    public void equalsTest_8() {
        TestCase.assertTrue(AbstractCollisionPair.equals(this.pair_2_to_3, this.pair_3_to_2));
    }

    @Test
    public void equalsTest_9() {
        TestCase.assertTrue(AbstractCollisionPair.equals(this.pair_3_to_2, this.pair_2_to_3));
    }

    @Test
    public void equalsTest_10() {
        TestCase.assertTrue(AbstractCollisionPair.equals(this.pair_3_to_2, this.pair_3_to_2));
    }

    @Test
    public void equalsTest_11() {
        TestCase.assertFalse(AbstractCollisionPair.equals(this.pair_1a_to_2, null));
    }

    @Test
    public void equalsTest_12() {
        TestCase.assertFalse(AbstractCollisionPair.equals(null, this.pair_2_to_1a));
    }

    @Test
    public void equalsTest_13() {
        TestCase.assertTrue(AbstractCollisionPair.equals(null, null));
    }

    @Test
    public void equalsTest_14() {
        TestCase.assertFalse(AbstractCollisionPair.equals(this.pair_1a_to_2, new Object()));
    }

    @Test
    public void equalsTest_15() {
        TestCase.assertFalse(AbstractCollisionPair.equals(null, new Object()));
    }
}
