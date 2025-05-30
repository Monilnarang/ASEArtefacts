package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class StringUtilsStartsEndsWithTest_Parameterized extends AbstractLangTest {

    private static final String foo = "foo";

    private static final String bar = "bar";

    private static final String foobar = "foobar";

    private static final String FOO = "FOO";

    private static final String BAR = "BAR";

    private static final String FOOBAR = "FOOBAR";

    @Test
    public void testEndsWith_1() {
        assertTrue(StringUtils.endsWith(null, null), "endsWith(null, null)");
    }

    @Test
    public void testEndsWith_2() {
        assertFalse(StringUtils.endsWith(FOOBAR, null), "endsWith(FOOBAR, null)");
    }

    @Test
    public void testEndsWith_3() {
        assertFalse(StringUtils.endsWith(null, FOO), "endsWith(null, FOO)");
    }

    @Test
    public void testEndsWith_4() {
        assertTrue(StringUtils.endsWith(FOOBAR, ""), "endsWith(FOOBAR, \"\")");
    }

    @Test
    public void testEndsWith_5() {
        assertFalse(StringUtils.endsWith(foobar, foo), "endsWith(foobar, foo)");
    }

    @Test
    public void testEndsWith_6() {
        assertFalse(StringUtils.endsWith(FOOBAR, FOO), "endsWith(FOOBAR, FOO)");
    }

    @Test
    public void testEndsWith_7() {
        assertFalse(StringUtils.endsWith(foobar, FOO), "endsWith(foobar, FOO)");
    }

    @Test
    public void testEndsWith_8() {
        assertFalse(StringUtils.endsWith(FOOBAR, foo), "endsWith(FOOBAR, foo)");
    }

    @Test
    public void testEndsWith_9() {
        assertFalse(StringUtils.endsWith(foo, foobar), "endsWith(foo, foobar)");
    }

    @Test
    public void testEndsWith_10() {
        assertFalse(StringUtils.endsWith(bar, foobar), "endsWith(foo, foobar)");
    }

    @Test
    public void testEndsWith_11() {
        assertTrue(StringUtils.endsWith(foobar, bar), "endsWith(foobar, bar)");
    }

    @Test
    public void testEndsWith_12() {
        assertTrue(StringUtils.endsWith(FOOBAR, BAR), "endsWith(FOOBAR, BAR)");
    }

    @Test
    public void testEndsWith_13() {
        assertFalse(StringUtils.endsWith(foobar, BAR), "endsWith(foobar, BAR)");
    }

    @Test
    public void testEndsWith_14() {
        assertFalse(StringUtils.endsWith(FOOBAR, bar), "endsWith(FOOBAR, bar)");
    }

    @Test
    public void testEndsWith_15() {
        assertTrue(StringUtils.endsWith("\u03B1\u03B2\u03B3\u03B4", "\u03B4"), "endsWith(\u03B1\u03B2\u03B3\u03B4, \u03B4)");
    }

    @Test
    public void testEndsWith_16() {
        assertFalse(StringUtils.endsWith("\u03B1\u03B2\u03B3\u03B4", "\u03B3\u0394"), "endsWith(\u03B1\u03B2\u03B3\u03B4, \u03B3\u0394)");
    }

    @Test
    public void testEndsWithAny_1() {
        assertFalse(StringUtils.endsWithAny(null, (String) null), "StringUtils.endsWithAny(null, null)");
    }

    @Test
    public void testEndsWithAny_2() {
        assertFalse(StringUtils.endsWithAny(null, "abc"), "StringUtils.endsWithAny(null, new String[] {abc})");
    }

    @Test
    public void testEndsWithAny_3() {
        assertFalse(StringUtils.endsWithAny("abcxyz", (String) null), "StringUtils.endsWithAny(abcxyz, null)");
    }

    @Test
    public void testEndsWithAny_6() {
        assertTrue(StringUtils.endsWithAny("abcxyz", null, "xyz", "abc"), "StringUtils.endsWithAny(abcxyz, new String[] {null, xyz, abc})");
    }

    @Test
    public void testEndsWithAny_7() {
        assertFalse(StringUtils.endsWithAny("defg", null, "xyz", "abc"), "StringUtils.endsWithAny(defg, new String[] {null, xyz, abc})");
    }

    @Test
    public void testEndsWithAny_9() {
        assertFalse(StringUtils.endsWithAny("abcXYZ", "def", "xyz"));
    }

    @Test
    public void testEndsWithAny_11() {
        assertFalse(StringUtils.endsWithAny("abcXYZ", (CharSequence) null));
    }

    @Test
    public void testEndsWithAny_12() {
        assertFalse(StringUtils.endsWithAny("abcXYZ", (CharSequence[]) null));
    }

    @Test
    public void testEndsWithAny_13() {
        assertTrue(StringUtils.endsWithAny("abcXYZ", ""));
    }

    @Test
    public void testEndsWithAny_14() {
        assertTrue(StringUtils.endsWithAny("abcxyz", new StringBuilder("abc"), new StringBuffer("xyz")), "StringUtils.endsWithAny(abcxyz, StringBuilder(abc), StringBuffer(xyz))");
    }

    @Test
    public void testEndsWithAny_15() {
        assertTrue(StringUtils.endsWithAny(new StringBuffer("abcxyz"), new StringBuilder("abc"), new StringBuffer("xyz")), "StringUtils.endsWithAny(StringBuffer(abcxyz), StringBuilder(abc), StringBuffer(xyz))");
    }

    @Test
    public void testEndsWithIgnoreCase_1() {
        assertTrue(StringUtils.endsWithIgnoreCase(null, null), "endsWithIgnoreCase(null, null)");
    }

    @Test
    public void testEndsWithIgnoreCase_2() {
        assertFalse(StringUtils.endsWithIgnoreCase(FOOBAR, null), "endsWithIgnoreCase(FOOBAR, null)");
    }

    @Test
    public void testEndsWithIgnoreCase_3() {
        assertFalse(StringUtils.endsWithIgnoreCase(null, FOO), "endsWithIgnoreCase(null, FOO)");
    }

    @Test
    public void testEndsWithIgnoreCase_4() {
        assertTrue(StringUtils.endsWithIgnoreCase(FOOBAR, ""), "endsWithIgnoreCase(FOOBAR, \"\")");
    }

    @Test
    public void testEndsWithIgnoreCase_5() {
        assertFalse(StringUtils.endsWithIgnoreCase(foobar, foo), "endsWithIgnoreCase(foobar, foo)");
    }

    @Test
    public void testEndsWithIgnoreCase_6() {
        assertFalse(StringUtils.endsWithIgnoreCase(FOOBAR, FOO), "endsWithIgnoreCase(FOOBAR, FOO)");
    }

    @Test
    public void testEndsWithIgnoreCase_7() {
        assertFalse(StringUtils.endsWithIgnoreCase(foobar, FOO), "endsWithIgnoreCase(foobar, FOO)");
    }

    @Test
    public void testEndsWithIgnoreCase_8() {
        assertFalse(StringUtils.endsWithIgnoreCase(FOOBAR, foo), "endsWithIgnoreCase(FOOBAR, foo)");
    }

    @Test
    public void testEndsWithIgnoreCase_9() {
        assertFalse(StringUtils.endsWithIgnoreCase(foo, foobar), "endsWithIgnoreCase(foo, foobar)");
    }

    @Test
    public void testEndsWithIgnoreCase_10() {
        assertFalse(StringUtils.endsWithIgnoreCase(bar, foobar), "endsWithIgnoreCase(foo, foobar)");
    }

    @Test
    public void testEndsWithIgnoreCase_11() {
        assertTrue(StringUtils.endsWithIgnoreCase(foobar, bar), "endsWithIgnoreCase(foobar, bar)");
    }

    @Test
    public void testEndsWithIgnoreCase_12() {
        assertTrue(StringUtils.endsWithIgnoreCase(FOOBAR, BAR), "endsWithIgnoreCase(FOOBAR, BAR)");
    }

    @Test
    public void testEndsWithIgnoreCase_13() {
        assertTrue(StringUtils.endsWithIgnoreCase(foobar, BAR), "endsWithIgnoreCase(foobar, BAR)");
    }

    @Test
    public void testEndsWithIgnoreCase_14() {
        assertTrue(StringUtils.endsWithIgnoreCase(FOOBAR, bar), "endsWithIgnoreCase(FOOBAR, bar)");
    }

    @Test
    public void testEndsWithIgnoreCase_17() {
        assertFalse(StringUtils.endsWithIgnoreCase("ABCDEF", "cde"));
    }

    @Test
    public void testEndsWithIgnoreCase_18() {
        assertTrue(StringUtils.endsWithIgnoreCase("\u03B1\u03B2\u03B3\u03B4", "\u0394"), "endsWith(\u03B1\u03B2\u03B3\u03B4, \u0394)");
    }

    @Test
    public void testEndsWithIgnoreCase_19() {
        assertFalse(StringUtils.endsWithIgnoreCase("\u03B1\u03B2\u03B3\u03B4", "\u0393"), "endsWith(\u03B1\u03B2\u03B3\u03B4, \u0393)");
    }

    @Test
    public void testStartsWith_1() {
        assertTrue(StringUtils.startsWith(null, null), "startsWith(null, null)");
    }

    @Test
    public void testStartsWith_2() {
        assertFalse(StringUtils.startsWith(FOOBAR, null), "startsWith(FOOBAR, null)");
    }

    @Test
    public void testStartsWith_3() {
        assertFalse(StringUtils.startsWith(null, FOO), "startsWith(null, FOO)");
    }

    @Test
    public void testStartsWith_4() {
        assertTrue(StringUtils.startsWith(FOOBAR, ""), "startsWith(FOOBAR, \"\")");
    }

    @Test
    public void testStartsWith_5() {
        assertTrue(StringUtils.startsWith(foobar, foo), "startsWith(foobar, foo)");
    }

    @Test
    public void testStartsWith_6() {
        assertTrue(StringUtils.startsWith(FOOBAR, FOO), "startsWith(FOOBAR, FOO)");
    }

    @Test
    public void testStartsWith_7() {
        assertFalse(StringUtils.startsWith(foobar, FOO), "startsWith(foobar, FOO)");
    }

    @Test
    public void testStartsWith_8() {
        assertFalse(StringUtils.startsWith(FOOBAR, foo), "startsWith(FOOBAR, foo)");
    }

    @Test
    public void testStartsWith_9() {
        assertFalse(StringUtils.startsWith(foo, foobar), "startsWith(foo, foobar)");
    }

    @Test
    public void testStartsWith_10() {
        assertFalse(StringUtils.startsWith(bar, foobar), "startsWith(foo, foobar)");
    }

    @Test
    public void testStartsWith_11() {
        assertFalse(StringUtils.startsWith(foobar, bar), "startsWith(foobar, bar)");
    }

    @Test
    public void testStartsWith_12() {
        assertFalse(StringUtils.startsWith(FOOBAR, BAR), "startsWith(FOOBAR, BAR)");
    }

    @Test
    public void testStartsWith_13() {
        assertFalse(StringUtils.startsWith(foobar, BAR), "startsWith(foobar, BAR)");
    }

    @Test
    public void testStartsWith_14() {
        assertFalse(StringUtils.startsWith(FOOBAR, bar), "startsWith(FOOBAR, bar)");
    }

    @Test
    public void testStartsWithAny_1() {
        assertFalse(StringUtils.startsWithAny(null, (String[]) null));
    }

    @Test
    public void testStartsWithAny_2() {
        assertFalse(StringUtils.startsWithAny(null, "abc"));
    }

    @Test
    public void testStartsWithAny_3() {
        assertFalse(StringUtils.startsWithAny("abcxyz", (String[]) null));
    }

    @Test
    public void testStartsWithAny_4() {
        assertFalse(StringUtils.startsWithAny("abcxyz"));
    }

    @Test
    public void testStartsWithAny_6() {
        assertTrue(StringUtils.startsWithAny("abcxyz", null, "xyz", "abc"));
    }

    @Test
    public void testStartsWithAny_11() {
        assertTrue(StringUtils.startsWithAny("abcxyz", new StringBuilder("xyz"), new StringBuffer("abc")), "StringUtils.startsWithAny(abcxyz, StringBuilder(xyz), StringBuffer(abc))");
    }

    @Test
    public void testStartsWithAny_12() {
        assertTrue(StringUtils.startsWithAny(new StringBuffer("abcxyz"), new StringBuilder("xyz"), new StringBuffer("abc")), "StringUtils.startsWithAny(StringBuffer(abcxyz), StringBuilder(xyz), StringBuffer(abc))");
    }

    @Test
    public void testStartsWithIgnoreCase_1() {
        assertTrue(StringUtils.startsWithIgnoreCase(null, null), "startsWithIgnoreCase(null, null)");
    }

    @Test
    public void testStartsWithIgnoreCase_2() {
        assertFalse(StringUtils.startsWithIgnoreCase(FOOBAR, null), "startsWithIgnoreCase(FOOBAR, null)");
    }

    @Test
    public void testStartsWithIgnoreCase_3() {
        assertFalse(StringUtils.startsWithIgnoreCase(null, FOO), "startsWithIgnoreCase(null, FOO)");
    }

    @Test
    public void testStartsWithIgnoreCase_4() {
        assertTrue(StringUtils.startsWithIgnoreCase(FOOBAR, ""), "startsWithIgnoreCase(FOOBAR, \"\")");
    }

    @Test
    public void testStartsWithIgnoreCase_5() {
        assertTrue(StringUtils.startsWithIgnoreCase(foobar, foo), "startsWithIgnoreCase(foobar, foo)");
    }

    @Test
    public void testStartsWithIgnoreCase_6() {
        assertTrue(StringUtils.startsWithIgnoreCase(FOOBAR, FOO), "startsWithIgnoreCase(FOOBAR, FOO)");
    }

    @Test
    public void testStartsWithIgnoreCase_7() {
        assertTrue(StringUtils.startsWithIgnoreCase(foobar, FOO), "startsWithIgnoreCase(foobar, FOO)");
    }

    @Test
    public void testStartsWithIgnoreCase_8() {
        assertTrue(StringUtils.startsWithIgnoreCase(FOOBAR, foo), "startsWithIgnoreCase(FOOBAR, foo)");
    }

    @Test
    public void testStartsWithIgnoreCase_9() {
        assertFalse(StringUtils.startsWithIgnoreCase(foo, foobar), "startsWithIgnoreCase(foo, foobar)");
    }

    @Test
    public void testStartsWithIgnoreCase_10() {
        assertFalse(StringUtils.startsWithIgnoreCase(bar, foobar), "startsWithIgnoreCase(foo, foobar)");
    }

    @Test
    public void testStartsWithIgnoreCase_11() {
        assertFalse(StringUtils.startsWithIgnoreCase(foobar, bar), "startsWithIgnoreCase(foobar, bar)");
    }

    @Test
    public void testStartsWithIgnoreCase_12() {
        assertFalse(StringUtils.startsWithIgnoreCase(FOOBAR, BAR), "startsWithIgnoreCase(FOOBAR, BAR)");
    }

    @Test
    public void testStartsWithIgnoreCase_13() {
        assertFalse(StringUtils.startsWithIgnoreCase(foobar, BAR), "startsWithIgnoreCase(foobar, BAR)");
    }

    @Test
    public void testStartsWithIgnoreCase_14() {
        assertFalse(StringUtils.startsWithIgnoreCase(FOOBAR, bar), "startsWithIgnoreCase(FOOBAR, bar)");
    }

    @ParameterizedTest
    @MethodSource("Provider_testEndsWithAny_4to5")
    public void testEndsWithAny_4to5(String param1, String param2, String param3) {
        assertTrue(StringUtils.endsWithAny(param2, param3), param1);
    }

    static public Stream<Arguments> Provider_testEndsWithAny_4to5() {
        return Stream.of(arguments("StringUtils.endsWithAny(abcxyz, new String[] {\"\"})", "abcxyz", ""), arguments("StringUtils.endsWithAny(abcxyz, new String[] {xyz})", "abcxyz", "xyz"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testEndsWithAny_8_10")
    public void testEndsWithAny_8_10(String param1, String param2, String param3) {
        assertTrue(StringUtils.endsWithAny(param1, param2, param3));
    }

    static public Stream<Arguments> Provider_testEndsWithAny_8_10() {
        return Stream.of(arguments("abcXYZ", "def", "XYZ"), arguments("abcXYZ", "def", "YZ"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testEndsWithIgnoreCase_15to16")
    public void testEndsWithIgnoreCase_15to16(String param1, String param2) {
        assertTrue(StringUtils.endsWithIgnoreCase(param1, param2));
    }

    static public Stream<Arguments> Provider_testEndsWithIgnoreCase_15to16() {
        return Stream.of(arguments("abcdef", "def"), arguments("ABCDEF", "def"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testStartsWithAny_5_8")
    public void testStartsWithAny_5_8(String param1, String param2) {
        assertTrue(StringUtils.startsWithAny(param1, param2));
    }

    static public Stream<Arguments> Provider_testStartsWithAny_5_8() {
        return Stream.of(arguments("abcxyz", "abc"), arguments("abcxyz", ""));
    }

    @ParameterizedTest
    @MethodSource("Provider_testStartsWithAny_7_9to10")
    public void testStartsWithAny_7_9to10(String param1, String param2, String param3) {
        assertFalse(StringUtils.startsWithAny(param1, param2, param3, "abcd"));
    }

    static public Stream<Arguments> Provider_testStartsWithAny_7_9to10() {
        return Stream.of(arguments("abcxyz", "xyz", "abcd"), arguments("abcxyz", "xyz", "ABCX"), arguments("ABCXYZ", "xyz", "abc"));
    }
}
