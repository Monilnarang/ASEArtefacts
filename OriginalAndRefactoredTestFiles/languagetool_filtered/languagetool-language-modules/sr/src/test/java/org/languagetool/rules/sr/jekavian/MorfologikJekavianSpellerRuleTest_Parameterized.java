package org.languagetool.rules.sr.jekavian;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.language.JekavianSerbian;
import org.languagetool.rules.Rule;
import java.io.IOException;
import java.util.Collections;
import static org.junit.Assert.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class MorfologikJekavianSpellerRuleTest_Parameterized {

    private Rule rule;

    private JLanguageTool languageTool;

    @Before
    public void setUp() throws Exception {
        rule = new MorfologikJekavianSpellerRule(TestTools.getMessages("sr"), new JekavianSerbian(), null, Collections.emptyList());
        languageTool = new JLanguageTool(new JekavianSerbian());
    }

    @ParameterizedTest
    @MethodSource("Provider_testMorfologikSpeller_1to4")
    public void testMorfologikSpeller_1to4(int param1, String param2) throws IOException {
        assertEquals(param1, rule.match(languageTool.getAnalyzedSentence(param2)).length);
    }

    static public Stream<Arguments> Provider_testMorfologikSpeller_1to4() {
        return Stream.of(arguments(0, "Тамо је лијеп цвијет."), arguments(0, "Дјечак и дјевојчица играју се заједно."), arguments(0, ","), arguments(0, "III"));
    }
}
