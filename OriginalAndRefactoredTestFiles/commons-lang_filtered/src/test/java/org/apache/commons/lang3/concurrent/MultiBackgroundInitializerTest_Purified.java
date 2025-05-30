package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MultiBackgroundInitializerTest_Purified extends AbstractLangTest {

    protected static class AbstractChildBackgroundInitializer extends BackgroundInitializer<CloseableCounter> {

        volatile ExecutorService currentExecutor;

        CloseableCounter counter = new CloseableCounter();

        volatile int initializeCalls;

        Exception ex;

        final CountDownLatch latch = new CountDownLatch(1);

        boolean waitForLatch;

        public void enableLatch() {
            waitForLatch = true;
        }

        public CloseableCounter getCloseableCounter() {
            return counter;
        }

        protected CloseableCounter initializeInternal() throws Exception {
            initializeCalls++;
            currentExecutor = getActiveExecutor();
            if (waitForLatch) {
                latch.await();
            }
            if (ex != null) {
                throw ex;
            }
            return counter.increment();
        }

        public void releaseLatch() {
            latch.countDown();
        }
    }

    protected static class CloseableCounter {

        public static CloseableCounter wrapInteger(final int i) {
            return new CloseableCounter().setInitializeCalls(i);
        }

        volatile int initializeCalls;

        volatile boolean closed;

        public void close() {
            closed = true;
        }

        @Override
        public boolean equals(final Object other) {
            if (other instanceof CloseableCounter) {
                return initializeCalls == ((CloseableCounter) other).getInitializeCalls();
            }
            return false;
        }

        public int getInitializeCalls() {
            return initializeCalls;
        }

        @Override
        public int hashCode() {
            return initializeCalls;
        }

        public CloseableCounter increment() {
            initializeCalls++;
            return this;
        }

        public boolean isClosed() {
            return closed;
        }

        public CloseableCounter setInitializeCalls(final int i) {
            initializeCalls = i;
            return this;
        }
    }

    protected static class MethodChildBackgroundInitializer extends AbstractChildBackgroundInitializer {

        @Override
        protected CloseableCounter initialize() throws Exception {
            return initializeInternal();
        }
    }

    private static final String CHILD_INIT = "childInitializer";

    protected static final long PERIOD_MILLIS = 50;

    protected MultiBackgroundInitializer initializer;

    private void checkChild(final BackgroundInitializer<?> child, final ExecutorService expExec) throws ConcurrentException {
        final AbstractChildBackgroundInitializer cinit = (AbstractChildBackgroundInitializer) child;
        final Integer result = cinit.get().getInitializeCalls();
        assertEquals(1, result.intValue(), "Wrong result");
        assertEquals(1, cinit.initializeCalls, "Wrong number of executions");
        if (expExec != null) {
            assertEquals(expExec, cinit.currentExecutor, "Wrong executor service");
        }
    }

    private MultiBackgroundInitializer.MultiBackgroundInitializerResults checkInitialize() throws ConcurrentException {
        final int count = 5;
        for (int i = 0; i < count; i++) {
            initializer.addInitializer(CHILD_INIT + i, createChildBackgroundInitializer());
        }
        initializer.start();
        final MultiBackgroundInitializer.MultiBackgroundInitializerResults res = initializer.get();
        assertEquals(count, res.initializerNames().size(), "Wrong number of child initializers");
        for (int i = 0; i < count; i++) {
            final String key = CHILD_INIT + i;
            assertTrue(res.initializerNames().contains(key), "Name not found: " + key);
            assertEquals(CloseableCounter.wrapInteger(1), res.getResultObject(key), "Wrong result object");
            assertFalse(res.isException(key), "Exception flag");
            assertNull(res.getException(key), "Got an exception");
            checkChild(res.getInitializer(key), initializer.getActiveExecutor());
        }
        return res;
    }

    protected AbstractChildBackgroundInitializer createChildBackgroundInitializer() {
        return new MethodChildBackgroundInitializer();
    }

    @BeforeEach
    public void setUp() {
        initializer = new MultiBackgroundInitializer();
    }

    @Test
    public void testInitializeNoChildren_1() throws ConcurrentException {
        assertTrue(initializer.start(), "Wrong result of start()");
    }

    @Test
    public void testInitializeNoChildren_2_testMerged_2() throws ConcurrentException {
        final MultiBackgroundInitializer.MultiBackgroundInitializerResults res = initializer.get();
        assertTrue(res.initializerNames().isEmpty(), "Got child initializers");
        assertTrue(initializer.getActiveExecutor().isShutdown(), "Executor not shutdown");
    }
}
