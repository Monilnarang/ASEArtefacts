package org.apache.commons.dbcp2.datasources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.util.Properties;
import org.apache.commons.dbcp2.cpdsadapter.DriverAdapterCPDS;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class TestInstanceKeyDataSource_Parameterized {

    private static final class ThrowOnSetupDefaultsDataSource extends SharedPoolDataSource {

        private static final long serialVersionUID = -448025812063133259L;

        ThrowOnSetupDefaultsDataSource() {
        }

        @Override
        protected void setupDefaults(final Connection connection, final String userName) throws SQLException {
            throw new SQLException("bang!");
        }
    }

    private static final String DRIVER = "org.apache.commons.dbcp2.TesterDriver";

    private static final String URL = "jdbc:apache:commons:testdriver";

    private static final String USER = "foo";

    private static final String PASS = "bar";

    private DriverAdapterCPDS pcds;

    private SharedPoolDataSource spds;

    @BeforeEach
    public void setUp() throws ClassNotFoundException {
        pcds = new DriverAdapterCPDS();
        pcds.setDriver(DRIVER);
        pcds.setUrl(URL);
        pcds.setUser(USER);
        pcds.setPassword(PASS);
        pcds.setPoolPreparedStatements(false);
        spds = new SharedPoolDataSource();
        spds.setConnectionPoolDataSource(pcds);
    }

    @AfterEach
    public void tearDown() throws Exception {
        spds.close();
    }

    @Test
    public void testDefaultTransactionIsolation_1() {
        assertEquals(InstanceKeyDataSource.UNKNOWN_TRANSACTIONISOLATION, spds.getDefaultTransactionIsolation());
    }

    @Test
    public void testDefaultTransactionIsolation_2() {
        spds.setDefaultTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        assertEquals(Connection.TRANSACTION_READ_COMMITTED, spds.getDefaultTransactionIsolation());
    }

    @Test
    public void testIsWrapperFor_1() throws Exception {
        assertTrue(spds.isWrapperFor(InstanceKeyDataSource.class));
    }

    @Test
    public void testIsWrapperFor_2() throws Exception {
        assertTrue(spds.isWrapperFor(AutoCloseable.class));
    }

    @Test
    public void testJndiEnvironment_2_testMerged_2() {
        final Properties properties = new Properties();
        properties.setProperty("name", "clarke");
        spds.setJndiEnvironment(properties);
        assertEquals("clarke", spds.getJndiEnvironment("name"));
        spds.setJndiEnvironment("name", "asimov");
        assertEquals("asimov", spds.getJndiEnvironment("name"));
    }

    @Test
    public void testJndiPropertiesNotInitialized_2() {
        spds.setJndiEnvironment("name", "king");
        assertEquals("king", spds.getJndiEnvironment("name"));
    }

    @Test
    public void testMaxConnLifetimeMillis_1() {
        assertEquals(-1, spds.getMaxConnLifetimeMillis());
    }

    @Test
    public void testMaxConnLifetimeMillis_2() {
        spds.setMaxConnLifetimeMillis(10);
        assertEquals(10, spds.getMaxConnLifetimeMillis());
    }

    @Test
    public void testRollbackAfterValidation_1() {
        assertFalse(spds.isRollbackAfterValidation());
    }

    @Test
    public void testRollbackAfterValidation_2() {
        spds.setRollbackAfterValidation(true);
        assertTrue(spds.isRollbackAfterValidation());
    }

    @SuppressWarnings("resource")
    @Test
    public void testUnwrap_1() throws Exception {
        assertSame(spds.unwrap(InstanceKeyDataSource.class), spds);
    }

    @SuppressWarnings("resource")
    @Test
    public void testUnwrap_2() throws Exception {
        assertSame(spds.unwrap(AutoCloseable.class), spds);
    }

    @Test
    public void testValidationQuery_1() {
        assertNull(spds.getValidationQuery());
    }

    @Test
    public void testValidationQuery_2() {
        spds.setValidationQuery("anything");
        assertEquals("anything", spds.getValidationQuery());
    }

    @Test
    public void testValidationQueryTimeout_1() {
        assertEquals(-1, spds.getValidationQueryTimeout());
    }

    @Test
    public void testValidationQueryTimeout_2() {
        spds.setValidationQueryTimeout(10);
        assertEquals(10, spds.getValidationQueryTimeout());
    }

    @Test
    public void testValidationQueryTimeoutDuration_1() {
        assertEquals(Duration.ofSeconds(-1), spds.getValidationQueryTimeoutDuration());
    }

    @Test
    public void testValidationQueryTimeoutDuration_2() {
        spds.setValidationQueryTimeout(Duration.ofSeconds(10));
        assertEquals(Duration.ofSeconds(10), spds.getValidationQueryTimeoutDuration());
    }

    @ParameterizedTest
    @MethodSource("Provider_testJndiEnvironment_1_1")
    public void testJndiEnvironment_1_1(String param1) {
        assertNull(spds.getJndiEnvironment(param1));
    }

    static public Stream<Arguments> Provider_testJndiEnvironment_1_1() {
        return Stream.of(arguments("name"), arguments("name"));
    }
}
