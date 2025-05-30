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

public class JaccardDistanceTest_Parameterized {

    private static JaccardDistance classBeingTested;

    @BeforeAll
    public static void setUp() {
        classBeingTested = new JaccardDistance();
    }

    @ParameterizedTest
    @MethodSource("Provider_testGettingJaccardDistanceCharSequence_1to3_5_13")
    public void testGettingJaccardDistanceCharSequence_1to3_5_13(double param1, String param2, String param3) {
        assertEquals(param1, classBeingTested.apply(param2, param3));
    }

    static public Stream<Arguments> Provider_testGettingJaccardDistanceCharSequence_1to3_5_13() {
        return Stream.of(arguments(0.0, "", ""), arguments(1.0, "left", ""), arguments(1.0, "", "right"), arguments(1.0, "fly", "ant"), arguments(0.0, "the same string", "the same string"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGettingJaccardDistanceCharSequence_4_6to12")
    public void testGettingJaccardDistanceCharSequence_4_6to12(double param1, String param2, String param3, double param4, int param5) {
        assertEquals(param1 - param4 / param5, classBeingTested.apply(param2, param3));
    }

    static public Stream<Arguments> Provider_testGettingJaccardDistanceCharSequence_4_6to12() {
        return Stream.of(arguments(1.0, "frog", "fog", 3.0, 4), arguments(1.0, "elephant", "hippo", 2.0, 9), arguments(1.0, "ABC Corporation", "ABC Corp", 7.0, 11), arguments(1.0, "D N H Enterprises Inc", "D & H Enterprises, Inc.", 13.0, 17), arguments(1.0, "My Gym Children's Fitness Center", "My Gym. Childrens Fitness", 16.0, 18), arguments(1.0, "PENNSYLVANIA", "PENNCISYLVNIA", 9.0, 10), arguments(1.0, "left", "right", 1.0, 8), arguments(1.0, "leettteft", "ritttght", 1.0, 8));
    }
}
