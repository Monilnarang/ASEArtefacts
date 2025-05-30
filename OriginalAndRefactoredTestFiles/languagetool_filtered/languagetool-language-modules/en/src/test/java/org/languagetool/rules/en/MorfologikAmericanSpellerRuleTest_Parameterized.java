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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class MorfologikAmericanSpellerRuleTest_Parameterized extends AbstractEnglishSpellerRuleTest {

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
    public void testMorfologikSpeller_3() throws IOException {
        assertTrue(rule.match(lt.getAnalyzedSentence("fianc"))[0].getSuggestedReplacements().contains("fiancé"));
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
    public void testGetOnlySuggestions_5_testMerged_5() throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence("cemetary"));
        assertThat(matches.length, is(1));
        assertThat(matches[0].getSuggestedReplacements().size(), is(1));
        assertThat(matches[0].getSuggestedReplacements().get(0), is("cemetery"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSuggestionForMisspelledHyphenatedWords_1_1to2_2to23_25to26_45to53")
    public void testSuggestionForMisspelledHyphenatedWords_1_1to2_2to23_25to26_45to53(String param1, String param2) throws IOException {
        assertSuggestion(param1, param2);
    }

    static public Stream<Arguments> Provider_testSuggestionForMisspelledHyphenatedWords_1_1to2_2to23_25to26_45to53() {
        return Stream.of(arguments("one-diminensional", "one-dimensional"), arguments("parple-people-eater", "purple-people-eater"), arguments("Abu-Bakr", "Abu Bakr"), arguments("Abudhabi", "Abu Dhabi"), arguments("Casagrande", "Casa Grande"), arguments("ELPASO", "El Paso"), arguments("Eldorado", "El Dorado"), arguments("nom-de-plume", "nom de plume"), arguments("sui-generis", "sui generis"), arguments("Wiener-Neustadt", "Wiener Neustadt"), arguments("Zyklon-B", "Zyklon B"), arguments("He teached us.", "taught"), arguments("He buyed the wrong brand", "bought"), arguments("I thinked so.", "thought"), arguments("She becomed", "became"), arguments("It begined", "began"), arguments("It bited", "bit"), arguments("She dealed", "dealt"), arguments("She drived", "drove"), arguments("He drawed", "drew"), arguments("She finded", "found"), arguments("It hurted", "hurt"), arguments("It was keeped", "kept"), arguments("He maked", "made"), arguments("She runed", "ran"), arguments("She selled", "sold"), arguments("He speaked", "spoke"), arguments("auditory stimuluses", "stimuli"), arguments("analysises", "analyses"), arguments("parenthesises", "parentheses"), arguments("childs", "children"), arguments("womans", "women"), arguments("gooder", "better"), arguments("bader", "worse"), arguments("goodest", "best"), arguments("badest", "worst"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMorfologikSpeller_1to2_4to44")
    public void testMorfologikSpeller_1to2_4to44(int param1, String param2) throws IOException {
        assertEquals(param1, rule.match(lt.getAnalyzedSentence(param2)).length);
    }

    static public Stream<Arguments> Provider_testMorfologikSpeller_1to2_4to44() {
        return Stream.of(arguments(0, "mansplaining"), arguments(0, "Qur'an"), arguments(0, "This is an example: we get behavior as a dictionary word."), arguments(0, "Why don't we speak today."), arguments(0, "An URL like http://sdaasdwe.com is no error."), arguments(0, "He doesn't know what to do."), arguments(0, ","), arguments(0, 123454), arguments(0, "I like my emoji 😾"), arguments(0, "μ"), arguments(0, "I like my emoji ❤️"), arguments(0, "This is English text 🗺."), arguments(0, "Yes ma'am."), arguments(0, "Yes ma’am."), arguments(0, "'twas but a dream of thee"), arguments(0, "fo'c'sle"), arguments(0, "O'Connell, O’Connell, O'Connor, O’Neill"), arguments(0, "viva voce, a fortiori, in vitro"), arguments(0, "5¼\" floppy disk drive"), arguments(0, "a visual magnitude of -2½"), arguments(0, "Water freezes at 0º C. 175ºC"), arguments(0, "33°5′40″N and 32°59′0″E."), arguments(0, "It's up to 5·10-³ meters."), arguments(0, "141°00′7.128″W"), arguments(0, "1031－1095"), arguments(0, "It is thus written 1″."), arguments(0, "a 30½-inch scale length."), arguments(0, "symbolically stated as A ∈ ℝ3."), arguments(0, "Thus ℵ0 is a regular cardinal."), arguments(0, "the classical space B(ℓ2)"), arguments(0, "🏽"), arguments(0, "🧡🚴🏽♂️ , 🎉💛✈️"), arguments(0, "компьютерная"), arguments(0, "中文維基百科 中文维基百科"), arguments(0, "The statements¹ of⁷ the⁵⁰ government⁹‽"), arguments(0, "At 3 o'clock."), arguments(0, "At 3 o’clock."), arguments(0, "fast⇿superfast"), arguments(0, "C'est la vie."), arguments(0, "c’est la guerre!"), arguments(0, "Andorra la Vella is the capital and largest city of Andorra."), arguments(0, "bona fides."), arguments(0, "doctor honoris causa"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSuggestionForIrregularWords_24_27")
    public void testSuggestionForIrregularWords_24_27(String param1, String param2, String param3) throws IOException {
        assertSuggestion(param1, param2, param3);
    }

    static public Stream<Arguments> Provider_testSuggestionForIrregularWords_24_27() {
        return Stream.of(arguments("farer", "further", "farther"), arguments("farest", "furthest", "farthest"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsMisspelled_1to4")
    public void testIsMisspelled_1to4(String param1) throws IOException {
        assertTrue(rule.isMisspelled(param1));
    }

    static public Stream<Arguments> Provider_testIsMisspelled_1to4() {
        return Stream.of(arguments("sdadsadas"), arguments("bicylce"), arguments("tabble"), arguments("tabbles"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsMisspelled_5to7")
    public void testIsMisspelled_5to7(String param1) throws IOException {
        assertFalse(rule.isMisspelled(param1));
    }

    static public Stream<Arguments> Provider_testIsMisspelled_5to7() {
        return Stream.of(arguments("bicycle"), arguments("table"), arguments("tables"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetOnlySuggestions_1_3")
    public void testGetOnlySuggestions_1_3(int param1, String param2) throws IOException {
        assertThat(rule.getOnlySuggestions(param2).size(), is(param1));
    }

    static public Stream<Arguments> Provider_testGetOnlySuggestions_1_3() {
        return Stream.of(arguments(1, "cemetary"), arguments(1, "Cemetary"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetOnlySuggestions_2_4")
    public void testGetOnlySuggestions_2_4(String param1, int param2, String param3) throws IOException {
        assertThat(rule.getOnlySuggestions(param3).get(param2).getReplacement(), is(param1));
    }

    static public Stream<Arguments> Provider_testGetOnlySuggestions_2_4() {
        return Stream.of(arguments("cemetery", 0, "cemetary"), arguments("Cemetery", 0, "Cemetary"));
    }
}
