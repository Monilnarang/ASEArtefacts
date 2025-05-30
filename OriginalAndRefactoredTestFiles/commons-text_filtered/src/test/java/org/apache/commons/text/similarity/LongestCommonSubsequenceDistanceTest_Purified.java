package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LongestCommonSubsequenceDistanceTest_Purified {

    private static LongestCommonSubsequenceDistance subject;

    @BeforeAll
    public static void setup() {
        subject = new LongestCommonSubsequenceDistance();
    }

    @Test
    public void testGettingLongestCommonSubsequenceDistance_1() {
        assertEquals(0, subject.apply("", ""));
    }

    @Test
    public void testGettingLongestCommonSubsequenceDistance_2() {
        assertEquals(4, subject.apply("left", ""));
    }

    @Test
    public void testGettingLongestCommonSubsequenceDistance_3() {
        assertEquals(5, subject.apply("", "right"));
    }

    @Test
    public void testGettingLongestCommonSubsequenceDistance_4() {
        assertEquals(1, subject.apply("frog", "fog"));
    }

    @Test
    public void testGettingLongestCommonSubsequenceDistance_5() {
        assertEquals(6, subject.apply("fly", "ant"));
    }

    @Test
    public void testGettingLongestCommonSubsequenceDistance_6() {
        assertEquals(11, subject.apply("elephant", "hippo"));
    }

    @Test
    public void testGettingLongestCommonSubsequenceDistance_7() {
        assertEquals(7, subject.apply("ABC Corporation", "ABC Corp"));
    }

    @Test
    public void testGettingLongestCommonSubsequenceDistance_8() {
        assertEquals(4, subject.apply("D N H Enterprises Inc", "D & H Enterprises, Inc."));
    }

    @Test
    public void testGettingLongestCommonSubsequenceDistance_9() {
        assertEquals(9, subject.apply("My Gym Children's Fitness Center", "My Gym. Childrens Fitness"));
    }

    @Test
    public void testGettingLongestCommonSubsequenceDistance_10() {
        assertEquals(3, subject.apply("PENNSYLVANIA", "PENNCISYLVNIA"));
    }

    @Test
    public void testGettingLongestCommonSubsequenceDistance_11() {
        assertEquals(7, subject.apply("left", "right"));
    }

    @Test
    public void testGettingLongestCommonSubsequenceDistance_12() {
        assertEquals(9, subject.apply("leettteft", "ritttght"));
    }

    @Test
    public void testGettingLongestCommonSubsequenceDistance_13() {
        assertEquals(0, subject.apply("the same string", "the same string"));
    }
}
