package org.springframework.ws.soap.axiom.support;

import java.io.StringWriter;
import java.util.Locale;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.OMXMLBuilderFactory;
import org.apache.axiom.soap.SOAPEnvelope;
import org.apache.axiom.soap.SOAPMessage;
import org.apache.axiom.soap.SOAPModelBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xmlunit.assertj.XmlAssert;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;
import org.springframework.xml.DocumentBuilderFactoryUtils;
import org.springframework.xml.sax.SaxUtils;
import static org.assertj.core.api.Assertions.assertThat;

class AxiomUtilsTests_Purified {

    private OMElement element;

    @BeforeEach
    void setUp() throws Exception {
        OMFactory factory = OMAbstractFactory.getOMFactory();
        OMNamespace namespace = factory.createOMNamespace("http://www.springframework.org", "prefix");
        this.element = factory.createOMElement("element", namespace);
    }

    @Test
    void testToLanguage_1() {
        assertThat(AxiomUtils.toLanguage(Locale.CANADA_FRENCH)).isEqualTo("fr-CA");
    }

    @Test
    void testToLanguage_2() {
        assertThat(AxiomUtils.toLanguage(Locale.ENGLISH)).isEqualTo("en");
    }

    @Test
    void testToLocale_1() {
        assertThat(AxiomUtils.toLocale("fr-CA")).isEqualTo(Locale.CANADA_FRENCH);
    }

    @Test
    void testToLocale_2() {
        assertThat(AxiomUtils.toLocale("en")).isEqualTo(Locale.ENGLISH);
    }
}
