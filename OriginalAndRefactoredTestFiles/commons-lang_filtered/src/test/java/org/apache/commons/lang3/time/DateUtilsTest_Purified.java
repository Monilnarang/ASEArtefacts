package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.TimeZone;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.DefaultLocale;
import org.junitpioneer.jupiter.ReadsDefaultLocale;
import org.junitpioneer.jupiter.WritesDefaultLocale;

@ReadsDefaultLocale
@WritesDefaultLocale
public class DateUtilsTest_Purified extends AbstractLangTest {

    private static final TimeZone TIME_ZONE_DEFAULT = TimeZone.getDefault();

    private static final TimeZone TIME_ZONE_MET = TimeZone.getTimeZone("MET");

    private static Date BASE_DATE;

    private static void assertCalendarsEquals(final String message, final Calendar cal1, final Calendar cal2, final long delta) {
        assertFalse(Math.abs(cal1.getTime().getTime() - cal2.getTime().getTime()) > delta, message + " expected " + cal1.getTime() + " but got " + cal2.getTime());
    }

    private static void assertWeekIterator(final Iterator<?> it, final Calendar start) {
        final Calendar end = (Calendar) start.clone();
        end.add(Calendar.DATE, 6);
        assertWeekIterator(it, start, end);
    }

    private static void assertWeekIterator(final Iterator<?> it, final Calendar start, final Calendar end) {
        Calendar cal = (Calendar) it.next();
        assertCalendarsEquals("", start, cal, 0);
        Calendar last;
        int count = 1;
        while (it.hasNext()) {
            assertCalendarsEquals("", cal, DateUtils.truncate(cal, Calendar.DATE), 0);
            last = cal;
            cal = (Calendar) it.next();
            count++;
            last.add(Calendar.DATE, 1);
            assertCalendarsEquals("", last, cal, 0);
        }
        assertFalse(count % 7 != 0, "There were " + count + " days in this iterator");
        assertCalendarsEquals("", end, cal, 0);
    }

    private static void assertWeekIterator(final Iterator<?> it, final Date start, final Date end) {
        final Calendar calStart = Calendar.getInstance();
        calStart.setTime(start);
        final Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(end);
        assertWeekIterator(it, calStart, calEnd);
    }

    @BeforeAll
    public static void classSetup() {
        final GregorianCalendar cal = new GregorianCalendar(2000, 6, 5, 4, 3, 2);
        cal.set(Calendar.MILLISECOND, 1);
        BASE_DATE = cal.getTime();
    }

    private DateFormat dateParser;

    private DateFormat dateTimeParser;

    private Date dateAmPm1;

    private Date dateAmPm2;

    private Date dateAmPm3;

    private Date dateAmPm4;

    private Date date0;

    private Date date1;

    private Date date2;

    private Date date3;

    private Date date4;

    private Date date5;

    private Date date6;

    private Date date7;

    private Date date8;

    private Calendar calAmPm1;

    private Calendar calAmPm2;

    private Calendar calAmPm3;

    private Calendar calAmPm4;

    private Calendar cal1;

    private Calendar cal2;

    private Calendar cal3;

    private Calendar cal4;

    private Calendar cal5;

    private Calendar cal6;

    private Calendar cal7;

    private Calendar cal8;

    @AfterEach
    public void afterEachResetTimeZones() {
        TimeZone.setDefault(TIME_ZONE_DEFAULT);
        dateTimeParser.setTimeZone(TIME_ZONE_DEFAULT);
    }

    private void assertDate(final Date date, final int year, final int month, final int day, final int hour, final int min, final int sec, final int mil) {
        final GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        assertEquals(year, cal.get(Calendar.YEAR));
        assertEquals(month, cal.get(Calendar.MONTH));
        assertEquals(day, cal.get(Calendar.DAY_OF_MONTH));
        assertEquals(hour, cal.get(Calendar.HOUR_OF_DAY));
        assertEquals(min, cal.get(Calendar.MINUTE));
        assertEquals(sec, cal.get(Calendar.SECOND));
        assertEquals(mil, cal.get(Calendar.MILLISECOND));
    }

    @BeforeEach
    public void setUp() throws Exception {
        dateParser = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
        dateTimeParser = new SimpleDateFormat("MMM dd, yyyy H:mm:ss.SSS", Locale.ENGLISH);
        dateAmPm1 = dateTimeParser.parse("February 3, 2002 01:10:00.000");
        dateAmPm2 = dateTimeParser.parse("February 3, 2002 11:10:00.000");
        dateAmPm3 = dateTimeParser.parse("February 3, 2002 13:10:00.000");
        dateAmPm4 = dateTimeParser.parse("February 3, 2002 19:10:00.000");
        date0 = dateTimeParser.parse("February 3, 2002 12:34:56.789");
        date1 = dateTimeParser.parse("February 12, 2002 12:34:56.789");
        date2 = dateTimeParser.parse("November 18, 2001 1:23:11.321");
        try {
            TimeZone.setDefault(TIME_ZONE_MET);
            dateTimeParser.setTimeZone(TIME_ZONE_MET);
            date3 = dateTimeParser.parse("March 30, 2003 05:30:45.000");
            date4 = dateTimeParser.parse("March 30, 2003 01:10:00.000");
            date5 = dateTimeParser.parse("March 30, 2003 01:40:00.000");
            date6 = dateTimeParser.parse("March 30, 2003 02:10:00.000");
            date7 = dateTimeParser.parse("March 30, 2003 02:40:00.000");
            date8 = dateTimeParser.parse("October 26, 2003 05:30:45.000");
        } finally {
            dateTimeParser.setTimeZone(TIME_ZONE_DEFAULT);
            TimeZone.setDefault(TIME_ZONE_DEFAULT);
        }
        calAmPm1 = Calendar.getInstance();
        calAmPm1.setTime(dateAmPm1);
        calAmPm2 = Calendar.getInstance();
        calAmPm2.setTime(dateAmPm2);
        calAmPm3 = Calendar.getInstance();
        calAmPm3.setTime(dateAmPm3);
        calAmPm4 = Calendar.getInstance();
        calAmPm4.setTime(dateAmPm4);
        cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        try {
            TimeZone.setDefault(TIME_ZONE_MET);
            cal3 = Calendar.getInstance();
            cal3.setTime(date3);
            cal4 = Calendar.getInstance();
            cal4.setTime(date4);
            cal5 = Calendar.getInstance();
            cal5.setTime(date5);
            cal6 = Calendar.getInstance();
            cal6.setTime(date6);
            cal7 = Calendar.getInstance();
            cal7.setTime(date7);
            cal8 = Calendar.getInstance();
            cal8.setTime(date8);
        } finally {
            TimeZone.setDefault(TIME_ZONE_DEFAULT);
        }
    }

    @Test
    public void testConstructor_1() {
        assertNotNull(new DateUtils());
    }

    @Test
    public void testConstructor_2_testMerged_2() {
        final Constructor<?>[] cons = DateUtils.class.getDeclaredConstructors();
        assertEquals(1, cons.length);
        assertTrue(Modifier.isPublic(cons[0].getModifiers()));
    }

    @Test
    public void testConstructor_4() {
        assertTrue(Modifier.isPublic(DateUtils.class.getModifiers()));
    }

    @Test
    public void testConstructor_5() {
        assertFalse(Modifier.isFinal(DateUtils.class.getModifiers()));
    }
}
