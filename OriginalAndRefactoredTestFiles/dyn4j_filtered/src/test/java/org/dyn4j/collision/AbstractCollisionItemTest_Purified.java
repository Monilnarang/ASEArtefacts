package org.dyn4j.collision;

import java.util.HashMap;
import org.dyn4j.geometry.Geometry;
import org.junit.Before;
import org.junit.Test;
import junit.framework.TestCase;

public class AbstractCollisionItemTest_Purified {

    private TestCollisionBody cb1;

    private TestCollisionBody cb2;

    private Fixture f1a;

    private Fixture f1b;

    private Fixture f2;

    private CollisionItem<TestCollisionBody, Fixture> item1a;

    private CollisionItem<TestCollisionBody, Fixture> item1b;

    private CollisionItem<TestCollisionBody, Fixture> item2;

    @Before
    public void setup() {
        this.f1a = new Fixture(Geometry.createCircle(1.0));
        this.f1b = new Fixture(Geometry.createCircle(1.0));
        this.f2 = new Fixture(Geometry.createCircle(2.0));
        this.cb1 = new TestCollisionBody(this.f1a);
        this.cb1.addFixture(this.f1b);
        this.cb2 = new TestCollisionBody(this.f2);
        this.item1a = new BasicCollisionItem<TestCollisionBody, Fixture>(this.cb1, this.f1a);
        this.item1b = new BasicCollisionItem<TestCollisionBody, Fixture>(this.cb1, this.f1b);
        this.item2 = new BasicCollisionItem<TestCollisionBody, Fixture>(this.cb2, this.f2);
    }

    @Test
    public void equalsTest_1() {
        TestCase.assertTrue(AbstractCollisionItem.equals(this.item1a, this.item1a));
    }

    @Test
    public void equalsTest_2() {
        TestCase.assertTrue(AbstractCollisionItem.equals(this.item1b, this.item1b));
    }

    @Test
    public void equalsTest_3() {
        TestCase.assertTrue(AbstractCollisionItem.equals(this.item2, this.item2));
    }

    @Test
    public void equalsTest_4() {
        TestCase.assertFalse(AbstractCollisionItem.equals(this.item1a, this.item1b));
    }

    @Test
    public void equalsTest_5() {
        TestCase.assertFalse(AbstractCollisionItem.equals(this.item1a, this.item2));
    }

    @Test
    public void equalsTest_6() {
        TestCase.assertFalse(AbstractCollisionItem.equals(this.item1b, this.item2));
    }

    @Test
    public void equalsTest_7() {
        TestCase.assertFalse(AbstractCollisionItem.equals(this.item1b, this.item1a));
    }

    @Test
    public void equalsTest_8() {
        TestCase.assertFalse(AbstractCollisionItem.equals(this.item2, this.item1a));
    }

    @Test
    public void equalsTest_9() {
        TestCase.assertFalse(AbstractCollisionItem.equals(this.item2, this.item1b));
    }

    @Test
    public void equalsTest_10() {
        TestCase.assertFalse(AbstractCollisionItem.equals(this.item2, null));
    }

    @Test
    public void equalsTest_11() {
        TestCase.assertFalse(AbstractCollisionItem.equals(null, this.item2));
    }

    @Test
    public void equalsTest_12() {
        TestCase.assertTrue(AbstractCollisionItem.equals(null, null));
    }

    @Test
    public void equalsTest_13() {
        TestCase.assertFalse(AbstractCollisionItem.equals(this.item2, new Object()));
    }

    @Test
    public void equalsTest_14() {
        TestCase.assertFalse(AbstractCollisionItem.equals(null, new Object()));
    }
}
