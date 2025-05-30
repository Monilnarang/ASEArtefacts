package org.eclipse.collections.impl;

import java.util.Iterator;
import java.util.Map;
import org.eclipse.collections.api.collection.ImmutableCollection;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.procedure.CollectionAddProcedure;
import org.eclipse.collections.impl.collection.mutable.UnmodifiableMutableCollectionTestCase;
import org.eclipse.collections.impl.set.mutable.SetAdapter;
import org.eclipse.collections.impl.set.mutable.primitive.BooleanHashSet;
import org.eclipse.collections.impl.set.mutable.primitive.ByteHashSet;
import org.eclipse.collections.impl.set.mutable.primitive.CharHashSet;
import org.eclipse.collections.impl.set.mutable.primitive.DoubleHashSet;
import org.eclipse.collections.impl.set.mutable.primitive.FloatHashSet;
import org.eclipse.collections.impl.set.mutable.primitive.IntHashSet;
import org.eclipse.collections.impl.set.mutable.primitive.LongHashSet;
import org.eclipse.collections.impl.set.mutable.primitive.ShortHashSet;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.ImmutableEntry;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UnmodifiableMapEntrySetTest_Purified extends UnmodifiableMutableCollectionTestCase<Map.Entry<String, String>> {

    @Override
    protected MutableSet<Map.Entry<String, String>> getCollection() {
        return SetAdapter.adapt(new UnmodifiableMap<>(Maps.mutable.of("1", "1", "2", "2")).entrySet());
    }

    private MutableSet<Map.Entry<String, String>> newCollection() {
        return SetAdapter.adapt(new UnmodifiableMap<>(Maps.mutable.<String, String>of()).entrySet());
    }

    private <T> MutableSet<Map.Entry<T, T>> newWith(T one) {
        MutableMap<T, T> map = Maps.mutable.of(one, one);
        return SetAdapter.adapt(new UnmodifiableMap<>(map).entrySet());
    }

    private <T> MutableSet<Map.Entry<T, T>> newWith(T one, T two) {
        MutableMap<T, T> map = Maps.mutable.of(one, one, two, two);
        return SetAdapter.adapt(new UnmodifiableMap<>(map).entrySet());
    }

    private <T> MutableSet<Map.Entry<T, T>> newWith(T one, T two, T three) {
        MutableMap<T, T> map = Maps.mutable.of(one, one, two, two, three, three);
        return SetAdapter.adapt(new UnmodifiableMap<>(map).entrySet());
    }

    private <T> MutableSet<Map.Entry<T, T>> newWith(T... littleElements) {
        MutableMap<T, T> map = Maps.mutable.of();
        for (int i = 0; i < littleElements.length; i++) {
            map.put(littleElements[i], littleElements[i]);
        }
        return SetAdapter.adapt(new UnmodifiableMap<>(map).entrySet());
    }

    private ImmutableEntry<Integer, Integer> entry(int i) {
        return ImmutableEntry.of(i, i);
    }

    @Test
    public void equalsAndHashCode_1() {
        Verify.assertEqualsAndHashCode(this.newWith(1, 2, 3), this.newWith(1, 2, 3));
    }

    @Test
    public void equalsAndHashCode_2() {
        assertNotEquals(this.newWith(1, 2, 3), this.newWith(1, 2));
    }

    @Test
    public void isEmpty_1() {
        Verify.assertEmpty(this.newCollection());
    }

    @Test
    public void isEmpty_2() {
        Verify.assertNotEmpty(this.newWith(1, 2));
    }

    @Test
    public void isEmpty_3() {
        assertTrue(this.newWith(1, 2).notEmpty());
    }
}
