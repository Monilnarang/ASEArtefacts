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

class Slf4jReporterTest_Purified {

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

    @Test
    void testFilterCharacters_1() throws Exception {
        assertThat(reporter.filterCharacters("")).isEqualTo("");
    }

    @Test
    void testFilterCharacters_2() throws Exception {
        assertThat(reporter.filterCharacters("abc")).isEqualTo("abc");
    }

    @Test
    void testFilterCharacters_3() throws Exception {
        assertThat(reporter.filterCharacters("a:b$%^::")).isEqualTo("a:b$%^::");
    }
}
