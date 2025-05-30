package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import org.junit.jupiter.api.Test;

public class EndianUtilsTest_Purified {

    @Test
    public void testSwapDouble_1() {
        assertEquals(0.0, EndianUtils.swapDouble(0.0), 0.0);
    }

    @Test
    public void testSwapDouble_2() {
        final double d1 = Double.longBitsToDouble(0x0102030405060708L);
        final double d2 = Double.longBitsToDouble(0x0807060504030201L);
        assertEquals(d2, EndianUtils.swapDouble(d1), 0.0);
    }

    @Test
    public void testSwapFloat_1() {
        assertEquals(0.0f, EndianUtils.swapFloat(0.0f), 0.0);
    }

    @Test
    public void testSwapFloat_2() {
        final float f1 = Float.intBitsToFloat(0x01020304);
        final float f2 = Float.intBitsToFloat(0x04030201);
        assertEquals(f2, EndianUtils.swapFloat(f1), 0.0);
    }

    @Test
    public void testSwapInteger_1() {
        assertEquals(0, EndianUtils.swapInteger(0));
    }

    @Test
    public void testSwapInteger_2() {
        assertEquals(0x04030201, EndianUtils.swapInteger(0x01020304));
    }

    @Test
    public void testSwapInteger_3() {
        assertEquals(0x01000000, EndianUtils.swapInteger(0x00000001));
    }

    @Test
    public void testSwapInteger_4() {
        assertEquals(0x00000001, EndianUtils.swapInteger(0x01000000));
    }

    @Test
    public void testSwapInteger_5() {
        assertEquals(0x11111111, EndianUtils.swapInteger(0x11111111));
    }

    @Test
    public void testSwapInteger_6() {
        assertEquals(0xabcdef10, EndianUtils.swapInteger(0x10efcdab));
    }

    @Test
    public void testSwapInteger_7() {
        assertEquals(0xab, EndianUtils.swapInteger(0xab000000));
    }

    @Test
    public void testSwapLong_1() {
        assertEquals(0, EndianUtils.swapLong(0));
    }

    @Test
    public void testSwapLong_2() {
        assertEquals(0x0807060504030201L, EndianUtils.swapLong(0x0102030405060708L));
    }

    @Test
    public void testSwapLong_3() {
        assertEquals(0xffffffffffffffffL, EndianUtils.swapLong(0xffffffffffffffffL));
    }

    @Test
    public void testSwapLong_4() {
        assertEquals(0xab, EndianUtils.swapLong(0xab00000000000000L));
    }

    @Test
    public void testSwapShort_1() {
        assertEquals((short) 0, EndianUtils.swapShort((short) 0));
    }

    @Test
    public void testSwapShort_2() {
        assertEquals((short) 0x0201, EndianUtils.swapShort((short) 0x0102));
    }

    @Test
    public void testSwapShort_3() {
        assertEquals((short) 0xffff, EndianUtils.swapShort((short) 0xffff));
    }

    @Test
    public void testSwapShort_4() {
        assertEquals((short) 0x0102, EndianUtils.swapShort((short) 0x0201));
    }

    @Test
    public void testSymmetry_1() {
        assertEquals((short) 0x0102, EndianUtils.swapShort(EndianUtils.swapShort((short) 0x0102)));
    }

    @Test
    public void testSymmetry_2() {
        assertEquals(0x01020304, EndianUtils.swapInteger(EndianUtils.swapInteger(0x01020304)));
    }

    @Test
    public void testSymmetry_3() {
        assertEquals(0x0102030405060708L, EndianUtils.swapLong(EndianUtils.swapLong(0x0102030405060708L)));
    }

    @Test
    public void testSymmetry_4() {
        final float f1 = Float.intBitsToFloat(0x01020304);
        assertEquals(f1, EndianUtils.swapFloat(EndianUtils.swapFloat(f1)), 0.0);
    }

    @Test
    public void testSymmetry_5() {
        final double d1 = Double.longBitsToDouble(0x0102030405060708L);
        assertEquals(d1, EndianUtils.swapDouble(EndianUtils.swapDouble(d1)), 0.0);
    }
}
