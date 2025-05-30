package org.apache.hadoop.yarn.server.resourcemanager.scheduler.capacity;

import org.apache.hadoop.thirdparty.com.google.common.collect.ImmutableList;
import org.junit.Assert;
import org.junit.Test;
import java.util.List;

public class TestQueuePath_Purified {

    private static final String TEST_QUEUE = "root.level_1.level_2.level_3";

    @Test
    public void testCreation_1_testMerged_1() {
        QueuePath queuePath = new QueuePath(TEST_QUEUE);
        Assert.assertEquals(TEST_QUEUE, queuePath.getFullPath());
        Assert.assertEquals("root.level_1.level_2", queuePath.getParent());
        Assert.assertEquals("level_3", queuePath.getLeafName());
        QueuePath appendedPath = queuePath.createNewLeaf("level_4");
        Assert.assertEquals(TEST_QUEUE + CapacitySchedulerConfiguration.DOT + "level_4", appendedPath.getFullPath());
        Assert.assertEquals("root.level_1.level_2.level_3", appendedPath.getParent());
        Assert.assertEquals("level_4", appendedPath.getLeafName());
    }

    @Test
    public void testCreation_4() {
        QueuePath rootPath = new QueuePath(CapacitySchedulerConfiguration.ROOT);
        Assert.assertNull(rootPath.getParent());
    }

    @Test
    public void testEmptyPart_1() {
        QueuePath queuePathWithEmptyPart = new QueuePath("root..level_2");
        Assert.assertTrue(queuePathWithEmptyPart.hasEmptyPart());
    }

    @Test
    public void testEmptyPart_2() {
        QueuePath queuePathWithoutEmptyPart = new QueuePath(TEST_QUEUE);
        Assert.assertFalse(queuePathWithoutEmptyPart.hasEmptyPart());
    }

    @Test
    public void testEquals_1_testMerged_1() {
        QueuePath queuePath = new QueuePath(TEST_QUEUE);
        QueuePath queuePathSame = new QueuePath(TEST_QUEUE);
        Assert.assertEquals(queuePath, queuePathSame);
        Assert.assertNotEquals(null, queuePath);
    }

    @Test
    public void testEquals_2() {
        QueuePath empty = new QueuePath("");
        QueuePath emptySame = new QueuePath("");
        Assert.assertEquals(empty, emptySame);
    }
}
