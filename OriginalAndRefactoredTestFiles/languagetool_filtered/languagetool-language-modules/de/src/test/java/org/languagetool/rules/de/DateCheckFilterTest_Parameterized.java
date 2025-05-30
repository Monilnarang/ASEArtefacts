package org.languagetool.rules.de;

import org.junit.Test;
import org.languagetool.rules.FakeRule;
import org.languagetool.rules.RuleMatch;
import java.util.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class DateCheckFilterTest_Parameterized {

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

    @ParameterizedTest
    @MethodSource("Provider_testGetDayOfWeek1_1to10")
    public void testGetDayOfWeek1_1to10(String param1, int param2) {
        assertThat(filter.getDayOfWeek(param1), is(param2));
    }

    static public Stream<Arguments> Provider_testGetDayOfWeek1_1to10() {
        return Stream.of(arguments("So", 1), arguments("Mo", 2), arguments("mo", 2), arguments("Mon.", 2), arguments("Montag", 2), arguments("montag", 2), arguments("Di", 3), arguments("Fr", 6), arguments("Samstag", 7), arguments("Sonnabend", 7));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetMonth_1to8")
    public void testGetMonth_1to8(String param1, int param2) {
        assertThat(filter.getMonth(param1), is(param2));
    }

    static public Stream<Arguments> Provider_testGetMonth_1to8() {
        return Stream.of(arguments("Januar", 1), arguments("Jan", 1), arguments("Jan.", 1), arguments("Dezember", 12), arguments("Dez", 12), arguments("dez", 12), arguments("DEZEMBER", 12), arguments("dezember", 12));
    }
}
