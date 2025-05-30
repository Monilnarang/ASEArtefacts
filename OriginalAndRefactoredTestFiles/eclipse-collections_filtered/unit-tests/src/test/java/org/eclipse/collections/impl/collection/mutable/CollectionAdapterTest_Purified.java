package org.eclipse.collections.impl.collection.mutable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.collection.MutableCollection;
import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.multimap.Multimap;
import org.eclipse.collections.api.multimap.MutableMultimap;
import org.eclipse.collections.api.partition.PartitionMutableCollection;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.impl.bag.mutable.HashBag;
import org.eclipse.collections.impl.block.factory.IntegerPredicates;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.block.function.NegativeIntervalFunction;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.fixed.ArrayAdapter;
import org.eclipse.collections.impl.list.mutable.ArrayListAdapter;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.mutable.ListAdapter;
import org.eclipse.collections.impl.list.mutable.RandomAccessListAdapter;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.eclipse.collections.impl.set.mutable.SetAdapter;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.test.SerializeTestHelper;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.eclipse.collections.impl.factory.Iterables.iList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CollectionAdapterTest_Purified extends AbstractCollectionTestCase {

    @Override
    protected <T> CollectionAdapter<T> newWith(T... littleElements) {
        return new CollectionAdapter<>(new ArrayList<>(FastList.newListWith(littleElements)));
    }

    private <T> CollectionAdapter<T> newSet() {
        return new CollectionAdapter<>(UnifiedSet.newSet());
    }

    private <T> CollectionAdapter<T> newList() {
        return new CollectionAdapter<>(FastList.newList());
    }

    @Override
    @Test
    public void toImmutable_1() {
        Verify.assertInstanceOf(ImmutableList.class, new CollectionAdapter<>(Collections.singletonList("1")).toImmutable());
    }

    @Override
    @Test
    public void toImmutable_2() {
        Verify.assertInstanceOf(ImmutableSet.class, new CollectionAdapter<>(Collections.singleton("1")).toImmutable());
    }

    @Override
    @Test
    public void select_1() {
        Verify.assertContainsAll(this.<Integer>newSet().with(1, 2, 3, 4, 5).select(Predicates.lessThan(3)), 1, 2);
    }

    @Override
    @Test
    public void select_2() {
        Verify.assertContainsAll(this.<Integer>newSet().with(-1, 2, 3, 4, 5).select(Predicates.lessThan(3), FastList.newList()), -1, 2);
    }

    @Override
    @Test
    public void reject_1() {
        Verify.assertContainsAll(this.<Integer>newSet().with(1, 2, 3, 4).reject(Predicates.lessThan(3)), 3, 4);
    }

    @Override
    @Test
    public void reject_2() {
        Verify.assertContainsAll(this.<Integer>newSet().with(1, 2, 3, 4).reject(Predicates.lessThan(3), FastList.newList()), 3, 4);
    }

    @Test
    public void adapt_1() {
        Verify.assertInstanceOf(FastList.class, CollectionAdapter.adapt(FastList.newList()));
    }

    @Test
    public void adapt_2() {
        Verify.assertInstanceOf(ArrayListAdapter.class, CollectionAdapter.adapt(new ArrayList<>()));
    }

    @Test
    public void adapt_3() {
        Verify.assertInstanceOf(SetAdapter.class, CollectionAdapter.adapt(new HashSet<>()));
    }

    @Test
    public void adapt_4() {
        Verify.assertInstanceOf(UnifiedSet.class, CollectionAdapter.adapt(UnifiedSet.newSet()));
    }

    @Test
    public void adapt_5() {
        Verify.assertInstanceOf(RandomAccessListAdapter.class, CollectionAdapter.adapt(Collections.emptyList()));
    }

    @Test
    public void adapt_6() {
        Verify.assertInstanceOf(ListAdapter.class, CollectionAdapter.adapt(new LinkedList<>()));
    }

    @Test
    public void adapt_7() {
        Verify.assertInstanceOf(ArrayAdapter.class, CollectionAdapter.adapt(ArrayAdapter.newArray()));
    }

    @Test
    public void wrapSet_1() {
        Verify.assertInstanceOf(SetAdapter.class, CollectionAdapter.wrapSet(new HashSet<>()));
    }

    @Test
    public void wrapSet_2() {
        Verify.assertInstanceOf(UnifiedSet.class, CollectionAdapter.wrapSet(new FastList<>()));
    }

    @Test
    public void wrapList_1() {
        Verify.assertInstanceOf(ArrayListAdapter.class, CollectionAdapter.wrapList(new ArrayList<>()));
    }

    @Test
    public void wrapList_2() {
        Verify.assertInstanceOf(FastList.class, CollectionAdapter.wrapList(new HashSet<>()));
    }

    @Test
    public void wrapList_3() {
        Verify.assertInstanceOf(FastList.class, CollectionAdapter.wrapList(FastList.newList()));
    }

    @Test
    public void testEquals_1() {
        assertEquals(new CollectionAdapter<>(FastList.newList()), new CollectionAdapter<>(FastList.newList()));
    }

    @Test
    public void testEquals_2() {
        assertNotEquals(new CollectionAdapter<>(FastList.newList()), new CollectionAdapter<>(FastList.newListWith(1)));
    }

    @Test
    public void testEquals_3() {
        assertEquals(new CollectionAdapter<>(FastList.newListWith(1)), new CollectionAdapter<>(FastList.newListWith(1)));
    }

    @Test
    public void testEquals_4() {
        assertNotEquals(new CollectionAdapter<>(FastList.newListWith(1)), new CollectionAdapter<>(FastList.newListWith(2)));
    }

    @Test
    public void testNewEmpty_1() {
        Verify.assertInstanceOf(UnifiedSet.class, new CollectionAdapter<>(new HashSet<>()).newEmpty());
    }

    @Test
    public void testNewEmpty_2() {
        Verify.assertInstanceOf(FastList.class, new CollectionAdapter<>(new ArrayList<>()).newEmpty());
    }
}
