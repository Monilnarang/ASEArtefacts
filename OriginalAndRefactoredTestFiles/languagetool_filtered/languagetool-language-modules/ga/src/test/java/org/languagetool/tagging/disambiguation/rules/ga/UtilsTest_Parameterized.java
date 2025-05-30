package org.languagetool.tagging.disambiguation.rules.ga;

import org.junit.Test;
import org.languagetool.tagging.ga.Retaggable;
import org.languagetool.tagging.ga.Utils;
import static org.junit.Assert.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class UtilsTest_Parameterized {

    String torrach = "ｔｏｒｒａｃｈ";

    @Test
    public void testUnEclipseChar_6() {
        assertEquals(null, Utils.unEclipseChar("carr", 'g', 'c'));
    }

    @Test
    public void testUnEclipse_10() {
        assertEquals(null, Utils.unEclipse("carr"));
    }

    @Test
    public void testUnLeniteDefiniteS_9() {
        assertEquals(null, Utils.unLeniteDefiniteS("seomra9"));
    }

    @Test
    public void testSimplify_1() {
        String boldUpper = "\uD835\uDC12\uD835\uDC04\uD835\uDC00\uD835\uDC0D\uD835\uDC00\uD835\uDC13\uD835\uDC07\uD835\uDC00\uD835\uDC08\uD835\uDC11";
        assertEquals("SEANATHAIR", Utils.simplifyMathematical(boldUpper));
    }

    @Test
    public void testSimplify_2() {
        String boldLower = "\uD835\uDC2C\uD835\uDC1E\uD835\uDC1A\uD835\uDC27\uD835\uDC1A\uD835\uDC2D\uD835\uDC21\uD835\uDC1A\uD835\uDC22\uD835\uDC2B";
        assertEquals("seanathair", Utils.simplifyMathematical(boldLower));
    }

    @Test
    public void testSimplify_3() {
        assertEquals("999", Utils.simplifyMathematical("\uD835\uDFFF\uD835\uDFFF\uD835\uDFFF", false, true));
    }

    @Test
    public void testIsAllMathsChars_3() {
        String boldUpper = "\uD835\uDC12\uD835\uDC04\uD835\uDC00\uD835\uDC0D\uD835\uDC00\uD835\uDC13\uD835\uDC07\uD835\uDC00\uD835\uDC08\uD835\uDC11";
        assertEquals(true, Utils.isAllMathsChars(boldUpper));
    }

    @Test
    public void testIsAllHalfWidthChars_1() {
        assertEquals(true, Utils.isAllHalfWidthChars(torrach));
    }

    @Test
    public void testIsAllHalfWidthChars_2() {
        assertEquals(false, Utils.isAllHalfWidthChars(torrach + "a"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testToLowerCaseIrish_1to4")
    public void testToLowerCaseIrish_1to4(String param1, String param2) {
        assertEquals(param1, Utils.toLowerCaseIrish(param2));
    }

    static public Stream<Arguments> Provider_testToLowerCaseIrish_1to4() {
        return Stream.of(arguments("test", "TEST"), arguments("test", "Test"), arguments("t-aon", "tAON"), arguments("n-aon", "nAON"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testUnLenited_1to4")
    public void testUnLenited_1to4(String param1, String param2) {
        assertEquals(param1, Utils.unLenite(param2));
    }

    static public Stream<Arguments> Provider_testUnLenited_1to4() {
        return Stream.of(arguments("Kate", "Khate"), arguments("can", "chan"), arguments("ba", "bha"), arguments("b", "bh"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testUnLenited_5to6")
    public void testUnLenited_5to6(String param1) {
        assertEquals(param1, Utils.unLenite("can"));
    }

    static public Stream<Arguments> Provider_testUnLenited_5to6() {
        return Stream.of(arguments("can"), arguments("a"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testUnEclipseChar_1to5")
    public void testUnEclipseChar_1to5(String param1, String param2, String param3, String param4) {
        assertEquals(param1, Utils.unEclipseChar(param2, param3, param4));
    }

    static public Stream<Arguments> Provider_testUnEclipseChar_1to5() {
        return Stream.of(arguments("carr", "gcarr", "g", "c"), arguments("Carr", "gCarr", "g", "c"), arguments("Carr", "G-carr", "g", "c"), arguments("Carr", "Gcarr", "g", "c"), arguments("CARR", "GCARR", "g", "c"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testUnEclipse_1to9")
    public void testUnEclipse_1to9(String param1, String param2) {
        assertEquals(param1, Utils.unEclipse(param2));
    }

    static public Stream<Arguments> Provider_testUnEclipse_1to9() {
        return Stream.of(arguments("carr", "g-carr"), arguments("doras", "n-doras"), arguments("Geata", "N-geata"), arguments("peann", "bpeann"), arguments("bean", "mbean"), arguments("Éin", "N-éin"), arguments("focal", "bhfocal"), arguments("Focal", "Bhfocal"), arguments("Focal", "Bfocal"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testUnLeniteDefiniteS_1to8")
    public void testUnLeniteDefiniteS_1to8(String param1, String param2) {
        assertEquals(param1, Utils.unLeniteDefiniteS(param2));
    }

    static public Stream<Arguments> Provider_testUnLeniteDefiniteS_1to8() {
        return Stream.of(arguments("seomra1", "t-seomra1"), arguments("seomra2", "tseomra2"), arguments("Seomra3", "tSeomra3"), arguments("Seomra4", "TSeomra4"), arguments("Seomra5", "Tseomra5"), arguments("Seomra6", "t-Seomra6"), arguments("Seomra7", "T-Seomra7"), arguments("Seomra8", "T-seomra8"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testUnPonc_1to4")
    public void testUnPonc_1to4(String param1, String param2) {
        assertEquals(param1, Utils.unPonc(param2));
    }

    static public Stream<Arguments> Provider_testUnPonc_1to4() {
        return Stream.of(arguments("chuir", "ċuir"), arguments("CHUIR", "ĊUIR"), arguments("Chuir", "Ċuir"), arguments("FÉACH", "FÉAĊ"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsAllMathsChars_1to2")
    public void testIsAllMathsChars_1to2(String param1) {
        assertEquals(param1, Utils.isAllMathsChars("foo"));
    }

    static public Stream<Arguments> Provider_testIsAllMathsChars_1to2() {
        return Stream.of(arguments("foo"), arguments("f\uD835\uDC12"));
    }
}
