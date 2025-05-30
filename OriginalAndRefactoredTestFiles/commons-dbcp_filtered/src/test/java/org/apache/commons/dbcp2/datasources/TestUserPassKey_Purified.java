package org.apache.commons.dbcp2.datasources;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import org.apache.commons.dbcp2.Utils;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestUserPassKey_Purified {

    private UserPassKey userPassKey;

    private UserPassKey anotherUserPassKey;

    @BeforeEach
    public void setUp() {
        userPassKey = new UserPassKey("user", "pass");
        anotherUserPassKey = new UserPassKey((String) null, "");
    }

    @Test
    public void testEquals_1() {
        assertEquals(new UserPassKey("user"), new UserPassKey("user", (char[]) null));
    }

    @Test
    public void testEquals_2() {
        assertEquals(userPassKey, userPassKey);
    }

    @Test
    public void testEquals_3() {
        assertNotEquals(userPassKey, null);
    }

    @Test
    public void testEquals_4() {
        assertNotEquals(userPassKey, new Object());
    }

    @Test
    public void testEquals_5() {
        assertNotEquals(new UserPassKey(null), userPassKey);
    }

    @Test
    public void testEquals_6() {
        assertEquals(new UserPassKey(null), new UserPassKey(null));
    }

    @Test
    public void testEquals_7() {
        assertNotEquals(new UserPassKey("user", "pass"), new UserPassKey("foo", "pass"));
    }

    @Test
    public void testGettersAndSetters_1() {
        assertEquals("user", userPassKey.getUserName());
    }

    @Test
    public void testGettersAndSetters_2() {
        assertEquals("pass", userPassKey.getPassword());
    }

    @Test
    public void testGettersAndSetters_3() {
        assertArrayEquals(Utils.toCharArray("pass"), userPassKey.getPasswordCharArray());
    }

    @Test
    public void testHashcode_1() {
        assertEquals(userPassKey.hashCode(), new UserPassKey("user", "pass").hashCode());
    }

    @Test
    public void testHashcode_2() {
        assertNotEquals(userPassKey.hashCode(), anotherUserPassKey.hashCode());
    }

    @Test
    public void testSerialization_1() {
        assertEquals(userPassKey, SerializationUtils.roundtrip(userPassKey));
    }

    @Test
    public void testSerialization_2() {
        assertEquals(anotherUserPassKey, SerializationUtils.roundtrip(anotherUserPassKey));
    }

    @Test
    public void testToString_1() {
        assertEquals(userPassKey.toString(), new UserPassKey("user", "pass").toString());
    }

    @Test
    public void testToString_2() {
        assertNotEquals(userPassKey.toString(), anotherUserPassKey.toString());
    }
}
