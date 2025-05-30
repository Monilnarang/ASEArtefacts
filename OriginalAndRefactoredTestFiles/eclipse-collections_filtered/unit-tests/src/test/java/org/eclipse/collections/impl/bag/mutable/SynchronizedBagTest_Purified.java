package org.eclipse.collections.impl.bag.mutable;

import java.util.Set;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.bag.Bag;
import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.factory.Bags;
import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.MapIterable;
import org.eclipse.collections.api.multimap.Multimap;
import org.eclipse.collections.api.partition.PartitionMutableCollection;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.tuple.primitive.ObjectIntPair;
import org.eclipse.collections.impl.SynchronizedRichIterable;
import org.eclipse.collections.impl.block.factory.IntegerPredicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.block.factory.primitive.IntPredicates;
import org.eclipse.collections.impl.collection.mutable.AbstractSynchronizedCollectionTestCase;
import org.eclipse.collections.impl.test.SerializeTestHelper;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;
import org.junit.jupiter.api.Test;
import static org.eclipse.collections.impl.factory.Iterables.iBag;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SynchronizedBagTest_Purified extends AbstractSynchronizedCollectionTestCase {

    @Override
    protected <T> MutableBag<T> newWith(T... littleElements) {
        return new SynchronizedBag<>(HashBag.newBagWith(littleElements));
    }

    @Override
    @Test
    public void getFirst_1() {
        assertNotNull(this.newWith(1, 2, 3).getFirst());
    }

    @Override
    @Test
    public void getFirst_2() {
        assertNull(this.newWith().getFirst());
    }

    @Override
    @Test
    public void getLast_1() {
        assertNotNull(this.newWith(1, 2, 3).getLast());
    }

    @Override
    @Test
    public void getLast_2() {
        assertNull(this.newWith().getLast());
    }

    @Override
    @Test
    public void equalsAndHashCode_1() {
        Verify.assertPostSerializedEqualsAndHashCode(this.newWith(1, 1, 1, 2, 2, 3));
    }

    @Override
    @Test
    public void equalsAndHashCode_2() {
        Verify.assertInstanceOf(SynchronizedBag.class, SerializeTestHelper.serializeDeserialize(this.newWith(1, 1, 1, 2, 2, 3)));
    }
}
