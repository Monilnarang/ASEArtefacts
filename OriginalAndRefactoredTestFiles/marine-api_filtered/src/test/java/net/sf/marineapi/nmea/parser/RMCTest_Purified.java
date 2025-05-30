package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.CompassPoint;
import net.sf.marineapi.nmea.util.DataStatus;
import net.sf.marineapi.nmea.util.Date;
import net.sf.marineapi.nmea.util.FaaMode;
import net.sf.marineapi.nmea.util.Position;
import net.sf.marineapi.nmea.util.Time;
import net.sf.marineapi.nmea.util.NavStatus;
import org.junit.Before;
import org.junit.Test;

public class RMCTest_Purified {

    public static final String EXAMPLE = "$GPRMC,120044.567,A,6011.552,N,02501.941,E,000.0,360.0,160705,006.1,E,A,S*74";

    public static final String EXAMPLE_LEGACY = "$GPRMC,183729,A,3907.356,N,12102.482,W,000.0,360.0,080301,015.5,E,S*10";

    RMCParser empty;

    RMCParser rmc;

    RMCParser legacy;

    @Before
    public void setUp() {
        try {
            empty = new RMCParser(TalkerId.GP);
            rmc = new RMCParser(EXAMPLE);
            legacy = new RMCParser(EXAMPLE_LEGACY);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testConstructor_1() {
        assertEquals(13, empty.getFieldCount());
    }

    @Test
    public void testConstructor_2() {
        assertEquals(12, legacy.getFieldCount());
    }

    @Test
    public void testGetDirectionOfVariation_1() {
        assertTrue(rmc.getVariation() < 0);
    }

    @Test
    public void testGetDirectionOfVariation_2() {
        assertEquals(CompassPoint.EAST, rmc.getDirectionOfVariation());
    }
}
