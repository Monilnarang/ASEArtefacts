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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

final class ThreadPoolMetricsTest_Parameterized {

    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 3, 1, MINUTES, new LinkedBlockingQueue<>(1));

    private final SimpleMeterRegistry registry = new SimpleMeterRegistry();

    @BeforeEach
    void setUp() {
        new ThreadPoolMetrics(executor).withMetricName("http.client.threads").withDefaultTags(Tag.of("application", "test")).bindTo(registry);
    }

    private Gauge gauge(final String name) {
        return registry.find(name).tag("application", "test").gauge();
    }

    @ParameterizedTest
    @MethodSource("Provider_shouldMeasureInitial_1to6")
    void shouldMeasureInitial_1to6(double param1, String param2) {
        assertThat(gauge(param2).value(), is(param1));
    }

    static public Stream<Arguments> Provider_shouldMeasureInitial_1to6() {
        return Stream.of(arguments(0.0, "http.client.threads.available"), arguments(0.0, "http.client.threads.leased"), arguments(0.0, "http.client.threads.total"), arguments(1.0, "http.client.threads.min"), arguments(3.0, "http.client.threads.max"), arguments(0.0, "http.client.threads.queued"));
    }
}
