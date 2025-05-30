package org.apache.hadoop.hdfs.server.federation.router;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.Trash;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.hdfs.DFSClient;
import org.apache.hadoop.hdfs.DFSTestUtil;
import org.apache.hadoop.hdfs.server.federation.MiniRouterDFSCluster;
import org.apache.hadoop.hdfs.server.federation.RouterConfigBuilder;
import org.apache.hadoop.hdfs.server.federation.StateStoreDFSCluster;
import org.apache.hadoop.hdfs.server.federation.resolver.MountTableManager;
import org.apache.hadoop.hdfs.server.federation.resolver.MountTableResolver;
import org.apache.hadoop.hdfs.server.federation.store.protocol.*;
import org.apache.hadoop.hdfs.server.federation.store.records.MountTable;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.util.Time;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import static org.apache.hadoop.fs.CommonConfigurationKeysPublic.FS_TRASH_INTERVAL_KEY;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestRouterTrash_Purified {

    public static final Logger LOG = LoggerFactory.getLogger(TestRouterTrash.class);

    private static StateStoreDFSCluster cluster;

    private static MiniRouterDFSCluster.RouterContext routerContext;

    private static MountTableResolver mountTable;

    private static FileSystem routerFs;

    private static FileSystem nnFs;

    private static final String TEST_USER = "test-trash";

    private static MiniRouterDFSCluster.NamenodeContext nnContext;

    private static String ns0;

    private static String ns1;

    private static final String MOUNT_POINT = "/home/data";

    private static final String FILE = MOUNT_POINT + "/file1";

    private static final String TRASH_ROOT = "/user/" + TEST_USER + "/.Trash";

    private static final String CURRENT = "/Current";

    @BeforeClass
    public static void globalSetUp() throws Exception {
        cluster = new StateStoreDFSCluster(false, 2);
        Configuration conf = new RouterConfigBuilder().stateStore().admin().rpc().http().build();
        conf.set(FS_TRASH_INTERVAL_KEY, "100");
        cluster.addRouterOverrides(conf);
        cluster.startCluster();
        cluster.startRouters();
        cluster.waitClusterUp();
        ns0 = cluster.getNameservices().get(0);
        ns1 = cluster.getNameservices().get(1);
        routerContext = cluster.getRandomRouter();
        routerFs = routerContext.getFileSystem();
        nnContext = cluster.getNamenode(ns0, null);
        nnFs = nnContext.getFileSystem();
        Router router = routerContext.getRouter();
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

    @After
    public void clearFile() throws IOException {
        FileStatus[] fileStatuses = nnFs.listStatus(new Path("/"));
        for (FileStatus file : fileStatuses) {
            nnFs.delete(file.getPath(), true);
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

    @Test
    public void testIsTrashPath_1_testMerged_1() throws IOException {
        UserGroupInformation ugi = UserGroupInformation.getCurrentUser();
        assertNotNull(ugi);
        assertTrue(MountTableResolver.isTrashPath("/user/" + ugi.getUserName() + "/.Trash/Current" + MOUNT_POINT));
        assertTrue(MountTableResolver.isTrashPath("/user/" + ugi.getUserName() + "/.Trash/" + Time.now() + MOUNT_POINT));
        assertFalse(MountTableResolver.isTrashPath("/home/user/" + ugi.getUserName() + "/.Trash/Current" + MOUNT_POINT));
        assertFalse(MountTableResolver.isTrashPath("/home/user/" + ugi.getUserName() + "/.Trash/" + Time.now() + MOUNT_POINT));
    }

    @Test
    public void testIsTrashPath_4() throws IOException {
        assertFalse(MountTableResolver.isTrashPath(MOUNT_POINT));
    }

    @Test
    public void testIsTrashPath_7() throws IOException {
        assertFalse(MountTableResolver.isTrashPath(""));
    }

    @Test
    public void testIsTrashPath_8() throws IOException {
        assertFalse(MountTableResolver.isTrashPath("/home/user/empty.Trash/Current"));
    }

    @Test
    public void testIsTrashPath_9() throws IOException {
        assertFalse(MountTableResolver.isTrashPath("/home/user/.Trash"));
    }

    @Test
    public void testIsTrashPath_10() throws IOException {
        assertFalse(MountTableResolver.isTrashPath("/.Trash/Current"));
    }

    @Test
    public void testSubtractTrashCurrentPath_1_testMerged_1() throws IOException {
        UserGroupInformation ugi = UserGroupInformation.getCurrentUser();
        assertNotNull(ugi);
        assertEquals(MOUNT_POINT, MountTableResolver.subtractTrashCurrentPath("/user/" + ugi.getUserName() + "/.Trash/Current" + MOUNT_POINT));
        assertEquals(MOUNT_POINT, MountTableResolver.subtractTrashCurrentPath("/user/" + ugi.getUserName() + "/.Trash/" + Time.now() + MOUNT_POINT));
        assertEquals("/home/user/" + ugi.getUserName() + "/.Trash/Current" + MOUNT_POINT, MountTableResolver.subtractTrashCurrentPath("/home/user/" + ugi.getUserName() + "/.Trash/Current" + MOUNT_POINT));
        long time = Time.now();
        assertEquals("/home/user/" + ugi.getUserName() + "/.Trash/" + time + MOUNT_POINT, MountTableResolver.subtractTrashCurrentPath("/home/user/" + ugi.getUserName() + "/.Trash/" + time + MOUNT_POINT));
    }

    @Test
    public void testSubtractTrashCurrentPath_6() throws IOException {
        assertEquals("", MountTableResolver.subtractTrashCurrentPath(""));
    }

    @Test
    public void testSubtractTrashCurrentPath_7() throws IOException {
        assertEquals("/home/user/empty.Trash/Current", MountTableResolver.subtractTrashCurrentPath("/home/user/empty.Trash/Current"));
    }

    @Test
    public void testSubtractTrashCurrentPath_8() throws IOException {
        assertEquals("/home/user/.Trash", MountTableResolver.subtractTrashCurrentPath("/home/user/.Trash"));
    }

    @Test
    public void testSubtractTrashCurrentPath_9() throws IOException {
        assertEquals("/.Trash/Current", MountTableResolver.subtractTrashCurrentPath("/.Trash/Current"));
    }
}
