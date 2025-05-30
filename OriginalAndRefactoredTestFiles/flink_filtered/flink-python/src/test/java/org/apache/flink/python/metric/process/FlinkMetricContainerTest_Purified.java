package org.apache.flink.python.metric.process;

import org.apache.flink.metrics.Counter;
import org.apache.flink.metrics.MeterView;
import org.apache.flink.metrics.MetricGroup;
import org.apache.flink.runtime.metrics.MetricRegistry;
import org.apache.flink.runtime.metrics.NoOpMetricRegistry;
import org.apache.flink.runtime.metrics.groups.GenericMetricGroup;
import org.apache.flink.runtime.metrics.groups.MetricGroupTest;
import org.apache.flink.runtime.metrics.util.InterceptingOperatorMetricGroup;
import org.apache.beam.model.pipeline.v1.MetricsApi.MonitoringInfo;
import org.apache.beam.runners.core.metrics.DistributionData;
import org.apache.beam.runners.core.metrics.GaugeData;
import org.apache.beam.runners.core.metrics.MonitoringInfoConstants;
import org.apache.beam.runners.core.metrics.SimpleMonitoringInfoBuilder;
import org.apache.beam.sdk.metrics.DistributionResult;
import org.apache.beam.sdk.metrics.MetricKey;
import org.apache.beam.sdk.metrics.MetricName;
import org.apache.beam.vendor.guava.v32_1_2_jre.com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class FlinkMetricContainerTest_Purified {

    private InterceptingOperatorMetricGroup metricGroup = new InterceptingOperatorMetricGroup();

    private FlinkMetricContainer container;

    private static final List<String> DEFAULT_SCOPE_COMPONENTS = Arrays.asList("key", "value", "MetricGroupType.key", "MetricGroupType.value");

    private static final String DEFAULT_NAMESPACE = "[\"key\", \"value\", \"MetricGroupType.key\", \"MetricGroupType.value\"]";

    @BeforeEach
    void beforeTest() {
        metricGroup = new InterceptingOperatorMetricGroup() {

            @Override
            public MetricGroup addGroup(int name) {
                return this;
            }

            @Override
            public MetricGroup addGroup(String name) {
                return this;
            }

            @Override
            public MetricGroup addGroup(String key, String value) {
                return this;
            }
        };
        container = new FlinkMetricContainer(metricGroup);
    }

    @Test
    void testCounterMonitoringInfoUpdate_1() {
        assertThat(metricGroup.get("myCounter")).isNull();
    }

    @Test
    void testCounterMonitoringInfoUpdate_2() {
        Counter userCounter = (Counter) metricGroup.get("myCounter");
        assertThat(userCounter.getCount()).isEqualTo(111L);
    }

    @Test
    void testMeterMonitoringInfoUpdate_1() {
        assertThat(metricGroup.get("myMeter")).isNull();
    }

    @Test
    void testMeterMonitoringInfoUpdate_2_testMerged_2() {
        MeterView userMeter = (MeterView) metricGroup.get("myMeter");
        userMeter.update();
        assertThat(userMeter.getCount()).isEqualTo(111L);
        assertThat(userMeter.getRate()).isEqualTo(1.85);
    }
}
