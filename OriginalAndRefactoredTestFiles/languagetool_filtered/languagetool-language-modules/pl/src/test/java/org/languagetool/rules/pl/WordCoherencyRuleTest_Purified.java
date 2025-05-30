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

public class WordCoherencyRuleTest_Purified {

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

    @Test
    public void testCallIndependence_1() throws IOException {
        assertGood("To jest blef.");
    }

    @Test
    public void testCallIndependence_2() throws IOException {
        assertGood("A to nie bluff.");
    }

    @Test
    public void testRuleCompleteTexts_1() throws IOException {
        assertEquals(0, lt.check("To jest blef. Nie wierzysz? To naprawdę blef!").size());
    }

    @Test
    public void testRuleCompleteTexts_2() throws IOException {
        assertEquals(1, lt.check("To jest blef. Nie wierzysz? To naprawdę bluff!").size());
    }

    @Test
    public void testRuleCompleteTexts_3() throws IOException {
        assertEquals(1, lt.check("To jest bluff. Nie wierzysz? To naprawdę blef!").size());
    }

    @Test
    public void testRuleCompleteTexts_4() throws IOException {
        assertEquals(0, lt.check("To jest blef. Nie wierzysz? Nie widzisz blefu!").size());
    }

    @Test
    public void testRuleCompleteTexts_5() throws IOException {
        assertEquals(1, lt.check("To jest blef. Nie wierzysz? Nie widzisz bluffu!").size());
    }

    @Test
    public void testRuleCompleteTexts_6() throws IOException {
        assertEquals(1, lt.check("Chwalił się blefem.\n\nTak było nie zmyślam. Ale bluff mu nie wyszedł.").size());
    }
}
