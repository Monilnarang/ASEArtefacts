package org.apache.druid.java.util.common.parsers;

import com.google.common.base.Function;
import org.apache.druid.java.util.common.DateTimes;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.util.TimeZone;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TimestampParserTest_Parameterized {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testExtractTimeZone_1() {
        Assert.assertEquals(DateTimeZone.UTC, ParserUtils.getDateTimeZone("UTC"));
    }

    @Test
    public void testExtractTimeZone_7() {
        Assert.assertNull(ParserUtils.getDateTimeZone(null));
    }

    @ParameterizedTest
    @MethodSource("Provider_testStripQuotes_1to2")
    public void testStripQuotes_1to2(String param1, String param2) {
        Assert.assertEquals(param1, ParserUtils.stripQuotes(param2));
    }

    static public Stream<Arguments> Provider_testStripQuotes_1to2() {
        return Stream.of(arguments("hello world", "\"hello world\""), arguments("hello world", "    \"    hello world   \"    "));
    }

    @ParameterizedTest
    @MethodSource("Provider_testExtractTimeZone_2_5")
    public void testExtractTimeZone_2_5(String param1, String param2) {
        Assert.assertEquals(DateTimeZone.forTimeZone(TimeZone.getTimeZone(param2)), ParserUtils.getDateTimeZone(param1));
    }

    static public Stream<Arguments> Provider_testExtractTimeZone_2_5() {
        return Stream.of(arguments("PST", "PST"), arguments("Australia/Hobart", "Australia/Hobart"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testExtractTimeZone_3to4_6")
    public void testExtractTimeZone_3to4_6(String param1) {
        Assert.assertNull(ParserUtils.getDateTimeZone(param1));
    }

    static public Stream<Arguments> Provider_testExtractTimeZone_3to4_6() {
        return Stream.of(arguments("Hello"), arguments("AEST"), arguments(""));
    }
}
