package org.apache.flink.util;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MathUtilTest_Purified {

    @Test
    void testRoundDownToPowerOf2_1() {
        assertThat(MathUtils.roundDownToPowerOf2(0)).isZero();
    }

    @Test
    void testRoundDownToPowerOf2_2() {
        assertThat(MathUtils.roundDownToPowerOf2(1)).isOne();
    }

    @Test
    void testRoundDownToPowerOf2_3() {
        assertThat(MathUtils.roundDownToPowerOf2(2)).isEqualTo(2);
    }

    @Test
    void testRoundDownToPowerOf2_4() {
        assertThat(MathUtils.roundDownToPowerOf2(3)).isEqualTo(2);
    }

    @Test
    void testRoundDownToPowerOf2_5() {
        assertThat(MathUtils.roundDownToPowerOf2(4)).isEqualTo(4);
    }

    @Test
    void testRoundDownToPowerOf2_6() {
        assertThat(MathUtils.roundDownToPowerOf2(5)).isEqualTo(4);
    }

    @Test
    void testRoundDownToPowerOf2_7() {
        assertThat(MathUtils.roundDownToPowerOf2(6)).isEqualTo(4);
    }

    @Test
    void testRoundDownToPowerOf2_8() {
        assertThat(MathUtils.roundDownToPowerOf2(7)).isEqualTo(4);
    }

    @Test
    void testRoundDownToPowerOf2_9() {
        assertThat(MathUtils.roundDownToPowerOf2(8)).isEqualTo(8);
    }

    @Test
    void testRoundDownToPowerOf2_10() {
        assertThat(MathUtils.roundDownToPowerOf2(9)).isEqualTo(8);
    }

    @Test
    void testRoundDownToPowerOf2_11() {
        assertThat(MathUtils.roundDownToPowerOf2(15)).isEqualTo(8);
    }

    @Test
    void testRoundDownToPowerOf2_12() {
        assertThat(MathUtils.roundDownToPowerOf2(16)).isEqualTo(16);
    }

    @Test
    void testRoundDownToPowerOf2_13() {
        assertThat(MathUtils.roundDownToPowerOf2(17)).isEqualTo(16);
    }

    @Test
    void testRoundDownToPowerOf2_14() {
        assertThat(MathUtils.roundDownToPowerOf2(31)).isEqualTo(16);
    }

    @Test
    void testRoundDownToPowerOf2_15() {
        assertThat(MathUtils.roundDownToPowerOf2(32)).isEqualTo(32);
    }

    @Test
    void testRoundDownToPowerOf2_16() {
        assertThat(MathUtils.roundDownToPowerOf2(33)).isEqualTo(32);
    }

    @Test
    void testRoundDownToPowerOf2_17() {
        assertThat(MathUtils.roundDownToPowerOf2(42)).isEqualTo(32);
    }

    @Test
    void testRoundDownToPowerOf2_18() {
        assertThat(MathUtils.roundDownToPowerOf2(63)).isEqualTo(32);
    }

    @Test
    void testRoundDownToPowerOf2_19() {
        assertThat(MathUtils.roundDownToPowerOf2(64)).isEqualTo(64);
    }

    @Test
    void testRoundDownToPowerOf2_20() {
        assertThat(MathUtils.roundDownToPowerOf2(125)).isEqualTo(64);
    }

    @Test
    void testRoundDownToPowerOf2_21() {
        assertThat(MathUtils.roundDownToPowerOf2(25654)).isEqualTo(16384);
    }

    @Test
    void testRoundDownToPowerOf2_22() {
        assertThat(MathUtils.roundDownToPowerOf2(34366363)).isEqualTo(33554432);
    }

    @Test
    void testRoundDownToPowerOf2_23() {
        assertThat(MathUtils.roundDownToPowerOf2(63463463)).isEqualTo(33554432);
    }

    @Test
    void testRoundDownToPowerOf2_24() {
        assertThat(MathUtils.roundDownToPowerOf2(1852987883)).isEqualTo(1073741824);
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
    void testRoundUpToPowerOf2_3() {
        assertThat(MathUtils.roundUpToPowerOfTwo(2)).isEqualTo(2);
    }

    @Test
    void testRoundUpToPowerOf2_4() {
        assertThat(MathUtils.roundUpToPowerOfTwo(3)).isEqualTo(4);
    }

    @Test
    void testRoundUpToPowerOf2_5() {
        assertThat(MathUtils.roundUpToPowerOfTwo(4)).isEqualTo(4);
    }

    @Test
    void testRoundUpToPowerOf2_6() {
        assertThat(MathUtils.roundUpToPowerOfTwo(5)).isEqualTo(8);
    }

    @Test
    void testRoundUpToPowerOf2_7() {
        assertThat(MathUtils.roundUpToPowerOfTwo(6)).isEqualTo(8);
    }

    @Test
    void testRoundUpToPowerOf2_8() {
        assertThat(MathUtils.roundUpToPowerOfTwo(7)).isEqualTo(8);
    }

    @Test
    void testRoundUpToPowerOf2_9() {
        assertThat(MathUtils.roundUpToPowerOfTwo(8)).isEqualTo(8);
    }

    @Test
    void testRoundUpToPowerOf2_10() {
        assertThat(MathUtils.roundUpToPowerOfTwo(9)).isEqualTo(16);
    }

    @Test
    void testRoundUpToPowerOf2_11() {
        assertThat(MathUtils.roundUpToPowerOfTwo(15)).isEqualTo(16);
    }

    @Test
    void testRoundUpToPowerOf2_12() {
        assertThat(MathUtils.roundUpToPowerOfTwo(16)).isEqualTo(16);
    }

    @Test
    void testRoundUpToPowerOf2_13() {
        assertThat(MathUtils.roundUpToPowerOfTwo(17)).isEqualTo(32);
    }

    @Test
    void testRoundUpToPowerOf2_14() {
        assertThat(MathUtils.roundUpToPowerOfTwo(31)).isEqualTo(32);
    }

    @Test
    void testRoundUpToPowerOf2_15() {
        assertThat(MathUtils.roundUpToPowerOfTwo(32)).isEqualTo(32);
    }

    @Test
    void testRoundUpToPowerOf2_16() {
        assertThat(MathUtils.roundUpToPowerOfTwo(33)).isEqualTo(64);
    }

    @Test
    void testRoundUpToPowerOf2_17() {
        assertThat(MathUtils.roundUpToPowerOfTwo(42)).isEqualTo(64);
    }

    @Test
    void testRoundUpToPowerOf2_18() {
        assertThat(MathUtils.roundUpToPowerOfTwo(63)).isEqualTo(64);
    }

    @Test
    void testRoundUpToPowerOf2_19() {
        assertThat(MathUtils.roundUpToPowerOfTwo(64)).isEqualTo(64);
    }

    @Test
    void testRoundUpToPowerOf2_20() {
        assertThat(MathUtils.roundUpToPowerOfTwo(125)).isEqualTo(128);
    }

    @Test
    void testRoundUpToPowerOf2_21() {
        assertThat(MathUtils.roundUpToPowerOfTwo(25654)).isEqualTo(32768);
    }

    @Test
    void testRoundUpToPowerOf2_22() {
        assertThat(MathUtils.roundUpToPowerOfTwo(34366363)).isEqualTo(67108864);
    }

    @Test
    void testRoundUpToPowerOf2_23() {
        assertThat(MathUtils.roundUpToPowerOfTwo(67108863)).isEqualTo(67108864);
    }

    @Test
    void testRoundUpToPowerOf2_24() {
        assertThat(MathUtils.roundUpToPowerOfTwo(67108864)).isEqualTo(67108864);
    }

    @Test
    void testRoundUpToPowerOf2_25() {
        assertThat(MathUtils.roundUpToPowerOfTwo(0x3FFFFFFE)).isEqualTo(0x40000000);
    }

    @Test
    void testRoundUpToPowerOf2_26() {
        assertThat(MathUtils.roundUpToPowerOfTwo(0x3FFFFFFF)).isEqualTo(0x40000000);
    }

    @Test
    void testRoundUpToPowerOf2_27() {
        assertThat(MathUtils.roundUpToPowerOfTwo(0x40000000)).isEqualTo(0x40000000);
    }

    @Test
    void testPowerOfTwo_1() {
        assertThat(MathUtils.isPowerOf2(1)).isTrue();
    }

    @Test
    void testPowerOfTwo_2() {
        assertThat(MathUtils.isPowerOf2(2)).isTrue();
    }

    @Test
    void testPowerOfTwo_3() {
        assertThat(MathUtils.isPowerOf2(4)).isTrue();
    }

    @Test
    void testPowerOfTwo_4() {
        assertThat(MathUtils.isPowerOf2(8)).isTrue();
    }

    @Test
    void testPowerOfTwo_5() {
        assertThat(MathUtils.isPowerOf2(32768)).isTrue();
    }

    @Test
    void testPowerOfTwo_6() {
        assertThat(MathUtils.isPowerOf2(65536)).isTrue();
    }

    @Test
    void testPowerOfTwo_7() {
        assertThat(MathUtils.isPowerOf2(1 << 30)).isTrue();
    }

    @Test
    void testPowerOfTwo_8() {
        assertThat(MathUtils.isPowerOf2(1L + Integer.MAX_VALUE)).isTrue();
    }

    @Test
    void testPowerOfTwo_9() {
        assertThat(MathUtils.isPowerOf2(1L << 41)).isTrue();
    }

    @Test
    void testPowerOfTwo_10() {
        assertThat(MathUtils.isPowerOf2(1L << 62)).isTrue();
    }

    @Test
    void testPowerOfTwo_11() {
        assertThat(MathUtils.isPowerOf2(3)).isFalse();
    }

    @Test
    void testPowerOfTwo_12() {
        assertThat(MathUtils.isPowerOf2(5)).isFalse();
    }

    @Test
    void testPowerOfTwo_13() {
        assertThat(MathUtils.isPowerOf2(567923)).isFalse();
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
    void testDivideRoundUp_1() {
        assertThat(MathUtils.divideRoundUp(0, 1)).isZero();
    }

    @Test
    void testDivideRoundUp_2() {
        assertThat(MathUtils.divideRoundUp(0, 2)).isZero();
    }

    @Test
    void testDivideRoundUp_3() {
        assertThat(MathUtils.divideRoundUp(1, 1)).isOne();
    }

    @Test
    void testDivideRoundUp_4() {
        assertThat(MathUtils.divideRoundUp(1, 2)).isOne();
    }

    @Test
    void testDivideRoundUp_5() {
        assertThat(MathUtils.divideRoundUp(2, 1)).isEqualTo(2);
    }

    @Test
    void testDivideRoundUp_6() {
        assertThat(MathUtils.divideRoundUp(2, 2)).isOne();
    }

    @Test
    void testDivideRoundUp_7() {
        assertThat(MathUtils.divideRoundUp(2, 3)).isOne();
    }
}
