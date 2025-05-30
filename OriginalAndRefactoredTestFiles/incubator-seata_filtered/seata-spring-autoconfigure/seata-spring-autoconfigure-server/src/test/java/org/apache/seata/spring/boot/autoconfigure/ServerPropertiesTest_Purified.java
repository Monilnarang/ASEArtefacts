package org.apache.seata.spring.boot.autoconfigure;

import org.apache.seata.spring.boot.autoconfigure.properties.server.MetricsProperties;
import org.apache.seata.spring.boot.autoconfigure.properties.server.ServerProperties;
import org.apache.seata.spring.boot.autoconfigure.properties.server.ServerRecoveryProperties;
import org.apache.seata.spring.boot.autoconfigure.properties.server.ServerUndoProperties;
import org.apache.seata.spring.boot.autoconfigure.properties.server.store.DbcpProperties;
import org.apache.seata.spring.boot.autoconfigure.properties.server.store.DruidProperties;
import org.apache.seata.spring.boot.autoconfigure.properties.server.store.HikariProperties;
import org.apache.seata.spring.boot.autoconfigure.properties.server.store.StoreDBProperties;
import org.apache.seata.spring.boot.autoconfigure.properties.server.store.StoreFileProperties;
import org.apache.seata.spring.boot.autoconfigure.properties.server.store.StoreProperties;
import org.apache.seata.spring.boot.autoconfigure.properties.server.store.StoreRedisProperties;
import org.apache.seata.spring.boot.autoconfigure.properties.server.store.StoreRedisProperties.Sentinel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ServerPropertiesTest_Purified {

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
    public void testServerRecoveryProperties_1() {
        assertEquals(context.getBean(ServerRecoveryProperties.class).getAsyncCommittingRetryPeriod(), 1000);
    }

    @Test
    public void testServerRecoveryProperties_2() {
        assertEquals(context.getBean(ServerRecoveryProperties.class).getCommittingRetryPeriod(), 1000);
    }

    @Test
    public void testServerRecoveryProperties_3() {
        assertEquals(context.getBean(ServerRecoveryProperties.class).getRollbackingRetryPeriod(), 1000);
    }

    @Test
    public void testServerRecoveryProperties_4() {
        assertEquals(context.getBean(ServerRecoveryProperties.class).getTimeoutRetryPeriod(), 1000);
    }
}
