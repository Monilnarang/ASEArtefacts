package org.apache.hadoop.hdfs.server.namenode;

import static org.apache.hadoop.fs.CommonConfigurationKeys.FS_PERMISSIONS_UMASK_KEY;
import static org.apache.hadoop.hdfs.server.namenode.AclTestHelpers.*;
import static org.apache.hadoop.fs.permission.AclEntryScope.*;
import static org.apache.hadoop.fs.permission.AclEntryType.*;
import static org.apache.hadoop.fs.permission.FsAction.*;
import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.CommonConfigurationKeys;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.permission.AclEntry;
import org.apache.hadoop.fs.permission.AclStatus;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.fs.permission.FsAction;
import org.apache.hadoop.hdfs.DFSConfigKeys;
import org.apache.hadoop.hdfs.DFSTestUtil;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.hdfs.protocol.AclException;
import org.apache.hadoop.hdfs.protocol.FsPermissionExtension;
import org.apache.hadoop.hdfs.protocol.HdfsConstants.SafeModeAction;
import org.apache.hadoop.hdfs.server.namenode.FSDirectory.DirOp;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.security.AccessControlException;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.util.Lists;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.apache.hadoop.thirdparty.com.google.common.collect.ImmutableList;

public abstract class FSAclBaseTest_Purified {

    private static final UserGroupInformation BRUCE = UserGroupInformation.createUserForTesting("bruce", new String[] {});

    private static final UserGroupInformation DIANA = UserGroupInformation.createUserForTesting("diana", new String[] {});

    private static final UserGroupInformation SUPERGROUP_MEMBER = UserGroupInformation.createUserForTesting("super", new String[] { DFSConfigKeys.DFS_PERMISSIONS_SUPERUSERGROUP_DEFAULT });

    private static final UserGroupInformation BOB = UserGroupInformation.createUserForTesting("bob", new String[] { "groupY", "groupZ" });

    protected static MiniDFSCluster cluster;

    protected static Configuration conf;

    private static int pathCount = 0;

    private static Path path;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    private FileSystem fs, fsAsBruce, fsAsDiana, fsAsSupergroupMember, fsAsBob;

    protected static void startCluster() throws IOException {
        conf.setBoolean(DFSConfigKeys.DFS_NAMENODE_ACLS_ENABLED_KEY, true);
        cluster = new MiniDFSCluster.Builder(conf).numDataNodes(1).build();
        cluster.waitActive();
    }

    @AfterClass
    public static void shutdown() {
        if (cluster != null) {
            cluster.shutdown();
        }
    }

    @Before
    public void setUp() throws Exception {
        pathCount += 1;
        path = new Path("/p" + pathCount);
        initFileSystems();
    }

    @After
    public void destroyFileSystems() {
        IOUtils.cleanupWithLogger(null, fs, fsAsBruce, fsAsDiana, fsAsSupergroupMember);
        fs = fsAsBruce = fsAsDiana = fsAsSupergroupMember = fsAsBob = null;
    }

    protected FileSystem createFileSystem() throws Exception {
        return cluster.getFileSystem();
    }

    protected FileSystem createFileSystem(UserGroupInformation user) throws Exception {
        return DFSTestUtil.getFileSystemAs(user, cluster.getConfiguration(0));
    }

    private void initFileSystems() throws Exception {
        fs = createFileSystem();
        fsAsBruce = createFileSystem(BRUCE);
        fsAsDiana = createFileSystem(DIANA);
        fsAsBob = createFileSystem(BOB);
        fsAsSupergroupMember = createFileSystem(SUPERGROUP_MEMBER);
    }

    private void restartCluster() throws Exception {
        destroyFileSystems();
        shutdown();
        cluster = new MiniDFSCluster.Builder(conf).numDataNodes(1).format(false).build();
        cluster.waitActive();
        initFileSystems();
    }

    private static void assertAclFeature(boolean expectAclFeature) throws IOException {
        assertAclFeature(path, expectAclFeature);
    }

    private static void assertAclFeature(Path pathToCheck, boolean expectAclFeature) throws IOException {
        assertAclFeature(cluster, pathToCheck, expectAclFeature);
    }

    public static void assertAclFeature(final MiniDFSCluster miniCluster, Path pathToCheck, boolean expectAclFeature) throws IOException {
        AclFeature aclFeature = getAclFeature(pathToCheck, miniCluster);
        if (expectAclFeature) {
            assertNotNull(aclFeature);
            ImmutableList<AclEntry> entries = AclStorage.getEntriesFromAclFeature(aclFeature);
            assertFalse(entries.isEmpty());
        } else {
            assertNull(aclFeature);
        }
    }

    public static AclFeature getAclFeature(Path pathToCheck, MiniDFSCluster cluster) throws IOException {
        INode inode = cluster.getNamesystem().getFSDirectory().getINode(pathToCheck.toUri().getPath(), DirOp.READ_LINK);
        assertNotNull(inode);
        AclFeature aclFeature = inode.getAclFeature();
        return aclFeature;
    }

    private void assertPermission(short perm) throws IOException {
        assertPermission(path, perm);
    }

    private void assertPermission(Path pathToCheck, short perm) throws IOException {
        AclTestHelpers.assertPermission(fs, pathToCheck, perm);
    }

    @Test
    public void testSetPermissionCannotSetAclBit_1() throws IOException {
        assertPermission((short) 0700);
    }

    @Test
    public void testSetPermissionCannotSetAclBit_2_testMerged_2() throws IOException {
        FileSystem.mkdirs(fs, path, FsPermission.createImmutable((short) 0750));
        fs.setPermission(path, FsPermission.createImmutable((short) 0700));
        fs.setPermission(path, new FsPermissionExtension(FsPermission.createImmutable((short) 0755), true, true, true));
        INode inode = cluster.getNamesystem().getFSDirectory().getINode(path.toUri().getPath(), DirOp.READ_LINK);
        assertNotNull(inode);
        FsPermission perm = inode.getFsPermission();
        assertNotNull(perm);
        assertEquals(0755, perm.toShort());
        FileStatus stat = fs.getFileStatus(path);
        assertFalse(stat.hasAcl());
        assertFalse(stat.isEncrypted());
        assertFalse(stat.isErasureCoded());
        assertEquals(0755, perm.toExtendedShort());
    }

    @Test
    public void testSetPermissionCannotSetAclBit_9() throws IOException {
        assertAclFeature(false);
    }
}
