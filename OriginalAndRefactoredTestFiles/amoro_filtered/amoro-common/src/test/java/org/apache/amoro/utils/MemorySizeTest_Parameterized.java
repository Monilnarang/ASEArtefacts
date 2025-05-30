package org.apache.amoro.utils;

import static org.apache.amoro.utils.MemorySize.MemoryUnit.MEGA_BYTES;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class MemorySizeTest_Parameterized {

    @Test
    public void testUnitConversion_1_testMerged_1() {
        final MemorySize zero = MemorySize.ZERO;
        assertEquals(0, zero.getBytes());
        assertEquals(0, zero.getKibiBytes());
        assertEquals(0, zero.getMebiBytes());
        assertEquals(0, zero.getGibiBytes());
        assertEquals(0, zero.getTebiBytes());
    }

    @Test
    public void testUnitConversion_21_testMerged_2() {
        final MemorySize teras = new MemorySize(2L * 1024 * 1024 * 1024 * 1024 + 10);
        assertEquals(2199023255562L, teras.getBytes());
        assertEquals(2147483648L, teras.getKibiBytes());
        assertEquals(2097152, teras.getMebiBytes());
        assertEquals(2048, teras.getGibiBytes());
        assertEquals(2, teras.getTebiBytes());
    }

    @Test
    public void testUnitConversion_6_testMerged_3() {
        final MemorySize bytes = new MemorySize(955);
        assertEquals(955, bytes.getBytes());
        assertEquals(0, bytes.getKibiBytes());
        assertEquals(0, bytes.getMebiBytes());
        assertEquals(0, bytes.getGibiBytes());
        assertEquals(0, bytes.getTebiBytes());
    }

    @Test
    public void testUnitConversion_11_testMerged_4() {
        final MemorySize kilos = new MemorySize(18500);
        assertEquals(18500, kilos.getBytes());
        assertEquals(18, kilos.getKibiBytes());
        assertEquals(0, kilos.getMebiBytes());
        assertEquals(0, kilos.getGibiBytes());
        assertEquals(0, kilos.getTebiBytes());
    }

    @Test
    public void testUnitConversion_16_testMerged_5() {
        final MemorySize megas = new MemorySize(15 * 1024 * 1024);
        assertEquals(15_728_640, megas.getBytes());
        assertEquals(15_360, megas.getKibiBytes());
        assertEquals(15, megas.getMebiBytes());
        assertEquals(0, megas.getGibiBytes());
        assertEquals(0, megas.getTebiBytes());
    }

    @Test
    public void testUpperCase_1() {
        assertEquals(1L, MemorySize.parse("1 B").getBytes());
    }

    @Test
    public void testParseWithDefaultUnit_2() {
        assertNotEquals(7, MemorySize.parse("7340032", MEGA_BYTES));
    }

    @ParameterizedTest
    @MethodSource("Provider_testParseBytes_1_1to2_2to5")
    public void testParseBytes_1_1to2_2to5(int param1, int param2) {
        assertEquals(param1, MemorySize.parseBytes(param2));
    }

    static public Stream<Arguments> Provider_testParseBytes_1_1to2_2to5() {
        return Stream.of(arguments(1234, 1234), arguments(1234, "1234b"), arguments(1234, "1234 b"), arguments(1234, "1234bytes"), arguments(1234, "1234 bytes"), arguments(155L,       155      ), arguments(155L, "      155      bytes   "));
    }

    @ParameterizedTest
    @MethodSource("Provider_testParseKibiBytes_1to2_2to6")
    public void testParseKibiBytes_1to2_2to6(int param1, String param2) {
        assertEquals(param1, MemorySize.parse(param2).getKibiBytes());
    }

    static public Stream<Arguments> Provider_testParseKibiBytes_1to2_2to6() {
        return Stream.of(arguments(667766, "667766k"), arguments(667766, "667766 k"), arguments(667766, "667766kb"), arguments(667766, "667766 kb"), arguments(667766, "667766kibibytes"), arguments(667766, "667766 kibibytes"), arguments(1L, "1 K"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testParseMebiBytes_1to3_3to6")
    public void testParseMebiBytes_1to3_3to6(int param1, String param2) {
        assertEquals(param1, MemorySize.parse(param2).getMebiBytes());
    }

    static public Stream<Arguments> Provider_testParseMebiBytes_1to3_3to6() {
        return Stream.of(arguments(7657623, "7657623m"), arguments(7657623, "7657623 m"), arguments(7657623, "7657623mb"), arguments(7657623, "7657623 mb"), arguments(7657623, "7657623mebibytes"), arguments(7657623, "7657623 mebibytes"), arguments(1L, "1 M"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testParseGibiBytes_1to4_4to6")
    public void testParseGibiBytes_1to4_4to6(int param1, String param2) {
        assertEquals(param1, MemorySize.parse(param2).getGibiBytes());
    }

    static public Stream<Arguments> Provider_testParseGibiBytes_1to4_4to6() {
        return Stream.of(arguments(987654, "987654g"), arguments(987654, "987654 g"), arguments(987654, "987654gb"), arguments(987654, "987654 gb"), arguments(987654, "987654gibibytes"), arguments(987654, "987654 gibibytes"), arguments(1L, "1 G"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testParseTebiBytes_1to5_5to6")
    public void testParseTebiBytes_1to5_5to6(int param1, String param2) {
        assertEquals(param1, MemorySize.parse(param2).getTebiBytes());
    }

    static public Stream<Arguments> Provider_testParseTebiBytes_1to5_5to6() {
        return Stream.of(arguments(1234567, "1234567t"), arguments(1234567, "1234567 t"), arguments(1234567, "1234567tb"), arguments(1234567, "1234567 tb"), arguments(1234567, "1234567tebibytes"), arguments(1234567, "1234567 tebibytes"), arguments(1L, "1 T"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testParseWithDefaultUnit_1_3_6to10")
    public void testParseWithDefaultUnit_1_3_6to10(int param1, int param2) {
        assertEquals(param1, MemorySize.parse(param2, MEGA_BYTES).getMebiBytes());
    }

    static public Stream<Arguments> Provider_testParseWithDefaultUnit_1_3_6to10() {
        return Stream.of(arguments(7, 7), arguments(7, "7m"), arguments(7, "7 m"), arguments(7, "7mb"), arguments(7, "7 mb"), arguments(7, "7mebibytes"), arguments(7, "7 mebibytes"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testParseWithDefaultUnit_4to5")
    public void testParseWithDefaultUnit_4to5(int param1, int param2) {
        assertEquals(param1, MemorySize.parse(param2, MEGA_BYTES).getKibiBytes());
    }

    static public Stream<Arguments> Provider_testParseWithDefaultUnit_4to5() {
        return Stream.of(arguments(7168, 7), arguments(7168, "7m"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testToHumanReadableString_1to9")
    public void testToHumanReadableString_1to9(String param1, long param2) {
        assertThat(new MemorySize(param2).toHumanReadableString(), is(param1));
    }

    static public Stream<Arguments> Provider_testToHumanReadableString_1to9() {
        return Stream.of(arguments("0 bytes", 0L), arguments("1 bytes", 1L), arguments("1024 bytes", 1024L), arguments("1.001kb (1025 bytes)", 1025L), arguments("1.500kb (1536 bytes)", 1536L), arguments("976.563kb (1000000 bytes)", "1_000_000L"), arguments("953.674mb (1000000000 bytes)", "1_000_000_000L"), arguments("931.323gb (1000000000000 bytes)", "1_000_000_000_000L"), arguments("909.495tb (1000000000000000 bytes)", "1_000_000_000_000_000L"));
    }
}
