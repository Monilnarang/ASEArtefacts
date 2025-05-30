package org.apache.druid.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Injector;
import com.google.inject.Key;
import org.apache.commons.io.IOUtils;
import org.apache.druid.common.utils.SocketUtil;
import org.apache.druid.discovery.DruidLeaderSelector;
import org.apache.druid.guice.GuiceInjectors;
import org.apache.druid.guice.JsonConfigProvider;
import org.apache.druid.guice.LazySingleton;
import org.apache.druid.guice.LifecycleModule;
import org.apache.druid.guice.annotations.Self;
import org.apache.druid.guice.http.DruidHttpClientConfig;
import org.apache.druid.initialization.Initialization;
import org.apache.druid.java.util.common.StringUtils;
import org.apache.druid.server.initialization.BaseJettyTest;
import org.apache.druid.server.initialization.ServerConfig;
import org.apache.druid.server.initialization.jetty.JettyServerInitUtils;
import org.apache.druid.server.initialization.jetty.JettyServerInitializer;
import org.apache.druid.server.security.AllowAllAuthenticator;
import org.apache.druid.server.security.AllowAllAuthorizer;
import org.apache.druid.server.security.AuthenticationUtils;
import org.apache.druid.server.security.AuthorizerMapper;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.zip.Deflater;

public class AsyncManagementForwardingServletTest_Purified extends BaseJettyTest {

    private static final ExpectedRequest COORDINATOR_EXPECTED_REQUEST = new ExpectedRequest();

    private static final ExpectedRequest OVERLORD_EXPECTED_REQUEST = new ExpectedRequest();

    private static int coordinatorPort;

    private static int overlordPort;

    private static boolean isValidLeader;

    private Server coordinator;

    private Server overlord;

    private static class ExpectedRequest {

        private boolean called = false;

        private String path;

        private String query;

        private String method;

        private Map<String, String> headers;

        private String body;

        private void reset() {
            called = false;
            path = null;
            query = null;
            method = null;
            headers = null;
            body = null;
        }
    }

    @Override
    @Before
    public void setup() throws Exception {
        super.setup();
        coordinatorPort = SocketUtil.findOpenPortFrom(port + 1);
        overlordPort = SocketUtil.findOpenPortFrom(coordinatorPort + 1);
        coordinator = makeTestServer(coordinatorPort, COORDINATOR_EXPECTED_REQUEST);
        overlord = makeTestServer(overlordPort, OVERLORD_EXPECTED_REQUEST);
        coordinator.start();
        overlord.start();
        isValidLeader = true;
    }

    @After
    public void tearDown() throws Exception {
        coordinator.stop();
        overlord.stop();
        COORDINATOR_EXPECTED_REQUEST.reset();
        OVERLORD_EXPECTED_REQUEST.reset();
        isValidLeader = true;
    }

    @Override
    protected Injector setupInjector() {
        return Initialization.makeInjectorWithModules(GuiceInjectors.makeStartupInjector(), ImmutableList.of((binder) -> {
            JsonConfigProvider.bindInstance(binder, Key.get(DruidNode.class, Self.class), new DruidNode("test", "localhost", false, null, null, true, false));
            binder.bind(JettyServerInitializer.class).to(ProxyJettyServerInit.class).in(LazySingleton.class);
            LifecycleModule.register(binder, Server.class);
        }));
    }

    private static Server makeTestServer(int port, ExpectedRequest expectedRequest) {
        Server server = new Server(port);
        ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(new ServletHolder(new HttpServlet() {

            @Override
            protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
                handle(req, resp);
            }

            @Override
            protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
                handle(req, resp);
            }

            @Override
            protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
                handle(req, resp);
            }

            @Override
            protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
                handle(req, resp);
            }

