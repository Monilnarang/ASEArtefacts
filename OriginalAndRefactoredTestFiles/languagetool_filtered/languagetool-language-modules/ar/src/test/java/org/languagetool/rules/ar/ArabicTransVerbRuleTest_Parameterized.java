package org.languagetool.rules.ar;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.TestTools;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ArabicTransVerbRuleTest_Parameterized {

    private ArabicTransVerbRule rule;

    private JLanguageTool lt;

    @Before
    public void setUp() throws IOException {
        rule = new ArabicTransVerbRule(TestTools.getEnglishMessages());
        lt = new JLanguageTool(Languages.getLanguageForShortCode("ar"));
    }

    private void assertCorrect(String sentence) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(sentence));
        assertEquals(0, matches.length);
    }

    private void assertIncorrect(String sentence) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(sentence));
        assertEquals(1, matches.length);
    }

    @Test
    public void testRule_1() throws IOException {
        assertCorrect("كان أَفَاضَ في الحديث");
    }

    @ParameterizedTest
    @MethodSource("Provider_testRule_2to6")
    public void testRule_2to6(String param1) throws IOException {
        assertIncorrect(param1);
    }

    static public Stream<Arguments> Provider_testRule_2to6() {
        return Stream.of(arguments("كان أفاض من الحديث"), arguments("لقد أفاضت من الحديث"), arguments("لقد أفاضت الحديث"), arguments("كان أفاضها الحديث"), arguments("إذ استعجل الأمر"));
    }
}
