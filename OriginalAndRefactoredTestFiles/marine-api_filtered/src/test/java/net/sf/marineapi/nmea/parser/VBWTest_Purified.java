package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.sentence.VBWSentence;
import net.sf.marineapi.nmea.util.DataStatus;
import org.junit.Before;
import org.junit.Test;

public class VBWTest_Purified {

    public static final String EXAMPLE = "$IIVBW,11.0,02.0,A,07.5,13.3,A,06.65,A,12.3,A";

    private VBWSentence vbw;

    private VBWSentence empty;

    @Before
    public void setUp() throws Exception {
        vbw = new VBWParser(EXAMPLE);
        empty = new VBWParser(TalkerId.II);
    }

    @Test
    public void testVBWParserString_1() {
        assertEquals(TalkerId.II, vbw.getTalkerId());
    }

    @Test
    public void testVBWParserString_2() {
        assertEquals(SentenceId.VBW.name(), vbw.getSentenceId());
    }

    @Test
    public void testVBWParserString_3() {
        assertEquals(10, vbw.getFieldCount());
    }

    @Test
    public void testVBWParserTalkerId_1() {
        assertEquals(TalkerId.II, empty.getTalkerId());
    }

    @Test
    public void testVBWParserTalkerId_2() {
        assertEquals(SentenceId.VBW.name(), empty.getSentenceId());
    }

    @Test
    public void testVBWParserTalkerId_3() {
        assertEquals(10, empty.getFieldCount());
    }

    @Test
    public void testVBWParserTalkerId_4() {
        assertTrue(empty.toString().startsWith("$IIVBW,"));
    }
}
