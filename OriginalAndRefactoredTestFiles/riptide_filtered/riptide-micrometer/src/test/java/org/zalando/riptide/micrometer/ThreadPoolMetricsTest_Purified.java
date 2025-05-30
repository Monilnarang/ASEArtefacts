package org.zalando.riptide.micrometer;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import static java.util.concurrent.TimeUnit.MINUTES;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.zalando.fauxpas.FauxPas.throwingRunnable;

final class ThreadPoolMetricsTest_Purified {

    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 3, 1, MINUTES, new LinkedBlockingQueue<>(1));

    private final SimpleMeterRegistry registry = new SimpleMeterRegistry();

    @BeforeEach
    void setUp() {
        new ThreadPoolMetrics(executor).withMetricName("http.client.threads").withDefaultTags(Tag.of("application", "test")).bindTo(registry);
    }

    private Gauge gauge(final String name) {
        return registry.find(name).tag("application", "test").gauge();
    }

    @Test
    void shouldMeasureInitial_1() {
        assertThat(gauge("http.client.threads.available").value(), is(0.0));
    }

    @Test
    void shouldMeasureInitial_2() {
        assertThat(gauge("http.client.threads.leased").value(), is(0.0));
    }

    @Test
    void shouldMeasureInitial_3() {
        assertThat(gauge("http.client.threads.total").value(), is(0.0));
    }

    @Test
    void shouldMeasureInitial_4() {
        assertThat(gauge("http.client.threads.min").value(), is(1.0));
    }

    @Test
    void shouldMeasureInitial_5() {
        assertThat(gauge("http.client.threads.max").value(), is(3.0));
    }

    @Test
    void shouldMeasureInitial_6() {
        assertThat(gauge("http.client.threads.queued").value(), is(0.0));
    }
}
