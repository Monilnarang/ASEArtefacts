package org.languagetool.synthesis.ca;

import org.junit.Test;
import org.languagetool.AnalyzedToken;
import org.languagetool.Language;
import org.languagetool.language.ValencianCatalan;
import java.io.IOException;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;

public class CatalanSynthesizerTest_Purified {

    private final CatalanSynthesizer synth = CatalanSynthesizer.INSTANCE_CAT;

    private String synth(String word, String pos) throws IOException {
        return Arrays.toString(synth.synthesize(dummyToken(word), pos));
    }

    private String synthRegex(String word, String pos) throws IOException {
        return Arrays.toString(synth.synthesize(dummyToken(word), pos, true));
    }

    private String synthNonRegex(String word, String pos) throws IOException {
        return Arrays.toString(synth.synthesize(dummyToken(word), pos, false));
    }

    private AnalyzedToken dummyToken(String tokenStr) {
        return new AnalyzedToken(tokenStr, tokenStr, tokenStr);
    }

    @Test
    public final void testSynthesizeStringString_1() throws IOException {
        assertEquals("[un]", synth("1", "_spell_number_"));
    }

    @Test
    public final void testSynthesizeStringString_2() throws IOException {
        assertEquals("[onze]", synth("11", "_spell_number_"));
    }

    @Test
    public final void testSynthesizeStringString_3() throws IOException {
        assertEquals("[vint-i-un]", synth("21", "_spell_number_"));
    }

    @Test
    public final void testSynthesizeStringString_4() throws IOException {
        assertEquals("[vint-i-quatre]", synth("24", "_spell_number_"));
    }

    @Test
    public final void testSynthesizeStringString_5() throws IOException {
        assertEquals("[vint-i-una]", synth("21", "_spell_number_:feminine"));
    }

    @Test
    public final void testSynthesizeStringString_6() throws IOException {
        assertEquals("[vint-i-dues]", synth("22", "_spell_number_:feminine"));
    }

    @Test
    public final void testSynthesizeStringString_7() throws IOException {
        assertEquals("[dos]", synth("2", "_spell_number_"));
    }

    @Test
    public final void testSynthesizeStringString_8() throws IOException {
        assertEquals("[dues]", synth("2", "_spell_number_:feminine"));
    }

    @Test
    public final void testSynthesizeStringString_9() throws IOException {
        assertEquals("[dues-centes quaranta-dues]", synth("242", "_spell_number_:feminine"));
    }

    @Test
    public final void testSynthesizeStringString_10() throws IOException {
        assertEquals("[dos milions dues-centes cinquanta-una mil dues-centes quaranta-una]", synth("2251241", "_spell_number_:feminine"));
    }

    @Test
    public final void testSynthesizeStringString_11() throws IOException {
        assertEquals(0, synth.synthesize(dummyToken("blablabla"), "blablabla").length);
    }

    @Test
    public final void testSynthesizeStringString_12() throws IOException {
        assertEquals("[sento]", synth("sentir", "VMIP1S0C"));
    }

    @Test
    public final void testSynthesizeStringString_13() throws IOException {
        assertEquals("[sent]", synth("sentir", "VMIP1S0Z"));
    }

    @Test
    public final void testSynthesizeStringString_14() throws IOException {
        assertEquals("[senta]", synth("sentir", "VMSP3S0V"));
    }

    @Test
    public final void testSynthesizeStringString_15() throws IOException {
        assertEquals("[nostres]", synth("nostre", "PX1CP0P0"));
    }

    @Test
    public final void testSynthesizeStringString_16() throws IOException {
        assertEquals("[presidents]", synth("president", "NCMP000"));
    }

    @Test
    public final void testSynthesizeStringString_17() throws IOException {
        assertEquals("[comprovat]", synth("comprovar", "VMP00SM.?"));
    }

    @Test
    public final void testSynthesizeStringString_18() throws IOException {
        assertEquals("[albèrxics]", synthRegex("albèrxic", "NCMP000"));
    }

    @Test
    public final void testSynthesizeStringString_19() throws IOException {
        assertEquals("[faig servir]", synth("fer servir", "VMIP1S0C"));
    }

    @Test
    public final void testSynthesizeStringString_20() throws IOException {
        assertEquals("[comprovades, comprovats, comprovada, comprovat]", synthRegex("comprovar", "V.P.*"));
    }

    @Test
    public final void testSynthesizeStringString_21() throws IOException {
        assertEquals("[contestant, contestar]", synthRegex("contestar", "VM[GN]0000.?"));
    }
}
