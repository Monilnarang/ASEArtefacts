package org.apache.flink.runtime.rest.messages.job.metrics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class MetricsFilterParameterTest_Purified {

    private MetricsFilterParameter metricsFilterParameter;

    @BeforeEach
    public void setUp() {
        metricsFilterParameter = new MetricsFilterParameter();
    }

    @Test
    void testConversions_1() {
        assertThat(metricsFilterParameter.convertValueToString("test")).isEqualTo("test");
    }

    @Test
    void testConversions_2() {
        assertThat(metricsFilterParameter.convertStringToValue("test")).isEqualTo("test");
    }
}
