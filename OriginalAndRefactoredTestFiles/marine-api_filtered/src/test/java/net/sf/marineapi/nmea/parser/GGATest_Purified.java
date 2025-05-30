package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.CompassPoint;
import net.sf.marineapi.nmea.util.Datum;
import net.sf.marineapi.nmea.util.GpsFixQuality;
import net.sf.marineapi.nmea.util.Position;
import net.sf.marineapi.nmea.util.Time;
import net.sf.marineapi.nmea.util.Units;
import org.junit.Before;
import org.junit.Test;

public class GGATest_Purified {

    public static final String EXAMPLE = "$GPGGA,120044.567,6011.552,N,02501.941,E,1,00,2.0,28.0,M,19.6,M,,*63";

    private GGAParser gga;

    private GGAParser empty;

    @Before
    public void setUp() {
        try {
            empty = new GGAParser(TalkerId.GP);
            gga = new GGAParser(EXAMPLE);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSetAltitudeUnits_1() {
        assertEquals(Units.METER, gga.getAltitudeUnits());
    }

    @Test
    public void testSetAltitudeUnits_2() {
        gga.setAltitudeUnits(Units.FEET);
        assertEquals(Units.FEET, gga.getAltitudeUnits());
    }

    @Test
    public void testSetFixQuality_1() {
        assertEquals(GpsFixQuality.NORMAL, gga.getFixQuality());
    }

    @Test
    public void testSetFixQuality_2() {
        gga.setFixQuality(GpsFixQuality.INVALID);
        assertEquals(GpsFixQuality.INVALID, gga.getFixQuality());
    }

    @Test
    public void testSetGeoidalHeightUnits_1() {
        assertEquals(Units.METER, gga.getGeoidalHeightUnits());
    }

    @Test
    public void testSetGeoidalHeightUnits_2() {
        gga.setGeoidalHeightUnits(Units.FEET);
        assertEquals(Units.FEET, gga.getGeoidalHeightUnits());
    }
}
