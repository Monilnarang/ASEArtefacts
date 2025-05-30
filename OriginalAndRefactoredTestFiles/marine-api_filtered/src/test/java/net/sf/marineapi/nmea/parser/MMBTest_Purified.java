package net.sf.marineapi.nmea.parser;

import net.sf.marineapi.nmea.sentence.MMBSentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MMBTest_Purified {

    public static final String EXAMPLE = "$IIMMB,29.9870,I,1.0154,B*75";

    MMBSentence mmb;

    MMBSentence empty;

    @Before
    public void setUp() throws Exception {
        mmb = new MMBParser(EXAMPLE);
        empty = new MMBParser(TalkerId.WI);
    }

    @Test
    public void testConstructors_1() {
        assertEquals(4, mmb.getFieldCount());
    }

    @Test
    public void testConstructors_2() {
        assertEquals(4, empty.getFieldCount());
    }

    @Test
    public void testConstructors_3() {
        assertEquals(TalkerId.II, mmb.getTalkerId());
    }

    @Test
    public void testConstructors_4() {
        assertEquals(TalkerId.WI, empty.getTalkerId());
    }

    @Test
    public void testConstructors_5() {
        assertEquals(SentenceId.MMB.name(), empty.getSentenceId());
    }
}
