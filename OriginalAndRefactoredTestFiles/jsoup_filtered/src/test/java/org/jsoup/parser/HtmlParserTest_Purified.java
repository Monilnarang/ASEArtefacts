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

public class HtmlParserTest_Purified {

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
    public void handlesUnclosedTitleAtEof_1() {
        assertEquals("Data", Jsoup.parse("<title>Data").title());
    }

    @Test
    public void handlesUnclosedTitleAtEof_2() {
        assertEquals("Data<", Jsoup.parse("<title>Data<").title());
    }

    @Test
    public void handlesUnclosedTitleAtEof_3() {
        assertEquals("Data</", Jsoup.parse("<title>Data</").title());
    }

    @Test
    public void handlesUnclosedTitleAtEof_4() {
        assertEquals("Data</t", Jsoup.parse("<title>Data</t").title());
    }

    @Test
    public void handlesUnclosedTitleAtEof_5() {
        assertEquals("Data</ti", Jsoup.parse("<title>Data</ti").title());
    }

    @Test
    public void handlesUnclosedTitleAtEof_6() {
        assertEquals("Data", Jsoup.parse("<title>Data</title>").title());
    }

    @Test
    public void handlesUnclosedTitleAtEof_7() {
        assertEquals("Data", Jsoup.parse("<title>Data</title >").title());
    }

    @Test
    public void handlesUnclosedScriptAtEof_1() {
        assertEquals("Data", Jsoup.parse("<script>Data").select("script").first().data());
    }

    @Test
    public void handlesUnclosedScriptAtEof_2() {
        assertEquals("Data<", Jsoup.parse("<script>Data<").select("script").first().data());
    }

    @Test
    public void handlesUnclosedScriptAtEof_3() {
        assertEquals("Data</sc", Jsoup.parse("<script>Data</sc").select("script").first().data());
    }

    @Test
    public void handlesUnclosedScriptAtEof_4() {
        assertEquals("Data</-sc", Jsoup.parse("<script>Data</-sc").select("script").first().data());
    }

    @Test
    public void handlesUnclosedScriptAtEof_5() {
        assertEquals("Data</sc-", Jsoup.parse("<script>Data</sc-").select("script").first().data());
    }

    @Test
    public void handlesUnclosedScriptAtEof_6() {
        assertEquals("Data</sc--", Jsoup.parse("<script>Data</sc--").select("script").first().data());
    }

    @Test
    public void handlesUnclosedScriptAtEof_7() {
        assertEquals("Data", Jsoup.parse("<script>Data</script>").select("script").first().data());
    }

    @Test
    public void handlesUnclosedScriptAtEof_8() {
        assertEquals("Data</script", Jsoup.parse("<script>Data</script").select("script").first().data());
    }

    @Test
    public void handlesUnclosedScriptAtEof_9() {
        assertEquals("Data", Jsoup.parse("<script>Data</script ").select("script").first().data());
    }

    @Test
    public void handlesUnclosedScriptAtEof_10() {
        assertEquals("Data", Jsoup.parse("<script>Data</script n").select("script").first().data());
    }

    @Test
    public void handlesUnclosedScriptAtEof_11() {
        assertEquals("Data", Jsoup.parse("<script>Data</script n=").select("script").first().data());
    }

    @Test
    public void handlesUnclosedScriptAtEof_12() {
        assertEquals("Data", Jsoup.parse("<script>Data</script n=\"").select("script").first().data());
    }

    @Test
    public void handlesUnclosedScriptAtEof_13() {
        assertEquals("Data", Jsoup.parse("<script>Data</script n=\"p").select("script").first().data());
    }

    @Test
    public void handlesUnclosedRawtextAtEof_1() {
        assertEquals("Data", Jsoup.parse("<style>Data").select("style").first().data());
    }

    @Test
    public void handlesUnclosedRawtextAtEof_2() {
        assertEquals("Data</st", Jsoup.parse("<style>Data</st").select("style").first().data());
    }

    @Test
    public void handlesUnclosedRawtextAtEof_3() {
        assertEquals("Data", Jsoup.parse("<style>Data</style>").select("style").first().data());
    }

    @Test
    public void handlesUnclosedRawtextAtEof_4() {
        assertEquals("Data</style", Jsoup.parse("<style>Data</style").select("style").first().data());
    }

    @Test
    public void handlesUnclosedRawtextAtEof_5() {
        assertEquals("Data</-style", Jsoup.parse("<style>Data</-style").select("style").first().data());
    }

    @Test
    public void handlesUnclosedRawtextAtEof_6() {
        assertEquals("Data</style-", Jsoup.parse("<style>Data</style-").select("style").first().data());
    }

    @Test
    public void handlesUnclosedRawtextAtEof_7() {
        assertEquals("Data</style--", Jsoup.parse("<style>Data</style--").select("style").first().data());
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
}
