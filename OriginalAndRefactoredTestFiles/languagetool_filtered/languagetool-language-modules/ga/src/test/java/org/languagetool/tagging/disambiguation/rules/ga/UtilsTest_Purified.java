package org.languagetool.tagging.disambiguation.rules.ga;

import org.junit.Test;
import org.languagetool.tagging.ga.Retaggable;
import org.languagetool.tagging.ga.Utils;
import static org.junit.Assert.*;

public class UtilsTest_Purified {

    String torrach = "ｔｏｒｒａｃｈ";

    @Test
    public void testToLowerCaseIrish_1() {
        assertEquals("test", Utils.toLowerCaseIrish("TEST"));
    }

    @Test
    public void testToLowerCaseIrish_2() {
        assertEquals("test", Utils.toLowerCaseIrish("Test"));
    }

    @Test
    public void testToLowerCaseIrish_3() {
        assertEquals("t-aon", Utils.toLowerCaseIrish("tAON"));
    }

    @Test
    public void testToLowerCaseIrish_4() {
        assertEquals("n-aon", Utils.toLowerCaseIrish("nAON"));
    }

    @Test
    public void testUnLenited_1() {
        assertEquals("Kate", Utils.unLenite("Khate"));
    }

    @Test
    public void testUnLenited_2() {
        assertEquals("can", Utils.unLenite("chan"));
    }

    @Test
    public void testUnLenited_3() {
        assertEquals("ba", Utils.unLenite("bha"));
    }

    @Test
    public void testUnLenited_4() {
        assertEquals("b", Utils.unLenite("bh"));
    }

    @Test
    public void testUnLenited_5() {
        assertEquals(null, Utils.unLenite("can"));
    }

    @Test
    public void testUnLenited_6() {
        assertEquals(null, Utils.unLenite("a"));
    }

    @Test
    public void testUnEclipseChar_1() {
        assertEquals("carr", Utils.unEclipseChar("gcarr", 'g', 'c'));
    }

    @Test
    public void testUnEclipseChar_2() {
        assertEquals("Carr", Utils.unEclipseChar("gCarr", 'g', 'c'));
    }

    @Test
    public void testUnEclipseChar_3() {
        assertEquals("Carr", Utils.unEclipseChar("G-carr", 'g', 'c'));
    }

    @Test
    public void testUnEclipseChar_4() {
        assertEquals("Carr", Utils.unEclipseChar("Gcarr", 'g', 'c'));
    }

    @Test
    public void testUnEclipseChar_5() {
        assertEquals("CARR", Utils.unEclipseChar("GCARR", 'g', 'c'));
    }

    @Test
    public void testUnEclipseChar_6() {
        assertEquals(null, Utils.unEclipseChar("carr", 'g', 'c'));
    }

    @Test
    public void testUnEclipse_1() {
        assertEquals("carr", Utils.unEclipse("g-carr"));
    }

    @Test
    public void testUnEclipse_2() {
        assertEquals("doras", Utils.unEclipse("n-doras"));
    }

    @Test
    public void testUnEclipse_3() {
        assertEquals("Geata", Utils.unEclipse("N-geata"));
    }

    @Test
    public void testUnEclipse_4() {
        assertEquals("peann", Utils.unEclipse("bpeann"));
    }

    @Test
    public void testUnEclipse_5() {
        assertEquals("bean", Utils.unEclipse("mbean"));
    }

    @Test
    public void testUnEclipse_6() {
        assertEquals("Éin", Utils.unEclipse("N-éin"));
    }

    @Test
    public void testUnEclipse_7() {
        assertEquals("focal", Utils.unEclipse("bhfocal"));
    }

    @Test
    public void testUnEclipse_8() {
        assertEquals("Focal", Utils.unEclipse("Bhfocal"));
    }

    @Test
    public void testUnEclipse_9() {
        assertEquals("Focal", Utils.unEclipse("Bfocal"));
    }

    @Test
    public void testUnEclipse_10() {
        assertEquals(null, Utils.unEclipse("carr"));
    }

    @Test
    public void testUnLeniteDefiniteS_1() {
        assertEquals("seomra1", Utils.unLeniteDefiniteS("t-seomra1"));
    }

    @Test
    public void testUnLeniteDefiniteS_2() {
        assertEquals("seomra2", Utils.unLeniteDefiniteS("tseomra2"));
    }

    @Test
    public void testUnLeniteDefiniteS_3() {
        assertEquals("Seomra3", Utils.unLeniteDefiniteS("tSeomra3"));
    }

    @Test
    public void testUnLeniteDefiniteS_4() {
        assertEquals("Seomra4", Utils.unLeniteDefiniteS("TSeomra4"));
    }

    @Test
    public void testUnLeniteDefiniteS_5() {
        assertEquals("Seomra5", Utils.unLeniteDefiniteS("Tseomra5"));
    }

    @Test
    public void testUnLeniteDefiniteS_6() {
        assertEquals("Seomra6", Utils.unLeniteDefiniteS("t-Seomra6"));
    }

    @Test
    public void testUnLeniteDefiniteS_7() {
        assertEquals("Seomra7", Utils.unLeniteDefiniteS("T-Seomra7"));
    }

    @Test
    public void testUnLeniteDefiniteS_8() {
        assertEquals("Seomra8", Utils.unLeniteDefiniteS("T-seomra8"));
    }

    @Test
    public void testUnLeniteDefiniteS_9() {
        assertEquals(null, Utils.unLeniteDefiniteS("seomra9"));
    }

    @Test
    public void testUnPonc_1() {
        assertEquals("chuir", Utils.unPonc("ċuir"));
    }

    @Test
    public void testUnPonc_2() {
        assertEquals("CHUIR", Utils.unPonc("ĊUIR"));
    }

    @Test
    public void testUnPonc_3() {
        assertEquals("Chuir", Utils.unPonc("Ċuir"));
    }

    @Test
    public void testUnPonc_4() {
        assertEquals("FÉACH", Utils.unPonc("FÉAĊ"));
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
    public void testIsAllMathsChars_1() {
        assertEquals(false, Utils.isAllMathsChars("foo"));
    }

    @Test
    public void testIsAllMathsChars_2() {
        assertEquals(false, Utils.isAllMathsChars("f\uD835\uDC12"));
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
}
