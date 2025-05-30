package org.languagetool.rules.pt;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.language.BrazilianPortuguese;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class PortugueseBarbarismRuleTest_Parameterized {

    private PortugueseBarbarismsRule rule;

    private JLanguageTool lt;

    @Before
    public void setUp() throws Exception {
        lt = new JLanguageTool(BrazilianPortuguese.getInstance());
        rule = new PortugueseBarbarismsRule(TestTools.getMessages("pt"), "/pt/pt-BR/barbarisms.txt", lt.getLanguage());
    }

    private void assertNoMatches(String sentence) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(sentence));
        assertEquals(0, matches.length);
    }

    @ParameterizedTest
    @MethodSource("Provider_testReplaceBarbarisms_1to3")
    public void testReplaceBarbarisms_1to3(String param1) throws IOException {
        assertNoMatches(param1);
    }

    static public Stream<Arguments> Provider_testReplaceBarbarisms_1to3() {
        return Stream.of(arguments("New York Stock Exchange"), arguments("Yankee Doodle, faça o morra"), arguments("mas inferior ao Opera Browser."));
    }
}
