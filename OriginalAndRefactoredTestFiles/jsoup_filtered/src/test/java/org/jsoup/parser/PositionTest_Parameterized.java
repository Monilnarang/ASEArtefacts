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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PositionTest_Parameterized {

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
    void parserTrackDefaults_1_testMerged_1() {
        Parser htmlParser = Parser.htmlParser();
        assertFalse(htmlParser.isTrackPosition());
        htmlParser.setTrackPosition(true);
        assertTrue(htmlParser.isTrackPosition());
    }

    @Test
    void parserTrackDefaults_3_testMerged_2() {
        Parser xmlParser = Parser.xmlParser();
        assertFalse(xmlParser.isTrackPosition());
        xmlParser.setTrackPosition(true);
        assertTrue(xmlParser.isTrackPosition());
    }

    @ParameterizedTest
    @MethodSource("trackAttributesPositionsDedupes_1_1Provider")
    void trackAttributesPositionsDedupes_1_1(String param1, String param2) {
        StringBuilder htmlPos = new StringBuilder();
        accumulateAttributePositions(htmlDoc.expectFirst(param1), htmlPos);
        assertEquals(param2, htmlPos.toString());
    }

    static public Stream<Arguments> trackAttributesPositionsDedupes_1_1Provider() {
        return Stream.of(arguments("p", "id:3-5=6-7; "), arguments("p", "id:3-5=6-7; "));
    }

    @ParameterizedTest
    @MethodSource("trackAttributesPositionsDedupes_2_2Provider")
    void trackAttributesPositionsDedupes_2_2(String param1, String param2) {
        StringBuilder htmlUcPos = new StringBuilder();
        accumulateAttributePositions(htmlDocUc.expectFirst(param1), htmlUcPos);
        assertEquals(param2, htmlUcPos.toString());
    }

    static public Stream<Arguments> trackAttributesPositionsDedupes_2_2Provider() {
        return Stream.of(arguments("p", "id:3-5=6-7; Id:13-15=16-17; "), arguments("p", "Id:3-5=6-7; id:8-10=11-12; "));
    }

    @ParameterizedTest
    @MethodSource("trackAttributesPositionsDedupes_3_3Provider")
    void trackAttributesPositionsDedupes_3_3(String param1, String param2) {
        StringBuilder xmlPos = new StringBuilder();
        accumulateAttributePositions(xmlDoc.expectFirst(param1), xmlPos);
        assertEquals(param2, xmlPos.toString());
    }

    static public Stream<Arguments> trackAttributesPositionsDedupes_3_3Provider() {
        return Stream.of(arguments("p", "id:3-5=6-7; Id:13-15=16-17; "), arguments("p", "Id:3-5=6-7; id:8-10=11-12; "));
    }

    @ParameterizedTest
    @MethodSource("trackAttributesPositionsDedupes_4_4Provider")
    void trackAttributesPositionsDedupes_4_4(String param1, String param2) {
        StringBuilder xmlLcPos = new StringBuilder();
        accumulateAttributePositions(xmlDocLc.expectFirst(param1), xmlLcPos);
        assertEquals(param2, xmlLcPos.toString());
    }

    static public Stream<Arguments> trackAttributesPositionsDedupes_4_4Provider() {
        return Stream.of(arguments("p", "id:3-5=6-7; "), arguments("p", "id:3-5=6-7; "));
    }
}
