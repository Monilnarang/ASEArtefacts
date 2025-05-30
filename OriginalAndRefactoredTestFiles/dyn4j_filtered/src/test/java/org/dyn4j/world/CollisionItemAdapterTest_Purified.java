package org.dyn4j.world;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Geometry;
import org.junit.Before;
import org.junit.Test;
import junit.framework.TestCase;

public class CollisionItemAdapterTest_Purified {

    private CollisionItemAdapter<Body, BodyFixture> item;

    @Before
    public void setup() {
        this.item = new CollisionItemAdapter<Body, BodyFixture>();
    }

    @Test
    public void getAndSet_1() {
        TestCase.assertNull(this.item.getBody());
    }

    @Test
    public void getAndSet_2() {
        TestCase.assertNull(this.item.getFixture());
    }

    @Test
    public void getAndSet_3_testMerged_3() {
        Body b1 = new Body();
        Body b2 = new Body();
        BodyFixture b1f1 = b1.addFixture(Geometry.createCircle(0.5));
        BodyFixture b2f1 = b2.addFixture(Geometry.createCircle(0.5));
        this.item.set(b1, b1f1);
        TestCase.assertSame(b1, this.item.getBody());
        TestCase.assertSame(b1f1, this.item.getFixture());
        this.item.set(b2, b2f1);
        TestCase.assertSame(b2, this.item.getBody());
        TestCase.assertSame(b2f1, this.item.getFixture());
    }

    @Test
    public void copy_1_testMerged_1() {
        CollisionItemAdapter<Body, BodyFixture> c1 = this.item.copy();
        TestCase.assertNull(c1.getBody());
        TestCase.assertNull(c1.getFixture());
    }

    @Test
    public void copy_3_testMerged_2() {
        Body b1 = new Body();
        BodyFixture b1f1 = b1.addFixture(Geometry.createCircle(0.5));
        this.item.set(b1, b1f1);
        CollisionItemAdapter<Body, BodyFixture> c2 = this.item.copy();
        TestCase.assertSame(b1, c2.getBody());
        TestCase.assertSame(b1f1, c2.getFixture());
    }
}
