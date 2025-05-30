package org.apache.commons.pool3.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.io.Serializable;
import java.lang.management.ManagementFactory;
import java.lang.ref.WeakReference;
import java.nio.charset.UnsupportedCharsetException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import org.apache.commons.lang3.ThreadUtils;
import org.apache.commons.lang3.time.DurationUtils;
import org.apache.commons.pool3.BasePooledObjectFactory;
import org.apache.commons.pool3.ObjectPool;
import org.apache.commons.pool3.PoolUtils;
import org.apache.commons.pool3.PooledObject;
import org.apache.commons.pool3.PooledObjectFactory;
import org.apache.commons.pool3.SwallowedExceptionListener;
import org.apache.commons.pool3.TestBaseObjectPool;
import org.apache.commons.pool3.TestException;
import org.apache.commons.pool3.VisitTracker;
import org.apache.commons.pool3.VisitTrackerFactory;
import org.apache.commons.pool3.Waiter;
import org.apache.commons.pool3.WaiterFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class TestGenericObjectPool_Purified extends TestBaseObjectPool {

    private final class ConcurrentBorrowAndEvictThread extends Thread {

        private final boolean borrow;

        private String obj;

        private ConcurrentBorrowAndEvictThread(final boolean borrow) {
            this.borrow = borrow;
        }

        @Override
        public void run() {
            try {
                if (borrow) {
                    obj = genericObjectPool.borrowObject();
                } else {
                    genericObjectPool.evict();
                }
            } catch (final Exception e) {
            }
        }
    }

    private static final class CreateErrorFactory extends BasePooledObjectFactory<String, InterruptedException> {

        private final Semaphore semaphore = new Semaphore(0);

        @Override
        public String create() throws InterruptedException {
            semaphore.acquire();
            throw new UnknownError("wiggle");
        }

        public boolean hasQueuedThreads() {
            return semaphore.hasQueuedThreads();
        }

        public void release() {
            semaphore.release();
        }

        @Override
        public PooledObject<String> wrap(final String obj) {
            return new DefaultPooledObject<>(obj);
        }
    }

    private static final class CreateFailFactory extends BasePooledObjectFactory<String, InterruptedException> {

        private final Semaphore semaphore = new Semaphore(0);

        @Override
        public String create() throws InterruptedException {
            semaphore.acquire();
            throw new UnsupportedCharsetException("wibble");
        }

        public boolean hasQueuedThreads() {
            return semaphore.hasQueuedThreads();
        }

        public void release() {
            semaphore.release();
        }

        @Override
        public PooledObject<String> wrap(final String obj) {
            return new DefaultPooledObject<>(obj);
        }
    }

    private static final class DummyFactory extends BasePooledObjectFactory<Object, RuntimeException> {

        @Override
        public Object create() {
            return null;
        }

        @Override
        public PooledObject<Object> wrap(final Object value) {
            return new DefaultPooledObject<>(value);
        }
    }

    private static final class EvictionThread<T, E extends Exception> extends Thread {

        private final GenericObjectPool<T, E> pool;

        private EvictionThread(final GenericObjectPool<T, E> pool) {
            this.pool = pool;
        }

        @Override
        public void run() {
            try {
                pool.evict();
            } catch (final Exception e) {
            }
        }
    }

    private static final class HashSetFactory extends BasePooledObjectFactory<HashSet<String>, RuntimeException> {

        @Override
        public HashSet<String> create() {
            return new HashSet<>();
        }

        @Override
        public PooledObject<HashSet<String>> wrap(final HashSet<String> value) {
            return new DefaultPooledObject<>(value);
        }
    }

    static class InvalidateThread implements Runnable {

        private final String obj;

        private final ObjectPool<String, ? extends Exception> pool;

        private boolean done;

        InvalidateThread(final ObjectPool<String, ? extends Exception> pool, final String obj) {
            this.obj = obj;
            this.pool = pool;
        }

        public boolean complete() {
            return done;
        }

        @Override
        public void run() {
            try {
                pool.invalidateObject(obj);
            } catch (final IllegalStateException ex) {
            } catch (final Exception ex) {
                fail("Unexpected exception " + ex.toString());
            } finally {
                done = true;
            }
        }
    }

    private static final class InvalidFactory extends BasePooledObjectFactory<Object, RuntimeException> {

        @Override
        public Object create() {
            return new Object();
        }

        @Override
        public boolean validateObject(final PooledObject<Object> obj) {
            Waiter.sleepQuietly(1000);
            return false;
        }

        @Override
        public PooledObject<Object> wrap(final Object value) {
            return new DefaultPooledObject<>(value);
        }
    }

    public static class SimpleFactory implements PooledObjectFactory<String, TestException> {

        int makeCounter;

        int activationCounter;

        int validateCounter;

        int activeCount;

        boolean evenValid = true;

        boolean oddValid = true;

        boolean exceptionOnPassivate;

        boolean exceptionOnActivate;

        boolean exceptionOnDestroy;

        boolean exceptionOnValidate;

        boolean enableValidation = true;

        long destroyLatency;

        long makeLatency;

        long validateLatency;

        int maxTotal = Integer.MAX_VALUE;

        public SimpleFactory() {
            this(true);
        }

        public SimpleFactory(final boolean valid) {
            this(valid, valid);
        }

        public SimpleFactory(final boolean evalid, final boolean ovalid) {
            evenValid = evalid;
            oddValid = ovalid;
        }

        @Override
        public void activateObject(final PooledObject<String> obj) throws TestException {
            final boolean hurl;
            final boolean evenTest;
            final boolean oddTest;
            final int counter;
            synchronized (this) {
                hurl = exceptionOnActivate;
                evenTest = evenValid;
                oddTest = oddValid;
                counter = activationCounter++;
            }
            if (hurl && !(counter % 2 == 0 ? evenTest : oddTest)) {
                throw new TestException();
            }
        }

        @Override
        public void destroyObject(final PooledObject<String> obj) throws TestException {
            final long waitLatency;
            final boolean hurl;
            synchronized (this) {
                waitLatency = destroyLatency;
                hurl = exceptionOnDestroy;
            }
            if (waitLatency > 0) {
                doWait(waitLatency);
            }
            synchronized (this) {
                activeCount--;
            }
            if (hurl) {
                throw new TestException();
            }
        }

        private void doWait(final long latency) {
            Waiter.sleepQuietly(latency);
        }

        public synchronized int getMakeCounter() {
            return makeCounter;
        }

        public synchronized boolean isThrowExceptionOnActivate() {
            return exceptionOnActivate;
        }

        public synchronized boolean isValidationEnabled() {
            return enableValidation;
        }

        @Override
        public PooledObject<String> makeObject() {
            final long waitLatency;
            synchronized (this) {
                activeCount++;
                if (activeCount > maxTotal) {
                    throw new IllegalStateException("Too many active instances: " + activeCount);
                }
                waitLatency = makeLatency;
            }
            if (waitLatency > 0) {
                doWait(waitLatency);
            }
            final int counter;
            synchronized (this) {
                counter = makeCounter++;
            }
            return new DefaultPooledObject<>(String.valueOf(counter));
        }

        @Override
        public void passivateObject(final PooledObject<String> obj) throws TestException {
            final boolean hurl;
            synchronized (this) {
                hurl = exceptionOnPassivate;
            }
            if (hurl) {
                throw new TestException();
            }
        }

        public synchronized void setDestroyLatency(final long destroyLatency) {
            this.destroyLatency = destroyLatency;
        }

        public synchronized void setEvenValid(final boolean valid) {
            evenValid = valid;
        }

        public synchronized void setMakeLatency(final long makeLatency) {
            this.makeLatency = makeLatency;
        }

        public synchronized void setMaxTotal(final int maxTotal) {
            this.maxTotal = maxTotal;
        }

        public synchronized void setOddValid(final boolean valid) {
            oddValid = valid;
        }

        public synchronized void setThrowExceptionOnActivate(final boolean b) {
            exceptionOnActivate = b;
        }

        public synchronized void setThrowExceptionOnDestroy(final boolean b) {
            exceptionOnDestroy = b;
        }

        public synchronized void setThrowExceptionOnPassivate(final boolean bool) {
            exceptionOnPassivate = bool;
        }

        public synchronized void setThrowExceptionOnValidate(final boolean bool) {
            exceptionOnValidate = bool;
        }

        public synchronized void setValid(final boolean valid) {
            setEvenValid(valid);
            setOddValid(valid);
        }

        public synchronized void setValidateLatency(final long validateLatency) {
            this.validateLatency = validateLatency;
        }

        public synchronized void setValidationEnabled(final boolean b) {
            enableValidation = b;
        }

        @Override
        public boolean validateObject(final PooledObject<String> obj) {
            final boolean validate;
            final boolean throwException;
            final boolean evenTest;
            final boolean oddTest;
            final long waitLatency;
            final int counter;
            synchronized (this) {
                validate = enableValidation;
                throwException = exceptionOnValidate;
                evenTest = evenValid;
                oddTest = oddValid;
                counter = validateCounter++;
                waitLatency = validateLatency;
            }
            if (waitLatency > 0) {
                doWait(waitLatency);
            }
            if (throwException) {
                throw new RuntimeException("validation failed");
            }
            if (validate) {
                return counter % 2 == 0 ? evenTest : oddTest;
            }
            return true;
        }
    }

    public static class TestEvictionPolicy<T> implements EvictionPolicy<T> {

        private final AtomicInteger callCount = new AtomicInteger();

        @Override
        public boolean evict(final EvictionConfig config, final PooledObject<T> underTest, final int idleCount) {
            return callCount.incrementAndGet() > 1500;
        }
    }

    static class TestThread<T, E extends Exception> implements Runnable {

        private final Random random;

        private final ObjectPool<T, E> pool;

        private final int iter;

        private final int startDelay;

        private final int holdTime;

        private final boolean randomDelay;

        private final Object expectedObject;

        private volatile boolean complete;

        private volatile boolean failed;

        private volatile Throwable error;

        private TestThread(final ObjectPool<T, E> pool) {
            this(pool, 100, 50, true, null);
        }

        private TestThread(final ObjectPool<T, E> pool, final int iter) {
            this(pool, iter, 50, true, null);
        }

        private TestThread(final ObjectPool<T, E> pool, final int iter, final int delay) {
            this(pool, iter, delay, true, null);
        }

        private TestThread(final ObjectPool<T, E> pool, final int iter, final int delay, final boolean randomDelay) {
            this(pool, iter, delay, randomDelay, null);
        }

        private TestThread(final ObjectPool<T, E> pool, final int iter, final int delay, final boolean randomDelay, final Object obj) {
            this(pool, iter, delay, delay, randomDelay, obj);
        }

        private TestThread(final ObjectPool<T, E> pool, final int iter, final int startDelay, final int holdTime, final boolean randomDelay, final Object obj) {
            this.pool = pool;
            this.iter = iter;
            this.startDelay = startDelay;
            this.holdTime = holdTime;
            this.randomDelay = randomDelay;
            this.random = this.randomDelay ? new Random() : null;
            this.expectedObject = obj;
        }

        public boolean complete() {
            return complete;
        }

        public boolean failed() {
            return failed;
        }

        @Override
        public void run() {
            for (int i = 0; i < iter; i++) {
                final long actualStartDelay = randomDelay ? (long) random.nextInt(startDelay) : startDelay;
                final long actualHoldTime = randomDelay ? (long) random.nextInt(holdTime) : holdTime;
                Waiter.sleepQuietly(actualStartDelay);
                T obj = null;
                try {
                    obj = pool.borrowObject();
                } catch (final Exception e) {
                    error = e;
                    failed = true;
                    complete = true;
                    break;
                }
                if (expectedObject != null && !expectedObject.equals(obj)) {
                    error = new Throwable("Expected: " + expectedObject + " found: " + obj);
                    failed = true;
                    complete = true;
                    break;
                }
                Waiter.sleepQuietly(actualHoldTime);
                try {
                    pool.returnObject(obj);
                } catch (final Exception e) {
                    error = e;
                    failed = true;
                    complete = true;
                    break;
                }
            }
            complete = true;
        }
    }

    static class WaitingTestThread<E extends Exception> extends Thread {

        private final GenericObjectPool<String, E> pool;

        private final long pause;

        private Throwable thrown;

        private long preBorrowMillis;

        private long postBorrowMillis;

        private long postReturnMillis;

        private long endedMillis;

        private String objectId;

        WaitingTestThread(final GenericObjectPool<String, E> pool, final long pause) {
            this.pool = pool;
            this.pause = pause;
            this.thrown = null;
        }

        @Override
        public void run() {
            try {
                preBorrowMillis = System.currentTimeMillis();
                final String obj = pool.borrowObject();
                objectId = obj;
                postBorrowMillis = System.currentTimeMillis();
                Thread.sleep(pause);
                pool.returnObject(obj);
                postReturnMillis = System.currentTimeMillis();
            } catch (final Throwable e) {
                thrown = e;
            } finally {
                endedMillis = System.currentTimeMillis();
            }
        }
    }

    private static final boolean DISPLAY_THREAD_DETAILS = Boolean.getBoolean("TestGenericObjectPool.display.thread.details");

    private GenericObjectPool<String, TestException> genericObjectPool;

    private SimpleFactory simpleFactory;

    private void assertConfiguration(final GenericObjectPoolConfig<?> expected, final GenericObjectPool<?, ?> actual) {
        assertEquals(Boolean.valueOf(expected.getTestOnCreate()), Boolean.valueOf(actual.getTestOnCreate()), "testOnCreate");
        assertEquals(Boolean.valueOf(expected.getTestOnBorrow()), Boolean.valueOf(actual.getTestOnBorrow()), "testOnBorrow");
        assertEquals(Boolean.valueOf(expected.getTestOnReturn()), Boolean.valueOf(actual.getTestOnReturn()), "testOnReturn");
        assertEquals(Boolean.valueOf(expected.getTestWhileIdle()), Boolean.valueOf(actual.getTestWhileIdle()), "testWhileIdle");
        assertEquals(Boolean.valueOf(expected.getBlockWhenExhausted()), Boolean.valueOf(actual.getBlockWhenExhausted()), "whenExhaustedAction");
        assertEquals(expected.getMaxTotal(), actual.getMaxTotal(), "maxTotal");
        assertEquals(expected.getMaxIdle(), actual.getMaxIdle(), "maxIdle");
        assertEquals(expected.getMaxWaitDuration(), actual.getMaxWaitDuration(), "maxWaitDuration");
        assertEquals(expected.getMinEvictableIdleDuration(), actual.getMinEvictableIdleDuration(), "minEvictableIdleDuration");
        assertEquals(expected.getNumTestsPerEvictionRun(), actual.getNumTestsPerEvictionRun(), "numTestsPerEvictionRun");
        assertEquals(expected.getEvictorShutdownTimeoutDuration(), actual.getEvictorShutdownTimeoutDuration(), "evictorShutdownTimeoutDuration");
    }

    private void checkEvict(final boolean lifo) throws Exception {
        genericObjectPool.setSoftMinEvictableIdleDuration(Duration.ofMillis(10));
        genericObjectPool.setMinIdle(2);
        genericObjectPool.setTestWhileIdle(true);
        genericObjectPool.setLifo(lifo);
        genericObjectPool.addObjects(5);
        genericObjectPool.evict();
        simpleFactory.setEvenValid(false);
        simpleFactory.setOddValid(false);
        simpleFactory.setThrowExceptionOnActivate(true);
        genericObjectPool.evict();
        genericObjectPool.addObjects(5);
        simpleFactory.setThrowExceptionOnActivate(false);
        simpleFactory.setThrowExceptionOnPassivate(true);
        genericObjectPool.evict();
        simpleFactory.setThrowExceptionOnPassivate(false);
        simpleFactory.setEvenValid(true);
        simpleFactory.setOddValid(true);
        Thread.sleep(125);
        genericObjectPool.evict();
        assertEquals(2, genericObjectPool.getNumIdle());
    }

    private void checkEvictionOrder(final boolean lifo) throws Exception {
        checkEvictionOrderPart1(lifo);
        tearDown();
        setUp();
        checkEvictionOrderPart2(lifo);
    }

    private void checkEvictionOrderPart1(final boolean lifo) throws Exception {
        genericObjectPool.setNumTestsPerEvictionRun(2);
        genericObjectPool.setMinEvictableIdleDuration(Duration.ofMillis(100));
        genericObjectPool.setLifo(lifo);
        for (int i = 0; i < 5; i++) {
            genericObjectPool.addObject();
            Thread.sleep(100);
        }
        genericObjectPool.evict();
        final Object obj = genericObjectPool.borrowObject();
        assertNotEquals("0", obj, "oldest not evicted");
        assertNotEquals("1", obj, "second oldest not evicted");
        assertEquals(lifo ? "4" : "2", obj, "Wrong instance returned");
    }

    private void checkEvictionOrderPart2(final boolean lifo) throws Exception {
        genericObjectPool.setNumTestsPerEvictionRun(2);
        genericObjectPool.setMinEvictableIdleDuration(Duration.ofMillis(100));
        genericObjectPool.setLifo(lifo);
        for (int i = 0; i < 5; i++) {
            genericObjectPool.addObject();
            Thread.sleep(100);
        }
        genericObjectPool.evict();
        genericObjectPool.evict();
        final Object obj = genericObjectPool.borrowObject();
        assertEquals("4", obj, "Wrong instance remaining in pool");
    }

    private void checkEvictorVisiting(final boolean lifo) throws Exception {
        VisitTracker<Object> obj;
        VisitTrackerFactory<Object> trackerFactory = new VisitTrackerFactory<>();
        try (GenericObjectPool<VisitTracker<Object>, RuntimeException> trackerPool = new GenericObjectPool<>(trackerFactory)) {
            trackerPool.setNumTestsPerEvictionRun(2);
            trackerPool.setMinEvictableIdleDuration(Duration.ofMillis(-1));
            trackerPool.setTestWhileIdle(true);
            trackerPool.setLifo(lifo);
            trackerPool.setTestOnReturn(false);
            trackerPool.setTestOnBorrow(false);
            for (int i = 0; i < 8; i++) {
                trackerPool.addObject();
            }
            trackerPool.evict();
            obj = trackerPool.borrowObject();
            trackerPool.returnObject(obj);
            obj = trackerPool.borrowObject();
            trackerPool.returnObject(obj);
            trackerPool.evict();
            for (int i = 0; i < 8; i++) {
                final VisitTracker<Object> tracker = trackerPool.borrowObject();
                if (tracker.getId() >= 4) {
                    assertEquals(0, tracker.getValidateCount(), "Unexpected instance visited " + tracker.getId());
                } else {
                    assertEquals(1, tracker.getValidateCount(), "Instance " + tracker.getId() + " visited wrong number of times.");
                }
            }
        }
        trackerFactory = new VisitTrackerFactory<>();
        try (GenericObjectPool<VisitTracker<Object>, RuntimeException> trackerPool = new GenericObjectPool<>(trackerFactory)) {
            trackerPool.setNumTestsPerEvictionRun(3);
            trackerPool.setMinEvictableIdleDuration(Duration.ofMillis(-1));
            trackerPool.setTestWhileIdle(true);
            trackerPool.setLifo(lifo);
            trackerPool.setTestOnReturn(false);
            trackerPool.setTestOnBorrow(false);
            for (int i = 0; i < 8; i++) {
                trackerPool.addObject();
            }
            trackerPool.evict();
            trackerPool.evict();
            obj = trackerPool.borrowObject();
            trackerPool.returnObject(obj);
            obj = trackerPool.borrowObject();
            trackerPool.returnObject(obj);
            obj = trackerPool.borrowObject();
            trackerPool.returnObject(obj);
            trackerPool.evict();
            for (int i = 0; i < 8; i++) {
                final VisitTracker<Object> tracker = trackerPool.borrowObject();
                if (tracker.getId() != 0) {
                    assertEquals(1, tracker.getValidateCount(), "Instance " + tracker.getId() + " visited wrong number of times.");
                } else {
                    assertEquals(2, tracker.getValidateCount(), "Instance " + tracker.getId() + " visited wrong number of times.");
                }
            }
        }
        final int[] smallPrimes = { 2, 3, 5, 7 };
        final Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                try (GenericObjectPool<VisitTracker<Object>, RuntimeException> trackerPool = new GenericObjectPool<>(trackerFactory)) {
                    trackerPool.setNumTestsPerEvictionRun(smallPrimes[i]);
                    trackerPool.setMinEvictableIdleDuration(Duration.ofMillis(-1));
                    trackerPool.setTestWhileIdle(true);
                    trackerPool.setLifo(lifo);
                    trackerPool.setTestOnReturn(false);
                    trackerPool.setTestOnBorrow(false);
                    trackerPool.setMaxIdle(-1);
                    final int instanceCount = 10 + random.nextInt(20);
                    trackerPool.setMaxTotal(instanceCount);
                    for (int k = 0; k < instanceCount; k++) {
                        trackerPool.addObject();
                    }
                    final int runs = 10 + random.nextInt(50);
                    for (int k = 0; k < runs; k++) {
                        trackerPool.evict();
                    }
                    final int cycleCount = runs * trackerPool.getNumTestsPerEvictionRun() / instanceCount;
                    VisitTracker<Object> tracker = null;
                    int visitCount = 0;
                    for (int k = 0; k < instanceCount; k++) {
                        tracker = trackerPool.borrowObject();
                        assertTrue(trackerPool.getNumActive() <= trackerPool.getMaxTotal());
                        visitCount = tracker.getValidateCount();
                        assertTrue(visitCount >= cycleCount && visitCount <= cycleCount + 1);
                    }
                }
            }
        }
    }

    private BasePooledObjectFactory<String, RuntimeException> createDefaultPooledObjectFactory() {
        return new BasePooledObjectFactory<>() {

            @Override
            public String create() {
                return null;
            }

            @Override
            public PooledObject<String> wrap(final String obj) {
                return new DefaultPooledObject<>(obj);
            }
        };
    }

    private BasePooledObjectFactory<String, RuntimeException> createNullPooledObjectFactory() {
        return new BasePooledObjectFactory<>() {

            @Override
            public String create() {
                return null;
            }

            @Override
            public PooledObject<String> wrap(final String obj) {
                return null;
            }
        };
    }

    private BasePooledObjectFactory<String, InterruptedException> createSlowObjectFactory(final Duration sleepDuration) {
        return new BasePooledObjectFactory<>() {

            @Override
            public String create() throws InterruptedException {
                ThreadUtils.sleep(sleepDuration);
                return "created";
            }

            @Override
            public PooledObject<String> wrap(final String obj) {
                return new DefaultPooledObject<>(obj);
            }
        };
    }

    @Override
    protected Object getNthObject(final int n) {
        return String.valueOf(n);
    }

    @Override
    protected boolean isFifo() {
        return false;
    }

    @Override
    protected boolean isLifo() {
        return true;
    }

    @Override
    protected ObjectPool<String, TestException> makeEmptyPool(final int minCap) {
        final GenericObjectPool<String, TestException> mtPool = new GenericObjectPool<>(new SimpleFactory());
        mtPool.setMaxTotal(minCap);
        mtPool.setMaxIdle(minCap);
        return mtPool;
    }

    @Override
    protected <E extends Exception> ObjectPool<Object, E> makeEmptyPool(final PooledObjectFactory<Object, E> fac) {
        return new GenericObjectPool<>(fac);
    }

    private <T, E extends Exception> void runTestThreads(final int numThreads, final int iterations, final int delay, final GenericObjectPool<T, E> testPool) {
        final TestThread<T, E>[] threads = new TestThread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new TestThread<>(testPool, iterations, delay);
            final Thread t = new Thread(threads[i]);
            t.start();
        }
        for (int i = 0; i < numThreads; i++) {
            while (!threads[i].complete()) {
                Waiter.sleepQuietly(500L);
            }
            if (threads[i].failed()) {
                fail("Thread " + i + " failed: " + threads[i].error.toString());
            }
        }
    }

    @BeforeEach
    public void setUp() {
        simpleFactory = new SimpleFactory();
        genericObjectPool = new GenericObjectPool<>(simpleFactory);
    }

    @AfterEach
    public void tearDown() throws Exception {
        final ObjectName jmxName = genericObjectPool.getJmxName();
        final String poolName = Objects.toString(jmxName, null);
        genericObjectPool.clear();
        genericObjectPool.close();
        genericObjectPool = null;
        simpleFactory = null;
        final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        final Set<ObjectName> result = mbs.queryNames(new ObjectName("org.apache.commoms.pool3:type=GenericObjectPool,*"), null);
        final int registeredPoolCount = result.size();
        final StringBuilder msg = new StringBuilder("Current pool is: ");
        msg.append(poolName);
        msg.append("  Still open pools are: ");
        for (final ObjectName name : result) {
            msg.append(name.toString());
            msg.append(" created via\n");
            msg.append(mbs.getAttribute(name, "CreationStackTrace"));
            msg.append('\n');
            mbs.unregisterMBean(name);
        }
        assertEquals(0, registeredPoolCount, msg.toString());
        Thread.yield();
        if (EvictionTimer.getExecutor() != null) {
            Thread.sleep(1000);
        }
        assertNull(EvictionTimer.getExecutor(), "EvictionTimer.getExecutor()");
    }

    @Test
    @Timeout(value = 60000, unit = TimeUnit.MILLISECONDS)
    public void testAddObject_1() throws Exception {
        assertEquals(0, genericObjectPool.getNumIdle(), "should be zero idle");
    }

    @Test
    @Timeout(value = 60000, unit = TimeUnit.MILLISECONDS)
    public void testAddObject_2_testMerged_2() throws Exception {
        genericObjectPool.addObject();
        assertEquals(1, genericObjectPool.getNumIdle(), "should be one idle");
        assertEquals(0, genericObjectPool.getNumActive(), "should be zero active");
        final String obj = genericObjectPool.borrowObject();
        assertEquals(1, genericObjectPool.getNumActive(), "should be one active");
    }

    @Test
    @Timeout(value = 60000, unit = TimeUnit.MILLISECONDS)
    public void testAddObject_4() throws Exception {
        assertEquals(0, genericObjectPool.getNumIdle(), "should be zero idle");
    }
}
