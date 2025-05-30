package org.apache.commons.dbcp2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import javax.management.AttributeNotFoundException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.sql.DataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class TestBasicDataSource_Purified extends TestConnectionPool {

    private static final String CATALOG = "test catalog";

    protected BasicDataSource ds;

    protected BasicDataSource createDataSource() throws Exception {
        return new BasicDataSource();
    }

    @Override
    protected Connection getConnection() throws Exception {
        return ds.getConnection();
    }

    @BeforeEach
    public void setUp() throws Exception {
        ds = createDataSource();
        ds.setDriverClassName("org.apache.commons.dbcp2.TesterDriver");
        ds.setUrl("jdbc:apache:commons:testdriver");
        ds.setMaxTotal(getMaxTotal());
        ds.setMaxWait(getMaxWaitDuration());
        ds.setDefaultAutoCommit(Boolean.TRUE);
        ds.setDefaultReadOnly(Boolean.FALSE);
        ds.setDefaultTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        ds.setDefaultCatalog(CATALOG);
        ds.setUsername("userName");
        ds.setPassword("password");
        ds.setValidationQuery("SELECT DUMMY FROM DUAL");
        ds.setConnectionInitSqls(Arrays.asList("SELECT 1", "SELECT 2"));
        ds.setDriverClassLoader(new TesterClassLoader());
        ds.setJmxName("org.apache.commons.dbcp2:name=test");
    }

    @Override
    @AfterEach
    public void tearDown() throws Exception {
        super.tearDown();
        ds.close();
        ds = null;
    }

    @Test
    public void testEmptyValidationQuery_1() throws Exception {
        assertNotNull(ds.getValidationQuery());
    }

    @Test
    public void testEmptyValidationQuery_2_testMerged_2() throws Exception {
        ds.setValidationQuery("");
        assertNull(ds.getValidationQuery());
    }

    @Test
    public void testIsWrapperFor_1() throws Exception {
        assertTrue(ds.isWrapperFor(BasicDataSource.class));
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

    @Test
    public void testSetValidationTestProperties_1() {
        assertTrue(ds.getTestOnBorrow());
    }

    @Test
    public void testSetValidationTestProperties_2() {
        assertFalse(ds.getTestOnReturn());
    }

    @Test
    public void testSetValidationTestProperties_3() {
        assertFalse(ds.getTestWhileIdle());
    }

    @Test
    public void testSetValidationTestProperties_4() {
        assertTrue(ds.getTestOnBorrow());
    }

    @Test
    public void testSetValidationTestProperties_5_testMerged_5() {
        ds.setTestOnBorrow(true);
        ds.setTestOnReturn(true);
        ds.setTestWhileIdle(true);
        assertTrue(ds.getTestOnReturn());
        assertTrue(ds.getTestWhileIdle());
        ds.setTestOnBorrow(false);
        ds.setTestOnReturn(false);
        ds.setTestWhileIdle(false);
        assertFalse(ds.getTestOnBorrow());
    }

    @Test
    public void testSetValidationTestProperties_8() {
        assertFalse(ds.getTestOnReturn());
    }

    @Test
    public void testSetValidationTestProperties_9() {
        assertFalse(ds.getTestWhileIdle());
    }
}
