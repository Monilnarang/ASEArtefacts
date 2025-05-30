package org.eclipse.collections.impl.set.immutable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import org.eclipse.collections.api.block.function.Function0;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.set.UnsortedSetIterable;
import org.eclipse.collections.api.set.sorted.MutableSortedSet;
import org.eclipse.collections.impl.block.factory.Functions;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.function.AddFunction;
import org.eclipse.collections.impl.block.function.PassThruFunction0;
import org.eclipse.collections.impl.block.procedure.CollectionAddProcedure;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.set.sorted.mutable.TreeSortedSet;
import org.eclipse.collections.impl.test.SerializeTestHelper;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class AbstractImmutableUnifiedSetTestCase_Purified {

    public abstract ImmutableSet<Integer> newSet(Integer... elements);

    public abstract ImmutableSet<Integer> newSetWith(int one, int two);

    public abstract ImmutableSet<Integer> newSetWith(int one, int two, int three);

    public abstract ImmutableSet<Integer> newSetWith(int... littleElements);

    @Test
    public void select_1() {
        assertTrue(this.newSetWith(1, 2, 3, 4, 5).select(Predicates.lessThan(3)).containsAllArguments(1, 2));
    }

    @Test
    public void select_2() {
        assertFalse(this.newSetWith(-1, 2, 3, 4, 5).select(Predicates.lessThan(3)).containsAllArguments(3, 4, 5));
    }

    @Test
    public void getFirst_1() {
        assertEquals(Integer.valueOf(1), this.newSetWith(1, 2, 3).getFirst());
    }

    @Test
    public void getFirst_2() {
        assertNotEquals(Integer.valueOf(3), this.newSetWith(1, 2, 3).getFirst());
    }

    @Test
    public void isEmpty_1() {
        assertTrue(this.newSet().isEmpty());
    }

    @Test
    public void isEmpty_2() {
        assertTrue(this.newSetWith(1, 2).notEmpty());
    }
}
