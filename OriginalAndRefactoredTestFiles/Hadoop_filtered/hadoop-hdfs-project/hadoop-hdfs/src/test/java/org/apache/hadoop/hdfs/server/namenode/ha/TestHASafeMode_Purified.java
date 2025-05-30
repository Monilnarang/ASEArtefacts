package org.apache.hadoop.hdfs.server.namenode.ha;

import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_HA_NN_NOT_BECOME_ACTIVE_IN_SAFEMODE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.hadoop.ha.ServiceFailedException;
import org.apache.hadoop.test.LambdaTestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.ha.HAServiceProtocol.RequestSource;
import org.apache.hadoop.ha.HAServiceProtocol.StateChangeRequestInfo;
import org.apache.hadoop.hdfs.DFSClient;
import org.apache.hadoop.hdfs.DFSClientAdapter;
import org.apache.hadoop.hdfs.DFSConfigKeys;
import org.apache.hadoop.hdfs.DFSOutputStream;
import org.apache.hadoop.hdfs.DFSTestUtil;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.hdfs.MiniDFSNNTopology;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;
import org.apache.hadoop.hdfs.protocol.ExtendedBlock;
import org.apache.hadoop.hdfs.protocol.HdfsConstants.SafeModeAction;
import org.apache.hadoop.hdfs.server.blockmanagement.BlockManagerTestUtil;
import org.apache.hadoop.hdfs.server.namenode.FSImage;
import org.apache.hadoop.hdfs.server.namenode.FSNamesystem;
import org.apache.hadoop.hdfs.server.namenode.NameNode;
import org.apache.hadoop.hdfs.server.namenode.NameNodeAdapter;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.ipc.RemoteException;
import org.apache.hadoop.ipc.StandbyException;
import org.apache.hadoop.ipc.protobuf.RpcHeaderProtos.RpcResponseHeaderProto.RpcErrorCodeProto;
import org.apache.hadoop.test.GenericTestUtils;
import org.apache.hadoop.test.Whitebox;
import org.apache.hadoop.util.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.event.Level;
import java.util.function.Supplier;

public class TestHASafeMode_Purified {

    private static final Logger LOG = LoggerFactory.getLogger(TestHASafeMode.class);

    private static final int BLOCK_SIZE = 1024;

    private NameNode nn0;

    private NameNode nn1;

    private FileSystem fs;

    private MiniDFSCluster cluster;

    static {
        DFSTestUtil.setNameNodeLogLevel(Level.TRACE);
        GenericTestUtils.setLogLevel(FSImage.LOG, Level.TRACE);
    }

    @Before
    public void setupCluster() throws Exception {
        Configuration conf = new Configuration();
        conf.setInt(DFSConfigKeys.DFS_BLOCK_SIZE_KEY, BLOCK_SIZE);
        conf.setInt(DFSConfigKeys.DFS_HEARTBEAT_INTERVAL_KEY, 1);
        conf.setInt(DFSConfigKeys.DFS_HA_TAILEDITS_PERIOD_KEY, 1);
        conf.setBoolean("dfs.namenode.snapshot.trashroot.enabled", false);
        cluster = new MiniDFSCluster.Builder(conf).nnTopology(MiniDFSNNTopology.simpleHATopology()).numDataNodes(3).waitSafeMode(false).build();
        cluster.waitActive();
        nn0 = cluster.getNameNode(0);
        nn1 = cluster.getNameNode(1);
        fs = HATestUtil.configureFailoverFs(cluster, conf);
        cluster.transitionToActive(0);
    }

    @After
    public void shutdownCluster() {
        if (cluster != null) {
            cluster.shutdown();
            cluster = null;
        }
    }

    private void restartStandby() throws IOException {
        cluster.shutdownNameNode(1);
        cluster.getConfiguration(1).setInt(DFSConfigKeys.DFS_NAMENODE_SAFEMODE_EXTENSION_KEY, 30000);
        cluster.getConfiguration(1).setInt(DFSConfigKeys.DFS_HA_TAILEDITS_PERIOD_KEY, 1);
        cluster.restartNameNode(1);
        nn1 = cluster.getNameNode(1);
        assertEquals(nn1.getNamesystem().getTransactionsSinceLastLogRoll(), 0L);
    }

