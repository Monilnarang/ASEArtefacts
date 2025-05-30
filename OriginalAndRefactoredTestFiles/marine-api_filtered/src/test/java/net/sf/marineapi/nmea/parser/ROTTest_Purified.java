package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.sentence.ROTSentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.util.DataStatus;
import org.junit.Before;
import org.junit.Test;

public class ROTTest_Purified {

    public static final String EXAMPLE = "$HCROT,-0.3,A";

    public static final String INVALID_EXAMPLE = "$HCROT,-0.3,V";

    ROTSentence rot;

    ROTSentence irot;

    @Before
    public void setUp() throws Exception {
        rot = new ROTParser(EXAMPLE);
        irot = new ROTParser(INVALID_EXAMPLE);
    }

    @Test
    public void testGetStatus_1() {
        assertEquals(DataStatus.ACTIVE, rot.getStatus());
    }

    @Test
    public void testGetStatus_2() {
        assertEquals(DataStatus.VOID, irot.getStatus());
    }
}
