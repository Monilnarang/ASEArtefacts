package com.graphhopper.routing.ev;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class EnumEncodedValueTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_testSize_1to5")
    public void testSize_1to5(int param1, int param2, int param3, int param4) {
        assertEquals(param1, param2 - Integer.numberOfLeadingZeros(param3 - param4));
    }

    static public Stream<Arguments> Provider_testSize_1to5() {
        return Stream.of(arguments(3, 32, 7, 1), arguments(3, 32, 8, 1), arguments(4, 32, 9, 1), arguments(4, 32, 16, 1), arguments(5, 32, 17, 1));
    }
}
