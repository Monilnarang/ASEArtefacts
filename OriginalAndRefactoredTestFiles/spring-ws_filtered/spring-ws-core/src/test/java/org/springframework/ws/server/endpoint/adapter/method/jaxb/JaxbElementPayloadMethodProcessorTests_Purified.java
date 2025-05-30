package org.springframework.ws.server.endpoint.adapter.method.jaxb;

import java.io.ByteArrayOutputStream;
import javax.xml.namespace.QName;
import javax.xml.transform.Transformer;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xmlunit.assertj.XmlAssert;
import org.springframework.core.MethodParameter;
import org.springframework.ws.MockWebServiceMessage;
import org.springframework.ws.MockWebServiceMessageFactory;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.context.DefaultMessageContext;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.axiom.AxiomSoapMessage;
import org.springframework.ws.soap.axiom.AxiomSoapMessageFactory;
import org.springframework.xml.transform.StringResult;
import org.springframework.xml.transform.TransformerFactoryUtils;
import static org.assertj.core.api.Assertions.assertThat;

class JaxbElementPayloadMethodProcessorTests_Purified {

    private JaxbElementPayloadMethodProcessor processor;

    private MethodParameter supportedParameter;

    private MethodParameter supportedReturnType;

    private MethodParameter stringReturnType;

    @BeforeEach
    void setUp() throws Exception {
        this.processor = new JaxbElementPayloadMethodProcessor();
        this.supportedParameter = new MethodParameter(getClass().getMethod("supported", JAXBElement.class), 0);
        this.supportedReturnType = new MethodParameter(getClass().getMethod("supported", JAXBElement.class), -1);
        this.stringReturnType = new MethodParameter(getClass().getMethod("string"), -1);
    }

    @ResponsePayload
    public JAXBElement<MyType> supported(@RequestPayload JAXBElement<MyType> element) {
        return element;
    }

    @ResponsePayload
    public JAXBElement<String> string() {
        return new JAXBElement<>(new QName("string"), String.class, "Foo");
    }

    @XmlType(name = "myType", namespace = "http://springframework.org")
    public static class MyType {

        private String string;

        @XmlElement(name = "string", namespace = "http://springframework.org")
        public String getString() {
            return this.string;
        }

        public void setString(String string) {
            this.string = string;
        }
    }

    @Test
    void handleReturnValueAxiom_1() throws Exception {
        AxiomSoapMessageFactory messageFactory = new AxiomSoapMessageFactory();
        MessageContext messageContext = new DefaultMessageContext(messageFactory);
        this.processor.handleReturnValue(messageContext, this.supportedReturnType, element);
        assertThat(messageContext.hasResponse()).isTrue();
    }

    @Test
    void handleReturnValueAxiom_2() throws Exception {
        StringResult payloadResult = new StringResult();
        transformer.transform(response.getPayloadSource(), payloadResult);
    }

    @Test
    void handleReturnValueAxiom_3() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        response.writeTo(bos);
        String messageResult = bos.toString("UTF-8");
    }
}
