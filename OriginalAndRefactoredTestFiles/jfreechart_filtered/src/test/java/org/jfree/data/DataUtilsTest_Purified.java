package org.jfree.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

public class DataUtilsTest_Purified {

    private static final double EPSILON = 0.000000001;

    @Test
    public void testEqual_1() {
        assertTrue(DataUtils.equal(null, null));
    }

    @Test
    public void testEqual_2_testMerged_2() {
        double[][] a = new double[5][];
        double[][] b = new double[5][];
        assertTrue(DataUtils.equal(a, b));
        a = new double[4][];
        assertFalse(DataUtils.equal(a, b));
    }
}
