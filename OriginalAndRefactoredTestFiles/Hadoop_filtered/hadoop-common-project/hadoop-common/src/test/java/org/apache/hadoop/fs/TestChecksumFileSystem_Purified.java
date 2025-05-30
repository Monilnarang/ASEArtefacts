package org.apache.hadoop.fs;

import java.util.Arrays;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.permission.FsPermission;
import static org.apache.hadoop.fs.FileSystemTestHelper.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.test.GenericTestUtils;
import org.junit.*;
import static org.junit.Assert.*;

public class TestChecksumFileSystem_Purified {

    static final String TEST_ROOT_DIR = GenericTestUtils.getTempPath("work-dir/localfs");

    static LocalFileSystem localFs;

    @Before
    public void resetLocalFs() throws Exception {
        localFs = FileSystem.getLocal(new Configuration());
        localFs.setVerifyChecksum(true);
    }

    void verifyRename(Path srcPath, Path dstPath, boolean dstIsDir) throws Exception {
        localFs.delete(srcPath, true);
        localFs.delete(dstPath, true);
        Path realDstPath = dstPath;
        if (dstIsDir) {
            localFs.mkdirs(dstPath);
            realDstPath = new Path(dstPath, srcPath.getName());
        }
        writeFile(localFs, srcPath, 1);
        assertTrue(localFs.exists(localFs.getChecksumFile(srcPath)));
        assertTrue(localFs.rename(srcPath, dstPath));
        assertTrue(localFs.exists(localFs.getChecksumFile(realDstPath)));
        writeFile(localFs.getRawFileSystem(), srcPath, 1);
        assertFalse(localFs.exists(localFs.getChecksumFile(srcPath)));
        assertTrue(localFs.rename(srcPath, dstPath));
        assertFalse(localFs.exists(localFs.getChecksumFile(realDstPath)));
        writeFile(localFs, srcPath, 1);
        assertTrue(localFs.exists(localFs.getChecksumFile(srcPath)));
        assertTrue(localFs.rename(srcPath, dstPath));
        assertTrue(localFs.exists(localFs.getChecksumFile(realDstPath)));
    }

    @Test
    public void testgetChecksumLength_1() throws Exception {
        assertEquals(8, ChecksumFileSystem.getChecksumLength(0L, 512));
    }

    @Test
    public void testgetChecksumLength_2() throws Exception {
        assertEquals(12, ChecksumFileSystem.getChecksumLength(1L, 512));
    }

    @Test
    public void testgetChecksumLength_3() throws Exception {
        assertEquals(12, ChecksumFileSystem.getChecksumLength(512L, 512));
    }

    @Test
    public void testgetChecksumLength_4() throws Exception {
        assertEquals(16, ChecksumFileSystem.getChecksumLength(513L, 512));
    }

    @Test
    public void testgetChecksumLength_5() throws Exception {
        assertEquals(16, ChecksumFileSystem.getChecksumLength(1023L, 512));
    }

    @Test
    public void testgetChecksumLength_6() throws Exception {
        assertEquals(16, ChecksumFileSystem.getChecksumLength(1024L, 512));
    }

    @Test
    public void testgetChecksumLength_7() throws Exception {
        assertEquals(408, ChecksumFileSystem.getChecksumLength(100L, 1));
    }

    @Test
    public void testgetChecksumLength_8() throws Exception {
        assertEquals(4000000000008L, ChecksumFileSystem.getChecksumLength(10000000000000L, 10));
    }
}
