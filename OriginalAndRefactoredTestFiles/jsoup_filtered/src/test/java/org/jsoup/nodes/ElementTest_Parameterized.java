package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.jsoup.TextUtil;
import org.jsoup.helper.ValidationException;
import org.jsoup.parser.ParseSettings;
import org.jsoup.parser.Parser;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.jsoup.select.Evaluator;
import org.jsoup.select.NodeFilter;
import org.jsoup.select.NodeVisitor;
import org.jsoup.select.QueryParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import static org.jsoup.nodes.NodeIteratorTest.assertIterates;
import static org.jsoup.nodes.NodeIteratorTest.trackSeen;
import static org.jsoup.select.SelectorTest.assertSelectedOwnText;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.provider.CsvSource;

public class ElementTest_Parameterized {

    private final String reference = "<div id=div1><p>Hello</p><p>Another <b>element</b></p><div id=div2><img src=foo.png></div></div>";

    private static void validateScriptContents(String src, Element el) {
        assertEquals("", el.text());
        assertEquals("", el.ownText());
        assertEquals("", el.wholeText());
        assertEquals(src, el.html());
        assertEquals(src, el.data());
    }

    private static void validateXmlScriptContents(Element el) {
        assertEquals("var foo = 5 < 2; var bar = 1 && 2;", el.text());
        assertEquals("var foo = 5 < 2; var bar = 1 && 2;", el.ownText());
        assertEquals("var foo = 5 < 2;\nvar bar = 1 && 2;", el.wholeText());
        assertEquals("var foo = 5 &lt; 2;\nvar bar = 1 &amp;&amp; 2;", el.html());
        assertEquals("", el.data());
    }

    @Test
    public void testGetElementById_1() {
        Document doc = Jsoup.parse(reference);
        Element div = doc.getElementById("div1");
        assertEquals("div1", div.id());
    }

    @Test
    public void testGetElementById_2() {
        Document doc = Jsoup.parse(reference);
        Element div = doc.getElementById("div1");
        assertNull(doc.getElementById("none"));
    }

    @Test
    public void testGetElementById_3() {
        Document doc2 = Jsoup.parse("<div id=1><div id=2><p>Hello <span id=2>world!</span></p></div></div>");
        Element div2 = doc2.getElementById("2");
        assertEquals("div", div2.tagName());
    }

    @Test
    public void testGetElementById_4() {
        Document doc2 = Jsoup.parse("<div id=1><div id=2><p>Hello <span id=2>world!</span></p></div></div>");
        Element div2 = doc2.getElementById("2");
        Element span = div2.child(0).getElementById("2");
        assertEquals("span", span.tagName());
    }

    @Test
    public void textHasSpacesAfterBlock_1() {
        Document doc = Jsoup.parse("<div>One</div><div>Two</div><span>Three</span><p>Fou<i>r</i></p>");
        String text = doc.text();
        assertEquals("One Two Three Four", text);
    }

    @Test
    public void textHasSpacesAfterBlock_2() {
        Document doc = Jsoup.parse("<div>One</div><div>Two</div><span>Three</span><p>Fou<i>r</i></p>");
        String wholeText = doc.wholeText();
        assertEquals("OneTwoThreeFour", wholeText);
    }

    @Test
    public void textHasSpacesAfterBlock_3() {
        assertEquals("OneTwo", Jsoup.parse("<span>One</span><span>Two</span>").text());
    }

    @Test
    public void testGetParents_1() {
        Document doc = Jsoup.parse("<div><p>Hello <span>there</span></div>");
        Element span = doc.select("span").first();
        Elements parents = span.parents();
        assertEquals(4, parents.size());
    }

    @Test
    public void testGetParents_6() {
        Element orphan = new Element("p");
        Elements none = orphan.parents();
        assertEquals(0, none.size());
    }

    @Test
    public void testRoot_1() {
        Element el = new Element("a");
        el.append("<span>Hello</span>");
        assertEquals("<a><span>Hello</span></a>", el.outerHtml());
    }

