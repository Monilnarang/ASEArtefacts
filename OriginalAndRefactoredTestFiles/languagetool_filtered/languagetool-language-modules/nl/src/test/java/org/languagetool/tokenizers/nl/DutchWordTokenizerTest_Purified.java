package org.languagetool.tokenizers.nl;

import org.junit.Test;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class DutchWordTokenizerTest_Purified {

    private final DutchWordTokenizer wordTokenizer = new DutchWordTokenizer();

    private void assertTokenize(String input, String expected) {
        List<String> result = wordTokenizer.tokenize(input);
        assertEquals(expected, result.toString());
    }

    @Test
    public void testTokenize_1() {
        assertTokenize("This is\u00A0a test", "[This,  , is,  , a,  , test]");
    }

    @Test
    public void testTokenize_2() {
        assertTokenize("Bla bla oma's bla bla 'test", "[Bla,  , bla,  , oma's,  , bla,  , bla,  , ', test]");
    }

    @Test
    public void testTokenize_3() {
        assertTokenize("Bla bla oma`s bla bla 'test", "[Bla,  , bla,  , oma`s,  , bla,  , bla,  , ', test]");
    }

    @Test
    public void testTokenize_4() {
        assertTokenize("Ik zie het''", "[Ik,  , zie,  , het, ', ']");
    }

    @Test
    public void testTokenize_5() {
        assertTokenize("Ik zie het``", "[Ik,  , zie,  , het, `, `]");
    }

    @Test
    public void testTokenize_6() {
        assertTokenize("''Ik zie het", "[', ', Ik,  , zie,  , het]");
    }

    @Test
    public void testTokenize_7() {
        assertTokenize("Ik 'zie' het", "[Ik,  , ', zie, ',  , het]");
    }

    @Test
    public void testTokenize_8() {
        assertTokenize("Ik ‘zie’ het", "[Ik,  , ‘, zie, ’,  , het]");
    }

    @Test
    public void testTokenize_9() {
        assertTokenize("Ik \"zie\" het", "[Ik,  , \", zie, \",  , het]");
    }

    @Test
    public void testTokenize_10() {
        assertTokenize("Ik “zie” het", "[Ik,  , “, zie, ”,  , het]");
    }

    @Test
    public void testTokenize_11() {
        assertTokenize("'zie'", "[', zie, ']");
    }

    @Test
    public void testTokenize_12() {
        assertTokenize("‘zie’", "[‘, zie, ’]");
    }

    @Test
    public void testTokenize_13() {
        assertTokenize("\"zie\"", "[\", zie, \"]");
    }

    @Test
    public void testTokenize_14() {
        assertTokenize("“zie”", "[“, zie, ”]");
    }

    @Test
    public void testTokenize_15() {
        assertTokenize("Ik `zie het", "[Ik,  , `, zie,  , het]");
    }

    @Test
    public void testTokenize_16() {
        assertTokenize("Ik ``zie het", "[Ik,  , `, `, zie,  , het]");
    }

    @Test
    public void testTokenize_17() {
        assertTokenize("'", "[']");
    }

    @Test
    public void testTokenize_18() {
        assertTokenize("''", "[, ', ']");
    }

    @Test
    public void testTokenize_19() {
        assertTokenize("'x'", "[', x, ']");
    }

    @Test
    public void testTokenize_20() {
        assertTokenize("`x`", "[`, x, `]");
    }
}
