package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.jsoup.parser.ParseSettings;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class AttributeTest_Parameterized {

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

    @ParameterizedTest
    @MethodSource("Provider_booleanAttributesAreNotCaseSensitive_1to3")
    void booleanAttributesAreNotCaseSensitive_1to3(String param1) {
        assertTrue(Attribute.isBooleanAttribute(param1));
    }

    static public Stream<Arguments> Provider_booleanAttributesAreNotCaseSensitive_1to3() {
        return Stream.of(arguments("required"), arguments("REQUIRED"), arguments("rEQUIREd"));
    }
}
