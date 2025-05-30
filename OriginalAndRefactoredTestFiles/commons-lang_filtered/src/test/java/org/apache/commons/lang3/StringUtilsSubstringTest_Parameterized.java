package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class StringUtilsSubstringTest_Parameterized extends AbstractLangTest {

    private static final String FOO = "foo";

    private static final String BAR = "bar";

    private static final String BAZ = "baz";

    private static final String FOOBAR = "foobar";

    private static final String SENTENCE = "foo bar baz";

    @Test
    public void testCountMatches_String_1() {
        assertEquals(0, StringUtils.countMatches(null, null));
    }

    @Test
    public void testLeft_String_1() {
        assertSame(null, StringUtils.left(null, -1));
    }

    @Test
    public void testLeft_String_4() {
        assertEquals("", StringUtils.left("", -1));
    }

    @Test
    public void testLeft_String_7() {
        assertEquals("", StringUtils.left(FOOBAR, -1));
    }

    @Test
    public void testLeft_String_8() {
        assertEquals("", StringUtils.left(FOOBAR, 0));
    }

    @Test
    public void testLeft_String_9() {
        assertEquals(FOO, StringUtils.left(FOOBAR, 3));
    }

    @Test
    public void testLeft_String_10() {
        assertSame(FOOBAR, StringUtils.left(FOOBAR, 80));
    }

    @Test
    public void testMid_String_1() {
        assertSame(null, StringUtils.mid(null, -1, 0));
    }

    @Test
    public void testMid_String_2() {
        assertSame(null, StringUtils.mid(null, 0, -1));
    }

    @Test
    public void testMid_String_5() {
        assertEquals("", StringUtils.mid("", 0, -1));
    }

    @Test
    public void testMid_String_8() {
        assertEquals("", StringUtils.mid(FOOBAR, 3, -1));
    }

    @Test
    public void testMid_String_11() {
        assertEquals(FOO, StringUtils.mid(FOOBAR, 0, 3));
    }

    @Test
    public void testMid_String_13() {
        assertEquals(FOOBAR, StringUtils.mid(FOOBAR, 0, 80));
    }

    @Test
    public void testMid_String_16() {
        assertEquals(FOO, StringUtils.mid(FOOBAR, -1, 3));
    }

    @Test
    public void testRight_String_1() {
        assertSame(null, StringUtils.right(null, -1));
    }

    @Test
    public void testRight_String_4() {
        assertEquals("", StringUtils.right("", -1));
    }

    @Test
    public void testRight_String_7() {
        assertEquals("", StringUtils.right(FOOBAR, -1));
    }

    @Test
    public void testRight_String_8() {
        assertEquals("", StringUtils.right(FOOBAR, 0));
    }

    @Test
    public void testRight_String_9() {
        assertEquals(BAR, StringUtils.right(FOOBAR, 3));
    }

    @Test
    public void testRight_String_10() {
        assertSame(FOOBAR, StringUtils.right(FOOBAR, 80));
    }

    @Test
    public void testSubstring_StringInt_1() {
        assertNull(StringUtils.substring(null, 0));
    }

    @Test
    public void testSubstring_StringInt_4() {
        assertEquals("", StringUtils.substring(SENTENCE, 80));
    }

    @Test
    public void testSubstring_StringInt_5() {
        assertEquals(BAZ, StringUtils.substring(SENTENCE, 8));
    }

    @Test
    public void testSubstring_StringInt_6() {
        assertEquals(BAZ, StringUtils.substring(SENTENCE, -3));
    }

    @Test
    public void testSubstring_StringInt_7() {
        assertEquals(SENTENCE, StringUtils.substring(SENTENCE, 0));
    }

    @Test
    public void testSubstring_StringIntInt_7() {
        assertEquals(FOO, StringUtils.substring(SENTENCE, 0, 3));
    }

    @Test
    public void testSubstring_StringIntInt_8() {
        assertEquals("o", StringUtils.substring(SENTENCE, -9, 3));
    }

    @Test
    public void testSubstring_StringIntInt_9() {
        assertEquals(FOO, StringUtils.substring(SENTENCE, 0, -8));
    }

    @Test
    public void testSubstring_StringIntInt_10() {
        assertEquals("o", StringUtils.substring(SENTENCE, -9, -8));
    }

    @Test
    public void testSubstring_StringIntInt_11() {
        assertEquals(SENTENCE, StringUtils.substring(SENTENCE, 0, 80));
    }

    @Test
    public void testSubstringAfter_StringString_2() {
        assertNull(StringUtils.substringAfter(null, null));
    }

    @Test
    public void testSubstringAfterLast_StringString_2() {
        assertNull(StringUtils.substringAfterLast(null, null));
    }

    @Test
    public void testSubstringBefore_StringString_2() {
        assertNull(StringUtils.substringBefore(null, null));
    }

    @Test
    public void testSubstringBeforeLast_StringString_2() {
        assertNull(StringUtils.substringBeforeLast(null, null));
    }

    @Test
    public void testSubstringBetween_StringString_1() {
        assertNull(StringUtils.substringBetween(null, "tag"));
    }

    @Test
    public void testSubstringBetween_StringString_5() {
        assertNull(StringUtils.substringBetween("abc", null));
    }

    @Test
    public void testSubstringBetween_StringStringString_1() {
        assertNull(StringUtils.substringBetween(null, "", ""));
    }

    @Test
    public void testSubstringBetween_StringStringString_2() {
        assertNull(StringUtils.substringBetween("", null, ""));
    }

    @Test
    public void testSubstringBetween_StringStringString_3() {
        assertNull(StringUtils.substringBetween("", "", null));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCountMatches_char_1_3_9")
    public void testCountMatches_char_1_3_9(int param1, String param2) {
        assertEquals(param1, StringUtils.countMatches(param2, 'D'));
    }

    static public Stream<Arguments> Provider_testCountMatches_char_1_3_9() {
        return Stream.of(arguments(0, "D"), arguments(0, "DD"), arguments(0, "?"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCountMatches_char_2to4_4to8_10_12to16")
    public void testCountMatches_char_2to4_4to8_10_12to16(int param1, String param2, String param3) {
        assertEquals(param1, StringUtils.countMatches(param2, param3));
    }

    static public Stream<Arguments> Provider_testCountMatches_char_2to4_4to8_10_12to16() {
        return Stream.of(arguments(5, "one long someone sentence of one", " "), arguments(6, "one long someone sentence of one", "o"), arguments(4, "oooooooooooo", "ooo"), arguments(0, "x", ""), arguments(0, "", ""), arguments(3, "one long someone sentence of one", "one"), arguments(0, "one long someone sentence of one", "two"), arguments(4, "oooooooooooo", "ooo"), arguments(0, "", "?"), arguments(0, "abba", ""), arguments(2, "abba", "a"), arguments(1, "abba", "ab"), arguments(0, "abba", "xxx"), arguments(1, "ababa", "aba"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCountMatches_String_2_11")
    public void testCountMatches_String_2_11(int param1, String param2) {
        assertEquals(param1, StringUtils.countMatches(param2, null));
    }

    static public Stream<Arguments> Provider_testCountMatches_String_2_11() {
        return Stream.of(arguments(0, "blah"), arguments(0, "abba"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLeft_String_2to3")
    public void testLeft_String_2to3(int param1) {
        assertSame(param1, StringUtils.left(null, 0));
    }

    static public Stream<Arguments> Provider_testLeft_String_2to3() {
        return Stream.of(arguments(0), arguments(2));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLeft_String_5to6")
    public void testLeft_String_5to6(String param1, String param2, int param3) {
        assertEquals(param1, StringUtils.left(param2, param3));
    }

    static public Stream<Arguments> Provider_testLeft_String_5to6() {
        return Stream.of(arguments("", "", 0), arguments("", "", 2));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMid_String_3to4")
    public void testMid_String_3to4(int param1, int param2) {
        assertSame(param1, StringUtils.mid(param2, 3, 0));
    }

    static public Stream<Arguments> Provider_testMid_String_3to4() {
        return Stream.of(arguments(3, 0), arguments(3, 2));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMid_String_6to7")
    public void testMid_String_6to7(String param1, String param2, int param3, int param4) {
        assertEquals(param1, StringUtils.mid(param2, param3, param4));
    }

    static public Stream<Arguments> Provider_testMid_String_6to7() {
        return Stream.of(arguments("", "", 0, 0), arguments("", "", 0, 2));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMid_String_9to10_15")
    public void testMid_String_9to10_15(String param1, int param2, int param3) {
        assertEquals(param1, StringUtils.mid(FOOBAR, param2, param3));
    }

    static public Stream<Arguments> Provider_testMid_String_9to10_15() {
        return Stream.of(arguments("", 3, 0), arguments("b", 3, 1), arguments("", 9, 3));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMid_String_12_14")
    public void testMid_String_12_14(int param1, int param2) {
        assertEquals(BAR, StringUtils.mid(FOOBAR, param1, param2));
    }

    static public Stream<Arguments> Provider_testMid_String_12_14() {
        return Stream.of(arguments(3, 3), arguments(3, 80));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRight_String_2to3")
    public void testRight_String_2to3(int param1) {
        assertSame(param1, StringUtils.right(null, 0));
    }

    static public Stream<Arguments> Provider_testRight_String_2to3() {
        return Stream.of(arguments(0), arguments(2));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRight_String_5to6")
    public void testRight_String_5to6(String param1, String param2, int param3) {
        assertEquals(param1, StringUtils.right(param2, param3));
    }

    static public Stream<Arguments> Provider_testRight_String_5to6() {
        return Stream.of(arguments("", "", 0), arguments("", "", 2));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSubstring_StringInt_2to3_12to16")
    public void testSubstring_StringInt_2to3_12to16(String param1, String param2, int param3) {
        assertEquals(param1, StringUtils.substring(param2, param3));
    }

    static public Stream<Arguments> Provider_testSubstring_StringInt_2to3_12to16() {
        return Stream.of(arguments("", "", 0), arguments("", "", 2), arguments("abc", "abc", 0), arguments("bc", "abc", 1), arguments("c", "abc", 2), arguments("", "abc", 3), arguments("", "abc", 4));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSubstring_StringInt_8to11")
    public void testSubstring_StringInt_8to11(String param1, String param2, int param3) {
        assertEquals(param1, StringUtils.substring(param2, -param3));
    }

    static public Stream<Arguments> Provider_testSubstring_StringInt_8to11() {
        return Stream.of(arguments("abc", "abc", 4), arguments("abc", "abc", 3), arguments("bc", "abc", 2), arguments("c", "abc", 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSubstring_StringIntInt_1to2")
    public void testSubstring_StringIntInt_1to2(int param1, int param2) {
        assertNull(StringUtils.substring(param1, param2, 0));
    }

    static public Stream<Arguments> Provider_testSubstring_StringIntInt_1to2() {
        return Stream.of(arguments(0, 0), arguments(1, 2));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSubstring_StringIntInt_3to4")
    public void testSubstring_StringIntInt_3to4(String param1, String param2, int param3, int param4) {
        assertEquals(param1, StringUtils.substring(param2, param3, param4));
    }

    static public Stream<Arguments> Provider_testSubstring_StringIntInt_3to4() {
        return Stream.of(arguments("", "", 0, 0), arguments("", "", 1, 2));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSubstring_StringIntInt_5_13")
    public void testSubstring_StringIntInt_5_13(String param1, String param2, int param3, int param4) {
        assertEquals(param1, StringUtils.substring(param2, -param3, -param4));
    }

    static public Stream<Arguments> Provider_testSubstring_StringIntInt_5_13() {
        return Stream.of(arguments("", "", 2, 1), arguments("b", "abc", 2, 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSubstring_StringIntInt_6_12")
    public void testSubstring_StringIntInt_6_12(String param1, int param2, int param3) {
        assertEquals(param1, StringUtils.substring(SENTENCE, param2, param3));
    }

    static public Stream<Arguments> Provider_testSubstring_StringIntInt_6_12() {
        return Stream.of(arguments("", 8, 6), arguments("", 2, 2));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSubstringAfter_StringInt_1to4")
    public void testSubstringAfter_StringInt_1to4(int param1) {
        assertNull(StringUtils.substringAfter(param1, 0));
    }

    static public Stream<Arguments> Provider_testSubstringAfter_StringInt_1to4() {
        return Stream.of(arguments(0), arguments("X"), arguments(""), arguments("XX"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSubstringAfter_StringInt_1_3to6_6to7_7to9_9to10_10to14")
    public void testSubstringAfter_StringInt_1_3to6_6to7_7to9_9to10_10to14(String param1, String param2, int param3) {
        assertEquals(param1, StringUtils.substringAfter(param2, param3));
    }

    static public Stream<Arguments> Provider_testSubstringAfter_StringInt_1_3to6_6to7_7to9_9to10_10to14() {
        return Stream.of(arguments("", "", 0), arguments("", "", "X"), arguments("", "foo", 0), arguments("ot", "foot", "o"), arguments("bc", "abc", "a"), arguments("cba", "abcba", "b"), arguments("", "abc", "c"), arguments("", "abc", "d"), arguments("barXXbaz", "fooXXbarXXbaz", "XX"), arguments("", "", ""), arguments("", "", "XX"), arguments("ot", "foot", "o"), arguments("bc", "abc", "a"), arguments("cba", "abcba", "b"), arguments("", "abc", "c"), arguments("abc", "abc", ""), arguments("", "abc", "d"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSubstringAfter_StringString_5_8")
    public void testSubstringAfter_StringString_5_8(String param1, String param2) {
        assertEquals(param1, StringUtils.substringAfter(param2, null));
    }

    static public Stream<Arguments> Provider_testSubstringAfter_StringString_5_8() {
        return Stream.of(arguments("", ""), arguments("", "foo"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSubstringAfterLast_StringInt_1to4")
    public void testSubstringAfterLast_StringInt_1to4(int param1) {
        assertNull(StringUtils.substringAfterLast(param1, 0));
    }

    static public Stream<Arguments> Provider_testSubstringAfterLast_StringInt_1to4() {
        return Stream.of(arguments(0), arguments("X"), arguments(""), arguments("XX"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSubstringAfterLast_StringInt_1_3to6_6to7_7to9_9to10_10to11_11to15")
    public void testSubstringAfterLast_StringInt_1_3to6_6to7_7to9_9to10_10to11_11to15(String param1, String param2, int param3) {
        assertEquals(param1, StringUtils.substringAfterLast(param2, param3));
    }

    static public Stream<Arguments> Provider_testSubstringAfterLast_StringInt_1_3to6_6to7_7to9_9to10_10to11_11to15() {
        return Stream.of(arguments("", "", 0), arguments("", "", "a"), arguments("", "foo", 0), arguments("", "foo", "b"), arguments("t", "foot", "o"), arguments("bc", "abc", "a"), arguments("a", "abcba", "b"), arguments("", "abc", "c"), arguments("", "", "d"), arguments("baz", "fooXXbarXXbaz", "XX"), arguments("", "", ""), arguments("", "", "a"), arguments("", "foo", "b"), arguments("t", "foot", "o"), arguments("bc", "abc", "a"), arguments("a", "abcba", "b"), arguments("", "abc", "c"), arguments("", "", "d"), arguments("", "abc", ""));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSubstringAfterLast_StringString_5_8")
    public void testSubstringAfterLast_StringString_5_8(String param1, String param2) {
        assertEquals(param1, StringUtils.substringAfterLast(param2, null));
    }

    static public Stream<Arguments> Provider_testSubstringAfterLast_StringString_5_8() {
        return Stream.of(arguments("", ""), arguments("", "foo"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSubstringBefore_StringInt_1_1_4to6_6to7_7to9_9to10_10to11_11to12_12to15")
    public void testSubstringBefore_StringInt_1_1_4to6_6to7_7to9_9to10_10to11_11to12_12to15(String param1, String param2, String param3) {
        assertEquals(param1, StringUtils.substringBefore(param2, param3));
    }

    static public Stream<Arguments> Provider_testSubstringBefore_StringInt_1_1_4to6_6to7_7to9_9to10_10to11_11to12_12to15() {
        return Stream.of(arguments("foo", "fooXXbarXXbaz", "X"), arguments("", "", 0), arguments("", "", "X"), arguments("foo", "foo", 0), arguments("foo", "foo", "b"), arguments("f", "foot", "o"), arguments("", "abc", "a"), arguments("a", "abcba", "b"), arguments("ab", "abc", "c"), arguments("abc", "abc", 0), arguments("foo", "fooXXbarXXbaz", "XX"), arguments("", "", ""), arguments("", "", "XX"), arguments("foo", "foo", "b"), arguments("f", "foot", "o"), arguments("", "abc", "a"), arguments("a", "abcba", "b"), arguments("ab", "abc", "c"), arguments("", "abc", ""), arguments("abc", "abc", "X"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSubstringBefore_StringInt_2to3_3to4")
    public void testSubstringBefore_StringInt_2to3_3to4(int param1) {
        assertNull(StringUtils.substringBefore(param1, 0));
    }

    static public Stream<Arguments> Provider_testSubstringBefore_StringInt_2to3_3to4() {
        return Stream.of(arguments(0), arguments("X"), arguments(""), arguments("XX"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSubstringBefore_StringString_5_8")
    public void testSubstringBefore_StringString_5_8(String param1, String param2) {
        assertEquals(param1, StringUtils.substringBefore(param2, null));
    }

    static public Stream<Arguments> Provider_testSubstringBefore_StringString_5_8() {
        return Stream.of(arguments("", ""), arguments("foo", "foo"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSubstringBeforeLast_StringString_1_6to7_9to15_17to18")
    public void testSubstringBeforeLast_StringString_1_6to7_9to15_17to18(String param1, String param2, String param3) {
        assertEquals(param1, StringUtils.substringBeforeLast(param2, param3));
    }

    static public Stream<Arguments> Provider_testSubstringBeforeLast_StringString_1_6to7_9to15_17to18() {
        return Stream.of(arguments("fooXXbar", "fooXXbarXXbaz", "XX"), arguments("", "", ""), arguments("", "", "XX"), arguments("foo", "foo", "b"), arguments("fo", "foo", "o"), arguments("abc\r\n", "abc\r\n", "d"), arguments("abc", "abcdabc", "d"), arguments("abcdabc", "abcdabcd", "d"), arguments("a", "abc", "b"), arguments("abc ", "abc \n", "\n"), arguments("a", "a", ""), arguments("", "a", "a"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSubstringBeforeLast_StringString_3to4")
    public void testSubstringBeforeLast_StringString_3to4(String param1) {
        assertNull(StringUtils.substringBeforeLast(param1, ""));
    }

    static public Stream<Arguments> Provider_testSubstringBeforeLast_StringString_3to4() {
        return Stream.of(arguments(""), arguments("XX"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSubstringBeforeLast_StringString_5_8_16")
    public void testSubstringBeforeLast_StringString_5_8_16(String param1, String param2) {
        assertEquals(param1, StringUtils.substringBeforeLast(param2, null));
    }

    static public Stream<Arguments> Provider_testSubstringBeforeLast_StringString_5_8_16() {
        return Stream.of(arguments("", ""), arguments("foo", "foo"), arguments("a", "a"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSubstringBetween_StringString_2_4_6_8to10")
    public void testSubstringBetween_StringString_2_4_6_8to10(String param1, String param2, String param3) {
        assertEquals(param1, StringUtils.substringBetween(param2, param3));
    }

    static public Stream<Arguments> Provider_testSubstringBetween_StringString_2_4_6_8to10() {
        return Stream.of(arguments("", "", ""), arguments("", "    ", " "), arguments("", "abc", ""), arguments("bc", "abca", "a"), arguments("bc", "abcabca", "a"), arguments("bar", "\nbar\n", "\n"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSubstringBetween_StringString_3_7")
    public void testSubstringBetween_StringString_3_7(String param1, String param2) {
        assertNull(StringUtils.substringBetween(param1, param2));
    }

    static public Stream<Arguments> Provider_testSubstringBetween_StringString_3_7() {
        return Stream.of(arguments("", "abc"), arguments("abc", "a"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSubstringBetween_StringStringString_4to5_8to10")
    public void testSubstringBetween_StringStringString_4to5_8to10(String param1, String param2, String param3, String param4) {
        assertEquals(param1, StringUtils.substringBetween(param2, param3, param4));
    }

    static public Stream<Arguments> Provider_testSubstringBetween_StringStringString_4to5_8to10() {
        return Stream.of(arguments("", "", "", ""), arguments("", "foo", "", ""), arguments("", "    ", " ", "  "), arguments("bar", "<foo>bar</foo>", "<foo>", "</foo>"), arguments("abc", "yabczyabcz", "y", "z"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSubstringBetween_StringStringString_6to7")
    public void testSubstringBetween_StringStringString_6to7(String param1, String param2, String param3) {
        assertNull(StringUtils.substringBetween(param1, param2, param3));
    }

    static public Stream<Arguments> Provider_testSubstringBetween_StringStringString_6to7() {
        return Stream.of(arguments("foo", "", "]"), arguments("foo", "[", "]"));
    }
}
