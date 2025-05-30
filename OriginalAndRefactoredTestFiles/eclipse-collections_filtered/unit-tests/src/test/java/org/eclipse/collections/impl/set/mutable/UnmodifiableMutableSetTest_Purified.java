package org.eclipse.collections.impl.set.mutable;

import java.util.Collections;
import java.util.NoSuchElementException;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.bag.sorted.ImmutableSortedBag;
import org.eclipse.collections.api.bag.sorted.MutableSortedBag;
import org.eclipse.collections.api.collection.MutableCollection;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.bag.sorted.mutable.TreeBag;
import org.eclipse.collections.impl.collection.mutable.AbstractCollectionTestCase;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.test.SerializeTestHelper;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UnmodifiableMutableSetTest_Purified extends AbstractCollectionTestCase {

    private static final String LED_ZEPPELIN = "Led Zeppelin";

    private static final String METALLICA = "Metallica";

    private MutableSet<String> mutableSet;

    private MutableSet<String> unmodifiableSet;

    @BeforeEach
    public void setUp() {
        this.mutableSet = UnifiedSet.newSetWith(METALLICA, "Bon Jovi", "Europe", "Scorpions");
        this.unmodifiableSet = this.mutableSet.asUnmodifiable();
    }

    @Override
    protected <T> MutableSet<T> newWith(T... elements) {
        return UnifiedSet.newSetWith(elements).asUnmodifiable();
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

    @Test
    public void testEqualsAndHashCode_1() {
        Verify.assertEqualsAndHashCode(this.mutableSet, this.unmodifiableSet);
    }

    @Test
    public void testEqualsAndHashCode_2() {
        Verify.assertPostSerializedEqualsAndHashCode(this.unmodifiableSet);
    }

    @Test
    public void testEqualsAndHashCode_3() {
        Verify.assertInstanceOf(UnmodifiableMutableSet.class, SerializeTestHelper.serializeDeserialize(this.unmodifiableSet));
    }
}
