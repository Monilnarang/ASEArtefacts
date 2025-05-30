package org.eclipse.collections.impl.stack;

import java.util.Collections;
import java.util.EmptyStackException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.bag.sorted.SortedBag;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.function.Function0;
import org.eclipse.collections.api.block.function.Function2;
import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.api.block.predicate.Predicate2;
import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.api.factory.Bags;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.MapIterable;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.map.primitive.ObjectDoubleMap;
import org.eclipse.collections.api.multimap.Multimap;
import org.eclipse.collections.api.multimap.MutableMultimap;
import org.eclipse.collections.api.multimap.list.ListMultimap;
import org.eclipse.collections.api.partition.stack.PartitionStack;
import org.eclipse.collections.api.set.SetIterable;
import org.eclipse.collections.api.set.sorted.MutableSortedSet;
import org.eclipse.collections.api.stack.StackIterable;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.api.tuple.primitive.ObjectIntPair;
import org.eclipse.collections.impl.AbstractRichIterableTestCase;
import org.eclipse.collections.impl.bag.sorted.mutable.TreeBag;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.block.factory.Functions;
import org.eclipse.collections.impl.block.factory.IntegerPredicates;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.block.factory.PrimitiveFunctions;
import org.eclipse.collections.impl.block.factory.Procedures;
import org.eclipse.collections.impl.block.function.AddFunction;
import org.eclipse.collections.impl.block.function.NegativeIntervalFunction;
import org.eclipse.collections.impl.block.function.PassThruFunction0;
import org.eclipse.collections.impl.factory.Stacks;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.eclipse.collections.impl.map.sorted.mutable.TreeSortedMap;
import org.eclipse.collections.impl.multimap.list.FastListMultimap;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.set.mutable.primitive.BooleanHashSet;
import org.eclipse.collections.impl.set.mutable.primitive.ByteHashSet;
import org.eclipse.collections.impl.set.mutable.primitive.CharHashSet;
import org.eclipse.collections.impl.set.mutable.primitive.DoubleHashSet;
import org.eclipse.collections.impl.set.mutable.primitive.FloatHashSet;
import org.eclipse.collections.impl.set.mutable.primitive.IntHashSet;
import org.eclipse.collections.impl.set.mutable.primitive.LongHashSet;
import org.eclipse.collections.impl.set.mutable.primitive.ShortHashSet;
import org.eclipse.collections.impl.set.sorted.mutable.TreeSortedSet;
import org.eclipse.collections.impl.stack.mutable.ArrayStack;
import org.eclipse.collections.impl.stack.mutable.primitive.BooleanArrayStack;
import org.eclipse.collections.impl.stack.mutable.primitive.ByteArrayStack;
import org.eclipse.collections.impl.stack.mutable.primitive.CharArrayStack;
import org.eclipse.collections.impl.stack.mutable.primitive.DoubleArrayStack;
import org.eclipse.collections.impl.stack.mutable.primitive.FloatArrayStack;
import org.eclipse.collections.impl.stack.mutable.primitive.IntArrayStack;
import org.eclipse.collections.impl.stack.mutable.primitive.LongArrayStack;
import org.eclipse.collections.impl.stack.mutable.primitive.ShortArrayStack;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.Tuples;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class StackIterableTestCase_Purified extends AbstractRichIterableTestCase {

    @Override
    protected <T> StackIterable<T> newWith(T... littleElements) {
        return this.newStackWith(littleElements);
    }

    protected abstract <T> StackIterable<T> newStackWith(T... elements);

    protected abstract <T> StackIterable<T> newStackFromTopToBottom(T... elements);

    protected abstract <T> StackIterable<T> newStackFromTopToBottom(Iterable<T> elements);

    protected abstract <T> StackIterable<T> newStack(Iterable<T> elements);

    private static final class CountingPredicate<T> implements Predicate<T> {

        private static final long serialVersionUID = 1L;

        private final Predicate<T> predicate;

        private int count;

        private CountingPredicate(Predicate<T> predicate) {
            this.predicate = predicate;
        }

        private static <T> CountingPredicate<T> of(Predicate<T> predicate) {
            return new CountingPredicate<>(predicate);
        }

        @Override
        public boolean accept(T anObject) {
            this.count++;
            return this.predicate.accept(anObject);
        }
    }

    private static final class CountingPredicate2<T1, T2> implements Predicate2<T1, T2> {

        private static final long serialVersionUID = 1L;

        private final Predicate2<T1, T2> predicate;

        private int count;

        private CountingPredicate2(Predicate2<T1, T2> predicate) {
            this.predicate = predicate;
        }

        private static <T1, T2> CountingPredicate2<T1, T2> of(Predicate2<T1, T2> predicate) {
            return new CountingPredicate2<>(predicate);
        }

        @Override
        public boolean accept(T1 each, T2 parameter) {
            this.count++;
            return this.predicate.accept(each, parameter);
        }
    }

    private static final class CountingFunction<T, V> implements Function<T, V> {

        private static final long serialVersionUID = 1L;

        private int count;

        private final Function<T, V> function;

        private CountingFunction(Function<T, V> function) {
            this.function = function;
        }

        private static <T, V> CountingFunction<T, V> of(Function<T, V> function) {
            return new CountingFunction<>(function);
        }

        @Override
        public V valueOf(T object) {
            this.count++;
            return this.function.valueOf(object);
        }
    }

    @Test
    public void peek_1() {
        assertEquals("3", this.newStackWith("1", "2", "3").peek());
    }

    @Test
    public void peek_2() {
        assertEquals(Lists.mutable.with(), this.newStackWith("1", "2", "3").peek(0));
    }

    @Test
    public void peek_3() {
        assertEquals(Lists.mutable.with("3", "2"), this.newStackWith("1", "2", "3").peek(2));
    }

    @Test
    public void peekAt_1() {
        assertEquals("3", this.newStackWith("1", "2", "3").peekAt(0));
    }

    @Test
    public void peekAt_2() {
        assertEquals("2", this.newStackWith("1", "2", "3").peekAt(1));
    }

    @Test
    public void peekAt_3() {
        assertEquals("1", this.newStackWith("1", "2", "3").peekAt(2));
    }

    @Test
    public void size_1() {
        StackIterable<Integer> stack1 = this.newStackWith();
        assertEquals(0, stack1.size());
    }

    @Test
    public void size_2() {
        StackIterable<Integer> stack2 = this.newStackWith(1, 2);
        assertEquals(2, stack2.size());
    }

    @Override
    @Test
    public void injectInto_1() {
        assertEquals(Integer.valueOf(10), this.newStackWith(1, 2, 3, 4).injectInto(Integer.valueOf(0), AddFunction.INTEGER));
    }

    @Override
    @Test
    public void injectInto_2() {
        assertEquals(10, this.newStackWith(1, 2, 3, 4).injectInto(0, AddFunction.INTEGER_TO_INT));
    }

    @Override
    @Test
    public void injectInto_3() {
        assertEquals(7.0, this.newStackWith(1.0, 2.0, 3.0).injectInto(1.0d, AddFunction.DOUBLE_TO_DOUBLE), 0.001);
    }

    @Override
    @Test
    public void injectInto_4() {
        assertEquals(7, this.newStackWith(1, 2, 3).injectInto(1L, AddFunction.INTEGER_TO_LONG));
    }

    @Override
    @Test
    public void injectInto_5() {
        assertEquals(7.0, this.newStackWith(1, 2, 3).injectInto(1.0f, AddFunction.INTEGER_TO_FLOAT), 0.001);
    }

    @Override
    @Test
    public void max_1() {
        assertEquals(Integer.valueOf(4), this.newStackFromTopToBottom(4, 3, 2, 1).max());
    }

    @Override
    @Test
    public void max_2() {
        assertEquals(Integer.valueOf(1), this.newStackFromTopToBottom(4, 3, 2, 1).max(Comparators.reverseNaturalOrder()));
    }

    @Override
    @Test
    public void min_1() {
        assertEquals(Integer.valueOf(1), this.newStackWith(1, 2, 3, 4).min());
    }

    @Override
    @Test
    public void min_2() {
        assertEquals(Integer.valueOf(4), this.newStackWith(1, 2, 3, 4).min(Comparators.reverseNaturalOrder()));
    }

    @Override
    @Test
    public void makeString_1() {
        assertEquals("3, 2, 1", this.newStackFromTopToBottom(3, 2, 1).makeString());
    }

    @Override
    @Test
    public void makeString_2() {
        assertEquals("3~2~1", this.newStackFromTopToBottom(3, 2, 1).makeString("~"));
    }

    @Override
    @Test
    public void makeString_3() {
        assertEquals("[3/2/1]", this.newStackFromTopToBottom(3, 2, 1).makeString("[", "/", "]"));
    }

    @Override
    @Test
    public void appendString_1() {
        Appendable appendable = new StringBuilder();
        stack.appendString(appendable);
        assertEquals("3, 2, 1", appendable.toString());
    }

    @Override
    @Test
    public void appendString_2() {
        Appendable appendable2 = new StringBuilder();
        stack.appendString(appendable2, "/");
        assertEquals("3/2/1", appendable2.toString());
    }

    @Override
    @Test
    public void appendString_3() {
        Appendable appendable3 = new StringBuilder();
        stack.appendString(appendable3, "[", "/", "]");
        assertEquals("[3/2/1]", appendable3.toString());
    }

    @Test
    public void toSortedList_1() {
        assertEquals(Interval.oneTo(4), this.newStackFromTopToBottom(4, 3, 1, 2).toSortedList());
    }

    @Test
    public void toSortedList_2() {
        assertEquals(Interval.fromTo(4, 1), this.newStackFromTopToBottom(4, 3, 1, 2).toSortedList(Collections.reverseOrder()));
    }

    @Test
    public void testEquals_1_testMerged_1() {
        StackIterable<Integer> stack1 = this.newStackFromTopToBottom(1, 2, 3, 4);
        StackIterable<Integer> stack2 = this.newStackFromTopToBottom(1, 2, 3, 4);
        StackIterable<Integer> stack3 = this.newStackFromTopToBottom(5, 2, 1, 4);
        StackIterable<Integer> stack4 = this.newStackFromTopToBottom(1, 2, 3);
        StackIterable<Integer> stack5 = this.newStackFromTopToBottom(1, 2, 3, 4, 5);
        StackIterable<Integer> stack6 = this.newStackFromTopToBottom(1, 2, 3, null);
        Verify.assertEqualsAndHashCode(stack1, stack2);
        assertNotEquals(stack1, stack3);
        assertNotEquals(stack1, stack4);
        assertNotEquals(stack1, stack5);
        assertNotEquals(stack1, stack6);
    }

    @Test
    public void testEquals_2() {
        Verify.assertPostSerializedEqualsAndHashCode(this.newStackWith(1, 2, 3, 4));
    }

    @Test
    public void testEquals_7() {
        Verify.assertPostSerializedEqualsAndHashCode(this.newStackWith(null, null, null));
    }

    @Test
    public void testEquals_8() {
        assertEquals(Stacks.mutable.of(), this.newStackWith());
    }

    @Test
    public void distinct_1() {
        StackIterable<Integer> stack = this.newStackFromTopToBottom(5, 5, 4, 4, 3, 3, 2, 2, 1, 1);
        StackIterable<Integer> actual = stack.distinct();
        StackIterable<Integer> expected = this.newStackWith(1, 2, 3, 4, 5);
        assertEquals(expected, actual);
    }

    @Test
    public void distinct_2() {
        assertEquals(this.newStackWith(), this.newStackFromTopToBottom().distinct());
    }

    @Test
    public void testHashCode_1() {
        StackIterable<Integer> stack1 = this.newStackWith(1, 2, 3, 5);
        StackIterable<Integer> stack2 = this.newStackWith(1, 2, 3, 4);
        assertNotEquals(stack1.hashCode(), stack2.hashCode());
    }

    @Test
    public void testHashCode_2() {
        assertEquals(31 * 31 * 31 * 31 + 1 * 31 * 31 * 31 + 2 * 31 * 31 + 3 * 31 + 4, this.newStackFromTopToBottom(1, 2, 3, 4).hashCode());
    }

    @Test
    public void testHashCode_3() {
        assertEquals(31 * 31 * 31, this.newStackFromTopToBottom(null, null, null).hashCode());
    }

    @Test
    public void testHashCode_4() {
        assertNotEquals(this.newStackFromTopToBottom(1, 2, 3, 4).hashCode(), this.newStackFromTopToBottom(4, 3, 2, 1).hashCode());
    }
}
