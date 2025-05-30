package org.languagetool.rules.ca;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.language.ValencianCatalan;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

public class SimpleReplaceDNVSecondaryRuleTest_Purified {

    private SimpleReplaceDNVSecondaryRule rule;

    private JLanguageTool lt;

    @Before
    public void setUp() throws Exception {
        rule = new SimpleReplaceDNVSecondaryRule(TestTools.getMessages("ca"), ValencianCatalan.getInstance());
        lt = new JLanguageTool(ValencianCatalan.getInstance());
    }

    @Test
    public void testRule_1() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("Estan dispostes, estan indisposts, dispost a tot.")).length);
    }

    @Test
    public void testRule_2_testMerged_2() throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence("S'ha dispost a fer-ho."));
        assertEquals(1, matches.length);
        assertEquals("disposat", matches[0].getSuggestedReplacements().get(0));
    }
}
