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

public class JaccardSimilarityTest_Parameterized {

    private static JaccardSimilarity classBeingTested;

    @BeforeAll
    public static void setUp() {
        classBeingTested = new JaccardSimilarity();
    }

    @ParameterizedTest
    @MethodSource("Provider_testGettingJaccardSimilarityCharSequence_1to3_5_13")
    public void testGettingJaccardSimilarityCharSequence_1to3_5_13(double param1, String param2, String param3) {
        assertEquals(param1, classBeingTested.apply(param2, param3));
    }

    static public Stream<Arguments> Provider_testGettingJaccardSimilarityCharSequence_1to3_5_13() {
        return Stream.of(arguments(1.0, "", ""), arguments(0.0, "left", ""), arguments(0.0, "", "right"), arguments(0.0, "fly", "ant"), arguments(1.0, "the same string", "the same string"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGettingJaccardSimilarityCharSequence_4_6to12")
    public void testGettingJaccardSimilarityCharSequence_4_6to12(double param1, int param2, String param3, String param4) {
        assertEquals(param1 / param2, classBeingTested.apply(param3, param4));
    }

    static public Stream<Arguments> Provider_testGettingJaccardSimilarityCharSequence_4_6to12() {
        return Stream.of(arguments(3.0, 4, "frog", "fog"), arguments(2.0, 9, "elephant", "hippo"), arguments(7.0, 11, "ABC Corporation", "ABC Corp"), arguments(13.0, 17, "D N H Enterprises Inc", "D & H Enterprises, Inc."), arguments(16.0, 18, "My Gym Children's Fitness Center", "My Gym. Childrens Fitness"), arguments(9.0, 10, "PENNSYLVANIA", "PENNCISYLVNIA"), arguments(1.0, 8, "left", "right"), arguments(1.0, 8, "leettteft", "ritttght"));
    }
}
