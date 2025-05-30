package org.apache.druid.frame.util;

import com.google.common.collect.ImmutableSet;
import org.junit.Assert;
import org.junit.Test;

public class DurableStorageUtilsTest_Purified {

    private static final String CONTROLLER_ID = "controller_id_1";

    private static final String TASK_ID = "task_id_1";

    private static final int WORKER_NUMBER = 2;

    private static final int STAGE_NUMBER = 1;

    private static final int PARTITION_NUMBER = 3;

    @Test
    public void getNextDirNameWithPrefixFromPath_1() {
        Assert.assertEquals("", DurableStorageUtils.getNextDirNameWithPrefixFromPath("/123/123"));
    }

    @Test
    public void getNextDirNameWithPrefixFromPath_2() {
        Assert.assertEquals("123", DurableStorageUtils.getNextDirNameWithPrefixFromPath("123"));
    }

    @Test
    public void getNextDirNameWithPrefixFromPath_3() {
        Assert.assertEquals("controller_query_123", DurableStorageUtils.getNextDirNameWithPrefixFromPath("controller_query_123/123"));
    }

    @Test
    public void getNextDirNameWithPrefixFromPath_4() {
        Assert.assertEquals("", DurableStorageUtils.getNextDirNameWithPrefixFromPath(""));
    }

    @Test
    public void getNextDirNameWithPrefixFromPath_5() {
        Assert.assertNull(DurableStorageUtils.getNextDirNameWithPrefixFromPath(null));
    }

    @Test
    public void isQueryResultFileActive_1() {
        Assert.assertTrue(DurableStorageUtils.isQueryResultFileActive(DurableStorageUtils.QUERY_RESULTS_DIR + "/123/result", ImmutableSet.of("123")));
    }

    @Test
    public void isQueryResultFileActive_2() {
        Assert.assertFalse(DurableStorageUtils.isQueryResultFileActive(DurableStorageUtils.QUERY_RESULTS_DIR + "/123/result", ImmutableSet.of("")));
    }

    @Test
    public void isQueryResultFileActive_3() {
        Assert.assertFalse(DurableStorageUtils.isQueryResultFileActive(DurableStorageUtils.QUERY_RESULTS_DIR + "/", ImmutableSet.of("123")));
    }

    @Test
    public void isQueryResultFileActive_4() {
        Assert.assertFalse(DurableStorageUtils.isQueryResultFileActive(null, ImmutableSet.of("123")));
    }

    @Test
    public void isQueryResultFileActive_5() {
        Assert.assertFalse(DurableStorageUtils.isQueryResultFileActive(DurableStorageUtils.QUERY_RESULTS_DIR, ImmutableSet.of("123")));
    }
}
