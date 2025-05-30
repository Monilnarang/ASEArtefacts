package org.apache.hadoop.hdfs.server.blockmanagement;

import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_USE_DFS_NETWORK_TOPOLOGY_KEY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.CommonConfigurationKeysPublic;
import org.apache.hadoop.fs.StorageType;
import org.apache.hadoop.hdfs.DFSTestUtil;
import org.apache.hadoop.hdfs.TestBlockStoragePolicy;
import org.apache.hadoop.hdfs.protocol.Block;
import org.apache.hadoop.hdfs.protocol.ExtendedBlock;
import org.apache.hadoop.hdfs.protocol.LocatedBlock;
import org.apache.hadoop.hdfs.server.common.HdfsServerConstants;
import org.apache.hadoop.net.NetworkTopology;
import org.apache.hadoop.net.NetworkTopologyWithNodeGroup;
import org.apache.hadoop.net.Node;
import org.junit.Test;

public class TestReplicationPolicyWithNodeGroup_Purified extends BaseReplicationPolicyTest {

    public TestReplicationPolicyWithNodeGroup() {
        this.blockPlacementPolicy = BlockPlacementPolicyWithNodeGroup.class.getName();
    }

    @Override
    DatanodeDescriptor[] getDatanodeDescriptors(Configuration conf) {
        conf.setBoolean(DFS_USE_DFS_NETWORK_TOPOLOGY_KEY, false);
        conf.set(CommonConfigurationKeysPublic.NET_TOPOLOGY_IMPL_KEY, NetworkTopologyWithNodeGroup.class.getName());
        final String[] racks = { "/d1/r1/n1", "/d1/r1/n1", "/d1/r1/n2", "/d1/r2/n3", "/d1/r2/n3", "/d1/r2/n4", "/d2/r3/n5", "/d2/r3/n6" };
        storages = DFSTestUtil.createDatanodeStorageInfos(racks);
        return DFSTestUtil.toDatanodeDescriptor(storages);
    }

    private static final DatanodeStorageInfo[] storagesInBoundaryCase;

    private static final DatanodeDescriptor[] dataNodesInBoundaryCase;

    static {
        final String[] racksInBoundaryCase = { "/d1/r1/n1", "/d1/r1/n1", "/d1/r1/n1", "/d1/r1/n2", "/d1/r2/n3", "/d1/r2/n3" };
        storagesInBoundaryCase = DFSTestUtil.createDatanodeStorageInfos(racksInBoundaryCase);
        dataNodesInBoundaryCase = DFSTestUtil.toDatanodeDescriptor(storagesInBoundaryCase);
    }

    private static final DatanodeStorageInfo[] storagesInMoreTargetsCase;

    private final static DatanodeDescriptor[] dataNodesInMoreTargetsCase;

    static {
        final String[] racksInMoreTargetsCase = { "/r1/n1", "/r1/n1", "/r1/n2", "/r1/n2", "/r1/n3", "/r1/n3", "/r2/n4", "/r2/n4", "/r2/n5", "/r2/n5", "/r2/n6", "/r2/n6" };
        storagesInMoreTargetsCase = DFSTestUtil.createDatanodeStorageInfos(racksInMoreTargetsCase);
        dataNodesInMoreTargetsCase = DFSTestUtil.toDatanodeDescriptor(storagesInMoreTargetsCase);
    }

    private final static DatanodeDescriptor NODE = DFSTestUtil.getDatanodeDescriptor("9.9.9.9", "/d2/r4/n7");

    private static final DatanodeStorageInfo[] storagesForDependencies;

    private static final DatanodeDescriptor[] dataNodesForDependencies;

    static {
        final String[] racksForDependencies = { "/d1/r1/n1", "/d1/r1/n1", "/d1/r1/n2", "/d1/r1/n2", "/d1/r1/n3", "/d1/r1/n4" };
        final String[] hostNamesForDependencies = { "h1", "h2", "h3", "h4", "h5", "h6" };
        storagesForDependencies = DFSTestUtil.createDatanodeStorageInfos(racksForDependencies, hostNamesForDependencies);
        dataNodesForDependencies = DFSTestUtil.toDatanodeDescriptor(storagesForDependencies);
    }

    private static boolean checkTargetsOnDifferentNodeGroup(DatanodeStorageInfo[] targets) {
        if (targets.length == 0)
            return true;
        Set<String> targetSet = new HashSet<>();
        for (DatanodeStorageInfo storage : targets) {
            final DatanodeDescriptor node = storage.getDatanodeDescriptor();
            String nodeGroup = NetworkTopology.getLastHalf(node.getNetworkLocation());
            if (targetSet.contains(nodeGroup)) {
                return false;
            } else {
                targetSet.add(nodeGroup);
            }
        }
        return true;
    }

    private boolean isOnSameRack(DatanodeDescriptor left, DatanodeStorageInfo right) {
        return cluster.isOnSameRack(left, right.getDatanodeDescriptor());
    }

    private boolean isOnSameNodeGroup(DatanodeStorageInfo left, DatanodeStorageInfo right) {
        return isOnSameNodeGroup(left.getDatanodeDescriptor(), right);
    }

    private boolean isOnSameNodeGroup(DatanodeDescriptor left, DatanodeStorageInfo right) {
        return cluster.isOnSameNodeGroup(left, right.getDatanodeDescriptor());
    }

    private DatanodeStorageInfo[] chooseTarget(int numOfReplicas, DatanodeDescriptor writer, Set<Node> excludedNodes, List<DatanodeDescriptor> favoredNodes) {
        return replicator.chooseTarget(filename, numOfReplicas, writer, excludedNodes, BLOCK_SIZE, favoredNodes, TestBlockStoragePolicy.DEFAULT_STORAGE_POLICY, null);
    }

    private void verifyNoTwoTargetsOnSameNodeGroup(DatanodeStorageInfo[] targets) {
        Set<String> nodeGroupSet = new HashSet<>();
        for (DatanodeStorageInfo target : targets) {
            nodeGroupSet.add(target.getDatanodeDescriptor().getNetworkLocation());
        }
        assertEquals(nodeGroupSet.size(), targets.length);
    }

    private long calculateRemaining(DatanodeDescriptor dataNode) {
        long sum = 0;
        for (DatanodeStorageInfo storageInfo : dataNode.getStorageInfos()) {
            sum += storageInfo.getRemaining();
        }
        return sum;
    }

    @Test
    public void testChooseTarget3_1_testMerged_1() throws Exception {
        DatanodeStorageInfo[] targets;
        targets = chooseTarget(0);
        assertEquals(targets.length, 0);
        targets = chooseTarget(1);
        assertEquals(targets.length, 1);
        assertEquals(storages[1], targets[0]);
        targets = chooseTarget(2);
        assertEquals(targets.length, 2);
        assertFalse(isOnSameRack(targets[0], targets[1]));
        targets = chooseTarget(3);
        assertEquals(targets.length, 3);
        assertTrue(isOnSameRack(targets[1], targets[2]));
        targets = chooseTarget(4);
        assertEquals(targets.length, 4);
        verifyNoTwoTargetsOnSameNodeGroup(targets);
        assertTrue(isOnSameRack(targets[1], targets[2]) || isOnSameRack(targets[2], targets[3]));
    }

    @Test
    public void testChooseTarget3_13() throws Exception {
        assertTrue(cluster.isNodeGroupAware());
    }
}
