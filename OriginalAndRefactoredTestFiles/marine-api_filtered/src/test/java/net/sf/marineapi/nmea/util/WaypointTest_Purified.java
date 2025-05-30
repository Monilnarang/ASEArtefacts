package net.sf.marineapi.nmea.util;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class WaypointTest_Purified {

    private final String id1 = "FOO";

    private final String id2 = "BAR";

    private final String desc = "Description text";

    Waypoint point;

    @Before
    public void setUp() {
        point = new Waypoint(id1, 60.0, 25.0, Datum.WGS84);
    }

    @Test
    public void testDescription_1() {
        assertEquals("", point.getDescription());
    }

    @Test
    public void testDescription_2() {
        point.setDescription(desc);
        assertEquals(desc, point.getDescription());
    }

    @Test
    public void testId_1() {
        assertEquals(id1, point.getId());
    }

    @Test
    public void testId_2() {
        point.setId(id2);
        assertEquals(id2, point.getId());
    }
}
