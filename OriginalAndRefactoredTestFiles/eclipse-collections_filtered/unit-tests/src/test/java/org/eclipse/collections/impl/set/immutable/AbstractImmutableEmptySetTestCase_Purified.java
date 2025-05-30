package org.eclipse.collections.impl.set.immutable;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.set.primitive.ImmutableBooleanSet;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.block.factory.PrimitiveFunctions;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractImmutableEmptySetTestCase_Purified extends AbstractImmutableSetTestCase {

    @Override
    public void anySatisfyWith() {
        ImmutableSet<Integer> integers = this.classUnderTest();
        assertFalse(integers.anySatisfyWith(ERROR_THROWING_PREDICATE_2, Integer.class));
    }

    @Override
    public void allSatisfyWith() {
        ImmutableSet<Integer> integers = this.classUnderTest();
        assertTrue(integers.allSatisfyWith(ERROR_THROWING_PREDICATE_2, Integer.class));
    }

    @Override
    public void noneSatisfy() {
        ImmutableSet<Integer> integers = this.classUnderTest();
        assertTrue(integers.noneSatisfy(ERROR_THROWING_PREDICATE));
    }

    @Override
    public void noneSatisfyWith() {
        ImmutableSet<Integer> integers = this.classUnderTest();
        assertTrue(integers.noneSatisfyWith(ERROR_THROWING_PREDICATE_2, Integer.class));
    }

    @Test
    public void containsAll_1() {
        assertTrue(this.classUnderTest().castToSet().containsAll(new HashSet<>()));
    }

    @Test
    public void containsAll_2() {
        assertFalse(this.classUnderTest().castToSet().containsAll(UnifiedSet.newSetWith(1)));
    }
}
