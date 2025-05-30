package org.apache.hadoop.io.nativeio;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathIOException;
import org.apache.hadoop.fs.contract.ContractTestUtils;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.test.GenericTestUtils;
import org.apache.hadoop.test.LambdaTestUtils;
import org.apache.hadoop.test.StatUtils;
import org.apache.hadoop.util.NativeCodeLoader;
import org.apache.hadoop.util.Time;
import static org.apache.hadoop.io.nativeio.NativeIO.POSIX.*;
import static org.apache.hadoop.io.nativeio.NativeIO.POSIX.Stat.*;
import static org.apache.hadoop.test.PlatformAssumptions.assumeNotWindows;
import static org.apache.hadoop.test.PlatformAssumptions.assumeWindows;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assume.*;
import static org.junit.Assert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestNativeIO_Purified {

    static final Logger LOG = LoggerFactory.getLogger(TestNativeIO.class);

    static final File TEST_DIR = GenericTestUtils.getTestDir("testnativeio");

    @Before
    public void checkLoaded() {
        assumeTrue(NativeCodeLoader.isNativeCodeLoaded());
    }

    @Before
    public void setupTestDir() {
        FileUtil.fullyDelete(TEST_DIR);
        TEST_DIR.mkdirs();
    }

    private boolean doStatTest(String testFilePath) throws Exception {
        NativeIO.POSIX.Stat stat = NativeIO.POSIX.getStat(testFilePath);
        String owner = stat.getOwner();
        String group = stat.getGroup();
        int mode = stat.getMode();
        String expectedOwner = System.getProperty("user.name");
        assertEquals(expectedOwner, owner);
        assertNotNull(group);
        assertTrue(!group.isEmpty());
        StatUtils.Permission expected = StatUtils.getPermissionFromProcess(testFilePath);
        StatUtils.Permission permission = new StatUtils.Permission(owner, group, new FsPermission(mode));
        assertEquals(expected.getOwner(), permission.getOwner());
        assertEquals(expected.getGroup(), permission.getGroup());
        assertEquals(expected.getFsPermission(), permission.getFsPermission());
        LOG.info("Load permission test is successful for path: {}, stat: {}", testFilePath, stat);
        LOG.info("On mask, stat is owner: {}, group: {}, permission: {}", owner, group, permission.getFsPermission().toOctal());
        return true;
    }

    private void assertPermissions(File f, int expected) throws IOException {
        FileSystem localfs = FileSystem.getLocal(new Configuration());
        FsPermission perms = localfs.getFileStatus(new Path(f.getAbsolutePath())).getPermission();
        assertEquals(expected, perms.toShort());
    }

    private static byte[] generateSequentialBytes(int start, int length) {
        byte[] result = new byte[length];
        for (int i = 0; i < length; i++) {
            result[i] = (byte) ((start + i) % 127);
        }
        return result;
    }

    private static void deletePmemMappedFile(String filePath) {
        try {
            if (filePath != null) {
                boolean result = Files.deleteIfExists(Paths.get(filePath));
                if (!result) {
                    throw new IOException();
                }
            }
        } catch (Throwable e) {
            LOG.error("Failed to delete the mapped file " + filePath + " from persistent memory", e);
        }
    }

    @Test(timeout = 10000)
    public void testNativePosixConsts_1() {
        assertTrue("Native 0_RDONLY const not set", O_RDONLY >= 0);
    }

    @Test(timeout = 10000)
    public void testNativePosixConsts_2() {
        assertTrue("Native 0_WRONLY const not set", O_WRONLY >= 0);
    }

    @Test(timeout = 10000)
    public void testNativePosixConsts_3() {
        assertTrue("Native 0_RDWR const not set", O_RDWR >= 0);
    }

    @Test(timeout = 10000)
    public void testNativePosixConsts_4() {
        assertTrue("Native 0_CREAT const not set", O_CREAT >= 0);
    }

    @Test(timeout = 10000)
    public void testNativePosixConsts_5() {
        assertTrue("Native 0_EXCL const not set", O_EXCL >= 0);
    }

    @Test(timeout = 10000)
    public void testNativePosixConsts_6() {
        assertTrue("Native 0_NOCTTY const not set", O_NOCTTY >= 0);
    }

    @Test(timeout = 10000)
    public void testNativePosixConsts_7() {
        assertTrue("Native 0_TRUNC const not set", O_TRUNC >= 0);
    }

    @Test(timeout = 10000)
    public void testNativePosixConsts_8() {
        assertTrue("Native 0_APPEND const not set", O_APPEND >= 0);
    }

    @Test(timeout = 10000)
    public void testNativePosixConsts_9() {
        assertTrue("Native 0_NONBLOCK const not set", O_NONBLOCK >= 0);
    }

    @Test(timeout = 10000)
    public void testNativePosixConsts_10() {
        assertTrue("Native 0_SYNC const not set", O_SYNC >= 0);
    }

    @Test(timeout = 10000)
    public void testNativePosixConsts_11() {
        assertTrue("Native S_IFMT const not set", S_IFMT >= 0);
    }

    @Test(timeout = 10000)
    public void testNativePosixConsts_12() {
        assertTrue("Native S_IFIFO const not set", S_IFIFO >= 0);
    }

    @Test(timeout = 10000)
    public void testNativePosixConsts_13() {
        assertTrue("Native S_IFCHR const not set", S_IFCHR >= 0);
    }

    @Test(timeout = 10000)
    public void testNativePosixConsts_14() {
        assertTrue("Native S_IFDIR const not set", S_IFDIR >= 0);
    }

    @Test(timeout = 10000)
    public void testNativePosixConsts_15() {
        assertTrue("Native S_IFBLK const not set", S_IFBLK >= 0);
    }

    @Test(timeout = 10000)
    public void testNativePosixConsts_16() {
        assertTrue("Native S_IFREG const not set", S_IFREG >= 0);
    }

    @Test(timeout = 10000)
    public void testNativePosixConsts_17() {
        assertTrue("Native S_IFLNK const not set", S_IFLNK >= 0);
    }

    @Test(timeout = 10000)
    public void testNativePosixConsts_18() {
        assertTrue("Native S_IFSOCK const not set", S_IFSOCK >= 0);
    }

    @Test(timeout = 10000)
    public void testNativePosixConsts_19() {
        assertTrue("Native S_ISUID const not set", S_ISUID >= 0);
    }

    @Test(timeout = 10000)
    public void testNativePosixConsts_20() {
        assertTrue("Native S_ISGID const not set", S_ISGID >= 0);
    }

    @Test(timeout = 10000)
    public void testNativePosixConsts_21() {
        assertTrue("Native S_ISVTX const not set", S_ISVTX >= 0);
    }

    @Test(timeout = 10000)
    public void testNativePosixConsts_22() {
        assertTrue("Native S_IRUSR const not set", S_IRUSR >= 0);
    }

    @Test(timeout = 10000)
    public void testNativePosixConsts_23() {
        assertTrue("Native S_IWUSR const not set", S_IWUSR >= 0);
    }

    @Test(timeout = 10000)
    public void testNativePosixConsts_24() {
        assertTrue("Native S_IXUSR const not set", S_IXUSR >= 0);
    }

    @Test(timeout = 10000)
    public void testNativeFadviseConsts_1() {
        assertTrue("Native POSIX_FADV_NORMAL const not set", POSIX_FADV_NORMAL >= 0);
    }

    @Test(timeout = 10000)
    public void testNativeFadviseConsts_2() {
        assertTrue("Native POSIX_FADV_RANDOM const not set", POSIX_FADV_RANDOM >= 0);
    }

    @Test(timeout = 10000)
    public void testNativeFadviseConsts_3() {
        assertTrue("Native POSIX_FADV_SEQUENTIAL const not set", POSIX_FADV_SEQUENTIAL >= 0);
    }

    @Test(timeout = 10000)
    public void testNativeFadviseConsts_4() {
        assertTrue("Native POSIX_FADV_WILLNEED const not set", POSIX_FADV_WILLNEED >= 0);
    }

    @Test(timeout = 10000)
    public void testNativeFadviseConsts_5() {
        assertTrue("Native POSIX_FADV_DONTNEED const not set", POSIX_FADV_DONTNEED >= 0);
    }

    @Test(timeout = 10000)
    public void testNativeFadviseConsts_6() {
        assertTrue("Native POSIX_FADV_NOREUSE const not set", POSIX_FADV_NOREUSE >= 0);
    }
}
