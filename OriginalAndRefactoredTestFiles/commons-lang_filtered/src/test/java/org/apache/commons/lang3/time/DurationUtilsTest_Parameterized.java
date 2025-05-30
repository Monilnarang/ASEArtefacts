package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.AbstractLangTest;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class DurationUtilsTest_Parameterized extends AbstractLangTest {

    @Test
    public void testGetNanosOfMiili_1() {
        assertEquals(0, DurationUtils.getNanosOfMiili(null));
    }

    @Test
    public void testGetNanosOfMiili_2() {
        assertEquals(0, DurationUtils.getNanosOfMiili(Duration.ZERO));
    }

    @Test
    public void testGetNanosOfMilli_1() {
        assertEquals(0, DurationUtils.getNanosOfMilli(null));
    }

    @Test
    public void testGetNanosOfMilli_2() {
        assertEquals(0, DurationUtils.getNanosOfMilli(Duration.ZERO));
    }

    @Test
    public void testIsPositive_1() {
        assertFalse(DurationUtils.isPositive(Duration.ZERO));
    }

    @Test
    public void testIsPositive_2() {
        assertFalse(DurationUtils.isPositive(Duration.ofMillis(-1)));
    }

    @Test
    public void testIsPositive_3() {
        assertTrue(DurationUtils.isPositive(Duration.ofMillis(1)));
    }

    @Test
    public void testLongToIntRangeFit_1() {
        assertEquals(0, DurationUtils.LONG_TO_INT_RANGE.fit(0L));
    }

    @Test
    public void testLongToIntRangeFit_2() {
        assertEquals(Integer.MIN_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(NumberUtils.LONG_INT_MIN_VALUE));
    }

    @Test
    public void testLongToIntRangeFit_5() {
        assertEquals(Integer.MAX_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(NumberUtils.LONG_INT_MAX_VALUE));
    }

    @Test
    public void testLongToIntRangeFit_8() {
        assertEquals(Integer.MIN_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(Long.MIN_VALUE));
    }

    @Test
    public void testLongToIntRangeFit_9() {
        assertEquals(Integer.MAX_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(Long.MAX_VALUE));
    }

    @Test
    public void testLongToIntRangeFit_10() {
        assertEquals(Short.MIN_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit((long) Short.MIN_VALUE));
    }

    @Test
    public void testLongToIntRangeFit_11() {
        assertEquals(Short.MAX_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit((long) Short.MAX_VALUE));
    }

    @Test
    public void testSince_1() {
        assertTrue(DurationUtils.since(Instant.EPOCH).compareTo(Duration.ZERO) >= 0);
    }

    @Test
    public void testSince_2() {
        assertTrue(DurationUtils.since(Instant.MIN).compareTo(Duration.ZERO) >= 0);
    }

    @Test
    public void testSince_3() {
        assertTrue(DurationUtils.since(Instant.MAX).compareTo(Duration.ZERO) <= 0);
    }

    @Test
    public void testToDuration_1() {
        assertEquals(Duration.ofDays(1), DurationUtils.toDuration(1, TimeUnit.DAYS));
    }

    @Test
    public void testToDuration_2() {
        assertEquals(Duration.ofHours(1), DurationUtils.toDuration(1, TimeUnit.HOURS));
    }

    @Test
    public void testToDuration_3() {
        assertEquals(Duration.ofMillis(1), DurationUtils.toDuration(1_000, TimeUnit.MICROSECONDS));
    }

    @Test
    public void testToDuration_4() {
        assertEquals(Duration.ofMillis(1), DurationUtils.toDuration(1, TimeUnit.MILLISECONDS));
    }

    @Test
    public void testToDuration_5() {
        assertEquals(Duration.ofMinutes(1), DurationUtils.toDuration(1, TimeUnit.MINUTES));
    }

    @Test
    public void testToDuration_6() {
        assertEquals(Duration.ofNanos(1), DurationUtils.toDuration(1, TimeUnit.NANOSECONDS));
    }

    @Test
    public void testToDuration_7() {
        assertEquals(Duration.ofSeconds(1), DurationUtils.toDuration(1, TimeUnit.SECONDS));
    }

    @Test
    public void testToDuration_8() {
        assertEquals(1, DurationUtils.toDuration(1, TimeUnit.MILLISECONDS).toMillis());
    }

    @Test
    public void testToDuration_9() {
        assertEquals(-1, DurationUtils.toDuration(-1, TimeUnit.MILLISECONDS).toMillis());
    }

    @Test
    public void testToDuration_10() {
        assertEquals(0, DurationUtils.toDuration(0, TimeUnit.SECONDS).toMillis());
    }

    @Test
    public void testToMillisInt_1() {
        assertEquals(0, DurationUtils.toMillisInt(Duration.ZERO));
    }

    @Test
    public void testToMillisInt_2() {
        assertEquals(1, DurationUtils.toMillisInt(Duration.ofMillis(1)));
    }

    @Test
    public void testToMillisInt_3() {
        assertEquals(Integer.MIN_VALUE, DurationUtils.toMillisInt(Duration.ofMillis(Integer.MIN_VALUE)));
    }

    @Test
    public void testToMillisInt_4() {
        assertEquals(Integer.MAX_VALUE, DurationUtils.toMillisInt(Duration.ofMillis(Integer.MAX_VALUE)));
    }

    @Test
    public void testToMillisInt_9() {
        assertEquals(Integer.MIN_VALUE, DurationUtils.toMillisInt(Duration.ofNanos(Long.MIN_VALUE)));
    }

    @Test
    public void testToMillisInt_10() {
        assertEquals(Integer.MAX_VALUE, DurationUtils.toMillisInt(Duration.ofNanos(Long.MAX_VALUE)));
    }

    @Test
    public void testZeroIfNull_1() {
        assertEquals(Duration.ZERO, DurationUtils.zeroIfNull(null));
    }

    @Test
    public void testZeroIfNull_2() {
        assertEquals(Duration.ofDays(1), DurationUtils.zeroIfNull(Duration.ofDays(1)));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetNanosOfMiili_3to10")
    public void testGetNanosOfMiili_3to10(int param1, int param2) {
        assertEquals(param1, DurationUtils.getNanosOfMiili(Duration.ofNanos(param2)));
    }

    static public Stream<Arguments> Provider_testGetNanosOfMiili_3to10() {
        return Stream.of(arguments(1, 1), arguments(10, 10), arguments(100, 100), arguments("1_000", "1_000"), arguments("10_000", "10_000"), arguments("100_000", "100_000"), arguments(0, "1_000_000"), arguments(1, "1_000_001"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetNanosOfMilli_3to10")
    public void testGetNanosOfMilli_3to10(int param1, int param2) {
        assertEquals(param1, DurationUtils.getNanosOfMilli(Duration.ofNanos(param2)));
    }

    static public Stream<Arguments> Provider_testGetNanosOfMilli_3to10() {
        return Stream.of(arguments(1, 1), arguments(10, 10), arguments(100, 100), arguments("1_000", "1_000"), arguments("10_000", "10_000"), arguments("100_000", "100_000"), arguments(0, "1_000_000"), arguments(1, "1_000_001"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLongToIntRangeFit_3to4")
    public void testLongToIntRangeFit_3to4(int param1) {
        assertEquals(Integer.MIN_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(NumberUtils.LONG_INT_MIN_VALUE - param1));
    }

    static public Stream<Arguments> Provider_testLongToIntRangeFit_3to4() {
        return Stream.of(arguments(1), arguments(2));
    }

    @ParameterizedTest
    @MethodSource("Provider_testLongToIntRangeFit_6to7")
    public void testLongToIntRangeFit_6to7(int param1) {
        assertEquals(Integer.MAX_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(NumberUtils.LONG_INT_MAX_VALUE + param1));
    }

    static public Stream<Arguments> Provider_testLongToIntRangeFit_6to7() {
        return Stream.of(arguments(1), arguments(2));
    }

    @ParameterizedTest
    @MethodSource("Provider_testToMillisInt_5to6")
    public void testToMillisInt_5to6(int param1) {
        assertEquals(Integer.MAX_VALUE, DurationUtils.toMillisInt(Duration.ofMillis(NumberUtils.LONG_INT_MAX_VALUE + param1)));
    }

    static public Stream<Arguments> Provider_testToMillisInt_5to6() {
        return Stream.of(arguments(1), arguments(2));
    }

    @ParameterizedTest
    @MethodSource("Provider_testToMillisInt_7to8")
    public void testToMillisInt_7to8(int param1) {
        assertEquals(Integer.MIN_VALUE, DurationUtils.toMillisInt(Duration.ofMillis(NumberUtils.LONG_INT_MIN_VALUE - param1)));
    }

    static public Stream<Arguments> Provider_testToMillisInt_7to8() {
        return Stream.of(arguments(1), arguments(2));
    }
}
