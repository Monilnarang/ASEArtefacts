package org.languagetool.rules.uk;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.language.Ukrainian;
import org.languagetool.rules.RuleMatch;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class UkrainianWordRepeatRuleTest_Parameterized {

    private JLanguageTool lt;

    private UkrainianWordRepeatRule rule;

    @Before
    public void setUp() throws IOException {
        lt = new JLanguageTool(Ukrainian.DEFAULT_VARIANT);
        rule = new UkrainianWordRepeatRule(TestTools.getMessages("uk"), lt.getLanguage());
    }

    private void assertEmptyMatch(String text) throws IOException {
        assertEquals(text, Collections.<RuleMatch>emptyList(), Arrays.asList(rule.match(lt.getAnalyzedSentence(text))));
    }

    @Test
    public void testRule_10() throws IOException {
        assertEquals(1, rule.match(lt.getAnalyzedSentence("без без повного розрахунку")).length);
    }

    @Test
    public void testRule_11_testMerged_11() throws IOException {
        RuleMatch[] match = rule.match(lt.getAnalyzedSentence("Верховної Ради І і ІІ скликань"));
        assertEquals(1, match.length);
        assertEquals(2, match[0].getSuggestedReplacements().size());
    }

    @ParameterizedTest
    @MethodSource("Provider_testRule_1to9")
    public void testRule_1to9(String param1) throws IOException {
        assertEmptyMatch(param1);
    }

    static public Stream<Arguments> Provider_testRule_1to9() {
        return Stream.of(arguments("без повного розрахунку"), arguments("без бугіма бугіма"), arguments("без 100 100"), arguments("1.30 3.20 3.20"), arguments("ще в В.Кандинського"), arguments("Від добра добра не шукають."), arguments("Що, що, а кіно в Україні..."), arguments("Відповідно до ст. ст. 3, 7, 18."), arguments("Не можу сказати ні так, ні ні."));
    }
}
