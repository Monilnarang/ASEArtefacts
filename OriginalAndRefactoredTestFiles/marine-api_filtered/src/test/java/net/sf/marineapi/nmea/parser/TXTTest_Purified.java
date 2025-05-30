package net.sf.marineapi.nmea.parser;

import net.sf.marineapi.nmea.sentence.TalkerId;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TXTTest_Purified {

    public static final String EXAMPLE = "$GPTXT,01,01,TARG1,Message*35";

    private TXTParser txt;

    private TXTParser empty;

    @Before
    public void setUp() {
        try {
            txt = new TXTParser(EXAMPLE);
            empty = new TXTParser(TalkerId.II);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testStringConstructor_1() {
        assertEquals(TalkerId.GP, txt.getTalkerId());
    }

    @Test
    public void testStringConstructor_2() {
        assertEquals("TXT", txt.getSentenceId());
    }

    @Test
    public void testStringConstructor_3() {
        assertEquals(4, txt.getFieldCount());
    }

    @Test
    public void testTalkerIdConstructor_1() {
        assertEquals(TalkerId.II, empty.getTalkerId());
    }

    @Test
    public void testTalkerIdConstructor_2() {
        assertEquals("TXT", empty.getSentenceId());
    }

    @Test
    public void testTalkerIdConstructor_3() {
        assertEquals(4, empty.getFieldCount());
    }
}
