package org.apache.flink.client.program.rest;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.configuration.RestOptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import static org.assertj.core.api.Assertions.assertThat;

class RestClusterClientConfigurationTest_Purified {

    private RestClusterClientConfiguration restClusterClientConfiguration;

    @BeforeEach
    void setUp() throws Exception {
        final Configuration config = new Configuration();
        config.set(RestOptions.AWAIT_LEADER_TIMEOUT, Duration.ofMillis(1L));
        config.set(RestOptions.RETRY_MAX_ATTEMPTS, 2);
        config.set(RestOptions.RETRY_DELAY, Duration.ofMillis(3L));
        restClusterClientConfiguration = RestClusterClientConfiguration.fromConfiguration(config);
    }

    @Test
    void testConfiguration_1() {
        assertThat(restClusterClientConfiguration.getAwaitLeaderTimeout()).isEqualTo(1);
    }

    @Test
    void testConfiguration_2() {
        assertThat(restClusterClientConfiguration.getRetryMaxAttempts()).isEqualTo(2);
    }

    @Test
    void testConfiguration_3() {
        assertThat(restClusterClientConfiguration.getRetryDelay()).isEqualTo(3);
    }
}
