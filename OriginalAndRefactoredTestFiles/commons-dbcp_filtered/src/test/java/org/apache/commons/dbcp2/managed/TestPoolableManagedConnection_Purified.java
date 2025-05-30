package org.apache.commons.dbcp2.managed;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Properties;
import javax.transaction.TransactionManager;
import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.Constants;
import org.apache.commons.dbcp2.DriverConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.TesterDriver;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.geronimo.transaction.manager.TransactionManagerImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPoolableManagedConnection_Purified {

    private TransactionManager transactionManager;

    private TransactionRegistry transactionRegistry;

    private GenericObjectPool<PoolableConnection> pool;

    private Connection conn;

    private PoolableManagedConnection poolableManagedConnection;

    @BeforeEach
    public void setUp() throws Exception {
        transactionManager = new TransactionManagerImpl();
        final Properties properties = new Properties();
        properties.setProperty(Constants.KEY_USER, "userName");
        properties.setProperty(Constants.KEY_PASSWORD, "password");
        final ConnectionFactory connectionFactory = new DriverConnectionFactory(new TesterDriver(), "jdbc:apache:commons:testdriver", properties);
        final XAConnectionFactory xaConnectionFactory = new LocalXAConnectionFactory(transactionManager, connectionFactory);
        transactionRegistry = xaConnectionFactory.getTransactionRegistry();
        final PoolableConnectionFactory factory = new PoolableConnectionFactory(xaConnectionFactory, null);
        factory.setValidationQuery("SELECT DUMMY FROM DUAL");
        factory.setDefaultReadOnly(Boolean.TRUE);
        factory.setDefaultAutoCommit(Boolean.TRUE);
        pool = new GenericObjectPool<>(factory);
        factory.setPool(pool);
        pool.setMaxTotal(10);
        pool.setMaxWait(Duration.ofMillis(100));
    }

    @AfterEach
    public void tearDown() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
        if (pool != null && !pool.isClosed()) {
            pool.close();
        }
    }

    @Test
    public void testManagedConnection_1() throws Exception {
        assertEquals(0, pool.getNumActive());
    }

    @Test
    public void testManagedConnection_2_testMerged_2() throws Exception {
        conn = pool.borrowObject();
        assertEquals(1, pool.getNumActive());
    }

    @Test
    public void testManagedConnection_4() throws Exception {
        assertEquals(0, pool.getNumActive());
    }
}
