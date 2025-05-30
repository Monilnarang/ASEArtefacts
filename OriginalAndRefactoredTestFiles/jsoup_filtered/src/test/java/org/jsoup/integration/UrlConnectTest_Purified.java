package org.jsoup.integration;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.helper.W3CDom;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.FormElement;
import org.jsoup.parser.HtmlTreeBuilder;
import org.jsoup.parser.Parser;
import org.jsoup.parser.XmlTreeBuilder;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@Disabled
public class UrlConnectTest_Purified {

    private static final String WEBSITE_WITH_INVALID_CERTIFICATE = "https://certs.cac.washington.edu/CAtest/";

    private static final String WEBSITE_WITH_SNI = "https://jsoup.org/";

    public static String browserUa = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36";

    private static String ihVal(String key, Document doc) {
        return doc.select("th:contains(" + key + ") + td").first().text();
    }

    @Test
    public void handlesSuperDeepPage_1_testMerged_1() throws IOException {
        String url = "http://sv.stargate.wikia.com/wiki/M2J";
        Document doc = Jsoup.connect(url).get();
        assertEquals("M2J | Sv.stargate Wiki | FANDOM powered by Wikia", doc.title());
        assertEquals(110160, doc.select("dd").size());
    }

    @Test
    public void handlesSuperDeepPage_3() throws IOException {
        long start = System.currentTimeMillis();
        assertTrue(System.currentTimeMillis() - start < 1000);
    }
}
