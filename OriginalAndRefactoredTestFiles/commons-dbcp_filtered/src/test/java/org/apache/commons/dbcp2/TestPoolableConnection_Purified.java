package org.apache.commons.dbcp2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.management.OperationsException;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPoolableConnection_Purified {

    private GenericObjectPool<PoolableConnection> pool;

    @BeforeEach
    public void setUp() throws Exception {
        final PoolableConnectionFactory factory = new PoolableConnectionFactory(new DriverConnectionFactory(new TesterDriver(), "jdbc:apache:commons:testdriver", null), null);
        factory.setDefaultAutoCommit(Boolean.TRUE);
        factory.setDefaultReadOnly(Boolean.TRUE);
        pool = new GenericObjectPool<>(factory);
        factory.setPool(pool);
    }

    @AfterEach
    public void tearDown() {
        pool.close();
    }

    @Test
    public void testClosingWrappedInDelegate_1_testMerged_1() throws Exception {
        Assertions.assertEquals(0, pool.getNumActive());
    }

    @Test
    public void testClosingWrappedInDelegate_2_testMerged_2() throws Exception {
        final Connection conn = pool.borrowObject();
        final DelegatingConnection<Connection> outer = new DelegatingConnection<>(conn);
        Assertions.assertFalse(outer.isClosed());
        Assertions.assertFalse(conn.isClosed());
        Assertions.assertEquals(1, pool.getNumActive());
        outer.close();
        Assertions.assertTrue(outer.isClosed());
        Assertions.assertTrue(conn.isClosed());
        Assertions.assertEquals(1, pool.getNumIdle());
    }
}
