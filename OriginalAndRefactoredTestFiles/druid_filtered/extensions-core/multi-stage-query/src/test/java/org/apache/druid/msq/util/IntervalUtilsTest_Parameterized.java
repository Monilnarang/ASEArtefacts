package org.apache.druid.msq.util;

import org.apache.druid.java.util.common.DateTimes;
import org.apache.druid.java.util.common.Intervals;
import org.apache.druid.java.util.common.granularity.Granularities;
import org.apache.druid.java.util.common.granularity.PeriodGranularity;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class IntervalUtilsTest_Parameterized {

    public static List<Interval> intervals(final String... intervals) {
        return Arrays.stream(intervals).map(Intervals::of).collect(Collectors.toList());
    }

    @Test
    public void test_difference_1() {
        Assert.assertEquals(intervals(), IntervalUtils.difference(intervals(), intervals("2000/P1D")));
    }

    @Test
    public void test_difference_2() {
        Assert.assertEquals(intervals("2000/P1D"), IntervalUtils.difference(intervals("2000/P1D"), intervals()));
    }

    @Test
    public void test_difference_5() {
        Assert.assertEquals(intervals("2000/2000-02-01", "2000-02-02/2001"), IntervalUtils.difference(intervals("2000/2001"), intervals("2000-02-01/P1D")));
    }

    @Test
    public void test_difference_6() {
        Assert.assertEquals(intervals(), IntervalUtils.difference(intervals("2000/2001"), intervals("1999/2001")));
    }

    @Test
    public void test_difference_7() {
        Assert.assertEquals(intervals("2000-01-14/2000-02-01", "2000-02-02/2001"), IntervalUtils.difference(intervals("2000/P1D", "2000-01-14/2001"), intervals("2000/P1D", "2000-02-01/P1D")));
    }

    @Test
    public void test_difference_8() {
        Assert.assertEquals(intervals("2000-01-01/2000-07-01", "2000-07-02/2001-01-01", "2002-01-01/2002-07-01", "2002-07-02/2003-01-01"), IntervalUtils.difference(intervals("2000/P1Y", "2002/P1Y"), intervals("2000-07-01/P1D", "2002-07-01/P1D")));
    }

    @Test
    public void test_difference_9() {
        Assert.assertEquals(intervals(), IntervalUtils.difference(intervals("2000-01-12/2000-01-15"), intervals("2000-01-12/2000-01-13", "2000-01-13/2000-01-16")));
    }

    @Test
    public void test_difference_10() {
        Assert.assertEquals(intervals("2000-07-14/2000-07-15"), IntervalUtils.difference(intervals("2000/2001"), intervals("2000-01-01/2000-07-14", "2000-07-15/2001")));
    }

    @Test
    public void test_doesIntervalMatchesGranularity_withStandardGranularities_1() {
        Assert.assertTrue(IntervalUtils.isAligned(Intervals.ETERNITY, Granularities.ALL));
    }

    @Test
    public void test_doesIntervalMatchesGranularity_withStandardGranularities_2() {
        Assert.assertTrue(IntervalUtils.isAligned(Intervals.of("2000-01-01/2001-01-01"), Granularities.YEAR));
    }

    @Test
    public void test_doesIntervalMatchesGranularity_withStandardGranularities_3() {
        Assert.assertTrue(IntervalUtils.isAligned(Intervals.of("2000-01-01/2000-04-01"), Granularities.QUARTER));
    }

    @Test
    public void test_doesIntervalMatchesGranularity_withStandardGranularities_4() {
        Assert.assertTrue(IntervalUtils.isAligned(Intervals.of("2000-01-01/2000-02-01"), Granularities.MONTH));
    }

    @Test
    public void test_doesIntervalMatchesGranularity_withStandardGranularities_5() {
        Assert.assertTrue(IntervalUtils.isAligned(Intervals.of("1999-12-27/2000-01-03"), Granularities.WEEK));
    }

    @Test
    public void test_doesIntervalMatchesGranularity_withStandardGranularities_6() {
        Assert.assertTrue(IntervalUtils.isAligned(Intervals.of("2000-01-01/2000-01-02"), Granularities.DAY));
    }

    @Test
    public void test_doesIntervalMatchesGranularity_withStandardGranularities_7() {
        Assert.assertTrue(IntervalUtils.isAligned(Intervals.of("2000-01-01T00:00:00.000/2000-01-01T08:00:00.000"), Granularities.EIGHT_HOUR));
    }

    @Test
    public void test_doesIntervalMatchesGranularity_withStandardGranularities_8() {
        Assert.assertTrue(IntervalUtils.isAligned(Intervals.of("2000-01-01T00:00:00.000/2000-01-01T01:00:00.000"), Granularities.HOUR));
    }

    @Test
    public void test_doesIntervalMatchesGranularity_withStandardGranularities_9() {
        Assert.assertTrue(IntervalUtils.isAligned(Intervals.of("2000-01-01T00:00:00.000/2000-01-01T00:01:00.000"), Granularities.MINUTE));
    }

    @Test
    public void test_doesIntervalMatchesGranularity_withStandardGranularities_10() {
        Assert.assertTrue(IntervalUtils.isAligned(Intervals.of("2000-01-01T00:00:00.000/2000-01-01T00:00:01.000"), Granularities.SECOND));
    }

    @Test
    public void test_doesIntervalMatchesGranularity_withPeriodGranularity_1() {
        Assert.assertTrue(IntervalUtils.isAligned(Intervals.of("2000-01-01/2000-01-04"), new PeriodGranularity(new Period("P3D"), DateTimes.of("2000-01-01"), null)));
    }

    @Test
    public void test_doesIntervalMatchesGranularity_withPeriodGranularity_2() {
        Assert.assertFalse(IntervalUtils.isAligned(Intervals.of("2000-01-01/2000-01-04"), new PeriodGranularity(new Period("P3D"), DateTimes.of("2000-01-02"), null)));
    }

    @ParameterizedTest
    @MethodSource("Provider_test_difference_3to4")
    public void test_difference_3to4(String param1, String param2, String param3) {
        Assert.assertEquals(intervals(param1), IntervalUtils.difference(intervals(param2), intervals(param3)));
    }

    static public Stream<Arguments> Provider_test_difference_3to4() {
        return Stream.of(arguments("2000/2001", "2000/2001", "2003/2004"), arguments("2000-01-02/2001", "2000/2001", "2000/P1D"));
    }

    @ParameterizedTest
    @MethodSource("Provider_test_doesIntervalMatchesGranularity_withStandardGranularities_11to12")
    public void test_doesIntervalMatchesGranularity_withStandardGranularities_11to12(String param1) {
        Assert.assertFalse(IntervalUtils.isAligned(Intervals.of(param1), Granularities.YEAR));
    }

    static public Stream<Arguments> Provider_test_doesIntervalMatchesGranularity_withStandardGranularities_11to12() {
        return Stream.of(arguments("2000-01-01/2002-01-01"), arguments("2000-01-01/2002-01-08"));
    }
}
