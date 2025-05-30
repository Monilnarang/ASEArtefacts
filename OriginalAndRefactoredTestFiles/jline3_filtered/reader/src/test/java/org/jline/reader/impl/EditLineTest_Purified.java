package org.jline.reader.impl;

import org.jline.keymap.KeyMap;
import org.jline.reader.LineReader;
import org.jline.reader.Reference;
import org.junit.jupiter.api.Test;
import static org.jline.keymap.KeyMap.ctrl;
import static org.jline.reader.LineReader.BACKWARD_KILL_LINE;
import static org.jline.reader.LineReader.BACKWARD_KILL_WORD;
import static org.jline.reader.LineReader.BACKWARD_WORD;
import static org.jline.reader.LineReader.END_OF_LINE;
import static org.jline.reader.LineReader.FORWARD_WORD;
import static org.jline.reader.LineReader.KILL_WORD;

public class EditLineTest_Purified extends ReaderTestSupport {

    @Test
    public void testMoveToEnd_1() throws Exception {
        assertBuffer("This is a XtestX", new TestBuffer("This is a test").op(BACKWARD_WORD).append('X').op(END_OF_LINE).append('X'));
    }

    @Test
    public void testMoveToEnd_2() throws Exception {
        assertBuffer("This is Xa testX", new TestBuffer("This is a test").op(BACKWARD_WORD).op(BACKWARD_WORD).append('X').op(END_OF_LINE).append('X'));
    }

    @Test
    public void testMoveToEnd_3() throws Exception {
        assertBuffer("This Xis a testX", new TestBuffer("This is a test").op(BACKWARD_WORD).op(BACKWARD_WORD).op(BACKWARD_WORD).append('X').op(END_OF_LINE).append('X'));
    }

    @Test
    public void testPreviousWord_1() throws Exception {
        assertBuffer("This is a Xtest", new TestBuffer("This is a test").op(BACKWARD_WORD).append('X'));
    }

    @Test
    public void testPreviousWord_2() throws Exception {
        assertBuffer("This is Xa test", new TestBuffer("This is a test").op(BACKWARD_WORD).op(BACKWARD_WORD).append('X'));
    }

    @Test
    public void testPreviousWord_3() throws Exception {
        assertBuffer("This Xis a test", new TestBuffer("This is a test").op(BACKWARD_WORD).op(BACKWARD_WORD).op(BACKWARD_WORD).append('X'));
    }

    @Test
    public void testPreviousWord_4() throws Exception {
        assertBuffer("XThis is a test", new TestBuffer("This is a test").op(BACKWARD_WORD).op(BACKWARD_WORD).op(BACKWARD_WORD).op(BACKWARD_WORD).append('X'));
    }

    @Test
    public void testPreviousWord_5() throws Exception {
        assertBuffer("XThis is a test", new TestBuffer("This is a test").op(BACKWARD_WORD).op(BACKWARD_WORD).op(BACKWARD_WORD).op(BACKWARD_WORD).op(BACKWARD_WORD).append('X'));
    }

    @Test
    public void testPreviousWord_6() throws Exception {
        assertBuffer("XThis is a test", new TestBuffer("This is a test").op(BACKWARD_WORD).op(BACKWARD_WORD).op(BACKWARD_WORD).op(BACKWARD_WORD).op(BACKWARD_WORD).op(BACKWARD_WORD).append('X'));
    }

    @Test
    public void testBackwardWord_1() throws Exception {
        assertBuffer("This is a Xtest", new TestBuffer("This is a test").op(BACKWARD_WORD).append('X'));
    }

    @Test
    public void testBackwardWord_2() throws Exception {
        assertBuffer("This is Xa test", new TestBuffer("This is a test").op(BACKWARD_WORD).op(BACKWARD_WORD).append('X'));
    }

    @Test
    public void testForwardWord_1() throws Exception {
        assertBuffer("This Xis a test", new TestBuffer("This is a test").ctrlA().op(FORWARD_WORD).append('X'));
    }

    @Test
    public void testForwardWord_2() throws Exception {
        assertBuffer("This is Xa test", new TestBuffer("This is a test").ctrlA().op(FORWARD_WORD).op(FORWARD_WORD).append('X'));
    }

    @Test
    public void testBackwardWordWithSeparator_1() throws Exception {
        assertBuffer("/tmp/foo/Xmoo", new TestBuffer("/tmp/foo/moo").op(BACKWARD_WORD).append('X'));
    }

