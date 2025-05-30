package org.eclipse.collections.impl;

import java.util.Collections;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.partition.PartitionIterable;
import org.eclipse.collections.impl.block.factory.Functions;
import org.eclipse.collections.impl.multimap.list.FastListMultimap;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UnmodifiableRichIterableTest_Purified extends AbstractRichIterableTestCase {

    private static final String METALLICA = "Metallica";

    private static final String BON_JOVI = "Bon Jovi";

    private static final String EUROPE = "Europe";

    private static final String SCORPIONS = "Scorpions";

    private static final String[] EMPTY_STRING_ARRAY = new String[0];

    private RichIterable<String> mutableCollection;

    private RichIterable<String> unmodifiableCollection;

    @Override
    protected <T> RichIterable<T> newWith(T... elements) {
        return UnmodifiableRichIterable.of(Lists.mutable.of(elements));
    }

    @BeforeEach
    public void setUp() {
        this.mutableCollection = Lists.mutable.of(METALLICA, BON_JOVI, EUROPE, SCORPIONS);
        this.unmodifiableCollection = UnmodifiableRichIterable.of(this.mutableCollection);
    }

    @Test
    public void testDelegatingMethods_1() {
        assertTrue(this.mutableCollection.notEmpty());
    }

    @Test
    public void testDelegatingMethods_2() {
        assertTrue(this.unmodifiableCollection.notEmpty());
    }

    @Test
    public void testDelegatingMethods_3() {
        assertFalse(this.mutableCollection.isEmpty());
    }

    @Test
    public void testDelegatingMethods_4() {
        assertFalse(this.unmodifiableCollection.isEmpty());
    }

    @Test
    public void testDelegatingMethods_5() {
        Verify.assertIterableSize(this.mutableCollection.size(), this.unmodifiableCollection);
    }

    @Test
    public void testDelegatingMethods_6() {
        assertEquals(this.mutableCollection.getFirst(), this.unmodifiableCollection.getFirst());
    }

    @Test
    public void testDelegatingMethods_7() {
        assertEquals(this.mutableCollection.getLast(), this.unmodifiableCollection.getLast());
    }

    @Test
    public void converters_1() {
        assertEquals(this.mutableCollection.toBag(), this.unmodifiableCollection.toBag());
    }

    @Test
    public void converters_2() {
        assertEquals(this.mutableCollection.asLazy().toBag(), this.unmodifiableCollection.asLazy().toBag());
    }

    @Test
    public void converters_3() {
        assertArrayEquals(this.mutableCollection.toArray(), this.unmodifiableCollection.toArray());
    }

    @Test
    public void converters_4() {
        assertArrayEquals(this.mutableCollection.toArray(EMPTY_STRING_ARRAY), this.unmodifiableCollection.toArray(EMPTY_STRING_ARRAY));
    }

    @Test
    public void converters_5() {
        assertEquals(this.mutableCollection.toList(), this.unmodifiableCollection.toList());
    }

    @Test
    public void converters_6() {
        Verify.assertListsEqual(Lists.mutable.of(BON_JOVI, EUROPE, METALLICA, SCORPIONS), this.unmodifiableCollection.toSortedList());
    }

    @Test
    public void converters_7() {
        Verify.assertListsEqual(Lists.mutable.of(SCORPIONS, METALLICA, EUROPE, BON_JOVI), this.unmodifiableCollection.toSortedList(Collections.reverseOrder()));
    }

    @Test
    public void converters_8() {
        Verify.assertListsEqual(Lists.mutable.of(BON_JOVI, EUROPE, METALLICA, SCORPIONS), this.unmodifiableCollection.toSortedListBy(Functions.getStringPassThru()));
    }

    @Test
    public void converters_9() {
        Verify.assertSize(4, this.unmodifiableCollection.toSet());
    }

    @Test
    public void converters_10() {
        Verify.assertSize(4, this.unmodifiableCollection.toMap(Functions.getStringPassThru(), Functions.getStringPassThru()));
    }

    @Test
    @Override
    public void equalsAndHashCode_1() {
        assertNotEquals(this.newWith(1, 2, 3).hashCode(), this.newWith(1, 2, 3).hashCode());
    }

    @Test
    @Override
    public void equalsAndHashCode_2() {
        assertNotEquals(this.newWith(1, 2, 3), this.newWith(1, 2, 3));
    }

    @Test
    @Override
    public void groupBy_1() {
        assertEquals(this.mutableCollection.groupBy(Functions.getStringPassThru()), this.unmodifiableCollection.groupBy(Functions.getStringPassThru()));
    }

    @Test
    @Override
    public void groupBy_2() {
        assertEquals(this.mutableCollection.groupBy(Functions.getStringPassThru(), FastListMultimap.newMultimap()), this.unmodifiableCollection.groupBy(Functions.getStringPassThru(), FastListMultimap.newMultimap()));
    }
}
