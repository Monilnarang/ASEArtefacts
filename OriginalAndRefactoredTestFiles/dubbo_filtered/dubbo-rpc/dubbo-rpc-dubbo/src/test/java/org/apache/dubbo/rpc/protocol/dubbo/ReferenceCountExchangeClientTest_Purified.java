package org.apache.dubbo.rpc.protocol.dubbo;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.common.utils.DubboAppender;
import org.apache.dubbo.common.utils.LogUtil;
import org.apache.dubbo.common.utils.NetUtils;
import org.apache.dubbo.remoting.exchange.ExchangeClient;
import org.apache.dubbo.rpc.Exporter;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Protocol;
import org.apache.dubbo.rpc.ProxyFactory;
import org.apache.dubbo.rpc.protocol.dubbo.support.ProtocolUtils;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.apache.dubbo.remoting.Constants.CONNECTIONS_KEY;
import static org.apache.dubbo.rpc.protocol.dubbo.Constants.LAZY_REQUEST_WITH_WARNING_KEY;
import static org.apache.dubbo.rpc.protocol.dubbo.Constants.SHARE_CONNECTIONS_KEY;

class ReferenceCountExchangeClientTest_Purified {

    public static ProxyFactory proxy = ExtensionLoader.getExtensionLoader(ProxyFactory.class).getAdaptiveExtension();

    Exporter<?> demoExporter;

    Exporter<?> helloExporter;

    Invoker<IDemoService> demoServiceInvoker;

    Invoker<IHelloService> helloServiceInvoker;

    IDemoService demoService;

    IHelloService helloService;

    ExchangeClient demoClient;

    ExchangeClient helloClient;

    String errorMsg = "safe guard client , should not be called ,must have a bug";

    @BeforeAll
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterAll
    public static void tearDownAfterClass() {
        ProtocolUtils.closeAll();
    }

    public static Invoker<?> referInvoker(Class<?> type, URL url) {
        return DubboProtocol.getDubboProtocol().refer(type, url);
    }

    public static <T> Exporter<T> export(T instance, Class<T> type, String url) {
        return export(instance, type, URL.valueOf(url));
    }

    public static <T> Exporter<T> export(T instance, Class<T> type, URL url) {
        return ExtensionLoader.getExtensionLoader(Protocol.class).getExtension(DubboProtocol.NAME).export(proxy.getInvoker(instance, type, url));
    }

    @BeforeEach
    public void setUp() throws Exception {
    }

    @SuppressWarnings("unchecked")
    private void init(int connections, int shareConnections) {
        Assertions.assertTrue(connections >= 0);
        Assertions.assertTrue(shareConnections >= 1);
        int port = NetUtils.getAvailablePort();
        String params = CONNECTIONS_KEY + "=" + connections + "&" + SHARE_CONNECTIONS_KEY + "=" + shareConnections + "&" + LAZY_REQUEST_WITH_WARNING_KEY + "=" + "true";
        URL demoUrl = URL.valueOf("dubbo://127.0.0.1:" + port + "/demo?" + params);
        URL helloUrl = URL.valueOf("dubbo://127.0.0.1:" + port + "/hello?" + params);
        demoExporter = export(new DemoServiceImpl(), IDemoService.class, demoUrl);
        helloExporter = export(new HelloServiceImpl(), IHelloService.class, helloUrl);
        demoServiceInvoker = (Invoker<IDemoService>) referInvoker(IDemoService.class, demoUrl);
        demoService = proxy.getProxy(demoServiceInvoker);
        Assertions.assertEquals("demo", demoService.demo());
        helloServiceInvoker = (Invoker<IHelloService>) referInvoker(IHelloService.class, helloUrl);
        helloService = proxy.getProxy(helloServiceInvoker);
        Assertions.assertEquals("hello", helloService.hello());
        demoClient = getClient(demoServiceInvoker);
        helloClient = getClient(helloServiceInvoker);
    }

    private void destroy() {
        demoServiceInvoker.destroy();
        helloServiceInvoker.destroy();
        demoExporter.getInvoker().destroy();
        helloExporter.getInvoker().destroy();
    }

    private ExchangeClient getClient(Invoker<?> invoker) {
        if (invoker.getUrl().getParameter(CONNECTIONS_KEY, 1) == 1) {
            return getInvokerClient(invoker);
        } else {
            ReferenceCountExchangeClient client = getReferenceClient(invoker);
            try {
                Field clientField = ReferenceCountExchangeClient.class.getDeclaredField("client");
                clientField.setAccessible(true);
                return (ExchangeClient) clientField.get(client);
            } catch (Exception e) {
                e.printStackTrace();
                Assertions.fail(e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    private ReferenceCountExchangeClient getReferenceClient(Invoker<?> invoker) {
        return getReferenceClientList(invoker).get(0);
    }

    private List<ReferenceCountExchangeClient> getReferenceClientList(Invoker<?> invoker) {
        List<ExchangeClient> invokerClientList = getInvokerClientList(invoker);
        List<ReferenceCountExchangeClient> referenceCountExchangeClientList = new ArrayList<>(invokerClientList.size());
        for (ExchangeClient exchangeClient : invokerClientList) {
            Assertions.assertTrue(exchangeClient instanceof ReferenceCountExchangeClient);
            referenceCountExchangeClientList.add((ReferenceCountExchangeClient) exchangeClient);
        }
        return referenceCountExchangeClientList;
    }

    private ExchangeClient getInvokerClient(Invoker<?> invoker) {
        return getInvokerClientList(invoker).get(0);
    }

    private List<ExchangeClient> getInvokerClientList(Invoker<?> invoker) {
        @SuppressWarnings("rawtypes")
        DubboInvoker dInvoker = (DubboInvoker) invoker;
        try {
            Field clientField = DubboInvoker.class.getDeclaredField("clientsProvider");
            clientField.setAccessible(true);
            ClientsProvider clientsProvider = (ClientsProvider) clientField.get(dInvoker);
            List<? extends ExchangeClient> clients = clientsProvider.getClients();
            List<ExchangeClient> clientList = new ArrayList<ExchangeClient>(clients.size());
            for (ExchangeClient client : clients) {
                clientList.add(client);
            }
            Collections.sort(clientList, Comparator.comparing(c -> Integer.valueOf(Objects.hashCode(c))));
            return clientList;
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public interface IDemoService {

        String demo();
    }

    public interface IHelloService {

        String hello();
    }

    public class DemoServiceImpl implements IDemoService {

        public String demo() {
            return "demo";
        }
    }

    public class HelloServiceImpl implements IHelloService {

        public String hello() {
            return "hello";
        }
    }

    @Test
    void test_share_connect_1() {
        Assertions.assertEquals(demoClient.getLocalAddress(), helloClient.getLocalAddress());
    }

    @Test
    void test_share_connect_2() {
        Assertions.assertEquals(demoClient, helloClient);
    }

    @Test
    void test_not_share_connect_1() {
        Assertions.assertNotSame(demoClient.getLocalAddress(), helloClient.getLocalAddress());
    }

    @Test
    void test_not_share_connect_2() {
        Assertions.assertNotSame(demoClient, helloClient);
    }

    @Test
    void test_multi_destroy_1() {
        Assertions.assertEquals("hello", helloService.hello());
    }

    @Test
    void test_multi_destroy_2() {
        Assertions.assertEquals(0, LogUtil.findMessage(errorMsg), "should not  warning message");
    }
}
