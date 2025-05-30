package org.dyn4j.collision;

import org.junit.Test;
import junit.framework.TestCase;

public class CollisionsTest_Purified {

    @Test
    public void estimates_1() {
        TestCase.assertEquals(0, Collisions.getEstimatedCollisionPairs(0));
    }

    @Test
    public void estimates_2() {
        TestCase.assertTrue(Collisions.getEstimatedCollisionPairs(2) > 0);
    }

    @Test
    public void estimates_3() {
        TestCase.assertTrue(Collisions.getEstimatedCollisionsPerObject() > 0);
    }

    @Test
    public void estimates_4() {
        TestCase.assertEquals(1, Collisions.getEstimatedRaycastCollisions(0));
    }

    @Test
    public void estimates_5() {
        TestCase.assertTrue(Collisions.getEstimatedRaycastCollisions(2) > 0);
    }
}
