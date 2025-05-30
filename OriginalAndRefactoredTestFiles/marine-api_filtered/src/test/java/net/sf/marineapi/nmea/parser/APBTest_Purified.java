package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.sf.marineapi.nmea.sentence.APBSentence;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.DataStatus;
import net.sf.marineapi.nmea.util.Direction;
import org.junit.Before;
import org.junit.Test;

public class APBTest_Purified {

    public static final String EXAMPLE = "$GPAPB,A,A,0.10,R,N,V,V,011,M,DEST,011,M,011,M";

    private APBSentence apb;

    private APBSentence empty;

    @Before
    public void setUp() throws Exception {
        apb = new APBParser(EXAMPLE);
        empty = new APBParser(TalkerId.AG);
    }

    @Test
    public void testAPBParserString_1() {
        assertEquals(TalkerId.GP, apb.getTalkerId());
    }

    @Test
    public void testAPBParserString_2() {
        assertEquals("APB", apb.getSentenceId());
    }

    @Test
    public void testAPBParserString_3() {
        assertEquals(14, apb.getFieldCount());
    }

    @Test
    public void testAPBParserTalkerId_1() {
        assertEquals(TalkerId.AG, empty.getTalkerId());
    }

    @Test
    public void testAPBParserTalkerId_2() {
        assertEquals("APB", empty.getSentenceId());
    }

    @Test
    public void testAPBParserTalkerId_3() {
        assertEquals(14, empty.getFieldCount());
    }
}
