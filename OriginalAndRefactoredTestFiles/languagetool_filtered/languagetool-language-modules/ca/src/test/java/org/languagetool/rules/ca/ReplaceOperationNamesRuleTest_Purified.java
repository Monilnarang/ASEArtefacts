package org.languagetool.rules.ca;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.language.Catalan;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

public class ReplaceOperationNamesRuleTest_Purified {

    private ReplaceOperationNamesRule rule;

    private JLanguageTool lt;

    @Before
    public void setUp() throws IOException {
        rule = new ReplaceOperationNamesRule(TestTools.getEnglishMessages(), Catalan.getInstance());
        lt = new JLanguageTool(Catalan.getInstance());
    }

    private void assertCorrect(String sentence) throws IOException {
        final RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(sentence));
        assertEquals(0, matches.length);
    }

    private void assertIncorrect(String sentence) throws IOException {
        final RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(sentence));
        assertEquals(1, matches.length);
    }

    @Test
    public void testRule_1() throws IOException {
        assertCorrect("tot tenyit amb llum de nostàlgia");
    }

    @Test
    public void testRule_2() throws IOException {
        assertCorrect("Ho van fer per duplicat.");
    }

    @Test
    public void testRule_3() throws IOException {
        assertCorrect("Assecat el braç del riu");
    }

    @Test
    public void testRule_4() throws IOException {
        assertCorrect("el llibre empaquetat");
    }

    @Test
    public void testRule_5() throws IOException {
        assertCorrect("un resultat equilibrat");
    }

    @Test
    public void testRule_6() throws IOException {
        assertCorrect("el nostre equip era bastant equilibrat");
    }

    @Test
    public void testRule_7() throws IOException {
        assertCorrect("un llibre ben empaquetat");
    }

    @Test
    public void testRule_8() throws IOException {
        assertCorrect("l'informe filtrat pel ministre");
    }

    @Test
    public void testRule_9() throws IOException {
        assertCorrect("L'informe filtrat és terrible");
    }

    @Test
    public void testRule_10() throws IOException {
        assertCorrect("ha liderat la batalla");
    }

    @Test
    public void testRule_11() throws IOException {
        assertCorrect("Els tinc empaquetats");
    }

    @Test
    public void testRule_12() throws IOException {
        assertCorrect("amb tractament unitari i equilibrat");
    }

    @Test
    public void testRule_13() throws IOException {
        assertCorrect("Processat després de la mort de Carles II");
    }

    @Test
    public void testRule_14() throws IOException {
        assertCorrect("Processat diverses vegades");
    }

    @Test
    public void testRule_15() throws IOException {
        assertCorrect("moltes vegades empaquetat amb pressa");
    }

    @Test
    public void testRule_16() throws IOException {
        assertCorrect("és llavors embotellat i llançat al mercat");
    }

    @Test
    public void testRule_17() throws IOException {
        assertCorrect("la comercialització de vi embotellat amb les firmes comercials");
    }

    @Test
    public void testRule_18() throws IOException {
        assertCorrect("eixia al mercat el vi blanc embotellat amb la marca");
    }

    @Test
    public void testRule_19() throws IOException {
        assertCorrect("que arribi a un equilibrat matrimoni");
    }

    @Test
    public void testRule_20() throws IOException {
        assertCorrect("És un cafè amb molt de cos i molt equilibrat.");
    }

    @Test
    public void testRule_21() throws IOException {
        assertCorrect("i per tant etiquetat com a observat");
    }

    @Test
    public void testRule_22() throws IOException {
        assertCorrect("Molt equilibrat en les seves característiques");
    }

    @Test
    public void testRule_23() throws IOException {
        assertCorrect("filtrat per Wikileaks");
    }

    @Test
    public void testRule_24() throws IOException {
        assertCorrect("una vegada filtrat");
    }

    @Test
    public void testRule_25() throws IOException {
        assertCorrect("no equilibrat");
    }

    @Test
    public void testRule_26() throws IOException {
        assertIncorrect("Assecat del braç del riu");
    }

    @Test
    public void testRule_27() throws IOException {
        assertIncorrect("Cal vigilar el filtrat del vi");
    }

    @Test
    public void testRule_28() throws IOException {
        assertIncorrect("El procés d'empaquetat");
    }

    @Test
    public void testRule_29() throws IOException {
        assertIncorrect("Els equilibrats de les rodes");
    }

    @Test
    public void testRule_30() throws IOException {
        assertIncorrect("El procés d'etiquetat de les ampolles");
    }

    @Test
    public void testRule_31() throws IOException {
        assertIncorrect("El rentat de cotes");
    }

    @Test
    public void testRule_32_testMerged_32() throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence("El repicat i el rejuntat."));
        assertEquals(2, matches.length);
        matches = rule.match(lt.getAnalyzedSentence("El procés de relligat dels llibres."));
        assertEquals(1, matches.length);
        assertEquals("relligadura", matches[0].getSuggestedReplacements().get(0));
        assertEquals("relligament", matches[0].getSuggestedReplacements().get(1));
        assertEquals("relligada", matches[0].getSuggestedReplacements().get(2));
        matches = rule.match(lt.getAnalyzedSentence("Els rentats de cervell."));
        assertEquals("rentades", matches[0].getSuggestedReplacements().get(0));
        assertEquals("rentatges", matches[0].getSuggestedReplacements().get(1));
        assertEquals("rentaments", matches[0].getSuggestedReplacements().get(2));
    }
}
