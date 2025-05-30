package org.apache.hadoop.hdfs.server.datanode;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.apache.hadoop.hdfs.server.protocol.DatanodeStorage.State.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DFSClient;
import org.apache.hadoop.hdfs.DFSTestUtil;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.HdfsConfiguration;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.hdfs.protocol.Block;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;
import org.apache.hadoop.hdfs.protocol.ExtendedBlock;
import org.apache.hadoop.hdfs.protocol.LocatedBlock;
import org.apache.hadoop.hdfs.protocol.LocatedBlocks;
import org.apache.hadoop.hdfs.server.blockmanagement.BlockInfo;
import org.apache.hadoop.hdfs.server.blockmanagement.BlockManager;
import org.apache.hadoop.hdfs.server.blockmanagement.BlockManagerTestUtil;
import org.apache.hadoop.hdfs.server.blockmanagement.DatanodeManager;
import org.apache.hadoop.hdfs.server.blockmanagement.NumberReplicas;
import org.apache.hadoop.hdfs.server.protocol.DatanodeStorage;
import org.apache.hadoop.hdfs.server.protocol.StorageReport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.apache.hadoop.thirdparty.com.google.common.collect.Iterables;

public class TestReadOnlySharedStorage_Purified {

    public static final Logger LOG = LoggerFactory.getLogger(TestReadOnlySharedStorage.class);

    private static final short NUM_DATANODES = 3;

    private static final int RO_NODE_INDEX = 0;

    private static final int BLOCK_SIZE = 1024;

    private static final long seed = 0x1BADF00DL;

    private static final Path PATH = new Path("/" + TestReadOnlySharedStorage.class.getName() + ".dat");

    private static final int RETRIES = 10;

    private Configuration conf;

    private MiniDFSCluster cluster;

    private DistributedFileSystem fs;

    private DFSClient client;

    private BlockManager blockManager;

    private DatanodeManager datanodeManager;

    private DatanodeInfo normalDataNode;

    private DatanodeInfo readOnlyDataNode;

    private Block block;

    private BlockInfo storedBlock;

    private ExtendedBlock extendedBlock;

    @Before
    public void setup() throws IOException, InterruptedException {
        conf = new HdfsConfiguration();
        SimulatedFSDataset.setFactory(conf);
        Configuration[] overlays = new Configuration[NUM_DATANODES];
        for (int i = 0; i < overlays.length; i++) {
            overlays[i] = new Configuration();
            if (i == RO_NODE_INDEX) {
                overlays[i].setEnum(SimulatedFSDataset.CONFIG_PROPERTY_STATE, i == RO_NODE_INDEX ? READ_ONLY_SHARED : NORMAL);
            }
        }
        cluster = new MiniDFSCluster.Builder(conf).numDataNodes(NUM_DATANODES).dataNodeConfOverlays(overlays).build();
        fs = cluster.getFileSystem();
        blockManager = cluster.getNameNode().getNamesystem().getBlockManager();
        datanodeManager = blockManager.getDatanodeManager();
        client = new DFSClient(new InetSocketAddress("localhost", cluster.getNameNodePort()), cluster.getConfiguration(0));
        for (int i = 0; i < NUM_DATANODES; i++) {
            DataNode dataNode = cluster.getDataNodes().get(i);
            validateStorageState(BlockManagerTestUtil.getStorageReportsForDatanode(datanodeManager.getDatanode(dataNode.getDatanodeId())), i == RO_NODE_INDEX ? READ_ONLY_SHARED : NORMAL);
        }
        DFSTestUtil.createFile(fs, PATH, BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE, (short) 1, seed);
        LocatedBlock locatedBlock = getLocatedBlock();
        extendedBlock = locatedBlock.getBlock();
        block = extendedBlock.getLocalBlock();
        storedBlock = blockManager.getStoredBlock(block);
        assertThat(locatedBlock.getLocations().length, is(1));
        normalDataNode = locatedBlock.getLocations()[0];
        readOnlyDataNode = datanodeManager.getDatanode(cluster.getDataNodes().get(RO_NODE_INDEX).getDatanodeId());
        assertThat(normalDataNode, is(not(readOnlyDataNode)));
        validateNumberReplicas(1);
        cluster.injectBlocks(0, RO_NODE_INDEX, Collections.singleton(block));
        waitForLocations(2);
    }

    @After
    public void tearDown() throws IOException {
        fs.delete(PATH, false);
        if (cluster != null) {
            fs.close();
            cluster.shutdown();
            cluster = null;
        }
    }

    private void waitForLocations(int locations) throws IOException, InterruptedException {
        for (int tries = 0; tries < RETRIES; ) try {
            LocatedBlock locatedBlock = getLocatedBlock();
            assertThat(locatedBlock.getLocations().length, is(locations));
            break;
        } catch (AssertionError e) {
            if (++tries < RETRIES) {
                Thread.sleep(1000);
            } else {
                throw e;
            }
        }
    }

    private LocatedBlock getLocatedBlock() throws IOException {
        LocatedBlocks locatedBlocks = client.getLocatedBlocks(PATH.toString(), 0, BLOCK_SIZE);
        assertThat(locatedBlocks.getLocatedBlocks().size(), is(1));
        return Iterables.getOnlyElement(locatedBlocks.getLocatedBlocks());
    }

    private void validateStorageState(StorageReport[] storageReports, DatanodeStorage.State state) {
        for (StorageReport storageReport : storageReports) {
            DatanodeStorage storage = storageReport.getStorage();
            assertThat(storage.getState(), is(state));
        }
    }

    private void validateNumberReplicas(int expectedReplicas) throws IOException {
        NumberReplicas numberReplicas = blockManager.countNodes(storedBlock);
        assertThat(numberReplicas.liveReplicas(), is(expectedReplicas));
        assertThat(numberReplicas.excessReplicas(), is(0));
        assertThat(numberReplicas.corruptReplicas(), is(0));
        assertThat(numberReplicas.decommissionedAndDecommissioning(), is(0));
        assertThat(numberReplicas.replicasOnStaleNodes(), is(0));
        BlockManagerTestUtil.updateState(blockManager);
        assertThat(blockManager.getLowRedundancyBlocksCount(), is(0L));
        assertThat(blockManager.getExcessBlocksCount(), is(0L));
    }

    @Test
    public void testNormalReplicaOffline_1_testMerged_1() throws Exception {
        NumberReplicas numberReplicas = blockManager.countNodes(storedBlock);
        assertThat(numberReplicas.liveReplicas(), is(0));
        BlockManagerTestUtil.updateState(blockManager);
        assertThat(blockManager.getLowRedundancyBlocksCount(), is(1L));
    }

    @Test
    public void testNormalReplicaOffline_3() throws Exception {
        assertThat(getLocatedBlock().getLocations().length, is(2));
    }
}
