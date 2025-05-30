package org.wildfly.extension.messaging.activemq;

import org.apache.activemq.artemis.api.config.ActiveMQDefaultConfiguration;
import org.apache.activemq.artemis.api.core.client.ActiveMQClient;
import org.hornetq.api.core.client.HornetQClient;
import org.jboss.as.subsystem.test.AbstractSubsystemTest;
import org.junit.Assert;
import org.junit.Test;
import org.wildfly.extension.messaging.activemq.ha.HAAttributes;
import org.wildfly.extension.messaging.activemq.jms.ConnectionFactoryAttributes;
import org.wildfly.extension.messaging.activemq.jms.legacy.LegacyConnectionFactoryDefinition;

public class AttributeDefaultsTest_Purified extends AbstractSubsystemTest {

    public AttributeDefaultsTest() {
        super(MessagingExtension.SUBSYSTEM_NAME, new MessagingExtension());
    }

    @Test
    public void testAttributeValues_1() {
        Assert.assertNotEquals(ServerDefinition.GLOBAL_MAX_DISK_USAGE.getName(), ServerDefinition.GLOBAL_MAX_DISK_USAGE.getDefaultValue().asInt(), ActiveMQDefaultConfiguration.getDefaultMaxDiskUsage());
    }

    @Test
    public void testAttributeValues_2() {
        Assert.assertNotEquals(ServerDefinition.GLOBAL_MAX_MEMORY_SIZE.getName(), ServerDefinition.GLOBAL_MAX_MEMORY_SIZE.getDefaultValue().asLong(), ActiveMQDefaultConfiguration.getDefaultMaxGlobalSizeAsPercentOfJvmMaxMemory(ActiveMQDefaultConfiguration.DEFAULT_GLOBAL_MAX_MEMORY_PERCENT));
    }

    @Test
    public void testAttributeValues_3() {
        Assert.assertNotEquals(ServerDefinition.JOURNAL_POOL_FILES.getName(), ServerDefinition.JOURNAL_POOL_FILES.getDefaultValue().asInt(), ActiveMQDefaultConfiguration.getDefaultJournalPoolFiles());
    }

    @Test
    public void testAttributeValues_4() {
        Assert.assertEquals(BridgeDefinition.INITIAL_CONNECT_ATTEMPTS.getName(), BridgeDefinition.INITIAL_CONNECT_ATTEMPTS.getDefaultValue().asInt(), ActiveMQDefaultConfiguration.getDefaultBridgeInitialConnectAttempts());
    }

    @Test
    public void testAttributeValues_5() {
        Assert.assertEquals(BridgeDefinition.RECONNECT_ATTEMPTS.getName(), BridgeDefinition.RECONNECT_ATTEMPTS.getDefaultValue().asInt(), ActiveMQDefaultConfiguration.getDefaultBridgeReconnectAttempts());
    }

    @Test
    public void testAttributeValues_6() {
        Assert.assertEquals(BridgeDefinition.RECONNECT_ATTEMPTS_ON_SAME_NODE.getName(), BridgeDefinition.RECONNECT_ATTEMPTS_ON_SAME_NODE.getDefaultValue().asInt(), ActiveMQDefaultConfiguration.getDefaultBridgeConnectSameNode());
    }

    @Test
    public void testAttributeValues_7() {
        Assert.assertEquals(BridgeDefinition.USE_DUPLICATE_DETECTION.getName(), BridgeDefinition.USE_DUPLICATE_DETECTION.getDefaultValue().asBoolean(), ActiveMQDefaultConfiguration.isDefaultBridgeDuplicateDetection());
    }

    @Test
    public void testAttributeValues_8() {
        Assert.assertEquals(ClusterConnectionDefinition.CHECK_PERIOD.getName(), ClusterConnectionDefinition.CHECK_PERIOD.getDefaultValue().asLong(), ActiveMQDefaultConfiguration.getDefaultClusterFailureCheckPeriod());
    }

    @Test
    public void testAttributeValues_9() {
        Assert.assertEquals(ClusterConnectionDefinition.CONNECTION_TTL.getName(), ClusterConnectionDefinition.CONNECTION_TTL.getDefaultValue().asLong(), ActiveMQDefaultConfiguration.getDefaultClusterConnectionTtl());
    }

    @Test
    public void testAttributeValues_10() {
        Assert.assertEquals(ClusterConnectionDefinition.INITIAL_CONNECT_ATTEMPTS.getName(), ClusterConnectionDefinition.INITIAL_CONNECT_ATTEMPTS.getDefaultValue().asInt(), ActiveMQDefaultConfiguration.getDefaultClusterInitialConnectAttempts());
    }

    @Test
    public void testAttributeValues_11() {
        Assert.assertEquals(ClusterConnectionDefinition.MAX_HOPS.getName(), ClusterConnectionDefinition.MAX_HOPS.getDefaultValue().asInt(), ActiveMQDefaultConfiguration.getDefaultClusterMaxHops());
    }

    @Test
    public void testAttributeValues_12() {
        Assert.assertEquals(ClusterConnectionDefinition.MAX_RETRY_INTERVAL.getName(), ClusterConnectionDefinition.MAX_RETRY_INTERVAL.getDefaultValue().asLong(), ActiveMQDefaultConfiguration.getDefaultClusterMaxRetryInterval());
    }

    @Test
    public void testAttributeValues_13() {
        Assert.assertEquals(ClusterConnectionDefinition.MESSAGE_LOAD_BALANCING_TYPE.getName(), ClusterConnectionDefinition.MESSAGE_LOAD_BALANCING_TYPE.getDefaultValue().asString(), ActiveMQDefaultConfiguration.getDefaultClusterMessageLoadBalancingType());
    }

    @Test
    public void testAttributeValues_14() {
        Assert.assertEquals(ClusterConnectionDefinition.NOTIFICATION_ATTEMPTS.getName(), ClusterConnectionDefinition.NOTIFICATION_ATTEMPTS.getDefaultValue().asInt(), ActiveMQDefaultConfiguration.getDefaultClusterNotificationAttempts());
    }

