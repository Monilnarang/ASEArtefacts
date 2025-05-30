package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import net.sf.marineapi.nmea.sentence.RSASentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.DataStatus;
import net.sf.marineapi.nmea.util.Side;
import org.junit.Before;
import org.junit.Test;

public class RSATest_Purified {

    public static final String EXAMPLE = "$IIRSA,1.2,A,2.3,V";

    RSASentence empty;

    RSASentence instance;

    @Before
    public void setUp() throws Exception {
        empty = new RSAParser(TalkerId.II);
        instance = new RSAParser(EXAMPLE);
    }

    @Test
    public void testRSAParserString_1() {
        assertEquals(TalkerId.II, instance.getTalkerId());
    }

    @Test
    public void testRSAParserString_2() {
        assertEquals(SentenceId.RSA.name(), instance.getSentenceId());
    }

    @Test
    public void testRSAParserTalkerId_1() {
        assertEquals(TalkerId.II, empty.getTalkerId());
    }

    @Test
    public void testRSAParserTalkerId_2() {
        assertEquals(SentenceId.RSA.name(), empty.getSentenceId());
    }

    @Test
    public void testRSAParserTalkerId_3() {
        assertEquals(DataStatus.VOID, empty.getStatus(Side.STARBOARD));
    }

    @Test
    public void testRSAParserTalkerId_4() {
        assertEquals(DataStatus.VOID, empty.getStatus(Side.PORT));
    }

    @Test
    public void testGetRudderAngle_1() {
        assertEquals(1.2, instance.getRudderAngle(Side.STARBOARD), 0.1);
    }

    @Test
    public void testGetRudderAngle_2() {
        assertEquals(2.3, instance.getRudderAngle(Side.PORT), 0.1);
    }

    @Test
    public void testGetStatus_1() {
        assertEquals(DataStatus.ACTIVE, instance.getStatus(Side.STARBOARD));
    }

    @Test
    public void testGetStatus_2() {
        assertEquals(DataStatus.VOID, instance.getStatus(Side.PORT));
    }
}
