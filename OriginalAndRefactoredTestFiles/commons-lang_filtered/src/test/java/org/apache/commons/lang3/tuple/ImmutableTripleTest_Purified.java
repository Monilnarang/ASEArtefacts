package org.apache.commons.lang3.tuple;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.TreeMap;
import org.apache.commons.lang3.AbstractLangTest;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.Test;

public class ImmutableTripleTest_Purified extends AbstractLangTest {

    @Test
    public void testBasic_1_testMerged_1() {
        final ImmutableTriple<Integer, String, Boolean> triple = new ImmutableTriple<>(0, "foo", Boolean.TRUE);
        assertEquals(0, triple.left.intValue());
        assertEquals(0, triple.getLeft().intValue());
        assertEquals("foo", triple.middle);
        assertEquals("foo", triple.getMiddle());
        assertEquals(Boolean.TRUE, triple.right);
        assertEquals(Boolean.TRUE, triple.getRight());
    }

    @Test
    public void testBasic_7_testMerged_2() {
        final ImmutableTriple<Object, String, Integer> triple2 = new ImmutableTriple<>(null, "bar", 42);
        assertNull(triple2.left);
        assertNull(triple2.getLeft());
        assertEquals("bar", triple2.middle);
        assertEquals("bar", triple2.getMiddle());
        assertEquals(Integer.valueOf(42), triple2.right);
        assertEquals(Integer.valueOf(42), triple2.getRight());
    }

    @Test
    public void testEquals_1() {
        assertEquals(ImmutableTriple.of(null, "foo", 42), ImmutableTriple.of(null, "foo", 42));
    }

    @Test
    public void testEquals_2() {
        assertNotEquals(ImmutableTriple.of("foo", 0, Boolean.TRUE), ImmutableTriple.of("foo", null, null));
    }

    @Test
    public void testEquals_3() {
        assertNotEquals(ImmutableTriple.of("foo", "bar", "baz"), ImmutableTriple.of("xyz", "bar", "blo"));
    }

    @Test
    public void testEquals_4_testMerged_4() {
        final ImmutableTriple<String, String, String> p = ImmutableTriple.of("foo", "bar", "baz");
        assertEquals(p, p);
        assertNotEquals(p, new Object());
    }

    @Test
    public void testToString_1() {
        assertEquals("(null,null,null)", ImmutableTriple.of(null, null, null).toString());
    }

    @Test
    public void testToString_2() {
        assertEquals("(null,two,null)", ImmutableTriple.of(null, "two", null).toString());
    }

    @Test
    public void testToString_3() {
        assertEquals("(one,null,null)", ImmutableTriple.of("one", null, null).toString());
    }

    @Test
    public void testToString_4() {
        assertEquals("(one,two,null)", ImmutableTriple.of("one", "two", null).toString());
    }

    @Test
    public void testToString_5() {
        assertEquals("(null,two,three)", ImmutableTriple.of(null, "two", "three").toString());
    }

    @Test
    public void testToString_6() {
        assertEquals("(one,null,three)", ImmutableTriple.of("one", null, "three").toString());
    }

    @Test
    public void testToString_7() {
        assertEquals("(one,two,three)", MutableTriple.of("one", "two", "three").toString());
    }
}
