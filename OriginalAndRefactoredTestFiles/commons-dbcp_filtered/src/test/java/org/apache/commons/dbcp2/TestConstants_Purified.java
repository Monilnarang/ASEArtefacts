package org.apache.commons.dbcp2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

public class TestConstants_Purified {

    @Test
    public void testConstants_1() {
        assertNotNull(new Constants());
    }

    @Test
    public void testConstants_2() {
        assertEquals(",connectionpool=", Constants.JMX_CONNECTION_POOL_BASE_EXT);
    }

    @Test
    public void testConstants_3() {
        assertEquals("connections", Constants.JMX_CONNECTION_POOL_PREFIX);
    }

    @Test
    public void testConstants_4() {
        assertEquals(",connectionpool=connections,connection=", Constants.JMX_CONNECTION_BASE_EXT);
    }

    @Test
    public void testConstants_5() {
        assertEquals(",connectionpool=connections,connection=", Constants.JMX_STATEMENT_POOL_BASE_EXT);
    }

    @Test
    public void testConstants_6() {
        assertEquals(",statementpool=statements", Constants.JMX_STATEMENT_POOL_PREFIX);
    }
}
