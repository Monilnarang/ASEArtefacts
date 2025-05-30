package org.apache.skywalking.oap.server.core.analysis.metrics.expression;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class LikeMatchTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_testLike_1to3_6")
    public void testLike_1to3_6(String param1, String param2) {
        assertTrue(new LikeMatch().match(param1, param2));
    }

    static public Stream<Arguments> Provider_testLike_1to3_6() {
        return Stream.of(arguments("MaxBlack", "%Black"), arguments("MaxBlack", "Max%"), arguments("MaxBlack", "%axBl%"), arguments("MaxBlack", "\"%Black\""));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLike_4to5_7")
    public void testLike_4to5_7(String param1, String param2) {
        assertFalse(new LikeMatch().match(param1, param2));
    }

    static public Stream<Arguments> Provider_testLike_4to5_7() {
        return Stream.of(arguments("CarolineChanning", "Max%"), arguments("CarolineChanning", "%Max"), arguments("CarolineChanning", "\"Max%\""));
    }
}
