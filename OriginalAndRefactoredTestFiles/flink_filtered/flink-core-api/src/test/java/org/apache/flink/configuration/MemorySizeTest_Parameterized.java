package org.apache.flink.configuration;

import org.apache.flink.core.testutils.CommonTestUtils;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.apache.flink.configuration.MemorySize.MemoryUnit.MEGA_BYTES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class MemorySizeTest_Parameterized {

    @Test
    void testUnitConversion_1_testMerged_1() {
        final MemorySize zero = MemorySize.ZERO;
        assertThat(zero.getBytes()).isZero();
        assertThat(zero.getKibiBytes()).isZero();
        assertThat(zero.getMebiBytes()).isZero();
        assertThat(zero.getGibiBytes()).isZero();
        assertThat(zero.getTebiBytes()).isZero();
    }

    @Test
    void testUnitConversion_21_testMerged_2() {
        final MemorySize teras = new MemorySize(2L * 1024 * 1024 * 1024 * 1024 + 10);
        assertThat(teras.getBytes()).isEqualTo(2199023255562L);
        assertThat(teras.getKibiBytes()).isEqualTo(2147483648L);
        assertThat(teras.getMebiBytes()).isEqualTo(2097152);
        assertThat(teras.getGibiBytes()).isEqualTo(2048);
        assertThat(teras.getTebiBytes()).isEqualTo(2);
    }

    @Test
    void testUnitConversion_6_testMerged_3() {
        final MemorySize bytes = new MemorySize(955);
        assertThat(bytes.getBytes()).isEqualTo(955);
        assertThat(bytes.getKibiBytes()).isZero();
        assertThat(bytes.getMebiBytes()).isZero();
        assertThat(bytes.getGibiBytes()).isZero();
        assertThat(bytes.getTebiBytes()).isZero();
    }

    @Test
    void testUnitConversion_11_testMerged_4() {
        final MemorySize kilos = new MemorySize(18500);
        assertThat(kilos.getBytes()).isEqualTo(18500);
        assertThat(kilos.getKibiBytes()).isEqualTo(18);
        assertThat(kilos.getMebiBytes()).isZero();
        assertThat(kilos.getGibiBytes()).isZero();
        assertThat(kilos.getTebiBytes()).isZero();
    }

    @Test
    void testUnitConversion_16_testMerged_5() {
        final MemorySize megas = new MemorySize(15 * 1024 * 1024);
        assertThat(megas.getBytes()).isEqualTo(15_728_640);
        assertThat(megas.getKibiBytes()).isEqualTo(15_360);
        assertThat(megas.getMebiBytes()).isEqualTo(15);
        assertThat(megas.getGibiBytes()).isZero();
        assertThat(megas.getTebiBytes()).isZero();
    }

    @Test
    void testUpperCase_1() {
        assertThat(MemorySize.parse("1 B").getBytes()).isOne();
    }

    @Test
    void testUpperCase_2() {
        assertThat(MemorySize.parse("1 K").getKibiBytes()).isOne();
    }

    @Test
    void testUpperCase_3() {
        assertThat(MemorySize.parse("1 M").getMebiBytes()).isOne();
    }

    @Test
    void testUpperCase_4() {
        assertThat(MemorySize.parse("1 G").getGibiBytes()).isOne();
    }

    @Test
    void testUpperCase_5() {
        assertThat(MemorySize.parse("1 T").getTebiBytes()).isOne();
    }

    @Test
    void testParseWithDefaultUnit_2() {
        assertThat(MemorySize.parse("7340032", MEGA_BYTES)).isNotEqualTo(7);
    }

    @ParameterizedTest
    @MethodSource("Provider_testParseBytes_1_1to2_2to5")
    void testParseBytes_1_1to2_2to5(int param1, int param2) {
        assertThat(MemorySize.parseBytes(param2)).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_testParseBytes_1_1to2_2to5() {
        return Stream.of(arguments(1234, 1234), arguments(1234, "1234b"), arguments(1234, "1234 b"), arguments(1234, "1234bytes"), arguments(1234, "1234 bytes"), arguments(155L,       155      ), arguments(155L, "      155      bytes   "));
    }

    @ParameterizedTest
    @MethodSource("Provider_testParseKibiBytes_1to6")
    void testParseKibiBytes_1to6(int param1, String param2) {
        assertThat(MemorySize.parse(param2).getKibiBytes()).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_testParseKibiBytes_1to6() {
        return Stream.of(arguments(667766, "667766k"), arguments(667766, "667766 k"), arguments(667766, "667766kb"), arguments(667766, "667766 kb"), arguments(667766, "667766kibibytes"), arguments(667766, "667766 kibibytes"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testParseMebiBytes_1to6")
    void testParseMebiBytes_1to6(int param1, String param2) {
        assertThat(MemorySize.parse(param2).getMebiBytes()).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_testParseMebiBytes_1to6() {
        return Stream.of(arguments(7657623, "7657623m"), arguments(7657623, "7657623 m"), arguments(7657623, "7657623mb"), arguments(7657623, "7657623 mb"), arguments(7657623, "7657623mebibytes"), arguments(7657623, "7657623 mebibytes"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testParseGibiBytes_1to6")
    void testParseGibiBytes_1to6(int param1, String param2) {
        assertThat(MemorySize.parse(param2).getGibiBytes()).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_testParseGibiBytes_1to6() {
        return Stream.of(arguments(987654, "987654g"), arguments(987654, "987654 g"), arguments(987654, "987654gb"), arguments(987654, "987654 gb"), arguments(987654, "987654gibibytes"), arguments(987654, "987654 gibibytes"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testParseTebiBytes_1to6")
    void testParseTebiBytes_1to6(int param1, String param2) {
        assertThat(MemorySize.parse(param2).getTebiBytes()).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_testParseTebiBytes_1to6() {
        return Stream.of(arguments(1234567, "1234567t"), arguments(1234567, "1234567 t"), arguments(1234567, "1234567tb"), arguments(1234567, "1234567 tb"), arguments(1234567, "1234567tebibytes"), arguments(1234567, "1234567 tebibytes"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testParseWithDefaultUnit_1_3_6to10")
    void testParseWithDefaultUnit_1_3_6to10(int param1, int param2) {
        assertThat(MemorySize.parse(param2, MEGA_BYTES).getMebiBytes()).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_testParseWithDefaultUnit_1_3_6to10() {
        return Stream.of(arguments(7, 7), arguments(7, "7m"), arguments(7, "7 m"), arguments(7, "7mb"), arguments(7, "7 mb"), arguments(7, "7mebibytes"), arguments(7, "7 mebibytes"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testParseWithDefaultUnit_4to5")
    void testParseWithDefaultUnit_4to5(int param1, int param2) {
        assertThat(MemorySize.parse(param2, MEGA_BYTES).getKibiBytes()).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_testParseWithDefaultUnit_4to5() {
        return Stream.of(arguments(7168, 7), arguments(7168, "7m"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testToHumanReadableString_1to9")
    void testToHumanReadableString_1to9(String param1, long param2) {
        assertThat(new MemorySize(param2).toHumanReadableString()).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_testToHumanReadableString_1to9() {
        return Stream.of(arguments("0 bytes", 0L), arguments("1 bytes", 1L), arguments("1024 bytes", 1024L), arguments("1.001kb (1025 bytes)", 1025L), arguments("1.500kb (1536 bytes)", 1536L), arguments("976.563kb (1000000 bytes)", "1_000_000L"), arguments("953.674mb (1000000000 bytes)", "1_000_000_000L"), arguments("931.323gb (1000000000000 bytes)", "1_000_000_000_000L"), arguments("909.495tb (1000000000000000 bytes)", "1_000_000_000_000_000L"));
    }
}
