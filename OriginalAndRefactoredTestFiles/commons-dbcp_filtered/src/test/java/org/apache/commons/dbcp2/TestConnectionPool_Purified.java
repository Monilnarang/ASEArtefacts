package org.apache.commons.dbcp2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.Hashtable;
import java.util.Random;
import java.util.Stack;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public abstract class TestConnectionPool_Purified {

    protected final class PoolTest implements Runnable {

        private final Duration connHoldDuration;

        private final int numStatements;

        private volatile boolean isRun;

        private String state;

        private final Thread thread;

        private Throwable thrown;

        private final Random random = new Random();

        private final long createdMillis;

        private long started;

        private long ended;

        private long preconnected;

        private long connected;

        private long postconnected;

        private int loops;

        private int connHash;

        private final boolean stopOnException;

        private final boolean loopOnce;

        public PoolTest(final ThreadGroup threadGroup, final Duration connHoldDuration, final boolean isStopOnException) {
            this(threadGroup, connHoldDuration, isStopOnException, false, 1);
        }

        private PoolTest(final ThreadGroup threadGroup, final Duration connHoldDuration, final boolean isStopOnException, final boolean once, final int numStatements) {
            this.loopOnce = once;
            this.connHoldDuration = connHoldDuration;
            stopOnException = isStopOnException;
            isRun = true;
            thrown = null;
            thread = new Thread(threadGroup, this, "Thread+" + currentThreadCount++);
            thread.setDaemon(false);
            createdMillis = timeStampMillis();
            this.numStatements = numStatements;
        }

        public PoolTest(final ThreadGroup threadGroup, final Duration connHoldDuration, final boolean isStopOnException, final int numStatements) {
            this(threadGroup, connHoldDuration, isStopOnException, false, numStatements);
        }

        public Thread getThread() {
            return thread;
        }

        @Override
        public void run() {
            started = timeStampMillis();
            try {
                while (isRun) {
                    loops++;
                    state = "Getting Connection";
                    preconnected = timeStampMillis();
                    try (Connection conn = getConnection()) {
                        connHash = System.identityHashCode(((DelegatingConnection<?>) conn).getInnermostDelegate());
                        connected = timeStampMillis();
                        state = "Using Connection";
                        assertNotNull(conn);
                        final String sql = numStatements == 1 ? "select * from dual" : "select count " + random.nextInt(numStatements - 1);
                        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                            assertNotNull(stmt);
                            try (ResultSet rset = stmt.executeQuery()) {
                                assertNotNull(rset);
                                assertTrue(rset.next());
                                state = "Holding Connection";
                                Thread.sleep(connHoldDuration.toMillis());
                                state = "Closing ResultSet";
                            }
                            state = "Closing Statement";
                        }
                        state = "Closing Connection";
                    }
                    postconnected = timeStampMillis();
                    state = "Closed";
                    if (loopOnce) {
                        break;
                    }
                }
                state = DONE;
            } catch (final Throwable t) {
                thrown = t;
                if (!stopOnException) {
                    throw new RuntimeException();
                }
            } finally {
                ended = timeStampMillis();
            }
        }

        public void start() {
            thread.start();
        }

        public void stop() {
            isRun = false;
        }
    }

    final class TestThread implements Runnable {

        final Random random = new Random();

        boolean complete;

        boolean failed;

        int iter = 100;

        int delay = 50;

        public TestThread() {
        }

        public TestThread(final int iter) {
            this.iter = iter;
        }

        public TestThread(final int iter, final int delay) {
            this.iter = iter;
            this.delay = delay;
        }

        public boolean complete() {
            return complete;
        }

        public boolean failed() {
            return failed;
        }

        @Override
        public void run() {
            for (int i = 0; i < iter; i++) {
                try {
                    Thread.sleep(random.nextInt(delay));
                } catch (final Exception e) {
                }
                try (Connection conn = newConnection();
                    PreparedStatement stmt = conn.prepareStatement("select 'literal', SYSDATE from dual");
                    ResultSet rset = stmt.executeQuery()) {
                    try {
                        Thread.sleep(random.nextInt(delay));
                    } catch (final Exception ignore) {
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                    failed = true;
                    complete = true;
                    break;
                }
            }
            complete = true;
        }
    }

    private static final Duration MAX_WAIT_DURATION = Duration.ofMillis(100);

    private static final boolean DISPLAY_THREAD_DETAILS = Boolean.getBoolean("TestConnectionPool.display.thread.details");

    private static int currentThreadCount;

    private static final String DONE = "Done";

    protected final Stack<Connection> connectionStack = new Stack<>();

    protected void assertBackPointers(final Connection conn, final Statement statement) throws SQLException {
        assertFalse(conn.isClosed());
        assertFalse(isClosed(statement));
        assertSame(conn, statement.getConnection(), "statement.getConnection() should return the exact same connection instance that was used to create the statement");
        final ResultSet resultSet = statement.getResultSet();
        assertFalse(isClosed(resultSet));
        assertSame(statement, resultSet.getStatement(), "resultSet.getStatement() should return the exact same statement instance that was used to create the result set");
        final ResultSet executeResultSet = statement.executeQuery("select * from dual");
        assertFalse(isClosed(executeResultSet));
        assertSame(statement, executeResultSet.getStatement(), "resultSet.getStatement() should return the exact same statement instance that was used to create the result set");
        final ResultSet keysResultSet = statement.getGeneratedKeys();
        assertFalse(isClosed(keysResultSet));
        assertSame(statement, keysResultSet.getStatement(), "resultSet.getStatement() should return the exact same statement instance that was used to create the result set");
        ResultSet preparedResultSet = null;
        if (statement instanceof PreparedStatement) {
            final PreparedStatement preparedStatement = (PreparedStatement) statement;
            preparedResultSet = preparedStatement.executeQuery();
            assertFalse(isClosed(preparedResultSet));
            assertSame(statement, preparedResultSet.getStatement(), "resultSet.getStatement() should return the exact same statement instance that was used to create the result set");
        }
        resultSet.getStatement().getConnection().close();
        assertTrue(conn.isClosed());
        assertTrue(isClosed(statement));
        assertTrue(isClosed(resultSet));
        assertTrue(isClosed(executeResultSet));
        assertTrue(isClosed(keysResultSet));
        if (preparedResultSet != null) {
            assertTrue(isClosed(preparedResultSet));
        }
    }

    protected abstract Connection getConnection() throws Exception;

    protected int getMaxTotal() {
        return 10;
    }

    protected Duration getMaxWaitDuration() {
        return MAX_WAIT_DURATION;
    }

    protected String getUsername(final Connection conn) throws SQLException {
        try (final Statement stmt = conn.createStatement();
            final ResultSet rs = stmt.executeQuery("select username")) {
            if (rs.next()) {
                return rs.getString(1);
            }
        }
        return null;
    }

    protected boolean isClosed(final ResultSet resultSet) {
        try {
            resultSet.getWarnings();
            return false;
        } catch (final SQLException e) {
            return true;
        }
    }

    protected boolean isClosed(final Statement statement) {
        try {
            statement.getWarnings();
            return false;
        } catch (final SQLException e) {
            return true;
        }
    }

    protected void multipleThreads(final Duration holdDuration, final boolean expectError, final boolean loopOnce, final Duration maxWaitDuration) throws Exception {
        multipleThreads(holdDuration, expectError, loopOnce, maxWaitDuration, 1, 2 * getMaxTotal(), 300);
    }

    protected void multipleThreads(final Duration holdDuration, final boolean expectError, final boolean loopOnce, final Duration maxWaitDuration, final int numStatements, final int numThreads, final long duration) throws Exception {
        final long startTimeMillis = timeStampMillis();
        final PoolTest[] pts = new PoolTest[numThreads];
        final ThreadGroup threadGroup = new ThreadGroup("foo") {

            @Override
            public void uncaughtException(final Thread t, final Throwable e) {
                for (final PoolTest pt : pts) {
                    pt.stop();
                }
            }
        };
        for (int i = 0; i < pts.length; i++) {
            pts[i] = new PoolTest(threadGroup, holdDuration, expectError, loopOnce, numStatements);
        }
        for (final PoolTest pt : pts) {
            pt.start();
        }
        Thread.sleep(duration);
        for (final PoolTest pt : pts) {
            pt.stop();
        }
        int done = 0;
        int failed = 0;
        int didNotRun = 0;
        int loops = 0;
        for (final PoolTest poolTest : pts) {
            poolTest.thread.join();
            loops += poolTest.loops;
            final String state = poolTest.state;
            if (DONE.equals(state)) {
                done++;
            }
            if (poolTest.loops == 0) {
                didNotRun++;
            }
            final Throwable thrown = poolTest.thrown;
            if (thrown != null) {
                failed++;
                if (!expectError || !(thrown instanceof SQLException)) {
                    System.err.println("Unexpected error: " + thrown.getMessage());
                }
            }
        }
        final long timeMillis = timeStampMillis() - startTimeMillis;
        println("Multithread test time = " + timeMillis + " ms. Threads: " + pts.length + ". Loops: " + loops + ". Hold time: " + holdDuration + ". maxWaitMillis: " + maxWaitDuration + ". Done: " + done + ". Did not run: " + didNotRun + ". Failed: " + failed + ". expectError: " + expectError);
        if (expectError) {
            if (DISPLAY_THREAD_DETAILS || pts.length / 2 != failed) {
                final long offset = pts[0].createdMillis - 1000;
                println("Offset: " + offset);
                for (int i = 0; i < pts.length; i++) {
                    final PoolTest pt = pts[i];
                    println("Pre: " + (pt.preconnected - offset) + ". Post: " + (pt.postconnected != 0 ? Long.toString(pt.postconnected - offset) : "-") + ". Hash: " + pt.connHash + ". Startup: " + (pt.started - pt.createdMillis) + ". getConn(): " + (pt.connected != 0 ? Long.toString(pt.connected - pt.preconnected) : "-") + ". Runtime: " + (pt.ended - pt.started) + ". IDX: " + i + ". Loops: " + pt.loops + ". State: " + pt.state + ". thrown: " + pt.thrown + ".");
                }
            }
            if (didNotRun > 0) {
                println("NOTE: some threads did not run the code: " + didNotRun);
            }
            assertTrue(failed > 0, "Expected some of the threads to fail");
            assertEquals(pts.length / 2, failed + didNotRun, "WARNING: Expected half the threads to fail");
        } else {
            assertEquals(0, failed, "Did not expect any threads to fail");
        }
    }

    @SuppressWarnings("resource")
    protected Connection newConnection() throws Exception {
        return connectionStack.push(getConnection());
    }

    void println(final String string) {
        if (Boolean.getBoolean(getClass().getSimpleName() + ".debug")) {
            System.out.println(string);
        }
    }

    @AfterEach
    public void tearDown() throws Exception {
        while (!connectionStack.isEmpty()) {
            Utils.closeQuietly((AutoCloseable) connectionStack.pop());
        }
    }

    long timeStampMillis() {
        return System.currentTimeMillis();
    }

    @Test
    public void testAutoCommitBehavior_1_testMerged_1() throws Exception {
        final Connection conn0 = newConnection();
        assertNotNull(conn0, "connection should not be null");
        assertTrue(conn0.getAutoCommit(), "autocommit should be true for conn0");
        conn0.setAutoCommit(false);
        assertFalse(conn0.getAutoCommit(), "autocommit should be false for conn0");
    }

    @Test
    public void testAutoCommitBehavior_3() throws Exception {
        final Connection conn1 = newConnection();
        assertTrue(conn1.getAutoCommit(), "autocommit should be true for conn1");
    }

    @Test
    public void testAutoCommitBehavior_6() throws Exception {
        final Connection conn2 = newConnection();
        assertTrue(conn2.getAutoCommit(), "autocommit should be true for conn2");
    }

    @Test
    public void testAutoCommitBehavior_7() throws Exception {
        final Connection conn3 = newConnection();
        assertTrue(conn3.getAutoCommit(), "autocommit should be true for conn3");
    }
}
