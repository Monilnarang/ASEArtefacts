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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class StringMatcherTest_Parameterized {

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

    @ParameterizedTest
    @MethodSource("Provider_requiredSubstrings_1to2_5to18")
    public void requiredSubstrings_1to2_5to18(String param1, String param2) {
        assertRequiredSubstrings(param1, param2);
    }

    static public Stream<Arguments> Provider_requiredSubstrings_1to2_5to18() {
        return Stream.of(arguments("", "[]"), arguments("foo", "[foo]"), arguments("PRP.+", "[PRP)"), arguments(".*PRP.+", "(PRP)"), arguments(".*PRP", "(PRP]"), arguments(".+PRP", "(PRP]"), arguments("a.+b", "[a, b]"), arguments("a.*b", "[a, b]"), arguments("\\bZünglein an der (Wage)\\b", "[Zünglein an der Wage]"), arguments("(ökumenische[rn]?) (.*Messen?)", "[ökumenische,  , Messe)"), arguments("(CO2|Kohlendioxid|Schadstoff)\\-?Emulsion(en)?", "(Emulsion)"), arguments("\\bder (\\w*(Verkehrs|Verbots|Namens|Hinweis|Warn)schild)", "[der , schild]"), arguments("\\bvon Seiten\\b", "[von Seiten]"), arguments("((\\-)?[0-9]+[0-9.,]{0,15})(?:[\\s  ]+)(°[^CFK])", "(°)"), arguments("\\b(teils\\s[^,]+\\steils)\\b", "[teils, teils]"), arguments("§ ?(\\d+[a-z]?)", "[§)"));
    }

    @ParameterizedTest
    @MethodSource("Provider_requiredSubstrings_3to4")
    public void requiredSubstrings_3to4(String param1) {
        assertRequiredSubstrings(param1, null);
    }

    static public Stream<Arguments> Provider_requiredSubstrings_3to4() {
        return Stream.of(arguments("foo|bar"), arguments("\\w"));
    }
}
