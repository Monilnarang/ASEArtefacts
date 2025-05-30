package org.languagetool.rules.en;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.*;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.languagetool.rules.en.AvsAnRule.Determiner;

public class AvsAnRuleTest_Purified {

    private AvsAnRule rule;

    private JLanguageTool lt;

    @Before
    public void setUp() throws IOException {
        rule = new AvsAnRule(TestTools.getEnglishMessages());
        lt = new JLanguageTool(Languages.getLanguageForShortCode("en"));
    }

    private void assertCorrect(String sentence) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(sentence));
        assertEquals(0, matches.length);
    }

    private void assertIncorrect(String sentence) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(sentence));
        assertEquals(1, matches.length);
    }

    private Determiner getDeterminerFor(String word) {
        AnalyzedTokenReadings token = new AnalyzedTokenReadings(new AnalyzedToken(word, "fake-postag", "fake-lemma"), 0);
        return rule.getCorrectDeterminerFor(token);
    }

    @Test
    public void testRule_1() throws IOException {
        assertCorrect("It must be an xml name.");
    }

    @Test
    public void testRule_2() throws IOException {
        assertCorrect("analyze an hprof file");
    }

    @Test
    public void testRule_3() throws IOException {
        assertCorrect("This is an sbt project.");
    }

    @Test
    public void testRule_4() throws IOException {
        assertCorrect("Import an Xcode project.");
    }

    @Test
    public void testRule_5() throws IOException {
        assertCorrect("This is a oncer.");
    }

    @Test
    public void testRule_6() throws IOException {
        assertCorrect("She was a Oaxacan chef.");
    }

    @Test
    public void testRule_7() throws IOException {
        assertCorrect("The doctor requested a urinalysis.");
    }

    @Test
    public void testRule_8() throws IOException {
        assertCorrect("She brought a Ouija board.");
    }

    @Test
    public void testRule_9() throws IOException {
        assertCorrect("This is a test sentence.");
    }

    @Test
    public void testRule_10() throws IOException {
        assertCorrect("It was an hour ago.");
    }

    @Test
    public void testRule_11() throws IOException {
        assertCorrect("A university is ...");
    }

    @Test
    public void testRule_12() throws IOException {
        assertCorrect("A one-way street ...");
    }

    @Test
    public void testRule_13() throws IOException {
        assertCorrect("An hour's work ...");
    }

    @Test
    public void testRule_14() throws IOException {
        assertCorrect("Going to an \"industry party\".");
    }

    @Test
    public void testRule_15() throws IOException {
        assertCorrect("An 8-year old boy ...");
    }

    @Test
    public void testRule_16() throws IOException {
        assertCorrect("An 18-year old boy ...");
    }

    @Test
    public void testRule_17() throws IOException {
        assertCorrect("The A-levels are ...");
    }

    @Test
    public void testRule_18() throws IOException {
        assertCorrect("An NOP check ...");
    }

    @Test
    public void testRule_19() throws IOException {
        assertCorrect("A USA-wide license ...");
    }

    @Test
    public void testRule_20() throws IOException {
        assertCorrect("...asked a UN member.");
    }

    @Test
    public void testRule_21() throws IOException {
        assertCorrect("In an un-united Germany...");
    }

    @Test
    public void testRule_22() throws IOException {
        assertCorrect("Here, a and b are supplementary angles.");
    }

    @Test
    public void testRule_23() throws IOException {
        assertCorrect("The Qur'an was translated into Polish.");
    }

    @Test
    public void testRule_24() throws IOException {
        assertCorrect("See an:Grammatica");
    }

    @Test
    public void testRule_25() throws IOException {
        assertCorrect("See http://www.an.com");
    }

    @Test
    public void testRule_26() throws IOException {
        assertCorrect("Station A equals station B.");
    }

    @Test
    public void testRule_27() throws IOException {
        assertCorrect("e.g., the case endings -a -i -u and mood endings -u -a");
    }

    @Test
    public void testRule_28() throws IOException {
        assertCorrect("A'ight, y'all.");
    }

    @Test
    public void testRule_29() throws IOException {
        assertCorrect("He also wrote the comic strips Abbie an' Slats.");
    }

    @Test
    public void testRule_30() throws IOException {
        assertIncorrect("It was a hour ago.");
    }

    @Test
    public void testRule_31() throws IOException {
        assertIncorrect("It was an sentence that's long.");
    }

    @Test
    public void testRule_32() throws IOException {
        assertIncorrect("It was a uninteresting talk.");
    }

    @Test
    public void testRule_33() throws IOException {
        assertIncorrect("An university");
    }

    @Test
    public void testRule_34() throws IOException {
        assertIncorrect("A unintersting ...");
    }

    @Test
    public void testRule_35() throws IOException {
        assertIncorrect("A hour's work ...");
    }

    @Test
    public void testRule_36() throws IOException {
        assertIncorrect("Going to a \"industry party\".");
    }

    @Test
    public void testRule_37() throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence("It was a uninteresting talk with an long sentence."));
        assertEquals(2, matches.length);
    }

    @Test
    public void testRule_38() throws IOException {
        assertCorrect("A University");
    }

    @Test
    public void testRule_39() throws IOException {
        assertCorrect("A Europe wide something");
    }

    @Test
    public void testRule_40() throws IOException {
        assertIncorrect("then an University sdoj fixme sdoopsd");
    }

    @Test
    public void testRule_41() throws IOException {
        assertIncorrect("A 8-year old boy ...");
    }

    @Test
    public void testRule_42() throws IOException {
        assertIncorrect("A 18-year old boy ...");
    }

    @Test
    public void testRule_43() throws IOException {
        assertIncorrect("...asked an UN member.");
    }

    @Test
    public void testRule_44() throws IOException {
        assertIncorrect("In a un-united Germany...");
    }

    @Test
    public void testRule_45() throws IOException {
        assertCorrect("A. R.J. Turgot");
    }

    @Test
    public void testRule_46() throws IOException {
        assertCorrect("Make sure that 3.a as well as 3.b are correct.");
    }

    @Test
    public void testRule_47() throws IOException {
        assertCorrect("Anyone for an MSc?");
    }

    @Test
    public void testRule_48() throws IOException {
        assertIncorrect("Anyone for a MSc?");
    }

    @Test
    public void testRule_49() throws IOException {
        assertCorrect("Anyone for an XMR-based writer?");
    }

    @Test
    public void testRule_50() throws IOException {
        assertCorrect("Its name in English is a[1] (), plural A's, As, as, or a's.");
    }

    @Test
    public void testRule_51() throws IOException {
        assertCorrect("An historic event");
    }

    @Test
    public void testRule_52() throws IOException {
        assertCorrect("A historic event");
    }

    @Test
    public void testSuggestions_1() throws IOException {
        assertEquals("a string", rule.suggestAorAn("string"));
    }

    @Test
    public void testSuggestions_2() throws IOException {
        assertEquals("a university", rule.suggestAorAn("university"));
    }

    @Test
    public void testSuggestions_3() throws IOException {
        assertEquals("an hour", rule.suggestAorAn("hour"));
    }

    @Test
    public void testSuggestions_4() throws IOException {
        assertEquals("an all-terrain", rule.suggestAorAn("all-terrain"));
    }

    @Test
    public void testSuggestions_5() throws IOException {
        assertEquals("a UNESCO", rule.suggestAorAn("UNESCO"));
    }

    @Test
    public void testSuggestions_6() throws IOException {
        assertEquals("a historical", rule.suggestAorAn("historical"));
    }

    @Test
    public void testGetCorrectDeterminerFor_1() throws IOException {
        assertEquals(Determiner.A, getDeterminerFor("string"));
    }

    @Test
    public void testGetCorrectDeterminerFor_2() throws IOException {
        assertEquals(Determiner.A, getDeterminerFor("university"));
    }

    @Test
    public void testGetCorrectDeterminerFor_3() throws IOException {
        assertEquals(Determiner.A, getDeterminerFor("UNESCO"));
    }

    @Test
    public void testGetCorrectDeterminerFor_4() throws IOException {
        assertEquals(Determiner.A, getDeterminerFor("one-way"));
    }

    @Test
    public void testGetCorrectDeterminerFor_5() throws IOException {
        assertEquals(Determiner.AN, getDeterminerFor("interesting"));
    }

    @Test
    public void testGetCorrectDeterminerFor_6() throws IOException {
        assertEquals(Determiner.AN, getDeterminerFor("hour"));
    }

    @Test
    public void testGetCorrectDeterminerFor_7() throws IOException {
        assertEquals(Determiner.AN, getDeterminerFor("all-terrain"));
    }

    @Test
    public void testGetCorrectDeterminerFor_8() throws IOException {
        assertEquals(Determiner.A_OR_AN, getDeterminerFor("historical"));
    }

    @Test
    public void testGetCorrectDeterminerFor_9() throws IOException {
        assertEquals(Determiner.UNKNOWN, getDeterminerFor(""));
    }

    @Test
    public void testGetCorrectDeterminerFor_10() throws IOException {
        assertEquals(Determiner.UNKNOWN, getDeterminerFor("-way"));
    }

    @Test
    public void testGetCorrectDeterminerFor_11() throws IOException {
        assertEquals(Determiner.UNKNOWN, getDeterminerFor("camelCase"));
    }
}
