package org.languagetool.rules.en;

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

    private final JLanguageTool lt = new JLanguageTool(Languages.getLanguageForShortCode("en-US"));

    @Before
    public void before() {
        TestTools.disableAllRulesExcept(lt, "EN_WORD_COHERENCY");
    }

    private void assertGood(String s) throws IOException {
        WordCoherencyRule rule = new WordCoherencyRule(TestTools.getEnglishMessages());
        List<AnalyzedSentence> analyzedSentences = lt.analyzeText(s);
        assertEquals(0, rule.match(analyzedSentences).length);
    }

    private void assertError(String s) throws IOException {
        WordCoherencyRule rule = new WordCoherencyRule(TestTools.getEnglishMessages());
        List<AnalyzedSentence> analyzedSentences = lt.analyzeText(s);
        assertEquals(1, rule.match(analyzedSentences).length);
    }

    @Test
    public void testCallIndependence_1() throws IOException {
        assertGood("He likes archaeology.");
    }

    @Test
    public void testCallIndependence_2() throws IOException {
        assertGood("She likes archeology, too.");
    }

    @Test
    public void testRuleCompleteTexts_1() throws IOException {
        assertEquals(0, lt.check("He likes archaeology. Really? She likes archaeology, too.").size());
    }

    @Test
    public void testRuleCompleteTexts_2() throws IOException {
        assertEquals(1, lt.check("He likes archaeology. Really? She likes archeology, too.").size());
    }

    @Test
    public void testRuleCompleteTexts_3() throws IOException {
        assertEquals(1, lt.check("He likes archeology. Really? She likes archaeology, too.").size());
    }

    @Test
    public void testRuleCompleteTexts_4() throws IOException {
        assertEquals(1, lt.check("Mix of upper case and lower case: Westernize and westernise.").size());
    }
}