    @Test
    public void testAttributeValues_15() {
        Assert.assertEquals(ClusterConnectionDefinition.NOTIFICATION_INTERVAL.getName(), ClusterConnectionDefinition.NOTIFICATION_INTERVAL.getDefaultValue().asLong(), ActiveMQDefaultConfiguration.getDefaultClusterNotificationInterval());
    }

    @Test
    public void testAttributeValues_16() {
        Assert.assertEquals(ClusterConnectionDefinition.RECONNECT_ATTEMPTS.getName(), ClusterConnectionDefinition.RECONNECT_ATTEMPTS.getDefaultValue().asInt(), ActiveMQDefaultConfiguration.getDefaultClusterReconnectAttempts());
    }

    @Test
    public void testAttributeValues_17() {
        Assert.assertEquals(ClusterConnectionDefinition.RETRY_INTERVAL.getName(), ClusterConnectionDefinition.RETRY_INTERVAL.getDefaultValue().asLong(), ActiveMQDefaultConfiguration.getDefaultClusterRetryInterval());
    }

    @Test
    public void testAttributeValues_18() {
        Assert.assertEquals(ClusterConnectionDefinition.RETRY_INTERVAL_MULTIPLIER.getName(), ClusterConnectionDefinition.RETRY_INTERVAL_MULTIPLIER.getDefaultValue().asDouble(), ActiveMQDefaultConfiguration.getDefaultClusterRetryIntervalMultiplier(), 0);
    }

    @Test
    public void testAttributeValues_19() {
        Assert.assertEquals(CommonAttributes.BRIDGE_CONFIRMATION_WINDOW_SIZE.getName(), CommonAttributes.BRIDGE_CONFIRMATION_WINDOW_SIZE.getDefaultValue().asInt(), ActiveMQDefaultConfiguration.getDefaultBridgeConfirmationWindowSize());
    }

    @Test
    public void testAttributeValues_20() {
        Assert.assertEquals(CommonAttributes.CALL_TIMEOUT.getName(), CommonAttributes.CALL_TIMEOUT.getDefaultValue().asLong(), ActiveMQClient.DEFAULT_CALL_TIMEOUT);
    }

    @Test
    public void testAttributeValues_21() {
        Assert.assertEquals(CommonAttributes.CHECK_PERIOD.getName(), CommonAttributes.CHECK_PERIOD.getDefaultValue().asLong(), ActiveMQClient.DEFAULT_CLIENT_FAILURE_CHECK_PERIOD);
    }

    @Test
    public void testAttributeValues_22() {
        Assert.assertEquals(CommonAttributes.CONNECTION_TTL.getName(), CommonAttributes.CONNECTION_TTL.getDefaultValue().asLong(), ActiveMQClient.DEFAULT_CONNECTION_TTL);
    }

    @Test
    public void testAttributeValues_23() {
        Assert.assertEquals(CommonAttributes.HA.getName(), CommonAttributes.HA.getDefaultValue().asBoolean(), ActiveMQClient.DEFAULT_HA);
    }

    @Test
    public void testAttributeValues_24() {
        Assert.assertEquals(CommonAttributes.MAX_RETRY_INTERVAL.getName(), CommonAttributes.MAX_RETRY_INTERVAL.getDefaultValue().asLong(), ActiveMQClient.DEFAULT_MAX_RETRY_INTERVAL);
    }

    @Test
    public void testAttributeValues_25() {
        Assert.assertEquals(CommonAttributes.MIN_LARGE_MESSAGE_SIZE.getName(), CommonAttributes.MIN_LARGE_MESSAGE_SIZE.getDefaultValue().asLong(), ActiveMQClient.DEFAULT_MIN_LARGE_MESSAGE_SIZE);
    }

    @Test
    public void testAttributeValues_26() {
        Assert.assertEquals(CommonAttributes.RETRY_INTERVAL.getName(), CommonAttributes.RETRY_INTERVAL.getDefaultValue().asLong(), ActiveMQClient.DEFAULT_RETRY_INTERVAL);
    }

    @Test
    public void testAttributeValues_27() {
        Assert.assertEquals(CommonAttributes.RETRY_INTERVAL_MULTIPLIER.getName(), CommonAttributes.RETRY_INTERVAL_MULTIPLIER.getDefaultValue().asDouble(), ActiveMQClient.DEFAULT_RETRY_INTERVAL_MULTIPLIER, 0);
    }

    @Test
    public void testAttributeValues_28() {
        Assert.assertEquals(ConnectionFactoryAttributes.Common.AUTO_GROUP.getName(), ConnectionFactoryAttributes.Common.AUTO_GROUP.getDefaultValue().asBoolean(), ActiveMQClient.DEFAULT_AUTO_GROUP);
    }

    @Test
    public void testAttributeValues_29() {
        Assert.assertEquals(ConnectionFactoryAttributes.Common.BLOCK_ON_ACKNOWLEDGE.getName(), ConnectionFactoryAttributes.Common.BLOCK_ON_ACKNOWLEDGE.getDefaultValue().asBoolean(), ActiveMQClient.DEFAULT_BLOCK_ON_ACKNOWLEDGE);
    }

    @Test
    public void testAttributeValues_30() {
        Assert.assertEquals(ConnectionFactoryAttributes.Common.BLOCK_ON_DURABLE_SEND.getName(), ConnectionFactoryAttributes.Common.BLOCK_ON_DURABLE_SEND.getDefaultValue().asBoolean(), ActiveMQClient.DEFAULT_BLOCK_ON_DURABLE_SEND);
    }

    @Test
    public void testAttributeValues_31() {
        Assert.assertEquals(ConnectionFactoryAttributes.Common.BLOCK_ON_NON_DURABLE_SEND.getName(), ConnectionFactoryAttributes.Common.BLOCK_ON_NON_DURABLE_SEND.getDefaultValue().asBoolean(), ActiveMQClient.DEFAULT_BLOCK_ON_NON_DURABLE_SEND);
    }

    @Test
    public void testAttributeValues_32() {
        Assert.assertEquals(ConnectionFactoryAttributes.Common.CACHE_LARGE_MESSAGE_CLIENT.getName(), ConnectionFactoryAttributes.Common.CACHE_LARGE_MESSAGE_CLIENT.getDefaultValue().asBoolean(), ActiveMQClient.DEFAULT_CACHE_LARGE_MESSAGE_CLIENT);
    }

