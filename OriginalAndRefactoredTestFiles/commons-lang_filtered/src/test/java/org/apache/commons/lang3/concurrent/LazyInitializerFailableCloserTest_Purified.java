package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.Test;

public class LazyInitializerFailableCloserTest_Purified extends AbstractConcurrentInitializerTest {

    private final AtomicBoolean closed = new AtomicBoolean();

    @Override
    protected LazyInitializer<Object> createInitializer() {
        return LazyInitializer.builder().setInitializer(this::makeObject).setCloser(e -> throwingCloser()).get();
    }

    private Object makeObject() throws ConcurrentException {
        if (closed.get()) {
            throw new ConcurrentException("test", new IOException());
        }
        return new Object();
    }

    private void throwingCloser() throws ConcurrentException {
        closed.set(true);
        if (!closed.get()) {
            throw new ConcurrentException("test", new IOException());
        }
    }

    @Test
    public void testIsInitialized_1_testMerged_1() throws ConcurrentException {
        final LazyInitializer<Object> initializer = createInitializer();
        assertFalse(initializer.isInitialized());
        initializer.get();
        assertTrue(initializer.isInitialized());
    }

    @Test
    public void testIsInitialized_3() throws ConcurrentException {
        assertFalse(closed.get());
    }

    @Test
    public void testIsInitialized_4() throws ConcurrentException {
        assertTrue(closed.get());
    }
}
