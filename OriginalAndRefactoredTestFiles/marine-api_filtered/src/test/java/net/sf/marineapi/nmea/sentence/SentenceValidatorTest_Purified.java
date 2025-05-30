package net.sf.marineapi.nmea.sentence;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.sf.marineapi.nmea.parser.BODTest;
import net.sf.marineapi.nmea.parser.GGATest;
import net.sf.marineapi.nmea.parser.GLLTest;
import net.sf.marineapi.nmea.parser.GSATest;
import net.sf.marineapi.nmea.parser.GSVTest;
import net.sf.marineapi.nmea.parser.RMBTest;
import net.sf.marineapi.nmea.parser.RMCTest;
import net.sf.marineapi.nmea.parser.RTETest;
import net.sf.marineapi.nmea.parser.VTGTest;
import net.sf.marineapi.nmea.parser.WPLTest;
import net.sf.marineapi.nmea.parser.ZDATest;
import org.junit.Test;

public class SentenceValidatorTest_Purified {

    @Test
    public void testIsValid_1_testMerged_1() {
        String a = "$ABCDE,1,2,3,4,5,6,7,8,9";
        assertTrue(SentenceValidator.isValid(a));
        assertTrue(SentenceValidator.isValid(Checksum.add(a)));
    }

    @Test
    public void testIsValid_3_testMerged_2() {
        String b = "$ABCDE,";
        assertTrue(SentenceValidator.isValid(b));
        assertTrue(SentenceValidator.isValid(Checksum.add(b)));
    }

    @Test
    public void testIsValid_5_testMerged_3() {
        String c = "$ABCDE,,,,,,";
        assertTrue(SentenceValidator.isValid(c));
        assertTrue(SentenceValidator.isValid(Checksum.add(c)));
    }

    @Test
    public void testIsValid_7_testMerged_4() {
        String d = "$ABCDE,1,TWO,three,FOUR?,5,6.0,-7.0,Eigth-8,N1N3,#T3n";
        assertTrue(SentenceValidator.isValid(d));
        assertTrue(SentenceValidator.isValid(Checksum.add(d)));
    }

    @Test
    public void testIsValid_9_testMerged_5() {
        String e = "!ABCDE,1,2,3,4,5,6,7,8,9";
        assertTrue(SentenceValidator.isValid(e));
        assertTrue(SentenceValidator.isValid(Checksum.add(e)));
    }

    @Test
    public void testIsValidWithInvalidInput_1() {
        assertFalse(SentenceValidator.isValid("$ABCDE,1,2,3,4,5,6,7,8,9*00"));
    }

    @Test
    public void testIsValidWithInvalidInput_2() {
        assertFalse(SentenceValidator.isValid(null));
    }

    @Test
    public void testIsValidWithInvalidInput_3() {
        assertFalse(SentenceValidator.isValid(""));
    }

    @Test
    public void testIsValidWithInvalidInput_4() {
        assertFalse(SentenceValidator.isValid("$"));
    }

    @Test
    public void testIsValidWithInvalidInput_5() {
        assertFalse(SentenceValidator.isValid("*"));
    }

    @Test
    public void testIsValidWithInvalidInput_6() {
        assertFalse(SentenceValidator.isValid("$,*"));
    }

    @Test
    public void testIsValidWithInvalidInput_7() {
        assertFalse(SentenceValidator.isValid("$GPGSV*"));
    }

    @Test
    public void testIsValidWithInvalidInput_8() {
        assertFalse(SentenceValidator.isValid("foobar"));
    }

    @Test
    public void testIsValidWithInvalidInput_9() {
        assertFalse(SentenceValidator.isValid("$gpgga,1,2,3,4,5,6,7,8,9"));
    }

    @Test
    public void testIsValidWithInvalidInput_10() {
        assertFalse(SentenceValidator.isValid("GPGGA,1,2,3,4,5,6,7,8,9"));
    }

    @Test
    public void testIsValidWithInvalidInput_11() {
        assertFalse(SentenceValidator.isValid("$GpGGA,1,2,3,4,5,6,7,8,9"));
    }

