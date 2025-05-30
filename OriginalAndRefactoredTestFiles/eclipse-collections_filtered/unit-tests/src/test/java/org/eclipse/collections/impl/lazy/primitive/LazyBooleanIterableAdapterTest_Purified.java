package org.eclipse.collections.impl.lazy.primitive;

import java.util.Arrays;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.iterator.BooleanIterator;
import org.eclipse.collections.impl.bag.mutable.primitive.BooleanHashBag;
import org.eclipse.collections.impl.block.factory.primitive.BooleanPredicates;
import org.eclipse.collections.impl.factory.primitive.BooleanLists;
import org.eclipse.collections.impl.factory.primitive.ByteLists;
import org.eclipse.collections.impl.factory.primitive.CharLists;
import org.eclipse.collections.impl.factory.primitive.DoubleLists;
import org.eclipse.collections.impl.factory.primitive.FloatLists;
import org.eclipse.collections.impl.factory.primitive.IntLists;
import org.eclipse.collections.impl.factory.primitive.LongLists;
import org.eclipse.collections.impl.factory.primitive.ShortLists;
import org.eclipse.collections.impl.list.mutable.primitive.BooleanArrayList;
import org.eclipse.collections.impl.set.mutable.primitive.BooleanHashSet;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LazyBooleanIterableAdapterTest_Purified {

    private final LazyBooleanIterableAdapter iterable = new LazyBooleanIterableAdapter(BooleanArrayList.newListWith(true, false, true));

    @Test
    public void empty_1() {
        assertFalse(this.iterable.isEmpty());
    }

    @Test
    public void empty_2() {
        assertTrue(this.iterable.notEmpty());
    }

    @Test
    public void empty_3() {
        Verify.assertNotEmpty(this.iterable);
    }

    @Test
    public void count_1() {
        assertEquals(2, this.iterable.count(BooleanPredicates.isTrue()));
    }

    @Test
    public void count_2() {
        assertEquals(1, this.iterable.count(BooleanPredicates.isFalse()));
    }

    @Test
    public void anySatisfy_1() {
        assertTrue(this.iterable.anySatisfy(BooleanPredicates.isTrue()));
    }

    @Test
    public void anySatisfy_2() {
        assertTrue(this.iterable.anySatisfy(BooleanPredicates.isFalse()));
    }

    @Test
    public void select_1() {
        Verify.assertSize(2, this.iterable.select(BooleanPredicates.isTrue()));
    }

    @Test
    public void select_2() {
        Verify.assertSize(1, this.iterable.select(BooleanPredicates.isFalse()));
    }

    @Test
    public void reject_1() {
        Verify.assertSize(1, this.iterable.reject(BooleanPredicates.isTrue()));
    }

    @Test
    public void reject_2() {
        Verify.assertSize(2, this.iterable.reject(BooleanPredicates.isFalse()));
    }

    @Test
    public void contains_1() {
        assertTrue(this.iterable.contains(true));
    }

    @Test
    public void contains_2() {
        assertTrue(this.iterable.contains(false));
    }

    @Test
    public void containsAllArray_1() {
        assertTrue(this.iterable.containsAll(true, false));
    }

    @Test
    public void containsAllArray_2() {
        assertTrue(this.iterable.containsAll(false, true));
    }

    @Test
    public void containsAllArray_3() {
        assertTrue(this.iterable.containsAll());
    }

    @Test
    public void containsAllIterable_1() {
        assertTrue(this.iterable.containsAll(BooleanArrayList.newListWith(true)));
    }

    @Test
    public void containsAllIterable_2() {
        assertTrue(this.iterable.containsAll(BooleanArrayList.newListWith(false)));
    }

    @Test
    public void containsAllIterable_3() {
        assertTrue(this.iterable.containsAll(BooleanArrayList.newListWith(true, false)));
    }

    @Test
    public void containsAllIterable_4() {
        assertTrue(this.iterable.containsAll(BooleanArrayList.newListWith(false, true)));
    }
}
