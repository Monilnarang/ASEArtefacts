package org.apache.commons.cli;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class UtilTest_Parameterized {

    @Test
    public void testStripLeadingAndTrailingQuotes_1() {
        assertNull(Util.stripLeadingAndTrailingQuotes(null));
    }

    @Test
    public void testStripLeadingHyphens_4() {
        assertNull(Util.stripLeadingHyphens(null));
    }

    @ParameterizedTest
    @MethodSource("Provider_testStripLeadingAndTrailingQuotes_2to7")
    public void testStripLeadingAndTrailingQuotes_2to7(String param1, String param2) {
        assertEquals(param1, Util.stripLeadingAndTrailingQuotes(param2));
    }

    static public Stream<Arguments> Provider_testStripLeadingAndTrailingQuotes_2to7() {
        return Stream.of(arguments("", ""), arguments("foo", "\"foo\""), arguments("foo \"bar\"", "foo \"bar\""), arguments("\"foo\" bar", "\"foo\" bar"), arguments("\"foo\" and \"bar\"", "\"foo\" and \"bar\""), arguments("\"", "\""));
    }

    @ParameterizedTest
    @MethodSource("Provider_testStripLeadingHyphens_1to3")
    public void testStripLeadingHyphens_1to3(String param1, String param2) {
        assertEquals(param1, Util.stripLeadingHyphens(param2));
    }

    static public Stream<Arguments> Provider_testStripLeadingHyphens_1to3() {
        return Stream.of(arguments("f", "-f"), arguments("foo", "--foo"), arguments("-foo", "---foo"));
    }
}
