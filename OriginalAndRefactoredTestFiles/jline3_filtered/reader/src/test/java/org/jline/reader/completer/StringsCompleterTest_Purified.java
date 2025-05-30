package org.jline.reader.completer;

import org.jline.reader.LineReader;
import org.jline.reader.impl.DefaultParser;
import org.jline.reader.impl.ReaderTestSupport;
import org.jline.reader.impl.completer.StringsCompleter;
import org.junit.jupiter.api.Test;

public class StringsCompleterTest_Purified extends ReaderTestSupport {

    @Test
    public void test1_1() throws Exception {
        assertBuffer("foo ", new TestBuffer("f").tab());
    }

    @Test
    public void test1_2() throws Exception {
        assertBuffer("ba", new TestBuffer("b").tab());
    }

    @Test
    public void test1_3() throws Exception {
        assertBuffer("ba", new TestBuffer("ba").tab());
    }

    @Test
    public void test1_4() throws Exception {
        assertBuffer("baz ", new TestBuffer("baz").tab());
    }

    @Test
    public void escapeCharsNull_1() throws Exception {
        assertBuffer("'foo bar' ", new TestBuffer("f").tab());
    }

    @Test
    public void escapeCharsNull_2() throws Exception {
        assertBuffer("'foo bar' ", new TestBuffer("'f").tab());
    }

    @Test
    public void escapeCharsNull_3() throws Exception {
        assertBuffer("foo'b", new TestBuffer("foo'b").tab());
    }

    @Test
    public void escapeCharsNull_4() throws Exception {
        assertBuffer("bar'f", new TestBuffer("bar'f").tab());
    }

    @Test
    public void middleQuotesEscapeCharsNull_1() throws Exception {
        assertBuffer("/foo?name='foo ", new TestBuffer("/f").tab());
    }

    @Test
    public void middleQuotesEscapeCharsNull_2() throws Exception {
        assertBuffer("/foo?name='foo bar' ", new TestBuffer("/foo?name='foo b").tab());
    }
}
