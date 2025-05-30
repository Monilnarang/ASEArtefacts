package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.function.Predicate;
import org.apache.commons.lang3.ThreadUtils.ThreadGroupPredicate;
import org.apache.commons.lang3.ThreadUtils.ThreadPredicate;
import org.apache.commons.lang3.function.Predicates;
import org.junit.jupiter.api.Test;

public class ThreadUtilsTest_Purified extends AbstractLangTest {

    private static final class TestThread extends Thread {

        private final CountDownLatch latch = new CountDownLatch(1);

        TestThread(final String name) {
            super(name);
        }

        TestThread(final ThreadGroup group, final String name) {
            super(group, name);
        }

        @Override
        public void run() {
            latch.countDown();
            try {
                synchronized (this) {
                    this.wait();
                }
            } catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        @Override
        public synchronized void start() {
            super.start();
            try {
                latch.await();
            } catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Test
    public void testConstructor_1() {
        assertNotNull(new ThreadUtils());
    }

    @Test
    public void testConstructor_2_testMerged_2() {
        final Constructor<?>[] cons = ThreadUtils.class.getDeclaredConstructors();
        assertEquals(1, cons.length);
        assertTrue(Modifier.isPublic(cons[0].getModifiers()));
    }

    @Test
    public void testConstructor_4() {
        assertTrue(Modifier.isPublic(ThreadUtils.class.getModifiers()));
    }

    @Test
    public void testConstructor_5() {
        assertFalse(Modifier.isFinal(ThreadUtils.class.getModifiers()));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testDepreacted_1() {
        assertNotNull(ThreadUtils.ALWAYS_TRUE_PREDICATE);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testDepreacted_2_testMerged_2() {
        final ThreadPredicate tp = ThreadUtils.ALWAYS_TRUE_PREDICATE;
        assertTrue(tp.test(null));
        assertTrue(tp.test(new Thread()));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testDepreacted_4_testMerged_3() {
        final ThreadGroupPredicate tgp = ThreadUtils.ALWAYS_TRUE_PREDICATE;
        assertTrue(tgp.test(null));
        assertTrue(tgp.test(new ThreadGroup("")));
    }
}
