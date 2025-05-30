package org.languagetool.rules.ar.filters;

import org.junit.Test;
import org.languagetool.rules.FakeRule;
import org.languagetool.rules.RuleMatch;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ArabicDateCheckFilterTest_Parameterized {

    private final RuleMatch match = new RuleMatch(new FakeRule(), null, 0, 10, "message");

    private final ArabicDateCheckFilter filter = new ArabicDateCheckFilter();

    private Map<String, String> makeMap(String year, String month, String dayOfMonth, String weekDay) {
        Map<String, String> map = new HashMap<>();
        map.put("year", year);
        map.put("month", month);
        map.put("day", dayOfMonth);
        map.put("weekDay", weekDay);
        return map;
    }

    @Test
    public void testAccept_1() {
        assertNull(filter.acceptRuleMatch(match, makeMap("2022", "3", "12", "السبت"), -1, null, null));
    }

    @Test
    public void testAccept_2() {
        assertNotNull(filter.acceptRuleMatch(match, makeMap("2022", "3", "12", "الأحد"), -1, null, null));
    }

    @Test
    public void testGetDayOfWeek1_1() {
        assertThat(filter.getDayOfWeek("الأحد"), is(Calendar.SUNDAY));
    }

    @Test
    public void testGetDayOfWeek1_2() {
        assertThat(filter.getDayOfWeek("الإثنين"), is(Calendar.MONDAY));
    }

    @Test
    public void testGetDayOfWeek1_3() {
        assertThat(filter.getDayOfWeek("الثلاثاء"), is(Calendar.TUESDAY));
    }

    @Test
    public void testGetDayOfWeek1_4() {
        assertThat(filter.getDayOfWeek("الأربعاء"), is(Calendar.WEDNESDAY));
    }

    @Test
    public void testGetDayOfWeek1_5() {
        assertThat(filter.getDayOfWeek("الخميس"), is(Calendar.THURSDAY));
    }

    @Test
    public void testGetDayOfWeek1_6() {
        assertThat(filter.getDayOfWeek("الجمعة"), is(Calendar.FRIDAY));
    }

    @Test
    public void testGetDayOfWeek1_7() {
        assertThat(filter.getDayOfWeek("السبت"), is(Calendar.SATURDAY));
    }

    @Test
    public void testGetDayOfWeek1_8() {
        assertThat(filter.getDayOfWeek(Calendar.SUNDAY), is("الأحد"));
    }

    @Test
    public void testGetDayOfWeek1_9() {
        assertThat(filter.getDayOfWeek(Calendar.MONDAY), is("الإثنين"));
    }

    @Test
    public void testGetDayOfWeek1_10() {
        assertThat(filter.getDayOfWeek(Calendar.TUESDAY), is("الثلاثاء"));
    }

    @Test
    public void testGetDayOfWeek1_11() {
        assertThat(filter.getDayOfWeek(Calendar.WEDNESDAY), is("الأربعاء"));
    }

    @Test
    public void testGetDayOfWeek1_12() {
        assertThat(filter.getDayOfWeek(Calendar.THURSDAY), is("الخميس"));
    }

    @Test
    public void testGetDayOfWeek1_13() {
        assertThat(filter.getDayOfWeek(Calendar.FRIDAY), is("الجمعة"));
    }

    @Test
    public void testGetDayOfWeek1_14() {
        assertThat(filter.getDayOfWeek(Calendar.SATURDAY), is("السبت"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetMonth_1to8")
    public void testGetMonth_1to8(String param1, int param2) {
        assertThat(filter.getMonth(param1), is(param2));
    }

    static public Stream<Arguments> Provider_testGetMonth_1to8() {
        return Stream.of(arguments("جانفي", 1), arguments("جانفييه", 1), arguments("يناير", 1), arguments("ديسمبر", 12), arguments("كانون الأول", 12), arguments("كانون أول", 12), arguments("أبريل", 4), arguments("نيسان", 4));
    }
}
