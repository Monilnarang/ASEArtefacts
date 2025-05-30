package org.languagetool.rules.es;

import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.language.Spanish;
import org.languagetool.markup.AnnotatedText;
import org.languagetool.markup.AnnotatedTextBuilder;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import java.util.Collections;
import static org.junit.Assert.assertEquals;

public class SpanishUnpairedBracketsRuleTest_Purified {

    private SpanishUnpairedBracketsRule rule;

    private JLanguageTool lt;

    private void assertMatches(String input, int expectedMatches) throws IOException {
        final RuleMatch[] matches = rule.match(Collections.singletonList(lt.getAnalyzedSentence(input)));
        assertEquals(expectedMatches, matches.length);
    }

    private void assertCorrectText(String sentences) throws IOException {
        AnnotatedText aText = new AnnotatedTextBuilder().addText(sentences).build();
        RuleMatch[] matches = rule.match(lt.analyzeText(sentences), aText);
        assertEquals(0, matches.length);
    }

    @Test
    public void testSpanishRule_1() throws IOException {
        assertMatches("Soy un hombre (muy honrado).", 0);
    }

    @Test
    public void testSpanishRule_2() throws IOException {
        assertMatches("D'Hondt.", 0);
    }

    @Test
    public void testSpanishRule_3() throws IOException {
        assertMatches("Guns N’ Roses", 0);
    }

    @Test
    public void testSpanishRule_4() throws IOException {
        assertMatches("Guns N' Roses", 0);
    }

    @Test
    public void testSpanishRule_5() throws IOException {
        assertMatches("D’Hondt.", 0);
    }

    @Test
    public void testSpanishRule_6() throws IOException {
        assertMatches("L’Équipe", 0);
    }

    @Test
    public void testSpanishRule_7() throws IOException {
        assertMatches("rock ’n’ roll", 0);
    }

    @Test
    public void testSpanishRule_8() throws IOException {
        assertMatches("Harper's Dictionary of Classical Antiquities", 0);
    }

    @Test
    public void testSpanishRule_9() throws IOException {
        assertMatches("Harper’s Dictionary of Classical Antiquities", 0);
    }

    @Test
    public void testSpanishRule_10() throws IOException {
        assertMatches("Soy un hombre muy honrado).", 1);
    }

    @Test
    public void testSpanishRule_11() throws IOException {
        assertMatches("Soy un hombre (muy honrado.", 1);
    }

    @Test
    public void testSpanishRule_12() throws IOException {
        assertMatches("Eso es “importante y qué pasa. ", 1);
    }

    @Test
    public void testSpanishRule_13() throws IOException {
        assertMatches("Eso es \"importante y qué. ", 1);
    }

    @Test
    public void testSpanishRule_14() throws IOException {
        assertMatches("Eso es (imposible. ", 1);
    }

    @Test
    public void testSpanishRule_15() throws IOException {
        assertMatches("Eso es (imposible.\n\n", 1);
    }

    @Test
    public void testSpanishRule_16() throws IOException {
        assertMatches("Eso es) imposible. ", 1);
    }

    @Test
    public void testSpanishRule_17() throws IOException {
        assertMatches("Eso es imposible).\t\n ", 1);
    }

    @Test
    public void testSpanishRule_18() throws IOException {
        assertMatches("Eso es «importante, ¿ah que sí?", 1);
    }

    @Test
    public void testSpanishRule_19() throws IOException {
        assertCorrectText("\n\n" + "a) New York\n" + "b) Boston\n");
    }

    @Test
    public void testSpanishRule_20() throws IOException {
        assertCorrectText("\n\n" + "1.) New York\n" + "2.) Boston\n");
    }

    @Test
    public void testSpanishRule_21() throws IOException {
        assertCorrectText("\n\n" + "XII.) New York\n" + "XIII.) Boston\n");
    }

    @Test
    public void testSpanishRule_22() throws IOException {
        assertCorrectText("\n\n" + "A) New York\n" + "B) Boston\n" + "C) Foo\n");
    }
}
