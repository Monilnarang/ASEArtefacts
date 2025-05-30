package org.apache.druid.msq.indexing;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.apache.druid.error.DruidException;
import org.apache.druid.server.lookup.cache.LookupLoadingSpec;
import org.junit.Assert;
import org.junit.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class MSQWorkerTaskTest_Purified {

    private final String controllerTaskId = "ctr";

    private final String dataSource = "ds";

    private final int workerNumber = 1;

    private final ImmutableMap<String, Object> context = ImmutableMap.of("key", "val");

    private final int retryCount = 0;

    private final MSQWorkerTask msqWorkerTask = new MSQWorkerTask(controllerTaskId, dataSource, workerNumber, context, retryCount);

    @Test
    public void testEquals_1() {
        Assert.assertEquals(msqWorkerTask, new MSQWorkerTask(controllerTaskId, dataSource, workerNumber, context, retryCount));
    }

    @Test
    public void testEquals_2() {
        Assert.assertEquals(msqWorkerTask.getRetryTask(), new MSQWorkerTask(controllerTaskId, dataSource, workerNumber, context, retryCount + 1));
    }

    @Test
    public void testEquals_3() {
        Assert.assertNotEquals(msqWorkerTask, msqWorkerTask.getRetryTask());
    }

    @Test
    public void testGetter_1() {
        Assert.assertEquals(controllerTaskId, msqWorkerTask.getControllerTaskId());
    }

    @Test
    public void testGetter_2() {
        Assert.assertEquals(dataSource, msqWorkerTask.getDataSource());
    }

    @Test
    public void testGetter_3() {
        Assert.assertEquals(workerNumber, msqWorkerTask.getWorkerNumber());
    }

    @Test
    public void testGetter_4() {
        Assert.assertEquals(retryCount, msqWorkerTask.getRetryCount());
    }
}
