package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.ThrowingSupplier;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SystemPropertiesTest_Parameterized {

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
    public void testGetUserName_4() {
        assertNotNull(SystemProperties.getUserName(null));
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetUserName_2to3")
    public void testGetUserName_2to3(String param1) {
        assertNotNull(SystemProperties.getUserName(param1));
    }

    static public Stream<Arguments> Provider_testGetUserName_2to3() {
        return Stream.of(arguments(""), arguments("User"));
    }
}
