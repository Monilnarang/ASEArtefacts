package org.apache.hadoop.hdfs.server.datanode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DFSConfigKeys;
import org.apache.hadoop.hdfs.DFSTestUtil;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.hdfs.StripedFileTestUtil;
import org.apache.hadoop.hdfs.protocol.DatanodeID;
import org.apache.hadoop.hdfs.protocol.ErasureCodingPolicy;
import org.apache.hadoop.hdfs.protocol.LocatedBlocks;
import org.apache.hadoop.hdfs.protocol.LocatedStripedBlock;
import org.apache.hadoop.hdfs.server.blockmanagement.BlockManager;
import org.apache.hadoop.hdfs.server.blockmanagement.BlockManagerTestUtil;
import org.apache.hadoop.hdfs.server.blockmanagement.DatanodeDescriptor;
import org.apache.hadoop.hdfs.server.namenode.NameNodeAdapter;
import org.apache.hadoop.metrics2.MetricsRecordBuilder;
import static org.apache.hadoop.test.MetricsAsserts.getLongCounter;
import static org.apache.hadoop.test.MetricsAsserts.getLongCounterWithoutCheck;
import static org.apache.hadoop.test.MetricsAsserts.getMetrics;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;

public class TestDataNodeErasureCodingMetrics_Purified {

    public static final Logger LOG = LoggerFactory.getLogger(TestDataNodeErasureCodingMetrics.class);

    private final ErasureCodingPolicy ecPolicy = StripedFileTestUtil.getDefaultECPolicy();

    private final int dataBlocks = ecPolicy.getNumDataUnits();

    private final int parityBlocks = ecPolicy.getNumParityUnits();

    private final int cellSize = ecPolicy.getCellSize();

    private final int blockSize = cellSize * 2;

    private final int groupSize = dataBlocks + parityBlocks;

    private final int blockGroupSize = blockSize * dataBlocks;

    private final int numDNs = groupSize + 1;

    private MiniDFSCluster cluster;

    private Configuration conf;

    private DistributedFileSystem fs;

    @Before
    public void setup() throws IOException {
        conf = new Configuration();
        conf.setLong(DFSConfigKeys.DFS_BLOCK_SIZE_KEY, blockSize);
        conf.setInt(DFSConfigKeys.DFS_NAMENODE_REDUNDANCY_INTERVAL_SECONDS_KEY, 1);
        cluster = new MiniDFSCluster.Builder(conf).numDataNodes(numDNs).build();
        cluster.waitActive();
        cluster.getFileSystem().getClient().setErasureCodingPolicy("/", StripedFileTestUtil.getDefaultECPolicy().getName());
        fs = cluster.getFileSystem();
        fs.enableErasureCodingPolicy(StripedFileTestUtil.getDefaultECPolicy().getName());
    }

    @After
    public void tearDown() {
        if (cluster != null) {
            cluster.shutdown();
        }
    }

    private long getLongMetric(String metricName) {
        long metricValue = 0;
        for (DataNode dn : cluster.getDataNodes()) {
            MetricsRecordBuilder rb = getMetrics(dn.getMetrics().name());
            metricValue += getLongCounter(metricName, rb);
        }
        return metricValue;
    }

    private long getLongMetricWithoutCheck(String metricName) {
        long metricValue = 0;
        for (DataNode dn : cluster.getDataNodes()) {
            MetricsRecordBuilder rb = getMetrics(dn.getMetrics().name());
            metricValue += getLongCounterWithoutCheck(metricName, rb);
        }
        return metricValue;
    }

    private void doTest(String fileName, int fileLen, int deadNodeIndex) throws Exception {
        assertTrue(fileLen > 0);
        assertTrue(deadNodeIndex >= 0 && deadNodeIndex < numDNs);
        Path file = new Path(fileName);
        final byte[] data = StripedFileTestUtil.generateBytes(fileLen);
        DFSTestUtil.writeFile(fs, file, data);
        StripedFileTestUtil.waitBlockGroupsReported(fs, fileName);
        final LocatedBlocks locatedBlocks = StripedFileTestUtil.getLocatedBlocks(file, fs);
        final LocatedStripedBlock lastBlock = (LocatedStripedBlock) locatedBlocks.getLastLocatedBlock();
        assertTrue(lastBlock.getLocations().length > deadNodeIndex);
        final DataNode toCorruptDn = cluster.getDataNode(lastBlock.getLocations()[deadNodeIndex].getIpcPort());
        LOG.info("Datanode to be corrupted: " + toCorruptDn);
        assertNotNull("Failed to find a datanode to be corrupted", toCorruptDn);
        toCorruptDn.shutdown();
        setDataNodeDead(toCorruptDn.getDatanodeId());
        DFSTestUtil.waitForDatanodeState(cluster, toCorruptDn.getDatanodeUuid(), false, 10000);
        final int workCount = getComputedDatanodeWork();
        assertTrue("Wrongly computed block reconstruction work", workCount > 0);
        cluster.triggerHeartbeats();
        int totalBlocks = (fileLen / blockGroupSize) * groupSize;
        final int remainder = fileLen % blockGroupSize;
        totalBlocks += (remainder == 0) ? 0 : (remainder % blockSize == 0) ? remainder / blockSize + parityBlocks : remainder / blockSize + 1 + parityBlocks;
        StripedFileTestUtil.waitForAllReconstructionFinished(file, fs, totalBlocks);
    }

