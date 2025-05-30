package org.apache.seata.spring.boot.autoconfigure;

import org.apache.seata.spring.boot.autoconfigure.properties.SeataProperties;
import org.apache.seata.spring.boot.autoconfigure.properties.client.LoadBalanceProperties;
import org.apache.seata.spring.boot.autoconfigure.properties.client.LockProperties;
import org.apache.seata.spring.boot.autoconfigure.properties.client.RmProperties;
import org.apache.seata.spring.boot.autoconfigure.properties.client.ServiceProperties;
import org.apache.seata.spring.boot.autoconfigure.properties.client.TmProperties;
import org.apache.seata.spring.boot.autoconfigure.properties.client.UndoProperties;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.Map;
import static org.apache.seata.common.DefaultValues.DEFAULT_GLOBAL_TRANSACTION_TIMEOUT;
import static org.apache.seata.common.DefaultValues.DEFAULT_TM_COMMIT_RETRY_COUNT;
import static org.apache.seata.common.DefaultValues.DEFAULT_TM_ROLLBACK_RETRY_COUNT;
import static org.apache.seata.common.DefaultValues.DEFAULT_TRANSACTION_UNDO_LOG_TABLE;
import static org.apache.seata.common.DefaultValues.DEFAULT_TX_GROUP;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientPropertiesTest_Purified {

    private static AnnotationConfigApplicationContext context;

    @BeforeAll
    public static void initContext() {
        context = new AnnotationConfigApplicationContext("org.apache.seata.spring.boot.autoconfigure.properties");
    }

    @AfterAll
    public static void closeContext() {
        context.close();
    }

    @Test
    public void testSeataProperties_1() {
        assertTrue(context.getBean(SeataProperties.class).isEnabled());
    }

    @Test
    public void testSeataProperties_2() {
        assertNull(context.getBean(SeataProperties.class).getApplicationId());
    }

    @Test
    public void testSeataProperties_3() {
        assertEquals(DEFAULT_TX_GROUP, context.getBean(SeataProperties.class).getTxServiceGroup());
    }

    @Test
    public void testSeataProperties_4() {
        assertTrue(context.getBean(SeataProperties.class).isEnableAutoDataSourceProxy());
    }

    @Test
    public void testSeataProperties_5() {
        assertEquals("AT", context.getBean(SeataProperties.class).getDataSourceProxyMode());
    }

    @Test
    public void testSeataProperties_6() {
        assertFalse(context.getBean(SeataProperties.class).isUseJdkProxy());
    }

    @Test
    public void testLockProperties_1() {
        assertEquals(10, context.getBean(LockProperties.class).getRetryInterval());
    }

    @Test
    public void testLockProperties_2() {
        assertEquals(30, context.getBean(LockProperties.class).getRetryTimes());
    }

    @Test
    public void testLockProperties_3() {
        assertTrue(context.getBean(LockProperties.class).isRetryPolicyBranchRollbackOnConflict());
    }

    @Test
    public void testRmProperties_1() {
        Assertions.assertEquals(10000, context.getBean(RmProperties.class).getAsyncCommitBufferLimit());
    }

    @Test
    public void testRmProperties_2() {
        assertEquals(5, context.getBean(RmProperties.class).getReportRetryCount());
    }

    @Test
    public void testRmProperties_3() {
        assertTrue(context.getBean(RmProperties.class).isTableMetaCheckEnable());
    }

    @Test
    public void testRmProperties_4() {
        assertFalse(context.getBean(RmProperties.class).isReportSuccessEnable());
    }

    @Test
    public void testRmProperties_5() {
        assertEquals(60000L, context.getBean(RmProperties.class).getTableMetaCheckerInterval());
    }

    @Test
    public void testRmProperties_6() {
        assertFalse(context.getBean(RmProperties.class).isSagaRetryPersistModeUpdate());
    }

    @Test
    public void testRmProperties_7() {
        assertFalse(context.getBean(RmProperties.class).isSagaCompensatePersistModeUpdate());
    }

    @Test
    public void testTmProperties_1() {
        assertEquals(DEFAULT_TM_COMMIT_RETRY_COUNT, context.getBean(TmProperties.class).getCommitRetryCount());
    }

    @Test
    public void testTmProperties_2() {
        assertEquals(DEFAULT_TM_ROLLBACK_RETRY_COUNT, context.getBean(TmProperties.class).getRollbackRetryCount());
    }

    @Test
    public void testTmProperties_3() {
        assertEquals(DEFAULT_GLOBAL_TRANSACTION_TIMEOUT, context.getBean(TmProperties.class).getDefaultGlobalTransactionTimeout());
    }

    @Test
    public void testUndoProperties_1() {
        assertTrue(context.getBean(UndoProperties.class).isDataValidation());
    }

    @Test
    public void testUndoProperties_2() {
        assertEquals("jackson", context.getBean(UndoProperties.class).getLogSerialization());
    }

    @Test
    public void testUndoProperties_3() {
        assertEquals(DEFAULT_TRANSACTION_UNDO_LOG_TABLE, context.getBean(UndoProperties.class).getLogTable());
    }

    @Test
    public void testLoadBalanceProperties_1() {
        assertEquals("XID", context.getBean(LoadBalanceProperties.class).getType());
    }

    @Test
    public void testLoadBalanceProperties_2() {
        assertEquals(10, context.getBean(LoadBalanceProperties.class).getVirtualNodes());
    }
}
