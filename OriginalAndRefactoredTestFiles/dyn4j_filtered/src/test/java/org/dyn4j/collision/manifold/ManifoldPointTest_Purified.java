package org.dyn4j.collision.manifold;

import org.dyn4j.geometry.Vector2;
import org.junit.Test;
import junit.framework.TestCase;

public class ManifoldPointTest_Purified {

    @Test
    public void create_1_testMerged_1() {
        ManifoldPoint mp = new ManifoldPoint(ManifoldPointId.DISTANCE);
        TestCase.assertNotNull(mp.point);
        TestCase.assertEquals(0.0, mp.depth);
        TestCase.assertEquals(ManifoldPointId.DISTANCE, mp.id);
    }

    @Test
    public void create_4_testMerged_2() {
        Vector2 pt = new Vector2(1.0, 2.0);
        ManifoldPoint mp2 = new ManifoldPoint(ManifoldPointId.DISTANCE, pt, 1.0);
        TestCase.assertEquals(ManifoldPointId.DISTANCE, mp2.id);
        TestCase.assertNotNull(mp2.point);
        TestCase.assertNotSame(pt, mp2.point);
        TestCase.assertEquals(pt.x, mp2.point.x);
        TestCase.assertEquals(pt.y, mp2.point.y);
        TestCase.assertEquals(1.0, mp2.depth);
    }

    @Test
    public void create_10_testMerged_3() {
        ManifoldPoint mp3 = new ManifoldPoint((ManifoldPointId) null);
        TestCase.assertNotNull(mp3.point);
        TestCase.assertEquals(0.0, mp3.depth);
        TestCase.assertEquals(ManifoldPointId.DISTANCE, mp3.id);
    }

    @Test
    public void copy_1_testMerged_1() {
        Vector2 pt = new Vector2(1.0, 2.0);
        ManifoldPoint mp = new ManifoldPoint(ManifoldPointId.DISTANCE, pt, 1.0);
        ManifoldPoint mp2 = mp.copy();
        TestCase.assertEquals(ManifoldPointId.DISTANCE, mp2.id);
        TestCase.assertNotNull(mp2.point);
        TestCase.assertNotSame(mp.point, mp2.point);
        TestCase.assertSame(mp.id, mp2.id);
        TestCase.assertEquals(pt.x, mp2.point.x);
        TestCase.assertEquals(pt.y, mp2.point.y);
        TestCase.assertEquals(1.0, mp2.depth);
        ManifoldPoint mp3 = new ManifoldPoint(ManifoldPointId.DISTANCE);
        mp3.set(mp);
        TestCase.assertEquals(ManifoldPointId.DISTANCE, mp3.id);
        TestCase.assertNotNull(mp3.point);
        TestCase.assertSame(mp.id, mp3.id);
        TestCase.assertNotSame(mp.point, mp3.point);
        TestCase.assertEquals(pt.x, mp3.point.x);
        TestCase.assertEquals(pt.y, mp3.point.y);
        TestCase.assertEquals(1.0, mp3.depth);
    }

    @Test
    public void copy_15_testMerged_2() {
        IndexedManifoldPointId mpid = new IndexedManifoldPointId(1, 2, 3, true);
        ManifoldPoint mp4 = new ManifoldPoint(mpid, new Vector2(3, 4), 2);
        ManifoldPoint mp5 = mp4.copy();
        TestCase.assertNotSame(mp4, mp5);
        TestCase.assertNotSame(mp4.id, mp5.id);
        TestCase.assertEquals(mp4.depth, mp5.depth);
        TestCase.assertNotNull(mp5.point);
        TestCase.assertNotSame(mp4.point, mp5.point);
        TestCase.assertEquals(mp4.point.x, mp5.point.x);
        TestCase.assertEquals(mp4.point.y, mp5.point.y);
    }
}
