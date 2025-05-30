package org.springframework.ws.soap.server.endpoint.interceptor;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Locale;
import javax.xml.XMLConstants;
import javax.xml.transform.Transformer;
import javax.xml.transform.stream.StreamSource;
import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPConstants;
import jakarta.xml.soap.SOAPMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.LocatorImpl;
import org.xmlunit.assertj.XmlAssert;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.ws.MockWebServiceMessage;
import org.springframework.ws.MockWebServiceMessageFactory;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.context.DefaultMessageContext;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.SoapVersion;
import org.springframework.ws.soap.axiom.AxiomSoapMessageFactory;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.soap.saaj.support.SaajUtils;
import org.springframework.ws.soap.soap11.Soap11Fault;
import org.springframework.ws.soap.soap12.Soap12Fault;
import org.springframework.ws.transport.MockTransportInputStream;
import org.springframework.ws.transport.TransportInputStream;
import org.springframework.xml.transform.TransformerFactoryUtils;
import org.springframework.xml.validation.ValidationErrorHandler;
import org.springframework.xml.xsd.SimpleXsdSchema;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class PayloadValidatingInterceptorTests_Purified {

    private PayloadValidatingInterceptor interceptor;

    private MessageContext context;

    private SaajSoapMessageFactory soap11Factory;

    private SaajSoapMessageFactory soap12Factory;

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
        this.soap12Factory = new SaajSoapMessageFactory(MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL));
        this.transformer = TransformerFactoryUtils.newInstance().newTransformer();
    }

    @Test
    void testHandleInvalidRequestSoap11_1() throws Exception {
        boolean result = this.interceptor.handleRequest(this.context, null);
        assertThat(result).isFalse();
    }

    @Test
    void testHandleInvalidRequestSoap11_2() throws Exception {
        assertThat(this.context.hasResponse()).isTrue();
    }

    @Test
    void testHandleInvalidRequestSoap11_3_testMerged_3() throws Exception {
        SoapMessage response = (SoapMessage) this.context.getResponse();
        assertThat(response.getSoapBody().hasFault()).isTrue();
        Soap11Fault fault = (Soap11Fault) response.getSoapBody().getFault();
        assertThat(fault.getFaultCode()).isEqualTo(SoapVersion.SOAP_11.getClientOrSenderFaultName());
        assertThat(fault.getFaultStringOrReason()).isEqualTo(PayloadValidatingInterceptor.DEFAULT_FAULTSTRING_OR_REASON);
        assertThat(fault.getFaultDetail()).isNotNull();
    }

    @Test
    void testHandleInvalidRequestSoap12_1() throws Exception {
        boolean result = this.interceptor.handleRequest(this.context, null);
        assertThat(result).isFalse();
    }

    @Test
    void testHandleInvalidRequestSoap12_2() throws Exception {
        assertThat(this.context.hasResponse()).isTrue();
    }

    @Test
    void testHandleInvalidRequestSoap12_3_testMerged_3() throws Exception {
        SoapMessage response = (SoapMessage) this.context.getResponse();
        assertThat(response.getSoapBody().hasFault()).isTrue();
        Soap12Fault fault = (Soap12Fault) response.getSoapBody().getFault();
        assertThat(fault.getFaultCode()).isEqualTo(SoapVersion.SOAP_12.getClientOrSenderFaultName());
        assertThat(fault.getFaultReasonText(Locale.ENGLISH)).isEqualTo(PayloadValidatingInterceptor.DEFAULT_FAULTSTRING_OR_REASON);
        assertThat(fault.getFaultDetail()).isNotNull();
    }

    @Test
    void testHandleInvalidRequestOverridenProperties_1() throws Exception {
        boolean result = this.interceptor.handleRequest(this.context, null);
        assertThat(result).isFalse();
    }

    @Test
    void testHandleInvalidRequestOverridenProperties_2() throws Exception {
        assertThat(this.context.hasResponse()).isTrue();
    }

    @Test
    void testHandleInvalidRequestOverridenProperties_3_testMerged_3() throws Exception {
        String faultString = "fout";
        Locale locale = new Locale("nl");
        this.interceptor.setFaultStringOrReason(faultString);
        this.interceptor.setFaultStringOrReasonLocale(locale);
        SoapMessage response = (SoapMessage) this.context.getResponse();
        assertThat(response.getSoapBody().hasFault()).isTrue();
        Soap11Fault fault = (Soap11Fault) response.getSoapBody().getFault();
        assertThat(fault.getFaultCode()).isEqualTo(SoapVersion.SOAP_11.getClientOrSenderFaultName());
        assertThat(fault.getFaultStringOrReason()).isEqualTo(faultString);
        assertThat(fault.getFaultStringLocale()).isEqualTo(locale);
        assertThat(fault.getFaultDetail()).isNull();
    }

    @Test
    void testHandleValidRequest_1() throws Exception {
        boolean result = this.interceptor.handleRequest(this.context, null);
        assertThat(result).isTrue();
    }

    @Test
    void testHandleValidRequest_2() throws Exception {
        assertThat(this.context.hasResponse()).isFalse();
    }

    @Test
    void testHandleValidRequestMultipleSchemas_1() throws Exception {
        boolean result = this.interceptor.handleRequest(this.context, null);
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
        boolean result = interceptor.handleRequest(this.context, null);
        assertThat(result).isTrue();
    }

    @Test
    void testXsdSchema_2() throws Exception {
        assertThat(this.context.hasResponse()).isFalse();
    }
}
