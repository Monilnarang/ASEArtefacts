package org.jsoup.internal;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import static org.jsoup.internal.StringUtil.normaliseWhitespace;
import static org.jsoup.internal.StringUtil.resolve;
import static org.junit.jupiter.api.Assertions.*;

public class StringUtilTest_Purified {

    @Test
    public void join_1() {
        assertEquals("", StringUtil.join(Collections.singletonList(""), " "));
    }

    @Test
    public void join_2() {
        assertEquals("one", StringUtil.join(Collections.singletonList("one"), " "));
    }

    @Test
    public void join_3() {
        assertEquals("one two three", StringUtil.join(Arrays.asList("one", "two", "three"), " "));
    }

    @Test
    public void padding_1() {
        assertEquals("", StringUtil.padding(0));
    }

    @Test
    public void padding_2() {
        assertEquals(" ", StringUtil.padding(1));
    }

    @Test
    public void padding_3() {
        assertEquals("  ", StringUtil.padding(2));
    }

    @Test
    public void padding_4() {
        assertEquals("               ", StringUtil.padding(15));
    }

    @Test
    public void padding_5() {
        assertEquals("                              ", StringUtil.padding(45));
    }

    @Test
    public void padding_6() {
        assertEquals("", StringUtil.padding(0, -1));
    }

    @Test
    public void padding_7() {
        assertEquals("                    ", StringUtil.padding(20, -1));
    }

    @Test
    public void padding_8() {
        assertEquals("                     ", StringUtil.padding(21, -1));
    }

    @Test
    public void padding_9() {
        assertEquals("                              ", StringUtil.padding(30, -1));
    }

    @Test
    public void padding_10() {
        assertEquals("                                             ", StringUtil.padding(45, -1));
    }

    @Test
    public void padding_11() {
        assertEquals("", StringUtil.padding(0, 0));
    }

    @Test
    public void padding_12() {
        assertEquals("", StringUtil.padding(21, 0));
    }

    @Test
    public void padding_13() {
        assertEquals("", StringUtil.padding(0, 30));
    }

    @Test
    public void padding_14() {
        assertEquals(" ", StringUtil.padding(1, 30));
    }

    @Test
    public void padding_15() {
        assertEquals("  ", StringUtil.padding(2, 30));
    }

    @Test
    public void padding_16() {
        assertEquals("               ", StringUtil.padding(15, 30));
    }

    @Test
    public void padding_17() {
        assertEquals("                              ", StringUtil.padding(45, 30));
    }

    @Test
    public void padding_18() {
        assertEquals(5, StringUtil.padding(20, 5).length());
    }

    @Test
    public void isBlank_1() {
        assertTrue(StringUtil.isBlank(null));
    }

    @Test
    public void isBlank_2() {
        assertTrue(StringUtil.isBlank(""));
    }

    @Test
    public void isBlank_3() {
        assertTrue(StringUtil.isBlank("      "));
    }

    @Test
    public void isBlank_4() {
        assertTrue(StringUtil.isBlank("   \r\n  "));
    }

    @Test
    public void isBlank_5() {
        assertFalse(StringUtil.isBlank("hello"));
    }

    @Test
    public void isBlank_6() {
        assertFalse(StringUtil.isBlank("   hello   "));
    }

    @Test
    public void isNumeric_1() {
        assertFalse(StringUtil.isNumeric(null));
    }

    @Test
    public void isNumeric_2() {
        assertFalse(StringUtil.isNumeric(" "));
    }

    @Test
    public void isNumeric_3() {
        assertFalse(StringUtil.isNumeric("123 546"));
    }

    @Test
    public void isNumeric_4() {
        assertFalse(StringUtil.isNumeric("hello"));
    }

    @Test
    public void isNumeric_5() {
        assertFalse(StringUtil.isNumeric("123.334"));
    }

    @Test
    public void isNumeric_6() {
        assertTrue(StringUtil.isNumeric("1"));
    }

