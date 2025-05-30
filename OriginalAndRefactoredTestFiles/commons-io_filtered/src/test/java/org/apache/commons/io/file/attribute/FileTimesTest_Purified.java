package org.apache.commons.io.file.attribute;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class FileTimesTest_Purified {

    public static Stream<Arguments> fileTimeNanoUnitsToNtfsProvider() {
        return Stream.of(Arguments.of("1601-01-01T00:00:00.0000000Z", 0), Arguments.of("1601-01-01T00:00:00.0000001Z", 1), Arguments.of("1601-01-01T00:00:00.0000010Z", 10), Arguments.of("1601-01-01T00:00:00.0000100Z", 100), Arguments.of("1601-01-01T00:00:00.0001000Z", 1000), Arguments.of("1600-12-31T23:59:59.9999999Z", -1), Arguments.of("+30828-09-14T02:48:05.477580700Z", Long.MAX_VALUE), Arguments.of("+30828-09-14T02:48:05.477580600Z", Long.MAX_VALUE - 1), Arguments.of("+30828-09-14T02:48:05.477579700Z", Long.MAX_VALUE - 10), Arguments.of("+30828-09-14T02:48:05.477570700Z", Long.MAX_VALUE - 100), Arguments.of("+30828-09-14T02:48:05.477480700Z", Long.MAX_VALUE - 1000), Arguments.of("-27627-04-19T21:11:54.522419200Z", Long.MIN_VALUE), Arguments.of("-27627-04-19T21:11:54.522419300Z", Long.MIN_VALUE + 1), Arguments.of("-27627-04-19T21:11:54.522420200Z", Long.MIN_VALUE + 10), Arguments.of("-27627-04-19T21:11:54.522429200Z", Long.MIN_VALUE + 100), Arguments.of("-27627-04-19T21:11:54.522519200Z", Long.MIN_VALUE + 1000), Arguments.of("1601-01-01T00:00:00.0010000Z", FileTimes.HUNDRED_NANOS_PER_MILLISECOND), Arguments.of("1601-01-01T00:00:00.0010001Z", FileTimes.HUNDRED_NANOS_PER_MILLISECOND + 1), Arguments.of("1601-01-01T00:00:00.0009999Z", FileTimes.HUNDRED_NANOS_PER_MILLISECOND - 1), Arguments.of("1600-12-31T23:59:59.9990000Z", -FileTimes.HUNDRED_NANOS_PER_MILLISECOND), Arguments.of("1600-12-31T23:59:59.9990001Z", -FileTimes.HUNDRED_NANOS_PER_MILLISECOND + 1), Arguments.of("1600-12-31T23:59:59.9989999Z", -FileTimes.HUNDRED_NANOS_PER_MILLISECOND - 1), Arguments.of("1970-01-01T00:00:00.0000000Z", -FileTimes.UNIX_TO_NTFS_OFFSET), Arguments.of("1970-01-01T00:00:00.0000001Z", -FileTimes.UNIX_TO_NTFS_OFFSET + 1), Arguments.of("1970-01-01T00:00:00.0010000Z", -FileTimes.UNIX_TO_NTFS_OFFSET + FileTimes.HUNDRED_NANOS_PER_MILLISECOND), Arguments.of("1969-12-31T23:59:59.9999999Z", -FileTimes.UNIX_TO_NTFS_OFFSET - 1), Arguments.of("1969-12-31T23:59:59.9990000Z", -FileTimes.UNIX_TO_NTFS_OFFSET - FileTimes.HUNDRED_NANOS_PER_MILLISECOND));
    }

    public static Stream<Arguments> fileTimeToNtfsProvider() {
        return Stream.of(Arguments.of("1970-01-01T00:00:00Z", FileTime.from(Instant.EPOCH)), Arguments.of("1969-12-31T23:59:00Z", FileTime.from(Instant.EPOCH.minusSeconds(60))), Arguments.of("1970-01-01T00:01:00Z", FileTime.from(Instant.EPOCH.plusSeconds(60))));
    }

    public static Stream<Arguments> isUnixFileTimeProvider() {
        return Stream.of(Arguments.of("2022-12-27T12:45:22Z", true), Arguments.of("2038-01-19T03:14:07Z", true), Arguments.of("1901-12-13T23:14:08Z", true), Arguments.of("1901-12-13T03:14:08Z", false), Arguments.of("2038-01-19T03:14:08Z", false), Arguments.of("2099-06-30T12:31:42Z", false));
    }

    @Test
    public void testMinusMillis_1() {
        final int millis = 2;
        assertEquals(Instant.EPOCH.minusMillis(millis), FileTimes.minusMillis(FileTimes.EPOCH, millis).toInstant());
    }

    @Test
    public void testMinusMillis_2() {
        assertEquals(Instant.EPOCH, FileTimes.minusMillis(FileTimes.EPOCH, 0).toInstant());
    }

    @Test
    public void testMinusNanos_1() {
        final int millis = 2;
        assertEquals(Instant.EPOCH.minusNanos(millis), FileTimes.minusNanos(FileTimes.EPOCH, millis).toInstant());
    }

    @Test
    public void testMinusNanos_2() {
        assertEquals(Instant.EPOCH, FileTimes.minusNanos(FileTimes.EPOCH, 0).toInstant());
    }

    @Test
    public void testMinusSeconds_1() {
        final int seconds = 2;
        assertEquals(Instant.EPOCH.minusSeconds(seconds), FileTimes.minusSeconds(FileTimes.EPOCH, seconds).toInstant());
    }

    @Test
    public void testMinusSeconds_2() {
        assertEquals(Instant.EPOCH, FileTimes.minusSeconds(FileTimes.EPOCH, 0).toInstant());
    }

    @Test
    public void testPlusMinusMillis_1() {
        final int millis = 2;
        assertEquals(Instant.EPOCH.plusMillis(millis), FileTimes.plusMillis(FileTimes.EPOCH, millis).toInstant());
    }

    @Test
    public void testPlusMinusMillis_2() {
        assertEquals(Instant.EPOCH, FileTimes.plusMillis(FileTimes.EPOCH, 0).toInstant());
    }

    @Test
    public void testPlusNanos_1() {
        final int millis = 2;
        assertEquals(Instant.EPOCH.plusNanos(millis), FileTimes.plusNanos(FileTimes.EPOCH, millis).toInstant());
    }

    @Test
    public void testPlusNanos_2() {
        assertEquals(Instant.EPOCH, FileTimes.plusNanos(FileTimes.EPOCH, 0).toInstant());
    }

    @Test
    public void testPlusSeconds_1() {
        final int seconds = 2;
        assertEquals(Instant.EPOCH.plusSeconds(seconds), FileTimes.plusSeconds(FileTimes.EPOCH, seconds).toInstant());
    }

    @Test
    public void testPlusSeconds_2() {
        assertEquals(Instant.EPOCH, FileTimes.plusSeconds(FileTimes.EPOCH, 0).toInstant());
    }
}
