package org.apache.dubbo.config.spring.registry.nacos.nacos;

import org.apache.dubbo.registry.nacos.NacosServiceName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.apache.dubbo.common.constants.RegistryConstants.DEFAULT_CATEGORY;
import static org.apache.dubbo.registry.nacos.NacosServiceName.WILDCARD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NacosServiceNameTest_Purified {

    private static final String category = DEFAULT_CATEGORY;

    private static final String serviceInterface = "org.apache.dubbo.registry.nacos.NacosServiceName";

    private static final String version = "1.0.0";

    private static final String group = "default";

    private final NacosServiceName name = new NacosServiceName();

    @BeforeEach
    public void init() {
        name.setCategory(category);
        name.setServiceInterface(serviceInterface);
        name.setVersion(version);
        name.setGroup(group);
    }

    @Test
    void testGetter_1() {
        assertEquals(category, name.getCategory());
    }

    @Test
    void testGetter_2() {
        assertEquals(serviceInterface, name.getServiceInterface());
    }

    @Test
    void testGetter_3() {
        assertEquals(version, name.getVersion());
    }

    @Test
    void testGetter_4() {
        assertEquals(group, name.getGroup());
    }

    @Test
    void testGetter_5() {
        assertEquals("providers:org.apache.dubbo.registry.nacos.NacosServiceName:1.0.0:default", name.getValue());
    }

    @Test
    void testIsConcrete_1() {
        assertTrue(name.isConcrete());
    }

    @Test
    void testIsConcrete_2_testMerged_2() {
        name.setGroup(WILDCARD);
        assertFalse(name.isConcrete());
    }

    @Test
    void testIsConcrete_4() {
        assertTrue(name.isConcrete());
    }
}
