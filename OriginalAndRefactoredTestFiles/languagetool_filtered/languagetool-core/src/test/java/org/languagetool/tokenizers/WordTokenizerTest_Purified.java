package org.languagetool.tokenizers;

import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class WordTokenizerTest_Purified {

    private final WordTokenizer wordTokenizer = new WordTokenizer();

    private String tokenize(String text) {
        List<String> tokens = wordTokenizer.tokenize(text);
        return String.join("|", tokens);
    }

    @Test
    public void testIsUrl_1() {
        assertTrue(WordTokenizer.isUrl("www.languagetool.org"));
    }

    @Test
    public void testIsUrl_2() {
        assertTrue(WordTokenizer.isUrl("languagetool.org/"));
    }

    @Test
    public void testIsUrl_3() {
        assertTrue(WordTokenizer.isUrl("languagetool.org/foo"));
    }

    @Test
    public void testIsUrl_4() {
        assertTrue(WordTokenizer.isUrl("subdomain.languagetool.org/"));
    }

    @Test
    public void testIsUrl_5() {
        assertTrue(WordTokenizer.isUrl("http://www.languagetool.org"));
    }

    @Test
    public void testIsUrl_6() {
        assertTrue(WordTokenizer.isUrl("https://www.languagetool.org"));
    }

    @Test
    public void testIsUrl_7() {
        assertFalse(WordTokenizer.isUrl("languagetool.org"));
    }

    @Test
    public void testIsUrl_8() {
        assertFalse(WordTokenizer.isUrl("sub.languagetool.org"));
    }

    @Test
    public void testIsUrl_9() {
        assertFalse(WordTokenizer.isUrl("something-else"));
    }

    @Test
    public void testIsEMail_1() {
        assertTrue(WordTokenizer.isEMail("martin.mustermann@test.de"));
    }

    @Test
    public void testIsEMail_2() {
        assertTrue(WordTokenizer.isEMail("martin.mustermann@test.languagetool.de"));
    }

    @Test
    public void testIsEMail_3() {
        assertTrue(WordTokenizer.isEMail("martin-mustermann@test.com"));
    }

    @Test
    public void testIsEMail_4() {
        assertFalse(WordTokenizer.isEMail("@test.de"));
    }

    @Test
    public void testIsEMail_5() {
        assertFalse(WordTokenizer.isEMail("f.test@test"));
    }

    @Test
    public void testIsEMail_6() {
        assertFalse(WordTokenizer.isEMail("f@t.t"));
    }

    @Test
    public void testUrlTokenize_1() {
        assertEquals("\"|This| |http://foo.org|.|\"", tokenize("\"This http://foo.org.\""));
    }

    @Test
    public void testUrlTokenize_2() {
        assertEquals("«|This| |http://foo.org|.|»", tokenize("«This http://foo.org.»"));
    }

    @Test
    public void testUrlTokenize_3() {
        assertEquals("This| |http://foo.org|.|.|.", tokenize("This http://foo.org..."));
    }

    @Test
    public void testUrlTokenize_4() {
        assertEquals("This| |http://foo.org|.", tokenize("This http://foo.org."));
    }

    @Test
    public void testUrlTokenize_5() {
        assertEquals("This| |http://foo.org| |blah", tokenize("This http://foo.org blah"));
    }

    @Test
    public void testUrlTokenize_6() {
        assertEquals("This| |http://foo.org| |and| |ftp://bla.com| |blah", tokenize("This http://foo.org and ftp://bla.com blah"));
    }

    @Test
    public void testUrlTokenize_7() {
        assertEquals("foo| |http://localhost:32000/?ch=1| |bar", tokenize("foo http://localhost:32000/?ch=1 bar"));
    }

    @Test
    public void testUrlTokenize_8() {
        assertEquals("foo| |ftp://localhost:32000/| |bar", tokenize("foo ftp://localhost:32000/ bar"));
    }

    @Test
    public void testUrlTokenize_9() {
        assertEquals("foo| |http://google.de/?aaa| |bar", tokenize("foo http://google.de/?aaa bar"));
    }

    @Test
    public void testUrlTokenize_10() {
        assertEquals("foo| |http://www.flickr.com/123@N04/hallo#test| |bar", tokenize("foo http://www.flickr.com/123@N04/hallo#test bar"));
    }

    @Test
    public void testUrlTokenize_11() {
        assertEquals("foo| |http://www.youtube.com/watch?v=wDN_EYUvUq0| |bar", tokenize("foo http://www.youtube.com/watch?v=wDN_EYUvUq0 bar"));
    }

    @Test
    public void testUrlTokenize_12() {
        assertEquals("foo| |http://example.net/index.html?s=A54C6FE2%23info| |bar", tokenize("foo http://example.net/index.html?s=A54C6FE2%23info bar"));
    }

    @Test
    public void testUrlTokenize_13() {
        assertEquals("foo| |https://writerduet.com/script/#V6922~***~branch=-MClu-LnPrTNz8oz_rJb| |bar", tokenize("foo https://writerduet.com/script/#V6922~***~branch=-MClu-LnPrTNz8oz_rJb bar"));
    }

    @Test
    public void testUrlTokenize_14() {
        assertEquals("foo| |https://joe:passwd@example.net:8080/index.html?action=x&session=A54C6FE2#info| |bar", tokenize("foo https://joe:passwd@example.net:8080/index.html?action=x&session=A54C6FE2#info bar"));
    }

    @Test
    public void testUrlTokenizeWithQuote_1() {
        assertEquals("This| |'|http://foo.org|'| |blah", tokenize("This 'http://foo.org' blah"));
    }

    @Test
    public void testUrlTokenizeWithQuote_2() {
        assertEquals("This| |\"|http://foo.org|\"| |blah", tokenize("This \"http://foo.org\" blah"));
    }

    @Test
    public void testUrlTokenizeWithQuote_3() {
        assertEquals("This| |(|\"|http://foo.org|\"|)| |blah", tokenize("This (\"http://foo.org\") blah"));
    }

    @Test
    public void testUrlTokenizeWithAppendedCharacter_1() {
        assertEquals("foo| |(|http://ex.net/p?a=x#i|)| |bar", tokenize("foo (http://ex.net/p?a=x#i) bar"));
    }

    @Test
    public void testUrlTokenizeWithAppendedCharacter_2() {
        assertEquals("foo| |http://ex.net/p?a=x#i|,| |bar", tokenize("foo http://ex.net/p?a=x#i, bar"));
    }

    @Test
    public void testUrlTokenizeWithAppendedCharacter_3() {
        assertEquals("foo| |http://ex.net/p?a=x#i|.| |bar", tokenize("foo http://ex.net/p?a=x#i. bar"));
    }

    @Test
    public void testUrlTokenizeWithAppendedCharacter_4() {
        assertEquals("foo| |http://ex.net/p?a=x#i|:| |bar", tokenize("foo http://ex.net/p?a=x#i: bar"));
    }

    @Test
    public void testUrlTokenizeWithAppendedCharacter_5() {
        assertEquals("foo| |http://ex.net/p?a=x#i|?| |bar", tokenize("foo http://ex.net/p?a=x#i? bar"));
    }

    @Test
    public void testUrlTokenizeWithAppendedCharacter_6() {
        assertEquals("foo| |http://ex.net/p?a=x#i|!| |bar", tokenize("foo http://ex.net/p?a=x#i! bar"));
    }

    @Test
    public void testIncompleteUrlTokenize_1() {
        assertEquals("http|:|/", tokenize("http:/"));
    }

    @Test
    public void testIncompleteUrlTokenize_2() {
        assertEquals("http://", tokenize("http://"));
    }

    @Test
    public void testIncompleteUrlTokenize_3() {
        assertEquals("http://a", tokenize("http://a"));
    }

    @Test
    public void testIncompleteUrlTokenize_4() {
        assertEquals("foo| |http| |bar", tokenize("foo http bar"));
    }

    @Test
    public void testIncompleteUrlTokenize_5() {
        assertEquals("foo| |http|:| |bar", tokenize("foo http: bar"));
    }

    @Test
    public void testIncompleteUrlTokenize_6() {
        assertEquals("foo| |http|:|/| |bar", tokenize("foo http:/ bar"));
    }

    @Test
    public void testIncompleteUrlTokenize_7() {
        assertEquals("foo| |http://| |bar", tokenize("foo http:// bar"));
    }

    @Test
    public void testIncompleteUrlTokenize_8() {
        assertEquals("foo| |http://a| |bar", tokenize("foo http://a bar"));
    }

    @Test
    public void testIncompleteUrlTokenize_9() {
        assertEquals("foo| |http://|?| |bar", tokenize("foo http://? bar"));
    }

    @Test
    public void testCheckCurrencyExpression_1() {
        assertTrue(wordTokenizer.isCurrencyExpression("US$45"));
    }

    @Test
    public void testCheckCurrencyExpression_2() {
        assertTrue(wordTokenizer.isCurrencyExpression("5,000€"));
    }

    @Test
    public void testCheckCurrencyExpression_3() {
        assertTrue(wordTokenizer.isCurrencyExpression("£1.50"));
    }

    @Test
    public void testCheckCurrencyExpression_4() {
        assertTrue(wordTokenizer.isCurrencyExpression("R$1.999.99"));
    }

    @Test
    public void testCheckCurrencyExpression_5() {
        assertFalse(wordTokenizer.isCurrencyExpression("US$"));
    }

    @Test
    public void testCheckCurrencyExpression_6() {
        assertFalse(wordTokenizer.isCurrencyExpression("X€"));
    }

    @Test
    public void testCheckCurrencyExpression_7() {
        assertFalse(wordTokenizer.isCurrencyExpression(".50£"));
    }

    @Test
    public void testCheckCurrencyExpression_8() {
        assertFalse(wordTokenizer.isCurrencyExpression("5R$5"));
    }
}
