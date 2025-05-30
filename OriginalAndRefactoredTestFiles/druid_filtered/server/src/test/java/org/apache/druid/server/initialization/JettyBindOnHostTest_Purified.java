package org.apache.druid.server.initialization;

import com.google.common.collect.ImmutableList;
import com.google.inject.Binder;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import org.apache.commons.io.IOUtils;
import org.apache.druid.guice.GuiceInjectors;
import org.apache.druid.guice.Jerseys;
import org.apache.druid.guice.JsonConfigProvider;
import org.apache.druid.guice.LazySingleton;
import org.apache.druid.guice.LifecycleModule;
import org.apache.druid.guice.annotations.Self;
import org.apache.druid.initialization.Initialization;
import org.apache.druid.server.DruidNode;
import org.apache.druid.server.initialization.jetty.JettyServerInitializer;
import org.apache.druid.server.security.AuthTestUtils;
import org.apache.druid.server.security.AuthorizerMapper;
import org.eclipse.jetty.server.Server;
import org.junit.Assert;
import org.junit.Test;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class JettyBindOnHostTest_Purified extends BaseJettyTest {

    @Override
    protected Injector setupInjector() {
        return Initialization.makeInjectorWithModules(GuiceInjectors.makeStartupInjector(), ImmutableList.<Module>of(new Module() {

            @Override
            public void configure(Binder binder) {
                JsonConfigProvider.bindInstance(binder, Key.get(DruidNode.class, Self.class), new DruidNode("test", "localhost", true, null, null, true, false));
                binder.bind(JettyServerInitializer.class).to(JettyServerInit.class).in(LazySingleton.class);
                Jerseys.addResource(binder, DefaultResource.class);
                binder.bind(AuthorizerMapper.class).toInstance(AuthTestUtils.TEST_AUTHORIZER_MAPPER);
                LifecycleModule.register(binder, Server.class);
            }
        }));
    }

    @Test
    public void testBindOnHost_1() throws Exception {
        Assert.assertEquals("localhost", server.getURI().getHost());
    }

    @Test
    public void testBindOnHost_2_testMerged_2() throws Exception {
        final URL url = new URL("http://localhost:" + port + "/default");
        final HttpURLConnection get = (HttpURLConnection) url.openConnection();
        Assert.assertEquals(DEFAULT_RESPONSE_CONTENT, IOUtils.toString(get.getInputStream(), StandardCharsets.UTF_8));
        final HttpURLConnection post = (HttpURLConnection) url.openConnection();
        post.setRequestMethod("POST");
        Assert.assertEquals(DEFAULT_RESPONSE_CONTENT, IOUtils.toString(post.getInputStream(), StandardCharsets.UTF_8));
    }
}
