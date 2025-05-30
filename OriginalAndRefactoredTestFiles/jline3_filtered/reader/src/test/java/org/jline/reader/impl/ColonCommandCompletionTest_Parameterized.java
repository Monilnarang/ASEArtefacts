package org.jline.reader.impl;

import java.io.IOException;
import java.util.List;
import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;
import org.jline.utils.AttributedString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ColonCommandCompletionTest_Parameterized extends ReaderTestSupport {

    private static class ColonCommandCompleter implements Completer {

        String[] commands;

        public ColonCommandCompleter(String... commands) {
            this.commands = commands;
        }

        @Override
        public void complete(LineReader reader, ParsedLine line, List<Candidate> candidates) {
            for (String c : commands) {
                candidates.add(new Candidate(":" + AttributedString.stripAnsi(c), c, null, null, null, null, true));
            }
        }
    }

    @ParameterizedTest
    @MethodSource("Provider_testColonCommandCompletion_1to2")
    public void testColonCommandCompletion_1to2(String param1, String param2) throws IOException {
        assertBuffer(param1, new TestBuffer(":p\t\t"));
    }

    static public Stream<Arguments> Provider_testColonCommandCompletion_1to2() {
        return Stream.of(arguments(":paste", ":p\t\t"), arguments(":power ", ":po\t"));
    }
}
