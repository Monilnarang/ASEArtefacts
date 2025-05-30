package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.TextUtil;
import org.jsoup.integration.servlets.FileServlet;
import org.jsoup.internal.Normalizer;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.CDataNode;
import org.jsoup.nodes.Comment;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.LeafNode;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.Range;
import org.jsoup.nodes.TextNode;
import org.jsoup.nodes.XmlDeclaration;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import static org.junit.jupiter.api.Assertions.*;

class PositionTest_Purified {

    static Parser TrackingHtmlParser = Parser.htmlParser().setTrackPosition(true);

    static Parser TrackingXmlParser = Parser.xmlParser().setTrackPosition(true);

    static void accumulatePositions(Node node, StringBuilder sb) {
        sb.append(node.nodeName()).append(':').append(node.sourceRange().startPos()).append('-').append(node.sourceRange().endPos());
        if (node instanceof Element) {
            Element el = (Element) node;
            sb.append("~").append(el.endSourceRange().startPos()).append('-').append(el.endSourceRange().endPos());
        }
        sb.append("; ");
    }

    private void printRange(Node node) {
        if (node instanceof Element) {
            Element el = (Element) node;
            System.out.println(el.tagName() + "\t" + el.sourceRange().start().pos() + "-" + el.sourceRange().end().pos() + "\t... " + el.endSourceRange().start().pos() + "-" + el.endSourceRange().end().pos());
        } else {
            System.out.println(node.nodeName() + "\t" + node.sourceRange().start().pos() + "-" + node.sourceRange().end().pos());
        }
    }

    static void accumulateAttributePositions(Node node, StringBuilder sb) {
        if (node instanceof LeafNode)
            return;
        for (Attribute attribute : node.attributes()) {
            accumulatePositions(attribute, sb);
        }
    }

    static void accumulatePositions(Attribute attr, StringBuilder sb) {
        Range.AttributeRange range = attr.sourceRange();
        sb.append(attr.getKey()).append(':').append(range.nameRange().startPos()).append('-').append(range.nameRange().endPos()).append('=').append(range.valueRange().startPos()).append('-').append(range.valueRange().endPos());
        sb.append("; ");
    }

    @Test
    void trackAttributesPositionsDedupes_1() {
        StringBuilder htmlPos = new StringBuilder();
        accumulateAttributePositions(htmlDoc.expectFirst("p"), htmlPos);
        assertEquals("id:3-5=6-7; ", htmlPos.toString());
    }

    @Test
    void trackAttributesPositionsDedupes_2() {
        StringBuilder htmlUcPos = new StringBuilder();
        accumulateAttributePositions(htmlDocUc.expectFirst("p"), htmlUcPos);
        assertEquals("id:3-5=6-7; Id:13-15=16-17; ", htmlUcPos.toString());
    }

    @Test
    void trackAttributesPositionsDedupes_3() {
        StringBuilder xmlPos = new StringBuilder();
        accumulateAttributePositions(xmlDoc.expectFirst("p"), xmlPos);
        assertEquals("id:3-5=6-7; Id:13-15=16-17; ", xmlPos.toString());
    }

    @Test
    void trackAttributesPositionsDedupes_4() {
        StringBuilder xmlLcPos = new StringBuilder();
        accumulateAttributePositions(xmlDocLc.expectFirst("p"), xmlLcPos);
        assertEquals("id:3-5=6-7; ", xmlLcPos.toString());
    }

    @Test
    void trackAttributesPositionsDirectionalDedupes_1() {
        StringBuilder htmlPos = new StringBuilder();
        accumulateAttributePositions(htmlDoc.expectFirst("p"), htmlPos);
        assertEquals("id:3-5=6-7; ", htmlPos.toString());
    }

    @Test
    void trackAttributesPositionsDirectionalDedupes_2() {
        StringBuilder htmlUcPos = new StringBuilder();
        accumulateAttributePositions(htmlDocUc.expectFirst("p"), htmlUcPos);
        assertEquals("Id:3-5=6-7; id:8-10=11-12; ", htmlUcPos.toString());
    }

    @Test
    void trackAttributesPositionsDirectionalDedupes_3() {
        StringBuilder xmlPos = new StringBuilder();
        accumulateAttributePositions(xmlDoc.expectFirst("p"), xmlPos);
        assertEquals("Id:3-5=6-7; id:8-10=11-12; ", xmlPos.toString());
    }

    @Test
    void trackAttributesPositionsDirectionalDedupes_4() {
        StringBuilder xmlLcPos = new StringBuilder();
        accumulateAttributePositions(xmlDocLc.expectFirst("p"), xmlLcPos);
        assertEquals("id:3-5=6-7; ", xmlLcPos.toString());
    }
}
