package org.languagetool.rules;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.AnalyzedSentence;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import java.io.IOException;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class CommaWhitespaceRuleTest_Purified {

    private CommaWhitespaceRule rule;

    private JLanguageTool lt;

    @Before
    public void setUp() {
        rule = new CommaWhitespaceRule(TestTools.getEnglishMessages());
        lt = new JLanguageTool(TestTools.getDemoLanguage());
    }

    private void assertMatches(String text, int expectedMatches) throws IOException {
        assertEquals(expectedMatches, rule.match(lt.getAnalyzedSentence(text)).length);
    }

    private void assertMatchesForText(String text, int expectedMatches) throws IOException {
        List<AnalyzedSentence> analyzedSentences = lt.analyzeText(text);
        int matchCount = 0;
        for (AnalyzedSentence analyzedSentence : analyzedSentences) {
            matchCount += rule.match(analyzedSentence).length;
        }
        assertEquals(expectedMatches, matchCount);
    }

    @Test
    public void testRule_1() throws IOException {
        assertMatches("This is a test sentence.", 0);
    }

    @Test
    public void testRule_2() throws IOException {
        assertMatches("I work with the technology .Net and Azure.", 0);
    }

    @Test
    public void testRule_3() throws IOException {
        assertMatches("I work with the technology .NET and Azure.", 0);
    }

    @Test
    public void testRule_4() throws IOException {
        assertMatches("I use .MP3 or .WAV file suffix", 0);
    }

    @Test
    public void testRule_5() throws IOException {
        assertMatches("This, is, a test sentence.", 0);
    }

    @Test
    public void testRule_6() throws IOException {
        assertMatches("This (foo bar) is a test!.", 0);
    }

    @Test
    public void testRule_7() throws IOException {
        assertMatches("Das kostet €2,45.", 0);
    }

    @Test
    public void testRule_8() throws IOException {
        assertMatches("Das kostet 50,- Euro", 0);
    }

    @Test
    public void testRule_9() throws IOException {
        assertMatches("This is a sentence with ellipsis ...", 0);
    }

    @Test
    public void testRule_10() throws IOException {
        assertMatches("This is a figure: .5 and it's correct.", 0);
    }

    @Test
    public void testRule_11() throws IOException {
        assertMatches("This is $1,000,000.", 0);
    }

    @Test
    public void testRule_12() throws IOException {
        assertMatches("This is 1,5.", 0);
    }

    @Test
    public void testRule_13() throws IOException {
        assertMatches("This is a ,,test''.", 0);
    }

    @Test
    public void testRule_14() throws IOException {
        assertMatches("Run ./validate.sh to check the file.", 0);
    }

    @Test
    public void testRule_15() throws IOException {
        assertMatches("This is,\u00A0really,\u00A0non-breaking whitespace.", 0);
    }

    @Test
    public void testRule_16() throws IOException {
        assertMatches("In his book,\u0002 Einstein proved this to be true.", 0);
    }

    @Test
    public void testRule_17() throws IOException {
        assertMatches("- [ ] A checkbox at GitHub", 0);
    }

    @Test
    public void testRule_18() throws IOException {
        assertMatches("- [x] A checked checkbox at GitHub", 0);
    }

    @Test
    public void testRule_19() throws IOException {
        assertMatches("A sentence 'with' ten \"correct\" examples of ’using’ quotation “marks” at «once» in it.", 0);
    }

    @Test
    public void testRule_20() throws IOException {
        assertMatches("I'd recommend resaving the .DOC as a PDF file.", 0);
    }

    @Test
    public void testRule_21() throws IOException {
        assertMatches("I'd recommend resaving the .mp3 as a WAV file.", 0);
    }

    @Test
    public void testRule_22() throws IOException {
        assertMatches("I'd suggest buying the .org domain.", 0);
    }

    @Test
    public void testRule_23() throws IOException {
        assertMatches(". This isn't good.", 0);
    }

    @Test
    public void testRule_24() throws IOException {
        assertMatches("), this isn't good.", 0);
    }

    @Test
    public void testRule_25() throws IOException {
        assertMatches("Das sind .exe-Dateien", 0);
    }

    @Test
    public void testRule_26() throws IOException {
        assertMatches("I live in .Los Angeles", 1);
    }

    @Test
    public void testRule_27() throws IOException {
        assertMatchesForText("Die Vertriebsniederlassu\u00ADng der Versorgungstechnik..\u00AD.", 1);
    }

    @Test
    public void testRule_28() throws IOException {
        assertMatchesForText("Die Vertriebsniederlassu\u00ADng der Versorgungstechnik..\u00AD.\n", 1);
    }

    @Test
    public void testRule_29() throws IOException {
        assertMatches("This,is a test sentence.", 1);
    }

    @Test
    public void testRule_30() throws IOException {
        assertMatches("This , is a test sentence.", 1);
    }

    @Test
    public void testRule_31() throws IOException {
        assertMatches("This ,is a test sentence.", 2);
    }

    @Test
    public void testRule_32() throws IOException {
        assertMatches(",is a test sentence.", 2);
    }

    @Test
    public void testRule_33() throws IOException {
        assertMatches("This ( foo bar) is a test!.", 1);
    }

    @Test
    public void testRule_34() throws IOException {
        assertMatches("This (foo bar ) is a test!.", 1);
    }

    @Test
    public void testRule_35() throws IOException {
        assertMatches("This is a sentence with an orphaned full stop .", 1);
    }

    @Test
    public void testRule_36() throws IOException {
        assertMatches("This is a test with a OOo footnote\u0002, which is denoted by 0x2 in the text.", 0);
    }

    @Test
    public void testRule_37() throws IOException {
        assertMatches("A sentence ' with ' ten \" incorrect \" examples of ’ using ’ quotation “ marks ” at « once » in it.", 10);
    }

    @Test
    public void testRule_38() throws IOException {
        assertMatches("A sentence ' with' one examples of wrong quotations marks in it.", 1);
    }

    @Test
    public void testRule_39() throws IOException {
        assertMatches("A sentence 'with ' one examples of wrong quotations marks in it.", 1);
    }

    @Test
    public void testRule_40_testMerged_40() throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence("ABB (  z.B. )"));
        assertEquals(2, matches.length);
        assertEquals(4, matches[0].getFromPos());
        assertEquals(6, matches[0].getToPos());
        assertEquals(11, matches[1].getFromPos());
        assertEquals(13, matches[1].getToPos());
        matches = rule.match(lt.getAnalyzedSentence("This ,"));
        assertEquals(1, matches.length);
        assertEquals(",", matches[0].getSuggestedReplacements().get(0));
        matches = rule.match(lt.getAnalyzedSentence("This ,is a test sentence."));
        assertEquals(", ", matches[0].getSuggestedReplacements().get(0));
        matches = rule.match(lt.getAnalyzedSentence("This , is a test sentence."));
        matches = rule.match(lt.getAnalyzedSentence("You \" fixed\" it."));
        assertEquals("\" ", matches[0].getSuggestedReplacements().get(0));
        assertEquals(" \"", matches[0].getSuggestedReplacements().get(1));
        assertEquals(3, matches[0].getFromPos());
        matches = rule.match(lt.getAnalyzedSentence("You \"fixed \" it."));
        assertEquals(10, matches[0].getFromPos());
        assertEquals(13, matches[0].getToPos());
    }

    @Test
    public void testRule_61() throws IOException {
        assertMatches("Ellipsis . . . as suggested by The Chicago Manual of Style", 3);
    }

    @Test
    public void testRule_62() throws IOException {
        assertMatches("Ellipsis . . . . as suggested by The Chicago Manual of Style", 4);
    }
}
