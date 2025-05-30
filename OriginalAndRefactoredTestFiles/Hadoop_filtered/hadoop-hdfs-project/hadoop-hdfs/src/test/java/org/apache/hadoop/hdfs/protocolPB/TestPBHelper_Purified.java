package org.apache.hadoop.hdfs.protocolPB;

import org.apache.hadoop.thirdparty.protobuf.UninitializedMessageException;
import org.apache.hadoop.hdfs.protocol.AddErasureCodingPolicyResponse;
import org.apache.hadoop.hdfs.protocol.SystemErasureCodingPolicies;
import org.apache.hadoop.hdfs.server.protocol.OutlierMetrics;
import org.apache.hadoop.hdfs.server.protocol.SlowDiskReports;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.apache.hadoop.thirdparty.com.google.common.collect.ImmutableMap;
import org.apache.hadoop.fs.permission.AclEntry;
import org.apache.hadoop.fs.permission.AclEntryScope;
import org.apache.hadoop.fs.permission.AclEntryType;
import org.apache.hadoop.fs.permission.AclStatus;
import org.apache.hadoop.fs.permission.FsAction;
import org.apache.hadoop.fs.FsServerDefaults;
import org.apache.hadoop.fs.StorageType;
import org.apache.hadoop.hdfs.DFSConfigKeys;
import org.apache.hadoop.hdfs.DFSTestUtil;
import org.apache.hadoop.hdfs.StripedFileTestUtil;
import org.apache.hadoop.hdfs.client.HdfsClientConfigKeys;
import org.apache.hadoop.hdfs.protocol.Block;
import org.apache.hadoop.hdfs.protocol.BlockChecksumType;
import org.apache.hadoop.hdfs.protocol.BlockType;
import org.apache.hadoop.hdfs.protocol.DatanodeID;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo.DatanodeInfoBuilder;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo.AdminStates;
import org.apache.hadoop.hdfs.protocol.ExtendedBlock;
import org.apache.hadoop.hdfs.protocol.LocatedBlock;
import org.apache.hadoop.hdfs.protocol.proto.DatanodeProtocolProtos.BlockCommandProto;
import org.apache.hadoop.hdfs.protocol.proto.DatanodeProtocolProtos.BlockECReconstructionCommandProto;
import org.apache.hadoop.hdfs.protocol.proto.DatanodeProtocolProtos.BlockRecoveryCommandProto;
import org.apache.hadoop.hdfs.protocol.proto.DatanodeProtocolProtos.DatanodeRegistrationProto;
import org.apache.hadoop.hdfs.protocol.proto.HdfsProtos;
import org.apache.hadoop.hdfs.protocol.proto.HdfsProtos.BlockProto;
import org.apache.hadoop.hdfs.protocol.proto.HdfsProtos.BlockTypeProto;
import org.apache.hadoop.hdfs.protocol.proto.HdfsProtos.DatanodeIDProto;
import org.apache.hadoop.hdfs.protocol.proto.HdfsProtos.DatanodeStorageProto;
import org.apache.hadoop.hdfs.protocol.proto.HdfsProtos.ExtendedBlockProto;
import org.apache.hadoop.hdfs.protocol.proto.HdfsProtos.LocatedBlockProto;
import org.apache.hadoop.hdfs.protocol.proto.HdfsServerProtos.BlockKeyProto;
import org.apache.hadoop.hdfs.protocol.proto.HdfsServerProtos.BlockWithLocationsProto;
import org.apache.hadoop.hdfs.protocol.proto.HdfsServerProtos.BlocksWithLocationsProto;
import org.apache.hadoop.hdfs.protocol.proto.HdfsServerProtos.CheckpointSignatureProto;
import org.apache.hadoop.hdfs.protocol.proto.HdfsServerProtos.ExportedBlockKeysProto;
import org.apache.hadoop.hdfs.protocol.proto.HdfsServerProtos.NamenodeRegistrationProto;
import org.apache.hadoop.hdfs.protocol.proto.HdfsServerProtos.NamenodeRegistrationProto.NamenodeRoleProto;
import org.apache.hadoop.hdfs.protocol.proto.HdfsServerProtos.NamespaceInfoProto;
import org.apache.hadoop.hdfs.protocol.proto.HdfsServerProtos.RecoveringBlockProto;
import org.apache.hadoop.hdfs.protocol.proto.HdfsServerProtos.RemoteEditLogManifestProto;
import org.apache.hadoop.hdfs.protocol.proto.HdfsServerProtos.RemoteEditLogProto;
import org.apache.hadoop.hdfs.protocol.proto.HdfsServerProtos.StorageInfoProto;
import org.apache.hadoop.hdfs.security.token.block.BlockKey;
import org.apache.hadoop.hdfs.security.token.block.BlockTokenIdentifier;
import org.apache.hadoop.hdfs.security.token.block.ExportedBlockKeys;
import org.apache.hadoop.hdfs.server.blockmanagement.BlockManagerTestUtil;
import org.apache.hadoop.hdfs.server.blockmanagement.DatanodeStorageInfo;
import org.apache.hadoop.hdfs.server.common.HdfsServerConstants;
import org.apache.hadoop.hdfs.server.common.HdfsServerConstants.NamenodeRole;
import org.apache.hadoop.hdfs.server.common.HdfsServerConstants.NodeType;
import org.apache.hadoop.hdfs.server.common.StorageInfo;
import org.apache.hadoop.hdfs.server.namenode.CheckpointSignature;
import org.apache.hadoop.hdfs.server.protocol.BlockCommand;
import org.apache.hadoop.hdfs.server.protocol.BlockECReconstructionCommand.BlockECReconstructionInfo;
import org.apache.hadoop.hdfs.server.protocol.BlockRecoveryCommand;
import org.apache.hadoop.hdfs.server.protocol.BlockRecoveryCommand.RecoveringBlock;
import org.apache.hadoop.hdfs.server.protocol.BlocksWithLocations;
import org.apache.hadoop.hdfs.server.protocol.BlocksWithLocations.BlockWithLocations;
import org.apache.hadoop.hdfs.server.protocol.BlocksWithLocations.StripedBlockWithLocations;
import org.apache.hadoop.hdfs.server.protocol.BlockECReconstructionCommand;
import org.apache.hadoop.hdfs.server.protocol.DatanodeProtocol;
import org.apache.hadoop.hdfs.server.protocol.DatanodeRegistration;
import org.apache.hadoop.hdfs.server.protocol.DatanodeStorage;
import org.apache.hadoop.hdfs.server.protocol.NamenodeRegistration;
import org.apache.hadoop.hdfs.server.protocol.NamespaceInfo;
import org.apache.hadoop.hdfs.server.protocol.RemoteEditLog;
import org.apache.hadoop.hdfs.server.protocol.RemoteEditLogManifest;
import org.apache.hadoop.hdfs.server.protocol.SlowPeerReports;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.hdfs.protocol.ErasureCodingPolicy;
import org.apache.hadoop.io.erasurecode.ECSchema;
import org.apache.hadoop.security.proto.SecurityProtos.TokenProto;
import org.apache.hadoop.security.token.Token;
import org.apache.hadoop.test.GenericTestUtils;
import org.apache.hadoop.util.DataChecksum;
import org.apache.hadoop.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.apache.hadoop.thirdparty.com.google.common.base.Joiner;
import org.apache.hadoop.thirdparty.com.google.common.collect.ImmutableList;
import org.apache.hadoop.thirdparty.protobuf.ByteString;

