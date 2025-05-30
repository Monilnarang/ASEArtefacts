package org.eclipse.collections.impl.block.function.primitive;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public final class DoubleFunctionImplTest_Purified {

    private static final Object JUNK = new Object();

    private static final class TestDoubleFunctionImpl extends DoubleFunctionImpl<Object> {

        private static final long serialVersionUID = 1L;

        private final double toReturn;

        private TestDoubleFunctionImpl(double toReturn) {
            this.toReturn = toReturn;
        }

        @Override
        public double doubleValueOf(Object anObject) {
            return this.toReturn;
        }
    }

    @Test
    public void testValueOf_1() {
        assertSame(new TestDoubleFunctionImpl(0.0d).valueOf(JUNK), new TestDoubleFunctionImpl(0.0d).valueOf(JUNK));
    }

    @Test
    public void testValueOf_2() {
        assertEquals(Double.valueOf(1.0d), new TestDoubleFunctionImpl(1.0d).valueOf(JUNK));
    }
}
