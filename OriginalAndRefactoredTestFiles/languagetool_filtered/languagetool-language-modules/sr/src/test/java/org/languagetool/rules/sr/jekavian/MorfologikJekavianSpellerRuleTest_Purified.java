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

public class MorfologikJekavianSpellerRuleTest_Purified {

    private Rule rule;

    private JLanguageTool languageTool;

    @Before
    public void setUp() throws Exception {
        rule = new MorfologikJekavianSpellerRule(TestTools.getMessages("sr"), new JekavianSerbian(), null, Collections.emptyList());
        languageTool = new JLanguageTool(new JekavianSerbian());
    }

    @Test
    public void testMorfologikSpeller_1() throws IOException {
        assertEquals(0, rule.match(languageTool.getAnalyzedSentence("Тамо је лијеп цвијет.")).length);
    }

    @Test
    public void testMorfologikSpeller_2() throws IOException {
        assertEquals(0, rule.match(languageTool.getAnalyzedSentence("Дјечак и дјевојчица играју се заједно.")).length);
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
