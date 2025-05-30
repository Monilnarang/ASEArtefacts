package net.sf.marineapi.nmea.parser;

import net.sf.marineapi.nmea.sentence.STALKSentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class STALKTest_Purified {

    public static final String EXAMPLE = "$STALK,52,A1,00,00*36";

    private STALKSentence stalk;

    private STALKSentence empty;

    @Before
    public void setUp() {
        try {
            stalk = new STALKParser(EXAMPLE);
            empty = new STALKParser(TalkerId.ST);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testConstructor_1() {
        assertEquals(4, stalk.getFieldCount());
    }

    @Test
    public void testConstructor_2() {
        assertEquals(TalkerId.ST, stalk.getTalkerId());
    }

    @Test
    public void testConstructor_3() {
        assertEquals(SentenceId.ALK.name(), stalk.getSentenceId());
    }

    @Test
    public void testConstructor_4() {
        assertEquals(2, empty.getFieldCount());
    }

    @Test
    public void testConstructor_5() {
        assertEquals(TalkerId.ST, empty.getTalkerId());
    }

    @Test
    public void testConstructor_6() {
        assertEquals(SentenceId.ALK.name(), empty.getSentenceId());
    }
}
