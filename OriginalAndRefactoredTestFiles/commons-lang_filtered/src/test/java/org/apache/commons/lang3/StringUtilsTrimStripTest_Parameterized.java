package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class StringUtilsTrimStripTest_Parameterized extends AbstractLangTest {

    private static final String FOO = "foo";

    @Test
    public void testStripAccents_1() {
        final String cue = "\u00C7\u00FA\u00EA";
        assertEquals("Cue", StringUtils.stripAccents(cue), "Failed to strip accents from " + cue);
    }

    @Test
    public void testStripAccents_2() {
        final String lots = "\u00C0\u00C1\u00C2\u00C3\u00C4\u00C5\u00C7\u00C8\u00C9\u00CA\u00CB\u00CC\u00CD\u00CE\u00CF\u00D1\u00D2\u00D3" + "\u00D4\u00D5\u00D6\u00D9\u00DA\u00DB\u00DC\u00DD";
        assertEquals("AAAAAACEEEEIIIINOOOOOUUUUY", StringUtils.stripAccents(lots), "Failed to strip accents from " + lots);
    }

    @Test
    public void testStripAccents_3() {
        assertNull(StringUtils.stripAccents(null), "Failed null safety");
    }

    @Test
    public void testStripEndStringString_1() {
        assertNull(StringUtils.stripEnd(null, null));
    }

    @Test
    public void testStripEndStringString_5() {
        assertEquals(StringUtilsTest.WHITESPACE + StringUtilsTest.NON_WHITESPACE, StringUtils.stripEnd(StringUtilsTest.WHITESPACE + StringUtilsTest.NON_WHITESPACE + StringUtilsTest.WHITESPACE, null));
    }

    @Test
    public void testStripStartStringString_1() {
        assertNull(StringUtils.stripStart(null, null));
    }

    @Test
    public void testStripStartStringString_5() {
        assertEquals(StringUtilsTest.NON_WHITESPACE + StringUtilsTest.WHITESPACE, StringUtils.stripStart(StringUtilsTest.WHITESPACE + StringUtilsTest.NON_WHITESPACE + StringUtilsTest.WHITESPACE, null));
    }

    @Test
    public void testStripString_1() {
        assertNull(StringUtils.strip(null));
    }

    @Test
    public void testStripString_5() {
        assertEquals(StringUtilsTest.NON_WHITESPACE, StringUtils.strip(StringUtilsTest.WHITESPACE + StringUtilsTest.NON_WHITESPACE + StringUtilsTest.WHITESPACE));
    }

    @Test
    public void testStripStringString_1() {
        assertNull(StringUtils.strip(null, null));
    }

    @Test
    public void testStripStringString_5() {
        assertEquals(StringUtilsTest.NON_WHITESPACE, StringUtils.strip(StringUtilsTest.WHITESPACE + StringUtilsTest.NON_WHITESPACE + StringUtilsTest.WHITESPACE, null));
    }

    @Test
    public void testStripToEmptyString_1() {
        assertEquals("", StringUtils.stripToEmpty(null));
    }

    @Test
    public void testStripToEmptyString_4() {
        assertEquals("", StringUtils.stripToEmpty(StringUtilsTest.WHITESPACE));
    }

    @Test
    public void testStripToEmptyString_6() {
        assertEquals(StringUtilsTest.NON_WHITESPACE, StringUtils.stripToEmpty(StringUtilsTest.WHITESPACE + StringUtilsTest.NON_WHITESPACE + StringUtilsTest.WHITESPACE));
    }

    @Test
    public void testStripToNullString_1() {
        assertNull(StringUtils.stripToNull(null));
    }

    @Test
    public void testStripToNullString_4() {
        assertNull(StringUtils.stripToNull(StringUtilsTest.WHITESPACE));
    }

    @Test
    public void testStripToNullString_5() {
        assertEquals("ab c", StringUtils.stripToNull("  ab c  "));
    }

    @Test
    public void testStripToNullString_6() {
        assertEquals(StringUtilsTest.NON_WHITESPACE, StringUtils.stripToNull(StringUtilsTest.WHITESPACE + StringUtilsTest.NON_WHITESPACE + StringUtilsTest.WHITESPACE));
    }

    @Test
    public void testTrim_2() {
        assertEquals(FOO, StringUtils.trim(" " + FOO + "  "));
    }

    @Test
    public void testTrim_3() {
        assertEquals(FOO, StringUtils.trim(" " + FOO));
    }

    @Test
    public void testTrim_6() {
        assertEquals("", StringUtils.trim(StringUtilsTest.TRIMMABLE));
    }

    @Test
    public void testTrim_7() {
        assertEquals(StringUtilsTest.NON_TRIMMABLE, StringUtils.trim(StringUtilsTest.NON_TRIMMABLE));
    }

    @Test
    public void testTrim_9() {
        assertNull(StringUtils.trim(null));
    }

    @Test
    public void testTrimToEmpty_2() {
        assertEquals(FOO, StringUtils.trimToEmpty(" " + FOO + "  "));
    }

    @Test
    public void testTrimToEmpty_3() {
        assertEquals(FOO, StringUtils.trimToEmpty(" " + FOO));
    }

    @Test
    public void testTrimToEmpty_6() {
        assertEquals("", StringUtils.trimToEmpty(StringUtilsTest.TRIMMABLE));
    }

    @Test
    public void testTrimToEmpty_7() {
        assertEquals(StringUtilsTest.NON_TRIMMABLE, StringUtils.trimToEmpty(StringUtilsTest.NON_TRIMMABLE));
    }

    @Test
    public void testTrimToEmpty_9() {
        assertEquals("", StringUtils.trimToEmpty(null));
    }

    @Test
    public void testTrimToNull_2() {
        assertEquals(FOO, StringUtils.trimToNull(" " + FOO + "  "));
    }

    @Test
    public void testTrimToNull_3() {
        assertEquals(FOO, StringUtils.trimToNull(" " + FOO));
    }

    @Test
    public void testTrimToNull_6() {
        assertNull(StringUtils.trimToNull(StringUtilsTest.TRIMMABLE));
    }

    @Test
    public void testTrimToNull_7() {
        assertEquals(StringUtilsTest.NON_TRIMMABLE, StringUtils.trimToNull(StringUtilsTest.NON_TRIMMABLE));
    }

    @Test
    public void testTrimToNull_9() {
        assertNull(StringUtils.trimToNull(null));
    }

    @ParameterizedTest
    @MethodSource("Provider_testStripAccents_4to6_8to12")
    public void testStripAccents_4to6_8to12(String param1, String param2, String param3) {
        assertEquals(param1, StringUtils.stripAccents(param3), param2);
    }

    static public Stream<Arguments> Provider_testStripAccents_4to6_8to12() {
        return Stream.of(arguments("", "Failed empty String", ""), arguments("control", "Failed to handle non-accented text", "control"), arguments("eclair", "Failed to handle easy example", "\u00E9clair"), arguments("The cafe\u2019s pinata gave me deja vu.", "Failed to handle accented text", "The caf\u00e9\u2019s pi\u00f1ata gave me d\u00e9j\u00e0 vu."), arguments("fluid quest", "Failed to handle ligatures", "\ufb02uid que\ufb06"), arguments("a b c 1 2 3", "Failed to handle superscript text", "\u1d43 \u1d47 \u1d9c \u00b9 \u00b2 \u00b3"), arguments("math italic", "Failed to handle UTF32 example", "\uD835\uDC5A\uD835\uDC4E\uD835\uDC61\u210E \uD835\uDC56\uD835\uDC61\uD835\uDC4E\uD835\uDC59\uD835\uDC56\uD835\uDC50"), arguments("\uD83D\uDF01 \uD83D\uDF02 \uD83D\uDF03 \uD83D\uDF04", "Failed to handle non-accented text", "\uD83D\uDF01 \uD83D\uDF02 \uD83D\uDF03 \uD83D\uDF04"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testStripAccents_1to3_7")
    public void testStripAccents_1to3_7(String param1, String param2) {
        assertEquals(param1, StringUtils.stripAccents(param2));
    }

    static public Stream<Arguments> Provider_testStripAccents_1to3_7() {
        return Stream.of(arguments("ALOSZZCND aloszzcnd", "\u0104\u0141\u00D3\u015A\u017B\u0179\u0106\u0143\u0110 \u0105\u0142\u00F3\u015B\u017C\u017A\u0107\u0144\u0111"), arguments("1⁄4", "\u00BC"), arguments("1⁄2", "\u00BD"), arguments("3⁄4", "\u00BE"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testStripEndStringString_2to4")
    public void testStripEndStringString_2to4(String param1, String param2) {
        assertEquals(param1, StringUtils.stripEnd(param2, null));
    }

    static public Stream<Arguments> Provider_testStripEndStringString_2to4() {
        return Stream.of(arguments("", ""), arguments("", "        "), arguments("  abc", "  abc  "));
    }

    @ParameterizedTest
    @MethodSource("Provider_testStripEndStringString_6_11_15")
    public void testStripEndStringString_6_11_15(String param1) {
        assertNull(StringUtils.stripEnd(param1, ""));
    }

    static public Stream<Arguments> Provider_testStripEndStringString_6_11_15() {
        return Stream.of(arguments(""), arguments(" "), arguments("ab"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testStripEndStringString_7to9_12to14_16to19")
    public void testStripEndStringString_7to9_12to14_16to19(String param1, String param2, String param3) {
        assertEquals(param1, StringUtils.stripEnd(param2, param3));
    }

    static public Stream<Arguments> Provider_testStripEndStringString_7to9_12to14_16to19() {
        return Stream.of(arguments("", "", ""), arguments("        ", "        ", ""), arguments("  abc  ", "  abc  ", ""), arguments("", "", " "), arguments("", "        ", " "), arguments("  abc", "  abc  ", " "), arguments("", "", "ab"), arguments("        ", "        ", "ab"), arguments("  abc  ", "  abc  ", "ab"), arguments("abc", "abcabab", "ab"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testStripEndStringString_10_20")
    public void testStripEndStringString_10_20(String param1) {
        assertEquals(StringUtilsTest.WHITESPACE, StringUtils.stripEnd(StringUtilsTest.WHITESPACE, param1));
    }

    static public Stream<Arguments> Provider_testStripEndStringString_10_20() {
        return Stream.of(arguments(""), arguments(""));
    }

    @ParameterizedTest
    @MethodSource("Provider_testStripStartStringString_2to4")
    public void testStripStartStringString_2to4(String param1, String param2) {
        assertEquals(param1, StringUtils.stripStart(param2, null));
    }

    static public Stream<Arguments> Provider_testStripStartStringString_2to4() {
        return Stream.of(arguments("", ""), arguments("", "        "), arguments("abc  ", "  abc  "));
    }

    @ParameterizedTest
    @MethodSource("Provider_testStripStartStringString_6_11_15")
    public void testStripStartStringString_6_11_15(String param1) {
        assertNull(StringUtils.stripStart(param1, ""));
    }

    static public Stream<Arguments> Provider_testStripStartStringString_6_11_15() {
        return Stream.of(arguments(""), arguments(" "), arguments("ab"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testStripStartStringString_7to9_12to14_16to19")
    public void testStripStartStringString_7to9_12to14_16to19(String param1, String param2, String param3) {
        assertEquals(param1, StringUtils.stripStart(param2, param3));
    }

    static public Stream<Arguments> Provider_testStripStartStringString_7to9_12to14_16to19() {
        return Stream.of(arguments("", "", ""), arguments("        ", "        ", ""), arguments("  abc  ", "  abc  ", ""), arguments("", "", " "), arguments("", "        ", " "), arguments("abc  ", "  abc  ", " "), arguments("", "", "ab"), arguments("        ", "        ", "ab"), arguments("  abc  ", "  abc  ", "ab"), arguments("cabab", "abcabab", "ab"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testStripStartStringString_10_20")
    public void testStripStartStringString_10_20(String param1) {
        assertEquals(StringUtilsTest.WHITESPACE, StringUtils.stripStart(StringUtilsTest.WHITESPACE, param1));
    }

    static public Stream<Arguments> Provider_testStripStartStringString_10_20() {
        return Stream.of(arguments(""), arguments(""));
    }

    @ParameterizedTest
    @MethodSource("Provider_testStripString_2to4")
    public void testStripString_2to4(String param1, String param2) {
        assertEquals(param1, StringUtils.strip(param2));
    }

    static public Stream<Arguments> Provider_testStripString_2to4() {
        return Stream.of(arguments("", ""), arguments("", "        "), arguments("abc", "  abc  "));
    }

    @ParameterizedTest
    @MethodSource("Provider_testStripStringString_2to4")
    public void testStripStringString_2to4(String param1, String param2) {
        assertEquals(param1, StringUtils.strip(param2, null));
    }

    static public Stream<Arguments> Provider_testStripStringString_2to4() {
        return Stream.of(arguments("", ""), arguments("", "        "), arguments("abc", "  abc  "));
    }

    @ParameterizedTest
    @MethodSource("Provider_testStripStringString_6_11_15")
    public void testStripStringString_6_11_15(String param1) {
        assertNull(StringUtils.strip(param1, ""));
    }

    static public Stream<Arguments> Provider_testStripStringString_6_11_15() {
        return Stream.of(arguments(""), arguments(" "), arguments("ab"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testStripStringString_7to9_12to14_16to19")
    public void testStripStringString_7to9_12to14_16to19(String param1, String param2, String param3) {
        assertEquals(param1, StringUtils.strip(param2, param3));
    }

    static public Stream<Arguments> Provider_testStripStringString_7to9_12to14_16to19() {
        return Stream.of(arguments("", "", ""), arguments("        ", "        ", ""), arguments("  abc  ", "  abc  ", ""), arguments("", "", " "), arguments("", "        ", " "), arguments("abc", "  abc  ", " "), arguments("", "", "ab"), arguments("        ", "        ", "ab"), arguments("  abc  ", "  abc  ", "ab"), arguments("c", "abcabab", "ab"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testStripStringString_10_20")
    public void testStripStringString_10_20(String param1) {
        assertEquals(StringUtilsTest.WHITESPACE, StringUtils.strip(StringUtilsTest.WHITESPACE, param1));
    }

    static public Stream<Arguments> Provider_testStripStringString_10_20() {
        return Stream.of(arguments(""), arguments(""));
    }

    @ParameterizedTest
    @MethodSource("Provider_testStripToEmptyString_2to3_5")
    public void testStripToEmptyString_2to3_5(String param1, String param2) {
        assertEquals(param1, StringUtils.stripToEmpty(param2));
    }

    static public Stream<Arguments> Provider_testStripToEmptyString_2to3_5() {
        return Stream.of(arguments("", ""), arguments("", "        "), arguments("ab c", "  ab c  "));
    }

    @ParameterizedTest
    @MethodSource("Provider_testStripToNullString_2to3")
    public void testStripToNullString_2to3(String param1) {
        assertNull(StringUtils.stripToNull(param1));
    }

    static public Stream<Arguments> Provider_testStripToNullString_2to3() {
        return Stream.of(arguments(""), arguments("        "));
    }

    @ParameterizedTest
    @MethodSource("Provider_testTrim_1_4")
    public void testTrim_1_4(String param1) {
        assertEquals(FOO, StringUtils.trim(FOO + param1));
    }

    static public Stream<Arguments> Provider_testTrim_1_4() {
        return Stream.of(arguments("  "), arguments(""));
    }

    @ParameterizedTest
    @MethodSource("Provider_testTrim_5_8")
    public void testTrim_5_8(String param1, String param2) {
        assertEquals(param1, StringUtils.trim(param2));
    }

    static public Stream<Arguments> Provider_testTrim_5_8() {
        return Stream.of(arguments("", " \t\r\n\b "), arguments("", ""));
    }

    @ParameterizedTest
    @MethodSource("Provider_testTrimToEmpty_1_4")
    public void testTrimToEmpty_1_4(String param1) {
        assertEquals(FOO, StringUtils.trimToEmpty(FOO + param1));
    }

    static public Stream<Arguments> Provider_testTrimToEmpty_1_4() {
        return Stream.of(arguments("  "), arguments(""));
    }

    @ParameterizedTest
    @MethodSource("Provider_testTrimToEmpty_5_8")
    public void testTrimToEmpty_5_8(String param1, String param2) {
        assertEquals(param1, StringUtils.trimToEmpty(param2));
    }

    static public Stream<Arguments> Provider_testTrimToEmpty_5_8() {
        return Stream.of(arguments("", " \t\r\n\b "), arguments("", ""));
    }

    @ParameterizedTest
    @MethodSource("Provider_testTrimToNull_1_4")
    public void testTrimToNull_1_4(String param1) {
        assertEquals(FOO, StringUtils.trimToNull(FOO + param1));
    }

    static public Stream<Arguments> Provider_testTrimToNull_1_4() {
        return Stream.of(arguments("  "), arguments(""));
    }

    @ParameterizedTest
    @MethodSource("Provider_testTrimToNull_5_8")
    public void testTrimToNull_5_8(String param1) {
        assertNull(StringUtils.trimToNull(param1));
    }

    static public Stream<Arguments> Provider_testTrimToNull_5_8() {
        return Stream.of(arguments(" \t\r\n\b "), arguments(""));
    }
}
