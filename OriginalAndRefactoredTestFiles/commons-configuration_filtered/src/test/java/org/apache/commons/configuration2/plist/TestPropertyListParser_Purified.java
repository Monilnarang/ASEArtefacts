package org.apache.commons.configuration2.plist;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.io.Reader;
import java.util.Calendar;
import java.util.SimpleTimeZone;
import org.junit.jupiter.api.Test;

public class TestPropertyListParser_Purified {

    private final PropertyListParser parser = new PropertyListParser((Reader) null);

    @Test
    public void testRemoveQuotes_1() {
        assertEquals("abc", parser.removeQuotes("abc"));
    }

    @Test
    public void testRemoveQuotes_2() {
        assertEquals("abc", parser.removeQuotes("\"abc\""));
    }

    @Test
    public void testRemoveQuotes_3() {
        assertEquals("", parser.removeQuotes("\"\""));
    }

    @Test
    public void testRemoveQuotes_4() {
        assertEquals("", parser.removeQuotes(""));
    }

    @Test
    public void testRemoveQuotes_5() {
        assertNull(parser.removeQuotes(null));
    }

    @Test
    public void testUnescapeQuotes_1() {
        assertEquals("aaa\"bbb\"ccc", parser.unescapeQuotes("aaa\"bbb\"ccc"));
    }

    @Test
    public void testUnescapeQuotes_2() {
        assertEquals("aaa\"bbb\"ccc", parser.unescapeQuotes("aaa\\\"bbb\\\"ccc"));
    }
}
