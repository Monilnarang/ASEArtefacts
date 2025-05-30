package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.TimeZone;
import org.junit.jupiter.api.Test;

public class TimeZonesTest_Purified {

    static final String TIME_ZONE_GET_AVAILABLE_IDS = "java.util.TimeZone#getAvailableIDs()";

    @Test
    public void testToTimeZone_1() {
        assertEquals(TimeZone.getDefault(), TimeZones.toTimeZone(null));
    }

    @Test
    public void testToTimeZone_2() {
        assertEquals(TimeZone.getDefault(), TimeZones.toTimeZone(TimeZone.getDefault()));
    }

    @Test
    public void testToTimeZone_3() {
        assertEquals(TimeZones.GMT, TimeZones.toTimeZone(TimeZones.GMT));
    }
}
