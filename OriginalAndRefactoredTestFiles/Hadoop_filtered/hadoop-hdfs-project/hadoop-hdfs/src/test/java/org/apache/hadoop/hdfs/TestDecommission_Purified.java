package org.apache.hadoop.hdfs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;
import java.util.EnumSet;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.apache.commons.text.TextStringBuilder;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.CommonConfigurationKeys;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.BatchedRemoteIterator.BatchedEntries;
import org.apache.hadoop.hdfs.client.HdfsClientConfigKeys;
import org.apache.hadoop.hdfs.client.HdfsDataInputStream;
import org.apache.hadoop.hdfs.client.HdfsDataOutputStream;
import org.apache.hadoop.hdfs.protocol.Block;
import org.apache.hadoop.hdfs.protocol.DatanodeID;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo.AdminStates;
import org.apache.hadoop.hdfs.protocol.ExtendedBlock;
import org.apache.hadoop.hdfs.protocol.HdfsConstants.DatanodeReportType;
import org.apache.hadoop.hdfs.protocol.LocatedBlock;
import org.apache.hadoop.hdfs.protocol.LocatedBlocks;
import org.apache.hadoop.hdfs.protocol.OpenFileEntry;
import org.apache.hadoop.hdfs.protocol.OpenFilesIterator;
import org.apache.hadoop.hdfs.server.blockmanagement.BlockInfo;
import org.apache.hadoop.hdfs.server.blockmanagement.BlockInfoContiguous;
import org.apache.hadoop.hdfs.server.blockmanagement.BlockManager;
import org.apache.hadoop.hdfs.server.blockmanagement.BlockManagerTestUtil;
import org.apache.hadoop.hdfs.server.blockmanagement.DatanodeDescriptor;
import org.apache.hadoop.hdfs.server.blockmanagement.DatanodeManager;
import org.apache.hadoop.hdfs.server.blockmanagement.DatanodeAdminManager;
import org.apache.hadoop.hdfs.server.common.HdfsServerConstants.BlockUCState;
import org.apache.hadoop.hdfs.server.datanode.DataNode;
import org.apache.hadoop.hdfs.server.datanode.DataNodeTestUtils;
import org.apache.hadoop.hdfs.server.datanode.SimulatedFSDataset;
import org.apache.hadoop.hdfs.server.namenode.FSNamesystem;
import org.apache.hadoop.hdfs.server.namenode.ha.HATestUtil;
import org.apache.hadoop.hdfs.server.namenode.NameNode;
import org.apache.hadoop.hdfs.server.namenode.NameNodeAdapter;
import org.apache.hadoop.hdfs.server.blockmanagement.DatanodeStatistics;
import org.apache.hadoop.hdfs.tools.DFSAdmin;
import org.apache.hadoop.test.GenericTestUtils;
import org.apache.hadoop.util.Lists;
import org.apache.hadoop.util.ToolRunner;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.eclipse.jetty.util.ajax.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

public class TestDecommission_Purified extends AdminStatesBaseTest {

    public static final Logger LOG = LoggerFactory.getLogger(TestDecommission.class);

