package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.jsoup.TextUtil;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;
import org.jsoup.select.NodeVisitor;
import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.List;
import static org.jsoup.parser.Parser.*;
import static org.junit.jupiter.api.Assertions.*;

public class NodeTest_Purified {

    private Attributes getAttributesCaseInsensitive(Element element) {
        Attributes matches = new Attributes();
        for (Attribute attribute : element.attributes()) {
            if (attribute.getKey().equalsIgnoreCase("value")) {
                matches.put(attribute);
            }
        }
        return matches;
    }

    private Attributes singletonAttributes() {
        Attributes attributes = new Attributes();
        attributes.put("value", "bar");
        return attributes;
    }

    @Test
    public void root_1_testMerged_1() {
        Document doc = Jsoup.parse("<div><p>Hello");
        Element p = doc.select("p").first();
        Node root = p.root();
        assertSame(doc, root);
        assertNull(root.parent());
        assertSame(doc.root(), doc);
        assertSame(doc.root(), doc.ownerDocument());
    }

    @Test
    public void root_5_testMerged_2() {
        Element standAlone = new Element(Tag.valueOf("p"), "");
        assertNull(standAlone.parent());
        assertSame(standAlone.root(), standAlone);
        assertNull(standAlone.ownerDocument());
    }

    @Test
    void nodeName_1_testMerged_1() {
        Element div = new Element("DIV");
        assertEquals("DIV", div.tagName());
        assertEquals("DIV", div.nodeName());
        assertEquals("div", div.normalName());
        assertTrue(div.nameIs("div"));
    }

    @Test
    void nodeName_5_testMerged_2() {
        TextNode text = new TextNode("Some Text");
        assertEquals("#text", text.nodeName());
        assertEquals("#text", text.normalName());
    }
}
