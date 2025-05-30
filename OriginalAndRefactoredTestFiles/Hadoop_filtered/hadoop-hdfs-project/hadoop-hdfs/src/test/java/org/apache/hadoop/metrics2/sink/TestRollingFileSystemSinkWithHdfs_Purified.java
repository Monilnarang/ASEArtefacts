package org.apache.hadoop.metrics2.sink;

import java.io.IOException;
import java.net.URI;
import java.util.Calendar;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.metrics2.MetricsException;
import org.apache.hadoop.metrics2.MetricsSystem;
import org.apache.hadoop.metrics2.sink.RollingFileSystemSinkTestBase.MyMetrics1;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TestRollingFileSystemSinkWithHdfs_Purified extends RollingFileSystemSinkTestBase {

    private static final int NUM_DATANODES = 4;

    private MiniDFSCluster cluster;

    @Before
    public void setupHdfs() throws IOException {
        Configuration conf = new Configuration();
        cluster = new MiniDFSCluster.Builder(conf).numDataNodes(NUM_DATANODES).build();
        RollingFileSystemSink.hasFlushed = false;
    }

    @After
    public void shutdownHdfs() {
        if (cluster != null) {
            cluster.shutdown();
        }
    }

    @Test
    public void testInitWithNoHDFS_1() {
        assertTrue("The sink was not initialized as expected", MockSink.initialized);
    }

    @Test
    public void testInitWithNoHDFS_2() {
        assertFalse("The sink threw an unexpected error on initialization", MockSink.errored);
    }
}
