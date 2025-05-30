package org.apache.hadoop.fs;

import org.junit.Test;
import static org.junit.Assert.*;
import com.google.re2j.PatternSyntaxException;

public class TestGlobPattern_Purified {

    private void assertMatch(boolean yes, String glob, String... input) {
        GlobPattern pattern = new GlobPattern(glob);
        for (String s : input) {
            boolean result = pattern.matches(s);
            assertTrue(glob + " should" + (yes ? "" : " not") + " match " + s, yes ? result : !result);
        }
    }

    private void shouldThrow(String... globs) {
        for (String glob : globs) {
            try {
                GlobPattern.compile(glob);
            } catch (PatternSyntaxException e) {
                e.printStackTrace();
                continue;
            }
            assertTrue("glob " + glob + " should throw", false);
        }
    }

    @Test
    public void testValidPatterns_1() {
        assertMatch(true, "*", "^$", "foo", "bar", "\n");
    }

    @Test
    public void testValidPatterns_2() {
        assertMatch(true, "?", "?", "^", "[", "]", "$");
    }

    @Test
    public void testValidPatterns_3() {
        assertMatch(true, "foo*", "foo", "food", "fool", "foo\n", "foo\nbar");
    }

    @Test
    public void testValidPatterns_4() {
        assertMatch(true, "f*d", "fud", "food", "foo\nd");
    }

    @Test
    public void testValidPatterns_5() {
        assertMatch(true, "*d", "good", "bad", "\nd");
    }

    @Test
    public void testValidPatterns_6() {
        assertMatch(true, "\\*\\?\\[\\{\\\\", "*?[{\\");
    }

    @Test
    public void testValidPatterns_7() {
        assertMatch(true, "[]^-]", "]", "-", "^");
    }

    @Test
    public void testValidPatterns_8() {
        assertMatch(true, "]", "]");
    }

    @Test
    public void testValidPatterns_9() {
        assertMatch(true, "^.$()|+", "^.$()|+");
    }

    @Test
    public void testValidPatterns_10() {
        assertMatch(true, "[^^]", ".", "$", "[", "]");
    }

    @Test
    public void testValidPatterns_11() {
        assertMatch(false, "[^^]", "^");
    }

    @Test
    public void testValidPatterns_12() {
        assertMatch(true, "[!!-]", "^", "?");
    }

    @Test
    public void testValidPatterns_13() {
        assertMatch(false, "[!!-]", "!", "-");
    }

    @Test
    public void testValidPatterns_14() {
        assertMatch(true, "{[12]*,[45]*,[78]*}", "1", "2!", "4", "42", "7", "7$");
    }

    @Test
    public void testValidPatterns_15() {
        assertMatch(false, "{[12]*,[45]*,[78]*}", "3", "6", "9ß");
    }

    @Test
    public void testValidPatterns_16() {
        assertMatch(true, "}", "}");
    }
}
