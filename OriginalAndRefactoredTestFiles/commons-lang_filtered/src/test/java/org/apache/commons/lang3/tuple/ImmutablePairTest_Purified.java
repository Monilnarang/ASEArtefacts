package org.apache.commons.lang3.tuple;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;
import org.apache.commons.lang3.AbstractLangTest;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.Test;

public class ImmutablePairTest_Purified extends AbstractLangTest {

    @Test
    public void testEquals_1() {
        assertEquals(ImmutablePair.of(null, "foo"), ImmutablePair.of(null, "foo"));
    }

    @Test
    public void testEquals_2() {
        assertNotEquals(ImmutablePair.of("foo", 0), ImmutablePair.of("foo", null));
    }

    @Test
    public void testEquals_3() {
        assertNotEquals(ImmutablePair.of("foo", "bar"), ImmutablePair.of("xyz", "bar"));
    }

    @Test
    public void testEquals_4_testMerged_4() {
        final ImmutablePair<String, String> p = ImmutablePair.of("foo", "bar");
        assertEquals(p, p);
        assertNotEquals(p, new Object());
    }

    @Test
    public void testToString_1() {
        assertEquals("(null,null)", ImmutablePair.of(null, null).toString());
    }

    @Test
    public void testToString_2() {
        assertEquals("(null,two)", ImmutablePair.of(null, "two").toString());
    }

    @Test
    public void testToString_3() {
        assertEquals("(one,null)", ImmutablePair.of("one", null).toString());
    }

    @Test
    public void testToString_4() {
        assertEquals("(one,two)", ImmutablePair.of("one", "two").toString());
    }
}
