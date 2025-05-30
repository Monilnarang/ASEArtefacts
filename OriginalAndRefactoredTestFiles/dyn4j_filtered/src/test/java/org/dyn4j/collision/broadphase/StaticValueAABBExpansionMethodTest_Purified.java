package org.dyn4j.collision.broadphase;

import org.dyn4j.geometry.AABB;
import org.junit.Test;
import junit.framework.TestCase;

public class StaticValueAABBExpansionMethodTest_Purified {

    @Test
    public void expand_1() {
        StaticValueAABBExpansionMethod<Object> method = new StaticValueAABBExpansionMethod<Object>(4);
        TestCase.assertEquals(4.0, method.getExpansion());
    }

    @Test
    public void expand_2_testMerged_2() {
        AABB aabb = new AABB(0, 0, 0, 0);
        method.expand(null, aabb);
        TestCase.assertEquals(-2.0, aabb.getMinX());
        TestCase.assertEquals(-2.0, aabb.getMinY());
        TestCase.assertEquals(2.0, aabb.getMaxX());
        TestCase.assertEquals(2.0, aabb.getMaxY());
        aabb = new AABB(-3, -2, 1, 5);
        TestCase.assertEquals(-5.0, aabb.getMinX());
        TestCase.assertEquals(-4.0, aabb.getMinY());
        TestCase.assertEquals(3.0, aabb.getMaxX());
        TestCase.assertEquals(7.0, aabb.getMaxY());
    }
}
