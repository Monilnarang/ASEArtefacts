package org.eclipse.collections.impl.block.factory;

import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntegerPredicatesTest_Purified {

    private static final Function<Integer, Integer> INT_VALUE = integer -> integer;

    @Test
    public void isOdd_1() {
        assertTrue(IntegerPredicates.isOdd().accept(1));
    }

    @Test
    public void isOdd_2() {
        assertFalse(IntegerPredicates.isOdd().accept(-2));
    }

    @Test
    public void isEven_1() {
        assertTrue(IntegerPredicates.isEven().accept(-42));
    }

    @Test
    public void isEven_2() {
        assertTrue(IntegerPredicates.isEven().accept(0));
    }

    @Test
    public void isEven_3() {
        assertFalse(IntegerPredicates.isEven().accept(1));
    }

    @Test
    public void attributeIsOdd_1() {
        assertTrue(IntegerPredicates.attributeIsOdd(INT_VALUE).accept(1));
    }

    @Test
    public void attributeIsOdd_2() {
        assertFalse(IntegerPredicates.attributeIsOdd(INT_VALUE).accept(-2));
    }

    @Test
    public void attributeIsEven_1() {
        assertTrue(IntegerPredicates.attributeIsEven(INT_VALUE).accept(-42));
    }

    @Test
    public void attributeIsEven_2() {
        assertTrue(IntegerPredicates.attributeIsEven(INT_VALUE).accept(0));
    }

    @Test
    public void attributeIsEven_3() {
        assertFalse(IntegerPredicates.attributeIsEven(INT_VALUE).accept(1));
    }

    @Test
    public void attributeIsZero_1() {
        assertFalse(IntegerPredicates.attributeIsZero(INT_VALUE).accept(-42));
    }

    @Test
    public void attributeIsZero_2() {
        assertTrue(IntegerPredicates.attributeIsZero(INT_VALUE).accept(0));
    }

    @Test
    public void attributeIsZero_3() {
        assertFalse(IntegerPredicates.attributeIsZero(INT_VALUE).accept(1));
    }

    @Test
    public void attributeIsPositive_1() {
        assertFalse(IntegerPredicates.attributeIsPositive(INT_VALUE).accept(-42));
    }

    @Test
    public void attributeIsPositive_2() {
        assertFalse(IntegerPredicates.attributeIsPositive(INT_VALUE).accept(0));
    }

    @Test
    public void attributeIsPositive_3() {
        assertTrue(IntegerPredicates.attributeIsPositive(INT_VALUE).accept(1));
    }

    @Test
    public void attributeIsNegative_1() {
        assertTrue(IntegerPredicates.attributeIsNegative(INT_VALUE).accept(-42));
    }

    @Test
    public void attributeIsNegative_2() {
        assertFalse(IntegerPredicates.attributeIsNegative(INT_VALUE).accept(0));
    }

    @Test
    public void attributeIsNegative_3() {
        assertFalse(IntegerPredicates.attributeIsNegative(INT_VALUE).accept(1));
    }

    @Test
    public void isZero_1() {
        assertTrue(IntegerPredicates.isZero().accept(0));
    }

    @Test
    public void isZero_2() {
        assertFalse(IntegerPredicates.isZero().accept(1));
    }

    @Test
    public void isZero_3() {
        assertFalse(IntegerPredicates.isZero().accept(-1));
    }

    @Test
    public void isPositive_1() {
        assertFalse(IntegerPredicates.isPositive().accept(0));
    }

    @Test
    public void isPositive_2() {
        assertTrue(IntegerPredicates.isPositive().accept(1));
    }

    @Test
    public void isPositive_3() {
        assertFalse(IntegerPredicates.isPositive().accept(-1));
    }

    @Test
    public void isNegative_1() {
        assertFalse(IntegerPredicates.isNegative().accept(0));
    }

    @Test
    public void isNegative_2() {
        assertFalse(IntegerPredicates.isNegative().accept(1));
    }

    @Test
    public void isNegative_3() {
        assertTrue(IntegerPredicates.isNegative().accept(-1));
    }
}
