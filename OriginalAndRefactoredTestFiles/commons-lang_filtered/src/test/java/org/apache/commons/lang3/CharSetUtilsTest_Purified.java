package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.Test;

public class CharSetUtilsTest_Purified extends AbstractLangTest {

    @Test
    public void testConstructor_1() {
        assertNotNull(new CharSetUtils());
    }

    @Test
    public void testConstructor_2_testMerged_2() {
        final Constructor<?>[] cons = CharSetUtils.class.getDeclaredConstructors();
        assertEquals(1, cons.length);
        assertTrue(Modifier.isPublic(cons[0].getModifiers()));
    }

    @Test
    public void testConstructor_4() {
        assertTrue(Modifier.isPublic(CharSetUtils.class.getModifiers()));
    }

    @Test
    public void testConstructor_5() {
        assertFalse(Modifier.isFinal(CharSetUtils.class.getModifiers()));
    }

    @Test
    public void testContainsAny_StringString_1() {
        assertFalse(CharSetUtils.containsAny(null, (String) null));
    }

    @Test
    public void testContainsAny_StringString_2() {
        assertFalse(CharSetUtils.containsAny(null, ""));
    }

    @Test
    public void testContainsAny_StringString_3() {
        assertFalse(CharSetUtils.containsAny("", (String) null));
    }

    @Test
    public void testContainsAny_StringString_4() {
        assertFalse(CharSetUtils.containsAny("", ""));
    }

    @Test
    public void testContainsAny_StringString_5() {
        assertFalse(CharSetUtils.containsAny("", "a-e"));
    }

    @Test
    public void testContainsAny_StringString_6() {
        assertFalse(CharSetUtils.containsAny("hello", (String) null));
    }

    @Test
    public void testContainsAny_StringString_7() {
        assertFalse(CharSetUtils.containsAny("hello", ""));
    }

    @Test
    public void testContainsAny_StringString_8() {
        assertTrue(CharSetUtils.containsAny("hello", "a-e"));
    }

    @Test
    public void testContainsAny_StringString_9() {
        assertTrue(CharSetUtils.containsAny("hello", "l-p"));
    }

    @Test
    public void testContainsAny_StringStringarray_1() {
        assertFalse(CharSetUtils.containsAny(null, (String[]) null));
    }

    @Test
    public void testContainsAny_StringStringarray_2() {
        assertFalse(CharSetUtils.containsAny(null));
    }

    @Test
    public void testContainsAny_StringStringarray_3() {
        assertFalse(CharSetUtils.containsAny(null, (String) null));
    }

    @Test
    public void testContainsAny_StringStringarray_4() {
        assertFalse(CharSetUtils.containsAny(null, "a-e"));
    }

    @Test
    public void testContainsAny_StringStringarray_5() {
        assertFalse(CharSetUtils.containsAny("", (String[]) null));
    }

    @Test
    public void testContainsAny_StringStringarray_6() {
        assertFalse(CharSetUtils.containsAny(""));
    }

    @Test
    public void testContainsAny_StringStringarray_7() {
        assertFalse(CharSetUtils.containsAny("", (String) null));
    }

    @Test
    public void testContainsAny_StringStringarray_8() {
        assertFalse(CharSetUtils.containsAny("", "a-e"));
    }

    @Test
    public void testContainsAny_StringStringarray_9() {
        assertFalse(CharSetUtils.containsAny("hello", (String[]) null));
    }

    @Test
    public void testContainsAny_StringStringarray_10() {
        assertFalse(CharSetUtils.containsAny("hello"));
    }

    @Test
    public void testContainsAny_StringStringarray_11() {
        assertFalse(CharSetUtils.containsAny("hello", (String) null));
    }

    @Test
    public void testContainsAny_StringStringarray_12() {
        assertTrue(CharSetUtils.containsAny("hello", "a-e"));
    }

    @Test
    public void testContainsAny_StringStringarray_13() {
        assertTrue(CharSetUtils.containsAny("hello", "el"));
    }

