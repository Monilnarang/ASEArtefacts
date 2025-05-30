package com.graphhopper.reader.osm.conditional;

import org.junit.jupiter.api.Test;
import java.text.ParseException;
import java.util.Calendar;
import static org.junit.jupiter.api.Assertions.*;

public class DateRangeParserTest_Purified {

    final DateRangeParser dateRangeParser = new DateRangeParser();

    private void assertSameDate(int year, int month, int day, String dateString) throws ParseException {
        Calendar expected = getCalendar(year, month, day);
        ParsedCalendar actualParsed = DateRangeParser.parseDateString(dateString);
        Calendar actual = actualParsed.parsedCalendar;
        assertEquals(expected.get(Calendar.YEAR), actual.get(Calendar.YEAR));
        assertEquals(expected.get(Calendar.MONTH), actual.get(Calendar.MONTH));
        assertEquals(expected.get(Calendar.DAY_OF_MONTH), actual.get(Calendar.DAY_OF_MONTH));
    }

    protected Calendar getCalendar(int year, int month, int day) {
        Calendar calendar = DateRangeParser.createCalendar();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar;
    }

    @Test
    public void testParseConditional_1() throws ParseException {
        assertSameDate(2014, Calendar.DECEMBER, 15, "2014 Dec 15");
    }

    @Test
    public void testParseConditional_2() throws ParseException {
        assertSameDate(2015, Calendar.MARCH, 2, "2015 Mar 2");
    }

    @Test
    public void testParseConditional_3() throws ParseException {
        assertSameDate(2015, Calendar.MARCH, 1, "2015 Mar");
    }

    @Test
    public void testParseConditional_4() throws ParseException {
        assertSameDate(1970, Calendar.MARCH, 31, "Mar 31");
    }

    @Test
    public void testParseConditional_5() throws ParseException {
        assertSameDate(1970, Calendar.DECEMBER, 1, "Dec");
    }
}
