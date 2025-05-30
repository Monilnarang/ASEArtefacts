package com.graphhopper.reader.dem;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SRTMProviderTest_Parameterized {

    private double precision = .1;

    SRTMProvider instance;

    @BeforeEach
    public void setUp() {
        instance = new SRTMProvider();
    }

    @AfterEach
    public void tearDown() {
        instance.release();
    }

    @Test
    public void testGetFileString_1() {
        assertEquals("Eurasia/N49E011", instance.getFileName(49, 11));
    }

    @Test
    public void testGetFileString_2() {
        assertEquals("Eurasia/N52W002", instance.getFileName(52.268157, -1.230469));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetFileString_3to4")
    public void testGetFileString_3to4(String param1, double param2, double param3) {
        assertEquals(param1, instance.getFileName(-param3, param2));
    }

    static public Stream<Arguments> Provider_testGetFileString_3to4() {
        return Stream.of(arguments("Africa/S06E034", 34.804687, 5.965754), arguments("Australia/S29E131", 131.484375, 28.304381));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetFileString_5to9")
    public void testGetFileString_5to9(String param1, int param2, int param3) {
        assertEquals(param1, instance.getFileName(-param2, -param3));
    }

    static public Stream<Arguments> Provider_testGetFileString_5to9() {
        return Stream.of(arguments("South_America/S09W045", 9, 45), arguments("South_America/S10W046", 9.1, 45.1), arguments("South_America/S10W045", 9.6, 45), arguments("South_America/S28W071", 28, 71), arguments("South_America/S29W072", 28.88316, 71.070557));
    }
}
