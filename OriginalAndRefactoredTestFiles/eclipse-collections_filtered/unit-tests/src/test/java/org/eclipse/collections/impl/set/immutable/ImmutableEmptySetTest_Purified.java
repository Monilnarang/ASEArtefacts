package org.eclipse.collections.impl.set.immutable;

import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.factory.Bags;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.primitive.IntInterval;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class ImmutableEmptySetTest_Purified extends AbstractImmutableEmptySetTestCase {

    @Override
    protected ImmutableSet<Integer> classUnderTest() {
        return Sets.immutable.of();
    }

    @Override
    @Test
    public void newWithout_1() {
        assertSame(Sets.immutable.of(), Sets.immutable.of().newWithout(1));
    }

    @Override
    @Test
    public void newWithout_2() {
        assertSame(Sets.immutable.of(), Sets.immutable.of().newWithoutAll(Interval.oneTo(3)));
    }
}
