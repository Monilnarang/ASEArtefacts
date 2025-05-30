package org.apache.commons.lang3.tuple;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.HashMap;
import java.util.Map.Entry;
import org.apache.commons.lang3.AbstractLangTest;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.Test;

public class MutablePairTest_Purified extends AbstractLangTest {

    @Test
    public void testEquals_1() {
        assertEquals(MutablePair.of(null, "foo"), MutablePair.of(null, "foo"));
    }

    @Test
    public void testEquals_2() {
        assertNotEquals(MutablePair.of("foo", 0), MutablePair.of("foo", null));
    }

    @Test
    public void testEquals_3() {
        assertNotEquals(MutablePair.of("foo", "bar"), MutablePair.of("xyz", "bar"));
    }

    @Test
    public void testEquals_4_testMerged_4() {
        final MutablePair<String, String> p = MutablePair.of("foo", "bar");
        assertEquals(p, p);
        assertNotEquals(p, new Object());
    }

    @Test
    public void testToString_1() {
        assertEquals("(null,null)", MutablePair.of(null, null).toString());
    }

    @Test
    public void testToString_2() {
        assertEquals("(null,two)", MutablePair.of(null, "two").toString());
    }

    @Test
    public void testToString_3() {
        assertEquals("(one,null)", MutablePair.of("one", null).toString());
    }

    @Test
    public void testToString_4() {
        assertEquals("(one,two)", MutablePair.of("one", "two").toString());
    }
}
