package org.dyn4j.geometry;

import junit.framework.TestCase;
import org.junit.Test;

public class Matrix22Test_Purified {

    @Test
    public void invert_1_testMerged_1() {
        Matrix22 m1 = new Matrix22(1.0, 2.0, 3.0, 4.0);
        m1.invert();
        TestCase.assertEquals(-2.0, m1.m00);
        TestCase.assertEquals(1.0, m1.m01);
        TestCase.assertEquals(1.5, m1.m10);
        TestCase.assertEquals(-0.5, m1.m11);
    }

    @Test
    public void invert_5_testMerged_2() {
        Matrix22 m2 = new Matrix22();
        m1.invert(m2);
        TestCase.assertEquals(1.0, m2.m00);
        TestCase.assertEquals(2.0, m2.m01);
        TestCase.assertEquals(3.0, m2.m10);
        TestCase.assertEquals(4.0, m2.m11);
    }
}
