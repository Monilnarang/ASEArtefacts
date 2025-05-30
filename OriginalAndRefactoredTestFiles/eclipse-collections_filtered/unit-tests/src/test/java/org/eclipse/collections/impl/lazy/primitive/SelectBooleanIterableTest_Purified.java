package org.eclipse.collections.impl.lazy.primitive;

import org.eclipse.collections.api.iterator.BooleanIterator;
import org.eclipse.collections.impl.block.factory.primitive.BooleanPredicates;
import org.eclipse.collections.impl.list.mutable.primitive.BooleanArrayList;
import org.eclipse.collections.impl.math.MutableInteger;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SelectBooleanIterableTest_Purified {

    private final SelectBooleanIterable iterable = new SelectBooleanIterable(BooleanArrayList.newListWith(true, false, false, true), BooleanPredicates.isTrue());

    @Test
    public void empty_1() {
        assertTrue(this.iterable.notEmpty());
    }

    @Test
    public void empty_2() {
        assertFalse(this.iterable.isEmpty());
    }

    @Test
    public void count_1() {
        assertEquals(2L, this.iterable.count(BooleanPredicates.isTrue()));
    }

    @Test
    public void count_2() {
        assertEquals(0L, this.iterable.count(BooleanPredicates.isFalse()));
    }

    @Test
    public void anySatisfy_1() {
        assertTrue(this.iterable.anySatisfy(BooleanPredicates.isTrue()));
    }

    @Test
    public void anySatisfy_2() {
        assertFalse(this.iterable.anySatisfy(BooleanPredicates.isFalse()));
    }

    @Test
    public void allSatisfy_1() {
        assertTrue(this.iterable.allSatisfy(BooleanPredicates.isTrue()));
    }

    @Test
    public void allSatisfy_2() {
        assertFalse(this.iterable.allSatisfy(BooleanPredicates.isFalse()));
    }

    @Test
    public void select_1() {
        assertEquals(0L, this.iterable.select(BooleanPredicates.isFalse()).size());
    }

    @Test
    public void select_2() {
        assertEquals(2L, this.iterable.select(BooleanPredicates.equal(true)).size());
    }

    @Test
    public void reject_1() {
        assertEquals(2L, this.iterable.reject(BooleanPredicates.isFalse()).size());
    }

    @Test
    public void reject_2() {
        assertEquals(0L, this.iterable.reject(BooleanPredicates.equal(true)).size());
    }

    @Test
    public void detectIfNone_1() {
        assertTrue(this.iterable.detectIfNone(BooleanPredicates.isTrue(), false));
    }

    @Test
    public void detectIfNone_2() {
        assertFalse(this.iterable.detectIfNone(BooleanPredicates.isFalse(), false));
    }

    @Test
    public void toArray_1() {
        assertEquals(2L, this.iterable.toArray().length);
    }

    @Test
    public void toArray_2() {
        assertTrue(this.iterable.toArray()[0]);
    }

    @Test
    public void toArray_3() {
        assertTrue(this.iterable.toArray()[1]);
    }

    @Test
    public void contains_1() {
        assertTrue(this.iterable.contains(true));
    }

    @Test
    public void contains_2() {
        assertFalse(this.iterable.contains(false));
    }

    @Test
    public void containsAll_1() {
        assertTrue(this.iterable.containsAll(true, true));
    }

    @Test
    public void containsAll_2() {
        assertFalse(this.iterable.containsAll(false, true));
    }

    @Test
    public void containsAll_3() {
        assertFalse(this.iterable.containsAll(false, false));
    }
}
