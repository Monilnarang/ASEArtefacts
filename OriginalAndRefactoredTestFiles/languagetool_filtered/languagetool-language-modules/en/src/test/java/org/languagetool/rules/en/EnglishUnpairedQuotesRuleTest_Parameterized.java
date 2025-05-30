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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class EnglishUnpairedQuotesRuleTest_Parameterized {

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

    @ParameterizedTest
    @MethodSource("Provider_testRule_1to5_7to65_67to71_75to78")
    public void testRule_1to5_7to65_67to71_75to78(String param1) throws IOException {
        assertCorrect(param1);
    }

    static public Stream<Arguments> Provider_testRule_1to5_7to65_67to71_75to78() {
        return Stream.of(arguments("This is a word 'test'."), arguments("I don't know."), arguments("This is the joint presidents' declaration."), arguments("The screen is 20\""), arguments("The screen is 20\" wide."), arguments("This is what he said: \"We believe in freedom. This is what we do.\""), arguments("He was an ol' man."), arguments("'till the end."), arguments("jack-o'-lantern"), arguments("jack o'lantern"), arguments("sittin' there"), arguments("Nothin'"), arguments("ya'"), arguments("I'm not goin'"), arguments("y'know"), arguments("Please find attached Fritz' revisions"), arguments("You're only foolin' round."), arguments("I stayed awake 'till the morning."), arguments("under the 'Global Markets' heading"), arguments("He's an 'admin'."), arguments("However, he's still expected to start in the 49ers' next game on Oct."), arguments("all of his great-grandfathers' names"), arguments("Though EES' past profits now are in question"), arguments("Networks' Communicator and FocusFocus' Conference."), arguments("Additional funding came from MegaMags' founders and existing individual investors."), arguments("al-Jazā’er"), arguments("second Mu’taq and third"), arguments("second Mu'taq and third"), arguments("The phrase ‘\\1 \\2’ is British English."), arguments("The phrase ‘1 2’ is British English."), arguments("22' N., long. "), arguments("11º 22'"), arguments("11° 22'"), arguments("11° 22.5'"), arguments("In case I garbled mine, here 'tis."), arguments("It's about three o’clock."), arguments("It's about three o'clock."), arguments("Rory O’More"), arguments("Rory O'More"), arguments("Côte d’Ivoire"), arguments("Côte d'Ivoire"), arguments("Colonel d’Aubigni"), arguments("They are members of the Bahá'í Faith."), arguments("This is a \"special test\", right?"), arguments("In addition, the government would pay a $1,000 \"cost of education\" grant to the schools."), arguments("Paradise lost to the alleged water needs of Texas' big cities Thursday."), arguments("Kill 'em all!"), arguments("Puttin' on the Ritz"), arguments("Dunkin' Donuts"), arguments("Hold 'em!"), arguments("(Ketab fi Isti'mal al-'Adad al-Hindi)"), arguments("(al-'Adad al-Hindi)"), arguments("On their 'host' societies."), arguments("On their 'host society'."), arguments("Burke-rostagno the Richard S. Burkes' home in Wayne may be the setting for the wedding reception for their daughter."), arguments("The '49 team was off to a so-so 5-5 beginning"), arguments("The best reason that can be advanced for the state adopting the practice was the advent of expanded highway construction during the 1920s and '30s."), arguments("A Republican survey says Kennedy won the '60 election on the religious issue."), arguments("Economy class seats have a seat pitch of 31-33\", with newer aircraft having thinner seats that have a 31\" pitch."), arguments("\"02\" will sort before \"10\" as expected so it will have size of 10\"."), arguments("\"02\" will sort before \"10\" as expected so it will have size of 10\""), arguments("\"02\" will sort before \"10\""), arguments("On their 'host societies'."), arguments("On their host 'societies'."), arguments("On their host societies'."), arguments("I think that Liszt's \"Forgotten Waltz No.3\" is a hidden masterpiece."), arguments("I think that Liszt's \"Forgotten Waltz No. 3\" is a hidden masterpiece."), arguments("Turkish distinguishes between dotted and dotless \"I\"s."), arguments("It has recognized no \"bora\"-like pattern in his behaviour."), arguments("Some text. This is \"12345\", a number."), arguments("Some text.\n\nThis is \"12345\", a number."), arguments("Some text. This is 12345\", a number."), arguments("Some text. This is 12345\", a number."));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRule_6_66_72to74")
    public void testRule_6_66_72to74(String param1) throws IOException {
        assertIncorrect(param1);
    }

    static public Stream<Arguments> Provider_testRule_6_66_72to74() {
        return Stream.of(arguments("The screen is very\" wide."), arguments("On their 'host societies."), arguments("This is a test with an apostrophe &'."), arguments("He is making them feel comfortable all along.\""), arguments("\"He is making them feel comfortable all along."));
    }
}
