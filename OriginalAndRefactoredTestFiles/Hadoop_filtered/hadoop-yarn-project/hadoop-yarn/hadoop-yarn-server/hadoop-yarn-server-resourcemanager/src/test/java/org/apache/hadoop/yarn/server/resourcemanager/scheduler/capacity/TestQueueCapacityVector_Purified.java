package org.apache.hadoop.yarn.server.resourcemanager.scheduler.capacity;

import org.apache.hadoop.util.Lists;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.capacity.QueueCapacityVector.ResourceUnitCapacityType;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.capacity.QueueCapacityVector.QueueCapacityVectorEntry;
import org.apache.hadoop.yarn.util.resource.ResourceUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.util.List;
import static org.apache.hadoop.yarn.api.records.ResourceInformation.MEMORY_URI;
import static org.apache.hadoop.yarn.api.records.ResourceInformation.VCORES_URI;
import static org.apache.hadoop.yarn.server.resourcemanager.scheduler.capacity.CSQueueUtils.EPSILON;

public class TestQueueCapacityVector_Purified {

    private static final String CUSTOM_RESOURCE = "custom";

    public static final String MIXED_CAPACITY_VECTOR_STRING = "[custom=3.0,memory-mb=10.0w,vcores=6.0%]";

    private final YarnConfiguration conf = new YarnConfiguration();

    @Before
    public void setUp() {
        conf.set(YarnConfiguration.RESOURCE_TYPES, CUSTOM_RESOURCE);
        ResourceUtils.resetResourceTypes(conf);
    }

    @Test
    public void testToString_1() {
        QueueCapacityVector capacityVector = QueueCapacityVector.newInstance();
        capacityVector.setResource(MEMORY_URI, 10, ResourceUnitCapacityType.WEIGHT);
        capacityVector.setResource(VCORES_URI, 6, ResourceUnitCapacityType.PERCENTAGE);
        capacityVector.setResource(CUSTOM_RESOURCE, 3, ResourceUnitCapacityType.ABSOLUTE);
        Assert.assertEquals(MIXED_CAPACITY_VECTOR_STRING, capacityVector.toString());
    }

    @Test
    public void testToString_2() {
        QueueCapacityVector emptyCapacityVector = new QueueCapacityVector();
        Assert.assertEquals("[]", emptyCapacityVector.toString());
    }
}
