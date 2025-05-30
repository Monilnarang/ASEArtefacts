package org.apache.hadoop.hdfs.server.federation.router;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.ContentSummary;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.protocol.ClientProtocol;
import org.apache.hadoop.hdfs.protocol.HdfsFileStatus;
import org.apache.hadoop.hdfs.server.federation.MiniRouterDFSCluster.RouterContext;
import org.apache.hadoop.hdfs.server.federation.RouterConfigBuilder;
import org.apache.hadoop.hdfs.server.federation.StateStoreDFSCluster;
import org.apache.hadoop.hdfs.server.federation.resolver.MountTableManager;
import org.apache.hadoop.hdfs.server.federation.resolver.MountTableResolver;
import org.apache.hadoop.hdfs.server.federation.resolver.RemoteLocation;
import org.apache.hadoop.hdfs.server.federation.resolver.RouterResolveException;
import org.apache.hadoop.hdfs.server.federation.store.protocol.AddMountTableEntryRequest;
import org.apache.hadoop.hdfs.server.federation.store.protocol.AddMountTableEntryResponse;
import org.apache.hadoop.hdfs.server.federation.store.protocol.GetMountTableEntriesRequest;
import org.apache.hadoop.hdfs.server.federation.store.protocol.GetMountTableEntriesResponse;
import org.apache.hadoop.hdfs.server.federation.store.protocol.RemoveMountTableEntryRequest;
import org.apache.hadoop.hdfs.server.federation.store.records.MountTable;
import org.apache.hadoop.test.LambdaTestUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TestRouterMountTableWithoutDefaultNS_Purified {

    private static StateStoreDFSCluster cluster;

    private static RouterContext routerContext;

    private static MountTableResolver mountTable;

    private static ClientProtocol routerProtocol;

    private static FileSystem nnFs0;

    private static FileSystem nnFs1;

    @BeforeClass
    public static void globalSetUp() throws Exception {
        cluster = new StateStoreDFSCluster(false, 2);
        Configuration conf = new RouterConfigBuilder().stateStore().admin().rpc().build();
        conf.setInt(RBFConfigKeys.DFS_ROUTER_ADMIN_MAX_COMPONENT_LENGTH_KEY, 20);
        conf.setBoolean(RBFConfigKeys.DFS_ROUTER_DEFAULT_NAMESERVICE_ENABLE, false);
        cluster.addRouterOverrides(conf);
        cluster.startCluster();
        cluster.startRouters();
        cluster.waitClusterUp();
        nnFs0 = cluster.getNamenode("ns0", null).getFileSystem();
        nnFs1 = cluster.getNamenode("ns1", null).getFileSystem();
        routerContext = cluster.getRandomRouter();
        Router router = routerContext.getRouter();
        routerProtocol = routerContext.getClient().getNamenode();
        mountTable = (MountTableResolver) router.getSubclusterResolver();
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
    public void clearMountTable() throws IOException {
        RouterClient client = routerContext.getAdminClient();
        MountTableManager mountTableManager = client.getMountTableManager();
        GetMountTableEntriesRequest req1 = GetMountTableEntriesRequest.newInstance("/");
        GetMountTableEntriesResponse response = mountTableManager.getMountTableEntries(req1);
        for (MountTable entry : response.getEntries()) {
            RemoveMountTableEntryRequest req2 = RemoveMountTableEntryRequest.newInstance(entry.getSourcePath());
            mountTableManager.removeMountTableEntry(req2);
        }
    }

    private boolean addMountTable(final MountTable entry) throws IOException {
        RouterClient client = routerContext.getAdminClient();
        MountTableManager mountTableManager = client.getMountTableManager();
        AddMountTableEntryRequest addRequest = AddMountTableEntryRequest.newInstance(entry);
        AddMountTableEntryResponse addResponse = mountTableManager.addMountTableEntry(addRequest);
        mountTable.loadCache(true);
        return addResponse.getStatus();
    }

    void writeData(FileSystem fs, Path path, int fileLength) throws IOException {
        try (FSDataOutputStream outputStream = fs.create(path)) {
            for (int writeSize = 0; writeSize < fileLength; writeSize++) {
                outputStream.write(writeSize);
            }
        }
    }

    @Test
    public void testGetFileInfoWithSubMountPoint_1() throws IOException {
        MountTable addEntry = MountTable.newInstance("/testdir/1", Collections.singletonMap("ns0", "/testdir/1"));
        assertTrue(addMountTable(addEntry));
    }

    @Test
    public void testGetFileInfoWithSubMountPoint_2_testMerged_2() throws IOException {
        HdfsFileStatus finfo = routerProtocol.getFileInfo("/testdir");
        assertNotNull(finfo);
        assertEquals("supergroup", finfo.getGroup());
        assertTrue(finfo.isDirectory());
    }
}
