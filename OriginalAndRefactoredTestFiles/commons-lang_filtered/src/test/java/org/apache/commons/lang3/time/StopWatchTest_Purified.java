package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.commons.lang3.AbstractLangTest;
import org.apache.commons.lang3.ThreadUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class StopWatchTest_Purified extends AbstractLangTest {

    private static final int SPLIT_CLOCK_STR_LEN = 12;

    private static final Duration MIN_DURATION = Duration.ofMillis(20);

    private static final String MESSAGE = "Baking cookies";

    private static final String ZERO_HOURS_PREFIX = "00:";

    private static final String ZERO_TIME_ELAPSED = "00:00:00.000";

    private StopWatch createMockStopWatch(final long nanos) {
        final StopWatch watch = StopWatch.createStarted();
        watch.suspend();
        return set(watch, nanos);
    }

    private StopWatch set(final StopWatch watch, final long nanos) {
        try {
            final long currentNanos = System.nanoTime();
            FieldUtils.writeField(watch, "startTimeNanos", currentNanos - nanos, true);
            FieldUtils.writeField(watch, "stopTimeNanos", currentNanos, true);
        } catch (final IllegalAccessException e) {
            return null;
        }
        return watch;
    }

    private void sleepPlus1(final Duration duration) throws InterruptedException {
        ThreadUtils.sleep(duration.plusMillis(1));
    }

    private int throwIOException() throws IOException {
        throw new IOException("A");
    }

    @Test
    public void testMessage_1() {
        assertNull(StopWatch.create().getMessage());
    }

    @Test
    public void testMessage_2_testMerged_2() {
        final StopWatch stopWatch = new StopWatch(MESSAGE);
        assertEquals(MESSAGE, stopWatch.getMessage());
        assertTrue(stopWatch.toString().startsWith(MESSAGE), "stopWatch.toString");
        stopWatch.start();
        stopWatch.split();
        assertTrue(stopWatch.toSplitString().startsWith(MESSAGE), "stopWatch.toSplitString");
    }

    @Test
    public void testStopInstantSimple_1() throws InterruptedException {
        final StopWatch watch = StopWatch.createStarted();
        watch.stop();
        final Instant stopTime = watch.getStopInstant();
        assertEquals(stopTime, watch.getStopInstant());
    }

    @Test
    public void testStopInstantSimple_2_testMerged_2() throws InterruptedException {
        final long testStartMillis = System.currentTimeMillis();
        final long testEndMillis = System.currentTimeMillis();
        assertTrue(testStartMillis < testEndMillis);
        assertTrue(Instant.ofEpochMilli(testStartMillis).isBefore(Instant.ofEpochMilli(testEndMillis)));
    }

    @Test
    public void testStopTimeSimple_1() throws InterruptedException {
        final StopWatch watch = StopWatch.createStarted();
        watch.stop();
        final long stopTime = watch.getStopTime();
        assertEquals(stopTime, watch.getStopTime());
    }

    @Test
    public void testStopTimeSimple_2() throws InterruptedException {
        final long testStartMillis = System.currentTimeMillis();
        final long testEndMillis = System.currentTimeMillis();
        assertTrue(testStartMillis < testEndMillis);
    }

    @Test
    public void testToStringWithMessage_1() throws InterruptedException {
        assertTrue(new StopWatch(MESSAGE).toString().startsWith(MESSAGE), "message");
    }

    @Test
    public void testToStringWithMessage_2() throws InterruptedException {
        final StopWatch watch = new StopWatch(MESSAGE);
        watch.start();
        watch.split();
        final String splitStr = watch.toString();
        assertEquals(SPLIT_CLOCK_STR_LEN + MESSAGE.length() + 1, splitStr.length(), "Formatted split string not the correct length");
    }
}
