package org.apache.hadoop.fs.viewfs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.security.PrivilegedExceptionAction;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.BlockStoragePolicySpi;
import org.apache.hadoop.fs.ContentSummary;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileSystemTestHelper;
import org.apache.hadoop.fs.FsConstants;
import org.apache.hadoop.fs.FsStatus;
import org.apache.hadoop.fs.LocalFileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.fs.TestFileUtil;
import org.apache.hadoop.fs.Trash;
import org.apache.hadoop.fs.UnsupportedFileSystemException;
import org.apache.hadoop.fs.contract.ContractTestUtils;
import org.apache.hadoop.fs.permission.AclEntry;
import org.apache.hadoop.fs.permission.AclStatus;
import org.apache.hadoop.fs.permission.AclUtil;
import org.apache.hadoop.fs.permission.FsAction;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.fs.viewfs.ViewFileSystem.MountPoint;
import org.apache.hadoop.security.AccessControlException;
import org.apache.hadoop.security.Credentials;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.security.token.Token;
import org.apache.hadoop.test.GenericTestUtils;
import org.assertj.core.api.Assertions;
import org.junit.Assume;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.apache.hadoop.fs.FileSystemTestHelper.*;
import static org.apache.hadoop.fs.viewfs.Constants.CONFIG_VIEWFS_ENABLE_INNER_CACHE;
import static org.apache.hadoop.fs.viewfs.Constants.PERMISSION_555;
import static org.apache.hadoop.fs.viewfs.Constants.CONFIG_VIEWFS_TRASH_FORCE_INSIDE_MOUNT_POINT;
import static org.apache.hadoop.fs.FileSystem.TRASH_PREFIX;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.apache.hadoop.test.GenericTestUtils.assertExceptionContains;
import static org.junit.Assert.*;

abstract public class ViewFileSystemBaseTest_Purified {

    FileSystem fsView;

    FileSystem fsTarget;

    Path targetTestRoot;

    Configuration conf;

    final FileSystemTestHelper fileSystemTestHelper;

    private static final Logger LOG = LoggerFactory.getLogger(ViewFileSystemBaseTest.class);

    public ViewFileSystemBaseTest() {
        this.fileSystemTestHelper = createFileSystemHelper();
    }

    protected FileSystemTestHelper createFileSystemHelper() {
        return new FileSystemTestHelper();
    }

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Before
    public void setUp() throws Exception {
        initializeTargetTestRoot();
        fsTarget.mkdirs(new Path(targetTestRoot, "user"));
        fsTarget.mkdirs(new Path(targetTestRoot, "data"));
        fsTarget.mkdirs(new Path(targetTestRoot, "dir2"));
        fsTarget.mkdirs(new Path(targetTestRoot, "dir3"));
        FileSystemTestHelper.createFile(fsTarget, new Path(targetTestRoot, "aFile"));
        conf = ViewFileSystemTestSetup.createConfig();
        setupMountPoints();
        fsView = FileSystem.get(FsConstants.VIEWFS_URI, conf);
    }

    @After
    public void tearDown() throws Exception {
        fsTarget.delete(fileSystemTestHelper.getTestRootPath(fsTarget), true);
    }

    void initializeTargetTestRoot() throws IOException {
        targetTestRoot = fileSystemTestHelper.getAbsoluteTestRootPath(fsTarget);
        fsTarget.delete(targetTestRoot, true);
        fsTarget.mkdirs(targetTestRoot);
    }

    void setupMountPoints() {
        ConfigUtil.addLink(conf, "/targetRoot", targetTestRoot.toUri());
        ConfigUtil.addLink(conf, "/user", new Path(targetTestRoot, "user").toUri());
        ConfigUtil.addLink(conf, "/user2", new Path(targetTestRoot, "user").toUri());
        ConfigUtil.addLink(conf, "/data", new Path(targetTestRoot, "data").toUri());
        ConfigUtil.addLink(conf, "/internalDir/linkToDir2", new Path(targetTestRoot, "dir2").toUri());
        ConfigUtil.addLink(conf, "/internalDir/internalDir2/linkToDir3", new Path(targetTestRoot, "dir3").toUri());
        ConfigUtil.addLink(conf, "/danglingLink", new Path(targetTestRoot, "missingTarget").toUri());
        ConfigUtil.addLink(conf, "/linkToAFile", new Path(targetTestRoot, "aFile").toUri());
    }

    int getExpectedMountPoints() {
        return 8;
    }

    int getExpectedDelegationTokenCount() {
        return 0;
    }

    int getExpectedDelegationTokenCountWithCredentials() {
        return 0;
    }

