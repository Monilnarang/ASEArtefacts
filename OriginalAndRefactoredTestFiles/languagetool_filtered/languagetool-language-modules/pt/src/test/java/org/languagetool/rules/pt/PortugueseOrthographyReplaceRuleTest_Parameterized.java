package org.languagetool.rules.pt;

import org.junit.BeforeClass;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.language.Portuguese;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class PortugueseOrthographyReplaceRuleTest_Parameterized {

    private static PortugueseOrthographyReplaceRule rule;

    private static JLanguageTool lt;

    @BeforeClass
    public static void setUp() throws Exception {
        lt = new JLanguageTool(Portuguese.getInstance());
        rule = new PortugueseOrthographyReplaceRule(TestTools.getMessages("pt"), lt.getLanguage());
    }

    private void assertRuleId(RuleMatch match) {
        assert match.getRule().getId().startsWith("PT_SIMPLE_REPLACE_ORTHOGRAPHY");
    }

    private void assertNoMatches(String sentence) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(sentence));
        assertEquals(0, matches.length);
    }

    private void assertSingleMatch(String sentence, String... suggestions) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(sentence));
        assertEquals(1, matches.length);
        assertRuleId(matches[0]);
        List<String> returnedSuggestions = matches[0].getSuggestedReplacements();
        assertEquals(suggestions.length, returnedSuggestions.size());
        for (int i = 0; i < suggestions.length; i++) {
            assertEquals(suggestions[i], returnedSuggestions.get(i));
        }
    }

    @ParameterizedTest
    @MethodSource("Provider_testRule_1_3_5")
    public void testRule_1_3_5(String param1) throws IOException {
        assertNoMatches(param1);
    }

    static public Stream<Arguments> Provider_testRule_1_3_5() {
        return Stream.of(arguments("Já volto."), arguments("Gosto de você."), arguments("Disse-me sotto voce."));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRule_2_4")
    public void testRule_2_4(String param1, String param2) throws IOException {
        assertSingleMatch(param1, param2);
    }

    static public Stream<Arguments> Provider_testRule_2_4() {
        return Stream.of(arguments("Ja volto.", "Já"), arguments("Gosto de voce.", "você"));
    }
}