    @Test
    public void isNumeric_7() {
        assertTrue(StringUtil.isNumeric("1234"));
    }

    @Test
    public void isWhitespace_1() {
        assertTrue(StringUtil.isWhitespace('\t'));
    }

    @Test
    public void isWhitespace_2() {
        assertTrue(StringUtil.isWhitespace('\n'));
    }

    @Test
    public void isWhitespace_3() {
        assertTrue(StringUtil.isWhitespace('\r'));
    }

    @Test
    public void isWhitespace_4() {
        assertTrue(StringUtil.isWhitespace('\f'));
    }

    @Test
    public void isWhitespace_5() {
        assertTrue(StringUtil.isWhitespace(' '));
    }

    @Test
    public void isWhitespace_6() {
        assertFalse(StringUtil.isWhitespace('\u00a0'));
    }

    @Test
    public void isWhitespace_7() {
        assertFalse(StringUtil.isWhitespace('\u2000'));
    }

    @Test
    public void isWhitespace_8() {
        assertFalse(StringUtil.isWhitespace('\u3000'));
    }

    @Test
    public void normaliseWhiteSpace_1() {
        assertEquals(" ", normaliseWhitespace("    \r \n \r\n"));
    }

    @Test
    public void normaliseWhiteSpace_2() {
        assertEquals(" hello there ", normaliseWhitespace("   hello   \r \n  there    \n"));
    }

    @Test
    public void normaliseWhiteSpace_3() {
        assertEquals("hello", normaliseWhitespace("hello"));
    }

    @Test
    public void normaliseWhiteSpace_4() {
        assertEquals("hello there", normaliseWhitespace("hello\nthere"));
    }

    @Test
    public void resolvesRelativeUrls_1() {
        assertEquals("http://example.com/one/two?three", resolve("http://example.com", "./one/two?three"));
    }

    @Test
    public void resolvesRelativeUrls_2() {
        assertEquals("http://example.com/one/two?three", resolve("http://example.com?one", "./one/two?three"));
    }

    @Test
    public void resolvesRelativeUrls_3() {
        assertEquals("http://example.com/one/two?three#four", resolve("http://example.com", "./one/two?three#four"));
    }

    @Test
    public void resolvesRelativeUrls_4() {
        assertEquals("https://example.com/one", resolve("http://example.com/", "https://example.com/one"));
    }

    @Test
    public void resolvesRelativeUrls_5() {
        assertEquals("http://example.com/one/two.html", resolve("http://example.com/two/", "../one/two.html"));
    }

    @Test
    public void resolvesRelativeUrls_6() {
        assertEquals("https://example2.com/one", resolve("https://example.com/", "//example2.com/one"));
    }

    @Test
    public void resolvesRelativeUrls_7() {
        assertEquals("https://example.com:8080/one", resolve("https://example.com:8080", "./one"));
    }

    @Test
    public void resolvesRelativeUrls_8() {
        assertEquals("https://example2.com/one", resolve("http://example.com/", "https://example2.com/one"));
    }

    @Test
    public void resolvesRelativeUrls_9() {
        assertEquals("https://example.com/one", resolve("wrong", "https://example.com/one"));
    }

    @Test
    public void resolvesRelativeUrls_10() {
        assertEquals("https://example.com/one", resolve("https://example.com/one", ""));
    }

    @Test
    public void resolvesRelativeUrls_11() {
        assertEquals("", resolve("wrong", "also wrong"));
    }

    @Test
    public void resolvesRelativeUrls_12() {
        assertEquals("ftp://example.com/one", resolve("ftp://example.com/two/", "../one"));
    }

    @Test
    public void resolvesRelativeUrls_13() {
        assertEquals("ftp://example.com/one/two.c", resolve("ftp://example.com/one/", "./two.c"));
    }

    @Test
    public void resolvesRelativeUrls_14() {
        assertEquals("ftp://example.com/one/two.c", resolve("ftp://example.com/one/", "two.c"));
    }

