package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.sentence.DTMSentence;
import net.sf.marineapi.nmea.sentence.TalkerId;
import org.junit.Before;
import org.junit.Test;

public class DTMTest_Purified {

    public static final String EXAMPLE = "$GPDTM,W84,,0.000000,N,0.000000,E,0.0,W84*6F";

    private DTMSentence dtm;

    private DTMSentence empty;

    @Before
    public void setUp() throws Exception {
        dtm = new DTMParser(EXAMPLE);
        empty = new DTMParser(TalkerId.GP);
    }

    @Test
    public void testDTMParserString_1() {
        assertEquals("DTM", dtm.getSentenceId());
    }

    @Test
    public void testDTMParserString_2() {
        assertEquals(TalkerId.GP, dtm.getTalkerId());
    }

    @Test
    public void testDTMParserString_3() {
        assertEquals(8, dtm.getFieldCount());
    }

    @Test
    public void testDTMParserTalkerId_1() {
        assertEquals("DTM", empty.getSentenceId());
    }

    @Test
    public void testDTMParserTalkerId_2() {
        assertEquals(TalkerId.GP, empty.getTalkerId());
    }

    @Test
    public void testDTMParserTalkerId_3() {
        assertEquals(8, empty.getFieldCount());
    }
}
