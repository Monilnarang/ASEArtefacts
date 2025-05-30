package org.springframework.ws.server.endpoint.adapter.method.jaxb;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXSource;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.AttributesImpl;
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
import org.springframework.xml.sax.AbstractXmlReader;
import org.springframework.xml.transform.StringResult;
import org.springframework.xml.transform.TransformerFactoryUtils;
import static org.assertj.core.api.Assertions.assertThat;

class XmlRootElementPayloadMethodProcessorTests_Purified {

    private XmlRootElementPayloadMethodProcessor processor;

    private MethodParameter rootElementParameter;

    private MethodParameter typeParameter;

    private MethodParameter rootElementReturnType;

    @BeforeEach
    void setUp() throws Exception {
        this.processor = new XmlRootElementPayloadMethodProcessor();
        this.rootElementParameter = new MethodParameter(getClass().getMethod("rootElement", MyRootElement.class), 0);
        this.typeParameter = new MethodParameter(getClass().getMethod("type", MyType.class), 0);
        this.rootElementReturnType = new MethodParameter(getClass().getMethod("rootElement", MyRootElement.class), -1);
    }

    @ResponsePayload
    public MyRootElement rootElement(@RequestPayload MyRootElement rootElement) {
        return rootElement;
    }

    public void type(@RequestPayload MyType type) {
    }

    @XmlRootElement(name = "root", namespace = "http://springframework.org")
    public static class MyRootElement {

        private String string;

        @XmlElement(name = "string", namespace = "http://springframework.org")
        public String getString() {
            return this.string;
        }

        public void setString(String string) {
            this.string = string;
        }
    }

    @XmlType(name = "root", namespace = "http://springframework.org")
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
    void supportsParameter_1() {
        assertThat(this.processor.supportsParameter(this.rootElementParameter)).isTrue();
    }

    @Test
    void supportsParameter_2() {
        assertThat(this.processor.supportsParameter(this.typeParameter)).isTrue();
    }

    @Test
    void handleReturnValueAxiom_1() throws Exception {
        AxiomSoapMessageFactory messageFactory = new AxiomSoapMessageFactory();
        MessageContext messageContext = new DefaultMessageContext(messageFactory);
        this.processor.handleReturnValue(messageContext, this.rootElementReturnType, rootElement);
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

    @Test
    void handleReturnValueAxiomNoPayloadCaching_1() throws Exception {
        AxiomSoapMessageFactory messageFactory = new AxiomSoapMessageFactory();
        messageFactory.setPayloadCaching(false);
        MessageContext messageContext = new DefaultMessageContext(messageFactory);
        this.processor.handleReturnValue(messageContext, this.rootElementReturnType, rootElement);
        assertThat(messageContext.hasResponse()).isTrue();
    }

    @Test
    void handleReturnValueAxiomNoPayloadCaching_2() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        response.writeTo(bos);
        String messageResult = bos.toString("UTF-8");
    }
}
