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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class CloseableUtilsTest_Parameterized {

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
    public void test_closeAndSuppressExceptions_ioException_1() {
        CloseableUtils.closeAndSuppressExceptions(ioExceptionCloseable, chomper);
        assertClosed(ioExceptionCloseable);
    }

    @Test
    public void test_closeAndSuppressExceptions_runtimeException_1() {
        CloseableUtils.closeAndSuppressExceptions(runtimeExceptionCloseable, chomper);
        assertClosed(runtimeExceptionCloseable);
    }

    @Test
    public void test_closeAndSuppressExceptions_assertionError_1() {
        CloseableUtils.closeAndSuppressExceptions(assertionErrorCloseable, chomper);
        assertClosed(assertionErrorCloseable);
    }

    @ParameterizedTest
    @MethodSource("Provider_test_closeAndSuppressExceptions_quiet_2_2_2_2")
    public void test_closeAndSuppressExceptions_quiet_2_2_2_2(int param1) {
        Assert.assertEquals(param1, chomped.get());
    }

    static public Stream<Arguments> Provider_test_closeAndSuppressExceptions_quiet_2_2_2_2() {
        return Stream.of(arguments(0), arguments(1), arguments(1), arguments(1));
    }
}
