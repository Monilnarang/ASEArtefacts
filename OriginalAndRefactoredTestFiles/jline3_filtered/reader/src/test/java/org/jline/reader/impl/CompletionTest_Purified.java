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

public class CompletionTest_Purified extends ReaderTestSupport {

    @Test
    public void testCompleteEscape_1() throws IOException {
        assertBuffer("foo\\ bar ", new TestBuffer("fo\t"));
    }

    @Test
    public void testCompleteEscape_2() throws IOException {
        assertBuffer("\"foo bar\" ", new TestBuffer("\"fo\t"));
    }

    @Test
    public void testListAndMenu_1() throws IOException {
        assertBuffer("foo", new TestBuffer("fo\t"));
    }

    @Test
    public void testListAndMenu_34() throws IOException {
        assertBuffer("foo", new TestBuffer("fo\t\t\t"));
    }

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

    @Test
    public void testListAndMenu_4() throws IOException {
        assertBuffer("foo", new TestBuffer("fo\t\t"));
    }

    @Test
    public void testListAndMenu_7() throws IOException {
        assertBuffer("foo", new TestBuffer("fo\t"));
    }

    @Test
    public void testListAndMenu_10() throws IOException {
        assertBuffer("foo", new TestBuffer("fo\t"));
    }

    @Test
    public void testListAndMenu_13() throws IOException {
        assertBuffer("foo", new TestBuffer("fo\t\t"));
    }

    @Test
    public void testListAndMenu_16() throws IOException {
        assertBuffer("foo", new TestBuffer("fo\t"));
    }

    @Test
    public void testListAndMenu_19() throws IOException {
        assertBuffer("foo", new TestBuffer("fo\t\t"));
    }

    @Test
    public void testListAndMenu_22() throws IOException {
        assertBuffer("foo", new TestBuffer("fo\t"));
    }

    @Test
    public void testListAndMenu_25() throws IOException {
        assertBuffer("foo", new TestBuffer("fo\t\t"));
    }

    @Test
    public void testListAndMenu_28() throws IOException {
        assertBuffer("foo", new TestBuffer("fo\t"));
    }

    @Test
    public void testListAndMenu_31() throws IOException {
        assertBuffer("foo", new TestBuffer("fo\t\t"));
    }

    @Test
    public void testTypoMatcher_1() throws Exception {
        assertBuffer("foobar ", new TestBuffer("foobaZ\t"));
    }

    @Test
    public void testTypoMatcher_2() throws Exception {
        assertBuffer("foobaZ", new TestBuffer("foobaZ\t"));
    }

    @Test
    public void testCompletePrefix_1() throws Exception {
        assertLine("read and ", new TestBuffer("read an\t\n"));
    }

    @Test
    public void testCompletePrefix_2() throws Exception {
        assertLine("read and ", new TestBuffer("read an\033[D\t\n"));
    }

    @Test
    public void testCompletePrefix_3() throws Exception {
        assertLine("read and nd", new TestBuffer("read and\033[D\033[D\t\n"));
    }

    @Test
    public void testMenuOrder_1() {
        assertLine("aa_helloWorld12345 ", new TestBuffer("a\t\n\n"));
    }

    @Test
    public void testMenuOrder_2() {
        assertLine("ab_helloWorld123 ", new TestBuffer("a\t\t\n\n"));
    }
}
