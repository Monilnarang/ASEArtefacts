package org.jsoup.nodes;

import org.jsoup.Jsoup;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;
import static org.jsoup.nodes.Document.OutputSettings;
import static org.jsoup.nodes.Entities.EscapeMode.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class EntitiesTest_Parameterized {

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

    @ParameterizedTest
    @MethodSource("Provider_xhtml_1to4")
    public void xhtml_1to4(int param1, String param2) {
        assertEquals(param1, xhtml.codepointForName(param2));
    }

    static public Stream<Arguments> Provider_xhtml_1to4() {
        return Stream.of(arguments(38, "amp"), arguments(62, "gt"), arguments(60, "lt"), arguments(34, "quot"));
    }

    @ParameterizedTest
    @MethodSource("Provider_xhtml_5to8")
    public void xhtml_5to8(String param1, int param2) {
        assertEquals(param1, xhtml.nameForCodepoint(param2));
    }

    static public Stream<Arguments> Provider_xhtml_5to8() {
        return Stream.of(arguments("amp", 38), arguments("gt", 62), arguments("lt", 60), arguments("quot", 34));
    }

    @ParameterizedTest
    @MethodSource("Provider_getByName_1to4")
    public void getByName_1to4(String param1, String param2) {
        assertEquals(param1, Entities.getByName(param2));
    }

    static public Stream<Arguments> Provider_getByName_1to4() {
        return Stream.of(arguments("≫⃒", "nGt"), arguments("fj", "fjlig"), arguments("≫", "gg"), arguments("©", "copy"));
    }
}
