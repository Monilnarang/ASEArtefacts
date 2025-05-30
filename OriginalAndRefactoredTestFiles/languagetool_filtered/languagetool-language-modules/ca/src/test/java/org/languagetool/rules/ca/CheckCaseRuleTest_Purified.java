package org.languagetool.rules.ca;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.language.Catalan;
import org.languagetool.rules.RuleMatch;

public class CheckCaseRuleTest_Purified {

    private CheckCaseRule rule;

    private JLanguageTool lt;

    @Before
    public void setUp() throws Exception {
        lt = new JLanguageTool(Catalan.getInstance());
        rule = new CheckCaseRule(TestTools.getMessages("ca"), lt.getLanguage());
    }

    @Test
    public void testRule_1() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("Sap que tinc dos bons amics?")).length);
    }

    @Test
    public void testRule_2() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("La seua millor amiga no sap què passa amb l'avi, però en parla en YouTube.")).length);
    }

    @Test
    public void testRule_3() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("El país necessita tecnologia més moderna.")).length);
    }

    @Test
    public void testRule_4() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("'Da Vinci'")).length);
    }

    @Test
    public void testRule_5() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("‒ 'Da Vinci'")).length);
    }

    @Test
    public void testRule_6() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("‒ ¡'Da Vinci'!")).length);
    }

    @Test
    public void testRule_7() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("El Prat de Llobregat")).length);
    }

    @Test
    public void testRule_8() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("08820 - El Prat de Llobregat")).length);
    }

    @Test
    public void testRule_9() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("el Prat de Llobregat")).length);
    }

    @Test
    public void testRule_10() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("Da Vinci")).length);
    }

    @Test
    public void testRule_11() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("Amb Joan Pau i Josep Maria.")).length);
    }

    @Test
    public void testRule_12() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("ESTAT D'ALARMA")).length);
    }

    @Test
    public void testRule_13() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("d'educació secundària")).length);
    }

    @Test
    public void testRule_14() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("Educació Secundària Obligatòria")).length);
    }

    @Test
    public void testRule_15() throws IOException {
        assertEquals(1, rule.match(lt.getAnalyzedSentence("Educació Secundària obligatòria")).length);
    }

    @Test
    public void testRule_16() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("Educació secundària obligatòria")).length);
    }

    @Test
    public void testRule_17() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("d'educació secundària obligatòria")).length);
    }

    @Test
    public void testRule_18() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("\\u2022 Intel·ligència artificial")).length);
    }

    @Test
    public void testRule_19() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("● Intel·ligència artificial")).length);
    }

    @Test
    public void testRule_20() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("Els drets humans")).length);
    }

    @Test
    public void testRule_21() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("Declaració Universal dels Drets Humans")).length);
    }

    @Test
    public void testRule_22() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("El codi Da Vinci")).length);
    }

    @Test
    public void testRule_23() throws IOException {
        assertEquals(1, rule.match(lt.getAnalyzedSentence("Declaració Universal dels drets humans")).length);
    }

    @Test
    public void testRule_24_testMerged_24() throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence("Joan pau"));
        assertEquals(1, matches.length);
        assertEquals("Joan Pau", matches[0].getSuggestedReplacements().get(0));
        matches = rule.match(lt.getAnalyzedSentence("Expedient de Regulació Temporal d'Ocupació"));
        assertEquals("Expedient de regulació temporal d'ocupació", matches[0].getSuggestedReplacements().get(0));
        matches = rule.match(lt.getAnalyzedSentence("Em vaig entrevistar amb Joan maria"));
        assertEquals("Joan Maria", matches[0].getSuggestedReplacements().get(0));
        matches = rule.match(lt.getAnalyzedSentence("Em vaig entrevistar amb Da Vinci"));
        assertEquals("da Vinci", matches[0].getSuggestedReplacements().get(0));
        matches = rule.match(lt.getAnalyzedSentence("-\"Leonardo Da Vinci\""));
        assertEquals("Leonardo da Vinci", matches[0].getSuggestedReplacements().get(0));
        matches = rule.match(lt.getAnalyzedSentence("-\"¿Leonardo Da Vinci?\""));
        matches = rule.match(lt.getAnalyzedSentence("Baixar Al-Assad"));
        assertEquals("Baixar al-Assad", matches[0].getSuggestedReplacements().get(0));
    }
}
