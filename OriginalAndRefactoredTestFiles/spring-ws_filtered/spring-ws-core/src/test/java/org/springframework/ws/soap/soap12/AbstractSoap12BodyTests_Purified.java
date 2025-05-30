package org.springframework.ws.soap.soap12;

import java.util.Iterator;
import java.util.Locale;
import javax.xml.namespace.QName;
import org.junit.jupiter.api.Test;
import org.xmlunit.assertj.XmlAssert;
import org.springframework.ws.soap.AbstractSoapBodyTests;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.SoapFaultDetailElement;
import org.springframework.ws.soap.SoapVersion;
import org.springframework.xml.transform.StringResult;
import org.springframework.xml.transform.StringSource;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractSoap12BodyTests_Purified extends AbstractSoapBodyTests {

    @Test
    void testAddMustUnderstandFault_1() throws Exception {
        SoapFault fault = this.soapBody.addMustUnderstandFault("SOAP Must Understand Error", Locale.ENGLISH);
        assertThat(fault.getFaultCode()).isEqualTo(new QName("http://www.w3.org/2003/05/soap-envelope", "MustUnderstand"));
    }

    @Test
    void testAddMustUnderstandFault_2() throws Exception {
        StringResult result = new StringResult();
        this.transformer.transform(fault.getSource(), result);
    }

    @Test
    void testAddSenderFault_1() throws Exception {
        SoapFault fault = this.soapBody.addClientOrSenderFault("faultString", Locale.ENGLISH);
        assertThat(fault.getFaultCode()).isEqualTo(new QName("http://www.w3.org/2003/05/soap-envelope", "Sender"));
    }

    @Test
    void testAddSenderFault_2() throws Exception {
        StringResult result = new StringResult();
        this.transformer.transform(fault.getSource(), result);
    }

    @Test
    void testAddReceiverFault_1() throws Exception {
        SoapFault fault = this.soapBody.addServerOrReceiverFault("faultString", Locale.ENGLISH);
        assertThat(fault.getFaultCode()).isEqualTo(new QName("http://www.w3.org/2003/05/soap-envelope", "Receiver"));
    }

    @Test
    void testAddReceiverFault_2() throws Exception {
        StringResult result = new StringResult();
        this.transformer.transform(fault.getSource(), result);
    }

    @Test
    void testAddFaultWithSubcode_1_testMerged_1() throws Exception {
        Soap12Fault fault = (Soap12Fault) this.soapBody.addServerOrReceiverFault("faultString", Locale.ENGLISH);
        QName subcode1 = new QName("http://www.springframework.org", "Subcode1", "spring-ws");
        fault.addFaultSubcode(subcode1);
        QName subcode2 = new QName("http://www.springframework.org", "Subcode2", "spring-ws");
        fault.addFaultSubcode(subcode2);
        Iterator<QName> iterator = fault.getFaultSubcodes();
        assertThat(iterator.hasNext()).isTrue();
        assertThat(iterator.next()).isEqualTo(subcode1);
        assertThat(iterator.next()).isEqualTo(subcode2);
        assertThat(iterator.hasNext()).isFalse();
    }

    @Test
    void testAddFaultWithSubcode_6() throws Exception {
        StringResult result = new StringResult();
        this.transformer.transform(fault.getSource(), result);
    }
}
