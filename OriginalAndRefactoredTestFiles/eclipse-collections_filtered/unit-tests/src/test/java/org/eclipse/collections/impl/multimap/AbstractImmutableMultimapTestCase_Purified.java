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

public abstract class AbstractImmutableMultimapTestCase_Purified {

    protected abstract <K, V> ImmutableMultimap<K, V> classUnderTest();

    protected abstract MutableCollection<String> mutableCollection();

    @Test
    public void size_1() {
        Verify.assertEmpty(this.classUnderTest());
    }

    @Test
    public void size_2_testMerged_2() {
        ImmutableMultimap<String, String> one = this.<String, String>classUnderTest().newWith("1", "1");
        Verify.assertSize(1, one);
        ImmutableMultimap<String, String> two = one.newWith("2", "2");
        Verify.assertSize(2, two);
    }

    @Test
    public void allowDuplicates_1() {
        Verify.assertEmpty(this.classUnderTest());
    }

    @Test
    public void allowDuplicates_2_testMerged_2() {
        ImmutableMultimap<String, String> one = this.<String, String>classUnderTest().newWith("1", "1");
        Verify.assertSize(1, one);
        ImmutableMultimap<String, String> two = one.newWith("1", "1");
        Verify.assertSize(2, two);
    }

    @Test
    public void noDuplicates_1() {
        Verify.assertEmpty(this.classUnderTest());
    }

    @Test
    public void noDuplicates_2_testMerged_2() {
        ImmutableMultimap<String, String> one = this.<String, String>classUnderTest().newWith("1", "1");
        Verify.assertSize(1, one);
        ImmutableMultimap<String, String> two = one.newWith("1", "1");
        Verify.assertSize(1, two);
    }
}
