package org.apache.hadoop.lib.util;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.hadoop.test.HTestCase;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestCheck_Parameterized extends HTestCase {

    @ParameterizedTest
    @MethodSource("Provider_validIdentifierValid_1to4")
    public void validIdentifierValid_1to4(String param1, String param2, int param3, String param4) throws Exception {
        assertEquals(Check.validIdentifier(param2, param3, param4), param1);
    }

    static public Stream<Arguments> Provider_validIdentifierValid_1to4() {
        return Stream.of(arguments("a", "a", 1, ""), arguments("a1", "a1", 2, ""), arguments("a_", "a_", 3, ""), arguments("_", "_", 1, ""));
    }

    @ParameterizedTest
    @MethodSource("Provider_checkGEZero_1to2")
    public void checkGEZero_1to2(int param1, int param2, String param3) {
        assertEquals(Check.ge0(param2, param3), param1);
    }

    static public Stream<Arguments> Provider_checkGEZero_1to2() {
        return Stream.of(arguments(120, 120, "test"), arguments(0, 0, "test"));
    }
}
