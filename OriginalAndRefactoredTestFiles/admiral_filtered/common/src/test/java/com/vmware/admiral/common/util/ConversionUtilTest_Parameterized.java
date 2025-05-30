package com.vmware.admiral.common.util;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ConversionUtilTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_testBinaryMemoryToBytes_1_1to2_2to3_3to4_4to5_5to6_6")
    public void testBinaryMemoryToBytes_1_1to2_2to3_3to4_4to5_5to6_6(double param1, double param2, int param3, String param4) throws Exception {
        assertEquals(param1, ConversionUtil.memoryToBytes(param3, param4), param2);
    }

    static public Stream<Arguments> Provider_testBinaryMemoryToBytes_1_1to2_2to3_3to4_4to5_5to6_6() {
        return Stream.of(arguments(2.0, 0.01, 2, "B"), arguments(2048.0, 0.01, 2, "KiB"), arguments(2097152.0, 0.01, 2, "MiB"), arguments(2147483648.0, 0.01, 2, "GiB"), arguments(2199023255552.0, 0.01, 2, "TiB"), arguments(2251799813685248.0, 0.01, 2, "PiB"), arguments(2.0, 0.01, 2, "B"), arguments(2000.0, 0.01, 2, "KB"), arguments(2000000.0, 0.01, 2, "MB"), arguments(2000000000.0, 0.01, 2, "GB"), arguments(2000000000000.0, 0.01, 2, "TB"), arguments(2000000000000000.0, 0.01, 2, "PB"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testBinaryMemory_1_1to2_2to3_3to4_4to5_5to6_6to8")
    public void testBinaryMemory_1_1to2_2to3_3to4_4to5_5to6_6to8(double param1, double param2, int param3, String param4, String param5) throws Exception {
        assertEquals(param1, ConversionUtil.memoryBinaryConversion(param3, param4, param5), param2);
    }

    static public Stream<Arguments> Provider_testBinaryMemory_1_1to2_2to3_3to4_4to5_5to6_6to8() {
        return Stream.of(arguments(2.0, 0.01, 2, "B", "B"), arguments(2048.0, 0.01, 2, "KiB", "B"), arguments(2097152.0, 0.01, 2, "MiB", "B"), arguments(2147483648.0, 0.01, 2, "GiB", "B"), arguments(2199023255552.0, 0.01, 2, "TiB", "B"), arguments(2251799813685248.0, 0.01, 2, "PiB", "B"), arguments(2147483648.0, 0.01, 2.0, "GiB", "B"), arguments(2097152.0, 0.01, 2.0, "GiB", "kiB"), arguments(2048.0, 0.01, 2.0, "GiB", "MiB"), arguments(2.0, 0.01, 2.0, "GiB", "GiB"), arguments(2097152.0, 0.01, 2.0, "MiB", "B"), arguments(2048.0, 0.01, 2.0, "MiB", "kiB"), arguments(2.0, 0.01, 2.0, "MiB", "MiB"), arguments(0.00195, 0.00001, 2.0, "MiB", "GiB"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testDecimalMemory_1to6")
    public void testDecimalMemory_1to6(double param1, double param2, int param3, String param4, String param5) throws Exception {
        assertEquals(param1, ConversionUtil.memoryDecimalConversion(param3, param4, param5), param2);
    }

    static public Stream<Arguments> Provider_testDecimalMemory_1to6() {
        return Stream.of(arguments(2.0, 0.01, 2, "B", "B"), arguments(2000.0, 0.01, 2, "KB", "B"), arguments(2000000.0, 0.01, 2, "MB", "B"), arguments(2000000000.0, 0.01, 2, "GB", "B"), arguments(2000000000000.0, 0.01, 2, "TB", "B"), arguments(2000000000000000.0, 0.01, 2, "PB", "B"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCpuToHertz_1to7")
    public void testCpuToHertz_1to7(int param1, int param2, String param3) throws Exception {
        assertEquals(param1, ConversionUtil.cpuToHertz(param2, param3));
    }

    static public Stream<Arguments> Provider_testCpuToHertz_1to7() {
        return Stream.of(arguments(2, 2, "Hz"), arguments(2000, 2, "KHz"), arguments(2000000, 2, "MHz"), arguments(2000000000L, 2, "GHz"), arguments(2000000000000L, 2, "THz"), arguments(2000000000000000L, 2, "PHz"), arguments(0, 0, "NHz"));
    }
}
