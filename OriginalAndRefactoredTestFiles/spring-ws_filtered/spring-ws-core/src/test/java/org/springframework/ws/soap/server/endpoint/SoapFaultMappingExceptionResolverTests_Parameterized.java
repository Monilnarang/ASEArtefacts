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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class SoapFaultMappingExceptionResolverTests_Parameterized {

    private SoapFaultMappingExceptionResolver resolver;

    @BeforeEach
    void setUp() {
        this.resolver = new SoapFaultMappingExceptionResolver();
    }

    @Test
    void testGetDepth_3() {
        assertThat(this.resolver.getDepth("IllegalArgumentException", new IllegalStateException())).isEqualTo(-1);
    }

    @ParameterizedTest
    @MethodSource("Provider_testGetDepth_1to2")
    void testGetDepth_1to2(int param1, String param2) {
        assertThat(this.resolver.getDepth(param2, new Exception())).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_testGetDepth_1to2() {
        return Stream.of(arguments(0, "java.lang.Exception"), arguments(2, "java.lang.Exception"));
    }
}
