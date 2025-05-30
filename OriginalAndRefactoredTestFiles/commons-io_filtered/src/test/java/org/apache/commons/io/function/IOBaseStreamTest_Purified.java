package org.apache.commons.io.function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.BaseStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IOBaseStreamTest_Purified {

    private static class IOBaseStreamFixture<T, S extends IOBaseStreamFixture<T, S, B>, B extends BaseStream<T, B>> implements IOBaseStream<T, S, B> {

        private final B baseStream;

        private IOBaseStreamFixture(final B baseStream) {
            this.baseStream = baseStream;
        }

        @Override
        public B unwrap() {
            return baseStream;
        }

        @SuppressWarnings("unchecked")
        @Override
        public S wrap(final B delegate) {
            return delegate == baseStream ? (S) this : (S) new IOBaseStreamFixture<T, S, B>(delegate);
        }
    }

    private static final class IOBaseStreamPathFixture<B extends BaseStream<Path, B>> extends IOBaseStreamFixture<Path, IOBaseStreamPathFixture<B>, B> {

        private IOBaseStreamPathFixture(final B baseStream) {
            super(baseStream);
        }

        @Override
        public IOBaseStreamPathFixture<B> wrap(final B delegate) {
            return delegate == unwrap() ? this : new IOBaseStreamPathFixture<>(delegate);
        }
    }

    private static final class MyRuntimeException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        MyRuntimeException(final String message) {
            super(message);
        }
    }

    private BaseStream<Path, ? extends BaseStream<Path, ?>> baseStream;

    private IOBaseStreamFixture<Path, ? extends IOBaseStreamFixture<Path, ?, ?>, ?> ioBaseStream;

    private IOBaseStreamPathFixture<? extends BaseStream<Path, ?>> ioBaseStreamPath;

    private IOStream<Path> ioBaseStreamAdapter;

    @BeforeEach
    public void beforeEach() {
        baseStream = createStreamOfPaths();
        ioBaseStream = createIOBaseStream();
        ioBaseStreamPath = createIOBaseStreamPath();
        ioBaseStreamAdapter = createIOBaseStreamAdapter();
    }

    private IOBaseStreamFixture<Path, ?, Stream<Path>> createIOBaseStream() {
        return new IOBaseStreamFixture<>(createStreamOfPaths());
    }

    private IOStream<Path> createIOBaseStreamAdapter() {
        return IOStreamAdapter.adapt(createStreamOfPaths());
    }

    private IOBaseStreamPathFixture<Stream<Path>> createIOBaseStreamPath() {
        return new IOBaseStreamPathFixture<>(createStreamOfPaths());
    }

    private Stream<Path> createStreamOfPaths() {
        return Stream.of(TestConstants.ABS_PATH_A, TestConstants.ABS_PATH_B);
    }

    @SuppressWarnings("resource")
    @Test
    public void testIsParallel_1() {
        assertFalse(baseStream.isParallel());
    }

    @SuppressWarnings("resource")
    @Test
    public void testIsParallel_2() {
        assertFalse(ioBaseStream.isParallel());
    }

    @SuppressWarnings("resource")
    @Test
    public void testIsParallel_3() {
        assertFalse(ioBaseStream.asBaseStream().isParallel());
    }

    @SuppressWarnings("resource")
    @Test
    public void testIsParallel_4() {
        assertFalse(ioBaseStreamPath.asBaseStream().isParallel());
    }

    @SuppressWarnings("resource")
    @Test
    public void testIsParallel_5() {
        assertFalse(ioBaseStreamPath.isParallel());
    }
}
