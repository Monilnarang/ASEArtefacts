package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import net.sf.marineapi.nmea.sentence.MTASentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import org.junit.Before;
import org.junit.Test;

public class MTATest_Purified {

    public static final String EXAMPLE = "$IIMTA,21.5,C";

    private MTASentence mta;

    @Before
    public void setUp() throws Exception {
        mta = new MTAParser(EXAMPLE);
    }

    @Test
    public void testMTAParserString_1() {
        assertEquals(TalkerId.II, mta.getTalkerId());
    }

    @Test
    public void testMTAParserString_2() {
        assertEquals(SentenceId.MTA.name(), mta.getSentenceId());
    }
}
