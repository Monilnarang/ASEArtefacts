package org.apache.hadoop.log;

import org.apache.hadoop.log.LogThrottlingHelper.LogAction;
import org.apache.hadoop.util.FakeTimer;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestLogThrottlingHelper_Parameterized {

    private static final int LOG_PERIOD = 100;

    private LogThrottlingHelper helper;

    private FakeTimer timer;

    @Before
    public void setup() {
        timer = new FakeTimer();
        helper = new LogThrottlingHelper(LOG_PERIOD, null, timer);
    }

    @ParameterizedTest
    @MethodSource("Provider_testNamedLoggersWithoutSpecifiedPrimary_1to2")
    public void testNamedLoggersWithoutSpecifiedPrimary_1to2(String param1, int param2) {
        assertTrue(helper.record(param1, param2).shouldLog());
    }

    static public Stream<Arguments> Provider_testNamedLoggersWithoutSpecifiedPrimary_1to2() {
        return Stream.of(arguments("foo", 0), arguments("bar", 0));
    }

    @ParameterizedTest
    @MethodSource("Provider_testNamedLoggersWithoutSpecifiedPrimary_3to4_9")
    public void testNamedLoggersWithoutSpecifiedPrimary_3to4_9(String param1, int param2) {
        assertFalse(helper.record(param1, LOG_PERIOD / param2).shouldLog());
    }

    static public Stream<Arguments> Provider_testNamedLoggersWithoutSpecifiedPrimary_3to4_9() {
        return Stream.of(arguments("foo", 2), arguments("bar", 2), arguments("bar", 2));
    }

    @ParameterizedTest
    @MethodSource("Provider_testNamedLoggersWithoutSpecifiedPrimary_5to6")
    public void testNamedLoggersWithoutSpecifiedPrimary_5to6(String param1) {
        assertTrue(helper.record(param1, LOG_PERIOD).shouldLog());
    }

    static public Stream<Arguments> Provider_testNamedLoggersWithoutSpecifiedPrimary_5to6() {
        return Stream.of(arguments("foo"), arguments("bar"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testNamedLoggersWithoutSpecifiedPrimary_7to8")
    public void testNamedLoggersWithoutSpecifiedPrimary_7to8(String param1, int param2, int param3) {
        assertFalse(helper.record(param1, (LOG_PERIOD * param3) / param2).shouldLog());
    }

    static public Stream<Arguments> Provider_testNamedLoggersWithoutSpecifiedPrimary_7to8() {
        return Stream.of(arguments("foo", 2, 3), arguments("bar", 2, 3));
    }

    @ParameterizedTest
    @MethodSource("Provider_testNamedLoggersWithoutSpecifiedPrimary_10to11")
    public void testNamedLoggersWithoutSpecifiedPrimary_10to11(String param1, int param2) {
        assertTrue(helper.record(param1, LOG_PERIOD * param2).shouldLog());
    }

    static public Stream<Arguments> Provider_testNamedLoggersWithoutSpecifiedPrimary_10to11() {
        return Stream.of(arguments("foo", 2), arguments("bar", 2));
    }
}
