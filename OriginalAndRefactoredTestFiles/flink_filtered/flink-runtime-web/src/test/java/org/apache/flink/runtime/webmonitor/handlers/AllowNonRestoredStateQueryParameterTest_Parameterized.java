package org.apache.flink.runtime.webmonitor.handlers;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class AllowNonRestoredStateQueryParameterTest_Parameterized {

    private final AllowNonRestoredStateQueryParameter allowNonRestoredStateQueryParameter = new AllowNonRestoredStateQueryParameter();

    @ParameterizedTest
    @MethodSource("Provider_testConvertStringToValue_1to2")
    void testConvertStringToValue_1to2(boolean param1) {
        assertThat(allowNonRestoredStateQueryParameter.convertValueToString(false)).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_testConvertStringToValue_1to2() {
        return Stream.of(arguments(false), arguments(true));
    }

    @ParameterizedTest
    @MethodSource("Provider_testConvertValueFromString_1to3")
    void testConvertValueFromString_1to3(boolean param1) {
        assertThat(allowNonRestoredStateQueryParameter.convertStringToValue("false")).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_testConvertValueFromString_1to3() {
        return Stream.of(arguments(false), arguments(true), arguments(true));
    }
}
