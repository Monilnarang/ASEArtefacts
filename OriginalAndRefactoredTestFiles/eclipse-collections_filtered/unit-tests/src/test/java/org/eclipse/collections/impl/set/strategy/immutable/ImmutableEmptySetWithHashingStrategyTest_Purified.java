package org.eclipse.collections.impl.set.strategy.immutable;

import org.eclipse.collections.api.block.HashingStrategy;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.impl.block.factory.HashingStrategies;
import org.eclipse.collections.impl.factory.HashingStrategySets;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.set.immutable.AbstractImmutableEmptySetTestCase;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ImmutableEmptySetWithHashingStrategyTest_Purified extends AbstractImmutableEmptySetTestCase {

    private static final HashingStrategy<Integer> HASHING_STRATEGY = HashingStrategies.nullSafeHashingStrategy(new HashingStrategy<Integer>() {

        public int computeHashCode(Integer object) {
            return object.hashCode();
        }

        public boolean equals(Integer object1, Integer object2) {
            return object1.equals(object2);
        }
    });

    @Override
    protected ImmutableSet<Integer> classUnderTest() {
        return new ImmutableEmptySetWithHashingStrategy<>(HASHING_STRATEGY);
    }

    @Override
    @Test
    public void newWithout_1() {
        assertEquals(HashingStrategySets.immutable.of(HASHING_STRATEGY), HashingStrategySets.immutable.of(HASHING_STRATEGY).newWithout(1));
    }

    @Override
    @Test
    public void newWithout_2() {
        assertEquals(HashingStrategySets.immutable.of(HASHING_STRATEGY), HashingStrategySets.immutable.of(HASHING_STRATEGY).newWithoutAll(Interval.oneTo(3)));
    }
}
