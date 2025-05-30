package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.TimeZone;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;

public class FastTimeZoneTest_Purified extends AbstractLangTest {

    private static final int HOURS_23 = 23 * 60 * 60 * 1000;

    private static final int HOURS_2 = 2 * 60 * 60 * 1000;

    private static final int MINUTES_59 = 59 * 60 * 1000;

    private static final int MINUTES_5 = 5 * 60 * 1000;

    @Test
    public void testGmtPrefix_1() {
        assertEquals(HOURS_23, FastTimeZone.getGmtTimeZone("GMT+23:00").getRawOffset());
    }

    @Test
    public void testGmtPrefix_2() {
        assertEquals(-HOURS_23, FastTimeZone.getGmtTimeZone("GMT-23:00").getRawOffset());
    }

    @Test
    public void testHoursColonMinutes_1() {
        assertEquals(HOURS_23, FastTimeZone.getGmtTimeZone("23:00").getRawOffset());
    }

    @Test
    public void testHoursColonMinutes_2() {
        assertEquals(HOURS_2, FastTimeZone.getGmtTimeZone("2:00").getRawOffset());
    }

    @Test
    public void testHoursColonMinutes_3() {
        assertEquals(MINUTES_59, FastTimeZone.getGmtTimeZone("00:59").getRawOffset());
    }

    @Test
    public void testHoursColonMinutes_4() {
        assertEquals(MINUTES_5, FastTimeZone.getGmtTimeZone("00:5").getRawOffset());
    }

    @Test
    public void testHoursColonMinutes_5() {
        assertEquals(HOURS_23 + MINUTES_59, FastTimeZone.getGmtTimeZone("23:59").getRawOffset());
    }

    @Test
    public void testHoursColonMinutes_6() {
        assertEquals(HOURS_2 + MINUTES_5, FastTimeZone.getGmtTimeZone("2:5").getRawOffset());
    }

    @Test
    public void testHoursMinutes_1() {
        assertEquals(HOURS_23, FastTimeZone.getGmtTimeZone("2300").getRawOffset());
    }

    @Test
    public void testHoursMinutes_2() {
        assertEquals(HOURS_2, FastTimeZone.getGmtTimeZone("0200").getRawOffset());
    }

    @Test
    public void testHoursMinutes_3() {
        assertEquals(MINUTES_59, FastTimeZone.getGmtTimeZone("0059").getRawOffset());
    }

    @Test
    public void testHoursMinutes_4() {
        assertEquals(MINUTES_5, FastTimeZone.getGmtTimeZone("0005").getRawOffset());
    }

    @Test
    public void testHoursMinutes_5() {
        assertEquals(HOURS_23 + MINUTES_59, FastTimeZone.getGmtTimeZone("2359").getRawOffset());
    }

    @Test
    public void testHoursMinutes_6() {
        assertEquals(HOURS_2 + MINUTES_5, FastTimeZone.getGmtTimeZone("0205").getRawOffset());
    }

    @Test
    public void testSign_1() {
        assertEquals(HOURS_23, FastTimeZone.getGmtTimeZone("+23:00").getRawOffset());
    }

    @Test
    public void testSign_2() {
        assertEquals(HOURS_2, FastTimeZone.getGmtTimeZone("+2:00").getRawOffset());
    }

    @Test
    public void testSign_3() {
        assertEquals(-HOURS_23, FastTimeZone.getGmtTimeZone("-23:00").getRawOffset());
    }

    @Test
    public void testSign_4() {
        assertEquals(-HOURS_2, FastTimeZone.getGmtTimeZone("-2:00").getRawOffset());
    }

    @Test
    public void testZeroOffsetsReturnSingleton_1() {
        assertEquals(FastTimeZone.getGmtTimeZone(), FastTimeZone.getTimeZone("+0"));
    }

    @Test
    public void testZeroOffsetsReturnSingleton_2() {
        assertEquals(FastTimeZone.getGmtTimeZone(), FastTimeZone.getTimeZone("-0"));
    }
}
