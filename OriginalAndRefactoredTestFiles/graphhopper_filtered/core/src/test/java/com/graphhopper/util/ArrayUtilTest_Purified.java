package com.graphhopper.util;

import com.carrotsearch.hppc.IntArrayList;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static com.carrotsearch.hppc.IntArrayList.from;
import static org.junit.jupiter.api.Assertions.*;

class ArrayUtilTest_Purified {

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
}
