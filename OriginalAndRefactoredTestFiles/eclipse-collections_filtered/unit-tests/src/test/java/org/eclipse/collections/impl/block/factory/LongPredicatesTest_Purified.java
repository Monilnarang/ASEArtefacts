package org.eclipse.collections.impl.block.factory;

import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LongPredicatesTest_Purified {

    private static final Function<Long, Long> LONG_VALUE = Long::longValue;

    @Test
    public void isOdd_1() {
        assertTrue(LongPredicates.isOdd().accept(1L));
    }

    @Test
    public void isOdd_2() {
        assertFalse(LongPredicates.isOdd().accept(-2L));
    }

    @Test
    public void isEven_1() {
        assertTrue(LongPredicates.isEven().accept(-42L));
    }

    @Test
    public void isEven_2() {
        assertTrue(LongPredicates.isEven().accept(0L));
    }

    @Test
    public void isEven_3() {
        assertFalse(LongPredicates.isEven().accept(1L));
    }

    @Test
    public void attributeIsOdd_1() {
        assertTrue(LongPredicates.attributeIsOdd(LONG_VALUE).accept(1L));
    }

    @Test
    public void attributeIsOdd_2() {
        assertFalse(LongPredicates.attributeIsOdd(LONG_VALUE).accept(-2L));
    }

    @Test
    public void attributeIsEven_1() {
        assertTrue(LongPredicates.attributeIsEven(LONG_VALUE).accept(-42L));
    }

    @Test
    public void attributeIsEven_2() {
        assertTrue(LongPredicates.attributeIsEven(LONG_VALUE).accept(0L));
    }

    @Test
    public void attributeIsEven_3() {
        assertFalse(LongPredicates.attributeIsEven(LONG_VALUE).accept(1L));
    }

    @Test
    public void isZero_1() {
        assertTrue(LongPredicates.isZero().accept(0L));
    }

    @Test
    public void isZero_2() {
        assertFalse(LongPredicates.isZero().accept(1L));
    }

    @Test
    public void isZero_3() {
        assertFalse(LongPredicates.isZero().accept(-1L));
    }

    @Test
    public void isPositive_1() {
        assertFalse(LongPredicates.isPositive().accept(0L));
    }

    @Test
    public void isPositive_2() {
        assertTrue(LongPredicates.isPositive().accept(1L));
    }

    @Test
    public void isPositive_3() {
        assertFalse(LongPredicates.isPositive().accept(-1L));
    }

    @Test
    public void isNegative_1() {
        assertFalse(LongPredicates.isNegative().accept(0L));
    }

    @Test
    public void isNegative_2() {
        assertFalse(LongPredicates.isNegative().accept(1L));
    }

    @Test
    public void isNegative_3() {
        assertTrue(LongPredicates.isNegative().accept(-1L));
    }
}
