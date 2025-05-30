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

public class DurationUtilsTest_Purified extends AbstractLangTest {

    @Test
    public void testGetNanosOfMiili_1() {
        assertEquals(0, DurationUtils.getNanosOfMiili(null));
    }

    @Test
    public void testGetNanosOfMiili_2() {
        assertEquals(0, DurationUtils.getNanosOfMiili(Duration.ZERO));
    }

    @Test
    public void testGetNanosOfMiili_3() {
        assertEquals(1, DurationUtils.getNanosOfMiili(Duration.ofNanos(1)));
    }

    @Test
    public void testGetNanosOfMiili_4() {
        assertEquals(10, DurationUtils.getNanosOfMiili(Duration.ofNanos(10)));
    }

    @Test
    public void testGetNanosOfMiili_5() {
        assertEquals(100, DurationUtils.getNanosOfMiili(Duration.ofNanos(100)));
    }

    @Test
    public void testGetNanosOfMiili_6() {
        assertEquals(1_000, DurationUtils.getNanosOfMiili(Duration.ofNanos(1_000)));
    }

    @Test
    public void testGetNanosOfMiili_7() {
        assertEquals(10_000, DurationUtils.getNanosOfMiili(Duration.ofNanos(10_000)));
    }

    @Test
    public void testGetNanosOfMiili_8() {
        assertEquals(100_000, DurationUtils.getNanosOfMiili(Duration.ofNanos(100_000)));
    }

    @Test
    public void testGetNanosOfMiili_9() {
        assertEquals(0, DurationUtils.getNanosOfMiili(Duration.ofNanos(1_000_000)));
    }

    @Test
    public void testGetNanosOfMiili_10() {
        assertEquals(1, DurationUtils.getNanosOfMiili(Duration.ofNanos(1_000_001)));
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
    public void testGetNanosOfMilli_3() {
        assertEquals(1, DurationUtils.getNanosOfMilli(Duration.ofNanos(1)));
    }

    @Test
    public void testGetNanosOfMilli_4() {
        assertEquals(10, DurationUtils.getNanosOfMilli(Duration.ofNanos(10)));
    }

    @Test
    public void testGetNanosOfMilli_5() {
        assertEquals(100, DurationUtils.getNanosOfMilli(Duration.ofNanos(100)));
    }

    @Test
    public void testGetNanosOfMilli_6() {
        assertEquals(1_000, DurationUtils.getNanosOfMilli(Duration.ofNanos(1_000)));
    }

    @Test
    public void testGetNanosOfMilli_7() {
        assertEquals(10_000, DurationUtils.getNanosOfMilli(Duration.ofNanos(10_000)));
    }

    @Test
    public void testGetNanosOfMilli_8() {
        assertEquals(100_000, DurationUtils.getNanosOfMilli(Duration.ofNanos(100_000)));
    }

    @Test
    public void testGetNanosOfMilli_9() {
        assertEquals(0, DurationUtils.getNanosOfMilli(Duration.ofNanos(1_000_000)));
    }

    @Test
    public void testGetNanosOfMilli_10() {
        assertEquals(1, DurationUtils.getNanosOfMilli(Duration.ofNanos(1_000_001)));
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
    public void testLongToIntRangeFit_3() {
        assertEquals(Integer.MIN_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(NumberUtils.LONG_INT_MIN_VALUE - 1));
    }

    @Test
    public void testLongToIntRangeFit_4() {
        assertEquals(Integer.MIN_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(NumberUtils.LONG_INT_MIN_VALUE - 2));
    }

    @Test
    public void testLongToIntRangeFit_5() {
        assertEquals(Integer.MAX_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(NumberUtils.LONG_INT_MAX_VALUE));
    }

    @Test
    public void testLongToIntRangeFit_6() {
        assertEquals(Integer.MAX_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(NumberUtils.LONG_INT_MAX_VALUE + 1));
    }

    @Test
    public void testLongToIntRangeFit_7() {
        assertEquals(Integer.MAX_VALUE, DurationUtils.LONG_TO_INT_RANGE.fit(NumberUtils.LONG_INT_MAX_VALUE + 2));
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
    public void testToMillisInt_5() {
        assertEquals(Integer.MAX_VALUE, DurationUtils.toMillisInt(Duration.ofMillis(NumberUtils.LONG_INT_MAX_VALUE + 1)));
    }

    @Test
    public void testToMillisInt_6() {
        assertEquals(Integer.MAX_VALUE, DurationUtils.toMillisInt(Duration.ofMillis(NumberUtils.LONG_INT_MAX_VALUE + 2)));
    }

    @Test
    public void testToMillisInt_7() {
        assertEquals(Integer.MIN_VALUE, DurationUtils.toMillisInt(Duration.ofMillis(NumberUtils.LONG_INT_MIN_VALUE - 1)));
    }

    @Test
    public void testToMillisInt_8() {
        assertEquals(Integer.MIN_VALUE, DurationUtils.toMillisInt(Duration.ofMillis(NumberUtils.LONG_INT_MIN_VALUE - 2)));
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
}
