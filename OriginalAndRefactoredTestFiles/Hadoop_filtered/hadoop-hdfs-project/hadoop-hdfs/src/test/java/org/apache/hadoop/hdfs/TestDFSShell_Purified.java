package org.apache.hadoop.hdfs;

import java.io.*;
import java.security.Permission;
import java.security.PrivilegedExceptionAction;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.GZIPOutputStream;
import java.util.function.Supplier;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.hadoop.hdfs.protocol.ErasureCodingPolicy;
import org.apache.hadoop.hdfs.protocol.XAttrNotFoundException;
import org.apache.hadoop.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Test;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.permission.AclEntry;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.hdfs.client.HdfsClientConfigKeys;
import org.apache.hadoop.hdfs.protocol.Block;
import org.apache.hadoop.hdfs.protocol.BlockListAsLongs;
import org.apache.hadoop.hdfs.protocol.ExtendedBlock;
import org.apache.hadoop.hdfs.server.datanode.FsDatasetTestUtils.MaterializedReplica;
import org.apache.hadoop.hdfs.server.namenode.FSDirectory;
import org.apache.hadoop.hdfs.server.protocol.DatanodeStorage;
import org.apache.hadoop.hdfs.tools.DFSAdmin;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.BZip2Codec;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.net.ServerSocketUtil;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.test.GenericTestUtils;
import org.apache.hadoop.test.PathUtils;
import org.apache.hadoop.util.ReflectionUtils;
import org.apache.hadoop.util.StringUtils;
import org.apache.hadoop.util.Time;
import org.apache.hadoop.util.ToolRunner;
import org.junit.rules.Timeout;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Assert;
import org.slf4j.event.Level;
import static org.apache.hadoop.fs.CommonConfigurationKeysPublic.FS_TRASH_INTERVAL_KEY;
import static org.apache.hadoop.fs.permission.AclEntryScope.ACCESS;
import static org.apache.hadoop.fs.permission.AclEntryScope.DEFAULT;
import static org.apache.hadoop.fs.permission.AclEntryType.*;
import static org.apache.hadoop.fs.permission.FsAction.*;
import static org.apache.hadoop.hdfs.server.namenode.AclTestHelpers.aclEntry;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;
import static org.hamcrest.core.StringContains.containsString;

public class TestDFSShell_Purified {

    private static final Logger LOG = LoggerFactory.getLogger(TestDFSShell.class);

    private static final AtomicInteger counter = new AtomicInteger();

    private final int SUCCESS = 0;

    private final int ERROR = 1;

    static final String TEST_ROOT_DIR = PathUtils.getTestDirName(TestDFSShell.class);

    private static final String RAW_A1 = "raw.a1";

    private static final String TRUSTED_A1 = "trusted.a1";

    private static final String USER_A1 = "user.a1";

    private static final byte[] RAW_A1_VALUE = new byte[] { 0x32, 0x32, 0x32 };

    private static final byte[] TRUSTED_A1_VALUE = new byte[] { 0x31, 0x31, 0x31 };

    private static final byte[] USER_A1_VALUE = new byte[] { 0x31, 0x32, 0x33 };

    private static final int BLOCK_SIZE = 1024;

    private static MiniDFSCluster miniCluster;

    private static DistributedFileSystem dfs;

    @BeforeClass
    public static void setup() throws IOException {
        final Configuration conf = new Configuration();
        conf.setBoolean(DFSConfigKeys.DFS_PERMISSIONS_ENABLED_KEY, true);
        conf.setInt(DFSConfigKeys.DFS_BLOCK_SIZE_KEY, BLOCK_SIZE);
        conf.set(MiniDFSCluster.HDFS_MINIDFS_BASEDIR, GenericTestUtils.getTestDir("TestDFSShell").getAbsolutePath());
        conf.setBoolean(DFSConfigKeys.DFS_NAMENODE_XATTRS_ENABLED_KEY, true);
        conf.setBoolean(DFSConfigKeys.DFS_NAMENODE_ACLS_ENABLED_KEY, true);
        conf.setLong(DFSConfigKeys.DFS_NAMENODE_ACCESSTIME_PRECISION_KEY, 120000);
        miniCluster = new MiniDFSCluster.Builder(conf).numDataNodes(2).build();
        miniCluster.waitActive();
        dfs = miniCluster.getFileSystem();
    }

    @AfterClass
    public static void tearDown() {
        if (miniCluster != null) {
            miniCluster.shutdown(true, true);
        }
    }

