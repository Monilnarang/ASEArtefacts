package org.apache.dubbo.rpc.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ServiceDescriptorInternalCacheTest_Purified {

    @Test
    void genericService_1() {
        Assertions.assertNotNull(ServiceDescriptorInternalCache.genericService());
    }

    @Test
    void genericService_2() {
        Assertions.assertEquals(GenericService.class, ServiceDescriptorInternalCache.genericService().getServiceInterfaceClass());
    }

    @Test
    void echoService_1() {
        Assertions.assertNotNull(ServiceDescriptorInternalCache.echoService());
    }

    @Test
    void echoService_2() {
        Assertions.assertEquals(EchoService.class, ServiceDescriptorInternalCache.echoService().getServiceInterfaceClass());
    }
}
