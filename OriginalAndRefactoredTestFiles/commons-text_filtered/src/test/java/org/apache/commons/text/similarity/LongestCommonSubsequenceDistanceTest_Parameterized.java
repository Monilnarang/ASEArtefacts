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

public class LongestCommonSubsequenceDistanceTest_Parameterized {

    private static LongestCommonSubsequenceDistance subject;

    @BeforeAll
    public static void setup() {
        subject = new LongestCommonSubsequenceDistance();
    }

    @ParameterizedTest
    @MethodSource("Provider_testGettingLongestCommonSubsequenceDistance_1to13")
    public void testGettingLongestCommonSubsequenceDistance_1to13(int param1, String param2, String param3) {
        assertEquals(param1, subject.apply(param2, param3));
    }

    static public Stream<Arguments> Provider_testGettingLongestCommonSubsequenceDistance_1to13() {
        return Stream.of(arguments(0, "", ""), arguments(4, "left", ""), arguments(5, "", "right"), arguments(1, "frog", "fog"), arguments(6, "fly", "ant"), arguments(11, "elephant", "hippo"), arguments(7, "ABC Corporation", "ABC Corp"), arguments(4, "D N H Enterprises Inc", "D & H Enterprises, Inc."), arguments(9, "My Gym Children's Fitness Center", "My Gym. Childrens Fitness"), arguments(3, "PENNSYLVANIA", "PENNCISYLVNIA"), arguments(7, "left", "right"), arguments(9, "leettteft", "ritttght"), arguments(0, "the same string", "the same string"));
    }
}
