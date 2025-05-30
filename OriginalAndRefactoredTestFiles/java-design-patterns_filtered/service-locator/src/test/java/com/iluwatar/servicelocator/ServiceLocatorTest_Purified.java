package com.iluwatar.servicelocator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.junit.jupiter.api.Test;

class ServiceLocatorTest_Purified {

    @Test
    void testGetNonExistentService_1() {
        assertNull(ServiceLocator.getService("fantastic/unicorn/service"));
    }

    @Test
    void testGetNonExistentService_2() {
        assertNull(ServiceLocator.getService("another/fantastic/unicorn/service"));
    }
}
