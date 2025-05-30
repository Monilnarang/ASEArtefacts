package org.apache.hadoop.mapreduce.util;

import java.io.File;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.FileUtil;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TestMRAsyncDiskService_Purified {

    public static final Logger LOG = LoggerFactory.getLogger(TestMRAsyncDiskService.class);

    private static String TEST_ROOT_DIR = new Path(System.getProperty("test.build.data", "/tmp")).toString();

    @Before
    public void setUp() {
        FileUtil.fullyDelete(new File(TEST_ROOT_DIR));
    }

    private String relativeToWorking(String pathname) {
        String cwd = System.getProperty("user.dir", "/");
        pathname = (new Path(pathname)).toUri().getPath();
        cwd = (new Path(cwd)).toUri().getPath();
        String[] cwdParts = cwd.split(Path.SEPARATOR);
        String[] pathParts = pathname.split(Path.SEPARATOR);
        if (cwd.equals(pathname)) {
            LOG.info("relative to working: " + pathname + " -> .");
            return ".";
        }
        int common = 0;
        for (int i = 0; i < Math.min(cwdParts.length, pathParts.length); i++) {
            if (cwdParts[i].equals(pathParts[i])) {
                common++;
            } else {
                break;
            }
        }
        StringBuilder sb = new StringBuilder();
        int parentDirsRequired = cwdParts.length - common;
        for (int i = 0; i < parentDirsRequired; i++) {
            sb.append("..");
            sb.append(Path.SEPARATOR);
        }
        for (int i = common; i < pathParts.length; i++) {
            sb.append(pathParts[i]);
            sb.append(Path.SEPARATOR);
        }
        String s = sb.toString();
        if (s.endsWith(Path.SEPARATOR)) {
            s = s.substring(0, s.length() - 1);
        }
        LOG.info("relative to working: " + pathname + " -> " + s);
        return s;
    }

    private void makeSureCleanedUp(String[] vols, MRAsyncDiskService service) throws Throwable {
        service.shutdown();
        if (!service.awaitTermination(5000)) {
            fail("MRAsyncDiskService is still not shutdown in 5 seconds!");
        }
        for (int i = 0; i < vols.length; i++) {
            File subDir = new File(vols[0]);
            String[] subDirContent = subDir.list();
            assertEquals("Volume should contain a single child: " + MRAsyncDiskService.TOBEDELETED, 1, subDirContent.length);
            File toBeDeletedDir = new File(vols[0], MRAsyncDiskService.TOBEDELETED);
            String[] content = toBeDeletedDir.list();
            assertNotNull("Cannot find " + toBeDeletedDir, content);
            assertThat(content).withFailMessage(toBeDeletedDir.toString() + " should be empty now.").isEmpty();
        }
    }

    @Test
    public void testRelativeToWorking_1() {
        assertEquals(".", relativeToWorking(System.getProperty("user.dir", ".")));
    }

    @Test
    public void testRelativeToWorking_2_testMerged_2() {
        String cwd = System.getProperty("user.dir", ".");
        Path cwdPath = new Path(cwd);
        Path subdir = new Path(cwdPath, "foo");
        assertEquals("foo", relativeToWorking(subdir.toUri().getPath()));
        Path subsubdir = new Path(subdir, "bar");
        assertEquals("foo/bar", relativeToWorking(subsubdir.toUri().getPath()));
        Path parent = new Path(cwdPath, "..");
        assertEquals("..", relativeToWorking(parent.toUri().getPath()));
        Path sideways = new Path(parent, "baz");
        assertEquals("../baz", relativeToWorking(sideways.toUri().getPath()));
    }
}
