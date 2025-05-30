package org.asteriskjava.manager.response;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.TimeZone;
import static org.junit.jupiter.api.Assertions.*;

class CoreStatusResponseTest_Purified {

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
    void testGetCoreStartupTimeAsDate_1() {
        assertNotNull(tz, "TimeZone not found");
    }

    @Test
    void testGetCoreStartupTimeAsDate_2() {
        response.setCoreStartupDate("2009-05-27");
        response.setCoreStartupTime("02:49:15");
        assertEquals("Wed May 27 02:49:15 CEST 2009", response.getCoreStartupDateTimeAsDate(tz).toString());
    }

    @Test
    void testGetCoreStartupTimeAsDateIfDateIsNull_1() {
        assertNotNull(tz, "TimeZone not found");
    }

    @Test
    void testGetCoreStartupTimeAsDateIfDateIsNull_2() {
        response.setCoreStartupDate(null);
        response.setCoreStartupTime("02:49:15");
        assertNull(response.getCoreStartupDateTimeAsDate(tz));
    }
}
