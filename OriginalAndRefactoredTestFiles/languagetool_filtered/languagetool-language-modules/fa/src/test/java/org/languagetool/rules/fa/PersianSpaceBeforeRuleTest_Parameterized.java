package org.languagetool.rules.fa;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class PersianSpaceBeforeRuleTest_Parameterized {

    private PersianSpaceBeforeRule rule;

    private JLanguageTool lt;

    @Before
    public void setUp() throws IOException {
        rule = new PersianSpaceBeforeRule(TestTools.getEnglishMessages(), TestTools.getDemoLanguage());
        lt = new JLanguageTool(TestTools.getDemoLanguage());
    }

    private void assertMatches(String text, int expectedMatches) throws IOException {
        assertEquals(expectedMatches, rule.match(lt.getAnalyzedSentence(text)).length);
    }

    @ParameterizedTest
    @MethodSource("Provider_testRules_1to3")
    public void testRules_1to3(String param1, int param2) throws IOException {
        assertMatches(param1, param2);
    }

    static public Stream<Arguments> Provider_testRules_1to3() {
        return Stream.of(arguments("به اینجا", 1), arguments("من به اینجا", 0), arguments("(به اینجا", 0));
    }
}