    private void setUpNestedMountPoint() throws IOException {
        ConfigUtil.setIsNestedMountPointSupported(conf, true);
        ConfigUtil.addLink(conf, "/user/userA", new Path(targetTestRoot, "user").toUri());
        ConfigUtil.addLink(conf, "/user/userB", new Path(targetTestRoot, "userB").toUri());
        ConfigUtil.addLink(conf, "/data/dataA", new Path(targetTestRoot, "dataA").toUri());
        ConfigUtil.addLink(conf, "/data/dataB", new Path(targetTestRoot, "user").toUri());
        ConfigUtil.addLink(conf, "/internalDir/linkToDir2/linkToDir2", new Path(targetTestRoot, "linkToDir2").toUri());
        fsView = FileSystem.get(FsConstants.VIEWFS_URI, conf);
    }

    static protected boolean SupportsBlocks = false;

    void compareBLs(BlockLocation[] viewBL, BlockLocation[] targetBL) {
        Assert.assertEquals(targetBL.length, viewBL.length);
        int i = 0;
        for (BlockLocation vbl : viewBL) {
            Assertions.assertThat(vbl.toString()).isEqualTo(targetBL[i].toString());
            Assertions.assertThat(vbl.getOffset()).isEqualTo(targetBL[i].getOffset());
            Assertions.assertThat(vbl.getLength()).isEqualTo(targetBL[i].getLength());
            i++;
        }
    }

    private void verifyRootChildren(FileStatus[] dirPaths) throws IOException {
        FileStatus fs;
        Assert.assertEquals(getExpectedDirPaths(), dirPaths.length);
        fs = fileSystemTestHelper.containsPath(fsView, "/user", dirPaths);
        Assert.assertNotNull(fs);
        Assert.assertTrue("A mount should appear as symlink", fs.isSymlink());
        fs = fileSystemTestHelper.containsPath(fsView, "/data", dirPaths);
        Assert.assertNotNull(fs);
        Assert.assertTrue("A mount should appear as symlink", fs.isSymlink());
        fs = fileSystemTestHelper.containsPath(fsView, "/internalDir", dirPaths);
        Assert.assertNotNull(fs);
        Assert.assertTrue("A mount should appear as symlink", fs.isDirectory());
        fs = fileSystemTestHelper.containsPath(fsView, "/danglingLink", dirPaths);
        Assert.assertNotNull(fs);
        Assert.assertTrue("A mount should appear as symlink", fs.isSymlink());
        fs = fileSystemTestHelper.containsPath(fsView, "/linkToAFile", dirPaths);
        Assert.assertNotNull(fs);
        Assert.assertTrue("A mount should appear as symlink", fs.isSymlink());
    }

    int getExpectedDirPaths() {
        return 7;
    }

    private FileStatus[] listStatusInternal(boolean located, Path dataPath) throws IOException {
        FileStatus[] dirPaths = new FileStatus[0];
        if (located) {
            RemoteIterator<LocatedFileStatus> statIter = fsView.listLocatedStatus(dataPath);
            ArrayList<LocatedFileStatus> tmp = new ArrayList<LocatedFileStatus>(10);
            while (statIter.hasNext()) {
                tmp.add(statIter.next());
            }
            dirPaths = tmp.toArray(dirPaths);
        } else {
            dirPaths = fsView.listStatus(dataPath);
        }
        return dirPaths;
    }

    Path getTrashRootInFallBackFS() throws IOException {
        return new Path(fsTarget.getHomeDirectory().toUri().getPath(), TRASH_PREFIX);
    }

    static class DeepTrashRootMockFS extends MockFileSystem {

        public static final Path TRASH = new Path("/vol/very/deep/deep/trash/dir/.Trash");

        @Override
        public Path getTrashRoot(Path path) {
            return TRASH;
        }
    }

    @Test
    public void testBasicPaths_1() {
        Assert.assertEquals(FsConstants.VIEWFS_URI, fsView.getUri());
    }

    @Test
    public void testBasicPaths_2() {
        Assert.assertEquals(fsView.makeQualified(new Path("/user/" + System.getProperty("user.name"))), fsView.getWorkingDirectory());
    }

    @Test
    public void testBasicPaths_3() {
        Assert.assertEquals(fsView.makeQualified(new Path("/user/" + System.getProperty("user.name"))), fsView.getHomeDirectory());
    }

    @Test
    public void testBasicPaths_4() {
        Assert.assertEquals(new Path("/foo/bar").makeQualified(FsConstants.VIEWFS_URI, null), fsView.makeQualified(new Path("/foo/bar")));
    }

