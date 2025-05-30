package org.adoptopenjdk.jitwatch.test;

import static org.adoptopenjdk.jitwatch.core.JITWatchConstants.*;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Map;
import org.adoptopenjdk.jitwatch.util.StringUtil;
import org.junit.Test;

public class TestStringUtil_Purified {

    @Test
    public void testFormatTimestamp_1() {
        assertEquals("00:00:00", StringUtil.formatTimestamp(0, false));
    }

    @Test
    public void testFormatTimestamp_2() {
        assertEquals("00:00:00.000", StringUtil.formatTimestamp(0, true));
    }

    @Test
    public void testFormatTimestamp_3() {
        assertEquals("00:00:04", StringUtil.formatTimestamp(4000, false));
    }

    @Test
    public void testFormatTimestamp_4() {
        assertEquals("00:00:04.567", StringUtil.formatTimestamp(4567, true));
    }

    @Test
    public void testFormatTimestamp_5() {
        assertEquals("00:01:00", StringUtil.formatTimestamp(60 * 1000 + 123, false));
    }

    @Test
    public void testFormatTimestamp_6() {
        assertEquals("00:01:00.123", StringUtil.formatTimestamp(60 * 1000 + 123, true));
    }

    @Test
    public void testFormatTimestamp_7() {
        assertEquals("01:00:00", StringUtil.formatTimestamp(60 * 60 * 1000 + 123, false));
    }

    @Test
    public void testFormatTimestamp_8() {
        assertEquals("01:00:00.123", StringUtil.formatTimestamp(60 * 60 * 1000 + 123, true));
    }

    @Test
    public void testFormatTimestamp_9() {
        assertEquals("1d 01:00:00.123", StringUtil.formatTimestamp(25 * 60 * 60 * 1000 + 123, true));
    }
}
