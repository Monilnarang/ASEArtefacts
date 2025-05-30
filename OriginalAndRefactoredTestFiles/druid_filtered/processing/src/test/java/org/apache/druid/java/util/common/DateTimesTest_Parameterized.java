package org.apache.druid.java.util.common;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Test;
import java.util.TimeZone;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class DateTimesTest_Parameterized {

    @Test
    public void testCanCompareAsString_1() {
        Assert.assertTrue(DateTimes.canCompareAsString(DateTimes.EPOCH));
    }

    @Test
    public void testCanCompareAsString_3() {
        Assert.assertEquals("0000-01-01T00:00:00.000Z", DateTimes.COMPARE_DATE_AS_STRING_MIN.toString());
    }

    @Test
    public void testCanCompareAsString_4() {
        Assert.assertEquals("9999-12-31T23:59:59.999Z", DateTimes.COMPARE_DATE_AS_STRING_MAX.toString());
    }

    @Test
    public void testCanCompareAsString_7() {
        Assert.assertFalse(DateTimes.canCompareAsString(DateTimes.MIN));
    }

    @Test
    public void testCanCompareAsString_8() {
        Assert.assertFalse(DateTimes.canCompareAsString(DateTimes.MAX));
    }

    @Test
    public void testCanCompareAsString_11() {
        Assert.assertFalse(DateTimes.canCompareAsString(DateTimes.of("2000").withZone(DateTimes.inferTzFromString("America/Los_Angeles"))));
    }

    @Test
    public void testEarlierOf_1() {
        Assert.assertNull(DateTimes.earlierOf(null, null));
    }

    @Test
    public void testEarlierOf_2_testMerged_2() {
        final DateTime jan14 = DateTimes.of("2013-01-14");
        Assert.assertEquals(jan14, DateTimes.earlierOf(null, jan14));
        Assert.assertEquals(jan14, DateTimes.earlierOf(jan14, null));
        Assert.assertEquals(jan14, DateTimes.earlierOf(jan14, jan14));
        final DateTime jan15 = DateTimes.of("2013-01-15");
        Assert.assertEquals(jan14, DateTimes.earlierOf(jan15, jan14));
        Assert.assertEquals(jan14, DateTimes.earlierOf(jan14, jan15));
    }

    @Test
    public void testLaterOf_1() {
        Assert.assertNull(DateTimes.laterOf(null, null));
    }

    @Test
    public void testLaterOf_2_testMerged_2() {
        final DateTime jan14 = DateTimes.of("2013-01-14");
        Assert.assertEquals(jan14, DateTimes.laterOf(null, jan14));
        Assert.assertEquals(jan14, DateTimes.laterOf(jan14, null));
        Assert.assertEquals(jan14, DateTimes.laterOf(jan14, jan14));
        final DateTime jan15 = DateTimes.of("2013-01-15");
        Assert.assertEquals(jan15, DateTimes.laterOf(jan15, jan14));
        Assert.assertEquals(jan15, DateTimes.laterOf(jan14, jan15));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCanCompareAsString_2_5to6")
    public void testCanCompareAsString_2_5to6(String param1) {
        Assert.assertTrue(DateTimes.canCompareAsString(DateTimes.of(param1)));
    }

    static public Stream<Arguments> Provider_testCanCompareAsString_2_5to6() {
        return Stream.of(arguments("0000-01-01"), arguments(9999), arguments(2000));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCanCompareAsString_9to10")
    public void testCanCompareAsString_9to10(String param1) {
        Assert.assertFalse(DateTimes.canCompareAsString(DateTimes.of(param1)));
    }

    static public Stream<Arguments> Provider_testCanCompareAsString_9to10() {
        return Stream.of(arguments("-1-01-01T00:00:00"), arguments("10000-01-01"));
    }
}
