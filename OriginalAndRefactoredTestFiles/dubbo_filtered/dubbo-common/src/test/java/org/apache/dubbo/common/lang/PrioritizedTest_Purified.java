package org.apache.dubbo.common.lang;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import static java.util.Arrays.asList;
import static java.util.Collections.sort;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PrioritizedTest_Purified {

    public static PrioritizedValue of(int value) {
        return new PrioritizedValue(value);
    }

    static class PrioritizedValue implements Prioritized {

        private final int value;

        private PrioritizedValue(int value) {
            this.value = value;
        }

        @Override
        public int getPriority() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof PrioritizedValue))
                return false;
            PrioritizedValue that = (PrioritizedValue) o;
            return value == that.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    @Test
    void testConstants_1() {
        assertEquals(Integer.MAX_VALUE, Prioritized.MIN_PRIORITY);
    }

    @Test
    void testConstants_2() {
        assertEquals(Integer.MIN_VALUE, Prioritized.MAX_PRIORITY);
    }
}
