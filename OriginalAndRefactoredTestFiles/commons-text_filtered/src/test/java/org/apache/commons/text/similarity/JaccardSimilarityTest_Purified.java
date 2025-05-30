package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class JaccardSimilarityTest_Purified {

    private static JaccardSimilarity classBeingTested;

    @BeforeAll
    public static void setUp() {
        classBeingTested = new JaccardSimilarity();
    }

    @Test
    public void testGettingJaccardSimilarityCharSequence_1() {
        assertEquals(1.0, classBeingTested.apply("", ""));
    }

    @Test
    public void testGettingJaccardSimilarityCharSequence_2() {
        assertEquals(0.0, classBeingTested.apply("left", ""));
    }

    @Test
    public void testGettingJaccardSimilarityCharSequence_3() {
        assertEquals(0.0, classBeingTested.apply("", "right"));
    }

    @Test
    public void testGettingJaccardSimilarityCharSequence_4() {
        assertEquals(3.0 / 4, classBeingTested.apply("frog", "fog"));
    }

    @Test
    public void testGettingJaccardSimilarityCharSequence_5() {
        assertEquals(0.0, classBeingTested.apply("fly", "ant"));
    }

    @Test
    public void testGettingJaccardSimilarityCharSequence_6() {
        assertEquals(2.0 / 9, classBeingTested.apply("elephant", "hippo"));
    }

    @Test
    public void testGettingJaccardSimilarityCharSequence_7() {
        assertEquals(7.0 / 11, classBeingTested.apply("ABC Corporation", "ABC Corp"));
    }

    @Test
    public void testGettingJaccardSimilarityCharSequence_8() {
        assertEquals(13.0 / 17, classBeingTested.apply("D N H Enterprises Inc", "D & H Enterprises, Inc."));
    }

    @Test
    public void testGettingJaccardSimilarityCharSequence_9() {
        assertEquals(16.0 / 18, classBeingTested.apply("My Gym Children's Fitness Center", "My Gym. Childrens Fitness"));
    }

    @Test
    public void testGettingJaccardSimilarityCharSequence_10() {
        assertEquals(9.0 / 10, classBeingTested.apply("PENNSYLVANIA", "PENNCISYLVNIA"));
    }

    @Test
    public void testGettingJaccardSimilarityCharSequence_11() {
        assertEquals(1.0 / 8, classBeingTested.apply("left", "right"));
    }

    @Test
    public void testGettingJaccardSimilarityCharSequence_12() {
        assertEquals(1.0 / 8, classBeingTested.apply("leettteft", "ritttght"));
    }

    @Test
    public void testGettingJaccardSimilarityCharSequence_13() {
        assertEquals(1.0, classBeingTested.apply("the same string", "the same string"));
    }
}
