package org.languagetool.rules.pt;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.language.BrazilianPortuguese;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

public class PortugueseBarbarismRuleTest_Purified {

    private PortugueseBarbarismsRule rule;

    private JLanguageTool lt;

    @Before
    public void setUp() throws Exception {
        lt = new JLanguageTool(BrazilianPortuguese.getInstance());
        rule = new PortugueseBarbarismsRule(TestTools.getMessages("pt"), "/pt/pt-BR/barbarisms.txt", lt.getLanguage());
    }

    private void assertNoMatches(String sentence) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(sentence));
        assertEquals(0, matches.length);
    }

    @Test
    public void testReplaceBarbarisms_1() throws IOException {
        assertNoMatches("New York Stock Exchange");
    }

    @Test
    public void testReplaceBarbarisms_2() throws IOException {
        assertNoMatches("Yankee Doodle, faça o morra");
    }

    @Test
    public void testReplaceBarbarisms_3() throws IOException {
        assertNoMatches("mas inferior ao Opera Browser.");
    }
}
