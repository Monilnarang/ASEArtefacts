package org.dyn4j.collision.broadphase;

import org.dyn4j.collision.CollisionItem;
import org.dyn4j.collision.Fixture;
import org.dyn4j.collision.TestCollisionBody;
import org.dyn4j.geometry.Geometry;
import org.junit.Test;
import junit.framework.TestCase;

public class AssortedBroadphaseTest_Purified {

    public void adapterSuccess() {
        BroadphaseFilter<CollisionItem<TestCollisionBody, Fixture>> bf = new CollisionItemBroadphaseFilter<TestCollisionBody, Fixture>();
        AABBProducer<CollisionItem<TestCollisionBody, Fixture>> p = new CollisionItemAABBProducer<TestCollisionBody, Fixture>();
        AABBExpansionMethod<CollisionItem<TestCollisionBody, Fixture>> e = new NullAABBExpansionMethod<CollisionItem<TestCollisionBody, Fixture>>();
        BroadphaseDetector<CollisionItem<TestCollisionBody, Fixture>> bp = new DynamicAABBTree<CollisionItem<TestCollisionBody, Fixture>>(bf, p, e);
        CollisionItemBroadphaseDetector<TestCollisionBody, Fixture> cibd = new CollisionItemBroadphaseDetectorAdapter<TestCollisionBody, Fixture>(bp);
        TestCase.assertNotNull(cibd);
        TestCase.assertNotNull(cibd.getBroadphaseFilter());
        TestCase.assertNotNull(cibd.getAABBProducer());
        TestCase.assertNotNull(cibd.getAABBExpansionMethod());
        TestCase.assertNotNull(cibd.getDecoratedBroadphaseDetector());
        TestCase.assertEquals(bf, cibd.getBroadphaseFilter());
        TestCase.assertEquals(p, cibd.getAABBProducer());
        TestCase.assertEquals(e, cibd.getAABBExpansionMethod());
        TestCase.assertEquals(bp, cibd.getDecoratedBroadphaseDetector());
    }

    @Test
    public void helpers_1_testMerged_1() {
        DynamicAABBTreeLeaf<TestCollisionBody> leaf = new DynamicAABBTreeLeaf<TestCollisionBody>(new TestCollisionBody());
        TestCase.assertTrue(leaf.isLeaf());
        TestCase.assertNotNull(leaf.toString());
    }

    @Test
    public void helpers_3_testMerged_2() {
        DynamicAABBTreeNode node = new DynamicAABBTreeNode();
        TestCase.assertFalse(node.isLeaf());
        TestCase.assertNotNull(node.toString());
    }
}
