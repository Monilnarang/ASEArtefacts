package org.apache.flink.configuration;

import org.junit.jupiter.api.Test;
import java.util.HashSet;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ParentFirstPatternsTest_Parameterized {

    private static final HashSet<String> PARENT_FIRST_PACKAGES = new HashSet<>(CoreOptions.ALWAYS_PARENT_FIRST_LOADER_PATTERNS.defaultValue());

    @ParameterizedTest
    @MethodSource("Provider_testAllCorePatterns_1_1to2_2to3_3to5")
    void testAllCorePatterns_1_1to2_2to3_3to5(String param1) {
        assertThat(PARENT_FIRST_PACKAGES).contains(param1);
    }

    static public Stream<Arguments> Provider_testAllCorePatterns_1_1to2_2to3_3to5() {
        return Stream.of(arguments("java."), arguments("org.apache.flink."), arguments("javax.annotation."), arguments("org.slf4j"), arguments("org.apache.log4j"), arguments("org.apache.logging"), arguments("org.apache.commons.logging"), arguments("ch.qos.logback"));
    }
}
