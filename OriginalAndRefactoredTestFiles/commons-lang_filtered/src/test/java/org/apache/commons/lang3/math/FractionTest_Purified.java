package org.apache.commons.lang3.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class FractionTest_Purified extends AbstractLangTest {

    private static final int SKIP = 500;

    @Test
    public void testConstants_1() {
        assertEquals(0, Fraction.ZERO.getNumerator());
    }

    @Test
    public void testConstants_2() {
        assertEquals(1, Fraction.ZERO.getDenominator());
    }

    @Test
    public void testConstants_3() {
        assertEquals(1, Fraction.ONE.getNumerator());
    }

    @Test
    public void testConstants_4() {
        assertEquals(1, Fraction.ONE.getDenominator());
    }

    @Test
    public void testConstants_5() {
        assertEquals(1, Fraction.ONE_HALF.getNumerator());
    }

    @Test
    public void testConstants_6() {
        assertEquals(2, Fraction.ONE_HALF.getDenominator());
    }

    @Test
    public void testConstants_7() {
        assertEquals(1, Fraction.ONE_THIRD.getNumerator());
    }

    @Test
    public void testConstants_8() {
        assertEquals(3, Fraction.ONE_THIRD.getDenominator());
    }

    @Test
    public void testConstants_9() {
        assertEquals(2, Fraction.TWO_THIRDS.getNumerator());
    }

    @Test
    public void testConstants_10() {
        assertEquals(3, Fraction.TWO_THIRDS.getDenominator());
    }

    @Test
    public void testConstants_11() {
        assertEquals(1, Fraction.ONE_QUARTER.getNumerator());
    }

    @Test
    public void testConstants_12() {
        assertEquals(4, Fraction.ONE_QUARTER.getDenominator());
    }

    @Test
    public void testConstants_13() {
        assertEquals(2, Fraction.TWO_QUARTERS.getNumerator());
    }

    @Test
    public void testConstants_14() {
        assertEquals(4, Fraction.TWO_QUARTERS.getDenominator());
    }

    @Test
    public void testConstants_15() {
        assertEquals(3, Fraction.THREE_QUARTERS.getNumerator());
    }

    @Test
    public void testConstants_16() {
        assertEquals(4, Fraction.THREE_QUARTERS.getDenominator());
    }

    @Test
    public void testConstants_17() {
        assertEquals(1, Fraction.ONE_FIFTH.getNumerator());
    }

    @Test
    public void testConstants_18() {
        assertEquals(5, Fraction.ONE_FIFTH.getDenominator());
    }

    @Test
    public void testConstants_19() {
        assertEquals(2, Fraction.TWO_FIFTHS.getNumerator());
    }

    @Test
    public void testConstants_20() {
        assertEquals(5, Fraction.TWO_FIFTHS.getDenominator());
    }

    @Test
    public void testConstants_21() {
        assertEquals(3, Fraction.THREE_FIFTHS.getNumerator());
    }

    @Test
    public void testConstants_22() {
        assertEquals(5, Fraction.THREE_FIFTHS.getDenominator());
    }

    @Test
    public void testConstants_23() {
        assertEquals(4, Fraction.FOUR_FIFTHS.getNumerator());
    }

    @Test
    public void testConstants_24() {
        assertEquals(5, Fraction.FOUR_FIFTHS.getDenominator());
    }
}
