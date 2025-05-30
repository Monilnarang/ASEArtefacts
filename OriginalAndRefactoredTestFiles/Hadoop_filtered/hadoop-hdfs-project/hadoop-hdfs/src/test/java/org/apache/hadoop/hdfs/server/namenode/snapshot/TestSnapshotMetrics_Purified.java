package org.apache.hadoop.hdfs.server.namenode.snapshot;

import static org.apache.hadoop.test.MetricsAsserts.assertCounter;
import static org.apache.hadoop.test.MetricsAsserts.assertGauge;
import static org.apache.hadoop.test.MetricsAsserts.getMetrics;
import static org.junit.Assert.assertEquals;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DFSTestUtil;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.hdfs.protocol.SnapshottableDirectoryStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestSnapshotMetrics_Purified {

    private static final long seed = 0;

    private static final short REPLICATION = 3;

    private static final String NN_METRICS = "NameNodeActivity";

    private static final String NS_METRICS = "FSNamesystem";

    private final Path dir = new Path("/TestSnapshot");

    private final Path sub1 = new Path(dir, "sub1");

    private final Path file1 = new Path(sub1, "file1");

    private final Path file2 = new Path(sub1, "file2");

    private Configuration conf;

    private MiniDFSCluster cluster;

    private DistributedFileSystem hdfs;

    @Before
    public void setUp() throws Exception {
        conf = new Configuration();
        cluster = new MiniDFSCluster.Builder(conf).numDataNodes(REPLICATION).build();
        cluster.waitActive();
        hdfs = cluster.getFileSystem();
        DFSTestUtil.createFile(hdfs, file1, 1024, REPLICATION, seed);
        DFSTestUtil.createFile(hdfs, file2, 1024, REPLICATION, seed);
    }

    @After
    public void tearDown() throws Exception {
        if (cluster != null) {
            cluster.shutdown();
            cluster = null;
        }
    }

    @Test
    public void testSnapshottableDirs_1() throws Exception {
        assertGauge("SnapshottableDirectories", 0, getMetrics(NS_METRICS));
    }

    @Test
    public void testSnapshottableDirs_2() throws Exception {
        assertCounter("AllowSnapshotOps", 0L, getMetrics(NN_METRICS));
    }

    @Test
    public void testSnapshottableDirs_3() throws Exception {
        assertCounter("DisallowSnapshotOps", 0L, getMetrics(NN_METRICS));
    }

    @Test
    public void testSnapshottableDirs_4() throws Exception {
        assertGauge("SnapshottableDirectories", 1, getMetrics(NS_METRICS));
    }

    @Test
    public void testSnapshottableDirs_5() throws Exception {
        assertCounter("AllowSnapshotOps", 1L, getMetrics(NN_METRICS));
    }

    @Test
    public void testSnapshottableDirs_6() throws Exception {
        assertGauge("SnapshottableDirectories", 2, getMetrics(NS_METRICS));
    }

    @Test
    public void testSnapshottableDirs_7() throws Exception {
        assertCounter("AllowSnapshotOps", 2L, getMetrics(NN_METRICS));
    }

    @Test
    public void testSnapshottableDirs_8() throws Exception {
        assertGauge("SnapshottableDirectories", 3, getMetrics(NS_METRICS));
    }

    @Test
    public void testSnapshottableDirs_9() throws Exception {
        assertCounter("AllowSnapshotOps", 3L, getMetrics(NN_METRICS));
    }

    @Test
    public void testSnapshottableDirs_10() throws Exception {
        assertGauge("SnapshottableDirectories", 3, getMetrics(NS_METRICS));
    }

    @Test
    public void testSnapshottableDirs_11() throws Exception {
        assertCounter("AllowSnapshotOps", 4L, getMetrics(NN_METRICS));
    }

    @Test
    public void testSnapshottableDirs_12() throws Exception {
        assertGauge("SnapshottableDirectories", 2, getMetrics(NS_METRICS));
    }

    @Test
    public void testSnapshottableDirs_13() throws Exception {
        assertCounter("DisallowSnapshotOps", 1L, getMetrics(NN_METRICS));
    }

    @Test
    public void testSnapshottableDirs_14() throws Exception {
        assertGauge("SnapshottableDirectories", 1, getMetrics(NS_METRICS));
    }

    @Test
    public void testSnapshottableDirs_15() throws Exception {
        hdfs.allowSnapshot(sub1);
        Path sub2 = new Path(dir, "sub2");
        DFSTestUtil.createFile(hdfs, file, 1024, REPLICATION, seed);
        hdfs.allowSnapshot(sub2);
        Path subsub1 = new Path(sub1, "sub1sub1");
        DFSTestUtil.createFile(hdfs, subfile, 1024, REPLICATION, seed);
        hdfs.allowSnapshot(subsub1);
        hdfs.allowSnapshot(sub1);
        hdfs.disallowSnapshot(sub1);
        hdfs.delete(subsub1, true);
        SnapshottableDirectoryStatus[] status = hdfs.getSnapshottableDirListing();
        assertEquals(1, status.length);
    }

    @Test
    public void testSnapshottableDirs_16() throws Exception {
        assertCounter("ListSnapshottableDirOps", 1L, getMetrics(NN_METRICS));
    }
}
