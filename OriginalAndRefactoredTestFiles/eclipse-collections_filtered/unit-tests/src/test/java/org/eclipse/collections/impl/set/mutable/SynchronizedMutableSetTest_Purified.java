package org.eclipse.collections.impl.set.mutable;

import java.util.Collections;
import java.util.TreeSet;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.bag.sorted.ImmutableSortedBag;
import org.eclipse.collections.api.bag.sorted.MutableSortedBag;
import org.eclipse.collections.api.collection.MutableCollection;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.bag.sorted.mutable.TreeBag;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.collection.mutable.AbstractSynchronizedCollectionTestCase;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SynchronizedMutableSetTest_Purified extends AbstractSynchronizedCollectionTestCase {

    @Override
    protected <T> SynchronizedMutableSet<T> newWith(T... littleElements) {
        return new SynchronizedMutableSet<>(SetAdapter.adapt(new TreeSet<>(FastList.newListWith(littleElements))));
    }

    @Override
    @Test
    public void equalsAndHashCode_1() {
        Verify.assertPostSerializedEqualsAndHashCode(this.newWith(1, 2, 3));
    }

    @Override
    @Test
    public void equalsAndHashCode_2() {
        Verify.assertInstanceOf(SynchronizedMutableSet.class, this.newWith(1, 2, 3));
    }
}
