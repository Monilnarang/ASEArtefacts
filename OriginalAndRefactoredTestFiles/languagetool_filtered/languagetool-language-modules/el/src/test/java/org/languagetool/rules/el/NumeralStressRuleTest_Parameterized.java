package org.languagetool.rules.el;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.language.Greek;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class NumeralStressRuleTest_Parameterized {

    private NumeralStressRule rule;

    private JLanguageTool lt;

    @Before
    public void setUp() throws IOException {
        rule = new NumeralStressRule(TestTools.getMessages("el"));
        lt = new JLanguageTool(new Greek());
    }

    private void assertCorrect(String sentence) throws IOException {
        final RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(sentence));
        assertEquals(0, matches.length);
    }

    private void assertIncorrect(String sentence, String correction) throws IOException {
        final RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(sentence));
        assertEquals(1, matches.length);
        assertEquals(1, matches[0].getSuggestedReplacements().size());
        assertEquals(correction, matches[0].getSuggestedReplacements().get(0));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRule_1to8")
    public void testRule_1to8(String param1) throws IOException {
        assertCorrect(param1);
    }

    static public Stream<Arguments> Provider_testRule_1to8() {
        return Stream.of(arguments("1ος"), arguments("2η"), arguments("3ο"), arguments("20ός"), arguments("30ή"), arguments("40ό"), arguments("1000ών"), arguments("1010ες"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRule_9to17")
    public void testRule_9to17(String param1, String param2) throws IOException {
        assertIncorrect(param1, param2);
    }

    static public Stream<Arguments> Provider_testRule_9to17() {
        return Stream.of(arguments("4ός", "4ος"), arguments("5ή", "5η"), arguments("6ό", "6ο"), arguments("100ος", "100ός"), arguments("200η", "200ή"), arguments("300ο", "300ό"), arguments("2000ων", "2000ών"), arguments("2010ές", "2010ες"), arguments("2020α", "2020ά"));
    }
}