    @Test
    public void testAttributeValues_33() {
        Assert.assertEquals(ConnectionFactoryAttributes.Common.COMPRESS_LARGE_MESSAGES.getName(), ConnectionFactoryAttributes.Common.COMPRESS_LARGE_MESSAGES.getDefaultValue().asBoolean(), ActiveMQClient.DEFAULT_COMPRESS_LARGE_MESSAGES);
    }

    @Test
    public void testAttributeValues_34() {
        Assert.assertEquals(ConnectionFactoryAttributes.Common.CONFIRMATION_WINDOW_SIZE.getName(), ConnectionFactoryAttributes.Common.CONFIRMATION_WINDOW_SIZE.getDefaultValue().asInt(), ActiveMQClient.DEFAULT_CONFIRMATION_WINDOW_SIZE);
    }

    @Test
    public void testAttributeValues_35() {
        Assert.assertEquals(ConnectionFactoryAttributes.Common.CONNECTION_LOAD_BALANCING_CLASS_NAME.getName(), ConnectionFactoryAttributes.Common.CONNECTION_LOAD_BALANCING_CLASS_NAME.getDefaultValue().asString(), ActiveMQClient.DEFAULT_CONNECTION_LOAD_BALANCING_POLICY_CLASS_NAME);
    }

    @Test
    public void testAttributeValues_36() {
        Assert.assertEquals(ConnectionFactoryAttributes.Common.CONSUMER_MAX_RATE.getName(), ConnectionFactoryAttributes.Common.CONSUMER_MAX_RATE.getDefaultValue().asInt(), ActiveMQClient.DEFAULT_CONSUMER_MAX_RATE);
    }

    @Test
    public void testAttributeValues_37() {
        Assert.assertEquals(ConnectionFactoryAttributes.Common.CONSUMER_WINDOW_SIZE.getName(), ConnectionFactoryAttributes.Common.CONSUMER_WINDOW_SIZE.getDefaultValue().asInt(), ActiveMQClient.DEFAULT_CONSUMER_WINDOW_SIZE);
    }

    @Test
    public void testAttributeValues_38() {
        Assert.assertEquals(ConnectionFactoryAttributes.Common.DUPS_OK_BATCH_SIZE.getName(), ConnectionFactoryAttributes.Common.DUPS_OK_BATCH_SIZE.getDefaultValue().asInt(), ActiveMQClient.DEFAULT_ACK_BATCH_SIZE);
    }

    @Test
    public void testAttributeValues_39() {
        Assert.assertEquals(ConnectionFactoryAttributes.Common.FAILOVER_ON_INITIAL_CONNECTION.getName(), ConnectionFactoryAttributes.Common.FAILOVER_ON_INITIAL_CONNECTION.getDefaultValue().asBoolean(), ActiveMQClient.DEFAULT_FAILOVER_ON_INITIAL_CONNECTION);
    }

    @Test
    public void testAttributeValues_40() {
        Assert.assertEquals(ConnectionFactoryAttributes.Common.PRE_ACKNOWLEDGE.getName(), ConnectionFactoryAttributes.Common.PRE_ACKNOWLEDGE.getDefaultValue().asBoolean(), ActiveMQClient.DEFAULT_PRE_ACKNOWLEDGE);
    }

    @Test
    public void testAttributeValues_41() {
        Assert.assertEquals(ConnectionFactoryAttributes.Common.PRODUCER_MAX_RATE.getName(), ConnectionFactoryAttributes.Common.PRODUCER_MAX_RATE.getDefaultValue().asInt(), ActiveMQClient.DEFAULT_PRODUCER_MAX_RATE);
    }

    @Test
    public void testAttributeValues_42() {
        Assert.assertEquals(ConnectionFactoryAttributes.Common.PRODUCER_WINDOW_SIZE.getName(), ConnectionFactoryAttributes.Common.PRODUCER_WINDOW_SIZE.getDefaultValue().asInt(), ActiveMQClient.DEFAULT_PRODUCER_WINDOW_SIZE);
    }

    @Test
    public void testAttributeValues_43() {
        Assert.assertEquals(ConnectionFactoryAttributes.Common.RECONNECT_ATTEMPTS.getName(), ConnectionFactoryAttributes.Common.RECONNECT_ATTEMPTS.getDefaultValue().asInt(), ActiveMQClient.DEFAULT_RECONNECT_ATTEMPTS);
    }

    @Test
    public void testAttributeValues_44() {
        Assert.assertEquals(ConnectionFactoryAttributes.Common.SCHEDULED_THREAD_POOL_MAX_SIZE.getName(), ConnectionFactoryAttributes.Common.SCHEDULED_THREAD_POOL_MAX_SIZE.getDefaultValue().asInt(), ActiveMQDefaultConfiguration.getDefaultScheduledThreadPoolMaxSize());
    }

    @Test
    public void testAttributeValues_45() {
        Assert.assertEquals(ConnectionFactoryAttributes.Common.USE_GLOBAL_POOLS.getName(), ConnectionFactoryAttributes.Common.USE_GLOBAL_POOLS.getDefaultValue().asBoolean(), ActiveMQClient.DEFAULT_USE_GLOBAL_POOLS);
    }

    @Test
    public void testAttributeValues_46() {
        Assert.assertEquals(ConnectionFactoryAttributes.Common.USE_TOPOLOGY.getName(), ConnectionFactoryAttributes.Common.USE_TOPOLOGY.getDefaultValue().asBoolean(), ActiveMQClient.DEFAULT_USE_TOPOLOGY_FOR_LOADBALANCING);
    }

    @Test
    public void testAttributeValues_47() {
        Assert.assertEquals(DivertDefinition.EXCLUSIVE.getName(), DivertDefinition.EXCLUSIVE.getDefaultValue().asBoolean(), ActiveMQDefaultConfiguration.isDefaultDivertExclusive());
    }

