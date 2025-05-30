package org.jline.reader.impl;

import java.io.IOException;
import java.util.List;
import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;
import org.jline.utils.AttributedString;
import org.junit.jupiter.api.Test;

public class ColonCommandCompletionTest_Purified extends ReaderTestSupport {

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

    @Test
    public void testColonCommandCompletion_1() throws IOException {
        assertBuffer(":paste", new TestBuffer(":p\t\t"));
    }

    @Test
    public void testColonCommandCompletion_2() throws IOException {
        assertBuffer(":power ", new TestBuffer(":po\t"));
    }
}
