package org.apache.amoro.utils;

import static org.apache.amoro.utils.MemorySize.MemoryUnit.MEGA_BYTES;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import org.junit.Test;

public class MemorySizeTest_Purified {

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
    public void testParseBytes_1() {
        assertEquals(1234, MemorySize.parseBytes("1234"));
    }

    @Test
    public void testParseBytes_2() {
        assertEquals(1234, MemorySize.parseBytes("1234b"));
    }

    @Test
    public void testParseBytes_3() {
        assertEquals(1234, MemorySize.parseBytes("1234 b"));
    }

    @Test
    public void testParseBytes_4() {
        assertEquals(1234, MemorySize.parseBytes("1234bytes"));
    }

    @Test
    public void testParseBytes_5() {
        assertEquals(1234, MemorySize.parseBytes("1234 bytes"));
    }

    @Test
    public void testParseKibiBytes_1() {
        assertEquals(667766, MemorySize.parse("667766k").getKibiBytes());
    }

    @Test
    public void testParseKibiBytes_2() {
        assertEquals(667766, MemorySize.parse("667766 k").getKibiBytes());
    }

    @Test
    public void testParseKibiBytes_3() {
        assertEquals(667766, MemorySize.parse("667766kb").getKibiBytes());
    }

    @Test
    public void testParseKibiBytes_4() {
        assertEquals(667766, MemorySize.parse("667766 kb").getKibiBytes());
    }

    @Test
    public void testParseKibiBytes_5() {
        assertEquals(667766, MemorySize.parse("667766kibibytes").getKibiBytes());
    }

    @Test
    public void testParseKibiBytes_6() {
        assertEquals(667766, MemorySize.parse("667766 kibibytes").getKibiBytes());
    }

    @Test
    public void testParseMebiBytes_1() {
        assertEquals(7657623, MemorySize.parse("7657623m").getMebiBytes());
    }

    @Test
    public void testParseMebiBytes_2() {
        assertEquals(7657623, MemorySize.parse("7657623 m").getMebiBytes());
    }

    @Test
    public void testParseMebiBytes_3() {
        assertEquals(7657623, MemorySize.parse("7657623mb").getMebiBytes());
    }

    @Test
    public void testParseMebiBytes_4() {
        assertEquals(7657623, MemorySize.parse("7657623 mb").getMebiBytes());
    }

    @Test
    public void testParseMebiBytes_5() {
        assertEquals(7657623, MemorySize.parse("7657623mebibytes").getMebiBytes());
    }

    @Test
    public void testParseMebiBytes_6() {
        assertEquals(7657623, MemorySize.parse("7657623 mebibytes").getMebiBytes());
    }

    @Test
    public void testParseGibiBytes_1() {
        assertEquals(987654, MemorySize.parse("987654g").getGibiBytes());
    }

    @Test
    public void testParseGibiBytes_2() {
        assertEquals(987654, MemorySize.parse("987654 g").getGibiBytes());
    }

    @Test
    public void testParseGibiBytes_3() {
        assertEquals(987654, MemorySize.parse("987654gb").getGibiBytes());
    }

    @Test
    public void testParseGibiBytes_4() {
        assertEquals(987654, MemorySize.parse("987654 gb").getGibiBytes());
    }

    @Test
    public void testParseGibiBytes_5() {
        assertEquals(987654, MemorySize.parse("987654gibibytes").getGibiBytes());
    }

    @Test
    public void testParseGibiBytes_6() {
        assertEquals(987654, MemorySize.parse("987654 gibibytes").getGibiBytes());
    }

    @Test
    public void testParseTebiBytes_1() {
        assertEquals(1234567, MemorySize.parse("1234567t").getTebiBytes());
    }

    @Test
    public void testParseTebiBytes_2() {
        assertEquals(1234567, MemorySize.parse("1234567 t").getTebiBytes());
    }

    @Test
    public void testParseTebiBytes_3() {
        assertEquals(1234567, MemorySize.parse("1234567tb").getTebiBytes());
    }

    @Test
    public void testParseTebiBytes_4() {
        assertEquals(1234567, MemorySize.parse("1234567 tb").getTebiBytes());
    }

    @Test
    public void testParseTebiBytes_5() {
        assertEquals(1234567, MemorySize.parse("1234567tebibytes").getTebiBytes());
    }

