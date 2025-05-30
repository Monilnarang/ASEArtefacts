package org.languagetool.rules.ga;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.language.Irish;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

public class DhaNoBeirtRuleTest_Purified {

    private DhaNoBeirtRule rule;

    private JLanguageTool lt;

    @Before
    public void setUp() throws IOException {
        rule = new DhaNoBeirtRule(TestTools.getMessages("ga"));
        lt = new JLanguageTool(Irish.getInstance());
    }

    private void assertCorrect(String sentence) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(sentence));
        assertEquals(0, matches.length);
    }

    private void assertIncorrect(String sentence, int expected) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(sentence));
        assertEquals(expected, matches.length);
    }

    @Test
    public void testRule_1() throws IOException {
        assertCorrect("Seo abairt bheag.");
    }

    @Test
    public void testRule_2() throws IOException {
        assertCorrect("Tá beirt dheartháireacha agam.");
    }

    @Test
    public void testRule_3() throws IOException {
        assertIncorrect("Tá dhá dheartháireacha agam.", 1);
    }

    @Test
    public void testRule_4() throws IOException {
        assertIncorrect("Seo dhá ab déag", 2);
    }

    @Test
    public void testRule_5() throws IOException {
        assertIncorrect("Tá dhá dheartháireacha níos aosta déag agam.", 2);
    }
}
