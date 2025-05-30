package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LongestCommonSubsequenceTest_Purified {

    private static LongestCommonSubsequence subject;

    @BeforeAll
    public static void setup() {
        subject = new LongestCommonSubsequence();
    }

    @Test
    @Deprecated
    public void testLogestCommonSubsequence_1() {
        assertEquals("", subject.logestCommonSubsequence("", ""));
    }

    @Test
    @Deprecated
    public void testLogestCommonSubsequence_2() {
        assertEquals("", subject.logestCommonSubsequence("left", ""));
    }

    @Test
    @Deprecated
    public void testLogestCommonSubsequence_3() {
        assertEquals("", subject.logestCommonSubsequence("", "right"));
    }

    @Test
    @Deprecated
    public void testLogestCommonSubsequence_4() {
        assertEquals("fog", subject.logestCommonSubsequence("frog", "fog"));
    }

    @Test
    @Deprecated
    public void testLogestCommonSubsequence_5() {
        assertEquals("", subject.logestCommonSubsequence("fly", "ant"));
    }

    @Test
    @Deprecated
    public void testLogestCommonSubsequence_6() {
        assertEquals("h", subject.logestCommonSubsequence("elephant", "hippo"));
    }

    @Test
    @Deprecated
    public void testLogestCommonSubsequence_7() {
        assertEquals("ABC Corp", subject.logestCommonSubsequence("ABC Corporation", "ABC Corp"));
    }

    @Test
    @Deprecated
    public void testLogestCommonSubsequence_8() {
        assertEquals("D  H Enterprises Inc", subject.logestCommonSubsequence("D N H Enterprises Inc", "D & H Enterprises, Inc."));
    }

    @Test
    @Deprecated
    public void testLogestCommonSubsequence_9() {
        assertEquals("My Gym Childrens Fitness", subject.logestCommonSubsequence("My Gym Children's Fitness Center", "My Gym. Childrens Fitness"));
    }

    @Test
    @Deprecated
    public void testLogestCommonSubsequence_10() {
        assertEquals("PENNSYLVNIA", subject.logestCommonSubsequence("PENNSYLVANIA", "PENNCISYLVNIA"));
    }

    @Test
    @Deprecated
    public void testLogestCommonSubsequence_11() {
        assertEquals("t", subject.logestCommonSubsequence("left", "right"));
    }

    @Test
    @Deprecated
    public void testLogestCommonSubsequence_12() {
        assertEquals("tttt", subject.logestCommonSubsequence("leettteft", "ritttght"));
    }

    @Test
    @Deprecated
    public void testLogestCommonSubsequence_13() {
        assertEquals("the same string", subject.logestCommonSubsequence("the same string", "the same string"));
    }

    @Test
    public void testLongestCommonSubsequence_1() {
        assertEquals("", subject.longestCommonSubsequence("", ""));
    }

    @Test
    public void testLongestCommonSubsequence_2() {
        assertEquals("", subject.longestCommonSubsequence("left", ""));
    }

    @Test
    public void testLongestCommonSubsequence_3() {
        assertEquals("", subject.longestCommonSubsequence("", "right"));
    }

    @Test
    public void testLongestCommonSubsequence_4() {
        assertEquals("fog", subject.longestCommonSubsequence("frog", "fog"));
    }

    @Test
    public void testLongestCommonSubsequence_5() {
        assertEquals("", subject.longestCommonSubsequence("fly", "ant"));
    }

    @Test
    public void testLongestCommonSubsequence_6() {
        assertEquals("h", subject.longestCommonSubsequence("elephant", "hippo"));
    }

    @Test
    public void testLongestCommonSubsequence_7() {
        assertEquals("ABC Corp", subject.longestCommonSubsequence("ABC Corporation", "ABC Corp"));
    }

    @Test
    public void testLongestCommonSubsequence_8() {
        assertEquals("D  H Enterprises Inc", subject.longestCommonSubsequence("D N H Enterprises Inc", "D & H Enterprises, Inc."));
    }

    @Test
    public void testLongestCommonSubsequence_9() {
        assertEquals("My Gym Childrens Fitness", subject.longestCommonSubsequence("My Gym Children's Fitness Center", "My Gym. Childrens Fitness"));
    }

    @Test
    public void testLongestCommonSubsequence_10() {
        assertEquals("PENNSYLVNIA", subject.longestCommonSubsequence("PENNSYLVANIA", "PENNCISYLVNIA"));
    }

    @Test
    public void testLongestCommonSubsequence_11() {
        assertEquals("t", subject.longestCommonSubsequence("left", "right"));
    }

    @Test
    public void testLongestCommonSubsequence_12() {
        assertEquals("tttt", subject.longestCommonSubsequence("leettteft", "ritttght"));
    }

    @Test
    public void testLongestCommonSubsequence_13() {
        assertEquals("the same string", subject.longestCommonSubsequence("the same string", "the same string"));
    }

    @Test
    public void testLongestCommonSubsequenceApply_1() {
        assertEquals(0, subject.apply("", ""));
    }

    @Test
    public void testLongestCommonSubsequenceApply_2() {
        assertEquals(0, subject.apply("left", ""));
    }

    @Test
    public void testLongestCommonSubsequenceApply_3() {
        assertEquals(0, subject.apply("", "right"));
    }

    @Test
    public void testLongestCommonSubsequenceApply_4() {
        assertEquals(3, subject.apply("frog", "fog"));
    }

    @Test
    public void testLongestCommonSubsequenceApply_5() {
        assertEquals(0, subject.apply("fly", "ant"));
    }

    @Test
    public void testLongestCommonSubsequenceApply_6() {
        assertEquals(1, subject.apply("elephant", "hippo"));
    }

    @Test
    public void testLongestCommonSubsequenceApply_7() {
        assertEquals(8, subject.apply("ABC Corporation", "ABC Corp"));
    }

    @Test
    public void testLongestCommonSubsequenceApply_8() {
        assertEquals(20, subject.apply("D N H Enterprises Inc", "D & H Enterprises, Inc."));
    }

    @Test
    public void testLongestCommonSubsequenceApply_9() {
        assertEquals(24, subject.apply("My Gym Children's Fitness Center", "My Gym. Childrens Fitness"));
    }

    @Test
    public void testLongestCommonSubsequenceApply_10() {
        assertEquals(11, subject.apply("PENNSYLVANIA", "PENNCISYLVNIA"));
    }

    @Test
    public void testLongestCommonSubsequenceApply_11() {
        assertEquals(1, subject.apply("left", "right"));
    }

    @Test
    public void testLongestCommonSubsequenceApply_12() {
        assertEquals(4, subject.apply("leettteft", "ritttght"));
    }

    @Test
    public void testLongestCommonSubsequenceApply_13() {
        assertEquals(15, subject.apply("the same string", "the same string"));
    }
}
