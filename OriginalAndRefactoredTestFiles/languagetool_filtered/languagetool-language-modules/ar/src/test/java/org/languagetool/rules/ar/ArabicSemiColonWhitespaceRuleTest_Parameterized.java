package org.languagetool.rules.ar;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.TestTools;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ArabicSemiColonWhitespaceRuleTest_Parameterized {

    private ArabicSemiColonWhitespaceRule rule;

    private JLanguageTool lt;

    @Before
    public void setUp() {
        rule = new ArabicSemiColonWhitespaceRule(TestTools.getEnglishMessages());
        lt = new JLanguageTool(Languages.getLanguageForShortCode("ar"));
    }

    private void assertMatches(String text, int expectedMatches) throws IOException {
        assertEquals(expectedMatches, rule.match(lt.getAnalyzedSentence(text)).length);
    }

    @ParameterizedTest
    @MethodSource("Provider_testRule_1to3")
    public void testRule_1to3(String param1, int param2) throws IOException {
        assertMatches(param1, param2);
    }

    static public Stream<Arguments> Provider_testRule_1to3() {
        return Stream.of(arguments("This is a test sentence؛", 0), arguments("أهذه تجربة؛", 0), arguments("أهذه تجربة ؛", 1));
    }
}
