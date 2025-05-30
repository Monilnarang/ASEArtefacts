package org.languagetool.rules.en;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.*;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.languagetool.rules.en.AvsAnRule.Determiner;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class AvsAnRuleTest_Parameterized {

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
    public void testRule_37() throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence("It was a uninteresting talk with an long sentence."));
        assertEquals(2, matches.length);
    }

    @Test
    public void testGetCorrectDeterminerFor_8() throws IOException {
        assertEquals(Determiner.A_OR_AN, getDeterminerFor("historical"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRule_1to29_38to39_45to47_49to52")
    public void testRule_1to29_38to39_45to47_49to52(String param1) throws IOException {
        assertCorrect(param1);
    }

    static public Stream<Arguments> Provider_testRule_1to29_38to39_45to47_49to52() {
        return Stream.of(arguments("It must be an xml name."), arguments("analyze an hprof file"), arguments("This is an sbt project."), arguments("Import an Xcode project."), arguments("This is a oncer."), arguments("She was a Oaxacan chef."), arguments("The doctor requested a urinalysis."), arguments("She brought a Ouija board."), arguments("This is a test sentence."), arguments("It was an hour ago."), arguments("A university is ..."), arguments("A one-way street ..."), arguments("An hour's work ..."), arguments("Going to an \"industry party\"."), arguments("An 8-year old boy ..."), arguments("An 18-year old boy ..."), arguments("The A-levels are ..."), arguments("An NOP check ..."), arguments("A USA-wide license ..."), arguments("...asked a UN member."), arguments("In an un-united Germany..."), arguments("Here, a and b are supplementary angles."), arguments("The Qur'an was translated into Polish."), arguments("See an:Grammatica"), arguments("See http://www.an.com"), arguments("Station A equals station B."), arguments("e.g., the case endings -a -i -u and mood endings -u -a"), arguments("A'ight, y'all."), arguments("He also wrote the comic strips Abbie an' Slats."), arguments("A University"), arguments("A Europe wide something"), arguments("A. R.J. Turgot"), arguments("Make sure that 3.a as well as 3.b are correct."), arguments("Anyone for an MSc?"), arguments("Anyone for an XMR-based writer?"), arguments("Its name in English is a[1] (), plural A's, As, as, or a's."), arguments("An historic event"), arguments("A historic event"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRule_30to36_40to44_48")
    public void testRule_30to36_40to44_48(String param1) throws IOException {
        assertIncorrect(param1);
    }

    static public Stream<Arguments> Provider_testRule_30to36_40to44_48() {
        return Stream.of(arguments("It was a hour ago."), arguments("It was an sentence that's long."), arguments("It was a uninteresting talk."), arguments("An university"), arguments("A unintersting ..."), arguments("A hour's work ..."), arguments("Going to a \"industry party\"."), arguments("then an University sdoj fixme sdoopsd"), arguments("A 8-year old boy ..."), arguments("A 18-year old boy ..."), arguments("...asked an UN member."), arguments("In a un-united Germany..."), arguments("Anyone for a MSc?"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSuggestions_1to6")
    public void testSuggestions_1to6(String param1, String param2) throws IOException {
        assertEquals(param1, rule.suggestAorAn(param2));
    }

    static public Stream<Arguments> Provider_testSuggestions_1to6() {
        return Stream.of(arguments("a string", "string"), arguments("a university", "university"), arguments("an hour", "hour"), arguments("an all-terrain", "all-terrain"), arguments("a UNESCO", "UNESCO"), arguments("a historical", "historical"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetCorrectDeterminerFor_1to4")
    public void testGetCorrectDeterminerFor_1to4(String param1) throws IOException {
        assertEquals(Determiner.A, getDeterminerFor(param1));
    }

    static public Stream<Arguments> Provider_testGetCorrectDeterminerFor_1to4() {
        return Stream.of(arguments("string"), arguments("university"), arguments("UNESCO"), arguments("one-way"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetCorrectDeterminerFor_5to7")
    public void testGetCorrectDeterminerFor_5to7(String param1) throws IOException {
        assertEquals(Determiner.AN, getDeterminerFor(param1));
    }

    static public Stream<Arguments> Provider_testGetCorrectDeterminerFor_5to7() {
        return Stream.of(arguments("interesting"), arguments("hour"), arguments("all-terrain"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetCorrectDeterminerFor_9to11")
    public void testGetCorrectDeterminerFor_9to11(String param1) throws IOException {
        assertEquals(Determiner.UNKNOWN, getDeterminerFor(param1));
    }

    static public Stream<Arguments> Provider_testGetCorrectDeterminerFor_9to11() {
        return Stream.of(arguments(""), arguments("-way"), arguments("camelCase"));
    }
}
