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

public class ElementTest_Purified {

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
    public void testGetParents_1_testMerged_1() {
        Document doc = Jsoup.parse("<div><p>Hello <span>there</span></div>");
        Element span = doc.select("span").first();
        Elements parents = span.parents();
        assertEquals(4, parents.size());
        assertEquals("p", parents.get(0).tagName());
        assertEquals("div", parents.get(1).tagName());
        assertEquals("body", parents.get(2).tagName());
        assertEquals("html", parents.get(3).tagName());
    }

    @Test
    public void testGetParents_6() {
        Element orphan = new Element("p");
        Elements none = orphan.parents();
        assertEquals(0, none.size());
    }

    @Test
    public void testRoot_1_testMerged_1() {
        Element el = new Element("a");
        el.append("<span>Hello</span>");
        assertEquals("<a><span>Hello</span></a>", el.outerHtml());
        Element span = el.selectFirst("span");
        assertNotNull(span);
        Element el2 = span.root();
        assertSame(el, el2);
    }

    @Test
    public void testRoot_4_testMerged_2() {
        Document doc = Jsoup.parse("<div><p>One<p>Two<p>Three");
        Element div = doc.selectFirst("div");
        assertSame(doc, div.root());
        assertSame(doc, div.ownerDocument());
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

    @Test
    void outerHtmlAppendable_1() {
        StringBuffer buffer = new StringBuffer();
        doc.body().outerHtml(buffer);
        assertEquals("\n<body>\n <div>\n  One\n </div>\n</body>", buffer.toString());
    }

    @Test
    void outerHtmlAppendable_2() {
        StringBuilder builder = new StringBuilder();
        doc.body().outerHtml(builder);
        assertEquals("<body>\n <div>\n  One\n </div>\n</body>", builder.toString());
    }
}
