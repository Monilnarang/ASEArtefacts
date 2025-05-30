package com.graphhopper.reader.osm;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OSMReaderUtilityTest_Purified {

    private void assertParsDurationError(String value) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> OSMReaderUtility.parseDuration(value));
        assertEquals("Cannot parse duration tag value: " + value, e.getMessage());
    }

    @Test
    public void testParseDuration_1() {
        assertEquals(10 * 60, OSMReaderUtility.parseDuration("00:10"));
    }

    @Test
    public void testParseDuration_2() {
        assertEquals(35 * 60, OSMReaderUtility.parseDuration("35"));
    }

    @Test
    public void testParseDuration_3() {
        assertEquals(70 * 60, OSMReaderUtility.parseDuration("01:10"));
    }

    @Test
    public void testParseDuration_4() {
        assertEquals(70 * 60 + 2, OSMReaderUtility.parseDuration("01:10:02"));
    }

    @Test
    public void testParseDuration_5() {
        assertEquals(0, OSMReaderUtility.parseDuration(null));
    }

    @Test
    public void testParseDuration_6() {
        assertEquals(60 * 20 * 60, OSMReaderUtility.parseDuration("20:00"));
    }

    @Test
    public void testParseDuration_7() {
        assertEquals(20 * 60, OSMReaderUtility.parseDuration("0:20:00"));
    }

    @Test
    public void testParseDuration_8() {
        assertEquals((60 * 2 + 20) * 60 + 2, OSMReaderUtility.parseDuration("02:20:02"));
    }

    @Test
    public void testParseDuration_9() {
        assertEquals(31 + 31, OSMReaderUtility.parseDuration("P2M") / (24 * 60 * 60));
    }

    @Test
    public void testParseDuration_10() {
        assertEquals(2 * 60, OSMReaderUtility.parseDuration("PT2M"));
    }

    @Test
    public void testParseDuration_11() {
        assertEquals((5 * 60 + 12) * 60 + 36, OSMReaderUtility.parseDuration("PT5H12M36S"));
    }

    @Test
    public void testWrongDurationFormats_1() {
        assertParsDurationError("PT5h12m36s");
    }

    @Test
    public void testWrongDurationFormats_2() {
        assertParsDurationError("oh");
    }

    @Test
    public void testWrongDurationFormats_3() {
        assertParsDurationError("01:10:2");
    }
}
