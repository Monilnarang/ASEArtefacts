package org.languagetool.rules.ga;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.language.Irish;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class DhaNoBeirtRuleTest_Parameterized {

    private DhaNoBeirtRule rule;

    private JLanguageTool lt;

    @Before
    public void setUp() throws IOException {
        rule = new DhaNoBeirtRule(TestTools.getMessages("ga"));
        lt = new JLanguageTool(Irish.getInstance());
    }

    private void assertCorrect(String sentence) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(sentence));
        assertEquals(0, matches.length);
    }

    private void assertIncorrect(String sentence, int expected) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(sentence));
        assertEquals(expected, matches.length);
    }

    @ParameterizedTest
    @MethodSource("Provider_testRule_1to2")
    public void testRule_1to2(String param1) throws IOException {
        assertCorrect(param1);
    }

    static public Stream<Arguments> Provider_testRule_1to2() {
        return Stream.of(arguments("Seo abairt bheag."), arguments("Tá beirt dheartháireacha agam."));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRule_3to5")
    public void testRule_3to5(String param1, int param2) throws IOException {
        assertIncorrect(param1, param2);
    }

    static public Stream<Arguments> Provider_testRule_3to5() {
        return Stream.of(arguments("Tá dhá dheartháireacha agam.", 1), arguments("Seo dhá ab déag", 2), arguments("Tá dhá dheartháireacha níos aosta déag agam.", 2));
    }
}
