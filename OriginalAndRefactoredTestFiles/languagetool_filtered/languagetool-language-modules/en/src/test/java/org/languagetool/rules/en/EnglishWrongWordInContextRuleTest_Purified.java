package org.languagetool.rules.en;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.Language;
import org.languagetool.Languages;

public class EnglishWrongWordInContextRuleTest_Purified {

    private JLanguageTool lt;

    private EnglishWrongWordInContextRule rule;

    @Before
    public void setUp() throws IOException {
        Language english = Languages.getLanguageForShortCode("en-US");
        lt = new JLanguageTool(english);
        rule = new EnglishWrongWordInContextRule(null, english);
    }

    private void assertGood(String sentence) throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence(sentence)).length);
    }

    private void assertBad(String sentence) throws IOException {
        assertEquals(1, rule.match(lt.getAnalyzedSentence(sentence)).length);
    }

    @Test
    public void testRule_1() throws IOException {
        assertBad("I have proscribed you a course of antibiotics.");
    }

    @Test
    public void testRule_2() throws IOException {
        assertGood("I have prescribed you a course of antibiotics.");
    }

    @Test
    public void testRule_3() throws IOException {
        assertGood("Name one country that does not proscribe theft.");
    }

    @Test
    public void testRule_4() throws IOException {
        assertBad("Name one country that does not prescribe theft.");
    }

    @Test
    public void testRule_5() throws IOException {
        assertEquals("prescribed", rule.match(lt.getAnalyzedSentence("I have proscribed you a course of antibiotics."))[0].getSuggestedReplacements().get(0));
    }

    @Test
    public void testRule_6() throws IOException {
        assertBad("We know that heroine is highly addictive.");
    }

    @Test
    public void testRule_7() throws IOException {
        assertGood("He wrote about his addiction to heroin.");
    }

    @Test
    public void testRule_8() throws IOException {
        assertGood("A heroine is the principal female character in a novel.");
    }

    @Test
    public void testRule_9() throws IOException {
        assertBad("A heroin is the principal female character in a novel.");
    }

    @Test
    public void testRule_10() throws IOException {
        assertBad("What a bazaar behavior!");
    }

    @Test
    public void testRule_11() throws IOException {
        assertGood("I bought these books at the church bazaar.");
    }

    @Test
    public void testRule_12() throws IOException {
        assertGood("She has a bizarre haircut.");
    }

    @Test
    public void testRule_13() throws IOException {
        assertBad("The Saturday morning bizarre is worth seeing even if you buy nothing.");
    }

    @Test
    public void testRule_14() throws IOException {
        assertBad("The bridle party waited on the lawn.");
    }

    @Test
    public void testRule_15() throws IOException {
        assertGood("Forgo the champagne treatment a bridal boutique often provides.");
    }

    @Test
    public void testRule_16() throws IOException {
        assertGood("He sat there holding his horse by the bridle.");
    }

    @Test
    public void testRule_17() throws IOException {
        assertBad("Each rider used his own bridal.");
    }

    @Test
    public void testRule_18() throws IOException {
        assertBad("They have some great deserts on this menu.");
    }

    @Test
    public void testRule_19() throws IOException {
        assertGood("They have some great desserts on this menu.");
    }

    @Test
    public void testRule_20() throws IOException {
        assertBad("They have some great marble statutes.");
    }

    @Test
    public void testRule_21() throws IOException {
        assertGood("They have a great marble statue.");
    }

    @Test
    public void testRule_22() throws IOException {
        assertGood("Protons and neutrons");
    }

    @Test
    public void testRule_23() throws IOException {
        assertBad("Protons and neurons");
    }

    @Test
    public void testRule_24() throws IOException {
        assertBad("The plane taxied to the hanger.");
    }

    @Test
    public void testRule_25() throws IOException {
        assertGood("The plane taxied to the hangar.");
    }
}
