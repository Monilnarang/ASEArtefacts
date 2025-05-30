package org.languagetool.rules.el;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.language.Greek;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

public class GreekRedundancyRuleTest_Purified {

    private GreekRedundancyRule rule;

    private JLanguageTool langTool;

    @Before
    public void setUp() throws IOException {
        rule = new GreekRedundancyRule(TestTools.getMessages("el"), new Greek());
        langTool = new JLanguageTool(new Greek());
    }

    @Test
    public void testRule_1() throws IOException {
        assertEquals(0, rule.match(langTool.getAnalyzedSentence("Τώρα μπαίνω στο σπίτι.")).length);
    }

    @Test
    public void testRule_2() throws IOException {
        assertEquals(0, rule.match(langTool.getAnalyzedSentence("Απόψε θα βγω.")).length);
    }
}
