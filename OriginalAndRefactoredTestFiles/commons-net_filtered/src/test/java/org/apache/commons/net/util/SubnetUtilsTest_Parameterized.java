package org.apache.commons.net.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.net.util.SubnetUtils.SubnetInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.FieldSource;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@SuppressWarnings("deprecation")
public class SubnetUtilsTest_Parameterized {

    private static final List<ImmutablePair<String, Long>> CIDR_SIZES = Arrays.asList(ImmutablePair.of("192.168.0.1/8", 16777216L), ImmutablePair.of("192.168.0.1/9", 8388608L), ImmutablePair.of("192.168.0.1/10", 4194304L), ImmutablePair.of("192.168.0.1/11", 2097152L), ImmutablePair.of("192.168.0.1/12", 1048576L), ImmutablePair.of("192.168.0.1/13", 524288L), ImmutablePair.of("192.168.0.1/14", 262144L), ImmutablePair.of("192.168.0.1/15", 131072L), ImmutablePair.of("192.168.0.1/16", 65536L), ImmutablePair.of("192.168.0.1/17", 32768L), ImmutablePair.of("192.168.0.1/18", 16384L), ImmutablePair.of("192.168.0.1/19", 8192L), ImmutablePair.of("192.168.0.1/20", 4096L), ImmutablePair.of("192.168.0.1/21", 2048L), ImmutablePair.of("192.168.0.1/22", 1024L), ImmutablePair.of("192.168.0.1/23", 512L), ImmutablePair.of("192.168.0.1/24", 256L), ImmutablePair.of("192.168.0.1/25", 128L), ImmutablePair.of("192.168.0.1/26", 64L), ImmutablePair.of("192.168.0.1/27", 32L), ImmutablePair.of("192.168.0.1/28", 16L), ImmutablePair.of("192.168.0.1/29", 8L), ImmutablePair.of("192.168.0.1/30", 4L), ImmutablePair.of("192.168.0.1/31", 2L), ImmutablePair.of("192.168.0.1/32", 1L));

    static Stream<Arguments> provideValueSetsTestNET641() {
        return Stream.of(Arguments.of("192.168.1.0/00", "0.0.0.0"), Arguments.of("192.168.1.0/30", "0.0.0.0"), Arguments.of("192.168.1.0/31", "0.0.0.0"), Arguments.of("192.168.1.0/32", "0.0.0.0"));
    }

    @ParameterizedTest
    @MethodSource("Provider_test1_1to4")
    public void test1_1to4(String param1, String param2) {
        assertFalse(new SubnetUtils(param2).getInfo().isInRange(param1));
    }

    static public Stream<Arguments> Provider_test1_1to4() {
        return Stream.of(arguments("0.0.0.0", "192.168.1.0/00"), arguments("0.0.0.0", "192.168.1.0/30"), arguments("0.0.0.0", "192.168.1.0/31"), arguments("0.0.0.0", "192.168.1.0/32"));
    }
}
