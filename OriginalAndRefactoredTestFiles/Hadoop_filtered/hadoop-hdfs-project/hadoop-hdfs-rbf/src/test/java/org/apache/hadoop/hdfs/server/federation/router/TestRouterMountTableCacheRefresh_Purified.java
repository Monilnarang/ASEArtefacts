package org.apache.hadoop.hdfs.server.federation.router;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.curator.test.TestingServer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.CommonConfigurationKeys;
import org.apache.hadoop.hdfs.server.federation.FederationTestUtils;
import org.apache.hadoop.hdfs.server.federation.MiniRouterDFSCluster;
import org.apache.hadoop.hdfs.server.federation.MiniRouterDFSCluster.RouterContext;
import org.apache.hadoop.hdfs.server.federation.RouterConfigBuilder;
import org.apache.hadoop.hdfs.server.federation.resolver.FileSubclusterResolver;
import org.apache.hadoop.hdfs.server.federation.resolver.MountTableManager;
import org.apache.hadoop.hdfs.server.federation.store.RouterStore;
import org.apache.hadoop.hdfs.server.federation.store.protocol.AddMountTableEntryRequest;
import org.apache.hadoop.hdfs.server.federation.store.protocol.AddMountTableEntryResponse;
import org.apache.hadoop.hdfs.server.federation.store.protocol.GetMountTableEntriesRequest;
import org.apache.hadoop.hdfs.server.federation.store.protocol.RefreshMountTableEntriesRequest;
import org.apache.hadoop.hdfs.server.federation.store.protocol.RefreshMountTableEntriesResponse;
import org.apache.hadoop.hdfs.server.federation.store.protocol.RemoveMountTableEntryRequest;
import org.apache.hadoop.hdfs.server.federation.store.protocol.RemoveMountTableEntryResponse;
import org.apache.hadoop.hdfs.server.federation.store.protocol.UpdateMountTableEntryRequest;
import org.apache.hadoop.hdfs.server.federation.store.protocol.UpdateMountTableEntryResponse;
import org.apache.hadoop.hdfs.server.federation.store.records.MountTable;
import org.apache.hadoop.service.Service.STATE;
import org.apache.hadoop.test.GenericTestUtils;
import org.apache.hadoop.util.Time;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestRouterMountTableCacheRefresh_Purified {

    private static TestingServer curatorTestingServer;

    private static MiniRouterDFSCluster cluster;

    private static RouterContext routerContext;

    private static MountTableManager mountTableManager;

    @BeforeClass
    public static void setUp() throws Exception {
        curatorTestingServer = new TestingServer();
        curatorTestingServer.start();
        final String connectString = curatorTestingServer.getConnectString();
        int numNameservices = 2;
        cluster = new MiniRouterDFSCluster(false, numNameservices);
        Configuration conf = new RouterConfigBuilder().refreshCache().admin().rpc().heartbeat().build();
        conf.setClass(RBFConfigKeys.FEDERATION_FILE_RESOLVER_CLIENT_CLASS, RBFConfigKeys.FEDERATION_FILE_RESOLVER_CLIENT_CLASS_DEFAULT, FileSubclusterResolver.class);
        conf.set(CommonConfigurationKeys.ZK_ADDRESS, connectString);
        conf.setBoolean(RBFConfigKeys.DFS_ROUTER_STORE_ENABLE, true);
        cluster.addRouterOverrides(conf);
        cluster.startCluster();
        cluster.startRouters();
        cluster.waitClusterUp();
        routerContext = cluster.getRandomRouter();
        RouterStore routerStateManager = routerContext.getRouter().getRouterStateManager();
        mountTableManager = routerContext.getAdminClient().getMountTableManager();
        FederationTestUtils.waitRouterRegistered(routerStateManager, numNameservices, 60000);
    }

    @AfterClass
    public static void destory() {
        try {
            curatorTestingServer.close();
            cluster.shutdown();
        } catch (IOException e) {
        }
    }

    @After
    public void tearDown() throws IOException {
        clearEntries();
    }

    private void clearEntries() throws IOException {
        List<MountTable> result = getMountTableEntries();
        for (MountTable mountTable : result) {
            RemoveMountTableEntryResponse removeMountTableEntry = mountTableManager.removeMountTableEntry(RemoveMountTableEntryRequest.newInstance(mountTable.getSourcePath()));
            assertTrue(removeMountTableEntry.getStatus());
        }
    }

    private List<RouterContext> getRouters() {
        List<RouterContext> result = new ArrayList<>();
        for (RouterContext rc : cluster.getRouters()) {
            if (rc.getRouter().getServiceState() == STATE.STARTED) {
                result.add(rc);
            }
        }
        return result;
    }

    private int getNumMountTableEntries() throws IOException {
        List<MountTable> records = getMountTableEntries();
        int oldEntriesCount = records.size();
        return oldEntriesCount;
    }

    private MountTable getMountTableEntry(String srcPath) throws IOException {
        List<MountTable> mountTableEntries = getMountTableEntries();
        for (MountTable mountTable : mountTableEntries) {
            String sourcePath = mountTable.getSourcePath();
            if (srcPath.equals(sourcePath)) {
                return mountTable;
            }
        }
        return null;
    }

    private void addMountTableEntry(MountTableManager mountTableMgr, MountTable newEntry) throws IOException {
        AddMountTableEntryRequest addRequest = AddMountTableEntryRequest.newInstance(newEntry);
        AddMountTableEntryResponse addResponse = mountTableMgr.addMountTableEntry(addRequest);
        assertTrue(addResponse.getStatus());
    }

    private List<MountTable> getMountTableEntries() throws IOException {
        return getMountTableEntries(mountTableManager);
    }

    private List<MountTable> getMountTableEntries(MountTableManager mountTableManagerParam) throws IOException {
        GetMountTableEntriesRequest request = GetMountTableEntriesRequest.newInstance("/");
        return mountTableManagerParam.getMountTableEntries(request).getEntries();
    }

    @Test
    public void testMountTableEntriesCacheUpdatedAfterRemoveAPICall_1_testMerged_1() throws IOException {
        int addCount = getNumMountTableEntries();
        assertEquals(1, addCount);
        int removeCount = getNumMountTableEntries();
        assertEquals(addCount - 1, removeCount);
    }

    @Test
    public void testMountTableEntriesCacheUpdatedAfterRemoveAPICall_2() throws IOException {
        String srcPath = "/removePathSrc";
        MountTable newEntry = MountTable.newInstance(srcPath, Collections.singletonMap("ns0", "/removePathDest"), Time.now(), Time.now());
        addMountTableEntry(mountTableManager, newEntry);
        RemoveMountTableEntryResponse removeMountTableEntry = mountTableManager.removeMountTableEntry(RemoveMountTableEntryRequest.newInstance(srcPath));
        assertTrue(removeMountTableEntry.getStatus());
    }

    @Test
    public void testMountTableEntriesCacheUpdatedAfterUpdateAPICall_1() throws IOException {
        int addCount = getNumMountTableEntries();
        assertEquals(1, addCount);
    }

    @Test
    public void testMountTableEntriesCacheUpdatedAfterUpdateAPICall_2_testMerged_2() throws IOException {
        String srcPath = "/updatePathSrc";
        MountTable newEntry = MountTable.newInstance(srcPath, Collections.singletonMap("ns0", "/updatePathDest"), Time.now(), Time.now());
        addMountTableEntry(mountTableManager, newEntry);
        String key = "ns1";
        String value = "/updatePathDest2";
        MountTable upateEntry = MountTable.newInstance(srcPath, Collections.singletonMap(key, value), Time.now(), Time.now());
        UpdateMountTableEntryResponse updateMountTableEntry = mountTableManager.updateMountTableEntry(UpdateMountTableEntryRequest.newInstance(upateEntry));
        assertTrue(updateMountTableEntry.getStatus());
        MountTable updatedMountTable = getMountTableEntry(srcPath);
        assertNotNull("Updated mount table entrty cannot be null", updatedMountTable);
        assertEquals(1, updatedMountTable.getDestinations().size());
        assertEquals(key, updatedMountTable.getDestinations().get(0).getNameserviceId());
        assertEquals(value, updatedMountTable.getDestinations().get(0).getDest());
    }
}
