package com.graphhopper.util;

import com.carrotsearch.hppc.IntArrayList;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static com.carrotsearch.hppc.IntArrayList.from;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ArrayUtilTest_Parameterized {

    @Test
    public void testRange_1() {
        assertEquals(from(3, 4, 5, 6), ArrayUtil.range(3, 7));
    }

    @Test
    public void testRange_2() {
        assertEquals(from(-3, -2), ArrayUtil.range(-3, -1));
    }

    @Test
    public void testRange_3() {
        assertEquals(from(), ArrayUtil.range(5, 5));
    }

    @Test
    public void testRangeClosed_1() {
        assertEquals(from(3, 4, 5, 6, 7), ArrayUtil.rangeClosed(3, 7));
    }

    @Test
    public void testRangeClosed_2() {
        assertEquals(from(-3, -2, -1), ArrayUtil.rangeClosed(-3, -1));
    }

    @Test
    public void testRangeClosed_3() {
        assertEquals(from(5), ArrayUtil.rangeClosed(5, 5));
    }

    @Test
    public void testIsPermutation_1() {
        assertTrue(ArrayUtil.isPermutation(IntArrayList.from()));
    }

    @Test
    public void testIsPermutation_2() {
        assertTrue(ArrayUtil.isPermutation(IntArrayList.from(0)));
    }

    @Test
    public void testIsPermutation_3() {
        assertTrue(ArrayUtil.isPermutation(IntArrayList.from(0, 1)));
    }

    @Test
    public void testIsPermutation_4() {
        assertTrue(ArrayUtil.isPermutation(IntArrayList.from(6, 2, 4, 0, 1, 3, 5)));
    }

    @Test
    public void testIsPermutation_5() {
        assertFalse(ArrayUtil.isPermutation(IntArrayList.from(1, 2)));
    }

    @Test
    public void testIsPermutation_6() {
        assertFalse(ArrayUtil.isPermutation(IntArrayList.from(-1)));
    }

    @Test
    public void testIsPermutation_7() {
        assertFalse(ArrayUtil.isPermutation(IntArrayList.from(1)));
    }

    @Test
    public void testIsPermutation_8() {
        assertFalse(ArrayUtil.isPermutation(IntArrayList.from(3, 4, 0, 1)));
    }

    @Test
    public void testIsPermutation_9() {
        assertFalse(ArrayUtil.isPermutation(IntArrayList.from(0, 1, 3, 3, 4, 4, 6)));
    }

    @Test
    public void testReverse_1() {
        assertEquals(from(), ArrayUtil.reverse(from()));
    }

    @Test
    public void testReverse_2() {
        assertEquals(from(1), ArrayUtil.reverse(from(1)));
    }

    @Test
    public void testReverse_3() {
        assertEquals(from(9, 5), ArrayUtil.reverse(from(5, 9)));
    }

    @Test
    public void testReverse_4() {
        assertEquals(from(7, 1, 3), ArrayUtil.reverse(from(3, 1, 7)));
    }

    @Test
    public void testReverse_5() {
        assertEquals(from(4, 3, 2, 1), ArrayUtil.reverse(from(1, 2, 3, 4)));
    }

    @Test
    public void testReverse_6() {
        assertEquals(from(5, 4, 3, 2, 1), ArrayUtil.reverse(from(1, 2, 3, 4, 5)));
    }

    @Test
    public void testShuffle_1() {
        assertEquals(from(4, 1, 3, 2), ArrayUtil.shuffle(from(1, 2, 3, 4), new Random(0)));
    }

    @Test
    public void testShuffle_2() {
        assertEquals(from(4, 3, 2, 1, 5), ArrayUtil.shuffle(from(1, 2, 3, 4, 5), new Random(1)));
    }

    @Test
    public void removeConsecutiveDuplicates_1_testMerged_1() {
        int[] arr = new int[] { 3, 3, 4, 2, 1, -3, -3, 9, 3, 6, 6, 7, 7 };
        assertEquals(9, ArrayUtil.removeConsecutiveDuplicates(arr, arr.length));
        assertEquals(IntArrayList.from(3, 4, 2, 1, -3, 9, 3, 6, 7, 6, 6, 7, 7), IntArrayList.from(arr));
    }

    @Test
    public void removeConsecutiveDuplicates_3_testMerged_2() {
        int[] brr = new int[] { 4, 4, 3, 5, 3 };
        assertEquals(2, ArrayUtil.removeConsecutiveDuplicates(brr, 3));
        assertEquals(IntArrayList.from(4, 3, 3, 5, 3), IntArrayList.from(brr));
    }

    @Test
    public void testWithoutConsecutiveDuplicates_1() {
        assertEquals(from(), ArrayUtil.withoutConsecutiveDuplicates(from()));
    }

    @Test
    public void testWithoutConsecutiveDuplicates_2() {
        assertEquals(from(1), ArrayUtil.withoutConsecutiveDuplicates(from(1)));
    }

    @Test
    public void testWithoutConsecutiveDuplicates_3() {
        assertEquals(from(1), ArrayUtil.withoutConsecutiveDuplicates(from(1, 1)));
    }

    @Test
    public void testWithoutConsecutiveDuplicates_4() {
        assertEquals(from(1), ArrayUtil.withoutConsecutiveDuplicates(from(1, 1, 1)));
    }

    @Test
    public void testWithoutConsecutiveDuplicates_5() {
        assertEquals(from(1, 2), ArrayUtil.withoutConsecutiveDuplicates(from(1, 1, 2)));
    }

    @Test
    public void testWithoutConsecutiveDuplicates_6() {
        assertEquals(from(1, 2, 1), ArrayUtil.withoutConsecutiveDuplicates(from(1, 2, 1)));
    }

    @Test
    public void testWithoutConsecutiveDuplicates_7() {
        assertEquals(from(5, 6, 5, 8, 9, 11, 2, -1, 3), ArrayUtil.withoutConsecutiveDuplicates(from(5, 5, 5, 6, 6, 5, 5, 8, 9, 11, 11, 2, 2, -1, 3, 3)));
    }

    @Test
    public void testCalcSortOrder_1() {
        assertEquals(from(), from(ArrayUtil.calcSortOrder(from(), from())));
    }

    @Test
    public void testCalcSortOrder_2() {
        assertEquals(from(0), from(ArrayUtil.calcSortOrder(from(3), from(4))));
    }

    @Test
    public void testCalcSortOrder_3() {
        assertEquals(from(0, 2, 3, 1), from(ArrayUtil.calcSortOrder(from(3, 6, 3, 4), from(0, -1, 2, -6))));
    }

    @Test
    public void testCalcSortOrder_4() {
        assertEquals(from(2, 3, 1, 0), from(ArrayUtil.calcSortOrder(from(3, 3, 0, 0), from(0, -1, 1, 2))));
    }

    @Test
    public void testCalcSortOrder_5() {
        assertEquals(from(), from(ArrayUtil.calcSortOrder(new int[] { 3, 3, 0, 0 }, new int[] { 0, -1, 1, 2 }, 0)));
    }

    @Test
    public void testCalcSortOrder_6() {
        assertEquals(from(0), from(ArrayUtil.calcSortOrder(new int[] { 3, 3, 0, 0 }, new int[] { 0, -1, 1, 2 }, 1)));
    }

    @Test
    public void testCalcSortOrder_7() {
        assertEquals(from(1, 0), from(ArrayUtil.calcSortOrder(new int[] { 3, 3, 0, 0 }, new int[] { 0, -1, 1, 2 }, 2)));
    }

    @Test
    public void testCalcSortOrder_8() {
        assertEquals(from(2, 1, 0), from(ArrayUtil.calcSortOrder(new int[] { 3, 3, 0, 0 }, new int[] { 0, -1, 1, 2 }, 3)));
    }

    @Test
    public void testCalcSortOrder_9() {
        assertEquals(from(2, 3, 1, 0), from(ArrayUtil.calcSortOrder(new int[] { 3, 3, 0, 0 }, new int[] { 0, -1, 1, 2 }, 4)));
    }

    @Test
    public void testInvert_1() {
        assertEquals(from(-1, -1, -1, 3), from(ArrayUtil.invert(new int[] { 3, 3, 3, 3 })));
    }

    @Test
    public void testMerge_5() {
        int[] a = { 2, 6, 8, 12, 15 };
        int[] b = { 3, 7, 9, 10, 11, 12, 15, 20, 21, 26 };
        assertEquals(from(2, 3, 6, 7, 8, 9, 10, 11, 12, 15, 20, 21, 26), from(ArrayUtil.merge(a, b)));
    }

    @ParameterizedTest
    @MethodSource("Provider_testInvert_2to3")
    public void testInvert_2to3(String param1, String param2, String param3, String param4, String param5) {
        assertEquals(from(3, 2, 0, 1), from(ArrayUtil.invert(new int[] { 2, 3, 1, 0 })));
    }

    static public Stream<Arguments> Provider_testInvert_2to3() {
        return Stream.of(arguments("{'2', '3', '1', '0'}", 3, 2, 0, 1), arguments("{'3', '2', '0', '1'}", 2, 3, 1, 0));
    }

    @ParameterizedTest
    @MethodSource("Provider_testMerge_1to4")
    public void testMerge_1to4(String param1, String param2, String param3) {
        assertArrayEquals(new int[] {}, ArrayUtil.merge(new int[] {}, new int[] {}));
    }

    static public Stream<Arguments> Provider_testMerge_1to4() {
        return Stream.of(arguments("{}", "{}", "{}"), arguments("{'4', '5'}", "{}", "{'4', '5'}"), arguments("{'4', '5'}", "{'4', '5'}", "{}"), arguments("{'3', '6', '9'}", "{'6', '6', '6', '9'}", "{'3', '9'}"));
    }
}