    @Test
    public void testAttributeValues_48() {
        Assert.assertEquals(GroupingHandlerDefinition.GROUP_TIMEOUT.getName(), GroupingHandlerDefinition.GROUP_TIMEOUT.getDefaultValue().asLong(), ActiveMQDefaultConfiguration.getDefaultGroupingHandlerGroupTimeout());
    }

    @Test
    public void testAttributeValues_49() {
        Assert.assertEquals(GroupingHandlerDefinition.REAPER_PERIOD.getName(), GroupingHandlerDefinition.REAPER_PERIOD.getDefaultValue().asLong(), ActiveMQDefaultConfiguration.getDefaultGroupingHandlerReaperPeriod());
    }

    @Test
    public void testAttributeValues_50() {
        Assert.assertEquals(GroupingHandlerDefinition.TIMEOUT.getName(), GroupingHandlerDefinition.TIMEOUT.getDefaultValue().asLong(), ActiveMQDefaultConfiguration.getDefaultGroupingHandlerTimeout());
    }

    @Test
    public void testAttributeValues_51() {
        Assert.assertEquals(HAAttributes.ALLOW_FAILBACK.getName(), HAAttributes.ALLOW_FAILBACK.getDefaultValue().asBoolean(), ActiveMQDefaultConfiguration.isDefaultAllowAutoFailback());
    }

    @Test
    public void testAttributeValues_52() {
        Assert.assertEquals(HAAttributes.BACKUP_PORT_OFFSET.getName(), HAAttributes.BACKUP_PORT_OFFSET.getDefaultValue().asInt(), ActiveMQDefaultConfiguration.getDefaultHapolicyBackupPortOffset());
    }

    @Test
    public void testAttributeValues_53() {
        Assert.assertEquals(HAAttributes.BACKUP_REQUEST_RETRIES.getName(), HAAttributes.BACKUP_REQUEST_RETRIES.getDefaultValue().asInt(), ActiveMQDefaultConfiguration.getDefaultHapolicyBackupRequestRetries());
    }

    @Test
    public void testAttributeValues_54() {
        Assert.assertEquals(HAAttributes.BACKUP_REQUEST_RETRY_INTERVAL.getName(), HAAttributes.BACKUP_REQUEST_RETRY_INTERVAL.getDefaultValue().asLong(), ActiveMQDefaultConfiguration.getDefaultHapolicyBackupRequestRetryInterval());
    }

    @Test
    public void testAttributeValues_55() {
        Assert.assertEquals(HAAttributes.FAILOVER_ON_SERVER_SHUTDOWN.getName(), HAAttributes.FAILOVER_ON_SERVER_SHUTDOWN.getDefaultValue().asBoolean(), ActiveMQDefaultConfiguration.isDefaultFailoverOnServerShutdown());
    }

    @Test
    public void testAttributeValues_56() {
        Assert.assertEquals(HAAttributes.INITIAL_REPLICATION_SYNC_TIMEOUT.getName(), HAAttributes.INITIAL_REPLICATION_SYNC_TIMEOUT.getDefaultValue().asLong(), ActiveMQDefaultConfiguration.getDefaultInitialReplicationSyncTimeout());
    }

    @Test
    public void testAttributeValues_57() {
        Assert.assertEquals(HAAttributes.MAX_BACKUPS.getName(), HAAttributes.MAX_BACKUPS.getDefaultValue().asInt(), ActiveMQDefaultConfiguration.getDefaultHapolicyMaxBackups());
    }

    @Test
    public void testAttributeValues_58() {
        Assert.assertEquals(HAAttributes.MAX_SAVED_REPLICATED_JOURNAL_SIZE.getName(), HAAttributes.MAX_SAVED_REPLICATED_JOURNAL_SIZE.getDefaultValue().asInt(), ActiveMQDefaultConfiguration.getDefaultMaxSavedReplicatedJournalsSize());
    }

    @Test
    public void testAttributeValues_59() {
        Assert.assertEquals(HAAttributes.REQUEST_BACKUP.getName(), HAAttributes.REQUEST_BACKUP.getDefaultValue().asBoolean(), ActiveMQDefaultConfiguration.isDefaultHapolicyRequestBackup());
    }

    @Test
    public void testAttributeValues_60() {
        Assert.assertEquals(HAAttributes.RESTART_BACKUP.getName(), HAAttributes.RESTART_BACKUP.getDefaultValue().asBoolean(), ActiveMQDefaultConfiguration.isDefaultRestartBackup());
    }

    @Test
    public void testAttributeValues_61() {
        Assert.assertEquals(JGroupsBroadcastGroupDefinition.BROADCAST_PERIOD.getName(), JGroupsBroadcastGroupDefinition.BROADCAST_PERIOD.getDefaultValue().asLong(), ActiveMQDefaultConfiguration.getDefaultBroadcastPeriod());
    }

    @Test
    public void testAttributeValues_62() {
        Assert.assertEquals(JGroupsDiscoveryGroupDefinition.INITIAL_WAIT_TIMEOUT.getName(), JGroupsDiscoveryGroupDefinition.INITIAL_WAIT_TIMEOUT.getDefaultValue().asLong(), ActiveMQClient.DEFAULT_DISCOVERY_INITIAL_WAIT_TIMEOUT);
    }

    @Test
    public void testAttributeValues_63() {
        Assert.assertEquals(JGroupsDiscoveryGroupDefinition.REFRESH_TIMEOUT.getName(), JGroupsDiscoveryGroupDefinition.REFRESH_TIMEOUT.getDefaultValue().asLong(), ActiveMQDefaultConfiguration.getDefaultBroadcastRefreshTimeout());
    }

    @Test
    public void testAttributeValues_64() {
        Assert.assertEquals(LegacyConnectionFactoryDefinition.AUTO_GROUP.getName(), LegacyConnectionFactoryDefinition.AUTO_GROUP.getDefaultValue().asBoolean(), HornetQClient.DEFAULT_AUTO_GROUP);
    }

    @Test
    public void testAttributeValues_65() {
        Assert.assertEquals(LegacyConnectionFactoryDefinition.BLOCK_ON_ACKNOWLEDGE.getName(), LegacyConnectionFactoryDefinition.BLOCK_ON_ACKNOWLEDGE.getDefaultValue().asBoolean(), HornetQClient.DEFAULT_BLOCK_ON_ACKNOWLEDGE);
    }

