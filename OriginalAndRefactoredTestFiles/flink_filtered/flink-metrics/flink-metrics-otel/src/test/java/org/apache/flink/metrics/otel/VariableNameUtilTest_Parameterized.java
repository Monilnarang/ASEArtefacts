package org.apache.flink.metrics.otel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class VariableNameUtilTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_getVariableName_1to7")
    void getVariableName_1to7(String param1, String param2) {
        Assertions.assertEquals(param1, VariableNameUtil.getVariableName(param2));
    }

    static public Stream<Arguments> Provider_getVariableName_1to7() {
        return Stream.of(arguments("t", "<t>"), arguments("<t", "<t"), arguments("t>", "t>"), arguments("<", "<"), arguments(">", ">"), arguments("", "<>"), arguments("", ""));
    }
}
