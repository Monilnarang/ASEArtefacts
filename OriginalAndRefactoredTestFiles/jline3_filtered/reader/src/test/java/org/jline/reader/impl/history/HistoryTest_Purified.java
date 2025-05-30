package org.jline.reader.impl.history;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.jline.reader.History;
import org.jline.reader.LineReader;
import org.jline.reader.impl.ReaderTestSupport;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class HistoryTest_Purified extends ReaderTestSupport {

    private DefaultHistory history;

    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        history = new DefaultHistory(reader);
    }

    @AfterEach
    public void tearDown() {
        history = null;
    }

    private void assertHistoryContains(final int offset, final String... items) {
        assertEquals(items.length, history.size());
        int i = 0;
        for (History.Entry entry : history) {
            assertEquals(offset + i, entry.index());
            assertEquals(items[i++], entry.line());
        }
    }

    @Test
    public void testAdd_1() {
        assertEquals(0, history.size());
    }

    @Test
    public void testAdd_2_testMerged_2() {
        history.add("test");
        assertEquals(1, history.size());
        assertEquals("test", history.get(0));
        assertEquals(1, history.index());
    }

    @Test
    public void testOffset_1() {
        assertEquals(0, history.size());
    }

    @Test
    public void testOffset_2() {
        assertEquals(0, history.index());
    }

    @Test
    public void testOffset_3_testMerged_3() {
        history.add("a");
        history.add("b");
        history.add("c");
        history.add("d");
        history.add("e");
        assertEquals(5, history.size());
        assertEquals(5, history.index());
        history.add("f");
        assertEquals(6, history.index());
        assertEquals("f", history.get(5));
    }

    @Test
    public void testOffset_5() {
        assertHistoryContains(0, "a", "b", "c", "d", "e");
    }

    @Test
    public void testOffset_8() {
        assertHistoryContains(1, "b", "c", "d", "e", "f");
    }
}
