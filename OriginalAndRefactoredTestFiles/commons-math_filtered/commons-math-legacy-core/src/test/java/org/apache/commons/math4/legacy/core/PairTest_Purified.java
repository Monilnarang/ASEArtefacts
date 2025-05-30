package org.apache.commons.math4.legacy.core;

import org.junit.Assert;
import org.junit.Test;

public class PairTest_Purified {

    private static class MyInteger {

        private int i;

        MyInteger(int i) {
            this.i = i;
        }

        public void set(int value) {
            this.i = value;
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof MyInteger)) {
                return false;
            } else {
                return i == ((MyInteger) o).i;
            }
        }

        @Override
        public int hashCode() {
            return i;
        }
    }

    @Test
    public void testToString_1() {
        Assert.assertEquals("[null, null]", new Pair<>(null, null).toString());
    }

    @Test
    public void testToString_2() {
        Assert.assertEquals("[foo, 3]", new Pair<>("foo", 3).toString());
    }
}
