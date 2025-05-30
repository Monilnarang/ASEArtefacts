package org.apache.dubbo.common.config;

import org.apache.dubbo.common.beanutil.JavaBeanAccessor;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InmemoryConfigurationTest_Purified {

    private InmemoryConfiguration memConfig;

    private static final String MOCK_KEY = "mockKey";

    private static final String MOCK_VALUE = "mockValue";

    private static final String MOCK_ONE_KEY = "one";

    private static final String MOCK_TWO_KEY = "two";

    private static final String MOCK_THREE_KEY = "three";

    @BeforeEach
    public void init() {
        memConfig = new InmemoryConfiguration();
    }

    @AfterEach
    public void clean() {
    }

    @Test
    void testGetMemProperty_1() {
        Assertions.assertNull(memConfig.getInternalProperty(MOCK_KEY));
    }

    @Test
    void testGetMemProperty_2() {
        Assertions.assertFalse(memConfig.containsKey(MOCK_KEY));
    }

    @Test
    void testGetMemProperty_3() {
        Assertions.assertNull(memConfig.getString(MOCK_KEY));
    }

    @Test
    void testGetMemProperty_4() {
        Assertions.assertNull(memConfig.getProperty(MOCK_KEY));
    }

    @Test
    void testGetMemProperty_5_testMerged_5() {
        memConfig.addProperty(MOCK_KEY, MOCK_VALUE);
        Assertions.assertTrue(memConfig.containsKey(MOCK_KEY));
        Assertions.assertEquals(MOCK_VALUE, memConfig.getInternalProperty(MOCK_KEY));
        Assertions.assertEquals(MOCK_VALUE, memConfig.getString(MOCK_KEY, MOCK_VALUE));
        Assertions.assertEquals(MOCK_VALUE, memConfig.getProperty(MOCK_KEY, MOCK_VALUE));
    }
}
