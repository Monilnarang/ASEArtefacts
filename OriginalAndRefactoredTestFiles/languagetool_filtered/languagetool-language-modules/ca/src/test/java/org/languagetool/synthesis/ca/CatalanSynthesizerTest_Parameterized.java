package org.languagetool.synthesis.ca;

import org.junit.Test;
import org.languagetool.AnalyzedToken;
import org.languagetool.Language;
import org.languagetool.language.ValencianCatalan;
import java.io.IOException;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class CatalanSynthesizerTest_Parameterized {

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
    public final void testSynthesizeStringString_11() throws IOException {
        assertEquals(0, synth.synthesize(dummyToken("blablabla"), "blablabla").length);
    }

    @ParameterizedTest
    @MethodSource("Provider_testSynthesizeStringString_1to10_12to17_19")
    public final void testSynthesizeStringString_1to10_12to17_19(String param1, int param2, String param3) throws IOException {
        assertEquals(param1, synth(param2, param3));
    }

    static public Stream<Arguments> Provider_testSynthesizeStringString_1to10_12to17_19() {
        return Stream.of(arguments("[un]", 1, "_spell_number_"), arguments("[onze]", 11, "_spell_number_"), arguments("[vint-i-un]", 21, "_spell_number_"), arguments("[vint-i-quatre]", 24, "_spell_number_"), arguments("[vint-i-una]", 21, "_spell_number_:feminine"), arguments("[vint-i-dues]", 22, "_spell_number_:feminine"), arguments("[dos]", 2, "_spell_number_"), arguments("[dues]", 2, "_spell_number_:feminine"), arguments("[dues-centes quaranta-dues]", 242, "_spell_number_:feminine"), arguments("[dos milions dues-centes cinquanta-una mil dues-centes quaranta-una]", 2251241, "_spell_number_:feminine"), arguments("[sento]", "sentir", "VMIP1S0C"), arguments("[sent]", "sentir", "VMIP1S0Z"), arguments("[senta]", "sentir", "VMSP3S0V"), arguments("[nostres]", "nostre", "PX1CP0P0"), arguments("[presidents]", "president", "NCMP000"), arguments("[comprovat]", "comprovar", "VMP00SM.?"), arguments("[faig servir]", "fer servir", "VMIP1S0C"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSynthesizeStringString_18_20to21")
    public final void testSynthesizeStringString_18_20to21(String param1, String param2, String param3) throws IOException {
        assertEquals(param1, synthRegex(param2, param3));
    }

    static public Stream<Arguments> Provider_testSynthesizeStringString_18_20to21() {
        return Stream.of(arguments("[albèrxics]", "albèrxic", "NCMP000"), arguments("[comprovades, comprovats, comprovada, comprovat]", "comprovar", "V.P.*"), arguments("[contestant, contestar]", "contestar", "VM[GN]0000.?"));
    }
}
