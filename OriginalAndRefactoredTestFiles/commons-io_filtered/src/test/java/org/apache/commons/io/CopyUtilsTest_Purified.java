package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.input.CharSequenceInputStream;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.io.test.TestUtils;
import org.apache.commons.io.test.ThrowOnCloseInputStream;
import org.apache.commons.io.test.ThrowOnFlushAndCloseOutputStream;
import org.junit.jupiter.api.Test;

@SuppressWarnings("deprecation")
public class CopyUtilsTest_Purified {

    private static final int FILE_SIZE = 1024 * 4 + 1;

    private final byte[] inData = TestUtils.generateTestData(FILE_SIZE);

    @SuppressWarnings("resource")
    @Test
    public void testCopy_inputStreamToWriter_1() throws Exception {
        InputStream in = new ByteArrayInputStream(inData);
        in = new ThrowOnCloseInputStream(in);
        CopyUtils.copy(in, writer);
        assertEquals(0, in.available(), "Not all bytes were read");
    }

    @SuppressWarnings("resource")
    @Test
    public void testCopy_inputStreamToWriter_2_testMerged_2() throws Exception {
        final ByteArrayOutputStream baout = new ByteArrayOutputStream();
        assertEquals(inData.length, baout.size(), "Sizes differ");
        assertArrayEquals(inData, baout.toByteArray(), "Content differs");
    }
}
