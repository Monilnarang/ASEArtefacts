package org.apache.dubbo.common;

import org.apache.dubbo.common.beans.factory.ScopeBeanFactory;
import org.apache.dubbo.common.config.ConfigurationCache;
import org.apache.dubbo.common.lang.ShutdownHookCallbacks;
import org.apache.dubbo.common.status.reporter.FrameworkStatusReportService;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.rpc.model.FrameworkModel;
import org.apache.dubbo.rpc.model.ModuleModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CommonScopeModelInitializerTest_Purified {

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
    void test_1_testMerged_1() {
        ScopeBeanFactory applicationModelBeanFactory = applicationModel.getBeanFactory();
        Assertions.assertNotNull(applicationModelBeanFactory.getBean(ShutdownHookCallbacks.class));
        Assertions.assertNotNull(applicationModelBeanFactory.getBean(FrameworkStatusReportService.class));
        Assertions.assertNotNull(applicationModelBeanFactory.getBean(ConfigurationCache.class));
    }

    @Test
    void test_4() {
        ScopeBeanFactory moduleModelBeanFactory = moduleModel.getBeanFactory();
        Assertions.assertNotNull(moduleModelBeanFactory.getBean(ConfigurationCache.class));
    }
}
