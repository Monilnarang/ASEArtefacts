package org.eclipse.collections.impl.multimap;

import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.collection.MutableCollection;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.multimap.ImmutableMultimap;
import org.eclipse.collections.api.multimap.Multimap;
import org.eclipse.collections.api.multimap.bag.ImmutableBagMultimap;
import org.eclipse.collections.api.multimap.bag.MutableBagMultimap;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.eclipse.collections.impl.multimap.bag.HashBagMultimap;
import org.eclipse.collections.impl.set.mutable.UnmodifiableMutableSet;
import org.eclipse.collections.impl.test.SerializeTestHelper;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.Tuples;
import org.eclipse.collections.impl.utility.Iterate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public abstract class AbstractImmutableMultimapTestCase_Parameterized {

    protected abstract <K, V> ImmutableMultimap<K, V> classUnderTest();

    protected abstract MutableCollection<String> mutableCollection();

    @Test
    public void size_1() {
        Verify.assertEmpty(this.classUnderTest());
    }

    @Test
    public void allowDuplicates_1() {
        Verify.assertEmpty(this.classUnderTest());
    }

    @Test
    public void noDuplicates_1() {
        Verify.assertEmpty(this.classUnderTest());
    }

    @ParameterizedTest
    @MethodSource("Provider_size_2_testMerged_2_2_2")
    public void size_2_testMerged_2_2_2(int param1, int param2, int param3, int param4, int param5, int param6) {
        ImmutableMultimap<String, String> one = this.<String, String>classUnderTest().newWith(param3, param4);
        Verify.assertSize(param1, one);
        ImmutableMultimap<String, String> two = one.newWith(param5, param6);
        Verify.assertSize(param2, two);
    }

    static public Stream<Arguments> Provider_size_2_testMerged_2_2_2() {
        return Stream.of(arguments(1, 1, 1, 2, 2, 2), arguments(1, 1, 1, 1, 1, 2), arguments(1, 1, 1, 1, 1, 1));
    }
}
