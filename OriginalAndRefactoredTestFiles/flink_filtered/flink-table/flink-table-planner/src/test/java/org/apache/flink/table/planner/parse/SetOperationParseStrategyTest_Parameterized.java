package org.apache.flink.table.planner.parse;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class SetOperationParseStrategyTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_testMatches_1to6")
    void testMatches_1to6(String param1) {
        assertThat(SetOperationParseStrategy.INSTANCE.match(param1)).isTrue();
    }

    static public Stream<Arguments> Provider_testMatches_1to6() {
        return Stream.of(arguments("SET"), arguments("SET table.local-time-zone = Europe/Berlin"), arguments("SET table.local-time-zone = 'Europe/Berlin'"), arguments("SET;"), arguments("SET table.local-time-zone = Europe/Berlin;"), arguments("SET table.local-time-zone = 'Europe/Berlin';"));
    }
}
