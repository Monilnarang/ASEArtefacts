package org.eclipse.collections.impl.set.sorted.mutable;

import java.util.Comparator;
import java.util.NoSuchElementException;
import org.eclipse.collections.api.collection.MutableCollection;
import org.eclipse.collections.api.set.sorted.MutableSortedSet;
import org.eclipse.collections.impl.block.factory.Functions;
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
import static org.junit.jupiter.api.Assertions.fail;

public class UnmodifiableSortedSetTest_Purified extends AbstractSortedSetTestCase {

    private static final String LED_ZEPPELIN = "Led Zeppelin";

    private static final String METALLICA = "Metallica";

    private MutableSortedSet<String> mutableSet;

    private MutableSortedSet<String> unmodifiableSet;

    @BeforeEach
    public void setUp() {
        this.mutableSet = TreeSortedSet.newSetWith(METALLICA, "Bon Jovi", "Europe", "Scorpions");
        this.unmodifiableSet = this.mutableSet.asUnmodifiable();
    }

    @Override
    protected <T> MutableSortedSet<T> newWith(T... elements) {
        return TreeSortedSet.newSetWith(elements).asUnmodifiable();
    }

    @Override
    protected <T> MutableSortedSet<T> newWith(Comparator<? super T> comparator, T... elements) {
        return TreeSortedSet.newSetWith(comparator, elements).asUnmodifiable();
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
        Verify.assertInstanceOf(UnmodifiableSortedSet.class, SerializeTestHelper.serializeDeserialize(this.unmodifiableSet));
    }
}
