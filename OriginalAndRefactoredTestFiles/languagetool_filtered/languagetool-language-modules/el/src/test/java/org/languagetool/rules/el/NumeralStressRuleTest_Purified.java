package org.languagetool.rules.el;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.language.Greek;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

public class NumeralStressRuleTest_Purified {

    private NumeralStressRule rule;

    private JLanguageTool lt;

    @Before
    public void setUp() throws IOException {
        rule = new NumeralStressRule(TestTools.getMessages("el"));
        lt = new JLanguageTool(new Greek());
    }

    private void assertCorrect(String sentence) throws IOException {
        final RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(sentence));
        assertEquals(0, matches.length);
    }

    private void assertIncorrect(String sentence, String correction) throws IOException {
        final RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(sentence));
        assertEquals(1, matches.length);
        assertEquals(1, matches[0].getSuggestedReplacements().size());
        assertEquals(correction, matches[0].getSuggestedReplacements().get(0));
    }

    @Test
    public void testRule_1() throws IOException {
        assertCorrect("1ος");
    }

    @Test
    public void testRule_2() throws IOException {
        assertCorrect("2η");
    }

    @Test
    public void testRule_3() throws IOException {
        assertCorrect("3ο");
    }

    @Test
    public void testRule_4() throws IOException {
        assertCorrect("20ός");
    }

    @Test
    public void testRule_5() throws IOException {
        assertCorrect("30ή");
    }

    @Test
    public void testRule_6() throws IOException {
        assertCorrect("40ό");
    }

    @Test
    public void testRule_7() throws IOException {
        assertCorrect("1000ών");
    }

    @Test
    public void testRule_8() throws IOException {
        assertCorrect("1010ες");
    }

    @Test
    public void testRule_9() throws IOException {
        assertIncorrect("4ός", "4ος");
    }

    @Test
    public void testRule_10() throws IOException {
        assertIncorrect("5ή", "5η");
    }

    @Test
    public void testRule_11() throws IOException {
        assertIncorrect("6ό", "6ο");
    }

    @Test
    public void testRule_12() throws IOException {
        assertIncorrect("100ος", "100ός");
    }

    @Test
    public void testRule_13() throws IOException {
        assertIncorrect("200η", "200ή");
    }

    @Test
    public void testRule_14() throws IOException {
        assertIncorrect("300ο", "300ό");
    }

    @Test
    public void testRule_15() throws IOException {
        assertIncorrect("2000ων", "2000ών");
    }

    @Test
    public void testRule_16() throws IOException {
        assertIncorrect("2010ές", "2010ες");
    }

    @Test
    public void testRule_17() throws IOException {
        assertIncorrect("2020α", "2020ά");
    }
}
