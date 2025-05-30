package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.sentence.RMBSentence;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.CompassPoint;
import net.sf.marineapi.nmea.util.DataStatus;
import net.sf.marineapi.nmea.util.Direction;
import net.sf.marineapi.nmea.util.Waypoint;
import org.junit.Before;
import org.junit.Test;

public class RMBTest_Purified {

    public static final String EXAMPLE = "$GPRMB,A,0.00,R,,RUSKI,5536.200,N,01436.500,E,432.3,234.9,,V*58";

    private RMBSentence empty;

    private RMBSentence rmb;

    @Before
    public void setUp() {
        try {
            empty = new RMBParser(TalkerId.GP);
            rmb = new RMBParser(EXAMPLE);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testArrivalStatus_1() {
        assertEquals(DataStatus.VOID, rmb.getArrivalStatus());
    }

    @Test
    public void testArrivalStatus_2() {
        assertFalse(rmb.hasArrived());
    }

    @Test
    public void testArrivalStatus_3_testMerged_3() {
        rmb.setArrivalStatus(DataStatus.ACTIVE);
        assertEquals(DataStatus.ACTIVE, rmb.getArrivalStatus());
        assertTrue(rmb.hasArrived());
    }

    @Test
    public void testArrivalStatus_5() {
        assertEquals(DataStatus.VOID, rmb.getArrivalStatus());
    }

    @Test
    public void testArrivalStatus_6() {
        assertFalse(rmb.hasArrived());
    }
}
