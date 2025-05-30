package org.apache.dubbo.rpc.model;

import org.apache.dubbo.rpc.support.MockScopeModelAware;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ScopeModelAwareExtensionProcessorTest_Purified {

    private FrameworkModel frameworkModel;

    private ApplicationModel applicationModel;

    private ModuleModel moduleModel;

    @BeforeEach
    public void setUp() {
        frameworkModel = new FrameworkModel();
        applicationModel = frameworkModel.newApplication();
        moduleModel = applicationModel.newModule();
    }

    @AfterEach
    public void reset() {
        frameworkModel.destroy();
    }

    @Test
    void testInitialize_1_testMerged_1() {
        ScopeModelAwareExtensionProcessor processor1 = new ScopeModelAwareExtensionProcessor(frameworkModel);
        Assertions.assertEquals(processor1.getFrameworkModel(), frameworkModel);
        Assertions.assertEquals(processor1.getScopeModel(), frameworkModel);
        Assertions.assertNull(processor1.getApplicationModel());
        Assertions.assertNull(processor1.getModuleModel());
    }

    @Test
    void testInitialize_5_testMerged_2() {
        ScopeModelAwareExtensionProcessor processor2 = new ScopeModelAwareExtensionProcessor(applicationModel);
        Assertions.assertEquals(processor2.getApplicationModel(), applicationModel);
        Assertions.assertEquals(processor2.getScopeModel(), applicationModel);
        Assertions.assertEquals(processor2.getFrameworkModel(), frameworkModel);
        Assertions.assertNull(processor2.getModuleModel());
    }

    @Test
    void testInitialize_9_testMerged_3() {
        ScopeModelAwareExtensionProcessor processor3 = new ScopeModelAwareExtensionProcessor(moduleModel);
        Assertions.assertEquals(processor3.getModuleModel(), moduleModel);
        Assertions.assertEquals(processor3.getScopeModel(), moduleModel);
    }
}
