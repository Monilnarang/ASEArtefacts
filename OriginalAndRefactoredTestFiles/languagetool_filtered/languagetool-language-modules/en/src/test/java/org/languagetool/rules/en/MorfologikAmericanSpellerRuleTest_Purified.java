package org.languagetool.rules.en;

import org.junit.BeforeClass;
import org.junit.Test;
import org.languagetool.*;
import org.languagetool.language.CanadianEnglish;
import org.languagetool.language.English;
import org.languagetool.languagemodel.LanguageModel;
import org.languagetool.rules.Rule;
import org.languagetool.rules.RuleMatch;
import org.languagetool.rules.ngrams.FakeLanguageModel;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;

public class MorfologikAmericanSpellerRuleTest_Purified extends AbstractEnglishSpellerRuleTest {

    private static final Language language = Languages.getLanguageForShortCode("en-US");

    private static MorfologikAmericanSpellerRule rule;

    private static JLanguageTool lt;

    private static MorfologikCanadianSpellerRule caRule;

    private static JLanguageTool caLangTool;

    @BeforeClass
    public static void setup() throws IOException {
        rule = new MorfologikAmericanSpellerRule(TestTools.getMessages("en"), language);
        lt = new JLanguageTool(language);
        English canadianEnglish = CanadianEnglish.getInstance();
        caRule = new MorfologikCanadianSpellerRule(TestTools.getMessages("en"), canadianEnglish, null, emptyList());
        caLangTool = new JLanguageTool(canadianEnglish);
    }

    private void assertSuggestion(String input, String... expectedSuggestions) throws IOException {
        assertSuggestion(input, rule, lt, expectedSuggestions);
    }

    private void assertSuggestion(String input, Rule rule, JLanguageTool lt, String... expectedSuggestions) throws IOException {
        assertSuggestion(input, singletonList(Arrays.asList(expectedSuggestions)), lt, rule);
    }

