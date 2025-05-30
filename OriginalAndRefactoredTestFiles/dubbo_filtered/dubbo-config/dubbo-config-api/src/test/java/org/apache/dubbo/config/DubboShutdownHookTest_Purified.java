package org.apache.dubbo.config;

import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.rpc.model.FrameworkModel;
import org.apache.dubbo.rpc.model.ModuleModel;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static java.util.Arrays.asList;

public class DubboShutdownHookTest_Purified {

    private DubboShutdownHook dubboShutdownHook;

    private ApplicationModel applicationModel;

    @BeforeEach
    public void init() {
        SysProps.setProperty(CommonConstants.IGNORE_LISTEN_SHUTDOWN_HOOK, "false");
        FrameworkModel frameworkModel = new FrameworkModel();
        applicationModel = frameworkModel.newApplication();
        ModuleModel moduleModel = applicationModel.newModule();
        dubboShutdownHook = new DubboShutdownHook(applicationModel);
    }

    @AfterEach
    public void clear() {
        SysProps.clear();
    }

    @Test
    public void testDubboShutdownHook_1() {
        Assertions.assertNotNull(dubboShutdownHook);
    }

    @Test
    public void testDubboShutdownHook_2() {
        Assertions.assertLinesMatch(asList("DubboShutdownHook"), asList(dubboShutdownHook.getName()));
    }

    @Test
    public void testDubboShutdownHook_3() {
        Assertions.assertFalse(dubboShutdownHook.getRegistered());
    }
}
