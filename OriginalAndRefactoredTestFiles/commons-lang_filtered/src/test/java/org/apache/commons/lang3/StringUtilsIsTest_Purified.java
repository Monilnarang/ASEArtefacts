package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class StringUtilsIsTest_Purified extends AbstractLangTest {

    @Test
    public void testIsAlpha_1() {
        assertFalse(StringUtils.isAlpha(null));
    }

    @Test
    public void testIsAlpha_2() {
        assertFalse(StringUtils.isAlpha(""));
    }

    @Test
    public void testIsAlpha_3() {
        assertFalse(StringUtils.isAlpha(" "));
    }

    @Test
    public void testIsAlpha_4() {
        assertTrue(StringUtils.isAlpha("a"));
    }

    @Test
    public void testIsAlpha_5() {
        assertTrue(StringUtils.isAlpha("A"));
    }

    @Test
    public void testIsAlpha_6() {
        assertTrue(StringUtils.isAlpha("kgKgKgKgkgkGkjkjlJlOKLgHdGdHgl"));
    }

    @Test
    public void testIsAlpha_7() {
        assertFalse(StringUtils.isAlpha("ham kso"));
    }

    @Test
    public void testIsAlpha_8() {
        assertFalse(StringUtils.isAlpha("1"));
    }

    @Test
    public void testIsAlpha_9() {
        assertFalse(StringUtils.isAlpha("hkHKHik6iUGHKJgU7tUJgKJGI87GIkug"));
    }

    @Test
    public void testIsAlpha_10() {
        assertFalse(StringUtils.isAlpha("_"));
    }

    @Test
    public void testIsAlpha_11() {
        assertFalse(StringUtils.isAlpha("hkHKHik*khbkuh"));
    }

    @Test
    public void testIsAlphanumeric_1() {
        assertFalse(StringUtils.isAlphanumeric(null));
    }

    @Test
    public void testIsAlphanumeric_2() {
        assertFalse(StringUtils.isAlphanumeric(""));
    }

    @Test
    public void testIsAlphanumeric_3() {
        assertFalse(StringUtils.isAlphanumeric(" "));
    }

    @Test
    public void testIsAlphanumeric_4() {
        assertTrue(StringUtils.isAlphanumeric("a"));
    }

    @Test
    public void testIsAlphanumeric_5() {
        assertTrue(StringUtils.isAlphanumeric("A"));
    }

    @Test
    public void testIsAlphanumeric_6() {
        assertTrue(StringUtils.isAlphanumeric("kgKgKgKgkgkGkjkjlJlOKLgHdGdHgl"));
    }

    @Test
    public void testIsAlphanumeric_7() {
        assertFalse(StringUtils.isAlphanumeric("ham kso"));
    }

    @Test
    public void testIsAlphanumeric_8() {
        assertTrue(StringUtils.isAlphanumeric("1"));
    }

    @Test
    public void testIsAlphanumeric_9() {
        assertTrue(StringUtils.isAlphanumeric("hkHKHik6iUGHKJgU7tUJgKJGI87GIkug"));
    }

    @Test
    public void testIsAlphanumeric_10() {
        assertFalse(StringUtils.isAlphanumeric("_"));
    }

    @Test
    public void testIsAlphanumeric_11() {
        assertFalse(StringUtils.isAlphanumeric("hkHKHik*khbkuh"));
    }

    @Test
    public void testIsAlphanumericSpace_1() {
        assertFalse(StringUtils.isAlphanumericSpace(null));
    }

    @Test
    public void testIsAlphanumericSpace_2() {
        assertTrue(StringUtils.isAlphanumericSpace(""));
    }

    @Test
    public void testIsAlphanumericSpace_3() {
        assertTrue(StringUtils.isAlphanumericSpace(" "));
    }

    @Test
    public void testIsAlphanumericSpace_4() {
        assertTrue(StringUtils.isAlphanumericSpace("a"));
    }

    @Test
    public void testIsAlphanumericSpace_5() {
        assertTrue(StringUtils.isAlphanumericSpace("A"));
    }

    @Test
    public void testIsAlphanumericSpace_6() {
        assertTrue(StringUtils.isAlphanumericSpace("kgKgKgKgkgkGkjkjlJlOKLgHdGdHgl"));
    }

    @Test
    public void testIsAlphanumericSpace_7() {
        assertTrue(StringUtils.isAlphanumericSpace("ham kso"));
    }

    @Test
    public void testIsAlphanumericSpace_8() {
        assertTrue(StringUtils.isAlphanumericSpace("1"));
    }

    @Test
    public void testIsAlphanumericSpace_9() {
        assertTrue(StringUtils.isAlphanumericSpace("hkHKHik6iUGHKJgU7tUJgKJGI87GIkug"));
    }

    @Test
    public void testIsAlphanumericSpace_10() {
        assertFalse(StringUtils.isAlphanumericSpace("_"));
    }

    @Test
    public void testIsAlphanumericSpace_11() {
        assertFalse(StringUtils.isAlphanumericSpace("hkHKHik*khbkuh"));
    }

    @Test
    public void testIsAlphaspace_1() {
        assertFalse(StringUtils.isAlphaSpace(null));
    }

    @Test
    public void testIsAlphaspace_2() {
        assertTrue(StringUtils.isAlphaSpace(""));
    }

    @Test
    public void testIsAlphaspace_3() {
        assertTrue(StringUtils.isAlphaSpace(" "));
    }

    @Test
    public void testIsAlphaspace_4() {
        assertTrue(StringUtils.isAlphaSpace("a"));
    }

    @Test
    public void testIsAlphaspace_5() {
        assertTrue(StringUtils.isAlphaSpace("A"));
    }

    @Test
    public void testIsAlphaspace_6() {
        assertTrue(StringUtils.isAlphaSpace("kgKgKgKgkgkGkjkjlJlOKLgHdGdHgl"));
    }

    @Test
    public void testIsAlphaspace_7() {
        assertTrue(StringUtils.isAlphaSpace("ham kso"));
    }

    @Test
    public void testIsAlphaspace_8() {
        assertFalse(StringUtils.isAlphaSpace("1"));
    }

    @Test
    public void testIsAlphaspace_9() {
        assertFalse(StringUtils.isAlphaSpace("hkHKHik6iUGHKJgU7tUJgKJGI87GIkug"));
    }

    @Test
    public void testIsAlphaspace_10() {
        assertFalse(StringUtils.isAlphaSpace("_"));
    }

    @Test
    public void testIsAlphaspace_11() {
        assertFalse(StringUtils.isAlphaSpace("hkHKHik*khbkuh"));
    }

    @Test
    public void testIsAsciiPrintable_String_1() {
        assertFalse(StringUtils.isAsciiPrintable(null));
    }

    @Test
    public void testIsAsciiPrintable_String_2() {
        assertTrue(StringUtils.isAsciiPrintable(""));
    }

    @Test
    public void testIsAsciiPrintable_String_3() {
        assertTrue(StringUtils.isAsciiPrintable(" "));
    }

    @Test
    public void testIsAsciiPrintable_String_4() {
        assertTrue(StringUtils.isAsciiPrintable("a"));
    }

    @Test
    public void testIsAsciiPrintable_String_5() {
        assertTrue(StringUtils.isAsciiPrintable("A"));
    }

    @Test
    public void testIsAsciiPrintable_String_6() {
        assertTrue(StringUtils.isAsciiPrintable("1"));
    }

    @Test
    public void testIsAsciiPrintable_String_7() {
        assertTrue(StringUtils.isAsciiPrintable("Ceki"));
    }

    @Test
    public void testIsAsciiPrintable_String_8() {
        assertTrue(StringUtils.isAsciiPrintable("!ab2c~"));
    }

    @Test
    public void testIsAsciiPrintable_String_9() {
        assertTrue(StringUtils.isAsciiPrintable("1000"));
    }

    @Test
    public void testIsAsciiPrintable_String_10() {
        assertTrue(StringUtils.isAsciiPrintable("10 00"));
    }

    @Test
    public void testIsAsciiPrintable_String_11() {
        assertFalse(StringUtils.isAsciiPrintable("10\t00"));
    }

    @Test
    public void testIsAsciiPrintable_String_12() {
        assertTrue(StringUtils.isAsciiPrintable("10.00"));
    }

    @Test
    public void testIsAsciiPrintable_String_13() {
        assertTrue(StringUtils.isAsciiPrintable("10,00"));
    }

    @Test
    public void testIsAsciiPrintable_String_14() {
        assertTrue(StringUtils.isAsciiPrintable("!ab-c~"));
    }

    @Test
    public void testIsAsciiPrintable_String_15() {
        assertTrue(StringUtils.isAsciiPrintable("hkHK=Hik6i?UGH_KJgU7.tUJgKJ*GI87GI,kug"));
    }

    @Test
    public void testIsAsciiPrintable_String_16() {
        assertTrue(StringUtils.isAsciiPrintable("\u0020"));
    }

    @Test
    public void testIsAsciiPrintable_String_17() {
        assertTrue(StringUtils.isAsciiPrintable("\u0021"));
    }

    @Test
    public void testIsAsciiPrintable_String_18() {
        assertTrue(StringUtils.isAsciiPrintable("\u007e"));
    }

    @Test
    public void testIsAsciiPrintable_String_19() {
        assertFalse(StringUtils.isAsciiPrintable("\u007f"));
    }

    @Test
    public void testIsAsciiPrintable_String_20() {
        assertTrue(StringUtils.isAsciiPrintable("G?lc?"));
    }

    @Test
    public void testIsAsciiPrintable_String_21() {
        assertTrue(StringUtils.isAsciiPrintable("=?iso-8859-1?Q?G=FClc=FC?="));
    }

    @Test
    public void testIsAsciiPrintable_String_22() {
        assertFalse(StringUtils.isAsciiPrintable("G\u00fclc\u00fc"));
    }

    @Test
    public void testIsNumeric_1() {
        assertFalse(StringUtils.isNumeric(null));
    }

    @Test
    public void testIsNumeric_2() {
        assertFalse(StringUtils.isNumeric(""));
    }

    @Test
    public void testIsNumeric_3() {
        assertFalse(StringUtils.isNumeric(" "));
    }

    @Test
    public void testIsNumeric_4() {
        assertFalse(StringUtils.isNumeric("a"));
    }

    @Test
    public void testIsNumeric_5() {
        assertFalse(StringUtils.isNumeric("A"));
    }

    @Test
    public void testIsNumeric_6() {
        assertFalse(StringUtils.isNumeric("kgKgKgKgkgkGkjkjlJlOKLgHdGdHgl"));
    }

    @Test
    public void testIsNumeric_7() {
        assertFalse(StringUtils.isNumeric("ham kso"));
    }

    @Test
    public void testIsNumeric_8() {
        assertTrue(StringUtils.isNumeric("1"));
    }

    @Test
    public void testIsNumeric_9() {
        assertTrue(StringUtils.isNumeric("1000"));
    }

    @Test
    public void testIsNumeric_10() {
        assertTrue(StringUtils.isNumeric("\u0967\u0968\u0969"));
    }

    @Test
    public void testIsNumeric_11() {
        assertFalse(StringUtils.isNumeric("\u0967\u0968 \u0969"));
    }

    @Test
    public void testIsNumeric_12() {
        assertFalse(StringUtils.isNumeric("2.3"));
    }

    @Test
    public void testIsNumeric_13() {
        assertFalse(StringUtils.isNumeric("10 00"));
    }

    @Test
    public void testIsNumeric_14() {
        assertFalse(StringUtils.isNumeric("hkHKHik6iUGHKJgU7tUJgKJGI87GIkug"));
    }

    @Test
    public void testIsNumeric_15() {
        assertFalse(StringUtils.isNumeric("_"));
    }

    @Test
    public void testIsNumeric_16() {
        assertFalse(StringUtils.isNumeric("hkHKHik*khbkuh"));
    }

    @Test
    public void testIsNumeric_17() {
        assertFalse(StringUtils.isNumeric("+123"));
    }

    @Test
    public void testIsNumeric_18() {
        assertFalse(StringUtils.isNumeric("-123"));
    }

    @Test
    public void testIsNumericSpace_1() {
        assertFalse(StringUtils.isNumericSpace(null));
    }

    @Test
    public void testIsNumericSpace_2() {
        assertTrue(StringUtils.isNumericSpace(""));
    }

    @Test
    public void testIsNumericSpace_3() {
        assertTrue(StringUtils.isNumericSpace(" "));
    }

    @Test
    public void testIsNumericSpace_4() {
        assertFalse(StringUtils.isNumericSpace("a"));
    }

    @Test
    public void testIsNumericSpace_5() {
        assertFalse(StringUtils.isNumericSpace("A"));
    }

    @Test
    public void testIsNumericSpace_6() {
        assertFalse(StringUtils.isNumericSpace("kgKgKgKgkgkGkjkjlJlOKLgHdGdHgl"));
    }

    @Test
    public void testIsNumericSpace_7() {
        assertFalse(StringUtils.isNumericSpace("ham kso"));
    }

    @Test
    public void testIsNumericSpace_8() {
        assertTrue(StringUtils.isNumericSpace("1"));
    }

    @Test
    public void testIsNumericSpace_9() {
        assertTrue(StringUtils.isNumericSpace("1000"));
    }

    @Test
    public void testIsNumericSpace_10() {
        assertFalse(StringUtils.isNumericSpace("2.3"));
    }

    @Test
    public void testIsNumericSpace_11() {
        assertTrue(StringUtils.isNumericSpace("10 00"));
    }

    @Test
    public void testIsNumericSpace_12() {
        assertTrue(StringUtils.isNumericSpace("\u0967\u0968\u0969"));
    }

    @Test
    public void testIsNumericSpace_13() {
        assertTrue(StringUtils.isNumericSpace("\u0967\u0968 \u0969"));
    }

    @Test
    public void testIsNumericSpace_14() {
        assertFalse(StringUtils.isNumericSpace("hkHKHik6iUGHKJgU7tUJgKJGI87GIkug"));
    }

    @Test
    public void testIsNumericSpace_15() {
        assertFalse(StringUtils.isNumericSpace("_"));
    }

    @Test
    public void testIsNumericSpace_16() {
        assertFalse(StringUtils.isNumericSpace("hkHKHik*khbkuh"));
    }

    @Test
    public void testIsWhitespace_1() {
        assertFalse(StringUtils.isWhitespace(null));
    }

    @Test
    public void testIsWhitespace_2() {
        assertTrue(StringUtils.isWhitespace(""));
    }

    @Test
    public void testIsWhitespace_3() {
        assertTrue(StringUtils.isWhitespace(" "));
    }

    @Test
    public void testIsWhitespace_4() {
        assertTrue(StringUtils.isWhitespace("\t \n \t"));
    }

    @Test
    public void testIsWhitespace_5() {
        assertFalse(StringUtils.isWhitespace("\t aa\n \t"));
    }

    @Test
    public void testIsWhitespace_6() {
        assertTrue(StringUtils.isWhitespace(" "));
    }

    @Test
    public void testIsWhitespace_7() {
        assertFalse(StringUtils.isWhitespace(" a "));
    }

    @Test
    public void testIsWhitespace_8() {
        assertFalse(StringUtils.isWhitespace("a  "));
    }

    @Test
    public void testIsWhitespace_9() {
        assertFalse(StringUtils.isWhitespace("  a"));
    }

    @Test
    public void testIsWhitespace_10() {
        assertFalse(StringUtils.isWhitespace("aba"));
    }

    @Test
    public void testIsWhitespace_11() {
        assertFalse(StringUtils.isWhitespace("a"));
    }

    @Test
    public void testIsWhitespace_12() {
        assertTrue(StringUtils.isWhitespace(StringUtilsTest.WHITESPACE));
    }

    @Test
    public void testIsWhitespace_13() {
        assertFalse(StringUtils.isWhitespace(StringUtilsTest.NON_WHITESPACE));
    }
}
