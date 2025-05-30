package org.apache.flink.configuration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class GlobalConfigurationTest_Purified {

    @TempDir
    private File tmpDir;

    @Test
    void testHiddenKey_1() {
        assertThat(GlobalConfiguration.isSensitive("password123")).isTrue();
    }

    @Test
    void testHiddenKey_2() {
        assertThat(GlobalConfiguration.isSensitive("123pasSword")).isTrue();
    }

    @Test
    void testHiddenKey_3() {
        assertThat(GlobalConfiguration.isSensitive("PasSword")).isTrue();
    }

    @Test
    void testHiddenKey_4() {
        assertThat(GlobalConfiguration.isSensitive("Secret")).isTrue();
    }

    @Test
    void testHiddenKey_5() {
        assertThat(GlobalConfiguration.isSensitive("polaris.client-secret")).isTrue();
    }

    @Test
    void testHiddenKey_6() {
        assertThat(GlobalConfiguration.isSensitive("client-secret")).isTrue();
    }

    @Test
    void testHiddenKey_7() {
        assertThat(GlobalConfiguration.isSensitive("service-key-json")).isTrue();
    }

    @Test
    void testHiddenKey_8() {
        assertThat(GlobalConfiguration.isSensitive("auth.basic.password")).isTrue();
    }

    @Test
    void testHiddenKey_9() {
        assertThat(GlobalConfiguration.isSensitive("auth.basic.token")).isTrue();
    }

    @Test
    void testHiddenKey_10() {
        assertThat(GlobalConfiguration.isSensitive("avro-confluent.basic-auth.user-info")).isTrue();
    }

    @Test
    void testHiddenKey_11() {
        assertThat(GlobalConfiguration.isSensitive("key.avro-confluent.basic-auth.user-info")).isTrue();
    }

    @Test
    void testHiddenKey_12() {
        assertThat(GlobalConfiguration.isSensitive("value.avro-confluent.basic-auth.user-info")).isTrue();
    }

    @Test
    void testHiddenKey_13() {
        assertThat(GlobalConfiguration.isSensitive("kafka.jaas.config")).isTrue();
    }

    @Test
    void testHiddenKey_14() {
        assertThat(GlobalConfiguration.isSensitive("properties.ssl.truststore.password")).isTrue();
    }

    @Test
    void testHiddenKey_15() {
        assertThat(GlobalConfiguration.isSensitive("properties.ssl.keystore.password")).isTrue();
    }

    @Test
    void testHiddenKey_16() {
        assertThat(GlobalConfiguration.isSensitive("fs.azure.account.key.storageaccount123456.core.windows.net")).isTrue();
    }

    @Test
    void testHiddenKey_17() {
        assertThat(GlobalConfiguration.isSensitive("Hello")).isFalse();
    }

    @Test
    void testHiddenKey_18() {
        assertThat(GlobalConfiguration.isSensitive("metrics.reporter.dghttp.apikey")).isTrue();
    }
}
