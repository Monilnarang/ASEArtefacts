package org.languagetool.rules.pt;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.language.Portuguese;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

public class BrazilianPortugueseSimpleReplaceRuleTest_Purified {

    private BrazilianPortugueseReplaceRule rule;

    private JLanguageTool lt;

    @Before
    public void setUp() throws Exception {
        lt = new JLanguageTool(Portuguese.getInstance());
        rule = new BrazilianPortugueseReplaceRule(TestTools.getMessages("pt"), "/pt/pt-BR/replace.txt", lt.getLanguage());
    }

    private void checkSimpleReplaceRule(String sentence, String word) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(sentence));
        assertEquals("Invalid matches.length while checking sentence: " + sentence, 1, matches.length);
        assertEquals("Invalid replacement count while checking sentence: " + sentence, 1, matches[0].getSuggestedReplacements().size());
        assertEquals("Invalid suggested replacement while checking sentence: " + sentence, word, matches[0].getSuggestedReplacements().get(0));
    }

    private void assertNoMatches(String sentence) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(sentence));
        assertEquals(0, matches.length);
    }

    @Test
    public void testRule_1() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("Fui de ônibus até o açougue italiano.")).length);
    }

    @Test
    public void testRule_2() throws IOException {
        assertNoMatches("José António Miranda Coutinho");
    }

    @Test
    public void testRule_3() throws IOException {
        assertNoMatches("Jerónimo Soares");
    }
}
