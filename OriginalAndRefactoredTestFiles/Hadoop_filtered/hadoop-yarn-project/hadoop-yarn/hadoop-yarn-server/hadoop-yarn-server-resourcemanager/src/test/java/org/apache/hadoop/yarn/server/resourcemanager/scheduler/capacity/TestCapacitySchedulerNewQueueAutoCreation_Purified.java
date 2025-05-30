package org.apache.hadoop.yarn.server.resourcemanager.scheduler.capacity;

import org.apache.hadoop.yarn.api.records.QueueState;
import org.apache.hadoop.test.GenericTestUtils;
import org.apache.hadoop.util.Time;
import org.apache.hadoop.yarn.api.records.ApplicationAttemptId;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.exceptions.YarnException;
import org.apache.hadoop.yarn.server.resourcemanager.MockNM;
import org.apache.hadoop.yarn.server.resourcemanager.MockRM;
import org.apache.hadoop.yarn.server.resourcemanager.nodelabels.NullRMNodeLabelsManager;
import org.apache.hadoop.yarn.server.resourcemanager.nodelabels.RMNodeLabelsManager;
import org.apache.hadoop.yarn.server.resourcemanager.rmapp.RMAppState;
import org.apache.hadoop.yarn.server.resourcemanager.rmapp.attempt.RMAppAttemptState;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.ResourceScheduler;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.SchedulerDynamicEditException;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.event.AppAddedSchedulerEvent;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.event.AppAttemptAddedSchedulerEvent;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.event.SchedulerEvent;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.event.AppAttemptRemovedSchedulerEvent;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.event.AppRemovedSchedulerEvent;
import org.apache.hadoop.yarn.server.utils.BuilderUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Set;
import java.util.HashSet;
import java.io.IOException;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assume.assumeThat;

public class TestCapacitySchedulerNewQueueAutoCreation_Purified extends TestCapacitySchedulerAutoCreatedQueueBase {

    private static final Logger LOG = LoggerFactory.getLogger(org.apache.hadoop.yarn.server.resourcemanager.scheduler.capacity.TestCapacitySchedulerAutoCreatedQueueBase.class);

    public static final int GB = 1024;

    public static final int MAX_MEMORY = 1200;

    private MockRM mockRM = null;

    private CapacityScheduler cs;

    private CapacitySchedulerConfiguration csConf;

    private CapacitySchedulerQueueManager autoQueueHandler;

    private AutoCreatedQueueDeletionPolicy policy = new AutoCreatedQueueDeletionPolicy();

    public CapacityScheduler getCs() {
        return cs;
    }

    public AutoCreatedQueueDeletionPolicy getPolicy() {
        return policy;
    }

    @Before
    public void setUp() throws Exception {
        csConf = new CapacitySchedulerConfiguration();
        csConf.setClass(YarnConfiguration.RM_SCHEDULER, CapacityScheduler.class, ResourceScheduler.class);
        csConf.setQueues("root", new String[] { "a", "b" });
        csConf.setNonLabeledQueueWeight("root", 1f);
        csConf.setNonLabeledQueueWeight("root.a", 1f);
        csConf.setNonLabeledQueueWeight("root.b", 1f);
        csConf.setQueues("root.a", new String[] { "a1" });
        csConf.setNonLabeledQueueWeight("root.a.a1", 1f);
        csConf.setAutoQueueCreationV2Enabled("root", true);
        csConf.setAutoQueueCreationV2Enabled("root.a", true);
        csConf.setAutoQueueCreationV2Enabled("root.e", true);
        csConf.setAutoQueueCreationV2Enabled(PARENT_QUEUE, true);
        csConf.setAutoExpiredDeletionTime(1);
    }

    @After
    public void tearDown() {
        if (mockRM != null) {
            mockRM.stop();
        }
    }

