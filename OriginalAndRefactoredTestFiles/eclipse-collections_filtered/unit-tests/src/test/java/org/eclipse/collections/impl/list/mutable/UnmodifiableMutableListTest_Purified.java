package org.eclipse.collections.impl.list.mutable;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Random;
import org.eclipse.collections.api.LazyIterable;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.partition.list.PartitionMutableList;
import org.eclipse.collections.impl.Counter;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.block.factory.Functions;
import org.eclipse.collections.impl.block.factory.HashingStrategies;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.test.SerializeTestHelper;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.eclipse.collections.impl.factory.Iterables.iList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UnmodifiableMutableListTest_Purified {

    private static final String LED_ZEPPELIN = "Led Zeppelin";

    private static final String METALLICA = "Metallica";

    private MutableList<String> mutableList;

    private MutableList<String> unmodifiableList;

    @BeforeEach
    public void setUp() {
        this.mutableList = Lists.mutable.of(METALLICA, "Bon Jovi", "Europe", "Scorpions");
        this.unmodifiableList = UnmodifiableMutableList.of(this.mutableList);
    }

    @Test
    public void equalsAndHashCode_1() {
        Verify.assertEqualsAndHashCode(this.mutableList, this.unmodifiableList);
    }

    @Test
    public void equalsAndHashCode_2() {
        Verify.assertPostSerializedEqualsAndHashCode(this.unmodifiableList);
    }

    @Test
    public void equalsAndHashCode_3() {
        Verify.assertInstanceOf(UnmodifiableMutableList.class, SerializeTestHelper.serializeDeserialize(this.unmodifiableList));
    }

    @Test
    public void delegatingMethods_1() {
        Verify.assertItemAtIndex("Europe", 2, this.unmodifiableList);
    }

    @Test
    public void delegatingMethods_2() {
        assertEquals(2, this.unmodifiableList.indexOf("Europe"));
    }

    @Test
    public void delegatingMethods_3() {
        assertEquals(0, this.unmodifiableList.lastIndexOf(METALLICA));
    }

    @Test
    public void toImmutable_1() {
        Verify.assertInstanceOf(ImmutableList.class, this.unmodifiableList.toImmutable());
    }

    @Test
    public void toImmutable_2() {
        assertEquals(this.unmodifiableList, this.unmodifiableList.toImmutable());
    }
}
