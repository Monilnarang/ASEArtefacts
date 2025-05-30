package org.languagetool.rules.ru;

import org.junit.Test;
import org.languagetool.AnalyzedSentence;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.language.Russian;
import java.io.IOException;
import java.util.Collections;
import static org.junit.Assert.assertEquals;

public class RussianWordCoherencyRuleTest_Purified {

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
    public void testRule_1() throws IOException {
        assertGood("По шкале Цельсия абсолютному нулю соответствует температура −273,15 °C.");
    }

    @Test
    public void testRule_2() throws IOException {
        assertGood("По шкале Цельсия абсолютному нулю соответствует температура −273,15 °C.");
    }

    @Test
    public void testRule_3() throws IOException {
        assertError("По шкале Цельсия абсолютному нулю соответствует температура −273,15 °C или ноль по шкале Кельвина.");
    }

    @Test
    public void testCallIndependence_1() throws IOException {
        assertGood("Абсолютный нуль.");
    }

    @Test
    public void testCallIndependence_2() throws IOException {
        assertGood("Ноль по шкале Кельвина.");
    }

    @Test
    public void testRuleCompleteTexts_1() throws IOException {
        assertEquals(0, lt.check("По шкале Цельсия абсолютному нулю соответствует температура −273,15 °C или нуль по шкале Кельвина.").size());
    }

    @Test
    public void testRuleCompleteTexts_2() throws IOException {
        assertEquals(1, lt.check("По шкале Цельсия абсолютному нулю соответствует температура −273,15 °C или ноль по шкале Кельвина.").size());
    }

    @Test
    public void testRuleCompleteTexts_3() throws IOException {
        assertEquals(1, lt.check("Абсолютный нуль.\n\nСовсем недостижим. И ноль по шкале Кельвина.").size());
    }
}
