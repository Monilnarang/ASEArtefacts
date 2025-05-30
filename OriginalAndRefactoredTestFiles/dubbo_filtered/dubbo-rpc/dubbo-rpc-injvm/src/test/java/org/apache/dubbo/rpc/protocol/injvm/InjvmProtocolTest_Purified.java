package org.apache.dubbo.rpc.protocol.injvm;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.rpc.Exporter;
import org.apache.dubbo.rpc.FutureContext;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Protocol;
import org.apache.dubbo.rpc.ProxyFactory;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.model.ApplicationModel;
import org.apache.dubbo.rpc.model.FrameworkModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.apache.dubbo.common.constants.CommonConstants.APPLICATION_KEY;
import static org.apache.dubbo.common.constants.CommonConstants.GROUP_KEY;
import static org.apache.dubbo.common.constants.CommonConstants.INTERFACE_KEY;
import static org.apache.dubbo.common.constants.CommonConstants.VERSION_KEY;
import static org.apache.dubbo.rpc.Constants.ASYNC_KEY;
import static org.apache.dubbo.rpc.Constants.GENERIC_KEY;
import static org.apache.dubbo.rpc.Constants.LOCAL_PROTOCOL;
import static org.apache.dubbo.rpc.Constants.SCOPE_KEY;
import static org.apache.dubbo.rpc.Constants.SCOPE_LOCAL;
import static org.apache.dubbo.rpc.Constants.SCOPE_REMOTE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InjvmProtocolTest_Purified {

    private final Protocol protocol = ExtensionLoader.getExtensionLoader(Protocol.class).getAdaptiveExtension();

    private final ProxyFactory proxy = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getAdaptiveExtension();

    private final List<Exporter<?>> exporters = new ArrayList<>();

    @AfterEach
    public void after() throws Exception {
        for (Exporter<?> exporter : exporters) {
            exporter.unexport();
        }
        exporters.clear();
    }

    @Test
    void testLocalProtocolAsync_1_testMerged_1() throws ExecutionException, InterruptedException {
        DemoService service = new DemoServiceImpl();
        URL url = URL.valueOf("injvm://127.0.0.1/TestService").addParameter(ASYNC_KEY, true).addParameter(INTERFACE_KEY, DemoService.class.getName()).addParameter("application", "consumer").setScopeModel(ApplicationModel.defaultModel().getDefaultModule());
        Invoker<?> invoker = proxy.getInvoker(service, DemoService.class, url);
        assertTrue(invoker.isAvailable());
        Exporter<?> exporter = protocol.export(invoker);
        service = proxy.getProxy(protocol.refer(DemoService.class, url));
        assertNull(service.getAsyncResult());
    }

    @Test
    void testLocalProtocolAsync_3() throws ExecutionException, InterruptedException {
        assertEquals("DONE", FutureContext.getContext().getCompletableFuture().get());
    }

    @Test
    void testApplication_1_testMerged_1() {
        DemoService service = new DemoServiceImpl();
        URL url = URL.valueOf("injvm://127.0.0.1/TestService").addParameter(INTERFACE_KEY, DemoService.class.getName()).addParameter("application", "consumer").addParameter(APPLICATION_KEY, "test-app").setScopeModel(ApplicationModel.defaultModel().getDefaultModule());
        Invoker<?> invoker = proxy.getInvoker(service, DemoService.class, url);
        assertTrue(invoker.isAvailable());
        Exporter<?> exporter = protocol.export(invoker);
        service = proxy.getProxy(protocol.refer(DemoService.class, url));
        assertEquals("test-app", service.getApplication());
    }

    @Test
    void testApplication_3() {
        assertTrue(StringUtils.isEmpty(RpcContext.getServiceContext().getRemoteApplicationName()));
    }

    @Test
    void testRemoteAddress_1_testMerged_1() {
        DemoService service = new DemoServiceImpl();
        URL url = URL.valueOf("injvm://127.0.0.1/TestService").addParameter(INTERFACE_KEY, DemoService.class.getName()).addParameter("application", "consumer").addParameter(APPLICATION_KEY, "test-app").setScopeModel(ApplicationModel.defaultModel().getDefaultModule());
        Invoker<?> invoker = proxy.getInvoker(service, DemoService.class, url);
        assertTrue(invoker.isAvailable());
        Exporter<?> exporter = protocol.export(invoker);
        service = proxy.getProxy(protocol.refer(DemoService.class, url));
        assertEquals("127.0.0.1:0", service.getRemoteAddress());
    }

    @Test
    void testRemoteAddress_3() {
        assertNull(RpcContext.getServiceContext().getRemoteAddress());
    }
}
