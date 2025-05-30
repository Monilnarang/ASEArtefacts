package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;
import static org.jsoup.nodes.Document.OutputSettings;
import static org.jsoup.nodes.Entities.EscapeMode.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class EntitiesTest_Purified {

    @Test
    public void xhtml_1() {
        assertEquals(38, xhtml.codepointForName("amp"));
    }

    @Test
    public void xhtml_2() {
        assertEquals(62, xhtml.codepointForName("gt"));
    }

    @Test
    public void xhtml_3() {
        assertEquals(60, xhtml.codepointForName("lt"));
    }

    @Test
    public void xhtml_4() {
        assertEquals(34, xhtml.codepointForName("quot"));
    }

    @Test
    public void xhtml_5() {
        assertEquals("amp", xhtml.nameForCodepoint(38));
    }

    @Test
    public void xhtml_6() {
        assertEquals("gt", xhtml.nameForCodepoint(62));
    }

    @Test
    public void xhtml_7() {
        assertEquals("lt", xhtml.nameForCodepoint(60));
    }

    @Test
    public void xhtml_8() {
        assertEquals("quot", xhtml.nameForCodepoint(34));
    }

    @Test
    public void getByName_1() {
        assertEquals("≫⃒", Entities.getByName("nGt"));
    }

    @Test
    public void getByName_2() {
        assertEquals("fj", Entities.getByName("fjlig"));
    }

    @Test
    public void getByName_3() {
        assertEquals("≫", Entities.getByName("gg"));
    }

    @Test
    public void getByName_4() {
        assertEquals("©", Entities.getByName("copy"));
    }

    @Test
    public void unescape_1() {
        String text = "Hello &AElig; &amp;&LT&gt; &reg &angst; &angst &#960; &#960 &#x65B0; there &! &frac34; &copy; &COPY;";
        assertEquals("Hello Æ &<> ® Å &angst π π 新 there &! ¾ © ©", Entities.unescape(text));
    }

    @Test
    public void unescape_2() {
        assertEquals("&0987654321; &unknown", Entities.unescape("&0987654321; &unknown"));
    }

    @Test
    public void caseSensitive_1() {
        String unescaped = "Ü ü & &";
        assertEquals("&Uuml; &uuml; &amp; &amp;", Entities.escape(unescaped, new OutputSettings().charset("ascii").escapeMode(extended)));
    }

    @Test
    public void caseSensitive_2() {
        String escaped = "&Uuml; &uuml; &amp; &AMP";
        assertEquals("Ü ü & &", Entities.unescape(escaped));
    }
}
