package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class IOCaseTest_Purified {

    private static final boolean WINDOWS = File.separatorChar == '\\';

    private void assert0(final byte[] arr) {
        for (final byte e : arr) {
            assertEquals(0, e);
        }
    }

    private void assert0(final char[] arr) {
        for (final char e : arr) {
            assertEquals(0, e);
        }
    }

    private IOCase serialize(final IOCase value) throws Exception {
        final ByteArrayOutputStream buf = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(buf)) {
            out.writeObject(value);
            out.flush();
        }
        final ByteArrayInputStream bufin = new ByteArrayInputStream(buf.toByteArray());
        final ObjectInputStream in = new ObjectInputStream(bufin);
        return (IOCase) in.readObject();
    }

    @Test
    public void test_checkCompare_case_1() {
        assertEquals(0, IOCase.SENSITIVE.checkCompareTo("ABC", "ABC"));
    }

    @Test
    public void test_checkCompare_case_2() {
        assertTrue(IOCase.SENSITIVE.checkCompareTo("ABC", "abc") < 0);
    }

    @Test
    public void test_checkCompare_case_3() {
        assertTrue(IOCase.SENSITIVE.checkCompareTo("abc", "ABC") > 0);
    }

    @Test
    public void test_checkCompare_case_4() {
        assertEquals(0, IOCase.INSENSITIVE.checkCompareTo("ABC", "ABC"));
    }

    @Test
    public void test_checkCompare_case_5() {
        assertEquals(0, IOCase.INSENSITIVE.checkCompareTo("ABC", "abc"));
    }

    @Test
    public void test_checkCompare_case_6() {
        assertEquals(0, IOCase.INSENSITIVE.checkCompareTo("abc", "ABC"));
    }

    @Test
    public void test_checkCompare_case_7() {
        assertEquals(0, IOCase.SYSTEM.checkCompareTo("ABC", "ABC"));
    }

    @Test
    public void test_checkCompare_case_8() {
        assertEquals(WINDOWS, IOCase.SYSTEM.checkCompareTo("ABC", "abc") == 0);
    }

    @Test
    public void test_checkCompare_case_9() {
        assertEquals(WINDOWS, IOCase.SYSTEM.checkCompareTo("abc", "ABC") == 0);
    }

    @Test
    public void test_checkEndsWith_case_1() {
        assertTrue(IOCase.SENSITIVE.checkEndsWith("ABC", "BC"));
    }

    @Test
    public void test_checkEndsWith_case_2() {
        assertFalse(IOCase.SENSITIVE.checkEndsWith("ABC", "Bc"));
    }

    @Test
    public void test_checkEndsWith_case_3() {
        assertTrue(IOCase.INSENSITIVE.checkEndsWith("ABC", "BC"));
    }

    @Test
    public void test_checkEndsWith_case_4() {
        assertTrue(IOCase.INSENSITIVE.checkEndsWith("ABC", "Bc"));
    }

    @Test
    public void test_checkEndsWith_case_5() {
        assertTrue(IOCase.SYSTEM.checkEndsWith("ABC", "BC"));
    }

    @Test
    public void test_checkEndsWith_case_6() {
        assertEquals(WINDOWS, IOCase.SYSTEM.checkEndsWith("ABC", "Bc"));
    }

    @Test
    public void test_checkEndsWith_functionality_1() {
        assertTrue(IOCase.SENSITIVE.checkEndsWith("ABC", ""));
    }

    @Test
    public void test_checkEndsWith_functionality_2() {
        assertFalse(IOCase.SENSITIVE.checkEndsWith("ABC", "A"));
    }

    @Test
    public void test_checkEndsWith_functionality_3() {
        assertFalse(IOCase.SENSITIVE.checkEndsWith("ABC", "AB"));
    }

    @Test
    public void test_checkEndsWith_functionality_4() {
        assertTrue(IOCase.SENSITIVE.checkEndsWith("ABC", "ABC"));
    }

    @Test
    public void test_checkEndsWith_functionality_5() {
        assertTrue(IOCase.SENSITIVE.checkEndsWith("ABC", "BC"));
    }

    @Test
    public void test_checkEndsWith_functionality_6() {
        assertTrue(IOCase.SENSITIVE.checkEndsWith("ABC", "C"));
    }

    @Test
    public void test_checkEndsWith_functionality_7() {
        assertFalse(IOCase.SENSITIVE.checkEndsWith("ABC", "ABCD"));
    }

    @Test
    public void test_checkEndsWith_functionality_8() {
        assertFalse(IOCase.SENSITIVE.checkEndsWith("", "ABC"));
    }

    @Test
    public void test_checkEndsWith_functionality_9() {
        assertTrue(IOCase.SENSITIVE.checkEndsWith("", ""));
    }

    @Test
    public void test_checkEndsWith_functionality_10() {
        assertFalse(IOCase.SENSITIVE.checkEndsWith("ABC", null));
    }

    @Test
    public void test_checkEndsWith_functionality_11() {
        assertFalse(IOCase.SENSITIVE.checkEndsWith(null, "ABC"));
    }

    @Test
    public void test_checkEndsWith_functionality_12() {
        assertFalse(IOCase.SENSITIVE.checkEndsWith(null, null));
    }

    @Test
    public void test_checkEquals_case_1() {
        assertTrue(IOCase.SENSITIVE.checkEquals("ABC", "ABC"));
    }

    @Test
    public void test_checkEquals_case_2() {
        assertFalse(IOCase.SENSITIVE.checkEquals("ABC", "Abc"));
    }

    @Test
    public void test_checkEquals_case_3() {
        assertTrue(IOCase.INSENSITIVE.checkEquals("ABC", "ABC"));
    }

    @Test
    public void test_checkEquals_case_4() {
        assertTrue(IOCase.INSENSITIVE.checkEquals("ABC", "Abc"));
    }

    @Test
    public void test_checkEquals_case_5() {
        assertTrue(IOCase.SYSTEM.checkEquals("ABC", "ABC"));
    }

    @Test
    public void test_checkEquals_case_6() {
        assertEquals(WINDOWS, IOCase.SYSTEM.checkEquals("ABC", "Abc"));
    }

    @Test
    public void test_checkEquals_functionality_1() {
        assertFalse(IOCase.SENSITIVE.checkEquals("ABC", ""));
    }

    @Test
    public void test_checkEquals_functionality_2() {
        assertFalse(IOCase.SENSITIVE.checkEquals("ABC", "A"));
    }

    @Test
    public void test_checkEquals_functionality_3() {
        assertFalse(IOCase.SENSITIVE.checkEquals("ABC", "AB"));
    }

    @Test
    public void test_checkEquals_functionality_4() {
        assertTrue(IOCase.SENSITIVE.checkEquals("ABC", "ABC"));
    }

    @Test
    public void test_checkEquals_functionality_5() {
        assertFalse(IOCase.SENSITIVE.checkEquals("ABC", "BC"));
    }

    @Test
    public void test_checkEquals_functionality_6() {
        assertFalse(IOCase.SENSITIVE.checkEquals("ABC", "C"));
    }

    @Test
    public void test_checkEquals_functionality_7() {
        assertFalse(IOCase.SENSITIVE.checkEquals("ABC", "ABCD"));
    }

    @Test
    public void test_checkEquals_functionality_8() {
        assertFalse(IOCase.SENSITIVE.checkEquals("", "ABC"));
    }

    @Test
    public void test_checkEquals_functionality_9() {
        assertTrue(IOCase.SENSITIVE.checkEquals("", ""));
    }

    @Test
    public void test_checkEquals_functionality_10() {
        assertFalse(IOCase.SENSITIVE.checkEquals("ABC", null));
    }

    @Test
    public void test_checkEquals_functionality_11() {
        assertFalse(IOCase.SENSITIVE.checkEquals(null, "ABC"));
    }

    @Test
    public void test_checkEquals_functionality_12() {
        assertTrue(IOCase.SENSITIVE.checkEquals(null, null));
    }

    @Test
    public void test_checkIndexOf_case_1() {
        assertEquals(1, IOCase.SENSITIVE.checkIndexOf("ABC", 0, "BC"));
    }

    @Test
    public void test_checkIndexOf_case_2() {
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf("ABC", 0, "Bc"));
    }

    @Test
    public void test_checkIndexOf_case_3() {
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(null, 0, "Bc"));
    }

    @Test
    public void test_checkIndexOf_case_4() {
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(null, 0, null));
    }

    @Test
    public void test_checkIndexOf_case_5() {
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf("ABC", 0, null));
    }

    @Test
    public void test_checkIndexOf_case_6() {
        assertEquals(1, IOCase.INSENSITIVE.checkIndexOf("ABC", 0, "BC"));
    }

    @Test
    public void test_checkIndexOf_case_7() {
        assertEquals(1, IOCase.INSENSITIVE.checkIndexOf("ABC", 0, "Bc"));
    }

    @Test
    public void test_checkIndexOf_case_8() {
        assertEquals(1, IOCase.SYSTEM.checkIndexOf("ABC", 0, "BC"));
    }

    @Test
    public void test_checkIndexOf_case_9() {
        assertEquals(WINDOWS ? 1 : -1, IOCase.SYSTEM.checkIndexOf("ABC", 0, "Bc"));
    }

    @Test
    public void test_checkIndexOf_functionality_1() {
        assertEquals(0, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 0, "A"));
    }

    @Test
    public void test_checkIndexOf_functionality_2() {
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 1, "A"));
    }

    @Test
    public void test_checkIndexOf_functionality_3() {
        assertEquals(0, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 0, "AB"));
    }

    @Test
    public void test_checkIndexOf_functionality_4() {
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 1, "AB"));
    }

    @Test
    public void test_checkIndexOf_functionality_5() {
        assertEquals(0, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 0, "ABC"));
    }

    @Test
    public void test_checkIndexOf_functionality_6() {
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 1, "ABC"));
    }

    @Test
    public void test_checkIndexOf_functionality_7() {
        assertEquals(3, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 0, "D"));
    }

    @Test
    public void test_checkIndexOf_functionality_8() {
        assertEquals(3, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 3, "D"));
    }

    @Test
    public void test_checkIndexOf_functionality_9() {
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 4, "D"));
    }

    @Test
    public void test_checkIndexOf_functionality_10() {
        assertEquals(3, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 0, "DE"));
    }

    @Test
    public void test_checkIndexOf_functionality_11() {
        assertEquals(3, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 3, "DE"));
    }

    @Test
    public void test_checkIndexOf_functionality_12() {
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 4, "DE"));
    }

    @Test
    public void test_checkIndexOf_functionality_13() {
        assertEquals(3, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 0, "DEF"));
    }

    @Test
    public void test_checkIndexOf_functionality_14() {
        assertEquals(3, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 3, "DEF"));
    }

    @Test
    public void test_checkIndexOf_functionality_15() {
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 4, "DEF"));
    }

    @Test
    public void test_checkIndexOf_functionality_16() {
        assertEquals(9, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 0, "J"));
    }

    @Test
    public void test_checkIndexOf_functionality_17() {
        assertEquals(9, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 8, "J"));
    }

    @Test
    public void test_checkIndexOf_functionality_18() {
        assertEquals(9, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 9, "J"));
    }

    @Test
    public void test_checkIndexOf_functionality_19() {
        assertEquals(8, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 0, "IJ"));
    }

    @Test
    public void test_checkIndexOf_functionality_20() {
        assertEquals(8, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 8, "IJ"));
    }

    @Test
    public void test_checkIndexOf_functionality_21() {
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 9, "IJ"));
    }

    @Test
    public void test_checkIndexOf_functionality_22() {
        assertEquals(7, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 6, "HIJ"));
    }

    @Test
    public void test_checkIndexOf_functionality_23() {
        assertEquals(7, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 7, "HIJ"));
    }

    @Test
    public void test_checkIndexOf_functionality_24() {
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 8, "HIJ"));
    }

    @Test
    public void test_checkIndexOf_functionality_25() {
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf("ABCDEFGHIJ", 0, "DED"));
    }

    @Test
    public void test_checkIndexOf_functionality_26() {
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf("DEF", 0, "ABCDEFGHIJ"));
    }

    @Test
    public void test_checkIndexOf_functionality_27() {
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf("ABC", 0, null));
    }

    @Test
    public void test_checkIndexOf_functionality_28() {
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(null, 0, "ABC"));
    }

    @Test
    public void test_checkIndexOf_functionality_29() {
        assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(null, 0, null));
    }

    @Test
    public void test_checkRegionMatches_case_1() {
        assertTrue(IOCase.SENSITIVE.checkRegionMatches("ABC", 0, "AB"));
    }

    @Test
    public void test_checkRegionMatches_case_2() {
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 0, "Ab"));
    }

    @Test
    public void test_checkRegionMatches_case_3() {
        assertTrue(IOCase.INSENSITIVE.checkRegionMatches("ABC", 0, "AB"));
    }

    @Test
    public void test_checkRegionMatches_case_4() {
        assertTrue(IOCase.INSENSITIVE.checkRegionMatches("ABC", 0, "Ab"));
    }

    @Test
    public void test_checkRegionMatches_case_5() {
        assertTrue(IOCase.SYSTEM.checkRegionMatches("ABC", 0, "AB"));
    }

    @Test
    public void test_checkRegionMatches_case_6() {
        assertEquals(WINDOWS, IOCase.SYSTEM.checkRegionMatches("ABC", 0, "Ab"));
    }

    @Test
    public void test_checkRegionMatches_functionality_1() {
        assertTrue(IOCase.SENSITIVE.checkRegionMatches("ABC", 0, ""));
    }

    @Test
    public void test_checkRegionMatches_functionality_2() {
        assertTrue(IOCase.SENSITIVE.checkRegionMatches("ABC", 0, "A"));
    }

    @Test
    public void test_checkRegionMatches_functionality_3() {
        assertTrue(IOCase.SENSITIVE.checkRegionMatches("ABC", 0, "AB"));
    }

    @Test
    public void test_checkRegionMatches_functionality_4() {
        assertTrue(IOCase.SENSITIVE.checkRegionMatches("ABC", 0, "ABC"));
    }

    @Test
    public void test_checkRegionMatches_functionality_5() {
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 0, "BC"));
    }

    @Test
    public void test_checkRegionMatches_functionality_6() {
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 0, "C"));
    }

    @Test
    public void test_checkRegionMatches_functionality_7() {
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 0, "ABCD"));
    }

    @Test
    public void test_checkRegionMatches_functionality_8() {
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("", 0, "ABC"));
    }

    @Test
    public void test_checkRegionMatches_functionality_9() {
        assertTrue(IOCase.SENSITIVE.checkRegionMatches("", 0, ""));
    }

    @Test
    public void test_checkRegionMatches_functionality_10() {
        assertTrue(IOCase.SENSITIVE.checkRegionMatches("ABC", 1, ""));
    }

    @Test
    public void test_checkRegionMatches_functionality_11() {
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 1, "A"));
    }

    @Test
    public void test_checkRegionMatches_functionality_12() {
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 1, "AB"));
    }

    @Test
    public void test_checkRegionMatches_functionality_13() {
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 1, "ABC"));
    }

    @Test
    public void test_checkRegionMatches_functionality_14() {
        assertTrue(IOCase.SENSITIVE.checkRegionMatches("ABC", 1, "BC"));
    }

    @Test
    public void test_checkRegionMatches_functionality_15() {
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 1, "C"));
    }

    @Test
    public void test_checkRegionMatches_functionality_16() {
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 1, "ABCD"));
    }

    @Test
    public void test_checkRegionMatches_functionality_17() {
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("", 1, "ABC"));
    }

    @Test
    public void test_checkRegionMatches_functionality_18() {
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("", 1, ""));
    }

    @Test
    public void test_checkRegionMatches_functionality_19() {
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 0, null));
    }

    @Test
    public void test_checkRegionMatches_functionality_20() {
        assertFalse(IOCase.SENSITIVE.checkRegionMatches(null, 0, "ABC"));
    }

    @Test
    public void test_checkRegionMatches_functionality_21() {
        assertFalse(IOCase.SENSITIVE.checkRegionMatches(null, 0, null));
    }

    @Test
    public void test_checkRegionMatches_functionality_22() {
        assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 1, null));
    }

    @Test
    public void test_checkRegionMatches_functionality_23() {
        assertFalse(IOCase.SENSITIVE.checkRegionMatches(null, 1, "ABC"));
    }

    @Test
    public void test_checkRegionMatches_functionality_24() {
        assertFalse(IOCase.SENSITIVE.checkRegionMatches(null, 1, null));
    }

    @Test
    public void test_checkStartsWith_case_1() {
        assertTrue(IOCase.SENSITIVE.checkStartsWith("ABC", "AB"));
    }

    @Test
    public void test_checkStartsWith_case_2() {
        assertFalse(IOCase.SENSITIVE.checkStartsWith("ABC", "Ab"));
    }

    @Test
    public void test_checkStartsWith_case_3() {
        assertTrue(IOCase.INSENSITIVE.checkStartsWith("ABC", "AB"));
    }

    @Test
    public void test_checkStartsWith_case_4() {
        assertTrue(IOCase.INSENSITIVE.checkStartsWith("ABC", "Ab"));
    }

    @Test
    public void test_checkStartsWith_case_5() {
        assertTrue(IOCase.SYSTEM.checkStartsWith("ABC", "AB"));
    }

    @Test
    public void test_checkStartsWith_case_6() {
        assertEquals(WINDOWS, IOCase.SYSTEM.checkStartsWith("ABC", "Ab"));
    }

    @Test
    public void test_checkStartsWith_functionality_1() {
        assertTrue(IOCase.SENSITIVE.checkStartsWith("ABC", ""));
    }

    @Test
    public void test_checkStartsWith_functionality_2() {
        assertTrue(IOCase.SENSITIVE.checkStartsWith("ABC", "A"));
    }

    @Test
    public void test_checkStartsWith_functionality_3() {
        assertTrue(IOCase.SENSITIVE.checkStartsWith("ABC", "AB"));
    }

    @Test
    public void test_checkStartsWith_functionality_4() {
        assertTrue(IOCase.SENSITIVE.checkStartsWith("ABC", "ABC"));
    }

    @Test
    public void test_checkStartsWith_functionality_5() {
        assertFalse(IOCase.SENSITIVE.checkStartsWith("ABC", "BC"));
    }

    @Test
    public void test_checkStartsWith_functionality_6() {
        assertFalse(IOCase.SENSITIVE.checkStartsWith("ABC", "C"));
    }

    @Test
    public void test_checkStartsWith_functionality_7() {
        assertFalse(IOCase.SENSITIVE.checkStartsWith("ABC", "ABCD"));
    }

    @Test
    public void test_checkStartsWith_functionality_8() {
        assertFalse(IOCase.SENSITIVE.checkStartsWith("", "ABC"));
    }

    @Test
    public void test_checkStartsWith_functionality_9() {
        assertTrue(IOCase.SENSITIVE.checkStartsWith("", ""));
    }

    @Test
    public void test_checkStartsWith_functionality_10() {
        assertFalse(IOCase.SENSITIVE.checkStartsWith("ABC", null));
    }

    @Test
    public void test_checkStartsWith_functionality_11() {
        assertFalse(IOCase.SENSITIVE.checkStartsWith(null, "ABC"));
    }

    @Test
    public void test_checkStartsWith_functionality_12() {
        assertFalse(IOCase.SENSITIVE.checkStartsWith(null, null));
    }

    @Test
    public void test_getName_1() {
        assertEquals("Sensitive", IOCase.SENSITIVE.getName());
    }

    @Test
    public void test_getName_2() {
        assertEquals("Insensitive", IOCase.INSENSITIVE.getName());
    }

    @Test
    public void test_getName_3() {
        assertEquals("System", IOCase.SYSTEM.getName());
    }

    @Test
    public void test_isCaseSensitive_1() {
        assertTrue(IOCase.SENSITIVE.isCaseSensitive());
    }

    @Test
    public void test_isCaseSensitive_2() {
        assertFalse(IOCase.INSENSITIVE.isCaseSensitive());
    }

    @Test
    public void test_isCaseSensitive_3() {
        assertEquals(!WINDOWS, IOCase.SYSTEM.isCaseSensitive());
    }

    @Test
    public void test_isCaseSensitive_static_1() {
        assertTrue(IOCase.isCaseSensitive(IOCase.SENSITIVE));
    }

    @Test
    public void test_isCaseSensitive_static_2() {
        assertFalse(IOCase.isCaseSensitive(IOCase.INSENSITIVE));
    }

    @Test
    public void test_isCaseSensitive_static_3() {
        assertEquals(!WINDOWS, IOCase.isCaseSensitive(IOCase.SYSTEM));
    }

    @Test
    public void test_serialization_1() throws Exception {
        assertSame(IOCase.SENSITIVE, serialize(IOCase.SENSITIVE));
    }

    @Test
    public void test_serialization_2() throws Exception {
        assertSame(IOCase.INSENSITIVE, serialize(IOCase.INSENSITIVE));
    }

    @Test
    public void test_serialization_3() throws Exception {
        assertSame(IOCase.SYSTEM, serialize(IOCase.SYSTEM));
    }

    @Test
    public void test_toString_1() {
        assertEquals("Sensitive", IOCase.SENSITIVE.toString());
    }

    @Test
    public void test_toString_2() {
        assertEquals("Insensitive", IOCase.INSENSITIVE.toString());
    }

    @Test
    public void test_toString_3() {
        assertEquals("System", IOCase.SYSTEM.toString());
    }
}
