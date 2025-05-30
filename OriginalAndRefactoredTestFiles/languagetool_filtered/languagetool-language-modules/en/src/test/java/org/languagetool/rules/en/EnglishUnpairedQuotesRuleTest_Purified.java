package org.languagetool.rules.en;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.TestTools;
import org.languagetool.markup.AnnotatedText;
import org.languagetool.markup.AnnotatedTextBuilder;
import org.languagetool.rules.RuleMatch;
import org.languagetool.rules.TextLevelRule;
import java.io.IOException;
import java.util.Collections;
import static org.junit.Assert.assertEquals;

public class EnglishUnpairedQuotesRuleTest_Purified {

    private TextLevelRule rule;

    private JLanguageTool lt;

    @Before
    public void setUp() {
        rule = new EnglishUnpairedQuotesRule(TestTools.getEnglishMessages(), Languages.getLanguageForShortCode("en"));
        lt = new JLanguageTool(Languages.getLanguageForShortCode("en"));
    }

    private void assertCorrect(String sentence) throws IOException {
        RuleMatch[] matches = rule.match(Collections.singletonList(lt.getAnalyzedSentence(sentence)));
        assertEquals(0, matches.length);
    }

    private void assertCorrectText(String sentences) throws IOException {
        AnnotatedText aText = new AnnotatedTextBuilder().addText(sentences).build();
        RuleMatch[] matches = rule.match(lt.analyzeText(sentences), aText);
        assertEquals(0, matches.length);
    }

    private void assertIncorrect(String sentence) throws IOException {
        RuleMatch[] matches = rule.match(Collections.singletonList(lt.getAnalyzedSentence(sentence)));
        assertEquals(1, matches.length);
    }

    private int getMatches(String input, JLanguageTool lt) throws IOException {
        return lt.check(input).size();
    }

    @Test
    public void testRule_1() throws IOException {
        assertCorrect("This is a word 'test'.");
    }

    @Test
    public void testRule_2() throws IOException {
        assertCorrect("I don't know.");
    }

    @Test
    public void testRule_3() throws IOException {
        assertCorrect("This is the joint presidents' declaration.");
    }

    @Test
    public void testRule_4() throws IOException {
        assertCorrect("The screen is 20\"");
    }

    @Test
    public void testRule_5() throws IOException {
        assertCorrect("The screen is 20\" wide.");
    }

    @Test
    public void testRule_6() throws IOException {
        assertIncorrect("The screen is very\" wide.");
    }

    @Test
    public void testRule_7() throws IOException {
        assertCorrect("This is what he said: \"We believe in freedom. This is what we do.\"");
    }

    @Test
    public void testRule_8() throws IOException {
        assertCorrect("He was an ol' man.");
    }

    @Test
    public void testRule_9() throws IOException {
        assertCorrect("'till the end.");
    }

    @Test
    public void testRule_10() throws IOException {
        assertCorrect("jack-o'-lantern");
    }

    @Test
    public void testRule_11() throws IOException {
        assertCorrect("jack o'lantern");
    }

    @Test
    public void testRule_12() throws IOException {
        assertCorrect("sittin' there");
    }

    @Test
    public void testRule_13() throws IOException {
        assertCorrect("Nothin'");
    }

    @Test
    public void testRule_14() throws IOException {
        assertCorrect("ya'");
    }

    @Test
    public void testRule_15() throws IOException {
        assertCorrect("I'm not goin'");
    }

    @Test
    public void testRule_16() throws IOException {
        assertCorrect("y'know");
    }

    @Test
    public void testRule_17() throws IOException {
        assertCorrect("Please find attached Fritz' revisions");
    }

    @Test
    public void testRule_18() throws IOException {
        assertCorrect("You're only foolin' round.");
    }

    @Test
    public void testRule_19() throws IOException {
        assertCorrect("I stayed awake 'till the morning.");
    }

    @Test
    public void testRule_20() throws IOException {
        assertCorrect("under the 'Global Markets' heading");
    }

