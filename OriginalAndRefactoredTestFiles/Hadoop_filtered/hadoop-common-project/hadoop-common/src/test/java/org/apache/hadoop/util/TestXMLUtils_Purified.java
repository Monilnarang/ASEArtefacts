package org.apache.hadoop.util;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.SAXParser;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.hadoop.test.AbstractHadoopTestBase;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class TestXMLUtils_Purified extends AbstractHadoopTestBase {

    private static InputStream getResourceStream(final String filename) {
        return TestXMLUtils.class.getResourceAsStream(filename);
    }

    @Test
    public void testBestEffortSetAttribute_1() throws Exception {
        AtomicBoolean flag1 = new AtomicBoolean(true);
        XMLUtils.bestEffortSetAttribute(factory, flag1, "unsupportedAttribute false", "abc");
        Assert.assertFalse("unexpected attribute results in return of false?", flag1.get());
    }

    @Test
    public void testBestEffortSetAttribute_2() throws Exception {
        AtomicBoolean flag2 = new AtomicBoolean(true);
        XMLUtils.bestEffortSetAttribute(factory, flag2, XMLConstants.ACCESS_EXTERNAL_DTD, "");
        Assert.assertTrue("expected attribute results in return of true?", flag2.get());
    }

    @Test
    public void testBestEffortSetAttribute_3() throws Exception {
        AtomicBoolean flag3 = new AtomicBoolean(false);
        XMLUtils.bestEffortSetAttribute(factory, flag3, XMLConstants.ACCESS_EXTERNAL_DTD, "");
        Assert.assertFalse("expected attribute results in return of false if input flag is false?", flag3.get());
    }
}
