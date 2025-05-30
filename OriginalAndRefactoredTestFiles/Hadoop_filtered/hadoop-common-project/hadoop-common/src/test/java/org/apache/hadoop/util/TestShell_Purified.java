package org.apache.hadoop.util;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.HashMap;
import java.util.Map;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.test.GenericTestUtils;
import static org.apache.hadoop.util.Shell.*;
import static org.junit.Assume.assumeTrue;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.rules.Timeout;

public class TestShell_Purified extends Assert {

    @Rule
    public Timeout testTimeout = new Timeout(30000, TimeUnit.MILLISECONDS);

    @Rule
    public TestName methodName = new TestName();

    private File rootTestDir = GenericTestUtils.getTestDir();

    private File methodDir;

    private static class Command extends Shell {

        private int runCount = 0;

        private Command(long interval) {
            super(interval);
        }

        @Override
        protected String[] getExecString() {
            return WINDOWS ? (new String[] { "cmd.exe", "/c", "echo", "hello" }) : (new String[] { "echo", "hello" });
        }

        @Override
        protected void parseExecResult(BufferedReader lines) throws IOException {
            ++runCount;
        }

        public int getRunCount() {
            return runCount;
        }
    }

    @Before
    public void setup() {
        rootTestDir.mkdirs();
        assertTrue("Not a directory " + rootTestDir, rootTestDir.isDirectory());
        methodDir = new File(rootTestDir, methodName.getMethodName());
    }

    private void assertInString(String string, String search) {
        assertNotNull("Empty String", string);
        if (!string.contains(search)) {
            fail("Did not find \"" + search + "\" in " + string);
        }
    }

    private static int countTimerThreads() {
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        int count = 0;
        ThreadInfo[] infos = threadBean.getThreadInfo(threadBean.getAllThreadIds(), 20);
        for (ThreadInfo info : infos) {
            if (info == null)
                continue;
            for (StackTraceElement elem : info.getStackTrace()) {
                if (elem.getClassName().contains("Timer")) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }

    private File touch(File path) throws IOException {
        path.getParentFile().mkdirs();
        FileUtils.writeByteArrayToFile(path, new byte[] {});
        return path;
    }

    private FileNotFoundException assertHomeResolveFailed(String path, String expectedText) throws Exception {
        try {
            File f = checkHadoopHomeInner(path);
            fail("Expected an exception with the text `" + expectedText + "`" + " -but got the path " + f);
            return null;
        } catch (FileNotFoundException ex) {
            assertExContains(ex, expectedText);
            return ex;
        }
    }

    private FileNotFoundException assertWinutilsResolveFailed(File hadoopHome, String expectedText) throws Exception {
        try {
            File f = getQualifiedBinInner(hadoopHome, WINUTILS_EXE);
            fail("Expected an exception with the text `" + expectedText + "`" + " -but got the path " + f);
            return null;
        } catch (FileNotFoundException ex) {
            assertExContains(ex, expectedText);
            return ex;
        }
    }

    private void assertExContains(Exception ex, String expectedText) throws Exception {
        if (!ex.toString().contains(expectedText)) {
            throw ex;
        }
    }

    @Test
    public void testBashQuote_1() {
        assertEquals("'foobar'", Shell.bashQuote("foobar"));
    }

    @Test
    public void testBashQuote_2() {
        assertEquals("'foo'\\''bar'", Shell.bashQuote("foo'bar"));
    }

    @Test
    public void testBashQuote_3() {
        assertEquals("''\\''foo'\\''bar'\\'''", Shell.bashQuote("'foo'bar'"));
    }
}
