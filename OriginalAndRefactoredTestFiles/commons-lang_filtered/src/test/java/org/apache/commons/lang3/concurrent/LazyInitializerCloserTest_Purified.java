package org.apache.commons.lang3.concurrent;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.Test;

public class LazyInitializerCloserTest_Purified extends AbstractConcurrentInitializerTest {

    private final AtomicBoolean closed = new AtomicBoolean();

    @Override
    protected LazyInitializer<Object> createInitializer() {
        return LazyInitializer.builder().setInitializer(Object::new).setCloser(e -> closed.set(true)).get();
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