    @Test
    public void testIsValidWithInvalidInput_12() {
        assertFalse(SentenceValidator.isValid("$GPGGa,1,2,3,4,5,6,7,8,9"));
    }

    @Test
    public void testIsValidWithInvalidInput_13() {
        assertFalse(SentenceValidator.isValid("$GPGG#,1,2,3,4,5,6,7,8,9"));
    }

    @Test
    public void testIsValidWithInvalidInput_14() {
        assertFalse(SentenceValidator.isValid("$AB,1,2,3,4,5,6,7,8,9"));
    }

    @Test
    public void testIsValidWithInvalidInput_15() {
        assertFalse(SentenceValidator.isValid("$ABCDEFGHIJK,1,2,3,4,5,6,7,8,9"));
    }

    @Test
    public void testIsValidWithInvalidInput_16() {
        assertFalse(SentenceValidator.isValid("$GPGGA,1,2,3,4,5,6,7,8,9*00"));
    }

    @Test
    public void testIsValidWithValidInput_1() {
        assertTrue(SentenceValidator.isValid(BODTest.EXAMPLE));
    }

    @Test
    public void testIsValidWithValidInput_2() {
        assertTrue(SentenceValidator.isValid(GGATest.EXAMPLE));
    }

    @Test
    public void testIsValidWithValidInput_3() {
        assertTrue(SentenceValidator.isValid(GLLTest.EXAMPLE));
    }

    @Test
    public void testIsValidWithValidInput_4() {
        assertTrue(SentenceValidator.isValid(GSATest.EXAMPLE));
    }

    @Test
    public void testIsValidWithValidInput_5() {
        assertTrue(SentenceValidator.isValid(GSVTest.EXAMPLE));
    }

    @Test
    public void testIsValidWithValidInput_6() {
        assertTrue(SentenceValidator.isValid(RMBTest.EXAMPLE));
    }

    @Test
    public void testIsValidWithValidInput_7() {
        assertTrue(SentenceValidator.isValid(RMCTest.EXAMPLE));
    }

    @Test
    public void testIsValidWithValidInput_8() {
        assertTrue(SentenceValidator.isValid(RTETest.EXAMPLE));
    }

    @Test
    public void testIsValidWithValidInput_9() {
        assertTrue(SentenceValidator.isValid(VTGTest.EXAMPLE));
    }

    @Test
    public void testIsValidWithValidInput_10() {
        assertTrue(SentenceValidator.isValid(WPLTest.EXAMPLE));
    }

    @Test
    public void testIsValidWithValidInput_11() {
        assertTrue(SentenceValidator.isValid(ZDATest.EXAMPLE));
    }

    @Test
    public void testIsValidAIS_1() {
        assertTrue(SentenceValidator.isValid("!AIVDM,1,1,,A,133sfv002POVWD0Je4sMA:a@00S?,0"));
    }

    @Test
    public void testIsValidAIS_2() {
        assertTrue(SentenceValidator.isValid("!AIVDM,1,1,,A,133sfv002POVWD0Je4sMA:a@00S?,0*3F"));
    }

    @Test
    public void testIsValidAIS_3() {
        assertTrue(SentenceValidator.isValid("!AIVDM,1,1,,A,13IWB67P00wdI9pKF>atlww>2@G4,0"));
    }

    @Test
    public void testIsValidAIS_4() {
        assertTrue(SentenceValidator.isValid("!AIVDM,1,1,,A,13IWB67P00wdI9pKF>atlww>2@G4,0*4F"));
    }

    @Test
    public void testIsValidAIS_5() {
        assertTrue(SentenceValidator.isValid("!AIVDM,1,1,,B,1CI7wSg000woG1rK3DMh0q3D00RV,0"));
    }

    @Test
    public void testIsValidAIS_6() {
        assertTrue(SentenceValidator.isValid("!AIVDM,1,1,,B,1CI7wSg000woG1rK3DMh0q3D00RV,0*6D"));
    }
}
