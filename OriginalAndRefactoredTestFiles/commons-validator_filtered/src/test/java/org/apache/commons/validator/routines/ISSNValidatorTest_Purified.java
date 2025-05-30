package org.apache.commons.validator.routines;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Random;
import org.apache.commons.validator.routines.checkdigit.CheckDigit;
import org.apache.commons.validator.routines.checkdigit.EAN13CheckDigit;
import org.junit.jupiter.api.Test;

public class ISSNValidatorTest_Purified {

    private static final ISSNValidator VALIDATOR = ISSNValidator.getInstance();

    private static final String[] VALID_FORMAT = { "ISSN 0317-8471", "1050-124X", "ISSN 1562-6865", "1063-7710", "1748-7188", "ISSN 0264-2875", "1750-0095", "1188-1534", "1911-1479", "ISSN 1911-1460", "0001-6772", "1365-201X", "0264-3596", "1144-875X" };

    private static final String[] INVALID_FORMAT = { "", "   ", "ISBN 0317-8471", "'1050-124X", "ISSN1562-6865", "10637710", "1748-7188'", "ISSN  0264-2875", "1750 0095", "1188_1534", "1911-1478" };

    @Test
    public void testIsValidExtract_1() {
        assertEquals("12345679", VALIDATOR.extractFromEAN13("9771234567003"));
    }

    @Test
    public void testIsValidExtract_2() {
        assertEquals("00014664", VALIDATOR.extractFromEAN13("9770001466006"));
    }

    @Test
    public void testIsValidExtract_3() {
        assertEquals("03178471", VALIDATOR.extractFromEAN13("9770317847001"));
    }

    @Test
    public void testIsValidExtract_4() {
        assertEquals("1144875X", VALIDATOR.extractFromEAN13("9771144875007"));
    }

    @Test
    public void testValidCheckDigitEan13_1() {
        assertNull(VALIDATOR.extractFromEAN13("9771234567001"));
    }

    @Test
    public void testValidCheckDigitEan13_2() {
        assertNull(VALIDATOR.extractFromEAN13("9771234567002"));
    }

    @Test
    public void testValidCheckDigitEan13_3() {
        assertNotNull(VALIDATOR.extractFromEAN13("9771234567003"));
    }

    @Test
    public void testValidCheckDigitEan13_4() {
        assertNull(VALIDATOR.extractFromEAN13("9771234567004"));
    }

    @Test
    public void testValidCheckDigitEan13_5() {
        assertNull(VALIDATOR.extractFromEAN13("9771234567005"));
    }

    @Test
    public void testValidCheckDigitEan13_6() {
        assertNull(VALIDATOR.extractFromEAN13("9771234567006"));
    }

    @Test
    public void testValidCheckDigitEan13_7() {
        assertNull(VALIDATOR.extractFromEAN13("9771234567007"));
    }

    @Test
    public void testValidCheckDigitEan13_8() {
        assertNull(VALIDATOR.extractFromEAN13("9771234567008"));
    }

    @Test
    public void testValidCheckDigitEan13_9() {
        assertNull(VALIDATOR.extractFromEAN13("9771234567009"));
    }

    @Test
    public void testValidCheckDigitEan13_10() {
        assertNull(VALIDATOR.extractFromEAN13("9771234567000"));
    }
}
