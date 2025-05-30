package org.languagetool.rules.de;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.languagetool.AnalyzedSentence;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.TestTools;
import org.languagetool.rules.RuleMatch;

public class WordCoherencyRuleTest_Purified {

    private final JLanguageTool lt = new JLanguageTool(Languages.getLanguageForShortCode("de-DE"));

    @Before
    public void before() {
        TestTools.disableAllRulesExcept(lt, "DE_WORD_COHERENCY");
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
        assertGood("Das ist aufwendig.");
    }

    @Test
    public void testCallIndependence_2() throws IOException {
        assertGood("Aber nicht zu aufwändig.");
    }

    @Test
    public void testRuleCompleteTexts_1() throws IOException {
        assertEquals(0, lt.check("Das ist aufwändig. Aber hallo. Es ist wirklich aufwändig.").size());
    }

    @Test
    public void testRuleCompleteTexts_2() throws IOException {
        assertEquals(1, lt.check("Das ist aufwendig. Aber hallo. Es ist wirklich aufwändig.").size());
    }

    @Test
    public void testRuleCompleteTexts_3() throws IOException {
        assertEquals(1, lt.check("Das ist aufwändig. Aber hallo. Es ist wirklich aufwendig.").size());
    }

    @Test
    public void testRuleCompleteTexts_4() throws IOException {
        assertEquals(0, lt.check("Das ist aufwendig. Aber hallo. Es ist wirklich aufwendiger als so.").size());
    }

    @Test
    public void testRuleCompleteTexts_5() throws IOException {
        assertEquals(1, lt.check("Das ist aufwendig. Aber hallo. Es ist wirklich aufwändiger als so.").size());
    }

    @Test
    public void testRuleCompleteTexts_6() throws IOException {
        assertEquals(1, lt.check("Das ist aufwändig. Aber hallo. Es ist wirklich aufwendiger als so.").size());
    }

    @Test
    public void testRuleCompleteTexts_7() throws IOException {
        assertEquals(1, lt.check("Das ist das aufwändigste. Aber hallo. Es ist wirklich aufwendiger als so.").size());
    }

    @Test
    public void testRuleCompleteTexts_8() throws IOException {
        assertEquals(1, lt.check("Das ist das aufwändigste. Aber hallo. Es ist wirklich aufwendig.").size());
    }

    @Test
    public void testRuleCompleteTexts_9() throws IOException {
        assertEquals(1, lt.check("Das ist das aufwändigste.\n\nAber hallo. Es ist wirklich aufwendig.").size());
    }
}
