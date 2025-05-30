package org.jline.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ColorsTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_testRounding_1to7")
    public void testRounding_1to7(int param1, int param2, int param3, String param4) {
        assertEquals(param1, Colors.roundColor(param2, param3, param4));
    }

    static public Stream<Arguments> Provider_testRounding_1to7() {
        return Stream.of(arguments(2, 71, 16, "cam02"), arguments(2, 71, 16, "camlab(1,2)"), arguments(8, 71, 16, "rgb"), arguments(8, 71, 16, "rgb(2,4,3)"), arguments(2, 71, 16, "cie76"), arguments(2, 71, 16, "cie94"), arguments(2, 71, 16, "cie00"));
    }
}
