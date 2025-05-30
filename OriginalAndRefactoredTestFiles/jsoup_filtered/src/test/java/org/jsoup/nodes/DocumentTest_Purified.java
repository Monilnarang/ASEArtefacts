package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.jsoup.TextUtil;
import org.jsoup.integration.ParseTest;
import org.jsoup.nodes.Document.OutputSettings;
import org.jsoup.nodes.Document.OutputSettings.Syntax;
import org.jsoup.parser.ParseSettings;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class DocumentTest_Purified {

    private static final String charsetUtf8 = "UTF-8";

    private static final String charsetIso8859 = "ISO-8859-1";

    private Document createHtmlDocument(String charset) {
        final Document doc = Document.createShell("");
        doc.head().appendElement("meta").attr("charset", charset);
        doc.head().appendElement("meta").attr("name", "charset").attr("content", charset);
        return doc;
    }

    private Document createXmlDocument(String version, String charset, boolean addDecl) {
        final Document doc = new Document("");
        doc.appendElement("root").text("node");
        doc.outputSettings().syntax(Syntax.xml);
        if (addDecl) {
            XmlDeclaration decl = new XmlDeclaration("xml", false);
            decl.attr("version", version);
            decl.attr("encoding", charset);
            doc.prependChild(decl);
        }
        return doc;
    }

    @Test
    public void testTitles_1_testMerged_1() {
        Document noTitle = Jsoup.parse("<p>Hello</p>");
        assertEquals("", noTitle.title());
        noTitle.title("Hello");
        assertEquals("Hello", noTitle.title());
        assertEquals("Hello", noTitle.select("title").first().text());
    }

    @Test
    public void testTitles_4_testMerged_2() {
        Document withTitle = Jsoup.parse("<title>First</title><title>Ignore</title><p>Hello</p>");
        assertEquals("First", withTitle.title());
        withTitle.title("Hello");
        assertEquals("Hello", withTitle.title());
        assertEquals("Hello", withTitle.select("title").first().text());
    }

    @Test
    public void testTitles_7() {
        Document normaliseTitle = Jsoup.parse("<title>   Hello\nthere   \n   now   \n");
        assertEquals("Hello there now", normaliseTitle.title());
    }
}
