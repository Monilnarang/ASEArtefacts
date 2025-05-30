package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.TimeZone;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class FastTimeZoneTest_Parameterized extends AbstractLangTest {

    private static final int HOURS_23 = 23 * 60 * 60 * 1000;

    private static final int HOURS_2 = 2 * 60 * 60 * 1000;

    private static final int MINUTES_59 = 59 * 60 * 1000;

    private static final int MINUTES_5 = 5 * 60 * 1000;

    @Test
    public void testSign_4() {
        assertEquals(-HOURS_2, FastTimeZone.getGmtTimeZone("-2:00").getRawOffset());
    }

    @ParameterizedTest
    @MethodSource("Provider_testGmtPrefix_1_1_1_1")
    public void testGmtPrefix_1_1_1_1(String param1) {
        assertEquals(HOURS_23, FastTimeZone.getGmtTimeZone(param1).getRawOffset());
    }

    static public Stream<Arguments> Provider_testGmtPrefix_1_1_1_1() {
        return Stream.of(arguments("GMT+23:00"), arguments("23:00"), arguments(2300), arguments("+23:00"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGmtPrefix_2to3")
    public void testGmtPrefix_2to3(String param1) {
        assertEquals(-HOURS_23, FastTimeZone.getGmtTimeZone(param1).getRawOffset());
    }

    static public Stream<Arguments> Provider_testGmtPrefix_2to3() {
        return Stream.of(arguments("GMT-23:00"), arguments("-23:00"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testHoursColonMinutes_2_2_2")
    public void testHoursColonMinutes_2_2_2(String param1) {
        assertEquals(HOURS_2, FastTimeZone.getGmtTimeZone(param1).getRawOffset());
    }

    static public Stream<Arguments> Provider_testHoursColonMinutes_2_2_2() {
        return Stream.of(arguments("2:00"), arguments(0200), arguments("+2:00"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testHoursColonMinutes_3_3")
    public void testHoursColonMinutes_3_3(String param1) {
        assertEquals(MINUTES_59, FastTimeZone.getGmtTimeZone(param1).getRawOffset());
    }

    static public Stream<Arguments> Provider_testHoursColonMinutes_3_3() {
        return Stream.of(arguments("00:59"), arguments(0059));
    }

    @ParameterizedTest
    @MethodSource("Provider_testHoursColonMinutes_4_4")
    public void testHoursColonMinutes_4_4(String param1) {
        assertEquals(MINUTES_5, FastTimeZone.getGmtTimeZone(param1).getRawOffset());
    }

    static public Stream<Arguments> Provider_testHoursColonMinutes_4_4() {
        return Stream.of(arguments("00:5"), arguments(0005));
    }

    @ParameterizedTest
    @MethodSource("Provider_testHoursColonMinutes_5_5")
    public void testHoursColonMinutes_5_5(String param1) {
        assertEquals(HOURS_23 + MINUTES_59, FastTimeZone.getGmtTimeZone(param1).getRawOffset());
    }

    static public Stream<Arguments> Provider_testHoursColonMinutes_5_5() {
        return Stream.of(arguments("23:59"), arguments(2359));
    }

    @ParameterizedTest
    @MethodSource("Provider_testHoursColonMinutes_6_6")
    public void testHoursColonMinutes_6_6(String param1) {
        assertEquals(HOURS_2 + MINUTES_5, FastTimeZone.getGmtTimeZone(param1).getRawOffset());
    }

    static public Stream<Arguments> Provider_testHoursColonMinutes_6_6() {
        return Stream.of(arguments("2:5"), arguments(0205));
    }

    @ParameterizedTest
    @MethodSource("Provider_testZeroOffsetsReturnSingleton_1to2")
    public void testZeroOffsetsReturnSingleton_1to2(int param1) {
        assertEquals(FastTimeZone.getGmtTimeZone(), FastTimeZone.getTimeZone(param1));
    }

    static public Stream<Arguments> Provider_testZeroOffsetsReturnSingleton_1to2() {
        return Stream.of(arguments(+0), arguments(-0));
    }
}
