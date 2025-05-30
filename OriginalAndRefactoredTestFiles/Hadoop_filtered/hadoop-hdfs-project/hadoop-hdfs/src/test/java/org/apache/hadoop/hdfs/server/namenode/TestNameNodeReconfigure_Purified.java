package org.apache.hadoop.hdfs.server.namenode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.hadoop.hdfs.server.blockmanagement.DatanodeAdminBackoffMonitor;
import org.apache.hadoop.hdfs.server.blockmanagement.DatanodeAdminMonitorInterface;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_DATANODE_MAX_NODES_TO_REPORT_KEY;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_IMAGE_PARALLEL_LOAD_KEY;
import static org.junit.Assert.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.ReconfigurationException;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.hdfs.protocol.HdfsConstants.StoragePolicySatisfierMode;
import org.apache.hadoop.hdfs.protocol.BlockType;
import org.apache.hadoop.hdfs.DFSConfigKeys;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.HdfsConfiguration;
import org.apache.hadoop.hdfs.server.blockmanagement.DatanodeManager;
import org.apache.hadoop.hdfs.server.blockmanagement.BlockManager;
import org.apache.hadoop.hdfs.server.blockmanagement.SlowPeerTracker;
import org.apache.hadoop.hdfs.server.namenode.sps.StoragePolicySatisfyManager;
import org.apache.hadoop.hdfs.server.protocol.OutlierMetrics;
import org.apache.hadoop.ipc.RemoteException;
import org.apache.hadoop.test.GenericTestUtils;
import static org.apache.hadoop.fs.CommonConfigurationKeysPublic.HADOOP_CALLER_CONTEXT_ENABLED_KEY;
import static org.apache.hadoop.fs.CommonConfigurationKeysPublic.HADOOP_CALLER_CONTEXT_ENABLED_DEFAULT;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_DATANODE_PEER_STATS_ENABLED_KEY;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_HEARTBEAT_INTERVAL_KEY;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_HEARTBEAT_INTERVAL_DEFAULT;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_NAMENODE_HEARTBEAT_RECHECK_INTERVAL_KEY;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_NAMENODE_HEARTBEAT_RECHECK_INTERVAL_DEFAULT;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_STORAGE_POLICY_SATISFIER_MODE_KEY;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_STORAGE_POLICY_SATISFIER_MODE_DEFAULT;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_BLOCK_INVALIDATE_LIMIT_KEY;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_NAMENODE_AVOID_SLOW_DATANODE_FOR_READ_KEY;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_NAMENODE_BLOCKPLACEMENTPOLICY_EXCLUDE_SLOW_NODES_ENABLED_KEY;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_NAMENODE_MAX_SLOWPEER_COLLECT_NODES_KEY;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_NAMENODE_DECOMMISSION_BACKOFF_MONITOR_PENDING_LIMIT;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_NAMENODE_DECOMMISSION_BACKOFF_MONITOR_PENDING_BLOCKS_PER_LOCK;
import static org.apache.hadoop.fs.CommonConfigurationKeys.IPC_BACKOFF_ENABLE_DEFAULT;

public class TestNameNodeReconfigure_Purified {

    public static final Logger LOG = LoggerFactory.getLogger(TestNameNodeReconfigure.class);

    private MiniDFSCluster cluster;

    private final int customizedBlockInvalidateLimit = 500;

    @Before
    public void setUp() throws IOException {
        Configuration conf = new HdfsConfiguration();
        conf.setInt(DFS_BLOCK_INVALIDATE_LIMIT_KEY, customizedBlockInvalidateLimit);
        cluster = new MiniDFSCluster.Builder(conf).build();
        cluster.waitActive();
    }

    void verifyReconfigureCallerContextEnabled(final NameNode nameNode, final FSNamesystem nameSystem, boolean expected) {
        assertEquals(HADOOP_CALLER_CONTEXT_ENABLED_KEY + " has wrong value", expected, nameNode.getNamesystem().getCallerContextEnabled());
        assertEquals(HADOOP_CALLER_CONTEXT_ENABLED_KEY + " has wrong value", expected, nameNode.getConf().getBoolean(HADOOP_CALLER_CONTEXT_ENABLED_KEY, HADOOP_CALLER_CONTEXT_ENABLED_DEFAULT));
    }

    void verifyReconfigureIPCBackoff(final NameNode nameNode, final NameNodeRpcServer nnrs, String property, boolean expected) {
        assertEquals(property + " has wrong value", expected, nnrs.getClientRpcServer().isClientBackoffEnabled());
        assertEquals(property + " has wrong value", expected, nameNode.getConf().getBoolean(property, IPC_BACKOFF_ENABLE_DEFAULT));
    }

    void verifySPSEnabled(final NameNode nameNode, String property, StoragePolicySatisfierMode expected, boolean isSatisfierRunning) {
        StoragePolicySatisfyManager spsMgr = nameNode.getNamesystem().getBlockManager().getSPSManager();
        boolean isSPSRunning = spsMgr != null ? spsMgr.isSatisfierRunning() : false;
        assertEquals(property + " has wrong value", isSatisfierRunning, isSPSRunning);
        String actual = nameNode.getConf().get(property, DFS_STORAGE_POLICY_SATISFIER_MODE_DEFAULT);
        assertEquals(property + " has wrong value", expected, StoragePolicySatisfierMode.fromString(actual));
    }

    private List<Boolean> validatePeerReport(String jsonReport) {
        List<Boolean> containReport = new ArrayList<>();
        containReport.add(jsonReport.contains("node1"));
        containReport.add(jsonReport.contains("node2"));
        containReport.add(jsonReport.contains("node3"));
        containReport.add(jsonReport.contains("node4"));
        containReport.add(jsonReport.contains("node5"));
        containReport.add(jsonReport.contains("node6"));
        return containReport;
    }

    @After
    public void shutDown() throws IOException {
        if (cluster != null) {
            cluster.shutdown();
        }
    }

    @Test
    public void testEnableParallelLoadAfterReconfigured_1() throws ReconfigurationException {
        assertEquals(false, FSImageFormatProtobuf.getEnableParallelLoad());
    }

    @Test
    public void testEnableParallelLoadAfterReconfigured_2() throws ReconfigurationException {
        assertEquals(true, FSImageFormatProtobuf.getEnableParallelLoad());
    }
}
