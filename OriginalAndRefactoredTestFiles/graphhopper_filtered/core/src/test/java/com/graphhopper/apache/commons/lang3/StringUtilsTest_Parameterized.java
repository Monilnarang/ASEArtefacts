package com.graphhopper.apache.commons.lang3;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class StringUtilsTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_testGetLevenshteinDistance_StringString_1to10")
    public void testGetLevenshteinDistance_StringString_1to10(int param1, String param2, String param3) {
        assertEquals(param1, StringUtils.getLevenshteinDistance(param2, param3));
    }

    static public Stream<Arguments> Provider_testGetLevenshteinDistance_StringString_1to10() {
        return Stream.of(arguments(0, "", ""), arguments(1, "", "a"), arguments(7, "aaapppp", ""), arguments(1, "frog", "fog"), arguments(3, "fly", "ant"), arguments(7, "elephant", "hippo"), arguments(7, "hippo", "elephant"), arguments(8, "hippo", "zzzzzzzz"), arguments(8, "zzzzzzzz", "hippo"), arguments(1, "hello", "hallo"));
    }
}
