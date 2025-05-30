package org.springframework.ws.soap.server.endpoint;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;
import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPConstants;
import jakarta.xml.soap.SOAPMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ws.context.DefaultMessageContext;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.SoapMessageException;
import org.springframework.ws.soap.SoapMessageFactory;
import org.springframework.ws.soap.SoapVersion;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.soap.soap11.Soap11Fault;
import org.springframework.ws.soap.soap12.Soap12Fault;
import static org.assertj.core.api.Assertions.assertThat;

class SoapFaultMappingExceptionResolverTests_Purified {

    private SoapFaultMappingExceptionResolver resolver;

    @BeforeEach
    void setUp() {
        this.resolver = new SoapFaultMappingExceptionResolver();
    }

    @Test
    void testGetDepth_1() {
        assertThat(this.resolver.getDepth("java.lang.Exception", new Exception())).isEqualTo(0);
    }

    @Test
    void testGetDepth_2() {
        assertThat(this.resolver.getDepth("java.lang.Exception", new IllegalArgumentException())).isEqualTo(2);
    }

    @Test
    void testGetDepth_3() {
        assertThat(this.resolver.getDepth("IllegalArgumentException", new IllegalStateException())).isEqualTo(-1);
    }
}
