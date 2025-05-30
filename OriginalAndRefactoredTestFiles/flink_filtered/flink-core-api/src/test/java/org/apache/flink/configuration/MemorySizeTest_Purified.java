package org.apache.flink.configuration;

import org.apache.flink.core.testutils.CommonTestUtils;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.apache.flink.configuration.MemorySize.MemoryUnit.MEGA_BYTES;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

class MemorySizeTest_Purified {

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
    void testParseBytes_1() {
        assertThat(MemorySize.parseBytes("1234")).isEqualTo(1234);
    }

    @Test
    void testParseBytes_2() {
        assertThat(MemorySize.parseBytes("1234b")).isEqualTo(1234);
    }

    @Test
    void testParseBytes_3() {
        assertThat(MemorySize.parseBytes("1234 b")).isEqualTo(1234);
    }

    @Test
    void testParseBytes_4() {
        assertThat(MemorySize.parseBytes("1234bytes")).isEqualTo(1234);
    }

    @Test
    void testParseBytes_5() {
        assertThat(MemorySize.parseBytes("1234 bytes")).isEqualTo(1234);
    }

    @Test
    void testParseKibiBytes_1() {
        assertThat(MemorySize.parse("667766k").getKibiBytes()).isEqualTo(667766);
    }

    @Test
    void testParseKibiBytes_2() {
        assertThat(MemorySize.parse("667766 k").getKibiBytes()).isEqualTo(667766);
    }

    @Test
    void testParseKibiBytes_3() {
        assertThat(MemorySize.parse("667766kb").getKibiBytes()).isEqualTo(667766);
    }

    @Test
    void testParseKibiBytes_4() {
        assertThat(MemorySize.parse("667766 kb").getKibiBytes()).isEqualTo(667766);
    }

    @Test
    void testParseKibiBytes_5() {
        assertThat(MemorySize.parse("667766kibibytes").getKibiBytes()).isEqualTo(667766);
    }

    @Test
    void testParseKibiBytes_6() {
        assertThat(MemorySize.parse("667766 kibibytes").getKibiBytes()).isEqualTo(667766);
    }

    @Test
    void testParseMebiBytes_1() {
        assertThat(MemorySize.parse("7657623m").getMebiBytes()).isEqualTo(7657623);
    }

    @Test
    void testParseMebiBytes_2() {
        assertThat(MemorySize.parse("7657623 m").getMebiBytes()).isEqualTo(7657623);
    }

    @Test
    void testParseMebiBytes_3() {
        assertThat(MemorySize.parse("7657623mb").getMebiBytes()).isEqualTo(7657623);
    }

    @Test
    void testParseMebiBytes_4() {
        assertThat(MemorySize.parse("7657623 mb").getMebiBytes()).isEqualTo(7657623);
    }

    @Test
    void testParseMebiBytes_5() {
        assertThat(MemorySize.parse("7657623mebibytes").getMebiBytes()).isEqualTo(7657623);
    }

    @Test
    void testParseMebiBytes_6() {
        assertThat(MemorySize.parse("7657623 mebibytes").getMebiBytes()).isEqualTo(7657623);
    }

    @Test
    void testParseGibiBytes_1() {
        assertThat(MemorySize.parse("987654g").getGibiBytes()).isEqualTo(987654);
    }

    @Test
    void testParseGibiBytes_2() {
        assertThat(MemorySize.parse("987654 g").getGibiBytes()).isEqualTo(987654);
    }

    @Test
    void testParseGibiBytes_3() {
        assertThat(MemorySize.parse("987654gb").getGibiBytes()).isEqualTo(987654);
    }

    @Test
    void testParseGibiBytes_4() {
        assertThat(MemorySize.parse("987654 gb").getGibiBytes()).isEqualTo(987654);
    }

    @Test
    void testParseGibiBytes_5() {
        assertThat(MemorySize.parse("987654gibibytes").getGibiBytes()).isEqualTo(987654);
    }

    @Test
    void testParseGibiBytes_6() {
        assertThat(MemorySize.parse("987654 gibibytes").getGibiBytes()).isEqualTo(987654);
    }

    @Test
    void testParseTebiBytes_1() {
        assertThat(MemorySize.parse("1234567t").getTebiBytes()).isEqualTo(1234567);
    }

    @Test
    void testParseTebiBytes_2() {
        assertThat(MemorySize.parse("1234567 t").getTebiBytes()).isEqualTo(1234567);
    }

