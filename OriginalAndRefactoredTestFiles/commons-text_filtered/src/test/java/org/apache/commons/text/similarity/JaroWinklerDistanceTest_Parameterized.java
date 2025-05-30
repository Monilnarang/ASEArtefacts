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

public class JaroWinklerDistanceTest_Parameterized {

    private static JaroWinklerDistance distance;

    @BeforeAll
    public static void setUp() {
        distance = new JaroWinklerDistance();
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetJaroWinklerDistance_StringString_1to12")
    public void testGetJaroWinklerDistance_StringString_1to12(double param1, double param2, String param3, String param4) {
        assertEquals(param1, distance.apply(param3, param4), param2);
    }

    static public Stream<Arguments> Provider_testGetJaroWinklerDistance_StringString_1to12() {
        return Stream.of(arguments(0.07501d, 0.00001d, "frog", "fog"), arguments(1.0d, 0.00000000000000000001d, "fly", "ant"), arguments(0.55834d, 0.00001d, "elephant", "hippo"), arguments(0.09334d, 0.00001d, "ABC Corporation", "ABC Corp"), arguments(0.04749d, 0.00001d, "D N H Enterprises Inc", "D & H Enterprises, Inc."), arguments(0.058d, 0.00001d, "My Gym Children's Fitness Center", "My Gym. Childrens Fitness"), arguments(0.101982d, 0.00001d, "PENNSYLVANIA", "PENNCISYLVNIA"), arguments(0.028572d, 0.00001d, "/opt/software1", "/opt/software2"), arguments(0.058334d, 0.00001d, "aaabcd", "aaacdb"), arguments(0.088889d, 0.00001d, "John Horn", "John Hopkins"), arguments(0d, 0.00001d, "", ""), arguments(0d, 0.00001d, "foo", "foo"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetJaroWinklerDistance_StringString_13to16")
    public void testGetJaroWinklerDistance_StringString_13to16(double param1, int param2, double param3, String param4, String param5) {
        assertEquals(param2 - param3, distance.apply(param4, param5), param1);
    }

    static public Stream<Arguments> Provider_testGetJaroWinklerDistance_StringString_13to16() {
        return Stream.of(arguments(0.00001d, 1, 0.94166d, "foo", "foo "), arguments(0.00001d, 1, 0.90666d, "foo", "foo  "), arguments(0.00001d, 1, 0.86666d, "foo", " foo "), arguments(0.00001d, 1, 0.51111d, "foo", "  foo"));
    }
}
