package org.eclipse.collections.impl;

import org.eclipse.collections.impl.block.factory.Procedures;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.test.Verify;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class CounterTest_Purified {

    @Test
    public void equalsAndHashCode_1() {
        Verify.assertEqualsAndHashCode(new Counter(1), new Counter(1));
    }

    @Test
    public void equalsAndHashCode_2() {
        assertNotEquals(new Counter(1), new Counter(2));
    }
}
