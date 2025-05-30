package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DateUtilsFragmentTest_Purified extends AbstractLangTest {

    private static final int months = 7;

    private static final int days = 23;

    private static final int hours = 19;

    private static final int minutes = 53;

    private static final int seconds = 47;

    private static final int millis = 991;

    private Date aDate;

    private Calendar aCalendar;

    @BeforeEach
    public void setUp() {
        aCalendar = Calendar.getInstance();
        aCalendar.set(2005, months, days, hours, minutes, seconds);
        aCalendar.set(Calendar.MILLISECOND, millis);
        aDate = aCalendar.getTime();
    }

    @Test
    public void testHourOfDayFragmentInLargerUnitWithCalendar_1() {
        assertEquals(0, DateUtils.getFragmentInHours(aCalendar, Calendar.HOUR_OF_DAY));
    }

    @Test
    public void testHourOfDayFragmentInLargerUnitWithCalendar_2() {
        assertEquals(0, DateUtils.getFragmentInDays(aCalendar, Calendar.HOUR_OF_DAY));
    }

    @Test
    public void testHourOfDayFragmentInLargerUnitWithDate_1() {
        assertEquals(0, DateUtils.getFragmentInHours(aDate, Calendar.HOUR_OF_DAY));
    }

    @Test
    public void testHourOfDayFragmentInLargerUnitWithDate_2() {
        assertEquals(0, DateUtils.getFragmentInDays(aDate, Calendar.HOUR_OF_DAY));
    }

    @Test
    public void testMillisecondFragmentInLargerUnitWithCalendar_1() {
        assertEquals(0, DateUtils.getFragmentInMilliseconds(aCalendar, Calendar.MILLISECOND));
    }

    @Test
    public void testMillisecondFragmentInLargerUnitWithCalendar_2() {
        assertEquals(0, DateUtils.getFragmentInSeconds(aCalendar, Calendar.MILLISECOND));
    }

    @Test
    public void testMillisecondFragmentInLargerUnitWithCalendar_3() {
        assertEquals(0, DateUtils.getFragmentInMinutes(aCalendar, Calendar.MILLISECOND));
    }

    @Test
    public void testMillisecondFragmentInLargerUnitWithCalendar_4() {
        assertEquals(0, DateUtils.getFragmentInHours(aCalendar, Calendar.MILLISECOND));
    }

    @Test
    public void testMillisecondFragmentInLargerUnitWithCalendar_5() {
        assertEquals(0, DateUtils.getFragmentInDays(aCalendar, Calendar.MILLISECOND));
    }

    @Test
    public void testMillisecondFragmentInLargerUnitWithDate_1() {
        assertEquals(0, DateUtils.getFragmentInMilliseconds(aDate, Calendar.MILLISECOND));
    }

    @Test
    public void testMillisecondFragmentInLargerUnitWithDate_2() {
        assertEquals(0, DateUtils.getFragmentInSeconds(aDate, Calendar.MILLISECOND));
    }

    @Test
    public void testMillisecondFragmentInLargerUnitWithDate_3() {
        assertEquals(0, DateUtils.getFragmentInMinutes(aDate, Calendar.MILLISECOND));
    }

    @Test
    public void testMillisecondFragmentInLargerUnitWithDate_4() {
        assertEquals(0, DateUtils.getFragmentInHours(aDate, Calendar.MILLISECOND));
    }

    @Test
    public void testMillisecondFragmentInLargerUnitWithDate_5() {
        assertEquals(0, DateUtils.getFragmentInDays(aDate, Calendar.MILLISECOND));
    }

    @Test
    public void testMinuteFragmentInLargerUnitWithCalendar_1() {
        assertEquals(0, DateUtils.getFragmentInMinutes(aCalendar, Calendar.MINUTE));
    }

    @Test
    public void testMinuteFragmentInLargerUnitWithCalendar_2() {
        assertEquals(0, DateUtils.getFragmentInHours(aCalendar, Calendar.MINUTE));
    }

    @Test
    public void testMinuteFragmentInLargerUnitWithCalendar_3() {
        assertEquals(0, DateUtils.getFragmentInDays(aCalendar, Calendar.MINUTE));
    }

    @Test
    public void testMinuteFragmentInLargerUnitWithDate_1() {
        assertEquals(0, DateUtils.getFragmentInMinutes(aDate, Calendar.MINUTE));
    }

    @Test
    public void testMinuteFragmentInLargerUnitWithDate_2() {
        assertEquals(0, DateUtils.getFragmentInHours(aDate, Calendar.MINUTE));
    }

    @Test
    public void testMinuteFragmentInLargerUnitWithDate_3() {
        assertEquals(0, DateUtils.getFragmentInDays(aDate, Calendar.MINUTE));
    }

    @Test
    public void testSecondFragmentInLargerUnitWithCalendar_1() {
        assertEquals(0, DateUtils.getFragmentInSeconds(aCalendar, Calendar.SECOND));
    }

    @Test
    public void testSecondFragmentInLargerUnitWithCalendar_2() {
        assertEquals(0, DateUtils.getFragmentInMinutes(aCalendar, Calendar.SECOND));
    }

    @Test
    public void testSecondFragmentInLargerUnitWithCalendar_3() {
        assertEquals(0, DateUtils.getFragmentInHours(aCalendar, Calendar.SECOND));
    }

    @Test
    public void testSecondFragmentInLargerUnitWithCalendar_4() {
        assertEquals(0, DateUtils.getFragmentInDays(aCalendar, Calendar.SECOND));
    }

    @Test
    public void testSecondFragmentInLargerUnitWithDate_1() {
        assertEquals(0, DateUtils.getFragmentInSeconds(aDate, Calendar.SECOND));
    }

    @Test
    public void testSecondFragmentInLargerUnitWithDate_2() {
        assertEquals(0, DateUtils.getFragmentInMinutes(aDate, Calendar.SECOND));
    }

    @Test
    public void testSecondFragmentInLargerUnitWithDate_3() {
        assertEquals(0, DateUtils.getFragmentInHours(aDate, Calendar.SECOND));
    }

    @Test
    public void testSecondFragmentInLargerUnitWithDate_4() {
        assertEquals(0, DateUtils.getFragmentInDays(aDate, Calendar.SECOND));
    }
}