public class TestPBHelper_Purified {

    private static final double DELTA = 0.000001;

    private static StorageInfo getStorageInfo(NodeType type) {
        return new StorageInfo(1, 2, "cid", 3, type);
    }

    void compare(DatanodeID dn, DatanodeID dn2) {
        assertEquals(dn.getIpAddr(), dn2.getIpAddr());
        assertEquals(dn.getHostName(), dn2.getHostName());
        assertEquals(dn.getDatanodeUuid(), dn2.getDatanodeUuid());
        assertEquals(dn.getXferPort(), dn2.getXferPort());
        assertEquals(dn.getInfoPort(), dn2.getInfoPort());
        assertEquals(dn.getIpcPort(), dn2.getIpcPort());
    }

    void compare(DatanodeStorage dns1, DatanodeStorage dns2) {
        assertThat(dns2.getStorageID(), is(dns1.getStorageID()));
        assertThat(dns2.getState(), is(dns1.getState()));
        assertThat(dns2.getStorageType(), is(dns1.getStorageType()));
    }

    private static BlockWithLocations getBlockWithLocations(int bid, boolean isStriped) {
        final String[] datanodeUuids = { "dn1", "dn2", "dn3" };
        final String[] storageIDs = { "s1", "s2", "s3" };
        final StorageType[] storageTypes = { StorageType.DISK, StorageType.DISK, StorageType.DISK };
        final byte[] indices = { 0, 1, 2 };
        final short dataBlkNum = 6;
        BlockWithLocations blkLocs = new BlockWithLocations(new Block(bid, 0, 1), datanodeUuids, storageIDs, storageTypes);
        if (isStriped) {
            blkLocs = new StripedBlockWithLocations(blkLocs, indices, dataBlkNum, StripedFileTestUtil.getDefaultECPolicy().getCellSize());
        }
        return blkLocs;
    }

