package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class BitFieldTest_Purified extends AbstractLangTest {

    private static final BitField bf_multi = new BitField(0x3F80);

    private static final BitField bf_single = new BitField(0x4000);

    private static final BitField bf_zero = new BitField(0);

    @Test
    public void testByte_1() {
        assertEquals(0, new BitField(0).setByteBoolean((byte) 0, true));
    }

    @Test
    public void testByte_2() {
        assertEquals(1, new BitField(1).setByteBoolean((byte) 0, true));
    }

    @Test
    public void testByte_3() {
        assertEquals(2, new BitField(2).setByteBoolean((byte) 0, true));
    }

    @Test
    public void testByte_4() {
        assertEquals(4, new BitField(4).setByteBoolean((byte) 0, true));
    }

    @Test
    public void testByte_5() {
        assertEquals(8, new BitField(8).setByteBoolean((byte) 0, true));
    }

    @Test
    public void testByte_6() {
        assertEquals(16, new BitField(16).setByteBoolean((byte) 0, true));
    }

    @Test
    public void testByte_7() {
        assertEquals(32, new BitField(32).setByteBoolean((byte) 0, true));
    }

    @Test
    public void testByte_8() {
        assertEquals(64, new BitField(64).setByteBoolean((byte) 0, true));
    }

    @Test
    public void testByte_9() {
        assertEquals(-128, new BitField(128).setByteBoolean((byte) 0, true));
    }

    @Test
    public void testByte_10() {
        assertEquals(1, new BitField(0).setByteBoolean((byte) 1, false));
    }

    @Test
    public void testByte_11() {
        assertEquals(0, new BitField(1).setByteBoolean((byte) 1, false));
    }

    @Test
    public void testByte_12() {
        assertEquals(0, new BitField(2).setByteBoolean((byte) 2, false));
    }

    @Test
    public void testByte_13() {
        assertEquals(0, new BitField(4).setByteBoolean((byte) 4, false));
    }

    @Test
    public void testByte_14() {
        assertEquals(0, new BitField(8).setByteBoolean((byte) 8, false));
    }

    @Test
    public void testByte_15() {
        assertEquals(0, new BitField(16).setByteBoolean((byte) 16, false));
    }

    @Test
    public void testByte_16() {
        assertEquals(0, new BitField(32).setByteBoolean((byte) 32, false));
    }

    @Test
    public void testByte_17() {
        assertEquals(0, new BitField(64).setByteBoolean((byte) 64, false));
    }

    @Test
    public void testByte_18() {
        assertEquals(0, new BitField(128).setByteBoolean((byte) 128, false));
    }

    @Test
    public void testByte_19() {
        assertEquals(-2, new BitField(1).setByteBoolean((byte) 255, false));
    }

    @Test
    public void testByte_20() {
        final byte clearedBit = new BitField(0x40).setByteBoolean((byte) -63, false);
        assertFalse(new BitField(0x40).isSet(clearedBit));
    }

    @Test
    public void testClear_1() {
        assertEquals(bf_multi.clear(-1), 0xFFFFC07F);
    }

    @Test
    public void testClear_2() {
        assertEquals(bf_single.clear(-1), 0xFFFFBFFF);
    }

    @Test
    public void testClear_3() {
        assertEquals(bf_zero.clear(-1), 0xFFFFFFFF);
    }

    @Test
    public void testClearShort_1() {
        assertEquals(bf_multi.clearShort((short) -1), (short) 0xC07F);
    }

    @Test
    public void testClearShort_2() {
        assertEquals(bf_single.clearShort((short) -1), (short) 0xBFFF);
    }

    @Test
    public void testClearShort_3() {
        assertEquals(bf_zero.clearShort((short) -1), (short) 0xFFFF);
    }

    @Test
    public void testGetRawValue_1() {
        assertEquals(bf_multi.getRawValue(-1), 0x3F80);
    }

    @Test
    public void testGetRawValue_2() {
        assertEquals(bf_multi.getRawValue(0), 0);
    }

    @Test
    public void testGetRawValue_3() {
        assertEquals(bf_single.getRawValue(-1), 0x4000);
    }

    @Test
    public void testGetRawValue_4() {
        assertEquals(bf_single.getRawValue(0), 0);
    }

    @Test
    public void testGetRawValue_5() {
        assertEquals(bf_zero.getRawValue(-1), 0);
    }

    @Test
    public void testGetRawValue_6() {
        assertEquals(bf_zero.getRawValue(0), 0);
    }

    @Test
    public void testGetShortRawValue_1() {
        assertEquals(bf_multi.getShortRawValue((short) -1), (short) 0x3F80);
    }

    @Test
    public void testGetShortRawValue_2() {
        assertEquals(bf_multi.getShortRawValue((short) 0), (short) 0);
    }

    @Test
    public void testGetShortRawValue_3() {
        assertEquals(bf_single.getShortRawValue((short) -1), (short) 0x4000);
    }

    @Test
    public void testGetShortRawValue_4() {
        assertEquals(bf_single.getShortRawValue((short) 0), (short) 0);
    }

    @Test
    public void testGetShortRawValue_5() {
        assertEquals(bf_zero.getShortRawValue((short) -1), (short) 0);
    }

    @Test
    public void testGetShortRawValue_6() {
        assertEquals(bf_zero.getShortRawValue((short) 0), (short) 0);
    }

    @Test
    public void testGetShortValue_1() {
        assertEquals(bf_multi.getShortValue((short) -1), (short) 127);
    }

    @Test
    public void testGetShortValue_2() {
        assertEquals(bf_multi.getShortValue((short) 0), (short) 0);
    }

    @Test
    public void testGetShortValue_3() {
        assertEquals(bf_single.getShortValue((short) -1), (short) 1);
    }

    @Test
    public void testGetShortValue_4() {
        assertEquals(bf_single.getShortValue((short) 0), (short) 0);
    }

    @Test
    public void testGetShortValue_5() {
        assertEquals(bf_zero.getShortValue((short) -1), (short) 0);
    }

    @Test
    public void testGetShortValue_6() {
        assertEquals(bf_zero.getShortValue((short) 0), (short) 0);
    }

    @Test
    public void testGetValue_1() {
        assertEquals(bf_multi.getValue(-1), 127);
    }

    @Test
    public void testGetValue_2() {
        assertEquals(bf_multi.getValue(0), 0);
    }

    @Test
    public void testGetValue_3() {
        assertEquals(bf_single.getValue(-1), 1);
    }

    @Test
    public void testGetValue_4() {
        assertEquals(bf_single.getValue(0), 0);
    }

    @Test
    public void testGetValue_5() {
        assertEquals(bf_zero.getValue(-1), 0);
    }

    @Test
    public void testGetValue_6() {
        assertEquals(bf_zero.getValue(0), 0);
    }

    @Test
    public void testSet_1() {
        assertEquals(bf_multi.set(0), 0x3F80);
    }

    @Test
    public void testSet_2() {
        assertEquals(bf_single.set(0), 0x4000);
    }

    @Test
    public void testSet_3() {
        assertEquals(bf_zero.set(0), 0);
    }

    @Test
    public void testSetBoolean_1() {
        assertEquals(bf_multi.set(0), bf_multi.setBoolean(0, true));
    }

    @Test
    public void testSetBoolean_2() {
        assertEquals(bf_single.set(0), bf_single.setBoolean(0, true));
    }

    @Test
    public void testSetBoolean_3() {
        assertEquals(bf_zero.set(0), bf_zero.setBoolean(0, true));
    }

    @Test
    public void testSetBoolean_4() {
        assertEquals(bf_multi.clear(-1), bf_multi.setBoolean(-1, false));
    }

    @Test
    public void testSetBoolean_5() {
        assertEquals(bf_single.clear(-1), bf_single.setBoolean(-1, false));
    }

    @Test
    public void testSetBoolean_6() {
        assertEquals(bf_zero.clear(-1), bf_zero.setBoolean(-1, false));
    }

    @Test
    public void testSetShort_1() {
        assertEquals(bf_multi.setShort((short) 0), (short) 0x3F80);
    }

    @Test
    public void testSetShort_2() {
        assertEquals(bf_single.setShort((short) 0), (short) 0x4000);
    }

    @Test
    public void testSetShort_3() {
        assertEquals(bf_zero.setShort((short) 0), (short) 0);
    }

    @Test
    public void testSetShortBoolean_1() {
        assertEquals(bf_multi.setShort((short) 0), bf_multi.setShortBoolean((short) 0, true));
    }

    @Test
    public void testSetShortBoolean_2() {
        assertEquals(bf_single.setShort((short) 0), bf_single.setShortBoolean((short) 0, true));
    }

    @Test
    public void testSetShortBoolean_3() {
        assertEquals(bf_zero.setShort((short) 0), bf_zero.setShortBoolean((short) 0, true));
    }

    @Test
    public void testSetShortBoolean_4() {
        assertEquals(bf_multi.clearShort((short) -1), bf_multi.setShortBoolean((short) -1, false));
    }

    @Test
    public void testSetShortBoolean_5() {
        assertEquals(bf_single.clearShort((short) -1), bf_single.setShortBoolean((short) -1, false));
    }

    @Test
    public void testSetShortBoolean_6() {
        assertEquals(bf_zero.clearShort((short) -1), bf_zero.setShortBoolean((short) -1, false));
    }
}
