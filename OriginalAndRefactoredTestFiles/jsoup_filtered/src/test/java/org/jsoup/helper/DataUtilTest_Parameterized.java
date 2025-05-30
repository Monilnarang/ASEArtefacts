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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class DataUtilTest_Parameterized {

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
    public void testCharset_5() {
        assertNull(DataUtil.getCharsetFromContentType(null));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCharset_1_1to2_2to3_3_5")
    public void testCharset_1_1to2_2to3_3_5(String param1, String param2) {
        assertEquals(param1, DataUtil.getCharsetFromContentType(param2));
    }

    static public Stream<Arguments> Provider_testCharset_1_1to2_2to3_3_5() {
        return Stream.of(arguments("utf-8", "text/html;charset=utf-8 "), arguments("UTF-8", "text/html; charset=UTF-8"), arguments("ISO-8859-1", "text/html; charset=ISO-8859-1"), arguments("utf-8", "text/html; charset=\"utf-8\""), arguments("UTF-8", "text/html;charset=\"UTF-8\""), arguments("ISO-8859-1", "text/html; charset=\"ISO-8859-1\""), arguments("UTF-8", "text/html; charset='UTF-8'"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCharset_1to2_4_4_6")
    public void testCharset_1to2_4_4_6(String param1) {
        assertNull(DataUtil.getCharsetFromContentType(param1));
    }

    static public Stream<Arguments> Provider_testCharset_1to2_4_4_6() {
        return Stream.of(arguments("text/html"), arguments("text/html;charset=Unknown"), arguments("text/html; charset=\"Unsupported\""), arguments("text/html; charset="), arguments("text/html; charset=;"));
    }
}
