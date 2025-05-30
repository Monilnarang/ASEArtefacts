package org.apache.commons.text.numbers;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.function.BiFunction;
import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ParsedDecimalTest_Parameterized {

    private static final class FormatOptionsImpl implements ParsedDecimal.FormatOptions {

        private boolean includeFractionPlaceholder = true;

        private boolean signedZero = true;

        private char[] digits = "0123456789".toCharArray();

        private char decimalSeparator = '.';

        private char thousandsGroupingSeparator = ',';

        private boolean groupThousands;

        private char minusSign = '-';

        private String exponentSeparator = "E";

        private boolean alwaysIncludeExponent;

        @Override
        public char getDecimalSeparator() {
            return decimalSeparator;
        }

        @Override
        public char[] getDigits() {
            return digits;
        }

        @Override
        public char[] getExponentSeparatorChars() {
            return exponentSeparator.toCharArray();
        }

        @Override
        public char getGroupingSeparator() {
            return thousandsGroupingSeparator;
        }

        @Override
        public char getMinusSign() {
            return minusSign;
        }

        @Override
        public boolean isAlwaysIncludeExponent() {
            return alwaysIncludeExponent;
        }

        @Override
        public boolean isGroupThousands() {
            return groupThousands;
        }

        @Override
        public boolean isIncludeFractionPlaceholder() {
            return includeFractionPlaceholder;
        }

        @Override
        public boolean isSignedZero() {
            return signedZero;
        }

        public void setAlwaysIncludeExponent(final boolean alwaysIncludeExponent) {
            this.alwaysIncludeExponent = alwaysIncludeExponent;
        }

        public void setDecimalSeparator(final char decimalSeparator) {
            this.decimalSeparator = decimalSeparator;
        }

        public void setDigitsFromString(final String digits) {
            this.digits = digits.toCharArray();
        }

        public void setExponentSeparator(final String exponentSeparator) {
            this.exponentSeparator = exponentSeparator;
        }

        public void setGroupThousands(final boolean groupThousands) {
            this.groupThousands = groupThousands;
        }

        public void setIncludeFractionPlaceholder(final boolean includeFractionPlaceholder) {
            this.includeFractionPlaceholder = includeFractionPlaceholder;
        }

        public void setMinusSign(final char minusSign) {
            this.minusSign = minusSign;
        }

        public void setSignedZero(final boolean signedZero) {
            this.signedZero = signedZero;
        }

        public void setThousandsGroupingSeparator(final char thousandsGroupingSeparator) {
            this.thousandsGroupingSeparator = thousandsGroupingSeparator;
        }
    }

    private static void assertMaxPrecision(final double d, final int maxPrecision, final boolean negative, final String digits, final int exponent) {
        final ParsedDecimal dec = ParsedDecimal.from(d);
        dec.maxPrecision(maxPrecision);
        assertSimpleDecimal(dec, negative, digits, exponent);
    }

    private static void assertRound(final double d, final int roundExponent, final boolean negative, final String digits, final int exponent) {
        final ParsedDecimal dec = ParsedDecimal.from(d);
        dec.round(roundExponent);
        assertSimpleDecimal(dec, negative, digits, exponent);
    }

    private static void assertSimpleDecimal(final ParsedDecimal parsed, final boolean negative, final String digits, final int exponent) {
        Assertions.assertEquals(negative, parsed.negative);
        Assertions.assertEquals(digits, digitString(parsed));
        Assertions.assertEquals(exponent, parsed.getExponent());
        Assertions.assertEquals(digits.length(), parsed.digitCount);
        Assertions.assertEquals(exponent, parsed.getScientificExponent() - digits.length() + 1);
    }

    private static void assertThrowsWithMessage(final Executable fn, final Class<? extends Throwable> type, final String msg) {
        final Throwable exc = Assertions.assertThrows(type, fn);
        Assertions.assertEquals(msg, exc.getMessage());
    }

    private static void checkFrom(final double d, final String digits, final int exponent) {
        final boolean negative = Math.signum(d) < 0;
        assertSimpleDecimal(ParsedDecimal.from(d), negative, digits, exponent);
        assertSimpleDecimal(ParsedDecimal.from(-d), !negative, digits, exponent);
    }

    private static void checkToEngineeringString(final double d, final String expected, final ParsedDecimal.FormatOptions opts) {
        checkToStringMethod(d, expected, ParsedDecimal::toEngineeringString, opts);
        final String pos = ParsedDecimal.from(d).toEngineeringString(opts);
        Assertions.assertEquals(0, parseExponent(pos, opts) % 3);
        final String neg = ParsedDecimal.from(-d).toEngineeringString(opts);
        Assertions.assertEquals(0, parseExponent(neg, opts) % 3);
    }

    private static void checkToPlainString(final double d, final String expected, final ParsedDecimal.FormatOptions opts) {
        checkToStringMethod(d, expected, ParsedDecimal::toPlainString, opts);
    }

    private static void checkToScientificString(final double d, final String expected, final ParsedDecimal.FormatOptions opts) {
        checkToStringMethod(d, expected, ParsedDecimal::toScientificString, opts);
    }

    private static void checkToStringMethod(final double d, final String expected, final BiFunction<ParsedDecimal, ParsedDecimal.FormatOptions, String> fn, final ParsedDecimal.FormatOptions opts) {
        final ParsedDecimal pos = ParsedDecimal.from(d);
        final String actual = fn.apply(pos, opts);
        Assertions.assertEquals(expected, actual);
    }

    private static double createRandomDouble(final UniformRandomProvider rng) {
        final long mask = (1L << 52) - 1 | 1L << 63;
        final long bits = rng.nextLong() & mask;
        final long exp = rng.nextInt(2045) + 1;
        return Double.longBitsToDouble(bits | exp << 52);
    }

    private static String digitString(final ParsedDecimal dec) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dec.digitCount; ++i) {
            sb.append(dec.digits[i]);
        }
        return sb.toString();
    }

    private static int parseExponent(final String str, final ParsedDecimal.FormatOptions opts) {
        final char[] expSep = opts.getExponentSeparatorChars();
        final int expStartIdx = str.indexOf(String.valueOf(expSep));
        if (expStartIdx > -1) {
            int expIdx = expStartIdx + expSep.length;
            boolean neg = false;
            if (str.charAt(expIdx) == opts.getMinusSign()) {
                ++expIdx;
                neg = true;
            }
            final String expStr = str.substring(expIdx);
            final int val = Integer.parseInt(expStr);
            return neg ? -val : val;
        }
        return 0;
    }

    @Test
    void testIsZero_1() {
        Assertions.assertTrue(ParsedDecimal.from(0.0).isZero());
    }

    @Test
    void testIsZero_2() {
        Assertions.assertTrue(ParsedDecimal.from(-0.0).isZero());
    }

    @Test
    void testIsZero_3() {
        Assertions.assertFalse(ParsedDecimal.from(1.0).isZero());
    }

    @Test
    void testIsZero_4() {
        Assertions.assertFalse(ParsedDecimal.from(-1.0).isZero());
    }

    @Test
    void testIsZero_5() {
        Assertions.assertFalse(ParsedDecimal.from(Double.MIN_NORMAL).isZero());
    }

    @Test
    void testIsZero_6() {
        Assertions.assertFalse(ParsedDecimal.from(-Double.MIN_NORMAL).isZero());
    }

    @Test
    void testIsZero_7() {
        Assertions.assertFalse(ParsedDecimal.from(Double.MAX_VALUE).isZero());
    }

    @Test
    void testIsZero_8() {
        Assertions.assertFalse(ParsedDecimal.from(-Double.MIN_VALUE).isZero());
    }

    @Test
    void testRound_mixed_1_testMerged_1() {
        final double a = 9.94e-10;
        assertRound(a, -13, false, "994", -12);
        assertRound(a, -12, false, "994", -12);
        assertRound(a, -11, false, "99", -11);
        assertRound(a, -10, false, "1", -9);
        assertRound(a, -9, false, "1", -9);
        assertRound(a, -8, false, "0", 0);
    }

    @Test
    void testRound_mixed_7_testMerged_2() {
        final double b = -3.1415;
        assertRound(b, -5, true, "31415", -4);
        assertRound(b, -4, true, "31415", -4);
        assertRound(b, -3, true, "3142", -3);
        assertRound(b, -2, true, "314", -2);
        assertRound(b, -1, true, "31", -1);
        assertRound(b, 0, true, "3", 0);
        assertRound(b, 1, true, "0", 0);
        assertRound(b, 2, true, "0", 0);
    }

    @Test
    void testRound_mixed_15_testMerged_3() {
        final double c = 5.55e10;
        assertRound(c, 7, false, "555", 8);
        assertRound(c, 8, false, "555", 8);
        assertRound(c, 9, false, "56", 9);
        assertRound(c, 10, false, "6", 10);
        assertRound(c, 11, false, "1", 11);
        assertRound(c, 12, false, "0", 0);
    }

    @Test
    void testRound_nine_1_testMerged_1() {
        final double a = 9e-10;
        assertRound(a, -11, false, "9", -10);
        assertRound(a, -10, false, "9", -10);
        assertRound(a, -9, false, "1", -9);
    }

    @Test
    void testRound_one_1_testMerged_1() {
        final double a = 1e-10;
        assertRound(a, -11, false, "1", -10);
        assertRound(a, -10, false, "1", -10);
        assertRound(a, -9, false, "0", 0);
    }

    @ParameterizedTest
    @MethodSource("Provider_testMaxPrecision_halfEvenRounding_1_1to2_2to3_3to5")
    void testMaxPrecision_halfEvenRounding_1_1to2_2to3_3to5(double param1, int param2, int param3, int param4) {
        assertMaxPrecision(param1, param2, param3, param4, 0);
    }

    static public Stream<Arguments> Provider_testMaxPrecision_halfEvenRounding_1_1to2_2to3_3to5() {
        return Stream.of(arguments(5.5, 1, 6, 0), arguments(2.5, 1, 2, 0), arguments(1.6, 1, 2, 0), arguments(1.1, 1, 1, 0), arguments(1.0, 1, 1, 0), arguments(9.0, 1, 9, 0), arguments(1.0, 1, 1, 0), arguments(0.0, 1, 0, 0));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMaxPrecision_halfEvenRounding_4to6_6to10")
    void testMaxPrecision_halfEvenRounding_4to6_6to10(int param1, int param2, int param3, double param4) {
        assertMaxPrecision(-1.0, param1, param2, param3, param4);
    }

    static public Stream<Arguments> Provider_testMaxPrecision_halfEvenRounding_4to6_6to10() {
        return Stream.of(arguments(1, 1, 0, 1.0), arguments(1, 1, 0, 1.1), arguments(1, 2, 0, 1.6), arguments(1, 2, 0, 2.5), arguments(1, 6, 0, 5.5), arguments(1, 0, 0, 0.0), arguments(1, 1, 0, 1.0), arguments(1, 9, 0, 9.0));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRound_nine_4_testMerged_2_2")
    void testRound_nine_4_testMerged_2_2(int param1, int param2, int param3, int param4, int param5, int param6, int param7, int param8, int param9, int param10) {
        final double b = -9;
        assertRound(b, -1, param1, param2, param3);
        assertRound(b, param4, param5, param6, param7);
        assertRound(b, param8, param9, param10, 1);
    }

    static public Stream<Arguments> Provider_testRound_nine_4_testMerged_2_2() {
        return Stream.of(arguments(9, 9, 0, 1, 0, 9, 0, 1, 1, 1), arguments(1, 1, 0, 1, 0, 1, 0, 1, 0, 0));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRound_nine_7_testMerged_3_3")
    void testRound_nine_7_testMerged_3_3(double param1, int param2, int param3, int param4, int param5, int param6, int param7, int param8, int param9, int param10) {
        final double c = 9e10;
        assertRound(c, param1, param2, param3, param4);
        assertRound(c, param5, param6, param7, param8);
        assertRound(c, param9, param10, "1", 11);
    }

    static public Stream<Arguments> Provider_testRound_nine_7_testMerged_3_3() {
        return Stream.of(arguments(9e10, 9, 9, 10, 10, 9, 10, 11, 1, 11), arguments(1e10, 9, 1, 10, 10, 1, 10, 11, 0, 0));
    }
}
