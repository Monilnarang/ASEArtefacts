package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class HammingDistanceTest_Parameterized {

    private static HammingDistance distance;

    @BeforeAll
    public static void setUp() {
        distance = new HammingDistance();
    }

    @ParameterizedTest
    @MethodSource("Provider_testHammingDistanceCharSequence_1to8")
    public void testHammingDistanceCharSequence_1to8(int param1, String param2, String param3) {
        assertEquals(param1, distance.apply(param2, param3));
    }

    static public Stream<Arguments> Provider_testHammingDistanceCharSequence_1to8() {
        return Stream.of(arguments(0, "", ""), arguments(0, "pappa", "pappa"), arguments(1, "papaa", "pappa"), arguments(3, "karolin", "kathrin"), arguments(3, "karolin", "kerstin"), arguments(2, 1011101, 1001001), arguments(3, 2173896, 2233796), arguments(2, "ATCG", "ACCC"));
    }
}
