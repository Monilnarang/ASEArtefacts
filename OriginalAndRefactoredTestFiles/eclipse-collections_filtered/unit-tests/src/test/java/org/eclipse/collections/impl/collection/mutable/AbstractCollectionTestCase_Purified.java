package org.eclipse.collections.impl.collection.mutable;

import java.util.Collections;
import org.eclipse.collections.api.collection.ImmutableCollection;
import org.eclipse.collections.api.collection.MutableCollection;
import org.eclipse.collections.api.factory.Bags;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.multimap.bag.MutableBagMultimap;
import org.eclipse.collections.api.tuple.Twin;
import org.eclipse.collections.impl.AbstractRichIterableTestCase;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.collector.Collectors2;
import org.eclipse.collections.impl.factory.Multimaps;
import org.eclipse.collections.impl.lazy.LazyIterableAdapter;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.eclipse.collections.impl.factory.Iterables.iList;
import static org.eclipse.collections.impl.factory.Iterables.mSet;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractCollectionTestCase_Purified extends AbstractRichIterableTestCase {

    @Override
    protected abstract <T> MutableCollection<T> newWith(T... littleElements);

    @Test
    public void toImmutable_1() {
        Verify.assertInstanceOf(MutableCollection.class, this.newWith());
    }

    @Test
    public void toImmutable_2() {
        Verify.assertInstanceOf(ImmutableCollection.class, this.newWith().toImmutable());
    }

    @Test
    public void removeAll_1_testMerged_1() {
        MutableCollection<Integer> objects = this.newWith(1, 2, 3);
        assertTrue(objects.removeAll(FastList.newListWith(1, 2, 4)));
        assertEquals(Bags.mutable.of(3), objects.toBag());
    }

    @Test
    public void removeAll_3_testMerged_2() {
        MutableCollection<Integer> objects2 = this.newWith(1, 2, 3);
        assertFalse(objects2.removeAll(FastList.newListWith(4, 5)));
        assertEquals(Bags.mutable.of(1, 2, 3), objects2.toBag());
    }

    @Test
    public void removeAllIterable_1_testMerged_1() {
        MutableCollection<Integer> objects = this.newWith(1, 2, 3);
        assertTrue(objects.removeAllIterable(FastList.newListWith(1, 2, 4)));
        assertEquals(Bags.mutable.of(3), objects.toBag());
    }

    @Test
    public void removeAllIterable_3_testMerged_2() {
        MutableCollection<Integer> objects2 = this.newWith(1, 2, 3);
        assertFalse(objects2.removeAllIterable(FastList.newListWith(4, 5)));
        assertEquals(Bags.mutable.of(1, 2, 3), objects2.toBag());
    }

    @Test
    public void retainAll_1_testMerged_1() {
        MutableCollection<Integer> objects = this.newWith(1, 2, 3);
        assertTrue(objects.retainAll(mSet(1, 2)));
        Verify.assertSize(2, objects);
        Verify.assertContainsAll(objects, 1, 2);
    }

    @Test
    public void retainAll_4_testMerged_2() {
        MutableCollection<Integer> integers1 = this.newWith(0);
        assertFalse(integers1.retainAll(FastList.newListWith(1, 0)));
        assertEquals(Bags.mutable.of(0), integers1.toBag());
    }

    @Test
    public void retainAll_6_testMerged_3() {
        MutableCollection<Integer> integers2 = this.newWith(1, 2, 3);
        Integer copy = new Integer(1);
        assertTrue(integers2.retainAll(FastList.newListWith(copy)));
        assertEquals(Bags.mutable.of(1), integers2.toBag());
        assertNotSame(copy, integers2.getFirst());
    }

    @Test
    public void retainAllIterable_1_testMerged_1() {
        MutableCollection<Integer> objects = this.newWith(1, 2, 3);
        assertTrue(objects.retainAllIterable(iList(1, 2)));
        Verify.assertSize(2, objects);
        Verify.assertContainsAll(objects, 1, 2);
    }

    @Test
    public void retainAllIterable_4_testMerged_2() {
        MutableCollection<Integer> integers = this.newWith(0);
        assertFalse(integers.retainAllIterable(FastList.newListWith(1, 0)));
        assertEquals(Bags.mutable.of(0), integers.toBag());
    }

    @Test
    public void removeIf_1_testMerged_1() {
        MutableCollection<Integer> objects1 = this.newWith(1, 2, 3);
        objects1.add(null);
        assertTrue(objects1.removeIf(Predicates.isNull()));
        Verify.assertSize(3, objects1);
        Verify.assertContainsAll(objects1, 1, 2, 3);
    }

    @Test
    public void removeIf_4_testMerged_2() {
        MutableCollection<Integer> objects2 = this.newWith(3, 4, 5);
        assertTrue(objects2.removeIf(Predicates.equal(3)));
        assertFalse(objects2.removeIf(Predicates.equal(6)));
    }

    @Test
    public void removeIf_6_testMerged_3() {
        MutableCollection<Integer> objects3 = this.newWith(1, 2, 3, 4, 5);
        assertTrue(objects3.removeIf(Predicates.greaterThan(0)));
        assertFalse(objects3.removeIf(Predicates.equal(5)));
    }

    @Test
    public void removeIfWith_1_testMerged_1() {
        MutableCollection<Integer> objects1 = this.newWith(1, 2, 3, 4);
        assertTrue(objects1.removeIfWith(Predicates2.lessThan(), 3));
        Verify.assertSize(2, objects1);
        Verify.assertContainsAll(objects1, 3, 4);
        assertFalse(objects1.removeIfWith(Predicates2.greaterThan(), 6));
    }

    @Test
    public void removeIfWith_5_testMerged_2() {
        MutableCollection<Integer> objects2 = this.newWith(1, 2, 3, 4, 5);
        assertTrue(objects2.removeIfWith(Predicates2.greaterThan(), 0));
        assertFalse(objects2.removeIfWith(Predicates2.greaterThan(), 3));
    }
}
