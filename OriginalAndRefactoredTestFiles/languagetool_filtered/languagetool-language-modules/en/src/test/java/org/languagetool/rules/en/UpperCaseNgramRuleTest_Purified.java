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

public class UpperCaseNgramRuleTest_Purified {

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

    @Test
    public void testRule_1() throws IOException {
        assertGood("The New York Times reviews their gallery all the time.");
    }

    @Test
    public void testRule_2() throws IOException {
        assertGood("This Was a Good Idea");
    }

    @Test
    public void testRule_3() throws IOException {
        assertGood("Professor Sprout acclimated the plant to a new environment.");
    }

    @Test
    public void testRule_4() throws IOException {
        assertGood("Beauty products, Clean & Clear facial wash.");
    }

    @Test
    public void testRule_5() throws IOException {
        assertGood("Please click Account > Withdraw > Update.");
    }

    @Test
    public void testRule_6() throws IOException {
        assertGood("The goal is to Develop, Discuss and Learn.");
    }

    @Test
    public void testRule_7() throws IOException {
        assertGood("(b) Summarize the strategy.");
    }

    @Test
    public void testRule_8() throws IOException {
        assertGood("Figure/Ground:");
    }

    @Test
    public void testRule_9() throws IOException {
        assertGood("What Happened?");
    }

    @Test
    public void testRule_10() throws IOException {
        assertGood("1- Have you personally made any improvements?");
    }

    @Test
    public void testRule_11() throws IOException {
        assertGood("Lesson #1 - Create a webinar.");
    }

    @Test
    public void testRule_12() throws IOException {
        assertGood("Please refund Order #5698656.");
    }

    @Test
    public void testRule_13() throws IOException {
        assertGood("Let's play games at Games.co.uk.");
    }

    @Test
    public void testRule_14() throws IOException {
        assertGood("Ben (Been).");
    }

    @Test
    public void testRule_15() throws IOException {
        assertGood("C stands for Curse.");
    }

    @Test
    public void testRule_16() throws IOException {
        assertGood("The United States also used the short-lived slogan, \"Tastes So Good, You'll Roar\", in the early 1980s.");
    }

    @Test
    public void testRule_17() throws IOException {
        assertGood("09/06 - Spoken to the business manager.");
    }

    @Test
    public void testRule_18() throws IOException {
        assertGood("12.3 Game.");
    }

    @Test
    public void testRule_19() throws IOException {
        assertGood("Let's talk to the Onboarding team.");
    }

    @Test
    public void testRule_20() throws IOException {
        assertGood("My name is Gentle.");
    }

    @Test
    public void testRule_21() throws IOException {
        assertGood("They called it Greet.");
    }

    @Test
    public void testRule_22() throws IOException {
        assertGood("What is Foreshadowing?");
    }

    @Test
    public void testRule_23() throws IOException {
        assertGood("His name is Carp.");
    }

    @Test
    public void testRule_24() throws IOException {
        assertGood("Victor or Rabbit as everyone calls him.");
    }

    @Test
    public void testRule_25() throws IOException {
        assertGood("Think I'm Tripping?");
    }

    @Test
    public void testRule_26() throws IOException {
        assertGood("Music and Concepts.");
    }

    @Test
    public void testRule_27() throws IOException {
        assertGood("It is called Ranked mode.");
    }

    @Test
    public void testRule_28() throws IOException {
        assertGood("I was into Chronicle of a Death Foretold.");
    }

    @Test
    public void testRule_29() throws IOException {
        assertGood("I talked with Engineering.");
    }

    @Test
    public void testRule_30() throws IOException {
        assertGood("They used Draft.js to solve it.");
    }

    @Test
    public void testRule_31() throws IOException {
        assertGood("And mine is Wed.");
    }

    @Test
    public void testRule_32() throws IOException {
        assertGood("I would support Knicks rather than Hawks.");
    }

    @Test
    public void testRule_33() throws IOException {
        assertGood("You Can't Judge a Book by the Cover");
    }

    @Test
    public void testRule_34() throws IOException {
        assertGood("What Does an Effective Cover Letter Look Like?");
    }

    @Test
    public void testRule_35() throws IOException {
        assertGood("Our external Counsel are reviewing the authority of FMPA to enter into the proposed transaction");
    }

    @Test
    public void testRule_36() throws IOException {
        assertGood("Otherwise, Staff will proceed to process your filing based on the pro forma tariff sheets submitted on August 15, 2000.");
    }

    @Test
    public void testRule_37() throws IOException {
        assertGood("But he is not accomplishing enough statistically to help most Fantasy teams.");
    }

    @Test
    public void testRule_38() throws IOException {
        assertGood("(4 hrs/wk) Manage all IT affairs.");
    }

    @Test
    public void testRule_39() throws IOException {
        assertGood("(Laravel MVC) Implements two distinct working algorithms.");
    }

    @Test
    public void testRule_40() throws IOException {
        assertGood("(Later) Connect different cont.");
    }

    @Test
    public void testRule_41() throws IOException {
        assertGood("$$/month (Includes everything!)");
    }

    @Test
    public void testRule_42() throws IOException {
        assertGood("- Foot care (Cleaning of feet, wash...");
    }

    @Test
    public void testRule_43() throws IOException {
        assertGood("- Exercise (Engage in exercises...");
    }

    @Test
    public void testRule_44() throws IOException {
        assertGood("-> Allowed the civilian government...");
    }

    @Test
    public void testRule_45() throws IOException {
        assertGood("-> Led by Italian Physicians...");
    }

    @Test
    public void testRule_46() throws IOException {
        assertGood("-> Used as inspiration...");
    }

    @Test
    public void testRule_47() throws IOException {
        assertGood("The sign read \"Seats to be Added\".");
    }

    @Test
    public void testRule_48() throws IOException {
        assertGood("“Helm, Engage.”");
    }

    @Test
    public void testRule_49() throws IOException {
        assertGood("\"Be careful, Reign!\"");
    }

    @Test
    public void testRule_50() throws IOException {
        assertGood("ii) Expanded the notes.");
    }

    @Test
    public void testRule_51() throws IOException {
        assertMatch("I really Like spaghetti.");
    }

    @Test
    public void testRule_52() throws IOException {
        assertMatch("This Was a good idea.");
    }

    @Test
    public void testRule_53() throws IOException {
        assertMatch("But this Was a good idea.");
    }

    @Test
    public void testRule_54() throws IOException {
        assertMatch("This indeed Was a good idea.");
    }
}
