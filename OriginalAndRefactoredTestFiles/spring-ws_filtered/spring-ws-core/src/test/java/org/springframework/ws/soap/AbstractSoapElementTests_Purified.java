package org.springframework.ws.soap;

import java.util.Iterator;
import javax.xml.namespace.QName;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.xml.transform.TransformerFactoryUtils;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class AbstractSoapElementTests_Purified {

    private SoapElement soapElement;

    protected Transformer transformer;

    @BeforeEach
    public final void setUp() throws Exception {
        TransformerFactory transformerFactory = TransformerFactoryUtils.newInstance();
        this.transformer = transformerFactory.newTransformer();
        this.soapElement = createSoapElement();
    }

    protected abstract SoapElement createSoapElement() throws Exception;

    @Test
    void testAttributes_1() {
        QName name = new QName("http://springframework.org/spring-ws", "attribute");
        String value = "value";
        this.soapElement.addAttribute(name, value);
        assertThat(this.soapElement.getAttributeValue(name)).isEqualTo(value);
    }

    @Test
    void testAttributes_2() {
        Iterator<QName> allAttributes = this.soapElement.getAllAttributes();
        assertThat(allAttributes.hasNext()).isTrue();
    }
}
