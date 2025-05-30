package org.languagetool.synthesis;

import org.junit.Ignore;
import org.junit.Test;
import org.languagetool.AnalyzedToken;
import java.io.IOException;
import java.util.Arrays;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class GermanSynthesizerTest_Parameterized {

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
    public void testSynthesize_15() throws IOException {
        assertThat(synthesizer.synthesize(new AnalyzedToken("fake", "FAKE", null), "FAKE", false).length, is(0));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSynthesize_1_1to2_2to7")
    public void testSynthesize_1_1to2_2to7(int param1, String param2, String param3) throws IOException {
        assertThat(synth(param1, param2), is(param3));
    }

    static public Stream<Arguments> Provider_testSynthesize_1_1to2_2to7() {
        return Stream.of(arguments(123, "_spell_number_", "[einhundertdreiundzwanzig]"), arguments("Zug", "SUB:DAT:SIN:MAS", "[Zug]"), arguments("Tisch", "SUB:DAT:SIN:MAS", "[Tisch]"), arguments("Buschfeuer", "SUB:GEN:SIN:NEU", "[Buschfeuers]"), arguments("Äußerung", "SUB:NOM:PLU:FEM", "[Äußerungen]"), arguments("Äußerung", "SUB:NOM:PLU:MAS", "[]"), arguments("Haus", "SUB:AKK:PLU:NEU", "[Häuser]"), arguments("Regelsystem", "SUB:NOM:PLU:NEU", "[Regelsysteme]"), arguments("Regelsystem", "SUB:DAT:PLU:NEU", "[Regelsystemen]"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSynthesize_3to6_8to14")
    public void testSynthesize_3to6_8to14(String param1, String param2, String param3) throws IOException {
        assertThat(synth(param1, param2, param3), is("[Diabetes-Zentrums]"));
    }

    static public Stream<Arguments> Provider_testSynthesize_3to6_8to14() {
        return Stream.of(arguments("Diabetes-Zentrum", "SUB:GEN:SIN.*", "[Diabetes-Zentrums]"), arguments("Diabetes-Zentrum", "SUB:GEN:PLU.*", "[Diabetes-Zentren]"), arguments("Xxxxxx-Zentrum", "SUB:GEN:PLU.*", "[Xxxxxx-Zentren]"), arguments("Xxxxxx-zentrum", "SUB:GEN:PLU.*", "[Xxxxxx-zentren]"), arguments("gelb-grün", "ADJ.*", "[]"), arguments("WLAN-LAN-Kabel", "SUB:GEN:SIN.*", "[WLAN-LAN-Kabels]"), arguments("Haus", ".*", "[Häuser, Haus, Häusern, Haus, Hause, Häuser, Hauses, Häuser, Haus]"), arguments("Regelsystem", ".*:PLU:.*", "[Regelsysteme, Regelsystemen]"), arguments("Regel-System", ".*:PLU:.*", "[Regelsysteme, Regelsystemen]"), arguments("Kühlschrankversuch", ".*:PLU:.*", "[Kühlschrankversuche, Kühlschrankversuchen]"), arguments("Kühlschrank-Versuch", ".*:PLU:.*", "[Kühlschrankversuche, Kühlschrankversuchen]"));
    }
}
