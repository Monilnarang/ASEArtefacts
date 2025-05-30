package org.apache.hadoop.util;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.hadoop.conf.Configuration;
import static java.lang.Thread.sleep;
import static org.apache.hadoop.fs.CommonConfigurationKeysPublic.SERVICE_SHUTDOWN_TIMEOUT;
import static org.apache.hadoop.fs.CommonConfigurationKeysPublic.SERVICE_SHUTDOWN_TIMEOUT_DEFAULT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestShutdownHookManager_Purified {

    static final Logger LOG = LoggerFactory.getLogger(TestShutdownHookManager.class.getName());

    private final ShutdownHookManager mgr = new ShutdownHookManager();

    private static final AtomicInteger INVOCATION_COUNT = new AtomicInteger();

    private class Hook implements Runnable {

        private final String name;

        private final long sleepTime;

        private final boolean expectFailure;

        private AssertionError assertion;

        private boolean invoked;

        private int invokedOrder;

        private boolean completed;

        private boolean interrupted;

        private long startTime;

        Hook(final String name, final long sleepTime, final boolean expectFailure) {
            this.name = name;
            this.sleepTime = sleepTime;
            this.expectFailure = expectFailure;
        }

        @Override
        public void run() {
            try {
                invoked = true;
                invokedOrder = INVOCATION_COUNT.incrementAndGet();
                startTime = System.currentTimeMillis();
                LOG.info("Starting shutdown of {} with sleep time of {}", name, sleepTime);
                if (sleepTime > 0) {
                    sleep(sleepTime);
                }
                LOG.info("Completed shutdown of {}", name);
                completed = true;
                if (expectFailure) {
                    assertion = new AssertionError("Expected a failure of " + name);
                }
            } catch (InterruptedException ex) {
                LOG.info("Shutdown {} interrupted exception", name, ex);
                interrupted = true;
                if (!expectFailure) {
                    assertion = new AssertionError("Timeout of " + name, ex);
                }
            }
            maybeThrowAssertion();
        }

        void maybeThrowAssertion() throws AssertionError {
            if (assertion != null) {
                throw assertion;
            }
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Hook{");
            sb.append("name='").append(name).append('\'');
            sb.append(", sleepTime=").append(sleepTime);
            sb.append(", expectFailure=").append(expectFailure);
            sb.append(", invoked=").append(invoked);
            sb.append(", invokedOrder=").append(invokedOrder);
            sb.append(", completed=").append(completed);
            sb.append(", interrupted=").append(interrupted);
            sb.append('}');
            return sb.toString();
        }
    }

    @Test
    public void testShutdownRemove_1() throws Throwable {
        assertNotNull("No ShutdownHookManager", mgr);
    }

    @Test
    public void testShutdownRemove_2() throws Throwable {
        assertEquals(0, mgr.getShutdownHooksInOrder().size());
    }

    @Test
    public void testShutdownRemove_3_testMerged_3() throws Throwable {
        Hook hook1 = new Hook("hook1", 0, false);
        Hook hook2 = new Hook("hook2", 0, false);
        mgr.addShutdownHook(hook1, 9);
        assertTrue("No hook1", mgr.hasShutdownHook(hook1));
        assertEquals(1, mgr.getShutdownHooksInOrder().size());
        assertFalse("Delete hook2 should not be allowed", mgr.removeShutdownHook(hook2));
        assertTrue("Can't delete hook1", mgr.removeShutdownHook(hook1));
    }

    @Test
    public void testShutdownRemove_7() throws Throwable {
        assertEquals(0, mgr.getShutdownHooksInOrder().size());
    }
}
