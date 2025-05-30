package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.sentence.RPMSentence;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.DataStatus;
import org.junit.Before;
import org.junit.Test;

public class RPMTest_Purified {

    public static final String EXAMPLE = "$IIRPM,E,1,2418.2,10.5,A";

    RPMSentence rpm;

    RPMSentence empty;

    @Before
    public void setUp() throws Exception {
        rpm = new RPMParser(EXAMPLE);
        empty = new RPMParser(TalkerId.II);
    }

    @Test
    public void testRPMParserString_1() {
        assertEquals(TalkerId.II, rpm.getTalkerId());
    }

    @Test
    public void testRPMParserString_2() {
        assertEquals("RPM", rpm.getSentenceId());
    }

    @Test
    public void testRPMParserString_3() {
        assertEquals(5, rpm.getFieldCount());
    }

    @Test
    public void testRPMParserTalkerId_1() {
        assertEquals(TalkerId.II, empty.getTalkerId());
    }

    @Test
    public void testRPMParserTalkerId_2() {
        assertEquals("RPM", empty.getSentenceId());
    }

    @Test
    public void testRPMParserTalkerId_3() {
        assertEquals(5, empty.getFieldCount());
    }
}
