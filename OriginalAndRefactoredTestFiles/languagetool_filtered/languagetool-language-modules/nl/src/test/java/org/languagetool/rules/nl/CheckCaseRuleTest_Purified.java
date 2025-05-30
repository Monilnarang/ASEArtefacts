package org.languagetool.rules.nl;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.language.Dutch;
import org.languagetool.rules.RuleMatch;

public class CheckCaseRuleTest_Purified {

    private CheckCaseRule rule;

    private JLanguageTool lt;

    @Before
    public void setUp() throws Exception {
        rule = new CheckCaseRule(TestTools.getMessages("nl"), new Dutch());
        lt = new JLanguageTool(new Dutch());
    }

    @Test
    public void testRule_1() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("een bisschop")).length);
    }

    @Test
    public void testRule_2() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("Een bisschop")).length);
    }

    @Test
    public void testRule_3() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("EEN BISSCHOP")).length);
    }

    @Test
    public void testRule_4_testMerged_4() throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence("Een Bisschop"));
        assertEquals(1, matches.length);
        assertEquals("Een bisschop", matches[0].getSuggestedReplacements().get(0));
        matches = rule.match(lt.getAnalyzedSentence("Hij is een Bisschop."));
        assertEquals("een bisschop", matches[0].getSuggestedReplacements().get(0));
        matches = rule.match(lt.getAnalyzedSentence("Mag ik die DVD lenen?"));
        assertEquals("dvd", matches[0].getSuggestedReplacements().get(0));
        matches = rule.match(lt.getAnalyzedSentence("Heb je de nieuwe IPAD al gezien?"));
        assertEquals("iPad", matches[0].getSuggestedReplacements().get(0));
    }
}
