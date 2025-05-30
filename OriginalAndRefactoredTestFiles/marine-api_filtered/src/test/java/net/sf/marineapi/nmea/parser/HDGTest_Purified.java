package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.sentence.HDGSentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import org.junit.Before;
import org.junit.Test;

public class HDGTest_Purified {

    public static final String EXAMPLE = "$HCHDG,123.4,1.2,E,1.2,W";

    HDGSentence hdg;

    @Before
    public void setUp() throws Exception {
        hdg = new HDGParser(EXAMPLE);
    }

    @Test
    public void testHDGParserString_1() {
        assertTrue(hdg.isValid());
    }

    @Test
    public void testHDGParserString_2() {
        assertEquals(TalkerId.HC, hdg.getTalkerId());
    }

    @Test
    public void testHDGParserString_3() {
        assertEquals("HDG", hdg.getSentenceId());
    }
}