    @Test
    public void testRenameAcrossMounts3_1_testMerged_1() throws IOException {
        Configuration conf2 = new Configuration(conf);
        conf2.set(Constants.CONFIG_VIEWFS_RENAME_STRATEGY, ViewFileSystem.RenameStrategy.SAME_TARGET_URI_ACROSS_MOUNTPOINT.toString());
        FileSystem fsView2 = FileSystem.newInstance(FsConstants.VIEWFS_URI, conf2);
        fileSystemTestHelper.createFile(fsView2, "/user/foo");
        fsView2.rename(new Path("/user/foo"), new Path("/user2/fooBarBar"));
        ContractTestUtils.assertPathDoesNotExist(fsView2, "src should not exist after rename", new Path("/user/foo"));
        ContractTestUtils.assertIsFile(fsView2, fileSystemTestHelper.getTestRootPath(fsView2, "/user2/fooBarBar"));
    }

    @Test
    public void testRenameAcrossMounts3_2() throws IOException {
        ContractTestUtils.assertPathDoesNotExist(fsTarget, "src should not exist after rename", new Path(targetTestRoot, "user/foo"));
    }

    @Test
    public void testRenameAcrossMounts3_4() throws IOException {
        ContractTestUtils.assertIsFile(fsTarget, new Path(targetTestRoot, "user/fooBarBar"));
    }

    @Test
    public void testRenameAcrossMounts4_1_testMerged_1() throws IOException {
        Configuration conf2 = new Configuration(conf);
        conf2.set(Constants.CONFIG_VIEWFS_RENAME_STRATEGY, ViewFileSystem.RenameStrategy.SAME_FILESYSTEM_ACROSS_MOUNTPOINT.toString());
        FileSystem fsView2 = FileSystem.newInstance(FsConstants.VIEWFS_URI, conf2);
        fileSystemTestHelper.createFile(fsView2, "/user/foo");
        fsView2.rename(new Path("/user/foo"), new Path("/data/fooBar"));
        ContractTestUtils.assertPathDoesNotExist(fsView2, "src should not exist after rename", new Path("/user/foo"));
        ContractTestUtils.assertIsFile(fsView2, fileSystemTestHelper.getTestRootPath(fsView2, "/data/fooBar"));
    }

    @Test
    public void testRenameAcrossMounts4_2() throws IOException {
        ContractTestUtils.assertPathDoesNotExist(fsTarget, "src should not exist after rename", new Path(targetTestRoot, "user/foo"));
    }

    @Test
    public void testRenameAcrossMounts4_4() throws IOException {
        ContractTestUtils.assertIsFile(fsTarget, new Path(targetTestRoot, "data/fooBar"));
    }

    @Test
    public void testRenameAcrossNestedMountPointSameTargetUriAcrossMountPoint_1_testMerged_1() throws IOException {
        Configuration conf2 = new Configuration(conf);
        conf2.set(Constants.CONFIG_VIEWFS_RENAME_STRATEGY, ViewFileSystem.RenameStrategy.SAME_TARGET_URI_ACROSS_MOUNTPOINT.toString());
        FileSystem fsView2 = FileSystem.newInstance(FsConstants.VIEWFS_URI, conf2);
        fileSystemTestHelper.createFile(fsView2, "/user/foo");
        fsView2.rename(new Path("/user/foo"), new Path("/user/userA/fooBarBar"));
        ContractTestUtils.assertPathDoesNotExist(fsView2, "src should not exist after rename", new Path("/user/foo"));
        ContractTestUtils.assertIsFile(fsView2, fileSystemTestHelper.getTestRootPath(fsView2, "/user/userA/fooBarBar"));
    }

    @Test
    public void testRenameAcrossNestedMountPointSameTargetUriAcrossMountPoint_2() throws IOException {
        ContractTestUtils.assertPathDoesNotExist(fsTarget, "src should not exist after rename", new Path(targetTestRoot, "user/foo"));
    }

    @Test
    public void testRenameAcrossNestedMountPointSameTargetUriAcrossMountPoint_4() throws IOException {
        ContractTestUtils.assertIsFile(fsTarget, new Path(targetTestRoot, "user/fooBarBar"));
    }

    @Test
    public void testRenameAcrossNestedMountPointSameFileSystemAcrossMountPoint_1_testMerged_1() throws IOException {
        Configuration conf2 = new Configuration(conf);
        conf2.set(Constants.CONFIG_VIEWFS_RENAME_STRATEGY, ViewFileSystem.RenameStrategy.SAME_FILESYSTEM_ACROSS_MOUNTPOINT.toString());
        FileSystem fsView2 = FileSystem.newInstance(FsConstants.VIEWFS_URI, conf2);
        fileSystemTestHelper.createFile(fsView2, "/data/foo");
        fsView2.rename(new Path("/data/foo"), new Path("/data/dataB/fooBar"));
        ContractTestUtils.assertPathDoesNotExist(fsView2, "src should not exist after rename", new Path("/data/foo"));
        ContractTestUtils.assertIsFile(fsView2, fileSystemTestHelper.getTestRootPath(fsView2, "/user/fooBar"));
    }

