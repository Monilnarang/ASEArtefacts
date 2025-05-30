package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import net.sf.marineapi.nmea.sentence.MTWSentence;
import net.sf.marineapi.nmea.sentence.TalkerId;
import org.junit.Before;
import org.junit.Test;

public class MTWTest_Purified {

    public static final String EXAMPLE = "$IIMTW,17.75,C";

    private MTWSentence mtw;

    @Before
    public void setUp() throws Exception {
        mtw = new MTWParser(EXAMPLE);
    }

    @Test
    public void testMTWParserString_1() {
        assertEquals("MTW", mtw.getSentenceId());
    }

    @Test
    public void testMTWParserString_2() {
        assertEquals(TalkerId.II, mtw.getTalkerId());
    }
}