    @Test
    public void testRule_21() throws IOException {
        assertCorrect("He's an 'admin'.");
    }

    @Test
    public void testRule_22() throws IOException {
        assertCorrect("However, he's still expected to start in the 49ers' next game on Oct.");
    }

    @Test
    public void testRule_23() throws IOException {
        assertCorrect("all of his great-grandfathers' names");
    }

    @Test
    public void testRule_24() throws IOException {
        assertCorrect("Though EES' past profits now are in question");
    }

    @Test
    public void testRule_25() throws IOException {
        assertCorrect("Networks' Communicator and FocusFocus' Conference.");
    }

    @Test
    public void testRule_26() throws IOException {
        assertCorrect("Additional funding came from MegaMags' founders and existing individual investors.");
    }

    @Test
    public void testRule_27() throws IOException {
        assertCorrect("al-Jazā’er");
    }

    @Test
    public void testRule_28() throws IOException {
        assertCorrect("second Mu’taq and third");
    }

    @Test
    public void testRule_29() throws IOException {
        assertCorrect("second Mu'taq and third");
    }

    @Test
    public void testRule_30() throws IOException {
        assertCorrect("The phrase ‘\\1 \\2’ is British English.");
    }

    @Test
    public void testRule_31() throws IOException {
        assertCorrect("The phrase ‘1 2’ is British English.");
    }

    @Test
    public void testRule_32() throws IOException {
        assertCorrect("22' N., long. ");
    }

    @Test
    public void testRule_33() throws IOException {
        assertCorrect("11º 22'");
    }

    @Test
    public void testRule_34() throws IOException {
        assertCorrect("11° 22'");
    }

    @Test
    public void testRule_35() throws IOException {
        assertCorrect("11° 22.5'");
    }

    @Test
    public void testRule_36() throws IOException {
        assertCorrect("In case I garbled mine, here 'tis.");
    }

    @Test
    public void testRule_37() throws IOException {
        assertCorrect("It's about three o’clock.");
    }

    @Test
    public void testRule_38() throws IOException {
        assertCorrect("It's about three o'clock.");
    }

    @Test
    public void testRule_39() throws IOException {
        assertCorrect("Rory O’More");
    }

    @Test
    public void testRule_40() throws IOException {
        assertCorrect("Rory O'More");
    }

    @Test
    public void testRule_41() throws IOException {
        assertCorrect("Côte d’Ivoire");
    }

    @Test
    public void testRule_42() throws IOException {
        assertCorrect("Côte d'Ivoire");
    }

    @Test
    public void testRule_43() throws IOException {
        assertCorrect("Colonel d’Aubigni");
    }

    @Test
    public void testRule_44() throws IOException {
        assertCorrect("They are members of the Bahá'í Faith.");
    }

    @Test
    public void testRule_45() throws IOException {
        assertCorrect("This is a \"special test\", right?");
    }

    @Test
    public void testRule_46() throws IOException {
        assertCorrect("In addition, the government would pay a $1,000 \"cost of education\" grant to the schools.");
    }

    @Test
    public void testRule_47() throws IOException {
        assertCorrect("Paradise lost to the alleged water needs of Texas' big cities Thursday.");
    }

    @Test
    public void testRule_48() throws IOException {
        assertCorrect("Kill 'em all!");
    }

    @Test
    public void testRule_49() throws IOException {
        assertCorrect("Puttin' on the Ritz");
    }

    @Test
    public void testRule_50() throws IOException {
        assertCorrect("Dunkin' Donuts");
    }

    @Test
    public void testRule_51() throws IOException {
        assertCorrect("Hold 'em!");
    }

    @Test
    public void testRule_52() throws IOException {
        assertCorrect("(Ketab fi Isti'mal al-'Adad al-Hindi)");
    }

    @Test
    public void testRule_53() throws IOException {
        assertCorrect("(al-'Adad al-Hindi)");
    }

    @Test
    public void testRule_54() throws IOException {
        assertCorrect("On their 'host' societies.");
    }