    private static String checkFile(FileSystem fileSys, Path name, int repl, String downnode, int numDatanodes) throws IOException {
        boolean isNodeDown = (downnode != null);
        assertTrue("Not HDFS:" + fileSys.getUri(), fileSys instanceof DistributedFileSystem);
        HdfsDataInputStream dis = (HdfsDataInputStream) fileSys.open(name);
        Collection<LocatedBlock> dinfo = dis.getAllBlocks();
        for (LocatedBlock blk : dinfo) {
            int hasdown = 0;
            DatanodeInfo[] nodes = blk.getLocations();
            for (int j = 0; j < nodes.length; j++) {
                if (isNodeDown && nodes[j].getXferAddr().equals(downnode)) {
                    hasdown++;
                    if (!nodes[j].isDecommissioned()) {
                        return "For block " + blk.getBlock() + " replica on " + nodes[j] + " is given as downnode, " + "but is not decommissioned";
                    }
                    if (j != nodes.length - 1) {
                        return "For block " + blk.getBlock() + " decommissioned node " + nodes[j] + " was not last node in list: " + (j + 1) + " of " + nodes.length;
                    }
                    LOG.info("Block " + blk.getBlock() + " replica on " + nodes[j] + " is decommissioned.");
                } else {
                    if (nodes[j].isDecommissioned()) {
                        return "For block " + blk.getBlock() + " replica on " + nodes[j] + " is unexpectedly decommissioned";
                    }
                }
            }
            LOG.info("Block " + blk.getBlock() + " has " + hasdown + " decommissioned replica.");
            if (Math.min(numDatanodes, repl + hasdown) != nodes.length) {
                return "Wrong number of replicas for block " + blk.getBlock() + ": " + nodes.length + ", expected " + Math.min(numDatanodes, repl + hasdown);
            }
        }
        return null;
    }

    private void verifyStats(NameNode namenode, FSNamesystem fsn, DatanodeInfo info, DataNode node, boolean decommissioning) throws InterruptedException, IOException {
        for (int i = 0; i < 10; i++) {
            long[] newStats = namenode.getRpcServer().getStats();
            assertEquals(newStats[0], decommissioning ? 0 : info.getCapacity());
            assertEquals(newStats[1], decommissioning ? 0 : info.getDfsUsed());
            assertEquals(newStats[2], decommissioning ? 0 : info.getRemaining());
            assertEquals(fsn.getTotalLoad(), info.getXceiverCount());
            DataNodeTestUtils.triggerHeartbeat(node);
        }
    }

    private DataNode getDataNode(DatanodeInfo decomInfo) {
        DataNode decomNode = null;
        for (DataNode dn : getCluster().getDataNodes()) {
            if (decomInfo.equals(dn.getDatanodeId())) {
                decomNode = dn;
                break;
            }
        }
        assertNotNull("Could not find decomNode in cluster!", decomNode);
        return decomNode;
    }

    private static String scanIntoString(final ByteArrayOutputStream baos) {
        final TextStringBuilder sb = new TextStringBuilder();
        final Scanner scanner = new Scanner(baos.toString());
        while (scanner.hasNextLine()) {
            sb.appendln(scanner.nextLine());
        }
        scanner.close();
        return sb.toString();
    }

    private boolean verifyOpenFilesListing(String message, HashSet<Path> closedFileSet, HashMap<Path, FSDataOutputStream> openFilesMap, ByteArrayOutputStream out, int expOpenFilesListSize) {
        final String outStr = scanIntoString(out);
        LOG.info(message + " - stdout: \n" + outStr);
        for (Path closedFilePath : closedFileSet) {
            if (outStr.contains(closedFilePath.toString())) {
                return false;
            }
        }
        HashSet<Path> openFilesNotListed = new HashSet<>();
        for (Path openFilePath : openFilesMap.keySet()) {
            if (!outStr.contains(openFilePath.toString())) {
                openFilesNotListed.add(openFilePath);
            }
        }
        int actualOpenFilesListedSize = openFilesMap.size() - openFilesNotListed.size();
        if (actualOpenFilesListedSize >= expOpenFilesListSize) {
            return true;
        } else {
            LOG.info("Open files that are not listed yet: " + openFilesNotListed);
            return false;
        }
    }

