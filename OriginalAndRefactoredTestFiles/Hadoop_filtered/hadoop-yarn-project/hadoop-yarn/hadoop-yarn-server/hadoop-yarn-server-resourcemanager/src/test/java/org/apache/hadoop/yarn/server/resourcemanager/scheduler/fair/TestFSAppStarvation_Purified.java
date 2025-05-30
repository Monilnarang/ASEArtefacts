package org.apache.hadoop.yarn.server.resourcemanager.scheduler.fair;

import org.apache.hadoop.yarn.api.records.ApplicationAttemptId;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.server.resourcemanager.MockRM;
import org.apache.hadoop.yarn.server.resourcemanager.rmnode.RMNode;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.event.NodeUpdateSchedulerEvent;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.fair.allocationfile.AllocationFileQueue;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.fair.allocationfile.AllocationFileWriter;
import org.apache.hadoop.yarn.util.ControlledClock;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import java.io.File;

public class TestFSAppStarvation_Purified extends FairSchedulerTestBase {

    private static final File ALLOC_FILE = new File(TEST_DIR, "test-QUEUES");

    private final ControlledClock clock = new ControlledClock();

    private static final int NODE_CAPACITY_MULTIPLE = 4;

    private static final String[] QUEUES = { "no-preemption", "minshare", "fairshare.child", "drf.child" };

    private FairSchedulerWithMockPreemption.MockPreemptionThread preemptionThread;

    @Before
    public void setup() {
        createConfiguration();
        conf.set(YarnConfiguration.RM_SCHEDULER, FairSchedulerWithMockPreemption.class.getCanonicalName());
        conf.set(FairSchedulerConfiguration.ALLOCATION_FILE, ALLOC_FILE.getAbsolutePath());
        conf.setBoolean(FairSchedulerConfiguration.PREEMPTION, true);
        conf.setFloat(FairSchedulerConfiguration.PREEMPTION_THRESHOLD, 0f);
        conf.setLong(FairSchedulerConfiguration.UPDATE_INTERVAL_MS, Long.MAX_VALUE);
    }

    @After
    public void teardown() {
        ALLOC_FILE.delete();
        conf = null;
        if (resourceManager != null) {
            resourceManager.stop();
            resourceManager = null;
        }
    }

    private void verifyLeafQueueStarvation() {
        for (String q : QUEUES) {
            if (!q.equals("no-preemption")) {
                boolean isStarved = scheduler.getQueueManager().getLeafQueue(q, false).isStarved();
                assertTrue(isStarved);
            }
        }
    }

    private void setupClusterAndSubmitJobs() throws Exception {
        setupStarvedCluster();
        submitAppsToEachLeafQueue();
        sendEnoughNodeUpdatesToAssignFully();
        clock.tickMsec(10);
        scheduler.update();
    }

    private void setupStarvedCluster() {
        AllocationFileWriter.create().drfDefaultQueueSchedulingPolicy().addQueue(new AllocationFileQueue.Builder("default").build()).addQueue(new AllocationFileQueue.Builder("no-preemption").fairSharePreemptionThreshold(0).build()).addQueue(new AllocationFileQueue.Builder("minshare").fairSharePreemptionThreshold(0).minSharePreemptionTimeout(0).minResources("2048mb,2vcores").build()).addQueue(new AllocationFileQueue.Builder("fairshare").fairSharePreemptionThreshold(1).fairSharePreemptionTimeout(0).schedulingPolicy("fair").subQueue(new AllocationFileQueue.Builder("child").fairSharePreemptionThreshold(1).fairSharePreemptionTimeout(0).schedulingPolicy("fair").build()).build()).addQueue(new AllocationFileQueue.Builder("drf").fairSharePreemptionThreshold(1).fairSharePreemptionTimeout(0).schedulingPolicy("drf").subQueue(new AllocationFileQueue.Builder("child").fairSharePreemptionThreshold(1).fairSharePreemptionTimeout(0).schedulingPolicy("drf").build()).build()).writeToFile(ALLOC_FILE.getAbsolutePath());
        assertTrue("Allocation file does not exist, not running the test", ALLOC_FILE.exists());
        resourceManager = new MockRM(conf);
        scheduler = (FairScheduler) resourceManager.getResourceScheduler();
        scheduler.setClock(clock);
        resourceManager.start();
        preemptionThread = (FairSchedulerWithMockPreemption.MockPreemptionThread) scheduler.preemptionThread;
        addNode(NODE_CAPACITY_MULTIPLE * 1024, NODE_CAPACITY_MULTIPLE);
        addNode(NODE_CAPACITY_MULTIPLE * 1024, NODE_CAPACITY_MULTIPLE);
        ApplicationAttemptId app = createSchedulingRequest(1024, 1, "root.default", "default", 8);
        scheduler.update();
        sendEnoughNodeUpdatesToAssignFully();
        assertEquals(8, scheduler.getSchedulerApp(app).getLiveContainers().size());
    }

    private void submitAppsToEachLeafQueue() {
        for (String queue : QUEUES) {
            createSchedulingRequest(1024, 1, "root." + queue, "user", 1);
        }
        scheduler.update();
    }

    private void sendEnoughNodeUpdatesToAssignFully() {
        for (RMNode node : rmNodes) {
            NodeUpdateSchedulerEvent nodeUpdateSchedulerEvent = new NodeUpdateSchedulerEvent(node);
            for (int i = 0; i < NODE_CAPACITY_MULTIPLE; i++) {
                scheduler.handle(nodeUpdateSchedulerEvent);
            }
        }
    }

    @Test
    public void testClusterUtilizationThreshold_1() throws Exception {
        assertNotNull("FSContext does not have an FSStarvedApps instance", scheduler.getContext().getStarvedApps());
    }

    @Test
    public void testClusterUtilizationThreshold_2() throws Exception {
        assertEquals("Found starved apps when preemption threshold is over 100%", 0, preemptionThread.totalAppsAdded());
    }
}
