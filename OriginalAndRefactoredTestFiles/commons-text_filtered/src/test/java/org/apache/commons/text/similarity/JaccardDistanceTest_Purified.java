package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class JaccardDistanceTest_Purified {

    private static JaccardDistance classBeingTested;

    @BeforeAll
    public static void setUp() {
        classBeingTested = new JaccardDistance();
    }

    @Test
    public void testGettingJaccardDistanceCharSequence_1() {
        assertEquals(0.0, classBeingTested.apply("", ""));
    }

    @Test
    public void testGettingJaccardDistanceCharSequence_2() {
        assertEquals(1.0, classBeingTested.apply("left", ""));
    }

    @Test
    public void testGettingJaccardDistanceCharSequence_3() {
        assertEquals(1.0, classBeingTested.apply("", "right"));
    }

    @Test
    public void testGettingJaccardDistanceCharSequence_4() {
        assertEquals(1.0 - 3.0 / 4, classBeingTested.apply("frog", "fog"));
    }

    @Test
    public void testGettingJaccardDistanceCharSequence_5() {
        assertEquals(1.0, classBeingTested.apply("fly", "ant"));
    }

    @Test
    public void testGettingJaccardDistanceCharSequence_6() {
        assertEquals(1.0 - 2.0 / 9, classBeingTested.apply("elephant", "hippo"));
    }

    @Test
    public void testGettingJaccardDistanceCharSequence_7() {
        assertEquals(1.0 - 7.0 / 11, classBeingTested.apply("ABC Corporation", "ABC Corp"));
    }

    @Test
    public void testGettingJaccardDistanceCharSequence_8() {
        assertEquals(1.0 - 13.0 / 17, classBeingTested.apply("D N H Enterprises Inc", "D & H Enterprises, Inc."));
    }

    @Test
    public void testGettingJaccardDistanceCharSequence_9() {
        assertEquals(1.0 - 16.0 / 18, classBeingTested.apply("My Gym Children's Fitness Center", "My Gym. Childrens Fitness"));
    }

    @Test
    public void testGettingJaccardDistanceCharSequence_10() {
        assertEquals(1.0 - 9.0 / 10, classBeingTested.apply("PENNSYLVANIA", "PENNCISYLVNIA"));
    }

    @Test
    public void testGettingJaccardDistanceCharSequence_11() {
        assertEquals(1.0 - 1.0 / 8, classBeingTested.apply("left", "right"));
    }

    @Test
    public void testGettingJaccardDistanceCharSequence_12() {
        assertEquals(1.0 - 1.0 / 8, classBeingTested.apply("leettteft", "ritttght"));
    }

    @Test
    public void testGettingJaccardDistanceCharSequence_13() {
        assertEquals(0.0, classBeingTested.apply("the same string", "the same string"));
    }
}