    @Rule
    public Timeout globalTimeout = new Timeout(30 * 1000);

    static Path writeFile(FileSystem fs, Path f) throws IOException {
        DataOutputStream out = fs.create(f);
        out.writeBytes("dhruba: " + f);
        out.close();
        assertTrue(fs.exists(f));
        return f;
    }

    static Path writeByte(FileSystem fs, Path f) throws IOException {
        DataOutputStream out = fs.create(f);
        out.writeByte(1);
        out.close();
        assertTrue(fs.exists(f));
        return f;
    }

    static Path mkdir(FileSystem fs, Path p) throws IOException {
        assertTrue(fs.mkdirs(p));
        assertTrue(fs.exists(p));
        assertTrue(fs.getFileStatus(p).isDirectory());
        return p;
    }

    static void rmr(FileSystem fs, Path p) throws IOException {
        assertTrue(fs.delete(p, true));
        assertFalse(fs.exists(p));
    }

    static File createLocalFile(File f) throws IOException {
        assertTrue(!f.exists());
        PrintWriter out = new PrintWriter(f);
        out.print("createLocalFile: " + f.getAbsolutePath());
        out.flush();
        out.close();
        assertTrue(f.exists());
        assertTrue(f.isFile());
        return f;
    }

    static File createLocalFileWithRandomData(int fileLength, File f) throws IOException {
        assertTrue(!f.exists());
        f.createNewFile();
        FileOutputStream out = new FileOutputStream(f.toString());
        byte[] buffer = new byte[fileLength];
        out.write(buffer);
        out.flush();
        out.close();
        return f;
    }

    static void show(String s) {
        System.out.println(Thread.currentThread().getStackTrace()[2] + " " + s);
    }

    private void textTest(Path root, Configuration conf) throws Exception {
        PrintStream bak = null;
        try {
            final FileSystem fs = root.getFileSystem(conf);
            fs.mkdirs(root);
            OutputStream zout = new GZIPOutputStream(fs.create(new Path(root, "file.gz")));
            Random r = new Random();
            bak = System.out;
            ByteArrayOutputStream file = new ByteArrayOutputStream();
            for (int i = 0; i < 1024; ++i) {
                char c = Character.forDigit(r.nextInt(26) + 10, 36);
                file.write(c);
                zout.write(c);
            }
            zout.close();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            System.setOut(new PrintStream(out));
            String[] argv = new String[2];
            argv[0] = "-text";
            argv[1] = new Path(root, "file.gz").toString();
            int ret = ToolRunner.run(new FsShell(conf), argv);
            assertEquals("'-text " + argv[1] + " returned " + ret, 0, ret);
            assertTrue("Output doesn't match input", Arrays.equals(file.toByteArray(), out.toByteArray()));
            SequenceFile.Writer writer = SequenceFile.createWriter(conf, SequenceFile.Writer.file(new Path(root, "file.gz")), SequenceFile.Writer.keyClass(Text.class), SequenceFile.Writer.valueClass(Text.class));
            writer.append(new Text("Foo"), new Text("Bar"));
            writer.close();
            out = new ByteArrayOutputStream();
            System.setOut(new PrintStream(out));
            argv = new String[2];
            argv[0] = "-text";
            argv[1] = new Path(root, "file.gz").toString();
            ret = ToolRunner.run(new FsShell(conf), argv);
            assertEquals("'-text " + argv[1] + " returned " + ret, 0, ret);
            assertTrue("Output doesn't match input", Arrays.equals("Foo\tBar\n".getBytes(), out.toByteArray()));
            out.reset();
            OutputStream dout = new DeflaterOutputStream(fs.create(new Path(root, "file.deflate")));
            byte[] outbytes = "foo".getBytes();
            dout.write(outbytes);
            dout.close();
            out = new ByteArrayOutputStream();
            System.setOut(new PrintStream(out));
            argv = new String[2];
            argv[0] = "-text";
            argv[1] = new Path(root, "file.deflate").toString();
            ret = ToolRunner.run(new FsShell(conf), argv);
            assertEquals("'-text " + argv[1] + " returned " + ret, 0, ret);
            assertTrue("Output doesn't match input", Arrays.equals(outbytes, out.toByteArray()));
            out.reset();
            CompressionCodec codec = ReflectionUtils.newInstance(BZip2Codec.class, conf);
            String extension = codec.getDefaultExtension();
            Path p = new Path(root, "file." + extension);
            OutputStream fout = new DataOutputStream(codec.createOutputStream(fs.create(p, true)));
            byte[] writebytes = "foo".getBytes();
            fout.write(writebytes);
            fout.close();
            out = new ByteArrayOutputStream();
            System.setOut(new PrintStream(out));
            argv = new String[2];
            argv[0] = "-text";
            argv[1] = new Path(root, p).toString();
            ret = ToolRunner.run(new FsShell(conf), argv);
            assertEquals("'-text " + argv[1] + " returned " + ret, 0, ret);
            assertTrue("Output doesn't match input", Arrays.equals(writebytes, out.toByteArray()));
            out.reset();
            OutputStream pout = fs.create(new Path(root, "file.txt"));
            writebytes = "bar".getBytes();
            pout.write(writebytes);
            pout.close();
            out = new ByteArrayOutputStream();
            System.setOut(new PrintStream(out));
            argv = new String[2];
            argv[0] = "-text";
            argv[1] = new Path(root, "file.txt").toString();
            ret = ToolRunner.run(new FsShell(conf), argv);
            assertEquals("'-text " + argv[1] + " returned " + ret, 0, ret);
            assertTrue("Output doesn't match input", Arrays.equals(writebytes, out.toByteArray()));
            out.reset();
        } finally {
            if (null != bak) {
                System.setOut(bak);
            }
        }
    }

