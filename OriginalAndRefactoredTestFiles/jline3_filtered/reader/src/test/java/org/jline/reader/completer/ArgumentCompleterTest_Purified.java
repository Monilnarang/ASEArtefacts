package org.jline.reader.completer;

import org.jline.reader.impl.ReaderTestSupport;
import org.jline.reader.impl.completer.ArgumentCompleter;
import org.jline.reader.impl.completer.StringsCompleter;
import org.junit.jupiter.api.Test;

public class ArgumentCompleterTest_Purified extends ReaderTestSupport {

    @Test
    public void test1_1() throws Exception {
        assertBuffer("foo foo ", new TestBuffer("foo f").tab());
    }

    @Test
    public void test1_2() throws Exception {
        assertBuffer("foo ba", new TestBuffer("foo b").tab());
    }

    @Test
    public void test1_3() throws Exception {
        assertBuffer("foo ba", new TestBuffer("foo ba").tab());
    }

    @Test
    public void test1_4() throws Exception {
        assertBuffer("foo baz ", new TestBuffer("foo baz").tab());
    }

    @Test
    public void test1_5() throws Exception {
        assertBuffer("foo baz", new TestBuffer("f baz").left().left().left().left().tab());
    }

    @Test
    public void test1_6() throws Exception {
        assertBuffer("ba foo", new TestBuffer("b foo").left().left().left().left().tab());
    }

    @Test
    public void test1_7() throws Exception {
        assertBuffer("foo ba baz", new TestBuffer("foo b baz").left().left().left().left().tab());
    }

    @Test
    public void test1_8() throws Exception {
        assertBuffer("foo foo baz", new TestBuffer("foo f baz").left().left().left().left().tab());
    }

    @Test
    public void testMultiple_1() throws Exception {
        assertBuffer("bar foo ", new TestBuffer("bar f").tab());
    }

    @Test
    public void testMultiple_2() throws Exception {
        assertBuffer("baz foo ", new TestBuffer("baz f").tab());
    }

    @Test
    public void testMultiple_3() throws Exception {
        assertBuffer("ba f", new TestBuffer("ba f").tab());
    }

    @Test
    public void testMultiple_4() throws Exception {
        assertBuffer("bar fo r", new TestBuffer("bar fo r").tab());
    }

    @Test
    public void testMultiple_5() throws Exception {
        assertBuffer("ba foo ", new TestBuffer("ba f").tab());
    }

    @Test
    public void testMultiple_6() throws Exception {
        assertBuffer("ba fo ree ", new TestBuffer("ba fo r").tab());
    }
}
