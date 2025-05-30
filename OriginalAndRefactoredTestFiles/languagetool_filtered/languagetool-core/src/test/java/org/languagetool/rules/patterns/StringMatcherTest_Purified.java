package org.languagetool.rules.patterns;

import com.google.common.collect.Sets;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import static org.junit.Assert.*;
import static org.languagetool.rules.patterns.StringMatcher.getPossibleRegexpValues;

public class StringMatcherTest_Purified {

    private static void assertPossibleValues(String regexp, String... expected) {
        assertEquals(expected.length == 0 ? null : Sets.newHashSet(expected), getPossibleRegexpValues(regexp));
        for (String s : expected) {
            trySomeMutations(regexp, s);
        }
        trySomeMutations(regexp, regexp);
    }

    private static void trySomeMutations(String regexp, String s) {
        assertStringMatcherConsistentWithPattern(regexp, s);
        for (int i = 0; i < s.length(); i++) {
            assertStringMatcherConsistentWithPattern(regexp, s.substring(0, i) + s.substring(i + 1));
            assertStringMatcherConsistentWithPattern(regexp, s.substring(0, i) + "x" + s.substring(i + 1));
        }
        assertStringMatcherConsistentWithPattern(regexp, "a" + s);
        assertStringMatcherConsistentWithPattern(regexp, s + "a");
    }

    private static void assertStringMatcherConsistentWithPattern(String regexp, String s) {
        assertEquals(StringMatcher.regexp(regexp).matches(s), s.matches(regexp));
        assertEquals(StringMatcher.create(regexp, true, false).matches(s), Pattern.compile(regexp, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(s).matches());
    }

    private static void assertRequiredSubstrings(String regexp, @Nullable String expected) {
        Substrings actual = StringMatcher.getRequiredSubstrings(regexp);
        assertEquals(expected, actual == null ? null : actual.toString());
        trySomeMutations(regexp, regexp);
        if (expected != null) {
            trySomeMutations(regexp, expected);
            trySomeMutations(regexp, expected.substring(1, expected.length() - 1));
        }
        if (actual != null) {
            for (String separator : Arrays.asList("", "a", "0", " ")) {
                trySomeMutations(regexp, String.join(separator, actual.substrings));
                trySomeMutations(regexp, separator + String.join(separator, actual.substrings));
                trySomeMutations(regexp, String.join(separator, actual.substrings) + separator);
            }
        }
    }

    @Test
    public void requiredSubstrings_1() {
        assertRequiredSubstrings("", "[]");
    }

    @Test
    public void requiredSubstrings_2() {
        assertRequiredSubstrings("foo", "[foo]");
    }

    @Test
    public void requiredSubstrings_3() {
        assertRequiredSubstrings("foo|bar", null);
    }

    @Test
    public void requiredSubstrings_4() {
        assertRequiredSubstrings("\\w", null);
    }

    @Test
    public void requiredSubstrings_5() {
        assertRequiredSubstrings("PRP.+", "[PRP)");
    }

    @Test
    public void requiredSubstrings_6() {
        assertRequiredSubstrings(".*PRP.+", "(PRP)");
    }

    @Test
    public void requiredSubstrings_7() {
        assertRequiredSubstrings(".*PRP", "(PRP]");
    }

    @Test
    public void requiredSubstrings_8() {
        assertRequiredSubstrings(".+PRP", "(PRP]");
    }

    @Test
    public void requiredSubstrings_9() {
        assertRequiredSubstrings("a.+b", "[a, b]");
    }

    @Test
    public void requiredSubstrings_10() {
        assertRequiredSubstrings("a.*b", "[a, b]");
    }

    @Test
    public void requiredSubstrings_11() {
        assertRequiredSubstrings("\\bZünglein an der (Wage)\\b", "[Zünglein an der Wage]");
    }

    @Test
    public void requiredSubstrings_12() {
        assertRequiredSubstrings("(ökumenische[rn]?) (.*Messen?)", "[ökumenische,  , Messe)");
    }

    @Test
    public void requiredSubstrings_13() {
        assertRequiredSubstrings("(CO2|Kohlendioxid|Schadstoff)\\-?Emulsion(en)?", "(Emulsion)");
    }

    @Test
    public void requiredSubstrings_14() {
        assertRequiredSubstrings("\\bder (\\w*(Verkehrs|Verbots|Namens|Hinweis|Warn)schild)", "[der , schild]");
    }

    @Test
    public void requiredSubstrings_15() {
        assertRequiredSubstrings("\\bvon Seiten\\b", "[von Seiten]");
    }

    @Test
    public void requiredSubstrings_16() {
        assertRequiredSubstrings("((\\-)?[0-9]+[0-9.,]{0,15})(?:[\\s  ]+)(°[^CFK])", "(°)");
    }

    @Test
    public void requiredSubstrings_17() {
        assertRequiredSubstrings("\\b(teils\\s[^,]+\\steils)\\b", "[teils, teils]");
    }

    @Test
    public void requiredSubstrings_18() {
        assertRequiredSubstrings("§ ?(\\d+[a-z]?)", "[§)");
    }
}
