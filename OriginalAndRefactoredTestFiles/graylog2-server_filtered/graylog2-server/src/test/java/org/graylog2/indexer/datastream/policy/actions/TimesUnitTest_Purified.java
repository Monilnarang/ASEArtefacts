package org.graylog2.indexer.datastream.policy.actions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TimesUnitTest_Purified {

    @Test
    public void testFormat_1() {
        assertEquals("10d", TimesUnit.DAYS.format(10L));
    }

    @Test
    public void testFormat_2() {
        assertEquals("10h", TimesUnit.HOURS.format(10L));
    }

    @Test
    public void testFormat_3() {
        assertEquals("10m", TimesUnit.MINUTES.format(10L));
    }

    @Test
    public void testFormat_4() {
        assertEquals("10s", TimesUnit.SECONDS.format(10L));
    }

    @Test
    public void testFormat_5() {
        assertEquals("10ms", TimesUnit.MILLISECONDS.format(10L));
    }

    @Test
    public void testFormat_6() {
        assertEquals("10micros", TimesUnit.MICROSECONDS.format(10L));
    }

    @Test
    public void testFormat_7() {
        assertEquals("10nanos", TimesUnit.NANOSECONDS.format(10L));
    }
}
