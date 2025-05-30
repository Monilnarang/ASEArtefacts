package org.apache.hadoop.hdfs;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.hadoop.fs.CommonConfigurationKeys;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.client.HdfsDataInputStream;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo.AdminStates;
import org.apache.hadoop.hdfs.protocol.HdfsConstants.DatanodeReportType;
import org.apache.hadoop.hdfs.protocol.LocatedBlock;
import org.apache.hadoop.hdfs.protocol.LocatedBlocks;
import org.apache.hadoop.hdfs.server.blockmanagement.BlockManager;
import org.apache.hadoop.hdfs.server.blockmanagement.DatanodeStorageInfo;
import org.apache.hadoop.hdfs.server.datanode.DataNode;
import org.apache.hadoop.hdfs.server.namenode.FSNamesystem;
import org.apache.hadoop.hdfs.server.namenode.NameNodeAdapter;
import org.apache.hadoop.hdfs.tools.DFSAdmin;
import org.apache.hadoop.test.GenericTestUtils;
import org.apache.hadoop.util.Lists;
import org.apache.hadoop.util.Time;
import org.apache.hadoop.util.ToolRunner;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.function.Supplier;

public class TestMaintenanceState_Purified extends AdminStatesBaseTest {

    public static final Logger LOG = LoggerFactory.getLogger(TestMaintenanceState.class);

    static private final long EXPIRATION_IN_MS = 50;

    private int minMaintenanceR = DFSConfigKeys.DFS_NAMENODE_MAINTENANCE_REPLICATION_MIN_DEFAULT;

    public TestMaintenanceState() {
        setUseCombinedHostFileManager();
    }

    void setMinMaintenanceR(int minMaintenanceR) {
        this.minMaintenanceR = minMaintenanceR;
        getConf().setInt(DFSConfigKeys.DFS_NAMENODE_MAINTENANCE_REPLICATION_MIN_KEY, minMaintenanceR);
    }

    static String getFirstBlockFirstReplicaUuid(FileSystem fileSys, Path name) throws IOException {
        DatanodeInfo[] nodes = getFirstBlockReplicasDatanodeInfos(fileSys, name);
        if (nodes != null && nodes.length != 0) {
            return nodes[0].getDatanodeUuid();
        } else {
            return null;
        }
    }

    static String checkFile(FSNamesystem ns, FileSystem fileSys, Path name, int repl, DatanodeInfo expectedExcludedNode, DatanodeInfo expectedMaintenanceNode) throws IOException {
        assertTrue("Not HDFS:" + fileSys.getUri(), fileSys instanceof DistributedFileSystem);
        HdfsDataInputStream dis = (HdfsDataInputStream) fileSys.open(name);
        BlockManager bm = ns.getBlockManager();
        Collection<LocatedBlock> dinfo = dis.getAllBlocks();
        String output;
        for (LocatedBlock blk : dinfo) {
            DatanodeInfo[] nodes = blk.getLocations();
            for (int j = 0; j < nodes.length; j++) {
                if (expectedExcludedNode != null && nodes[j].equals(expectedExcludedNode)) {
                    output = "For block " + blk.getBlock() + " replica on " + nodes[j] + " found in LocatedBlock.";
                    LOG.info(output);
                    return output;
                } else {
                    if (nodes[j].isInMaintenance()) {
                        output = "For block " + blk.getBlock() + " replica on " + nodes[j] + " which is in maintenance state.";
                        LOG.info(output);
                        return output;
                    }
                }
            }
            if (repl != nodes.length) {
                output = "Wrong number of replicas for block " + blk.getBlock() + ": expected " + repl + ", got " + nodes.length + " ,";
                for (int j = 0; j < nodes.length; j++) {
                    output += nodes[j] + ",";
                }
                output += "pending block # " + ns.getPendingReplicationBlocks() + " ,";
                output += "under replicated # " + ns.getUnderReplicatedBlocks() + " ,";
                if (expectedExcludedNode != null) {
                    output += "excluded node " + expectedExcludedNode;
                }
                LOG.info(output);
                return output;
            }
            Iterator<DatanodeStorageInfo> storageInfoIter = bm.getStorages(blk.getBlock().getLocalBlock()).iterator();
            List<DatanodeInfo> maintenanceNodes = new ArrayList<>();
            while (storageInfoIter.hasNext()) {
                DatanodeInfo node = storageInfoIter.next().getDatanodeDescriptor();
                if (node.isMaintenance()) {
                    maintenanceNodes.add(node);
                }
            }
            if (expectedMaintenanceNode != null) {
                if (!maintenanceNodes.contains(expectedMaintenanceNode)) {
                    output = "No maintenance replica on " + expectedMaintenanceNode;
                    LOG.info(output);
                    return output;
                }
            } else {
                if (maintenanceNodes.size() != 0) {
                    output = "Has maintenance replica(s)";
                    LOG.info(output);
                    return output;
                }
            }
        }
        return null;
    }

    static void checkWithRetry(FSNamesystem ns, FileSystem fileSys, Path name, int repl, DatanodeInfo inMaintenanceNode) {
        checkWithRetry(ns, fileSys, name, repl, inMaintenanceNode, inMaintenanceNode);
    }

    static void checkWithRetry(final FSNamesystem ns, final FileSystem fileSys, final Path name, final int repl, final DatanodeInfo excludedNode, final DatanodeInfo underMaintenanceNode) {
        try {
            GenericTestUtils.waitFor(new Supplier<Boolean>() {

                @Override
                public Boolean get() {
                    String output = null;
                    try {
                        output = checkFile(ns, fileSys, name, repl, excludedNode, underMaintenanceNode);
                    } catch (Exception ignored) {
                    }
                    return (output == null);
                }
            }, 100, 60000);
        } catch (Exception ignored) {
        }
    }

    static private DatanodeInfo[] getFirstBlockReplicasDatanodeInfos(FileSystem fileSys, Path name) throws IOException {
        assertTrue("Not HDFS:" + fileSys.getUri(), fileSys instanceof DistributedFileSystem);
        HdfsDataInputStream dis = (HdfsDataInputStream) fileSys.open(name);
        Collection<LocatedBlock> dinfo = dis.getAllBlocks();
        if (dinfo.iterator().hasNext()) {
            return dinfo.iterator().next().getLocations();
        } else {
            return null;
        }
    }

    @Test(timeout = 360000)
    public void testNodeDeadWhenInEnteringMaintenance_1_testMerged_1() throws Exception {
        final FSNamesystem ns = getCluster().getNamesystem(0);
        assertEquals(1, ns.getNumEnteringMaintenanceDataNodes());
    }

    @Test(timeout = 360000)
    public void testNodeDeadWhenInEnteringMaintenance_2_testMerged_2() throws Exception {
        final int numDatanodes = 1;
        startCluster(numNamenodes, numDatanodes);
        DFSClient client = getDfsClient(0);
        assertEquals("maintenance node shouldn't be live", numDatanodes - 1, client.datanodeReport(DatanodeReportType.LIVE).length);
        assertEquals("maintenance node should be live", numDatanodes, client.datanodeReport(DatanodeReportType.LIVE).length);
    }
}
