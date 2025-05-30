package org.apache.commons.dbcp2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import org.apache.commons.pool2.KeyedObjectPool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPStmtPoolingBasicDataSource_Purified extends TestBasicDataSource {

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        ds.setPoolPreparedStatements(true);
        ds.setMaxOpenPreparedStatements(2);
    }

    @Test
    public void testPStmtPoolingAcrossClose_2_testMerged_1() throws Exception {
        ds.setMaxTotal(1);
        ds.setMaxIdle(1);
        ds.setAccessToUnderlyingConnectionAllowed(true);
        assertEquals(1, ds.getNumActive());
        assertEquals(0, ds.getNumIdle());
        assertEquals(0, ds.getNumActive());
        assertEquals(1, ds.getNumIdle());
    }

    @Test
    public void testPStmtPoolingAcrossClose_1_testMerged_2() throws Exception {
        final Connection conn1 = getConnection();
        assertNotNull(conn1);
        final PreparedStatement stmt1 = conn1.prepareStatement("select 'a' from dual");
        assertNotNull(stmt1);
        final Statement inner1 = ((DelegatingPreparedStatement) stmt1).getInnermostDelegate();
        assertNotNull(inner1);
        final Connection conn2 = getConnection();
        assertNotNull(conn2);
        final PreparedStatement stmt2 = conn2.prepareStatement("select 'a' from dual");
        assertNotNull(stmt2);
        final Statement inner2 = ((DelegatingPreparedStatement) stmt2).getInnermostDelegate();
        assertNotNull(inner2);
        assertSame(inner1, inner2);
    }

    @Test
    public void testPStmtPoolingAcrossCloseWithClearOnReturn_2_testMerged_1() throws Exception {
        ds.setMaxTotal(1);
        ds.setMaxIdle(1);
        ds.setClearStatementPoolOnReturn(true);
        ds.setAccessToUnderlyingConnectionAllowed(true);
        assertEquals(1, ds.getNumActive());
        assertEquals(0, ds.getNumIdle());
        assertEquals(0, ds.getNumActive());
        assertEquals(1, ds.getNumIdle());
    }

    @Test
    public void testPStmtPoolingAcrossCloseWithClearOnReturn_1_testMerged_2() throws Exception {
        final Connection conn1 = getConnection();
        assertNotNull(conn1);
        final DelegatingConnection<Connection> poolableConn = (DelegatingConnection<Connection>) ((DelegatingConnection<Connection>) conn1).getDelegateInternal();
        final KeyedObjectPool<PStmtKey, DelegatingPreparedStatement> stmtPool = ((PoolingConnection) poolableConn.getDelegateInternal()).getStatementPool();
        final PreparedStatement stmt1 = conn1.prepareStatement("select 'a' from dual");
        assertNotNull(stmt1);
        final Statement inner1 = ((DelegatingPreparedStatement) stmt1).getInnermostDelegate();
        assertNotNull(inner1);
        final PreparedStatement stmt2 = conn1.prepareStatement("select 'a' from dual");
        assertNotNull(stmt2);
        final Statement inner2 = ((DelegatingPreparedStatement) stmt2).getInnermostDelegate();
        assertNotNull(inner2);
        assertSame(inner1, inner2);
        assertTrue(inner1.isClosed());
        assertEquals(0, stmtPool.getNumActive());
        assertEquals(0, stmtPool.getNumIdle());
        final Connection conn2 = getConnection();
        assertNotNull(conn2);
        final PreparedStatement stmt3 = conn2.prepareStatement("select 'a' from dual");
        assertNotNull(stmt3);
        final Statement inner3 = ((DelegatingPreparedStatement) stmt3).getInnermostDelegate();
        assertNotNull(inner3);
        assertNotSame(inner1, inner3);
    }

    @Test
    public void testPStmtPoolingWithNoClose_1_testMerged_1() throws Exception {
        final Connection conn1 = getConnection();
        assertNotNull(conn1);
        final PreparedStatement stmt1 = conn1.prepareStatement("select 'a' from dual");
        assertNotNull(stmt1);
        final Statement inner1 = ((DelegatingPreparedStatement) stmt1).getInnermostDelegate();
        assertNotNull(inner1);
        final PreparedStatement stmt2 = conn1.prepareStatement("select 'a' from dual");
        assertNotNull(stmt2);
        final Statement inner2 = ((DelegatingPreparedStatement) stmt2).getInnermostDelegate();
        assertNotNull(inner2);
        assertSame(inner1, inner2);
    }

    @Test
    public void testPStmtPoolingWithNoClose_2_testMerged_2() throws Exception {
        ds.setMaxTotal(1);
        ds.setMaxIdle(1);
        ds.setAccessToUnderlyingConnectionAllowed(true);
        assertEquals(1, ds.getNumActive());
        assertEquals(0, ds.getNumIdle());
    }
}
