package org.languagetool.rules.ru;

import org.junit.Test;
import org.languagetool.AnalyzedSentence;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.language.Russian;
import java.io.IOException;
import java.util.Collections;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class RussianWordCoherencyRuleTest_Parameterized {

    private final JLanguageTool lt = new JLanguageTool(Russian.getInstance());

    private void assertError(String s) throws IOException {
        RussianWordCoherencyRule rule = new RussianWordCoherencyRule(TestTools.getEnglishMessages());
        AnalyzedSentence analyzedSentence = lt.getAnalyzedSentence(s);
        assertEquals(1, rule.match(Collections.singletonList(analyzedSentence)).length);
    }

    private void assertGood(String s) throws IOException {
        RussianWordCoherencyRule rule = new RussianWordCoherencyRule(TestTools.getEnglishMessages());
        AnalyzedSentence analyzedSentence = lt.getAnalyzedSentence(s);
        assertEquals(0, rule.match(Collections.singletonList(analyzedSentence)).length);
    }

    @Test
    public void testRule_3() throws IOException {
        assertError("По шкале Цельсия абсолютному нулю соответствует температура −273,15 °C или ноль по шкале Кельвина.");
    }

    @ParameterizedTest
    @MethodSource("Provider_testRule_1_1to2_2")
    public void testRule_1_1to2_2(String param1) throws IOException {
        assertGood(param1);
    }

    static public Stream<Arguments> Provider_testRule_1_1to2_2() {
        return Stream.of(arguments("По шкале Цельсия абсолютному нулю соответствует температура −273,15 °C."), arguments("По шкале Цельсия абсолютному нулю соответствует температура −273,15 °C."), arguments("Абсолютный нуль."), arguments("Ноль по шкале Кельвина."));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRuleCompleteTexts_1to3")
    public void testRuleCompleteTexts_1to3(int param1, String param2) throws IOException {
        assertEquals(param1, lt.check(param2).size());
    }

    static public Stream<Arguments> Provider_testRuleCompleteTexts_1to3() {
        return Stream.of(arguments(0, "По шкале Цельсия абсолютному нулю соответствует температура −273,15 °C или нуль по шкале Кельвина."), arguments(1, "По шкале Цельсия абсолютному нулю соответствует температура −273,15 °C или ноль по шкале Кельвина."), arguments(1, "Абсолютный нуль.\n\nСовсем недостижим. И ноль по шкале Кельвина."));
    }
}
