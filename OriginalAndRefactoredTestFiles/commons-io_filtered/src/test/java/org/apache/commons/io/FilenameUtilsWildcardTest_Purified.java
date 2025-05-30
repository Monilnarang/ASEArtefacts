package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.File;
import java.util.Locale;
import org.junit.jupiter.api.Test;

public class FilenameUtilsWildcardTest_Purified {

    private static final boolean WINDOWS = File.separatorChar == '\\';

    private void assertMatch(final String text, final String wildcard, final boolean expected) {
        assertEquals(expected, FilenameUtils.wildcardMatch(text, wildcard), text + " " + wildcard);
    }

    @Test
    public void test_IO_246_1() {
        assertMatch("aaa", "aa*?", true);
    }

    @Test
    public void test_IO_246_2() {
        assertMatch("", "?*", false);
    }

    @Test
    public void test_IO_246_3() {
        assertMatch("a", "a?*", false);
    }

    @Test
    public void test_IO_246_4() {
        assertMatch("aa", "aa?*", false);
    }

    @Test
    public void test_IO_246_5() {
        assertMatch("a", "?*", true);
    }

    @Test
    public void test_IO_246_6() {
        assertMatch("aa", "?*", true);
    }

    @Test
    public void test_IO_246_7() {
        assertMatch("aaa", "?*", true);
    }

    @Test
    public void test_IO_246_8() {
        assertMatch("", "?", false);
    }

    @Test
    public void test_IO_246_9() {
        assertMatch("a", "a?", false);
    }

    @Test
    public void test_IO_246_10() {
        assertMatch("aa", "aa?", false);
    }

    @Test
    public void test_IO_246_11() {
        assertMatch("aab", "aa?", true);
    }

