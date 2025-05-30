package org.languagetool.rules.pl;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.AnalyzedSentence;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.TestTools;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class WordCoherencyRuleTest_Parameterized {

    private final JLanguageTool lt = new JLanguageTool(Languages.getLanguageForShortCode("pl-PL"));

    @Before
    public void before() {
        TestTools.disableAllRulesExcept(lt, "PL_WORD_COHERENCY");
    }

    private void assertError(String s, String expectedSuggestion) throws IOException {
        WordCoherencyRule rule = new WordCoherencyRule(TestTools.getEnglishMessages());
        List<AnalyzedSentence> analyzedSentences = lt.analyzeText(s);
        RuleMatch[] matches = rule.match(analyzedSentences);
        assertEquals(1, matches.length);
        assertEquals("[" + expectedSuggestion + "]", matches[0].getSuggestedReplacements().toString());
    }

    private void assertError(String s) throws IOException {
        WordCoherencyRule rule = new WordCoherencyRule(TestTools.getEnglishMessages());
        List<AnalyzedSentence> analyzedSentences = lt.analyzeText(s);
        assertEquals(1, rule.match(analyzedSentences).length);
    }

    private void assertGood(String s) throws IOException {
        WordCoherencyRule rule = new WordCoherencyRule(TestTools.getEnglishMessages());
        List<AnalyzedSentence> analyzedSentences = lt.analyzeText(s);
        assertEquals(0, rule.match(analyzedSentences).length);
    }

    @ParameterizedTest
    @MethodSource("Provider_testCallIndependence_1to2")
    public void testCallIndependence_1to2(String param1) throws IOException {
        assertGood(param1);
    }

    static public Stream<Arguments> Provider_testCallIndependence_1to2() {
        return Stream.of(arguments("To jest blef."), arguments("A to nie bluff."));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRuleCompleteTexts_1to6")
    public void testRuleCompleteTexts_1to6(int param1, String param2) throws IOException {
        assertEquals(param1, lt.check(param2).size());
    }

    static public Stream<Arguments> Provider_testRuleCompleteTexts_1to6() {
        return Stream.of(arguments(0, "To jest blef. Nie wierzysz? To naprawdę blef!"), arguments(1, "To jest blef. Nie wierzysz? To naprawdę bluff!"), arguments(1, "To jest bluff. Nie wierzysz? To naprawdę blef!"), arguments(0, "To jest blef. Nie wierzysz? Nie widzisz blefu!"), arguments(1, "To jest blef. Nie wierzysz? Nie widzisz bluffu!"), arguments(1, "Chwalił się blefem.\n\nTak było nie zmyślam. Ale bluff mu nie wyszedł."));
    }
}
