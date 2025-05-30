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

public class TerminalReaderTest_Purified extends ReaderTestSupport {

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
    public void testReadlineWithMask_1() throws Exception {
        assertLine("Sample String", new TestBuffer("Sample String\n"));
    }

    @Test
    public void testReadlineWithMask_2() throws Exception {
        assertTrue(this.out.toString().contains("*************"));
    }

    @Test
    public void testIllegalExpansionDoesntCrashReadLine_1() throws Exception {
        assertLine("!f", new TestBuffer("!f\n"));
    }

    @Test
    public void testIllegalExpansionDoesntCrashReadLine_2() throws Exception {
        DefaultHistory history = new DefaultHistory();
        reader.setHistory(history);
        assertEquals(1, history.size());
    }

    @Test
    public void testStoringHistory_1() throws Exception {
        assertLine("foo ! bar", new TestBuffer("foo ! bar\n"));
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
    public void testStoringHistory_3() throws Exception {
        assertLine("cd c:\\docs", new TestBuffer("cd c:\\\\docs\n"));
    }

    @Test
    public void testExpansionAndHistoryWithEscapes_1() throws Exception {
        assertLineAndHistory("echo ab!ef", "echo ab\\!ef", new TestBuffer("echo ab\\!ef\n"), true, "cd");
    }

    @Test
    public void testExpansionAndHistoryWithEscapes_2() throws Exception {
        assertLineAndHistory("echo ab\\!ef", "echo ab\\!ef", new TestBuffer("echo ab\\!ef\n"), false, "cd");
    }

    @Test
    public void testExpansionAndHistoryWithEscapes_3() throws Exception {
        assertLineAndHistory("echo ab!!ef", "echo ab\\!\\!ef", new TestBuffer("echo ab\\!\\!ef\n"), true, "cd");
    }

    @Test
    public void testExpansionAndHistoryWithEscapes_4() throws Exception {
        assertLineAndHistory("echo ab\\!\\!ef", "echo ab\\!\\!ef", new TestBuffer("echo ab\\!\\!ef\n"), false, "cd");
    }

    @Test
    public void testExpansionAndHistoryWithEscapes_5() throws Exception {
        assertLineAndHistory("echo abcdef", "echo abcdef", new TestBuffer("echo ab!!ef\n"), true, "cd");
    }

    @Test
    public void testExpansionAndHistoryWithEscapes_6() throws Exception {
        assertLineAndHistory("echo ab!!ef", "echo ab!!ef", new TestBuffer("echo ab!!ef\n"), false, "cd");
    }

    @Test
    public void testExpansionAndHistoryWithEscapes_7() throws Exception {
        assertLineAndHistory("echo abcGdef", "echo abc\\Gdef", new TestBuffer("echo abc\\Gdef\n"), true, "cd");
    }

    @Test
    public void testExpansionAndHistoryWithEscapes_8() throws Exception {
        assertLineAndHistory("echo abc\\Gdef", "echo abc\\Gdef", new TestBuffer("echo abc\\Gdef\n"), false, "cd");
    }

    @Test
    public void testExpansionAndHistoryWithEscapes_9() throws Exception {
        assertLineAndHistory("^abc^def", "\\^abc^def", new TestBuffer("\\^abc^def\n"), true, "echo abc");
    }

    @Test
    public void testExpansionAndHistoryWithEscapes_10() throws Exception {
        assertLineAndHistory("\\^abc^def", "\\^abc^def", new TestBuffer("\\^abc^def\n"), false, "echo abc");
    }

    @Test
    public void testExpansionAndHistoryWithEscapes_11() throws Exception {
        assertLineAndHistory("echo def", "echo def", new TestBuffer("^abc^def\n"), true, "echo abc");
    }

    @Test
    public void testExpansionAndHistoryWithEscapes_12() throws Exception {
        assertLineAndHistory("^abc^def", "^abc^def", new TestBuffer("^abc^def\n"), false, "echo abc");
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
}
