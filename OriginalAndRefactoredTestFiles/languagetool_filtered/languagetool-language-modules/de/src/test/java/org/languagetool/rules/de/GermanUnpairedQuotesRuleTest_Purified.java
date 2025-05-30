package org.languagetool.rules.de;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import java.util.Collections;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.rules.GenericUnpairedQuotesRule;
import org.languagetool.rules.GenericUnpairedQuotesRuleTest;
import org.languagetool.rules.RuleMatch;

public class GermanUnpairedQuotesRuleTest_Purified {

    private GenericUnpairedQuotesRule rule;

    private JLanguageTool lt;

    private void assertMatches(String input, int expectedMatches) throws IOException {
        RuleMatch[] matches = rule.match(Collections.singletonList(lt.getAnalyzedSentence(input)));
        assertEquals(expectedMatches, matches.length);
    }

    @Test
    public void testGermanRule_1() throws IOException {
        assertMatches("»Das sind die Sätze, die sie testen sollen«.", 0);
    }

    @Test
    public void testGermanRule_2() throws IOException {
        assertMatches("«Das sind die ‹Sätze›, die sie testen sollen».", 0);
    }

    @Test
    public void testGermanRule_3() throws IOException {
        assertMatches("»Das sind die ›Sätze‹, die sie testen sollen«.", 0);
    }

    @Test
    public void testGermanRule_4() throws IOException {
        assertMatches("»Das sind die Sätze ›noch mehr Anführungszeichen‹ ›schon wieder!‹, die sie testen sollen«.", 0);
    }

    @Test
    public void testGermanRule_5() throws IOException {
        assertMatches("»Das sind die Sätze ›noch mehr Anführungszeichen ›hier ein Fehler!‹‹, die sie testen sollen«.", 2);
    }

    @Test
    public void testGermanRule_6() throws IOException {
        assertMatches("„Das sind die Sätze ‚noch mehr Anführungszeichen‘ ‚schon wieder!‘, die sie testen sollen“.", 0);
    }

    @Test
    public void testGermanRule_7() throws IOException {
        assertMatches("„Das sind die Sätze ‚noch mehr Anführungszeichen ‚hier ein Fehler!‘‘, die sie testen sollen“.", 2);
    }

    @Test
    public void testGermanRule_8() throws IOException {
        assertMatches("„Das sind die Sätze, die sie testen sollen.“ „Hier steht ein zweiter Satz.“", 0);
    }

    @Test
    public void testGermanRule_9() throws IOException {
        assertMatches("Drücken Sie auf den \"Jetzt Starten\"-Knopf.", 0);
    }

    @Test
    public void testGermanRule_10() throws IOException {
        assertMatches("Welches ist dein Lieblings-\"Star Wars\"-Charakter?", 0);
    }

    @Test
    public void testGermanRule_11() throws IOException {
        assertMatches("‚So 'n Blödsinn!‘", 0);
    }

    @Test
    public void testGermanRule_12() throws IOException {
        assertMatches("‚’n Blödsinn!‘", 0);
    }

    @Test
    public void testGermanRule_13() throws IOException {
        assertMatches("'So 'n Blödsinn!'", 0);
    }

    @Test
    public void testGermanRule_14() throws IOException {
        assertMatches("''n Blödsinn!'", 0);
    }

    @Test
    public void testGermanRule_15() throws IOException {
        assertMatches("‚Das ist Hans’.‘", 0);
    }

    @Test
    public void testGermanRule_16() throws IOException {
        assertMatches("'Das ist Hans'.'", 0);
    }

    @Test
    public void testGermanRule_17() throws IOException {
        assertMatches("Das Fahrrad hat 26\" Räder.", 0);
    }

    @Test
    public void testGermanRule_18() throws IOException {
        assertMatches("\"Das Fahrrad hat 26\" Räder.\"", 0);
    }

    @Test
    public void testGermanRule_19() throws IOException {
        assertMatches("und steigern » Datenbankperformance steigern » Tipps zur Performance-Verbesserung", 0);
    }

    @Test
    public void testGermanRule_20() throws IOException {
        assertMatches("\"Das Fahrrad hat 26\" Räder.\" \"Und hier fehlt das abschließende doppelte Anführungszeichen.", 1);
    }

    @Test
    public void testGermanRule_21() throws IOException {
        assertMatches("Die „Sätze zum Testen.", 1);
    }

    @Test
    public void testGermanRule_22() throws IOException {
        assertMatches("Die «Sätze zum Testen.", 1);
    }

    @Test
    public void testGermanRule_23() throws IOException {
        assertMatches("Die »Sätze zum Testen.", 1);
    }
}
