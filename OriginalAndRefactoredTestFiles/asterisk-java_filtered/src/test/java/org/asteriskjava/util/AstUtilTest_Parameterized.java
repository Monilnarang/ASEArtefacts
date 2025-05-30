package org.asteriskjava.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class AstUtilTest_Parameterized {

    @Test
    void testIsTrue_5() {
        assertFalse(AstUtil.isTrue("false"), "false must be false");
    }

    @Test
    void testIsTrue_6() {
        assertFalse(AstUtil.isTrue(null), "null must be false");
    }

    @Test
    void testIsNull_1() {
        assertTrue(AstUtil.isNull(null), "null must be null");
    }

    @Test
    void testIsEqual_1() {
        assertTrue(AstUtil.isEqual(null, null));
    }

    @Test
    void testIsEqual_2() {
        assertTrue(AstUtil.isEqual("", ""));
    }

    @Test
    void testIsEqual_3() {
        assertFalse(AstUtil.isEqual(null, ""));
    }

    @Test
    void testIsEqual_4() {
        assertFalse(AstUtil.isEqual("", null));
    }

    @Test
    void testIsEqual_5() {
        assertFalse(AstUtil.isEqual("", "1"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsTrue_1to4")
    void testIsTrue_1to4(String param1, String param2) {
        assertTrue(AstUtil.isTrue(param2), param1);
    }

    static public Stream<Arguments> Provider_testIsTrue_1to4() {
        return Stream.of(arguments("on must be true", "on"), arguments("ON must be true", "ON"), arguments("Enabled must be true", "Enabled"), arguments("true must be true", true));
    }

    @ParameterizedTest
    @MethodSource("Provider_testParseCallerIdName_1_1to2_2to3_3to10_13to14")
    void testParseCallerIdName_1_1to2_2to3_3to10_13to14(String param1, int param2, String param3) {
        assertEquals(param1, AstUtil.parseCallerId(param3)[param2]);
    }

    static public Stream<Arguments> Provider_testParseCallerIdName_1_1to2_2to3_3to10_13to14() {
        return Stream.of(arguments("Hans Wurst", 0, "\"Hans Wurst\"<1234>"), arguments("Hans Wurst", 0, "\"Hans Wurst\" <1234>"), arguments("Hans Wurst", 0, "\" Hans Wurst \" <1234>"), arguments("Hans Wurst", 0, "  \"Hans Wurst  \"   <1234>  "), arguments("Hans Wurst", 0, "  \"  Hans Wurst  \"   <1234>  "), arguments("Hans Wurst", 0, "Hans Wurst <1234>"), arguments("Hans Wurst", 0, " Hans Wurst  <1234>  "), arguments("Hans <Wurst>", 0, "\"Hans <Wurst>\" <1234>"), arguments("Hans Wurst", 0, "Hans Wurst"), arguments("Hans Wurst", 0, " Hans Wurst "), arguments("<1234", 0, " <1234 "), arguments("1234>", 0, " 1234> "), arguments(1234, 1, "\"Hans Wurst\"<1234>"), arguments(1234, 1, "\"Hans Wurst\" <1234>"), arguments(1234, 1, "Hans Wurst <  1234 >   "));
    }

    @ParameterizedTest
    @MethodSource("Provider_testParseCallerIdName_4to7_9to12_16to17")
    void testParseCallerIdName_4to7_9to12_16to17(int param1, String param2) {
        assertEquals(param1, AstUtil.parseCallerId("<1234>")[param2]);
    }

    static public Stream<Arguments> Provider_testParseCallerIdName_4to7_9to12_16to17() {
        return Stream.of(arguments(0, "<1234>"), arguments(0, " <1234> "), arguments(0, ""), arguments(0, " "), arguments(1, "\"Hans Wurst\""), arguments(1, "Hans Wurst"), arguments(1, "Hans Wurst <>"), arguments(1, "Hans Wurst <  > "), arguments(1, ""), arguments(1, " "));
    }

    @ParameterizedTest
    @MethodSource("Provider_testParseCallerIdName_8_15")
    void testParseCallerIdName_8_15(int param1) {
        assertEquals(param1, AstUtil.parseCallerId(null)[0]);
    }

    static public Stream<Arguments> Provider_testParseCallerIdName_8_15() {
        return Stream.of(arguments(0), arguments(1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsNull_2to3")
    void testIsNull_2to3(String param1, String param2) {
        assertTrue(AstUtil.isNull(param2), param1);
    }

    static public Stream<Arguments> Provider_testIsNull_2to3() {
        return Stream.of(arguments("unknown must be null", "unknown"), arguments("<unknown> must be null", "<unknown>"));
    }
}
