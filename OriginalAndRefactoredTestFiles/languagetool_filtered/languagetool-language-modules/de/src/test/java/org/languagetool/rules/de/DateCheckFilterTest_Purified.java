package org.languagetool.rules.de;

import org.junit.Test;
import org.languagetool.rules.FakeRule;
import org.languagetool.rules.RuleMatch;
import java.util.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class DateCheckFilterTest_Purified {

    private final RuleMatch match = new RuleMatch(new FakeRule(), null, 0, 10, "message");

    private final DateCheckFilter filter = new DateCheckFilter();

    private Map<String, String> makeMap(String year, String month, String dayOfMonth, String weekDay) {
        Map<String, String> map = new HashMap<>();
        map.put("year", year);
        map.put("month", month);
        map.put("day", dayOfMonth);
        map.put("weekDay", weekDay);
        return map;
    }

    @Test
    public void testGetDayOfWeek1_1() {
        assertThat(filter.getDayOfWeek("So"), is(1));
    }

    @Test
    public void testGetDayOfWeek1_2() {
        assertThat(filter.getDayOfWeek("Mo"), is(2));
    }

    @Test
    public void testGetDayOfWeek1_3() {
        assertThat(filter.getDayOfWeek("mo"), is(2));
    }

    @Test
    public void testGetDayOfWeek1_4() {
        assertThat(filter.getDayOfWeek("Mon."), is(2));
    }

    @Test
    public void testGetDayOfWeek1_5() {
        assertThat(filter.getDayOfWeek("Montag"), is(2));
    }

    @Test
    public void testGetDayOfWeek1_6() {
        assertThat(filter.getDayOfWeek("montag"), is(2));
    }

    @Test
    public void testGetDayOfWeek1_7() {
        assertThat(filter.getDayOfWeek("Di"), is(3));
    }

    @Test
    public void testGetDayOfWeek1_8() {
        assertThat(filter.getDayOfWeek("Fr"), is(6));
    }

    @Test
    public void testGetDayOfWeek1_9() {
        assertThat(filter.getDayOfWeek("Samstag"), is(7));
    }

    @Test
    public void testGetDayOfWeek1_10() {
        assertThat(filter.getDayOfWeek("Sonnabend"), is(7));
    }

    @Test
    public void testGetMonth_1() {
        assertThat(filter.getMonth("Januar"), is(1));
    }

    @Test
    public void testGetMonth_2() {
        assertThat(filter.getMonth("Jan"), is(1));
    }

    @Test
    public void testGetMonth_3() {
        assertThat(filter.getMonth("Jan."), is(1));
    }

    @Test
    public void testGetMonth_4() {
        assertThat(filter.getMonth("Dezember"), is(12));
    }

    @Test
    public void testGetMonth_5() {
        assertThat(filter.getMonth("Dez"), is(12));
    }

    @Test
    public void testGetMonth_6() {
        assertThat(filter.getMonth("dez"), is(12));
    }

    @Test
    public void testGetMonth_7() {
        assertThat(filter.getMonth("DEZEMBER"), is(12));
    }

    @Test
    public void testGetMonth_8() {
        assertThat(filter.getMonth("dezember"), is(12));
    }
}
