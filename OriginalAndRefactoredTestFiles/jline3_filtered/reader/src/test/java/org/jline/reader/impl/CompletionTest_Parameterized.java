package org.jline.reader.impl;

import java.io.IOException;
import java.util.Arrays;
import org.jline.reader.*;
import org.jline.reader.LineReader.Option;
import org.jline.reader.impl.completer.AggregateCompleter;
import org.jline.reader.impl.completer.ArgumentCompleter;
import org.jline.reader.impl.completer.NullCompleter;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.terminal.Size;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class CompletionTest_Parameterized extends ReaderTestSupport {

    @Test
    public void testListAndMenu_2_testMerged_3() throws IOException {
        reader.setCompleter(new StringsCompleter("foo", "foobar"));
        reader.unsetOpt(Option.MENU_COMPLETE);
        reader.unsetOpt(Option.AUTO_LIST);
        reader.unsetOpt(Option.AUTO_MENU);
        reader.unsetOpt(Option.LIST_AMBIGUOUS);
        assertFalse(reader.list);
        assertFalse(reader.menu);
        reader.setOpt(Option.AUTO_LIST);
        assertTrue(reader.list);
        reader.setOpt(Option.LIST_AMBIGUOUS);
        reader.setOpt(Option.AUTO_MENU);
        assertTrue(reader.menu);
    }

    @ParameterizedTest
    @MethodSource("Provider_testCompleteEscape_1_1_1to2_2_4_7_10_13_16_19_22_25_28_31_34")
    public void testCompleteEscape_1_1_1to2_2_4_7_10_13_16_19_22_25_28_31_34(String param1, String param2) throws IOException {
        assertBuffer(param1, new TestBuffer("fo\t"));
    }

    static public Stream<Arguments> Provider_testCompleteEscape_1_1_1to2_2_4_7_10_13_16_19_22_25_28_31_34() {
        return Stream.of(arguments("foo\\ bar ", "fo\t"), arguments("\"foo bar\" ", "\"fo\t"), arguments("foo", "fo\t"), arguments("foo", "fo\t\t\t"), arguments("foo", "fo\t\t"), arguments("foo", "fo\t"), arguments("foo", "fo\t"), arguments("foo", "fo\t\t"), arguments("foo", "fo\t"), arguments("foo", "fo\t\t"), arguments("foo", "fo\t"), arguments("foo", "fo\t\t"), arguments("foo", "fo\t"), arguments("foo", "fo\t\t"), arguments("foobar ", "foobaZ\t"), arguments("foobaZ", "foobaZ\t"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCompletePrefix_1_1to2_2to3")
    public void testCompletePrefix_1_1to2_2to3(String param1, String param2) throws Exception {
        assertLine(param1, new TestBuffer("read an\t\n"));
    }

    static public Stream<Arguments> Provider_testCompletePrefix_1_1to2_2to3() {
        return Stream.of(arguments("read and ", "read an\t\n"), arguments("read and ", "read an\033[D\t\n"), arguments("read and nd", "read and\033[D\033[D\t\n"), arguments("aa_helloWorld12345 ", "a\t\n\n"), arguments("ab_helloWorld123 ", "a\t\t\n\n"));
    }
}