    @Test
    public void testContainsAny_StringStringarray_14() {
        assertFalse(CharSetUtils.containsAny("hello", "x"));
    }

    @Test
    public void testContainsAny_StringStringarray_15() {
        assertTrue(CharSetUtils.containsAny("hello", "e-i"));
    }

    @Test
    public void testContainsAny_StringStringarray_16() {
        assertTrue(CharSetUtils.containsAny("hello", "a-z"));
    }

    @Test
    public void testContainsAny_StringStringarray_17() {
        assertFalse(CharSetUtils.containsAny("hello", ""));
    }

    @Test
    public void testCount_StringString_1() {
        assertEquals(0, CharSetUtils.count(null, (String) null));
    }

    @Test
    public void testCount_StringString_2() {
        assertEquals(0, CharSetUtils.count(null, ""));
    }

    @Test
    public void testCount_StringString_3() {
        assertEquals(0, CharSetUtils.count("", (String) null));
    }

    @Test
    public void testCount_StringString_4() {
        assertEquals(0, CharSetUtils.count("", ""));
    }

    @Test
    public void testCount_StringString_5() {
        assertEquals(0, CharSetUtils.count("", "a-e"));
    }

    @Test
    public void testCount_StringString_6() {
        assertEquals(0, CharSetUtils.count("hello", (String) null));
    }

    @Test
    public void testCount_StringString_7() {
        assertEquals(0, CharSetUtils.count("hello", ""));
    }

    @Test
    public void testCount_StringString_8() {
        assertEquals(1, CharSetUtils.count("hello", "a-e"));
    }

    @Test
    public void testCount_StringString_9() {
        assertEquals(3, CharSetUtils.count("hello", "l-p"));
    }

    @Test
    public void testCount_StringStringarray_1() {
        assertEquals(0, CharSetUtils.count(null, (String[]) null));
    }

    @Test
    public void testCount_StringStringarray_2() {
        assertEquals(0, CharSetUtils.count(null));
    }

    @Test
    public void testCount_StringStringarray_3() {
        assertEquals(0, CharSetUtils.count(null, (String) null));
    }

    @Test
    public void testCount_StringStringarray_4() {
        assertEquals(0, CharSetUtils.count(null, "a-e"));
    }

    @Test
    public void testCount_StringStringarray_5() {
        assertEquals(0, CharSetUtils.count("", (String[]) null));
    }

    @Test
    public void testCount_StringStringarray_6() {
        assertEquals(0, CharSetUtils.count(""));
    }

    @Test
    public void testCount_StringStringarray_7() {
        assertEquals(0, CharSetUtils.count("", (String) null));
    }

    @Test
    public void testCount_StringStringarray_8() {
        assertEquals(0, CharSetUtils.count("", "a-e"));
    }

    @Test
    public void testCount_StringStringarray_9() {
        assertEquals(0, CharSetUtils.count("hello", (String[]) null));
    }

    @Test
    public void testCount_StringStringarray_10() {
        assertEquals(0, CharSetUtils.count("hello"));
    }

    @Test
    public void testCount_StringStringarray_11() {
        assertEquals(0, CharSetUtils.count("hello", (String) null));
    }

    @Test
    public void testCount_StringStringarray_12() {
        assertEquals(1, CharSetUtils.count("hello", "a-e"));
    }

    @Test
    public void testCount_StringStringarray_13() {
        assertEquals(3, CharSetUtils.count("hello", "el"));
    }

    @Test
    public void testCount_StringStringarray_14() {
        assertEquals(0, CharSetUtils.count("hello", "x"));
    }

    @Test
    public void testCount_StringStringarray_15() {
        assertEquals(2, CharSetUtils.count("hello", "e-i"));
    }

    @Test
    public void testCount_StringStringarray_16() {
        assertEquals(5, CharSetUtils.count("hello", "a-z"));
    }

