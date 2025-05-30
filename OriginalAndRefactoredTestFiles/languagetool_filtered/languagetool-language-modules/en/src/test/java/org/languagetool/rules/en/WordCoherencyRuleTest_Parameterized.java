package org.languagetool.rules.en;

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

    private final JLanguageTool lt = new JLanguageTool(Languages.getLanguageForShortCode("en-US"));

    @Before
    public void before() {
        TestTools.disableAllRulesExcept(lt, "EN_WORD_COHERENCY");
    }

    private void assertGood(String s) throws IOException {
        WordCoherencyRule rule = new WordCoherencyRule(TestTools.getEnglishMessages());
        List<AnalyzedSentence> analyzedSentences = lt.analyzeText(s);
        assertEquals(0, rule.match(analyzedSentences).length);
    }

    private void assertError(String s) throws IOException {
        WordCoherencyRule rule = new WordCoherencyRule(TestTools.getEnglishMessages());
        List<AnalyzedSentence> analyzedSentences = lt.analyzeText(s);
        assertEquals(1, rule.match(analyzedSentences).length);
    }

    @ParameterizedTest
    @MethodSource("Provider_testCallIndependence_1to2")
    public void testCallIndependence_1to2(String param1) throws IOException {
        assertGood(param1);
    }

    static public Stream<Arguments> Provider_testCallIndependence_1to2() {
        return Stream.of(arguments("He likes archaeology."), arguments("She likes archeology, too."));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRuleCompleteTexts_1to4")
    public void testRuleCompleteTexts_1to4(int param1, String param2) throws IOException {
        assertEquals(param1, lt.check(param2).size());
    }

    static public Stream<Arguments> Provider_testRuleCompleteTexts_1to4() {
        return Stream.of(arguments(0, "He likes archaeology. Really? She likes archaeology, too."), arguments(1, "He likes archaeology. Really? She likes archeology, too."), arguments(1, "He likes archeology. Really? She likes archaeology, too."), arguments(1, "Mix of upper case and lower case: Westernize and westernise."));
    }
}