    @Test
    public void testAttributeValues_66() {
        Assert.assertEquals(LegacyConnectionFactoryDefinition.BLOCK_ON_DURABLE_SEND.getName(), LegacyConnectionFactoryDefinition.BLOCK_ON_DURABLE_SEND.getDefaultValue().asBoolean(), HornetQClient.DEFAULT_BLOCK_ON_DURABLE_SEND);
    }

    @Test
    public void testAttributeValues_67() {
        Assert.assertEquals(LegacyConnectionFactoryDefinition.BLOCK_ON_NON_DURABLE_SEND.getName(), LegacyConnectionFactoryDefinition.BLOCK_ON_NON_DURABLE_SEND.getDefaultValue().asBoolean(), HornetQClient.DEFAULT_BLOCK_ON_NON_DURABLE_SEND);
    }

    @Test
    public void testAttributeValues_68() {
        Assert.assertEquals(LegacyConnectionFactoryDefinition.CACHE_LARGE_MESSAGE_CLIENT.getName(), LegacyConnectionFactoryDefinition.CACHE_LARGE_MESSAGE_CLIENT.getDefaultValue().asBoolean(), HornetQClient.DEFAULT_CACHE_LARGE_MESSAGE_CLIENT);
    }

    @Test
    public void testAttributeValues_69() {
        Assert.assertEquals(LegacyConnectionFactoryDefinition.CLIENT_FAILURE_CHECK_PERIOD.getName(), LegacyConnectionFactoryDefinition.CLIENT_FAILURE_CHECK_PERIOD.getDefaultValue().asLong(), HornetQClient.DEFAULT_CLIENT_FAILURE_CHECK_PERIOD);
    }

    @Test
    public void testAttributeValues_70() {
        Assert.assertEquals(LegacyConnectionFactoryDefinition.COMPRESS_LARGE_MESSAGES.getName(), LegacyConnectionFactoryDefinition.COMPRESS_LARGE_MESSAGES.getDefaultValue().asBoolean(), HornetQClient.DEFAULT_COMPRESS_LARGE_MESSAGES);
    }

    @Test
    public void testAttributeValues_71() {
        Assert.assertEquals(LegacyConnectionFactoryDefinition.CONFIRMATION_WINDOW_SIZE.getName(), LegacyConnectionFactoryDefinition.CONFIRMATION_WINDOW_SIZE.getDefaultValue().asInt(), HornetQClient.DEFAULT_CONFIRMATION_WINDOW_SIZE);
    }

    @Test
    public void testAttributeValues_72() {
        Assert.assertEquals(LegacyConnectionFactoryDefinition.CONNECTION_LOAD_BALANCING_CLASS_NAME.getName(), LegacyConnectionFactoryDefinition.CONNECTION_LOAD_BALANCING_CLASS_NAME.getDefaultValue().asString(), HornetQClient.DEFAULT_CONNECTION_LOAD_BALANCING_POLICY_CLASS_NAME);
    }

    @Test
    public void testAttributeValues_73() {
        Assert.assertEquals(LegacyConnectionFactoryDefinition.CONNECTION_TTL.getName(), LegacyConnectionFactoryDefinition.CONNECTION_TTL.getDefaultValue().asLong(), HornetQClient.DEFAULT_CONNECTION_TTL);
    }

    @Test
    public void testAttributeValues_74() {
        Assert.assertEquals(LegacyConnectionFactoryDefinition.CONSUMER_MAX_RATE.getName(), LegacyConnectionFactoryDefinition.CONSUMER_MAX_RATE.getDefaultValue().asInt(), HornetQClient.DEFAULT_CONSUMER_MAX_RATE);
    }

    @Test
    public void testAttributeValues_75() {
        Assert.assertEquals(LegacyConnectionFactoryDefinition.CONSUMER_WINDOW_SIZE.getName(), LegacyConnectionFactoryDefinition.CONSUMER_WINDOW_SIZE.getDefaultValue().asInt(), HornetQClient.DEFAULT_CONSUMER_WINDOW_SIZE);
    }

    @Test
    public void testAttributeValues_76() {
        Assert.assertEquals(LegacyConnectionFactoryDefinition.DUPS_OK_BATCH_SIZE.getName(), LegacyConnectionFactoryDefinition.DUPS_OK_BATCH_SIZE.getDefaultValue().asInt(), HornetQClient.DEFAULT_ACK_BATCH_SIZE);
    }

    @Test
    public void testAttributeValues_77() {
        Assert.assertEquals(LegacyConnectionFactoryDefinition.FAILOVER_ON_INITIAL_CONNECTION.getName(), LegacyConnectionFactoryDefinition.FAILOVER_ON_INITIAL_CONNECTION.getDefaultValue().asBoolean(), HornetQClient.DEFAULT_FAILOVER_ON_INITIAL_CONNECTION);
    }

    @Test
    public void testAttributeValues_78() {
        Assert.assertEquals(LegacyConnectionFactoryDefinition.INITIAL_CONNECT_ATTEMPTS.getName(), LegacyConnectionFactoryDefinition.INITIAL_CONNECT_ATTEMPTS.getDefaultValue().asInt(), HornetQClient.INITIAL_CONNECT_ATTEMPTS);
    }

    @Test
    public void testAttributeValues_79() {
        Assert.assertEquals(LegacyConnectionFactoryDefinition.INITIAL_MESSAGE_PACKET_SIZE.getName(), LegacyConnectionFactoryDefinition.INITIAL_MESSAGE_PACKET_SIZE.getDefaultValue().asInt(), HornetQClient.DEFAULT_INITIAL_MESSAGE_PACKET_SIZE);
    }

    @Test
    public void testAttributeValues_80() {
        Assert.assertEquals(LegacyConnectionFactoryDefinition.MAX_RETRY_INTERVAL.getName(), LegacyConnectionFactoryDefinition.MAX_RETRY_INTERVAL.getDefaultValue().asLong(), HornetQClient.DEFAULT_MAX_RETRY_INTERVAL);
    }

