package org.apache.druid.delta.input;

import org.apache.druid.java.util.common.Intervals;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.time.Instant;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class DeltaTimeUtilsTest_Parameterized {

    @Before
    public void setUp() {
        System.setProperty("user.timezone", "UTC");
    }

    @ParameterizedTest
    @MethodSource("Provider_testTimestampValue_1to2")
    public void testTimestampValue_1to2(String param1, String param2, String param3) {
        Assert.assertEquals(Instant.parse(param1), Instant.ofEpochMilli(DeltaTimeUtils.getMillisFromTimestamp(Instant.parse(param3).toEpochMilli() * param2)));
    }

    static public Stream<Arguments> Provider_testTimestampValue_1to2() {
        return Stream.of(arguments("2018-02-02T00:28:02.000Z", "1_000", "2018-02-02T00:28:02.000Z"), arguments("2024-01-31T00:58:03.000Z", "1_000", "2024-01-31T00:58:03.002Z"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testDateTimeValue_1to2")
    public void testDateTimeValue_1to2(String param1, String param2) {
        Assert.assertEquals(Instant.parse(param1), Instant.ofEpochSecond(DeltaTimeUtils.getSecondsFromDate((int) Intervals.of(param2).toDuration().getStandardDays())));
    }

    static public Stream<Arguments> Provider_testDateTimeValue_1to2() {
        return Stream.of(arguments("2020-02-01T00:00:00.000Z", "1970-01-01/2020-02-01"), arguments("2024-01-01T00:00:00.000Z", "1970-01-01/2024-01-01T02:23:00"));
    }
}
