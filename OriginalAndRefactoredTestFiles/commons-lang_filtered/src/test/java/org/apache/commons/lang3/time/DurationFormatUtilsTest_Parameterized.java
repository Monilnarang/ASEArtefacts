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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class DurationFormatUtilsTest_Parameterized extends AbstractLangTest {

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
    public void testMultipleOptionalBlocks_1() {
        assertEquals(DurationFormatUtils.formatDuration(Duration.ofHours(1).toMillis(), "'[['H']]'"), DurationFormatUtils.formatDuration(Duration.ofHours(1).toMillis(), "['{'d'}']['[['H']]']"));
    }

    @Test
    public void testMultipleOptionalBlocks_2() {
        assertEquals(DurationFormatUtils.formatDuration(Duration.ofDays(1).toMillis(), "['{'d'}']"), DurationFormatUtils.formatDuration(Duration.ofDays(1).toMillis(), "['{'d'}']['['H']']"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testEmptyOptionals_1_1_1to2_2to8_10to13")
    public void testEmptyOptionals_1_1_1to2_2to8_10to13(String param1, long param2, String param3) {
        assertEquals(param1, DurationFormatUtils.formatDuration(param2, param3));
    }

    static public Stream<Arguments> Provider_testEmptyOptionals_1_1_1to2_2to8_10to13() {
        return Stream.of(arguments("", 0L, "[d'd'][H'h'][m'm'][s's']"), arguments("", 0L, "['d''h''m''s's]"), arguments(61.999, 61999, "s.S"), arguments("1 1999", 61999, "m S"), arguments(61.999, 61999, "s.SSS"), arguments("1 1999", 61999, "m SSS"), arguments(61.0999, 61999, "s.SSSS"), arguments("1 1999", 61999, "m SSSS"), arguments(61.00999, 61999, "s.SSSSS"), arguments("1 01999", 61999, "m SSSSS"), arguments(0, 0, "S"), arguments("2h32m33s610ms", 9153610L, "[d'd'H'h'm'm's's']S'ms'"), arguments("15m15s361ms", 915361L, "[d'd'H'h'm'm's's']S'ms'"), arguments("9s153ms", 9153L, "[d'd'H'h'm'm's's']S'ms'"), arguments("915ms", 915L, "[d'd'H'h'm'm's's']S'ms'"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testFormatDurationISO_1to5")
    public void testFormatDurationISO_1to5(String param1, long param2) {
        assertEquals(param1, DurationFormatUtils.formatDurationISO(param2));
    }

    static public Stream<Arguments> Provider_testFormatDurationISO_1to5() {
        return Stream.of(arguments("P0Y0M0DT0H0M0.000S", 0L), arguments("P0Y0M0DT0H0M0.001S", 1L), arguments("P0Y0M0DT0H0M0.010S", 10L), arguments("P0Y0M0DT0H0M0.100S", 100L), arguments("P0Y0M0DT0H1M15.321S", 75321L));
    }

    @ParameterizedTest
    @MethodSource("Provider_testFormatPeriodISOMethod_1to3")
    public void testFormatPeriodISOMethod_1to3(String param1, long param2, long param3) {
        assertEquals(param1, DurationFormatUtils.formatPeriodISO(param2, param3));
    }

    static public Stream<Arguments> Provider_testFormatPeriodISOMethod_1to3() {
        return Stream.of(arguments("P0Y0M0DT0H0M0.000S", 0L, 0L), arguments("P0Y0M0DT0H0M1.000S", 0L, 1000L), arguments("P0Y0M0DT0H1M1.000S", 0L, 61000L));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLiteralPrefixOptionalToken_1_1to2_2to5")
    public void testLiteralPrefixOptionalToken_1_1to2_2to5(long param1, String param2, long param3, String param4) {
        assertEquals(DurationFormatUtils.formatDuration(param1, param2), DurationFormatUtils.formatDuration(param3, param4));
    }

    static public Stream<Arguments> Provider_testLiteralPrefixOptionalToken_1_1to2_2to5() {
        return Stream.of(arguments(10000L, "s's'", 10000L, "['['d']']['<'H'>']['{'m'}']s's'"), arguments(10000L, "s's'", 10000L, "['{'m'}']s's'"), arguments(915361000L, "d'd'H'h'm'm's's'", 915361000L, "[d'd'H'h'm'm']s's'"), arguments(9153610L, "H'h'm'm's's'", 9153610L, "[d'd'H'h'm'm']s's'"), arguments(915361L, "m'm's's'", 915361L, "[d'd'H'h'm'm']s's'"), arguments(9153L, "s's'", 9153L, "[d'd'H'h'm'm']s's'"), arguments(9153L, "s's'", 9153L, "[d'd'H'h'm'm']s's'"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testOptionalToken_6to9_14")
    public void testOptionalToken_6to9_14(long param1, long param2, String param3, long param4, long param5, String param6) {
        assertEquals(DurationFormatUtils.formatPeriod(param1, param2, param3), DurationFormatUtils.formatPeriod(param4, param5, param6));
    }

    static public Stream<Arguments> Provider_testOptionalToken_6to9_14() {
        return Stream.of(arguments(9153610L, 915361000L, "d'd'H'h'm'm's's'", 9153610L, 915361000L, "[d'd'H'h'm'm']s's'"), arguments(915361L, 9153610L, "H'h'm'm's's'", 915361L, 9153610L, "[d'd'H'h'm'm']s's'"), arguments(9153L, 915361L, "m'm's's'", 9153L, 915361L, "[d'd'H'h'm'm']s's'"), arguments(0L, 9153L, "s's'", 0L, 9153L, "[d'd'H'h'm'm']s's'"), arguments(915361L, 9153610L, "H'h''h2'm'm's's'", 915361L, 9153610L, "[d'd''d2'H'h''h2'm'm']s's'"));
    }
}
