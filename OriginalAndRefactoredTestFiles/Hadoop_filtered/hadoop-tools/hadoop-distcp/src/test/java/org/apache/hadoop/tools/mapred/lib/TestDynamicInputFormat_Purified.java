package org.apache.hadoop.tools.mapred.lib;

import org.apache.hadoop.tools.DistCpConstants;
import org.apache.hadoop.tools.DistCpContext;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.*;
import org.apache.hadoop.mapreduce.task.JobContextImpl;
import org.apache.hadoop.tools.CopyListing;
import org.apache.hadoop.tools.CopyListingFileStatus;
import org.apache.hadoop.tools.DistCpOptions;
import org.apache.hadoop.tools.StubContext;
import org.apache.hadoop.security.Credentials;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestDynamicInputFormat_Purified {

    private static final Logger LOG = LoggerFactory.getLogger(TestDynamicInputFormat.class);

    private static MiniDFSCluster cluster;

    private static final int N_FILES = 1000;

    private static final int NUM_SPLITS = 7;

    private static final Credentials CREDENTIALS = new Credentials();

    private static List<String> expectedFilePaths = new ArrayList<String>(N_FILES);

    @BeforeClass
    public static void setup() throws Exception {
        cluster = new MiniDFSCluster.Builder(getConfigurationForCluster()).numDataNodes(1).format(true).build();
        for (int i = 0; i < N_FILES; ++i) createFile("/tmp/source/" + String.valueOf(i));
        FileSystem fileSystem = cluster.getFileSystem();
        expectedFilePaths.add(fileSystem.listStatus(new Path("/tmp/source/0"))[0].getPath().getParent().toString());
    }

    private static Configuration getConfigurationForCluster() {
        Configuration configuration = new Configuration();
        System.setProperty("test.build.data", "target/tmp/build/TEST_DYNAMIC_INPUT_FORMAT/data");
        configuration.set("hadoop.log.dir", "target/tmp");
        LOG.debug("fs.default.name  == " + configuration.get("fs.default.name"));
        LOG.debug("dfs.http.address == " + configuration.get("dfs.http.address"));
        return configuration;
    }

    private static DistCpOptions getOptions() throws Exception {
        Path sourcePath = new Path(cluster.getFileSystem().getUri().toString() + "/tmp/source");
        Path targetPath = new Path(cluster.getFileSystem().getUri().toString() + "/tmp/target");
        List<Path> sourceList = new ArrayList<Path>();
        sourceList.add(sourcePath);
        return new DistCpOptions.Builder(sourceList, targetPath).maxMaps(NUM_SPLITS).build();
    }

    private static void createFile(String path) throws Exception {
        FileSystem fileSystem = null;
        DataOutputStream outputStream = null;
        try {
            fileSystem = cluster.getFileSystem();
            outputStream = fileSystem.create(new Path(path), true, 0);
            expectedFilePaths.add(fileSystem.listStatus(new Path(path))[0].getPath().toString());
        } finally {
            IOUtils.cleanupWithLogger(null, fileSystem, outputStream);
        }
    }

    @AfterClass
    public static void tearDown() {
        cluster.shutdown();
    }

    @Test
    public void testGetSplitRatio_1() throws Exception {
        Assert.assertEquals(1, DynamicInputFormat.getSplitRatio(1, 1000000000));
    }

    @Test
    public void testGetSplitRatio_2() throws Exception {
        Assert.assertEquals(2, DynamicInputFormat.getSplitRatio(11000000, 10));
    }

    @Test
    public void testGetSplitRatio_3() throws Exception {
        Assert.assertEquals(4, DynamicInputFormat.getSplitRatio(30, 700));
    }

    @Test
    public void testGetSplitRatio_4() throws Exception {
        Assert.assertEquals(2, DynamicInputFormat.getSplitRatio(30, 200));
    }

    @Test
    public void testGetSplitRatio_5_testMerged_5() throws Exception {
        Configuration conf = new Configuration();
        conf.setInt(DistCpConstants.CONF_LABEL_MAX_CHUNKS_TOLERABLE, -1);
        conf.setInt(DistCpConstants.CONF_LABEL_MAX_CHUNKS_IDEAL, -1);
        conf.setInt(DistCpConstants.CONF_LABEL_MIN_RECORDS_PER_CHUNK, -1);
        conf.setInt(DistCpConstants.CONF_LABEL_SPLIT_RATIO, -1);
        Assert.assertEquals(1, DynamicInputFormat.getSplitRatio(1, 1000000000, conf));
        Assert.assertEquals(2, DynamicInputFormat.getSplitRatio(11000000, 10, conf));
        Assert.assertEquals(4, DynamicInputFormat.getSplitRatio(30, 700, conf));
        Assert.assertEquals(2, DynamicInputFormat.getSplitRatio(30, 200, conf));
        conf.setInt(DistCpConstants.CONF_LABEL_MAX_CHUNKS_TOLERABLE, 100);
        conf.setInt(DistCpConstants.CONF_LABEL_MAX_CHUNKS_IDEAL, 30);
        conf.setInt(DistCpConstants.CONF_LABEL_MIN_RECORDS_PER_CHUNK, 10);
        conf.setInt(DistCpConstants.CONF_LABEL_SPLIT_RATIO, 53);
        Assert.assertEquals(53, DynamicInputFormat.getSplitRatio(3, 200, conf));
    }
}
