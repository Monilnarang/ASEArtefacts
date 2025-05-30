package org.apache.druid.query.groupby.having;

import org.apache.druid.error.DruidException;
import org.junit.Assert;
import org.junit.Test;
import java.math.BigDecimal;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class HavingSpecMetricComparatorTest_Parameterized {

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
    public void testCompareDoubleToLongWithNumbers_1() {
        Assert.assertEquals(1, HavingSpecMetricComparator.compareDoubleToLong(1 + 1e-6, 1));
    }

    @Test
    public void testCompareDoubleToLongWithNumbers_2() {
        Assert.assertEquals(-1, HavingSpecMetricComparator.compareDoubleToLong(1 - 1e-6, 1));
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

    @ParameterizedTest
    @MethodSource("Provider_testLongRegex_1to2")
    public void testLongRegex_1to2(int param1) {
        Assert.assertTrue(HavingSpecMetricComparator.LONG_PAT.matcher(param1).matches());
    }

    static public Stream<Arguments> Provider_testLongRegex_1to2() {
        return Stream.of(arguments(1), arguments(12));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLongRegex_3to9")
    public void testLongRegex_3to9(double param1) {
        Assert.assertFalse(HavingSpecMetricComparator.LONG_PAT.matcher(param1).matches());
    }

    static public Stream<Arguments> Provider_testLongRegex_3to9() {
        return Stream.of(arguments(1.), arguments(1.2), arguments(1.23), arguments(1E5), arguments(1.23E5), arguments(""), arguments("xyz"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCompareDoubleToLongWithNanReturns1_1_5")
    public void testCompareDoubleToLongWithNanReturns1_1_5(int param1, int param2) {
        Assert.assertEquals(param1, HavingSpecMetricComparator.compareDoubleToLong(Double.NaN, param2));
    }

    static public Stream<Arguments> Provider_testCompareDoubleToLongWithNanReturns1_1_5() {
        return Stream.of(arguments(1, 1), arguments(1, 0L));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCompareDoubleToLongWithNanReturns1_6_10")
    public void testCompareDoubleToLongWithNanReturns1_6_10(int param1, int param2) {
        Assert.assertEquals(param1, HavingSpecMetricComparator.compareDoubleToLong(Float.NaN, param2));
    }

    static public Stream<Arguments> Provider_testCompareDoubleToLongWithNanReturns1_6_10() {
        return Stream.of(arguments(1, 1), arguments(1, 0L));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCompareDoubleToLongWithInfinityReturns1_1_5")
    public void testCompareDoubleToLongWithInfinityReturns1_1_5(int param1, int param2) {
        Assert.assertEquals(param1, HavingSpecMetricComparator.compareDoubleToLong(Double.POSITIVE_INFINITY, param2));
    }

    static public Stream<Arguments> Provider_testCompareDoubleToLongWithInfinityReturns1_1_5() {
        return Stream.of(arguments(1, 1), arguments(1, 0L));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCompareDoubleToLongWithInfinityReturns1_6_10")
    public void testCompareDoubleToLongWithInfinityReturns1_6_10(int param1, int param2) {
        Assert.assertEquals(param1, HavingSpecMetricComparator.compareDoubleToLong(Float.POSITIVE_INFINITY, param2));
    }

    static public Stream<Arguments> Provider_testCompareDoubleToLongWithInfinityReturns1_6_10() {
        return Stream.of(arguments(1, 1), arguments(1, 0L));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCompareDoubleToLongWithInfinityReturnsNegative1_1_5")
    public void testCompareDoubleToLongWithInfinityReturnsNegative1_1_5(int param1, int param2) {
        Assert.assertEquals(-param1, HavingSpecMetricComparator.compareDoubleToLong(Double.NEGATIVE_INFINITY, param2));
    }

    static public Stream<Arguments> Provider_testCompareDoubleToLongWithInfinityReturnsNegative1_1_5() {
        return Stream.of(arguments(1, 1), arguments(1, 0L));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCompareDoubleToLongWithInfinityReturnsNegative1_6_10")
    public void testCompareDoubleToLongWithInfinityReturnsNegative1_6_10(int param1, int param2) {
        Assert.assertEquals(-param1, HavingSpecMetricComparator.compareDoubleToLong(Float.NEGATIVE_INFINITY, param2));
    }

    static public Stream<Arguments> Provider_testCompareDoubleToLongWithInfinityReturnsNegative1_6_10() {
        return Stream.of(arguments(1, 1), arguments(1, 0L));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCompareDoubleToLongWithNumbers_3to4")
    public void testCompareDoubleToLongWithNumbers_3to4(int param1, double param2, int param3) {
        Assert.assertEquals(param1, HavingSpecMetricComparator.compareDoubleToLong(param2, param3));
    }

    static public Stream<Arguments> Provider_testCompareDoubleToLongWithNumbers_3to4() {
        return Stream.of(arguments(0, 10D, 10), arguments(0, 0D, 0));
    }
}
