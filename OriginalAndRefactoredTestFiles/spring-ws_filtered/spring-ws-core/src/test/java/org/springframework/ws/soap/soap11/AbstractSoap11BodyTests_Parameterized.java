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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public abstract class AbstractSoap11BodyTests_Parameterized extends AbstractSoapBodyTests {

    @Test
    void testAddMustUnderstandFault_2() throws Exception {
        assertPayloadEqual("<SOAP-ENV:Fault xmlns:SOAP-ENV='http://schemas.xmlsoap.org/soap/envelope/'>" + "<faultcode>" + this.soapBody.getName().getPrefix() + ":MustUnderstand</faultcode>" + "<faultstring>SOAP Must Understand Error</faultstring></SOAP-ENV:Fault>");
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

    @ParameterizedTest
    @MethodSource("Provider_testAddMustUnderstandFault_1_1_1")
    void testAddMustUnderstandFault_1_1_1(String param1, String param2, String param3) throws Exception {
        SoapFault fault = this.soapBody.addMustUnderstandFault(param3, null);
        assertThat(fault.getFaultCode()).isEqualTo(new QName(param1, param2));
    }

    static public Stream<Arguments> Provider_testAddMustUnderstandFault_1_1_1() {
        return Stream.of(arguments("SOAP Must Understand Error", "http://schemas.xmlsoap.org/soap/envelope/", "MustUnderstand"), arguments("faultString", "http://schemas.xmlsoap.org/soap/envelope/", "Client"), arguments("faultString", "http://schemas.xmlsoap.org/soap/envelope/", "Server"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testAddClientFault_2_2")
    void testAddClientFault_2_2(String param1, String param2, String param3, String param4, String param5) throws Exception {
        assertPayloadEqual(param4 + param5 + this.soapBody.getName().getPrefix() + param3 + param2 + param1);
    }

    static public Stream<Arguments> Provider_testAddClientFault_2_2() {
        return Stream.of(arguments("</SOAP-ENV:Fault>", "<faultstring>faultString</faultstring>", ":Client</faultcode>", "<SOAP-ENV:Fault xmlns:SOAP-ENV='http://schemas.xmlsoap.org/soap/envelope/'>", "<faultcode>"), arguments("</SOAP-ENV:Fault>", "<faultstring>faultString</faultstring>", ":Server</faultcode>", "<SOAP-ENV:Fault xmlns:SOAP-ENV='http://schemas.xmlsoap.org/soap/envelope/'>", "<faultcode>"));
    }
}
