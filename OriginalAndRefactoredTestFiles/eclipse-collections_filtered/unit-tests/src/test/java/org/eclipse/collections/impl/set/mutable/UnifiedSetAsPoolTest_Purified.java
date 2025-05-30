package org.eclipse.collections.impl.set.mutable;

import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

public class UnifiedSetAsPoolTest_Purified {

    private final UnifiedSet<Integer> staticPool = UnifiedSet.newSet();

    private static final class AlwaysEqual {

        @Override
        public boolean equals(Object obj) {
            return obj != null;
        }

        @Override
        public int hashCode() {
            return 0;
        }
    }

    @Test
    public void removeFromPool_1() {
        Integer firstObject = 1;
        this.staticPool.put(firstObject);
        Integer returnedObject = this.staticPool.removeFromPool(firstObject);
        assertSame(returnedObject, firstObject);
    }

    @Test
    public void removeFromPool_2() {
        Verify.assertEmpty(this.staticPool);
    }
}
