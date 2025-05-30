package org.springframework.ws.client.support.interceptor;

import java.io.InputStream;
import javax.xml.XMLConstants;
import javax.xml.transform.Transformer;
import javax.xml.transform.stream.StreamSource;
import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPConstants;
import jakarta.xml.soap.SOAPMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.MockWebServiceMessage;
import org.springframework.ws.MockWebServiceMessageFactory;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.context.DefaultMessageContext;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.soap.saaj.support.SaajUtils;
import org.springframework.xml.transform.TransformerFactoryUtils;
import org.springframework.xml.xsd.SimpleXsdSchema;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class PayloadValidatingInterceptorTests_Purified {

    private PayloadValidatingInterceptor interceptor;

    private MessageContext context;

    private SaajSoapMessageFactory soap11Factory;

    private Transformer transformer;

    private static final String INVALID_MESSAGE = "invalidMessage.xml";

    private static final String SCHEMA = "schema.xsd";

    private static final String VALID_MESSAGE = "validMessage.xml";

    private static final String PRODUCT_SCHEMA = "productSchema.xsd";

    private static final String SIZE_SCHEMA = "sizeSchema.xsd";

    private static final String VALID_SOAP_MESSAGE = "validSoapMessage.xml";

    private static final String SCHEMA2 = "schema2.xsd";

    @BeforeEach
    void setUp() throws Exception {
        this.interceptor = new PayloadValidatingInterceptor();
        this.interceptor.setSchema(new ClassPathResource(SCHEMA, getClass()));
        this.interceptor.setValidateRequest(true);
        this.interceptor.setValidateResponse(true);
        this.interceptor.afterPropertiesSet();
        this.soap11Factory = new SaajSoapMessageFactory(MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL));
        this.transformer = TransformerFactoryUtils.newInstance().newTransformer();
    }

    @Test
    void testHandleValidRequest_1() throws Exception {
        boolean result = this.interceptor.handleRequest(this.context);
        assertThat(result).isTrue();
    }

    @Test
    void testHandleValidRequest_2() throws Exception {
        assertThat(this.context.hasResponse()).isFalse();
    }

    @Test
    void testHandleValidRequestMultipleSchemas_1() throws Exception {
        boolean result = this.interceptor.handleRequest(this.context);
        assertThat(result).isTrue();
    }

    @Test
    void testHandleValidRequestMultipleSchemas_2() throws Exception {
        assertThat(this.context.hasResponse()).isFalse();
    }

    @Test
    void testXsdSchema_1() throws Exception {
        PayloadValidatingInterceptor interceptor = new PayloadValidatingInterceptor();
        SimpleXsdSchema schema = new SimpleXsdSchema(new ClassPathResource(SCHEMA, getClass()));
        schema.afterPropertiesSet();
        interceptor.setXsdSchema(schema);
        interceptor.setValidateRequest(true);
        interceptor.setValidateResponse(true);
        interceptor.afterPropertiesSet();
        boolean result = interceptor.handleRequest(this.context);
        assertThat(result).isTrue();
    }

    @Test
    void testXsdSchema_2() throws Exception {
        assertThat(this.context.hasResponse()).isFalse();
    }
}
