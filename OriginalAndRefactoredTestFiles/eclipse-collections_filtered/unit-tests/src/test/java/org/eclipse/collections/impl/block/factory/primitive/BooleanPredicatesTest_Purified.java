package org.eclipse.collections.impl.block.factory.primitive;

import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class BooleanPredicatesTest_Purified {

    @Test
    public void testEqual_1() {
        assertTrue(BooleanPredicates.equal(true).accept(true));
    }

    @Test
    public void testEqual_2() {
        assertTrue(BooleanPredicates.equal(false).accept(false));
    }

    @Test
    public void testEqual_3() {
        assertFalse(BooleanPredicates.equal(true).accept(false));
    }

    @Test
    public void testEqual_4() {
        assertFalse(BooleanPredicates.equal(false).accept(true));
    }

    @Test
    public void testIsTrue_1() {
        assertTrue(BooleanPredicates.isTrue().accept(true));
    }

    @Test
    public void testIsTrue_2() {
        assertFalse(BooleanPredicates.isTrue().accept(false));
    }

    @Test
    public void testIsFalse_1() {
        assertTrue(BooleanPredicates.isFalse().accept(false));
    }

    @Test
    public void testIsFalse_2() {
        assertFalse(BooleanPredicates.isFalse().accept(true));
    }

    @Test
    public void testNot_1() {
        assertTrue(BooleanPredicates.not(BooleanPredicates.isTrue()).accept(false));
    }

    @Test
    public void testNot_2() {
        assertFalse(BooleanPredicates.not(BooleanPredicates.isTrue()).accept(true));
    }

    @Test
    public void testNot_3() {
        assertTrue(BooleanPredicates.not(BooleanPredicates.isFalse()).accept(true));
    }

    @Test
    public void testNot_4() {
        assertFalse(BooleanPredicates.not(BooleanPredicates.isFalse()).accept(false));
    }

    @Test
    public void testNot_5() {
        assertTrue(BooleanPredicates.not(true).accept(false));
    }

    @Test
    public void testNot_6() {
        assertFalse(BooleanPredicates.not(true).accept(true));
    }

    @Test
    public void testNot_7() {
        assertTrue(BooleanPredicates.not(false).accept(true));
    }

    @Test
    public void testNot_8() {
        assertFalse(BooleanPredicates.not(false).accept(false));
    }

    @Test
    public void testAlwaysTrue_1() {
        assertTrue(BooleanPredicates.alwaysTrue().accept(false));
    }

    @Test
    public void testAlwaysTrue_2() {
        assertTrue(BooleanPredicates.alwaysTrue().accept(true));
    }

    @Test
    public void testAlwaysFalse_1() {
        assertFalse(BooleanPredicates.alwaysFalse().accept(false));
    }

    @Test
    public void testAlwaysFalse_2() {
        assertFalse(BooleanPredicates.alwaysFalse().accept(true));
    }
}