    @Test
    void testParseTebiBytes_3() {
        assertThat(MemorySize.parse("1234567tb").getTebiBytes()).isEqualTo(1234567);
    }

    @Test
    void testParseTebiBytes_4() {
        assertThat(MemorySize.parse("1234567 tb").getTebiBytes()).isEqualTo(1234567);
    }

    @Test
    void testParseTebiBytes_5() {
        assertThat(MemorySize.parse("1234567tebibytes").getTebiBytes()).isEqualTo(1234567);
    }

    @Test
    void testParseTebiBytes_6() {
        assertThat(MemorySize.parse("1234567 tebibytes").getTebiBytes()).isEqualTo(1234567);
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
    void testTrimBeforeParse_1() {
        assertThat(MemorySize.parseBytes("      155      ")).isEqualTo(155L);
    }

    @Test
    void testTrimBeforeParse_2() {
        assertThat(MemorySize.parseBytes("      155      bytes   ")).isEqualTo(155L);
    }

    @Test
    void testParseWithDefaultUnit_1() {
        assertThat(MemorySize.parse("7", MEGA_BYTES).getMebiBytes()).isEqualTo(7);
    }

    @Test
    void testParseWithDefaultUnit_2() {
        assertThat(MemorySize.parse("7340032", MEGA_BYTES)).isNotEqualTo(7);
    }

    @Test
    void testParseWithDefaultUnit_3() {
        assertThat(MemorySize.parse("7m", MEGA_BYTES).getMebiBytes()).isEqualTo(7);
    }

    @Test
    void testParseWithDefaultUnit_4() {
        assertThat(MemorySize.parse("7", MEGA_BYTES).getKibiBytes()).isEqualTo(7168);
    }

    @Test
    void testParseWithDefaultUnit_5() {
        assertThat(MemorySize.parse("7m", MEGA_BYTES).getKibiBytes()).isEqualTo(7168);
    }

    @Test
    void testParseWithDefaultUnit_6() {
        assertThat(MemorySize.parse("7 m", MEGA_BYTES).getMebiBytes()).isEqualTo(7);
    }

    @Test
    void testParseWithDefaultUnit_7() {
        assertThat(MemorySize.parse("7mb", MEGA_BYTES).getMebiBytes()).isEqualTo(7);
    }

    @Test
    void testParseWithDefaultUnit_8() {
        assertThat(MemorySize.parse("7 mb", MEGA_BYTES).getMebiBytes()).isEqualTo(7);
    }

    @Test
    void testParseWithDefaultUnit_9() {
        assertThat(MemorySize.parse("7mebibytes", MEGA_BYTES).getMebiBytes()).isEqualTo(7);
    }

    @Test
    void testParseWithDefaultUnit_10() {
        assertThat(MemorySize.parse("7 mebibytes", MEGA_BYTES).getMebiBytes()).isEqualTo(7);
    }

    @Test
    void testToHumanReadableString_1() {
        assertThat(new MemorySize(0L).toHumanReadableString()).isEqualTo("0 bytes");
    }

    @Test
    void testToHumanReadableString_2() {
        assertThat(new MemorySize(1L).toHumanReadableString()).isEqualTo("1 bytes");
    }

    @Test
    void testToHumanReadableString_3() {
        assertThat(new MemorySize(1024L).toHumanReadableString()).isEqualTo("1024 bytes");
    }

    @Test
    void testToHumanReadableString_4() {
        assertThat(new MemorySize(1025L).toHumanReadableString()).isEqualTo("1.001kb (1025 bytes)");
    }

    @Test
    void testToHumanReadableString_5() {
        assertThat(new MemorySize(1536L).toHumanReadableString()).isEqualTo("1.500kb (1536 bytes)");
    }

    @Test
    void testToHumanReadableString_6() {
        assertThat(new MemorySize(1_000_000L).toHumanReadableString()).isEqualTo("976.563kb (1000000 bytes)");
    }

    @Test
    void testToHumanReadableString_7() {
        assertThat(new MemorySize(1_000_000_000L).toHumanReadableString()).isEqualTo("953.674mb (1000000000 bytes)");
    }

    @Test
    void testToHumanReadableString_8() {
        assertThat(new MemorySize(1_000_000_000_000L).toHumanReadableString()).isEqualTo("931.323gb (1000000000000 bytes)");
    }

    @Test
    void testToHumanReadableString_9() {
        assertThat(new MemorySize(1_000_000_000_000_000L).toHumanReadableString()).isEqualTo("909.495tb (1000000000000000 bytes)");
    }
}