    @Test
    public void testCount_StringStringarray_17() {
        assertEquals(0, CharSetUtils.count("hello", ""));
    }

    @Test
    public void testDelete_StringString_1() {
        assertNull(CharSetUtils.delete(null, (String) null));
    }

    @Test
    public void testDelete_StringString_2() {
        assertNull(CharSetUtils.delete(null, ""));
    }

    @Test
    public void testDelete_StringString_3() {
        assertEquals("", CharSetUtils.delete("", (String) null));
    }

    @Test
    public void testDelete_StringString_4() {
        assertEquals("", CharSetUtils.delete("", ""));
    }

    @Test
    public void testDelete_StringString_5() {
        assertEquals("", CharSetUtils.delete("", "a-e"));
    }

    @Test
    public void testDelete_StringString_6() {
        assertEquals("hello", CharSetUtils.delete("hello", (String) null));
    }

    @Test
    public void testDelete_StringString_7() {
        assertEquals("hello", CharSetUtils.delete("hello", ""));
    }

    @Test
    public void testDelete_StringString_8() {
        assertEquals("hllo", CharSetUtils.delete("hello", "a-e"));
    }

    @Test
    public void testDelete_StringString_9() {
        assertEquals("he", CharSetUtils.delete("hello", "l-p"));
    }

    @Test
    public void testDelete_StringString_10() {
        assertEquals("hello", CharSetUtils.delete("hello", "z"));
    }

    @Test
    public void testDelete_StringStringarray_1() {
        assertNull(CharSetUtils.delete(null, (String[]) null));
    }

    @Test
    public void testDelete_StringStringarray_2() {
        assertNull(CharSetUtils.delete(null));
    }

    @Test
    public void testDelete_StringStringarray_3() {
        assertNull(CharSetUtils.delete(null, (String) null));
    }

    @Test
    public void testDelete_StringStringarray_4() {
        assertNull(CharSetUtils.delete(null, "el"));
    }

    @Test
    public void testDelete_StringStringarray_5() {
        assertEquals("", CharSetUtils.delete("", (String[]) null));
    }

    @Test
    public void testDelete_StringStringarray_6() {
        assertEquals("", CharSetUtils.delete(""));
    }

    @Test
    public void testDelete_StringStringarray_7() {
        assertEquals("", CharSetUtils.delete("", (String) null));
    }

    @Test
    public void testDelete_StringStringarray_8() {
        assertEquals("", CharSetUtils.delete("", "a-e"));
    }

    @Test
    public void testDelete_StringStringarray_9() {
        assertEquals("hello", CharSetUtils.delete("hello", (String[]) null));
    }

    @Test
    public void testDelete_StringStringarray_10() {
        assertEquals("hello", CharSetUtils.delete("hello"));
    }

    @Test
    public void testDelete_StringStringarray_11() {
        assertEquals("hello", CharSetUtils.delete("hello", (String) null));
    }

    @Test
    public void testDelete_StringStringarray_12() {
        assertEquals("hello", CharSetUtils.delete("hello", "xyz"));
    }

    @Test
    public void testDelete_StringStringarray_13() {
        assertEquals("ho", CharSetUtils.delete("hello", "el"));
    }

    @Test
    public void testDelete_StringStringarray_14() {
        assertEquals("", CharSetUtils.delete("hello", "elho"));
    }

    @Test
    public void testDelete_StringStringarray_15() {
        assertEquals("hello", CharSetUtils.delete("hello", ""));
    }

    @Test
    public void testDelete_StringStringarray_16() {
        assertEquals("hello", CharSetUtils.delete("hello", ""));
    }

    @Test
    public void testDelete_StringStringarray_17() {
        assertEquals("", CharSetUtils.delete("hello", "a-z"));
    }

    @Test
    public void testDelete_StringStringarray_18() {
        assertEquals("", CharSetUtils.delete("----", "-"));
    }

    @Test
    public void testDelete_StringStringarray_19() {
        assertEquals("heo", CharSetUtils.delete("hello", "l"));
    }

