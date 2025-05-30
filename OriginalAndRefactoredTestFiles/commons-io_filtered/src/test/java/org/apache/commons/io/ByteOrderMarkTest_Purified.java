package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.nio.charset.Charset;
import org.junit.jupiter.api.Test;

public class ByteOrderMarkTest_Purified {

    private static final ByteOrderMark TEST_BOM_1 = new ByteOrderMark("test1", 1);

    private static final ByteOrderMark TEST_BOM_2 = new ByteOrderMark("test2", 1, 2);

    private static final ByteOrderMark TEST_BOM_3 = new ByteOrderMark("test3", 1, 2, 3);

    @Test
    public void testConstantCharsetNames_1() {
        assertNotNull(Charset.forName(ByteOrderMark.UTF_8.getCharsetName()));
    }

    @Test
    public void testConstantCharsetNames_2() {
        assertNotNull(Charset.forName(ByteOrderMark.UTF_16BE.getCharsetName()));
    }

    @Test
    public void testConstantCharsetNames_3() {
        assertNotNull(Charset.forName(ByteOrderMark.UTF_16LE.getCharsetName()));
    }

    @Test
    public void testConstantCharsetNames_4() {
        assertNotNull(Charset.forName(ByteOrderMark.UTF_32BE.getCharsetName()));
    }

    @Test
    public void testConstantCharsetNames_5() {
        assertNotNull(Charset.forName(ByteOrderMark.UTF_32LE.getCharsetName()));
    }

    @SuppressWarnings("EqualsWithItself")
    @Test
    public void testEquals_1() {
        assertEquals(ByteOrderMark.UTF_16BE, ByteOrderMark.UTF_16BE);
    }

    @SuppressWarnings("EqualsWithItself")
    @Test
    public void testEquals_2() {
        assertEquals(ByteOrderMark.UTF_16LE, ByteOrderMark.UTF_16LE);
    }

    @SuppressWarnings("EqualsWithItself")
    @Test
    public void testEquals_3() {
        assertEquals(ByteOrderMark.UTF_32BE, ByteOrderMark.UTF_32BE);
    }

    @SuppressWarnings("EqualsWithItself")
    @Test
    public void testEquals_4() {
        assertEquals(ByteOrderMark.UTF_32LE, ByteOrderMark.UTF_32LE);
    }

    @SuppressWarnings("EqualsWithItself")
    @Test
    public void testEquals_5() {
        assertEquals(ByteOrderMark.UTF_8, ByteOrderMark.UTF_8);
    }

    @SuppressWarnings("EqualsWithItself")
    @Test
    public void testEquals_6() {
        assertNotEquals(ByteOrderMark.UTF_8, ByteOrderMark.UTF_16BE);
    }

    @SuppressWarnings("EqualsWithItself")
    @Test
    public void testEquals_7() {
        assertNotEquals(ByteOrderMark.UTF_8, ByteOrderMark.UTF_16LE);
    }

    @SuppressWarnings("EqualsWithItself")
    @Test
    public void testEquals_8() {
        assertNotEquals(ByteOrderMark.UTF_8, ByteOrderMark.UTF_32BE);
    }

    @SuppressWarnings("EqualsWithItself")
    @Test
    public void testEquals_9() {
        assertNotEquals(ByteOrderMark.UTF_8, ByteOrderMark.UTF_32LE);
    }

    @SuppressWarnings("EqualsWithItself")
    @Test
    public void testEquals_10() {
        assertEquals(TEST_BOM_1, TEST_BOM_1, "test1 equals");
    }

    @SuppressWarnings("EqualsWithItself")
    @Test
    public void testEquals_11() {
        assertEquals(TEST_BOM_2, TEST_BOM_2, "test2 equals");
    }

    @SuppressWarnings("EqualsWithItself")
    @Test
    public void testEquals_12() {
        assertEquals(TEST_BOM_3, TEST_BOM_3, "test3 equals");
    }

    @SuppressWarnings("EqualsWithItself")
    @Test
    public void testEquals_13() {
        assertNotEquals(TEST_BOM_1, new Object(), "Object not equal");
    }

    @SuppressWarnings("EqualsWithItself")
    @Test
    public void testEquals_14() {
        assertNotEquals(TEST_BOM_1, new ByteOrderMark("1a", 2), "test1-1 not equal");
    }

    @SuppressWarnings("EqualsWithItself")
    @Test
    public void testEquals_15() {
        assertNotEquals(TEST_BOM_1, new ByteOrderMark("1b", 1, 2), "test1-2 not test2");
    }

