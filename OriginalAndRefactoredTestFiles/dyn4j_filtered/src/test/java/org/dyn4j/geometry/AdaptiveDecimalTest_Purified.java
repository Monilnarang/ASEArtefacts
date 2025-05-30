package org.dyn4j.geometry;

import java.util.Random;
import org.junit.Test;
import junit.framework.TestCase;

public class AdaptiveDecimalTest_Purified {

    private static final int SEED = 0;

    @Test
    public void create_1_testMerged_1() {
        AdaptiveDecimal ed = new AdaptiveDecimal(2);
        TestCase.assertEquals(0, ed.size());
        TestCase.assertEquals(2, ed.capacity());
        ed.append(1.0);
        TestCase.assertEquals(1, ed.size());
        TestCase.assertEquals(1.0, ed.get(0));
        TestCase.assertNotNull(ed.toString());
    }

    @Test
    public void create_6_testMerged_2() {
        AdaptiveDecimal ed3 = AdaptiveDecimal.valueOf(10.0);
        TestCase.assertEquals(10.0, ed3.get(0));
        AdaptiveDecimal ed4 = AdaptiveDecimal.fromSum(0.1, 0.2);
        TestCase.assertEquals(0.1 + 0.2, ed4.getEstimation());
        AdaptiveDecimal ed5 = AdaptiveDecimal.fromDiff(0.1, 0.2);
        TestCase.assertEquals(0.1 - 0.2, ed5.getEstimation());
        AdaptiveDecimal ed6 = AdaptiveDecimal.fromProduct(0.1, 0.2);
        TestCase.assertEquals(0.1 * 0.2, ed6.getEstimation());
    }

    @Test
    public void normalize_1_testMerged_1() {
        AdaptiveDecimal ed = new AdaptiveDecimal(2);
        TestCase.assertEquals(0, ed.size());
        ed.normalize();
        TestCase.assertEquals(1, ed.size());
    }

    @Test
    public void normalize_3_testMerged_2() {
        AdaptiveDecimal ed2 = new AdaptiveDecimal(2);
        ed2.append(1.0).append(-4.0);
        ed2.normalize();
        TestCase.assertEquals(2, ed2.size());
        TestCase.assertEquals(1.0, ed2.get(0));
        TestCase.assertEquals(-4.0, ed2.get(1));
    }
}
