package org.apache.hadoop.hdfs.server.namenode.metrics;

import java.util.concurrent.TimeUnit;
import org.apache.hadoop.crypto.key.JavaKeyStoreProvider;
import org.apache.hadoop.fs.CommonConfigurationKeysPublic;
import org.apache.hadoop.fs.FileSystemTestHelper;
import org.apache.hadoop.fs.FileSystemTestWrapper;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.ha.HAServiceProtocol;
import org.apache.hadoop.hdfs.DFSUtil;
import org.apache.hadoop.hdfs.StripedFileTestUtil;
import org.apache.hadoop.hdfs.client.CreateEncryptionZoneFlag;
import org.apache.hadoop.hdfs.client.HdfsAdmin;
import static org.apache.hadoop.fs.CommonConfigurationKeys.HA_HM_RPC_TIMEOUT_DEFAULT;
import static org.apache.hadoop.fs.CommonConfigurationKeys.HA_HM_RPC_TIMEOUT_KEY;
import static org.apache.hadoop.metrics2.source.JvmMetricsInfo.GcTimePercentage;
import static org.apache.hadoop.test.MetricsAsserts.assertCounter;
import static org.apache.hadoop.test.MetricsAsserts.assertCounterGt;
import static org.apache.hadoop.test.MetricsAsserts.assertGauge;
import static org.apache.hadoop.test.MetricsAsserts.assertQuantileGauges;
import static org.apache.hadoop.test.MetricsAsserts.getMetrics;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import org.apache.hadoop.hdfs.server.namenode.NameNodeRpcServer;
import org.apache.hadoop.ipc.metrics.RpcDetailedMetrics;
import org.apache.hadoop.thirdparty.com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Options.Rename;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DFSConfigKeys;
import org.apache.hadoop.hdfs.DFSTestUtil;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.HdfsConfiguration;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.hdfs.MiniDFSNNTopology;
import org.apache.hadoop.hdfs.protocol.ErasureCodingPolicy;
import org.apache.hadoop.hdfs.protocol.HdfsConstants.SafeModeAction;
import org.apache.hadoop.hdfs.protocol.LocatedBlock;
import org.apache.hadoop.hdfs.protocol.LocatedBlocks;
import org.apache.hadoop.hdfs.protocol.LocatedStripedBlock;
import org.apache.hadoop.hdfs.protocol.SystemErasureCodingPolicies;
import org.apache.hadoop.hdfs.server.blockmanagement.BlockManager;
import org.apache.hadoop.hdfs.server.blockmanagement.BlockManagerTestUtil;
import org.apache.hadoop.hdfs.server.blockmanagement.DatanodeDescriptor;
import org.apache.hadoop.hdfs.server.common.Storage;
import org.apache.hadoop.hdfs.server.datanode.DataNode;
import org.apache.hadoop.hdfs.server.datanode.DataNodeTestUtils;
import org.apache.hadoop.hdfs.server.datanode.fsdataset.FsDatasetSpi;
import org.apache.hadoop.hdfs.server.datanode.fsdataset.impl.FsVolumeImpl;
import org.apache.hadoop.hdfs.server.namenode.FSNamesystem;
import org.apache.hadoop.hdfs.server.namenode.MockNameNodeResourceChecker;
import org.apache.hadoop.hdfs.server.namenode.NameNode;
import org.apache.hadoop.hdfs.server.namenode.NameNodeAdapter;
import org.apache.hadoop.hdfs.server.namenode.ha.HATestUtil;
import org.apache.hadoop.hdfs.tools.NNHAServiceTarget;
import org.apache.hadoop.hdfs.util.HostsFileWriter;
import org.apache.hadoop.metrics2.MetricsRecordBuilder;
import org.apache.hadoop.metrics2.MetricsSource;
import org.apache.hadoop.metrics2.lib.DefaultMetricsSystem;
import org.apache.hadoop.test.GenericTestUtils;
import org.apache.hadoop.test.MetricsAsserts;
import org.slf4j.event.Level;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestNameNodeMetrics_Purified {

    private static final Configuration CONF = new HdfsConfiguration();

    private static final int DFS_REDUNDANCY_INTERVAL = 1;

    private static final Path TEST_ROOT_DIR_PATH = new Path("/testNameNodeMetrics");

    private static final String NN_METRICS = "NameNodeActivity";

    private static final String NS_METRICS = "FSNamesystem";

    private static final String JVM_METRICS = "JvmMetrics";

    private static final int BLOCK_SIZE = 1024 * 1024;

    private static final ErasureCodingPolicy EC_POLICY = SystemErasureCodingPolicies.getByID(SystemErasureCodingPolicies.XOR_2_1_POLICY_ID);

    public static final Logger LOG = LoggerFactory.getLogger(TestNameNodeMetrics.class);

    private static final int DATANODE_COUNT = EC_POLICY.getNumDataUnits() + EC_POLICY.getNumParityUnits() + 1;

    private static final int WAIT_GAUGE_VALUE_RETRIES = 20;

    private static final int PERCENTILES_INTERVAL = 1;

    static {
        CONF.setLong(DFSConfigKeys.DFS_BLOCK_SIZE_KEY, BLOCK_SIZE);
        CONF.setInt(DFSConfigKeys.DFS_BYTES_PER_CHECKSUM_KEY, 1);
        CONF.setLong(DFSConfigKeys.DFS_HEARTBEAT_INTERVAL_KEY, DFS_REDUNDANCY_INTERVAL);
        CONF.setTimeDuration(MiniDFSCluster.DFS_NAMENODE_DECOMMISSION_INTERVAL_TESTING_KEY, 999, TimeUnit.DAYS);
        CONF.setTimeDuration(DFSConfigKeys.DFS_DATANODE_DISK_CHECK_MIN_GAP_KEY, 10, TimeUnit.MILLISECONDS);
        CONF.setInt(DFSConfigKeys.DFS_DATANODE_FAILED_VOLUMES_TOLERATED_KEY, 1);
        CONF.setInt(DFSConfigKeys.DFS_NAMENODE_REDUNDANCY_INTERVAL_SECONDS_KEY, DFS_REDUNDANCY_INTERVAL);
        CONF.set(DFSConfigKeys.DFS_METRICS_PERCENTILES_INTERVALS_KEY, "" + PERCENTILES_INTERVAL);
        CONF.setBoolean(DFSConfigKeys.DFS_NAMENODE_AVOID_STALE_DATANODE_FOR_READ_KEY, true);
        GenericTestUtils.setLogLevel(LoggerFactory.getLogger(MetricsAsserts.class), Level.DEBUG);
    }

    private MiniDFSCluster cluster;

    private DistributedFileSystem fs;

    private final Random rand = new Random();

    private FSNamesystem namesystem;

    private HostsFileWriter hostsFileWriter;

    private BlockManager bm;

    private Path ecDir;

    private static Path getTestPath(String fileName) {
        return new Path(TEST_ROOT_DIR_PATH, fileName);
    }

    @Before
    public void setUp() throws Exception {
        hostsFileWriter = new HostsFileWriter();
        hostsFileWriter.initialize(CONF, "temp/decommission");
        cluster = new MiniDFSCluster.Builder(CONF).numDataNodes(DATANODE_COUNT).build();
        cluster.waitActive();
        namesystem = cluster.getNamesystem();
        bm = namesystem.getBlockManager();
        fs = cluster.getFileSystem();
        fs.enableErasureCodingPolicy(EC_POLICY.getName());
        ecDir = getTestPath("/ec");
        fs.mkdirs(ecDir);
        fs.setErasureCodingPolicy(ecDir, EC_POLICY.getName());
    }

    @After
    public void tearDown() throws Exception {
        MetricsSource source = DefaultMetricsSystem.instance().getSource("UgiMetrics");
        if (source != null) {
            MetricsRecordBuilder rb = getMetrics(source);
            assertQuantileGauges("GetGroups1s", rb);
        }
        if (hostsFileWriter != null) {
            hostsFileWriter.cleanup();
            hostsFileWriter = null;
        }
        if (cluster != null) {
            cluster.shutdown();
            cluster = null;
        }
    }

    private void createFile(Path file, long fileLen, short replicas) throws IOException {
        DFSTestUtil.createFile(fs, file, fileLen, replicas, rand.nextLong());
    }

    private void readFile(FileSystem fileSys, Path name) throws IOException {
        DataInputStream stm = fileSys.open(name);
        byte[] buffer = new byte[4];
        stm.read(buffer, 0, 4);
        stm.close();
    }

    private void verifyZeroMetrics() throws Exception {
        BlockManagerTestUtil.updateState(bm);
        MetricsRecordBuilder rb = waitForDnMetricValue(NS_METRICS, "CorruptBlocks", 0L, 500);
        assertGauge("UnderReplicatedBlocks", 0L, rb);
        assertGauge("LowRedundancyBlocks", 0L, rb);
        assertGauge("PendingReplicationBlocks", 0L, rb);
        assertGauge("PendingReconstructionBlocks", 0L, rb);
        assertGauge("LowRedundancyReplicatedBlocks", 0L, rb);
        assertGauge("CorruptReplicatedBlocks", 0L, rb);
        assertGauge("HighestPriorityLowRedundancyReplicatedBlocks", 0L, rb);
        assertGauge("LowRedundancyECBlockGroups", 0L, rb);
        assertGauge("CorruptECBlockGroups", 0L, rb);
        assertGauge("HighestPriorityLowRedundancyECBlocks", 0L, rb);
    }

    private void verifyAggregatedMetricsTally() throws Exception {
        BlockManagerTestUtil.updateState(bm);
        assertEquals("Under replicated metrics not matching!", namesystem.getLowRedundancyBlocks(), namesystem.getUnderReplicatedBlocks());
        assertEquals("Low redundancy metrics not matching!", namesystem.getLowRedundancyBlocks(), namesystem.getLowRedundancyReplicatedBlocks() + namesystem.getLowRedundancyECBlockGroups());
        assertEquals("Corrupt blocks metrics not matching!", namesystem.getCorruptReplicaBlocks(), namesystem.getCorruptReplicatedBlocks() + namesystem.getCorruptECBlockGroups());
        assertEquals("Missing blocks metrics not matching!", namesystem.getMissingBlocksCount(), namesystem.getMissingReplicatedBlocks() + namesystem.getMissingECBlockGroups());
        assertEquals("Missing blocks with replication factor one not matching!", namesystem.getMissingReplOneBlocksCount(), namesystem.getMissingReplicationOneBlocks());
        assertEquals("Bytes in future blocks metrics not matching!", namesystem.getBytesInFuture(), namesystem.getBytesInFutureReplicatedBlocks() + namesystem.getBytesInFutureECBlockGroups());
        assertEquals("Pending deletion blocks metrics not matching!", namesystem.getPendingDeletionBlocks(), namesystem.getPendingDeletionReplicatedBlocks() + namesystem.getPendingDeletionECBlocks());
    }

    private void waitForDeletion() throws InterruptedException {
        Thread.sleep(DFS_REDUNDANCY_INTERVAL * DATANODE_COUNT * 1000);
    }

    private MetricsRecordBuilder waitForDnMetricValue(String source, String name, long expected) throws Exception {
        waitForDeletion();
        return waitForDnMetricValue(source, name, expected, DFS_REDUNDANCY_INTERVAL * 500);
    }

    private MetricsRecordBuilder waitForDnMetricValue(String source, String name, long expected, long sleepInterval) throws Exception {
        MetricsRecordBuilder rb;
        long gauge;
        int retries = DATANODE_COUNT * WAIT_GAUGE_VALUE_RETRIES;
        rb = getMetrics(source);
        gauge = MetricsAsserts.getLongGauge(name, rb);
        while (gauge != expected && (--retries > 0)) {
            Thread.sleep(sleepInterval);
            BlockManagerTestUtil.updateState(bm);
            rb = getMetrics(source);
            gauge = MetricsAsserts.getLongGauge(name, rb);
        }
        assertGauge(name, expected, rb);
        return rb;
    }

    @Test
    public void testVolumeFailures_1() throws Exception {
        assertGauge("VolumeFailuresTotal", 0, getMetrics(NS_METRICS));
    }

    @Test
    public void testVolumeFailures_2() throws Exception {
        assertGauge("EstimatedCapacityLostTotal", 0L, getMetrics(NS_METRICS));
    }

    @Test
    public void testVolumeFailures_3() throws Exception {
        assertGauge("VolumeFailuresTotal", 1, getMetrics(NS_METRICS));
    }

    @Test
    public void testVolumeFailures_4() throws Exception {
        DataNode dn = cluster.getDataNodes().get(0);
        FsDatasetSpi.FsVolumeReferences volumeReferences = DataNodeTestUtils.getFSDataset(dn).getFsVolumeReferences();
        FsVolumeImpl fsVolume = (FsVolumeImpl) volumeReferences.get(0);
        File dataDir = new File(fsVolume.getBaseURI());
        long capacity = fsVolume.getCapacity();
        assertGauge("EstimatedCapacityLostTotal", capacity, getMetrics(NS_METRICS));
    }

    @Test
    public void testFileAdd_1() throws Exception {
        int blockCapacity = namesystem.getBlockCapacity();
        assertGauge("BlockCapacity", blockCapacity, getMetrics(NS_METRICS));
    }

    @Test
    public void testFileAdd_2_testMerged_2() throws Exception {
        final long blockCount = 32;
        final Path normalFile = getTestPath("testFileAdd");
        createFile(normalFile, blockCount * BLOCK_SIZE, (short) 3);
        final Path ecFile = new Path(ecDir, "ecFile.log");
        DFSTestUtil.createStripedFile(cluster, ecFile, null, (int) blockCount, 1, false, EC_POLICY);
        MetricsRecordBuilder rb = getMetrics(NN_METRICS);
        assertCounter("CreateFileOps", 2L, rb);
        assertCounter("FilesCreated", (long) (normalFile.depth() + ecFile.depth()), rb);
        long filesTotal = normalFile.depth() + ecFile.depth() + 1;
        rb = getMetrics(NS_METRICS);
        assertGauge("FilesTotal", filesTotal, rb);
        assertGauge("BlocksTotal", blockCount * 2, rb);
        filesTotal--;
        rb = waitForDnMetricValue(NS_METRICS, "FilesTotal", filesTotal);
        assertGauge("BlocksTotal", blockCount, rb);
        assertGauge("PendingDeletionBlocks", 0L, rb);
        assertGauge("BlocksTotal", 0L, rb);
        rb = getMetrics(NN_METRICS);
        assertCounter("DeleteFileOps", 2L, rb);
        assertCounter("FilesDeleted", 2L, rb);
    }

    @Test
    public void testExcessBlocks_1_testMerged_1() throws Exception {
        MetricsRecordBuilder rb = getMetrics(NS_METRICS);
        assertGauge("ExcessBlocks", 1L, rb);
        rb = getMetrics(NS_METRICS);
        assertGauge("ExcessBlocks", 0L, rb);
    }

    @Test
    public void testExcessBlocks_3() throws Exception {
        assertEquals(0L, bm.getExcessBlocksCount());
    }

    @Test
    public void testGetBlockLocationMetric_1() throws Exception {
        assertCounter("GetBlockLocations", 0L, getMetrics(NN_METRICS));
    }

    @Test
    public void testGetBlockLocationMetric_2() throws Exception {
        assertCounter("GetBlockLocations", 0L, getMetrics(NN_METRICS));
    }

    @Test
    public void testGetBlockLocationMetric_3() throws Exception {
        assertCounter("GetBlockLocations", 1L, getMetrics(NN_METRICS));
    }

    @Test
    public void testGetBlockLocationMetric_4() throws Exception {
        assertCounter("GetBlockLocations", 3L, getMetrics(NN_METRICS));
    }
}
