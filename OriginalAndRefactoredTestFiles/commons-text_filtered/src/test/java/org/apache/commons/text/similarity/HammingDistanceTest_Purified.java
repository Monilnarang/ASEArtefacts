package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class HammingDistanceTest_Purified {

    private static HammingDistance distance;

    @BeforeAll
    public static void setUp() {
        distance = new HammingDistance();
    }

    @Test
    public void testHammingDistanceCharSequence_1() {
        assertEquals(0, distance.apply("", ""));
    }

    @Test
    public void testHammingDistanceCharSequence_2() {
        assertEquals(0, distance.apply("pappa", "pappa"));
    }

    @Test
    public void testHammingDistanceCharSequence_3() {
        assertEquals(1, distance.apply("papaa", "pappa"));
    }

    @Test
    public void testHammingDistanceCharSequence_4() {
        assertEquals(3, distance.apply("karolin", "kathrin"));
    }

    @Test
    public void testHammingDistanceCharSequence_5() {
        assertEquals(3, distance.apply("karolin", "kerstin"));
    }

    @Test
    public void testHammingDistanceCharSequence_6() {
        assertEquals(2, distance.apply("1011101", "1001001"));
    }

    @Test
    public void testHammingDistanceCharSequence_7() {
        assertEquals(3, distance.apply("2173896", "2233796"));
    }

    @Test
    public void testHammingDistanceCharSequence_8() {
        assertEquals(2, distance.apply("ATCG", "ACCC"));
    }
}
