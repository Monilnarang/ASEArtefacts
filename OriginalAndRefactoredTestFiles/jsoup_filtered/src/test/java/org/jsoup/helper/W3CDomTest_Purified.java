package org.jsoup.helper;

import org.jsoup.Jsoup;
import org.jsoup.TextUtil;
import org.jsoup.integration.ParseTest;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class W3CDomTest_Purified {

    private static Document parseXml(String xml, boolean nameSpaceAware) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(nameSpaceAware);
            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setEntityResolver((publicId, systemId) -> {
                if (systemId.contains("about:legacy-compat")) {
                    return new InputSource(new StringReader(""));
                } else {
                    return null;
                }
            });
            Document dom = builder.parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
            dom.normalizeDocument();
            return dom;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private NodeList xpath(Document w3cDoc, String query) throws XPathExpressionException {
        XPathExpression xpath = XPathFactory.newInstance().newXPath().compile(query);
        return ((NodeList) xpath.evaluate(w3cDoc, XPathConstants.NODE));
    }

    private String output(String in, boolean modeHtml) {
        org.jsoup.nodes.Document jdoc = Jsoup.parse(in);
        Document w3c = W3CDom.convert(jdoc);
        Map<String, String> properties = modeHtml ? W3CDom.OutputHtml() : W3CDom.OutputXml();
        return TextUtil.normalizeSpaces(W3CDom.asString(w3c, properties));
    }

    private void assertEqualsIgnoreCase(String want, String have) {
        assertEquals(want.toLowerCase(Locale.ROOT), have.toLowerCase(Locale.ROOT));
    }

    @Test
    public void testRoundTripDoctype_1_testMerged_1() {
        String base = "<!DOCTYPE html><p>One</p>";
        assertEqualsIgnoreCase("<!DOCTYPE html SYSTEM \"about:legacy-compat\"><html xmlns=\"http://www.w3.org/1999/xhtml\"><head><META http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"></head><body><p>One</p></body></html>", output(base, true));
        assertEqualsIgnoreCase("<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE html SYSTEM \"about:legacy-compat\"><html xmlns=\"http://www.w3.org/1999/xhtml\"><head/><body><p>One</p></body></html>", output(base, false));
    }

    @Test
    public void testRoundTripDoctype_3_testMerged_2() {
        String publicDoc = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">";
        assertEqualsIgnoreCase("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\"><head><META http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"></head><body></body></html>", output(publicDoc, true));
        assertTrue(output(publicDoc, false).startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE html PUBLIC"));
    }

    @Test
    public void testRoundTripDoctype_5_testMerged_3() {
        String systemDoc = "<!DOCTYPE html SYSTEM \"exampledtdfile.dtd\">";
        assertEqualsIgnoreCase("<!DOCTYPE html SYSTEM \"exampledtdfile.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\"><head><META http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"></head><body></body></html>", output(systemDoc, true));
        assertEqualsIgnoreCase("<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE html SYSTEM \"exampledtdfile.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\"><head/><body/></html>", output(systemDoc, false));
    }

    @Test
    public void testRoundTripDoctype_7_testMerged_4() {
        String legacyDoc = "<!DOCTYPE html SYSTEM \"about:legacy-compat\">";
        assertEqualsIgnoreCase("<!DOCTYPE html SYSTEM \"about:legacy-compat\"><html xmlns=\"http://www.w3.org/1999/xhtml\"><head><META http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"></head><body></body></html>", output(legacyDoc, true));
        assertEqualsIgnoreCase("<?xml version=\"1.0\" encoding=\"UTF-8\"?><!DOCTYPE html SYSTEM \"about:legacy-compat\"><html xmlns=\"http://www.w3.org/1999/xhtml\"><head/><body/></html>", output(legacyDoc, false));
    }

    @Test
    public void testRoundTripDoctype_9_testMerged_5() {
        String noDoctype = "<p>One</p>";
        assertEqualsIgnoreCase("<html xmlns=\"http://www.w3.org/1999/xhtml\"><head><META http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"></head><body><p>One</p></body></html>", output(noDoctype, true));
        assertEqualsIgnoreCase("<?xml version=\"1.0\" encoding=\"UTF-8\"?><html xmlns=\"http://www.w3.org/1999/xhtml\"><head/><body><p>One</p></body></html>", output(noDoctype, false));
    }
}
