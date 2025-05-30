package org.graylog2.shared.rest.resources.csp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class CSPResourcesTest_Purified {

    static CSPResources cspResources;

    @BeforeEach
    void setup() {
        cspResources = new CSPResources("/org/graylog2/security/cspTest.config");
    }

    @Test
    void loadPropertiesTest_1() {
        assertThat(cspResources.cspString("default")).isEqualTo("connect-src url1.com:9999 url2.com;default-src 'self';img-src https://url3.com:9999 https://url4.com:9999;script-src 'self' 'unsafe-eval';style-src 'self' 'unsafe-inline'");
    }

    @Test
    void loadPropertiesTest_2() {
        assertThat(cspResources.cspString("swagger")).isEqualTo("connect-src url4.com;img-src https://url5.com:9999;script-src 'self' 'unsafe-eval' 'unsafe-inline';style-src 'self' 'unsafe-inline'");
    }
}
