package org.apache.druid.query.groupby.having;

import org.apache.druid.error.DruidException;
import org.junit.Assert;
import org.junit.Test;
import java.math.BigDecimal;

public class HavingSpecMetricComparatorTest_Purified {

    @Test
    public void testLongRegex_1() {
        Assert.assertTrue(HavingSpecMetricComparator.LONG_PAT.matcher("1").matches());
    }

    @Test
    public void testLongRegex_2() {
        Assert.assertTrue(HavingSpecMetricComparator.LONG_PAT.matcher("12").matches());
    }

    @Test
    public void testLongRegex_3() {
        Assert.assertFalse(HavingSpecMetricComparator.LONG_PAT.matcher("1.").matches());
    }

    @Test
    public void testLongRegex_4() {
        Assert.assertFalse(HavingSpecMetricComparator.LONG_PAT.matcher("1.2").matches());
    }

    @Test
    public void testLongRegex_5() {
        Assert.assertFalse(HavingSpecMetricComparator.LONG_PAT.matcher("1.23").matches());
    }

    @Test
    public void testLongRegex_6() {
        Assert.assertFalse(HavingSpecMetricComparator.LONG_PAT.matcher("1E5").matches());
    }

    @Test
    public void testLongRegex_7() {
        Assert.assertFalse(HavingSpecMetricComparator.LONG_PAT.matcher("1.23E5").matches());
    }

    @Test
    public void testLongRegex_8() {
        Assert.assertFalse(HavingSpecMetricComparator.LONG_PAT.matcher("").matches());
    }

    @Test
    public void testLongRegex_9() {
        Assert.assertFalse(HavingSpecMetricComparator.LONG_PAT.matcher("xyz").matches());
    }

    @Test
    public void testCompareDoubleToLongWithNanReturns1_1() {
        Assert.assertEquals(1, HavingSpecMetricComparator.compareDoubleToLong(Double.NaN, 1));
    }

    @Test
    public void testCompareDoubleToLongWithNanReturns1_2() {
        Assert.assertEquals(1, HavingSpecMetricComparator.compareDoubleToLong(Double.NaN, -1));
    }

    @Test
    public void testCompareDoubleToLongWithNanReturns1_3() {
        Assert.assertEquals(1, HavingSpecMetricComparator.compareDoubleToLong(Double.NaN, Long.MAX_VALUE));
    }

    @Test
    public void testCompareDoubleToLongWithNanReturns1_4() {
        Assert.assertEquals(1, HavingSpecMetricComparator.compareDoubleToLong(Double.NaN, Long.MIN_VALUE));
    }

    @Test
    public void testCompareDoubleToLongWithNanReturns1_5() {
        Assert.assertEquals(1, HavingSpecMetricComparator.compareDoubleToLong(Double.NaN, 0L));
    }

    @Test
    public void testCompareDoubleToLongWithNanReturns1_6() {
        Assert.assertEquals(1, HavingSpecMetricComparator.compareDoubleToLong(Float.NaN, 1));
    }

    @Test
    public void testCompareDoubleToLongWithNanReturns1_7() {
        Assert.assertEquals(1, HavingSpecMetricComparator.compareDoubleToLong(Float.NaN, -1));
    }

    @Test
    public void testCompareDoubleToLongWithNanReturns1_8() {
        Assert.assertEquals(1, HavingSpecMetricComparator.compareDoubleToLong(Float.NaN, Long.MAX_VALUE));
    }

    @Test
    public void testCompareDoubleToLongWithNanReturns1_9() {
        Assert.assertEquals(1, HavingSpecMetricComparator.compareDoubleToLong(Float.NaN, Long.MIN_VALUE));
    }

    @Test
    public void testCompareDoubleToLongWithNanReturns1_10() {
        Assert.assertEquals(1, HavingSpecMetricComparator.compareDoubleToLong(Float.NaN, 0L));
    }

    @Test
    public void testCompareDoubleToLongWithInfinityReturns1_1() {
        Assert.assertEquals(1, HavingSpecMetricComparator.compareDoubleToLong(Double.POSITIVE_INFINITY, 1));
    }

    @Test
    public void testCompareDoubleToLongWithInfinityReturns1_2() {
        Assert.assertEquals(1, HavingSpecMetricComparator.compareDoubleToLong(Double.POSITIVE_INFINITY, -1));
    }

    @Test
    public void testCompareDoubleToLongWithInfinityReturns1_3() {
        Assert.assertEquals(1, HavingSpecMetricComparator.compareDoubleToLong(Double.POSITIVE_INFINITY, Long.MAX_VALUE));
    }

    @Test
    public void testCompareDoubleToLongWithInfinityReturns1_4() {
        Assert.assertEquals(1, HavingSpecMetricComparator.compareDoubleToLong(Double.POSITIVE_INFINITY, Long.MIN_VALUE));
    }

    @Test
    public void testCompareDoubleToLongWithInfinityReturns1_5() {
        Assert.assertEquals(1, HavingSpecMetricComparator.compareDoubleToLong(Double.POSITIVE_INFINITY, 0L));
    }

