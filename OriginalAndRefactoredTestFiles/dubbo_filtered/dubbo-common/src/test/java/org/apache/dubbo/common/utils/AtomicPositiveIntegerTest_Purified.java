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

class AtomicPositiveIntegerTest_Purified {

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
    void testCompareAndSet2_2() {
        assertThat(i1.get(), is(2));
    }

    @Test
    void testWeakCompareAndSet2_1() {
        assertThat(i1.weakCompareAndSet(i1.get(), 2), is(true));
    }

    @Test
    void testWeakCompareAndSet2_2() {
        assertThat(i1.get(), is(2));
    }

    @Test
    void testEquals_1() {
        assertEquals(new AtomicPositiveInteger(), new AtomicPositiveInteger());
    }

    @Test
    void testEquals_2() {
        assertEquals(new AtomicPositiveInteger(1), new AtomicPositiveInteger(1));
    }
}
