package org.apache.hadoop.log;

import org.apache.hadoop.log.LogThrottlingHelper.LogAction;
import org.apache.hadoop.util.FakeTimer;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestLogThrottlingHelper_Purified {

    private static final int LOG_PERIOD = 100;

    private LogThrottlingHelper helper;

    private FakeTimer timer;

    @Before
    public void setup() {
        timer = new FakeTimer();
        helper = new LogThrottlingHelper(LOG_PERIOD, null, timer);
    }

    @Test
    public void testNamedLoggersWithoutSpecifiedPrimary_1() {
        assertTrue(helper.record("foo", 0).shouldLog());
    }

    @Test
    public void testNamedLoggersWithoutSpecifiedPrimary_2() {
        assertTrue(helper.record("bar", 0).shouldLog());
    }

    @Test
    public void testNamedLoggersWithoutSpecifiedPrimary_3() {
        assertFalse(helper.record("foo", LOG_PERIOD / 2).shouldLog());
    }

    @Test
    public void testNamedLoggersWithoutSpecifiedPrimary_4() {
        assertFalse(helper.record("bar", LOG_PERIOD / 2).shouldLog());
    }

    @Test
    public void testNamedLoggersWithoutSpecifiedPrimary_5() {
        assertTrue(helper.record("foo", LOG_PERIOD).shouldLog());
    }

    @Test
    public void testNamedLoggersWithoutSpecifiedPrimary_6() {
        assertTrue(helper.record("bar", LOG_PERIOD).shouldLog());
    }

    @Test
    public void testNamedLoggersWithoutSpecifiedPrimary_7() {
        assertFalse(helper.record("foo", (LOG_PERIOD * 3) / 2).shouldLog());
    }

    @Test
    public void testNamedLoggersWithoutSpecifiedPrimary_8() {
        assertFalse(helper.record("bar", (LOG_PERIOD * 3) / 2).shouldLog());
    }

    @Test
    public void testNamedLoggersWithoutSpecifiedPrimary_9() {
        assertFalse(helper.record("bar", LOG_PERIOD * 2).shouldLog());
    }

    @Test
    public void testNamedLoggersWithoutSpecifiedPrimary_10() {
        assertTrue(helper.record("foo", LOG_PERIOD * 2).shouldLog());
    }

    @Test
    public void testNamedLoggersWithoutSpecifiedPrimary_11() {
        assertTrue(helper.record("bar", LOG_PERIOD * 2).shouldLog());
    }
}
