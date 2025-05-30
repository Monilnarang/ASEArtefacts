package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class BitFieldTest_Parameterized extends AbstractLangTest {

    private static final BitField bf_multi = new BitField(0x3F80);

    private static final BitField bf_single = new BitField(0x4000);

    private static final BitField bf_zero = new BitField(0);

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

    @ParameterizedTest
    @MethodSource("Provider_testByte_1to8_10to18")
    public void testByte_1to8_10to18(int param1, int param2, int param3) {
        assertEquals(param1, new BitField(param3).setByteBoolean((byte) 0, param2));
    }

    static public Stream<Arguments> Provider_testByte_1to8_10to18() {
        return Stream.of(arguments(0, 0, 0), arguments(1, 1, 0), arguments(2, 2, 0), arguments(4, 4, 0), arguments(8, 8, 0), arguments(16, 16, 0), arguments(32, 32, 0), arguments(64, 64, 0), arguments(1, 0, 1), arguments(0, 1, 1), arguments(0, 2, 2), arguments(0, 4, 4), arguments(0, 8, 8), arguments(0, 16, 16), arguments(0, 32, 32), arguments(0, 64, 64), arguments(0, 128, 128));
    }

    @ParameterizedTest
    @MethodSource("Provider_testByte_9_19")
    public void testByte_9_19(int param1, int param2, int param3) {
        assertEquals(-param1, new BitField(param3).setByteBoolean((byte) 0, param2));
    }

    static public Stream<Arguments> Provider_testByte_9_19() {
        return Stream.of(arguments(128, 128, 0), arguments(2, 1, 255));
    }
}