    @Test
    public void testRule_55() throws IOException {
        assertCorrect("On their 'host society'.");
    }

    @Test
    public void testRule_56() throws IOException {
        assertCorrect("Burke-rostagno the Richard S. Burkes' home in Wayne may be the setting for the wedding reception for their daughter.");
    }

    @Test
    public void testRule_57() throws IOException {
        assertCorrect("The '49 team was off to a so-so 5-5 beginning");
    }

    @Test
    public void testRule_58() throws IOException {
        assertCorrect("The best reason that can be advanced for the state adopting the practice was the advent of expanded highway construction during the 1920s and '30s.");
    }

    @Test
    public void testRule_59() throws IOException {
        assertCorrect("A Republican survey says Kennedy won the '60 election on the religious issue.");
    }

    @Test
    public void testRule_60() throws IOException {
        assertCorrect("Economy class seats have a seat pitch of 31-33\", with newer aircraft having thinner seats that have a 31\" pitch.");
    }

    @Test
    public void testRule_61() throws IOException {
        assertCorrect("\"02\" will sort before \"10\" as expected so it will have size of 10\".");
    }

    @Test
    public void testRule_62() throws IOException {
        assertCorrect("\"02\" will sort before \"10\" as expected so it will have size of 10\"");
    }

    @Test
    public void testRule_63() throws IOException {
        assertCorrect("\"02\" will sort before \"10\"");
    }

    @Test
    public void testRule_64() throws IOException {
        assertCorrect("On their 'host societies'.");
    }

    @Test
    public void testRule_65() throws IOException {
        assertCorrect("On their host 'societies'.");
    }

    @Test
    public void testRule_66() throws IOException {
        assertIncorrect("On their 'host societies.");
    }

    @Test
    public void testRule_67() throws IOException {
        assertCorrect("On their host societies'.");
    }

    @Test
    public void testRule_68() throws IOException {
        assertCorrect("I think that Liszt's \"Forgotten Waltz No.3\" is a hidden masterpiece.");
    }

    @Test
    public void testRule_69() throws IOException {
        assertCorrect("I think that Liszt's \"Forgotten Waltz No. 3\" is a hidden masterpiece.");
    }

    @Test
    public void testRule_70() throws IOException {
        assertCorrect("Turkish distinguishes between dotted and dotless \"I\"s.");
    }

    @Test
    public void testRule_71() throws IOException {
        assertCorrect("It has recognized no \"bora\"-like pattern in his behaviour.");
    }

    @Test
    public void testRule_72() throws IOException {
        assertIncorrect("This is a test with an apostrophe &'.");
    }

    @Test
    public void testRule_73() throws IOException {
        assertIncorrect("He is making them feel comfortable all along.\"");
    }

    @Test
    public void testRule_74() throws IOException {
        assertIncorrect("\"He is making them feel comfortable all along.");
    }

    @Test
    public void testRule_75() throws IOException {
        assertCorrect("Some text. This is \"12345\", a number.");
    }

    @Test
    public void testRule_76() throws IOException {
        assertCorrect("Some text.\n\nThis is \"12345\", a number.");
    }

    @Test
    public void testRule_77() throws IOException {
        assertCorrect("Some text. This is 12345\", a number.");
    }

    @Test
    public void testRule_78() throws IOException {
        assertCorrect("Some text. This is 12345\", a number.");
    }

    @Test
    public void testRule_79() throws IOException {
        assertCorrect("\"When you bring someone,\" he said.\n" + "Gibson introduced the short-scale (30.5\") bass in 1961.");
    }

    @Test
    public void testRule_80_testMerged_80() throws IOException {
        RuleMatch[] matches;
        matches = rule.match(Collections.singletonList(lt.getAnalyzedSentence("\"This is a test” sentence.")));
        assertEquals(2, matches.length);
        matches = rule.match(Collections.singletonList(lt.getAnalyzedSentence("This \"is 'a test” sentence.")));
        assertEquals(3, matches.length);
    }
}