    @Test
    public void testAttributeValues_81() {
        Assert.assertEquals(LegacyConnectionFactoryDefinition.MIN_LARGE_MESSAGE_SIZE.getName(), LegacyConnectionFactoryDefinition.MIN_LARGE_MESSAGE_SIZE.getDefaultValue().asInt(), HornetQClient.DEFAULT_MIN_LARGE_MESSAGE_SIZE);
    }

    @Test
    public void testAttributeValues_82() {
        Assert.assertEquals(LegacyConnectionFactoryDefinition.PRE_ACKNOWLEDGE.getName(), LegacyConnectionFactoryDefinition.PRE_ACKNOWLEDGE.getDefaultValue().asBoolean(), HornetQClient.DEFAULT_PRE_ACKNOWLEDGE);
    }

    @Test
    public void testAttributeValues_83() {
        Assert.assertEquals(LegacyConnectionFactoryDefinition.PRODUCER_MAX_RATE.getName(), LegacyConnectionFactoryDefinition.PRODUCER_MAX_RATE.getDefaultValue().asInt(), HornetQClient.DEFAULT_PRODUCER_MAX_RATE);
    }

    @Test
    public void testAttributeValues_84() {
        Assert.assertEquals(LegacyConnectionFactoryDefinition.PRODUCER_WINDOW_SIZE.getName(), LegacyConnectionFactoryDefinition.PRODUCER_WINDOW_SIZE.getDefaultValue().asInt(), HornetQClient.DEFAULT_PRODUCER_WINDOW_SIZE);
    }

    @Test
    public void testAttributeValues_85() {
        Assert.assertEquals(LegacyConnectionFactoryDefinition.RECONNECT_ATTEMPTS.getName(), LegacyConnectionFactoryDefinition.RECONNECT_ATTEMPTS.getDefaultValue().asInt(), HornetQClient.DEFAULT_RECONNECT_ATTEMPTS);
    }

    @Test
    public void testAttributeValues_86() {
        Assert.assertEquals(LegacyConnectionFactoryDefinition.RETRY_INTERVAL.getName(), LegacyConnectionFactoryDefinition.RETRY_INTERVAL.getDefaultValue().asLong(), HornetQClient.DEFAULT_RETRY_INTERVAL);
    }

    @Test
    public void testAttributeValues_87() {
        Assert.assertEquals(LegacyConnectionFactoryDefinition.RETRY_INTERVAL_MULTIPLIER.getName(), LegacyConnectionFactoryDefinition.RETRY_INTERVAL_MULTIPLIER.getDefaultValue().asDouble(), HornetQClient.DEFAULT_RETRY_INTERVAL_MULTIPLIER, 0);
    }

    @Test
    public void testAttributeValues_88() {
        Assert.assertEquals(LegacyConnectionFactoryDefinition.USE_GLOBAL_POOLS.getName(), LegacyConnectionFactoryDefinition.USE_GLOBAL_POOLS.getDefaultValue().asBoolean(), HornetQClient.DEFAULT_USE_GLOBAL_POOLS);
    }

    @Test
    public void testAttributeValues_89() {
        Assert.assertEquals(ServerDefinition.ASYNC_CONNECTION_EXECUTION_ENABLED.getName(), ServerDefinition.ASYNC_CONNECTION_EXECUTION_ENABLED.getDefaultValue().asBoolean(), ActiveMQDefaultConfiguration.isDefaultAsyncConnectionExecutionEnabled());
    }

    @Test
    public void testAttributeValues_90() {
        Assert.assertEquals(ServerDefinition.CLUSTER_PASSWORD.getName(), ServerDefinition.CLUSTER_PASSWORD.getDefaultValue().asString(), ActiveMQDefaultConfiguration.getDefaultClusterPassword());
    }

    @Test
    public void testAttributeValues_91() {
        Assert.assertEquals(ServerDefinition.CLUSTER_USER.getName(), ServerDefinition.CLUSTER_USER.getDefaultValue().asString(), ActiveMQDefaultConfiguration.getDefaultClusterUser());
    }

    @Test
    public void testAttributeValues_92() {
        Assert.assertEquals(ServerDefinition.CONNECTION_TTL_OVERRIDE.getName(), ServerDefinition.CONNECTION_TTL_OVERRIDE.getDefaultValue().asLong(), ActiveMQDefaultConfiguration.getDefaultConnectionTtlOverride());
    }

    @Test
    public void testAttributeValues_93() {
        Assert.assertEquals(ServerDefinition.CREATE_BINDINGS_DIR.getName(), ServerDefinition.CREATE_BINDINGS_DIR.getDefaultValue().asBoolean(), ActiveMQDefaultConfiguration.isDefaultCreateBindingsDir());
    }

    @Test
    public void testAttributeValues_94() {
        Assert.assertEquals(ServerDefinition.CREATE_JOURNAL_DIR.getName(), ServerDefinition.CREATE_JOURNAL_DIR.getDefaultValue().asBoolean(), ActiveMQDefaultConfiguration.isDefaultCreateJournalDir());
    }

    @Test
    public void testAttributeValues_95() {
        Assert.assertEquals(ServerDefinition.DISK_SCAN_PERIOD.getName(), ServerDefinition.DISK_SCAN_PERIOD.getDefaultValue().asInt(), ActiveMQDefaultConfiguration.getDefaultDiskScanPeriod());
    }

    @Test
    public void testAttributeValues_96() {
        Assert.assertEquals(ServerDefinition.ID_CACHE_SIZE.getName(), ServerDefinition.ID_CACHE_SIZE.getDefaultValue().asInt(), ActiveMQDefaultConfiguration.getDefaultIdCacheSize());
    }

    @Test
    public void testAttributeValues_97() {
        Assert.assertEquals(ServerDefinition.JMX_DOMAIN.getName(), ServerDefinition.JMX_DOMAIN.getDefaultValue().asString(), ActiveMQDefaultConfiguration.getDefaultJmxDomain());
    }

    @Test
    public void testAttributeValues_98() {
        Assert.assertEquals(ServerDefinition.JOURNAL_BINDINGS_TABLE.getName(), ServerDefinition.JOURNAL_BINDINGS_TABLE.getDefaultValue().asString(), ActiveMQDefaultConfiguration.getDefaultBindingsTableName());
    }

