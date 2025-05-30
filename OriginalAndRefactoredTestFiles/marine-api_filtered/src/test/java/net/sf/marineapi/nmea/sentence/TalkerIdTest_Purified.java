package net.sf.marineapi.nmea.sentence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

public class TalkerIdTest_Purified {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testParse_1() {
        assertEquals(TalkerId.GP, TalkerId.parse("$GPGLL,,,,,,,"));
    }

    @Test
    public void testParse_2() {
        assertEquals(TalkerId.GL, TalkerId.parse("$GLGSV,,,,,,,"));
    }

    @Test
    public void testParse_3() {
        assertEquals(TalkerId.GN, TalkerId.parse("$GNGSV,,,,,,,"));
    }

    @Test
    public void testParse_4() {
        assertEquals(TalkerId.II, TalkerId.parse("$IIDPT,,,,,,,"));
    }

    @Test
    public void testParseAIS_1() {
        assertEquals(TalkerId.AI, TalkerId.parse("!AIVDM,,,,,,,"));
    }

    @Test
    public void testParseAIS_2() {
        assertEquals(TalkerId.AB, TalkerId.parse("!ABVDM,,,,,,,"));
    }

    @Test
    public void testParseAIS_3() {
        assertEquals(TalkerId.BS, TalkerId.parse("!BSVDM,,,,,,,"));
    }
}
