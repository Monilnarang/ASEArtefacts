package org.languagetool.rules.ca;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.language.Catalan;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

public class SimpleReplaceBalearicRuleTest_Purified {

    private SimpleReplaceBalearicRule rule;

    private JLanguageTool lt;

    @Before
    public void setUp() throws Exception {
        lt = new JLanguageTool(Catalan.getInstance());
        rule = new SimpleReplaceBalearicRule(TestTools.getMessages("ca"), lt.getLanguage());
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
    public void testRule_3() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("Prosper Mérimée.")).length);
    }

    @Test
    public void testRule_4_testMerged_4() throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence("El calcul del telefon."));
        assertEquals(2, matches.length);
        assertEquals("càlcul", matches[0].getSuggestedReplacements().get(0));
        assertEquals("telèfon", matches[1].getSuggestedReplacements().get(0));
        matches = rule.match(lt.getAnalyzedSentence("EL CALCUL DEL TELEFON."));
        assertEquals("CÀLCUL", matches[0].getSuggestedReplacements().get(0));
        assertEquals("TELÈFON", matches[1].getSuggestedReplacements().get(0));
    }
}
