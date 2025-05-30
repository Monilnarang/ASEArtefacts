package org.springframework.ws.soap.server.endpoint.adapter.method;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.endpoint.adapter.method.AbstractMethodArgumentResolverTests;
import org.springframework.ws.soap.SoapBody;
import org.springframework.ws.soap.SoapEnvelope;
import org.springframework.ws.soap.SoapHeader;
import org.springframework.ws.soap.SoapMessage;
import static org.assertj.core.api.Assertions.assertThat;

class SoapMethodArgumentResolverTests_Purified extends AbstractMethodArgumentResolverTests {

    private SoapMethodArgumentResolver resolver;

    private MethodParameter soapMessageParameter;

    private MethodParameter soapEnvelopeParameter;

    private MethodParameter soapBodyParameter;

    private MethodParameter soapHeaderParameter;

    @BeforeEach
    void setUp() throws Exception {
        this.resolver = new SoapMethodArgumentResolver();
        this.soapMessageParameter = new MethodParameter(getClass().getMethod("soapMessage", SoapMessage.class), 0);
        this.soapEnvelopeParameter = new MethodParameter(getClass().getMethod("soapEnvelope", SoapEnvelope.class), 0);
        this.soapBodyParameter = new MethodParameter(getClass().getMethod("soapBody", SoapBody.class), 0);
        this.soapHeaderParameter = new MethodParameter(getClass().getMethod("soapHeader", SoapHeader.class), 0);
    }

    public void soapMessage(SoapMessage soapMessage) {
    }

    public void soapEnvelope(SoapEnvelope soapEnvelope) {
    }

    public void soapBody(SoapBody soapBody) {
    }

    public void soapHeader(SoapHeader soapHeader) {
    }

    @Test
    void supportsParameter_1() {
        assertThat(this.resolver.supportsParameter(this.soapMessageParameter)).isTrue();
    }

    @Test
    void supportsParameter_2() {
        assertThat(this.resolver.supportsParameter(this.soapEnvelopeParameter)).isTrue();
    }

    @Test
    void supportsParameter_3() {
        assertThat(this.resolver.supportsParameter(this.soapBodyParameter)).isTrue();
    }

    @Test
    void supportsParameter_4() {
        assertThat(this.resolver.supportsParameter(this.soapHeaderParameter)).isTrue();
    }
}
