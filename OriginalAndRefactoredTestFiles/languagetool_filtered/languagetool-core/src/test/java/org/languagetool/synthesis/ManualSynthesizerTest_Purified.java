package org.languagetool.synthesis;

import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ManualSynthesizerTest_Purified {

    private ManualSynthesizer synthesizer;

    @Before
    public void setUp() throws Exception {
        String data = "# some test data\n" + "InflectedForm11\tLemma1\tPOS1\n" + "InflectedForm121\tLemma1\tPOS2\n" + "InflectedForm122\tLemma1\tPOS2\n" + "InflectedForm2\tLemma2\tPOS1\n";
        synthesizer = new ManualSynthesizer(new ByteArrayInputStream(data.getBytes("UTF-8")));
    }

    @Test
    public void testLookupNonExisting_1() throws IOException {
        assertNull(synthesizer.lookup("", ""));
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

    @Test
    public void testLookupNonExisting_5() throws IOException {
        assertNull(synthesizer.lookup("NONE", "UNKNOWN"));
    }

    @Test
    public void testInvalidLookup_1() throws IOException {
        assertNull(synthesizer.lookup("NONE", "POS1"));
    }

    @Test
    public void testInvalidLookup_2() throws IOException {
        assertNull(synthesizer.lookup("Lemma1", "UNKNOWN"));
    }

    @Test
    public void testInvalidLookup_3() throws IOException {
        assertNull(synthesizer.lookup("Lemma1", "POS."));
    }

    @Test
    public void testInvalidLookup_4() throws IOException {
        assertNull(synthesizer.lookup("Lemma2", "POS2"));
    }

    @Test
    public void testValidLookup_1() throws IOException {
        assertEquals("[InflectedForm11]", String.valueOf(synthesizer.lookup("Lemma1", "POS1")));
    }

    @Test
    public void testValidLookup_2() throws IOException {
        assertEquals("[InflectedForm121, InflectedForm122]", String.valueOf(synthesizer.lookup("Lemma1", "POS2")));
    }

    @Test
    public void testValidLookup_3() throws IOException {
        assertEquals("[InflectedForm2]", String.valueOf(synthesizer.lookup("Lemma2", "POS1")));
    }
}
