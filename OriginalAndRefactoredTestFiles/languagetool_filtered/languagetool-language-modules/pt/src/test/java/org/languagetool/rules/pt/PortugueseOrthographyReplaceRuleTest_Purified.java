package org.languagetool.rules.pt;

import org.junit.BeforeClass;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.language.Portuguese;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class PortugueseOrthographyReplaceRuleTest_Purified {

    private static PortugueseOrthographyReplaceRule rule;

    private static JLanguageTool lt;

    @BeforeClass
    public static void setUp() throws Exception {
        lt = new JLanguageTool(Portuguese.getInstance());
        rule = new PortugueseOrthographyReplaceRule(TestTools.getMessages("pt"), lt.getLanguage());
    }

    private void assertRuleId(RuleMatch match) {
        assert match.getRule().getId().startsWith("PT_SIMPLE_REPLACE_ORTHOGRAPHY");
    }

    private void assertNoMatches(String sentence) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(sentence));
        assertEquals(0, matches.length);
    }

    private void assertSingleMatch(String sentence, String... suggestions) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(sentence));
        assertEquals(1, matches.length);
        assertRuleId(matches[0]);
        List<String> returnedSuggestions = matches[0].getSuggestedReplacements();
        assertEquals(suggestions.length, returnedSuggestions.size());
        for (int i = 0; i < suggestions.length; i++) {
            assertEquals(suggestions[i], returnedSuggestions.get(i));
        }
    }

    @Test
    public void testRule_1() throws IOException {
        assertNoMatches("Já volto.");
    }

    @Test
    public void testRule_2() throws IOException {
        assertSingleMatch("Ja volto.", "Já");
    }

    @Test
    public void testRule_3() throws IOException {
        assertNoMatches("Gosto de você.");
    }

    @Test
    public void testRule_4() throws IOException {
        assertSingleMatch("Gosto de voce.", "você");
    }

    @Test
    public void testRule_5() throws IOException {
        assertNoMatches("Disse-me sotto voce.");
    }
}
