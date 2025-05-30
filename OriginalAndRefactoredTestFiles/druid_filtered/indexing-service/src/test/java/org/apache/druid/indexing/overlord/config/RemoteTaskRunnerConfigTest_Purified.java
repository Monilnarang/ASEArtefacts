package org.apache.druid.indexing.overlord.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import org.apache.druid.guice.GuiceInjectors;
import org.apache.druid.guice.IndexingServiceModuleHelper;
import org.apache.druid.guice.JsonConfigProvider;
import org.apache.druid.guice.JsonConfigurator;
import org.apache.druid.jackson.DefaultObjectMapper;
import org.joda.time.Period;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class RemoteTaskRunnerConfigTest_Purified {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static final ObjectMapper MAPPER = new DefaultObjectMapper();

    private static final Period DEFAULT_TIMEOUT = Period.ZERO;

    private static final String DEFAULT_VERSION = "";

    private static final long DEFAULT_MAX_ZNODE = 10 * 1024;

    private static final int DEFAULT_PENDING_TASKS_RUNNER_NUM_THREADS = 5;

    private static final int DEFAULT_MAX_RETRIES_BEFORE_BLACKLIST = 5;

    private static final Period DEFAULT_TASK_BACKOFF = new Period("PT10M");

    private static final Period DEFAULT_BLACKLIST_CLEANUP_PERIOD = new Period("PT5M");

    private RemoteTaskRunnerConfig reflect(RemoteTaskRunnerConfig config) throws IOException {
        return MAPPER.readValue(MAPPER.writeValueAsString(config), RemoteTaskRunnerConfig.class);
    }

    private RemoteTaskRunnerConfig generateRemoteTaskRunnerConfig(Period taskAssignmentTimeout, Period taskCleanupTimeout, String minWorkerVersion, long maxZnodeBytes, Period taskShutdownLinkTimeout, int pendingTasksRunnerNumThreads, int maxRetriesBeforeBlacklist, Period taskBlackListBackoffTime, Period taskBlackListCleanupPeriod) {
        final Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("taskAssignmentTimeout", taskAssignmentTimeout);
        objectMap.put("taskCleanupTimeout", taskCleanupTimeout);
        objectMap.put("minWorkerVersion", minWorkerVersion);
        objectMap.put("maxZnodeBytes", maxZnodeBytes);
        objectMap.put("taskShutdownLinkTimeout", taskShutdownLinkTimeout);
        objectMap.put("pendingTasksRunnerNumThreads", pendingTasksRunnerNumThreads);
        objectMap.put("maxRetriesBeforeBlacklist", maxRetriesBeforeBlacklist);
        objectMap.put("workerBlackListBackoffTime", taskBlackListBackoffTime);
        objectMap.put("workerBlackListCleanupPeriod", taskBlackListCleanupPeriod);
        return MAPPER.convertValue(objectMap, RemoteTaskRunnerConfig.class);
    }

    @Test
    public void testEquals_1() throws Exception {
        Assert.assertEquals(reflect(generateRemoteTaskRunnerConfig(DEFAULT_TIMEOUT, DEFAULT_TIMEOUT, DEFAULT_VERSION, DEFAULT_MAX_ZNODE, DEFAULT_TIMEOUT, DEFAULT_PENDING_TASKS_RUNNER_NUM_THREADS, DEFAULT_MAX_RETRIES_BEFORE_BLACKLIST, DEFAULT_TASK_BACKOFF, DEFAULT_BLACKLIST_CLEANUP_PERIOD)), reflect(generateRemoteTaskRunnerConfig(DEFAULT_TIMEOUT, DEFAULT_TIMEOUT, DEFAULT_VERSION, DEFAULT_MAX_ZNODE, DEFAULT_TIMEOUT, DEFAULT_PENDING_TASKS_RUNNER_NUM_THREADS, DEFAULT_MAX_RETRIES_BEFORE_BLACKLIST, DEFAULT_TASK_BACKOFF, DEFAULT_BLACKLIST_CLEANUP_PERIOD)));
    }

    @Test
    public void testEquals_2_testMerged_2() throws Exception {
        final Period timeout = Period.years(999);
        final String version = "someVersion";
        final long max = 20 * 1024;
        final int pendingTasksRunnerNumThreads = 20;
        final int maxRetriesBeforeBlacklist = 1;
        final Period taskBlackListBackoffTime = new Period("PT1M");
        final Period taskBlackListCleanupPeriod = Period.years(10);
        Assert.assertEquals(reflect(generateRemoteTaskRunnerConfig(timeout, timeout, version, max, timeout, pendingTasksRunnerNumThreads, maxRetriesBeforeBlacklist, taskBlackListBackoffTime, taskBlackListCleanupPeriod)), reflect(generateRemoteTaskRunnerConfig(timeout, timeout, version, max, timeout, pendingTasksRunnerNumThreads, maxRetriesBeforeBlacklist, taskBlackListBackoffTime, taskBlackListCleanupPeriod)));
        Assert.assertNotEquals(reflect(generateRemoteTaskRunnerConfig(timeout, timeout, version, max, timeout, pendingTasksRunnerNumThreads, maxRetriesBeforeBlacklist, taskBlackListBackoffTime, taskBlackListCleanupPeriod)), reflect(generateRemoteTaskRunnerConfig(DEFAULT_TIMEOUT, timeout, version, max, timeout, pendingTasksRunnerNumThreads, maxRetriesBeforeBlacklist, taskBlackListBackoffTime, taskBlackListCleanupPeriod)));
        Assert.assertNotEquals(reflect(generateRemoteTaskRunnerConfig(timeout, timeout, version, max, timeout, pendingTasksRunnerNumThreads, maxRetriesBeforeBlacklist, taskBlackListBackoffTime, taskBlackListCleanupPeriod)), reflect(generateRemoteTaskRunnerConfig(timeout, DEFAULT_TIMEOUT, version, max, timeout, pendingTasksRunnerNumThreads, maxRetriesBeforeBlacklist, taskBlackListBackoffTime, taskBlackListCleanupPeriod)));
        Assert.assertNotEquals(reflect(generateRemoteTaskRunnerConfig(timeout, timeout, version, max, timeout, pendingTasksRunnerNumThreads, maxRetriesBeforeBlacklist, taskBlackListBackoffTime, taskBlackListCleanupPeriod)), reflect(generateRemoteTaskRunnerConfig(timeout, timeout, DEFAULT_VERSION, max, timeout, pendingTasksRunnerNumThreads, maxRetriesBeforeBlacklist, taskBlackListBackoffTime, taskBlackListCleanupPeriod)));
        Assert.assertNotEquals(reflect(generateRemoteTaskRunnerConfig(timeout, timeout, version, max, timeout, pendingTasksRunnerNumThreads, maxRetriesBeforeBlacklist, taskBlackListBackoffTime, taskBlackListCleanupPeriod)), reflect(generateRemoteTaskRunnerConfig(timeout, timeout, version, DEFAULT_MAX_ZNODE, timeout, pendingTasksRunnerNumThreads, maxRetriesBeforeBlacklist, taskBlackListBackoffTime, taskBlackListCleanupPeriod)));
        Assert.assertNotEquals(reflect(generateRemoteTaskRunnerConfig(timeout, timeout, version, max, timeout, pendingTasksRunnerNumThreads, maxRetriesBeforeBlacklist, taskBlackListBackoffTime, taskBlackListCleanupPeriod)), reflect(generateRemoteTaskRunnerConfig(timeout, timeout, version, max, DEFAULT_TIMEOUT, pendingTasksRunnerNumThreads, maxRetriesBeforeBlacklist, taskBlackListBackoffTime, taskBlackListCleanupPeriod)));
        Assert.assertNotEquals(reflect(generateRemoteTaskRunnerConfig(timeout, timeout, version, max, timeout, pendingTasksRunnerNumThreads, maxRetriesBeforeBlacklist, taskBlackListBackoffTime, taskBlackListCleanupPeriod)), reflect(generateRemoteTaskRunnerConfig(timeout, timeout, version, max, timeout, DEFAULT_PENDING_TASKS_RUNNER_NUM_THREADS, maxRetriesBeforeBlacklist, taskBlackListBackoffTime, taskBlackListCleanupPeriod)));
        Assert.assertNotEquals(reflect(generateRemoteTaskRunnerConfig(timeout, timeout, version, max, timeout, pendingTasksRunnerNumThreads, maxRetriesBeforeBlacklist, taskBlackListBackoffTime, taskBlackListCleanupPeriod)), reflect(generateRemoteTaskRunnerConfig(timeout, timeout, version, max, timeout, pendingTasksRunnerNumThreads, DEFAULT_MAX_RETRIES_BEFORE_BLACKLIST, taskBlackListBackoffTime, taskBlackListCleanupPeriod)));
        Assert.assertNotEquals(reflect(generateRemoteTaskRunnerConfig(timeout, timeout, version, max, timeout, pendingTasksRunnerNumThreads, maxRetriesBeforeBlacklist, taskBlackListBackoffTime, taskBlackListCleanupPeriod)), reflect(generateRemoteTaskRunnerConfig(timeout, timeout, version, max, timeout, pendingTasksRunnerNumThreads, maxRetriesBeforeBlacklist, DEFAULT_TASK_BACKOFF, taskBlackListCleanupPeriod)));
        Assert.assertNotEquals(reflect(generateRemoteTaskRunnerConfig(timeout, timeout, version, max, timeout, pendingTasksRunnerNumThreads, maxRetriesBeforeBlacklist, taskBlackListBackoffTime, taskBlackListCleanupPeriod)), reflect(generateRemoteTaskRunnerConfig(timeout, timeout, version, max, timeout, pendingTasksRunnerNumThreads, maxRetriesBeforeBlacklist, taskBlackListBackoffTime, DEFAULT_BLACKLIST_CLEANUP_PERIOD)));
    }

    @Test
    public void testHashCode_1() throws Exception {
        Assert.assertEquals(reflect(generateRemoteTaskRunnerConfig(DEFAULT_TIMEOUT, DEFAULT_TIMEOUT, DEFAULT_VERSION, DEFAULT_MAX_ZNODE, DEFAULT_TIMEOUT, DEFAULT_PENDING_TASKS_RUNNER_NUM_THREADS, DEFAULT_MAX_RETRIES_BEFORE_BLACKLIST, DEFAULT_TASK_BACKOFF, DEFAULT_BLACKLIST_CLEANUP_PERIOD)).hashCode(), reflect(generateRemoteTaskRunnerConfig(DEFAULT_TIMEOUT, DEFAULT_TIMEOUT, DEFAULT_VERSION, DEFAULT_MAX_ZNODE, DEFAULT_TIMEOUT, DEFAULT_PENDING_TASKS_RUNNER_NUM_THREADS, DEFAULT_MAX_RETRIES_BEFORE_BLACKLIST, DEFAULT_TASK_BACKOFF, DEFAULT_BLACKLIST_CLEANUP_PERIOD)).hashCode());
    }

    @Test
    public void testHashCode_2_testMerged_2() throws Exception {
        final Period timeout = Period.years(999);
        final String version = "someVersion";
        final long max = 20 * 1024;
        final int pendingTasksRunnerNumThreads = 20;
        final int maxRetriesBeforeBlacklist = 80;
        final Period taskBlackListBackoffTime = new Period("PT1M");
        final Period taskBlackListCleanupPeriod = Period.years(10);
        Assert.assertEquals(reflect(generateRemoteTaskRunnerConfig(timeout, timeout, version, max, timeout, pendingTasksRunnerNumThreads, maxRetriesBeforeBlacklist, taskBlackListBackoffTime, taskBlackListCleanupPeriod)).hashCode(), reflect(generateRemoteTaskRunnerConfig(timeout, timeout, version, max, timeout, pendingTasksRunnerNumThreads, maxRetriesBeforeBlacklist, taskBlackListBackoffTime, taskBlackListCleanupPeriod)).hashCode());
        Assert.assertNotEquals(reflect(generateRemoteTaskRunnerConfig(timeout, timeout, version, max, timeout, pendingTasksRunnerNumThreads, maxRetriesBeforeBlacklist, taskBlackListBackoffTime, taskBlackListCleanupPeriod)).hashCode(), reflect(generateRemoteTaskRunnerConfig(DEFAULT_TIMEOUT, timeout, version, max, timeout, pendingTasksRunnerNumThreads, maxRetriesBeforeBlacklist, taskBlackListBackoffTime, taskBlackListCleanupPeriod)).hashCode());
        Assert.assertNotEquals(reflect(generateRemoteTaskRunnerConfig(timeout, timeout, version, max, timeout, pendingTasksRunnerNumThreads, maxRetriesBeforeBlacklist, taskBlackListBackoffTime, taskBlackListCleanupPeriod)).hashCode(), reflect(generateRemoteTaskRunnerConfig(timeout, DEFAULT_TIMEOUT, version, max, timeout, pendingTasksRunnerNumThreads, maxRetriesBeforeBlacklist, taskBlackListBackoffTime, taskBlackListCleanupPeriod)).hashCode());
        Assert.assertNotEquals(reflect(generateRemoteTaskRunnerConfig(timeout, timeout, version, max, timeout, pendingTasksRunnerNumThreads, maxRetriesBeforeBlacklist, taskBlackListBackoffTime, taskBlackListCleanupPeriod)).hashCode(), reflect(generateRemoteTaskRunnerConfig(timeout, timeout, DEFAULT_VERSION, max, timeout, pendingTasksRunnerNumThreads, maxRetriesBeforeBlacklist, taskBlackListBackoffTime, taskBlackListCleanupPeriod)).hashCode());
        Assert.assertNotEquals(reflect(generateRemoteTaskRunnerConfig(timeout, timeout, version, max, timeout, pendingTasksRunnerNumThreads, maxRetriesBeforeBlacklist, taskBlackListBackoffTime, taskBlackListCleanupPeriod)).hashCode(), reflect(generateRemoteTaskRunnerConfig(timeout, timeout, version, DEFAULT_MAX_ZNODE, timeout, pendingTasksRunnerNumThreads, maxRetriesBeforeBlacklist, taskBlackListBackoffTime, taskBlackListCleanupPeriod)).hashCode());
        Assert.assertNotEquals(reflect(generateRemoteTaskRunnerConfig(timeout, timeout, version, max, timeout, pendingTasksRunnerNumThreads, maxRetriesBeforeBlacklist, taskBlackListBackoffTime, taskBlackListCleanupPeriod)).hashCode(), reflect(generateRemoteTaskRunnerConfig(timeout, timeout, version, max, DEFAULT_TIMEOUT, pendingTasksRunnerNumThreads, maxRetriesBeforeBlacklist, taskBlackListBackoffTime, taskBlackListCleanupPeriod)).hashCode());
        Assert.assertNotEquals(reflect(generateRemoteTaskRunnerConfig(timeout, timeout, version, max, timeout, pendingTasksRunnerNumThreads, maxRetriesBeforeBlacklist, taskBlackListBackoffTime, taskBlackListCleanupPeriod)).hashCode(), reflect(generateRemoteTaskRunnerConfig(timeout, timeout, version, max, timeout, DEFAULT_PENDING_TASKS_RUNNER_NUM_THREADS, maxRetriesBeforeBlacklist, taskBlackListBackoffTime, taskBlackListCleanupPeriod)).hashCode());
        Assert.assertNotEquals(reflect(generateRemoteTaskRunnerConfig(timeout, timeout, version, max, timeout, pendingTasksRunnerNumThreads, maxRetriesBeforeBlacklist, taskBlackListBackoffTime, taskBlackListCleanupPeriod)).hashCode(), reflect(generateRemoteTaskRunnerConfig(timeout, timeout, version, max, timeout, pendingTasksRunnerNumThreads, DEFAULT_MAX_RETRIES_BEFORE_BLACKLIST, taskBlackListBackoffTime, taskBlackListCleanupPeriod)).hashCode());
        Assert.assertNotEquals(reflect(generateRemoteTaskRunnerConfig(timeout, timeout, version, max, timeout, pendingTasksRunnerNumThreads, maxRetriesBeforeBlacklist, taskBlackListBackoffTime, taskBlackListCleanupPeriod)).hashCode(), reflect(generateRemoteTaskRunnerConfig(timeout, timeout, version, max, timeout, pendingTasksRunnerNumThreads, maxRetriesBeforeBlacklist, DEFAULT_TASK_BACKOFF, taskBlackListCleanupPeriod)).hashCode());
        Assert.assertNotEquals(reflect(generateRemoteTaskRunnerConfig(timeout, timeout, version, max, timeout, pendingTasksRunnerNumThreads, maxRetriesBeforeBlacklist, taskBlackListBackoffTime, taskBlackListCleanupPeriod)).hashCode(), reflect(generateRemoteTaskRunnerConfig(timeout, timeout, version, max, timeout, pendingTasksRunnerNumThreads, maxRetriesBeforeBlacklist, taskBlackListBackoffTime, DEFAULT_BLACKLIST_CLEANUP_PERIOD)).hashCode());
    }
}
