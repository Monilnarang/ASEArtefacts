package org.languagetool.rules.nl;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.language.Dutch;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

public class SimpleReplaceRuleTest_Purified {

    private SimpleReplaceRule rule;

    private JLanguageTool lt;

    @Before
    public void setUp() throws Exception {
        rule = new SimpleReplaceRule(TestTools.getMessages("nl"));
        lt = new JLanguageTool(new Dutch());
    }

    private void checkSimpleReplaceRule(String sentence, String suggestion) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(sentence));
        assertEquals("Invalid matches.length while checking sentence: " + sentence, 1, matches.length);
        assertEquals("Invalid replacement count wile checking sentence: " + sentence, 1, matches[0].getSuggestedReplacements().size());
        assertEquals("Invalid suggested replacement while checking sentence: " + sentence, suggestion, matches[0].getSuggestedReplacements().get(0));
    }

    @Test
    public void testRule_1() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("all right")).length);
    }

    @Test
    public void testRule_2() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("De Kudde eigenschappen")).length);
    }
}
