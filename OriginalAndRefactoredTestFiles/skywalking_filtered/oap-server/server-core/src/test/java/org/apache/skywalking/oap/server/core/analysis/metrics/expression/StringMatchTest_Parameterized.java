package org.apache.skywalking.oap.server.core.analysis.metrics.expression;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class StringMatchTest_Parameterized {

    @Test
    public void stringShouldEqual_3() {
        assertFalse(new StringMatch().match("\"a\"", "ab"));
    }

    @ParameterizedTest
    @MethodSource("Provider_stringShouldEqual_1to2")
    public void stringShouldEqual_1to2(String param1, String param2) {
        assertTrue(new StringMatch().match(param1, param2));
    }

    static public Stream<Arguments> Provider_stringShouldEqual_1to2() {
        return Stream.of(arguments("\"a\"", "a"), arguments("a", "a"));
    }
}
