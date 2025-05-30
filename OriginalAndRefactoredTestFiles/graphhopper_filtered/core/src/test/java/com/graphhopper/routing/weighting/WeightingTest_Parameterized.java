package com.graphhopper.routing.weighting;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class WeightingTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_testToString_1to3")
    public void testToString_1to3(String param1) {
        assertTrue(Weighting.isValidName(param1));
    }

    static public Stream<Arguments> Provider_testToString_1to3() {
        return Stream.of(arguments("blup"), arguments("blup_a"), arguments("blup|a"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testToString_4to5")
    public void testToString_4to5(String param1) {
        assertFalse(Weighting.isValidName(param1));
    }

    static public Stream<Arguments> Provider_testToString_4to5() {
        return Stream.of(arguments("Blup"), arguments("Blup!"));
    }
}
