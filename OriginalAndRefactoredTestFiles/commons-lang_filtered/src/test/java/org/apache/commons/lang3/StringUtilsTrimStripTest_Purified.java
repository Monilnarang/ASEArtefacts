package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class StringUtilsTrimStripTest_Purified extends AbstractLangTest {

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
    public void testStripAccents_4() {
        assertEquals("", StringUtils.stripAccents(""), "Failed empty String");
    }

    @Test
    public void testStripAccents_5() {
        assertEquals("control", StringUtils.stripAccents("control"), "Failed to handle non-accented text");
    }

    @Test
    public void testStripAccents_6() {
        assertEquals("eclair", StringUtils.stripAccents("\u00E9clair"), "Failed to handle easy example");
    }

    @Test
    public void testStripAccents_7() {
        assertEquals("ALOSZZCND aloszzcnd", StringUtils.stripAccents("\u0104\u0141\u00D3\u015A\u017B\u0179\u0106\u0143\u0110 \u0105\u0142\u00F3\u015B\u017C\u017A\u0107\u0144\u0111"));
    }

    @Test
    public void testStripAccents_8() {
        assertEquals("The cafe\u2019s pinata gave me deja vu.", StringUtils.stripAccents("The caf\u00e9\u2019s pi\u00f1ata gave me d\u00e9j\u00e0 vu."), "Failed to handle accented text");
    }

    @Test
    public void testStripAccents_9() {
        assertEquals("fluid quest", StringUtils.stripAccents("\ufb02uid que\ufb06"), "Failed to handle ligatures");
    }

    @Test
    public void testStripAccents_10() {
        assertEquals("a b c 1 2 3", StringUtils.stripAccents("\u1d43 \u1d47 \u1d9c \u00b9 \u00b2 \u00b3"), "Failed to handle superscript text");
    }

    @Test
    public void testStripAccents_11() {
        assertEquals("math italic", StringUtils.stripAccents("\uD835\uDC5A\uD835\uDC4E\uD835\uDC61\u210E \uD835\uDC56\uD835\uDC61\uD835\uDC4E\uD835\uDC59\uD835\uDC56\uD835\uDC50"), "Failed to handle UTF32 example");
    }

    @Test
    public void testStripAccents_12() {
        assertEquals("\uD83D\uDF01 \uD83D\uDF02 \uD83D\uDF03 \uD83D\uDF04", StringUtils.stripAccents("\uD83D\uDF01 \uD83D\uDF02 \uD83D\uDF03 \uD83D\uDF04"), "Failed to handle non-accented text");
    }

    @Test
    public void testStripAccentsUnicodeVulgarFractions_1() {
        assertEquals("1⁄4", StringUtils.stripAccents("\u00BC"));
    }

    @Test
    public void testStripAccentsUnicodeVulgarFractions_2() {
        assertEquals("1⁄2", StringUtils.stripAccents("\u00BD"));
    }

    @Test
    public void testStripAccentsUnicodeVulgarFractions_3() {
        assertEquals("3⁄4", StringUtils.stripAccents("\u00BE"));
    }

    @Test
    public void testStripEndStringString_1() {
        assertNull(StringUtils.stripEnd(null, null));
    }

    @Test
    public void testStripEndStringString_2() {
        assertEquals("", StringUtils.stripEnd("", null));
    }

    @Test
    public void testStripEndStringString_3() {
        assertEquals("", StringUtils.stripEnd("        ", null));
    }

    @Test
    public void testStripEndStringString_4() {
        assertEquals("  abc", StringUtils.stripEnd("  abc  ", null));
    }

    @Test
    public void testStripEndStringString_5() {
        assertEquals(StringUtilsTest.WHITESPACE + StringUtilsTest.NON_WHITESPACE, StringUtils.stripEnd(StringUtilsTest.WHITESPACE + StringUtilsTest.NON_WHITESPACE + StringUtilsTest.WHITESPACE, null));
    }

    @Test
    public void testStripEndStringString_6() {
        assertNull(StringUtils.stripEnd(null, ""));
    }

    @Test
    public void testStripEndStringString_7() {
        assertEquals("", StringUtils.stripEnd("", ""));
    }

    @Test
    public void testStripEndStringString_8() {
        assertEquals("        ", StringUtils.stripEnd("        ", ""));
    }

    @Test
    public void testStripEndStringString_9() {
        assertEquals("  abc  ", StringUtils.stripEnd("  abc  ", ""));
    }

    @Test
    public void testStripEndStringString_10() {
        assertEquals(StringUtilsTest.WHITESPACE, StringUtils.stripEnd(StringUtilsTest.WHITESPACE, ""));
    }

    @Test
    public void testStripEndStringString_11() {
        assertNull(StringUtils.stripEnd(null, " "));
    }

    @Test
    public void testStripEndStringString_12() {
        assertEquals("", StringUtils.stripEnd("", " "));
    }

    @Test
    public void testStripEndStringString_13() {
        assertEquals("", StringUtils.stripEnd("        ", " "));
    }

    @Test
    public void testStripEndStringString_14() {
        assertEquals("  abc", StringUtils.stripEnd("  abc  ", " "));
    }

    @Test
    public void testStripEndStringString_15() {
        assertNull(StringUtils.stripEnd(null, "ab"));
    }

    @Test
    public void testStripEndStringString_16() {
        assertEquals("", StringUtils.stripEnd("", "ab"));
    }

    @Test
    public void testStripEndStringString_17() {
        assertEquals("        ", StringUtils.stripEnd("        ", "ab"));
    }

    @Test
    public void testStripEndStringString_18() {
        assertEquals("  abc  ", StringUtils.stripEnd("  abc  ", "ab"));
    }

    @Test
    public void testStripEndStringString_19() {
        assertEquals("abc", StringUtils.stripEnd("abcabab", "ab"));
    }

    @Test
    public void testStripEndStringString_20() {
        assertEquals(StringUtilsTest.WHITESPACE, StringUtils.stripEnd(StringUtilsTest.WHITESPACE, ""));
    }

    @Test
    public void testStripStartStringString_1() {
        assertNull(StringUtils.stripStart(null, null));
    }

    @Test
    public void testStripStartStringString_2() {
        assertEquals("", StringUtils.stripStart("", null));
    }

    @Test
    public void testStripStartStringString_3() {
        assertEquals("", StringUtils.stripStart("        ", null));
    }

    @Test
    public void testStripStartStringString_4() {
        assertEquals("abc  ", StringUtils.stripStart("  abc  ", null));
    }

    @Test
    public void testStripStartStringString_5() {
        assertEquals(StringUtilsTest.NON_WHITESPACE + StringUtilsTest.WHITESPACE, StringUtils.stripStart(StringUtilsTest.WHITESPACE + StringUtilsTest.NON_WHITESPACE + StringUtilsTest.WHITESPACE, null));
    }

    @Test
    public void testStripStartStringString_6() {
        assertNull(StringUtils.stripStart(null, ""));
    }

    @Test
    public void testStripStartStringString_7() {
        assertEquals("", StringUtils.stripStart("", ""));
    }

    @Test
    public void testStripStartStringString_8() {
        assertEquals("        ", StringUtils.stripStart("        ", ""));
    }

    @Test
    public void testStripStartStringString_9() {
        assertEquals("  abc  ", StringUtils.stripStart("  abc  ", ""));
    }

    @Test
    public void testStripStartStringString_10() {
        assertEquals(StringUtilsTest.WHITESPACE, StringUtils.stripStart(StringUtilsTest.WHITESPACE, ""));
    }

    @Test
    public void testStripStartStringString_11() {
        assertNull(StringUtils.stripStart(null, " "));
    }

    @Test
    public void testStripStartStringString_12() {
        assertEquals("", StringUtils.stripStart("", " "));
    }

    @Test
    public void testStripStartStringString_13() {
        assertEquals("", StringUtils.stripStart("        ", " "));
    }

    @Test
    public void testStripStartStringString_14() {
        assertEquals("abc  ", StringUtils.stripStart("  abc  ", " "));
    }

    @Test
    public void testStripStartStringString_15() {
        assertNull(StringUtils.stripStart(null, "ab"));
    }

    @Test
    public void testStripStartStringString_16() {
        assertEquals("", StringUtils.stripStart("", "ab"));
    }

    @Test
    public void testStripStartStringString_17() {
        assertEquals("        ", StringUtils.stripStart("        ", "ab"));
    }

    @Test
    public void testStripStartStringString_18() {
        assertEquals("  abc  ", StringUtils.stripStart("  abc  ", "ab"));
    }

    @Test
    public void testStripStartStringString_19() {
        assertEquals("cabab", StringUtils.stripStart("abcabab", "ab"));
    }

    @Test
    public void testStripStartStringString_20() {
        assertEquals(StringUtilsTest.WHITESPACE, StringUtils.stripStart(StringUtilsTest.WHITESPACE, ""));
    }

    @Test
    public void testStripString_1() {
        assertNull(StringUtils.strip(null));
    }

    @Test
    public void testStripString_2() {
        assertEquals("", StringUtils.strip(""));
    }

    @Test
    public void testStripString_3() {
        assertEquals("", StringUtils.strip("        "));
    }

    @Test
    public void testStripString_4() {
        assertEquals("abc", StringUtils.strip("  abc  "));
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
    public void testStripStringString_2() {
        assertEquals("", StringUtils.strip("", null));
    }

    @Test
    public void testStripStringString_3() {
        assertEquals("", StringUtils.strip("        ", null));
    }

    @Test
    public void testStripStringString_4() {
        assertEquals("abc", StringUtils.strip("  abc  ", null));
    }

    @Test
    public void testStripStringString_5() {
        assertEquals(StringUtilsTest.NON_WHITESPACE, StringUtils.strip(StringUtilsTest.WHITESPACE + StringUtilsTest.NON_WHITESPACE + StringUtilsTest.WHITESPACE, null));
    }

    @Test
    public void testStripStringString_6() {
        assertNull(StringUtils.strip(null, ""));
    }

    @Test
    public void testStripStringString_7() {
        assertEquals("", StringUtils.strip("", ""));
    }

    @Test
    public void testStripStringString_8() {
        assertEquals("        ", StringUtils.strip("        ", ""));
    }

    @Test
    public void testStripStringString_9() {
        assertEquals("  abc  ", StringUtils.strip("  abc  ", ""));
    }

    @Test
    public void testStripStringString_10() {
        assertEquals(StringUtilsTest.WHITESPACE, StringUtils.strip(StringUtilsTest.WHITESPACE, ""));
    }

    @Test
    public void testStripStringString_11() {
        assertNull(StringUtils.strip(null, " "));
    }

    @Test
    public void testStripStringString_12() {
        assertEquals("", StringUtils.strip("", " "));
    }

    @Test
    public void testStripStringString_13() {
        assertEquals("", StringUtils.strip("        ", " "));
    }

    @Test
    public void testStripStringString_14() {
        assertEquals("abc", StringUtils.strip("  abc  ", " "));
    }

    @Test
    public void testStripStringString_15() {
        assertNull(StringUtils.strip(null, "ab"));
    }

    @Test
    public void testStripStringString_16() {
        assertEquals("", StringUtils.strip("", "ab"));
    }

    @Test
    public void testStripStringString_17() {
        assertEquals("        ", StringUtils.strip("        ", "ab"));
    }

    @Test
    public void testStripStringString_18() {
        assertEquals("  abc  ", StringUtils.strip("  abc  ", "ab"));
    }

    @Test
    public void testStripStringString_19() {
        assertEquals("c", StringUtils.strip("abcabab", "ab"));
    }

    @Test
    public void testStripStringString_20() {
        assertEquals(StringUtilsTest.WHITESPACE, StringUtils.strip(StringUtilsTest.WHITESPACE, ""));
    }

    @Test
    public void testStripToEmptyString_1() {
        assertEquals("", StringUtils.stripToEmpty(null));
    }

    @Test
    public void testStripToEmptyString_2() {
        assertEquals("", StringUtils.stripToEmpty(""));
    }

    @Test
    public void testStripToEmptyString_3() {
        assertEquals("", StringUtils.stripToEmpty("        "));
    }

    @Test
    public void testStripToEmptyString_4() {
        assertEquals("", StringUtils.stripToEmpty(StringUtilsTest.WHITESPACE));
    }

    @Test
    public void testStripToEmptyString_5() {
        assertEquals("ab c", StringUtils.stripToEmpty("  ab c  "));
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
    public void testStripToNullString_2() {
        assertNull(StringUtils.stripToNull(""));
    }

    @Test
    public void testStripToNullString_3() {
        assertNull(StringUtils.stripToNull("        "));
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
    public void testTrim_1() {
        assertEquals(FOO, StringUtils.trim(FOO + "  "));
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
    public void testTrim_4() {
        assertEquals(FOO, StringUtils.trim(FOO + ""));
    }

    @Test
    public void testTrim_5() {
        assertEquals("", StringUtils.trim(" \t\r\n\b "));
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
    public void testTrim_8() {
        assertEquals("", StringUtils.trim(""));
    }

    @Test
    public void testTrim_9() {
        assertNull(StringUtils.trim(null));
    }

    @Test
    public void testTrimToEmpty_1() {
        assertEquals(FOO, StringUtils.trimToEmpty(FOO + "  "));
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
    public void testTrimToEmpty_4() {
        assertEquals(FOO, StringUtils.trimToEmpty(FOO + ""));
    }

    @Test
    public void testTrimToEmpty_5() {
        assertEquals("", StringUtils.trimToEmpty(" \t\r\n\b "));
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
    public void testTrimToEmpty_8() {
        assertEquals("", StringUtils.trimToEmpty(""));
    }

    @Test
    public void testTrimToEmpty_9() {
        assertEquals("", StringUtils.trimToEmpty(null));
    }

    @Test
    public void testTrimToNull_1() {
        assertEquals(FOO, StringUtils.trimToNull(FOO + "  "));
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
    public void testTrimToNull_4() {
        assertEquals(FOO, StringUtils.trimToNull(FOO + ""));
    }

    @Test
    public void testTrimToNull_5() {
        assertNull(StringUtils.trimToNull(" \t\r\n\b "));
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
    public void testTrimToNull_8() {
        assertNull(StringUtils.trimToNull(""));
    }

    @Test
    public void testTrimToNull_9() {
        assertNull(StringUtils.trimToNull(null));
    }
}
