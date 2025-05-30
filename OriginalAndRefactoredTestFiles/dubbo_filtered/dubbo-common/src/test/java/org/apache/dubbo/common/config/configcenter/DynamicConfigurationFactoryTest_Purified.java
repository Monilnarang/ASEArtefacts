package org.apache.dubbo.common.config.configcenter;

import org.apache.dubbo.common.config.configcenter.nop.NopDynamicConfigurationFactory;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DynamicConfigurationFactoryTest_Purified {

    private <T> ExtensionLoader<T> getExtensionLoader(Class<T> extClass) {
        return ApplicationModel.defaultModel().getDefaultModule().getExtensionLoader(extClass);
    }

    @Test
    void testDefaultExtension_1() {
        DynamicConfigurationFactory factory = getExtensionLoader(DynamicConfigurationFactory.class).getDefaultExtension();
        assertEquals(NopDynamicConfigurationFactory.class, factory.getClass());
    }

    @Test
    void testDefaultExtension_2() {
        assertEquals(NopDynamicConfigurationFactory.class, getExtensionLoader(DynamicConfigurationFactory.class).getExtension("nop").getClass());
    }
}
