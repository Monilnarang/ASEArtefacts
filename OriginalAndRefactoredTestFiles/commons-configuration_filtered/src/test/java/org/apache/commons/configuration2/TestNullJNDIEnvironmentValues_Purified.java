package org.apache.commons.configuration2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Iterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestNullJNDIEnvironmentValues_Purified {

    private JNDIConfiguration conf;

    @BeforeEach
    public void setUp() throws Exception {
        System.setProperty("java.naming.factory.initial", TestJNDIConfiguration.CONTEXT_FACTORY);
        conf = new JNDIConfiguration();
        conf.setThrowExceptionOnMissing(false);
    }

    @Test
    public void testClearProperty_1() {
        assertNotNull(conf.getShort("test.short", null));
    }

    @Test
    public void testClearProperty_2() {
        conf.clearProperty("test.short");
        assertNull(conf.getShort("test.short", null));
    }

    @Test
    public void testContainsKey_1() throws Exception {
        assertTrue(conf.containsKey("test.key"));
    }

    @Test
    public void testContainsKey_2() throws Exception {
        assertFalse(conf.containsKey("test.imaginarykey"));
    }
}
