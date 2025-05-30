package org.zalando.riptide.autoconfigure.metrics;

import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.client.MockRestServiceServer;
import org.zalando.logbook.autoconfigure.LogbookAutoConfiguration;
import org.zalando.riptide.Http;
import org.zalando.riptide.autoconfigure.MetricsTestAutoConfiguration;
import org.zalando.riptide.autoconfigure.OpenTracingFlowIdAutoConfiguration;
import org.zalando.riptide.autoconfigure.OpenTracingTestAutoConfiguration;
import org.zalando.riptide.autoconfigure.RiptideClientTest;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.zalando.riptide.Bindings.anyStatus;
import static org.zalando.riptide.Bindings.on;
import static org.zalando.riptide.Navigators.status;
import static org.zalando.riptide.PassRoute.pass;
import static org.zalando.riptide.failsafe.RetryRoute.retry;
import static org.zalando.riptide.micrometer.MicrometerPlugin.TAGS;

@RiptideClientTest
@ActiveProfiles("default")
final class MetricsTest_Purified {

    @Configuration
    @ImportAutoConfiguration({ JacksonAutoConfiguration.class, LogbookAutoConfiguration.class, OpenTracingFlowIdAutoConfiguration.class, OpenTracingTestAutoConfiguration.class, MetricsTestAutoConfiguration.class })
    static class ContextConfiguration {
    }

    @Autowired
    @Qualifier("foo")
    private Http foo;

    @Autowired
    @Qualifier("bar")
    private Http bar;

    @Autowired
    private MockRestServiceServer server;

    @Autowired
    private SimpleMeterRegistry registry;

    @AfterEach
    void teardown() {
        registry.clear();
    }

    private Collection<Timer> timers(final String name, final Tag... tags) {
        return registry.find(name).tags(Arrays.asList(tags)).timers();
    }

    @Test
    void shouldRecordRequests_1() {
        assertEquals(2, timers("http.client.requests").size());
    }

    @Test
    void shouldRecordRequests_2_testMerged_2() {
        final Timer timer = registry.find("http.client.requests").tag("client_id", "foo").timer();
        assertNotNull(timer);
        assertEquals("GET", timer.getId().getTag("http.method"));
        final Timer timer = registry.find("http.client.requests").tag("client_id", "bar").timer();
    }

    @Test
    void shouldRecordRetries_1() {
        assertEquals(3, timers("http.client.requests", Tag.of("test", "retries")).size());
    }

    @Test
    void shouldRecordRetries_2_testMerged_2() {
        final Timer timer = registry.find("http.client.requests").tag("test", "retries").tag("retry_number", "0").timer();
        assertNotNull(timer);
        assertEquals("foo", timer.getId().getTag("client_id"));
        final Timer timer = registry.find("http.client.requests").tag("test", "retries").tag("retry_number", "1").timer();
        final Timer timer = registry.find("http.client.requests").tag("test", "retries").tag("retry_number", "2").timer();
    }

    @Test
    void shouldRecordCircuitBreakers_1() {
        assertEquals(2, timers("http.client.circuit-breakers").size());
    }

    @Test
    void shouldRecordCircuitBreakers_2_testMerged_2() {
        final Timer timer = registry.find("http.client.circuit-breakers").tag("state", "HALF_OPEN").timer();
        assertNotNull(timer);
        assertEquals("bar", timer.getId().getTag("client_id"));
        assertEquals(4, timer.count());
        final Timer timer = registry.find("http.client.circuit-breakers").tag("state", "OPEN").timer();
        assertEquals("OPEN", timer.getId().getTag("state"));
    }
}
