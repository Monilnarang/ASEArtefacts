package org.apache.hadoop.hdfs.server.federation.router;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.protocol.ClientProtocol;
import org.apache.hadoop.hdfs.protocol.DirectoryListing;
import org.apache.hadoop.hdfs.protocol.HdfsFileStatus;
import org.apache.hadoop.hdfs.server.federation.MiniRouterDFSCluster.NamenodeContext;
import org.apache.hadoop.hdfs.server.federation.MiniRouterDFSCluster.RouterContext;
import org.apache.hadoop.hdfs.server.federation.RouterConfigBuilder;
import org.apache.hadoop.hdfs.server.federation.StateStoreDFSCluster;
import org.apache.hadoop.hdfs.server.federation.resolver.MountTableManager;
import org.apache.hadoop.hdfs.server.federation.resolver.MountTableResolver;
import org.apache.hadoop.hdfs.server.federation.resolver.order.DestinationOrder;
import org.apache.hadoop.hdfs.server.federation.store.protocol.AddMountTableEntryRequest;
import org.apache.hadoop.hdfs.server.federation.store.protocol.AddMountTableEntryResponse;
import org.apache.hadoop.hdfs.server.federation.store.protocol.GetMountTableEntriesRequest;
import org.apache.hadoop.hdfs.server.federation.store.protocol.GetMountTableEntriesResponse;
import org.apache.hadoop.hdfs.server.federation.store.protocol.RemoveMountTableEntryRequest;
import org.apache.hadoop.hdfs.server.federation.store.protocol.UpdateMountTableEntryRequest;
import org.apache.hadoop.hdfs.server.federation.store.protocol.UpdateMountTableEntryResponse;
import org.apache.hadoop.hdfs.server.federation.store.records.MountTable;
import org.apache.hadoop.security.AccessControlException;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.test.LambdaTestUtils;
import org.apache.hadoop.util.Time;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestRouterMountTable_Purified {

    private static StateStoreDFSCluster cluster;

    private static NamenodeContext nnContext0;

    private static NamenodeContext nnContext1;

    private static RouterContext routerContext;

    private static MountTableResolver mountTable;

    private static ClientProtocol routerProtocol;

    private static long startTime;

    private static FileSystem nnFs0;

    private static FileSystem nnFs1;

    private static FileSystem routerFs;

    @BeforeClass
    public static void globalSetUp() throws Exception {
        startTime = Time.now();
        cluster = new StateStoreDFSCluster(false, 2);
        Configuration conf = new RouterConfigBuilder().stateStore().admin().rpc().build();
        conf.setInt(RBFConfigKeys.DFS_ROUTER_ADMIN_MAX_COMPONENT_LENGTH_KEY, 20);
        cluster.addRouterOverrides(conf);
        cluster.startCluster();
        cluster.startRouters();
        cluster.waitClusterUp();
        nnContext0 = cluster.getNamenode("ns0", null);
        nnContext1 = cluster.getNamenode("ns1", null);
        nnFs0 = nnContext0.getFileSystem();
        nnFs1 = nnContext1.getFileSystem();
        routerContext = cluster.getRandomRouter();
        routerFs = routerContext.getFileSystem();
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

    private boolean updateMountTable(final MountTable entry) throws IOException {
        RouterClient client = routerContext.getAdminClient();
        MountTableManager mountTableManager = client.getMountTableManager();
        UpdateMountTableEntryRequest updateRequest = UpdateMountTableEntryRequest.newInstance(entry);
        UpdateMountTableEntryResponse updateResponse = mountTableManager.updateMountTableEntry(updateRequest);
        mountTable.loadCache(true);
        return updateResponse.getStatus();
    }

    private void getListing(String testPath) throws IOException, URISyntaxException {
        ClientProtocol clientProtocol1 = routerContext.getClient().getNamenode();
        DirectoryListing listing = clientProtocol1.getListing(testPath, HdfsFileStatus.EMPTY_NAME, false);
        assertEquals(1, listing.getPartialListing().length);
        HdfsFileStatus fileStatus = listing.getPartialListing()[0];
        String currentOwner = fileStatus.getOwner();
        String currentGroup = fileStatus.getGroup();
        String currentFileName = fileStatus.getFullPath(new Path("/")).getName();
        assertEquals("testB", currentFileName);
        assertEquals("userB", currentOwner);
        assertEquals("groupB", currentGroup);
    }

    private MountTable createEntry(String mountPath, String ns, String remotePath, String group, String owner, short permission) throws IOException {
        MountTable entry = MountTable.newInstance(mountPath, Collections.singletonMap(ns, remotePath));
        entry.setGroupName(group);
        entry.setOwnerName(owner);
        entry.setMode(FsPermission.createImmutable(permission));
        return entry;
    }

    @Test
    public void testGetMountPointStatus_1() throws IOException {
        MountTable addEntry = MountTable.newInstance("/testA/testB/testC/testD", Collections.singletonMap("ns0", "/testA/testB/testC/testD"));
        assertTrue(addMountTable(addEntry));
    }

    @Test
    public void testGetMountPointStatus_2_testMerged_2() throws IOException {
        RouterClientProtocol clientProtocol = new RouterClientProtocol(nnFs0.getConf(), routerContext.getRouter().getRpcServer());
        String src = "/";
        String child = "testA";
        Path childPath = new Path(src, child);
        HdfsFileStatus dirStatus = clientProtocol.getMountPointStatus(childPath.toString(), 0, 0);
        assertEquals(child, dirStatus.getLocalName());
        String src1 = "/testA";
        String child1 = "testB";
        Path childPath1 = new Path(src1, child1);
        HdfsFileStatus dirStatus1 = clientProtocol.getMountPointStatus(childPath1.toString(), 0, 0);
        assertEquals(child1, dirStatus1.getLocalName());
        String src2 = "/testA/testB";
        String child2 = "testC";
        Path childPath2 = new Path(src2, child2);
        HdfsFileStatus dirStatus2 = clientProtocol.getMountPointStatus(childPath2.toString(), 0, 0);
        assertEquals(child2, dirStatus2.getLocalName());
    }

    @Test
    public void testMountTablePermissionsNoDest_1() throws IOException {
        MountTable addEntry;
        addEntry = MountTable.newInstance("/testdir1", Collections.singletonMap("ns0", "/tmp/testdir1"));
        addEntry.setGroupName("group1");
        addEntry.setOwnerName("owner1");
        addEntry.setMode(FsPermission.createImmutable((short) 0775));
        assertTrue(addMountTable(addEntry));
    }

    @Test
    public void testMountTablePermissionsNoDest_2_testMerged_2() throws IOException {
        FileStatus[] list = routerFs.listStatus(new Path("/"));
        assertEquals("group1", list[0].getGroup());
        assertEquals("owner1", list[0].getOwner());
        assertEquals((short) 0775, list[0].getPermission().toShort());
    }

    @Test
    public void testMountPointResolved_1() throws IOException {
        MountTable addEntry = MountTable.newInstance("/testdir", Collections.singletonMap("ns0", "/tmp/testdir"));
        addEntry.setGroupName("group1");
        addEntry.setOwnerName("owner1");
        assertTrue(addMountTable(addEntry));
    }

    @Test
    public void testMountPointResolved_2_testMerged_2() throws IOException {
        HdfsFileStatus finfo = routerProtocol.getFileInfo("/testdir");
        assertEquals("owner1", finfo.getOwner());
        assertEquals("group1", finfo.getGroup());
    }

    @Test
    public void testMountPointResolved_3_testMerged_3() throws IOException {
        FileStatus[] finfo1 = routerFs.listStatus(new Path("/"));
        assertEquals("owner1", finfo1[0].getOwner());
        assertEquals("group1", finfo1[0].getGroup());
    }
}
