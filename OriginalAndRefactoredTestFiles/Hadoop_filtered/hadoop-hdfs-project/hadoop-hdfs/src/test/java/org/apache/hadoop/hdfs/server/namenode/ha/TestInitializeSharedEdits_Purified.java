package org.apache.hadoop.hdfs.server.namenode.ha;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.ha.HAServiceProtocol.RequestSource;
import org.apache.hadoop.ha.HAServiceProtocol.StateChangeRequestInfo;
import org.apache.hadoop.ha.ServiceFailedException;
import org.apache.hadoop.hdfs.DFSConfigKeys;
import org.apache.hadoop.hdfs.DFSUtil;
import org.apache.hadoop.hdfs.HAUtil;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.hdfs.MiniDFSNNTopology;
import org.apache.hadoop.hdfs.server.namenode.NameNode;
import org.apache.hadoop.hdfs.server.namenode.NameNodeAdapter;
import org.apache.hadoop.test.GenericTestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestInitializeSharedEdits_Purified {

    private static final Logger LOG = LoggerFactory.getLogger(TestInitializeSharedEdits.class);

    private static final Path TEST_PATH = new Path("/test");

    private Configuration conf;

    private MiniDFSCluster cluster;

    @Before
    public void setupCluster() throws IOException {
        conf = new Configuration();
        conf.setInt(DFSConfigKeys.DFS_HA_LOGROLL_PERIOD_KEY, 1);
        conf.setInt(DFSConfigKeys.DFS_HA_TAILEDITS_PERIOD_KEY, 1);
        HAUtil.setAllowStandbyReads(conf, true);
        MiniDFSNNTopology topology = MiniDFSNNTopology.simpleHATopology();
        cluster = new MiniDFSCluster.Builder(conf).nnTopology(topology).numDataNodes(0).build();
        cluster.waitActive();
        shutdownClusterAndRemoveSharedEditsDir();
    }

    @After
    public void shutdownCluster() throws IOException {
        if (cluster != null) {
            cluster.shutdown();
            cluster = null;
        }
    }

    private void shutdownClusterAndRemoveSharedEditsDir() throws IOException {
        cluster.shutdownNameNode(0);
        cluster.shutdownNameNode(1);
        File sharedEditsDir = new File(cluster.getSharedEditsDir(0, 1));
        assertTrue(FileUtil.fullyDelete(sharedEditsDir));
    }

    private void assertCannotStartNameNodes() {
        try {
            cluster.restartNameNode(0, false);
            fail("Should not have been able to start NN1 without shared dir");
        } catch (IOException ioe) {
            LOG.info("Got expected exception", ioe);
            GenericTestUtils.assertExceptionContains("storage directory does not exist or is not accessible", ioe);
        }
        try {
            cluster.restartNameNode(1, false);
            fail("Should not have been able to start NN2 without shared dir");
        } catch (IOException ioe) {
            LOG.info("Got expected exception", ioe);
            GenericTestUtils.assertExceptionContains("storage directory does not exist or is not accessible", ioe);
        }
    }

    private void assertCanStartHaNameNodes(String pathSuffix) throws ServiceFailedException, IOException, URISyntaxException, InterruptedException {
        cluster.restartNameNode(0, false);
        cluster.restartNameNode(1, true);
        cluster.getNameNode(0).getRpcServer().transitionToActive(new StateChangeRequestInfo(RequestSource.REQUEST_BY_USER));
        FileSystem fs = null;
        try {
            Path newPath = new Path(TEST_PATH, pathSuffix);
            fs = HATestUtil.configureFailoverFs(cluster, conf);
            assertTrue(fs.mkdirs(newPath));
            HATestUtil.waitForStandbyToCatchUp(cluster.getNameNode(0), cluster.getNameNode(1));
            assertTrue(NameNodeAdapter.getFileInfo(cluster.getNameNode(1), newPath.toString(), false, false, false).isDirectory());
        } finally {
            if (fs != null) {
                fs.close();
            }
        }
    }

    @Test
    public void testInitializeSharedEdits_1() throws Exception {
        assertCannotStartNameNodes();
    }

    @Test
    public void testInitializeSharedEdits_2() throws Exception {
        assertFalse(NameNode.initializeSharedEdits(cluster.getConfiguration(0)));
    }

    @Test
    public void testInitializeSharedEdits_3() throws Exception {
        assertCanStartHaNameNodes("1");
    }

    @Test
    public void testInitializeSharedEdits_4() throws Exception {
        assertCannotStartNameNodes();
    }

    @Test
    public void testInitializeSharedEdits_5() throws Exception {
        assertFalse(NameNode.initializeSharedEdits(cluster.getConfiguration(0)));
    }

    @Test
    public void testInitializeSharedEdits_6() throws Exception {
        assertCanStartHaNameNodes("2");
    }

    @Test
    public void testDontOverWriteExistingDir_1() throws IOException {
        assertFalse(NameNode.initializeSharedEdits(conf, false));
    }

    @Test
    public void testDontOverWriteExistingDir_2() throws IOException {
        assertTrue(NameNode.initializeSharedEdits(conf, false));
    }
}
