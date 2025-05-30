package org.jline.reader.completer;

import org.jline.reader.impl.ReaderTestSupport;
import org.jline.reader.impl.completer.NullCompleter;
import org.junit.jupiter.api.Test;

public class NullCompleterTest_Purified extends ReaderTestSupport {

    @Test
    public void test1_1() throws Exception {
        assertBuffer("f", new TestBuffer("f").tab());
    }

    @Test
    public void test1_2() throws Exception {
        assertBuffer("ba", new TestBuffer("ba").tab());
    }

    @Test
    public void test1_3() throws Exception {
        assertBuffer("baz", new TestBuffer("baz").tab());
    }
}
