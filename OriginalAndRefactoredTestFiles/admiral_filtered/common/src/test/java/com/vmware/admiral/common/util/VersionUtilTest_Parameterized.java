package com.vmware.admiral.common.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class VersionUtilTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_testCompareNumericVersions_1to4")
    public void testCompareNumericVersions_1to4(int param1, String param2, String param3) {
        assertEquals(param1, VersionUtil.compareNumericVersions(param2, param3));
    }

    static public Stream<Arguments> Provider_testCompareNumericVersions_1to4() {
        return Stream.of(arguments(0, "18.01.0", "18.1.0"), arguments(0, "1.2.3", "1.2.3"), arguments(1, "1.2.3", 1.2), arguments(1, "1.2.3", "1.2.1"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCompareNumericVersions_5to6")
    public void testCompareNumericVersions_5to6(int param1, int param2, String param3) {
        assertEquals(-param1, VersionUtil.compareNumericVersions(param2, param3));
    }

    static public Stream<Arguments> Provider_testCompareNumericVersions_5to6() {
        return Stream.of(arguments(1, 1, "1.2.1"), arguments(1, "1.2.0", "1.2.1"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCompareRawVersions_1to4")
    public void testCompareRawVersions_1to4(int param1, String param2, String param3) {
        assertEquals(param1, VersionUtil.compareRawVersions(param2, param3));
    }

    static public Stream<Arguments> Provider_testCompareRawVersions_1to4() {
        return Stream.of(arguments(0, "18.01.0-ce", "v18.1.0"), arguments(0, "v1.2.3", "1.2.3-abcde1234"), arguments(1, "ver1.2.3", 1.2), arguments(1, "version1.2.3", "v1.2.1"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCompareRawVersions_5to6")
    public void testCompareRawVersions_5to6(int param1, String param2, String param3) {
        assertEquals(-param1, VersionUtil.compareRawVersions(param2, param3));
    }

    static public Stream<Arguments> Provider_testCompareRawVersions_5to6() {
        return Stream.of(arguments(1, "rel1", "1.2.1-qe"), arguments(1, "sth1.2.0", "1.2.1sth"));
    }
}
