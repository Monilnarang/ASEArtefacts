package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

public class UtilTest_Purified {

    @Test
    public void testStripLeadingAndTrailingQuotes_1() {
        assertNull(Util.stripLeadingAndTrailingQuotes(null));
    }

    @Test
    public void testStripLeadingAndTrailingQuotes_2() {
        assertEquals("", Util.stripLeadingAndTrailingQuotes(""));
    }

    @Test
    public void testStripLeadingAndTrailingQuotes_3() {
        assertEquals("foo", Util.stripLeadingAndTrailingQuotes("\"foo\""));
    }

    @Test
    public void testStripLeadingAndTrailingQuotes_4() {
        assertEquals("foo \"bar\"", Util.stripLeadingAndTrailingQuotes("foo \"bar\""));
    }

    @Test
    public void testStripLeadingAndTrailingQuotes_5() {
        assertEquals("\"foo\" bar", Util.stripLeadingAndTrailingQuotes("\"foo\" bar"));
    }

    @Test
    public void testStripLeadingAndTrailingQuotes_6() {
        assertEquals("\"foo\" and \"bar\"", Util.stripLeadingAndTrailingQuotes("\"foo\" and \"bar\""));
    }

    @Test
    public void testStripLeadingAndTrailingQuotes_7() {
        assertEquals("\"", Util.stripLeadingAndTrailingQuotes("\""));
    }

    @Test
    public void testStripLeadingHyphens_1() {
        assertEquals("f", Util.stripLeadingHyphens("-f"));
    }

    @Test
    public void testStripLeadingHyphens_2() {
        assertEquals("foo", Util.stripLeadingHyphens("--foo"));
    }

    @Test
    public void testStripLeadingHyphens_3() {
        assertEquals("-foo", Util.stripLeadingHyphens("---foo"));
    }

    @Test
    public void testStripLeadingHyphens_4() {
        assertNull(Util.stripLeadingHyphens(null));
    }
}
