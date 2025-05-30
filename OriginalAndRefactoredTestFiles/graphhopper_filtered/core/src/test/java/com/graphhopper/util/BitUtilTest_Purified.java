package com.graphhopper.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BitUtilTest_Purified {

    private static final BitUtil bitUtil = BitUtil.LITTLE;

    @Test
    public void testToBitString_1() {
        assertEquals("0010101010101010101010101010101010101010101010101010101010101010", bitUtil.toBitString(Long.MAX_VALUE / 3));
    }

    @Test
    public void testToBitString_2() {
        assertEquals("0111111111111111111111111111111111111111111111111111111111111111", bitUtil.toBitString(Long.MAX_VALUE));
    }

    @Test
    public void testToBitString_3() {
        assertEquals("00101010101010101010101010101010", bitUtil.toBitString(bitUtil.fromInt(Integer.MAX_VALUE / 3)));
    }

    @Test
    public void testToBitString_4() {
        assertEquals("10000000000000000000000000000000", bitUtil.toBitString(1L << 63, 32));
    }

    @Test
    public void testToBitString_5() {
        assertEquals("00000000000000000000000000000001", bitUtil.toBitString((1L << 32), 32));
    }

    @Test
    public void testCountBitValue_1() {
        assertEquals(1, BitUtil.countBitValue(1));
    }

    @Test
    public void testCountBitValue_2() {
        assertEquals(2, BitUtil.countBitValue(2));
    }

    @Test
    public void testCountBitValue_3() {
        assertEquals(2, BitUtil.countBitValue(3));
    }

    @Test
    public void testCountBitValue_4() {
        assertEquals(3, BitUtil.countBitValue(4));
    }

    @Test
    public void testCountBitValue_5() {
        assertEquals(3, BitUtil.countBitValue(7));
    }

    @Test
    public void testCountBitValue_6() {
        assertEquals(4, BitUtil.countBitValue(8));
    }

    @Test
    public void testCountBitValue_7() {
        assertEquals(5, BitUtil.countBitValue(20));
    }

    @Test
    public void testUnsignedConversions_1_testMerged_1() {
        long l = Integer.toUnsignedLong(-1);
        assertEquals(4294967295L, l);
        assertEquals(-1, BitUtil.toSignedInt(l));
        int intVal = Integer.MAX_VALUE;
        long maxInt = intVal;
        assertEquals(intVal, BitUtil.toSignedInt(maxInt));
    }

    @Test
    public void testUnsignedConversions_6_testMerged_2() {
        intVal++;
        assertEquals(0xFFFFffffL, (1L << 32) - 1);
        assertTrue(0xFFFFffffL > 0L);
    }

    @Test
    public void testToLastBitString_1() {
        assertEquals("1", bitUtil.toLastBitString(1L, 1));
    }

    @Test
    public void testToLastBitString_2() {
        assertEquals("01", bitUtil.toLastBitString(1L, 2));
    }

    @Test
    public void testToLastBitString_3() {
        assertEquals("001", bitUtil.toLastBitString(1L, 3));
    }

    @Test
    public void testToLastBitString_4() {
        assertEquals("010", bitUtil.toLastBitString(2L, 3));
    }

    @Test
    public void testToLastBitString_5() {
        assertEquals("011", bitUtil.toLastBitString(3L, 3));
    }
}
