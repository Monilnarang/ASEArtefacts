package org.apache.dubbo.config;

import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.rpc.model.FrameworkModel;
import org.apache.dubbo.rpc.model.ModuleModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConfigScopeModelInitializerTest_Purified {

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
    void test_1() {
        Assertions.assertNotNull(applicationModel.getDeployer());
    }

    @Test
    void test_2() {
        Assertions.assertNotNull(moduleModel.getDeployer());
    }
}
