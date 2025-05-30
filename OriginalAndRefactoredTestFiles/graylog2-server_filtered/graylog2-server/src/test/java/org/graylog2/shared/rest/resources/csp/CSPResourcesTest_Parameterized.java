package org.graylog2.shared.rest.resources.csp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class CSPResourcesTest_Parameterized {

    static CSPResources cspResources;

    @BeforeEach
    void setup() {
        cspResources = new CSPResources("/org/graylog2/security/cspTest.config");
    }

    @ParameterizedTest
    @MethodSource("Provider_loadPropertiesTest_1to2")
    void loadPropertiesTest_1to2(String param1, String param2) {
        assertThat(cspResources.cspString(param2)).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_loadPropertiesTest_1to2() {
        return Stream.of(arguments("connect-src url1.com:9999 url2.com;default-src 'self';img-src https://url3.com:9999 https://url4.com:9999;script-src 'self' 'unsafe-eval';style-src 'self' 'unsafe-inline'", "default"), arguments("connect-src url4.com;img-src https://url5.com:9999;script-src 'self' 'unsafe-eval' 'unsafe-inline';style-src 'self' 'unsafe-inline'", "swagger"));
    }
}