    private int getComputedDatanodeWork() throws IOException, InterruptedException {
        final BlockManager bm = cluster.getNamesystem().getBlockManager();
        int workCount = 0;
        int retries = 20;
        while (retries > 0) {
            workCount = BlockManagerTestUtil.getComputedDatanodeWork(bm);
            if (workCount > 0) {
                break;
            }
            retries--;
            Thread.sleep(500);
        }
        LOG.info("Computed datanode work: " + workCount + ", retries: " + retries);
        return workCount;
    }

    private void setDataNodeDead(DatanodeID dnID) throws IOException {
        DatanodeDescriptor dnd = NameNodeAdapter.getDatanode(cluster.getNamesystem(), dnID);
        DFSTestUtil.setDatanodeDead(dnd);
        BlockManagerTestUtil.checkHeartbeat(cluster.getNamesystem().getBlockManager());
    }

    @Test(timeout = 120000)
    public void testFullBlock_1() throws Exception {
        Assert.assertEquals(0, getLongMetric("EcReconstructionReadTimeMillis"));
    }

    @Test(timeout = 120000)
    public void testFullBlock_2() throws Exception {
        Assert.assertEquals(0, getLongMetric("EcReconstructionDecodingTimeMillis"));
    }

    @Test(timeout = 120000)
    public void testFullBlock_3() throws Exception {
        Assert.assertEquals(0, getLongMetric("EcReconstructionWriteTimeMillis"));
    }

    @Test(timeout = 120000)
    public void testFullBlock_4() throws Exception {
        Assert.assertEquals("EcReconstructionTasks should be ", 1, getLongMetric("EcReconstructionTasks"));
    }

    @Test(timeout = 120000)
    public void testFullBlock_5() throws Exception {
        Assert.assertEquals("EcFailedReconstructionTasks should be ", 0, getLongMetric("EcFailedReconstructionTasks"));
    }

    @Test(timeout = 120000)
    public void testFullBlock_6() throws Exception {
        Assert.assertTrue(getLongMetric("EcDecodingTimeNanos") > 0);
    }

    @Test(timeout = 120000)
    public void testFullBlock_7() throws Exception {
        doTest("/testEcMetrics", blockGroupSize, 0);
        Assert.assertEquals("EcReconstructionBytesRead should be ", blockGroupSize, getLongMetric("EcReconstructionBytesRead"));
    }

    @Test(timeout = 120000)
    public void testFullBlock_8() throws Exception {
        Assert.assertEquals("EcReconstructionBytesWritten should be ", blockSize, getLongMetric("EcReconstructionBytesWritten"));
    }

    @Test(timeout = 120000)
    public void testFullBlock_9() throws Exception {
        Assert.assertEquals("EcReconstructionRemoteBytesRead should be ", 0, getLongMetricWithoutCheck("EcReconstructionRemoteBytesRead"));
    }

    @Test(timeout = 120000)
    public void testFullBlock_10() throws Exception {
        Assert.assertTrue(getLongMetric("EcReconstructionReadTimeMillis") > 0);
    }

    @Test(timeout = 120000)
    public void testFullBlock_11() throws Exception {
        Assert.assertTrue(getLongMetric("EcReconstructionDecodingTimeMillis") > 0);
    }

    @Test(timeout = 120000)
    public void testFullBlock_12() throws Exception {
        Assert.assertTrue(getLongMetric("EcReconstructionWriteTimeMillis") > 0);
    }

    @Test(timeout = 120000)
    public void testReconstructionBytesPartialGroup1_1_testMerged_1() throws Exception {
        final int fileLen = blockSize / 10;
        doTest("/testEcBytes", fileLen, 0);
        Assert.assertEquals("EcReconstructionBytesRead should be ", fileLen, getLongMetric("EcReconstructionBytesRead"));
        Assert.assertEquals("EcReconstructionBytesWritten should be ", fileLen, getLongMetric("EcReconstructionBytesWritten"));
    }

    @Test(timeout = 120000)
    public void testReconstructionBytesPartialGroup1_3() throws Exception {
        Assert.assertEquals("EcReconstructionRemoteBytesRead should be ", 0, getLongMetricWithoutCheck("EcReconstructionRemoteBytesRead"));
    }

    @Test(timeout = 120000)
    public void testReconstructionBytesPartialGroup2_1() throws Exception {
        Assert.assertEquals("ecReconstructionBytesRead should be ", cellSize * dataBlocks + cellSize + cellSize / 10, getLongMetric("EcReconstructionBytesRead"));
    }

    @Test(timeout = 120000)
    public void testReconstructionBytesPartialGroup2_2() throws Exception {
        Assert.assertEquals("EcReconstructionBytesWritten should be ", blockSize, getLongMetric("EcReconstructionBytesWritten"));
    }

    @Test(timeout = 120000)
    public void testReconstructionBytesPartialGroup2_3() throws Exception {
        Assert.assertEquals("EcReconstructionRemoteBytesRead should be ", 0, getLongMetricWithoutCheck("EcReconstructionRemoteBytesRead"));
    }

    @Test(timeout = 120000)
    public void testReconstructionBytesPartialGroup3_1() throws Exception {
        Assert.assertEquals("ecReconstructionBytesRead should be ", cellSize * dataBlocks + (cellSize / 10) * 2, getLongMetric("EcReconstructionBytesRead"));
    }

    @Test(timeout = 120000)
    public void testReconstructionBytesPartialGroup3_2() throws Exception {
        Assert.assertEquals("ecReconstructionBytesWritten should be ", cellSize + cellSize / 10, getLongMetric("EcReconstructionBytesWritten"));
    }

    @Test(timeout = 120000)
    public void testReconstructionBytesPartialGroup3_3() throws Exception {
        Assert.assertEquals("EcReconstructionRemoteBytesRead should be ", 0, getLongMetricWithoutCheck("EcReconstructionRemoteBytesRead"));
    }
}
