package org.apache.druid.utils;

import com.google.common.base.Throwables;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.internal.matchers.ThrowableCauseMatcher;
import org.junit.internal.matchers.ThrowableMessageMatcher;
import javax.annotation.Nullable;
import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public class CloseableUtilsTest_Purified {

    private final TestCloseable quietCloseable = new TestCloseable(null);

    private final TestCloseable quietCloseable2 = new TestCloseable(null);

    private final TestCloseable ioExceptionCloseable = new TestCloseable(new IOException());

    private final TestCloseable runtimeExceptionCloseable = new TestCloseable(new IllegalArgumentException());

    private final TestCloseable assertionErrorCloseable = new TestCloseable(new AssertionError());

    private final AtomicLong chomped = new AtomicLong();

    private final Consumer<Throwable> chomper = e -> chomped.incrementAndGet();

    private static void assertClosed(final TestCloseable... closeables) {
        for (TestCloseable closeable : closeables) {
            Assert.assertTrue(closeable.isClosed());
        }
    }

    private static class TestCloseable implements Closeable {

        @Nullable
        private final Throwable e;

        private final AtomicBoolean closed = new AtomicBoolean(false);

        TestCloseable(@Nullable Throwable e) {
            this.e = e;
        }

        @Override
        public void close() throws IOException {
            closed.set(true);
            if (e != null) {
                Throwables.propagateIfInstanceOf(e, IOException.class);
                throw Throwables.propagate(e);
            }
        }

        public boolean isClosed() {
            return closed.get();
        }
    }

    @Test
    public void test_closeAndSuppressExceptions_quiet_1() {
        CloseableUtils.closeAndSuppressExceptions(quietCloseable, chomper);
        assertClosed(quietCloseable);
    }

    @Test
    public void test_closeAndSuppressExceptions_quiet_2() {
        Assert.assertEquals(0, chomped.get());
    }

    @Test
    public void test_closeAndSuppressExceptions_ioException_1() {
        CloseableUtils.closeAndSuppressExceptions(ioExceptionCloseable, chomper);
        assertClosed(ioExceptionCloseable);
    }

    @Test
    public void test_closeAndSuppressExceptions_ioException_2() {
        Assert.assertEquals(1, chomped.get());
    }

    @Test
    public void test_closeAndSuppressExceptions_runtimeException_1() {
        CloseableUtils.closeAndSuppressExceptions(runtimeExceptionCloseable, chomper);
        assertClosed(runtimeExceptionCloseable);
    }

    @Test
    public void test_closeAndSuppressExceptions_runtimeException_2() {
        Assert.assertEquals(1, chomped.get());
    }

    @Test
    public void test_closeAndSuppressExceptions_assertionError_1() {
        CloseableUtils.closeAndSuppressExceptions(assertionErrorCloseable, chomper);
        assertClosed(assertionErrorCloseable);
    }

    @Test
    public void test_closeAndSuppressExceptions_assertionError_2() {
        Assert.assertEquals(1, chomped.get());
    }
}