    @Test
    public void testAttributeValues_99() {
        Assert.assertEquals(ServerDefinition.JOURNAL_COMPACT_MIN_FILES.getName(), ServerDefinition.JOURNAL_COMPACT_MIN_FILES.getDefaultValue().asInt(), ActiveMQDefaultConfiguration.getDefaultJournalCompactMinFiles());
    }

    @Test
    public void testAttributeValues_100() {
        Assert.assertEquals(ServerDefinition.JOURNAL_COMPACT_PERCENTAGE.getName(), ServerDefinition.JOURNAL_COMPACT_PERCENTAGE.getDefaultValue().asInt(), ActiveMQDefaultConfiguration.getDefaultJournalCompactPercentage());
    }

    @Test
    public void testAttributeValues_101() {
        Assert.assertEquals(ServerDefinition.JOURNAL_FILE_OPEN_TIMEOUT.getName(), ServerDefinition.JOURNAL_FILE_OPEN_TIMEOUT.getDefaultValue().asInt(), ActiveMQDefaultConfiguration.getDefaultJournalFileOpenTimeout());
    }

    @Test
    public void testAttributeValues_102() {
        Assert.assertEquals(ServerDefinition.JOURNAL_FILE_SIZE.getName(), ServerDefinition.JOURNAL_FILE_SIZE.getDefaultValue().asLong(), ActiveMQDefaultConfiguration.getDefaultJournalFileSize());
    }

    @Test
    public void testAttributeValues_103() {
        Assert.assertEquals(ServerDefinition.JOURNAL_JDBC_LOCK_EXPIRATION.getName(), ServerDefinition.JOURNAL_JDBC_LOCK_EXPIRATION.getDefaultValue().asInt() * 1000, ActiveMQDefaultConfiguration.getDefaultJdbcLockExpirationMillis());
    }

    @Test
    public void testAttributeValues_104() {
        Assert.assertEquals(ServerDefinition.JOURNAL_JDBC_LOCK_RENEW_PERIOD.getName(), ServerDefinition.JOURNAL_JDBC_LOCK_RENEW_PERIOD.getDefaultValue().asInt() * 1000, ActiveMQDefaultConfiguration.getDefaultJdbcLockRenewPeriodMillis());
    }

    @Test
    public void testAttributeValues_105() {
        Assert.assertEquals(ServerDefinition.JOURNAL_JDBC_NETWORK_TIMEOUT.getName(), ServerDefinition.JOURNAL_JDBC_NETWORK_TIMEOUT.getDefaultValue().asInt() * 1000, ActiveMQDefaultConfiguration.getDefaultJdbcNetworkTimeout());
    }

    @Test
    public void testAttributeValues_106() {
        Assert.assertEquals(ServerDefinition.JOURNAL_LARGE_MESSAGES_TABLE.getName(), ServerDefinition.JOURNAL_LARGE_MESSAGES_TABLE.getDefaultValue().asString(), ActiveMQDefaultConfiguration.getDefaultLargeMessagesTableName());
    }

    @Test
    public void testAttributeValues_107() {
        Assert.assertEquals(ServerDefinition.JOURNAL_MESSAGES_TABLE.getName(), ServerDefinition.JOURNAL_MESSAGES_TABLE.getDefaultValue().asString(), ActiveMQDefaultConfiguration.getDefaultMessageTableName());
    }

    @Test
    public void testAttributeValues_108() {
        Assert.assertEquals(ServerDefinition.JOURNAL_MIN_FILES.getName(), ServerDefinition.JOURNAL_MIN_FILES.getDefaultValue().asInt(), ActiveMQDefaultConfiguration.getDefaultJournalMinFiles());
    }

    @Test
    public void testAttributeValues_109() {
        Assert.assertEquals(ServerDefinition.JOURNAL_NODE_MANAGER_STORE_TABLE.getName(), ServerDefinition.JOURNAL_NODE_MANAGER_STORE_TABLE.getDefaultValue().asString(), ActiveMQDefaultConfiguration.getDefaultNodeManagerStoreTableName());
    }

    @Test
    public void testAttributeValues_110() {
        Assert.assertEquals(ServerDefinition.JOURNAL_PAGE_STORE_TABLE.getName(), ServerDefinition.JOURNAL_PAGE_STORE_TABLE.getDefaultValue().asString(), ActiveMQDefaultConfiguration.getDefaultPageStoreTableName());
    }

    @Test
    public void testAttributeValues_111() {
        Assert.assertEquals(ServerDefinition.JOURNAL_SYNC_NON_TRANSACTIONAL.getName(), ServerDefinition.JOURNAL_SYNC_NON_TRANSACTIONAL.getDefaultValue().asBoolean(), ActiveMQDefaultConfiguration.isDefaultJournalSyncNonTransactional());
    }

    @Test
    public void testAttributeValues_112() {
        Assert.assertEquals(ServerDefinition.JOURNAL_SYNC_TRANSACTIONAL.getName(), ServerDefinition.JOURNAL_SYNC_TRANSACTIONAL.getDefaultValue().asBoolean(), ActiveMQDefaultConfiguration.isDefaultJournalSyncTransactional());
    }

    @Test
    public void testAttributeValues_113() {
        Assert.assertEquals(ServerDefinition.LOG_JOURNAL_WRITE_RATE.getName(), ServerDefinition.LOG_JOURNAL_WRITE_RATE.getDefaultValue().asBoolean(), ActiveMQDefaultConfiguration.isDefaultJournalLogWriteRate());
    }

    @Test
    public void testAttributeValues_114() {
        Assert.assertEquals(ServerDefinition.MANAGEMENT_ADDRESS.getName(), ServerDefinition.MANAGEMENT_ADDRESS.getDefaultValue().asString(), ActiveMQDefaultConfiguration.getDefaultManagementAddress().toString());
    }

    @Test
    public void testAttributeValues_115() {
        Assert.assertEquals(ServerDefinition.MANAGEMENT_NOTIFICATION_ADDRESS.getName(), ServerDefinition.MANAGEMENT_NOTIFICATION_ADDRESS.getDefaultValue().asString(), ActiveMQDefaultConfiguration.getDefaultManagementNotificationAddress().toString());
    }

