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

public class CompoundFilterTest_Purified {

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
    public void testFilter_1() throws IOException {
        assertSuggestion(asList("tv", "meubel"), "tv-meubel");
    }

    @Test
    public void testFilter_2() throws IOException {
        assertSuggestion(asList("test-tv", "meubel"), "test-tv-meubel");
    }

    @Test
    public void testFilter_3() throws IOException {
        assertSuggestion(asList("onzin", "tv"), "onzin-tv");
    }

    @Test
    public void testFilter_4() throws IOException {
        assertSuggestion(asList("auto", "onderdeel"), "auto-onderdeel");
    }

    @Test
    public void testFilter_5() throws IOException {
        assertSuggestion(asList("test", "e-mail"), "test-e-mail");
    }

    @Test
    public void testFilter_6() throws IOException {
        assertSuggestion(asList("taxi", "jongen"), "taxi-jongen");
    }

    @Test
    public void testFilter_7() throws IOException {
        assertSuggestion(asList("rij", "instructeur"), "rijinstructeur");
    }

    @Test
    public void testFilter_8() throws IOException {
        assertSuggestion(asList("test", "e-mail"), "test-e-mail");
    }

    @Test
    public void testFilter_9() throws IOException {
        assertSuggestion(asList("ANWB", "wagen"), "ANWB-wagen");
    }

    @Test
    public void testFilter_10() throws IOException {
        assertSuggestion(asList("pro-deo", "advocaat"), "pro-deoadvocaat");
    }

    @Test
    public void testFilter_11() throws IOException {
        assertSuggestion(asList("ANWB", "tv", "wagen"), "ANWB-tv-wagen");
    }
}
