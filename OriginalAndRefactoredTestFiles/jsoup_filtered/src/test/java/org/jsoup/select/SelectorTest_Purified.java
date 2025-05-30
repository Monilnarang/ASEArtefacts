package org.jsoup.select;

import org.jsoup.Jsoup;
import org.jsoup.MultiLocaleExtension.MultiLocaleTest;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.*;

public class SelectorTest_Purified {

    public static void assertSelectedIds(Elements els, String... ids) {
        assertNotNull(els);
        assertEquals(ids.length, els.size(), "Incorrect number of selected elements");
        for (int i = 0; i < ids.length; i++) {
            assertEquals(ids[i], els.get(i).id(), "Incorrect content at index");
        }
    }

    public static void assertSelectedOwnText(Elements els, String... ownTexts) {
        assertNotNull(els);
        assertEquals(ownTexts.length, els.size(), "Incorrect number of selected elements");
        for (int i = 0; i < ownTexts.length; i++) {
            assertEquals(ownTexts[i], els.get(i).ownText(), "Incorrect content at index");
        }
    }

    @MultiLocaleTest
    public void containsOwn(Locale locale) {
        Locale.setDefault(locale);
        Document doc = Jsoup.parse("<p id=1>Hello <b>there</b> igor</p>");
        Elements ps = doc.select("p:containsOwn(Hello IGOR)");
        assertEquals(1, ps.size());
        assertEquals("1", ps.first().id());
        assertEquals(0, doc.select("p:containsOwn(there)").size());
        Document doc2 = Jsoup.parse("<p>Hello <b>there</b> IGOR</p>");
        assertEquals(1, doc2.select("p:containsOwn(igor)").size());
    }

    @MultiLocaleTest
    public void containsData(Locale locale) {
        Locale.setDefault(locale);
        String html = "<p>function</p><script>FUNCTION</script><style>item</style><span><!-- comments --></span>";
        Document doc = Jsoup.parse(html);
        Element body = doc.body();
        Elements dataEls1 = body.select(":containsData(function)");
        Elements dataEls2 = body.select("script:containsData(function)");
        Elements dataEls3 = body.select("span:containsData(comments)");
        Elements dataEls4 = body.select(":containsData(o)");
        Elements dataEls5 = body.select("style:containsData(ITEM)");
        assertEquals(2, dataEls1.size());
        assertEquals(1, dataEls2.size());
        assertEquals(dataEls1.last(), dataEls2.first());
        assertEquals("<script>FUNCTION</script>", dataEls2.outerHtml());
        assertEquals(1, dataEls3.size());
        assertEquals("span", dataEls3.first().tagName());
        assertEquals(3, dataEls4.size());
        assertEquals("body", dataEls4.first().tagName());
        assertEquals("script", dataEls4.get(1).tagName());
        assertEquals("span", dataEls4.get(2).tagName());
        assertEquals(1, dataEls5.size());
    }

    private final String mixedCase = "<html xmlns:n=\"urn:ns\"><n:mixedCase>text</n:mixedCase></html>";

    private final String lowercase = "<html xmlns:n=\"urn:ns\"><n:lowercase>text</n:lowercase></html>";

    @Test
    public void is_1_testMerged_1() {
        String html = "<h1 id=1><p></p></h1> <section><h1 id=2></h1></section> <article><h2 id=3></h2></article> <h2 id=4><p></p></h2>";
        Document doc = Jsoup.parse(html);
        assertSelectedIds(doc.select(":is(section, article) :is(h1, h2, h3)"), "2", "3");
        assertSelectedIds(doc.select(":is(section, article) ~ :is(h1, h2, h3):has(p)"), "4");
        assertSelectedIds(doc.select(":is(h1:has(p), h2:has(section), h3)"), "1");
        assertSelectedIds(doc.select(":is(h1, h2, h3):has(p)"), "1", "4");
    }

    @Test
    public void is_5() {
        String query = "div :is(h1, h2)";
        Evaluator parse = QueryParser.parse(query);
        assertEquals(query, parse.toString());
    }
}
