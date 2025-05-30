package org.apache.hadoop.hdfs.server.namenode;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.ReconfigurationException;
import org.apache.hadoop.hdfs.DFSConfigKeys;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.hdfs.MiniDFSNNTopology;
import org.apache.hadoop.hdfs.server.blockmanagement.BlockManager;
import org.apache.hadoop.test.LambdaTestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestRefreshNamenodeReplicationConfig_Purified {

    private MiniDFSCluster cluster = null;

    private BlockManager bm;

    @Before
    public void setup() throws IOException {
        Configuration config = new Configuration();
        config.setInt(DFSConfigKeys.DFS_NAMENODE_REPLICATION_MAX_STREAMS_KEY, 8);
        config.setInt(DFSConfigKeys.DFS_NAMENODE_REPLICATION_STREAMS_HARD_LIMIT_KEY, 10);
        config.setInt(DFSConfigKeys.DFS_NAMENODE_REPLICATION_WORK_MULTIPLIER_PER_ITERATION, 12);
        config.setInt(DFSConfigKeys.DFS_NAMENODE_RECONSTRUCTION_PENDING_TIMEOUT_SEC_KEY, 300);
        cluster = new MiniDFSCluster.Builder(config).nnTopology(MiniDFSNNTopology.simpleSingleNN(0, 0)).numDataNodes(0).build();
        cluster.waitActive();
        bm = cluster.getNameNode().getNamesystem().getBlockManager();
    }

    @After
    public void teardown() throws IOException {
        cluster.shutdown();
    }

    @Test(timeout = 90000)
    public void testParamsCanBeReconfigured_1() throws ReconfigurationException {
        assertEquals(8, bm.getMaxReplicationStreams());
    }

    @Test(timeout = 90000)
    public void testParamsCanBeReconfigured_2() throws ReconfigurationException {
        assertEquals(10, bm.getReplicationStreamsHardLimit());
    }

    @Test(timeout = 90000)
    public void testParamsCanBeReconfigured_3() throws ReconfigurationException {
        assertEquals(12, bm.getBlocksReplWorkMultiplier());
    }

    @Test(timeout = 90000)
    public void testParamsCanBeReconfigured_4() throws ReconfigurationException {
        assertEquals(300, bm.getReconstructionPendingTimeout());
    }

    @Test(timeout = 90000)
    public void testParamsCanBeReconfigured_5() throws ReconfigurationException {
        assertEquals(20, bm.getMaxReplicationStreams());
    }

    @Test(timeout = 90000)
    public void testParamsCanBeReconfigured_6() throws ReconfigurationException {
        assertEquals(22, bm.getReplicationStreamsHardLimit());
    }

    @Test(timeout = 90000)
    public void testParamsCanBeReconfigured_7() throws ReconfigurationException {
        assertEquals(24, bm.getBlocksReplWorkMultiplier());
    }

    @Test(timeout = 90000)
    public void testParamsCanBeReconfigured_8() throws ReconfigurationException {
        assertEquals(180, bm.getReconstructionPendingTimeout());
    }
}
