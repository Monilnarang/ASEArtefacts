package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import net.sf.marineapi.nmea.sentence.TLBSentence;
import net.sf.marineapi.nmea.sentence.TalkerId;

public class TLBTest_Purified {

    public static final String EXAMPLE = "$RATLB,1,SHIPONE,2,SHIPTWO,3,SHIPTHREE*3D";

    private TLBSentence empty, threeTargets;

    @Before
    public void setUp() {
        empty = new TLBParser(TalkerId.RA);
        threeTargets = new TLBParser(EXAMPLE);
    }

    @Test
    public void testAddTargetLabel_1_testMerged_1() {
        empty.addTargetLabel(3, "SHIPTHREE");
        assertTrue(empty.toString().contains("3,SHIPTHREE*"));
        empty.addTargetLabel(5, "SHIPFIVE");
        assertTrue(empty.toString().contains("3,SHIPTHREE,5,SHIPFIVE*"));
        empty.addTargetLabel(99, "SHIP99");
        assertTrue(empty.toString().contains("3,SHIPTHREE,5,SHIPFIVE,99,SHIP99*"));
    }

    @Test
    public void testAddTargetLabel_4() {
        threeTargets.addTargetLabel(4, "SHIPFOUR");
        assertTrue(threeTargets.toString().contains("1,SHIPONE,2,SHIPTWO,3,SHIPTHREE,4,SHIPFOUR*"));
    }
}