            private void handle(HttpServletRequest req, HttpServletResponse resp) throws IOException {
                boolean passed = expectedRequest.path.equals(req.getRequestURI());
                passed &= expectedRequest.query == null || expectedRequest.query.equals(req.getQueryString());
                passed &= expectedRequest.method.equals(req.getMethod());
                if (expectedRequest.headers != null) {
                    for (Map.Entry<String, String> header : expectedRequest.headers.entrySet()) {
                        passed &= header.getValue().equals(req.getHeader(header.getKey()));
                    }
                }
                passed &= expectedRequest.body == null || expectedRequest.body.equals(IOUtils.toString(req.getReader()));
                expectedRequest.called = true;
                resp.setStatus(passed ? 200 : 400);
            }
        }), "/*");
        server.setHandler(handler);
        return server;
    }

    public static class ProxyJettyServerInit implements JettyServerInitializer {

        @Override
        public void initialize(Server server, Injector injector) {
            final ServletContextHandler root = new ServletContextHandler(ServletContextHandler.SESSIONS);
            root.addServlet(new ServletHolder(new DefaultServlet()), "/*");
            final DruidLeaderSelector coordinatorLeaderSelector = new TestDruidLeaderSelector() {

                @Override
                public String getCurrentLeader() {
                    if (isValidLeader) {
                        return StringUtils.format("http://localhost:%d", coordinatorPort);
                    } else {
                        return null;
                    }
                }
            };
            final DruidLeaderSelector overlordLeaderSelector = new TestDruidLeaderSelector() {

                @Override
                public String getCurrentLeader() {
                    if (isValidLeader) {
                        return StringUtils.format("http://localhost:%d", overlordPort);
                    } else {
                        return null;
                    }
                }
            };
            ServletHolder holder = new ServletHolder(new AsyncManagementForwardingServlet(injector.getInstance(ObjectMapper.class), injector.getProvider(HttpClient.class), injector.getInstance(DruidHttpClientConfig.class), coordinatorLeaderSelector, overlordLeaderSelector, new AuthorizerMapper(ImmutableMap.of("allowAll", new AllowAllAuthorizer()))));
            holder.setInitParameter("maxThreads", "256");
            root.addServlet(holder, "/druid/coordinator/*");
            root.addServlet(holder, "/druid/indexer/*");
            root.addServlet(holder, "/proxy/*");
            AuthenticationUtils.addAuthenticationFilterChain(root, ImmutableList.of(new AllowAllAuthenticator()));
            JettyServerInitUtils.addExtensionFilters(root, injector);
            final HandlerList handlerList = new HandlerList();
            handlerList.setHandlers(new Handler[] { JettyServerInitUtils.wrapWithDefaultGzipHandler(root, ServerConfig.DEFAULT_GZIP_INFLATE_BUFFER_SIZE, Deflater.DEFAULT_COMPRESSION) });
            server.setHandler(handlerList);
        }
    }

    private static class TestDruidLeaderSelector implements DruidLeaderSelector {

        @Nullable
        @Override
        public String getCurrentLeader() {
            return null;
        }

        @Override
        public boolean isLeader() {
            return false;
        }

        @Override
        public int localTerm() {
            return 0;
        }

        @Override
        public void registerListener(Listener listener) {
        }

        @Override
        public void unregisterListener() {
        }
    }

    @Test
    public void testCoordinatorEnable_1_testMerged_1() throws Exception {
        HttpURLConnection connection = ((HttpURLConnection) new URL(StringUtils.format("http://localhost:%d%s", port, COORDINATOR_EXPECTED_REQUEST.path)).openConnection());
        connection.setRequestMethod(COORDINATOR_EXPECTED_REQUEST.method);
        Assert.assertEquals(200, connection.getResponseCode());
        Assert.assertTrue("coordinator called", COORDINATOR_EXPECTED_REQUEST.called);
    }

    @Test
    public void testCoordinatorEnable_3() throws Exception {
        Assert.assertFalse("overlord called", OVERLORD_EXPECTED_REQUEST.called);
    }

    @Test
    public void testCoordinatorDisable_1_testMerged_1() throws Exception {
        HttpURLConnection connection = ((HttpURLConnection) new URL(StringUtils.format("http://localhost:%d%s", port, COORDINATOR_EXPECTED_REQUEST.path)).openConnection());
        connection.setRequestMethod(COORDINATOR_EXPECTED_REQUEST.method);
        Assert.assertEquals(200, connection.getResponseCode());
        Assert.assertTrue("coordinator called", COORDINATOR_EXPECTED_REQUEST.called);
    }

    @Test
    public void testCoordinatorDisable_3() throws Exception {
        Assert.assertFalse("overlord called", OVERLORD_EXPECTED_REQUEST.called);
    }

    @Test
    public void testBadProxyDestination_1() throws Exception {
        HttpURLConnection connection = ((HttpURLConnection) new URL(StringUtils.format("http://localhost:%d/proxy/other/status", port)).openConnection());
        connection.setRequestMethod("GET");
        Assert.assertEquals(400, connection.getResponseCode());
    }

    @Test
    public void testBadProxyDestination_2() throws Exception {
        Assert.assertFalse("coordinator called", COORDINATOR_EXPECTED_REQUEST.called);
    }

    @Test
    public void testBadProxyDestination_3() throws Exception {
        Assert.assertFalse("overlord called", OVERLORD_EXPECTED_REQUEST.called);
    }

    @Test
    public void testCoordinatorNoPath_1() throws Exception {
        HttpURLConnection connection = ((HttpURLConnection) new URL(StringUtils.format("http://localhost:%d/proxy/coordinator", port)).openConnection());
        connection.setRequestMethod("GET");
        Assert.assertEquals(403, connection.getResponseCode());
    }

    @Test
    public void testCoordinatorNoPath_2() throws Exception {
        Assert.assertFalse("coordinator called", COORDINATOR_EXPECTED_REQUEST.called);
    }

    @Test
    public void testCoordinatorNoPath_3() throws Exception {
        Assert.assertFalse("overlord called", OVERLORD_EXPECTED_REQUEST.called);
    }

    @Test
    public void testOverlordNoPath_1() throws Exception {
        HttpURLConnection connection = ((HttpURLConnection) new URL(StringUtils.format("http://localhost:%d/proxy/overlord", port)).openConnection());
        connection.setRequestMethod("GET");
        Assert.assertEquals(403, connection.getResponseCode());
    }

    @Test
    public void testOverlordNoPath_2() throws Exception {
        Assert.assertFalse("coordinator called", COORDINATOR_EXPECTED_REQUEST.called);
    }

    @Test
    public void testOverlordNoPath_3() throws Exception {
        Assert.assertFalse("overlord called", OVERLORD_EXPECTED_REQUEST.called);
    }

    @Test
    public void testCoordinatorLeaderUnknown_1() throws Exception {
        HttpURLConnection connection = ((HttpURLConnection) new URL(StringUtils.format("http://localhost:%d/druid/coordinator/status", port)).openConnection());
        connection.setRequestMethod("GET");
        Assert.assertEquals(503, connection.getResponseCode());
    }

    @Test
    public void testCoordinatorLeaderUnknown_2() throws Exception {
        Assert.assertFalse("coordinator called", COORDINATOR_EXPECTED_REQUEST.called);
    }

    @Test
    public void testCoordinatorLeaderUnknown_3() throws Exception {
        Assert.assertFalse("overlord called", OVERLORD_EXPECTED_REQUEST.called);
    }

    @Test
    public void testOverlordLeaderUnknown_1() throws Exception {
        HttpURLConnection connection = ((HttpURLConnection) new URL(StringUtils.format("http://localhost:%d/druid/indexer/status", port)).openConnection());
        connection.setRequestMethod("GET");
        Assert.assertEquals(503, connection.getResponseCode());
    }

    @Test
    public void testOverlordLeaderUnknown_2() throws Exception {
        Assert.assertFalse("coordinator called", COORDINATOR_EXPECTED_REQUEST.called);
    }

    @Test
    public void testOverlordLeaderUnknown_3() throws Exception {
        Assert.assertFalse("overlord called", OVERLORD_EXPECTED_REQUEST.called);
    }

    @Test
    public void testUnsupportedProxyDestination_1() throws Exception {
        HttpURLConnection connection = ((HttpURLConnection) new URL(StringUtils.format("http://localhost:%d/proxy/other/status2", port)).openConnection());
        connection.setRequestMethod("GET");
        Assert.assertEquals(400, connection.getResponseCode());
    }

    @Test
    public void testUnsupportedProxyDestination_2() throws Exception {
        Assert.assertFalse("coordinator called", COORDINATOR_EXPECTED_REQUEST.called);
    }

    @Test
    public void testUnsupportedProxyDestination_3() throws Exception {
        Assert.assertFalse("overlord called", OVERLORD_EXPECTED_REQUEST.called);
    }

    @Test
    public void testLocalRequest_1() throws Exception {
        HttpURLConnection connection = ((HttpURLConnection) new URL(StringUtils.format("http://localhost:%d/status", port)).openConnection());
        connection.setRequestMethod("GET");
        Assert.assertEquals(404, connection.getResponseCode());
    }

    @Test
    public void testLocalRequest_2() throws Exception {
        Assert.assertFalse("coordinator called", COORDINATOR_EXPECTED_REQUEST.called);
    }

    @Test
    public void testLocalRequest_3() throws Exception {
        Assert.assertFalse("overlord called", OVERLORD_EXPECTED_REQUEST.called);
    }
}
