package org.languagetool.rules.de;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.util.Collections;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.rules.GenericUnpairedBracketsRule;
import org.languagetool.rules.RuleMatch;

public class GenericUnpairedBracketsRuleTest_Purified {

    private GenericUnpairedBracketsRule rule;

    private JLanguageTool lt;

    private void assertMatches(String input, int expectedMatches) throws IOException {
        RuleMatch[] matches = rule.match(Collections.singletonList(lt.getAnalyzedSentence(input)));
        assertEquals(expectedMatches, matches.length);
    }

    @Test
    public void testGermanRule_1() throws IOException {
        assertMatches("(Das sind die Sätze, die sie testen sollen).", 0);
    }

    @Test
    public void testGermanRule_2() throws IOException {
        assertMatches("(Das sind die {Sätze}, die sie testen sollen).", 0);
    }

    @Test
    public void testGermanRule_3() throws IOException {
        assertMatches("(Das sind die [Sätze], die sie testen sollen).", 0);
    }

    @Test
    public void testGermanRule_4() throws IOException {
        assertMatches("(Das sind die Sätze (noch mehr Klammern [schon wieder!]), die sie testen sollen).", 0);
    }

    @Test
    public void testGermanRule_5() throws IOException {
        assertMatches("Das ist ein Satz mit Smiley :-)", 0);
    }

    @Test
    public void testGermanRule_6() throws IOException {
        assertMatches("Das ist auch ein Satz mit Smiley ;-)", 0);
    }

    @Test
    public void testGermanRule_7() throws IOException {
        assertMatches("Das ist ein Satz mit Smiley :)", 0);
    }

    @Test
    public void testGermanRule_8() throws IOException {
        assertMatches("Das ist ein Satz mit Smiley :(", 0);
    }

    @Test
    public void testGermanRule_9() throws IOException {
        assertMatches("Die URL lautet https://de.wikipedia.org/wiki/Schlammersdorf_(Adelsgeschlecht)", 0);
    }

    @Test
    public void testGermanRule_10() throws IOException {
        assertMatches("Die URL lautet https://de.wikipedia.org/wiki/Schlammersdorf_(Adelsgeschlecht).", 0);
    }

    @Test
    public void testGermanRule_11() throws IOException {
        assertMatches("(Die URL lautet https://de.wikipedia.org/wiki/Schlammersdorf_(Adelsgeschlecht))", 0);
    }

    @Test
    public void testGermanRule_12() throws IOException {
        assertMatches("(Die URL lautet https://de.wikipedia.org/wiki/Schlammersdorf)", 0);
    }

    @Test
    public void testGermanRule_13() throws IOException {
        assertMatches("(Die URL lautet https://de.wikipedia.org/wiki/Schlammersdorf oder so)", 0);
    }

    @Test
    public void testGermanRule_14() throws IOException {
        assertMatches("(Die URL lautet: http://www.pariscinema.org/).", 0);
    }
}
