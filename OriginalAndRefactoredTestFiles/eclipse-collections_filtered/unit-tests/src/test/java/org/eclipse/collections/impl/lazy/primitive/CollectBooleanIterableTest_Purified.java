package org.eclipse.collections.impl.lazy.primitive;

import org.eclipse.collections.api.BooleanIterable;
import org.eclipse.collections.api.LazyBooleanIterable;
import org.eclipse.collections.api.iterator.BooleanIterator;
import org.eclipse.collections.impl.bag.mutable.primitive.BooleanHashBag;
import org.eclipse.collections.impl.block.factory.PrimitiveFunctions;
import org.eclipse.collections.impl.block.factory.primitive.BooleanPredicates;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.mutable.primitive.BooleanArrayList;
import org.eclipse.collections.impl.set.mutable.primitive.BooleanHashSet;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CollectBooleanIterableTest_Purified {

    private final BooleanIterable booleanIterable = Interval.zeroTo(2).collectBoolean(PrimitiveFunctions.integerIsPositive());

    @Test
    public void empty_1() {
        assertTrue(this.booleanIterable.notEmpty());
    }

    @Test
    public void empty_2() {
        assertFalse(this.booleanIterable.isEmpty());
    }

    @Test
    public void count_1() {
        assertEquals(2, this.booleanIterable.count(BooleanPredicates.equal(true)));
    }

    @Test
    public void count_2() {
        assertEquals(1, this.booleanIterable.count(BooleanPredicates.equal(false)));
    }

    @Test
    public void select_1() {
        assertEquals(2, this.booleanIterable.select(BooleanPredicates.equal(true)).size());
    }

    @Test
    public void select_2() {
        assertEquals(1, this.booleanIterable.select(BooleanPredicates.equal(false)).size());
    }

    @Test
    public void reject_1() {
        assertEquals(1, this.booleanIterable.reject(BooleanPredicates.equal(true)).size());
    }

    @Test
    public void reject_2() {
        assertEquals(2, this.booleanIterable.reject(BooleanPredicates.equal(false)).size());
    }

    @Test
    public void contains_1() {
        assertFalse(Interval.fromTo(-4, 0).collectBoolean(PrimitiveFunctions.integerIsPositive()).contains(true));
    }

    @Test
    public void contains_2() {
        assertTrue(Interval.fromTo(-2, 2).collectBoolean(PrimitiveFunctions.integerIsPositive()).contains(true));
    }

    @Test
    public void makeString_1() {
        assertEquals("false, true, true", this.booleanIterable.makeString());
    }

    @Test
    public void makeString_2() {
        assertEquals("false/true/true", this.booleanIterable.makeString("/"));
    }

    @Test
    public void makeString_3() {
        assertEquals("[false, true, true]", this.booleanIterable.makeString("[", ", ", "]"));
    }

    @Test
    public void appendString_1() {
        StringBuilder appendable = new StringBuilder();
        this.booleanIterable.appendString(appendable);
        assertEquals("false, true, true", appendable.toString());
    }

    @Test
    public void appendString_2() {
        StringBuilder appendable2 = new StringBuilder();
        this.booleanIterable.appendString(appendable2, "/");
        assertEquals("false/true/true", appendable2.toString());
    }

    @Test
    public void appendString_3() {
        StringBuilder appendable3 = new StringBuilder();
        this.booleanIterable.appendString(appendable3, "[", ", ", "]");
        assertEquals(this.booleanIterable.toString(), appendable3.toString());
    }

    @Test
    public void asLazy_1() {
        assertEquals(this.booleanIterable.toSet(), this.booleanIterable.asLazy().toSet());
    }

    @Test
    public void asLazy_2() {
        Verify.assertInstanceOf(LazyBooleanIterable.class, this.booleanIterable.asLazy());
    }
}
