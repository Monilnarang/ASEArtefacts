package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.util.Locale;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class FilenameUtilsWildcardTest_Parameterized {

    private static final boolean WINDOWS = File.separatorChar == '\\';

    private void assertMatch(final String text, final String wildcard, final boolean expected) {
        assertEquals(expected, FilenameUtils.wildcardMatch(text, wildcard), text + " " + wildcard);
    }

    @Test
    public void testMatch_1() {
        assertFalse(FilenameUtils.wildcardMatch(null, "Foo"));
    }

    @Test
    public void testMatch_2() {
        assertFalse(FilenameUtils.wildcardMatch("Foo", null));
    }

    @Test
    public void testMatch_3() {
        assertTrue(FilenameUtils.wildcardMatch(null, null));
    }

    @Test
    public void testMatchCaseSpecified_1() {
        assertFalse(FilenameUtils.wildcardMatch(null, "Foo", IOCase.SENSITIVE));
    }

    @Test
    public void testMatchCaseSpecified_2() {
        assertFalse(FilenameUtils.wildcardMatch("Foo", null, IOCase.SENSITIVE));
    }

    @Test
    public void testMatchCaseSpecified_3() {
        assertTrue(FilenameUtils.wildcardMatch(null, null, IOCase.SENSITIVE));
    }

    @Test
    public void testMatchOnSystem_1() {
        assertFalse(FilenameUtils.wildcardMatchOnSystem(null, "Foo"));
    }

    @Test
    public void testMatchOnSystem_2() {
        assertFalse(FilenameUtils.wildcardMatchOnSystem("Foo", null));
    }

    @Test
    public void testMatchOnSystem_3() {
        assertTrue(FilenameUtils.wildcardMatchOnSystem(null, null));
    }

    @Test
    public void testMatchOnSystem_10() {
        assertFalse(FilenameUtils.wildcardMatchOnSystem("Foo", "Bar"));
    }

    @ParameterizedTest
    @MethodSource("Provider_test_IO_246_1_1to2_2to3_3to4_4to5_5to6_6to7_7to8_8to9_9to10_10to11_11to12_12to32")
    public void test_IO_246_1_1to2_2to3_3to4_4to5_5to6_6to7_7to8_8to9_9to10_10to11_11to12_12to32(String param1, String param2) {
        assertMatch(param1, param2, true);
    }

    static public Stream<Arguments> Provider_test_IO_246_1_1to2_2to3_3to4_4to5_5to6_6to7_7to8_8to9_9to10_10to11_11to12_12to32() {
        return Stream.of(arguments("aaa", "aa*?"), arguments("", "?*"), arguments("a", "a?*"), arguments("aa", "aa?*"), arguments("a", "?*"), arguments("aa", "?*"), arguments("aaa", "?*"), arguments("", "?"), arguments("a", "a?"), arguments("aa", "aa?"), arguments("aab", "aa?"), arguments("aaa", "*a"), arguments("log.txt", "log.txt"), arguments("log.txt1", "log.txt"), arguments("log.txt", "log.txt*"), arguments("log.txt", "log.txt*1"), arguments("log.txt", "*log.txt*"), arguments("log.txt", "*.txt"), arguments("txt.log", "*.txt"), arguments("config.ini", "*.ini"), arguments("config.txt.bak", "con*.txt"), arguments("log.txt9", "*.txt?"), arguments("log.txt", "*.txt?"), arguments("progtestcase.java~5~", "*test*.java~*~"), arguments("progtestcase.java;5~", "*test*.java~*~"), arguments("progtestcase.java~5", "*test*.java~*~"), arguments("log.txt", "log.*"), arguments("log.txt", "log?*"), arguments("log.txt12", "log.txt??"), arguments("log.log", "log**log"), arguments("log.log", "log**"), arguments("log.log", "log.**"), arguments("log.log", "**.log"), arguments("log.log", "**log"), arguments("log.log", "log*log"), arguments("log.log", "log*"), arguments("log.log", "log.*"), arguments("log.log", "*.log"), arguments("log.log", "*log"), arguments("log.log", "*log?"), arguments("log.log", "*log?*"), arguments("log.log.abc", "*log?abc"), arguments("log.log.abc.log.abc", "*log?abc"), arguments("log.log.abc.log.abc.d", "*log?abc?d"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMatch_4to6_8to11_13to18")
    public void testMatch_4to6_8to11_13to18(String param1, String param2) {
        assertTrue(FilenameUtils.wildcardMatch(param1, param2));
    }

    static public Stream<Arguments> Provider_testMatch_4to6_8to11_13to18() {
        return Stream.of(arguments("Foo", "Foo"), arguments("", ""), arguments("", "*"), arguments("Foo", "Fo*"), arguments("Foo", "Fo?"), arguments("Foo Bar and Catflap", "Fo*"), arguments("New Bookmarks", "N?w ?o?k??r?s"), arguments("Foo Bar Foo", "F*o Bar*"), arguments("Adobe Acrobat Installer", "Ad*er"), arguments("Foo", "*Foo"), arguments("BarFoo", "*Foo"), arguments("Foo", "Foo*"), arguments("FooBar", "Foo*"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMatch_7_12_19to22")
    public void testMatch_7_12_19to22(String param1, String param2) {
        assertFalse(FilenameUtils.wildcardMatch(param1, param2));
    }

    static public Stream<Arguments> Provider_testMatch_7_12_19to22() {
        return Stream.of(arguments("", "?"), arguments("Foo", "Bar"), arguments("FOO", "*Foo"), arguments("BARFOO", "*Foo"), arguments("FOO", "Foo*"), arguments("FOOBAR", "Foo*"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMatchCaseSpecified_4to9_11to18")
    public void testMatchCaseSpecified_4to9_11to18(String param1, String param2) {
        assertTrue(FilenameUtils.wildcardMatch(param1, param2, IOCase.SENSITIVE));
    }

    static public Stream<Arguments> Provider_testMatchCaseSpecified_4to9_11to18() {
        return Stream.of(arguments("Foo", "Foo"), arguments("", ""), arguments("Foo", "Fo*"), arguments("Foo", "Fo?"), arguments("Foo Bar and Catflap", "Fo*"), arguments("New Bookmarks", "N?w ?o?k??r?s"), arguments("Foo Bar Foo", "F*o Bar*"), arguments("Adobe Acrobat Installer", "Ad*er"), arguments("Foo", "*Foo"), arguments("Foo", "Foo*"), arguments("Foo", "*Foo"), arguments("BarFoo", "*Foo"), arguments("Foo", "Foo*"), arguments("FooBar", "Foo*"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMatchCaseSpecified_10_19to22")
    public void testMatchCaseSpecified_10_19to22(String param1, String param2) {
        assertFalse(FilenameUtils.wildcardMatch(param1, param2, IOCase.SENSITIVE));
    }

    static public Stream<Arguments> Provider_testMatchCaseSpecified_10_19to22() {
        return Stream.of(arguments("Foo", "Bar"), arguments("FOO", "*Foo"), arguments("BARFOO", "*Foo"), arguments("FOO", "Foo*"), arguments("FOOBAR", "Foo*"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMatchCaseSpecified_23to26")
    public void testMatchCaseSpecified_23to26(String param1, String param2) {
        assertTrue(FilenameUtils.wildcardMatch(param1, param2, IOCase.INSENSITIVE));
    }

    static public Stream<Arguments> Provider_testMatchCaseSpecified_23to26() {
        return Stream.of(arguments("FOO", "*Foo"), arguments("BARFOO", "*Foo"), arguments("FOO", "Foo*"), arguments("FOOBAR", "Foo*"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMatchCaseSpecified_27to30")
    public void testMatchCaseSpecified_27to30(String param1, String param2) {
        assertEquals(WINDOWS, FilenameUtils.wildcardMatch(param1, param2, IOCase.SYSTEM));
    }

    static public Stream<Arguments> Provider_testMatchCaseSpecified_27to30() {
        return Stream.of(arguments("FOO", "*Foo"), arguments("BARFOO", "*Foo"), arguments("FOO", "Foo*"), arguments("FOOBAR", "Foo*"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMatchOnSystem_4to9_11to16")
    public void testMatchOnSystem_4to9_11to16(String param1, String param2) {
        assertTrue(FilenameUtils.wildcardMatchOnSystem(param1, param2));
    }

    static public Stream<Arguments> Provider_testMatchOnSystem_4to9_11to16() {
        return Stream.of(arguments("Foo", "Foo"), arguments("", ""), arguments("Foo", "Fo*"), arguments("Foo", "Fo?"), arguments("Foo Bar and Catflap", "Fo*"), arguments("New Bookmarks", "N?w ?o?k??r?s"), arguments("Foo Bar Foo", "F*o Bar*"), arguments("Adobe Acrobat Installer", "Ad*er"), arguments("Foo", "*Foo"), arguments("BarFoo", "*Foo"), arguments("Foo", "Foo*"), arguments("FooBar", "Foo*"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMatchOnSystem_17to20")
    public void testMatchOnSystem_17to20(String param1, String param2) {
        assertEquals(WINDOWS, FilenameUtils.wildcardMatchOnSystem(param1, param2));
    }

    static public Stream<Arguments> Provider_testMatchOnSystem_17to20() {
        return Stream.of(arguments("FOO", "*Foo"), arguments("BARFOO", "*Foo"), arguments("FOO", "Foo*"), arguments("FOOBAR", "Foo*"));
    }
}
