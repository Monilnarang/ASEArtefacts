package net.sf.marineapi.nmea.parser;

import net.sf.marineapi.nmea.sentence.GNSSentence;
import net.sf.marineapi.nmea.sentence.GNSSentence.Mode;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.CompassPoint;
import net.sf.marineapi.nmea.util.Position;
import net.sf.marineapi.nmea.util.Time;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GNSTest_Purified {

    public static final String EXAMPLE = "$GNGNS,014035.00,4332.69262,S,17235.48549,E,RR,13,0.9,25.63,11.24,,*70";

    GNSSentence gns;

    GNSSentence empty;

    @Before
    public void setUp() throws Exception {
        gns = new GNSParser(EXAMPLE);
        assertEquals(TalkerId.GN, gns.getTalkerId());
        assertEquals(SentenceId.GNS.name(), gns.getSentenceId());
        assertEquals(12, gns.getFieldCount());
        empty = new GNSParser(TalkerId.GP);
        assertEquals(TalkerId.GP, empty.getTalkerId());
        assertEquals(SentenceId.GNS.name(), empty.getSentenceId());
        assertEquals(12, empty.getFieldCount());
    }

    @Test
    public void getGpsMode_1() throws Exception {
        assertEquals(Mode.RTK, gns.getGpsMode());
    }

    @Test
    public void getGpsMode_2() throws Exception {
        assertEquals(Mode.NONE, empty.getGpsMode());
    }

    @Test
    public void getGlonassMode_1() throws Exception {
        assertEquals(Mode.RTK, gns.getGlonassMode());
    }

    @Test
    public void getGlonassMode_2() throws Exception {
        assertEquals(Mode.NONE, empty.getGlonassMode());
    }
}
