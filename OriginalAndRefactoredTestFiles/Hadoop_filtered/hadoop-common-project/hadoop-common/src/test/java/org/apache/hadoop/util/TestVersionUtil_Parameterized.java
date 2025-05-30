package org.apache.hadoop.util;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestVersionUtil_Parameterized {

    private static void assertExpectedValues(String lower, String higher) {
        assertTrue(VersionUtil.compareVersions(lower, higher) < 0);
        assertTrue(VersionUtil.compareVersions(higher, lower) > 0);
    }

    @ParameterizedTest
    @MethodSource("Provider_testCompareVersions_1to19")
    public void testCompareVersions_1to19(int param1, String param2, String param3) {
        assertEquals(param1, VersionUtil.compareVersions(param2, param3));
    }

    static public Stream<Arguments> Provider_testCompareVersions_1to19() {
        return Stream.of(arguments(0, "2.0.0", "2.0.0"), arguments(0, "2.0.0a", "2.0.0a"), arguments(0, "2.0.0-SNAPSHOT", "2.0.0-SNAPSHOT"), arguments(0, 1, 1), arguments(0, 1, 1.0), arguments(0, 1, "1.0.0"), arguments(0, 1.0, 1), arguments(0, 1.0, 1.0), arguments(0, 1.0, "1.0.0"), arguments(0, "1.0.0", 1), arguments(0, "1.0.0", 1.0), arguments(0, "1.0.0", "1.0.0"), arguments(0, "1.0.0-alpha-1", "1.0.0-a1"), arguments(0, "1.0.0-alpha-2", "1.0.0-a2"), arguments(0, "1.0.0-alpha1", "1.0.0-alpha-1"), arguments(0, "1a0", "1.0.0-alpha-0"), arguments(0, "1a0", "1-a0"), arguments(0, "1.a0", "1-a0"), arguments(0, "1.a0", "1.0.0-alpha-0"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCompareVersions_20to51")
    public void testCompareVersions_20to51(int param1, String param2) {
        assertExpectedValues(param1, param2);
    }

    static public Stream<Arguments> Provider_testCompareVersions_20to51() {
        return Stream.of(arguments(1, "2.0.0"), arguments("1.0.0", 2), arguments("1.0.0", "2.0.0"), arguments(1.0, "2.0.0"), arguments("1.0.0", "2.0.0"), arguments("1.0.0", "1.0.0a"), arguments("1.0.0.0", "2.0.0"), arguments("1.0.0", "1.0.0-dev"), arguments("1.0.0", "1.0.1"), arguments("1.0.0", "1.0.2"), arguments("1.0.0", "1.1.0"), arguments("2.0.0", "10.0.0"), arguments("1.0.0", "1.0.0a"), arguments("1.0.2a", "1.0.10"), arguments("1.0.2a", "1.0.2b"), arguments("1.0.2a", "1.0.2ab"), arguments("1.0.0a1", "1.0.0a2"), arguments("1.0.0a2", "1.0.0a10"), arguments(1.0, "1.a"), arguments("1.a0", 1.0), arguments("1a0", 1.0), arguments("1.0.1-alpha-1", "1.0.1-alpha-2"), arguments("1.0.1-beta-1", "1.0.1-beta-2"), arguments("1.0-SNAPSHOT", 1.0), arguments("1.0.0-SNAPSHOT", 1.0), arguments("1.0.0-SNAPSHOT", "1.0.0"), arguments("1.0.0", "1.0.1-SNAPSHOT"), arguments("1.0.1-SNAPSHOT", "1.0.1"), arguments("1.0.1-SNAPSHOT", "1.0.2"), arguments("1.0.1-alpha-1", "1.0.1-SNAPSHOT"), arguments("1.0.1-beta-1", "1.0.1-SNAPSHOT"), arguments("1.0.1-beta-2", "1.0.1-SNAPSHOT"));
    }
}
