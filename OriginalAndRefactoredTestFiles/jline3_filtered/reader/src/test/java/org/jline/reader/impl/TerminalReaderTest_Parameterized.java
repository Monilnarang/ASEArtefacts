package org.jline.reader.impl;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import org.jline.keymap.KeyMap;
import org.jline.reader.Binding;
import org.jline.reader.Expander;
import org.jline.reader.History;
import org.jline.reader.LineReader;
import org.jline.reader.LineReader.Option;
import org.jline.reader.Reference;
import org.jline.reader.Widget;
import org.jline.reader.impl.history.DefaultHistory;
import org.jline.utils.Curses;
import org.jline.utils.InfoCmp.Capability;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TerminalReaderTest_Parameterized extends ReaderTestSupport {

    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        reader.setHistory(createSeededHistory());
    }

    private History createSeededHistory() {
        History history = new DefaultHistory();
        history.add("dir");
        history.add("cd c:\\");
        history.add("mkdir monkey");
        return history;
    }

    private void assertLineAndHistory(String expectedLine, String expectedHistory, TestBuffer input, boolean expandEvents, String... historyItems) {
        DefaultHistory history = new DefaultHistory();
        reader.setHistory(history);
        if (historyItems != null) {
            for (String historyItem : historyItems) {
                history.add(historyItem);
            }
        }
        if (expandEvents) {
            reader.unsetOpt(Option.DISABLE_EVENT_EXPANSION);
        } else {
            reader.setOpt(Option.DISABLE_EVENT_EXPANSION);
        }
        assertLine(expectedLine, input, false);
        history.previous();
        assertEquals(expectedHistory, history.current());
    }

    protected void assertExpansionIllegalArgumentException(Expander expander, History history, String event) throws Exception {
        try {
            expander.expandHistory(history, event);
            fail("Expected IllegalArgumentException for " + event);
        } catch (IllegalArgumentException e) {
            assertEquals(event + ": event not found", e.getMessage());
        }
    }

    @Test
    public void testReadlineWithMask_2() throws Exception {
        assertTrue(this.out.toString().contains("*************"));
    }

    @Test
    public void testIllegalExpansionDoesntCrashReadLine_2() throws Exception {
        DefaultHistory history = new DefaultHistory();
        reader.setHistory(history);
        assertEquals(1, history.size());
    }

    @Test
    public void testStoringHistory_2_testMerged_2() throws Exception {
        DefaultHistory history = new DefaultHistory();
        reader.setHistory(history);
        history.previous();
        assertEquals("foo ! bar", history.current());
        history = new DefaultHistory();
        assertEquals("cd c:\\\\docs", history.current());
    }

    @Test
    public void testBell_1() throws Exception {
        assertEquals(0, out.size(), "out should not have received bell");
    }

    @Test
    public void testBell_2() throws Exception {
        String bell = Curses.tputs(terminal.getStringCapability(Capability.bell));
        assertEquals(bell, out.toString(), "out should have received bell");
    }

    @ParameterizedTest
    @MethodSource("Provider_testReadlineWithMask_1_1_1_3")
    public void testReadlineWithMask_1_1_1_3(String param1, String param2) throws Exception {
        assertLine(param1, new TestBuffer("Sample String\n"));
    }

    static public Stream<Arguments> Provider_testReadlineWithMask_1_1_1_3() {
        return Stream.of(arguments("Sample String", "Sample String\n"), arguments("!f", "!f\n"), arguments("foo ! bar", "foo ! bar\n"), arguments("cd c:\\docs", "cd c:\\\\docs\n"));
    }
}
