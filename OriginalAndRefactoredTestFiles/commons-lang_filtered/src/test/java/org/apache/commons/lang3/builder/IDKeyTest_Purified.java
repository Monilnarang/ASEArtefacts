package org.apache.commons.lang3.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

public class IDKeyTest_Purified {

    @Test
    public void testEquals_1() {
        assertEquals(new IDKey("1"), new IDKey("1"));
    }

    @Test
    public void testEquals_2() {
        assertNotEquals(new IDKey("1"), new IDKey("2"));
    }

    @Test
    public void testEquals_3() {
        assertNotEquals(new IDKey("1"), "2");
    }
}
