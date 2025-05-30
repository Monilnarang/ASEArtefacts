package org.apache.dubbo.common.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class AtomicPositiveIntegerTest_Parameterized {

    private AtomicPositiveInteger i1 = new AtomicPositiveInteger();

    private AtomicPositiveInteger i2 = new AtomicPositiveInteger(127);

    private AtomicPositiveInteger i3 = new AtomicPositiveInteger(Integer.MAX_VALUE);

    @Test
    void testGet_1() {
        assertEquals(0, i1.get());
    }

    @Test
    void testGet_2() {
        assertEquals(127, i2.get());
    }

    @Test
    void testGet_3() {
        assertEquals(Integer.MAX_VALUE, i3.get());
    }

    @Test
    void testCompareAndSet2_1() {
        assertThat(i1.compareAndSet(i1.get(), 2), is(true));
    }

    @Test
    void testWeakCompareAndSet2_1() {
        assertThat(i1.weakCompareAndSet(i1.get(), 2), is(true));
    }

    @Test
    void testEquals_1() {
        assertEquals(new AtomicPositiveInteger(), new AtomicPositiveInteger());
    }

    @Test
    void testEquals_2() {
        assertEquals(new AtomicPositiveInteger(1), new AtomicPositiveInteger(1));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCompareAndSet2_2_2")
    void testCompareAndSet2_2_2(int param1) {
        assertThat(i1.get(), is(param1));
    }

    static public Stream<Arguments> Provider_testCompareAndSet2_2_2() {
        return Stream.of(arguments(2), arguments(2));
    }
}
