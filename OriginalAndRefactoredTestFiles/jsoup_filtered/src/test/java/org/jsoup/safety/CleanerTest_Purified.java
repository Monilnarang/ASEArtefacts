package org.jsoup.safety;

import org.jsoup.Jsoup;
import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.jsoup.TextUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Entities;
import org.jsoup.nodes.Range;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.Arrays;
import java.util.Locale;
import static org.junit.jupiter.api.Assertions.*;

public class CleanerTest_Purified {

    @MultiLocaleTest
    public void safeListedProtocolShouldBeRetained(Locale locale) {
        Locale.setDefault(locale);
        Safelist safelist = Safelist.none().addTags("a").addAttributes("a", "href").addProtocols("a", "href", "something");
        String cleanHtml = Jsoup.clean("<a href=\"SOMETHING://x\"></a>", safelist);
        assertEquals("<a href=\"SOMETHING://x\"></a>", TextUtil.stripNewlines(cleanHtml));
    }

    @Test
    public void testIsValidBodyHtml_1_testMerged_1() {
        String ok = "<p>Test <b><a href='http://example.com/' rel='nofollow'>OK</a></b></p>";
        assertTrue(Jsoup.isValid(ok, Safelist.basic()));
        assertFalse(Jsoup.isValid(ok, Safelist.none()));
    }

    @Test
    public void testIsValidBodyHtml_2() {
        String ok1 = "<p>Test <b><a href='http://example.com/'>OK</a></b></p>";
        assertTrue(Jsoup.isValid(ok1, Safelist.basic()));
    }

    @Test
    public void testIsValidBodyHtml_3() {
        String nok1 = "<p><script></script>Not <b>OK</b></p>";
        assertFalse(Jsoup.isValid(nok1, Safelist.basic()));
    }

    @Test
    public void testIsValidBodyHtml_4() {
        String nok2 = "<p align=right>Test Not <b>OK</b></p>";
        assertFalse(Jsoup.isValid(nok2, Safelist.basic()));
    }

    @Test
    public void testIsValidBodyHtml_5() {
        String nok3 = "<!-- comment --><p>Not OK</p>";
        assertFalse(Jsoup.isValid(nok3, Safelist.basic()));
    }

    @Test
    public void testIsValidBodyHtml_6() {
        String nok4 = "<html><head>Foo</head><body><b>OK</b></body></html>";
        assertFalse(Jsoup.isValid(nok4, Safelist.basic()));
    }

    @Test
    public void testIsValidBodyHtml_7() {
        String nok5 = "<p>Test <b><a href='http://example.com/' rel='nofollowme'>OK</a></b></p>";
        assertFalse(Jsoup.isValid(nok5, Safelist.basic()));
    }

    @Test
    public void testIsValidBodyHtml_8() {
        String nok6 = "<p>Test <b><a href='http://example.com/'>OK</b></p>";
        assertFalse(Jsoup.isValid(nok6, Safelist.basic()));
    }

    @Test
    public void testIsValidBodyHtml_10() {
        String nok7 = "</div>What";
        assertFalse(Jsoup.isValid(nok7, Safelist.basic()));
    }
}
