package org.jsoup.parser;

import org.jsoup.Jsoup;
import org.jsoup.TextUtil;
import org.jsoup.integration.ParseTest;
import org.jsoup.internal.StringUtil;
import org.jsoup.nodes.*;
import org.jsoup.safety.Safelist;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Stream;
import static org.jsoup.parser.ParseSettings.preserveCase;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class HtmlParserTest_Parameterized {

    private static Stream<Arguments> dupeAttributeData() {
        return Stream.of(Arguments.of("<p One=One ONE=Two Two=two one=Three One=Four two=Five>Text</p>", "<p one=\"One\" two=\"two\">Text</p>"), Arguments.of("<img One=One ONE=Two Two=two one=Three One=Four two=Five>", "<img one=\"One\" two=\"two\">"), Arguments.of("<form One=One ONE=Two Two=two one=Three One=Four two=Five></form>", "<form one=\"One\" two=\"two\"></form>"));
    }

    private boolean didAddElements(String input) {
        Document html = Jsoup.parse(input);
        Document xml = Jsoup.parse(input, "", Parser.xmlParser());
        int htmlElementCount = html.getAllElements().size();
        int xmlElementCount = xml.getAllElements().size();
        return htmlElementCount > xmlElementCount;
    }

    private static void assertHtmlNamespace(Element el) {
        assertEquals(Parser.NamespaceHtml, el.tag().namespace());
    }

    private static void assertSvgNamespace(Element el) {
        assertEquals(Parser.NamespaceSvg, el.tag().namespace());
    }

    private static void assertMathNamespace(Element el) {
        assertEquals(Parser.NamespaceMathml, el.tag().namespace());
    }

    @Test
    public void canDetectAutomaticallyAddedElements_1() {
        String bare = "<script>One</script>";
        assertTrue(didAddElements(bare));
    }

    @Test
    public void canDetectAutomaticallyAddedElements_2() {
        String full = "<html><head><title>Check</title></head><body><p>One</p></body></html>";
        assertFalse(didAddElements(full));
    }

    @ParameterizedTest
    @MethodSource("Provider_handlesUnclosedTitleAtEof_1to7")
    public void handlesUnclosedTitleAtEof_1to7(String param1, String param2) {
        assertEquals(param1, Jsoup.parse(param2).title());
    }

    static public Stream<Arguments> Provider_handlesUnclosedTitleAtEof_1to7() {
        return Stream.of(arguments("Data", "<title>Data"), arguments("Data<", "<title>Data<"), arguments("Data</", "<title>Data</"), arguments("Data</t", "<title>Data</t"), arguments("Data</ti", "<title>Data</ti"), arguments("Data", "<title>Data</title>"), arguments("Data", "<title>Data</title >"));
    }

    @ParameterizedTest
    @MethodSource("Provider_handlesUnclosedScriptAtEof_1_1to2_2to3_3to4_4to5_5to6_6to7_7to13")
    public void handlesUnclosedScriptAtEof_1_1to2_2to3_3to4_4to5_5to6_6to7_7to13(String param1, String param2, String param3) {
        assertEquals(param1, Jsoup.parse(param3).select(param2).first().data());
    }

    static public Stream<Arguments> Provider_handlesUnclosedScriptAtEof_1_1to2_2to3_3to4_4to5_5to6_6to7_7to13() {
        return Stream.of(arguments("Data", "script", "<script>Data"), arguments("Data<", "script", "<script>Data<"), arguments("Data</sc", "script", "<script>Data</sc"), arguments("Data</-sc", "script", "<script>Data</-sc"), arguments("Data</sc-", "script", "<script>Data</sc-"), arguments("Data</sc--", "script", "<script>Data</sc--"), arguments("Data", "script", "<script>Data</script>"), arguments("Data</script", "script", "<script>Data</script"), arguments("Data", "script", "<script>Data</script "), arguments("Data", "script", "<script>Data</script n"), arguments("Data", "script", "<script>Data</script n="), arguments("Data", "script", "<script>Data</script n=\""), arguments("Data", "script", "<script>Data</script n=\"p"), arguments("Data", "style", "<style>Data"), arguments("Data</st", "style", "<style>Data</st"), arguments("Data", "style", "<style>Data</style>"), arguments("Data</style", "style", "<style>Data</style"), arguments("Data</-style", "style", "<style>Data</-style"), arguments("Data</style-", "style", "<style>Data</style-"), arguments("Data</style--", "style", "<style>Data</style--"));
    }
}