    private void verifyOpenFilesBlockingDecommission(HashSet<Path> closedFileSet, HashMap<Path, FSDataOutputStream> openFilesMap, final int maxOpenFiles) throws Exception {
        final PrintStream oldStreamOut = System.out;
        try {
            final ByteArrayOutputStream toolOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(toolOut));
            final DFSAdmin dfsAdmin = new DFSAdmin(getConf());
            GenericTestUtils.waitFor(new Supplier<Boolean>() {

                @Override
                public Boolean get() {
                    try {
                        boolean result1 = false;
                        boolean result2 = false;
                        toolOut.reset();
                        assertEquals(0, ToolRunner.run(dfsAdmin, new String[] { "-listOpenFiles", "-blockingDecommission" }));
                        toolOut.flush();
                        result1 = verifyOpenFilesListing("dfsadmin -listOpenFiles -blockingDecommission", closedFileSet, openFilesMap, toolOut, maxOpenFiles);
                        if (openFilesMap.size() > 0) {
                            String firstOpenFile = null;
                            HashMap<Path, FSDataOutputStream> newOpenFilesMap = new HashMap<>();
                            HashSet<Path> newClosedFileSet = new HashSet<>();
                            for (Map.Entry<Path, FSDataOutputStream> entry : openFilesMap.entrySet()) {
                                if (firstOpenFile == null) {
                                    newOpenFilesMap.put(entry.getKey(), entry.getValue());
                                    firstOpenFile = entry.getKey().toString();
                                } else {
                                    newClosedFileSet.add(entry.getKey());
                                }
                            }
                            toolOut.reset();
                            assertEquals(0, ToolRunner.run(dfsAdmin, new String[] { "-listOpenFiles", "-blockingDecommission", "-path", firstOpenFile }));
                            toolOut.flush();
                            result2 = verifyOpenFilesListing("dfsadmin -listOpenFiles -blockingDecommission -path" + firstOpenFile, newClosedFileSet, newOpenFilesMap, toolOut, 1);
                        } else {
                            result2 = true;
                        }
                        return result1 && result2;
                    } catch (Exception e) {
                        LOG.warn("Unexpected exception: " + e);
                    }
                    return false;
                }
            }, 1000, 60000);
        } finally {
            System.setOut(oldStreamOut);
        }
    }

    private void doDecomCheck(DatanodeManager datanodeManager, DatanodeAdminManager decomManager, int expectedNumCheckedNodes) throws IOException, ExecutionException, InterruptedException {
        ArrayList<DatanodeInfo> decommissionedNodes = Lists.newArrayList();
        for (DataNode d : getCluster().getDataNodes()) {
            DatanodeInfo dn = takeNodeOutofService(0, d.getDatanodeUuid(), 0, decommissionedNodes, AdminStates.DECOMMISSION_INPROGRESS);
            decommissionedNodes.add(dn);
        }
        BlockManagerTestUtil.recheckDecommissionState(datanodeManager);
        assertEquals("Unexpected # of nodes checked", expectedNumCheckedNodes, decomManager.getNumNodesChecked());
        for (DatanodeInfo dn : decommissionedNodes) {
            putNodeInService(0, dn);
        }
    }

    private void assertTrackedAndPending(DatanodeAdminManager decomManager, int tracked, int pending) {
        assertEquals("Unexpected number of tracked nodes", tracked, decomManager.getNumTrackedNodes());
        assertEquals("Unexpected number of pending nodes", pending, decomManager.getNumPendingNodes());
    }

    @SuppressWarnings({ "unchecked" })
    public void nodeUsageVerification(int numDatanodes, long[] nodesCapacity, AdminStates decommissionState) throws IOException, InterruptedException {
        Map<String, Map<String, String>> usage = null;
        DatanodeInfo decommissionedNodeInfo = null;
        String zeroNodeUsage = "0.00%";
        getConf().setInt(DFSConfigKeys.DFS_REPLICATION_KEY, 1);
        getConf().setInt(DFSConfigKeys.DFS_HEARTBEAT_INTERVAL_KEY, 1);
        getConf().setInt(DFSConfigKeys.DFS_NAMENODE_HEARTBEAT_RECHECK_INTERVAL_KEY, 1);
        FileSystem fileSys = null;
        Path file1 = new Path("testNodeUsage.dat");
        try {
            SimulatedFSDataset.setFactory(getConf());
            startCluster(1, numDatanodes, false, nodesCapacity, false);
            ArrayList<ArrayList<DatanodeInfo>> namenodeDecomList = new ArrayList<ArrayList<DatanodeInfo>>(1);
            namenodeDecomList.add(0, new ArrayList<DatanodeInfo>(numDatanodes));
            if (decommissionState == AdminStates.DECOMMISSIONED) {
                ArrayList<DatanodeInfo> decommissionedNode = namenodeDecomList.get(0);
                decommissionedNodeInfo = takeNodeOutofService(0, null, 0, decommissionedNode, decommissionState);
            }
            fileSys = getCluster().getFileSystem(0);
            FSNamesystem ns = getCluster().getNamesystem(0);
            writeFile(fileSys, file1, 1);
            Thread.sleep(2000);
            usage = (Map<String, Map<String, String>>) JSON.parse(ns.getNodeUsage());
            String minUsageBeforeDecom = usage.get("nodeUsage").get("min");
            assertTrue(!minUsageBeforeDecom.equalsIgnoreCase(zeroNodeUsage));
            if (decommissionState == AdminStates.DECOMMISSION_INPROGRESS) {
                ArrayList<DatanodeInfo> decommissioningNodes = namenodeDecomList.get(0);
                decommissionedNodeInfo = takeNodeOutofService(0, null, 0, decommissioningNodes, decommissionState);
                usage = (Map<String, Map<String, String>>) JSON.parse(ns.getNodeUsage());
                assertTrue(usage.get("nodeUsage").get("min").equalsIgnoreCase(zeroNodeUsage));
            }
            putNodeInService(0, decommissionedNodeInfo);
            usage = (Map<String, Map<String, String>>) JSON.parse(ns.getNodeUsage());
            String nodeusageAfterRecommi = decommissionState == AdminStates.DECOMMISSION_INPROGRESS ? minUsageBeforeDecom : zeroNodeUsage;
            assertTrue(usage.get("nodeUsage").get("min").equalsIgnoreCase(nodeusageAfterRecommi));
        } finally {
            cleanupFile(fileSys, file1);
        }
    }

    private void createClusterWithDeadNodesDecommissionInProgress(final int numLiveNodes, final List<DatanodeDescriptor> liveNodes, final int numDeadNodes, final Map<DatanodeDescriptor, MiniDFSCluster.DataNodeProperties> deadNodeProps, final ArrayList<DatanodeInfo> decommissionedNodes, final Path filePath) throws Exception {
        assertTrue("Must have numLiveNode > 0", numLiveNodes > 0);
        assertTrue("Must have numDeadNode > 0", numDeadNodes > 0);
        int numNodes = numLiveNodes + numDeadNodes;
        getConf().setInt(DFSConfigKeys.DFS_NAMENODE_DECOMMISSION_MAX_CONCURRENT_TRACKED_NODES, numDeadNodes);
        getConf().setInt(MiniDFSCluster.DFS_NAMENODE_DECOMMISSION_INTERVAL_TESTING_KEY, Integer.MAX_VALUE);
        startCluster(1, numNodes);
        final FSNamesystem namesystem = getCluster().getNamesystem();
        final BlockManager blockManager = namesystem.getBlockManager();
        final DatanodeManager datanodeManager = blockManager.getDatanodeManager();
        final DatanodeAdminManager decomManager = datanodeManager.getDatanodeAdminManager();
        assertEquals(numNodes, getCluster().getDataNodes().size());
        getCluster().waitActive();
        for (final DataNode node : getCluster().getDataNodes().subList(0, numLiveNodes)) {
            liveNodes.add(getDatanodeDesriptor(namesystem, node.getDatanodeUuid()));
        }
        assertEquals(numLiveNodes, liveNodes.size());
        final List<DatanodeDescriptor> deadNodes = getCluster().getDataNodes().subList(numLiveNodes, numNodes).stream().map(dn -> getDatanodeDesriptor(namesystem, dn.getDatanodeUuid())).collect(Collectors.toList());
        assertEquals(numDeadNodes, deadNodes.size());
        writeFile(getCluster().getFileSystem(), filePath, numNodes, 10);
        for (final DatanodeDescriptor deadNode : deadNodes) {
            takeNodeOutofService(0, deadNode.getDatanodeUuid(), 0, decommissionedNodes, AdminStates.DECOMMISSION_INPROGRESS);
            decommissionedNodes.add(deadNode);
            MiniDFSCluster.DataNodeProperties dn = getCluster().stopDataNode(deadNode.getXferAddr());
            deadNodeProps.put(deadNode, dn);
            deadNode.setLastUpdate(213);
        }
        assertEquals(numDeadNodes, deadNodeProps.size());
        GenericTestUtils.waitFor(() -> decomManager.getNumTrackedNodes() == 0 && decomManager.getNumPendingNodes() == numDeadNodes && deadNodes.stream().allMatch(node -> !BlockManagerTestUtil.isNodeHealthyForDecommissionOrMaintenance(blockManager, node) && !node.isAlive()), 500, 20000);
    }

    void appendBlock(final FileSystem fs, final Path file, int expectedReplicas) throws IOException {
        LOG.info("Appending to the block pipeline");
        boolean failed = false;
        Exception failedReason = null;
        try {
            FSDataOutputStream out = fs.append(file);
            for (int i = 0; i < 512; i++) {
                out.write(i);
            }
            out.close();
        } catch (Exception e) {
            failed = true;
            failedReason = e;
        } finally {
            BlockLocation[] blocksInFile = fs.getFileBlockLocations(file, 0, 0);
            assertEquals(1, blocksInFile.length);
            List<String> replicasInBlock = Arrays.asList(blocksInFile[0].getNames());
            if (failed) {
                String errMsg = String.format("Unexpected exception appending to the block pipeline." + " nodesWithReplica=[%s]", String.join(", ", replicasInBlock));
                LOG.error(errMsg, failedReason);
                fail(errMsg);
            } else if (expectedReplicas != replicasInBlock.size()) {
                String errMsg = String.format("Expecting %d replicas in block pipeline," + " unexpectedly found %d replicas. nodesWithReplica=[%s]", expectedReplicas, replicasInBlock.size(), String.join(", ", replicasInBlock));
                LOG.error(errMsg);
                fail(errMsg);
            } else {
                String infoMsg = String.format("Successfully appended block pipeline with %d replicas." + " nodesWithReplica=[%s]", replicasInBlock.size(), String.join(", ", replicasInBlock));
                LOG.info(infoMsg);
            }
        }
    }

    @Test(timeout = 360000)
    public void testDecommissionWithNamenodeRestart_1() throws IOException, InterruptedException {
        assertEquals("Number of datanodes should be 2 ", 2, getCluster().getDataNodes().size());
    }

    @Test(timeout = 360000)
    public void testDecommissionWithNamenodeRestart_2_testMerged_2() throws IOException, InterruptedException {
        int numDatanodes = 1;
        int replicas = 1;
        startCluster(numNamenodes, numDatanodes);
        Path file1 = new Path("testDecommissionWithNamenodeRestart.dat");
        FileSystem fileSys = getCluster().getFileSystem();
        writeFile(fileSys, file1, replicas);
        DFSClient client = getDfsClient(0);
        DatanodeInfo[] info = client.datanodeReport(DatanodeReportType.LIVE);
        DatanodeID excludedDatanodeID = info[0];
        numDatanodes += 1;
        DatanodeInfo datanodeInfo = NameNodeAdapter.getDatanode(getCluster().getNamesystem(), excludedDatanodeID);
        waitNodeState(datanodeInfo, AdminStates.DECOMMISSIONED);
        assertEquals("All datanodes must be alive", numDatanodes, client.datanodeReport(DatanodeReportType.LIVE).length);
        assertTrue("Checked if block was replicated after decommission.", checkFile(fileSys, file1, replicas, datanodeInfo.getXferAddr(), numDatanodes) == null);
    }
}
