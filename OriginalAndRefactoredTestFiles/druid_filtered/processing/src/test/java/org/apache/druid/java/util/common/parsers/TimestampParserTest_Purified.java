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

public class TimestampParserTest_Purified {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testStripQuotes_1() {
        Assert.assertEquals("hello world", ParserUtils.stripQuotes("\"hello world\""));
    }

    @Test
    public void testStripQuotes_2() {
        Assert.assertEquals("hello world", ParserUtils.stripQuotes("    \"    hello world   \"    "));
    }

    @Test
    public void testExtractTimeZone_1() {
        Assert.assertEquals(DateTimeZone.UTC, ParserUtils.getDateTimeZone("UTC"));
    }

    @Test
    public void testExtractTimeZone_2() {
        Assert.assertEquals(DateTimeZone.forTimeZone(TimeZone.getTimeZone("PST")), ParserUtils.getDateTimeZone("PST"));
    }

    @Test
    public void testExtractTimeZone_3() {
        Assert.assertNull(ParserUtils.getDateTimeZone("Hello"));
    }

    @Test
    public void testExtractTimeZone_4() {
        Assert.assertNull(ParserUtils.getDateTimeZone("AEST"));
    }

    @Test
    public void testExtractTimeZone_5() {
        Assert.assertEquals(DateTimeZone.forTimeZone(TimeZone.getTimeZone("Australia/Hobart")), ParserUtils.getDateTimeZone("Australia/Hobart"));
    }

    @Test
    public void testExtractTimeZone_6() {
        Assert.assertNull(ParserUtils.getDateTimeZone(""));
    }

    @Test
    public void testExtractTimeZone_7() {
        Assert.assertNull(ParserUtils.getDateTimeZone(null));
    }
}
