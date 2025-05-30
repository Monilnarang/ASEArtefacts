package org.languagetool.rules.uk;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.language.Ukrainian;
import org.languagetool.rules.RuleMatch;

public class UkrainianWordRepeatRuleTest_Purified {

    private JLanguageTool lt;

    private UkrainianWordRepeatRule rule;

    @Before
    public void setUp() throws IOException {
        lt = new JLanguageTool(Ukrainian.DEFAULT_VARIANT);
        rule = new UkrainianWordRepeatRule(TestTools.getMessages("uk"), lt.getLanguage());
    }

    private void assertEmptyMatch(String text) throws IOException {
        assertEquals(text, Collections.<RuleMatch>emptyList(), Arrays.asList(rule.match(lt.getAnalyzedSentence(text))));
    }

    @Test
    public void testRule_1() throws IOException {
        assertEmptyMatch("без повного розрахунку");
    }

    @Test
    public void testRule_2() throws IOException {
        assertEmptyMatch("без бугіма бугіма");
    }

    @Test
    public void testRule_3() throws IOException {
        assertEmptyMatch("без 100 100");
    }

    @Test
    public void testRule_4() throws IOException {
        assertEmptyMatch("1.30 3.20 3.20");
    }

    @Test
    public void testRule_5() throws IOException {
        assertEmptyMatch("ще в В.Кандинського");
    }

    @Test
    public void testRule_6() throws IOException {
        assertEmptyMatch("Від добра добра не шукають.");
    }

    @Test
    public void testRule_7() throws IOException {
        assertEmptyMatch("Що, що, а кіно в Україні...");
    }

    @Test
    public void testRule_8() throws IOException {
        assertEmptyMatch("Відповідно до ст. ст. 3, 7, 18.");
    }

    @Test
    public void testRule_9() throws IOException {
        assertEmptyMatch("Не можу сказати ні так, ні ні.");
    }

    @Test
    public void testRule_10() throws IOException {
        assertEquals(1, rule.match(lt.getAnalyzedSentence("без без повного розрахунку")).length);
    }

    @Test
    public void testRule_11_testMerged_11() throws IOException {
        RuleMatch[] match = rule.match(lt.getAnalyzedSentence("Верховної Ради І і ІІ скликань"));
        assertEquals(1, match.length);
        assertEquals(2, match[0].getSuggestedReplacements().size());
    }
}