    private void assertSuggestion(String input, List<List<String>> expectedSuggestionLists, JLanguageTool lt, Rule rule) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(input));
        assertThat("Expected " + expectedSuggestionLists.size() + " match, got: " + Arrays.toString(matches), matches.length, is(expectedSuggestionLists.size()));
        int i = 0;
        for (List<String> expectedSuggestions : expectedSuggestionLists) {
            assertTrue("Expected >= " + expectedSuggestions.size() + ", got: " + matches[0].getSuggestedReplacements(), matches[i].getSuggestedReplacements().size() >= expectedSuggestions.size());
            for (String expectedSuggestion : expectedSuggestions) {
                assertTrue("Replacements '" + matches[i].getSuggestedReplacements() + "' don't contain expected replacement '" + expectedSuggestion + "'", matches[i].getSuggestedReplacements().contains(expectedSuggestion));
            }
            i++;
        }
    }

    @Test
    public void testSuggestionForMisspelledHyphenatedWords_1() throws IOException {
        assertSuggestion("one-diminensional", "one-dimensional");
    }

    @Test
    public void testSuggestionForMisspelledHyphenatedWords_2() throws IOException {
        assertSuggestion("parple-people-eater", "purple-people-eater");
    }

    @Test
    public void testMorfologikSpeller_1() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("mansplaining")).length);
    }

    @Test
    public void testMorfologikSpeller_2() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("Qur'an")).length);
    }

    @Test
    public void testMorfologikSpeller_3() throws IOException {
        assertTrue(rule.match(lt.getAnalyzedSentence("fianc"))[0].getSuggestedReplacements().contains("fiancé"));
    }

    @Test
    public void testMorfologikSpeller_4() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("This is an example: we get behavior as a dictionary word.")).length);
    }

    @Test
    public void testMorfologikSpeller_5() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("Why don't we speak today.")).length);
    }

    @Test
    public void testMorfologikSpeller_6() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("An URL like http://sdaasdwe.com is no error.")).length);
    }

    @Test
    public void testMorfologikSpeller_7() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("He doesn't know what to do.")).length);
    }

    @Test
    public void testMorfologikSpeller_8() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence(",")).length);
    }

    @Test
    public void testMorfologikSpeller_9() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("123454")).length);
    }

    @Test
    public void testMorfologikSpeller_10() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("I like my emoji 😾")).length);
    }

    @Test
    public void testMorfologikSpeller_11() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("μ")).length);
    }

    @Test
    public void testMorfologikSpeller_12() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("I like my emoji ❤️")).length);
    }

    @Test
    public void testMorfologikSpeller_13() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("This is English text 🗺.")).length);
    }

    @Test
    public void testMorfologikSpeller_14() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("Yes ma'am.")).length);
    }

    @Test
    public void testMorfologikSpeller_15() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("Yes ma’am.")).length);
    }

    @Test
    public void testMorfologikSpeller_16() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("'twas but a dream of thee")).length);
    }

    @Test
    public void testMorfologikSpeller_17() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("fo'c'sle")).length);
    }

    @Test
    public void testMorfologikSpeller_18() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("O'Connell, O’Connell, O'Connor, O’Neill")).length);
    }

    @Test
    public void testMorfologikSpeller_19() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("viva voce, a fortiori, in vitro")).length);
    }

    @Test
    public void testMorfologikSpeller_20() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("5¼\" floppy disk drive")).length);
    }

    @Test
    public void testMorfologikSpeller_21() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("a visual magnitude of -2½")).length);
    }

    @Test
    public void testMorfologikSpeller_22() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("Water freezes at 0º C. 175ºC")).length);
    }

    @Test
    public void testMorfologikSpeller_23() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("33°5′40″N and 32°59′0″E.")).length);
    }

    @Test
    public void testMorfologikSpeller_24() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("It's up to 5·10-³ meters.")).length);
    }

    @Test
    public void testMorfologikSpeller_25() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("141°00′7.128″W")).length);
    }

    @Test
    public void testMorfologikSpeller_26() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("1031－1095")).length);
    }

    @Test
    public void testMorfologikSpeller_27() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("It is thus written 1″.")).length);
    }

    @Test
    public void testMorfologikSpeller_28() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("a 30½-inch scale length.")).length);
    }

    @Test
    public void testMorfologikSpeller_29() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("symbolically stated as A ∈ ℝ3.")).length);
    }

    @Test
    public void testMorfologikSpeller_30() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("Thus ℵ0 is a regular cardinal.")).length);
    }

    @Test
    public void testMorfologikSpeller_31() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("the classical space B(ℓ2)")).length);
    }

    @Test
    public void testMorfologikSpeller_32() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("🏽")).length);
    }

    @Test
    public void testMorfologikSpeller_33() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("🧡🚴🏽♂️ , 🎉💛✈️")).length);
    }

    @Test
    public void testMorfologikSpeller_34() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("компьютерная")).length);
    }

    @Test
    public void testMorfologikSpeller_35() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("中文維基百科 中文维基百科")).length);
    }

    @Test
    public void testMorfologikSpeller_36() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("The statements¹ of⁷ the⁵⁰ government⁹‽")).length);
    }

    @Test
    public void testMorfologikSpeller_37() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("At 3 o'clock.")).length);
    }

    @Test
    public void testMorfologikSpeller_38() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("At 3 o’clock.")).length);
    }

    @Test
    public void testMorfologikSpeller_39() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("fast⇿superfast")).length);
    }

    @Test
    public void testMorfologikSpeller_40() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("C'est la vie.")).length);
    }

    @Test
    public void testMorfologikSpeller_41() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("c’est la guerre!")).length);
    }

    @Test
    public void testMorfologikSpeller_42() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("Andorra la Vella is the capital and largest city of Andorra.")).length);
    }

    @Test
    public void testMorfologikSpeller_43() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("bona fides.")).length);
    }

    @Test
    public void testMorfologikSpeller_44() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("doctor honoris causa")).length);
    }

    @Test
    public void testMorfologikSpeller_45() throws IOException {
        assertSuggestion("Abu-Bakr", "Abu Bakr");
    }

    @Test
    public void testMorfologikSpeller_46() throws IOException {
        assertSuggestion("Abudhabi", "Abu Dhabi");
    }

    @Test
    public void testMorfologikSpeller_47() throws IOException {
        assertSuggestion("Casagrande", "Casa Grande");
    }

    @Test
    public void testMorfologikSpeller_48() throws IOException {
        assertSuggestion("ELPASO", "El Paso");
    }

    @Test
    public void testMorfologikSpeller_49() throws IOException {
        assertSuggestion("Eldorado", "El Dorado");
    }

    @Test
    public void testMorfologikSpeller_50() throws IOException {
        assertSuggestion("nom-de-plume", "nom de plume");
    }

    @Test
    public void testMorfologikSpeller_51() throws IOException {
        assertSuggestion("sui-generis", "sui generis");
    }

    @Test
    public void testMorfologikSpeller_52() throws IOException {
        assertSuggestion("Wiener-Neustadt", "Wiener Neustadt");
    }

    @Test
    public void testMorfologikSpeller_53() throws IOException {
        assertSuggestion("Zyklon-B", "Zyklon B");
    }

    @Test
    public void testMorfologikSpeller_54_testMerged_54() throws IOException {
        RuleMatch[] matches1 = rule.match(lt.getAnalyzedSentence("behaviour"));
        assertEquals(1, matches1.length);
        assertEquals(0, matches1[0].getFromPos());
        assertEquals(9, matches1[0].getToPos());
        assertEquals("behavior", matches1[0].getSuggestedReplacements().get(0));
        assertEquals(1, rule.match(lt.getAnalyzedSentence("aõh")).length);
        assertEquals(0, rule.match(lt.getAnalyzedSentence("a")).length);
        RuleMatch[] matches2 = rule.match(lt.getAnalyzedSentence("He teached us."));
        assertEquals(1, matches2.length);
        assertEquals(3, matches2[0].getFromPos());
        assertEquals(10, matches2[0].getToPos());
        assertEquals("taught", matches2[0].getSuggestedReplacements().get(0));
        assertEquals(0, rule.match(lt.getAnalyzedSentence("A web-based software.")).length);
        assertEquals(1, rule.match(lt.getAnalyzedSentence("A wxeb-based software.")).length);
        assertEquals(1, rule.match(lt.getAnalyzedSentence("A web-baxsed software.")).length);
        assertEquals(0, rule.match(lt.getAnalyzedSentence("A web-feature-driven-car software.")).length);
        assertEquals(1, rule.match(lt.getAnalyzedSentence("A web-feature-drivenx-car software.")).length);
        assertAllMatches(lt, rule, "robinson", "Robinson", "robin son", "robins on", "Robson", "Robeson", "robins", "Roberson", "Robins");
        assertEquals(0, rule.match(lt.getAnalyzedSentence("You're only foolin' round.")).length);
        assertEquals(0, rule.match(lt.getAnalyzedSentence("You’re only foolin’ round.")).length);
        assertEquals(0, rule.match(lt.getAnalyzedSentence("This is freakin' hilarious.")).length);
        assertEquals(0, rule.match(lt.getAnalyzedSentence("It's the meal that keeps on givin'.")).length);
        assertEquals(0, rule.match(lt.getAnalyzedSentence("Don't Stop Believin'.")).length);
        assertEquals(1, rule.match(lt.getAnalyzedSentence("wrongwordin'")).length);
        assertEquals(1, rule.match(lt.getAnalyzedSentence("wrongwordin’")).length);
    }

    @Test
    public void testSuggestionForIrregularWords_1() throws IOException {
        assertSuggestion("He teached us.", "taught");
    }

    @Test
    public void testSuggestionForIrregularWords_2() throws IOException {
        assertSuggestion("He buyed the wrong brand", "bought");
    }

    @Test
    public void testSuggestionForIrregularWords_3() throws IOException {
        assertSuggestion("I thinked so.", "thought");
    }

    @Test
    public void testSuggestionForIrregularWords_4() throws IOException {
        assertSuggestion("She becomed", "became");
    }

    @Test
    public void testSuggestionForIrregularWords_5() throws IOException {
        assertSuggestion("It begined", "began");
    }

    @Test
    public void testSuggestionForIrregularWords_6() throws IOException {
        assertSuggestion("It bited", "bit");
    }

    @Test
    public void testSuggestionForIrregularWords_7() throws IOException {
        assertSuggestion("She dealed", "dealt");
    }

    @Test
    public void testSuggestionForIrregularWords_8() throws IOException {
        assertSuggestion("She drived", "drove");
    }

    @Test
    public void testSuggestionForIrregularWords_9() throws IOException {
        assertSuggestion("He drawed", "drew");
    }

    @Test
    public void testSuggestionForIrregularWords_10() throws IOException {
        assertSuggestion("She finded", "found");
    }

    @Test
    public void testSuggestionForIrregularWords_11() throws IOException {
        assertSuggestion("It hurted", "hurt");
    }

    @Test
    public void testSuggestionForIrregularWords_12() throws IOException {
        assertSuggestion("It was keeped", "kept");
    }

    @Test
    public void testSuggestionForIrregularWords_13() throws IOException {
        assertSuggestion("He maked", "made");
    }

    @Test
    public void testSuggestionForIrregularWords_14() throws IOException {
        assertSuggestion("She runed", "ran");
    }

    @Test
    public void testSuggestionForIrregularWords_15() throws IOException {
        assertSuggestion("She selled", "sold");
    }

    @Test
    public void testSuggestionForIrregularWords_16() throws IOException {
        assertSuggestion("He speaked", "spoke");
    }

    @Test
    public void testSuggestionForIrregularWords_17() throws IOException {
        assertSuggestion("auditory stimuluses", "stimuli");
    }

    @Test
    public void testSuggestionForIrregularWords_18() throws IOException {
        assertSuggestion("analysises", "analyses");
    }

    @Test
    public void testSuggestionForIrregularWords_19() throws IOException {
        assertSuggestion("parenthesises", "parentheses");
    }

    @Test
    public void testSuggestionForIrregularWords_20() throws IOException {
        assertSuggestion("childs", "children");
    }

    @Test
    public void testSuggestionForIrregularWords_21() throws IOException {
        assertSuggestion("womans", "women");
    }

    @Test
    public void testSuggestionForIrregularWords_22() throws IOException {
        assertSuggestion("gooder", "better");
    }

    @Test
    public void testSuggestionForIrregularWords_23() throws IOException {
        assertSuggestion("bader", "worse");
    }

    @Test
    public void testSuggestionForIrregularWords_24() throws IOException {
        assertSuggestion("farer", "further", "farther");
    }

    @Test
    public void testSuggestionForIrregularWords_25() throws IOException {
        assertSuggestion("goodest", "best");
    }

    @Test
    public void testSuggestionForIrregularWords_26() throws IOException {
        assertSuggestion("badest", "worst");
    }

    @Test
    public void testSuggestionForIrregularWords_27() throws IOException {
        assertSuggestion("farest", "furthest", "farthest");
    }

    @Test
    public void testIsMisspelled_1() throws IOException {
        assertTrue(rule.isMisspelled("sdadsadas"));
    }

    @Test
    public void testIsMisspelled_2() throws IOException {
        assertTrue(rule.isMisspelled("bicylce"));
    }

    @Test
    public void testIsMisspelled_3() throws IOException {
        assertTrue(rule.isMisspelled("tabble"));
    }

    @Test
    public void testIsMisspelled_4() throws IOException {
        assertTrue(rule.isMisspelled("tabbles"));
    }

    @Test
    public void testIsMisspelled_5() throws IOException {
        assertFalse(rule.isMisspelled("bicycle"));
    }

    @Test
    public void testIsMisspelled_6() throws IOException {
        assertFalse(rule.isMisspelled("table"));
    }

    @Test
    public void testIsMisspelled_7() throws IOException {
        assertFalse(rule.isMisspelled("tables"));
    }

    @Test
    public void testGetOnlySuggestions_1() throws IOException {
        assertThat(rule.getOnlySuggestions("cemetary").size(), is(1));
    }

    @Test
    public void testGetOnlySuggestions_2() throws IOException {
        assertThat(rule.getOnlySuggestions("cemetary").get(0).getReplacement(), is("cemetery"));
    }

    @Test
    public void testGetOnlySuggestions_3() throws IOException {
        assertThat(rule.getOnlySuggestions("Cemetary").size(), is(1));
    }

    @Test
    public void testGetOnlySuggestions_4() throws IOException {
        assertThat(rule.getOnlySuggestions("Cemetary").get(0).getReplacement(), is("Cemetery"));
    }

    @Test
    public void testGetOnlySuggestions_5_testMerged_5() throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence("cemetary"));
        assertThat(matches.length, is(1));
        assertThat(matches[0].getSuggestedReplacements().size(), is(1));
        assertThat(matches[0].getSuggestedReplacements().get(0), is("cemetery"));
    }
}