    private void compare(BlockWithLocations locs1, BlockWithLocations locs2) {
        assertEquals(locs1.getBlock(), locs2.getBlock());
        assertTrue(Arrays.equals(locs1.getStorageIDs(), locs2.getStorageIDs()));
        if (locs1 instanceof StripedBlockWithLocations) {
            assertTrue(Arrays.equals(((StripedBlockWithLocations) locs1).getIndices(), ((StripedBlockWithLocations) locs2).getIndices()));
        }
    }

    private static BlockKey getBlockKey(int keyId) {
        return new BlockKey(keyId, 10, "encodedKey".getBytes());
    }

    private void compare(BlockKey k1, BlockKey k2) {
        assertEquals(k1.getExpiryDate(), k2.getExpiryDate());
        assertEquals(k1.getKeyId(), k2.getKeyId());
        assertTrue(Arrays.equals(k1.getEncodedKey(), k2.getEncodedKey()));
    }

    void compare(ExportedBlockKeys expKeys, ExportedBlockKeys expKeys1) {
        BlockKey[] allKeys = expKeys.getAllKeys();
        BlockKey[] allKeys1 = expKeys1.getAllKeys();
        assertEquals(allKeys.length, allKeys1.length);
        for (int i = 0; i < allKeys.length; i++) {
            compare(allKeys[i], allKeys1[i]);
        }
        compare(expKeys.getCurrentKey(), expKeys1.getCurrentKey());
        assertEquals(expKeys.getKeyUpdateInterval(), expKeys1.getKeyUpdateInterval());
        assertEquals(expKeys.getTokenLifetime(), expKeys1.getTokenLifetime());
    }

    private static void compare(RemoteEditLog l1, RemoteEditLog l2) {
        assertEquals(l1.getEndTxId(), l2.getEndTxId());
        assertEquals(l1.getStartTxId(), l2.getStartTxId());
    }

    private void convertAndCheckRemoteEditLogManifest(RemoteEditLogManifest m, List<RemoteEditLog> logs, long committedTxnId) {
        RemoteEditLogManifestProto mProto = PBHelper.convert(m);
        RemoteEditLogManifest m1 = PBHelper.convert(mProto);
        List<RemoteEditLog> logs1 = m1.getLogs();
        assertEquals(logs.size(), logs1.size());
        for (int i = 0; i < logs.size(); i++) {
            compare(logs.get(i), logs1.get(i));
        }
        assertEquals(committedTxnId, m.getCommittedTxnId());
    }

