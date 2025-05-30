package org.languagetool.rules.de;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;
import org.languagetool.AnalyzedSentence;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.TestTools;
import org.languagetool.rules.RuleMatch;

public class DuUpperLowerCaseRuleTest_Purified {

    private final DuUpperLowerCaseRule rule = new DuUpperLowerCaseRule(TestTools.getEnglishMessages());

    private final JLanguageTool lt = new JLanguageTool(Languages.getLanguageForShortCode("de-DE"));

    private void assertErrors(String input, int expectedMatches) throws IOException {
        AnalyzedSentence sentence = lt.getAnalyzedSentence(input);
        RuleMatch[] matches = rule.match(Collections.singletonList(sentence));
        assertThat("Expected " + expectedMatches + ", got " + matches.length + ": " + sentence.getText() + " -> " + Arrays.toString(matches), matches.length, is(expectedMatches));
    }

    @Test
    public void testRule_1() throws IOException {
        assertErrors("Du bist noch jung.", 0);
    }

    @Test
    public void testRule_2() throws IOException {
        assertErrors("Du bist noch jung, du bist noch fit.", 0);
    }

    @Test
    public void testRule_3() throws IOException {
        assertErrors("Aber du bist noch jung, du bist noch fit.", 0);
    }

    @Test
    public void testRule_4() throws IOException {
        assertErrors("Aber du bist noch jung, dir ist das egal.", 0);
    }

    @Test
    public void testRule_5() throws IOException {
        assertErrors("Hast Du ihre Brieftasche gesehen?", 0);
    }

    @Test
    public void testRule_6() throws IOException {
        assertErrors("Aber Du bist noch jung, du bist noch fit.", 1);
    }

    @Test
    public void testRule_7() throws IOException {
        assertErrors("Aber Du bist noch jung, dir ist das egal.", 1);
    }

    @Test
    public void testRule_8() throws IOException {
        assertErrors("Aber Du bist noch jung. Und dir ist das egal.", 1);
    }

    @Test
    public void testRule_9() throws IOException {
        assertErrors("Aber du bist noch jung. Und Du bist noch fit.", 1);
    }

    @Test
    public void testRule_10() throws IOException {
        assertErrors("Aber du bist noch jung, Dir ist das egal.", 1);
    }

    @Test
    public void testRule_11() throws IOException {
        assertErrors("Aber du bist noch jung. Und Dir ist das egal.", 1);
    }

    @Test
    public void testRule_12() throws IOException {
        assertErrors("Aber du bist noch jung, sagt euer Vater oft.", 0);
    }

    @Test
    public void testRule_13() throws IOException {
        assertErrors("Aber Du bist noch jung, sagt Euer Vater oft.", 0);
    }

    @Test
    public void testRule_14() throws IOException {
        assertErrors("Aber Du bist noch jung, sagt euer Vater oft.", 1);
    }

    @Test
    public void testRule_15() throws IOException {
        assertErrors("Aber du bist noch jung, sagt Euer Vater oft.", 1);
    }

    @Test
    public void testRule_16() throws IOException {
        assertErrors("Könnt Ihr Euch das vorstellen???", 0);
    }

    @Test
    public void testRule_17() throws IOException {
        assertErrors("Könnt ihr euch das vorstellen???", 0);
    }

    @Test
    public void testRule_18() throws IOException {
        assertErrors("Aber Samstags geht ihr Sohn zum Sport. Stellt Euch das mal vor!", 0);
    }

    @Test
    public void testRule_19() throws IOException {
        assertErrors("Wie geht es euch? Herr Meier, wie war ihr Urlaub?", 0);
    }

    @Test
    public void testRule_20() throws IOException {
        assertErrors("Wie geht es Euch? Herr Meier, wie war Ihr Urlaub?", 0);
    }

    @Test
    public void testRule_21() throws IOException {
        assertErrors("\"Du sagtest, du würdest es schaffen!\"", 0);
    }

    @Test
    public void testRule_22() throws IOException {
        assertErrors("Egal, was du tust: Du musst dein Bestes geben.", 0);
    }

    @Test
    public void testRule_23() throws IOException {
        assertErrors("Was auch immer du tust: ICH UND DU KÖNNEN ES SCHAFFEN.", 0);
    }

    @Test
    public void testRule_24() throws IOException {
        assertErrors("Hast Du die Adresse von ihr?", 0);
    }
}
