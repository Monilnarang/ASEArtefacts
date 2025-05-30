package org.eclipse.collections.impl.lazy.primitive;

import org.eclipse.collections.api.BooleanIterable;
import org.eclipse.collections.api.LazyBooleanIterable;
import org.eclipse.collections.api.iterator.BooleanIterator;
import org.eclipse.collections.impl.bag.mutable.primitive.BooleanHashBag;
import org.eclipse.collections.impl.block.factory.PrimitiveFunctions;
import org.eclipse.collections.impl.block.factory.primitive.BooleanPredicates;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.mutable.FastList;
import org.eclipse.collections.impl.list.mutable.primitive.BooleanArrayList;
import org.eclipse.collections.impl.set.mutable.primitive.BooleanHashSet;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class CollectBooleanIterableTest_Parameterized {

    private final BooleanIterable booleanIterable = Interval.zeroTo(2).collectBoolean(PrimitiveFunctions.integerIsPositive());

    @Test
    public void empty_1() {
        assertTrue(this.booleanIterable.notEmpty());
    }

    @Test
    public void empty_2() {
        assertFalse(this.booleanIterable.isEmpty());
    }

    @Test
    public void contains_1() {
        assertFalse(Interval.fromTo(-4, 0).collectBoolean(PrimitiveFunctions.integerIsPositive()).contains(true));
    }

    @Test
    public void contains_2() {
        assertTrue(Interval.fromTo(-2, 2).collectBoolean(PrimitiveFunctions.integerIsPositive()).contains(true));
    }

    @Test
    public void makeString_1() {
        assertEquals("false, true, true", this.booleanIterable.makeString());
    }

    @Test
    public void makeString_2() {
        assertEquals("false/true/true", this.booleanIterable.makeString("/"));
    }

    @Test
    public void makeString_3() {
        assertEquals("[false, true, true]", this.booleanIterable.makeString("[", ", ", "]"));
    }

    @Test
    public void appendString_1() {
        StringBuilder appendable = new StringBuilder();
        this.booleanIterable.appendString(appendable);
        assertEquals("false, true, true", appendable.toString());
    }

    @Test
    public void appendString_2() {
        StringBuilder appendable2 = new StringBuilder();
        this.booleanIterable.appendString(appendable2, "/");
        assertEquals("false/true/true", appendable2.toString());
    }

    @Test
    public void appendString_3() {
        StringBuilder appendable3 = new StringBuilder();
        this.booleanIterable.appendString(appendable3, "[", ", ", "]");
        assertEquals(this.booleanIterable.toString(), appendable3.toString());
    }

    @Test
    public void asLazy_1() {
        assertEquals(this.booleanIterable.toSet(), this.booleanIterable.asLazy().toSet());
    }

    @Test
    public void asLazy_2() {
        Verify.assertInstanceOf(LazyBooleanIterable.class, this.booleanIterable.asLazy());
    }

    @ParameterizedTest
    @MethodSource("Provider_count_1to2")
    public void count_1to2(int param1) {
        assertEquals(param1, this.booleanIterable.count(BooleanPredicates.equal(true)));
    }

    static public Stream<Arguments> Provider_count_1to2() {
        return Stream.of(arguments(2), arguments(1));
    }

    @ParameterizedTest
    @MethodSource("Provider_select_1to2")
    public void select_1to2(int param1) {
        assertEquals(param1, this.booleanIterable.select(BooleanPredicates.equal(true)).size());
    }

    static public Stream<Arguments> Provider_select_1to2() {
        return Stream.of(arguments(2), arguments(1));
    }

    @ParameterizedTest
    @MethodSource("Provider_reject_1to2")
    public void reject_1to2(int param1) {
        assertEquals(param1, this.booleanIterable.reject(BooleanPredicates.equal(true)).size());
    }

    static public Stream<Arguments> Provider_reject_1to2() {
        return Stream.of(arguments(1), arguments(2));
    }
}
