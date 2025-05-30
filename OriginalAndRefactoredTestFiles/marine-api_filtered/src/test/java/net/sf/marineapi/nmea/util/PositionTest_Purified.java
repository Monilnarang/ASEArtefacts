package net.sf.marineapi.nmea.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

public class PositionTest_Purified {

    Position instance;

    @Before
    public void setUp() {
        instance = new Position(60.0, 25.0, Datum.WGS84);
    }

    @Test
    public void testSetLatitudeNorth_1() {
        assertEquals(60.0, instance.getLatitude(), 0.0000001);
    }

    @Test
    public void testSetLatitudeNorth_2_testMerged_2() {
        instance.setLatitude(90.0);
        assertEquals(90.0, instance.getLatitude(), 0.0000001);
        assertEquals(CompassPoint.NORTH, instance.getLatitudeHemisphere());
    }

    @Test
    public void testSetLatitudeSouth_1() {
        assertEquals(60.0, instance.getLatitude(), 0.0000001);
    }

    @Test
    public void testSetLatitudeSouth_2_testMerged_2() {
        instance.setLatitude(-90.0);
        assertEquals(-90.0, instance.getLatitude(), 0.0000001);
        assertEquals(CompassPoint.SOUTH, instance.getLatitudeHemisphere());
    }

    @Test
    public void testSetLongitudeEast_1() {
        assertEquals(25.0, instance.getLongitude(), 0.0000001);
    }

    @Test
    public void testSetLongitudeEast_2_testMerged_2() {
        instance.setLongitude(180.0);
        assertEquals(180, instance.getLongitude(), 0.0000001);
        assertEquals(CompassPoint.EAST, instance.getLongitudeHemisphere());
    }

    @Test
    public void testSetLongitudeWest_1() {
        assertEquals(25.0, instance.getLongitude(), 0.0000001);
    }

    @Test
    public void testSetLongitudeWest_2_testMerged_2() {
        instance.setLongitude(-180.0);
        assertEquals(-180, instance.getLongitude(), 0.0000001);
        assertEquals(CompassPoint.WEST, instance.getLongitudeHemisphere());
    }
}
