package org.springframework.ws.server.endpoint.mapping;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ws.MockWebServiceMessage;
import org.springframework.ws.MockWebServiceMessageFactory;
import org.springframework.ws.context.DefaultMessageContext;
import org.springframework.ws.context.MessageContext;
import static org.assertj.core.api.Assertions.assertThat;

class SimpleMethodEndpointMappingTests_Purified {

    private SimpleMethodEndpointMapping mapping;

    @BeforeEach
    void setUp() throws Exception {
        this.mapping = new SimpleMethodEndpointMapping();
        this.mapping.setMethodPrefix("prefix");
        this.mapping.setMethodSuffix("Suffix");
        MyBean bean = new MyBean();
        this.mapping.setEndpoints(new Object[] { bean });
        this.mapping.afterPropertiesSet();
    }

    private static final class MyBean {

        public void prefixMyRequestSuffix() {
        }

        public void request() {
        }
    }

    @Test
    void testRegistration_1() {
        assertThat(this.mapping.lookupEndpoint("MyRequest")).isNotNull();
    }

    @Test
    void testRegistration_2() {
        assertThat(this.mapping.lookupEndpoint("request")).isNull();
    }
}
