package org.eclipse.collections.impl.bag.mutable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.bag.Bag;
import org.eclipse.collections.api.bag.ImmutableBagIterable;
import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.bag.MutableBagIterable;
import org.eclipse.collections.api.factory.Bags;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.partition.PartitionMutableCollection;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.api.tuple.primitive.ObjectIntPair;
import org.eclipse.collections.impl.block.factory.IntegerPredicates;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.block.factory.primitive.IntPredicates;
import org.eclipse.collections.impl.block.procedure.CollectionAddProcedure;
import org.eclipse.collections.impl.collection.mutable.AbstractCollectionTestCase;
import org.eclipse.collections.impl.factory.Iterables;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.eclipse.collections.impl.math.IntegerSum;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.Tuples;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class MutableBagTestCase_Purified extends AbstractCollectionTestCase {

    @Override
    protected abstract <T> MutableBagIterable<T> newWith(T... littleElements);

    protected abstract <T> MutableBagIterable<T> newWithOccurrences(ObjectIntPair<T>... elementsWithOccurrences);

    private void assertIteratorRemove(MutableBagIterable<Integer> bag, Iterator<Integer> iterator, MutableBagIterable<Integer> expected) {
        assertTrue(iterator.hasNext());
        Integer first = iterator.next();
        iterator.remove();
        expected.remove(first);
        assertEquals(expected, bag);
        assertThrows(IllegalStateException.class, iterator::remove);
    }

    protected static void assertBagsEqual(Bag<?> expected, Bag<?> actual) {
        assertEquals(expected.toMapOfItemToCount(), actual.toMapOfItemToCount());
        assertEquals(expected.sizeDistinct(), actual.sizeDistinct());
        assertEquals(expected.size(), actual.size());
        Verify.assertEqualsAndHashCode(expected, actual);
    }

    @Test
    @Override
    public void equalsAndHashCode_1() {
        assertNotEquals(this.newWith(1, 1, 2, 3), this.newWith(1, 2, 2, 3));
    }

    @Test
    @Override
    public void equalsAndHashCode_2() {
        Verify.assertEqualsAndHashCode(this.newWith(null, null, 2, 3), this.newWith(null, 2, null, 3));
    }

    @Test
    @Override
    public void equalsAndHashCode_3() {
        assertEquals(this.newWith(1, 1, 2, 3).toMapOfItemToCount().hashCode(), this.newWith(1, 1, 2, 3).hashCode());
    }

    @Test
    @Override
    public void equalsAndHashCode_4() {
        assertEquals(this.newWith(null, null, 2, 3).toMapOfItemToCount().hashCode(), this.newWith(null, null, 2, 3).hashCode());
    }

    @Test
    public void toStringOfItemToCount_1() {
        assertEquals("{}", this.newWith().toStringOfItemToCount());
    }

    @Test
    public void toStringOfItemToCount_2() {
        assertEquals("{1=3}", this.newWith(1, 1, 1).toStringOfItemToCount());
    }

    @Test
    public void toStringOfItemToCount_3() {
        String actual = this.newWith(1, 2, 2).toStringOfItemToCount();
        assertTrue("{1=1, 2=2}".equals(actual) || "{2=2, 1=1}".equals(actual));
    }

    @Override
    @Test
    public void toImmutable_1() {
        Verify.assertInstanceOf(MutableBagIterable.class, this.newWith());
    }

    @Override
    @Test
    public void toImmutable_2() {
        Verify.assertInstanceOf(ImmutableBagIterable.class, this.newWith().toImmutable());
    }

    @Override
    @Test
    public void toImmutable_3() {
        assertFalse(this.newWith().toImmutable() instanceof MutableBagIterable);
    }

    @Test
    @Override
    public void getLast_1() {
        assertEquals(Integer.valueOf(1), this.newWith(1).getLast());
    }

    @Test
    @Override
    public void getLast_2() {
        assertEquals(Integer.valueOf(3), this.newWith(3).getLast());
    }
}
