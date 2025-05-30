package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class StringUtilsIsTest_Parameterized extends AbstractLangTest {

    @Test
    public void testIsAlpha_1() {
        assertFalse(StringUtils.isAlpha(null));
    }

    @Test
    public void testIsAlphanumeric_1() {
        assertFalse(StringUtils.isAlphanumeric(null));
    }

    @Test
    public void testIsAlphanumericSpace_1() {
        assertFalse(StringUtils.isAlphanumericSpace(null));
    }

    @Test
    public void testIsAlphaspace_1() {
        assertFalse(StringUtils.isAlphaSpace(null));
    }

    @Test
    public void testIsAsciiPrintable_String_1() {
        assertFalse(StringUtils.isAsciiPrintable(null));
    }

    @Test
    public void testIsNumeric_1() {
        assertFalse(StringUtils.isNumeric(null));
    }

    @Test
    public void testIsNumericSpace_1() {
        assertFalse(StringUtils.isNumericSpace(null));
    }

    @Test
    public void testIsWhitespace_1() {
        assertFalse(StringUtils.isWhitespace(null));
    }

    @Test
    public void testIsWhitespace_12() {
        assertTrue(StringUtils.isWhitespace(StringUtilsTest.WHITESPACE));
    }

    @Test
    public void testIsWhitespace_13() {
        assertFalse(StringUtils.isWhitespace(StringUtilsTest.NON_WHITESPACE));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsAlpha_2to3_7to11")
    public void testIsAlpha_2to3_7to11(String param1) {
        assertFalse(StringUtils.isAlpha(param1));
    }

    static public Stream<Arguments> Provider_testIsAlpha_2to3_7to11() {
        return Stream.of(arguments(""), arguments(" "), arguments("ham kso"), arguments(1), arguments("hkHKHik6iUGHKJgU7tUJgKJGI87GIkug"), arguments("_"), arguments("hkHKHik*khbkuh"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsAlpha_4to6")
    public void testIsAlpha_4to6(String param1) {
        assertTrue(StringUtils.isAlpha(param1));
    }

    static public Stream<Arguments> Provider_testIsAlpha_4to6() {
        return Stream.of(arguments("a"), arguments("A"), arguments("kgKgKgKgkgkGkjkjlJlOKLgHdGdHgl"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsAlphanumeric_2to3_7_10to11")
    public void testIsAlphanumeric_2to3_7_10to11(String param1) {
        assertFalse(StringUtils.isAlphanumeric(param1));
    }

    static public Stream<Arguments> Provider_testIsAlphanumeric_2to3_7_10to11() {
        return Stream.of(arguments(""), arguments(" "), arguments("ham kso"), arguments("_"), arguments("hkHKHik*khbkuh"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsAlphanumeric_4to6_8to9")
    public void testIsAlphanumeric_4to6_8to9(String param1) {
        assertTrue(StringUtils.isAlphanumeric(param1));
    }

    static public Stream<Arguments> Provider_testIsAlphanumeric_4to6_8to9() {
        return Stream.of(arguments("a"), arguments("A"), arguments("kgKgKgKgkgkGkjkjlJlOKLgHdGdHgl"), arguments(1), arguments("hkHKHik6iUGHKJgU7tUJgKJGI87GIkug"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsAlphanumericSpace_2to9")
    public void testIsAlphanumericSpace_2to9(String param1) {
        assertTrue(StringUtils.isAlphanumericSpace(param1));
    }

    static public Stream<Arguments> Provider_testIsAlphanumericSpace_2to9() {
        return Stream.of(arguments(""), arguments(" "), arguments("a"), arguments("A"), arguments("kgKgKgKgkgkGkjkjlJlOKLgHdGdHgl"), arguments("ham kso"), arguments(1), arguments("hkHKHik6iUGHKJgU7tUJgKJGI87GIkug"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsAlphanumericSpace_10to11")
    public void testIsAlphanumericSpace_10to11(String param1) {
        assertFalse(StringUtils.isAlphanumericSpace(param1));
    }

    static public Stream<Arguments> Provider_testIsAlphanumericSpace_10to11() {
        return Stream.of(arguments("_"), arguments("hkHKHik*khbkuh"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsAlphaspace_2to7")
    public void testIsAlphaspace_2to7(String param1) {
        assertTrue(StringUtils.isAlphaSpace(param1));
    }

    static public Stream<Arguments> Provider_testIsAlphaspace_2to7() {
        return Stream.of(arguments(""), arguments(" "), arguments("a"), arguments("A"), arguments("kgKgKgKgkgkGkjkjlJlOKLgHdGdHgl"), arguments("ham kso"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsAlphaspace_8to11")
    public void testIsAlphaspace_8to11(int param1) {
        assertFalse(StringUtils.isAlphaSpace(param1));
    }

    static public Stream<Arguments> Provider_testIsAlphaspace_8to11() {
        return Stream.of(arguments(1), arguments("hkHKHik6iUGHKJgU7tUJgKJGI87GIkug"), arguments("_"), arguments("hkHKHik*khbkuh"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsAsciiPrintable_String_2to10_12to18_20to21")
    public void testIsAsciiPrintable_String_2to10_12to18_20to21(String param1) {
        assertTrue(StringUtils.isAsciiPrintable(param1));
    }

    static public Stream<Arguments> Provider_testIsAsciiPrintable_String_2to10_12to18_20to21() {
        return Stream.of(arguments(""), arguments(" "), arguments("a"), arguments("A"), arguments(1), arguments("Ceki"), arguments("!ab2c~"), arguments(1000), arguments("10 00"), arguments(10.00), arguments("10,00"), arguments("!ab-c~"), arguments("hkHK=Hik6i?UGH_KJgU7.tUJgKJ*GI87GI,kug"), arguments("\u0020"), arguments("\u0021"), arguments("\u007e"), arguments("G?lc?"), arguments("=?iso-8859-1?Q?G=FClc=FC?="));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsAsciiPrintable_String_11_19_22")
    public void testIsAsciiPrintable_String_11_19_22(String param1) {
        assertFalse(StringUtils.isAsciiPrintable(param1));
    }

    static public Stream<Arguments> Provider_testIsAsciiPrintable_String_11_19_22() {
        return Stream.of(arguments("10\t00"), arguments("\u007f"), arguments("G\u00fclc\u00fc"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsNumeric_2to7_11to18")
    public void testIsNumeric_2to7_11to18(String param1) {
        assertFalse(StringUtils.isNumeric(param1));
    }

    static public Stream<Arguments> Provider_testIsNumeric_2to7_11to18() {
        return Stream.of(arguments(""), arguments(" "), arguments("a"), arguments("A"), arguments("kgKgKgKgkgkGkjkjlJlOKLgHdGdHgl"), arguments("ham kso"), arguments("\u0967\u0968 \u0969"), arguments(2.3), arguments("10 00"), arguments("hkHKHik6iUGHKJgU7tUJgKJGI87GIkug"), arguments("_"), arguments("hkHKHik*khbkuh"), arguments(+123), arguments(-123));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsNumeric_8to10")
    public void testIsNumeric_8to10(int param1) {
        assertTrue(StringUtils.isNumeric(param1));
    }

    static public Stream<Arguments> Provider_testIsNumeric_8to10() {
        return Stream.of(arguments(1), arguments(1000), arguments("\u0967\u0968\u0969"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsNumericSpace_2to3_8to9_11to13")
    public void testIsNumericSpace_2to3_8to9_11to13(String param1) {
        assertTrue(StringUtils.isNumericSpace(param1));
    }

    static public Stream<Arguments> Provider_testIsNumericSpace_2to3_8to9_11to13() {
        return Stream.of(arguments(""), arguments(" "), arguments(1), arguments(1000), arguments("10 00"), arguments("\u0967\u0968\u0969"), arguments("\u0967\u0968 \u0969"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsNumericSpace_4to7_10_14to16")
    public void testIsNumericSpace_4to7_10_14to16(String param1) {
        assertFalse(StringUtils.isNumericSpace(param1));
    }

    static public Stream<Arguments> Provider_testIsNumericSpace_4to7_10_14to16() {
        return Stream.of(arguments("a"), arguments("A"), arguments("kgKgKgKgkgkGkjkjlJlOKLgHdGdHgl"), arguments("ham kso"), arguments(2.3), arguments("hkHKHik6iUGHKJgU7tUJgKJGI87GIkug"), arguments("_"), arguments("hkHKHik*khbkuh"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsWhitespace_2to4_6")
    public void testIsWhitespace_2to4_6(String param1) {
        assertTrue(StringUtils.isWhitespace(param1));
    }

    static public Stream<Arguments> Provider_testIsWhitespace_2to4_6() {
        return Stream.of(arguments(""), arguments(" "), arguments("\t \n \t"), arguments(" "));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsWhitespace_5_7to11")
    public void testIsWhitespace_5_7to11(String param1) {
        assertFalse(StringUtils.isWhitespace(param1));
    }

    static public Stream<Arguments> Provider_testIsWhitespace_5_7to11() {
        return Stream.of(arguments("\t aa\n \t"), arguments(" a "), arguments("a  "), arguments("  a"), arguments("aba"), arguments("a"));
    }
}
