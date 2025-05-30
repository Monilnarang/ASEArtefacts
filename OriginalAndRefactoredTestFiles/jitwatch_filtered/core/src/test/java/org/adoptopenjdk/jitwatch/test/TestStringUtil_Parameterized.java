package org.adoptopenjdk.jitwatch.test;

import static org.adoptopenjdk.jitwatch.core.JITWatchConstants.*;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Map;
import org.adoptopenjdk.jitwatch.util.StringUtil;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestStringUtil_Parameterized {

    @Test
    public void testFormatTimestamp_9() {
        assertEquals("1d 01:00:00.123", StringUtil.formatTimestamp(25 * 60 * 60 * 1000 + 123, true));
    }

    @ParameterizedTest
    @MethodSource("Provider_testFormatTimestamp_1to4")
    public void testFormatTimestamp_1to4(String param1, int param2) {
        assertEquals(param1, StringUtil.formatTimestamp(param2, false));
    }

    static public Stream<Arguments> Provider_testFormatTimestamp_1to4() {
        return Stream.of(arguments("00:00:00", 0), arguments("00:00:00.000", 0), arguments("00:00:04", 4000), arguments("00:00:04.567", 4567));
    }

    @ParameterizedTest
    @MethodSource("Provider_testFormatTimestamp_5to6")
    public void testFormatTimestamp_5to6(String param1, int param2, int param3, int param4) {
        assertEquals(param1, StringUtil.formatTimestamp(param4 * 1000 + param3, param2));
    }

    static public Stream<Arguments> Provider_testFormatTimestamp_5to6() {
        return Stream.of(arguments("00:01:00", 123, 60, 1000), arguments("00:01:00.123", 123, 60, 1000));
    }

    @ParameterizedTest
    @MethodSource("Provider_testFormatTimestamp_7to8")
    public void testFormatTimestamp_7to8(String param1, int param2, int param3, int param4, int param5) {
        assertEquals(param1, StringUtil.formatTimestamp(param5 * 60 * param4 + param3, param2));
    }

    static public Stream<Arguments> Provider_testFormatTimestamp_7to8() {
        return Stream.of(arguments("01:00:00", 123, 1000, 60, 60), arguments("01:00:00.123", 123, 1000, 60, 60));
    }
}
