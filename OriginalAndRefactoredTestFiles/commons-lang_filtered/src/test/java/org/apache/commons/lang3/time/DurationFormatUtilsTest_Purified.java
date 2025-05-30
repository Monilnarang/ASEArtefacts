package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.time.Duration;
import java.util.Calendar;
import java.util.TimeZone;
import org.apache.commons.lang3.AbstractLangTest;
import org.apache.commons.lang3.time.DurationFormatUtils.Token;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.DefaultTimeZone;

public class DurationFormatUtilsTest_Purified extends AbstractLangTest {

    private static final int FOUR_YEARS = 365 * 3 + 366;

    private void assertEqualDuration(final String expected, final int[] start, final int[] end, final String format) {
        assertEqualDuration(null, expected, start, end, format);
    }

    private void assertEqualDuration(final String message, final String expected, final int[] start, final int[] end, final String format) {
        final Calendar cal1 = Calendar.getInstance();
        cal1.set(start[0], start[1], start[2], start[3], start[4], start[5]);
        cal1.set(Calendar.MILLISECOND, 0);
        final Calendar cal2 = Calendar.getInstance();
        cal2.set(end[0], end[1], end[2], end[3], end[4], end[5]);
        cal2.set(Calendar.MILLISECOND, 0);
        final long milli1 = cal1.getTime().getTime();
        final long milli2 = cal2.getTime().getTime();
        final String result = DurationFormatUtils.formatPeriod(milli1, milli2, format);
        if (message == null) {
            assertEquals(expected, result);
        } else {
            assertEquals(expected, result, message);
        }
    }

    private void bruteForce(final int year, final int month, final int day, final String format, final int calendarType) {
        final String msg = year + "-" + month + "-" + day + " to ";
        final Calendar c = Calendar.getInstance();
        c.set(year, month, day, 0, 0, 0);
        final int[] array1 = { year, month, day, 0, 0, 0 };
        final int[] array2 = { year, month, day, 0, 0, 0 };
        for (int i = 0; i < FOUR_YEARS; i++) {
            array2[0] = c.get(Calendar.YEAR);
            array2[1] = c.get(Calendar.MONTH);
            array2[2] = c.get(Calendar.DAY_OF_MONTH);
            final String tmpMsg = msg + array2[0] + "-" + array2[1] + "-" + array2[2] + " at ";
            assertEqualDuration(tmpMsg + i, Integer.toString(i), array1, array2, format);
            c.add(calendarType, 1);
        }
    }

