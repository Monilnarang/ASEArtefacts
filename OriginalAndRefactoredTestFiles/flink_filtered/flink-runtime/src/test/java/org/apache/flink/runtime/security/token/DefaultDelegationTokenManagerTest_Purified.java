package org.apache.flink.runtime.security.token;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.core.security.token.DelegationTokenProvider;
import org.apache.flink.core.security.token.DelegationTokenReceiver;
import org.apache.flink.core.testutils.ManuallyTriggeredScheduledExecutorService;
import org.apache.flink.util.concurrent.ManuallyTriggeredScheduledExecutor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Clock;
import java.time.ZoneId;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import static java.time.Instant.ofEpochMilli;
import static org.apache.flink.configuration.ConfigurationUtils.getBooleanConfigOption;
import static org.apache.flink.configuration.SecurityOptions.DELEGATION_TOKENS_RENEWAL_TIME_RATIO;
import static org.apache.flink.core.security.token.DelegationTokenProvider.CONFIG_PREFIX;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DefaultDelegationTokenManagerTest_Purified {

    @BeforeEach
    public void beforeEach() {
        ExceptionThrowingDelegationTokenProvider.reset();
        ExceptionThrowingDelegationTokenReceiver.reset();
    }

    @AfterEach
    public void afterEach() {
        ExceptionThrowingDelegationTokenProvider.reset();
        ExceptionThrowingDelegationTokenReceiver.reset();
    }

    @Test
    public void testAllProvidersLoaded_1_testMerged_1() {
        Configuration configuration = new Configuration();
        configuration.set(getBooleanConfigOption(CONFIG_PREFIX + ".throw.enabled"), false);
        DefaultDelegationTokenManager delegationTokenManager = new DefaultDelegationTokenManager(configuration, null, null, null);
        assertEquals(3, delegationTokenManager.delegationTokenProviders.size());
        assertTrue(delegationTokenManager.isProviderLoaded("hadoopfs"));
        assertTrue(delegationTokenManager.isReceiverLoaded("hadoopfs"));
        assertTrue(delegationTokenManager.isProviderLoaded("hbase"));
        assertTrue(delegationTokenManager.isReceiverLoaded("hbase"));
        assertTrue(delegationTokenManager.isProviderLoaded("test"));
        assertTrue(delegationTokenManager.isReceiverLoaded("test"));
        assertFalse(delegationTokenManager.isProviderLoaded("throw"));
        assertFalse(delegationTokenManager.isReceiverLoaded("throw"));
    }

    @Test
    public void testAllProvidersLoaded_8() {
        assertTrue(ExceptionThrowingDelegationTokenProvider.constructed.get());
    }

    @Test
    public void testAllProvidersLoaded_9() {
        assertTrue(ExceptionThrowingDelegationTokenReceiver.constructed.get());
    }
}
