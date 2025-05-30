package org.apache.hadoop.hdfs.server.namenode.metrics;

import static org.apache.hadoop.test.MetricsAsserts.assertCounter;
import static org.apache.hadoop.test.MetricsAsserts.getMetrics;
import java.io.IOException;
import java.util.Random;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DFSConfigKeys;
import org.apache.hadoop.hdfs.DFSTestUtil;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.HdfsConfiguration;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.hdfs.protocol.HdfsFileStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestNNMetricFilesInGetListingOps_Purified {

    private static final Configuration CONF = new HdfsConfiguration();

    private static final String NN_METRICS = "NameNodeActivity";

    static {
        CONF.setLong(DFSConfigKeys.DFS_BLOCK_SIZE_KEY, 100);
        CONF.setInt(DFSConfigKeys.DFS_BYTES_PER_CHECKSUM_KEY, 1);
        CONF.setLong(DFSConfigKeys.DFS_HEARTBEAT_INTERVAL_KEY, 1L);
        CONF.setInt(DFSConfigKeys.DFS_NAMENODE_REDUNDANCY_INTERVAL_SECONDS_KEY, 1);
    }

    private MiniDFSCluster cluster;

    private DistributedFileSystem fs;

    private final Random rand = new Random();

    @Before
    public void setUp() throws Exception {
        cluster = new MiniDFSCluster.Builder(CONF).build();
        cluster.waitActive();
        cluster.getNameNode();
        fs = cluster.getFileSystem();
    }

    @After
    public void tearDown() throws Exception {
        if (cluster != null) {
            cluster.shutdown();
            cluster = null;
        }
    }

    private void createFile(String fileName, long fileLen, short replicas) throws IOException {
        Path filePath = new Path(fileName);
        DFSTestUtil.createFile(fs, filePath, fileLen, replicas, rand.nextLong());
    }

    @Test
    public void testFilesInGetListingOps_1() throws Exception {
        assertCounter("FilesInGetListingOps", 2L, getMetrics(NN_METRICS));
    }

    @Test
    public void testFilesInGetListingOps_2() throws Exception {
        assertCounter("FilesInGetListingOps", 4L, getMetrics(NN_METRICS));
    }
}
