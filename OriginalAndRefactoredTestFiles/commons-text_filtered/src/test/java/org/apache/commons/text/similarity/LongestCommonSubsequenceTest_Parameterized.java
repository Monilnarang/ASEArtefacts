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

public class LongestCommonSubsequenceTest_Parameterized {

    private static LongestCommonSubsequence subject;

    @BeforeAll
    public static void setup() {
        subject = new LongestCommonSubsequence();
    }

    @Deprecated
    @ParameterizedTest
    @MethodSource("Provider_testLogestCommonSubsequence_1to13")
    public void testLogestCommonSubsequence_1to13(String param1, String param2, String param3) {
        assertEquals(param1, subject.logestCommonSubsequence(param2, param3));
    }

    static public Stream<Arguments> Provider_testLogestCommonSubsequence_1to13() {
        return Stream.of(arguments("", "", ""), arguments("", "left", ""), arguments("", "", "right"), arguments("fog", "frog", "fog"), arguments("", "fly", "ant"), arguments("h", "elephant", "hippo"), arguments("ABC Corp", "ABC Corporation", "ABC Corp"), arguments("D  H Enterprises Inc", "D N H Enterprises Inc", "D & H Enterprises, Inc."), arguments("My Gym Childrens Fitness", "My Gym Children's Fitness Center", "My Gym. Childrens Fitness"), arguments("PENNSYLVNIA", "PENNSYLVANIA", "PENNCISYLVNIA"), arguments("t", "left", "right"), arguments("tttt", "leettteft", "ritttght"), arguments("the same string", "the same string", "the same string"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLongestCommonSubsequence_1to13")
    public void testLongestCommonSubsequence_1to13(String param1, String param2, String param3) {
        assertEquals(param1, subject.longestCommonSubsequence(param2, param3));
    }

    static public Stream<Arguments> Provider_testLongestCommonSubsequence_1to13() {
        return Stream.of(arguments("", "", ""), arguments("", "left", ""), arguments("", "", "right"), arguments("fog", "frog", "fog"), arguments("", "fly", "ant"), arguments("h", "elephant", "hippo"), arguments("ABC Corp", "ABC Corporation", "ABC Corp"), arguments("D  H Enterprises Inc", "D N H Enterprises Inc", "D & H Enterprises, Inc."), arguments("My Gym Childrens Fitness", "My Gym Children's Fitness Center", "My Gym. Childrens Fitness"), arguments("PENNSYLVNIA", "PENNSYLVANIA", "PENNCISYLVNIA"), arguments("t", "left", "right"), arguments("tttt", "leettteft", "ritttght"), arguments("the same string", "the same string", "the same string"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLongestCommonSubsequenceApply_1to13")
    public void testLongestCommonSubsequenceApply_1to13(int param1, String param2, String param3) {
        assertEquals(param1, subject.apply(param2, param3));
    }

    static public Stream<Arguments> Provider_testLongestCommonSubsequenceApply_1to13() {
        return Stream.of(arguments(0, "", ""), arguments(0, "left", ""), arguments(0, "", "right"), arguments(3, "frog", "fog"), arguments(0, "fly", "ant"), arguments(1, "elephant", "hippo"), arguments(8, "ABC Corporation", "ABC Corp"), arguments(20, "D N H Enterprises Inc", "D & H Enterprises, Inc."), arguments(24, "My Gym Children's Fitness Center", "My Gym. Childrens Fitness"), arguments(11, "PENNSYLVANIA", "PENNCISYLVNIA"), arguments(1, "left", "right"), arguments(4, "leettteft", "ritttght"), arguments(15, "the same string", "the same string"));
    }
}
