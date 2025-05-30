package net.sf.marineapi.nmea.parser;

import static org.junit.Assert.assertEquals;
import net.sf.marineapi.nmea.sentence.Checksum;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ChecksumTest_Parameterized {

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

    @ParameterizedTest
    @MethodSource("Provider_testAddAIS_1_1to2_2to3_3to6")
    public void testAddAIS_1_1to2_2to3_3to6(String param1, String param2) {
        assertEquals(param1, Checksum.add(param2));
    }

    static public Stream<Arguments> Provider_testAddAIS_1_1to2_2to3_3to6() {
        return Stream.of(arguments("!AIVDM,2,1,0,A,58wt8Ui`g??r21`7S=:22058<v05Htp000000015>8OA;0sk,0*7B", "!AIVDM,2,1,0,A,58wt8Ui`g??r21`7S=:22058<v05Htp000000015>8OA;0sk,0"), arguments("!AIVDM,2,2,0,A,eQ8823mDm3kP00000000000,2*5D", "!AIVDM,2,2,0,A,eQ8823mDm3kP00000000000,2"), arguments("!AIVDM,2,1,9,B,61c2;qLPH1m@wsm6ARhp<ji6ATHd<C8f=Bhk>34k;S8i=3To,0*2C", "!AIVDM,2,1,9,B,61c2;qLPH1m@wsm6ARhp<ji6ATHd<C8f=Bhk>34k;S8i=3To,0"), arguments("!AIVDM,2,2,9,B,Djhi=3Di<2pp=34k>4D,2*03", "!AIVDM,2,2,9,B,Djhi=3Di<2pp=34k>4D,2"), arguments("!AIVDM,1,1,,B,;8u:8CAuiT7Bm2CIM=fsDJ100000,0*51", "!AIVDM,1,1,,B,;8u:8CAuiT7Bm2CIM=fsDJ100000,0"), arguments("!AIVDM,1,1,,A,B6CdCm0t3`tba35f@V9faHi7kP06,0*58", "!AIVDM,1,1,,A,B6CdCm0t3`tba35f@V9faHi7kP06,0"), arguments("!AIVDM,1,1,,A,133sfv002POVWD0Je4sMA:a@00S?,0*3F", "!AIVDM,1,1,,A,133sfv002POVWD0Je4sMA:a@00S?,0"), arguments("!AIVDM,1,1,,A,13IWB67P00wdI9pKF>atlww>2@G4,0*4F", "!AIVDM,1,1,,A,13IWB67P00wdI9pKF>atlww>2@G4,0"), arguments("!AIVDM,1,1,,B,1CI7wSg000woG1rK3DMh0q3D00RV,0*6D", "!AIVDM,1,1,,B,1CI7wSg000woG1rK3DMh0q3D00RV,0"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testDelimiterIndex_1to2")
    public void testDelimiterIndex_1to2(int param1, String param2) {
        assertEquals(param1, Checksum.index(param2));
    }

    static public Stream<Arguments> Provider_testDelimiterIndex_1to2() {
        return Stream.of(arguments(13, "$GPGGA,,,,,,,"), arguments(13, "$GPGGA,,,,,,,*00"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCalculateAIS_1_1to2_2to3_3to4_4to5_5to6_6to12")
    public void testCalculateAIS_1_1to2_2to3_3to4_4to5_5to6_6to12(String param1, String param2) {
        assertEquals(param1, Checksum.calculate(param2));
    }

    static public Stream<Arguments> Provider_testCalculateAIS_1_1to2_2to3_3to4_4to5_5to6_6to12() {
        return Stream.of(arguments("7B", "!AIVDM,2,1,0,A,58wt8Ui`g??r21`7S=:22058<v05Htp000000015>8OA;0sk,0"), arguments("7B", "!AIVDM,2,1,0,A,58wt8Ui`g??r21`7S=:22058<v05Htp000000015>8OA;0sk,0*7B"), arguments(5D, "!AIVDM,2,2,0,A,eQ8823mDm3kP00000000000,2"), arguments(5D, "!AIVDM,2,2,0,A,eQ8823mDm3kP00000000000,2*5D"), arguments("2C", "!AIVDM,2,1,9,B,61c2;qLPH1m@wsm6ARhp<ji6ATHd<C8f=Bhk>34k;S8i=3To,0"), arguments("2C", "!AIVDM,2,1,9,B,61c2;qLPH1m@wsm6ARhp<ji6ATHd<C8f=Bhk>34k;S8i=3To,0*2C"), arguments(03, "!AIVDM,2,2,9,B,Djhi=3Di<2pp=34k>4D,2"), arguments(03, "!AIVDM,2,2,9,B,Djhi=3Di<2pp=34k>4D,2*03"), arguments(51, "!AIVDM,1,1,,B,;8u:8CAuiT7Bm2CIM=fsDJ100000,0"), arguments(51, "!AIVDM,1,1,,B,;8u:8CAuiT7Bm2CIM=fsDJ100000,0*51"), arguments(58, "!AIVDM,1,1,,A,B6CdCm0t3`tba35f@V9faHi7kP06,0"), arguments(58, "!AIVDM,1,1,,A,B6CdCm0t3`tba35f@V9faHi7kP06,0*58"), arguments(3F, "!AIVDM,1,1,,A,133sfv002POVWD0Je4sMA:a@00S?,0"), arguments(3F, "!AIVDM,1,1,,A,133sfv002POVWD0Je4sMA:a@00S?,0*3F"), arguments(4F, "!AIVDM,1,1,,A,13IWB67P00wdI9pKF>atlww>2@G4,0"), arguments(4F, "!AIVDM,1,1,,A,13IWB67P00wdI9pKF>atlww>2@G4,0*4F"), arguments(6D, "!AIVDM,1,1,,B,1CI7wSg000woG1rK3DMh0q3D00RV,0"), arguments(6D, "!AIVDM,1,1,,B,1CI7wSg000woG1rK3DMh0q3D00RV,0*6D"));
    }
}
