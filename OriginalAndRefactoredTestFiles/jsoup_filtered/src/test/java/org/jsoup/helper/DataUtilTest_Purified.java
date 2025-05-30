package org.jsoup.helper;

import org.jsoup.Jsoup;
import org.jsoup.integration.ParseTest;
import org.jsoup.internal.ControllableInputStream;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.jsoup.integration.ParseTest.getFile;
import static org.jsoup.integration.ParseTest.getPath;
import static org.junit.jupiter.api.Assertions.*;

public class DataUtilTest_Purified {

    private ControllableInputStream stream(String data) {
        return ControllableInputStream.wrap(new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8)), 0);
    }

    private ControllableInputStream stream(String data, String charset) {
        return ControllableInputStream.wrap(new ByteArrayInputStream(data.getBytes(Charset.forName(charset))), 0);
    }

    static class VaryingReadInputStream extends InputStream {

        final InputStream in;

        int stride = 0;

        VaryingReadInputStream(InputStream in) {
            this.in = in;
        }

        public int read() throws IOException {
            return in.read();
        }

        public int read(byte[] b) throws IOException {
            return in.read(b, 0, Math.min(b.length, ++stride));
        }

        public int read(byte[] b, int off, int len) throws IOException {
            return in.read(b, off, Math.min(len, ++stride));
        }
    }

    @Test
    public void testCharset_1() {
        assertEquals("utf-8", DataUtil.getCharsetFromContentType("text/html;charset=utf-8 "));
    }

    @Test
    public void testCharset_2() {
        assertEquals("UTF-8", DataUtil.getCharsetFromContentType("text/html; charset=UTF-8"));
    }

    @Test
    public void testCharset_3() {
        assertEquals("ISO-8859-1", DataUtil.getCharsetFromContentType("text/html; charset=ISO-8859-1"));
    }

    @Test
    public void testCharset_4() {
        assertNull(DataUtil.getCharsetFromContentType("text/html"));
    }

    @Test
    public void testCharset_5() {
        assertNull(DataUtil.getCharsetFromContentType(null));
    }

    @Test
    public void testCharset_6() {
        assertNull(DataUtil.getCharsetFromContentType("text/html;charset=Unknown"));
    }

    @Test
    public void testQuotedCharset_1() {
        assertEquals("utf-8", DataUtil.getCharsetFromContentType("text/html; charset=\"utf-8\""));
    }

    @Test
    public void testQuotedCharset_2() {
        assertEquals("UTF-8", DataUtil.getCharsetFromContentType("text/html;charset=\"UTF-8\""));
    }

    @Test
    public void testQuotedCharset_3() {
        assertEquals("ISO-8859-1", DataUtil.getCharsetFromContentType("text/html; charset=\"ISO-8859-1\""));
    }

    @Test
    public void testQuotedCharset_4() {
        assertNull(DataUtil.getCharsetFromContentType("text/html; charset=\"Unsupported\""));
    }

    @Test
    public void testQuotedCharset_5() {
        assertEquals("UTF-8", DataUtil.getCharsetFromContentType("text/html; charset='UTF-8'"));
    }

    @Test
    public void shouldNotThrowExceptionOnEmptyCharset_1() {
        assertNull(DataUtil.getCharsetFromContentType("text/html; charset="));
    }

    @Test
    public void shouldNotThrowExceptionOnEmptyCharset_2() {
        assertNull(DataUtil.getCharsetFromContentType("text/html; charset=;"));
    }
}
