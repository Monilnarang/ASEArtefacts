package org.apache.hadoop.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeoutException;
import org.apache.hadoop.test.GenericTestUtils;
import org.apache.hadoop.util.FakeTimer;
import org.junit.Before;
import org.junit.Test;
import java.util.function.Supplier;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.CommonConfigurationKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.assertj.core.api.Assertions.assertThat;

public class TestGroupsCaching_Purified {

    public static final Logger TESTLOG = LoggerFactory.getLogger(TestGroupsCaching.class);

    private static String[] myGroups = { "grp1", "grp2" };

    private Configuration conf;

    @Before
    public void setup() throws IOException {
        FakeGroupMapping.clearAll();
        ExceptionalGroupMapping.resetRequestCount();
        conf = new Configuration();
        conf.setClass(CommonConfigurationKeys.HADOOP_SECURITY_GROUP_MAPPING, FakeGroupMapping.class, ShellBasedUnixGroupsMapping.class);
    }

    public static class FakeGroupMapping extends ShellBasedUnixGroupsMapping {

        private static Set<String> allGroups = new LinkedHashSet<String>();

        private static Set<String> blackList = new HashSet<String>();

        private static int requestCount = 0;

        private static long getGroupsDelayMs = 0;

        private static boolean throwException;

        private static volatile CountDownLatch latch = null;

        @Override
        public Set<String> getGroupsSet(String user) throws IOException {
            TESTLOG.info("Getting groups for " + user);
            delayIfNecessary();
            requestCount++;
            if (throwException) {
                throw new IOException("For test");
            }
            if (blackList.contains(user)) {
                return Collections.emptySet();
            }
            return new LinkedHashSet<>(allGroups);
        }

        @Override
        public List<String> getGroups(String user) throws IOException {
            return new ArrayList<>(getGroupsSet(user));
        }

        private void delayIfNecessary() {
            if (latch != null) {
                try {
                    latch.await();
                    return;
                } catch (InterruptedException e) {
                }
            }
            if (getGroupsDelayMs > 0) {
                try {
                    Thread.sleep(getGroupsDelayMs);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        @Override
        public void cacheGroupsRefresh() throws IOException {
            TESTLOG.info("Cache is being refreshed.");
            clearBlackList();
            return;
        }

        public static void clearBlackList() throws IOException {
            TESTLOG.info("Clearing the blacklist");
            blackList.clear();
        }

        public static void clearAll() throws IOException {
            TESTLOG.info("Resetting FakeGroupMapping");
            blackList.clear();
            allGroups.clear();
            resetRequestCount();
            getGroupsDelayMs = 0;
            throwException = false;
            latch = null;
        }

        @Override
        public void cacheGroupsAdd(List<String> groups) throws IOException {
            TESTLOG.info("Adding " + groups + " to groups.");
            allGroups.addAll(groups);
        }

        public static void addToBlackList(String user) throws IOException {
            TESTLOG.info("Adding " + user + " to the blacklist");
            blackList.add(user);
        }

        public static int getRequestCount() {
            return requestCount;
        }

        public static void resetRequestCount() {
            requestCount = 0;
        }

        public static void setGetGroupsDelayMs(long delayMs) {
            getGroupsDelayMs = delayMs;
        }

        public static void setThrowException(boolean throwIfTrue) {
            throwException = throwIfTrue;
        }

        public static void pause() {
            latch = new CountDownLatch(1);
        }

        public static void resume() {
            if (latch != null) {
                latch.countDown();
            }
        }
    }

    public static class ExceptionalGroupMapping extends ShellBasedUnixGroupsMapping {

        private static int requestCount = 0;

        @Override
        public List<String> getGroups(String user) throws IOException {
            requestCount++;
            throw new IOException("For test");
        }

        @Override
        public Set<String> getGroupsSet(String user) throws IOException {
            requestCount++;
            throw new IOException("For test");
        }

        public static int getRequestCount() {
            return requestCount;
        }

        public static void resetRequestCount() {
            requestCount = 0;
        }
    }

    public static class FakeunPrivilegedGroupMapping extends FakeGroupMapping {

        private static boolean invoked = false;

        @Override
        public List<String> getGroups(String user) throws IOException {
            invoked = true;
            return super.getGroups(user);
        }
    }

    private void waitForGroupCounters(Groups groups, long expectedQueued, long expectedRunning, long expectedSuccess, long expectedExpection) throws InterruptedException {
        long[] expected = { expectedQueued, expectedRunning, expectedSuccess, expectedExpection };
        long[] actual = new long[expected.length];
        try {
            GenericTestUtils.waitFor(new Supplier<Boolean>() {

                @Override
                public Boolean get() {
                    actual[0] = groups.getBackgroundRefreshQueued();
                    actual[1] = groups.getBackgroundRefreshRunning();
                    actual[2] = groups.getBackgroundRefreshSuccess();
                    actual[3] = groups.getBackgroundRefreshException();
                    return Arrays.equals(actual, expected);
                }
            }, 20, 1000);
        } catch (TimeoutException e) {
            fail("Excepted group counter values are not reached in given time," + " expecting (Queued, Running, Success, Exception) : " + Arrays.toString(expected) + " but actual : " + Arrays.toString(actual));
        }
    }

    @Test
    public void testCachePreventsImplRequest_1_testMerged_1() throws Exception {
        FakeGroupMapping.clearBlackList();
        assertEquals(0, FakeGroupMapping.getRequestCount());
        assertEquals(1, FakeGroupMapping.getRequestCount());
    }

    @Test
    public void testCachePreventsImplRequest_2_testMerged_2() throws Exception {
        conf.setLong(CommonConfigurationKeys.HADOOP_SECURITY_GROUPS_NEGATIVE_CACHE_SECS, 0);
        Groups groups = new Groups(conf);
        groups.cacheGroupsAdd(Arrays.asList(myGroups));
        groups.refresh();
        assertTrue(groups.getGroups("me").size() == 2);
    }

    @Test
    public void testThreadBlockedWhenExpiredEntryExistsWithoutBackgroundRefresh_1() throws Exception {
        conf.setLong(CommonConfigurationKeys.HADOOP_SECURITY_GROUPS_CACHE_SECS, 1);
        conf.setBoolean(CommonConfigurationKeys.HADOOP_SECURITY_GROUPS_CACHE_BACKGROUND_RELOAD, false);
        FakeTimer timer = new FakeTimer();
        final Groups groups = new Groups(conf, timer);
        groups.cacheGroupsAdd(Arrays.asList(myGroups));
        groups.refresh();
        groups.getGroups("me");
        groups.cacheGroupsAdd(Arrays.asList("grp3"));
        assertThat(groups.getGroups("me").size()).isEqualTo(3);
    }

    @Test
    public void testThreadBlockedWhenExpiredEntryExistsWithoutBackgroundRefresh_2() throws Exception {
        FakeGroupMapping.clearBlackList();
        FakeGroupMapping.setGetGroupsDelayMs(100);
        int startingRequestCount = FakeGroupMapping.getRequestCount();
        assertEquals(startingRequestCount + 1, FakeGroupMapping.getRequestCount());
    }
}
