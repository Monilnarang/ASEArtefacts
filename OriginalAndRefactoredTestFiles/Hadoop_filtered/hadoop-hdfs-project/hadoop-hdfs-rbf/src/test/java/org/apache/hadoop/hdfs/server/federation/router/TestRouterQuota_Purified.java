package org.apache.hadoop.hdfs.server.federation.router;

import static org.apache.hadoop.test.LambdaTestUtils.intercept;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.CreateFlag;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.QuotaUsage;
import org.apache.hadoop.fs.StorageType;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.hdfs.DFSClient;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.client.HdfsClientConfigKeys;
import org.apache.hadoop.hdfs.client.HdfsDataOutputStream;
import org.apache.hadoop.hdfs.protocol.ClientProtocol;
import org.apache.hadoop.hdfs.protocol.DSQuotaExceededException;
import org.apache.hadoop.hdfs.protocol.HdfsConstants;
import org.apache.hadoop.hdfs.protocol.NSQuotaExceededException;
import org.apache.hadoop.hdfs.protocol.QuotaByStorageTypeExceededException;
import org.apache.hadoop.hdfs.server.federation.MiniRouterDFSCluster.NamenodeContext;
import org.apache.hadoop.hdfs.server.federation.MiniRouterDFSCluster.RouterContext;
import org.apache.hadoop.hdfs.server.federation.RouterConfigBuilder;
import org.apache.hadoop.hdfs.server.federation.StateStoreDFSCluster;
import org.apache.hadoop.hdfs.server.federation.resolver.MountTableManager;
import org.apache.hadoop.hdfs.server.federation.resolver.MountTableResolver;
import org.apache.hadoop.hdfs.server.federation.resolver.RemoteLocation;
import org.apache.hadoop.hdfs.server.federation.store.driver.StateStoreDriver;
import org.apache.hadoop.hdfs.server.federation.store.protocol.AddMountTableEntryRequest;
import org.apache.hadoop.hdfs.server.federation.store.protocol.AddMountTableEntryResponse;
import org.apache.hadoop.hdfs.server.federation.store.protocol.GetMountTableEntriesRequest;
import org.apache.hadoop.hdfs.server.federation.store.protocol.GetMountTableEntriesResponse;
import org.apache.hadoop.hdfs.server.federation.store.protocol.RemoveMountTableEntryRequest;
import org.apache.hadoop.hdfs.server.federation.store.protocol.RemoveMountTableEntryResponse;
import org.apache.hadoop.hdfs.server.federation.store.protocol.UpdateMountTableEntryRequest;
import org.apache.hadoop.hdfs.server.federation.store.protocol.UpdateMountTableEntryResponse;
import org.apache.hadoop.hdfs.server.federation.store.records.MountTable;
import org.apache.hadoop.security.AccessControlException;
import org.apache.hadoop.hdfs.server.federation.store.records.QueryResult;
import org.apache.hadoop.test.GenericTestUtils;
import org.apache.hadoop.test.LambdaTestUtils;
import org.apache.hadoop.util.Time;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestRouterQuota_Purified {

    private static StateStoreDFSCluster cluster;

    private static NamenodeContext nnContext1;

    private static NamenodeContext nnContext2;

    private static RouterContext routerContext;

    private static MountTableResolver resolver;

    private static final int BLOCK_SIZE = 512;

    @Before
    public void setUp() throws Exception {
        cluster = new StateStoreDFSCluster(false, 2);
        Configuration routerConf = new RouterConfigBuilder().stateStore().admin().quota().rpc().build();
        routerConf.set(RBFConfigKeys.DFS_ROUTER_QUOTA_CACHE_UPDATE_INTERVAL, "2s");
        Configuration hdfsConf = new Configuration(false);
        hdfsConf.setInt(HdfsClientConfigKeys.DFS_BLOCK_SIZE_KEY, BLOCK_SIZE);
        hdfsConf.setInt(HdfsClientConfigKeys.DFS_REPLICATION_KEY, 1);
        cluster.addRouterOverrides(routerConf);
        cluster.addNamenodeOverrides(hdfsConf);
        cluster.startCluster();
        cluster.startRouters();
        cluster.waitClusterUp();
        nnContext1 = cluster.getNamenode(cluster.getNameservices().get(0), null);
        nnContext2 = cluster.getNamenode(cluster.getNameservices().get(1), null);
        routerContext = cluster.getRandomRouter();
        Router router = routerContext.getRouter();
        resolver = (MountTableResolver) router.getSubclusterResolver();
    }

    @After
    public void tearDown() {
        if (cluster != null) {
            cluster.stopRouter(routerContext);
            cluster.shutdown();
            cluster = null;
        }
    }

    private boolean addMountTable(final MountTable entry) throws IOException {
        RouterClient client = routerContext.getAdminClient();
        MountTableManager mountTableManager = client.getMountTableManager();
        AddMountTableEntryRequest addRequest = AddMountTableEntryRequest.newInstance(entry);
        AddMountTableEntryResponse addResponse = mountTableManager.addMountTableEntry(addRequest);
        resolver.loadCache(true);
        return addResponse.getStatus();
    }

    private boolean updateMountTable(final MountTable entry) throws IOException {
        RouterClient client = routerContext.getAdminClient();
        MountTableManager mountTableManager = client.getMountTableManager();
        UpdateMountTableEntryRequest updateRequest = UpdateMountTableEntryRequest.newInstance(entry);
        UpdateMountTableEntryResponse updateResponse = mountTableManager.updateMountTableEntry(updateRequest);
        resolver.loadCache(true);
        return updateResponse.getStatus();
    }

    private void appendData(String path, DFSClient client, int dataLen) throws IOException {
        EnumSet<CreateFlag> createFlag = EnumSet.of(CreateFlag.APPEND);
        HdfsDataOutputStream stream = client.append(path, 1024, createFlag, null, null);
        byte[] data = new byte[dataLen];
        stream.write(data);
        stream.close();
    }

    private boolean removeMountTable(String path) throws IOException {
        RouterClient client = routerContext.getAdminClient();
        MountTableManager mountTableManager = client.getMountTableManager();
        RemoveMountTableEntryRequest removeRequest = RemoveMountTableEntryRequest.newInstance(path);
        RemoveMountTableEntryResponse removeResponse = mountTableManager.removeMountTableEntry(removeRequest);
        resolver.loadCache(true);
        return removeResponse.getStatus();
    }

    private MountTable getMountTable(String path) throws IOException {
        resolver.loadCache(true);
        RouterClient client = routerContext.getAdminClient();
        MountTableManager mountTableManager = client.getMountTableManager();
        GetMountTableEntriesRequest getRequest = GetMountTableEntriesRequest.newInstance(path);
        GetMountTableEntriesResponse response = mountTableManager.getMountTableEntries(getRequest);
        List<MountTable> results = response.getEntries();
        return !results.isEmpty() ? results.get(0) : null;
    }

    private void prepareGlobalQuotaTestMountTable(long nsQuota, long ssQuota) throws IOException {
        final FileSystem nnFs = nnContext1.getFileSystem();
        nnFs.mkdirs(new Path("/dir-1"));
        nnFs.mkdirs(new Path("/dir-2"));
        nnFs.mkdirs(new Path("/dir-3"));
        nnFs.mkdirs(new Path("/dir-4"));
        MountTable mountTable = MountTable.newInstance("/dir-1", Collections.singletonMap("ns0", "/dir-1"));
        mountTable.setQuota(new RouterQuotaUsage.Builder().quota(nsQuota).spaceQuota(ssQuota).build());
        addMountTable(mountTable);
        mountTable = MountTable.newInstance("/dir-1/dir-2", Collections.singletonMap("ns0", "/dir-2"));
        mountTable.setQuota(new RouterQuotaUsage.Builder().spaceQuota(ssQuota * 2).build());
        addMountTable(mountTable);
        mountTable = MountTable.newInstance("/dir-1/dir-2/dir-3", Collections.singletonMap("ns0", "/dir-3"));
        addMountTable(mountTable);
        mountTable = MountTable.newInstance("/dir-4", Collections.singletonMap("ns0", "/dir-4"));
        addMountTable(mountTable);
        RouterQuotaUpdateService updateService = routerContext.getRouter().getQuotaCacheUpdateService();
        updateService.periodicInvoke();
    }

    private void prepareStorageTypeQuotaTestMountTable(StorageType type, long blkSize, long quota0, long quota1, int len0, int len1) throws Exception {
        final FileSystem nnFs1 = nnContext1.getFileSystem();
        nnFs1.mkdirs(new Path("/type0"));
        nnFs1.mkdirs(new Path("/type1"));
        ((DistributedFileSystem) nnContext1.getFileSystem()).createFile(new Path("/type0/file")).storagePolicyName("HOT").blockSize(blkSize).build().close();
        ((DistributedFileSystem) nnContext1.getFileSystem()).createFile(new Path("/type1/file")).storagePolicyName("HOT").blockSize(blkSize).build().close();
        DFSClient client = nnContext1.getClient();
        appendData("/type0/file", client, len0);
        appendData("/type1/file", client, len1);
        MountTable mountTable = MountTable.newInstance("/type0", Collections.singletonMap("ns0", "/type0"));
        mountTable.setQuota(new RouterQuotaUsage.Builder().typeQuota(type, quota0).build());
        addMountTable(mountTable);
        mountTable = MountTable.newInstance("/type0/type1", Collections.singletonMap("ns0", "/type1"));
        mountTable.setQuota(new RouterQuotaUsage.Builder().typeQuota(type, quota1).build());
        addMountTable(mountTable);
        RouterQuotaUpdateService updateService = routerContext.getRouter().getQuotaCacheUpdateService();
        updateService.periodicInvoke();
    }

    private void verifyTypeQuotaAndConsume(long[] quota, long[] consume, QuotaUsage usage) {
        for (StorageType t : StorageType.values()) {
            if (quota != null) {
                assertEquals(quota[t.ordinal()], usage.getTypeQuota(t));
            }
            if (consume != null) {
                assertEquals(consume[t.ordinal()], usage.getTypeConsumed(t));
            }
        }
    }

    @Test
    public void testClearQuotaDefAfterRemovingMountTable_1_testMerged_1() throws Exception {
        long nsQuota = 5;
        long ssQuota = 3 * BLOCK_SIZE;
        final FileSystem nnFs = nnContext1.getFileSystem();
        mountTable.setQuota(new RouterQuotaUsage.Builder().quota(nsQuota).spaceQuota(ssQuota).build());
        RouterQuotaUpdateService updateService = routerContext.getRouter().getQuotaCacheUpdateService();
        RouterQuotaManager quotaManager = routerContext.getRouter().getQuotaManager();
        ClientProtocol client = nnContext1.getClient().getNamenode();
        QuotaUsage routerQuota = quotaManager.getQuotaUsage("/setdir");
        QuotaUsage subClusterQuota = client.getQuotaUsage("/testdir15");
        assertEquals(nsQuota, routerQuota.getQuota());
        assertEquals(ssQuota, routerQuota.getSpaceQuota());
        assertEquals(nsQuota, subClusterQuota.getQuota());
        assertEquals(ssQuota, subClusterQuota.getSpaceQuota());
        routerQuota = quotaManager.getQuotaUsage("/setdir");
        subClusterQuota = client.getQuotaUsage("/testdir15");
        assertNull(routerQuota);
        assertEquals(HdfsConstants.QUOTA_RESET, subClusterQuota.getQuota());
        assertEquals(HdfsConstants.QUOTA_RESET, subClusterQuota.getSpaceQuota());
    }

    @Test
    public void testClearQuotaDefAfterRemovingMountTable_8() throws Exception {
        assertTrue(removeMountTable("/mount"));
    }
}
