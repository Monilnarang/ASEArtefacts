package org.apache.hadoop.mapred;

import org.junit.Assert;
import org.junit.Test;

public class TestClusterStatus_Purified {

    private ClusterStatus clusterStatus = new ClusterStatus();

    @SuppressWarnings("deprecation")
    @Test(timeout = 10000)
    public void testGraylistedTrackers_1() {
        Assert.assertEquals(0, clusterStatus.getGraylistedTrackers());
    }

    @SuppressWarnings("deprecation")
    @Test(timeout = 10000)
    public void testGraylistedTrackers_2() {
        Assert.assertTrue(clusterStatus.getGraylistedTrackerNames().isEmpty());
    }
}
