package org.asteriskjava.manager.response;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.TimeZone;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class CoreStatusResponseTest_Parameterized {

    private TimeZone tz = TimeZone.getTimeZone("Europe/Berlin");

    private CoreStatusResponse response;

    private TimeZone defaultTimeZone;

    @BeforeEach
    void setUp() {
        this.response = new CoreStatusResponse();
        defaultTimeZone = TimeZone.getDefault();
        TimeZone.setDefault(tz);
    }

    @AfterEach
    void tearDown() {
        TimeZone.setDefault(defaultTimeZone);
    }

    @Test
    void testGetCoreStartupTimeAsDate_2() {
        response.setCoreStartupDate("2009-05-27");
        response.setCoreStartupTime("02:49:15");
        assertEquals("Wed May 27 02:49:15 CEST 2009", response.getCoreStartupDateTimeAsDate(tz).toString());
    }

    @Test
    void testGetCoreStartupTimeAsDateIfDateIsNull_2() {
        response.setCoreStartupDate(null);
        response.setCoreStartupTime("02:49:15");
        assertNull(response.getCoreStartupDateTimeAsDate(tz));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetCoreStartupTimeAsDate_1_1")
    void testGetCoreStartupTimeAsDate_1_1(String param1) {
        assertNotNull(tz, param1);
    }

    static public Stream<Arguments> Provider_testGetCoreStartupTimeAsDate_1_1() {
        return Stream.of(arguments("TimeZone not found"), arguments("TimeZone not found"));
    }
}
