package org.languagetool.synthesis;

import org.junit.Ignore;
import org.junit.Test;
import org.languagetool.AnalyzedToken;
import java.io.IOException;
import java.util.Arrays;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class GermanSynthesizerTest_Purified {

    private final GermanSynthesizer synthesizer = GermanSynthesizer.INSTANCE;

    private String synth(String word, String posTag) throws IOException {
        return Arrays.toString(synthesizer.synthesize(dummyToken(word), posTag));
    }

    private String synth(String word, String posTag, boolean regEx) throws IOException {
        return Arrays.toString(synthesizer.synthesize(dummyToken(word), posTag, regEx));
    }

    private AnalyzedToken dummyToken(String tokenStr) {
        return new AnalyzedToken(tokenStr, tokenStr, tokenStr);
    }

    @Test
    public void testSynthesize_1() throws IOException {
        assertThat(synth("123", "_spell_number_"), is("[einhundertdreiundzwanzig]"));
    }

    @Test
    public void testSynthesize_2() throws IOException {
        assertThat(synth("Zug", "SUB:DAT:SIN:MAS"), is("[Zug]"));
    }

    @Test
    public void testSynthesize_3() throws IOException {
        assertThat(synth("Tisch", "SUB:DAT:SIN:MAS"), is("[Tisch]"));
    }

    @Test
    public void testSynthesize_4() throws IOException {
        assertThat(synth("Buschfeuer", "SUB:GEN:SIN:NEU"), is("[Buschfeuers]"));
    }

    @Test
    public void testSynthesize_5() throws IOException {
        assertThat(synth("Äußerung", "SUB:NOM:PLU:FEM"), is("[Äußerungen]"));
    }

    @Test
    public void testSynthesize_6() throws IOException {
        assertThat(synth("Äußerung", "SUB:NOM:PLU:MAS"), is("[]"));
    }

    @Test
    public void testSynthesize_7() throws IOException {
        assertThat(synth("Haus", "SUB:AKK:PLU:NEU"), is("[Häuser]"));
    }

    @Test
    public void testSynthesize_8() throws IOException {
        assertThat(synth("Diabetes-Zentrum", "SUB:GEN:SIN.*", true), is("[Diabetes-Zentrums]"));
    }

    @Test
    public void testSynthesize_9() throws IOException {
        assertThat(synth("Diabetes-Zentrum", "SUB:GEN:PLU.*", true), is("[Diabetes-Zentren]"));
    }

    @Test
    public void testSynthesize_10() throws IOException {
        assertThat(synth("Xxxxxx-Zentrum", "SUB:GEN:PLU.*", true), is("[Xxxxxx-Zentren]"));
    }

    @Test
    public void testSynthesize_11() throws IOException {
        assertThat(synth("Xxxxxx-zentrum", "SUB:GEN:PLU.*", true), is("[Xxxxxx-zentren]"));
    }

    @Test
    public void testSynthesize_12() throws IOException {
        assertThat(synth("gelb-grün", "ADJ.*", true), is("[]"));
    }

    @Test
    public void testSynthesize_13() throws IOException {
        assertThat(synth("WLAN-LAN-Kabel", "SUB:GEN:SIN.*", true), is("[WLAN-LAN-Kabels]"));
    }

    @Test
    public void testSynthesize_14() throws IOException {
        assertThat(synth("Haus", ".*", true), is("[Häuser, Haus, Häusern, Haus, Hause, Häuser, Hauses, Häuser, Haus]"));
    }

    @Test
    public void testSynthesize_15() throws IOException {
        assertThat(synthesizer.synthesize(new AnalyzedToken("fake", "FAKE", null), "FAKE", false).length, is(0));
    }

    @Test
    public void testSynthesizeCompounds_1() throws IOException {
        assertThat(synth("Regelsystem", "SUB:NOM:PLU:NEU"), is("[Regelsysteme]"));
    }

    @Test
    public void testSynthesizeCompounds_2() throws IOException {
        assertThat(synth("Regelsystem", "SUB:DAT:PLU:NEU"), is("[Regelsystemen]"));
    }

    @Test
    public void testSynthesizeCompounds_3() throws IOException {
        assertThat(synth("Regelsystem", ".*:PLU:.*", true), is("[Regelsysteme, Regelsystemen]"));
    }

    @Test
    public void testSynthesizeCompounds_4() throws IOException {
        assertThat(synth("Regel-System", ".*:PLU:.*", true), is("[Regelsysteme, Regelsystemen]"));
    }

    @Test
    public void testSynthesizeCompounds_5() throws IOException {
        assertThat(synth("Kühlschrankversuch", ".*:PLU:.*", true), is("[Kühlschrankversuche, Kühlschrankversuchen]"));
    }

    @Test
    public void testSynthesizeCompounds_6() throws IOException {
        assertThat(synth("Kühlschrank-Versuch", ".*:PLU:.*", true), is("[Kühlschrankversuche, Kühlschrankversuchen]"));
    }
}
