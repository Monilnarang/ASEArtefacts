package org.languagetool.rules.de;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.languagetool.AnalyzedSentence;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.TestTools;
import org.languagetool.rules.RuleMatch;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class WordCoherencyRuleTest_Parameterized {

    private final JLanguageTool lt = new JLanguageTool(Languages.getLanguageForShortCode("de-DE"));

    @Before
    public void before() {
        TestTools.disableAllRulesExcept(lt, "DE_WORD_COHERENCY");
    }

    private void assertError(String s, String expectedSuggestion) throws IOException {
        WordCoherencyRule rule = new WordCoherencyRule(TestTools.getEnglishMessages());
        List<AnalyzedSentence> analyzedSentences = lt.analyzeText(s);
        RuleMatch[] matches = rule.match(analyzedSentences);
        assertEquals(1, matches.length);
        assertEquals("[" + expectedSuggestion + "]", matches[0].getSuggestedReplacements().toString());
    }

    private void assertError(String s) throws IOException {
        WordCoherencyRule rule = new WordCoherencyRule(TestTools.getEnglishMessages());
        List<AnalyzedSentence> analyzedSentences = lt.analyzeText(s);
        assertEquals(1, rule.match(analyzedSentences).length);
    }

    private void assertGood(String s) throws IOException {
        WordCoherencyRule rule = new WordCoherencyRule(TestTools.getEnglishMessages());
        List<AnalyzedSentence> analyzedSentences = lt.analyzeText(s);
        assertEquals(0, rule.match(analyzedSentences).length);
    }

    @ParameterizedTest
    @MethodSource("Provider_testCallIndependence_1to2")
    public void testCallIndependence_1to2(String param1) throws IOException {
        assertGood(param1);
    }

    static public Stream<Arguments> Provider_testCallIndependence_1to2() {
        return Stream.of(arguments("Das ist aufwendig."), arguments("Aber nicht zu aufwändig."));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRuleCompleteTexts_1to9")
    public void testRuleCompleteTexts_1to9(int param1, String param2) throws IOException {
        assertEquals(param1, lt.check(param2).size());
    }

    static public Stream<Arguments> Provider_testRuleCompleteTexts_1to9() {
        return Stream.of(arguments(0, "Das ist aufwändig. Aber hallo. Es ist wirklich aufwändig."), arguments(1, "Das ist aufwendig. Aber hallo. Es ist wirklich aufwändig."), arguments(1, "Das ist aufwändig. Aber hallo. Es ist wirklich aufwendig."), arguments(0, "Das ist aufwendig. Aber hallo. Es ist wirklich aufwendiger als so."), arguments(1, "Das ist aufwendig. Aber hallo. Es ist wirklich aufwändiger als so."), arguments(1, "Das ist aufwändig. Aber hallo. Es ist wirklich aufwendiger als so."), arguments(1, "Das ist das aufwändigste. Aber hallo. Es ist wirklich aufwendiger als so."), arguments(1, "Das ist das aufwändigste. Aber hallo. Es ist wirklich aufwendig."), arguments(1, "Das ist das aufwändigste.\n\nAber hallo. Es ist wirklich aufwendig."));
    }
}
