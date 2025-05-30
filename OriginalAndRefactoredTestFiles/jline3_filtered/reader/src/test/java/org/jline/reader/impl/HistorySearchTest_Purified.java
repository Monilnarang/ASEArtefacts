package org.jline.reader.impl;

import java.io.ByteArrayInputStream;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.impl.history.DefaultHistory;
import org.junit.jupiter.api.Test;
import static org.jline.keymap.KeyMap.translate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class HistorySearchTest_Purified extends ReaderTestSupport {

    private DefaultHistory setupHistory() {
        DefaultHistory history = new DefaultHistory();
        reader.setVariable(LineReader.HISTORY_SIZE, 10);
        reader.setHistory(history);
        history.add("foo");
        history.add("fiddle");
        history.add("faddle");
        return history;
    }

    @Test
    public void testReverseHistorySearch_1_testMerged_1() throws Exception {
        String readLineResult;
        readLineResult = reader.readLine();
        assertEquals("faddle", readLineResult);
        assertEquals("foo", readLineResult);
        assertEquals("fiddle", readLineResult);
    }

    @Test
    public void testReverseHistorySearch_2_testMerged_2() throws Exception {
        DefaultHistory history = setupHistory();
        assertEquals(3, history.size());
        assertEquals(4, history.size());
        assertEquals(5, history.size());
    }

    @Test
    public void testForwardHistorySearch_1_testMerged_1() throws Exception {
        String readLineResult;
        readLineResult = reader.readLine();
        assertEquals("fiddle", readLineResult);
        assertEquals("faddle", readLineResult);
    }

    @Test
    public void testForwardHistorySearch_2_testMerged_2() throws Exception {
        DefaultHistory history = setupHistory();
        assertEquals(4, history.size());
        assertEquals(5, history.size());
        assertEquals(6, history.size());
    }

    @Test
    public void testSearchHistoryAfterHittingEnd_1() throws Exception {
        String readLineResult;
        readLineResult = reader.readLine();
        assertEquals("fiddle", readLineResult);
    }

    @Test
    public void testSearchHistoryAfterHittingEnd_2() throws Exception {
        DefaultHistory history = setupHistory();
        assertEquals(4, history.size());
    }

    @Test
    public void testSearchHistoryWithNoMatches_1() throws Exception {
        String readLineResult;
        readLineResult = reader.readLine();
        assertEquals("x", readLineResult);
    }

    @Test
    public void testSearchHistoryWithNoMatches_2() throws Exception {
        DefaultHistory history = setupHistory();
        assertEquals(4, history.size());
    }
}
