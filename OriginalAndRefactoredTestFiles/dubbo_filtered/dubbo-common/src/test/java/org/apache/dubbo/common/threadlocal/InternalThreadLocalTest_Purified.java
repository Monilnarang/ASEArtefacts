package org.apache.dubbo.common.threadlocal;

import java.lang.reflect.Field;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InternalThreadLocalTest_Purified {

    private static final int THREADS = 10;

    private static final int PERFORMANCE_THREAD_COUNT = 1000;

    private static final int GET_COUNT = 1000000;

    @AfterEach
    public void setup() {
        InternalThreadLocalMap.remove();
    }

    @Test
    void testRemoveAll_1_testMerged_1() {
        final InternalThreadLocal<Integer> internalThreadLocal = new InternalThreadLocal<Integer>();
        internalThreadLocal.set(1);
        Assertions.assertEquals(1, (int) internalThreadLocal.get(), "set failed");
        Assertions.assertNull(internalThreadLocal.get(), "removeAll failed!");
    }

    @Test
    void testRemoveAll_2_testMerged_2() {
        final InternalThreadLocal<String> internalThreadLocalString = new InternalThreadLocal<String>();
        internalThreadLocalString.set("value");
        Assertions.assertEquals("value", internalThreadLocalString.get(), "set failed");
        Assertions.assertNull(internalThreadLocalString.get(), "removeAll failed!");
    }

    @Test
    void testSize_1() {
        Assertions.assertEquals(1, InternalThreadLocal.size(), "size method is wrong!");
    }

    @Test
    void testSize_2() {
        Assertions.assertEquals(2, InternalThreadLocal.size(), "size method is wrong!");
    }
}
