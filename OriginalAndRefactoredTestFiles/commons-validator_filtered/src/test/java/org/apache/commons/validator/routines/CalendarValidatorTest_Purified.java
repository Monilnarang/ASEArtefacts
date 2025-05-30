package org.apache.commons.validator.routines;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.apache.commons.lang3.time.TimeZones;
import org.apache.commons.validator.util.TestTimeZones;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.DefaultLocale;
import org.junitpioneer.jupiter.DefaultTimeZone;

public class CalendarValidatorTest_Purified extends AbstractCalendarValidatorTest {

    private static final int DATE_2005_11_23 = 20051123;

    private static final int TIME_12_03_45 = 120345;

    private CalendarValidator calValidator;

    @BeforeEach
    protected void setUp() {
        calValidator = new CalendarValidator();
        validator = calValidator;
    }

    @Override
    @Test
    @DefaultLocale(country = "UK", language = "en")
    @DefaultTimeZone("GMT")
    public void testFormat_1() {
        assertNull(calValidator.format(null), "null");
    }

    @Override
    @Test
    @DefaultLocale(country = "UK", language = "en")
    @DefaultTimeZone("GMT")
    public void testFormat_2_testMerged_2() {
        final Calendar cal20051231 = createCalendar(TimeZones.GMT, 20051231, 11500);
        final DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
        final String val = df.format(cal20051231.getTime());
        final DateFormat dfus = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);
        final String usval = dfus.format(cal20051231.getTime());
        final String germanPattern = "dd MMM yyyy";
        final DateFormat dedf = new SimpleDateFormat(germanPattern, Locale.GERMAN);
        final String deval = dedf.format(cal20051231.getTime());
        assertEquals(val, calValidator.format(cal20051231), "default");
        assertEquals(usval, calValidator.format(cal20051231, Locale.US), "locale");
        assertEquals("2005-12-31 01:15", calValidator.format(cal20051231, "yyyy-MM-dd HH:mm"), "patternA");
        assertEquals("2005-12-31 GMT", calValidator.format(cal20051231, "yyyy-MM-dd z"), "patternB");
        assertEquals(deval, calValidator.format(cal20051231, germanPattern, Locale.GERMAN), "both");
        final DateFormat dfest = DateFormat.getDateInstance(DateFormat.SHORT);
        dfest.setTimeZone(TestTimeZones.EST);
        final String valest = dfest.format(cal20051231.getTime());
        final DateFormat dfusest = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US);
        dfusest.setTimeZone(TestTimeZones.EST);
        final String valusest = dfusest.format(cal20051231.getTime());
        final DateFormat dedfest = new SimpleDateFormat(germanPattern, Locale.GERMAN);
        dedfest.setTimeZone(TestTimeZones.EST);
        final String devalest = dedfest.format(cal20051231.getTime());
        assertEquals(valest, calValidator.format(cal20051231, TestTimeZones.EST), "EST default");
        assertEquals(valusest, calValidator.format(cal20051231, Locale.US, TestTimeZones.EST), "EST locale");
        final String patternA = "yyyy-MM-dd HH:mm";
        final DateFormat dfA = new SimpleDateFormat(patternA);
        dfA.setTimeZone(TestTimeZones.EST);
        final String valA = dfA.format(cal20051231.getTime());
        assertEquals(valA, calValidator.format(cal20051231, patternA, TestTimeZones.EST), "EST patternA");
        final String patternB = "yyyy-MM-dd z";
        final DateFormat dfB = new SimpleDateFormat(patternB);
        dfB.setTimeZone(TestTimeZones.EST);
        final String valB = dfB.format(cal20051231.getTime());
        assertEquals(valB, calValidator.format(cal20051231, patternB, TestTimeZones.EST), "EST patternB");
        assertEquals(devalest, calValidator.format(cal20051231, germanPattern, Locale.GERMAN, TestTimeZones.EST), "EST both");
    }
}
