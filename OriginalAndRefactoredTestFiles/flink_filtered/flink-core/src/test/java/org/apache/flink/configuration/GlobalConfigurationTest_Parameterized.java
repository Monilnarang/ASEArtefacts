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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class GlobalConfigurationTest_Parameterized {

    @TempDir
    private File tmpDir;

    @Test
    void testHiddenKey_17() {
        assertThat(GlobalConfiguration.isSensitive("Hello")).isFalse();
    }

    @ParameterizedTest
    @MethodSource("Provider_testHiddenKey_1to16_18")
    void testHiddenKey_1to16_18(String param1) {
        assertThat(GlobalConfiguration.isSensitive(param1)).isTrue();
    }

    static public Stream<Arguments> Provider_testHiddenKey_1to16_18() {
        return Stream.of(arguments("password123"), arguments("123pasSword"), arguments("PasSword"), arguments("Secret"), arguments("polaris.client-secret"), arguments("client-secret"), arguments("service-key-json"), arguments("auth.basic.password"), arguments("auth.basic.token"), arguments("avro-confluent.basic-auth.user-info"), arguments("key.avro-confluent.basic-auth.user-info"), arguments("value.avro-confluent.basic-auth.user-info"), arguments("kafka.jaas.config"), arguments("properties.ssl.truststore.password"), arguments("properties.ssl.keystore.password"), arguments("fs.azure.account.key.storageaccount123456.core.windows.net"), arguments("metrics.reporter.dghttp.apikey"));
    }
}
