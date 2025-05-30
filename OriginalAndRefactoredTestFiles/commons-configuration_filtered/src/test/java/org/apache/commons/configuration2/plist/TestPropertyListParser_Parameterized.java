package org.apache.commons.configuration2.plist;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.io.Reader;
import java.util.Calendar;
import java.util.SimpleTimeZone;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestPropertyListParser_Parameterized {

    private final PropertyListParser parser = new PropertyListParser((Reader) null);

    @Test
    public void testRemoveQuotes_5() {
        assertNull(parser.removeQuotes(null));
    }

    @ParameterizedTest
    @MethodSource("Provider_testRemoveQuotes_1to4")
    public void testRemoveQuotes_1to4(String param1, String param2) {
        assertEquals(param1, parser.removeQuotes(param2));
    }

    static public Stream<Arguments> Provider_testRemoveQuotes_1to4() {
        return Stream.of(arguments("abc", "abc"), arguments("abc", "\"abc\""), arguments("", "\"\""), arguments("", ""));
    }

    @ParameterizedTest
    @MethodSource("Provider_testUnescapeQuotes_1to2")
    public void testUnescapeQuotes_1to2(String param1, String param2) {
        assertEquals(param1, parser.unescapeQuotes(param2));
    }

    static public Stream<Arguments> Provider_testUnescapeQuotes_1to2() {
        return Stream.of(arguments("aaa\"bbb\"ccc", "aaa\"bbb\"ccc"), arguments("aaa\"bbb\"ccc", "aaa\\\"bbb\\\"ccc"));
    }
}
