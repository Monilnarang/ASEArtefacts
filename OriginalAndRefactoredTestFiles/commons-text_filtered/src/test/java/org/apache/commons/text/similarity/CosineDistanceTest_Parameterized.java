package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class CosineDistanceTest_Parameterized {

    private static CosineDistance cosineDistance;

    @BeforeAll
    public static void setUp() {
        cosineDistance = new CosineDistance();
    }

    private Double roundValue(final Double value) {
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
    }

    @ParameterizedTest
    @MethodSource("Provider_testCosineDistance_1to4")
    public void testCosineDistance_1to4(double param1, String param2, String param3) {
        assertEquals(Double.valueOf(param1), roundValue(cosineDistance.apply(param2, param3)));
    }

    static public Stream<Arguments> Provider_testCosineDistance_1to4() {
        return Stream.of(arguments(0.5d, "the house", "da house"), arguments(0.0d, "AB", "AB"), arguments(1.0d, "AB", "BA"), arguments(0.08d, "the boy was from tamana shi, kumamoto ken, and the girl was from rio de janeiro, rio", "the boy was from tamana shi, kumamoto, and the boy was from rio de janeiro, rio de janeiro"));
    }
}
