package org.apache.hadoop.fs;

import org.junit.Test;
import static org.junit.Assert.*;
import com.google.re2j.PatternSyntaxException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestGlobPattern_Parameterized {

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
    public void testValidPatterns_14() {
        assertMatch(true, "{[12]*,[45]*,[78]*}", "1", "2!", "4", "42", "7", "7$");
    }

    @ParameterizedTest
    @MethodSource("Provider_testValidPatterns_1_10")
    public void testValidPatterns_1_10(String param1, String param2, String param3, String param4, String param5) {
        assertMatch(param1, param2, param3, param4, param5, "\n");
    }

    static public Stream<Arguments> Provider_testValidPatterns_1_10() {
        return Stream.of(arguments("*", "^$", "foo", "bar", "\n"), arguments("[^^]", ".", "$", "[", "]"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testValidPatterns_2to3")
    public void testValidPatterns_2to3(String param1, String param2, String param3, String param4, String param5, String param6) {
        assertMatch(param1, param2, param3, param4, param5, param6, "$");
    }

    static public Stream<Arguments> Provider_testValidPatterns_2to3() {
        return Stream.of(arguments("?", "?", "^", "[", "]", "$"), arguments("foo*", "foo", "food", "fool", "foo\n", "foo\nbar"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testValidPatterns_4to5_7_15")
    public void testValidPatterns_4to5_7_15(String param1, String param2, String param3, String param4) {
        assertMatch(param1, param2, param3, param4, "foo\nd");
    }

    static public Stream<Arguments> Provider_testValidPatterns_4to5_7_15() {
        return Stream.of(arguments("f*d", "fud", "food", "foo\nd"), arguments("*d", "good", "bad", "\nd"), arguments("[]^-]", "]", "-", "^"), arguments("{[12]*,[45]*,[78]*}", 3, 6, "9ß"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testValidPatterns_6_8to9_11_16")
    public void testValidPatterns_6_8to9_11_16(String param1, String param2) {
        assertMatch(param1, param2, "*?[{\\");
    }

    static public Stream<Arguments> Provider_testValidPatterns_6_8to9_11_16() {
        return Stream.of(arguments("\\*\\?\\[\\{\\\\", "*?[{\\"), arguments("]", "]"), arguments("^.$()|+", "^.$()|+"), arguments("[^^]", "^"), arguments("}", "}"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testValidPatterns_12to13")
    public void testValidPatterns_12to13(String param1, String param2, String param3) {
        assertMatch(param1, param2, param3, "?");
    }

    static public Stream<Arguments> Provider_testValidPatterns_12to13() {
        return Stream.of(arguments("[!!-]", "^", "?"), arguments("[!!-]", "!", "-"));
    }
}
