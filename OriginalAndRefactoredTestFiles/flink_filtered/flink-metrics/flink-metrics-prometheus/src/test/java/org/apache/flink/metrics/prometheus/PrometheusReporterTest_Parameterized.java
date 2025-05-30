package org.apache.flink.metrics.prometheus;

import org.apache.flink.metrics.Counter;
import org.apache.flink.metrics.Gauge;
import org.apache.flink.metrics.Histogram;
import org.apache.flink.metrics.Meter;
import org.apache.flink.metrics.Metric;
import org.apache.flink.metrics.MetricGroup;
import org.apache.flink.metrics.SimpleCounter;
import org.apache.flink.metrics.util.TestHistogram;
import org.apache.flink.metrics.util.TestMeter;
import org.apache.flink.util.PortRange;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PrometheusReporterTest_Parameterized {

    private static final String[] LABEL_NAMES = { "label1", "label2" };

    private static final String[] LABEL_VALUES = new String[] { "value1", "value2" };

    private static final String LOGICAL_SCOPE = "logical_scope";

    private static final String DIMENSIONS = String.format("%s=\"%s\",%s=\"%s\"", LABEL_NAMES[0], LABEL_VALUES[0], LABEL_NAMES[1], LABEL_VALUES[1]);

    private static final String DEFAULT_LABELS = "{" + DIMENSIONS + ",}";

    private static final String SCOPE_PREFIX = PrometheusReporter.SCOPE_PREFIX + LOGICAL_SCOPE + PrometheusReporter.SCOPE_SEPARATOR;

    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

    private static final PortRangeProvider portRangeProvider = new PortRangeProvider();

    private MetricGroup metricGroup;

    private PrometheusReporter reporter;

    @BeforeEach
    void setupReporter() {
        PortRange portRange = new PortRange(portRangeProvider.nextRange());
        reporter = new PrometheusReporter(portRange);
        metricGroup = TestUtils.createTestMetricGroup(LOGICAL_SCOPE, TestUtils.toMap(LABEL_NAMES, LABEL_VALUES));
    }

    @AfterEach
    void teardown() throws Exception {
        if (reporter != null) {
            reporter.close();
        }
    }

    private void assertThatGaugeIsExported(Metric metric, String name, String expectedValue) throws IOException, InterruptedException {
        assertThat(addMetricAndPollResponse(metric, name)).contains(createExpectedPollResponse(name, "", "gauge", expectedValue));
    }

    private String addMetricAndPollResponse(Metric metric, String metricName) throws IOException, InterruptedException {
        reporter.notifyOfAddedMetric(metric, metricName, metricGroup);
        return pollMetrics(reporter.getPort()).body();
    }

    static HttpResponse<String> pollMetrics(int port) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:" + port + "/metrics")).GET().build();
        return HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private static String createExpectedPollResponse(String name, String nameSuffix, String type, String value) {
        final String scopedName = SCOPE_PREFIX + name;
        return "" + String.format("# HELP %s %s (scope: %s)\n", scopedName, name, LOGICAL_SCOPE) + String.format("# TYPE %s %s\n", scopedName, type) + String.format("%s%s%s %s\n", scopedName, nameSuffix, DEFAULT_LABELS, value);
    }

    private static class PortRangeProvider {

        private int base = 9000;

        public String nextRange() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int lowEnd = base;
            int highEnd = base + 99;
            base += 100;
            return lowEnd + "-" + highEnd;
        }

        private boolean hasNext() {
            return base < 14000;
        }
    }

    @ParameterizedTest
    @MethodSource("Provider_invalidCharactersAreReplacedWithUnderscore_1to13")
    void invalidCharactersAreReplacedWithUnderscore_1to13(String param1, String param2) {
        assertThat(PrometheusReporter.replaceInvalidChars(param2)).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_invalidCharactersAreReplacedWithUnderscore_1to13() {
        return Stream.of(arguments("", ""), arguments("abc", "abc"), arguments("abc_", "abc\""), arguments("_abc", "\"abc"), arguments("_abc_", "\"abc\""), arguments("_a_b_c_", "\"a\"b\"c\""), arguments("____", "\"\"\"\""), arguments("____", "    "), arguments("_ab___c__", "\"ab ;(c)'"), arguments("a_b_c", "a b c"), arguments("a_b_c_", "a b c "), arguments("a_b_c_", "a;b'c*"), arguments("a___:__b___:__c", "a,=;:?'b,=;:?'c"));
    }
}