    @Test
    public void testKeep_StringString_1() {
        assertNull(CharSetUtils.keep(null, (String) null));
    }

    @Test
    public void testKeep_StringString_2() {
        assertNull(CharSetUtils.keep(null, ""));
    }

    @Test
    public void testKeep_StringString_3() {
        assertEquals("", CharSetUtils.keep("", (String) null));
    }

    @Test
    public void testKeep_StringString_4() {
        assertEquals("", CharSetUtils.keep("", ""));
    }

    @Test
    public void testKeep_StringString_5() {
        assertEquals("", CharSetUtils.keep("", "a-e"));
    }

    @Test
    public void testKeep_StringString_6() {
        assertEquals("", CharSetUtils.keep("hello", (String) null));
    }

    @Test
    public void testKeep_StringString_7() {
        assertEquals("", CharSetUtils.keep("hello", ""));
    }

    @Test
    public void testKeep_StringString_8() {
        assertEquals("", CharSetUtils.keep("hello", "xyz"));
    }

    @Test
    public void testKeep_StringString_9() {
        assertEquals("hello", CharSetUtils.keep("hello", "a-z"));
    }

    @Test
    public void testKeep_StringString_10() {
        assertEquals("hello", CharSetUtils.keep("hello", "oleh"));
    }

    @Test
    public void testKeep_StringString_11() {
        assertEquals("ell", CharSetUtils.keep("hello", "el"));
    }

    @Test
    public void testKeep_StringStringarray_1() {
        assertNull(CharSetUtils.keep(null, (String[]) null));
    }

    @Test
    public void testKeep_StringStringarray_2() {
        assertNull(CharSetUtils.keep(null));
    }

    @Test
    public void testKeep_StringStringarray_3() {
        assertNull(CharSetUtils.keep(null, (String) null));
    }

    @Test
    public void testKeep_StringStringarray_4() {
        assertNull(CharSetUtils.keep(null, "a-e"));
    }

    @Test
    public void testKeep_StringStringarray_5() {
        assertEquals("", CharSetUtils.keep("", (String[]) null));
    }

    @Test
    public void testKeep_StringStringarray_6() {
        assertEquals("", CharSetUtils.keep(""));
    }

    @Test
    public void testKeep_StringStringarray_7() {
        assertEquals("", CharSetUtils.keep("", (String) null));
    }

    @Test
    public void testKeep_StringStringarray_8() {
        assertEquals("", CharSetUtils.keep("", "a-e"));
    }

    @Test
    public void testKeep_StringStringarray_9() {
        assertEquals("", CharSetUtils.keep("hello", (String[]) null));
    }

    @Test
    public void testKeep_StringStringarray_10() {
        assertEquals("", CharSetUtils.keep("hello"));
    }

    @Test
    public void testKeep_StringStringarray_11() {
        assertEquals("", CharSetUtils.keep("hello", (String) null));
    }

    @Test
    public void testKeep_StringStringarray_12() {
        assertEquals("e", CharSetUtils.keep("hello", "a-e"));
    }

    @Test
    public void testKeep_StringStringarray_13() {
        assertEquals("e", CharSetUtils.keep("hello", "a-e"));
    }

    @Test
    public void testKeep_StringStringarray_14() {
        assertEquals("ell", CharSetUtils.keep("hello", "el"));
    }

    @Test
    public void testKeep_StringStringarray_15() {
        assertEquals("hello", CharSetUtils.keep("hello", "elho"));
    }

    @Test
    public void testKeep_StringStringarray_16() {
        assertEquals("hello", CharSetUtils.keep("hello", "a-z"));
    }

    @Test
    public void testKeep_StringStringarray_17() {
        assertEquals("----", CharSetUtils.keep("----", "-"));
    }

    @Test
    public void testKeep_StringStringarray_18() {
        assertEquals("ll", CharSetUtils.keep("hello", "l"));
    }

