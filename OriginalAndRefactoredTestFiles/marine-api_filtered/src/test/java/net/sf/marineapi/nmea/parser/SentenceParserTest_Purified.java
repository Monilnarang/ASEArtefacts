package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.sf.marineapi.nmea.sentence.Sentence;
import net.sf.marineapi.nmea.sentence.SentenceId;
import net.sf.marineapi.nmea.sentence.TalkerId;
import net.sf.marineapi.test.util.FOOParser;
import net.sf.marineapi.test.util.FOOSentence;
import org.junit.Before;
import org.junit.Test;

public class SentenceParserTest_Purified {

    public static final String VDO_EXAMPLE = "!AIVDO,1,1,,,13:r`R5P1orpG60JeHgRSj4l0000,0*56";

    public static final String VDM_EXAMPLE = "!AIVDM,1,1,,B,177KQJ5000G?tO`K>RA1wUbN0TKH,0*5C";

    private SentenceParser instance;

    @Before
    public void setUp() {
        instance = new SentenceParser(RMCTest.EXAMPLE);
    }

    @Test
    public void testSetBeginChar_1() {
        assertEquals(Sentence.BEGIN_CHAR, instance.getBeginChar());
    }

    @Test
    public void testSetBeginChar_2() {
        instance.setBeginChar(Sentence.ALTERNATIVE_BEGIN_CHAR);
        assertEquals(Sentence.ALTERNATIVE_BEGIN_CHAR, instance.getBeginChar());
    }

    @Test
    public void testIsValid_1() {
        assertTrue(instance.isValid());
    }

    @Test
    public void testIsValid_2() {
        instance.setStringValue(0, "\t");
        assertFalse(instance.isValid());
    }

    @Test
    public void testToString_1() {
        assertEquals(RMCTest.EXAMPLE, instance.toString());
    }

    @Test
    public void testToString_2() {
        assertEquals(instance.toString(), instance.toSentence());
    }
}
