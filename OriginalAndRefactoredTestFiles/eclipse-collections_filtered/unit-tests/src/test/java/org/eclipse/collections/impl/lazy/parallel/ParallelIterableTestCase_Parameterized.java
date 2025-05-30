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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public abstract class ParallelIterableTestCase_Parameterized {

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
    public void allSatisfy_6() {
        assertTrue(this.classUnderTest().allSatisfy(Predicates.lessThan(5)));
    }

    @Test
    public void allSatisfy_7() {
        assertTrue(this.classUnderTest().allSatisfy(Predicates.greaterThan(0)));
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

    @ParameterizedTest
    @MethodSource("Provider_anySatisfy_1to2")
    public void anySatisfy_1to2(int param1) {
        assertFalse(this.classUnderTest().anySatisfy(Predicates.lessThan(param1)));
    }

    static public Stream<Arguments> Provider_anySatisfy_1to2() {
        return Stream.of(arguments(0), arguments(1));
    }

    @ParameterizedTest
    @MethodSource("Provider_anySatisfy_3to6")
    public void anySatisfy_3to6(int param1) {
        assertTrue(this.classUnderTest().anySatisfy(Predicates.lessThan(param1)));
    }

    static public Stream<Arguments> Provider_anySatisfy_3to6() {
        return Stream.of(arguments(2), arguments(3), arguments(4), arguments(5));
    }

    @ParameterizedTest
    @MethodSource("Provider_anySatisfy_7to10")
    public void anySatisfy_7to10(int param1) {
        assertTrue(this.classUnderTest().anySatisfy(Predicates.greaterThan(param1)));
    }

    static public Stream<Arguments> Provider_anySatisfy_7to10() {
        return Stream.of(arguments(0), arguments(1), arguments(2), arguments(3));
    }

    @ParameterizedTest
    @MethodSource("Provider_anySatisfy_11to12")
    public void anySatisfy_11to12(int param1) {
        assertFalse(this.classUnderTest().anySatisfy(Predicates.greaterThan(param1)));
    }

    static public Stream<Arguments> Provider_anySatisfy_11to12() {
        return Stream.of(arguments(4), arguments(5));
    }

    @ParameterizedTest
    @MethodSource("Provider_anySatisfyWith_1to2")
    public void anySatisfyWith_1to2(int param1) {
        assertFalse(this.classUnderTest().anySatisfyWith(Predicates2.lessThan(), param1));
    }

    static public Stream<Arguments> Provider_anySatisfyWith_1to2() {
        return Stream.of(arguments(0), arguments(1));
    }

    @ParameterizedTest
    @MethodSource("Provider_anySatisfyWith_3to6")
    public void anySatisfyWith_3to6(int param1) {
        assertTrue(this.classUnderTest().anySatisfyWith(Predicates2.lessThan(), param1));
    }

    static public Stream<Arguments> Provider_anySatisfyWith_3to6() {
        return Stream.of(arguments(2), arguments(3), arguments(4), arguments(5));
    }

    @ParameterizedTest
    @MethodSource("Provider_anySatisfyWith_7to10")
    public void anySatisfyWith_7to10(int param1) {
        assertTrue(this.classUnderTest().anySatisfyWith(Predicates2.greaterThan(), param1));
    }

    static public Stream<Arguments> Provider_anySatisfyWith_7to10() {
        return Stream.of(arguments(0), arguments(1), arguments(2), arguments(3));
    }

    @ParameterizedTest
    @MethodSource("Provider_anySatisfyWith_11to12")
    public void anySatisfyWith_11to12(int param1) {
        assertFalse(this.classUnderTest().anySatisfyWith(Predicates2.greaterThan(), param1));
    }

    static public Stream<Arguments> Provider_anySatisfyWith_11to12() {
        return Stream.of(arguments(4), arguments(5));
    }

    @ParameterizedTest
    @MethodSource("Provider_allSatisfy_1to5")
    public void allSatisfy_1to5(int param1) {
        assertFalse(this.classUnderTest().allSatisfy(Predicates.lessThan(param1)));
    }

    static public Stream<Arguments> Provider_allSatisfy_1to5() {
        return Stream.of(arguments(0), arguments(1), arguments(2), arguments(3), arguments(4));
    }

    @ParameterizedTest
    @MethodSource("Provider_allSatisfy_8to12")
    public void allSatisfy_8to12(int param1) {
        assertFalse(this.classUnderTest().allSatisfy(Predicates.greaterThan(param1)));
    }

    static public Stream<Arguments> Provider_allSatisfy_8to12() {
        return Stream.of(arguments(1), arguments(2), arguments(3), arguments(4), arguments(5));
    }

    @ParameterizedTest
    @MethodSource("Provider_allSatisfyWith_1to5")
    public void allSatisfyWith_1to5(int param1) {
        assertFalse(this.classUnderTest().allSatisfyWith(Predicates2.lessThan(), param1));
    }

    static public Stream<Arguments> Provider_allSatisfyWith_1to5() {
        return Stream.of(arguments(0), arguments(1), arguments(2), arguments(3), arguments(4));
    }

    @ParameterizedTest
    @MethodSource("Provider_allSatisfyWith_8to12")
    public void allSatisfyWith_8to12(int param1) {
        assertFalse(this.classUnderTest().allSatisfyWith(Predicates2.greaterThan(), param1));
    }

    static public Stream<Arguments> Provider_allSatisfyWith_8to12() {
        return Stream.of(arguments(1), arguments(2), arguments(3), arguments(4), arguments(5));
    }

    @ParameterizedTest
    @MethodSource("Provider_noneSatisfy_1to2")
    public void noneSatisfy_1to2(int param1) {
        assertTrue(this.classUnderTest().noneSatisfy(Predicates.lessThan(param1)));
    }

    static public Stream<Arguments> Provider_noneSatisfy_1to2() {
        return Stream.of(arguments(0), arguments(1));
    }

    @ParameterizedTest
    @MethodSource("Provider_noneSatisfy_3to6")
    public void noneSatisfy_3to6(int param1) {
        assertFalse(this.classUnderTest().noneSatisfy(Predicates.lessThan(param1)));
    }

    static public Stream<Arguments> Provider_noneSatisfy_3to6() {
        return Stream.of(arguments(2), arguments(3), arguments(4), arguments(5));
    }

    @ParameterizedTest
    @MethodSource("Provider_noneSatisfy_7to10")
    public void noneSatisfy_7to10(int param1) {
        assertFalse(this.classUnderTest().noneSatisfy(Predicates.greaterThan(param1)));
    }

    static public Stream<Arguments> Provider_noneSatisfy_7to10() {
        return Stream.of(arguments(0), arguments(1), arguments(2), arguments(3));
    }

    @ParameterizedTest
    @MethodSource("Provider_noneSatisfy_11to12")
    public void noneSatisfy_11to12(int param1) {
        assertTrue(this.classUnderTest().noneSatisfy(Predicates.greaterThan(param1)));
    }

    static public Stream<Arguments> Provider_noneSatisfy_11to12() {
        return Stream.of(arguments(4), arguments(5));
    }

    @ParameterizedTest
    @MethodSource("Provider_noneSatisfyWith_1to2")
    public void noneSatisfyWith_1to2(int param1) {
        assertTrue(this.classUnderTest().noneSatisfyWith(Predicates2.lessThan(), param1));
    }

    static public Stream<Arguments> Provider_noneSatisfyWith_1to2() {
        return Stream.of(arguments(0), arguments(1));
    }

    @ParameterizedTest
    @MethodSource("Provider_noneSatisfyWith_3to6")
    public void noneSatisfyWith_3to6(int param1) {
        assertFalse(this.classUnderTest().noneSatisfyWith(Predicates2.lessThan(), param1));
    }

    static public Stream<Arguments> Provider_noneSatisfyWith_3to6() {
        return Stream.of(arguments(2), arguments(3), arguments(4), arguments(5));
    }

    @ParameterizedTest
    @MethodSource("Provider_noneSatisfyWith_7to10")
    public void noneSatisfyWith_7to10(int param1) {
        assertFalse(this.classUnderTest().noneSatisfyWith(Predicates2.greaterThan(), param1));
    }

    static public Stream<Arguments> Provider_noneSatisfyWith_7to10() {
        return Stream.of(arguments(0), arguments(1), arguments(2), arguments(3));
    }

    @ParameterizedTest
    @MethodSource("Provider_noneSatisfyWith_11to12")
    public void noneSatisfyWith_11to12(int param1) {
        assertTrue(this.classUnderTest().noneSatisfyWith(Predicates2.greaterThan(), param1));
    }

    static public Stream<Arguments> Provider_noneSatisfyWith_11to12() {
        return Stream.of(arguments(4), arguments(5));
    }
}
