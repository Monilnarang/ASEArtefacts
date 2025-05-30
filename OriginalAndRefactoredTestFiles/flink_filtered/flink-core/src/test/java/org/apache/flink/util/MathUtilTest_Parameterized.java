package org.apache.flink.util;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class MathUtilTest_Parameterized {

    @Test
    void testRoundDownToPowerOf2_1() {
        assertThat(MathUtils.roundDownToPowerOf2(0)).isZero();
    }

    @Test
    void testRoundDownToPowerOf2_2() {
        assertThat(MathUtils.roundDownToPowerOf2(1)).isOne();
    }

    @Test
    void testRoundDownToPowerOf2_25() {
        assertThat(MathUtils.roundDownToPowerOf2(Integer.MAX_VALUE)).isEqualTo(1073741824);
    }

    @Test
    void testRoundUpToPowerOf2_1() {
        assertThat(MathUtils.roundUpToPowerOfTwo(0)).isZero();
    }

    @Test
    void testRoundUpToPowerOf2_2() {
        assertThat(MathUtils.roundUpToPowerOfTwo(1)).isOne();
    }

    @Test
    void testPowerOfTwo_8() {
        assertThat(MathUtils.isPowerOf2(1L + Integer.MAX_VALUE)).isTrue();
    }

    @Test
    void testPowerOfTwo_14() {
        assertThat(MathUtils.isPowerOf2(Integer.MAX_VALUE)).isFalse();
    }

    @Test
    void testPowerOfTwo_15() {
        assertThat(MathUtils.isPowerOf2(Long.MAX_VALUE)).isFalse();
    }

    @Test
    void testFlipSignBit_1() {
        assertThat(MathUtils.flipSignBit(Long.MIN_VALUE)).isZero();
    }

    @Test
    void testFlipSignBit_2() {
        assertThat(MathUtils.flipSignBit(0L)).isEqualTo(Long.MIN_VALUE);
    }

    @Test
    void testFlipSignBit_3() {
        assertThat(MathUtils.flipSignBit(Long.MAX_VALUE)).isEqualTo(-1L);
    }

    @Test
    void testFlipSignBit_4() {
        assertThat(MathUtils.flipSignBit(-1L)).isEqualTo(Long.MAX_VALUE);
    }

    @Test
    void testFlipSignBit_5() {
        assertThat(MathUtils.flipSignBit(42L)).isEqualTo(42L | Long.MIN_VALUE);
    }

    @Test
    void testFlipSignBit_6() {
        assertThat(MathUtils.flipSignBit(-42L)).isEqualTo(-42L & Long.MAX_VALUE);
    }

    @Test
    void testDivideRoundUp_5() {
        assertThat(MathUtils.divideRoundUp(2, 1)).isEqualTo(2);
    }

    @ParameterizedTest
    @MethodSource("Provider_testRoundDownToPowerOf2_3to24")
    void testRoundDownToPowerOf2_3to24(int param1, int param2) {
        assertThat(MathUtils.roundDownToPowerOf2(param2)).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_testRoundDownToPowerOf2_3to24() {
        return Stream.of(arguments(2, 2), arguments(2, 3), arguments(4, 4), arguments(4, 5), arguments(4, 6), arguments(4, 7), arguments(8, 8), arguments(8, 9), arguments(8, 15), arguments(16, 16), arguments(16, 17), arguments(16, 31), arguments(32, 32), arguments(32, 33), arguments(32, 42), arguments(32, 63), arguments(64, 64), arguments(64, 125), arguments(16384, 25654), arguments(33554432, 34366363), arguments(33554432, 63463463), arguments(1073741824, 1852987883));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRoundUpToPowerOf2_3to27")
    void testRoundUpToPowerOf2_3to27(int param1, int param2) {
        assertThat(MathUtils.roundUpToPowerOfTwo(param2)).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_testRoundUpToPowerOf2_3to27() {
        return Stream.of(arguments(2, 2), arguments(4, 3), arguments(4, 4), arguments(8, 5), arguments(8, 6), arguments(8, 7), arguments(8, 8), arguments(16, 9), arguments(16, 15), arguments(16, 16), arguments(32, 17), arguments(32, 31), arguments(32, 32), arguments(64, 33), arguments(64, 42), arguments(64, 63), arguments(64, 64), arguments(128, 125), arguments(32768, 25654), arguments(67108864, 34366363), arguments(67108864, 67108863), arguments(67108864, 67108864), arguments("0x40000000", "0x3FFFFFFE"), arguments("0x40000000", "0x3FFFFFFF"), arguments("0x40000000", "0x40000000"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testPowerOfTwo_1to6")
    void testPowerOfTwo_1to6(int param1) {
        assertThat(MathUtils.isPowerOf2(param1)).isTrue();
    }

    static public Stream<Arguments> Provider_testPowerOfTwo_1to6() {
        return Stream.of(arguments(1), arguments(2), arguments(4), arguments(8), arguments(32768), arguments(65536));
    }

    @ParameterizedTest
    @MethodSource("Provider_testPowerOfTwo_7_9to10")
    void testPowerOfTwo_7_9to10(int param1, int param2) {
        assertThat(MathUtils.isPowerOf2(param1 << param2)).isTrue();
    }

    static public Stream<Arguments> Provider_testPowerOfTwo_7_9to10() {
        return Stream.of(arguments(1, 30), arguments(1L, 41), arguments(1L, 62));
    }

    @ParameterizedTest
    @MethodSource("Provider_testPowerOfTwo_11to13")
    void testPowerOfTwo_11to13(int param1) {
        assertThat(MathUtils.isPowerOf2(param1)).isFalse();
    }

    static public Stream<Arguments> Provider_testPowerOfTwo_11to13() {
        return Stream.of(arguments(3), arguments(5), arguments(567923));
    }

    @ParameterizedTest
    @MethodSource("Provider_testDivideRoundUp_1to2")
    void testDivideRoundUp_1to2(int param1, int param2) {
        assertThat(MathUtils.divideRoundUp(param1, param2)).isZero();
    }

    static public Stream<Arguments> Provider_testDivideRoundUp_1to2() {
        return Stream.of(arguments(0, 1), arguments(0, 2));
    }

    @ParameterizedTest
    @MethodSource("Provider_testDivideRoundUp_3to4_6to7")
    void testDivideRoundUp_3to4_6to7(int param1, int param2) {
        assertThat(MathUtils.divideRoundUp(param1, param2)).isOne();
    }

    static public Stream<Arguments> Provider_testDivideRoundUp_3to4_6to7() {
        return Stream.of(arguments(1, 1), arguments(1, 2), arguments(2, 2), arguments(2, 3));
    }
}
