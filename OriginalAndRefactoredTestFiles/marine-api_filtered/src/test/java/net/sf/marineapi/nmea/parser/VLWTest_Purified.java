package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.sentence.VLWSentence;
import org.junit.Before;
import org.junit.Test;

public class VLWTest_Purified {

    public static final String EXAMPLE = "$VWVLW,2.8,N,0.8,N";

    VLWSentence vlw;

    VLWSentence empty;

    @Before
    public void setUp() throws Exception {
        vlw = new VLWParser(EXAMPLE);
        empty = new VLWParser(TalkerId.VD);
    }

    @Test
    public void testVLWParserString_1() {
        assertEquals(TalkerId.VW, vlw.getTalkerId());
    }

    @Test
    public void testVLWParserString_2() {
        assertEquals("VLW", vlw.getSentenceId());
    }

    @Test
    public void testVLWParserString_3() {
        assertEquals(4, vlw.getFieldCount());
    }

    @Test
    public void testVLWParserTalkerId_1() {
        assertEquals(TalkerId.VD, empty.getTalkerId());
    }

    @Test
    public void testVLWParserTalkerId_2() {
        assertEquals("VLW", empty.getSentenceId());
    }

    @Test
    public void testVLWParserTalkerId_3() {
        assertEquals(4, empty.getFieldCount());
    }
}
