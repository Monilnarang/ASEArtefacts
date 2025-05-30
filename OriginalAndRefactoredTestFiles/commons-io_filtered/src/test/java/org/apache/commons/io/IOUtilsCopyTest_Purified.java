package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.io.file.TempFile;
import org.apache.commons.io.input.NullInputStream;
import org.apache.commons.io.input.NullReader;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.io.output.NullOutputStream;
import org.apache.commons.io.output.NullWriter;
import org.apache.commons.io.test.TestUtils;
import org.apache.commons.io.test.ThrowOnCloseInputStream;
import org.apache.commons.io.test.ThrowOnFlushAndCloseOutputStream;
import org.junit.jupiter.api.Test;

public class IOUtilsCopyTest_Purified {

    private static final int FILE_SIZE = 1024 * 4 + 1;

    private final byte[] inData = TestUtils.generateTestData(FILE_SIZE);

    @SuppressWarnings("resource")
    @Test
    public void testCopy_inputStreamToWriter_Encoding_1() throws Exception {
        InputStream in = new ByteArrayInputStream(inData);
        in = new ThrowOnCloseInputStream(in);
        IOUtils.copy(in, writer, "UTF8");
        assertEquals(0, in.available(), "Not all bytes were read");
    }

    @SuppressWarnings("resource")
    @Test
    public void testCopy_inputStreamToWriter_Encoding_2() throws Exception {
        final ByteArrayOutputStream baout = new ByteArrayOutputStream();
        byte[] bytes = baout.toByteArray();
        bytes = new String(bytes, StandardCharsets.UTF_8).getBytes(StandardCharsets.US_ASCII);
        assertArrayEquals(inData, bytes, "Content differs");
    }

    @SuppressWarnings("resource")
    @Test
    public void testCopy_inputStreamToWriter_Encoding_nullEncoding_1() throws Exception {
        InputStream in = new ByteArrayInputStream(inData);
        in = new ThrowOnCloseInputStream(in);
        IOUtils.copy(in, writer, (String) null);
        assertEquals(0, in.available(), "Not all bytes were read");
    }

    @SuppressWarnings("resource")
    @Test
    public void testCopy_inputStreamToWriter_Encoding_nullEncoding_2_testMerged_2() throws Exception {
        final ByteArrayOutputStream baout = new ByteArrayOutputStream();
        assertEquals(inData.length, baout.size(), "Sizes differ");
        assertArrayEquals(inData, baout.toByteArray(), "Content differs");
    }
}
