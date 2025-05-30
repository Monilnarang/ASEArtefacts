package org.jline.builtins;

import org.jline.builtins.Completers.TreeCompleter;
import org.jline.reader.LineReader;
import org.junit.jupiter.api.Test;
import static org.jline.builtins.Completers.TreeCompleter.node;

public class TreeCompleterTest_Purified extends ReaderTestSupport {

    @Test
    public void testCaseInsensitive_1() throws Exception {
        assertBuffer("ORA ACTIVES ", new TestBuffer("ORA AC").tab());
    }

    @Test
    public void testCaseInsensitive_2() throws Exception {
        assertBuffer("ora ACTIVES ", new TestBuffer("ora aC").tab());
    }

    @Test
    public void testCaseInsensitive_3() throws Exception {
        assertBuffer("ora ACTIVES ", new TestBuffer("ora ac").tab());
    }

    @Test
    public void testCaseInsensitive_4() throws Exception {
        assertBuffer("ORA LONGOPS ", new TestBuffer("ORA l").tab());
    }

    @Test
    public void testCaseInsensitive_5() throws Exception {
        assertBuffer("Ora LONGOPS -ALL ", new TestBuffer("Ora l").tab().tab());
    }
}
