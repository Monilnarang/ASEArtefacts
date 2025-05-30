package org.apache.commons.dbcp2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPoolingDataSource_Purified extends TestConnectionPool {

    protected PoolingDataSource<PoolableConnection> ds;

    private GenericObjectPool<PoolableConnection> pool;

    @Override
    protected Connection getConnection() throws Exception {
        return ds.getConnection();
    }

    @BeforeEach
    public void setUp() throws Exception {
        final Properties properties = new Properties();
        properties.setProperty(Constants.KEY_USER, "userName");
        properties.setProperty(Constants.KEY_PASSWORD, "password");
        final PoolableConnectionFactory factory = new PoolableConnectionFactory(new DriverConnectionFactory(new TesterDriver(), "jdbc:apache:commons:testdriver", properties), null);
        factory.setValidationQuery("SELECT DUMMY FROM DUAL");
        factory.setDefaultReadOnly(Boolean.TRUE);
        factory.setDefaultAutoCommit(Boolean.TRUE);
        pool = new GenericObjectPool<>(factory);
        factory.setPool(pool);
        pool.setMaxTotal(getMaxTotal());
        pool.setMaxWait(getMaxWaitDuration());
        ds = new PoolingDataSource<>(pool);
        ds.setAccessToUnderlyingConnectionAllowed(true);
    }

    @Override
    @AfterEach
    public void tearDown() throws Exception {
        ds.close();
        super.tearDown();
    }

    @Test
    public void testIsWrapperFor_1() throws Exception {
        assertTrue(ds.isWrapperFor(PoolingDataSource.class));
    }

    @Test
    public void testIsWrapperFor_2() throws Exception {
        assertTrue(ds.isWrapperFor(AutoCloseable.class));
    }

    @Test
    public void testIsWrapperFor_3() throws Exception {
        assertFalse(ds.isWrapperFor(String.class));
    }

    @Test
    public void testIsWrapperFor_4() throws Exception {
        assertFalse(ds.isWrapperFor(null));
    }
}
