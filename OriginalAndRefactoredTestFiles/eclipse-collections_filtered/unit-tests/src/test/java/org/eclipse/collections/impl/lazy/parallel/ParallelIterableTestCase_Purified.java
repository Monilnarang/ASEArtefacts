package org.eclipse.collections.impl.lazy.parallel;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import org.eclipse.collections.api.ParallelIterable;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.function.Function0;
import org.eclipse.collections.api.block.function.Function2;
import org.eclipse.collections.api.block.function.primitive.DoubleFunction;
import org.eclipse.collections.api.block.function.primitive.FloatFunction;
import org.eclipse.collections.api.block.predicate.Predicate;
import org.eclipse.collections.api.collection.MutableCollection;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.ParallelListIterable;
import org.eclipse.collections.api.set.ParallelSetIterable;
import org.eclipse.collections.api.set.sorted.ParallelSortedSetIterable;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.impl.bag.mutable.HashBag;
import org.eclipse.collections.impl.bag.mutable.primitive.CharHashBag;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.block.factory.IntegerPredicates;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.block.factory.Procedures2;
import org.eclipse.collections.impl.block.function.NegativeIntervalFunction;
import org.eclipse.collections.impl.block.function.PassThruFunction0;
import org.eclipse.collections.impl.block.function.checked.CheckedFunction;
import org.eclipse.collections.impl.block.predicate.checked.CheckedPredicate;
import org.eclipse.collections.impl.block.procedure.CollectionAddProcedure;
import org.eclipse.collections.impl.block.procedure.checked.CheckedProcedure;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.Tuples;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class ParallelIterableTestCase_Purified {

    private static final ImmutableList<Integer> BATCH_SIZES = Lists.immutable.with(2, 5, 10, 100, 1000, 10000, 50000);

    protected ExecutorService executorService;

    protected int batchSize = 2;

    @BeforeEach
    public void setUp() {
        this.executorService = Executors.newFixedThreadPool(10);
        this.batchSize = 2;
        assertFalse(Thread.interrupted());
    }

    @AfterEach
    public void tearDown() {
        this.executorService.shutdownNow();
        Thread.interrupted();
    }

    protected abstract ParallelIterable<Integer> classUnderTest();

    protected abstract ParallelIterable<Integer> newWith(Integer... littleElements);

    protected abstract RichIterable<Integer> getExpected();

    protected abstract RichIterable<Integer> getExpectedWith(Integer... littleElements);

    protected RichIterable<Integer> getExpectedCollect() {
        return this.getExpected();
    }

    protected final <T> RichIterable<T> getActual(ParallelIterable<T> actual) {
        if (actual instanceof ParallelListIterable<?>) {
            return actual.toList();
        }
        if (actual instanceof ParallelSortedSetIterable<?>) {
            return actual.toSortedSet(((ParallelSortedSetIterable<T>) actual).comparator());
        }
        if (actual instanceof ParallelSetIterable<?>) {
            return actual.toSet();
        }
        return actual.toBag();
    }

    protected abstract boolean isOrdered();

    protected abstract boolean isUnique();

    protected void assertStringsEqual(String regex, String expectedString, String actualString) {
        if (this.isOrdered()) {
            assertEquals(expectedString, actualString);
        } else {
            assertEquals(CharHashBag.newBagWith(expectedString.toCharArray()), CharHashBag.newBagWith(actualString.toCharArray()));
            assertTrue(Pattern.matches(regex, actualString));
        }
    }

    @Test
    public void selectWith_1() {
        assertEquals(this.getExpected().selectWith(Predicates2.greaterThan(), 1).selectWith(Predicates2.lessThan(), 4), this.getActual(this.classUnderTest().selectWith(Predicates2.greaterThan(), 1).selectWith(Predicates2.lessThan(), 4)));
    }

    @Test
    public void selectWith_2() {
        assertEquals(this.getExpected().selectWith(Predicates2.greaterThan(), 1).selectWith(Predicates2.lessThan(), 4).toList().toBag(), this.classUnderTest().selectWith(Predicates2.greaterThan(), 1).selectWith(Predicates2.lessThan(), 4).toList().toBag());
    }

    @Test
    public void selectWith_3() {
        assertEquals(this.getExpected().selectWith(Predicates2.greaterThan(), 1).selectWith(Predicates2.lessThan(), 4).toBag(), this.classUnderTest().selectWith(Predicates2.greaterThan(), 1).selectWith(Predicates2.lessThan(), 4).toBag());
    }

    @Test
    public void rejectWith_1() {
        assertEquals(this.getExpected().rejectWith(Predicates2.lessThanOrEqualTo(), 1).rejectWith(Predicates2.greaterThanOrEqualTo(), 4), this.getActual(this.classUnderTest().rejectWith(Predicates2.lessThanOrEqualTo(), 1).rejectWith(Predicates2.greaterThanOrEqualTo(), 4)));
    }

    @Test
    public void rejectWith_2() {
        assertEquals(this.getExpected().rejectWith(Predicates2.lessThanOrEqualTo(), 1).rejectWith(Predicates2.greaterThanOrEqualTo(), 4).toList().toBag(), this.classUnderTest().rejectWith(Predicates2.lessThanOrEqualTo(), 1).rejectWith(Predicates2.greaterThanOrEqualTo(), 4).toList().toBag());
    }

    @Test
    public void rejectWith_3() {
        assertEquals(this.getExpected().rejectWith(Predicates2.lessThanOrEqualTo(), 1).rejectWith(Predicates2.greaterThanOrEqualTo(), 4).toBag(), this.classUnderTest().rejectWith(Predicates2.lessThanOrEqualTo(), 1).rejectWith(Predicates2.greaterThanOrEqualTo(), 4).toBag());
    }

    @Test
    public void anySatisfy_1() {
        assertFalse(this.classUnderTest().anySatisfy(Predicates.lessThan(0)));
    }

    @Test
    public void anySatisfy_2() {
        assertFalse(this.classUnderTest().anySatisfy(Predicates.lessThan(1)));
    }

    @Test
    public void anySatisfy_3() {
        assertTrue(this.classUnderTest().anySatisfy(Predicates.lessThan(2)));
    }

    @Test
    public void anySatisfy_4() {
        assertTrue(this.classUnderTest().anySatisfy(Predicates.lessThan(3)));
    }

    @Test
    public void anySatisfy_5() {
        assertTrue(this.classUnderTest().anySatisfy(Predicates.lessThan(4)));
    }

    @Test
    public void anySatisfy_6() {
        assertTrue(this.classUnderTest().anySatisfy(Predicates.lessThan(5)));
    }

    @Test
    public void anySatisfy_7() {
        assertTrue(this.classUnderTest().anySatisfy(Predicates.greaterThan(0)));
    }

    @Test
    public void anySatisfy_8() {
        assertTrue(this.classUnderTest().anySatisfy(Predicates.greaterThan(1)));
    }

    @Test
    public void anySatisfy_9() {
        assertTrue(this.classUnderTest().anySatisfy(Predicates.greaterThan(2)));
    }

    @Test
    public void anySatisfy_10() {
        assertTrue(this.classUnderTest().anySatisfy(Predicates.greaterThan(3)));
    }

    @Test
    public void anySatisfy_11() {
        assertFalse(this.classUnderTest().anySatisfy(Predicates.greaterThan(4)));
    }

    @Test
    public void anySatisfy_12() {
        assertFalse(this.classUnderTest().anySatisfy(Predicates.greaterThan(5)));
    }

    @Test
    public void anySatisfyWith_1() {
        assertFalse(this.classUnderTest().anySatisfyWith(Predicates2.lessThan(), 0));
    }

    @Test
    public void anySatisfyWith_2() {
        assertFalse(this.classUnderTest().anySatisfyWith(Predicates2.lessThan(), 1));
    }

    @Test
    public void anySatisfyWith_3() {
        assertTrue(this.classUnderTest().anySatisfyWith(Predicates2.lessThan(), 2));
    }

    @Test
    public void anySatisfyWith_4() {
        assertTrue(this.classUnderTest().anySatisfyWith(Predicates2.lessThan(), 3));
    }

    @Test
    public void anySatisfyWith_5() {
        assertTrue(this.classUnderTest().anySatisfyWith(Predicates2.lessThan(), 4));
    }

    @Test
    public void anySatisfyWith_6() {
        assertTrue(this.classUnderTest().anySatisfyWith(Predicates2.lessThan(), 5));
    }

    @Test
    public void anySatisfyWith_7() {
        assertTrue(this.classUnderTest().anySatisfyWith(Predicates2.greaterThan(), 0));
    }

    @Test
    public void anySatisfyWith_8() {
        assertTrue(this.classUnderTest().anySatisfyWith(Predicates2.greaterThan(), 1));
    }

    @Test
    public void anySatisfyWith_9() {
        assertTrue(this.classUnderTest().anySatisfyWith(Predicates2.greaterThan(), 2));
    }

    @Test
    public void anySatisfyWith_10() {
        assertTrue(this.classUnderTest().anySatisfyWith(Predicates2.greaterThan(), 3));
    }

    @Test
    public void anySatisfyWith_11() {
        assertFalse(this.classUnderTest().anySatisfyWith(Predicates2.greaterThan(), 4));
    }

    @Test
    public void anySatisfyWith_12() {
        assertFalse(this.classUnderTest().anySatisfyWith(Predicates2.greaterThan(), 5));
    }

    @Test
    public void allSatisfy_1() {
        assertFalse(this.classUnderTest().allSatisfy(Predicates.lessThan(0)));
    }

    @Test
    public void allSatisfy_2() {
        assertFalse(this.classUnderTest().allSatisfy(Predicates.lessThan(1)));
    }

    @Test
    public void allSatisfy_3() {
        assertFalse(this.classUnderTest().allSatisfy(Predicates.lessThan(2)));
    }

    @Test
    public void allSatisfy_4() {
        assertFalse(this.classUnderTest().allSatisfy(Predicates.lessThan(3)));
    }

    @Test
    public void allSatisfy_5() {
        assertFalse(this.classUnderTest().allSatisfy(Predicates.lessThan(4)));
    }

    @Test
    public void allSatisfy_6() {
        assertTrue(this.classUnderTest().allSatisfy(Predicates.lessThan(5)));
    }

    @Test
    public void allSatisfy_7() {
        assertTrue(this.classUnderTest().allSatisfy(Predicates.greaterThan(0)));
    }

    @Test
    public void allSatisfy_8() {
        assertFalse(this.classUnderTest().allSatisfy(Predicates.greaterThan(1)));
    }

    @Test
    public void allSatisfy_9() {
        assertFalse(this.classUnderTest().allSatisfy(Predicates.greaterThan(2)));
    }

    @Test
    public void allSatisfy_10() {
        assertFalse(this.classUnderTest().allSatisfy(Predicates.greaterThan(3)));
    }

    @Test
    public void allSatisfy_11() {
        assertFalse(this.classUnderTest().allSatisfy(Predicates.greaterThan(4)));
    }

    @Test
    public void allSatisfy_12() {
        assertFalse(this.classUnderTest().allSatisfy(Predicates.greaterThan(5)));
    }

    @Test
    public void allSatisfyWith_1() {
        assertFalse(this.classUnderTest().allSatisfyWith(Predicates2.lessThan(), 0));
    }

    @Test
    public void allSatisfyWith_2() {
        assertFalse(this.classUnderTest().allSatisfyWith(Predicates2.lessThan(), 1));
    }

    @Test
    public void allSatisfyWith_3() {
        assertFalse(this.classUnderTest().allSatisfyWith(Predicates2.lessThan(), 2));
    }

    @Test
    public void allSatisfyWith_4() {
        assertFalse(this.classUnderTest().allSatisfyWith(Predicates2.lessThan(), 3));
    }

    @Test
    public void allSatisfyWith_5() {
        assertFalse(this.classUnderTest().allSatisfyWith(Predicates2.lessThan(), 4));
    }

    @Test
    public void allSatisfyWith_6() {
        assertTrue(this.classUnderTest().allSatisfyWith(Predicates2.lessThan(), 5));
    }

    @Test
    public void allSatisfyWith_7() {
        assertTrue(this.classUnderTest().allSatisfyWith(Predicates2.greaterThan(), 0));
    }

    @Test
    public void allSatisfyWith_8() {
        assertFalse(this.classUnderTest().allSatisfyWith(Predicates2.greaterThan(), 1));
    }

    @Test
    public void allSatisfyWith_9() {
        assertFalse(this.classUnderTest().allSatisfyWith(Predicates2.greaterThan(), 2));
    }

    @Test
    public void allSatisfyWith_10() {
        assertFalse(this.classUnderTest().allSatisfyWith(Predicates2.greaterThan(), 3));
    }

    @Test
    public void allSatisfyWith_11() {
        assertFalse(this.classUnderTest().allSatisfyWith(Predicates2.greaterThan(), 4));
    }

    @Test
    public void allSatisfyWith_12() {
        assertFalse(this.classUnderTest().allSatisfyWith(Predicates2.greaterThan(), 5));
    }

    @Test
    public void noneSatisfy_1() {
        assertTrue(this.classUnderTest().noneSatisfy(Predicates.lessThan(0)));
    }

    @Test
    public void noneSatisfy_2() {
        assertTrue(this.classUnderTest().noneSatisfy(Predicates.lessThan(1)));
    }

    @Test
    public void noneSatisfy_3() {
        assertFalse(this.classUnderTest().noneSatisfy(Predicates.lessThan(2)));
    }

    @Test
    public void noneSatisfy_4() {
        assertFalse(this.classUnderTest().noneSatisfy(Predicates.lessThan(3)));
    }

    @Test
    public void noneSatisfy_5() {
        assertFalse(this.classUnderTest().noneSatisfy(Predicates.lessThan(4)));
    }

    @Test
    public void noneSatisfy_6() {
        assertFalse(this.classUnderTest().noneSatisfy(Predicates.lessThan(5)));
    }

    @Test
    public void noneSatisfy_7() {
        assertFalse(this.classUnderTest().noneSatisfy(Predicates.greaterThan(0)));
    }

    @Test
    public void noneSatisfy_8() {
        assertFalse(this.classUnderTest().noneSatisfy(Predicates.greaterThan(1)));
    }

    @Test
    public void noneSatisfy_9() {
        assertFalse(this.classUnderTest().noneSatisfy(Predicates.greaterThan(2)));
    }

    @Test
    public void noneSatisfy_10() {
        assertFalse(this.classUnderTest().noneSatisfy(Predicates.greaterThan(3)));
    }

    @Test
    public void noneSatisfy_11() {
        assertTrue(this.classUnderTest().noneSatisfy(Predicates.greaterThan(4)));
    }

    @Test
    public void noneSatisfy_12() {
        assertTrue(this.classUnderTest().noneSatisfy(Predicates.greaterThan(5)));
    }

    @Test
    public void noneSatisfyWith_1() {
        assertTrue(this.classUnderTest().noneSatisfyWith(Predicates2.lessThan(), 0));
    }

    @Test
    public void noneSatisfyWith_2() {
        assertTrue(this.classUnderTest().noneSatisfyWith(Predicates2.lessThan(), 1));
    }

    @Test
    public void noneSatisfyWith_3() {
        assertFalse(this.classUnderTest().noneSatisfyWith(Predicates2.lessThan(), 2));
    }

    @Test
    public void noneSatisfyWith_4() {
        assertFalse(this.classUnderTest().noneSatisfyWith(Predicates2.lessThan(), 3));
    }

    @Test
    public void noneSatisfyWith_5() {
        assertFalse(this.classUnderTest().noneSatisfyWith(Predicates2.lessThan(), 4));
    }

    @Test
    public void noneSatisfyWith_6() {
        assertFalse(this.classUnderTest().noneSatisfyWith(Predicates2.lessThan(), 5));
    }

    @Test
    public void noneSatisfyWith_7() {
        assertFalse(this.classUnderTest().noneSatisfyWith(Predicates2.greaterThan(), 0));
    }

    @Test
    public void noneSatisfyWith_8() {
        assertFalse(this.classUnderTest().noneSatisfyWith(Predicates2.greaterThan(), 1));
    }

    @Test
    public void noneSatisfyWith_9() {
        assertFalse(this.classUnderTest().noneSatisfyWith(Predicates2.greaterThan(), 2));
    }

    @Test
    public void noneSatisfyWith_10() {
        assertFalse(this.classUnderTest().noneSatisfyWith(Predicates2.greaterThan(), 3));
    }

    @Test
    public void noneSatisfyWith_11() {
        assertTrue(this.classUnderTest().noneSatisfyWith(Predicates2.greaterThan(), 4));
    }

    @Test
    public void noneSatisfyWith_12() {
        assertTrue(this.classUnderTest().noneSatisfyWith(Predicates2.greaterThan(), 5));
    }

    @Test
    public void minWithEmptyBatch_1() {
        assertEquals(Integer.valueOf(1), this.classUnderTest().select(Predicates.lessThan(4)).min());
    }

    @Test
    public void minWithEmptyBatch_2() {
        assertEquals(Integer.valueOf(1), this.classUnderTest().reject(Predicates.greaterThan(3)).min());
    }

    @Test
    public void minWithEmptyBatch_3() {
        assertEquals(Integer.valueOf(1), this.classUnderTest().asUnique().min());
    }

    @Test
    public void maxWithEmptyBatch_1() {
        assertEquals(Integer.valueOf(3), this.classUnderTest().select(Predicates.lessThan(4)).max());
    }

    @Test
    public void maxWithEmptyBatch_2() {
        assertEquals(Integer.valueOf(3), this.classUnderTest().reject(Predicates.greaterThan(3)).max());
    }

    @Test
    public void maxWithEmptyBatch_3() {
        assertEquals(Integer.valueOf(4), this.classUnderTest().asUnique().max());
    }
}
