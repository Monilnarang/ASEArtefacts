package org.languagetool.rules.crh;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.language.CrimeanTatar;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class MorfologikCrimeanTatarSpellerRuleTest_Parameterized {

    private JLanguageTool langTool;

    private MorfologikCrimeanTatarSpellerRule rule;

    @Before
    public void init() throws IOException {
        rule = new MorfologikCrimeanTatarSpellerRule(TestTools.getMessages("crh"), new CrimeanTatar(), null, Collections.emptyList());
        langTool = new JLanguageTool(new CrimeanTatar());
    }

    @Test
    public void testMorfologikSpeller_1() throws IOException {
        assertEquals(Arrays.asList(), Arrays.asList(rule.match(langTool.getAnalyzedSentence("abadlarnı amutlarıñ!"))));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMorfologikSpeller_2to4")
    public void testMorfologikSpeller_2to4(int param1, String param2) throws IOException {
        assertEquals(param1, rule.match(langTool.getAnalyzedSentence(param2)).length);
    }

    static public Stream<Arguments> Provider_testMorfologikSpeller_2to4() {
        return Stream.of(arguments(1, "aaabadlarnı"), arguments(0, "abadanlaşırlar"), arguments(0, "meraba"));
    }
}
