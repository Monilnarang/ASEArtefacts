package com.graphhopper.reader.osm.conditional;

import org.junit.jupiter.api.Test;
import java.text.ParseException;
import java.util.Calendar;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class DateRangeParserTest_Parameterized {

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

    @ParameterizedTest
    @MethodSource("Provider_testParseConditional_1_5")
    public void testParseConditional_1_5(int param1, int param2, String param3) throws ParseException {
        assertSameDate(param1, Calendar.DECEMBER, param2, param3);
    }

    static public Stream<Arguments> Provider_testParseConditional_1_5() {
        return Stream.of(arguments(2014, 15, "2014 Dec 15"), arguments(1970, 1, "Dec"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testParseConditional_2to4")
    public void testParseConditional_2to4(int param1, int param2, String param3) throws ParseException {
        assertSameDate(param1, Calendar.MARCH, param2, param3);
    }

    static public Stream<Arguments> Provider_testParseConditional_2to4() {
        return Stream.of(arguments(2015, 2, "2015 Mar 2"), arguments(2015, 1, "2015 Mar"), arguments(1970, 31, "Mar 31"));
    }
}
