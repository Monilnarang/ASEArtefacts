package org.apache.druid.delta.input;

import org.apache.druid.java.util.common.Intervals;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.time.Instant;

public class DeltaTimeUtilsTest_Purified {

    @Before
    public void setUp() {
        System.setProperty("user.timezone", "UTC");
    }

    @Test
    public void testTimestampValue_1() {
        Assert.assertEquals(Instant.parse("2018-02-02T00:28:02.000Z"), Instant.ofEpochMilli(DeltaTimeUtils.getMillisFromTimestamp(Instant.parse("2018-02-02T00:28:02.000Z").toEpochMilli() * 1_000)));
    }

    @Test
    public void testTimestampValue_2() {
        Assert.assertEquals(Instant.parse("2024-01-31T00:58:03.000Z"), Instant.ofEpochMilli(DeltaTimeUtils.getMillisFromTimestamp(Instant.parse("2024-01-31T00:58:03.002Z").toEpochMilli() * 1_000)));
    }

    @Test
    public void testDateTimeValue_1() {
        Assert.assertEquals(Instant.parse("2020-02-01T00:00:00.000Z"), Instant.ofEpochSecond(DeltaTimeUtils.getSecondsFromDate((int) Intervals.of("1970-01-01/2020-02-01").toDuration().getStandardDays())));
    }

    @Test
    public void testDateTimeValue_2() {
        Assert.assertEquals(Instant.parse("2024-01-01T00:00:00.000Z"), Instant.ofEpochSecond(DeltaTimeUtils.getSecondsFromDate((int) Intervals.of("1970-01-01/2024-01-01T02:23:00").toDuration().getStandardDays())));
    }
}
