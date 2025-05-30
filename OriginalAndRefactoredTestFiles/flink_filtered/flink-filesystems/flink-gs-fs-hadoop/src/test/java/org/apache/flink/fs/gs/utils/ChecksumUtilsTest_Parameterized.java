package org.apache.flink.fs.gs.utils;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ChecksumUtilsTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_shouldConvertToStringChecksum_1to2")
    void shouldConvertToStringChecksum_1to2(String param1, int param2) {
        assertThat(ChecksumUtils.convertChecksumToString(param2)).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_shouldConvertToStringChecksum_1to2() {
        return Stream.of(arguments("AAAwOQ==", 12345), arguments("AADUMQ==", 54321));
    }
}
