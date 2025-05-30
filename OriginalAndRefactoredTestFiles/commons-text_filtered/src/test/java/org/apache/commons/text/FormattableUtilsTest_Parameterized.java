package org.apache.commons.text;

import static java.util.FormattableFlags.LEFT_JUSTIFY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import java.util.Formattable;
import java.util.Formatter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class FormattableUtilsTest_Parameterized {

    static class SimplestFormattable implements Formattable {

        private final String text;

        SimplestFormattable(final String text) {
            this.text = text;
        }

        @Override
        public void formatTo(final Formatter formatter, final int flags, final int width, final int precision) {
            formatter.format(text);
        }
    }

    private Formatter createFormatter() {
        return new Formatter();
    }

    @Test
    public void testDefaultAppend_1() {
        assertEquals("foo", FormattableUtils.append("foo", createFormatter(), 0, -1, -1).toString());
    }

    @Test
    public void testDefaultAppend_2() {
        assertEquals("fo", FormattableUtils.append("foo", createFormatter(), 0, -1, 2).toString());
    }

    @ParameterizedTest
    @MethodSource("Provider_testAlternatePadCharAndEllipsis_1_11")
    public void testAlternatePadCharAndEllipsis_1_11(String param1, String param2, int param3, String param4, String param5, int param6, int param7) {
        assertEquals(param1, FormattableUtils.append(param2, createFormatter(), param3, -param6, -param7, param4, param5).toString());
    }

    static public Stream<Arguments> Provider_testAlternatePadCharAndEllipsis_1_11() {
        return Stream.of(arguments("foo", "foo", 0, "_", "*", 1, 1), arguments("foo", "foo", 0, "_", "+*", 1, 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testAlternatePadCharAndEllipsis_2_12")
    public void testAlternatePadCharAndEllipsis_2_12(String param1, String param2, int param3, int param4, String param5, String param6, int param7) {
        assertEquals(param1, FormattableUtils.append(param2, createFormatter(), param3, -param7, param4, param5, param6).toString());
    }

    static public Stream<Arguments> Provider_testAlternatePadCharAndEllipsis_2_12() {
        return Stream.of(arguments("f*", "foo", 0, 2, "_", "*", 1), arguments("+*", "foo", 0, 2, "_", "+*", 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testAlternatePadCharAndEllipsis_3to4_13to14")
    public void testAlternatePadCharAndEllipsis_3to4_13to14(String param1, String param2, int param3, int param4, String param5, String param6, int param7) {
        assertEquals(param1, FormattableUtils.append(param2, createFormatter(), param3, param4, -param7, param5, param6).toString());
    }

    static public Stream<Arguments> Provider_testAlternatePadCharAndEllipsis_3to4_13to14() {
        return Stream.of(arguments("_foo", "foo", 0, 4, "_", "*", 1), arguments("___foo", "foo", 0, 6, "_", "*", 1), arguments("_foo", "foo", 0, 4, "_", "+*", 1), arguments("___foo", "foo", 0, 6, "_", "+*", 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testAlternatePadCharAndEllipsis_5to6_15to16")
    public void testAlternatePadCharAndEllipsis_5to6_15to16(String param1, String param2, int param3, int param4, int param5, String param6, String param7) {
        assertEquals(param1, FormattableUtils.append(param2, createFormatter(), param3, param4, param5, param6, param7).toString());
    }

    static public Stream<Arguments> Provider_testAlternatePadCharAndEllipsis_5to6_15to16() {
        return Stream.of(arguments("_f*", "foo", 0, 3, 2, "_", "*"), arguments("___f*", "foo", 0, 5, 2, "_", "*"), arguments("_+*", "foo", 0, 3, 2, "_", "+*"), arguments("___+*", "foo", 0, 5, 2, "_", "+*"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testAlternatePadCharAndEllipsis_7to8_17to18")
    public void testAlternatePadCharAndEllipsis_7to8_17to18(String param1, String param2, int param3, String param4, String param5, int param6) {
        assertEquals(param1, FormattableUtils.append(param2, createFormatter(), LEFT_JUSTIFY, param3, -param6, param4, param5).toString());
    }

    static public Stream<Arguments> Provider_testAlternatePadCharAndEllipsis_7to8_17to18() {
        return Stream.of(arguments("foo_", "foo", 4, "_", "*", 1), arguments("foo___", "foo", 6, "_", "*", 1), arguments("foo_", "foo", 4, "_", "+*", 1), arguments("foo___", "foo", 6, "_", "+*", 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testAlternatePadCharAndEllipsis_9to10_19to20")
    public void testAlternatePadCharAndEllipsis_9to10_19to20(String param1, String param2, int param3, int param4, String param5, String param6) {
        assertEquals(param1, FormattableUtils.append(param2, createFormatter(), LEFT_JUSTIFY, param3, param4, param5, param6).toString());
    }

    static public Stream<Arguments> Provider_testAlternatePadCharAndEllipsis_9to10_19to20() {
        return Stream.of(arguments("f*_", "foo", 3, 2, "_", "*"), arguments("f*___", "foo", 5, 2, "_", "*"), arguments("+*_", "foo", 3, 2, "_", "+*"), arguments("+*___", "foo", 5, 2, "_", "+*"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testDefaultAppend_3to4")
    public void testDefaultAppend_3to4(String param1, String param2, int param3, int param4, int param5) {
        assertEquals(param1, FormattableUtils.append(param2, createFormatter(), param3, param4, -param5).toString());
    }

    static public Stream<Arguments> Provider_testDefaultAppend_3to4() {
        return Stream.of(arguments(" foo", "foo", 0, 4, 1), arguments("   foo", "foo", 0, 6, 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testDefaultAppend_5to6")
    public void testDefaultAppend_5to6(String param1, String param2, int param3, int param4, int param5) {
        assertEquals(param1, FormattableUtils.append(param2, createFormatter(), param3, param4, param5).toString());
    }

    static public Stream<Arguments> Provider_testDefaultAppend_5to6() {
        return Stream.of(arguments(" fo", "foo", 0, 3, 2), arguments("   fo", "foo", 0, 5, 2));
    }

    @ParameterizedTest
    @MethodSource("Provider_testDefaultAppend_7to8")
    public void testDefaultAppend_7to8(String param1, String param2, int param3, int param4) {
        assertEquals(param1, FormattableUtils.append(param2, createFormatter(), LEFT_JUSTIFY, param3, -param4).toString());
    }

    static public Stream<Arguments> Provider_testDefaultAppend_7to8() {
        return Stream.of(arguments("foo ", "foo", 4, 1), arguments("foo   ", "foo", 6, 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testDefaultAppend_9to10")
    public void testDefaultAppend_9to10(String param1, String param2, int param3, int param4) {
        assertEquals(param1, FormattableUtils.append(param2, createFormatter(), LEFT_JUSTIFY, param3, param4).toString());
    }

    static public Stream<Arguments> Provider_testDefaultAppend_9to10() {
        return Stream.of(arguments("fo ", "foo", 3, 2), arguments("fo   ", "foo", 5, 2));
    }

    @ParameterizedTest
    @MethodSource("Provider_testEllipsis_1_11")
    public void testEllipsis_1_11(String param1, String param2, int param3, String param4, int param5, int param6) {
        assertEquals(param1, FormattableUtils.append(param2, createFormatter(), param3, -param5, -param6, param4).toString());
    }

    static public Stream<Arguments> Provider_testEllipsis_1_11() {
        return Stream.of(arguments("foo", "foo", 0, "*", 1, 1), arguments("foo", "foo", 0, "+*", 1, 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testEllipsis_2_12")
    public void testEllipsis_2_12(String param1, String param2, int param3, int param4, String param5, int param6) {
        assertEquals(param1, FormattableUtils.append(param2, createFormatter(), param3, -param6, param4, param5).toString());
    }

    static public Stream<Arguments> Provider_testEllipsis_2_12() {
        return Stream.of(arguments("f*", "foo", 0, 2, "*", 1), arguments("+*", "foo", 0, 2, "+*", 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testEllipsis_3to4_13to14")
    public void testEllipsis_3to4_13to14(String param1, String param2, int param3, int param4, String param5, int param6) {
        assertEquals(param1, FormattableUtils.append(param2, createFormatter(), param3, param4, -param6, param5).toString());
    }

    static public Stream<Arguments> Provider_testEllipsis_3to4_13to14() {
        return Stream.of(arguments(" foo", "foo", 0, 4, "*", 1), arguments("   foo", "foo", 0, 6, "*", 1), arguments(" foo", "foo", 0, 4, "+*", 1), arguments("   foo", "foo", 0, 6, "+*", 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testEllipsis_5to6_15to16")
    public void testEllipsis_5to6_15to16(String param1, String param2, int param3, int param4, int param5, String param6) {
        assertEquals(param1, FormattableUtils.append(param2, createFormatter(), param3, param4, param5, param6).toString());
    }

    static public Stream<Arguments> Provider_testEllipsis_5to6_15to16() {
        return Stream.of(arguments(" f*", "foo", 0, 3, 2, "*"), arguments("   f*", "foo", 0, 5, 2, "*"), arguments(" +*", "foo", 0, 3, 2, "+*"), arguments("   +*", "foo", 0, 5, 2, "+*"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testEllipsis_7to8_17to18")
    public void testEllipsis_7to8_17to18(String param1, String param2, int param3, String param4, int param5) {
        assertEquals(param1, FormattableUtils.append(param2, createFormatter(), LEFT_JUSTIFY, param3, -param5, param4).toString());
    }

    static public Stream<Arguments> Provider_testEllipsis_7to8_17to18() {
        return Stream.of(arguments("foo ", "foo", 4, "*", 1), arguments("foo   ", "foo", 6, "*", 1), arguments("foo ", "foo", 4, "+*", 1), arguments("foo   ", "foo", 6, "+*", 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testEllipsis_9to10_19to20")
    public void testEllipsis_9to10_19to20(String param1, String param2, int param3, int param4, String param5) {
        assertEquals(param1, FormattableUtils.append(param2, createFormatter(), LEFT_JUSTIFY, param3, param4, param5).toString());
    }

    static public Stream<Arguments> Provider_testEllipsis_9to10_19to20() {
        return Stream.of(arguments("f* ", "foo", 3, 2, "*"), arguments("f*   ", "foo", 5, 2, "*"), arguments("+* ", "foo", 3, 2, "+*"), arguments("+*   ", "foo", 5, 2, "+*"));
    }
}
