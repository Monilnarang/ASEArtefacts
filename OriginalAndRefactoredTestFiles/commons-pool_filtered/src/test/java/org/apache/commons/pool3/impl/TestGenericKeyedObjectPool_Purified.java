package org.apache.commons.pool3.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.lang.management.ManagementFactory;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.pool3.AbstractTestKeyedObjectPool;
import org.apache.commons.pool3.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool3.DestroyMode;
import org.apache.commons.pool3.KeyedObjectPool;
import org.apache.commons.pool3.KeyedPooledObjectFactory;
import org.apache.commons.pool3.PooledObject;
import org.apache.commons.pool3.TestException;
import org.apache.commons.pool3.VisitTracker;
import org.apache.commons.pool3.VisitTrackerFactory;
import org.apache.commons.pool3.Waiter;
import org.apache.commons.pool3.WaiterFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class TestGenericKeyedObjectPool_Purified extends AbstractTestKeyedObjectPool {

    private static final class DaemonThreadFactory implements ThreadFactory {

        @Override
        public Thread newThread(final Runnable r) {
            final Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        }
    }

    private static final class DummyFactory extends BaseKeyedPooledObjectFactory<Object, Object, RuntimeException> {

        @Override
        public Object create(final Object key) {
            return null;
        }

        @Override
        public PooledObject<Object> wrap(final Object value) {
            return new DefaultPooledObject<>(value);
        }
    }

    private static final class HashSetFactory extends BaseKeyedPooledObjectFactory<String, HashSet<String>, RuntimeException> {

        @Override
        public HashSet<String> create(final String key) {
            return new HashSet<>();
        }

        @Override
        public PooledObject<HashSet<String>> wrap(final HashSet<String> value) {
            return new DefaultPooledObject<>(value);
        }
    }

    private static class InvalidateThread implements Runnable {

        private final String obj;

        private final KeyedObjectPool<String, String, ? extends Exception> pool;

        private final String key;

        private boolean done;

        private InvalidateThread(final KeyedObjectPool<String, String, ? extends Exception> pool, final String key, final String obj) {
            this.obj = obj;
            this.pool = pool;
            this.key = key;
        }

        public boolean complete() {
            return done;
        }

        @Override
        public void run() {
            try {
                pool.invalidateObject(key, obj);
            } catch (final IllegalStateException ex) {
            } catch (final Exception ex) {
                fail("Unexpected exception " + ex.toString());
            } finally {
                done = true;
            }
        }
    }

    private static final class ObjectFactory extends BaseKeyedPooledObjectFactory<Integer, Object, RuntimeException> {

        @Override
        public Object create(final Integer key) {
            return new Object();
        }

        @Override
        public PooledObject<Object> wrap(final Object value) {
            return new DefaultPooledObject<>(value);
        }
    }

    public static class SimpleFactory<K> implements KeyedPooledObjectFactory<K, String, TestException> {

        volatile int counter;

        final boolean valid;

        int activeCount;

        int validateCounter;

        boolean evenValid = true;

        boolean oddValid = true;

        boolean enableValidation;

        long destroyLatency;

        long makeLatency;

        long validateLatency;

        volatile int maxTotalPerKey = Integer.MAX_VALUE;

        boolean exceptionOnPassivate;

        boolean exceptionOnActivate;

        boolean exceptionOnDestroy;

        boolean exceptionOnValidate;

        boolean exceptionOnCreate;

        public SimpleFactory() {
            this(true);
        }

        public SimpleFactory(final boolean valid) {
            this.valid = valid;
        }

        @Override
        public void activateObject(final K key, final PooledObject<String> obj) throws TestException {
            if (exceptionOnActivate && !(validateCounter++ % 2 == 0 ? evenValid : oddValid)) {
                throw new TestException();
            }
        }

        @Override
        public void destroyObject(final K key, final PooledObject<String> obj) throws TestException {
            doWait(destroyLatency);
            synchronized (this) {
                activeCount--;
            }
            if (exceptionOnDestroy) {
                throw new TestException();
            }
        }

        private void doWait(final long latency) {
            Waiter.sleepQuietly(latency);
        }

        @Override
        public PooledObject<String> makeObject(final K key) throws TestException {
            if (exceptionOnCreate) {
                throw new TestException();
            }
            doWait(makeLatency);
            String out = null;
            synchronized (this) {
                activeCount++;
                if (activeCount > maxTotalPerKey) {
                    throw new IllegalStateException("Too many active instances: " + activeCount);
                }
                out = String.valueOf(key) + String.valueOf(counter++);
            }
            return new DefaultPooledObject<>(out);
        }

        @Override
        public void passivateObject(final K key, final PooledObject<String> obj) throws TestException {
            if (exceptionOnPassivate) {
                throw new TestException();
            }
        }

        public void setDestroyLatency(final long destroyLatency) {
            this.destroyLatency = destroyLatency;
        }

        void setEvenValid(final boolean valid) {
            evenValid = valid;
        }

        public void setMakeLatency(final long makeLatency) {
            this.makeLatency = makeLatency;
        }

        public void setMaxTotalPerKey(final int maxTotalPerKey) {
            this.maxTotalPerKey = maxTotalPerKey;
        }

        public void setThrowExceptionOnActivate(final boolean b) {
            exceptionOnActivate = b;
        }

        public void setThrowExceptionOnDestroy(final boolean b) {
            exceptionOnDestroy = b;
        }

        public void setThrowExceptionOnPassivate(final boolean b) {
            exceptionOnPassivate = b;
        }

        public void setThrowExceptionOnValidate(final boolean b) {
            exceptionOnValidate = b;
        }

        void setValid(final boolean valid) {
            evenValid = valid;
            oddValid = valid;
        }

        public void setValidateLatency(final long validateLatency) {
            this.validateLatency = validateLatency;
        }

        public void setValidationEnabled(final boolean b) {
            enableValidation = b;
        }

        @Override
        public boolean validateObject(final K key, final PooledObject<String> obj) {
            doWait(validateLatency);
            if (exceptionOnValidate) {
                throw new RuntimeException("validation failed");
            }
            if (enableValidation) {
                return validateCounter++ % 2 == 0 ? evenValid : oddValid;
            }
            return valid;
        }
    }

    private static final class SimplePerKeyFactory extends BaseKeyedPooledObjectFactory<Object, Object, RuntimeException> {

        final ConcurrentHashMap<Object, AtomicInteger> map = new ConcurrentHashMap<>();

        @Override
        public Object create(final Object key) {
            final int counter = map.computeIfAbsent(key, k -> new AtomicInteger(-1)).incrementAndGet();
            return String.valueOf(key) + String.valueOf(counter);
        }

        @Override
        public PooledObject<Object> wrap(final Object value) {
            return new DefaultPooledObject<>(value);
        }
    }

    private static class SimpleTestThread<T, E extends Exception> implements Runnable {

        private final KeyedObjectPool<String, T, E> pool;

        private final String key;

        private SimpleTestThread(final KeyedObjectPool<String, T, E> pool, final String key) {
            this.pool = pool;
            this.key = key;
        }

        @Override
        public void run() {
            try {
                pool.returnObject(key, pool.borrowObject(key));
            } catch (final Exception e) {
            }
        }
    }

    private static final class SlowEvictionPolicy<T> extends DefaultEvictionPolicy<T> {

        private final long delay;

        private SlowEvictionPolicy(final long delay) {
            this.delay = delay;
        }

        @Override
        public boolean evict(final EvictionConfig config, final PooledObject<T> underTest, final int idleCount) {
            Waiter.sleepQuietly(delay);
            return super.evict(config, underTest, idleCount);
        }
    }

    private static class TestThread<T, E extends Exception> implements Runnable {

        private final Random random = new Random();

        private final KeyedObjectPool<String, T, E> pool;

        private final int iter;

        private final int startDelay;

        private final int holdTime;

        private final boolean randomDelay;

        private final T expectedObject;

        private final String key;

        private volatile boolean complete;

        private volatile boolean failed;

        private volatile Exception exception;

        private TestThread(final KeyedObjectPool<String, T, E> pool) {
            this(pool, 100, 50, 50, true, null, null);
        }

        private TestThread(final KeyedObjectPool<String, T, E> pool, final int iter) {
            this(pool, iter, 50, 50, true, null, null);
        }

        private TestThread(final KeyedObjectPool<String, T, E> pool, final int iter, final int delay) {
            this(pool, iter, delay, delay, true, null, null);
        }

        private TestThread(final KeyedObjectPool<String, T, E> pool, final int iter, final int startDelay, final int holdTime, final boolean randomDelay, final T expectedObject, final String key) {
            this.pool = pool;
            this.iter = iter;
            this.startDelay = startDelay;
            this.holdTime = holdTime;
            this.randomDelay = randomDelay;
            this.expectedObject = expectedObject;
            this.key = key;
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
                final String actualKey = key == null ? String.valueOf(random.nextInt(3)) : key;
                Waiter.sleepQuietly(randomDelay ? random.nextInt(startDelay) : startDelay);
                T obj = null;
                try {
                    obj = pool.borrowObject(actualKey);
                } catch (final Exception e) {
                    exception = e;
                    failed = true;
                    complete = true;
                    break;
                }
                if (expectedObject != null && !expectedObject.equals(obj)) {
                    exception = new Exception("Expected: " + expectedObject + " found: " + obj);
                    failed = true;
                    complete = true;
                    break;
                }
                Waiter.sleepQuietly(randomDelay ? random.nextInt(holdTime) : holdTime);
                try {
                    pool.returnObject(actualKey, obj);
                } catch (final Exception e) {
                    exception = e;
                    failed = true;
                    complete = true;
                    break;
                }
            }
            complete = true;
        }
    }

    static class WaitingTestThread<E extends Exception> extends Thread {

        private final KeyedObjectPool<String, String, E> pool;

        private final String key;

        private final long pause;

        private Throwable thrown;

        private long preBorrowMillis;

        private long postBorrowMillis;

        private long postReturnMillis;

        private long endedMillis;

        private String objectId;

        WaitingTestThread(final KeyedObjectPool<String, String, E> pool, final String key, final long pause) {
            this.pool = pool;
            this.key = key;
            this.pause = pause;
            this.thrown = null;
        }

        @Override
        public void run() {
            try {
                preBorrowMillis = System.currentTimeMillis();
                final String obj = pool.borrowObject(key);
                objectId = obj;
                postBorrowMillis = System.currentTimeMillis();
                Thread.sleep(pause);
                pool.returnObject(key, obj);
                postReturnMillis = System.currentTimeMillis();
            } catch (final Exception e) {
                thrown = e;
            } finally {
                endedMillis = System.currentTimeMillis();
            }
        }
    }

    private static final Integer KEY_ZERO = Integer.valueOf(0);

    private static final Integer KEY_ONE = Integer.valueOf(1);

    private static final Integer KEY_TWO = Integer.valueOf(2);

    private static final boolean DISPLAY_THREAD_DETAILS = Boolean.getBoolean("TestGenericKeyedObjectPool.display.thread.details");

    private GenericKeyedObjectPool<String, String, TestException> gkoPool;

    private SimpleFactory<String> simpleFactory;

    private void checkEvictionOrder(final boolean lifo) throws InterruptedException, TestException {
        final SimpleFactory<Integer> intFactory = new SimpleFactory<>();
        try (GenericKeyedObjectPool<Integer, String, TestException> intPool = new GenericKeyedObjectPool<>(intFactory)) {
            intPool.setNumTestsPerEvictionRun(2);
            intPool.setMinEvictableIdleDuration(Duration.ofMillis(100));
            intPool.setLifo(lifo);
            for (int i = 0; i < 3; i++) {
                final Integer key = Integer.valueOf(i);
                for (int j = 0; j < 5; j++) {
                    intPool.addObject(key);
                }
            }
            Thread.sleep(200);
            intPool.evict();
            assertEquals(3, intPool.getNumIdle(KEY_ZERO));
            final String objZeroA = intPool.borrowObject(KEY_ZERO);
            assertTrue(lifo ? objZeroA.equals("04") : objZeroA.equals("02"));
            assertEquals(2, intPool.getNumIdle(KEY_ZERO));
            final String objZeroB = intPool.borrowObject(KEY_ZERO);
            assertEquals("03", objZeroB);
            assertEquals(1, intPool.getNumIdle(KEY_ZERO));
            intPool.evict();
            assertEquals(0, intPool.getNumIdle(KEY_ZERO));
            assertEquals(4, intPool.getNumIdle(KEY_ONE));
            final String objOneA = intPool.borrowObject(KEY_ONE);
            assertTrue(lifo ? objOneA.equals("19") : objOneA.equals("16"));
            assertEquals(3, intPool.getNumIdle(KEY_ONE));
            final String objOneB = intPool.borrowObject(KEY_ONE);
            assertTrue(lifo ? objOneB.equals("18") : objOneB.equals("17"));
            assertEquals(2, intPool.getNumIdle(KEY_ONE));
            intPool.evict();
            assertEquals(0, intPool.getNumIdle(KEY_ONE));
            intPool.evict();
            assertEquals(3, intPool.getNumIdle(KEY_TWO));
            final String objTwoA = intPool.borrowObject(KEY_TWO);
            assertTrue(lifo ? objTwoA.equals("214") : objTwoA.equals("212"));
            assertEquals(2, intPool.getNumIdle(KEY_TWO));
            intPool.evict();
            assertEquals(0, intPool.getNumIdle(KEY_TWO));
            intPool.evict();
            intPool.returnObject(KEY_ZERO, objZeroA);
            intPool.returnObject(KEY_ZERO, objZeroB);
            intPool.returnObject(KEY_ONE, objOneA);
            intPool.returnObject(KEY_ONE, objOneB);
            intPool.returnObject(KEY_TWO, objTwoA);
            intPool.clear();
            intPool.setMinEvictableIdleDuration(Duration.ofMillis(500));
            intFactory.counter = 0;
            for (int i = 0; i < 3; i++) {
                final Integer key = Integer.valueOf(i);
                for (int j = 0; j < 5; j++) {
                    intPool.addObject(key);
                }
                Thread.sleep(200);
            }
            intPool.evict();
            assertEquals(3, intPool.getNumIdle(KEY_ZERO));
            intPool.evict();
            assertEquals(1, intPool.getNumIdle(KEY_ZERO));
            intPool.evict();
            assertEquals(0, intPool.getNumIdle(KEY_ZERO));
            assertEquals(5, intPool.getNumIdle(KEY_ONE));
            assertEquals(5, intPool.getNumIdle(KEY_TWO));
            intPool.evict();
            assertEquals(5, intPool.getNumIdle(KEY_ONE));
            assertEquals(5, intPool.getNumIdle(KEY_TWO));
            intPool.evict();
            assertEquals(5, intPool.getNumIdle(KEY_ONE));
            assertEquals(5, intPool.getNumIdle(KEY_TWO));
            intPool.evict();
            assertEquals(5, intPool.getNumIdle(KEY_ONE));
            assertEquals(5, intPool.getNumIdle(KEY_TWO));
            intPool.evict();
            assertEquals(5, intPool.getNumIdle(KEY_ONE));
            assertEquals(5, intPool.getNumIdle(KEY_TWO));
            intPool.evict();
            assertEquals(5, intPool.getNumIdle(KEY_ONE));
            assertEquals(5, intPool.getNumIdle(KEY_TWO));
            Thread.sleep(200);
            intPool.evict();
            assertEquals(3, intPool.getNumIdle(KEY_ONE));
            assertEquals(5, intPool.getNumIdle(KEY_TWO));
            final String obj = intPool.borrowObject(KEY_ONE);
            if (lifo) {
                assertEquals("19", obj);
            } else {
                assertEquals("15", obj);
            }
        }
    }

    private void checkEvictorVisiting(final boolean lifo) throws Exception {
        VisitTrackerFactory<Integer> trackerFactory = new VisitTrackerFactory<>();
        try (GenericKeyedObjectPool<Integer, VisitTracker<Integer>, RuntimeException> intPool = new GenericKeyedObjectPool<>(trackerFactory)) {
            intPool.setNumTestsPerEvictionRun(2);
            intPool.setMinEvictableIdleDuration(Duration.ofMillis(-1));
            intPool.setTestWhileIdle(true);
            intPool.setLifo(lifo);
            intPool.setTestOnReturn(false);
            intPool.setTestOnBorrow(false);
            for (int i = 0; i < 3; i++) {
                trackerFactory.resetId();
                final Integer key = Integer.valueOf(i);
                for (int j = 0; j < 8; j++) {
                    intPool.addObject(key);
                }
            }
            intPool.evict();
            VisitTracker<Integer> obj = intPool.borrowObject(KEY_ZERO);
            intPool.returnObject(KEY_ZERO, obj);
            obj = intPool.borrowObject(KEY_ZERO);
            intPool.returnObject(KEY_ZERO, obj);
            intPool.evict();
            for (int i = 0; i < 8; i++) {
                final VisitTracker<Integer> tracker = intPool.borrowObject(KEY_ZERO);
                if (tracker.getId() >= 4) {
                    assertEquals(0, tracker.getValidateCount(), "Unexpected instance visited " + tracker.getId());
                } else {
                    assertEquals(1, tracker.getValidateCount(), "Instance " + tracker.getId() + " visited wrong number of times.");
                }
            }
            intPool.setNumTestsPerEvictionRun(3);
            intPool.evict();
            intPool.evict();
            obj = intPool.borrowObject(KEY_ONE);
            intPool.returnObject(KEY_ONE, obj);
            obj = intPool.borrowObject(KEY_ONE);
            intPool.returnObject(KEY_ONE, obj);
            obj = intPool.borrowObject(KEY_ONE);
            intPool.returnObject(KEY_ONE, obj);
            intPool.evict();
            intPool.evict();
            intPool.evict();
            intPool.evict();
            for (int i = 0; i < 8; i++) {
                final VisitTracker<Integer> tracker = intPool.borrowObject(KEY_ONE);
                if (lifo && tracker.getId() > 1 || !lifo && tracker.getId() > 2) {
                    assertEquals(1, tracker.getValidateCount(), "Instance " + tracker.getId() + " visited wrong number of times.");
                } else {
                    assertEquals(2, tracker.getValidateCount(), "Instance " + tracker.getId() + " visited wrong number of times.");
                }
            }
        }
        final int[] smallPrimes = { 2, 3, 5, 7 };
        final Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        for (int i = 0; i < smallPrimes.length; i++) {
            for (int j = 0; j < 5; j++) {
                trackerFactory = new VisitTrackerFactory<>();
                try (GenericKeyedObjectPool<Integer, VisitTracker<Integer>, RuntimeException> intPool = new GenericKeyedObjectPool<>(trackerFactory)) {
                    intPool.setMaxIdlePerKey(-1);
                    intPool.setMaxTotalPerKey(-1);
                    intPool.setNumTestsPerEvictionRun(smallPrimes[i]);
                    intPool.setMinEvictableIdleDuration(Duration.ofMillis(-1));
                    intPool.setTestWhileIdle(true);
                    intPool.setLifo(lifo);
                    intPool.setTestOnReturn(false);
                    intPool.setTestOnBorrow(false);
                    final int zeroLength = 10 + random.nextInt(20);
                    for (int k = 0; k < zeroLength; k++) {
                        intPool.addObject(KEY_ZERO);
                    }
                    final int oneLength = 10 + random.nextInt(20);
                    for (int k = 0; k < oneLength; k++) {
                        intPool.addObject(KEY_ONE);
                    }
                    final int twoLength = 10 + random.nextInt(20);
                    for (int k = 0; k < twoLength; k++) {
                        intPool.addObject(KEY_TWO);
                    }
                    final int runs = 10 + random.nextInt(50);
                    for (int k = 0; k < runs; k++) {
                        intPool.evict();
                    }
                    final int totalInstances = zeroLength + oneLength + twoLength;
                    final int cycleCount = runs * intPool.getNumTestsPerEvictionRun() / totalInstances;
                    VisitTracker<Integer> tracker = null;
                    int visitCount = 0;
                    for (int k = 0; k < zeroLength; k++) {
                        tracker = intPool.borrowObject(KEY_ZERO);
                        visitCount = tracker.getValidateCount();
                        if (visitCount < cycleCount || visitCount > cycleCount + 1) {
                            fail(formatSettings("ZERO", "runs", runs, "lifo", lifo, "i", i, "j", j, "k", k, "visitCount", visitCount, "cycleCount", cycleCount, "totalInstances", totalInstances, zeroLength, oneLength, twoLength));
                        }
                    }
                    for (int k = 0; k < oneLength; k++) {
                        tracker = intPool.borrowObject(KEY_ONE);
                        visitCount = tracker.getValidateCount();
                        if (visitCount < cycleCount || visitCount > cycleCount + 1) {
                            fail(formatSettings("ONE", "runs", runs, "lifo", lifo, "i", i, "j", j, "k", k, "visitCount", visitCount, "cycleCount", cycleCount, "totalInstances", totalInstances, zeroLength, oneLength, twoLength));
                        }
                    }
                    final int[] visits = new int[twoLength];
                    for (int k = 0; k < twoLength; k++) {
                        tracker = intPool.borrowObject(KEY_TWO);
                        visitCount = tracker.getValidateCount();
                        visits[k] = visitCount;
                        if (visitCount < cycleCount || visitCount > cycleCount + 1) {
                            final StringBuilder sb = new StringBuilder("Visits:");
                            for (int l = 0; l <= k; l++) {
                                sb.append(visits[l]).append(' ');
                            }
                            fail(formatSettings("TWO " + sb.toString(), "runs", runs, "lifo", lifo, "i", i, "j", j, "k", k, "visitCount", visitCount, "cycleCount", cycleCount, "totalInstances", totalInstances, zeroLength, oneLength, twoLength));
                        }
                    }
                }
            }
        }
    }

    private String formatSettings(final String title, final String s, final int i, final String s0, final boolean b0, final String s1, final int i1, final String s2, final int i2, final String s3, final int i3, final String s4, final int i4, final String s5, final int i5, final String s6, final int i6, final int zeroLength, final int oneLength, final int twoLength) {
        final StringBuilder sb = new StringBuilder(80);
        sb.append(title).append(' ');
        sb.append(s).append('=').append(i).append(' ');
        sb.append(s0).append('=').append(b0).append(' ');
        sb.append(s1).append('=').append(i1).append(' ');
        sb.append(s2).append('=').append(i2).append(' ');
        sb.append(s3).append('=').append(i3).append(' ');
        sb.append(s4).append('=').append(i4).append(' ');
        sb.append(s5).append('=').append(i5).append(' ');
        sb.append(s6).append('=').append(i6).append(' ');
        sb.append("Lengths=").append(zeroLength).append(',').append(oneLength).append(',').append(twoLength).append(' ');
        return sb.toString();
    }

    @Override
    protected Object getNthObject(final Object key, final int n) {
        return String.valueOf(key) + String.valueOf(n);
    }

    @Override
    protected boolean isFifo() {
        return false;
    }

    @Override
    protected boolean isLifo() {
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected <E extends Exception> KeyedObjectPool<Object, Object, E> makeEmptyPool(final int minCapacity) {
        final KeyedPooledObjectFactory<Object, Object, RuntimeException> perKeyFactory = new SimplePerKeyFactory();
        final GenericKeyedObjectPool<Object, Object, RuntimeException> perKeyPool = new GenericKeyedObjectPool<>(perKeyFactory);
        perKeyPool.setMaxTotalPerKey(minCapacity);
        perKeyPool.setMaxIdlePerKey(minCapacity);
        return (KeyedObjectPool<Object, Object, E>) perKeyPool;
    }

    @Override
    protected <E extends Exception> KeyedObjectPool<Object, Object, E> makeEmptyPool(final KeyedPooledObjectFactory<Object, Object, E> fac) {
        return new GenericKeyedObjectPool<>(fac);
    }

    @Override
    protected Object makeKey(final int n) {
        return String.valueOf(n);
    }

    public <T, E extends Exception> void runTestThreads(final int numThreads, final int iterations, final int delay, final GenericKeyedObjectPool<String, T, E> gkopPool) {
        final ArrayList<TestThread<T, E>> threads = new ArrayList<>();
        for (int i = 0; i < numThreads; i++) {
            final TestThread<T, E> testThread = new TestThread<>(gkopPool, iterations, delay);
            threads.add(testThread);
            final Thread t = new Thread(testThread);
            t.start();
        }
        for (final TestThread<T, E> testThread : threads) {
            while (!testThread.complete()) {
                Waiter.sleepQuietly(500L);
            }
            if (testThread.failed()) {
                fail("Thread failed: " + threads.indexOf(testThread) + "\n" + ExceptionUtils.getStackTrace(testThread.exception));
            }
        }
    }

    @BeforeEach
    public void setUp() {
        simpleFactory = new SimpleFactory<>();
        gkoPool = new GenericKeyedObjectPool<>(simpleFactory);
    }

    @AfterEach
    public void tearDownJmx() throws Exception {
        super.tearDown();
        final ObjectName jmxName = gkoPool.getJmxName();
        final String poolName = Objects.toString(jmxName, null);
        gkoPool.clear();
        gkoPool.close();
        gkoPool = null;
        simpleFactory = null;
        final MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        final Set<ObjectName> result = mbs.queryNames(new ObjectName("org.apache.commoms.pool3:type=GenericKeyedObjectPool,*"), null);
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
    }

    @Test
    @Timeout(value = 60_000, unit = TimeUnit.MILLISECONDS)
    public void testNumActiveNumIdle2_1() throws Exception {
        assertEquals(0, gkoPool.getNumActive());
    }

    @Test
    @Timeout(value = 60_000, unit = TimeUnit.MILLISECONDS)
    public void testNumActiveNumIdle2_2() throws Exception {
        assertEquals(0, gkoPool.getNumIdle());
    }

    @Test
    @Timeout(value = 60_000, unit = TimeUnit.MILLISECONDS)
    public void testNumActiveNumIdle2_3() throws Exception {
        assertEquals(0, gkoPool.getNumActive("A"));
    }

    @Test
    @Timeout(value = 60_000, unit = TimeUnit.MILLISECONDS)
    public void testNumActiveNumIdle2_4() throws Exception {
        assertEquals(0, gkoPool.getNumIdle("A"));
    }

    @Test
    @Timeout(value = 60_000, unit = TimeUnit.MILLISECONDS)
    public void testNumActiveNumIdle2_5() throws Exception {
        assertEquals(0, gkoPool.getNumActive("B"));
    }

    @Test
    @Timeout(value = 60_000, unit = TimeUnit.MILLISECONDS)
    public void testNumActiveNumIdle2_6() throws Exception {
        assertEquals(0, gkoPool.getNumIdle("B"));
    }

    @Test
    @Timeout(value = 60_000, unit = TimeUnit.MILLISECONDS)
    public void testNumActiveNumIdle2_7_testMerged_7() throws Exception {
        final String objA0 = gkoPool.borrowObject("A");
        final String objB0 = gkoPool.borrowObject("B");
        assertEquals(2, gkoPool.getNumActive());
        assertEquals(1, gkoPool.getNumActive("A"));
        assertEquals(1, gkoPool.getNumActive("B"));
        final String objA1 = gkoPool.borrowObject("A");
        final String objB1 = gkoPool.borrowObject("B");
        assertEquals(4, gkoPool.getNumActive());
        assertEquals(2, gkoPool.getNumActive("A"));
        assertEquals(2, gkoPool.getNumActive("B"));
        gkoPool.returnObject("A", objA0);
        gkoPool.returnObject("B", objB0);
        assertEquals(2, gkoPool.getNumIdle());
        assertEquals(1, gkoPool.getNumIdle("A"));
        assertEquals(1, gkoPool.getNumIdle("B"));
        gkoPool.returnObject("A", objA1);
        gkoPool.returnObject("B", objB1);
        assertEquals(4, gkoPool.getNumIdle());
        assertEquals(2, gkoPool.getNumIdle("A"));
        assertEquals(2, gkoPool.getNumIdle("B"));
    }

    @Test
    @Timeout(value = 60_000, unit = TimeUnit.MILLISECONDS)
    public void testNumActiveNumIdle2_8() throws Exception {
        assertEquals(0, gkoPool.getNumIdle());
    }

    @Test
    @Timeout(value = 60_000, unit = TimeUnit.MILLISECONDS)
    public void testNumActiveNumIdle2_10() throws Exception {
        assertEquals(0, gkoPool.getNumIdle("A"));
    }

    @Test
    @Timeout(value = 60_000, unit = TimeUnit.MILLISECONDS)
    public void testNumActiveNumIdle2_12() throws Exception {
        assertEquals(0, gkoPool.getNumIdle("B"));
    }

    @Test
    @Timeout(value = 60_000, unit = TimeUnit.MILLISECONDS)
    public void testNumActiveNumIdle2_14() throws Exception {
        assertEquals(0, gkoPool.getNumIdle());
    }

    @Test
    @Timeout(value = 60_000, unit = TimeUnit.MILLISECONDS)
    public void testNumActiveNumIdle2_16() throws Exception {
        assertEquals(0, gkoPool.getNumIdle("A"));
    }

    @Test
    @Timeout(value = 60_000, unit = TimeUnit.MILLISECONDS)
    public void testNumActiveNumIdle2_18() throws Exception {
        assertEquals(0, gkoPool.getNumIdle("B"));
    }

    @Test
    @Timeout(value = 60_000, unit = TimeUnit.MILLISECONDS)
    public void testNumActiveNumIdle2_25() throws Exception {
        assertEquals(0, gkoPool.getNumActive());
    }

    @Test
    @Timeout(value = 60_000, unit = TimeUnit.MILLISECONDS)
    public void testNumActiveNumIdle2_27() throws Exception {
        assertEquals(0, gkoPool.getNumActive("A"));
    }

    @Test
    @Timeout(value = 60_000, unit = TimeUnit.MILLISECONDS)
    public void testNumActiveNumIdle2_29() throws Exception {
        assertEquals(0, gkoPool.getNumActive("B"));
    }
}
