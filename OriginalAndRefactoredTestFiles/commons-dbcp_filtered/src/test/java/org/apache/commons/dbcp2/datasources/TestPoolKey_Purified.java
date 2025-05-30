package org.apache.commons.dbcp2.datasources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPoolKey_Purified {

    private PoolKey poolKey;

    private PoolKey anotherPoolKey;

    @BeforeEach
    public void setUp() {
        poolKey = new PoolKey("ds", "user");
        anotherPoolKey = new PoolKey(null, null);
    }

    @Test
    public void testEquals_1() {
        assertEquals(poolKey, poolKey);
    }

    @Test
    public void testEquals_2() {
        assertNotEquals(poolKey, null);
    }

    @Test
    public void testEquals_3() {
        assertNotEquals(poolKey, new Object());
    }

    @Test
    public void testEquals_4() {
        assertNotEquals(new PoolKey(null, "user"), poolKey);
    }

    @Test
    public void testEquals_5() {
        assertEquals(new PoolKey(null, "user"), new PoolKey(null, "user"));
    }

    @Test
    public void testEquals_6() {
        assertNotEquals(new PoolKey(null, "user"), new PoolKey(null, "foo"));
    }

    @Test
    public void testEquals_7() {
        assertNotEquals(new PoolKey("ds", null), new PoolKey("foo", null));
    }

    @Test
    public void testEquals_8() {
        assertNotEquals(new PoolKey("ds", null), poolKey);
    }

    @Test
    public void testEquals_9() {
        assertEquals(new PoolKey("ds", null), new PoolKey("ds", null));
    }

    @Test
    public void testHashcode_1() {
        assertEquals(poolKey.hashCode(), new PoolKey("ds", "user").hashCode());
    }

    @Test
    public void testHashcode_2() {
        assertNotEquals(poolKey.hashCode(), anotherPoolKey.hashCode());
    }

    @Test
    public void testToString_1() {
        assertEquals(poolKey.toString(), new PoolKey("ds", "user").toString());
    }

    @Test
    public void testToString_2() {
        assertNotEquals(poolKey.toString(), anotherPoolKey.toString());
    }
}
