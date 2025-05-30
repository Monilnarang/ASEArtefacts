package org.apache.hadoop.hdfs.server.namenode.ha;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URI;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.hdfs.DFSConfigKeys;
import org.apache.hadoop.hdfs.DFSUtil;
import org.apache.hadoop.hdfs.HAUtil;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.hdfs.qjournal.MiniQJMHACluster;
import org.apache.hadoop.hdfs.qjournal.server.JournalTestUtil;
import org.apache.hadoop.hdfs.server.namenode.NNStorage;
import org.apache.hadoop.hdfs.server.namenode.NameNode;
import org.apache.hadoop.test.GenericTestUtils;
import org.apache.hadoop.util.Lists;
import static org.apache.hadoop.hdfs.server.namenode.NameNodeAdapter.getFileInfo;
import static org.apache.hadoop.hdfs.qjournal.client.QuorumJournalManager.QJM_RPC_MAX_TXNS_KEY;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.hadoop.thirdparty.com.google.common.base.Joiner;

public class TestStandbyInProgressTail_Purified {

    private static final Logger LOG = LoggerFactory.getLogger(TestStandbyInProgressTail.class);

    private Configuration conf;

    private MiniQJMHACluster qjmhaCluster;

    private MiniDFSCluster cluster;

    private NameNode nn0;

    private NameNode nn1;

    @Before
    public void startUp() throws IOException {
        conf = new Configuration();
        conf.setInt(DFSConfigKeys.DFS_HA_TAILEDITS_PERIOD_KEY, 20 * 60);
        conf.setBoolean(DFSConfigKeys.DFS_HA_TAILEDITS_INPROGRESS_KEY, true);
        conf.setInt(DFSConfigKeys.DFS_QJOURNAL_SELECT_INPUT_STREAMS_TIMEOUT_KEY, 500);
        conf.setInt(QJM_RPC_MAX_TXNS_KEY, 3);
        HAUtil.setAllowStandbyReads(conf, true);
        qjmhaCluster = new MiniQJMHACluster.Builder(conf).build();
        cluster = qjmhaCluster.getDfsCluster();
        nn0 = cluster.getNameNode(0);
        nn1 = cluster.getNameNode(1);
    }

    @After
    public void tearDown() throws IOException {
        if (qjmhaCluster != null) {
            qjmhaCluster.shutdown();
        }
    }

    private static void assertNoEditFiles(Iterable<URI> dirs) throws IOException {
        assertEditFiles(dirs);
    }

    private static void assertEditFiles(Iterable<URI> dirs, String... files) throws IOException {
        for (URI u : dirs) {
            File editDirRoot = new File(u.getPath());
            File editDir = new File(editDirRoot, "current");
            GenericTestUtils.assertExists(editDir);
            if (files.length == 0) {
                LOG.info("Checking no edit files exist in " + editDir);
            } else {
                LOG.info("Checking for following edit files in " + editDir + ": " + Joiner.on(",").join(files));
            }
            GenericTestUtils.assertGlobEquals(editDir, "edits_.*", files);
        }
    }

    private static void mkdirs(NameNode nameNode, String... dirNames) throws Exception {
        for (String dirName : dirNames) {
            nameNode.getRpcServer().mkdirs(dirName, FsPermission.createImmutable((short) 0755), true);
        }
    }

    private static void waitForFileInfo(NameNode standbyNN, String... fileNames) throws Exception {
        List<String> remainingFiles = Lists.newArrayList(fileNames);
        GenericTestUtils.waitFor(new Supplier<Boolean>() {

            @Override
            public Boolean get() {
                try {
                    standbyNN.getNamesystem().getEditLogTailer().doTailEdits();
                    for (Iterator<String> it = remainingFiles.iterator(); it.hasNext(); ) {
                        if (getFileInfo(standbyNN, it.next(), true, false, false) == null) {
                            return false;
                        } else {
                            it.remove();
                        }
                    }
                    return true;
                } catch (IOException | InterruptedException e) {
                    throw new AssertionError("Exception while waiting: " + e);
                }
            }
        }, 10, 1000);
    }

    @Test
    public void testInitStartInProgressTail_1_testMerged_1() throws Exception {
        cluster.transitionToActive(0);
        assertEditFiles(cluster.getNameDirs(0), NNStorage.getInProgressEditsFileName(1));
        assertNoEditFiles(cluster.getNameDirs(1));
    }

    @Test
    public void testInitStartInProgressTail_3() throws Exception {
        assertNull(getFileInfo(nn1, "/test", true, false, false));
    }

    @Test
    public void testInitStartInProgressTail_4() throws Exception {
        assertNull(getFileInfo(nn1, "/test2", true, false, false));
    }

    @Test
    public void testInitStartInProgressTail_5() throws Exception {
        assertNull(getFileInfo(nn1, "/test3", true, false, false));
    }
}
