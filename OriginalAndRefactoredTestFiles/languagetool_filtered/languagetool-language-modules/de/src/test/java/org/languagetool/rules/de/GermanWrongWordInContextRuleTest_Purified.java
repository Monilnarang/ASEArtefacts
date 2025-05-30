package org.languagetool.rules.de;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.Language;
import org.languagetool.Languages;

public class GermanWrongWordInContextRuleTest_Purified {

    private JLanguageTool lt;

    private GermanWrongWordInContextRule rule;

    @Before
    public void setUp() throws IOException {
        Language german = Languages.getLanguageForShortCode("de-DE");
        lt = new JLanguageTool(german);
        rule = new GermanWrongWordInContextRule(null, german);
    }

    private void assertGood(String sentence) throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence(sentence)).length);
    }

    private void assertBad(String sentence) throws IOException {
        assertEquals(1, rule.match(lt.getAnalyzedSentence(sentence)).length);
    }

    @Test
    public void testRule_1() throws IOException {
        assertBad("Eine Laiche ist ein toter Körper.");
    }

    @Test
    public void testRule_2() throws IOException {
        assertGood("Eine Leiche ist ein toter Körper.");
    }

    @Test
    public void testRule_3() throws IOException {
        assertGood("Die Leichen der Verstorbenen wurden ins Wasser geworfen.");
    }

    @Test
    public void testRule_4() throws IOException {
        assertGood("Er verzieht keine Miene.");
    }

    @Test
    public void testRule_5() throws IOException {
        assertGood("Er verzieht keine Miene.");
    }

    @Test
    public void testRule_6() throws IOException {
        assertGood("Die Explosion der Mine.");
    }

    @Test
    public void testRule_7() throws IOException {
        assertGood("Die Mine ist explodiert.");
    }

    @Test
    public void testRule_8() throws IOException {
        assertGood("Er versucht, keine Miene zu verziehen.");
    }

    @Test
    public void testRule_9() throws IOException {
        assertGood("Sie sollen weiter Minen eingesetzt haben.");
    }

    @Test
    public void testRule_10() throws IOException {
        assertGood("Er verzieht sich nach Bekanntgabe der Mineralölsteuerverordnung.");
    }

    @Test
    public void testRule_11() throws IOException {
        assertBad("Er verzieht keine Mine.");
    }

    @Test
    public void testRule_12() throws IOException {
        assertBad("Mit unbewegter Mine.");
    }

    @Test
    public void testRule_13() throws IOException {
        assertBad("Er setzt eine kalte Mine auf.");
    }

    @Test
    public void testRule_14() throws IOException {
        assertBad("Er sagt, die unterirdische Miene sei zusammengestürzt.");
    }

    @Test
    public void testRule_15() throws IOException {
        assertBad("Die Miene ist eingestürzt.");
    }

    @Test
    public void testRule_16() throws IOException {
        assertBad("Die Sprengung mit Mienen ist toll.");
    }

    @Test
    public void testRule_17() throws IOException {
        assertBad("Der Bleistift hat eine Miene.");
    }

    @Test
    public void testRule_18() throws IOException {
        assertBad("Die Mienen sind gestern Abend explodiert.");
    }

    @Test
    public void testRule_19() throws IOException {
        assertBad("Die Miene des Kugelschreibers ist leer.");
    }

    @Test
    public void testRule_20() throws IOException {
        assertEquals("Minen", rule.match(lt.getAnalyzedSentence("Er hat das mit den Mienen weggesprengt."))[0].getSuggestedReplacements().get(0));
    }

    @Test
    public void testRule_21() throws IOException {
        assertEquals("Miene", rule.match(lt.getAnalyzedSentence("Er versucht, keine Mine zu verziehen."))[0].getSuggestedReplacements().get(0));
    }

    @Test
    public void testRule_22() throws IOException {
        assertGood("Nervenzellen nennt man Neuronen");
    }

    @Test
    public void testRule_23() throws IOException {
        assertGood("Das Neutron ist elektisch neutral");
    }

    @Test
    public void testRule_24() throws IOException {
        assertBad("Atomkerne bestehen aus Protonen und Neuronen");
    }

    @Test
    public void testRule_25() throws IOException {
        assertBad("Über eine Synapse wird das Neutron mit einer bestimmten Zelle verknüpft und nimmt mit der lokal zugeordneten postsynaptischen Membranregion eines Dendriten Signale auf.");
    }

    @Test
    public void testRule_26() throws IOException {
        assertEquals("Neutronen", rule.match(lt.getAnalyzedSentence("Protonen und Neuronen sind Bausteine des Atomkerns"))[0].getSuggestedReplacements().get(0));
    }

    @Test
    public void testRule_27() throws IOException {
        assertEquals("Neurons", rule.match(lt.getAnalyzedSentence("Das Axon des Neutrons ..."))[0].getSuggestedReplacements().get(0));
    }

    @Test
    public void testRule_28() throws IOException {
        assertGood("Das Seil läuft durch eine Winde.");
    }

    @Test
    public void testRule_29() throws IOException {
        assertGood("Eine blutende Wunde");
    }

    @Test
    public void testRule_30() throws IOException {
        assertBad("Es kamen Keime in die Winde.");
    }

    @Test
    public void testRule_31() throws IOException {
        assertBad("Möglicherweise wehen die Wunde gerade nicht günstig.");
    }

    @Test
    public void testRule_32() throws IOException {
        assertGood("Er war durch die Narkose betäubt.");
    }

    @Test
    public void testRule_33() throws IOException {
        assertGood("Die Biene bestäubt die Blume.");
    }

    @Test
    public void testRule_34() throws IOException {
        assertBad("Den Kuchen mit Puderzucker betäuben.");
    }

    @Test
    public void testRule_35() throws IOException {
        assertBad("Von Drogen bestäubt spürte er keine Schmerzen.");
    }

    @Test
    public void testRule_36() throws IOException {
        assertGood("Er verreist stets mit leichtem Gepäck.");
    }

    @Test
    public void testRule_37() throws IOException {
        assertGood("Die Warze wurde vereist.");
    }

    @Test
    public void testRule_38() throws IOException {
        assertBad("Nach Diktat vereist.");
    }

    @Test
    public void testRule_39() throws IOException {
        assertBad("Die Tragfläche war verreist.");
    }
}
