package org.apache.hadoop.yarn.server.resourcemanager;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.metrics2.lib.DefaultMetricsSystem;
import org.apache.hadoop.security.UserGroupInformation;
import org.apache.hadoop.yarn.conf.HAUtil;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.QueueMetrics;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import static junit.framework.TestCase.assertNotNull;

public class TestRMHAMetrics_Purified {

    private Configuration configuration;

    private static final String RM1_ADDRESS = "1.1.1.1:1";

    private static final String RM1_NODE_ID = "rm1";

    private static final String RM2_ADDRESS = "0.0.0.0:0";

    private static final String RM2_NODE_ID = "rm2";

    @Before
    public void setUp() throws Exception {
        configuration = new Configuration();
        UserGroupInformation.setConfiguration(configuration);
        configuration.setBoolean(YarnConfiguration.RM_HA_ENABLED, true);
        configuration.set(YarnConfiguration.RM_HA_IDS, RM1_NODE_ID + "," + RM2_NODE_ID);
        for (String confKey : YarnConfiguration.getServiceAddressConfKeys(configuration)) {
            configuration.set(HAUtil.addSuffix(confKey, RM1_NODE_ID), RM1_ADDRESS);
            configuration.set(HAUtil.addSuffix(confKey, RM2_NODE_ID), RM2_ADDRESS);
        }
        ClusterMetrics.destroy();
        QueueMetrics.clearQueueMetrics();
        DefaultMetricsSystem.shutdown();
    }

    @Test(timeout = 300000)
    public void testMetricsAfterTransitionToStandby_1_testMerged_1() throws Exception {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName mxbeanName = new ObjectName("Hadoop:service=ResourceManager,name=RMInfo");
        Assert.assertEquals("initializing", (String) mbs.getAttribute(mxbeanName, "State"));
        Assert.assertEquals("standby", (String) mbs.getAttribute(mxbeanName, "State"));
        Assert.assertEquals("active", (String) mbs.getAttribute(mxbeanName, "State"));
    }

    @Test(timeout = 300000)
    public void testMetricsAfterTransitionToStandby_5() throws Exception {
        assertNotNull(DefaultMetricsSystem.instance().getSource("JvmMetrics"));
    }

    @Test(timeout = 300000)
    public void testMetricsAfterTransitionToStandby_6() throws Exception {
        assertNotNull(DefaultMetricsSystem.instance().getSource("UgiMetrics"));
    }
}