    private void restartActive() throws IOException {
        cluster.shutdownNameNode(0);
        cluster.getConfiguration(0).setInt(DFSConfigKeys.DFS_NAMENODE_SAFEMODE_EXTENSION_KEY, 30000);
        cluster.restartNameNode(0);
        nn0 = cluster.getNameNode(0);
    }

    private static void assertSafeMode(NameNode nn, int safe, int total, int numNodes, int nodeThresh) {
        String status = nn.getNamesystem().getSafemode();
        if (total == 0 && nodeThresh == 0) {
            assertTrue("Bad safemode status: '" + status + "'", status.isEmpty() || status.startsWith("Safe mode is ON. The reported blocks 0 " + "has reached the threshold 0.9990 of total blocks 0. The " + "minimum number of live datanodes is not required. In safe " + "mode extension. Safe mode will be turned off automatically " + "in 0 seconds."));
        } else if (safe == total) {
            if (nodeThresh == 0) {
                assertTrue("Bad safemode status: '" + status + "'", status.startsWith("Safe mode is ON. The reported blocks " + safe + " has reached the " + "threshold 0.9990 of total blocks " + total + ". The minimum number of live datanodes is not " + "required. In safe mode extension. Safe mode will be turned " + "off automatically"));
            } else {
                assertTrue("Bad safemode status: '" + status + "'", status.startsWith("Safe mode is ON. The reported blocks " + safe + " has reached " + "the threshold 0.9990 of total blocks " + total + ". The " + "number of live datanodes " + numNodes + " has reached " + "the minimum number " + nodeThresh + ". In safe mode " + "extension. Safe mode will be turned off automatically"));
            }
        } else {
            int additional = (int) (total * 0.9990) - safe;
            assertTrue("Bad safemode status: '" + status + "'", status.startsWith("Safe mode is ON. " + "The reported blocks " + safe + " needs additional " + additional + " blocks"));
        }
    }

    static void banner(String string) {
        LOG.info("\n\n\n\n================================================\n" + string + "\n" + "==================================================\n\n");
    }

    @Test
    public void testBlocksAddedBeforeStandbyRestart_1() throws Exception {
        assertSafeMode(nn1, 3, 3, 3, 0);
    }

    @Test
    public void testBlocksAddedBeforeStandbyRestart_2() throws Exception {
        HATestUtil.waitForStandbyToCatchUp(nn0, nn1);
        assertSafeMode(nn1, 8, 8, 3, 0);
    }

    @Test
    public void testBlocksAddedWhileInSafeMode_1() throws Exception {
        assertSafeMode(nn1, 3, 3, 3, 0);
    }

    @Test
    public void testBlocksAddedWhileInSafeMode_2() throws Exception {
        HATestUtil.waitForStandbyToCatchUp(nn0, nn1);
        assertSafeMode(nn1, 8, 8, 3, 0);
    }

    @Test
    public void testBlocksRemovedBeforeStandbyRestart_1() throws Exception {
        assertSafeMode(nn1, 0, 5, 3, 0);
    }

    @Test
    public void testBlocksRemovedBeforeStandbyRestart_2() throws Exception {
        HATestUtil.waitForStandbyToCatchUp(nn0, nn1);
        assertSafeMode(nn1, 0, 0, 3, 0);
    }

    @Test
    public void testBlocksRemovedWhileInSafeMode_1() throws Exception {
        assertSafeMode(nn1, 10, 10, 3, 0);
    }

    @Test
    public void testBlocksRemovedWhileInSafeMode_2() throws Exception {
        assertSafeMode(nn1, 10, 10, 3, 0);
    }

    @Test
    public void testBlocksRemovedWhileInSafeMode_3() throws Exception {
        HATestUtil.waitForStandbyToCatchUp(nn0, nn1);
        assertSafeMode(nn1, 0, 0, 3, 0);
    }

    @Test
    public void testComplexFailoverIntoSafemode_1() throws Exception {
        assertSafeMode(nn1, 3, 3, 3, 0);
    }

    @Test
    public void testComplexFailoverIntoSafemode_2() throws Exception {
        assertSafeMode(nn1, 5, 5, 3, 0);
    }
}
