package org.languagetool.rules.de;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.Language;
import org.languagetool.Languages;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class GermanWrongWordInContextRuleTest_Parameterized {

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

    @ParameterizedTest
    @MethodSource("Provider_testRule_1_11to19_24to25_30to31_34to35_38to39")
    public void testRule_1_11to19_24to25_30to31_34to35_38to39(String param1) throws IOException {
        assertBad(param1);
    }

    static public Stream<Arguments> Provider_testRule_1_11to19_24to25_30to31_34to35_38to39() {
        return Stream.of(arguments("Eine Laiche ist ein toter Körper."), arguments("Er verzieht keine Mine."), arguments("Mit unbewegter Mine."), arguments("Er setzt eine kalte Mine auf."), arguments("Er sagt, die unterirdische Miene sei zusammengestürzt."), arguments("Die Miene ist eingestürzt."), arguments("Die Sprengung mit Mienen ist toll."), arguments("Der Bleistift hat eine Miene."), arguments("Die Mienen sind gestern Abend explodiert."), arguments("Die Miene des Kugelschreibers ist leer."), arguments("Atomkerne bestehen aus Protonen und Neuronen"), arguments("Über eine Synapse wird das Neutron mit einer bestimmten Zelle verknüpft und nimmt mit der lokal zugeordneten postsynaptischen Membranregion eines Dendriten Signale auf."), arguments("Es kamen Keime in die Winde."), arguments("Möglicherweise wehen die Wunde gerade nicht günstig."), arguments("Den Kuchen mit Puderzucker betäuben."), arguments("Von Drogen bestäubt spürte er keine Schmerzen."), arguments("Nach Diktat vereist."), arguments("Die Tragfläche war verreist."));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRule_2to10_22to23_28to29_32to33_36to37")
    public void testRule_2to10_22to23_28to29_32to33_36to37(String param1) throws IOException {
        assertGood(param1);
    }

    static public Stream<Arguments> Provider_testRule_2to10_22to23_28to29_32to33_36to37() {
        return Stream.of(arguments("Eine Leiche ist ein toter Körper."), arguments("Die Leichen der Verstorbenen wurden ins Wasser geworfen."), arguments("Er verzieht keine Miene."), arguments("Er verzieht keine Miene."), arguments("Die Explosion der Mine."), arguments("Die Mine ist explodiert."), arguments("Er versucht, keine Miene zu verziehen."), arguments("Sie sollen weiter Minen eingesetzt haben."), arguments("Er verzieht sich nach Bekanntgabe der Mineralölsteuerverordnung."), arguments("Nervenzellen nennt man Neuronen"), arguments("Das Neutron ist elektisch neutral"), arguments("Das Seil läuft durch eine Winde."), arguments("Eine blutende Wunde"), arguments("Er war durch die Narkose betäubt."), arguments("Die Biene bestäubt die Blume."), arguments("Er verreist stets mit leichtem Gepäck."), arguments("Die Warze wurde vereist."));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRule_20to21_26to27")
    public void testRule_20to21_26to27(String param1, int param2, int param3, String param4) throws IOException {
        assertEquals(param1, rule.match(lt.getAnalyzedSentence(param4))[param3].getSuggestedReplacements().get(param2));
    }

    static public Stream<Arguments> Provider_testRule_20to21_26to27() {
        return Stream.of(arguments("Minen", 0, 0, "Er hat das mit den Mienen weggesprengt."), arguments("Miene", 0, 0, "Er versucht, keine Mine zu verziehen."), arguments("Neutronen", 0, 0, "Protonen und Neuronen sind Bausteine des Atomkerns"), arguments("Neurons", 0, 0, "Das Axon des Neutrons ..."));
    }
}