    public ExtendedBlock getExtendedBlock() {
        return getExtendedBlock(1);
    }

    public ExtendedBlock getExtendedBlock(long blkid) {
        return new ExtendedBlock("bpid", blkid, 100, 2);
    }

    private void compare(DatanodeInfo dn1, DatanodeInfo dn2) {
        assertEquals(dn1.getAdminState(), dn2.getAdminState());
        assertEquals(dn1.getBlockPoolUsed(), dn2.getBlockPoolUsed());
        assertEquals(dn1.getBlockPoolUsedPercent(), dn2.getBlockPoolUsedPercent(), DELTA);
        assertEquals(dn1.getCapacity(), dn2.getCapacity());
        assertEquals(dn1.getDatanodeReport(), dn2.getDatanodeReport());
        assertEquals(dn1.getDfsUsed(), dn1.getDfsUsed());
        assertEquals(dn1.getDfsUsedPercent(), dn1.getDfsUsedPercent(), DELTA);
        assertEquals(dn1.getIpAddr(), dn2.getIpAddr());
        assertEquals(dn1.getHostName(), dn2.getHostName());
        assertEquals(dn1.getInfoPort(), dn2.getInfoPort());
        assertEquals(dn1.getIpcPort(), dn2.getIpcPort());
        assertEquals(dn1.getLastUpdate(), dn2.getLastUpdate());
        assertEquals(dn1.getLevel(), dn2.getLevel());
        assertEquals(dn1.getNetworkLocation(), dn2.getNetworkLocation());
    }

    private void compare(StorageInfo expected, StorageInfo actual) {
        assertEquals(expected.clusterID, actual.clusterID);
        assertEquals(expected.namespaceID, actual.namespaceID);
        assertEquals(expected.cTime, actual.cTime);
        assertEquals(expected.layoutVersion, actual.layoutVersion);
    }

    private void compare(Token<BlockTokenIdentifier> expected, Token<BlockTokenIdentifier> actual) {
        assertTrue(Arrays.equals(expected.getIdentifier(), actual.getIdentifier()));
        assertTrue(Arrays.equals(expected.getPassword(), actual.getPassword()));
        assertEquals(expected.getKind(), actual.getKind());
        assertEquals(expected.getService(), actual.getService());
    }

    private void compare(LocatedBlock expected, LocatedBlock actual) {
        assertEquals(expected.getBlock(), actual.getBlock());
        compare(expected.getBlockToken(), actual.getBlockToken());
        assertEquals(expected.getStartOffset(), actual.getStartOffset());
        assertEquals(expected.isCorrupt(), actual.isCorrupt());
        DatanodeInfo[] ei = expected.getLocations();
        DatanodeInfo[] ai = actual.getLocations();
        assertEquals(ei.length, ai.length);
        for (int i = 0; i < ei.length; i++) {
            compare(ei[i], ai[i]);
        }
    }

    private LocatedBlock createLocatedBlock() {
        DatanodeInfo[] dnInfos = { DFSTestUtil.getLocalDatanodeInfo("127.0.0.1", "h1", AdminStates.DECOMMISSION_INPROGRESS), DFSTestUtil.getLocalDatanodeInfo("127.0.0.1", "h2", AdminStates.DECOMMISSIONED), DFSTestUtil.getLocalDatanodeInfo("127.0.0.1", "h3", AdminStates.NORMAL), DFSTestUtil.getLocalDatanodeInfo("127.0.0.1", "h4", AdminStates.NORMAL), DFSTestUtil.getLocalDatanodeInfo("127.0.0.1", "h5", AdminStates.NORMAL) };
        String[] storageIDs = { "s1", "s2", "s3", "s4", "s5" };
        StorageType[] media = { StorageType.DISK, StorageType.SSD, StorageType.DISK, StorageType.RAM_DISK, StorageType.NVDIMM };
        LocatedBlock lb = new LocatedBlock(new ExtendedBlock("bp12", 12345, 10, 53), dnInfos, storageIDs, media, 5, false, new DatanodeInfo[] {});
        lb.setBlockToken(new Token<BlockTokenIdentifier>("identifier".getBytes(), "password".getBytes(), new Text("kind"), new Text("service")));
        return lb;
    }

