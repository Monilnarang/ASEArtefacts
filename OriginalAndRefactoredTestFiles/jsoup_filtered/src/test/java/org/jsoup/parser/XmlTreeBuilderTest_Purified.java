package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.TextUtil;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import static org.jsoup.nodes.Document.OutputSettings.Syntax;
import static org.junit.jupiter.api.Assertions.*;

public class XmlTreeBuilderTest_Purified {

    private static void assertXmlNamespace(Element el) {
        assertEquals(Parser.NamespaceXml, el.tag().namespace(), String.format("Element %s not in XML namespace", el.tagName()));
    }

    @Test
    public void testDoesNotForceSelfClosingKnownTags_1() {
        Document htmlDoc = Jsoup.parse("<br>one</br>");
        assertEquals("<br>\none\n<br>", htmlDoc.body().html());
    }

    @Test
    public void testDoesNotForceSelfClosingKnownTags_2() {
        Document xmlDoc = Jsoup.parse("<br>one</br>", "", Parser.xmlParser());
        assertEquals("<br>one</br>", xmlDoc.html());
    }
}
