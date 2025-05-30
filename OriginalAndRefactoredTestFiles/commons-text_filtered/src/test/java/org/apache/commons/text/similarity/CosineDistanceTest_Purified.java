package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CosineDistanceTest_Purified {

    private static CosineDistance cosineDistance;

    @BeforeAll
    public static void setUp() {
        cosineDistance = new CosineDistance();
    }

    private Double roundValue(final Double value) {
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    @Test
    public void testCosineDistance_1() {
        assertEquals(Double.valueOf(0.5d), roundValue(cosineDistance.apply("the house", "da house")));
    }

    @Test
    public void testCosineDistance_2() {
        assertEquals(Double.valueOf(0.0d), roundValue(cosineDistance.apply("AB", "AB")));
    }

    @Test
    public void testCosineDistance_3() {
        assertEquals(Double.valueOf(1.0d), roundValue(cosineDistance.apply("AB", "BA")));
    }

    @Test
    public void testCosineDistance_4() {
        assertEquals(Double.valueOf(0.08d), roundValue(cosineDistance.apply("the boy was from tamana shi, kumamoto ken, and the girl was from rio de janeiro, rio", "the boy was from tamana shi, kumamoto, and the boy was from rio de janeiro, rio de janeiro")));
    }
}
