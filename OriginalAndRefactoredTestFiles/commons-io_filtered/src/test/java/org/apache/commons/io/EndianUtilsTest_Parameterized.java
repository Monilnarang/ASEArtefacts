package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class EndianUtilsTest_Parameterized {

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

    @ParameterizedTest
    @MethodSource("Provider_testSwapInteger_1to7")
    public void testSwapInteger_1to7(int param1, int param2) {
        assertEquals(param1, EndianUtils.swapInteger(param2));
    }

    static public Stream<Arguments> Provider_testSwapInteger_1to7() {
        return Stream.of(arguments(0, 0), arguments("0x04030201", "0x01020304"), arguments("0x01000000", "0x00000001"), arguments("0x00000001", "0x01000000"), arguments("0x11111111", "0x11111111"), arguments("0xabcdef10", "0x10efcdab"), arguments("0xab", "0xab000000"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSwapLong_1to4")
    public void testSwapLong_1to4(int param1, int param2) {
        assertEquals(param1, EndianUtils.swapLong(param2));
    }

    static public Stream<Arguments> Provider_testSwapLong_1to4() {
        return Stream.of(arguments(0, 0), arguments(0x0807060504030201L, 0x0102030405060708L), arguments(0xffffffffffffffffL, 0xffffffffffffffffL), arguments("0xab", 0xab00000000000000L));
    }

    @ParameterizedTest
    @MethodSource("Provider_testSwapShort_1to4")
    public void testSwapShort_1to4(int param1, int param2) {
        assertEquals((short) param1, EndianUtils.swapShort((short) param2));
    }

    static public Stream<Arguments> Provider_testSwapShort_1to4() {
        return Stream.of(arguments(0, 0), arguments("0x0201", "0x0102"), arguments("0xffff", "0xffff"), arguments("0x0102", "0x0201"));
    }
}