    @Test
    public void testSqueeze_StringString_1() {
        assertNull(CharSetUtils.squeeze(null, (String) null));
    }

    @Test
    public void testSqueeze_StringString_2() {
        assertNull(CharSetUtils.squeeze(null, ""));
    }

    @Test
    public void testSqueeze_StringString_3() {
        assertEquals("", CharSetUtils.squeeze("", (String) null));
    }

    @Test
    public void testSqueeze_StringString_4() {
        assertEquals("", CharSetUtils.squeeze("", ""));
    }

    @Test
    public void testSqueeze_StringString_5() {
        assertEquals("", CharSetUtils.squeeze("", "a-e"));
    }

    @Test
    public void testSqueeze_StringString_6() {
        assertEquals("hello", CharSetUtils.squeeze("hello", (String) null));
    }

    @Test
    public void testSqueeze_StringString_7() {
        assertEquals("hello", CharSetUtils.squeeze("hello", ""));
    }

    @Test
    public void testSqueeze_StringString_8() {
        assertEquals("hello", CharSetUtils.squeeze("hello", "a-e"));
    }

    @Test
    public void testSqueeze_StringString_9() {
        assertEquals("helo", CharSetUtils.squeeze("hello", "l-p"));
    }

    @Test
    public void testSqueeze_StringString_10() {
        assertEquals("heloo", CharSetUtils.squeeze("helloo", "l"));
    }

    @Test
    public void testSqueeze_StringString_11() {
        assertEquals("hello", CharSetUtils.squeeze("helloo", "^l"));
    }

    @Test
    public void testSqueeze_StringStringarray_1() {
        assertNull(CharSetUtils.squeeze(null, (String[]) null));
    }

    @Test
    public void testSqueeze_StringStringarray_2() {
        assertNull(CharSetUtils.squeeze(null));
    }

    @Test
    public void testSqueeze_StringStringarray_3() {
        assertNull(CharSetUtils.squeeze(null, (String) null));
    }

    @Test
    public void testSqueeze_StringStringarray_4() {
        assertNull(CharSetUtils.squeeze(null, "el"));
    }

    @Test
    public void testSqueeze_StringStringarray_5() {
        assertEquals("", CharSetUtils.squeeze("", (String[]) null));
    }

    @Test
    public void testSqueeze_StringStringarray_6() {
        assertEquals("", CharSetUtils.squeeze(""));
    }

    @Test
    public void testSqueeze_StringStringarray_7() {
        assertEquals("", CharSetUtils.squeeze("", (String) null));
    }

    @Test
    public void testSqueeze_StringStringarray_8() {
        assertEquals("", CharSetUtils.squeeze("", "a-e"));
    }

    @Test
    public void testSqueeze_StringStringarray_9() {
        assertEquals("hello", CharSetUtils.squeeze("hello", (String[]) null));
    }

    @Test
    public void testSqueeze_StringStringarray_10() {
        assertEquals("hello", CharSetUtils.squeeze("hello"));
    }

    @Test
    public void testSqueeze_StringStringarray_11() {
        assertEquals("hello", CharSetUtils.squeeze("hello", (String) null));
    }

    @Test
    public void testSqueeze_StringStringarray_12() {
        assertEquals("hello", CharSetUtils.squeeze("hello", "a-e"));
    }

    @Test
    public void testSqueeze_StringStringarray_13() {
        assertEquals("helo", CharSetUtils.squeeze("hello", "el"));
    }

    @Test
    public void testSqueeze_StringStringarray_14() {
        assertEquals("hello", CharSetUtils.squeeze("hello", "e"));
    }

    @Test
    public void testSqueeze_StringStringarray_15() {
        assertEquals("fofof", CharSetUtils.squeeze("fooffooff", "of"));
    }

    @Test
    public void testSqueeze_StringStringarray_16() {
        assertEquals("fof", CharSetUtils.squeeze("fooooff", "fo"));
    }
}
