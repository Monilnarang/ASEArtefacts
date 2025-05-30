package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import net.sf.marineapi.nmea.sentence.Checksum;
import org.junit.Test;

public class ChecksumTest_Purified {

    @Test
    public void testAddAIS_1() {
        assertEquals("!AIVDM,2,1,0,A,58wt8Ui`g??r21`7S=:22058<v05Htp000000015>8OA;0sk,0*7B", Checksum.add("!AIVDM,2,1,0,A,58wt8Ui`g??r21`7S=:22058<v05Htp000000015>8OA;0sk,0"));
    }

    @Test
    public void testAddAIS_2() {
        assertEquals("!AIVDM,2,2,0,A,eQ8823mDm3kP00000000000,2*5D", Checksum.add("!AIVDM,2,2,0,A,eQ8823mDm3kP00000000000,2"));
    }

    @Test
    public void testAddAIS_3() {
        assertEquals("!AIVDM,2,1,9,B,61c2;qLPH1m@wsm6ARhp<ji6ATHd<C8f=Bhk>34k;S8i=3To,0*2C", Checksum.add("!AIVDM,2,1,9,B,61c2;qLPH1m@wsm6ARhp<ji6ATHd<C8f=Bhk>34k;S8i=3To,0"));
    }

    @Test
    public void testAddAIS_4() {
        assertEquals("!AIVDM,2,2,9,B,Djhi=3Di<2pp=34k>4D,2*03", Checksum.add("!AIVDM,2,2,9,B,Djhi=3Di<2pp=34k>4D,2"));
    }

    @Test
    public void testAddAIS_5() {
        assertEquals("!AIVDM,1,1,,B,;8u:8CAuiT7Bm2CIM=fsDJ100000,0*51", Checksum.add("!AIVDM,1,1,,B,;8u:8CAuiT7Bm2CIM=fsDJ100000,0"));
    }

    @Test
    public void testAddAIS_6() {
        assertEquals("!AIVDM,1,1,,A,B6CdCm0t3`tba35f@V9faHi7kP06,0*58", Checksum.add("!AIVDM,1,1,,A,B6CdCm0t3`tba35f@V9faHi7kP06,0"));
    }

    @Test
    public void testCalculate_1() {
        assertEquals("1D", Checksum.calculate(BODTest.EXAMPLE));
    }

    @Test
    public void testCalculate_2() {
        assertEquals("63", Checksum.calculate(GGATest.EXAMPLE));
    }

    @Test
    public void testCalculate_3() {
        assertEquals("26", Checksum.calculate(GLLTest.EXAMPLE));
    }

    @Test
    public void testCalculate_4() {
        assertEquals("74", Checksum.calculate(RMCTest.EXAMPLE));
    }

    @Test
    public void testCalculate_5() {
        assertEquals("3D", Checksum.calculate(GSATest.EXAMPLE));
    }

    @Test
    public void testCalculate_6() {
        assertEquals("73", Checksum.calculate(GSVTest.EXAMPLE));
    }

    @Test
    public void testCalculate_7() {
        assertEquals("58", Checksum.calculate(RMBTest.EXAMPLE));
    }

    @Test
    public void testCalculate_8() {
        assertEquals("25", Checksum.calculate(RTETest.EXAMPLE));
    }

    @Test
    public void testDelimiterIndex_1() {
        assertEquals(13, Checksum.index("$GPGGA,,,,,,,"));
    }

    @Test
    public void testDelimiterIndex_2() {
        assertEquals(13, Checksum.index("$GPGGA,,,,,,,*00"));
    }

    @Test
    public void testCalculateAIS_1() {
        assertEquals("7B", Checksum.calculate("!AIVDM,2,1,0,A,58wt8Ui`g??r21`7S=:22058<v05Htp000000015>8OA;0sk,0"));
    }

    @Test
    public void testCalculateAIS_2() {
        assertEquals("7B", Checksum.calculate("!AIVDM,2,1,0,A,58wt8Ui`g??r21`7S=:22058<v05Htp000000015>8OA;0sk,0*7B"));
    }

    @Test
    public void testCalculateAIS_3() {
        assertEquals("5D", Checksum.calculate("!AIVDM,2,2,0,A,eQ8823mDm3kP00000000000,2"));
    }

