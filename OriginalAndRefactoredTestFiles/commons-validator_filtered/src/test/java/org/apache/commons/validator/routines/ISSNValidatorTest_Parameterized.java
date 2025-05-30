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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ISSNValidatorTest_Parameterized {

    private static final ISSNValidator VALIDATOR = ISSNValidator.getInstance();

    private static final String[] VALID_FORMAT = { "ISSN 0317-8471", "1050-124X", "ISSN 1562-6865", "1063-7710", "1748-7188", "ISSN 0264-2875", "1750-0095", "1188-1534", "1911-1479", "ISSN 1911-1460", "0001-6772", "1365-201X", "0264-3596", "1144-875X" };

    private static final String[] INVALID_FORMAT = { "", "   ", "ISBN 0317-8471", "'1050-124X", "ISSN1562-6865", "10637710", "1748-7188'", "ISSN  0264-2875", "1750 0095", "1188_1534", "1911-1478" };

    @Test
    public void testValidCheckDigitEan13_3() {
        assertNotNull(VALIDATOR.extractFromEAN13("9771234567003"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsValidExtract_1to4")
    public void testIsValidExtract_1to4(int param1, double param2) {
        assertEquals(param1, VALIDATOR.extractFromEAN13(param2));
    }

    static public Stream<Arguments> Provider_testIsValidExtract_1to4() {
        return Stream.of(arguments(12345679, 9771234567003), arguments(00014664, 9770001466006), arguments(03178471, 9770317847001), arguments("1144875X", 9771144875007));
    }

    @ParameterizedTest
    @MethodSource("Provider_testValidCheckDigitEan13_1to2_4to10")
    public void testValidCheckDigitEan13_1to2_4to10(double param1) {
        assertNull(VALIDATOR.extractFromEAN13(param1));
    }

    static public Stream<Arguments> Provider_testValidCheckDigitEan13_1to2_4to10() {
        return Stream.of(arguments(9771234567001), arguments(9771234567002), arguments(9771234567004), arguments(9771234567005), arguments(9771234567006), arguments(9771234567007), arguments(9771234567008), arguments(9771234567009), arguments(9771234567000));
    }
}
