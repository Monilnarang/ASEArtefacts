package org.languagetool.rules.ca;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.language.Catalan;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

public class SimpleReplaceRuleTest_Purified {

    private SimpleReplaceRule rule;

    private JLanguageTool lt;

    @Before
    public void setUp() throws Exception {
        lt = new JLanguageTool(Catalan.getInstance());
        rule = new SimpleReplaceRule(TestTools.getMessages("ca"), lt.getLanguage());
    }

    @Test
    public void testRule_1() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("Això està força bé.")).length);
    }

    @Test
    public void testRule_2() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("Joan Navarro no és de Navarra ni de Jerez.")).length);
    }

    @Test
    public void testRule_3_testMerged_3() throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence("El recader fa huelga."));
        assertEquals(2, matches.length);
        assertEquals("ordinari", matches[0].getSuggestedReplacements().get(0));
        assertEquals("transportista", matches[0].getSuggestedReplacements().get(1));
        assertEquals("vaga", matches[1].getSuggestedReplacements().get(0));
        matches = rule.match(lt.getAnalyzedSentence("EEUU"));
        assertEquals(1, matches.length);
        assertEquals("EUA", matches[0].getSuggestedReplacements().get(0));
        matches = rule.match(lt.getAnalyzedSentence("Aconteixements"));
        assertEquals("Esdeveniments", matches[0].getSuggestedReplacements().get(0));
    }
}