    @Test
    public void testCalculateAIS_4() {
        assertEquals("5D", Checksum.calculate("!AIVDM,2,2,0,A,eQ8823mDm3kP00000000000,2*5D"));
    }

    @Test
    public void testCalculateAIS_5() {
        assertEquals("2C", Checksum.calculate("!AIVDM,2,1,9,B,61c2;qLPH1m@wsm6ARhp<ji6ATHd<C8f=Bhk>34k;S8i=3To,0"));
    }

    @Test
    public void testCalculateAIS_6() {
        assertEquals("2C", Checksum.calculate("!AIVDM,2,1,9,B,61c2;qLPH1m@wsm6ARhp<ji6ATHd<C8f=Bhk>34k;S8i=3To,0*2C"));
    }

    @Test
    public void testCalculateAIS_7() {
        assertEquals("03", Checksum.calculate("!AIVDM,2,2,9,B,Djhi=3Di<2pp=34k>4D,2"));
    }

    @Test
    public void testCalculateAIS_8() {
        assertEquals("03", Checksum.calculate("!AIVDM,2,2,9,B,Djhi=3Di<2pp=34k>4D,2*03"));
    }

    @Test
    public void testCalculateAIS_9() {
        assertEquals("51", Checksum.calculate("!AIVDM,1,1,,B,;8u:8CAuiT7Bm2CIM=fsDJ100000,0"));
    }

    @Test
    public void testCalculateAIS_10() {
        assertEquals("51", Checksum.calculate("!AIVDM,1,1,,B,;8u:8CAuiT7Bm2CIM=fsDJ100000,0*51"));
    }

    @Test
    public void testCalculateAIS_11() {
        assertEquals("58", Checksum.calculate("!AIVDM,1,1,,A,B6CdCm0t3`tba35f@V9faHi7kP06,0"));
    }

    @Test
    public void testCalculateAIS_12() {
        assertEquals("58", Checksum.calculate("!AIVDM,1,1,,A,B6CdCm0t3`tba35f@V9faHi7kP06,0*58"));
    }

    @Test
    public void testCalculateIssue134_1() {
        assertEquals("3F", Checksum.calculate("!AIVDM,1,1,,A,133sfv002POVWD0Je4sMA:a@00S?,0"));
    }

    @Test
    public void testCalculateIssue134_2() {
        assertEquals("3F", Checksum.calculate("!AIVDM,1,1,,A,133sfv002POVWD0Je4sMA:a@00S?,0*3F"));
    }

    @Test
    public void testCalculateIssue134_3() {
        assertEquals("4F", Checksum.calculate("!AIVDM,1,1,,A,13IWB67P00wdI9pKF>atlww>2@G4,0"));
    }

    @Test
    public void testCalculateIssue134_4() {
        assertEquals("4F", Checksum.calculate("!AIVDM,1,1,,A,13IWB67P00wdI9pKF>atlww>2@G4,0*4F"));
    }

    @Test
    public void testCalculateIssue134_5() {
        assertEquals("6D", Checksum.calculate("!AIVDM,1,1,,B,1CI7wSg000woG1rK3DMh0q3D00RV,0"));
    }

    @Test
    public void testCalculateIssue134_6() {
        assertEquals("6D", Checksum.calculate("!AIVDM,1,1,,B,1CI7wSg000woG1rK3DMh0q3D00RV,0*6D"));
    }

    @Test
    public void testAddIssue134_1() {
        assertEquals("!AIVDM,1,1,,A,133sfv002POVWD0Je4sMA:a@00S?,0*3F", Checksum.add("!AIVDM,1,1,,A,133sfv002POVWD0Je4sMA:a@00S?,0"));
    }

    @Test
    public void testAddIssue134_2() {
        assertEquals("!AIVDM,1,1,,A,13IWB67P00wdI9pKF>atlww>2@G4,0*4F", Checksum.add("!AIVDM,1,1,,A,13IWB67P00wdI9pKF>atlww>2@G4,0"));
    }

    @Test
    public void testAddIssue134_3() {
        assertEquals("!AIVDM,1,1,,B,1CI7wSg000woG1rK3DMh0q3D00RV,0*6D", Checksum.add("!AIVDM,1,1,,B,1CI7wSg000woG1rK3DMh0q3D00RV,0"));
    }
}