    private LocatedBlock createLocatedBlockNoStorageMedia() {
        DatanodeInfo[] dnInfos = { DFSTestUtil.getLocalDatanodeInfo("127.0.0.1", "h1", AdminStates.DECOMMISSION_INPROGRESS), DFSTestUtil.getLocalDatanodeInfo("127.0.0.1", "h2", AdminStates.DECOMMISSIONED), DFSTestUtil.getLocalDatanodeInfo("127.0.0.1", "h3", AdminStates.NORMAL) };
        LocatedBlock lb = new LocatedBlock(new ExtendedBlock("bp12", 12345, 10, 53), dnInfos);
        lb.setBlockToken(new Token<BlockTokenIdentifier>("identifier".getBytes(), "password".getBytes(), new Text("kind"), new Text("service")));
        lb.setStartOffset(5);
        return lb;
    }

    private void assertBlockECRecoveryInfoEquals(BlockECReconstructionInfo blkECRecoveryInfo1, BlockECReconstructionInfo blkECRecoveryInfo2) {
        assertEquals(blkECRecoveryInfo1.getExtendedBlock(), blkECRecoveryInfo2.getExtendedBlock());
        DatanodeInfo[] sourceDnInfos1 = blkECRecoveryInfo1.getSourceDnInfos();
        DatanodeInfo[] sourceDnInfos2 = blkECRecoveryInfo2.getSourceDnInfos();
        assertDnInfosEqual(sourceDnInfos1, sourceDnInfos2);
        DatanodeInfo[] targetDnInfos1 = blkECRecoveryInfo1.getTargetDnInfos();
        DatanodeInfo[] targetDnInfos2 = blkECRecoveryInfo2.getTargetDnInfos();
        assertDnInfosEqual(targetDnInfos1, targetDnInfos2);
        String[] targetStorageIDs1 = blkECRecoveryInfo1.getTargetStorageIDs();
        String[] targetStorageIDs2 = blkECRecoveryInfo2.getTargetStorageIDs();
        assertEquals(targetStorageIDs1.length, targetStorageIDs2.length);
        for (int i = 0; i < targetStorageIDs1.length; i++) {
            assertEquals(targetStorageIDs1[i], targetStorageIDs2[i]);
        }
        byte[] liveBlockIndices1 = blkECRecoveryInfo1.getLiveBlockIndices();
        byte[] liveBlockIndices2 = blkECRecoveryInfo2.getLiveBlockIndices();
        for (int i = 0; i < liveBlockIndices1.length; i++) {
            assertEquals(liveBlockIndices1[i], liveBlockIndices2[i]);
        }
        ErasureCodingPolicy ecPolicy1 = blkECRecoveryInfo1.getErasureCodingPolicy();
        ErasureCodingPolicy ecPolicy2 = blkECRecoveryInfo2.getErasureCodingPolicy();
        compareECPolicies(StripedFileTestUtil.getDefaultECPolicy(), ecPolicy1);
        compareECPolicies(StripedFileTestUtil.getDefaultECPolicy(), ecPolicy2);
    }

    private void compareECPolicies(ErasureCodingPolicy ecPolicy1, ErasureCodingPolicy ecPolicy2) {
        assertEquals(ecPolicy1.getName(), ecPolicy2.getName());
        assertEquals(ecPolicy1.getNumDataUnits(), ecPolicy2.getNumDataUnits());
        assertEquals(ecPolicy1.getNumParityUnits(), ecPolicy2.getNumParityUnits());
    }

