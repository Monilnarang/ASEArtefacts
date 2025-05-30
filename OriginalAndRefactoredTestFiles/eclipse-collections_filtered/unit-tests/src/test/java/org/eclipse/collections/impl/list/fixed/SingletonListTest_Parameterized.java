package org.eclipse.collections.impl.list.fixed;

import java.util.Collections;
import java.util.Iterator;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.function.Function0;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.tuple.Twin;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.block.factory.Functions;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.block.function.AddFunction;
import org.eclipse.collections.impl.block.function.PassThruFunction0;
import org.eclipse.collections.impl.block.procedure.CollectionAddProcedure;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.mutable.SynchronizedMutableList;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SingletonListTest_Parameterized extends AbstractMemoryEfficientMutableListTestCase {

    @Override
    protected int getSize() {
        return 1;
    }

    @Override
    protected Class<?> getListType() {
        return SingletonList.class;
    }

    private static <T> MutableList<T> newWith(T item) {
        return Lists.fixedSize.of(item);
    }

    private MutableList<Integer> newList() {
        return Lists.fixedSize.of(1);
    }

    private MutableList<Integer> classUnderTestWithNull() {
        return Lists.fixedSize.of((Integer) null);
    }

    @Test
    public void equalsAndHashCode_1() {
        Verify.assertEqualsAndHashCode(this.list, FastList.newList(this.list));
    }

    @Test
    public void equalsAndHashCode_2() {
        Verify.assertPostSerializedEqualsAndHashCode(this.list);
    }

    @Test
    public void contains_1() {
        assertTrue(this.list.contains("1"));
    }

    @Test
    public void contains_2() {
        assertFalse(this.list.contains("2"));
    }

    @Test
    public void select_1() {
        Verify.assertContainsAll(SingletonListTest.newWith(1).select(Predicates.lessThan(3)), 1);
    }

    @Test
    public void select_2() {
        Verify.assertEmpty(SingletonListTest.newWith(1).select(Predicates.greaterThan(3)));
    }

    @Test
    public void selectWith_1() {
        Verify.assertContainsAll(SingletonListTest.newWith(1).selectWith(Predicates2.lessThan(), 3), 1);
    }

    @Test
    public void selectWith_2() {
        Verify.assertEmpty(SingletonListTest.newWith(1).selectWith(Predicates2.greaterThan(), 3));
    }

    @Test
    public void reject_1() {
        Verify.assertEmpty(SingletonListTest.newWith(1).reject(Predicates.lessThan(3)));
    }

    @Test
    public void reject_2() {
        Verify.assertContainsAll(SingletonListTest.newWith(1).reject(Predicates.greaterThan(3), UnifiedSet.newSet()), 1);
    }

    @Test
    public void rejectWith_1() {
        Verify.assertEmpty(SingletonListTest.newWith(1).rejectWith(Predicates2.lessThan(), 3));
    }

    @Test
    public void rejectWith_2() {
        Verify.assertContainsAll(SingletonListTest.newWith(1).rejectWith(Predicates2.greaterThan(), 3, UnifiedSet.newSet()), 1);
    }

    @Test
    public void anySatisfyWith_1() {
        assertFalse(SingletonListTest.newWith(1).anySatisfyWith(Predicates2.instanceOf(), String.class));
    }

    @Test
    public void anySatisfyWith_2() {
        assertTrue(SingletonListTest.newWith(1).anySatisfyWith(Predicates2.instanceOf(), Integer.class));
    }

    @Test
    public void collectWith_1() {
        assertEquals(FastList.newListWith(2), SingletonListTest.newWith(1).collectWith(AddFunction.INTEGER, 1));
    }

    @Test
    public void collectWith_2() {
        assertEquals(FastList.newListWith(2), SingletonListTest.newWith(1).collectWith(AddFunction.INTEGER, 1, FastList.newList()));
    }

    @Test
    public void isEmpty_1() {
        Verify.assertNotEmpty(SingletonListTest.newWith(1));
    }

    @Test
    public void isEmpty_2() {
        assertTrue(SingletonListTest.newWith(1).notEmpty());
    }

    @ParameterizedTest
    @MethodSource("Provider_countWith_1to2")
    public void countWith_1to2(int param1, int param2) {
        assertEquals(param1, SingletonListTest.newWith(param2).countWith(Predicates2.instanceOf(), Integer.class));
    }

    static public Stream<Arguments> Provider_countWith_1to2() {
        return Stream.of(arguments(1, 1), arguments(0, 1));
    }
}
