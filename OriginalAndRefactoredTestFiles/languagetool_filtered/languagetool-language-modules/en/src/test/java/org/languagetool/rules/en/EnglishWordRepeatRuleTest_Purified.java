package org.languagetool.rules.en;

import org.junit.Test;
import org.languagetool.*;
import java.io.IOException;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class EnglishWordRepeatRuleTest_Purified {

    private final Language english = Languages.getLanguageForShortCode("en");

    private final EnglishWordRepeatRule rule = new EnglishWordRepeatRule(TestTools.getEnglishMessages(), english);

    private JLanguageTool lt;

    private void assertGood(String sentence) throws IOException {
        assertMatches(sentence, 0);
    }

    private void assertBad(String sentence) throws IOException {
        assertMatches(sentence, 1);
    }

    private void assertMatches(String sentence, int expectedMatches) throws IOException {
        AnalyzedSentence aSentence = lt.getAnalyzedSentence(sentence);
        assertThat(rule.match(aSentence).length, is(expectedMatches));
    }

    @Test
    public void testRepeatRule_1() throws IOException {
        assertGood("This is a test.");
    }

    @Test
    public void testRepeatRule_2() throws IOException {
        assertGood("If I had had time, I would have gone to see him.");
    }

    @Test
    public void testRepeatRule_3() throws IOException {
        assertGood("I don't think that that is a problem.");
    }

    @Test
    public void testRepeatRule_4() throws IOException {
        assertGood("He also said that Azerbaijan had fulfilled a task he set, which was that that their defense budget should exceed the entire state budget of Armenia.");
    }

    @Test
    public void testRepeatRule_5() throws IOException {
        assertGood("Just as if that was proof that that English was correct.");
    }

    @Test
    public void testRepeatRule_6() throws IOException {
        assertGood("It was noticed after more than a month that that promise had not been carried out.");
    }

    @Test
    public void testRepeatRule_7() throws IOException {
        assertGood("It was said that that lady was an actress.");
    }

    @Test
    public void testRepeatRule_8() throws IOException {
        assertGood("Kurosawa's three consecutive movies after Seven Samurai had not managed to capture Japanese audiences in the way that that film had.");
    }

    @Test
    public void testRepeatRule_9() throws IOException {
        assertGood("The can can hold the water.");
    }

    @Test
    public void testRepeatRule_10() throws IOException {
        assertGood("May May awake up?");
    }

    @Test
    public void testRepeatRule_11() throws IOException {
        assertGood("May may awake up.");
    }

    @Test
    public void testRepeatRule_12() throws IOException {
        assertGood("The cat does meow meow");
    }

    @Test
    public void testRepeatRule_13() throws IOException {
        assertGood("Hah Hah");
    }

    @Test
    public void testRepeatRule_14() throws IOException {
        assertGood("Hip Hip Hooray");
    }

    @Test
    public void testRepeatRule_15() throws IOException {
        assertBad("Hip Hip");
    }

    @Test
    public void testRepeatRule_16() throws IOException {
        assertGood("It's S.T.E.A.M.");
    }

    @Test
    public void testRepeatRule_17() throws IOException {
        assertGood("Ok ok ok!");
    }

    @Test
    public void testRepeatRule_18() throws IOException {
        assertGood("O O O");
    }

    @Test
    public void testRepeatRule_19() throws IOException {
        assertGood("Alice and Bob had had a long-standing relationship.");
    }

    @Test
    public void testRepeatRule_20() throws IOException {
        assertBad("I may may awake up.");
    }

    @Test
    public void testRepeatRule_21() throws IOException {
        assertBad("That is May May.");
    }

    @Test
    public void testRepeatRule_22() throws IOException {
        assertGood("Will Will awake up?");
    }

    @Test
    public void testRepeatRule_23() throws IOException {
        assertGood("Will will awake up.");
    }

    @Test
    public void testRepeatRule_24() throws IOException {
        assertBad("I will will awake up.");
    }

    @Test
    public void testRepeatRule_25() throws IOException {
        assertBad("Please wait wait for me.");
    }

    @Test
    public void testRepeatRule_26() throws IOException {
        assertGood("Wait wait!");
    }

    @Test
    public void testRepeatRule_27() throws IOException {
        assertBad("That is Will Will.");
    }

    @Test
    public void testRepeatRule_28() throws IOException {
        assertBad("I will will hold the ladder.");
    }

    @Test
    public void testRepeatRule_29() throws IOException {
        assertBad("You can feel confident that that this administration will continue to support a free and open Internet.");
    }

    @Test
    public void testRepeatRule_30() throws IOException {
        assertBad("This is is a test.");
    }

    @Test
    public void testRepeatRule_31() throws IOException {
        assertGood("b a s i c a l l y");
    }

    @Test
    public void testRepeatRule_32() throws IOException {
        assertGood("You can contact E.ON on Instagram.");
    }

    @Test
    public void testRepeatRule_33() throws IOException {
        assertBad("But I i was not sure.");
    }

    @Test
    public void testRepeatRule_34() throws IOException {
        assertBad("I I am the best.");
    }

    @Test
    public void testRepeatRule_35() throws IOException {
        assertGood("In a land far far away.");
    }

    @Test
    public void testRepeatRule_36() throws IOException {
        assertGood("I love you so so much.");
    }

    @Test
    public void testRepeatRule_37() throws IOException {
        assertGood("Aye aye, sir!");
    }

    @Test
    public void testRepeatRule_38() throws IOException {
        assertGood("What Tom did didn't seem to bother Mary at all.");
    }

    @Test
    public void testRepeatRule_39() throws IOException {
        assertGood("Whatever you do don't leave the lid up on the toilet!");
    }

    @Test
    public void testRepeatRule_40() throws IOException {
        assertGood("Keep your chin up and whatever you do don't doubt yourself or your actions.");
    }

    @Test
    public void testRepeatRule_41() throws IOException {
        assertGood("I know that that can't really happen.");
    }

    @Test
    public void testRepeatRule_42() throws IOException {
        assertGood("Please pass her her phone.");
    }
}
