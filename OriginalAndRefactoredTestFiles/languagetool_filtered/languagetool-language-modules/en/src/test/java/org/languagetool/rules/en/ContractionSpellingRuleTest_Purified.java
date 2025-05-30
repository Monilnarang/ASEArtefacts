package org.languagetool.rules.en;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.TestTools;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

public class ContractionSpellingRuleTest_Purified {

    private ContractionSpellingRule rule;

    private JLanguageTool lt;

    @Before
    public void setUp() throws Exception {
        lt = new JLanguageTool(Languages.getLanguageForShortCode("en"));
        rule = new ContractionSpellingRule(TestTools.getMessages("en"), lt.getLanguage());
    }

    private void checkSimpleReplaceRule(String sentence, String word) throws IOException {
        final RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(sentence));
        assertEquals("Invalid matches.length while checking sentence: " + sentence, 1, matches.length);
        assertEquals("Invalid replacement count wile checking sentence: " + sentence, 1, matches[0].getSuggestedReplacements().size());
        assertEquals("Invalid suggested replacement while checking sentence: " + sentence, word, matches[0].getSuggestedReplacements().get(0));
    }

    @Test
    public void testRule_1() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("It wasn't me.")).length);
    }

    @Test
    public void testRule_2() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("I'm ill.")).length);
    }

    @Test
    public void testRule_3() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("Staatszerfall im südlichen Afrika.")).length);
    }

    @Test
    public void testRule_4() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("by IVE")).length);
    }

    @Test
    public void testRule_5() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("Never mind the whys and wherefores.")).length);
    }

    @Test
    public void testRule_6_testMerged_6() throws IOException {
        final RuleMatch[] matches = rule.match(lt.getAnalyzedSentence("Whereve you are"));
        assertEquals(2, matches[0].getSuggestedReplacements().size());
        assertEquals("Where've", matches[0].getSuggestedReplacements().get(0));
        assertEquals("Wherever", matches[0].getSuggestedReplacements().get(1));
    }
}
