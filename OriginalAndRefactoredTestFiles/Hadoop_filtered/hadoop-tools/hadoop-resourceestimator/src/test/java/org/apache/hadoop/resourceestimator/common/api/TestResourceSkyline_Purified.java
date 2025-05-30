package org.apache.hadoop.resourceestimator.common.api;

import java.util.TreeMap;
import org.apache.hadoop.yarn.api.records.Resource;
import org.apache.hadoop.yarn.server.resourcemanager.reservation.RLESparseResourceAllocation;
import org.apache.hadoop.yarn.server.resourcemanager.reservation.ReservationInterval;
import org.apache.hadoop.yarn.util.resource.DefaultResourceCalculator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestResourceSkyline_Purified {

    private ResourceSkyline resourceSkyline;

    private Resource resource1;

    private Resource resource2;

    private TreeMap<Long, Resource> resourceOverTime;

    private RLESparseResourceAllocation skylineList;

    @Before
    public final void setup() {
        resourceOverTime = new TreeMap<>();
        skylineList = new RLESparseResourceAllocation(resourceOverTime, new DefaultResourceCalculator());
        resource1 = Resource.newInstance(1024 * 100, 100);
        resource2 = Resource.newInstance(1024 * 200, 200);
    }

    @After
    public final void cleanUp() {
        resourceSkyline = null;
        resource1 = null;
        resource2 = null;
        resourceOverTime.clear();
        resourceOverTime = null;
        skylineList = null;
    }

    @Test
    public final void testGetJobId_1() {
        Assert.assertNull(resourceSkyline);
    }

    @Test
    public final void testGetJobId_2() {
        skylineList.addInterval(riAdd, resource1);
        skylineList.addInterval(riAdd, resource1);
        resourceSkyline = new ResourceSkyline("1", 1024.5, 0, 20, resource1, skylineList);
        Assert.assertEquals("1", resourceSkyline.getJobId());
    }

    @Test
    public final void testGetJobSubmissionTime_1() {
        Assert.assertNull(resourceSkyline);
    }

    @Test
    public final void testGetJobSubmissionTime_2() {
        skylineList.addInterval(riAdd, resource1);
        skylineList.addInterval(riAdd, resource1);
        resourceSkyline = new ResourceSkyline("1", 1024.5, 0, 20, resource1, skylineList);
        Assert.assertEquals(0, resourceSkyline.getJobSubmissionTime());
    }

    @Test
    public final void testGetJobFinishTime_1() {
        Assert.assertNull(resourceSkyline);
    }

    @Test
    public final void testGetJobFinishTime_2() {
        skylineList.addInterval(riAdd, resource1);
        skylineList.addInterval(riAdd, resource1);
        resourceSkyline = new ResourceSkyline("1", 1024.5, 0, 20, resource1, skylineList);
        Assert.assertEquals(20, resourceSkyline.getJobFinishTime());
    }
}
