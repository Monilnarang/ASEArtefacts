package org.springframework.ws.soap;

import java.util.Iterator;
import javax.xml.namespace.QName;
import org.junit.jupiter.api.Test;
import org.xmlunit.assertj.XmlAssert;
import org.springframework.xml.transform.StringResult;
import org.springframework.xml.transform.StringSource;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractSoapHeaderTests_Purified extends AbstractSoapElementTests {

    protected SoapHeader soapHeader;

    protected static final String NAMESPACE = "http://www.springframework.org";

    protected static final String PREFIX = "spring";

    @Override
    protected final SoapElement createSoapElement() throws Exception {
        this.soapHeader = createSoapHeader();
        return this.soapHeader;
    }

    protected abstract SoapHeader createSoapHeader() throws Exception;

    protected void assertHeaderElementEqual(SoapHeaderElement headerElement, String expected) throws Exception {
        StringResult result = new StringResult();
        this.transformer.transform(headerElement.getSource(), result);
        XmlAssert.assertThat(result.toString()).and(expected).ignoreWhitespace().areSimilar();
    }

    @Test
    void testAddHeaderElement_1_testMerged_1() throws Exception {
        QName qName = new QName(NAMESPACE, "localName", PREFIX);
        SoapHeaderElement headerElement = this.soapHeader.addHeaderElement(qName);
        assertThat(headerElement).isNotNull();
        assertThat(headerElement.getName()).isEqualTo(qName);
        this.transformer.transform(new StringSource(payload), headerElement.getResult());
        assertHeaderElementEqual(headerElement, "<spring:localName xmlns:spring='http://www.springframework.org'><spring:content/></spring:localName>");
    }

    @Test
    void testAddHeaderElement_3() throws Exception {
        Iterator<SoapHeaderElement> iterator = this.soapHeader.examineAllHeaderElements();
        assertThat(iterator.hasNext()).isTrue();
    }

    @Test
    void testExamineAllHeaderElement_1_testMerged_1() throws Exception {
        QName qName = new QName(NAMESPACE, "localName", PREFIX);
        SoapHeaderElement headerElement = this.soapHeader.addHeaderElement(qName);
        assertThat(headerElement.getName()).isEqualTo(qName);
        assertThat(headerElement).isNotNull();
    }

    @Test
    void testExamineAllHeaderElement_3_testMerged_2() throws Exception {
        Iterator<SoapHeaderElement> iterator = this.soapHeader.examineAllHeaderElements();
        assertThat(iterator).isNotNull();
        assertThat(iterator.hasNext()).isTrue();
        headerElement = iterator.next();
        assertThat(iterator.hasNext()).isFalse();
    }

    @Test
    void testExamineAllHeaderElement_6() throws Exception {
        StringResult result = new StringResult();
        this.transformer.transform(headerElement.getSource(), result);
    }
}
