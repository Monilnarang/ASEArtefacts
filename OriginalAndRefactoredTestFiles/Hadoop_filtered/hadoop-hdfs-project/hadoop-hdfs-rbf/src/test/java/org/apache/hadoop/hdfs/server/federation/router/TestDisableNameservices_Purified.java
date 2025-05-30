package org.apache.hadoop.hdfs.server.federation.router;

import static org.apache.hadoop.hdfs.server.federation.FederationTestUtils.simulateSlowNamenode;
import static org.apache.hadoop.util.Time.monotonicNow;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.hdfs.protocol.ClientProtocol;
import org.apache.hadoop.hdfs.server.federation.MiniRouterDFSCluster.NamenodeContext;
import org.apache.hadoop.hdfs.server.federation.MiniRouterDFSCluster.RouterContext;
import org.apache.hadoop.hdfs.server.federation.RouterConfigBuilder;
import org.apache.hadoop.hdfs.server.federation.StateStoreDFSCluster;
import org.apache.hadoop.hdfs.server.federation.metrics.RBFMetrics;
import org.apache.hadoop.hdfs.server.federation.resolver.MembershipNamenodeResolver;
import org.apache.hadoop.hdfs.server.federation.resolver.MountTableManager;
import org.apache.hadoop.hdfs.server.federation.resolver.MountTableResolver;
import org.apache.hadoop.hdfs.server.federation.store.DisabledNameserviceStore;
import org.apache.hadoop.hdfs.server.federation.store.StateStoreService;
import org.apache.hadoop.hdfs.server.federation.store.protocol.AddMountTableEntryRequest;
import org.apache.hadoop.hdfs.server.federation.store.protocol.DisableNameserviceRequest;
import org.apache.hadoop.hdfs.server.federation.store.records.MountTable;
import org.apache.hadoop.hdfs.server.namenode.NameNode;
import org.codehaus.jettison.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestDisableNameservices_Purified {

    private static StateStoreDFSCluster cluster;

    private static RouterContext routerContext;

    private static RouterClient routerAdminClient;

    private static ClientProtocol routerProtocol;

    @BeforeClass
    public static void setUp() throws Exception {
        cluster = new StateStoreDFSCluster(false, 2);
        Configuration routerConf = new RouterConfigBuilder().stateStore().metrics().admin().rpc().build();
        routerConf.setInt(RBFConfigKeys.DFS_ROUTER_HANDLER_COUNT_KEY, 8);
        routerConf.setInt(RBFConfigKeys.DFS_ROUTER_CLIENT_THREADS_SIZE, 4);
        cluster.setIndependentDNs();
        cluster.addRouterOverrides(routerConf);
        cluster.startCluster();
        cluster.startRouters();
        cluster.waitClusterUp();
        routerContext = cluster.getRandomRouter();
        routerProtocol = routerContext.getClient().getNamenode();
        routerAdminClient = routerContext.getAdminClient();
        setupNamespace();
        MiniDFSCluster dfsCluster = cluster.getCluster();
        NameNode nn0 = dfsCluster.getNameNode(0);
        simulateSlowNamenode(nn0, 1);
    }

    private static void setupNamespace() throws IOException {
        MountTableManager mountTable = routerAdminClient.getMountTableManager();
        Map<String, String> destinations = new TreeMap<>();
        destinations.put("ns0", "/dirns0");
        MountTable newEntry = MountTable.newInstance("/dirns0", destinations);
        AddMountTableEntryRequest request = AddMountTableEntryRequest.newInstance(newEntry);
        mountTable.addMountTableEntry(request);
        destinations = new TreeMap<>();
        destinations.put("ns1", "/dirns1");
        newEntry = MountTable.newInstance("/dirns1", destinations);
        request = AddMountTableEntryRequest.newInstance(newEntry);
        mountTable.addMountTableEntry(request);
        Router router = routerContext.getRouter();
        MountTableResolver mountTableResolver = (MountTableResolver) router.getSubclusterResolver();
        mountTableResolver.loadCache(true);
        NamenodeContext nn0 = cluster.getNamenode("ns0", null);
        nn0.getFileSystem().mkdirs(new Path("/dirns0/0"));
        nn0.getFileSystem().mkdirs(new Path("/dir-ns"));
        NamenodeContext nn1 = cluster.getNamenode("ns1", null);
        nn1.getFileSystem().mkdirs(new Path("/dirns1/1"));
    }

    @AfterClass
    public static void tearDown() {
        if (cluster != null) {
            cluster.stopRouter(routerContext);
            cluster.shutdown();
            cluster = null;
        }
    }

    @After
    public void cleanup() throws IOException {
        Router router = routerContext.getRouter();
        StateStoreService stateStore = router.getStateStore();
        DisabledNameserviceStore store = stateStore.getRegisteredRecordStore(DisabledNameserviceStore.class);
        store.loadCache(true);
        Set<String> disabled = store.getDisabledNameservices();
        for (String nsId : disabled) {
            store.enableNameservice(nsId);
        }
        store.loadCache(true);
    }

    private static void disableNameservice(final String nsId) throws IOException {
        NameserviceManager nsManager = routerAdminClient.getNameserviceManager();
        DisableNameserviceRequest req = DisableNameserviceRequest.newInstance(nsId);
        nsManager.disableNameservice(req);
        Router router = routerContext.getRouter();
        StateStoreService stateStore = router.getStateStore();
        DisabledNameserviceStore store = stateStore.getRegisteredRecordStore(DisabledNameserviceStore.class);
        store.loadCache(true);
        MembershipNamenodeResolver resolver = (MembershipNamenodeResolver) router.getNamenodeResolver();
        resolver.loadCache(true);
    }

    @Test
    public void testWithoutDisabling_1() throws IOException {
        long t0 = monotonicNow();
        long t = monotonicNow() - t0;
        assertTrue("It took too little: " + t + "ms", t > TimeUnit.SECONDS.toMillis(1));
    }

    @Test
    public void testWithoutDisabling_2_testMerged_2() throws IOException {
        FileSystem routerFs = routerContext.getFileSystem();
        FileStatus[] filesStatus = routerFs.listStatus(new Path("/"));
        assertEquals(3, filesStatus.length);
        assertEquals("dir-ns", filesStatus[0].getPath().getName());
        assertEquals("dirns0", filesStatus[1].getPath().getName());
        assertEquals("dirns1", filesStatus[2].getPath().getName());
    }

    @Test
    public void testDisabling_1() throws Exception {
        long t0 = monotonicNow();
        long t = monotonicNow() - t0;
        assertTrue("It took too long: " + t + "ms", t < TimeUnit.SECONDS.toMillis(1));
    }

    @Test
    public void testDisabling_2_testMerged_2() throws Exception {
        FileSystem routerFs = routerContext.getFileSystem();
        FileStatus[] filesStatus = routerFs.listStatus(new Path("/"));
        assertEquals(2, filesStatus.length);
        assertEquals("dirns0", filesStatus[0].getPath().getName());
        assertEquals("dirns1", filesStatus[1].getPath().getName());
        filesStatus = routerFs.listStatus(new Path("/dirns1"));
        assertEquals(1, filesStatus.length);
        assertEquals("1", filesStatus[0].getPath().getName());
    }
}