    private void assertDnInfosEqual(DatanodeInfo[] dnInfos1, DatanodeInfo[] dnInfos2) {
        assertEquals(dnInfos1.length, dnInfos2.length);
        for (int i = 0; i < dnInfos1.length; i++) {
            compare(dnInfos1[i], dnInfos2[i]);
        }
    }

    @Test
    public void testConvertNamenodeRole_1() {
        assertEquals(NamenodeRoleProto.BACKUP, PBHelper.convert(NamenodeRole.BACKUP));
    }

    @Test
    public void testConvertNamenodeRole_2() {
        assertEquals(NamenodeRoleProto.CHECKPOINT, PBHelper.convert(NamenodeRole.CHECKPOINT));
    }

    @Test
    public void testConvertNamenodeRole_3() {
        assertEquals(NamenodeRoleProto.NAMENODE, PBHelper.convert(NamenodeRole.NAMENODE));
    }

    @Test
    public void testConvertNamenodeRole_4() {
        assertEquals(NamenodeRole.BACKUP, PBHelper.convert(NamenodeRoleProto.BACKUP));
    }

    @Test
    public void testConvertNamenodeRole_5() {
        assertEquals(NamenodeRole.CHECKPOINT, PBHelper.convert(NamenodeRoleProto.CHECKPOINT));
    }

    @Test
    public void testConvertNamenodeRole_6() {
        assertEquals(NamenodeRole.NAMENODE, PBHelper.convert(NamenodeRoleProto.NAMENODE));
    }

    @Test
    public void testChecksumTypeProto_1() {
        assertEquals(DataChecksum.Type.NULL, PBHelperClient.convert(HdfsProtos.ChecksumTypeProto.CHECKSUM_NULL));
    }

    @Test
    public void testChecksumTypeProto_2() {
        assertEquals(DataChecksum.Type.CRC32, PBHelperClient.convert(HdfsProtos.ChecksumTypeProto.CHECKSUM_CRC32));
    }

    @Test
    public void testChecksumTypeProto_3() {
        assertEquals(DataChecksum.Type.CRC32C, PBHelperClient.convert(HdfsProtos.ChecksumTypeProto.CHECKSUM_CRC32C));
    }

    @Test
    public void testChecksumTypeProto_4() {
        assertEquals(PBHelperClient.convert(DataChecksum.Type.NULL), HdfsProtos.ChecksumTypeProto.CHECKSUM_NULL);
    }

    @Test
    public void testChecksumTypeProto_5() {
        assertEquals(PBHelperClient.convert(DataChecksum.Type.CRC32), HdfsProtos.ChecksumTypeProto.CHECKSUM_CRC32);
    }

    @Test
    public void testChecksumTypeProto_6() {
        assertEquals(PBHelperClient.convert(DataChecksum.Type.CRC32C), HdfsProtos.ChecksumTypeProto.CHECKSUM_CRC32C);
    }

    @Test
    public void testBlockChecksumTypeProto_1() {
        assertEquals(BlockChecksumType.MD5CRC, PBHelperClient.convert(HdfsProtos.BlockChecksumTypeProto.MD5CRC));
    }

    @Test
    public void testBlockChecksumTypeProto_2() {
        assertEquals(BlockChecksumType.COMPOSITE_CRC, PBHelperClient.convert(HdfsProtos.BlockChecksumTypeProto.COMPOSITE_CRC));
    }

    @Test
    public void testBlockChecksumTypeProto_3() {
        assertEquals(PBHelperClient.convert(BlockChecksumType.MD5CRC), HdfsProtos.BlockChecksumTypeProto.MD5CRC);
    }

    @Test
    public void testBlockChecksumTypeProto_4() {
        assertEquals(PBHelperClient.convert(BlockChecksumType.COMPOSITE_CRC), HdfsProtos.BlockChecksumTypeProto.COMPOSITE_CRC);
    }
}
