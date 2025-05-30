package com.iluwatar.servicelocator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ServiceLocatorTest_Parameterized {

    @ParameterizedTest
    @MethodSource("Provider_testGetNonExistentService_1to2")
    void testGetNonExistentService_1to2(String param1) {
        assertNull(ServiceLocator.getService(param1));
    }

    static public Stream<Arguments> Provider_testGetNonExistentService_1to2() {
        return Stream.of(arguments("fantastic/unicorn/service"), arguments("another/fantastic/unicorn/service"));
    }
}
