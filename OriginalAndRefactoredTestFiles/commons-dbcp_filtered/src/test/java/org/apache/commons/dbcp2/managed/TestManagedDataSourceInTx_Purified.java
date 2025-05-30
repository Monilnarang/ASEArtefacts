package org.apache.commons.dbcp2.managed;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.transaction.Synchronization;
import javax.transaction.Transaction;
import org.apache.commons.dbcp2.DelegatingConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

public class TestManagedDataSourceInTx_Purified extends TestManagedDataSource {

    @Override
    protected void assertBackPointers(final Connection conn, final Statement statement) throws SQLException {
        assertFalse(conn.isClosed());
        assertFalse(isClosed(statement));
        assertSame(conn, statement.getConnection(), "statement.getConnection() should return the exact same connection instance that was used to create the statement");
        try (ResultSet resultSet = statement.getResultSet()) {
            assertFalse(isClosed(resultSet));
            assertSame(statement, resultSet.getStatement(), "resultSet.getStatement() should return the exact same statement instance that was used to create the result set");
            try (ResultSet executeResultSet = statement.executeQuery("select * from dual")) {
                assertFalse(isClosed(executeResultSet));
                assertSame(statement, executeResultSet.getStatement(), "resultSet.getStatement() should return the exact same statement instance that was used to create the result set");
            }
            try (ResultSet keysResultSet = statement.getGeneratedKeys()) {
                assertFalse(isClosed(keysResultSet));
                assertSame(statement, keysResultSet.getStatement(), "resultSet.getStatement() should return the exact same statement instance that was used to create the result set");
            }
            if (statement instanceof PreparedStatement) {
                final PreparedStatement preparedStatement = (PreparedStatement) statement;
                try (ResultSet preparedResultSet = preparedStatement.executeQuery()) {
                    assertFalse(isClosed(preparedResultSet));
                    assertSame(statement, preparedResultSet.getStatement(), "resultSet.getStatement() should return the exact same statement instance that was used to create the result set");
                }
            }
            resultSet.getStatement().getConnection().close();
        }
    }

    @Override
    @BeforeEach
    public void setUp() throws Exception {
        super.setUp();
        transactionManager.begin();
    }

    @Override
    @AfterEach
    public void tearDown() throws Exception {
        if (transactionManager.getTransaction() != null) {
            transactionManager.commit();
        }
        super.tearDown();
    }

    @Override
    @Test
    public void testClearWarnings_1_testMerged_1() throws Exception {
        Connection connection = newConnection();
        assertNotNull(connection);
        final CallableStatement statement = connection.prepareCall("warning");
        assertNotNull(connection.getWarnings());
    }

    @Override
    @Test
    public void testClearWarnings_3_testMerged_2() throws Exception {
        final Connection sharedConnection = newConnection();
        assertNotNull(sharedConnection.getWarnings());
    }
}
