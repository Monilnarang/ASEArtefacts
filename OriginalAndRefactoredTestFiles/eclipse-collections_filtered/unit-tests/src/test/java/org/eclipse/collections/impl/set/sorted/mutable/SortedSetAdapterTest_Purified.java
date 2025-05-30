package org.eclipse.collections.impl.set.sorted.mutable;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.TreeSet;
import org.eclipse.collections.api.collection.MutableCollection;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.set.sorted.MutableSortedSet;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.factory.SortedSets;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.test.SerializeTestHelper;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public class SortedSetAdapterTest_Purified extends AbstractSortedSetTestCase {

    @Override
    protected <T> SortedSetAdapter<T> newWith(T... elements) {
        return new SortedSetAdapter<>(new TreeSet<>(FastList.newListWith(elements)));
    }

    @Override
    protected <T> SortedSetAdapter<T> newWith(Comparator<? super T> comparator, T... elements) {
        TreeSet<T> set = new TreeSet<>(comparator);
        set.addAll(FastList.newListWith(elements));
        return new SortedSetAdapter<>(set);
    }

    @Test
    public void withMethods_1() {
        Verify.assertContainsAll(this.newWith().with(1), 1);
    }

    @Test
    public void withMethods_2() {
        Verify.assertContainsAll(this.newWith().with(1, 2), 1, 2);
    }

    @Test
    public void withMethods_3() {
        Verify.assertContainsAll(this.newWith().with(1, 2, 3), 1, 2, 3);
    }

    @Test
    public void withMethods_4() {
        Verify.assertContainsAll(this.newWith().with(1, 2, 3, 4), 1, 2, 3, 4);
    }
}
