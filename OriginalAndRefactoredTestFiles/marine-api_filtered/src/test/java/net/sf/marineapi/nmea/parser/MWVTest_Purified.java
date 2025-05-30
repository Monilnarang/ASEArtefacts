package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.sentence.MWVSentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.DataStatus;
import net.sf.marineapi.nmea.util.Units;
import org.junit.Before;
import org.junit.Test;

public class MWVTest_Purified {

    public static final String EXAMPLE = "$IIMWV,125.1,T,5.5,M,A";

    private MWVSentence mwv;

    @Before
    public void setUp() throws Exception {
        mwv = new MWVParser(EXAMPLE);
    }

    @Test
    public void testSetTrue_1() {
        assertTrue(mwv.isTrue());
    }

    @Test
    public void testSetTrue_2() {
        mwv.setTrue(false);
        assertFalse(mwv.isTrue());
    }
}
