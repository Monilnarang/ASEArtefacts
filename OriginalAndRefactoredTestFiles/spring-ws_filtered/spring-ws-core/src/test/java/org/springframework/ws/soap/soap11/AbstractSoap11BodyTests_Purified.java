package org.springframework.ws.soap.soap11;

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

public abstract class AbstractSoap11BodyTests_Purified extends AbstractSoapBodyTests {

    @Test
    void testAddMustUnderstandFault_1() throws Exception {
        SoapFault fault = this.soapBody.addMustUnderstandFault("SOAP Must Understand Error", null);
        assertThat(fault.getFaultCode()).isEqualTo(new QName("http://schemas.xmlsoap.org/soap/envelope/", "MustUnderstand"));
    }

    @Test
    void testAddMustUnderstandFault_2() throws Exception {
        assertPayloadEqual("<SOAP-ENV:Fault xmlns:SOAP-ENV='http://schemas.xmlsoap.org/soap/envelope/'>" + "<faultcode>" + this.soapBody.getName().getPrefix() + ":MustUnderstand</faultcode>" + "<faultstring>SOAP Must Understand Error</faultstring></SOAP-ENV:Fault>");
    }

    @Test
    void testAddClientFault_1() throws Exception {
        SoapFault fault = this.soapBody.addClientOrSenderFault("faultString", null);
        assertThat(fault.getFaultCode()).isEqualTo(new QName("http://schemas.xmlsoap.org/soap/envelope/", "Client"));
    }

    @Test
    void testAddClientFault_2() throws Exception {
        assertPayloadEqual("<SOAP-ENV:Fault xmlns:SOAP-ENV='http://schemas.xmlsoap.org/soap/envelope/'>" + "<faultcode>" + this.soapBody.getName().getPrefix() + ":Client</faultcode>" + "<faultstring>faultString</faultstring>" + "</SOAP-ENV:Fault>");
    }

    @Test
    void testAddServerFault_1() throws Exception {
        SoapFault fault = this.soapBody.addServerOrReceiverFault("faultString", null);
        assertThat(fault.getFaultCode()).isEqualTo(new QName("http://schemas.xmlsoap.org/soap/envelope/", "Server"));
    }

    @Test
    void testAddServerFault_2() throws Exception {
        assertPayloadEqual("<SOAP-ENV:Fault xmlns:SOAP-ENV='http://schemas.xmlsoap.org/soap/envelope/'>" + "<faultcode>" + this.soapBody.getName().getPrefix() + ":Server</faultcode>" + "<faultstring>faultString</faultstring>" + "</SOAP-ENV:Fault>");
    }

    @Test
    void testAddFault_1_testMerged_1() throws Exception {
        QName faultCode = new QName("http://www.springframework.org", "fault", "spring");
        String faultString = "faultString";
        Soap11Fault fault = ((Soap11Body) this.soapBody).addFault(faultCode, faultString, Locale.ENGLISH);
        assertThat(fault).isNotNull();
        assertThat(fault.getFaultCode()).isEqualTo(faultCode);
        assertThat(fault.getFaultStringOrReason()).isEqualTo(faultString);
        assertThat(fault.getFaultStringLocale()).isEqualTo(Locale.ENGLISH);
        String actor = "http://www.springframework.org/actor";
        fault.setFaultActorOrRole(actor);
        assertThat(fault.getFaultActorOrRole()).isEqualTo(actor);
        assertPayloadEqual("<SOAP-ENV:Fault xmlns:SOAP-ENV='http://schemas.xmlsoap.org/soap/envelope/' " + "xmlns:spring='http://www.springframework.org'>" + "<faultcode>spring:fault</faultcode>" + "<faultstring xml:lang='en'>" + faultString + "</faultstring>" + "<faultactor>" + actor + "</faultactor>" + "</SOAP-ENV:Fault>");
    }

    @Test
    void testAddFault_2() throws Exception {
        assertThat(this.soapBody.hasFault()).isTrue();
    }

    @Test
    void testAddFault_3() throws Exception {
        assertThat(this.soapBody.getFault()).isNotNull();
    }

    @Test
    void testAddFaultNoPrefix_1_testMerged_1() {
        QName faultCode = new QName("http://www.springframework.org", "fault");
        String faultString = "faultString";
        Soap11Fault fault = ((Soap11Body) this.soapBody).addFault(faultCode, faultString, Locale.ENGLISH);
        assertThat(fault).isNotNull();
        assertThat(fault.getFaultCode()).isEqualTo(faultCode);
        assertThat(fault.getFaultStringOrReason()).isEqualTo(faultString);
        assertThat(fault.getFaultStringLocale()).isEqualTo(Locale.ENGLISH);
        String actor = "http://www.springframework.org/actor";
        fault.setFaultActorOrRole(actor);
        assertThat(fault.getFaultActorOrRole()).isEqualTo(actor);
    }

    @Test
    void testAddFaultNoPrefix_2() {
        assertThat(this.soapBody.hasFault()).isTrue();
    }

    @Test
    void testAddFaultNoPrefix_3() {
        assertThat(this.soapBody.getFault()).isNotNull();
    }
}