    @Test
    public void testBackwardWordWithSeparator_2() throws Exception {
        assertBuffer("/tmp/Xfoo/moo", new TestBuffer("/tmp/foo/moo").op(BACKWARD_WORD).op(BACKWARD_WORD).append('X'));
    }

    @Test
    public void testForwardWordWithSeparator_1() throws Exception {
        assertBuffer("/Xtmp/foo/moo", new TestBuffer("/tmp/foo/moo").ctrlA().op(FORWARD_WORD).append('X'));
    }

    @Test
    public void testForwardWordWithSeparator_2() throws Exception {
        assertBuffer("/tmp/Xfoo/moo", new TestBuffer("/tmp/foo/moo").ctrlA().op(FORWARD_WORD).op(FORWARD_WORD).append('X'));
    }

    @Test
    public void testEmacsBackwardWord_1() throws Exception {
        assertBuffer("This is a Xtest", new TestBuffer("This is a test").op(BACKWARD_WORD).append('X'));
    }

    @Test
    public void testEmacsBackwardWord_2() throws Exception {
        assertBuffer("This is Xa test", new TestBuffer("This is a test").op(BACKWARD_WORD).op(BACKWARD_WORD).append('X'));
    }

    @Test
    public void testEmacsForwardWord_1() throws Exception {
        assertBuffer("This Xis a test", new TestBuffer("This is a test").ctrlA().op(FORWARD_WORD).append('X'));
    }

    @Test
    public void testEmacsForwardWord_2() throws Exception {
        assertBuffer("This is Xa test", new TestBuffer("This is a test").ctrlA().op(FORWARD_WORD).op(FORWARD_WORD).append('X'));
    }

    @Test
    public void testEmacsBackwardWordWithSeparator_1() throws Exception {
        assertBuffer("/tmp/foo/Xmoo", new TestBuffer("/tmp/foo/moo").op(BACKWARD_WORD).append('X'));
    }

    @Test
    public void testEmacsBackwardWordWithSeparator_2() throws Exception {
        assertBuffer("/tmp/Xfoo/moo", new TestBuffer("/tmp/foo/moo").op(BACKWARD_WORD).op(BACKWARD_WORD).append('X'));
    }

    @Test
    public void testEmacsForwardWordWithSeparator_1() throws Exception {
        assertBuffer("/Xtmp/foo/moo", new TestBuffer("/tmp/foo/moo").ctrlA().op(FORWARD_WORD).append('X'));
    }

    @Test
    public void testEmacsForwardWordWithSeparator_2() throws Exception {
        assertBuffer("/tmp/Xfoo/moo", new TestBuffer("/tmp/foo/moo").ctrlA().op(FORWARD_WORD).op(FORWARD_WORD).append('X'));
    }

    @Test
    public void testLineStart_1() throws Exception {
        assertBuffer("XThis is a test", new TestBuffer("This is a test").ctrlA().append('X'));
    }

    @Test
    public void testLineStart_2() throws Exception {
        assertBuffer("TXhis is a test", new TestBuffer("This is a test").ctrlA().right().append('X'));
    }

    @Test
    public void testClearLine_1() throws Exception {
        assertBuffer("", new TestBuffer("This is a test").ctrlU());
    }

    @Test
    public void testClearLine_2() throws Exception {
        assertBuffer("t", new TestBuffer("This is a test").left().ctrlU());
    }

    @Test
    public void testClearLine_3() throws Exception {
        assertBuffer("st", new TestBuffer("This is a test").left().left().ctrlU());
    }

    @Test
    public void testAbortPartialBuffer_1() throws Exception {
        assertBuffer("", new TestBuffer("This is a test").ctrl('G'));
    }

    @Test
    public void testAbortPartialBuffer_2() throws Exception {
        assertConsoleOutputContains("\n");
    }

    @Test
    public void testAbortPartialBuffer_3() throws Exception {
        assertBeeped();
    }

    @Test
    public void testAbortPartialBuffer_4() throws Exception {
        assertBuffer("", new TestBuffer("This is a test").op(BACKWARD_WORD).op(BACKWARD_WORD).ctrl('G'));
    }

    @Test
    public void testAbortPartialBuffer_5() throws Exception {
        assertConsoleOutputContains("\n");
    }

    @Test
    public void testAbortPartialBuffer_6() throws Exception {
        assertBeeped();
    }
}
