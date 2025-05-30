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

public class GreekRedundancyRuleTest_Parameterized {

    private GreekRedundancyRule rule;

    private JLanguageTool langTool;

    @Before
    public void setUp() throws IOException {
        rule = new GreekRedundancyRule(TestTools.getMessages("el"), new Greek());
        langTool = new JLanguageTool(new Greek());
    }

    @ParameterizedTest
    @MethodSource("Provider_testRule_1to2")
    public void testRule_1to2(int param1, String param2) throws IOException {
        assertEquals(param1, rule.match(langTool.getAnalyzedSentence(param2)).length);
    }

    static public Stream<Arguments> Provider_testRule_1to2() {
        return Stream.of(arguments(0, "Τώρα μπαίνω στο σπίτι."), arguments(0, "Απόψε θα βγω."));
    }
}
