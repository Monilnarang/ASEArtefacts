package org.apache.flink.metrics.slf4j;

import org.apache.flink.metrics.Gauge;
import org.apache.flink.metrics.Histogram;
import org.apache.flink.metrics.Meter;
import org.apache.flink.metrics.MeterView;
import org.apache.flink.metrics.MetricConfig;
import org.apache.flink.metrics.MetricGroup;
import org.apache.flink.metrics.SimpleCounter;
import org.apache.flink.metrics.util.TestHistogram;
import org.apache.flink.metrics.util.TestMetricGroup;
import org.apache.flink.testutils.logging.LoggerAuditingExtension;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.slf4j.event.Level;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class Slf4jReporterTest_Parameterized {

    private static final String SCOPE = "scope";

    private static char delimiter;

    private static MetricGroup metricGroup;

    private Slf4jReporter reporter;

    @RegisterExtension
    private final LoggerAuditingExtension testLoggerResource = new LoggerAuditingExtension(Slf4jReporter.class, Level.INFO);

    @BeforeAll
    static void setUp() {
        delimiter = '.';
        metricGroup = TestMetricGroup.newBuilder().setMetricIdentifierFunction((s, characterFilter) -> SCOPE + delimiter + s).build();
    }

    @BeforeEach
    void setUpReporter() {
        reporter = new Slf4jReporter();
        reporter.open(new MetricConfig());
    }

    @ParameterizedTest
    @MethodSource("Provider_testFilterCharacters_1to3")
    void testFilterCharacters_1to3(String param1, String param2) throws Exception {
        assertThat(reporter.filterCharacters(param2)).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_testFilterCharacters_1to3() {
        return Stream.of(arguments("", ""), arguments("abc", "abc"), arguments("a:b$%^::", "a:b$%^::"));
    }
}
