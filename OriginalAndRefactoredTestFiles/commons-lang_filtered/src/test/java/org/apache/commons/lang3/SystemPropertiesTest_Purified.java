package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;

public class SystemPropertiesTest_Purified {

    private void basicKeyCheck(final String key) {
        assertNotNull(key);
        assertFalse(key.isEmpty());
        assertDoesNotThrow(() -> System.getProperties().get(key));
        assertDoesNotThrow(() -> System.getProperty(key));
        assertDoesNotThrow(() -> System.getProperty(key, ""));
    }

    private boolean isJava11OrGreater() {
        return SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_11);
    }

    @Test
    public void testGetUserName_1() {
        assertNotNull(SystemProperties.getUserName());
    }

    @Test
    public void testGetUserName_2() {
        assertNotNull(SystemProperties.getUserName(""));
    }

    @Test
    public void testGetUserName_3() {
        assertNotNull(SystemProperties.getUserName("User"));
    }

    @Test
    public void testGetUserName_4() {
        assertNotNull(SystemProperties.getUserName(null));
    }
}
