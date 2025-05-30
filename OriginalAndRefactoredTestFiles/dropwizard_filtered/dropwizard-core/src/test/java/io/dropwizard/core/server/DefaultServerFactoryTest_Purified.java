package io.dropwizard.core.server;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.configuration.ConfigurationException;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.configuration.YamlConfigurationFactory;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.core.setup.ExceptionMapperBinder;
import io.dropwizard.jackson.DiscoverableSubtypeResolver;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.jetty.HttpConnectorFactory;
import io.dropwizard.jetty.ServerPushFilterFactory;
import io.dropwizard.logging.common.ConsoleAppenderFactory;
import io.dropwizard.logging.common.FileAppenderFactory;
import io.dropwizard.logging.common.SyslogAppenderFactory;
import io.dropwizard.validation.BaseValidator;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.eclipse.jetty.server.AbstractNetworkConnector;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.NetworkConnector;
import org.eclipse.jetty.server.Server;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import static com.codahale.metrics.annotation.ResponseMeteredLevel.ALL;
import static org.assertj.core.api.Assertions.assertThat;

class DefaultServerFactoryTest_Purified {

    private final Environment environment = new Environment("test");

    private DefaultServerFactory http;

    @BeforeEach
    void setUp() throws Exception {
        final ObjectMapper objectMapper = Jackson.newObjectMapper();
        objectMapper.getSubtypeResolver().registerSubtypes(ConsoleAppenderFactory.class, FileAppenderFactory.class, SyslogAppenderFactory.class, HttpConnectorFactory.class);
        http = new YamlConfigurationFactory<>(DefaultServerFactory.class, BaseValidator.newValidator(), objectMapper, "dw").build(new ResourceConfigurationSourceProvider(), "yaml/server.yml");
    }

    @Path("/test")
    @Produces("text/plain")
    public static class TestResource {

        private final CountDownLatch requestReceived;

        private final CountDownLatch shutdownInvoked;

        public TestResource(CountDownLatch requestReceived, CountDownLatch shutdownInvoked) {
            this.requestReceived = requestReceived;
            this.shutdownInvoked = shutdownInvoked;
        }

        @GET
        public String get() throws Exception {
            requestReceived.countDown();
            shutdownInvoked.await();
            return "test";
        }
    }

    @Test
    void defaultsDumpAfterStartFalse_1() {
        assertThat(http.getDumpAfterStart()).isFalse();
    }

    @Test
    void defaultsDumpAfterStartFalse_2() {
        assertThat(http.build(environment).isDumpAfterStart()).isFalse();
    }

    @Test
    void defaultsDumpBeforeStopFalse_1() {
        assertThat(http.getDumpBeforeStop()).isFalse();
    }

    @Test
    void defaultsDumpBeforeStopFalse_2() {
        assertThat(http.build(environment).isDumpBeforeStop()).isFalse();
    }
}
