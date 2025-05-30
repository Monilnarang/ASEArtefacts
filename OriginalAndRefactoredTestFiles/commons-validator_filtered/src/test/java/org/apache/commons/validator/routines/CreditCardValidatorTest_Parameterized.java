package org.apache.commons.validator.routines;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.validator.routines.CreditCardValidator.CreditCardRange;
import org.apache.commons.validator.routines.checkdigit.LuhnCheckDigit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class CreditCardValidatorTest_Parameterized {

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

    @ParameterizedTest
    @MethodSource("Provider_testValidLength_1_5to7")
    public void testValidLength_1_5to7(int param1, int param2, int param3, int param4, int param5) {
        assertTrue(CreditCardValidator.validLength(param1, new CreditCardRange("", "", param1, param1)));
    }

    static public Stream<Arguments> Provider_testValidLength_1_5to7() {
        return Stream.of(arguments(14, "", "", 14, 14), arguments(15, "", "", 15, 17), arguments(16, "", "", 15, 17), arguments(17, "", "", 15, 17));
    }

    @ParameterizedTest
    @MethodSource("Provider_testValidLength_2to4_8")
    public void testValidLength_2to4_8(int param1, int param2, int param3, int param4, int param5) {
        assertFalse(CreditCardValidator.validLength(param1, new CreditCardRange("", "", 14, 14)));
    }

    static public Stream<Arguments> Provider_testValidLength_2to4_8() {
        return Stream.of(arguments(15, "", "", 14, 14), arguments(13, "", "", 14, 14), arguments(14, "", "", 15, 17), arguments(18, "", "", 15, 17));
    }

    @ParameterizedTest
    @MethodSource("Provider_testValidLength_9_11_13")
    public void testValidLength_9_11_13(int param1, int param2, int param3, int param4) {
        assertFalse(CreditCardValidator.validLength(param1, new CreditCardRange("", "", new int[] { 15, 17 })));
    }

    static public Stream<Arguments> Provider_testValidLength_9_11_13() {
        return Stream.of(arguments(14, "", "", "{'15', '17'}"), arguments(16, "", "", "{'15', '17'}"), arguments(18, "", "", "{'15', '17'}"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testValidLength_10_12")
    public void testValidLength_10_12(int param1, int param2, int param3, int param4) {
        assertTrue(CreditCardValidator.validLength(param1, new CreditCardRange("", "", new int[] { param1, 17 })));
    }

    static public Stream<Arguments> Provider_testValidLength_10_12() {
        return Stream.of(arguments(15, "", "", "{'15', '17'}"), arguments(17, "", "", "{'15', '17'}"));
    }
}
