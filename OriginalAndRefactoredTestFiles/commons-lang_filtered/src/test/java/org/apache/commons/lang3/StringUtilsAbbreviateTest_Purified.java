package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class StringUtilsAbbreviateTest_Purified {

    private void assertAbbreviateWithAbbrevMarkerAndOffset(final String expected, final String abbrevMarker, final int offset, final int maxWidth) {
        final String abcdefghijklmno = "abcdefghijklmno";
        final String message = "abbreviate(String,String,int,int) failed";
        final String actual = StringUtils.abbreviate(abcdefghijklmno, abbrevMarker, offset, maxWidth);
        if (offset >= 0 && offset < abcdefghijklmno.length()) {
            assertTrue(actual.indexOf((char) ('a' + offset)) != -1, message + " -- should contain offset character");
        }
        assertTrue(actual.length() <= maxWidth, () -> message + " -- should not be greater than maxWidth");
        assertEquals(expected, actual, message);
    }

    private void assertAbbreviateWithOffset(final String expected, final int offset, final int maxWidth) {
        final String abcdefghijklmno = "abcdefghijklmno";
        final String message = "abbreviate(String,int,int) failed";
        final String actual = StringUtils.abbreviate(abcdefghijklmno, offset, maxWidth);
        if (offset >= 0 && offset < abcdefghijklmno.length()) {
            assertTrue(actual.indexOf((char) ('a' + offset)) != -1, message + " -- should contain offset character");
        }
        assertTrue(actual.length() <= maxWidth, () -> message + " -- should not be greater than maxWidth");
        assertEquals(expected, actual, message);
    }

    @Test
    public void testAbbreviateMiddle_1() {
        assertNull(StringUtils.abbreviateMiddle(null, null, 0));
    }

    @Test
    public void testAbbreviateMiddle_2() {
        assertEquals("abc", StringUtils.abbreviateMiddle("abc", null, 0));
    }

    @Test
    public void testAbbreviateMiddle_3() {
        assertEquals("abc", StringUtils.abbreviateMiddle("abc", ".", 0));
    }

    @Test
    public void testAbbreviateMiddle_4() {
        assertEquals("abc", StringUtils.abbreviateMiddle("abc", ".", 3));
    }

    @Test
    public void testAbbreviateMiddle_5() {
        assertEquals("ab.f", StringUtils.abbreviateMiddle("abcdef", ".", 4));
    }

    @Test
    public void testAbbreviateMiddle_6() {
        assertEquals("A very long text with un...f the text is complete.", StringUtils.abbreviateMiddle("A very long text with unimportant stuff in the middle but interesting start and " + "end to see if the text is complete.", "...", 50));
    }

    @Test
    public void testAbbreviateMiddle_7_testMerged_7() {
        final String longText = "Start text" + StringUtils.repeat("x", 10000) + "Close text";
        assertEquals("Start text->Close text", StringUtils.abbreviateMiddle(longText, "->", 22));
        assertEquals("abc", StringUtils.abbreviateMiddle("abc", ".", -1));
        assertEquals("abc", StringUtils.abbreviateMiddle("abc", ".", 1));
        assertEquals("abc", StringUtils.abbreviateMiddle("abc", ".", 2));
        assertEquals("a", StringUtils.abbreviateMiddle("a", ".", 1));
        assertEquals("a.d", StringUtils.abbreviateMiddle("abcd", ".", 3));
        assertEquals("a..f", StringUtils.abbreviateMiddle("abcdef", "..", 4));
        assertEquals("ab.ef", StringUtils.abbreviateMiddle("abcdef", ".", 5));
    }
}
