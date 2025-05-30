package org.apache.hadoop.fs.aliyun.oss;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileAlreadyExistsException;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileSystemContractBaseTest;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.security.UserGroupInformation;
import org.junit.Before;
import org.junit.Test;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeTrue;

public class TestAliyunOSSFileSystemContract_Purified extends FileSystemContractBaseTest {

    public static final String TEST_FS_OSS_NAME = "test.fs.oss.name";

    public static final String FS_OSS_IMPL_DISABLE_CACHE = "fs.oss.impl.disable.cache";

    private static Path testRootPath = new Path(AliyunOSSTestUtils.generateUniqueTestPath());

    @Before
    public void setUp() throws Exception {
        Configuration conf = new Configuration();
        fs = AliyunOSSTestUtils.createTestFileSystem(conf);
        assumeNotNull(fs);
    }

    @Override
    public Path getTestBaseDir() {
        return testRootPath;
    }

    @Override
    protected boolean renameSupported() {
        return true;
    }

    class TestRenameTask implements Runnable {

        private FileSystem fs;

        private Path srcPath;

        private Path dstPath;

        private boolean result;

        private boolean running;

        TestRenameTask(FileSystem fs, Path srcPath, Path dstPath) {
            this.fs = fs;
            this.srcPath = srcPath;
            this.dstPath = dstPath;
            this.result = false;
            this.running = false;
        }

        boolean isSucceed() {
            return this.result;
        }

        boolean isRunning() {
            return this.running;
        }

        @Override
        public void run() {
            try {
                running = true;
                result = fs.rename(srcPath, dstPath);
            } catch (Exception e) {
                e.printStackTrace();
                this.result = false;
            }
        }
    }

    protected int getGlobalTimeout() {
        return 120 * 1000;
    }

    @Test
    public void testDeleteSubdir_1_testMerged_1() throws IOException {
        Path subdir = this.path("/test/hadoop/subdir");
        assertTrue("Created subdir", this.fs.mkdirs(subdir));
        assertTrue("Subdir exists", this.fs.exists(subdir));
        assertTrue("Deleted subdir", this.fs.delete(subdir, true));
    }

    @Test
    public void testDeleteSubdir_2_testMerged_2() throws IOException {
        Path file = this.path("/test/hadoop/file");
        this.createFile(file);
        assertTrue("File exists", this.fs.exists(file));
        assertTrue("Deleted file", this.fs.delete(file, false));
    }

    @Test
    public void testDeleteSubdir_3_testMerged_3() throws IOException {
        Path parentDir = this.path("/test/hadoop");
        assertTrue("Parent dir exists", this.fs.exists(parentDir));
        assertTrue("Parent should exist", this.fs.exists(parentDir));
    }
}