    @Test
    public void test_IO_246_12() {
        assertMatch("aaa", "*a", true);
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
    public void testMatch_4() {
        assertTrue(FilenameUtils.wildcardMatch("Foo", "Foo"));
    }

    @Test
    public void testMatch_5() {
        assertTrue(FilenameUtils.wildcardMatch("", ""));
    }

    @Test
    public void testMatch_6() {
        assertTrue(FilenameUtils.wildcardMatch("", "*"));
    }

    @Test
    public void testMatch_7() {
        assertFalse(FilenameUtils.wildcardMatch("", "?"));
    }

    @Test
    public void testMatch_8() {
        assertTrue(FilenameUtils.wildcardMatch("Foo", "Fo*"));
    }

    @Test
    public void testMatch_9() {
        assertTrue(FilenameUtils.wildcardMatch("Foo", "Fo?"));
    }

    @Test
    public void testMatch_10() {
        assertTrue(FilenameUtils.wildcardMatch("Foo Bar and Catflap", "Fo*"));
    }

    @Test
    public void testMatch_11() {
        assertTrue(FilenameUtils.wildcardMatch("New Bookmarks", "N?w ?o?k??r?s"));
    }

    @Test
    public void testMatch_12() {
        assertFalse(FilenameUtils.wildcardMatch("Foo", "Bar"));
    }

    @Test
    public void testMatch_13() {
        assertTrue(FilenameUtils.wildcardMatch("Foo Bar Foo", "F*o Bar*"));
    }

    @Test
    public void testMatch_14() {
        assertTrue(FilenameUtils.wildcardMatch("Adobe Acrobat Installer", "Ad*er"));
    }

    @Test
    public void testMatch_15() {
        assertTrue(FilenameUtils.wildcardMatch("Foo", "*Foo"));
    }

    @Test
    public void testMatch_16() {
        assertTrue(FilenameUtils.wildcardMatch("BarFoo", "*Foo"));
    }

    @Test
    public void testMatch_17() {
        assertTrue(FilenameUtils.wildcardMatch("Foo", "Foo*"));
    }

    @Test
    public void testMatch_18() {
        assertTrue(FilenameUtils.wildcardMatch("FooBar", "Foo*"));
    }

    @Test
    public void testMatch_19() {
        assertFalse(FilenameUtils.wildcardMatch("FOO", "*Foo"));
    }

    @Test
    public void testMatch_20() {
        assertFalse(FilenameUtils.wildcardMatch("BARFOO", "*Foo"));
    }

    @Test
    public void testMatch_21() {
        assertFalse(FilenameUtils.wildcardMatch("FOO", "Foo*"));
    }

    @Test
    public void testMatch_22() {
        assertFalse(FilenameUtils.wildcardMatch("FOOBAR", "Foo*"));
    }

    @Test
    public void testMatch2_1() {
        assertMatch("log.txt", "log.txt", true);
    }

    @Test
    public void testMatch2_2() {
        assertMatch("log.txt1", "log.txt", false);
    }

    @Test
    public void testMatch2_3() {
        assertMatch("log.txt", "log.txt*", true);
    }

    @Test
    public void testMatch2_4() {
        assertMatch("log.txt", "log.txt*1", false);
    }

    @Test
    public void testMatch2_5() {
        assertMatch("log.txt", "*log.txt*", true);
    }

    @Test
    public void testMatch2_6() {
        assertMatch("log.txt", "*.txt", true);
    }

    @Test
    public void testMatch2_7() {
        assertMatch("txt.log", "*.txt", false);
    }

    @Test
    public void testMatch2_8() {
        assertMatch("config.ini", "*.ini", true);
    }

    @Test
    public void testMatch2_9() {
        assertMatch("config.txt.bak", "con*.txt", false);
    }

    @Test
    public void testMatch2_10() {
        assertMatch("log.txt9", "*.txt?", true);
    }

    @Test
    public void testMatch2_11() {
        assertMatch("log.txt", "*.txt?", false);
    }

    @Test
    public void testMatch2_12() {
        assertMatch("progtestcase.java~5~", "*test*.java~*~", true);
    }

    @Test
    public void testMatch2_13() {
        assertMatch("progtestcase.java;5~", "*test*.java~*~", false);
    }

    @Test
    public void testMatch2_14() {
        assertMatch("progtestcase.java~5", "*test*.java~*~", false);
    }

    @Test
    public void testMatch2_15() {
        assertMatch("log.txt", "log.*", true);
    }

    @Test
    public void testMatch2_16() {
        assertMatch("log.txt", "log?*", true);
    }

    @Test
    public void testMatch2_17() {
        assertMatch("log.txt12", "log.txt??", true);
    }

    @Test
    public void testMatch2_18() {
        assertMatch("log.log", "log**log", true);
    }

    @Test
    public void testMatch2_19() {
        assertMatch("log.log", "log**", true);
    }

    @Test
    public void testMatch2_20() {
        assertMatch("log.log", "log.**", true);
    }

    @Test
    public void testMatch2_21() {
        assertMatch("log.log", "**.log", true);
    }

    @Test
    public void testMatch2_22() {
        assertMatch("log.log", "**log", true);
    }

    @Test
    public void testMatch2_23() {
        assertMatch("log.log", "log*log", true);
    }

    @Test
    public void testMatch2_24() {
        assertMatch("log.log", "log*", true);
    }

    @Test
    public void testMatch2_25() {
        assertMatch("log.log", "log.*", true);
    }

    @Test
    public void testMatch2_26() {
        assertMatch("log.log", "*.log", true);
    }

    @Test
    public void testMatch2_27() {
        assertMatch("log.log", "*log", true);
    }

    @Test
    public void testMatch2_28() {
        assertMatch("log.log", "*log?", false);
    }

    @Test
    public void testMatch2_29() {
        assertMatch("log.log", "*log?*", true);
    }

    @Test
    public void testMatch2_30() {
        assertMatch("log.log.abc", "*log?abc", true);
    }

    @Test
    public void testMatch2_31() {
        assertMatch("log.log.abc.log.abc", "*log?abc", true);
    }

    @Test
    public void testMatch2_32() {
        assertMatch("log.log.abc.log.abc.d", "*log?abc?d", true);
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
    public void testMatchCaseSpecified_4() {
        assertTrue(FilenameUtils.wildcardMatch("Foo", "Foo", IOCase.SENSITIVE));
    }

    @Test
    public void testMatchCaseSpecified_5() {
        assertTrue(FilenameUtils.wildcardMatch("", "", IOCase.SENSITIVE));
    }

    @Test
    public void testMatchCaseSpecified_6() {
        assertTrue(FilenameUtils.wildcardMatch("Foo", "Fo*", IOCase.SENSITIVE));
    }

    @Test
    public void testMatchCaseSpecified_7() {
        assertTrue(FilenameUtils.wildcardMatch("Foo", "Fo?", IOCase.SENSITIVE));
    }

    @Test
    public void testMatchCaseSpecified_8() {
        assertTrue(FilenameUtils.wildcardMatch("Foo Bar and Catflap", "Fo*", IOCase.SENSITIVE));
    }

    @Test
    public void testMatchCaseSpecified_9() {
        assertTrue(FilenameUtils.wildcardMatch("New Bookmarks", "N?w ?o?k??r?s", IOCase.SENSITIVE));
    }

    @Test
    public void testMatchCaseSpecified_10() {
        assertFalse(FilenameUtils.wildcardMatch("Foo", "Bar", IOCase.SENSITIVE));
    }

    @Test
    public void testMatchCaseSpecified_11() {
        assertTrue(FilenameUtils.wildcardMatch("Foo Bar Foo", "F*o Bar*", IOCase.SENSITIVE));
    }

    @Test
    public void testMatchCaseSpecified_12() {
        assertTrue(FilenameUtils.wildcardMatch("Adobe Acrobat Installer", "Ad*er", IOCase.SENSITIVE));
    }

    @Test
    public void testMatchCaseSpecified_13() {
        assertTrue(FilenameUtils.wildcardMatch("Foo", "*Foo", IOCase.SENSITIVE));
    }

    @Test
    public void testMatchCaseSpecified_14() {
        assertTrue(FilenameUtils.wildcardMatch("Foo", "Foo*", IOCase.SENSITIVE));
    }

    @Test
    public void testMatchCaseSpecified_15() {
        assertTrue(FilenameUtils.wildcardMatch("Foo", "*Foo", IOCase.SENSITIVE));
    }

    @Test
    public void testMatchCaseSpecified_16() {
        assertTrue(FilenameUtils.wildcardMatch("BarFoo", "*Foo", IOCase.SENSITIVE));
    }

    @Test
    public void testMatchCaseSpecified_17() {
        assertTrue(FilenameUtils.wildcardMatch("Foo", "Foo*", IOCase.SENSITIVE));
    }

    @Test
    public void testMatchCaseSpecified_18() {
        assertTrue(FilenameUtils.wildcardMatch("FooBar", "Foo*", IOCase.SENSITIVE));
    }

    @Test
    public void testMatchCaseSpecified_19() {
        assertFalse(FilenameUtils.wildcardMatch("FOO", "*Foo", IOCase.SENSITIVE));
    }

    @Test
    public void testMatchCaseSpecified_20() {
        assertFalse(FilenameUtils.wildcardMatch("BARFOO", "*Foo", IOCase.SENSITIVE));
    }

    @Test
    public void testMatchCaseSpecified_21() {
        assertFalse(FilenameUtils.wildcardMatch("FOO", "Foo*", IOCase.SENSITIVE));
    }

    @Test
    public void testMatchCaseSpecified_22() {
        assertFalse(FilenameUtils.wildcardMatch("FOOBAR", "Foo*", IOCase.SENSITIVE));
    }

    @Test
    public void testMatchCaseSpecified_23() {
        assertTrue(FilenameUtils.wildcardMatch("FOO", "*Foo", IOCase.INSENSITIVE));
    }

    @Test
    public void testMatchCaseSpecified_24() {
        assertTrue(FilenameUtils.wildcardMatch("BARFOO", "*Foo", IOCase.INSENSITIVE));
    }

    @Test
    public void testMatchCaseSpecified_25() {
        assertTrue(FilenameUtils.wildcardMatch("FOO", "Foo*", IOCase.INSENSITIVE));
    }

    @Test
    public void testMatchCaseSpecified_26() {
        assertTrue(FilenameUtils.wildcardMatch("FOOBAR", "Foo*", IOCase.INSENSITIVE));
    }

    @Test
    public void testMatchCaseSpecified_27() {
        assertEquals(WINDOWS, FilenameUtils.wildcardMatch("FOO", "*Foo", IOCase.SYSTEM));
    }

    @Test
    public void testMatchCaseSpecified_28() {
        assertEquals(WINDOWS, FilenameUtils.wildcardMatch("BARFOO", "*Foo", IOCase.SYSTEM));
    }

    @Test
    public void testMatchCaseSpecified_29() {
        assertEquals(WINDOWS, FilenameUtils.wildcardMatch("FOO", "Foo*", IOCase.SYSTEM));
    }

    @Test
    public void testMatchCaseSpecified_30() {
        assertEquals(WINDOWS, FilenameUtils.wildcardMatch("FOOBAR", "Foo*", IOCase.SYSTEM));
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
    public void testMatchOnSystem_4() {
        assertTrue(FilenameUtils.wildcardMatchOnSystem("Foo", "Foo"));
    }

    @Test
    public void testMatchOnSystem_5() {
        assertTrue(FilenameUtils.wildcardMatchOnSystem("", ""));
    }

    @Test
    public void testMatchOnSystem_6() {
        assertTrue(FilenameUtils.wildcardMatchOnSystem("Foo", "Fo*"));
    }

    @Test
    public void testMatchOnSystem_7() {
        assertTrue(FilenameUtils.wildcardMatchOnSystem("Foo", "Fo?"));
    }

    @Test
    public void testMatchOnSystem_8() {
        assertTrue(FilenameUtils.wildcardMatchOnSystem("Foo Bar and Catflap", "Fo*"));
    }

    @Test
    public void testMatchOnSystem_9() {
        assertTrue(FilenameUtils.wildcardMatchOnSystem("New Bookmarks", "N?w ?o?k??r?s"));
    }

    @Test
    public void testMatchOnSystem_10() {
        assertFalse(FilenameUtils.wildcardMatchOnSystem("Foo", "Bar"));
    }

    @Test
    public void testMatchOnSystem_11() {
        assertTrue(FilenameUtils.wildcardMatchOnSystem("Foo Bar Foo", "F*o Bar*"));
    }

    @Test
    public void testMatchOnSystem_12() {
        assertTrue(FilenameUtils.wildcardMatchOnSystem("Adobe Acrobat Installer", "Ad*er"));
    }

    @Test
    public void testMatchOnSystem_13() {
        assertTrue(FilenameUtils.wildcardMatchOnSystem("Foo", "*Foo"));
    }

    @Test
    public void testMatchOnSystem_14() {
        assertTrue(FilenameUtils.wildcardMatchOnSystem("BarFoo", "*Foo"));
    }

    @Test
    public void testMatchOnSystem_15() {
        assertTrue(FilenameUtils.wildcardMatchOnSystem("Foo", "Foo*"));
    }

    @Test
    public void testMatchOnSystem_16() {
        assertTrue(FilenameUtils.wildcardMatchOnSystem("FooBar", "Foo*"));
    }

    @Test
    public void testMatchOnSystem_17() {
        assertEquals(WINDOWS, FilenameUtils.wildcardMatchOnSystem("FOO", "*Foo"));
    }

    @Test
    public void testMatchOnSystem_18() {
        assertEquals(WINDOWS, FilenameUtils.wildcardMatchOnSystem("BARFOO", "*Foo"));
    }

    @Test
    public void testMatchOnSystem_19() {
        assertEquals(WINDOWS, FilenameUtils.wildcardMatchOnSystem("FOO", "Foo*"));
    }

    @Test
    public void testMatchOnSystem_20() {
        assertEquals(WINDOWS, FilenameUtils.wildcardMatchOnSystem("FOOBAR", "Foo*"));
    }
}
