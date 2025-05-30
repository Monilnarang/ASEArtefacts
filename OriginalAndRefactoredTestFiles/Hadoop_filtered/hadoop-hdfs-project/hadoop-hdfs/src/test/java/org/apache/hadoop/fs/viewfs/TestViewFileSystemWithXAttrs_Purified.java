package org.apache.hadoop.fs.viewfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileSystemTestHelper;
import org.apache.hadoop.fs.FsConstants;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.hdfs.MiniDFSNNTopology;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class TestViewFileSystemWithXAttrs_Purified {

    private static MiniDFSCluster cluster;

    private static Configuration clusterConf = new Configuration();

    private static FileSystem fHdfs;

    private static FileSystem fHdfs2;

    private FileSystem fsView;

    private Configuration fsViewConf;

    private FileSystem fsTarget, fsTarget2;

    private Path targetTestRoot, targetTestRoot2, mountOnNn1, mountOnNn2;

    private FileSystemTestHelper fileSystemTestHelper = new FileSystemTestHelper("/tmp/TestViewFileSystemWithXAttrs");

    protected static final String name1 = "user.a1";

    protected static final byte[] value1 = { 0x31, 0x32, 0x33 };

    protected static final String name2 = "user.a2";

    protected static final byte[] value2 = { 0x37, 0x38, 0x39 };

    @BeforeClass
    public static void clusterSetupAtBeginning() throws IOException {
        cluster = new MiniDFSCluster.Builder(clusterConf).nnTopology(MiniDFSNNTopology.simpleFederatedTopology(2)).numDataNodes(2).build();
        cluster.waitClusterUp();
        fHdfs = cluster.getFileSystem(0);
        fHdfs2 = cluster.getFileSystem(1);
    }

    @AfterClass
    public static void ClusterShutdownAtEnd() throws Exception {
        if (cluster != null) {
            cluster.shutdown();
        }
    }

    @Before
    public void setUp() throws Exception {
        fsTarget = fHdfs;
        fsTarget2 = fHdfs2;
        targetTestRoot = fileSystemTestHelper.getAbsoluteTestRootPath(fsTarget);
        targetTestRoot2 = fileSystemTestHelper.getAbsoluteTestRootPath(fsTarget2);
        fsTarget.delete(targetTestRoot, true);
        fsTarget2.delete(targetTestRoot2, true);
        fsTarget.mkdirs(targetTestRoot);
        fsTarget2.mkdirs(targetTestRoot2);
        fsViewConf = ViewFileSystemTestSetup.createConfig();
        setupMountPoints();
        fsView = FileSystem.get(FsConstants.VIEWFS_URI, fsViewConf);
    }

    private void setupMountPoints() {
        mountOnNn1 = new Path("/mountOnNn1");
        mountOnNn2 = new Path("/mountOnNn2");
        ConfigUtil.addLink(fsViewConf, mountOnNn1.toString(), targetTestRoot.toUri());
        ConfigUtil.addLink(fsViewConf, mountOnNn2.toString(), targetTestRoot2.toUri());
    }

    @After
    public void tearDown() throws Exception {
        fsTarget.delete(fileSystemTestHelper.getTestRootPath(fsTarget), true);
        fsTarget2.delete(fileSystemTestHelper.getTestRootPath(fsTarget2), true);
    }

    @Test
    public void testXAttrOnMountEntry_1_testMerged_1() throws Exception {
        fsView.setXAttr(mountOnNn1, name1, value1);
        fsView.setXAttr(mountOnNn1, name2, value2);
        assertEquals(2, fsView.getXAttrs(mountOnNn1).size());
        assertArrayEquals(value1, fsView.getXAttr(mountOnNn1, name1));
        assertArrayEquals(value2, fsView.getXAttr(mountOnNn1, name2));
        assertArrayEquals(value1, fHdfs.getXAttr(targetTestRoot, name1));
        assertArrayEquals(value2, fHdfs.getXAttr(targetTestRoot, name2));
        assertEquals(0, fsView.getXAttrs(mountOnNn2).size());
        fsView.removeXAttr(mountOnNn1, name1);
        fsView.removeXAttr(mountOnNn1, name2);
        assertEquals(0, fsView.getXAttrs(mountOnNn1).size());
        fsView.setXAttr(mountOnNn2, name1, value1);
        fsView.setXAttr(mountOnNn2, name2, value2);
        assertEquals(2, fsView.getXAttrs(mountOnNn2).size());
        assertArrayEquals(value1, fsView.getXAttr(mountOnNn2, name1));
        assertArrayEquals(value2, fsView.getXAttr(mountOnNn2, name2));
        assertArrayEquals(value1, fHdfs2.getXAttr(targetTestRoot2, name1));
        assertArrayEquals(value2, fHdfs2.getXAttr(targetTestRoot2, name2));
    }

    @Test
    public void testXAttrOnMountEntry_7() throws Exception {
        assertEquals(0, fHdfs2.getXAttrs(targetTestRoot2).size());
    }

    @Test
    public void testXAttrOnMountEntry_9() throws Exception {
        assertEquals(0, fHdfs.getXAttrs(targetTestRoot).size());
    }

    @Test
    public void testXAttrOnMountEntry_16() throws Exception {
        assertEquals(0, fHdfs2.getXAttrs(targetTestRoot2).size());
    }
}
