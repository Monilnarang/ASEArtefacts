package net.sf.marineapi.nmea.parser;

import net.sf.marineapi.nmea.sentence.AISSentence;
import net.sf.marineapi.nmea.sentence.TalkerId;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class VDOTest_Purified {

    public static final String EXAMPLE = "!AIVDO,1,1,,B,H1c2;qA@PU>0U>060<h5=>0:1Dp,2*7D";

    public static final String PART1 = "!AIVDO,2,1,5,B,E1c2;q@b44ah4ah0h:2ab@70VRpU<Bgpm4:gP50HH`Th`QF5,0*7B";

    public static final String PART2 = "!AIVDO,2,2,5,B,1CQ1A83PCAH0,0*60";

    private AISSentence vdo;

    private AISSentence frag1;

    private AISSentence frag2;

    @Before
    public void setUp() throws Exception {
        vdo = new VDOParser(EXAMPLE);
        frag1 = new VDOParser(PART1);
        frag2 = new VDOParser(PART2);
    }

    @Test
    public void testGetNumberOfFragments_1() {
        assertEquals(1, vdo.getNumberOfFragments());
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
        assertEquals(1, vdo.getFragmentNumber());
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
        assertEquals("5", frag1.getMessageId());
    }

    @Test
    public void testGetMessageId_2() {
        assertEquals("5", frag2.getMessageId());
    }

    @Test
    public void testGetRadioChannel_1() {
        assertEquals("B", vdo.getRadioChannel());
    }

    @Test
    public void testGetRadioChannel_2() {
        assertEquals("B", frag1.getRadioChannel());
    }

    @Test
    public void testGetRadioChannel_3() {
        assertEquals("B", frag2.getRadioChannel());
    }

    @Test
    public void testGetPayload_1() {
        final String pl = "H1c2;qA@PU>0U>060<h5=>0:1Dp";
        assertEquals(pl, vdo.getPayload());
    }

    @Test
    public void testGetPayload_2() {
        final String f1 = "E1c2;q@b44ah4ah0h:2ab@70VRpU<Bgpm4:gP50HH`Th`QF5";
        assertEquals(f1, frag1.getPayload());
    }

    @Test
    public void testGetPayload_3() {
        final String f2 = "1CQ1A83PCAH0";
        assertEquals(f2, frag2.getPayload());
    }

    @Test
    public void testGetFillBits_1() {
        assertEquals(2, vdo.getFillBits());
    }

    @Test
    public void testGetFillBits_2() {
        assertEquals(0, frag1.getFillBits());
    }

    @Test
    public void testGetFillBits_3() {
        assertEquals(0, frag2.getFillBits());
    }

    @Test
    public void testIsFragmented_1() {
        assertFalse(vdo.isFragmented());
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
        assertTrue(vdo.isFirstFragment());
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
        assertTrue(vdo.isLastFragment());
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
        assertFalse(vdo.isPartOfMessage(frag1));
    }

    @Test
    public void testIsPartOfMessage_2() {
        assertFalse(vdo.isPartOfMessage(frag2));
    }

    @Test
    public void testIsPartOfMessage_3() {
        assertFalse(frag1.isPartOfMessage(vdo));
    }

    @Test
    public void testIsPartOfMessage_4() {
        assertFalse(frag2.isPartOfMessage(vdo));
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
        AISSentence example = new VDOParser(EXAMPLE);
        assertEquals(EXAMPLE, example.toString());
    }

    @Test
    public void testToStringWithAIS_2() {
        AISSentence empty = new VDOParser(TalkerId.AI);
        assertEquals("!AIVDO,,,,,,*55", empty.toString());
    }
}