    private DurationFormatUtils.Token createTokenWithCount(final CharSequence value, final int count) {
        final DurationFormatUtils.Token token = new DurationFormatUtils.Token(value, false, -1);
        assertNotNull(token.toString());
        for (int i = 1; i < count; i++) {
            token.increment();
            assertNotNull(token.toString());
        }
        return token;
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testConstructor_1() {
        assertNotNull(new DurationFormatUtils());
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testConstructor_2_testMerged_2() {
        final Constructor<?>[] cons = DurationFormatUtils.class.getDeclaredConstructors();
        assertEquals(1, cons.length);
        assertTrue(Modifier.isPublic(cons[0].getModifiers()));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testConstructor_4() {
        assertTrue(Modifier.isPublic(DurationFormatUtils.class.getModifiers()));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testConstructor_5() {
        assertFalse(Modifier.isFinal(DurationFormatUtils.class.getModifiers()));
    }

    @Test
    public void testEmptyOptionals_1() {
        assertEquals("", DurationFormatUtils.formatDuration(0L, "[d'd'][H'h'][m'm'][s's']"));
    }

    @Test
    public void testEmptyOptionals_2() {
        assertEquals("", DurationFormatUtils.formatDuration(0L, "['d''h''m''s's]"));
    }

    @Test
    public void testFormatDurationISO_1() {
        assertEquals("P0Y0M0DT0H0M0.000S", DurationFormatUtils.formatDurationISO(0L));
    }

    @Test
    public void testFormatDurationISO_2() {
        assertEquals("P0Y0M0DT0H0M0.001S", DurationFormatUtils.formatDurationISO(1L));
    }

    @Test
    public void testFormatDurationISO_3() {
        assertEquals("P0Y0M0DT0H0M0.010S", DurationFormatUtils.formatDurationISO(10L));
    }

    @Test
    public void testFormatDurationISO_4() {
        assertEquals("P0Y0M0DT0H0M0.100S", DurationFormatUtils.formatDurationISO(100L));
    }

    @Test
    public void testFormatDurationISO_5() {
        assertEquals("P0Y0M0DT0H1M15.321S", DurationFormatUtils.formatDurationISO(75321L));
    }

    @Test
    public void testFormatPeriodISOMethod_1() {
        assertEquals("P0Y0M0DT0H0M0.000S", DurationFormatUtils.formatPeriodISO(0L, 0L));
    }

    @Test
    public void testFormatPeriodISOMethod_2() {
        assertEquals("P0Y0M0DT0H0M1.000S", DurationFormatUtils.formatPeriodISO(0L, 1000L));
    }

    @Test
    public void testFormatPeriodISOMethod_3() {
        assertEquals("P0Y0M0DT0H1M1.000S", DurationFormatUtils.formatPeriodISO(0L, 61000L));
    }

    @Test
    public void testLANG982_1() {
        assertEquals("61.999", DurationFormatUtils.formatDuration(61999, "s.S"));
    }

    @Test
    public void testLANG982_2() {
        assertEquals("1 1999", DurationFormatUtils.formatDuration(61999, "m S"));
    }

    @Test
    public void testLANG982_3() {
        assertEquals("61.999", DurationFormatUtils.formatDuration(61999, "s.SSS"));
    }

    @Test
    public void testLANG982_4() {
        assertEquals("1 1999", DurationFormatUtils.formatDuration(61999, "m SSS"));
    }

    @Test
    public void testLANG982_5() {
        assertEquals("61.0999", DurationFormatUtils.formatDuration(61999, "s.SSSS"));
    }

    @Test
    public void testLANG982_6() {
        assertEquals("1 1999", DurationFormatUtils.formatDuration(61999, "m SSSS"));
    }

    @Test
    public void testLANG982_7() {
        assertEquals("61.00999", DurationFormatUtils.formatDuration(61999, "s.SSSSS"));
    }

    @Test
    public void testLANG982_8() {
        assertEquals("1 01999", DurationFormatUtils.formatDuration(61999, "m SSSSS"));
    }

    @Test
    public void testLANG984_1() {
        assertEquals("0", DurationFormatUtils.formatDuration(0, "S"));
    }

    @Test
    public void testLANG984_2() {
        assertEquals(Integer.toString(Integer.MAX_VALUE), DurationFormatUtils.formatDuration(Integer.MAX_VALUE, "S"));
    }

    @Test
    public void testLANG984_3_testMerged_3() {
        long maxIntPlus = Integer.MAX_VALUE;
        maxIntPlus++;
        assertEquals(Long.toString(maxIntPlus), DurationFormatUtils.formatDuration(maxIntPlus, "S"));
        assertEquals(Long.toString(Long.MAX_VALUE), DurationFormatUtils.formatDuration(Long.MAX_VALUE, "S"));
    }

    @Test
    public void testLiteralPrefixOptionalToken_1() {
        assertEquals(DurationFormatUtils.formatDuration(10000L, "s's'"), DurationFormatUtils.formatDuration(10000L, "['['d']']['<'H'>']['{'m'}']s's'"));
    }

    @Test
    public void testLiteralPrefixOptionalToken_2() {
        assertEquals(DurationFormatUtils.formatDuration(10000L, "s's'"), DurationFormatUtils.formatDuration(10000L, "['{'m'}']s's'"));
    }

    @Test
    public void testMultipleOptionalBlocks_1() {
        assertEquals(DurationFormatUtils.formatDuration(Duration.ofHours(1).toMillis(), "'[['H']]'"), DurationFormatUtils.formatDuration(Duration.ofHours(1).toMillis(), "['{'d'}']['[['H']]']"));
    }

    @Test
    public void testMultipleOptionalBlocks_2() {
        assertEquals(DurationFormatUtils.formatDuration(Duration.ofDays(1).toMillis(), "['{'d'}']"), DurationFormatUtils.formatDuration(Duration.ofDays(1).toMillis(), "['{'d'}']['['H']']"));
    }

    @Test
    public void testOptionalToken_1() {
        assertEquals(DurationFormatUtils.formatDuration(915361000L, "d'd'H'h'm'm's's'"), DurationFormatUtils.formatDuration(915361000L, "[d'd'H'h'm'm']s's'"));
    }

    @Test
    public void testOptionalToken_2() {
        assertEquals(DurationFormatUtils.formatDuration(9153610L, "H'h'm'm's's'"), DurationFormatUtils.formatDuration(9153610L, "[d'd'H'h'm'm']s's'"));
    }

    @Test
    public void testOptionalToken_3() {
        assertEquals(DurationFormatUtils.formatDuration(915361L, "m'm's's'"), DurationFormatUtils.formatDuration(915361L, "[d'd'H'h'm'm']s's'"));
    }

    @Test
    public void testOptionalToken_4() {
        assertEquals(DurationFormatUtils.formatDuration(9153L, "s's'"), DurationFormatUtils.formatDuration(9153L, "[d'd'H'h'm'm']s's'"));
    }

    @Test
    public void testOptionalToken_5() {
        assertEquals(DurationFormatUtils.formatDuration(9153L, "s's'"), DurationFormatUtils.formatDuration(9153L, "[d'd'H'h'm'm']s's'"));
    }

    @Test
    public void testOptionalToken_6() {
        assertEquals(DurationFormatUtils.formatPeriod(9153610L, 915361000L, "d'd'H'h'm'm's's'"), DurationFormatUtils.formatPeriod(9153610L, 915361000L, "[d'd'H'h'm'm']s's'"));
    }

    @Test
    public void testOptionalToken_7() {
        assertEquals(DurationFormatUtils.formatPeriod(915361L, 9153610L, "H'h'm'm's's'"), DurationFormatUtils.formatPeriod(915361L, 9153610L, "[d'd'H'h'm'm']s's'"));
    }

    @Test
    public void testOptionalToken_8() {
        assertEquals(DurationFormatUtils.formatPeriod(9153L, 915361L, "m'm's's'"), DurationFormatUtils.formatPeriod(9153L, 915361L, "[d'd'H'h'm'm']s's'"));
    }

    @Test
    public void testOptionalToken_9() {
        assertEquals(DurationFormatUtils.formatPeriod(0L, 9153L, "s's'"), DurationFormatUtils.formatPeriod(0L, 9153L, "[d'd'H'h'm'm']s's'"));
    }

    @Test
    public void testOptionalToken_10() {
        assertEquals("2h32m33s610ms", DurationFormatUtils.formatDuration(9153610L, "[d'd'H'h'm'm's's']S'ms'"));
    }

    @Test
    public void testOptionalToken_11() {
        assertEquals("15m15s361ms", DurationFormatUtils.formatDuration(915361L, "[d'd'H'h'm'm's's']S'ms'"));
    }

    @Test
    public void testOptionalToken_12() {
        assertEquals("9s153ms", DurationFormatUtils.formatDuration(9153L, "[d'd'H'h'm'm's's']S'ms'"));
    }

    @Test
    public void testOptionalToken_13() {
        assertEquals("915ms", DurationFormatUtils.formatDuration(915L, "[d'd'H'h'm'm's's']S'ms'"));
    }

    @Test
    public void testOptionalToken_14() {
        assertEquals(DurationFormatUtils.formatPeriod(915361L, 9153610L, "H'h''h2'm'm's's'"), DurationFormatUtils.formatPeriod(915361L, 9153610L, "[d'd''d2'H'h''h2'm'm']s's'"));
    }
}
