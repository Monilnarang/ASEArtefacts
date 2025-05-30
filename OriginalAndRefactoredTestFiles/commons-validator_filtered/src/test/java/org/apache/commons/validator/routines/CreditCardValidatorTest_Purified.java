package org.apache.commons.validator.routines;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.validator.routines.CreditCardValidator.CreditCardRange;
import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;
import org.junit.jupiter.api.Test;

public class CreditCardValidatorTest_Purified {

    private static final String VALID_VISA = "4417123456789113";

    private static final String ERROR_VISA = "4417123456789112";

    private static final String VALID_SHORT_VISA = "4222222222222";

    private static final String ERROR_SHORT_VISA = "4222222222229";

    private static final String VALID_AMEX = "378282246310005";

    private static final String ERROR_AMEX = "378282246310001";

    private static final String VALID_MASTERCARD = "5105105105105100";

    private static final String ERROR_MASTERCARD = "5105105105105105";

    private static final String VALID_DISCOVER = "6011000990139424";

    private static final String ERROR_DISCOVER = "6011000990139421";

    private static final String VALID_DISCOVER65 = "6534567890123458";

    private static final String ERROR_DISCOVER65 = "6534567890123450";

    private static final String VALID_DINERS = "30569309025904";

    private static final String ERROR_DINERS = "30569309025901";

    private static final String VALID_VPAY = "4370000000000061";

    private static final String VALID_VPAY2 = "4370000000000012";

    private static final String ERROR_VPAY = "4370000000000069";

    private static final String[] VALID_CARDS = { VALID_VISA, VALID_SHORT_VISA, VALID_AMEX, VALID_MASTERCARD, VALID_DISCOVER, VALID_DISCOVER65, VALID_DINERS, VALID_VPAY, VALID_VPAY2, "60115564485789458" };

    private static final String[] ERROR_CARDS = { ERROR_VISA, ERROR_SHORT_VISA, ERROR_AMEX, ERROR_MASTERCARD, ERROR_DISCOVER, ERROR_DISCOVER65, ERROR_DINERS, ERROR_VPAY, "", "12345678901", "12345678901234567890", "4417123456789112" };

    @Test
    public void testDisjointRange_1() {
        assertEquals(13, VALID_SHORT_VISA.length());
    }

    @Test
    public void testDisjointRange_2() {
        assertEquals(16, VALID_VISA.length());
    }

    @Test
    public void testDisjointRange_3() {
        assertEquals(14, VALID_DINERS.length());
    }

    @Test
    public void testDisjointRange_4_testMerged_4() {
        CreditCardValidator ccv = new CreditCardValidator(new CreditCardRange[] { new CreditCardRange("305", "4", new int[] { 13, 16 }) });
        assertTrue(ccv.isValid(VALID_SHORT_VISA));
        assertTrue(ccv.isValid(VALID_VISA));
        assertFalse(ccv.isValid(ERROR_SHORT_VISA));
        assertFalse(ccv.isValid(ERROR_VISA));
        assertFalse(ccv.isValid(VALID_DINERS));
        ccv = new CreditCardValidator(new CreditCardRange[] { new CreditCardRange("305", "4", new int[] { 13, 14, 16 }) });
        assertTrue(ccv.isValid(VALID_DINERS));
    }

    @Test
    public void testValidLength_1() {
        assertTrue(CreditCardValidator.validLength(14, new CreditCardRange("", "", 14, 14)));
    }

    @Test
    public void testValidLength_2() {
        assertFalse(CreditCardValidator.validLength(15, new CreditCardRange("", "", 14, 14)));
    }

    @Test
    public void testValidLength_3() {
        assertFalse(CreditCardValidator.validLength(13, new CreditCardRange("", "", 14, 14)));
    }

    @Test
    public void testValidLength_4() {
        assertFalse(CreditCardValidator.validLength(14, new CreditCardRange("", "", 15, 17)));
    }

    @Test
    public void testValidLength_5() {
        assertTrue(CreditCardValidator.validLength(15, new CreditCardRange("", "", 15, 17)));
    }

    @Test
    public void testValidLength_6() {
        assertTrue(CreditCardValidator.validLength(16, new CreditCardRange("", "", 15, 17)));
    }

    @Test
    public void testValidLength_7() {
        assertTrue(CreditCardValidator.validLength(17, new CreditCardRange("", "", 15, 17)));
    }

    @Test
    public void testValidLength_8() {
        assertFalse(CreditCardValidator.validLength(18, new CreditCardRange("", "", 15, 17)));
    }

    @Test
    public void testValidLength_9() {
        assertFalse(CreditCardValidator.validLength(14, new CreditCardRange("", "", new int[] { 15, 17 })));
    }

    @Test
    public void testValidLength_10() {
        assertTrue(CreditCardValidator.validLength(15, new CreditCardRange("", "", new int[] { 15, 17 })));
    }

    @Test
    public void testValidLength_11() {
        assertFalse(CreditCardValidator.validLength(16, new CreditCardRange("", "", new int[] { 15, 17 })));
    }

    @Test
    public void testValidLength_12() {
        assertTrue(CreditCardValidator.validLength(17, new CreditCardRange("", "", new int[] { 15, 17 })));
    }

    @Test
    public void testValidLength_13() {
        assertFalse(CreditCardValidator.validLength(18, new CreditCardRange("", "", new int[] { 15, 17 })));
    }
}