    @Test
    public void resolvesRelativeUrls_15() {
        assertEquals("http://example.com/g", resolve("http://example.com/b/c/d;p?q", "../../../g"));
    }

    @Test
    public void resolvesRelativeUrls_16() {
        assertEquals("http://example.com/g", resolve("http://example.com/b/c/d;p?q", "../../../../g"));
    }

    @Test
    public void resolvesRelativeUrls_17() {
        assertEquals("http://example.com/g", resolve("http://example.com/b/c/d;p?q", "/./g"));
    }

    @Test
    public void resolvesRelativeUrls_18() {
        assertEquals("http://example.com/g", resolve("http://example.com/b/c/d;p?q", "/../g"));
    }

    @Test
    public void resolvesRelativeUrls_19() {
        assertEquals("http://example.com/b/c/g.", resolve("http://example.com/b/c/d;p?q", "g."));
    }

    @Test
    public void resolvesRelativeUrls_20() {
        assertEquals("http://example.com/b/c/.g", resolve("http://example.com/b/c/d;p?q", ".g"));
    }

    @Test
    public void resolvesRelativeUrls_21() {
        assertEquals("http://example.com/b/c/g..", resolve("http://example.com/b/c/d;p?q", "g.."));
    }

    @Test
    public void resolvesRelativeUrls_22() {
        assertEquals("http://example.com/b/c/..g", resolve("http://example.com/b/c/d;p?q", "..g"));
    }

    @Test
    public void resolvesRelativeUrls_23() {
        assertEquals("http://example.com/b/g", resolve("http://example.com/b/c/d;p?q", "./../g"));
    }

    @Test
    public void resolvesRelativeUrls_24() {
        assertEquals("http://example.com/b/c/g/", resolve("http://example.com/b/c/d;p?q", "./g/."));
    }

    @Test
    public void resolvesRelativeUrls_25() {
        assertEquals("http://example.com/b/c/g/h", resolve("http://example.com/b/c/d;p?q", "g/./h"));
    }

    @Test
    public void resolvesRelativeUrls_26() {
        assertEquals("http://example.com/b/c/h", resolve("http://example.com/b/c/d;p?q", "g/../h"));
    }

    @Test
    public void resolvesRelativeUrls_27() {
        assertEquals("http://example.com/b/c/g;x=1/y", resolve("http://example.com/b/c/d;p?q", "g;x=1/./y"));
    }

    @Test
    public void resolvesRelativeUrls_28() {
        assertEquals("http://example.com/b/c/y", resolve("http://example.com/b/c/d;p?q", "g;x=1/../y"));
    }

    @Test
    public void resolvesRelativeUrls_29() {
        assertEquals("http://example.com/b/c/g?y/./x", resolve("http://example.com/b/c/d;p?q", "g?y/./x"));
    }

    @Test
    public void resolvesRelativeUrls_30() {
        assertEquals("http://example.com/b/c/g?y/../x", resolve("http://example.com/b/c/d;p?q", "g?y/../x"));
    }

    @Test
    public void resolvesRelativeUrls_31() {
        assertEquals("http://example.com/b/c/g#s/./x", resolve("http://example.com/b/c/d;p?q", "g#s/./x"));
    }

    @Test
    public void resolvesRelativeUrls_32() {
        assertEquals("http://example.com/b/c/g#s/../x", resolve("http://example.com/b/c/d;p?q", "g#s/../x"));
    }

    @Test
    void isAscii_1() {
        assertTrue(StringUtil.isAscii(""));
    }

    @Test
    void isAscii_2() {
        assertTrue(StringUtil.isAscii("example.com"));
    }

    @Test
    void isAscii_3() {
        assertTrue(StringUtil.isAscii("One Two"));
    }

    @Test
    void isAscii_4() {
        assertFalse(StringUtil.isAscii("🧔"));
    }

    @Test
    void isAscii_5() {
        assertFalse(StringUtil.isAscii("测试"));
    }

    @Test
    void isAscii_6() {
        assertFalse(StringUtil.isAscii("测试.com"));
    }
}
