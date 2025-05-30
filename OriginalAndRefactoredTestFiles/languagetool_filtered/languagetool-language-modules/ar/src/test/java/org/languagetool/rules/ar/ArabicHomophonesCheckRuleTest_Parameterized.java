package org.languagetool.rules.ar;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.language.Arabic;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ArabicHomophonesCheckRuleTest_Parameterized {

    private ArabicHomophonesRule rule;

    private JLanguageTool lt;

    @Before
    public void setUp() {
        rule = new ArabicHomophonesRule(TestTools.getEnglishMessages());
        lt = new JLanguageTool(new Arabic());
    }

    @ParameterizedTest
    @MethodSource("Provider_testRule_1to3")
    public void testRule_1to3(int param1, String param2) throws IOException {
        assertEquals(param1, rule.match(lt.getAnalyzedSentence(param2)).length);
    }

    static public Stream<Arguments> Provider_testRule_1to3() {
        return Stream.of(arguments(1, "ضن"), arguments(1, "حاضر"), arguments(1, "حض"));
    }
}
