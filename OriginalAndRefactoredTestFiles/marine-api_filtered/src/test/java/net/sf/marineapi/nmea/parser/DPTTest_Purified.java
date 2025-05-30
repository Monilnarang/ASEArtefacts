package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import net.sf.marineapi.nmea.sentence.DPTSentence;
import net.sf.marineapi.nmea.sentence.TalkerId;
import org.junit.Before;
import org.junit.Test;

public class DPTTest_Purified {

    public static final String EXAMPLE = "$IIDPT,012.6,-1.0,100";

    DPTSentence empty;

    DPTSentence dpt;

    @Before
    public void setUp() throws Exception {
        empty = new DPTParser(TalkerId.II);
        dpt = new DPTParser(EXAMPLE);
    }

    @Test
    public void testDPTParser_1() {
        assertEquals(TalkerId.II, empty.getTalkerId());
    }

    @Test
    public void testDPTParser_2() {
        assertEquals("DPT", empty.getSentenceId());
    }

    @Test
    public void testDPTParser_3() {
        assertEquals(3, empty.getFieldCount());
    }

    @Test
    public void testDPTParserString_1() {
        assertEquals(TalkerId.II, dpt.getTalkerId());
    }

    @Test
    public void testDPTParserString_2() {
        assertEquals("DPT", dpt.getSentenceId());
    }

    @Test
    public void testDPTParserString_3() {
        assertEquals(3, dpt.getFieldCount());
    }
}
