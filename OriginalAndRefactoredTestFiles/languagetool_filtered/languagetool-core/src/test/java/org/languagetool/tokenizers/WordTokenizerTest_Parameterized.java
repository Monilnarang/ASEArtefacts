package org.languagetool.tokenizers;

import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class WordTokenizerTest_Parameterized {

    private final WordTokenizer wordTokenizer = new WordTokenizer();

    private String tokenize(String text) {
        List<String> tokens = wordTokenizer.tokenize(text);
        return String.join("|", tokens);
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsUrl_1to6")
    public void testIsUrl_1to6(String param1) {
        assertTrue(WordTokenizer.isUrl(param1));
    }

    static public Stream<Arguments> Provider_testIsUrl_1to6() {
        return Stream.of(arguments("www.languagetool.org"), arguments("languagetool.org/"), arguments("languagetool.org/foo"), arguments("subdomain.languagetool.org/"), arguments("http://www.languagetool.org"), arguments("https://www.languagetool.org"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsUrl_7to9")
    public void testIsUrl_7to9(String param1) {
        assertFalse(WordTokenizer.isUrl(param1));
    }

    static public Stream<Arguments> Provider_testIsUrl_7to9() {
        return Stream.of(arguments("languagetool.org"), arguments("sub.languagetool.org"), arguments("something-else"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsEMail_1to3")
    public void testIsEMail_1to3(String param1) {
        assertTrue(WordTokenizer.isEMail(param1));
    }

    static public Stream<Arguments> Provider_testIsEMail_1to3() {
        return Stream.of(arguments("martin.mustermann@test.de"), arguments("martin.mustermann@test.languagetool.de"), arguments("martin-mustermann@test.com"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testIsEMail_4to6")
    public void testIsEMail_4to6(String param1) {
        assertFalse(WordTokenizer.isEMail(param1));
    }

    static public Stream<Arguments> Provider_testIsEMail_4to6() {
        return Stream.of(arguments("@test.de"), arguments("f.test@test"), arguments("f@t.t"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testUrlTokenize_1_1_1_1to2_2_2_2to3_3_3_3to4_4_4to5_5_5to6_6_6to7_7to8_8to9_9to14")
    public void testUrlTokenize_1_1_1_1to2_2_2_2to3_3_3_3to4_4_4to5_5_5to6_6_6to7_7to8_8to9_9to14(String param1, String param2) {
        assertEquals(param1, tokenize(param2));
    }

    static public Stream<Arguments> Provider_testUrlTokenize_1_1_1_1to2_2_2_2to3_3_3_3to4_4_4to5_5_5to6_6_6to7_7to8_8to9_9to14() {
        return Stream.of(arguments("\"|This| |http://foo.org|.|\"", "\"This http://foo.org.\""), arguments("«|This| |http://foo.org|.|»", "«This http://foo.org.»"), arguments("This| |http://foo.org|.|.|.", "This http://foo.org..."), arguments("This| |http://foo.org|.", "This http://foo.org."), arguments("This| |http://foo.org| |blah", "This http://foo.org blah"), arguments("This| |http://foo.org| |and| |ftp://bla.com| |blah", "This http://foo.org and ftp://bla.com blah"), arguments("foo| |http://localhost:32000/?ch=1| |bar", "foo http://localhost:32000/?ch=1 bar"), arguments("foo| |ftp://localhost:32000/| |bar", "foo ftp://localhost:32000/ bar"), arguments("foo| |http://google.de/?aaa| |bar", "foo http://google.de/?aaa bar"), arguments("foo| |http://www.flickr.com/123@N04/hallo#test| |bar", "foo http://www.flickr.com/123@N04/hallo#test bar"), arguments("foo| |http://www.youtube.com/watch?v=wDN_EYUvUq0| |bar", "foo http://www.youtube.com/watch?v=wDN_EYUvUq0 bar"), arguments("foo| |http://example.net/index.html?s=A54C6FE2%23info| |bar", "foo http://example.net/index.html?s=A54C6FE2%23info bar"), arguments("foo| |https://writerduet.com/script/#V6922~***~branch=-MClu-LnPrTNz8oz_rJb| |bar", "foo https://writerduet.com/script/#V6922~***~branch=-MClu-LnPrTNz8oz_rJb bar"), arguments("foo| |https://joe:passwd@example.net:8080/index.html?action=x&session=A54C6FE2#info| |bar", "foo https://joe:passwd@example.net:8080/index.html?action=x&session=A54C6FE2#info bar"), arguments("This| |'|http://foo.org|'| |blah", "This 'http://foo.org' blah"), arguments("This| |\"|http://foo.org|\"| |blah", "This \"http://foo.org\" blah"), arguments("This| |(|\"|http://foo.org|\"|)| |blah", "This (\"http://foo.org\") blah"), arguments("foo| |(|http://ex.net/p?a=x#i|)| |bar", "foo (http://ex.net/p?a=x#i) bar"), arguments("foo| |http://ex.net/p?a=x#i|,| |bar", "foo http://ex.net/p?a=x#i, bar"), arguments("foo| |http://ex.net/p?a=x#i|.| |bar", "foo http://ex.net/p?a=x#i. bar"), arguments("foo| |http://ex.net/p?a=x#i|:| |bar", "foo http://ex.net/p?a=x#i: bar"), arguments("foo| |http://ex.net/p?a=x#i|?| |bar", "foo http://ex.net/p?a=x#i? bar"), arguments("foo| |http://ex.net/p?a=x#i|!| |bar", "foo http://ex.net/p?a=x#i! bar"), arguments("http|:|/", "http:/"), arguments("http://", "http://"), arguments("http://a", "http://a"), arguments("foo| |http| |bar", "foo http bar"), arguments("foo| |http|:| |bar", "foo http: bar"), arguments("foo| |http|:|/| |bar", "foo http:/ bar"), arguments("foo| |http://| |bar", "foo http:// bar"), arguments("foo| |http://a| |bar", "foo http://a bar"), arguments("foo| |http://|?| |bar", "foo http://? bar"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCheckCurrencyExpression_1to4")
    public void testCheckCurrencyExpression_1to4(String param1) {
        assertTrue(wordTokenizer.isCurrencyExpression(param1));
    }

    static public Stream<Arguments> Provider_testCheckCurrencyExpression_1to4() {
        return Stream.of(arguments("US$45"), arguments("5,000€"), arguments("£1.50"), arguments("R$1.999.99"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testCheckCurrencyExpression_5to8")
    public void testCheckCurrencyExpression_5to8(String param1) {
        assertFalse(wordTokenizer.isCurrencyExpression(param1));
    }

    static public Stream<Arguments> Provider_testCheckCurrencyExpression_5to8() {
        return Stream.of(arguments("US$"), arguments("X€"), arguments(".50£"), arguments("5R$5"));
    }
}
