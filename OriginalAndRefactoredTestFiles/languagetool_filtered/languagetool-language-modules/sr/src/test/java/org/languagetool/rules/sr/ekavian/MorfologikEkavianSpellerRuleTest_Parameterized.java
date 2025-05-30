package org.languagetool.rules.sr.ekavian;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.language.SerbianSerbian;
import org.languagetool.rules.Rule;
import java.io.IOException;
import java.util.Collections;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class MorfologikEkavianSpellerRuleTest_Parameterized {

    private Rule rule;

    private JLanguageTool languageTool;

    @Before
    public void setUp() throws Exception {
        rule = new MorfologikEkavianSpellerRule(TestTools.getMessages("sr"), new SerbianSerbian(), null, Collections.emptyList());
        languageTool = new JLanguageTool(new SerbianSerbian());
    }

    @ParameterizedTest
    @MethodSource("Provider_testMorfologikSpeller_1to4")
    public void testMorfologikSpeller_1to4(int param1, String param2) throws IOException {
        assertEquals(param1, rule.match(languageTool.getAnalyzedSentence(param2)).length);
    }

    static public Stream<Arguments> Provider_testMorfologikSpeller_1to4() {
        return Stream.of(arguments(0, "Тамо је леп цвет"), arguments(0, "Дечак и девојчица играју се заједно."), arguments(0, ","), arguments(0, "III"));
    }
}
