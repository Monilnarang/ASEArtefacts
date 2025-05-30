package org.dyn4j.geometry;

import junit.framework.TestCase;
import org.junit.Test;

public class Vector3Test_Purified {

    @Test
    public void create_1_testMerged_1() {
        Vector3 v1 = new Vector3();
        TestCase.assertEquals(0.0, v1.x);
        TestCase.assertEquals(0.0, v1.y);
        TestCase.assertEquals(0.0, v1.z);
        TestCase.assertNotNull(v1.toString());
        Vector3 v2 = new Vector3(1.0, 2.0, 3.0);
        TestCase.assertEquals(1.0, v2.x);
        TestCase.assertEquals(2.0, v2.y);
        TestCase.assertEquals(3.0, v2.z);
        Vector3 v3 = new Vector3(v2);
        TestCase.assertFalse(v3 == v2);
        TestCase.assertEquals(1.0, v3.x);
        TestCase.assertEquals(2.0, v3.y);
        TestCase.assertEquals(3.0, v3.z);
        Vector3 v5 = new Vector3(v2, v1);
        TestCase.assertEquals(-1.0, v5.x);
        TestCase.assertEquals(-2.0, v5.y);
        TestCase.assertEquals(-3.0, v5.z);
    }

    @Test
    public void create_12_testMerged_2() {
        Vector3 v4 = new Vector3(0.0, 1.0, 1.0, 2.0, 3.0, 1.0);
        TestCase.assertEquals(2.0, v4.x);
        TestCase.assertEquals(2.0, v4.y);
        TestCase.assertEquals(0.0, v4.z);
    }
}
