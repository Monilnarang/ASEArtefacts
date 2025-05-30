package org.languagetool.rules.en;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.TestTools;
import org.languagetool.markup.AnnotatedText;
import org.languagetool.markup.AnnotatedTextBuilder;
import org.languagetool.rules.RuleMatch;
import org.languagetool.rules.TextLevelRule;

public class EnglishRepeatedWordsRuleTest_Purified {

    private TextLevelRule rule;

    private JLanguageTool lt;

    @Before
    public void setUp() {
        rule = new EnglishRepeatedWordsRule(TestTools.getEnglishMessages());
        lt = new JLanguageTool(Languages.getLanguageForShortCode("en"));
    }

    private RuleMatch[] getRuleMatches(String sentences) throws IOException {
        AnnotatedText aText = new AnnotatedTextBuilder().addText(sentences).build();
        return rule.match(lt.analyzeText(sentences), aText);
    }

    private void assertCorrectText(String sentences) throws IOException {
        AnnotatedText aText = new AnnotatedTextBuilder().addText(sentences).build();
        RuleMatch[] matches = rule.match(lt.analyzeText(sentences), aText);
        assertEquals(0, matches.length);
    }

    @Test
    public void testRule_1() throws IOException {
        assertCorrectText("They went on to form a new group. The bacteria causes a blood clot to form in the jugular vein.");
    }

    @Test
    public void testRule_2() throws IOException {
        assertCorrectText("Asia Global Crossing Ltd. Global Crossing and Asia Global Crossing.");
    }

    @Test
    public void testRule_3() throws IOException {
        assertCorrectText("It was a global effort. Announcing the participation of Enron Global Markets.");
    }

    @Test
    public void testRule_4() throws IOException {
        assertCorrectText("Matthew S. Anderson, Peter the Great. The Tomahawks were shipped from Great Britain.");
    }

    @Test
    public void testRule_5() throws IOException {
        assertCorrectText("It was great. The Tomahawks were shipped from Great Britain.");
    }

    @Test
    public void testRule_6() throws IOException {
        assertCorrectText("I found it very interesting. An interesting fact about me is that I have a twin.");
    }

    @Test
    public void testRule_7() throws IOException {
        assertCorrectText("Maybe it's because I used to be a FLAMING LIBERAL? Or maybe it's because with age, I developed EYES THAT SEE!");
    }

    @Test
    public void testRule_8() throws IOException {
        assertCorrectText("It needs to be done. That needs to be done.");
    }

    @Test
    public void testRule_9() throws IOException {
        assertCorrectText("This was needed. There is a need to do it.");
    }

    @Test
    public void testRule_10() throws IOException {
        assertCorrectText("It needs to be done. That also needed to be done.");
    }

    @Test
    public void testRule_11() throws IOException {
        assertCorrectText("I still need to sign in somewhere. You need to sign in too.");
    }

    @Test
    public void testRule_12() throws IOException {
        assertCorrectText("This is a new experience. Happy New Year!");
    }

    @Test
    public void testRule_13() throws IOException {
        assertCorrectText("It's often gloomy outside. More often than not, it's raining.");
    }

    @Test
    public void testRule_14() throws IOException {
        assertCorrectText("We have bad weather here often. Often times, it's raining.");
    }

    @Test
    public void testRule_15() throws IOException {
        assertCorrectText("The students were given some problems. They needed help to solve the problems.");
    }

    @Test
    public void testRule_16() throws IOException {
        assertCorrectText("Then, there were numerous problems after that. His initial interest lay in an attempt to solve Hilbert's fifth problem.");
    }

    @Test
    public void testRule_17() throws IOException {
        assertCorrectText("There were some problems with the tests. No problem, I'm not in a rush.");
    }

    @Test
    public void testRule_18() throws IOException {
        assertCorrectText("The students were given some problems. They were math problems.");
    }

    @Test
    public void testRule_19() throws IOException {
        assertCorrectText("We noticed them several times. Several thousand people stormed the gate.");
    }

    @Test
    public void testRule_20() throws IOException {
        assertCorrectText("I suggested that, but he also suggests that.");
    }

    @Test
    public void testRule_21() throws IOException {
        assertCorrectText("He suggested that we review them again. What do these suggest about the transaction history?");
    }

    @Test
    public void testRule_22() throws IOException {
        assertCorrectText("I suggested he look it over again. This strongly suggests that Mr. Batt is guilty.");
    }

    @Test
    public void testRule_23() throws IOException {
        assertCorrectText("In this example, persons is used instead of people because the law applies to the individuals and never to the group as a whole.");
    }

    @Test
    public void testRule_24() throws IOException {
        assertCorrectText("I suggested this. She suggests that");
    }

    @Test
    public void testRule_25_testMerged_25() throws IOException {
        RuleMatch[] matches = getRuleMatches("I suggested this. She suggests that.");
        assertEquals(1, matches.length);
        assertEquals(22, matches[0].getFromPos());
        assertEquals(30, matches[0].getToPos());
        assertEquals("proposes", matches[0].getSuggestedReplacements().get(0));
        assertEquals("recommends", matches[0].getSuggestedReplacements().get(1));
        assertEquals("submits", matches[0].getSuggestedReplacements().get(2));
        matches = getRuleMatches("I suggested this. She suggests that. And they suggested that.");
        assertEquals(2, matches.length);
        assertEquals(46, matches[1].getFromPos());
        assertEquals("proposed", matches[1].getSuggestedReplacements().get(0));
        matches = getRuleMatches("The problem was weird. And the solutions needed to be weird.");
        assertEquals("odd", matches[0].getSuggestedReplacements().get(0));
    }
}
