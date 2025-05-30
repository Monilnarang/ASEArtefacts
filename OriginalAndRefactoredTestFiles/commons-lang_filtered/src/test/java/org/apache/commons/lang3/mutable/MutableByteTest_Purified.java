package org.apache.commons.lang3.mutable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class MutableByteTest_Purified extends AbstractLangTest {

    @Test
    public void testConstructors_1() {
        assertEquals((byte) 0, new MutableByte().byteValue());
    }

    @Test
    public void testConstructors_2() {
        assertEquals((byte) 1, new MutableByte((byte) 1).byteValue());
    }

    @Test
    public void testConstructors_3() {
        assertEquals((byte) 2, new MutableByte(Byte.valueOf((byte) 2)).byteValue());
    }

    @Test
    public void testConstructors_4() {
        assertEquals((byte) 3, new MutableByte(new MutableByte((byte) 3)).byteValue());
    }

    @Test
    public void testConstructors_5() {
        assertEquals((byte) 2, new MutableByte("2").byteValue());
    }

    @Test
    public void testGetSet_1() {
        assertEquals((byte) 0, new MutableByte().byteValue());
    }

    @Test
    public void testGetSet_2() {
        assertEquals(Byte.valueOf((byte) 0), new MutableByte().getValue());
    }

    @Test
    public void testGetSet_3_testMerged_3() {
        final MutableByte mutNum = new MutableByte((byte) 0);
        mutNum.setValue((byte) 1);
        assertEquals((byte) 1, mutNum.byteValue());
        assertEquals(Byte.valueOf((byte) 1), mutNum.getValue());
        mutNum.setValue(Byte.valueOf((byte) 2));
        assertEquals((byte) 2, mutNum.byteValue());
        assertEquals(Byte.valueOf((byte) 2), mutNum.getValue());
        mutNum.setValue(new MutableByte((byte) 3));
        assertEquals((byte) 3, mutNum.byteValue());
        assertEquals(Byte.valueOf((byte) 3), mutNum.getValue());
    }

    @Test
    public void testToByte_1() {
        assertEquals(Byte.valueOf((byte) 0), new MutableByte((byte) 0).toByte());
    }

    @Test
    public void testToByte_2() {
        assertEquals(Byte.valueOf((byte) 123), new MutableByte((byte) 123).toByte());
    }

    @Test
    public void testToString_1() {
        assertEquals("0", new MutableByte((byte) 0).toString());
    }

    @Test
    public void testToString_2() {
        assertEquals("10", new MutableByte((byte) 10).toString());
    }

    @Test
    public void testToString_3() {
        assertEquals("-123", new MutableByte((byte) -123).toString());
    }
}
