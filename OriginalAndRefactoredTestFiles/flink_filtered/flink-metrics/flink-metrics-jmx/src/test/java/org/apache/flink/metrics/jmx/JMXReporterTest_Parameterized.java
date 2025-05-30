package org.apache.flink.metrics.jmx;

import org.apache.flink.management.jmx.JMXService;
import org.apache.flink.metrics.Gauge;
import org.apache.flink.metrics.HistogramStatistics;
import org.apache.flink.metrics.MetricGroup;
import org.apache.flink.metrics.reporter.MetricReporter;
import org.apache.flink.metrics.util.TestHistogram;
import org.apache.flink.metrics.util.TestMeter;
import org.apache.flink.metrics.util.TestMetricGroup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanServer;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import static org.apache.flink.metrics.jmx.JMXReporter.JMX_DOMAIN_PREFIX;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class JMXReporterTest_Parameterized {

    private static final Map<String, String> variables;

    private static final MetricGroup metricGroup;

    static {
        variables = new HashMap<>();
        variables.put("<host>", "localhost");
        metricGroup = TestMetricGroup.newBuilder().setLogicalScopeFunction((characterFilter, character) -> "taskmanager").setVariables(variables).build();
    }

    @AfterEach
    void shutdownService() throws IOException {
        JMXService.stopInstance();
    }

    @ParameterizedTest
    @MethodSource("Provider_testReplaceInvalidChars_1to13")
    void testReplaceInvalidChars_1to13(String param1, String param2) {
        assertThat(JMXReporter.replaceInvalidChars(param2)).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_testReplaceInvalidChars_1to13() {
        return Stream.of(arguments("", ""), arguments("abc", "abc"), arguments("abc", "abc\""), arguments("abc", "\"abc"), arguments("abc", "\"abc\""), arguments("abc", "\"a\"b\"c\""), arguments("", "\"\"\"\""), arguments("____", "    "), arguments("ab_-(c)-", "\"ab ;(c)'"), arguments("a_b_c", "a b c"), arguments("a_b_c_", "a b c "), arguments("a-b-c-", "a;b'c*"), arguments("a------b------c", "a,=;:?'b,=;:?'c"));
    }
}
