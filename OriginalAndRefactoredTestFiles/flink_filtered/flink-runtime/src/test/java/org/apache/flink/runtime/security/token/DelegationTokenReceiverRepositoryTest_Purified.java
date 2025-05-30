package org.apache.flink.runtime.security.token;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.SecurityOptions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.apache.flink.configuration.ConfigurationUtils.getBooleanConfigOption;
import static org.apache.flink.core.security.token.DelegationTokenProvider.CONFIG_PREFIX;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DelegationTokenReceiverRepositoryTest_Purified {

    @BeforeEach
    public void beforeEach() {
        ExceptionThrowingDelegationTokenReceiver.reset();
    }

    @AfterEach
    public void afterEach() {
        ExceptionThrowingDelegationTokenReceiver.reset();
    }

    @Test
    public void testAllReceiversLoaded_1_testMerged_1() {
        Configuration configuration = new Configuration();
        configuration.set(getBooleanConfigOption(CONFIG_PREFIX + ".throw.enabled"), false);
        DelegationTokenReceiverRepository delegationTokenReceiverRepository = new DelegationTokenReceiverRepository(configuration, null);
        assertEquals(3, delegationTokenReceiverRepository.delegationTokenReceivers.size());
        assertTrue(delegationTokenReceiverRepository.isReceiverLoaded("hadoopfs"));
        assertTrue(delegationTokenReceiverRepository.isReceiverLoaded("hbase"));
        assertTrue(delegationTokenReceiverRepository.isReceiverLoaded("test"));
        assertFalse(delegationTokenReceiverRepository.isReceiverLoaded("throw"));
    }

    @Test
    public void testAllReceiversLoaded_5() {
        assertTrue(ExceptionThrowingDelegationTokenReceiver.constructed.get());
    }

    @Test
    public void testDelegationTokenDisabled_1_testMerged_1() {
        Configuration configuration = new Configuration();
        configuration.set(SecurityOptions.DELEGATION_TOKENS_ENABLED, false);
        DelegationTokenReceiverRepository delegationTokenReceiverRepository = new DelegationTokenReceiverRepository(configuration, null);
        assertEquals(0, delegationTokenReceiverRepository.delegationTokenReceivers.size());
        assertFalse(delegationTokenReceiverRepository.isReceiverLoaded("hadoopfs"));
        assertFalse(delegationTokenReceiverRepository.isReceiverLoaded("hbase"));
        assertFalse(delegationTokenReceiverRepository.isReceiverLoaded("test"));
        assertFalse(delegationTokenReceiverRepository.isReceiverLoaded("throw"));
    }

    @Test
    public void testDelegationTokenDisabled_5() {
        assertFalse(ExceptionThrowingDelegationTokenReceiver.constructed.get());
    }
}