    static String createTree(FileSystem fs, String name) throws IOException {
        String path = "/test/" + name;
        Path root = mkdir(fs, new Path(path));
        Path sub = mkdir(fs, new Path(root, "sub"));
        Path root2 = mkdir(fs, new Path(path + "2"));
        writeFile(fs, new Path(root, "f1"));
        writeFile(fs, new Path(root, "f2"));
        writeFile(fs, new Path(sub, "f3"));
        writeFile(fs, new Path(sub, "f4"));
        writeFile(fs, new Path(root2, "f1"));
        mkdir(fs, new Path(root2, "sub"));
        return path;
    }

    private static void runCount(String path, long dirs, long files, FsShell shell) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(bytes);
        PrintStream oldOut = System.out;
        System.setOut(out);
        Scanner in = null;
        String results = null;
        try {
            runCmd(shell, "-count", path);
            results = bytes.toString();
            in = new Scanner(results);
            assertEquals(dirs, in.nextLong());
            assertEquals(files, in.nextLong());
        } finally {
            System.setOut(oldOut);
            if (in != null)
                in.close();
            IOUtils.closeStream(out);
            System.out.println("results:\n" + results);
        }
    }

    private static int runCmd(FsShell shell, String... args) throws IOException {
        StringBuilder cmdline = new StringBuilder("RUN:");
        for (String arg : args) cmdline.append(" " + arg);
        LOG.info(cmdline.toString());
        try {
            int exitCode;
            exitCode = shell.run(args);
            LOG.info("RUN: " + args[0] + " exit=" + exitCode);
            return exitCode;
        } catch (RuntimeException e) {
            LOG.error("RUN: " + args[0] + " RuntimeException=" + e.getMessage());
            throw e;
        } catch (Exception e) {
            LOG.error("RUN: " + args[0] + " Exception=" + e.getMessage());
            throw new IOException(StringUtils.stringifyException(e));
        }
    }

    private void confirmPermissionChange(String toApply, String expected, FileSystem fs, FsShell shell, Path dir2) throws IOException {
        LOG.info("Confirming permission change of " + toApply + " to " + expected);
        runCmd(shell, "-chmod", toApply, dir2.toString());
        String result = fs.getFileStatus(dir2).getPermission().toString();
        LOG.info("Permission change result: " + result);
        assertEquals(expected, result);
    }

    private void confirmOwner(String owner, String group, FileSystem fs, Path... paths) throws IOException {
        for (Path path : paths) {
            if (owner != null) {
                assertEquals(owner, fs.getFileStatus(path).getOwner());
            }
            if (group != null) {
                assertEquals(group, fs.getFileStatus(path).getGroup());
            }
        }
    }

    private static List<MaterializedReplica> getMaterializedReplicas(MiniDFSCluster cluster) throws IOException {
        List<MaterializedReplica> replicas = new ArrayList<>();
        String poolId = cluster.getNamesystem().getBlockPoolId();
        List<Map<DatanodeStorage, BlockListAsLongs>> blocks = cluster.getAllBlockReports(poolId);
        for (int i = 0; i < blocks.size(); i++) {
            Map<DatanodeStorage, BlockListAsLongs> map = blocks.get(i);
            for (Map.Entry<DatanodeStorage, BlockListAsLongs> e : map.entrySet()) {
                for (Block b : e.getValue()) {
                    replicas.add(cluster.getMaterializedReplica(i, new ExtendedBlock(poolId, b)));
                }
            }
        }
        return replicas;
    }

    private static void corrupt(List<MaterializedReplica> replicas, String content) throws IOException {
        StringBuilder sb = new StringBuilder(content);
        char c = content.charAt(0);
        sb.setCharAt(0, ++c);
        for (MaterializedReplica replica : replicas) {
            replica.corruptData(sb.toString().getBytes("UTF8"));
        }
    }

    static interface TestGetRunner {

        String run(int exitcode, String... options) throws IOException;
    }

    private static void doFsStat(Configuration conf, String format, Path... files) throws Exception {
        if (files == null || files.length == 0) {
            final String[] argv = (format == null ? new String[] { "-stat" } : new String[] { "-stat", format });
            assertEquals("Should have failed with missing arguments", -1, ToolRunner.run(new FsShell(conf), argv));
        } else {
            List<String> argv = new LinkedList<>();
            argv.add("-stat");
            if (format != null) {
                argv.add(format);
            }
            for (Path f : files) {
                argv.add(f.toString());
            }
            int ret = ToolRunner.run(new FsShell(conf), argv.toArray(new String[0]));
            assertEquals(argv + " returned non-zero status " + ret, 0, ret);
        }
    }

    private static String runLsr(final FsShell shell, String root, int returnvalue) throws Exception {
        System.out.println("root=" + root + ", returnvalue=" + returnvalue);
        final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        final PrintStream out = new PrintStream(bytes);
        final PrintStream oldOut = System.out;
        final PrintStream oldErr = System.err;
        System.setOut(out);
        System.setErr(out);
        final String results;
        try {
            assertEquals(returnvalue, shell.run(new String[] { "-lsr", root }));
            results = bytes.toString();
        } finally {
            System.setOut(oldOut);
            System.setErr(oldErr);
            IOUtils.closeStream(out);
        }
        System.out.println("results:\n" + results);
        return results;
    }

    private void doTestCopyCommandsWithRawXAttrs(FsShell shell, FileSystem fs, Path src, Path hdfsTestDir, boolean expectRaw) throws Exception {
        Path target;
        boolean srcIsRaw;
        if (src.isAbsolute()) {
            srcIsRaw = src.toString().contains("/.reserved/raw");
        } else {
            srcIsRaw = new Path(fs.getWorkingDirectory(), src).toString().contains("/.reserved/raw");
        }
        final boolean destIsRaw = hdfsTestDir.toString().contains("/.reserved/raw");
        final boolean srcDestMismatch = srcIsRaw ^ destIsRaw;
        if (srcDestMismatch) {
            doCopyAndTest(shell, hdfsTestDir, src, "-p", ERROR);
        } else {
            target = doCopyAndTest(shell, hdfsTestDir, src, "-p", SUCCESS);
            checkXAttrs(fs, target, expectRaw, false);
        }
        if (srcDestMismatch) {
            doCopyAndTest(shell, hdfsTestDir, src, "-px", ERROR);
        } else {
            target = doCopyAndTest(shell, hdfsTestDir, src, "-px", SUCCESS);
            checkXAttrs(fs, target, expectRaw, true);
        }
        if (srcDestMismatch) {
            doCopyAndTest(shell, hdfsTestDir, src, null, ERROR);
        } else {
            target = doCopyAndTest(shell, hdfsTestDir, src, null, SUCCESS);
            checkXAttrs(fs, target, expectRaw, false);
        }
    }

    private Path doCopyAndTest(FsShell shell, Path dest, Path src, String cpArgs, int expectedExitCode) throws Exception {
        final Path target = new Path(dest, "targetfile" + counter.getAndIncrement());
        final String[] argv = cpArgs == null ? new String[] { "-cp", src.toUri().toString(), target.toUri().toString() } : new String[] { "-cp", cpArgs, src.toUri().toString(), target.toUri().toString() };
        final int ret = ToolRunner.run(shell, argv);
        assertEquals("cp -p is not working", expectedExitCode, ret);
        return target;
    }

    private void checkXAttrs(FileSystem fs, Path target, boolean expectRaw, boolean expectVanillaXAttrs) throws Exception {
        final Map<String, byte[]> xattrs = fs.getXAttrs(target);
        int expectedCount = 0;
        if (expectRaw) {
            assertArrayEquals("raw.a1 has incorrect value", RAW_A1_VALUE, xattrs.get(RAW_A1));
            expectedCount++;
        }
        if (expectVanillaXAttrs) {
            assertArrayEquals("user.a1 has incorrect value", USER_A1_VALUE, xattrs.get(USER_A1));
            expectedCount++;
        }
        assertEquals("xattrs size mismatch", expectedCount, xattrs.size());
    }

    private void deleteFileUsingTrash(boolean serverTrash, boolean clientTrash) throws Exception {
        Configuration serverConf = new HdfsConfiguration();
        if (serverTrash) {
            serverConf.setLong(FS_TRASH_INTERVAL_KEY, 1);
        }
        MiniDFSCluster cluster = new MiniDFSCluster.Builder(serverConf).numDataNodes(1).format(true).build();
        Configuration clientConf = new Configuration(serverConf);
        if (clientTrash) {
            clientConf.setLong(FS_TRASH_INTERVAL_KEY, 1);
        } else {
            clientConf.setLong(FS_TRASH_INTERVAL_KEY, 0);
        }
        FsShell shell = new FsShell(clientConf);
        FileSystem fs = null;
        try {
            fs = cluster.getFileSystem();
            final String testdir = "/tmp/TestDFSShell-deleteFileUsingTrash-" + counter.getAndIncrement();
            writeFile(fs, new Path(testdir, "foo"));
            final String testFile = testdir + "/foo";
            final String trashFile = shell.getCurrentTrashDir() + "/" + testFile;
            String[] argv = new String[] { "-rm", testFile };
            int res = ToolRunner.run(shell, argv);
            assertEquals("rm failed", 0, res);
            if (serverTrash) {
                assertTrue("File not in trash", fs.exists(new Path(trashFile)));
            } else if (clientTrash) {
                assertTrue("File not in trashed", fs.exists(new Path(trashFile)));
            } else {
                assertFalse("File was not removed", fs.exists(new Path(testFile)));
                assertFalse("File was trashed", fs.exists(new Path(trashFile)));
            }
        } finally {
            if (fs != null) {
                fs.close();
            }
            if (cluster != null) {
                cluster.shutdown();
            }
        }
    }

    private void doSetXattr(ByteArrayOutputStream out, FsShell fshell, String[] setOp, String[] getOp, String[] expectArr, String[] dontExpectArr) throws Exception {
        int ret = ToolRunner.run(fshell, setOp);
        out.reset();
        ret = ToolRunner.run(fshell, getOp);
        final String str = out.toString();
        for (int i = 0; i < expectArr.length; i++) {
            final String expect = expectArr[i];
            final StringBuilder sb = new StringBuilder("Incorrect results from getfattr. Expected: ");
            sb.append(expect).append(" Full Result: ");
            sb.append(str);
            assertTrue(sb.toString(), str.indexOf(expect) != -1);
        }
        for (int i = 0; i < dontExpectArr.length; i++) {
            String dontExpect = dontExpectArr[i];
            final StringBuilder sb = new StringBuilder("Incorrect results from getfattr. Didn't Expect: ");
            sb.append(dontExpect).append(" Full Result: ");
            sb.append(str);
            assertTrue(sb.toString(), str.indexOf(dontExpect) == -1);
        }
        out.reset();
    }

    @Test(timeout = 30000)
    public void testZeroSizeFile_1_testMerged_1() throws IOException {
        final File f1 = new File(TEST_ROOT_DIR, "f1");
        assertTrue(!f1.exists());
        assertTrue(f1.createNewFile());
        assertTrue(f1.exists());
        assertTrue(f1.isFile());
        assertEquals(0L, f1.length());
    }

    @Test(timeout = 30000)
    public void testZeroSizeFile_6_testMerged_2() throws IOException {
        final File f2 = new File(TEST_ROOT_DIR, "f2");
        assertTrue(!f2.exists());
        dfs.copyToLocalFile(remotef, new Path(f2.getPath()));
        assertTrue(f2.exists());
        assertTrue(f2.isFile());
        assertEquals(0L, f2.length());
    }
}
