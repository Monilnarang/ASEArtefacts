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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestChecksumFileSystem_Parameterized {

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

    @ParameterizedTest
    @MethodSource("Provider_testgetChecksumLength_1to8")
    public void testgetChecksumLength_1to8(int param1, long param2, int param3) throws Exception {
        assertEquals(param1, ChecksumFileSystem.getChecksumLength(param2, param3));
    }

    static public Stream<Arguments> Provider_testgetChecksumLength_1to8() {
        return Stream.of(arguments(8, 0L, 512), arguments(12, 1L, 512), arguments(12, 512L, 512), arguments(16, 513L, 512), arguments(16, 1023L, 512), arguments(16, 1024L, 512), arguments(408, 100L, 1), arguments(4000000000008L, 10000000000000L, 10));
    }
}