    @SuppressWarnings("EqualsWithItself")
    @Test
    public void testEquals_16() {
        assertNotEquals(TEST_BOM_2, new ByteOrderMark("2", 1, 1), "test2 not equal");
    }

    @SuppressWarnings("EqualsWithItself")
    @Test
    public void testEquals_17() {
        assertNotEquals(TEST_BOM_3, new ByteOrderMark("3", 1, 2, 4), "test3 not equal");
    }

    @Test
    public void testGetCharsetName_1() {
        assertEquals("test1", TEST_BOM_1.getCharsetName(), "test1 name");
    }

    @Test
    public void testGetCharsetName_2() {
        assertEquals("test2", TEST_BOM_2.getCharsetName(), "test2 name");
    }

    @Test
    public void testGetCharsetName_3() {
        assertEquals("test3", TEST_BOM_3.getCharsetName(), "test3 name");
    }

    @Test
    public void testGetInt_1() {
        assertEquals(1, TEST_BOM_1.get(0), "test1 get(0)");
    }

    @Test
    public void testGetInt_2() {
        assertEquals(1, TEST_BOM_2.get(0), "test2 get(0)");
    }

    @Test
    public void testGetInt_3() {
        assertEquals(2, TEST_BOM_2.get(1), "test2 get(1)");
    }

    @Test
    public void testGetInt_4() {
        assertEquals(1, TEST_BOM_3.get(0), "test3 get(0)");
    }

    @Test
    public void testGetInt_5() {
        assertEquals(2, TEST_BOM_3.get(1), "test3 get(1)");
    }

    @Test
    public void testGetInt_6() {
        assertEquals(3, TEST_BOM_3.get(2), "test3 get(2)");
    }

    @Test
    public void testLength_1() {
        assertEquals(1, TEST_BOM_1.length(), "test1 length");
    }

    @Test
    public void testLength_2() {
        assertEquals(2, TEST_BOM_2.length(), "test2 length");
    }

    @Test
    public void testLength_3() {
        assertEquals(3, TEST_BOM_3.length(), "test3 length");
    }

    @Test
    public void testMatches_1() {
        assertTrue(ByteOrderMark.UTF_16BE.matches(ByteOrderMark.UTF_16BE.getRawBytes()));
    }

    @Test
    public void testMatches_2() {
        assertTrue(ByteOrderMark.UTF_16LE.matches(ByteOrderMark.UTF_16LE.getRawBytes()));
    }

    @Test
    public void testMatches_3() {
        assertTrue(ByteOrderMark.UTF_32BE.matches(ByteOrderMark.UTF_32BE.getRawBytes()));
    }

    @Test
    public void testMatches_4() {
        assertTrue(ByteOrderMark.UTF_16BE.matches(ByteOrderMark.UTF_16BE.getRawBytes()));
    }

    @Test
    public void testMatches_5() {
        assertTrue(ByteOrderMark.UTF_8.matches(ByteOrderMark.UTF_8.getRawBytes()));
    }

    @Test
    public void testMatches_6() {
        assertTrue(TEST_BOM_1.matches(TEST_BOM_1.getRawBytes()));
    }

    @Test
    public void testMatches_7() {
        assertTrue(TEST_BOM_2.matches(TEST_BOM_2.getRawBytes()));
    }

    @Test
    public void testMatches_8() {
        assertTrue(TEST_BOM_3.matches(TEST_BOM_3.getRawBytes()));
    }

    @Test
    public void testMatches_9() {
        assertFalse(TEST_BOM_1.matches(new ByteOrderMark("1a", 2).getRawBytes()));
    }

    @Test
    public void testMatches_10() {
        assertTrue(TEST_BOM_1.matches(new ByteOrderMark("1b", 1, 2).getRawBytes()));
    }

    @Test
    public void testMatches_11() {
        assertFalse(TEST_BOM_2.matches(new ByteOrderMark("2", 1, 1).getRawBytes()));
    }

    @Test
    public void testMatches_12() {
        assertFalse(TEST_BOM_3.matches(new ByteOrderMark("3", 1, 2, 4).getRawBytes()));
    }

    @Test
    public void testToString_1() {
        assertEquals("ByteOrderMark[test1: 0x1]", TEST_BOM_1.toString(), "test1 ");
    }

    @Test
    public void testToString_2() {
        assertEquals("ByteOrderMark[test2: 0x1,0x2]", TEST_BOM_2.toString(), "test2 ");
    }

    @Test
    public void testToString_3() {
        assertEquals("ByteOrderMark[test3: 0x1,0x2,0x3]", TEST_BOM_3.toString(), "test3 ");
    }
}
