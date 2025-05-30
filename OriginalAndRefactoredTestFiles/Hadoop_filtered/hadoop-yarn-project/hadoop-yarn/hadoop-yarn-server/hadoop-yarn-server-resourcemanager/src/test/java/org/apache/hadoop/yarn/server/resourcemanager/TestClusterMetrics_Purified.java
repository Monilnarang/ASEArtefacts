package org.apache.hadoop.yarn.server.resourcemanager;

import org.apache.hadoop.metrics2.MetricsSystem;
import org.apache.hadoop.metrics2.lib.DefaultMetricsSystem;
import org.apache.hadoop.test.GenericTestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Assert;
import org.junit.Test;
import java.util.function.Supplier;

public class TestClusterMetrics_Purified {

    private ClusterMetrics metrics;

    @Before
    public void setup() {
        DefaultMetricsSystem.initialize("ResourceManager");
        metrics = ClusterMetrics.getMetrics();
    }

    @After
    public void tearDown() {
        ClusterMetrics.destroy();
        MetricsSystem ms = DefaultMetricsSystem.instance();
        if (ms.getSource("ClusterMetrics") != null) {
            DefaultMetricsSystem.shutdown();
        }
    }

    @Test
    public void testAmMetrics_1() throws Exception {
        assert (metrics != null);
        Assert.assertTrue(!metrics.aMLaunchDelay.changed());
    }

    @Test
    public void testAmMetrics_2() throws Exception {
        assert (metrics != null);
        Assert.assertTrue(!metrics.aMRegisterDelay.changed());
    }

    @Test
    public void testAmMetrics_3() throws Exception {
        assert (metrics != null);
        Assert.assertTrue(!metrics.getAMContainerAllocationDelay().changed());
    }

    @Test
    public void testAmMetrics_4_testMerged_4() throws Exception {
        assert (metrics != null);
        metrics.addAMLaunchDelay(1);
        metrics.addAMRegisterDelay(1);
        metrics.addAMContainerAllocationDelay(1);
        Assert.assertTrue(metrics.aMLaunchDelay.changed());
        Assert.assertTrue(metrics.aMRegisterDelay.changed());
        Assert.assertTrue(metrics.getAMContainerAllocationDelay().changed());
    }
}
