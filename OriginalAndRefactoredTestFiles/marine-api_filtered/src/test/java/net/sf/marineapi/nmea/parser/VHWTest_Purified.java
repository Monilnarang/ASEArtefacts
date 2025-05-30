package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import org.junit.Before;
import org.junit.Test;

public class VHWTest_Purified {

    public static final String EXAMPLE = "$VWVHW,000.0,T,001.5,M,1.0,N,1.85,K";

    private VHWParser vhw;

    @Before
    public void setUp() throws Exception {
        vhw = new VHWParser(EXAMPLE);
    }

    @Test
    public void testConstructorString_1() {
        assertTrue(vhw.getTalkerId() == TalkerId.VW);
    }

    @Test
    public void testConstructorString_2() {
        assertTrue(SentenceId.valueOf(vhw.getSentenceId()) == SentenceId.VHW);
    }
}