    @Test
    public void testRenameAcrossNestedMountPointSameFileSystemAcrossMountPoint_2() throws IOException {
        ContractTestUtils.assertPathDoesNotExist(fsTarget, "src should not exist after rename", new Path(targetTestRoot, "data/foo"));
    }

    @Test
    public void testRenameAcrossNestedMountPointSameFileSystemAcrossMountPoint_4() throws IOException {
        ContractTestUtils.assertIsFile(fsTarget, new Path(targetTestRoot, "user/fooBar"));
    }

    @Test
    public void testOperationsThroughNestedMountPointsInternal_1_testMerged_1() throws IOException {
        fileSystemTestHelper.createFile(fsView, "/user/userB/foo");
        Assert.assertTrue("Created file should be type file", fsView.getFileStatus(new Path("/user/userB/foo")).isFile());
        Assert.assertTrue("Delete should succeed", fsView.delete(new Path("/user/userB/foo"), false));
        Assert.assertFalse("File should not exist after delete", fsView.exists(new Path("/user/userB/foo")));
        fileSystemTestHelper.createFile(fsView, "/internalDir/linkToDir2/linkToDir2/foo");
        Assert.assertTrue("Created file should be type file", fsView.getFileStatus(new Path("/internalDir/linkToDir2/linkToDir2/foo")).isFile());
        Assert.assertTrue("Delete should succeed", fsView.delete(new Path("/internalDir/linkToDir2/linkToDir2/foo"), false));
        Assert.assertFalse("File should not exist after delete", fsView.exists(new Path("/internalDir/linkToDir2/linkToDir2/foo")));
    }

    @Test
    public void testOperationsThroughNestedMountPointsInternal_2() throws IOException {
        Assert.assertTrue("Target of created file should be type file", fsTarget.getFileStatus(new Path(targetTestRoot, "userB/foo")).isFile());
    }

    @Test
    public void testOperationsThroughNestedMountPointsInternal_5() throws IOException {
        Assert.assertFalse("Target File should not exist after delete", fsTarget.exists(new Path(targetTestRoot, "userB/foo")));
    }

    @Test
    public void testOperationsThroughNestedMountPointsInternal_7() throws IOException {
        Assert.assertTrue("Target of created file should be type file", fsTarget.getFileStatus(new Path(targetTestRoot, "linkToDir2/foo")).isFile());
    }

    @Test
    public void testOperationsThroughNestedMountPointsInternal_10() throws IOException {
        Assert.assertFalse("Target File should not exist after delete", fsTarget.exists(new Path(targetTestRoot, "linkToDir2/foo")));
    }

    @Test
    public void testResolvePathInternalPaths_1() throws IOException {
        Assert.assertEquals(new Path("/"), fsView.resolvePath(new Path("/")));
    }

    @Test
    public void testResolvePathInternalPaths_2() throws IOException {
        Assert.assertEquals(new Path("/internalDir"), fsView.resolvePath(new Path("/internalDir")));
    }

    @Test
    public void testResolvePathMountPoints_1() throws IOException {
        Assert.assertEquals(new Path(targetTestRoot, "user"), fsView.resolvePath(new Path("/user")));
    }

    @Test
    public void testResolvePathMountPoints_2() throws IOException {
        Assert.assertEquals(new Path(targetTestRoot, "data"), fsView.resolvePath(new Path("/data")));
    }

    @Test
    public void testResolvePathMountPoints_3() throws IOException {
        Assert.assertEquals(new Path(targetTestRoot, "dir2"), fsView.resolvePath(new Path("/internalDir/linkToDir2")));
    }

    @Test
    public void testResolvePathMountPoints_4() throws IOException {
        Assert.assertEquals(new Path(targetTestRoot, "dir3"), fsView.resolvePath(new Path("/internalDir/internalDir2/linkToDir3")));
    }

    @Test
    public void testCreateNonRecursive_1() throws IOException {
        Path path = fileSystemTestHelper.getTestRootPath(fsView, "/user/foo");
        fsView.createNonRecursive(path, false, 1024, (short) 1, 1024L, null);
        FileStatus status = fsView.getFileStatus(new Path("/user/foo"));
        Assert.assertTrue("Created file should be type file", fsView.isFile(new Path("/user/foo")));
    }

    @Test
    public void testCreateNonRecursive_2() throws IOException {
        Assert.assertTrue("Target of created file should be type file", fsTarget.isFile(new Path(targetTestRoot, "user/foo")));
    }
}