    @Test
    public void testCompareDoubleToLongWithInfinityReturns1_6() {
        Assert.assertEquals(1, HavingSpecMetricComparator.compareDoubleToLong(Float.POSITIVE_INFINITY, 1));
    }

    @Test
    public void testCompareDoubleToLongWithInfinityReturns1_7() {
        Assert.assertEquals(1, HavingSpecMetricComparator.compareDoubleToLong(Float.POSITIVE_INFINITY, -1));
    }

    @Test
    public void testCompareDoubleToLongWithInfinityReturns1_8() {
        Assert.assertEquals(1, HavingSpecMetricComparator.compareDoubleToLong(Float.POSITIVE_INFINITY, Long.MAX_VALUE));
    }

    @Test
    public void testCompareDoubleToLongWithInfinityReturns1_9() {
        Assert.assertEquals(1, HavingSpecMetricComparator.compareDoubleToLong(Float.POSITIVE_INFINITY, Long.MIN_VALUE));
    }

    @Test
    public void testCompareDoubleToLongWithInfinityReturns1_10() {
        Assert.assertEquals(1, HavingSpecMetricComparator.compareDoubleToLong(Float.POSITIVE_INFINITY, 0L));
    }

    @Test
    public void testCompareDoubleToLongWithInfinityReturnsNegative1_1() {
        Assert.assertEquals(-1, HavingSpecMetricComparator.compareDoubleToLong(Double.NEGATIVE_INFINITY, 1));
    }

    @Test
    public void testCompareDoubleToLongWithInfinityReturnsNegative1_2() {
        Assert.assertEquals(-1, HavingSpecMetricComparator.compareDoubleToLong(Double.NEGATIVE_INFINITY, -1));
    }

    @Test
    public void testCompareDoubleToLongWithInfinityReturnsNegative1_3() {
        Assert.assertEquals(-1, HavingSpecMetricComparator.compareDoubleToLong(Double.NEGATIVE_INFINITY, Long.MAX_VALUE));
    }

    @Test
    public void testCompareDoubleToLongWithInfinityReturnsNegative1_4() {
        Assert.assertEquals(-1, HavingSpecMetricComparator.compareDoubleToLong(Double.NEGATIVE_INFINITY, Long.MIN_VALUE));
    }

    @Test
    public void testCompareDoubleToLongWithInfinityReturnsNegative1_5() {
        Assert.assertEquals(-1, HavingSpecMetricComparator.compareDoubleToLong(Double.NEGATIVE_INFINITY, 0L));
    }

    @Test
    public void testCompareDoubleToLongWithInfinityReturnsNegative1_6() {
        Assert.assertEquals(-1, HavingSpecMetricComparator.compareDoubleToLong(Float.NEGATIVE_INFINITY, 1));
    }

    @Test
    public void testCompareDoubleToLongWithInfinityReturnsNegative1_7() {
        Assert.assertEquals(-1, HavingSpecMetricComparator.compareDoubleToLong(Float.NEGATIVE_INFINITY, -1));
    }

    @Test
    public void testCompareDoubleToLongWithInfinityReturnsNegative1_8() {
        Assert.assertEquals(-1, HavingSpecMetricComparator.compareDoubleToLong(Float.NEGATIVE_INFINITY, Long.MAX_VALUE));
    }

    @Test
    public void testCompareDoubleToLongWithInfinityReturnsNegative1_9() {
        Assert.assertEquals(-1, HavingSpecMetricComparator.compareDoubleToLong(Float.NEGATIVE_INFINITY, Long.MIN_VALUE));
    }

    @Test
    public void testCompareDoubleToLongWithInfinityReturnsNegative1_10() {
        Assert.assertEquals(-1, HavingSpecMetricComparator.compareDoubleToLong(Float.NEGATIVE_INFINITY, 0L));
    }

    @Test
    public void testCompareDoubleToLongWithNumbers_1() {
        Assert.assertEquals(1, HavingSpecMetricComparator.compareDoubleToLong(1 + 1e-6, 1));
    }

    @Test
    public void testCompareDoubleToLongWithNumbers_2() {
        Assert.assertEquals(-1, HavingSpecMetricComparator.compareDoubleToLong(1 - 1e-6, 1));
    }

    @Test
    public void testCompareDoubleToLongWithNumbers_3() {
        Assert.assertEquals(0, HavingSpecMetricComparator.compareDoubleToLong(10D, 10));
    }

    @Test
    public void testCompareDoubleToLongWithNumbers_4() {
        Assert.assertEquals(0, HavingSpecMetricComparator.compareDoubleToLong(0D, 0));
    }

    @Test
    public void testCompareDoubleToLongWithNumbers_5() {
        Assert.assertEquals(0, HavingSpecMetricComparator.compareDoubleToLong(-0D, 0));
    }

    @Test
    public void testCompareDoubleToLongWithNumbers_6() {
        Assert.assertEquals(1, HavingSpecMetricComparator.compareDoubleToLong((double) Long.MAX_VALUE + 1, Long.MAX_VALUE));
    }

    @Test
    public void testCompareDoubleToLongWithNumbers_7() {
        Assert.assertEquals(-1, HavingSpecMetricComparator.compareDoubleToLong((double) Long.MIN_VALUE - 1, Long.MIN_VALUE));
    }
}
