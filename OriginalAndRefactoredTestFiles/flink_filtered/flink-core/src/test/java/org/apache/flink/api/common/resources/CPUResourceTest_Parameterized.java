package org.apache.flink.api.common.resources;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class CPUResourceTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_toHumanReadableString_1to11")
    void toHumanReadableString_1to11(String param1, int param2) {
        assertThat(new CPUResource(param2).toHumanReadableString()).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_toHumanReadableString_1to11() {
        return Stream.of(arguments("0.00 cores", 0), arguments("1.00 cores", 1), arguments("1.20 cores", 1.2), arguments("1.23 cores", 1.23), arguments("1.23 cores", 1.234), arguments("1.24 cores", 1.235), arguments("10.00 cores", 10), arguments("100.00 cores", 100), arguments("1000.00 cores", 1000), arguments("123456789.00 cores", 123456789), arguments("12345.68 cores", 12345.6789));
    }
}
