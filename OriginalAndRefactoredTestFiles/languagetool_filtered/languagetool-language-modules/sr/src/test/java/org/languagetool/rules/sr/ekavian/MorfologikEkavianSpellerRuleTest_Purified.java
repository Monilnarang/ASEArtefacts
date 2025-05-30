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

public class MorfologikEkavianSpellerRuleTest_Purified {

    private Rule rule;

    private JLanguageTool languageTool;

    @Before
    public void setUp() throws Exception {
        rule = new MorfologikEkavianSpellerRule(TestTools.getMessages("sr"), new SerbianSerbian(), null, Collections.emptyList());
        languageTool = new JLanguageTool(new SerbianSerbian());
    }

    @Test
    public void testMorfologikSpeller_1() throws IOException {
        assertEquals(0, rule.match(languageTool.getAnalyzedSentence("Тамо је леп цвет")).length);
    }

    @Test
    public void testMorfologikSpeller_2() throws IOException {
        assertEquals(0, rule.match(languageTool.getAnalyzedSentence("Дечак и девојчица играју се заједно.")).length);
    }

    @Test
    public void testMorfologikSpeller_3() throws IOException {
        assertEquals(0, rule.match(languageTool.getAnalyzedSentence(",")).length);
    }

    @Test
    public void testMorfologikSpeller_4() throws IOException {
        assertEquals(0, rule.match(languageTool.getAnalyzedSentence("III")).length);
    }
}