    @Test
    public void testParseTebiBytes_6() {
        assertEquals(1234567, MemorySize.parse("1234567 tebibytes").getTebiBytes());
    }

    @Test
    public void testUpperCase_1() {
        assertEquals(1L, MemorySize.parse("1 B").getBytes());
    }

    @Test
    public void testUpperCase_2() {
        assertEquals(1L, MemorySize.parse("1 K").getKibiBytes());
    }

    @Test
    public void testUpperCase_3() {
        assertEquals(1L, MemorySize.parse("1 M").getMebiBytes());
    }

    @Test
    public void testUpperCase_4() {
        assertEquals(1L, MemorySize.parse("1 G").getGibiBytes());
    }

    @Test
    public void testUpperCase_5() {
        assertEquals(1L, MemorySize.parse("1 T").getTebiBytes());
    }

    @Test
    public void testTrimBeforeParse_1() {
        assertEquals(155L, MemorySize.parseBytes("      155      "));
    }

    @Test
    public void testTrimBeforeParse_2() {
        assertEquals(155L, MemorySize.parseBytes("      155      bytes   "));
    }

    @Test
    public void testParseWithDefaultUnit_1() {
        assertEquals(7, MemorySize.parse("7", MEGA_BYTES).getMebiBytes());
    }

    @Test
    public void testParseWithDefaultUnit_2() {
        assertNotEquals(7, MemorySize.parse("7340032", MEGA_BYTES));
    }

    @Test
    public void testParseWithDefaultUnit_3() {
        assertEquals(7, MemorySize.parse("7m", MEGA_BYTES).getMebiBytes());
    }

    @Test
    public void testParseWithDefaultUnit_4() {
        assertEquals(7168, MemorySize.parse("7", MEGA_BYTES).getKibiBytes());
    }

    @Test
    public void testParseWithDefaultUnit_5() {
        assertEquals(7168, MemorySize.parse("7m", MEGA_BYTES).getKibiBytes());
    }

    @Test
    public void testParseWithDefaultUnit_6() {
        assertEquals(7, MemorySize.parse("7 m", MEGA_BYTES).getMebiBytes());
    }

    @Test
    public void testParseWithDefaultUnit_7() {
        assertEquals(7, MemorySize.parse("7mb", MEGA_BYTES).getMebiBytes());
    }

    @Test
    public void testParseWithDefaultUnit_8() {
        assertEquals(7, MemorySize.parse("7 mb", MEGA_BYTES).getMebiBytes());
    }

    @Test
    public void testParseWithDefaultUnit_9() {
        assertEquals(7, MemorySize.parse("7mebibytes", MEGA_BYTES).getMebiBytes());
    }

    @Test
    public void testParseWithDefaultUnit_10() {
        assertEquals(7, MemorySize.parse("7 mebibytes", MEGA_BYTES).getMebiBytes());
    }

    @Test
    public void testToHumanReadableString_1() {
        assertThat(new MemorySize(0L).toHumanReadableString(), is("0 bytes"));
    }

    @Test
    public void testToHumanReadableString_2() {
        assertThat(new MemorySize(1L).toHumanReadableString(), is("1 bytes"));
    }

    @Test
    public void testToHumanReadableString_3() {
        assertThat(new MemorySize(1024L).toHumanReadableString(), is("1024 bytes"));
    }

    @Test
    public void testToHumanReadableString_4() {
        assertThat(new MemorySize(1025L).toHumanReadableString(), is("1.001kb (1025 bytes)"));
    }

    @Test
    public void testToHumanReadableString_5() {
        assertThat(new MemorySize(1536L).toHumanReadableString(), is("1.500kb (1536 bytes)"));
    }

    @Test
    public void testToHumanReadableString_6() {
        assertThat(new MemorySize(1_000_000L).toHumanReadableString(), is("976.563kb (1000000 bytes)"));
    }

    @Test
    public void testToHumanReadableString_7() {
        assertThat(new MemorySize(1_000_000_000L).toHumanReadableString(), is("953.674mb (1000000000 bytes)"));
    }

    @Test
    public void testToHumanReadableString_8() {
        assertThat(new MemorySize(1_000_000_000_000L).toHumanReadableString(), is("931.323gb (1000000000000 bytes)"));
    }

    @Test
    public void testToHumanReadableString_9() {
        assertThat(new MemorySize(1_000_000_000_000_000L).toHumanReadableString(), is("909.495tb (1000000000000000 bytes)"));
    }
}
