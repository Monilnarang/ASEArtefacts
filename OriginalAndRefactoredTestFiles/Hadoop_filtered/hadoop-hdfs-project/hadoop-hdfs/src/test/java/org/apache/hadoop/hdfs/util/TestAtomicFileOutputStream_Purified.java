package org.apache.hadoop.hdfs.util;

import static org.apache.hadoop.test.PlatformAssumptions.assumeWindows;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.hdfs.DFSTestUtil;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.test.PathUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.apache.hadoop.thirdparty.com.google.common.base.Joiner;

public class TestAtomicFileOutputStream_Purified {

    private static final String TEST_STRING = "hello world";

    private static final String TEST_STRING_2 = "goodbye world";

    private static final File TEST_DIR = PathUtils.getTestDir(TestAtomicFileOutputStream.class);

    private static final File DST_FILE = new File(TEST_DIR, "test.txt");

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void cleanupTestDir() throws IOException {
        assertTrue(TEST_DIR.exists() || TEST_DIR.mkdirs());
        FileUtil.fullyDeleteContents(TEST_DIR);
    }

    private OutputStream createFailingStream() throws FileNotFoundException {
        return new AtomicFileOutputStream(DST_FILE) {

            @Override
            public void flush() throws IOException {
                throw new IOException("injected failure");
            }
        };
    }

    @Test
    public void testWriteNewFile_1() throws IOException {
        assertFalse(DST_FILE.exists());
    }

    @Test
    public void testWriteNewFile_2() throws IOException {
        assertFalse(DST_FILE.exists());
    }

    @Test
    public void testWriteNewFile_3() throws IOException {
        assertTrue(DST_FILE.exists());
    }

    @Test
    public void testWriteNewFile_4() throws IOException {
        fos.write(TEST_STRING.getBytes());
        String readBackData = DFSTestUtil.readFile(DST_FILE);
        assertEquals(TEST_STRING, readBackData);
    }

    @Test
    public void testOverwriteFile_1() throws IOException {
        assertTrue("Creating empty dst file", DST_FILE.createNewFile());
    }

    @Test
    public void testOverwriteFile_2() throws IOException {
        assertTrue("Empty file still exists", DST_FILE.exists());
    }

    @Test
    public void testOverwriteFile_3() throws IOException {
        assertEquals("", DFSTestUtil.readFile(DST_FILE));
    }

    @Test
    public void testOverwriteFile_4() throws IOException {
        fos.write(TEST_STRING.getBytes());
        String readBackData = DFSTestUtil.readFile(DST_FILE);
        assertEquals(TEST_STRING, readBackData);
    }
}
