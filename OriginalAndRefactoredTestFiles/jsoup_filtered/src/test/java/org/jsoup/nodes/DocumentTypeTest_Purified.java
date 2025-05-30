package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DocumentTypeTest_Purified {

    private String htmlOutput(String in) {
        DocumentType type = (DocumentType) Jsoup.parse(in).childNode(0);
        return type.outerHtml();
    }

    private String xmlOutput(String in) {
        return Jsoup.parse(in, "", Parser.xmlParser()).childNode(0).outerHtml();
    }

    @Test
    public void outerHtmlGeneration_1() {
        DocumentType html5 = new DocumentType("html", "", "");
        assertEquals("<!doctype html>", html5.outerHtml());
    }

    @Test
    public void outerHtmlGeneration_2() {
        DocumentType publicDocType = new DocumentType("html", "-//IETF//DTD HTML//", "");
        assertEquals("<!DOCTYPE html PUBLIC \"-//IETF//DTD HTML//\">", publicDocType.outerHtml());
    }

    @Test
    public void outerHtmlGeneration_3() {
        DocumentType systemDocType = new DocumentType("html", "", "http://www.ibm.com/data/dtd/v11/ibmxhtml1-transitional.dtd");
        assertEquals("<!DOCTYPE html SYSTEM \"http://www.ibm.com/data/dtd/v11/ibmxhtml1-transitional.dtd\">", systemDocType.outerHtml());
    }

    @Test
    public void outerHtmlGeneration_4_testMerged_4() {
        DocumentType combo = new DocumentType("notHtml", "--public", "--system");
        assertEquals("<!DOCTYPE notHtml PUBLIC \"--public\" \"--system\">", combo.outerHtml());
        assertEquals("notHtml", combo.name());
        assertEquals("--public", combo.publicId());
        assertEquals("--system", combo.systemId());
    }

    @Test
    public void testRoundTrip_1_testMerged_1() {
        String base = "<!DOCTYPE html>";
        assertEquals("<!doctype html>", htmlOutput(base));
        assertEquals(base, xmlOutput(base));
    }

    @Test
    public void testRoundTrip_3_testMerged_2() {
        String publicDoc = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">";
        assertEquals(publicDoc, htmlOutput(publicDoc));
        assertEquals(publicDoc, xmlOutput(publicDoc));
    }

    @Test
    public void testRoundTrip_5_testMerged_3() {
        String systemDoc = "<!DOCTYPE html SYSTEM \"exampledtdfile.dtd\">";
        assertEquals(systemDoc, htmlOutput(systemDoc));
        assertEquals(systemDoc, xmlOutput(systemDoc));
    }

    @Test
    public void testRoundTrip_7_testMerged_4() {
        String legacyDoc = "<!DOCTYPE html SYSTEM \"about:legacy-compat\">";
        assertEquals(legacyDoc, htmlOutput(legacyDoc));
        assertEquals(legacyDoc, xmlOutput(legacyDoc));
    }
}
