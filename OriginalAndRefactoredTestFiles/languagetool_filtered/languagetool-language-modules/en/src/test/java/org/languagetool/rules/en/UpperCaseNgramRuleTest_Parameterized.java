package org.languagetool.rules.en;

import org.junit.Test;
import org.languagetool.*;
import org.languagetool.languagemodel.LanguageModel;
import org.languagetool.rules.RuleMatch;
import org.languagetool.rules.ngrams.FakeLanguageModel;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class UpperCaseNgramRuleTest_Parameterized {

    private final static Map<String, Integer> map = new HashMap<>();

    static {
        map.put("really like", 100);
        map.put("like spaghetti", 100);
        map.put("This was", 100);
        map.put("This was a", 10);
        map.put("this was", 100);
        map.put("this was a", 10);
        map.put("indeed was", 100);
        map.put("indeed was a", 10);
    }

    private final LanguageModel lm = new FakeLanguageModel(map);

    private final Language lang = Languages.getLanguageForShortCode("en");

    private final UpperCaseNgramRule rule = new UpperCaseNgramRule(TestTools.getEnglishMessages(), lm, lang, null);

    private final JLanguageTool lt = new JLanguageTool(lang);

    private void assertGood(String s) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(s));
        assertTrue("Expected no matches, got: " + Arrays.toString(matches), matches.length == 0);
    }

    private void assertMatch(String s) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(s));
        assertTrue("Expected 1 match, got: " + Arrays.toString(matches), matches.length == 1);
    }

    @ParameterizedTest
    @MethodSource("Provider_testRule_1to50")
    public void testRule_1to50(String param1) throws IOException {
        assertGood(param1);
    }

    static public Stream<Arguments> Provider_testRule_1to50() {
        return Stream.of(arguments("The New York Times reviews their gallery all the time."), arguments("This Was a Good Idea"), arguments("Professor Sprout acclimated the plant to a new environment."), arguments("Beauty products, Clean & Clear facial wash."), arguments("Please click Account > Withdraw > Update."), arguments("The goal is to Develop, Discuss and Learn."), arguments("(b) Summarize the strategy."), arguments("Figure/Ground:"), arguments("What Happened?"), arguments("1- Have you personally made any improvements?"), arguments("Lesson #1 - Create a webinar."), arguments("Please refund Order #5698656."), arguments("Let's play games at Games.co.uk."), arguments("Ben (Been)."), arguments("C stands for Curse."), arguments("The United States also used the short-lived slogan, \"Tastes So Good, You'll Roar\", in the early 1980s."), arguments("09/06 - Spoken to the business manager."), arguments("12.3 Game."), arguments("Let's talk to the Onboarding team."), arguments("My name is Gentle."), arguments("They called it Greet."), arguments("What is Foreshadowing?"), arguments("His name is Carp."), arguments("Victor or Rabbit as everyone calls him."), arguments("Think I'm Tripping?"), arguments("Music and Concepts."), arguments("It is called Ranked mode."), arguments("I was into Chronicle of a Death Foretold."), arguments("I talked with Engineering."), arguments("They used Draft.js to solve it."), arguments("And mine is Wed."), arguments("I would support Knicks rather than Hawks."), arguments("You Can't Judge a Book by the Cover"), arguments("What Does an Effective Cover Letter Look Like?"), arguments("Our external Counsel are reviewing the authority of FMPA to enter into the proposed transaction"), arguments("Otherwise, Staff will proceed to process your filing based on the pro forma tariff sheets submitted on August 15, 2000."), arguments("But he is not accomplishing enough statistically to help most Fantasy teams."), arguments("(4 hrs/wk) Manage all IT affairs."), arguments("(Laravel MVC) Implements two distinct working algorithms."), arguments("(Later) Connect different cont."), arguments("$$/month (Includes everything!)"), arguments("- Foot care (Cleaning of feet, wash..."), arguments("- Exercise (Engage in exercises..."), arguments("-> Allowed the civilian government..."), arguments("-> Led by Italian Physicians..."), arguments("-> Used as inspiration..."), arguments("The sign read \"Seats to be Added\"."), arguments("“Helm, Engage.”"), arguments("\"Be careful, Reign!\""), arguments("ii) Expanded the notes."));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRule_51to54")
    public void testRule_51to54(String param1) throws IOException {
        assertMatch(param1);
    }

    static public Stream<Arguments> Provider_testRule_51to54() {
        return Stream.of(arguments("I really Like spaghetti."), arguments("This Was a good idea."), arguments("But this Was a good idea."), arguments("This indeed Was a good idea."));
    }
}
