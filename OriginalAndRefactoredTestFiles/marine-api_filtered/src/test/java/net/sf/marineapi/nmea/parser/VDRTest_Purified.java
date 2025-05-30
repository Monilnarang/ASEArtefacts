package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.sentence.VDRSentence;
import org.junit.Before;
import org.junit.Test;

public class VDRTest_Purified {

    public static final String EXAMPLE = "$IIVDR,123.4,T,124.5,M,1.2,N";

    private VDRSentence vdr;

    private VDRSentence empty;

    @Before
    public void setUp() throws Exception {
        vdr = new VDRParser(EXAMPLE);
        empty = new VDRParser(TalkerId.IN);
    }

    @Test
    public void testVDRParserString_1() {
        assertEquals(TalkerId.II, vdr.getTalkerId());
    }

    @Test
    public void testVDRParserString_2() {
        assertEquals(SentenceId.VDR.name(), vdr.getSentenceId());
    }

    @Test
    public void testVDRParserString_3() {
        assertEquals(6, vdr.getFieldCount());
    }

    @Test
    public void testVDRParserTalkerId_1() {
        assertEquals(TalkerId.IN, empty.getTalkerId());
    }

    @Test
    public void testVDRParserTalkerId_2() {
        assertEquals(SentenceId.VDR.name(), empty.getSentenceId());
    }

    @Test
    public void testVDRParserTalkerId_3() {
        assertEquals(6, empty.getFieldCount());
    }

    @Test
    public void testVDRParserTalkerId_4() {
        assertTrue(empty.toString().startsWith("$INVDR,,T,,M,,N*"));
    }
}