    protected void startScheduler() throws Exception {
        RMNodeLabelsManager mgr = new NullRMNodeLabelsManager();
        mgr.init(csConf);
        mockRM = new MockRM(csConf) {

            protected RMNodeLabelsManager createNodeLabelManager() {
                return mgr;
            }
        };
        cs = (CapacityScheduler) mockRM.getResourceScheduler();
        cs.updatePlacementRules();
        policy.init(cs.getConfiguration(), cs.getRMContext(), cs);
        mockRM.start();
        cs.start();
        autoQueueHandler = cs.getCapacitySchedulerQueueManager();
        mockRM.registerNode("h1:1234", MAX_MEMORY * GB);
    }

    private void createBasicQueueStructureAndValidate() throws Exception {
        MockNM nm1 = mockRM.registerNode("h1:1234", 1200 * GB);
        createQueue("root.c-auto");
        CSQueue c = cs.getQueue("root.c-auto");
        Assert.assertEquals(1 / 3f, c.getAbsoluteCapacity(), 1e-6);
        Assert.assertEquals(1f, c.getQueueCapacities().getWeight(), 1e-6);
        Assert.assertEquals(400 * GB, c.getQueueResourceQuotas().getEffectiveMinResource().getMemorySize());
        Assert.assertEquals(((LeafQueue) c).getUserLimitFactor(), -1, 1e-6);
        Assert.assertEquals(((LeafQueue) c).getMaxAMResourcePerQueuePercent(), 1, 1e-6);
        createQueue("root.d-auto");
        CSQueue d = cs.getQueue("root.d-auto");
        Assert.assertEquals(1 / 4f, d.getAbsoluteCapacity(), 1e-6);
        Assert.assertEquals(1f, d.getQueueCapacities().getWeight(), 1e-6);
        Assert.assertEquals(300 * GB, d.getQueueResourceQuotas().getEffectiveMinResource().getMemorySize());
        Assert.assertEquals(1 / 4f, c.getAbsoluteCapacity(), 1e-6);
        Assert.assertEquals(1f, c.getQueueCapacities().getWeight(), 1e-6);
        Assert.assertEquals(300 * GB, c.getQueueResourceQuotas().getEffectiveMinResource().getMemorySize());
        createQueue("root.a.a2-auto");
        CSQueue a2 = cs.getQueue("root.a.a2-auto");
        Assert.assertEquals(1 / 8f, a2.getAbsoluteCapacity(), 1e-6);
        Assert.assertEquals(1f, a2.getQueueCapacities().getWeight(), 1e-6);
        Assert.assertEquals(150 * GB, a2.getQueueResourceQuotas().getEffectiveMinResource().getMemorySize());
        createQueue("root.e-auto.e1-auto");
        CSQueue e = cs.getQueue("root.e-auto");
        Assert.assertEquals(1 / 5f, e.getAbsoluteCapacity(), 1e-6);
        Assert.assertEquals(1f, e.getQueueCapacities().getWeight(), 1e-6);
        Assert.assertEquals(240 * GB, e.getQueueResourceQuotas().getEffectiveMinResource().getMemorySize());
        CSQueue e1 = cs.getQueue("root.e-auto.e1-auto");
        Assert.assertEquals(1 / 5f, e1.getAbsoluteCapacity(), 1e-6);
        Assert.assertEquals(1f, e1.getQueueCapacities().getWeight(), 1e-6);
        Assert.assertEquals(240 * GB, e1.getQueueResourceQuotas().getEffectiveMinResource().getMemorySize());
    }

    protected AbstractLeafQueue createQueue(String queuePath) throws YarnException, IOException {
        return autoQueueHandler.createQueue(new QueuePath(queuePath));
    }

    private void assertQueueMinResource(CSQueue queue, float expected) {
        Assert.assertEquals(Math.round(expected * GB), queue.getQueueResourceQuotas().getEffectiveMinResource().getMemorySize(), 1e-6);
    }

    @Test
    public void testAutoCreateQueueIfFirstExistingParentQueueIsNotStatic_1() throws Exception {
        Assert.assertNotNull(cs.getQueue("root.a.a-parent-auto"));
    }

    @Test
    public void testAutoCreateQueueIfFirstExistingParentQueueIsNotStatic_2() throws Exception {
        CSQueue a2Leaf = cs.getQueue("a2-leaf-auto");
        Assert.assertEquals("root.a.a-parent-auto", a2Leaf.getParent().getQueuePath());
    }
}
