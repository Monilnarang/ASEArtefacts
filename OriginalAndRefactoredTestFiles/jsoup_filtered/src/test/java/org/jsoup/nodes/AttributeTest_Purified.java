package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.jsoup.parser.ParseSettings;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AttributeTest_Purified {

    @Test
    public void hasValue_1() {
        Attribute a1 = new Attribute("one", "");
        assertTrue(a1.hasDeclaredValue());
    }

    @Test
    public void hasValue_2() {
        Attribute a2 = new Attribute("two", null);
        assertFalse(a2.hasDeclaredValue());
    }

    @Test
    public void hasValue_3() {
        Attribute a3 = new Attribute("thr", "thr");
        assertTrue(a3.hasDeclaredValue());
    }

    @Test
    void booleanAttributesAreNotCaseSensitive_1() {
        assertTrue(Attribute.isBooleanAttribute("required"));
    }

    @Test
    void booleanAttributesAreNotCaseSensitive_2() {
        assertTrue(Attribute.isBooleanAttribute("REQUIRED"));
    }

    @Test
    void booleanAttributesAreNotCaseSensitive_3() {
        assertTrue(Attribute.isBooleanAttribute("rEQUIREd"));
    }

    @Test
    void booleanAttributesAreNotCaseSensitive_4() {
        assertFalse(Attribute.isBooleanAttribute("random string"));
    }

    @Test
    void booleanAttributesAreNotCaseSensitive_5_testMerged_5() {
        String html = "<a href=autofocus REQUIRED>One</a>";
        Document doc = Jsoup.parse(html);
        assertEquals("<a href=\"autofocus\" required>One</a>", doc.selectFirst("a").outerHtml());
        Document doc2 = Jsoup.parse(html, Parser.htmlParser().settings(ParseSettings.preserveCase));
        assertEquals("<a href=\"autofocus\" REQUIRED>One</a>", doc2.selectFirst("a").outerHtml());
    }
}
