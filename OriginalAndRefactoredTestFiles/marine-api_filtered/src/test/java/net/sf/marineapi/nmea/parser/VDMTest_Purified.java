package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.nmea.sentence.AISSentence;
import org.junit.Before;
import org.junit.Test;

public class VDMTest_Purified {

    public static final String EXAMPLE = "!AIVDM,1,1,,A,403OviQuMGCqWrRO9>E6fE700@GO,0*4D";

    public static final String PART1 = "!AIVDM,2,1,1,A,55?MbV02;H;s<HtKR20EHE:0@T4@Dn2222222216L961O5Gf0NSQEp6ClRp8,0*1C";

    public static final String PART2 = "!AIVDM,2,2,1,A,88888888880,2*25";

    private AISSentence vdm;

    private AISSentence frag1;

    private AISSentence frag2;

    @Before
    public void setUp() throws Exception {
        vdm = new VDMParser(EXAMPLE);
        frag1 = new VDMParser(PART1);
        frag2 = new VDMParser(PART2);
    }

    @Test
    public void testGetNumberOfFragments_1() {
        assertEquals(1, vdm.getNumberOfFragments());
    }

    @Test
    public void testGetNumberOfFragments_2() {
        assertEquals(2, frag1.getNumberOfFragments());
    }

    @Test
    public void testGetNumberOfFragments_3() {
        assertEquals(2, frag2.getNumberOfFragments());
    }

    @Test
    public void testGetFragmentNumber_1() {
        assertEquals(1, vdm.getFragmentNumber());
    }

    @Test
    public void testGetFragmentNumber_2() {
        assertEquals(1, frag1.getFragmentNumber());
    }

    @Test
    public void testGetFragmentNumber_3() {
        assertEquals(2, frag2.getFragmentNumber());
    }

    @Test
    public void testGetMessageId_1() {
        assertEquals("1", frag1.getMessageId());
    }

    @Test
    public void testGetMessageId_2() {
        assertEquals("1", frag2.getMessageId());
    }

    @Test
    public void testGetRadioChannel_1() {
        assertEquals("A", vdm.getRadioChannel());
    }

    @Test
    public void testGetRadioChannel_2() {
        assertEquals("A", frag1.getRadioChannel());
    }

    @Test
    public void testGetRadioChannel_3() {
        assertEquals("A", frag2.getRadioChannel());
    }

    @Test
    public void testGetPayload_1() {
        assertEquals("403OviQuMGCqWrRO9>E6fE700@GO", vdm.getPayload());
    }

    @Test
    public void testGetPayload_2() {
        assertEquals("88888888880", frag2.getPayload());
    }

    @Test
    public void testGetFillBits_1() {
        assertEquals(0, vdm.getFillBits());
    }

    @Test
    public void testGetFillBits_2() {
        assertEquals(0, frag1.getFillBits());
    }

    @Test
    public void testGetFillBits_3() {
        assertEquals(2, frag2.getFillBits());
    }

    @Test
    public void testIsFragmented_1() {
        assertFalse(vdm.isFragmented());
    }

    @Test
    public void testIsFragmented_2() {
        assertTrue(frag1.isFragmented());
    }

    @Test
    public void testIsFragmented_3() {
        assertTrue(frag2.isFragmented());
    }

    @Test
    public void testIsFirstFragment_1() {
        assertTrue(vdm.isFirstFragment());
    }

    @Test
    public void testIsFirstFragment_2() {
        assertTrue(frag1.isFirstFragment());
    }

    @Test
    public void testIsFirstFragment_3() {
        assertFalse(frag2.isFirstFragment());
    }

    @Test
    public void testIsLastFragment_1() {
        assertTrue(vdm.isLastFragment());
    }

    @Test
    public void testIsLastFragment_2() {
        assertFalse(frag1.isLastFragment());
    }

    @Test
    public void testIsLastFragment_3() {
        assertTrue(frag2.isLastFragment());
    }

    @Test
    public void testIsPartOfMessage_1() {
        assertFalse(vdm.isPartOfMessage(frag1));
    }

    @Test
    public void testIsPartOfMessage_2() {
        assertFalse(vdm.isPartOfMessage(frag2));
    }

    @Test
    public void testIsPartOfMessage_3() {
        assertFalse(frag1.isPartOfMessage(vdm));
    }

    @Test
    public void testIsPartOfMessage_4() {
        assertFalse(frag2.isPartOfMessage(vdm));
    }

    @Test
    public void testIsPartOfMessage_5() {
        assertTrue(frag1.isPartOfMessage(frag2));
    }

    @Test
    public void testIsPartOfMessage_6() {
        assertFalse(frag2.isPartOfMessage(frag1));
    }

    @Test
    public void testToStringWithAIS_1() {
        AISSentence vdm = new VDMParser(EXAMPLE);
        assertEquals(EXAMPLE, vdm.toString());
    }

    @Test
    public void testToStringWithAIS_2() {
        AISSentence empty = new VDMParser(TalkerId.AI);
        assertEquals("!AIVDM,,,,,,*57", empty.toString());
    }
}
