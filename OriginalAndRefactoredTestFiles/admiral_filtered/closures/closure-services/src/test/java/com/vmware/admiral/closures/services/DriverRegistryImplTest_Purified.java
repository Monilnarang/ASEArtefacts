package com.vmware.admiral.closures.services;

import static org.junit.Assert.assertNotNull;
import java.util.Map;
import java.util.function.Consumer;
import org.junit.Before;
import org.junit.Test;
import com.vmware.admiral.closures.drivers.DriverConstants;
import com.vmware.admiral.closures.drivers.DriverRegistry;
import com.vmware.admiral.closures.drivers.DriverRegistryImpl;
import com.vmware.admiral.closures.drivers.ExecutionDriver;
import com.vmware.admiral.closures.services.closure.Closure;
import com.vmware.admiral.closures.services.closuredescription.ClosureDescription;
import com.vmware.xenon.common.ServiceHost;

public class DriverRegistryImplTest_Purified {

    private DriverRegistry registry;

    @Before
    public void setUp() throws Exception {
        registry = new DriverRegistryImpl();
        Map<String, String> runtimes = registry.getSupportedRuntimes();
        runtimes.forEach((r, image) -> registry.register(r, new ExecutionDriver() {

            @Override
            public void executeClosure(Closure closure, ClosureDescription closureDescription, String token, Consumer<Throwable> errorHandler) {
            }

            @Override
            public void cleanClosure(Closure closure, Consumer<Throwable> errorHandler) {
            }

            @Override
            public void cleanImage(String imageName, String computeStateLink, Consumer<Throwable> errorHandler) {
            }

            @Override
            public void inspectImage(String imageName, String computeStateLink, Consumer<Throwable> errorHandler) {
            }

            @Override
            public ServiceHost getServiceHost() {
                return null;
            }
        }));
    }

    @Test
    public void testSupportedRuntime_1() {
        assertNotNull(registry.getDriver(DriverConstants.RUNTIME_NODEJS_4));
    }

    @Test
    public void testSupportedRuntime_2() {
        assertNotNull(registry.getDriver(DriverConstants.RUNTIME_JAVA_8));
    }

    @Test
    public void testSupportedRuntime_3() {
        assertNotNull(registry.getDriver(DriverConstants.RUNTIME_POWERSHELL_6));
    }

    @Test
    public void testSupportedRuntime_4() {
        assertNotNull(registry.getDriver(DriverConstants.RUNTIME_PYTHON_3));
    }

    @Test
    public void testSupportedRuntimeVersion_1() {
        assertNotNull(registry.getImageVersion(DriverConstants.RUNTIME_NODEJS_4));
    }

    @Test
    public void testSupportedRuntimeVersion_2() {
        assertNotNull(registry.getImageVersion(DriverConstants.RUNTIME_JAVA_8));
    }

    @Test
    public void testSupportedRuntimeVersion_3() {
        assertNotNull(registry.getImageVersion(DriverConstants.RUNTIME_POWERSHELL_6));
    }

    @Test
    public void testSupportedRuntimeVersion_4() {
        assertNotNull(registry.getImageVersion(DriverConstants.RUNTIME_PYTHON_3));
    }

    @Test
    public void testSupportedRuntimeVersion_5() {
        assertNotNull(registry.getBaseImageVersion(DriverConstants.RUNTIME_NODEJS_4));
    }

    @Test
    public void testSupportedRuntimeVersion_6() {
        assertNotNull(registry.getBaseImageVersion(DriverConstants.RUNTIME_JAVA_8));
    }

    @Test
    public void testSupportedRuntimeVersion_7() {
        assertNotNull(registry.getBaseImageVersion(DriverConstants.RUNTIME_POWERSHELL_6));
    }

    @Test
    public void testSupportedRuntimeVersion_8() {
        assertNotNull(registry.getBaseImageVersion(DriverConstants.RUNTIME_PYTHON_3));
    }
}
