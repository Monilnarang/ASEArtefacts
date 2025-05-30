package org.eclipse.collections.impl.set.fixed;

import java.util.Collections;
import java.util.Iterator;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.function.Function0;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.factory.Sets;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.partition.set.PartitionMutableSet;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.tuple.Twin;
import org.eclipse.collections.impl.block.factory.Functions;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.block.function.AddFunction;
import org.eclipse.collections.impl.block.function.PassThruFunction0;
import org.eclipse.collections.impl.block.procedure.CollectionAddProcedure;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.set.mutable.SynchronizedMutableSet;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.Tuples;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.eclipse.collections.impl.factory.Iterables.iSet;
import static org.eclipse.collections.impl.factory.Iterables.mSet;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SingletonSetTest_Parameterized extends AbstractMemoryEfficientMutableSetTestCase {

    private SingletonSet<String> set;

    private MutableSet<Integer> intSet;

    @BeforeEach
    public void setUp() {
        this.set = new SingletonSet<>("1");
        this.intSet = Sets.fixedSize.of(1);
    }

    @Override
    protected MutableSet<String> classUnderTest() {
        return new SingletonSet<>("1");
    }

    @Override
    protected MutableSet<String> classUnderTestWithNull() {
        return new SingletonSet<>(null);
    }

    private void assertUnchanged() {
        Verify.assertSize(1, this.set);
        Verify.assertContains("1", this.set);
        Verify.assertNotContains("2", this.set);
    }

    @Test
    public void select_1() {
        Verify.assertContainsAll(this.intSet.select(Predicates.lessThan(3)), 1);
    }

    @Test
    public void select_2() {
        Verify.assertEmpty(this.intSet.select(Predicates.greaterThan(3)));
    }

    @Test
    public void selectWith_1() {
        Verify.assertContainsAll(this.intSet.selectWith(Predicates2.lessThan(), 3), 1);
    }

    @Test
    public void selectWith_2() {
        Verify.assertEmpty(this.intSet.selectWith(Predicates2.greaterThan(), 3));
    }

    @Test
    public void reject_1() {
        Verify.assertEmpty(this.intSet.reject(Predicates.lessThan(3)));
    }

    @Test
    public void reject_2() {
        Verify.assertContainsAll(this.intSet.reject(Predicates.greaterThan(3), UnifiedSet.newSet()), 1);
    }

    @Test
    public void rejectWith_1() {
        Verify.assertEmpty(this.intSet.rejectWith(Predicates2.lessThan(), 3));
    }

    @Test
    public void rejectWith_2() {
        Verify.assertContainsAll(this.intSet.rejectWith(Predicates2.greaterThan(), 3, UnifiedSet.newSet()), 1);
    }

    @Test
    public void anySatisfyWith_1() {
        assertFalse(this.intSet.anySatisfyWith(Predicates2.instanceOf(), String.class));
    }

    @Test
    public void anySatisfyWith_2() {
        assertTrue(this.intSet.anySatisfyWith(Predicates2.instanceOf(), Integer.class));
    }

    @Test
    public void collectWith_1() {
        assertEquals(UnifiedSet.newSetWith(2), this.intSet.collectWith(AddFunction.INTEGER, 1));
    }

    @Test
    public void collectWith_2() {
        assertEquals(FastList.newListWith(2), this.intSet.collectWith(AddFunction.INTEGER, 1, FastList.newList()));
    }

    @Test
    public void isEmpty_1() {
        Verify.assertNotEmpty(this.intSet);
    }

    @Test
    public void isEmpty_2() {
        assertTrue(this.intSet.notEmpty());
    }

    @ParameterizedTest
    @MethodSource("Provider_countWith_1to2")
    public void countWith_1to2(int param1) {
        assertEquals(param1, this.intSet.countWith(Predicates2.instanceOf(), Integer.class));
    }

    static public Stream<Arguments> Provider_countWith_1to2() {
        return Stream.of(arguments(1), arguments(0));
    }
}