    @Test
    public void testRoot_2() {
        Element el = new Element("a");
        Element span = el.selectFirst("span");
        assertNotNull(span);
    }

    @Test
    public void testRoot_3() {
        Element el = new Element("a");
        Element span = el.selectFirst("span");
        Element el2 = span.root();
        assertSame(el, el2);
    }

    @Test
    public void testRoot_4() {
        Document doc = Jsoup.parse("<div><p>One<p>Two<p>Three");
        Element div = doc.selectFirst("div");
        assertSame(doc, div.root());
    }

    @Test
    public void testRoot_5() {
        Document doc = Jsoup.parse("<div><p>One<p>Two<p>Three");
        Element div = doc.selectFirst("div");
        assertSame(doc, div.ownerDocument());
    }

    @Test
    void doctypeIsPrettyPrinted_1() {
        Document doc1 = Jsoup.parse("<!--\nlicense\n-->\n \n<!doctype html>\n<html>");
        assertEquals("<!--\nlicense\n-->\n<!doctype html>\n<html>\n <head></head>\n <body></body>\n</html>", doc1.html());
    }

    @Test
    void doctypeIsPrettyPrinted_2() {
        Document doc1 = Jsoup.parse("<!--\nlicense\n-->\n \n<!doctype html>\n<html>");
        doc1.outputSettings().prettyPrint(false);
        assertEquals("<!--\nlicense\n--><!doctype html>\n<html><head></head><body></body></html>", doc1.html());
    }

    @Test
    void doctypeIsPrettyPrinted_3() {
        Document doc2 = Jsoup.parse("\n  <!doctype html><html>");
        assertEquals("<!doctype html>\n<html>\n <head></head>\n <body></body>\n</html>", doc2.html());
    }

    @Test
    void doctypeIsPrettyPrinted_4() {
        Document doc3 = Jsoup.parse("<!doctype html>\n<html>");
        assertEquals("<!doctype html>\n<html>\n <head></head>\n <body></body>\n</html>", doc3.html());
    }

    @Test
    void doctypeIsPrettyPrinted_5() {
        Document doc4 = Jsoup.parse("\n<!doctype html>\n<html>");
        assertEquals("<!doctype html>\n<html>\n <head></head>\n <body></body>\n</html>", doc4.html());
    }

    @Test
    void doctypeIsPrettyPrinted_6() {
        Document doc5 = Jsoup.parse("\n<!--\n comment \n -->  <!doctype html>\n<html>");
        assertEquals("<!--\n comment \n -->\n<!doctype html>\n<html>\n <head></head>\n <body></body>\n</html>", doc5.html());
    }

    @Test
    void doctypeIsPrettyPrinted_7() {
        Document doc6 = Jsoup.parse("<!--\n comment \n -->  <!doctype html>\n<html>");
        assertEquals("<!--\n comment \n -->\n<!doctype html>\n<html>\n <head></head>\n <body></body>\n</html>", doc6.html());
    }

    @Test
    void dataInCdataNode_1() {
        Element el = new Element("div");
        CDataNode cdata = new CDataNode("Some CData");
        el.appendChild(cdata);
        assertEquals("Some CData", el.data());
    }

    @Test
    void dataInCdataNode_2() {
        Document parse = Jsoup.parse("One <![CDATA[Hello]]>");
        assertEquals("Hello", parse.data());
    }

    @ParameterizedTest
    @CsvSource(value = {
	"<div><p>Hello <span>there</span></div>, span, p, 0",
	"<div><p>Hello <span>there</span></div>, span, div, 1",
	"<div><p>Hello <span>there</span></div>, span, body, 2",
	"<div><p>Hello <span>there</span></div>, span, html, 3"
})
    public void testGetParents_2to5(String param1, String param2, String param3, int param4) {
        Document doc = Jsoup.parse(param1);
        Element span = doc.select(param2).first();
        Elements parents = span.parents();
        assertEquals(param3, parents.get(param4).tagName());
    }
}
