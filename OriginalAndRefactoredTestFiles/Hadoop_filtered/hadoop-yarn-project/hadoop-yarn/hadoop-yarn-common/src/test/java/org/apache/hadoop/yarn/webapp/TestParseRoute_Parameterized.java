package org.apache.hadoop.yarn.webapp;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestParseRoute_Parameterized {

    @Test
    void testTrailingPaddings_1() {
        assertEquals(Arrays.asList("/foo/action", "foo", "action", ":a"), WebApp.parseRoute("/foo/action//:a / "));
    }

    @ParameterizedTest
    @MethodSource("Provider_testDefaultAction_1to2_2")
    void testDefaultAction_1to2_2(String param1, String param2, String param3, String param4) {
        assertEquals(Arrays.asList(param1, param2, param3), WebApp.parseRoute(param4));
    }

    static public Stream<Arguments> Provider_testDefaultAction_1to2_2() {
        return Stream.of(arguments("/foo", "foo", "index", "/foo"), arguments("/foo", "foo", "index", "/foo/"), arguments("/foo/action", "foo", "action", "/foo/action / "));
    }
}
