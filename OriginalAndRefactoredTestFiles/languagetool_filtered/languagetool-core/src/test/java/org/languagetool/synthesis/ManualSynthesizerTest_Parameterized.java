package org.languagetool.synthesis;

import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ManualSynthesizerTest_Parameterized {

    private ManualSynthesizer synthesizer;

    @Before
    public void setUp() throws Exception {
        String data = "# some test data\n" + "InflectedForm11\tLemma1\tPOS1\n" + "InflectedForm121\tLemma1\tPOS2\n" + "InflectedForm122\tLemma1\tPOS2\n" + "InflectedForm2\tLemma2\tPOS1\n";
        synthesizer = new ManualSynthesizer(new ByteArrayInputStream(data.getBytes("UTF-8")));
    }

    @Test
    public void testLookupNonExisting_2() throws IOException {
        assertNull(synthesizer.lookup("", null));
    }

    @Test
    public void testLookupNonExisting_3() throws IOException {
        assertNull(synthesizer.lookup(null, ""));
    }

    @Test
    public void testLookupNonExisting_4() throws IOException {
        assertNull(synthesizer.lookup(null, null));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLookupNonExisting_1_1to5")
    public void testLookupNonExisting_1_1to5(String param1, String param2) throws IOException {
        assertNull(synthesizer.lookup(param1, param2));
    }

    static public Stream<Arguments> Provider_testLookupNonExisting_1_1to5() {
        return Stream.of(arguments("", ""), arguments("NONE", "UNKNOWN"), arguments("NONE", "POS1"), arguments("Lemma1", "UNKNOWN"), arguments("Lemma1", "POS."), arguments("Lemma2", "POS2"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testValidLookup_1to3")
    public void testValidLookup_1to3(String param1, String param2, String param3) throws IOException {
        assertEquals(param1, String.valueOf(synthesizer.lookup(param2, param3)));
    }

    static public Stream<Arguments> Provider_testValidLookup_1to3() {
        return Stream.of(arguments("[InflectedForm11]", "Lemma1", "POS1"), arguments("[InflectedForm121, InflectedForm122]", "Lemma1", "POS2"), arguments("[InflectedForm2]", "Lemma2", "POS1"));
    }
}
