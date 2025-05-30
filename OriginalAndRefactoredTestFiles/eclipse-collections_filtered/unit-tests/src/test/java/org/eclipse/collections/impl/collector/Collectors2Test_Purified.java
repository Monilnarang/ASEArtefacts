package org.eclipse.collections.impl.collector;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.eclipse.collections.api.bag.ImmutableBag;
import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.bag.sorted.ImmutableSortedBag;
import org.eclipse.collections.api.bag.sorted.MutableSortedBag;
import org.eclipse.collections.api.bimap.ImmutableBiMap;
import org.eclipse.collections.api.bimap.MutableBiMap;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.collection.primitive.MutableIntCollection;
import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.map.primitive.MutableObjectLongMap;
import org.eclipse.collections.api.map.sorted.ImmutableSortedMap;
import org.eclipse.collections.api.map.sorted.MutableSortedMap;
import org.eclipse.collections.api.multimap.Multimap;
import org.eclipse.collections.api.multimap.bag.ImmutableBagMultimap;
import org.eclipse.collections.api.multimap.bag.MutableBagMultimap;
import org.eclipse.collections.api.multimap.list.ImmutableListMultimap;
import org.eclipse.collections.api.multimap.list.MutableListMultimap;
import org.eclipse.collections.api.multimap.set.ImmutableSetMultimap;
import org.eclipse.collections.api.multimap.set.MutableSetMultimap;
import org.eclipse.collections.api.partition.PartitionMutableCollection;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.set.sorted.ImmutableSortedSet;
import org.eclipse.collections.api.set.sorted.MutableSortedSet;
import org.eclipse.collections.api.stack.ImmutableStack;
import org.eclipse.collections.api.stack.MutableStack;
import org.eclipse.collections.impl.factory.BiMaps;
import org.eclipse.collections.impl.factory.Multimaps;
import org.eclipse.collections.impl.factory.SortedMaps;
import org.eclipse.collections.impl.factory.Stacks;
import org.eclipse.collections.impl.factory.primitive.IntBags;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.partition.bag.PartitionHashBag;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class Collectors2Test_Purified {

    public static final Interval SMALL_INTERVAL = Interval.oneTo(5);

    public static final Interval LARGE_INTERVAL = Interval.oneTo(20000);

    public static final Integer HALF_SIZE = Integer.valueOf(LARGE_INTERVAL.size() / 2);

    private final List<Integer> smallData = new ArrayList<>(SMALL_INTERVAL);

    private final List<Integer> bigData = new ArrayList<>(LARGE_INTERVAL);

    @Test
    public void makeString0_1() {
        assertEquals(SMALL_INTERVAL.makeString(), this.smallData.stream().collect(Collectors2.makeString()));
    }

    @Test
    public void makeString0_2() {
        assertEquals(SMALL_INTERVAL.reduceInPlace(Collectors2.makeString()), this.smallData.stream().collect(Collectors2.makeString()));
    }

    @Test
    public void makeString0_3() {
        assertEquals(LARGE_INTERVAL.makeString(), this.bigData.stream().collect(Collectors2.makeString()));
    }

    @Test
    public void makeString0_4() {
        assertEquals(LARGE_INTERVAL.reduceInPlace(Collectors2.makeString()), this.bigData.stream().collect(Collectors2.makeString()));
    }

    @Test
    public void makeString0Parallel_1() {
        assertEquals(SMALL_INTERVAL.makeString(), this.smallData.parallelStream().collect(Collectors2.makeString()));
    }

    @Test
    public void makeString0Parallel_2() {
        assertEquals(SMALL_INTERVAL.reduceInPlace(Collectors2.makeString()), this.smallData.parallelStream().collect(Collectors2.makeString()));
    }

    @Test
    public void makeString0Parallel_3() {
        assertEquals(LARGE_INTERVAL.makeString(), this.bigData.parallelStream().collect(Collectors2.makeString()));
    }

    @Test
    public void makeString0Parallel_4() {
        assertEquals(LARGE_INTERVAL.reduceInPlace(Collectors2.makeString()), this.bigData.parallelStream().collect(Collectors2.makeString()));
    }

    @Test
    public void makeString1_1() {
        assertEquals(SMALL_INTERVAL.makeString("/"), this.smallData.stream().collect(Collectors2.makeString("/")));
    }

    @Test
    public void makeString1_2() {
        assertEquals(SMALL_INTERVAL.reduceInPlace(Collectors2.makeString("/")), this.smallData.stream().collect(Collectors2.makeString("/")));
    }

    @Test
    public void makeString1_3() {
        assertEquals(LARGE_INTERVAL.makeString("/"), this.bigData.stream().collect(Collectors2.makeString("/")));
    }

    @Test
    public void makeString1_4() {
        assertEquals(LARGE_INTERVAL.reduceInPlace(Collectors2.makeString("/")), this.bigData.stream().collect(Collectors2.makeString("/")));
    }

    @Test
    public void makeString1Parallel_1() {
        assertEquals(SMALL_INTERVAL.makeString("/"), this.smallData.parallelStream().collect(Collectors2.makeString("/")));
    }

    @Test
    public void makeString1Parallel_2() {
        assertEquals(SMALL_INTERVAL.reduceInPlace(Collectors2.makeString("/")), this.smallData.parallelStream().collect(Collectors2.makeString("/")));
    }

    @Test
    public void makeString1Parallel_3() {
        assertEquals(LARGE_INTERVAL.makeString("/"), this.bigData.parallelStream().collect(Collectors2.makeString("/")));
    }

    @Test
    public void makeString1Parallel_4() {
        assertEquals(LARGE_INTERVAL.reduceInPlace(Collectors2.makeString("/")), this.bigData.parallelStream().collect(Collectors2.makeString("/")));
    }

    @Test
    public void makeString3_1() {
        assertEquals(SMALL_INTERVAL.makeString("[", "/", "]"), this.smallData.stream().collect(Collectors2.makeString("[", "/", "]")));
    }

    @Test
    public void makeString3_2() {
        assertEquals(SMALL_INTERVAL.reduceInPlace(Collectors2.makeString("[", "/", "]")), this.smallData.stream().collect(Collectors2.makeString("[", "/", "]")));
    }

    @Test
    public void makeString3_3() {
        assertEquals(LARGE_INTERVAL.makeString("[", "/", "]"), this.bigData.stream().collect(Collectors2.makeString("[", "/", "]")));
    }

    @Test
    public void makeString3_4() {
        assertEquals(LARGE_INTERVAL.reduceInPlace(Collectors2.makeString("[", "/", "]")), this.bigData.stream().collect(Collectors2.makeString("[", "/", "]")));
    }

    @Test
    public void makeString3Parallel_1() {
        assertEquals(SMALL_INTERVAL.makeString("[", "/", "]"), this.smallData.parallelStream().collect(Collectors2.makeString("[", "/", "]")));
    }

    @Test
    public void makeString3Parallel_2() {
        assertEquals(SMALL_INTERVAL.reduceInPlace(Collectors2.makeString("[", "/", "]")), this.smallData.parallelStream().collect(Collectors2.makeString("[", "/", "]")));
    }

    @Test
    public void makeString3Parallel_3() {
        assertEquals(LARGE_INTERVAL.makeString("[", "/", "]"), this.bigData.parallelStream().collect(Collectors2.makeString("[", "/", "]")));
    }

    @Test
    public void makeString3Parallel_4() {
        assertEquals(LARGE_INTERVAL.reduceInPlace(Collectors2.makeString("[", "/", "]")), this.bigData.parallelStream().collect(Collectors2.makeString("[", "/", "]")));
    }
}
