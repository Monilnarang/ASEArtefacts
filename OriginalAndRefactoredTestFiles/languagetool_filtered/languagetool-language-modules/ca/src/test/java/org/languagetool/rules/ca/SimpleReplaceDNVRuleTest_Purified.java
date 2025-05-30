package org.languagetool.rules.ca;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.language.ValencianCatalan;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

public class SimpleReplaceDNVRuleTest_Purified {

    private SimpleReplaceDNVRule rule;

    private JLanguageTool lt;

    @Before
    public void setUp() throws Exception {
        rule = new SimpleReplaceDNVRule(TestTools.getMessages("ca"), ValencianCatalan.getInstance());
        lt = new JLanguageTool(ValencianCatalan.getInstance());
    }

    @Test
    public void testRule_1() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("Ella és molt incauta.")).length);
    }

    @Test
    public void testRule_2_testMerged_2() throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence("L'arxipèleg."));
        assertEquals(1, matches.length);
        assertEquals("arxipèlag", matches[0].getSuggestedReplacements().get(0));
        matches = rule.match(lt.getAnalyzedSentence("colmena"));
        assertEquals("buc", matches[0].getSuggestedReplacements().get(0));
        assertEquals("rusc", matches[0].getSuggestedReplacements().get(1));
        matches = rule.match(lt.getAnalyzedSentence("colmenes"));
        assertEquals("bucs", matches[0].getSuggestedReplacements().get(0));
        assertEquals("ruscos", matches[0].getSuggestedReplacements().get(1));
        assertEquals("ruscs", matches[0].getSuggestedReplacements().get(2));
        matches = rule.match(lt.getAnalyzedSentence("afincaments"));
        assertEquals("establiments", matches[0].getSuggestedReplacements().get(0));
        assertEquals("instal·lacions", matches[0].getSuggestedReplacements().get(1));
        matches = rule.match(lt.getAnalyzedSentence("Els arxipèlegs"));
        assertEquals("arxipèlags", matches[0].getSuggestedReplacements().get(0));
        matches = rule.match(lt.getAnalyzedSentence("acevéssiu"));
        assertEquals("encebéssiu", matches[0].getSuggestedReplacements().get(0));
        matches = rule.match(lt.getAnalyzedSentence("S'arropeixen"));
        assertEquals("arrupeixen", matches[0].getSuggestedReplacements().get(0));
        assertEquals("arrupen", matches[0].getSuggestedReplacements().get(1));
        matches = rule.match(lt.getAnalyzedSentence("incautaren"));
        assertEquals("confiscaren", matches[0].getSuggestedReplacements().get(0));
        assertEquals("requisaren", matches[0].getSuggestedReplacements().get(1));
        assertEquals("comissaren", matches[0].getSuggestedReplacements().get(2));
        assertEquals("decomissaren", matches[0].getSuggestedReplacements().get(3));
    }
}
