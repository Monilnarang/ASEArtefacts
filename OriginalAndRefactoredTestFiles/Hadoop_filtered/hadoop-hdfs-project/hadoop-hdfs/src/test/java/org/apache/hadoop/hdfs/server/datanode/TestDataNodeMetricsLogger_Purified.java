package org.apache.hadoop.hdfs.server.datanode;

import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_DATANODE_METRICS_LOGGER_PERIOD_SECONDS_KEY;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.CommonConfigurationKeys;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.hdfs.DFSConfigKeys;
import org.apache.hadoop.hdfs.HdfsConfiguration;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.hdfs.server.namenode.PatternMatchingAppender;
import org.apache.hadoop.metrics2.util.MBeans;
import org.apache.hadoop.test.GenericTestUtils;
import org.apache.log4j.Appender;
import org.apache.log4j.AsyncAppender;
import org.junit.After;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import java.util.function.Supplier;

public class TestDataNodeMetricsLogger_Purified {

    static final Logger LOG = LoggerFactory.getLogger(TestDataNodeMetricsLogger.class);

    private static final String DATA_DIR = MiniDFSCluster.getBaseDirectory() + "data";

    private final static InetSocketAddress NN_ADDR = new InetSocketAddress("localhost", 5020);

    private DataNode dn;

    static final Random random = new Random(System.currentTimeMillis());

    @Rule
    public Timeout timeout = new Timeout(300000);

    public void startDNForTest(boolean enableMetricsLogging) throws IOException {
        Configuration conf = new HdfsConfiguration();
        conf.set(DFSConfigKeys.DFS_DATANODE_DATA_DIR_KEY, DATA_DIR);
        conf.set(DFSConfigKeys.DFS_DATANODE_ADDRESS_KEY, "0.0.0.0:0");
        conf.set(DFSConfigKeys.DFS_DATANODE_HTTP_ADDRESS_KEY, "0.0.0.0:0");
        conf.set(DFSConfigKeys.DFS_DATANODE_IPC_ADDRESS_KEY, "0.0.0.0:0");
        conf.setInt(CommonConfigurationKeys.IPC_CLIENT_CONNECT_MAX_RETRIES_KEY, 0);
        conf.setInt(DFS_DATANODE_METRICS_LOGGER_PERIOD_SECONDS_KEY, enableMetricsLogging ? 1 : 0);
        dn = InternalDataNodeTestUtils.startDNWithMockNN(conf, NN_ADDR, DATA_DIR);
    }

    @After
    public void tearDown() throws IOException {
        if (dn != null) {
            try {
                dn.shutdown();
            } catch (Exception e) {
                LOG.error("Cannot close: ", e);
            } finally {
                File dir = new File(DATA_DIR);
                if (dir.exists())
                    Assert.assertTrue("Cannot delete data-node dirs", FileUtil.fullyDelete(dir));
            }
        }
        dn = null;
    }

    private void addAppender(org.apache.log4j.Logger logger, Appender appender) {
        @SuppressWarnings("unchecked")
        List<Appender> appenders = Collections.list(logger.getAllAppenders());
        ((AsyncAppender) appenders.get(0)).addAppender(appender);
    }

    public interface TestFakeMetricMXBean {

        int getFakeMetric();
    }

    public static class TestFakeMetric implements TestFakeMetricMXBean {

        @Override
        public int getFakeMetric() {
            return 0;
        }
    }

    @Test
    public void testMetricsLoggerOnByDefault_1() throws IOException {
        assertNotNull(dn);
    }

    @Test
    public void testMetricsLoggerOnByDefault_2() throws IOException {
        assertNotNull(dn.getMetricsLoggerTimer());
    }

    @Test
    public void testDisableMetricsLogger_1() throws IOException {
        assertNotNull(dn);
    }

    @Test
    public void testDisableMetricsLogger_2() throws IOException {
        assertNull(dn.getMetricsLoggerTimer());
    }
}
