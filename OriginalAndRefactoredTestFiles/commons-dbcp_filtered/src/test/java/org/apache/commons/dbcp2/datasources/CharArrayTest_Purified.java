package org.apache.commons.dbcp2.datasources;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.Test;

public class CharArrayTest_Purified {

    @Test
    public void testEquals_1() {
        assertEquals(new CharArray("foo"), new CharArray("foo"));
    }

    @Test
    public void testEquals_2() {
        assertNotEquals(new CharArray("foo"), new CharArray("bar"));
    }

    @Test
    public void testHashCode_1() {
        assertEquals(new CharArray("foo").hashCode(), new CharArray("foo").hashCode());
    }

    @Test
    public void testHashCode_2() {
        assertNotEquals(new CharArray("foo").hashCode(), new CharArray("bar").hashCode());
    }
}
