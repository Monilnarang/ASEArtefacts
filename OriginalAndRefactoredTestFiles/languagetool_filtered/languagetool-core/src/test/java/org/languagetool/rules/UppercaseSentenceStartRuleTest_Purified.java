package org.languagetool.rules;

import org.junit.Test;
import org.languagetool.AnalyzedSentence;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import java.io.IOException;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class UppercaseSentenceStartRuleTest_Purified {

    private final UppercaseSentenceStartRule rule = new UppercaseSentenceStartRule(TestTools.getEnglishMessages(), TestTools.getDemoLanguage(), Example.wrong("<marker>a</marker> sentence."), Example.fixed("<marker>A</marker> sentence."));

    private final JLanguageTool lt = new JLanguageTool(TestTools.getDemoLanguage());

    private void assertGood(String s) throws IOException {
        List<AnalyzedSentence> analyzedSentences = lt.analyzeText(s);
        RuleMatch[] matches = rule.match(analyzedSentences);
        assertEquals(0, matches.length);
    }

    @Test
    public void testRule_1() throws IOException {
        assertGood("this");
    }

    @Test
    public void testRule_2() throws IOException {
        assertGood("a) This is a test sentence.");
    }

    @Test
    public void testRule_3() throws IOException {
        assertGood("iv. This is a test sentence...");
    }

    @Test
    public void testRule_4() throws IOException {
        assertGood("\"iv. This is a test sentence...\"");
    }

    @Test
    public void testRule_5() throws IOException {
        assertGood("»iv. This is a test sentence...");
    }

    @Test
    public void testRule_6() throws IOException {
        assertGood("This");
    }

    @Test
    public void testRule_7() throws IOException {
        assertGood("This is");
    }

    @Test
    public void testRule_8() throws IOException {
        assertGood("This is a test sentence");
    }

    @Test
    public void testRule_9() throws IOException {
        assertGood("");
    }

    @Test
    public void testRule_10() throws IOException {
        assertGood("http://www.languagetool.org");
    }

    @Test
    public void testRule_11() throws IOException {
        assertGood("eBay can be at sentence start in lowercase.");
    }

    @Test
    public void testRule_12() throws IOException {
        assertGood("¿Esto es una pregunta?");
    }

    @Test
    public void testRule_13() throws IOException {
        assertGood("¿Esto es una pregunta?, ¿y esto?");
    }

    @Test
    public void testRule_14() throws IOException {
        assertGood("ø This is a test sentence with a wrong bullet character.");
    }

    @Test
    public void testRule_15_testMerged_15() throws IOException {
        RuleMatch[] matches = rule.match(lt.analyzeText("this is a test sentence."));
        assertEquals(1, matches.length);
        assertEquals(0, matches[0].getFromPos());
        assertEquals(4, matches[0].getToPos());
        RuleMatch[] matches2 = rule.match(lt.analyzeText("this!"));
        assertEquals(1, matches2.length);
        assertEquals(0, matches2[0].getFromPos());
        assertEquals(4, matches2[0].getToPos());
        RuleMatch[] matches3 = rule.match(lt.analyzeText("'this is a sentence'."));
        assertEquals(1, matches3.length);
        RuleMatch[] matches4 = rule.match(lt.analyzeText("\"this is a sentence.\""));
        assertEquals(1, matches4.length);
        RuleMatch[] matches5 = rule.match(lt.analyzeText("„this is a sentence."));
        assertEquals(1, matches5.length);
        RuleMatch[] matches6 = rule.match(lt.analyzeText("«this is a sentence."));
        assertEquals(1, matches6.length);
        RuleMatch[] matches7 = rule.match(lt.analyzeText("‘this is a sentence."));
        assertEquals(1, matches7.length);
        RuleMatch[] matches8 = rule.match(lt.analyzeText("¿esto es una pregunta?"));
        assertEquals(1, matches8.length);
        RuleMatch[] matches9 = rule.match(lt.analyzeText("¿Esto es una pregunta? ¿y esto?"));
        assertEquals(1, matches9.length);
    }
}
