package org.springframework.ws.server.endpoint.interceptor;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xmlunit.assertj.XmlAssert;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.ws.MockWebServiceMessage;
import org.springframework.ws.MockWebServiceMessageFactory;
import org.springframework.ws.context.DefaultMessageContext;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.pox.dom.DomPoxMessage;
import org.springframework.ws.pox.dom.DomPoxMessageFactory;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.xml.sax.SaxUtils;
import org.springframework.xml.transform.ResourceSource;
import org.springframework.xml.transform.StringResult;
import org.springframework.xml.transform.TransformerFactoryUtils;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class PayloadTransformingInterceptorTests_Purified {

    private PayloadTransformingInterceptor interceptor;

    private Transformer transformer;

    private Resource input;

    private Resource output;

    private Resource xslt;

    @BeforeEach
    void setUp() throws Exception {
        this.interceptor = new PayloadTransformingInterceptor();
        TransformerFactory transformerFactory = TransformerFactoryUtils.newInstance();
        this.transformer = transformerFactory.newTransformer();
        this.input = new ClassPathResource("transformInput.xml", getClass());
        this.output = new ClassPathResource("transformOutput.xml", getClass());
        this.xslt = new ClassPathResource("transformation.xslt", getClass());
    }

    @Test
    void testSaaj_1() throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage saajMessage = messageFactory.createMessage();
        SaajSoapMessage message = new SaajSoapMessage(saajMessage);
        this.transformer.transform(new ResourceSource(this.input), message.getPayloadResult());
        MessageContext context = new DefaultMessageContext(message, new SaajSoapMessageFactory(messageFactory));
        assertThat(this.interceptor.handleRequest(context, null)).isTrue();
    }

    @Test
    void testSaaj_2() throws Exception {
        StringResult expected = new StringResult();
        this.transformer.transform(new SAXSource(SaxUtils.createInputSource(this.output)), expected);
        StringResult result = new StringResult();
        this.transformer.transform(message.getPayloadSource(), result);
    }

    @Test
    void testPox_1() throws Exception {
        DomPoxMessageFactory factory = new DomPoxMessageFactory();
        DomPoxMessage message = factory.createWebServiceMessage();
        this.transformer.transform(new ResourceSource(this.input), message.getPayloadResult());
        MessageContext context = new DefaultMessageContext(message, factory);
        assertThat(this.interceptor.handleRequest(context, null)).isTrue();
    }

    @Test
    void testPox_2() throws Exception {
        StringResult expected = new StringResult();
        this.transformer.transform(new SAXSource(SaxUtils.createInputSource(this.output)), expected);
        StringResult result = new StringResult();
        this.transformer.transform(message.getPayloadSource(), result);
    }
}
