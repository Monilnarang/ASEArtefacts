package org.languagetool.rules.ar;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.TestTools;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

public class ArabicDarjaRuleTest_Purified {

    private final JLanguageTool lt = new JLanguageTool(Languages.getLanguageForShortCode("ar"));

    private ArabicDarjaRule rule;

    @Before
    public void setUp() {
        rule = new ArabicDarjaRule(TestTools.getMessages("ar"));
    }

    @Test
    public void testRule_1() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("إن شاء")).length);
    }

    @Test
    public void testRule_2_testMerged_2() throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence("طرشي"));
        assertEquals(1, matches.length);
        assertEquals("فلفل حلو", matches[0].getSuggestedReplacements().get(0));
        assertEquals(1, rule.match(lt.getAnalyzedSentence("فايدة")).length);
    }
}
