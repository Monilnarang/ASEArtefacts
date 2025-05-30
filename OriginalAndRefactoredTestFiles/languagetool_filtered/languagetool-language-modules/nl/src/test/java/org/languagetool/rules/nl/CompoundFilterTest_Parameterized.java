package org.languagetool.rules.nl;

import org.junit.Test;
import org.languagetool.rules.FakeRule;
import org.languagetool.rules.RuleMatch;
import org.languagetool.rules.patterns.RuleFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class CompoundFilterTest_Parameterized {

    private final RuleMatch match = new RuleMatch(new FakeRule(), null, 0, 10, "message");

    private final RuleFilter filter = new CompoundFilter();

    private void assertSuggestion(List<String> words, String expectedSuggestion) throws IOException {
        RuleMatch ruleMatch = filter.acceptRuleMatch(match, makeMap(words), -1, null, null);
        assertThat(ruleMatch.getSuggestedReplacements().size(), is(1));
        assertThat(ruleMatch.getSuggestedReplacements().get(0), is(expectedSuggestion));
    }

    private Map<String, String> makeMap(List<String> words) {
        Map<String, String> map = new HashMap<>();
        int i = 1;
        for (String word : words) {
            map.put("word" + i, word);
            i++;
        }
        return map;
    }

    @Test
    public void testFilter_11() throws IOException {
        assertSuggestion(asList("ANWB", "tv", "wagen"), "ANWB-tv-wagen");
    }

    @ParameterizedTest
    @MethodSource("Provider_testFilter_1to10")
    public void testFilter_1to10(String param1, String param2, String param3) throws IOException {
        assertSuggestion(asList(param2, param3), param1);
    }

    static public Stream<Arguments> Provider_testFilter_1to10() {
        return Stream.of(arguments("tv-meubel", "tv", "meubel"), arguments("test-tv-meubel", "test-tv", "meubel"), arguments("onzin-tv", "onzin", "tv"), arguments("auto-onderdeel", "auto", "onderdeel"), arguments("test-e-mail", "test", "e-mail"), arguments("taxi-jongen", "taxi", "jongen"), arguments("rijinstructeur", "rij", "instructeur"), arguments("test-e-mail", "test", "e-mail"), arguments("ANWB-wagen", "ANWB", "wagen"), arguments("pro-deoadvocaat", "pro-deo", "advocaat"));
    }
}
