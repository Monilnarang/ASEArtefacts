package org.eclipse.collections.impl.set.sorted.immutable;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeSet;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.set.sorted.ImmutableSortedSet;
import org.eclipse.collections.api.set.sorted.SortedSetIterable;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.block.factory.PrimitiveFunctions;
import org.eclipse.collections.impl.factory.SortedSets;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.mutable.primitive.BooleanArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.ByteArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.CharArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.DoubleArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.FloatArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.LongArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.ShortArrayList;
import org.eclipse.collections.impl.set.sorted.mutable.TreeSortedSet;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ImmutableTreeSetTest_Purified extends AbstractImmutableSortedSetTestCase {

    @Override
    protected ImmutableSortedSet<Integer> classUnderTest() {
        return ImmutableTreeSet.newSetWith(1, 2, 3, 4);
    }

    @Override
    protected ImmutableSortedSet<Integer> classUnderTest(Comparator<? super Integer> comparator) {
        return ImmutableTreeSet.newSetWith(comparator, 1, 2, 3, 4);
    }

    @Override
    @Test
    public void equalsAndHashCode_1() {
        assertNotEquals(new TreeSet<>(Arrays.asList("1", "2", "3")), new TreeSet<>(Arrays.asList(1, 2, 3)));
    }

    @Override
    @Test
    public void equalsAndHashCode_2() {
        assertNotEquals(new TreeSet<>(Arrays.asList("1", "2", "3")), Sets.immutable.of("1", "2", null));
    }

    @Override
    @Test
    public void equalsAndHashCode_3() {
        assertNotEquals(SortedSets.immutable.of("1", "2", "3"), SortedSets.immutable.of(1, 2, 3));
    }
}
