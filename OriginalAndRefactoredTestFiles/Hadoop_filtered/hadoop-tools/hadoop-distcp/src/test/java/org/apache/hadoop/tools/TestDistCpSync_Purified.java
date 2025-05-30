package org.apache.hadoop.tools;

import org.apache.hadoop.HadoopIllegalArgumentException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.contract.ContractTestUtils;
import org.apache.hadoop.fs.RawLocalFileSystem;
import org.apache.hadoop.fs.CommonPathCapabilities;
import org.apache.hadoop.hdfs.DFSTestUtil;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.HdfsConfiguration;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.hdfs.protocol.HdfsConstants;
import org.apache.hadoop.hdfs.protocol.SnapshotDiffReport;
import org.apache.hadoop.hdfs.web.WebHdfsConstants;
import org.apache.hadoop.hdfs.web.WebHdfsFileSystem;
import org.apache.hadoop.hdfs.web.WebHdfsTestUtil;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.security.Credentials;
import org.apache.hadoop.test.GenericTestUtils;
import org.apache.hadoop.test.LambdaTestUtils;
import org.apache.hadoop.tools.mapred.CopyMapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.net.URI;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import static org.apache.hadoop.fs.impl.PathCapabilitiesSupport.validatePathCapabilityArgs;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestDistCpSync_Purified {

    private MiniDFSCluster cluster;

    private final Configuration conf = new HdfsConfiguration();

    private DistributedFileSystem dfs;

    private WebHdfsFileSystem webfs;

    private DistCpContext context;

    private final Path source = new Path("/source");

    private final Path target = new Path("/target");

    private final long BLOCK_SIZE = 1024;

    private final short DATA_NUM = 1;

    @Before
    public void setUp() throws Exception {
        cluster = new MiniDFSCluster.Builder(conf).numDataNodes(DATA_NUM).build();
        cluster.waitActive();
        webfs = WebHdfsTestUtil.getWebHdfsFileSystem(conf, WebHdfsConstants.WEBHDFS_SCHEME);
        dfs = cluster.getFileSystem();
        dfs.mkdirs(source);
        dfs.mkdirs(target);
        final DistCpOptions options = new DistCpOptions.Builder(Collections.singletonList(source), target).withSyncFolder(true).withUseDiff("s1", "s2").build();
        options.appendToConf(conf);
        context = new DistCpContext(options);
        conf.set(DistCpConstants.CONF_LABEL_TARGET_WORK_PATH, target.toString());
        conf.set(DistCpConstants.CONF_LABEL_TARGET_FINAL_PATH, target.toString());
        conf.setClass("fs.dummy.impl", DummyFs.class, FileSystem.class);
    }

    @After
    public void tearDown() throws Exception {
        IOUtils.cleanupWithLogger(null, dfs);
        if (cluster != null) {
            cluster.shutdown();
        }
    }

    private void enableAndCreateFirstSnapshot() throws Exception {
        dfs.allowSnapshot(source);
        dfs.allowSnapshot(target);
        dfs.createSnapshot(source, "s1");
        dfs.createSnapshot(target, "s1");
    }

    private void syncAndVerify() throws Exception {
        Assert.assertTrue(sync());
        verifyCopy(dfs.getFileStatus(source), dfs.getFileStatus(target), false);
    }

    private boolean sync() throws Exception {
        DistCpSync distCpSync = new DistCpSync(context, conf);
        return distCpSync.sync();
    }

    private void initData(Path dir) throws Exception {
        initData(dfs, dir);
    }

    private void initData(FileSystem fs, Path dir) throws Exception {
        final Path foo = new Path(dir, "foo");
        final Path bar = new Path(dir, "bar");
        final Path d1 = new Path(foo, "d1");
        final Path f1 = new Path(foo, "f1");
        final Path d2 = new Path(bar, "d2");
        final Path f2 = new Path(bar, "f2");
        final Path f3 = new Path(d1, "f3");
        final Path f4 = new Path(d2, "f4");
        DFSTestUtil.createFile(fs, f1, BLOCK_SIZE, DATA_NUM, 0);
        DFSTestUtil.createFile(fs, f2, BLOCK_SIZE, DATA_NUM, 0);
        DFSTestUtil.createFile(fs, f3, BLOCK_SIZE, DATA_NUM, 0);
        DFSTestUtil.createFile(fs, f4, BLOCK_SIZE, DATA_NUM, 0);
    }

    private int changeData(FileSystem fs, Path dir) throws Exception {
        final Path foo = new Path(dir, "foo");
        final Path bar = new Path(dir, "bar");
        final Path d1 = new Path(foo, "d1");
        final Path f2 = new Path(bar, "f2");
        final Path bar_d1 = new Path(bar, "d1");
        int numCreatedModified = 0;
        fs.rename(d1, bar_d1);
        numCreatedModified += 1;
        numCreatedModified += 1;
        final Path f3 = new Path(bar_d1, "f3");
        fs.delete(f3, true);
        final Path newfoo = new Path(bar_d1, "foo");
        fs.rename(foo, newfoo);
        numCreatedModified += 1;
        final Path f1 = new Path(newfoo, "f1");
        fs.delete(f1, true);
        DFSTestUtil.createFile(fs, f1, 2 * BLOCK_SIZE, DATA_NUM, 0);
        numCreatedModified += 1;
        DFSTestUtil.appendFile(fs, f2, (int) BLOCK_SIZE);
        numCreatedModified += 1;
        fs.rename(bar, new Path(dir, "foo"));
        return numCreatedModified;
    }

    private Map<Text, CopyListingFileStatus> getListing(Path listingPath) throws Exception {
        SequenceFile.Reader reader = new SequenceFile.Reader(conf, SequenceFile.Reader.file(listingPath));
        Text key = new Text();
        CopyListingFileStatus value = new CopyListingFileStatus();
        Map<Text, CopyListingFileStatus> values = new HashMap<>();
        while (reader.next(key, value)) {
            values.put(key, value);
            key = new Text();
            value = new CopyListingFileStatus();
        }
        return values;
    }

    private void verifyCopy(FileStatus s, FileStatus t, boolean compareName) throws Exception {
        verifyCopy(dfs, dfs, s, t, compareName);
    }

    private void verifyCopyByFs(FileSystem sfs, FileSystem tfs, FileStatus s, FileStatus t, boolean compareName) throws Exception {
        verifyCopy(sfs, tfs, s, t, compareName);
    }

    private void verifyCopy(FileSystem sfs, FileSystem tfs, FileStatus s, FileStatus t, boolean compareName) throws Exception {
        Assert.assertEquals(s.isDirectory(), t.isDirectory());
        if (compareName) {
            Assert.assertEquals(s.getPath().getName(), t.getPath().getName());
        }
        if (!s.isDirectory()) {
            byte[] sbytes = DFSTestUtil.readFileBuffer(sfs, s.getPath());
            byte[] tbytes = DFSTestUtil.readFileBuffer(tfs, t.getPath());
            Assert.assertArrayEquals(sbytes, tbytes);
        } else {
            FileStatus[] slist = sfs.listStatus(s.getPath());
            FileStatus[] tlist = tfs.listStatus(t.getPath());
            Assert.assertEquals(slist.length, tlist.length);
            for (int i = 0; i < slist.length; i++) {
                verifyCopy(sfs, tfs, slist[i], tlist[i], true);
            }
        }
    }

    private void initData2(Path dir) throws Exception {
        final Path test = new Path(dir, "test");
        final Path foo = new Path(dir, "foo");
        final Path bar = new Path(dir, "bar");
        final Path f1 = new Path(test, "f1");
        final Path f2 = new Path(foo, "f2");
        final Path f3 = new Path(bar, "f3");
        DFSTestUtil.createFile(dfs, f1, BLOCK_SIZE, DATA_NUM, 0L);
        DFSTestUtil.createFile(dfs, f2, BLOCK_SIZE, DATA_NUM, 1L);
        DFSTestUtil.createFile(dfs, f3, BLOCK_SIZE, DATA_NUM, 2L);
    }

    private void changeData2(Path dir) throws Exception {
        final Path tmpFoo = new Path(dir, "tmpFoo");
        final Path test = new Path(dir, "test");
        final Path foo = new Path(dir, "foo");
        final Path bar = new Path(dir, "bar");
        dfs.rename(test, tmpFoo);
        dfs.rename(foo, test);
        dfs.rename(bar, foo);
        dfs.rename(tmpFoo, bar);
    }

    private void initData3(Path dir) throws Exception {
        final Path test = new Path(dir, "test");
        final Path foo = new Path(dir, "foo");
        final Path bar = new Path(dir, "bar");
        final Path f1 = new Path(test, "file");
        final Path f2 = new Path(foo, "file");
        final Path f3 = new Path(bar, "file");
        DFSTestUtil.createFile(dfs, f1, BLOCK_SIZE, DATA_NUM, 0L);
        DFSTestUtil.createFile(dfs, f2, BLOCK_SIZE * 2, DATA_NUM, 1L);
        DFSTestUtil.createFile(dfs, f3, BLOCK_SIZE * 3, DATA_NUM, 2L);
    }

    private void changeData3(Path dir) throws Exception {
        final Path test = new Path(dir, "test");
        final Path foo = new Path(dir, "foo");
        final Path bar = new Path(dir, "bar");
        final Path f1 = new Path(test, "file");
        final Path f2 = new Path(foo, "file");
        final Path f3 = new Path(bar, "file");
        final Path newf1 = new Path(test, "newfile");
        final Path newf2 = new Path(foo, "newfile");
        final Path newf3 = new Path(bar, "newfile");
        dfs.rename(f1, newf1);
        dfs.rename(f2, newf2);
        dfs.rename(f3, newf3);
    }

    private void initData4(Path dir) throws Exception {
        final Path d1 = new Path(dir, "d1");
        final Path d2 = new Path(d1, "d2");
        final Path f1 = new Path(d2, "f1");
        DFSTestUtil.createFile(dfs, f1, BLOCK_SIZE, DATA_NUM, 0L);
    }

    private void changeData4(Path dir) throws Exception {
        final Path d1 = new Path(dir, "d1");
        final Path d11 = new Path(dir, "d11");
        final Path d2 = new Path(d1, "d2");
        final Path d21 = new Path(d1, "d21");
        final Path f1 = new Path(d2, "f1");
        dfs.delete(f1, false);
        dfs.rename(d2, d21);
        dfs.rename(d1, d11);
    }

    private void initData5(Path dir) throws Exception {
        final Path d1 = new Path(dir, "d1");
        final Path d2 = new Path(dir, "d2");
        final Path f1 = new Path(d1, "f1");
        final Path f2 = new Path(d2, "f2");
        DFSTestUtil.createFile(dfs, f1, BLOCK_SIZE, DATA_NUM, 0L);
        DFSTestUtil.createFile(dfs, f2, BLOCK_SIZE, DATA_NUM, 0L);
    }

    private void changeData5(Path dir) throws Exception {
        final Path d1 = new Path(dir, "d1");
        final Path d2 = new Path(dir, "d2");
        final Path f1 = new Path(d1, "f1");
        final Path tmp = new Path(dir, "tmp");
        dfs.delete(f1, false);
        dfs.rename(d1, tmp);
        dfs.rename(d2, d1);
        final Path f2 = new Path(d1, "f2");
        dfs.delete(f2, false);
    }

    private void initData6(Path dir) throws Exception {
        final Path foo = new Path(dir, "foo");
        final Path bar = new Path(dir, "bar");
        final Path foo_f1 = new Path(foo, "f1");
        final Path bar_f1 = new Path(bar, "f1");
        DFSTestUtil.createFile(dfs, foo_f1, BLOCK_SIZE, DATA_NUM, 0L);
        DFSTestUtil.createFile(dfs, bar_f1, BLOCK_SIZE, DATA_NUM, 0L);
    }

    private int changeData6(Path dir) throws Exception {
        final Path foo = new Path(dir, "foo");
        final Path bar = new Path(dir, "bar");
        final Path foo2 = new Path(dir, "foo2");
        final Path foo_f1 = new Path(foo, "f1");
        int numCreatedModified = 0;
        dfs.rename(foo, foo2);
        dfs.rename(bar, foo);
        dfs.rename(foo2, bar);
        DFSTestUtil.appendFile(dfs, foo_f1, (int) BLOCK_SIZE);
        numCreatedModified += 1;
        return numCreatedModified;
    }

    private void initData7(Path dir) throws Exception {
        final Path foo = new Path(dir, "foo");
        final Path bar = new Path(dir, "bar");
        final Path foo_f1 = new Path(foo, "f1");
        final Path bar_f1 = new Path(bar, "f1");
        DFSTestUtil.createFile(dfs, foo_f1, BLOCK_SIZE, DATA_NUM, 0L);
        DFSTestUtil.createFile(dfs, bar_f1, BLOCK_SIZE, DATA_NUM, 0L);
    }

    private int changeData7(Path dir) throws Exception {
        final Path foo = new Path(dir, "foo");
        final Path foo2 = new Path(dir, "foo2");
        final Path foo_f1 = new Path(foo, "f1");
        final Path foo2_f2 = new Path(foo2, "f2");
        final Path foo_d1 = new Path(foo, "d1");
        final Path foo_d1_f3 = new Path(foo_d1, "f3");
        int numCreatedModified = 0;
        dfs.rename(foo, foo2);
        DFSTestUtil.createFile(dfs, foo_f1, BLOCK_SIZE, DATA_NUM, 0L);
        numCreatedModified += 2;
        DFSTestUtil.appendFile(dfs, foo_f1, (int) BLOCK_SIZE);
        dfs.rename(foo_f1, foo2_f2);
        numCreatedModified -= 1;
        numCreatedModified += 2;
        DFSTestUtil.createFile(dfs, foo_d1_f3, BLOCK_SIZE, DATA_NUM, 0L);
        numCreatedModified += 2;
        return numCreatedModified;
    }

    private void initData8(Path dir) throws Exception {
        final Path foo = new Path(dir, "foo");
        final Path bar = new Path(dir, "bar");
        final Path d1 = new Path(dir, "d1");
        final Path foo_f1 = new Path(foo, "f1");
        final Path bar_f1 = new Path(bar, "f1");
        final Path d1_f1 = new Path(d1, "f1");
        DFSTestUtil.createFile(dfs, foo_f1, BLOCK_SIZE, DATA_NUM, 0L);
        DFSTestUtil.createFile(dfs, bar_f1, BLOCK_SIZE, DATA_NUM, 0L);
        DFSTestUtil.createFile(dfs, d1_f1, BLOCK_SIZE, DATA_NUM, 0L);
    }

    private int changeData8(Path dir) throws Exception {
        final Path foo = new Path(dir, "foo");
        final Path createdDir = new Path(dir, "c");
        final Path d1 = new Path(dir, "d1");
        final Path d1_f1 = new Path(d1, "f1");
        final Path createdDir_f1 = new Path(createdDir, "f1");
        final Path foo_f3 = new Path(foo, "f3");
        final Path new_foo = new Path(createdDir, "foo");
        final Path foo_f4 = new Path(foo, "f4");
        final Path foo_d1 = new Path(foo, "d1");
        final Path bar = new Path(dir, "bar");
        final Path bar1 = new Path(dir, "bar1");
        int numCreatedModified = 0;
        DFSTestUtil.createFile(dfs, foo_f3, BLOCK_SIZE, DATA_NUM, 0L);
        numCreatedModified += 1;
        DFSTestUtil.createFile(dfs, createdDir_f1, BLOCK_SIZE, DATA_NUM, 0L);
        numCreatedModified += 1;
        dfs.rename(createdDir_f1, foo_f4);
        numCreatedModified += 1;
        dfs.rename(d1_f1, createdDir_f1);
        numCreatedModified += 1;
        dfs.rename(d1, foo_d1);
        numCreatedModified += 1;
        dfs.rename(foo, new_foo);
        dfs.rename(bar, bar1);
        return numCreatedModified;
    }

    private void initData9(Path dir) throws Exception {
        final Path foo = new Path(dir, "foo");
        final Path foo_f1 = new Path(foo, "f1");
        DFSTestUtil.createFile(dfs, foo_f1, BLOCK_SIZE, DATA_NUM, 0L);
    }

    private void changeData9(Path dir) throws Exception {
        final Path foo = new Path(dir, "foo");
        final Path foo_f2 = new Path(foo, "f2");
        DFSTestUtil.createFile(dfs, foo_f2, BLOCK_SIZE, DATA_NUM, 0L);
    }

    private void initData10(Path dir) throws Exception {
        final Path staging = new Path(dir, ".staging");
        final Path stagingF1 = new Path(staging, "f1");
        final Path data = new Path(dir, "data");
        final Path dataF1 = new Path(data, "f1");
        DFSTestUtil.createFile(dfs, stagingF1, BLOCK_SIZE, DATA_NUM, 0L);
        DFSTestUtil.createFile(dfs, dataF1, BLOCK_SIZE, DATA_NUM, 0L);
    }

    private void changeData10(Path dir) throws Exception {
        final Path staging = new Path(dir, ".staging");
        final Path prod = new Path(dir, "prod");
        dfs.rename(staging, prod);
    }

    private java.nio.file.Path generateFilterFile(String fileName) throws IOException {
        java.nio.file.Path tmpFile = Files.createTempFile(fileName, "txt");
        String str = ".*\\.staging.*";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tmpFile.toString()))) {
            writer.write(str);
        }
        return tmpFile;
    }

    private void deleteFilterFile(java.nio.file.Path filePath) throws IOException {
        Files.delete(filePath);
    }

    private void initData11(Path dir) throws Exception {
        final Path staging = new Path(dir, "prod");
        final Path stagingF1 = new Path(staging, "f1");
        final Path data = new Path(dir, "data");
        final Path dataF1 = new Path(data, "f1");
        DFSTestUtil.createFile(dfs, stagingF1, BLOCK_SIZE, DATA_NUM, 0L);
        DFSTestUtil.createFile(dfs, dataF1, BLOCK_SIZE, DATA_NUM, 0L);
    }

    private void changeData11(Path dir) throws Exception {
        final Path staging = new Path(dir, "prod");
        final Path prod = new Path(dir, ".staging");
        dfs.rename(staging, prod);
    }

    private void verifySync(FileStatus s, FileStatus t, boolean compareName, String deletedName) throws Exception {
        Assert.assertEquals(s.isDirectory(), t.isDirectory());
        if (compareName) {
            Assert.assertEquals(s.getPath().getName(), t.getPath().getName());
        }
        if (!s.isDirectory()) {
            byte[] sbytes = DFSTestUtil.readFileBuffer(dfs, s.getPath());
            byte[] tbytes = DFSTestUtil.readFileBuffer(dfs, t.getPath());
            Assert.assertArrayEquals(sbytes, tbytes);
        } else {
            FileStatus[] slist = dfs.listStatus(s.getPath());
            FileStatus[] tlist = dfs.listStatus(t.getPath());
            int minFiles = tlist.length;
            if (slist.length < tlist.length) {
                minFiles = slist.length;
            }
            for (int i = 0; i < minFiles; i++) {
                if (slist[i].getPath().getName().contains(deletedName)) {
                    if (tlist[i].getPath().getName().contains(deletedName)) {
                        throw new Exception("Target is not synced as per exclusion filter");
                    }
                    continue;
                }
                verifySync(slist[i], tlist[i], true, deletedName);
            }
        }
    }

    private void snapshotDiffWithPaths(Path sourceFSPath, Path targetFSPath) throws Exception {
        FileSystem sourceFS = sourceFSPath.getFileSystem(conf);
        FileSystem targetFS = targetFSPath.getFileSystem(conf);
        initData(sourceFS, sourceFSPath);
        initData(targetFS, targetFSPath);
        List<Path> paths = Arrays.asList(sourceFSPath, targetFSPath);
        for (Path path : paths) {
            FileSystem fs = path.getFileSystem(conf);
            if (fs instanceof DistributedFileSystem) {
                ((DistributedFileSystem) fs).allowSnapshot(path);
            } else if (fs instanceof WebHdfsFileSystem) {
                ((WebHdfsFileSystem) fs).allowSnapshot(path);
            } else {
                throw new IOException("Unsupported fs: " + fs.getScheme());
            }
            fs.createSnapshot(path, "s1");
        }
        changeData(sourceFS, sourceFSPath);
        sourceFS.createSnapshot(sourceFSPath, "s2");
        final DistCpOptions options = new DistCpOptions.Builder(Collections.singletonList(sourceFSPath), targetFSPath).withUseDiff("s1", "s2").withSyncFolder(true).build();
        options.appendToConf(conf);
        new DistCp(conf, options).execute();
        verifyCopyByFs(sourceFS, targetFS, sourceFS.getFileStatus(sourceFSPath), targetFS.getFileStatus(targetFSPath), false);
    }

    public static class DummyFs extends RawLocalFileSystem {

        public DummyFs() {
            super();
        }

        public URI getUri() {
            return URI.create("dummy:///");
        }

        @Override
        public boolean hasPathCapability(Path path, String capability) throws IOException {
            switch(validatePathCapabilityArgs(makeQualified(path), capability)) {
                case CommonPathCapabilities.FS_SNAPSHOTS:
                    return true;
                default:
                    return super.hasPathCapability(path, capability);
            }
        }

        @Override
        public FileStatus getFileStatus(Path f) throws IOException {
            return new FileStatus();
        }

        public SnapshotDiffReport getSnapshotDiffReport(final Path snapshotDir, final String fromSnapshot, final String toSnapshot) {
            return new SnapshotDiffReport(snapshotDir.getName(), fromSnapshot, toSnapshot, new ArrayList<SnapshotDiffReport.DiffReportEntry>());
        }
    }

    @Test
    public void testFallback_1_testMerged_1() throws Exception {
        Assert.assertFalse(sync());
    }

    @Test
    public void testFallback_2_testMerged_2() throws Exception {
        final Path spath = new Path(source, HdfsConstants.DOT_SNAPSHOT_DIR + Path.SEPARATOR + "s2");
        Assert.assertEquals(spath, context.getSourcePaths().get(0));
    }

    @Test
    public void testFallback_5_testMerged_3() throws Exception {
        Assert.assertTrue(sync());
    }
}
