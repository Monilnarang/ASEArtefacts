package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.sentence.XTESentence;
import net.sf.marineapi.nmea.util.DataStatus;
import net.sf.marineapi.nmea.util.Direction;
import net.sf.marineapi.nmea.util.FaaMode;
import org.junit.Before;
import org.junit.Test;

public class XTETest_Purified {

    public static final String EXAMPLE = "$IIXTE,A,A,5.36,R,N*67";

    private XTESentence empty;

    private XTESentence instance;

    @Before
    public void setUp() throws Exception {
        instance = new XTEParser(EXAMPLE);
        empty = new XTEParser(TalkerId.GP);
    }

    @Test
    public void testXTEParserString_1() {
        assertEquals(TalkerId.II, instance.getTalkerId());
    }

    @Test
    public void testXTEParserString_2() {
        assertEquals(SentenceId.XTE.name(), instance.getSentenceId());
    }

    @Test
    public void testXTEParserString_3() {
        assertEquals(6, instance.getFieldCount());
    }

    @Test
    public void testXTEParserString_4() {
        assertTrue(instance.isValid());
    }

    @Test
    public void testXTEParserTalkerId_1() {
        assertEquals(TalkerId.GP, empty.getTalkerId());
    }

    @Test
    public void testXTEParserTalkerId_2() {
        assertEquals(SentenceId.XTE.name(), empty.getSentenceId());
    }

    @Test
    public void testXTEParserTalkerId_3() {
        assertEquals(6, empty.getFieldCount());
    }

    @Test
    public void testXTEParserTalkerId_4() {
        assertTrue(empty.isValid());
    }
}
