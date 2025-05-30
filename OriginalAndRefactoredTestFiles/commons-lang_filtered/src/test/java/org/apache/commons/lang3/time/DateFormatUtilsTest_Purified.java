package org.apache.commons.lang3.time;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.apache.commons.lang3.AbstractLangTest;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.DefaultLocale;
import org.junitpioneer.jupiter.DefaultTimeZone;

@SuppressWarnings("deprecation")
public class DateFormatUtilsTest_Purified extends AbstractLangTest {

    private void assertFormats(final String expectedValue, final String pattern, final TimeZone timeZone, final Calendar cal) {
        assertEquals(expectedValue, DateFormatUtils.format(cal.getTime(), pattern, timeZone));
        assertEquals(expectedValue, DateFormatUtils.format(cal.getTime().getTime(), pattern, timeZone));
        assertEquals(expectedValue, DateFormatUtils.format(cal, pattern, timeZone));
    }

    private Calendar createFebruaryTestDate(final TimeZone timeZone) {
        final Calendar cal = Calendar.getInstance(timeZone);
        cal.set(2002, Calendar.FEBRUARY, 23, 9, 11, 12);
        return cal;
    }

    private Calendar createJuneTestDate(final TimeZone timeZone) {
        final Calendar cal = Calendar.getInstance(timeZone);
        cal.set(2003, Calendar.JUNE, 8, 10, 11, 12);
        return cal;
    }

    @Test
    public void testConstructor_1() {
        assertNotNull(new DateFormatUtils());
    }

    @Test
    public void testConstructor_2_testMerged_2() {
        final Constructor<?>[] cons = DateFormatUtils.class.getDeclaredConstructors();
        assertEquals(1, cons.length);
        assertTrue(Modifier.isPublic(cons[0].getModifiers()));
    }

    @Test
    public void testConstructor_4() {
        assertTrue(Modifier.isPublic(DateFormatUtils.class.getModifiers()));
    }

    @Test
    public void testConstructor_5() {
        assertFalse(Modifier.isFinal(DateFormatUtils.class.getModifiers()));
    }
}
