package org.eclipse.collections.impl.lazy.primitive;

import java.util.NoSuchElementException;
import org.eclipse.collections.api.BooleanIterable;
import org.eclipse.collections.api.iterator.BooleanIterator;
import org.eclipse.collections.impl.bag.mutable.primitive.BooleanHashBag;
import org.eclipse.collections.impl.block.factory.primitive.BooleanPredicates;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.mutable.primitive.BooleanArrayList;
import org.eclipse.collections.impl.set.mutable.primitive.BooleanHashSet;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReverseBooleanIterableTest_Purified {

    @Test
    public void isEmpty_1() {
        Verify.assertEmpty(new BooleanArrayList().asReversed());
    }

    @Test
    public void isEmpty_2() {
        BooleanIterable iterable = BooleanArrayList.newListWith(false, false, true).asReversed();
        Verify.assertNotEmpty(iterable);
    }

    @Test
    public void size_1() {
        Verify.assertSize(0, new BooleanArrayList().asReversed());
    }

    @Test
    public void size_2() {
        BooleanIterable iterable = BooleanArrayList.newListWith(false, false, true).asReversed();
        Verify.assertSize(3, iterable);
    }

    @Test
    public void anySatisfy_1() {
        assertTrue(BooleanArrayList.newListWith(true, false).asReversed().anySatisfy(BooleanPredicates.equal(false)));
    }

    @Test
    public void anySatisfy_2() {
        assertFalse(BooleanArrayList.newListWith(true).asReversed().anySatisfy(BooleanPredicates.equal(false)));
    }

    @Test
    public void allSatisfy_1() {
        assertFalse(BooleanArrayList.newListWith(true, false).asReversed().allSatisfy(BooleanPredicates.equal(false)));
    }

    @Test
    public void allSatisfy_2() {
        assertTrue(BooleanArrayList.newListWith(false, false).asReversed().allSatisfy(BooleanPredicates.equal(false)));
    }

    @Test
    public void noneSatisfy_1() {
        assertFalse(BooleanArrayList.newListWith(true, false).asReversed().noneSatisfy(BooleanPredicates.equal(false)));
    }

    @Test
    public void noneSatisfy_2() {
        assertTrue(BooleanArrayList.newListWith(false, false).asReversed().noneSatisfy(BooleanPredicates.equal(true)));
    }

    @Test
    public void testToString_1() {
        BooleanIterable iterable = BooleanArrayList.newListWith(false, false, true).asReversed();
        assertEquals("[true, false, false]", iterable.toString());
    }

    @Test
    public void testToString_2() {
        assertEquals("[]", new BooleanArrayList().asReversed().toString());
    }

    @Test
    public void makeString_1_testMerged_1() {
        BooleanIterable iterable = BooleanArrayList.newListWith(false, false, true).asReversed();
        assertEquals("true, false, false", iterable.makeString());
        assertEquals("true", BooleanArrayList.newListWith(true).makeString("/"));
        assertEquals("true/false/false", iterable.makeString("/"));
        assertEquals(iterable.toString(), iterable.makeString("[", ", ", "]"));
    }

    @Test
    public void makeString_5() {
        assertEquals("", new BooleanArrayList().asReversed().makeString());
    }

    @Test
    public void appendString_1() {
        StringBuilder appendable = new StringBuilder();
        new BooleanArrayList().asReversed().appendString(appendable);
        assertEquals("", appendable.toString());
    }

    @Test
    public void appendString_2_testMerged_2() {
        BooleanIterable iterable = BooleanArrayList.newListWith(false, false, true).asReversed();
        StringBuilder appendable2 = new StringBuilder();
        iterable.appendString(appendable2);
        assertEquals("true, false, false", appendable2.toString());
        StringBuilder appendable3 = new StringBuilder();
        iterable.appendString(appendable3, "/");
        assertEquals("true/false/false", appendable3.toString());
        StringBuilder appendable4 = new StringBuilder();
        iterable.appendString(appendable4, "[", ", ", "]");
        assertEquals(iterable.toString(), appendable4.toString());
    }
}
