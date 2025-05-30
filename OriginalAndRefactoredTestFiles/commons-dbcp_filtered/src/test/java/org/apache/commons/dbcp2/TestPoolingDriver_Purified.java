package org.apache.commons.dbcp2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;
import javax.sql.DataSource;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPoolingDriver_Purified extends TestConnectionPool {

    private PoolingDriver driver;

    @Override
    protected Connection getConnection() throws Exception {
        return DriverManager.getConnection("jdbc:apache:commons:dbcp:test");
    }

    @BeforeEach
    public void setUp() throws Exception {
        final DriverConnectionFactory cf = new DriverConnectionFactory(new TesterDriver(), "jdbc:apache:commons:testdriver", null);
        final PoolableConnectionFactory pcf = new PoolableConnectionFactory(cf, null);
        pcf.setPoolStatements(true);
        pcf.setMaxOpenPreparedStatements(10);
        pcf.setValidationQuery("SELECT COUNT(*) FROM DUAL");
        pcf.setDefaultReadOnly(Boolean.FALSE);
        pcf.setDefaultAutoCommit(Boolean.TRUE);
        final GenericObjectPoolConfig<PoolableConnection> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxTotal(getMaxTotal());
        poolConfig.setMaxWait(getMaxWaitDuration());
        poolConfig.setMinIdle(10);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setTimeBetweenEvictionRuns(Duration.ofMillis(10_000));
        poolConfig.setNumTestsPerEvictionRun(5);
        poolConfig.setMinEvictableIdleTime(Duration.ofMillis(5_000));
        final GenericObjectPool<PoolableConnection> pool = new GenericObjectPool<>(pcf, poolConfig);
        pcf.setPool(pool);
        assertNotNull(pcf);
        driver = new PoolingDriver(true);
        driver.registerPool("test", pool);
    }

    @Override
    @AfterEach
    public void tearDown() throws Exception {
        driver.closePool("test");
        super.tearDown();
    }

    @Test
    public void testInvalidateConnection_1_testMerged_1() throws Exception {
        final Connection conn = DriverManager.getConnection("jdbc:apache:commons:dbcp:test");
        assertNotNull(conn);
        driver2.invalidateConnection(conn);
        assertTrue(conn.isClosed());
    }

    @Test
    public void testInvalidateConnection_2_testMerged_2() throws Exception {
        final ObjectPool<?> pool = driver.getConnectionPool("test");
        assertEquals(1, pool.getNumActive());
        assertEquals(0, pool.getNumIdle());
        assertEquals(0, pool.getNumActive());
    }

    @Test
    public void testReportedBug28912_1_testMerged_1() throws Exception {
        final Connection conn1 = getConnection();
        assertNotNull(conn1);
        assertFalse(conn1.isClosed());
        conn1.close();
        assertTrue(conn1.isClosed());
    }

    @Test
    public void testReportedBug28912_3_testMerged_2() throws Exception {
        final Connection conn2 = getConnection();
        assertNotNull(conn2);
        assertFalse(conn2.isClosed());
    }
}