    @Test
    public void testAttributeValues_116() {
        Assert.assertEquals(ServerDefinition.MEMORY_MEASURE_INTERVAL.getName(), ServerDefinition.MEMORY_MEASURE_INTERVAL.getDefaultValue().asLong(), ActiveMQDefaultConfiguration.getDefaultMemoryMeasureInterval());
    }

    @Test
    public void testAttributeValues_117() {
        Assert.assertEquals(ServerDefinition.MEMORY_WARNING_THRESHOLD.getName(), ServerDefinition.MEMORY_WARNING_THRESHOLD.getDefaultValue().asInt(), ActiveMQDefaultConfiguration.getDefaultMemoryWarningThreshold());
    }

    @Test
    public void testAttributeValues_118() {
        Assert.assertEquals(ServerDefinition.MESSAGE_COUNTER_MAX_DAY_HISTORY.getName(), ServerDefinition.MESSAGE_COUNTER_MAX_DAY_HISTORY.getDefaultValue().asInt(), ActiveMQDefaultConfiguration.getDefaultMessageCounterMaxDayHistory());
    }

    @Test
    public void testAttributeValues_119() {
        Assert.assertEquals(ServerDefinition.MESSAGE_COUNTER_SAMPLE_PERIOD.getName(), ServerDefinition.MESSAGE_COUNTER_SAMPLE_PERIOD.getDefaultValue().asLong(), ActiveMQDefaultConfiguration.getDefaultMessageCounterSamplePeriod());
    }

    @Test
    public void testAttributeValues_120() {
        Assert.assertEquals(ServerDefinition.MESSAGE_EXPIRY_SCAN_PERIOD.getName(), ServerDefinition.MESSAGE_EXPIRY_SCAN_PERIOD.getDefaultValue().asLong(), ActiveMQDefaultConfiguration.getDefaultMessageExpiryScanPeriod());
    }

    @Test
    public void testAttributeValues_121() {
        Assert.assertEquals(ServerDefinition.MESSAGE_EXPIRY_THREAD_PRIORITY.getName(), ServerDefinition.MESSAGE_EXPIRY_THREAD_PRIORITY.getDefaultValue().asInt(), ActiveMQDefaultConfiguration.getDefaultMessageExpiryThreadPriority());
    }

    @Test
    public void testAttributeValues_122() {
        Assert.assertEquals(ServerDefinition.PAGE_MAX_CONCURRENT_IO.getName(), ServerDefinition.PAGE_MAX_CONCURRENT_IO.getDefaultValue().asInt(), ActiveMQDefaultConfiguration.getDefaultMaxConcurrentPageIo());
    }

    @Test
    public void testAttributeValues_123() {
        Assert.assertEquals(ServerDefinition.PERSISTENCE_ENABLED.getName(), ServerDefinition.PERSISTENCE_ENABLED.getDefaultValue().asBoolean(), ActiveMQDefaultConfiguration.isDefaultPersistenceEnabled());
    }

    @Test
    public void testAttributeValues_124() {
        Assert.assertEquals(ServerDefinition.PERSIST_DELIVERY_COUNT_BEFORE_DELIVERY.getName(), ServerDefinition.PERSIST_DELIVERY_COUNT_BEFORE_DELIVERY.getDefaultValue().asBoolean(), ActiveMQDefaultConfiguration.isDefaultPersistDeliveryCountBeforeDelivery());
    }

    @Test
    public void testAttributeValues_125() {
        Assert.assertEquals(ServerDefinition.PERSIST_ID_CACHE.getName(), ServerDefinition.PERSIST_ID_CACHE.getDefaultValue().asBoolean(), ActiveMQDefaultConfiguration.isDefaultPersistIdCache());
    }

    @Test
    public void testAttributeValues_126() {
        Assert.assertEquals(ServerDefinition.SECURITY_ENABLED.getName(), ServerDefinition.SECURITY_ENABLED.getDefaultValue().asBoolean(), ActiveMQDefaultConfiguration.isDefaultSecurityEnabled());
    }

    @Test
    public void testAttributeValues_127() {
        Assert.assertEquals(ServerDefinition.SECURITY_INVALIDATION_INTERVAL.getName(), ServerDefinition.SECURITY_INVALIDATION_INTERVAL.getDefaultValue().asLong(), ActiveMQDefaultConfiguration.getDefaultSecurityInvalidationInterval());
    }

    @Test
    public void testAttributeValues_128() {
        Assert.assertEquals(ServerDefinition.SERVER_DUMP_INTERVAL.getName(), ServerDefinition.SERVER_DUMP_INTERVAL.getDefaultValue().asLong(), ActiveMQDefaultConfiguration.getDefaultServerDumpInterval());
    }

    @Test
    public void testAttributeValues_129() {
        Assert.assertEquals(ServerDefinition.THREAD_POOL_MAX_SIZE.getName(), ServerDefinition.THREAD_POOL_MAX_SIZE.getDefaultValue().asInt(), ActiveMQDefaultConfiguration.getDefaultThreadPoolMaxSize());
    }

    @Test
    public void testAttributeValues_130() {
        Assert.assertEquals(ServerDefinition.TRANSACTION_TIMEOUT.getName(), ServerDefinition.TRANSACTION_TIMEOUT.getDefaultValue().asLong(), ActiveMQDefaultConfiguration.getDefaultTransactionTimeout());
    }

    @Test
    public void testAttributeValues_131() {
        Assert.assertEquals(ServerDefinition.TRANSACTION_TIMEOUT_SCAN_PERIOD.getName(), ServerDefinition.TRANSACTION_TIMEOUT_SCAN_PERIOD.getDefaultValue().asLong(), ActiveMQDefaultConfiguration.getDefaultTransactionTimeoutScanPeriod());
    }

    @Test
    public void testAttributeValues_132() {
        Assert.assertEquals(ServerDefinition.WILD_CARD_ROUTING_ENABLED.getName(), ServerDefinition.WILD_CARD_ROUTING_ENABLED.getDefaultValue().asBoolean(), ActiveMQDefaultConfiguration.isDefaultWildcardRoutingEnabled());
    }
}
