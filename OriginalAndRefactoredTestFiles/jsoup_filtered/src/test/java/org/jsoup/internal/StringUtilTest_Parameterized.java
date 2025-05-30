package org.jsoup.internal;

import org.jsoup.Jsoup;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import static org.jsoup.internal.StringUtil.normaliseWhitespace;
import static org.jsoup.internal.StringUtil.resolve;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class StringUtilTest_Parameterized {

    @Test
    public void join_3() {
        assertEquals("one two three", StringUtil.join(Arrays.asList("one", "two", "three"), " "));
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
    public void isNumeric_1() {
        assertFalse(StringUtil.isNumeric(null));
    }

    @ParameterizedTest
    @MethodSource("Provider_join_1to2")
    public void join_1to2(String param1, String param2, String param3) {
        assertEquals(param1, StringUtil.join(Collections.singletonList(param3), param2));
    }

    static public Stream<Arguments> Provider_join_1to2() {
        return Stream.of(arguments("", " ", ""), arguments("one", " ", "one"));
    }

    @ParameterizedTest
    @MethodSource("Provider_padding_1to5")
    public void padding_1to5(String param1, int param2) {
        assertEquals(param1, StringUtil.padding(param2));
    }

    static public Stream<Arguments> Provider_padding_1to5() {
        return Stream.of(arguments("", 0), arguments(" ", 1), arguments("  ", 2), arguments("               ", 15), arguments("                              ", 45));
    }

    @ParameterizedTest
    @MethodSource("Provider_padding_6to10")
    public void padding_6to10(String param1, int param2, int param3) {
        assertEquals(param1, StringUtil.padding(param2, -param3));
    }

    static public Stream<Arguments> Provider_padding_6to10() {
        return Stream.of(arguments("", 0, 1), arguments("                    ", 20, 1), arguments("                     ", 21, 1), arguments("                              ", 30, 1), arguments("                                             ", 45, 1));
    }

    @ParameterizedTest
    @MethodSource("Provider_padding_11to17")
    public void padding_11to17(String param1, int param2, int param3) {
        assertEquals(param1, StringUtil.padding(param2, param3));
    }

    static public Stream<Arguments> Provider_padding_11to17() {
        return Stream.of(arguments("", 0, 0), arguments("", 21, 0), arguments("", 0, 30), arguments(" ", 1, 30), arguments("  ", 2, 30), arguments("               ", 15, 30), arguments("                              ", 45, 30));
    }

    @ParameterizedTest
    @MethodSource("Provider_isBlank_2to4")
    public void isBlank_2to4(String param1) {
        assertTrue(StringUtil.isBlank(param1));
    }

    static public Stream<Arguments> Provider_isBlank_2to4() {
        return Stream.of(arguments(""), arguments("      "), arguments("   \r\n  "));
    }

    @ParameterizedTest
    @MethodSource("Provider_isBlank_5to6")
    public void isBlank_5to6(String param1) {
        assertFalse(StringUtil.isBlank(param1));
    }

    static public Stream<Arguments> Provider_isBlank_5to6() {
        return Stream.of(arguments("hello"), arguments("   hello   "));
    }

    @ParameterizedTest
    @MethodSource("Provider_isNumeric_2to5")
    public void isNumeric_2to5(String param1) {
        assertFalse(StringUtil.isNumeric(param1));
    }

    static public Stream<Arguments> Provider_isNumeric_2to5() {
        return Stream.of(arguments(" "), arguments("123 546"), arguments("hello"), arguments(123.334));
    }

    @ParameterizedTest
    @MethodSource("Provider_isNumeric_6to7")
    public void isNumeric_6to7(int param1) {
        assertTrue(StringUtil.isNumeric(param1));
    }

    static public Stream<Arguments> Provider_isNumeric_6to7() {
        return Stream.of(arguments(1), arguments(1234));
    }

    @ParameterizedTest
    @MethodSource("Provider_isWhitespace_1to5")
    public void isWhitespace_1to5(String param1) {
        assertTrue(StringUtil.isWhitespace(param1));
    }

    static public Stream<Arguments> Provider_isWhitespace_1to5() {
        return Stream.of(arguments("\t"), arguments("\n"), arguments("\r"), arguments("\f"), arguments(" "));
    }

    @ParameterizedTest
    @MethodSource("Provider_isWhitespace_6to8")
    public void isWhitespace_6to8(String param1) {
        assertFalse(StringUtil.isWhitespace(param1));
    }

    static public Stream<Arguments> Provider_isWhitespace_6to8() {
        return Stream.of(arguments("\u00a0"), arguments("\u2000"), arguments("\u3000"));
    }

    @ParameterizedTest
    @MethodSource("Provider_normaliseWhiteSpace_1to4")
    public void normaliseWhiteSpace_1to4(String param1, String param2) {
        assertEquals(param1, normaliseWhitespace(param2));
    }

    static public Stream<Arguments> Provider_normaliseWhiteSpace_1to4() {
        return Stream.of(arguments(" ", "    \r \n \r\n"), arguments(" hello there ", "   hello   \r \n  there    \n"), arguments("hello", "hello"), arguments("hello there", "hello\nthere"));
    }

    @ParameterizedTest
    @MethodSource("Provider_resolvesRelativeUrls_1to32")
    public void resolvesRelativeUrls_1to32(String param1, String param2, String param3) {
        assertEquals(param1, resolve(param2, param3));
    }

    static public Stream<Arguments> Provider_resolvesRelativeUrls_1to32() {
        return Stream.of(arguments("http://example.com/one/two?three", "http://example.com", "./one/two?three"), arguments("http://example.com/one/two?three", "http://example.com?one", "./one/two?three"), arguments("http://example.com/one/two?three#four", "http://example.com", "./one/two?three#four"), arguments("https://example.com/one", "http://example.com/", "https://example.com/one"), arguments("http://example.com/one/two.html", "http://example.com/two/", "../one/two.html"), arguments("https://example2.com/one", "https://example.com/", "//example2.com/one"), arguments("https://example.com:8080/one", "https://example.com:8080", "./one"), arguments("https://example2.com/one", "http://example.com/", "https://example2.com/one"), arguments("https://example.com/one", "wrong", "https://example.com/one"), arguments("https://example.com/one", "https://example.com/one", ""), arguments("", "wrong", "also wrong"), arguments("ftp://example.com/one", "ftp://example.com/two/", "../one"), arguments("ftp://example.com/one/two.c", "ftp://example.com/one/", "./two.c"), arguments("ftp://example.com/one/two.c", "ftp://example.com/one/", "two.c"), arguments("http://example.com/g", "http://example.com/b/c/d;p?q", "../../../g"), arguments("http://example.com/g", "http://example.com/b/c/d;p?q", "../../../../g"), arguments("http://example.com/g", "http://example.com/b/c/d;p?q", "/./g"), arguments("http://example.com/g", "http://example.com/b/c/d;p?q", "/../g"), arguments("http://example.com/b/c/g.", "http://example.com/b/c/d;p?q", "g."), arguments("http://example.com/b/c/.g", "http://example.com/b/c/d;p?q", ".g"), arguments("http://example.com/b/c/g..", "http://example.com/b/c/d;p?q", "g.."), arguments("http://example.com/b/c/..g", "http://example.com/b/c/d;p?q", "..g"), arguments("http://example.com/b/g", "http://example.com/b/c/d;p?q", "./../g"), arguments("http://example.com/b/c/g/", "http://example.com/b/c/d;p?q", "./g/."), arguments("http://example.com/b/c/g/h", "http://example.com/b/c/d;p?q", "g/./h"), arguments("http://example.com/b/c/h", "http://example.com/b/c/d;p?q", "g/../h"), arguments("http://example.com/b/c/g;x=1/y", "http://example.com/b/c/d;p?q", "g;x=1/./y"), arguments("http://example.com/b/c/y", "http://example.com/b/c/d;p?q", "g;x=1/../y"), arguments("http://example.com/b/c/g?y/./x", "http://example.com/b/c/d;p?q", "g?y/./x"), arguments("http://example.com/b/c/g?y/../x", "http://example.com/b/c/d;p?q", "g?y/../x"), arguments("http://example.com/b/c/g#s/./x", "http://example.com/b/c/d;p?q", "g#s/./x"), arguments("http://example.com/b/c/g#s/../x", "http://example.com/b/c/d;p?q", "g#s/../x"));
    }

    @ParameterizedTest
    @MethodSource("Provider_isAscii_1to3")
    void isAscii_1to3(String param1) {
        assertTrue(StringUtil.isAscii(param1));
    }

    static public Stream<Arguments> Provider_isAscii_1to3() {
        return Stream.of(arguments(""), arguments("example.com"), arguments("One Two"));
    }

    @ParameterizedTest
    @MethodSource("Provider_isAscii_4to6")
    void isAscii_4to6(String param1) {
        assertFalse(StringUtil.isAscii(param1));
    }

    static public Stream<Arguments> Provider_isAscii_4to6() {
        return Stream.of(arguments("🧔"), arguments("测试"), arguments("测试.com"));
    }
}
