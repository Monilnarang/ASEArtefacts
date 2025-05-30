package org.dyn4j.geometry;

import org.junit.Test;
import junit.framework.TestCase;

public class RotationTest_Purified {

    @Test
    public void create_1_testMerged_1() {
        Rotation r1 = new Rotation();
        TestCase.assertEquals(1.0, r1.cost, 1.0e-6);
        TestCase.assertEquals(0.0, r1.sint, 1.0e-6);
        TestCase.assertNotNull(r1.toString());
    }

    @Test
    public void create_4_testMerged_2() {
        Rotation r2 = new Rotation(Math.PI);
        TestCase.assertEquals(-1.0, r2.cost, 1.0e-6);
        TestCase.assertEquals(0.0, r2.sint, 1.0e-6);
    }

    @Test
    public void create_6_testMerged_3() {
        Rotation r3 = new Rotation(-1.0, 0.0);
        TestCase.assertEquals(-1.0, r3.cost);
        TestCase.assertEquals(0.0, r3.sint);
    }

    @Test
    public void create_8_testMerged_4() {
        Rotation r4 = new Rotation(1.0);
        TestCase.assertEquals(Math.cos(1.0), r4.cost, 1.0e-6);
        TestCase.assertEquals(Math.sin(1.0), r4.sint, 1.0e-6);
        Rotation r5 = new Rotation(r4);
        TestCase.assertEquals(Math.cos(1.0), r5.cost, 1.0e-6);
        TestCase.assertEquals(Math.sin(1.0), r5.sint, 1.0e-6);
    }

    @Test
    public void create_12_testMerged_5() {
        Rotation r6 = Rotation.of(2.5);
        TestCase.assertEquals(Math.cos(2.5), r6.cost, 1.0e-6);
        TestCase.assertEquals(Math.sin(2.5), r6.sint, 1.0e-6);
        Rotation r9 = Rotation.of(new Vector2());
        TestCase.assertEquals(1.0, r9.cost, 1.0e-6);
        TestCase.assertEquals(0.0, r9.sint, 1.0e-6);
        Vector2 v1 = new Vector2(5, -5);
        Rotation r7 = Rotation.of(v1);
        TestCase.assertEquals(Math.cos(Math.toRadians(-45)), r7.cost, 1.0e-6);
        TestCase.assertEquals(Math.sin(Math.toRadians(-45)), r7.sint, 1.0e-6);
        Transform t1 = new Transform();
        t1.setRotation(-1.0);
        t1.translate(-10, -20);
        Rotation r8 = Rotation.of(t1);
        TestCase.assertEquals(Math.cos(-1.0), r8.cost, 1.0e-6);
        TestCase.assertEquals(Math.sin(-1.0), r8.sint, 1.0e-6);
        Rotation r0 = Rotation.rotation0();
        TestCase.assertEquals(Math.cos(Math.toRadians(0.0)), r0.cost, 1.0e-6);
        TestCase.assertEquals(Math.sin(Math.toRadians(0.0)), r0.sint, 1.0e-6);
        Rotation r90 = Rotation.rotation90();
        TestCase.assertEquals(Math.cos(Math.toRadians(90.0)), r90.cost, 1.0e-6);
        TestCase.assertEquals(Math.sin(Math.toRadians(90.0)), r90.sint, 1.0e-6);
        Rotation r180 = Rotation.rotation180();
        TestCase.assertEquals(Math.cos(Math.toRadians(180.0)), r180.cost, 1.0e-6);
        TestCase.assertEquals(Math.sin(Math.toRadians(180.0)), r180.sint, 1.0e-6);
        Rotation r270 = Rotation.rotation270();
        TestCase.assertEquals(Math.cos(Math.toRadians(270.0)), r270.cost, 1.0e-6);
        TestCase.assertEquals(Math.sin(Math.toRadians(270.0)), r270.sint, 1.0e-6);
        Rotation r45 = Rotation.rotation45();
        TestCase.assertEquals(Math.cos(Math.toRadians(45.0)), r45.cost, 1.0e-6);
        TestCase.assertEquals(Math.sin(Math.toRadians(45.0)), r45.sint, 1.0e-6);
        Rotation r135 = Rotation.rotation135();
        TestCase.assertEquals(Math.cos(Math.toRadians(135.0)), r135.cost, 1.0e-6);
        TestCase.assertEquals(Math.sin(Math.toRadians(135.0)), r135.sint, 1.0e-6);
        Rotation r225 = Rotation.rotation225();
        TestCase.assertEquals(Math.cos(Math.toRadians(225.0)), r225.cost, 1.0e-6);
        TestCase.assertEquals(Math.sin(Math.toRadians(225.0)), r225.sint, 1.0e-6);
        Rotation r315 = Rotation.rotation315();
        TestCase.assertEquals(Math.cos(Math.toRadians(315.0)), r315.cost, 1.0e-6);
        TestCase.assertEquals(Math.sin(Math.toRadians(315.0)), r315.sint, 1.0e-6);
    }

    @Test
    public void inverse_1_testMerged_1() {
        Rotation r1 = new Rotation(0.0);
        r1.inverse();
        TestCase.assertEquals(1.0, r1.cost, 1.0e-6);
        TestCase.assertEquals(0.0, r1.sint, 1.0e-6);
    }

    @Test
    public void inverse_3_testMerged_2() {
        Rotation r2 = new Rotation(Math.PI / 2.0);
        Rotation r3 = r2.copy();
        r2.inverse();
        TestCase.assertEquals(0.0, r2.cost, 1.0e-6);
        TestCase.assertEquals(-1.0, r2.sint, 1.0e-6);
        TestCase.assertEquals(r3, r2);
        Rotation temp = r2.getInversed();
        TestCase.assertEquals(0.0, temp.cost, 1.0e-6);
        TestCase.assertEquals(-1.0, temp.sint, 1.0e-6);
    }
}
