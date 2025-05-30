package org.springframework.ws.soap.saaj;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import jakarta.xml.soap.MessageFactory;
import jakarta.xml.soap.SOAPConstants;
import jakarta.xml.soap.SOAPMessage;
import org.junit.jupiter.api.Test;
import org.xmlunit.assertj.XmlAssert;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.soap.soap12.AbstractSoap12MessageTests;
import org.springframework.xml.transform.StringResult;
import org.springframework.xml.transform.StringSource;
import static org.assertj.core.api.Assertions.assertThat;

class SaajSoap12MessageTests_Purified extends AbstractSoap12MessageTests {

    @Override
    protected String getNS() {
        return "env";
    }

    @Override
    protected String getHeader() {
        return "";
    }

    private SOAPMessage saajMessage;

    @Override
    protected SoapMessage createSoapMessage() throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
        this.saajMessage = messageFactory.createMessage();
        this.saajMessage.getSOAPHeader().detachNode();
        return new SaajSoapMessage(this.saajMessage, true, messageFactory);
    }

    @Test
    void testGetPayloadResult_1() throws Exception {
        assertThat(this.saajMessage.getSOAPBody().hasChildNodes()).isTrue();
    }

    @Test
    void testGetPayloadResult_2() throws Exception {
        assertThat(this.saajMessage.getSOAPBody().getFirstChild().getLocalName()).isEqualTo("child");
    }
}
