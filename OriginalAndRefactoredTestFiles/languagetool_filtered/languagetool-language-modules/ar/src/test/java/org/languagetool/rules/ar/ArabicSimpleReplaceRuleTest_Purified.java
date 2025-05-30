package org.languagetool.rules.ar;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.TestTools;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

public class ArabicSimpleReplaceRuleTest_Purified {

    private ArabicSimpleReplaceRule rule;

    private final JLanguageTool lt = new JLanguageTool(Languages.getLanguageForShortCode("ar"));

    @Before
    public void setUp() {
        rule = new ArabicSimpleReplaceRule(TestTools.getMessages("ar"));
    }

    @Test
    public void testRule_1() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("عبد الله")).length);
    }

    @Test
    public void testRule_2_testMerged_2() throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence("عبدالله"));
        assertEquals(1, matches.length);
        assertEquals("عبد الله", matches[0].getSuggestedReplacements().get(0));
        assertEquals(1, rule.match(lt.getAnalyzedSentence("يافطة")).length);
        assertEquals(1, rule.match(lt.getAnalyzedSentence("المائة")).length);
        assertEquals(1, rule.match(lt.getAnalyzedSentence("الذى")).length);
    }
}
