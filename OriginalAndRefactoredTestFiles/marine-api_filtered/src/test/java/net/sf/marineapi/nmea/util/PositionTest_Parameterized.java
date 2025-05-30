package net.sf.marineapi.nmea.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class PositionTest_Parameterized {

    Position instance;

    @Before
    public void setUp() {
        instance = new Position(60.0, 25.0, Datum.WGS84);
    }

    @Test
    public void testSetLatitudeNorth_2_testMerged_2() {
        instance.setLatitude(90.0);
        assertEquals(90.0, instance.getLatitude(), 0.0000001);
        assertEquals(CompassPoint.NORTH, instance.getLatitudeHemisphere());
    }

    @Test
    public void testSetLatitudeSouth_2_testMerged_2() {
        instance.setLatitude(-90.0);
        assertEquals(-90.0, instance.getLatitude(), 0.0000001);
        assertEquals(CompassPoint.SOUTH, instance.getLatitudeHemisphere());
    }

    @Test
    public void testSetLongitudeEast_2_testMerged_2() {
        instance.setLongitude(180.0);
        assertEquals(180, instance.getLongitude(), 0.0000001);
        assertEquals(CompassPoint.EAST, instance.getLongitudeHemisphere());
    }

    @Test
    public void testSetLongitudeWest_2_testMerged_2() {
        instance.setLongitude(-180.0);
        assertEquals(-180, instance.getLongitude(), 0.0000001);
        assertEquals(CompassPoint.WEST, instance.getLongitudeHemisphere());
    }

    @ParameterizedTest
    @MethodSource("Provider_testSetLatitudeNorth_1_1")
    public void testSetLatitudeNorth_1_1(double param1, double param2) {
        assertEquals(param1, instance.getLatitude(), param2);
    }

    static public Stream<Arguments> Provider_testSetLatitudeNorth_1_1() {
        return Stream.of(arguments(60.0, 0.0000001), arguments(60.0, 0.0000001));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSetLongitudeEast_1_1")
    public void testSetLongitudeEast_1_1(double param1, double param2) {
        assertEquals(param1, instance.getLongitude(), param2);
    }

    static public Stream<Arguments> Provider_testSetLongitudeEast_1_1() {
        return Stream.of(arguments(25.0, 0.0000001), arguments(25.0, 0.0000001));
    }
}
