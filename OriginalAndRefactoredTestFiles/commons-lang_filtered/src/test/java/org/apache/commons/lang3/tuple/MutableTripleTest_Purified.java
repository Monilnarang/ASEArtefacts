package org.apache.commons.lang3.tuple;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.commons.lang3.AbstractLangTest;
import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.Test;

public class MutableTripleTest_Purified extends AbstractLangTest {

    @Test
    public void testBasic_1_testMerged_1() {
        final MutableTriple<Integer, String, Boolean> triple = new MutableTriple<>(0, "foo", Boolean.FALSE);
        assertEquals(0, triple.getLeft().intValue());
        assertEquals("foo", triple.getMiddle());
        assertEquals(Boolean.FALSE, triple.getRight());
    }

    @Test
    public void testBasic_4_testMerged_2() {
        final MutableTriple<Object, String, String> triple2 = new MutableTriple<>(null, "bar", "hello");
        assertNull(triple2.getLeft());
        assertEquals("bar", triple2.getMiddle());
        assertEquals("hello", triple2.getRight());
    }

    @Test
    public void testEquals_1() {
        assertEquals(MutableTriple.of(null, "foo", "baz"), MutableTriple.of(null, "foo", "baz"));
    }

    @Test
    public void testEquals_2() {
        assertNotEquals(MutableTriple.of("foo", 0, Boolean.TRUE), MutableTriple.of("foo", null, Boolean.TRUE));
    }

    @Test
    public void testEquals_3() {
        assertNotEquals(MutableTriple.of("foo", "bar", "baz"), MutableTriple.of("xyz", "bar", "baz"));
    }

    @Test
    public void testEquals_4() {
        assertNotEquals(MutableTriple.of("foo", "bar", "baz"), MutableTriple.of("foo", "bar", "blo"));
    }

    @Test
    public void testEquals_5_testMerged_5() {
        final MutableTriple<String, String, String> p = MutableTriple.of("foo", "bar", "baz");
        assertEquals(p, p);
        assertNotEquals(p, new Object());
    }

    @Test
    public void testToString_1() {
        assertEquals("(null,null,null)", MutableTriple.of(null, null, null).toString());
    }

    @Test
    public void testToString_2() {
        assertEquals("(null,two,null)", MutableTriple.of(null, "two", null).toString());
    }

    @Test
    public void testToString_3() {
        assertEquals("(one,null,null)", MutableTriple.of("one", null, null).toString());
    }

    @Test
    public void testToString_4() {
        assertEquals("(one,two,null)", MutableTriple.of("one", "two", null).toString());
    }

    @Test
    public void testToString_5() {
        assertEquals("(null,two,three)", MutableTriple.of(null, "two", "three").toString());
    }

    @Test
    public void testToString_6() {
        assertEquals("(one,null,three)", MutableTriple.of("one", null, "three").toString());
    }

    @Test
    public void testToString_7() {
        assertEquals("(one,two,three)", MutableTriple.of("one", "two", "three").toString());
    }
}
