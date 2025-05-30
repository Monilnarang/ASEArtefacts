package com.graphhopper.reader.osm;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class OSMReaderUtilityTest_Parameterized {

    private void assertParsDurationError(String value) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> OSMReaderUtility.parseDuration(value));
        assertEquals("Cannot parse duration tag value: " + value, e.getMessage());
    }

    @Test
    public void testParseDuration_5() {
        assertEquals(0, OSMReaderUtility.parseDuration(null));
    }

    @Test
    public void testParseDuration_9() {
        assertEquals(31 + 31, OSMReaderUtility.parseDuration("P2M") / (24 * 60 * 60));
    }

    @ParameterizedTest
    @MethodSource("Provider_testParseDuration_1to3_7_10")
    public void testParseDuration_1to3_7_10(int param1, int param2, String param3) {
        assertEquals(param1 * param2, OSMReaderUtility.parseDuration(param3));
    }

    static public Stream<Arguments> Provider_testParseDuration_1to3_7_10() {
        return Stream.of(arguments(10, 60, "00:10"), arguments(35, 60, 35), arguments(70, 60, "01:10"), arguments(20, 60, "0:20:00"), arguments(2, 60, "PT2M"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testParseDuration_4_6")
    public void testParseDuration_4_6(int param1, String param2, int param3, int param4) {
        assertEquals(param3 * param4 + param1, OSMReaderUtility.parseDuration(param2));
    }

    static public Stream<Arguments> Provider_testParseDuration_4_6() {
        return Stream.of(arguments(2, "01:10:02", 70, 60), arguments(60, "20:00", 60, 20));
    }

    @ParameterizedTest
    @MethodSource("Provider_testParseDuration_8_11")
    public void testParseDuration_8_11(int param1, String param2, int param3, int param4, int param5, int param6) {
        assertEquals((param5 * param6 + param4) * param3 + param1, OSMReaderUtility.parseDuration(param2));
    }

    static public Stream<Arguments> Provider_testParseDuration_8_11() {
        return Stream.of(arguments(2, "02:20:02", 60, 20, 60, 2), arguments(36, "PT5H12M36S", 60, 12, 5, 60));
    }

    @ParameterizedTest
    @MethodSource("Provider_testWrongDurationFormats_1to3")
    public void testWrongDurationFormats_1to3(String param1) {
        assertParsDurationError(param1);
    }

    static public Stream<Arguments> Provider_testWrongDurationFormats_1to3() {
        return Stream.of(arguments("PT5h12m36s"), arguments("oh"), arguments("01:10:2"));
    }
}
