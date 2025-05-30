package org.languagetool.rules.en;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.TestTools;
import org.languagetool.markup.AnnotatedText;
import org.languagetool.markup.AnnotatedTextBuilder;
import org.languagetool.rules.RuleMatch;
import org.languagetool.rules.TextLevelRule;
import java.io.IOException;
import java.util.Collections;
import static org.junit.Assert.assertEquals;

public class EnglishUnpairedBracketsRuleTest_Purified {

    private TextLevelRule rule;

    private JLanguageTool lt;

    @Before
    public void setUp() {
        rule = new EnglishUnpairedBracketsRule(TestTools.getEnglishMessages(), Languages.getLanguageForShortCode("en"));
        lt = new JLanguageTool(Languages.getLanguageForShortCode("en"));
    }

    private void assertCorrect(String sentence) throws IOException {
        RuleMatch[] matches = rule.match(Collections.singletonList(lt.getAnalyzedSentence(sentence)));
        assertEquals(0, matches.length);
    }

    private void assertCorrectText(String sentences) throws IOException {
        AnnotatedText aText = new AnnotatedTextBuilder().addText(sentences).build();
        RuleMatch[] matches = rule.match(lt.analyzeText(sentences), aText);
        assertEquals(0, matches.length);
    }

    private void assertIncorrect(String sentence) throws IOException {
        RuleMatch[] matches = rule.match(Collections.singletonList(lt.getAnalyzedSentence(sentence)));
        assertEquals(1, matches.length);
    }

    private int getMatches(String input, JLanguageTool lt) throws IOException {
        return lt.check(input).size();
    }

    @Test
    public void testRule_1() throws IOException {
        assertCorrect("(This is a test sentence).");
    }

    @Test
    public void testRule_2() throws IOException {
        assertCorrect("This is a word 'test'.");
    }

    @Test
    public void testRule_3() throws IOException {
        assertCorrect("This is no smiley: (some more text)");
    }

    @Test
    public void testRule_4() throws IOException {
        assertCorrect("This is a sentence with a smiley :)");
    }

    @Test
    public void testRule_5() throws IOException {
        assertCorrect("This is a sentence with a smiley :(");
    }

    @Test
    public void testRule_6() throws IOException {
        assertCorrect("This is a sentence with a smiley :-)");
    }

    @Test
    public void testRule_7() throws IOException {
        assertCorrect("This is a sentence with a smiley ;-) and so on...");
    }

    @Test
    public void testRule_8() throws IOException {
        assertCorrect("This is a [test] sentence...");
    }

    @Test
    public void testRule_9() throws IOException {
        assertCorrect("The plight of Tamil refugees caused a surge of support from most of the Tamil political parties.[90]");
    }

    @Test
    public void testRule_10() throws IOException {
        assertCorrect("(([20] [20] [20]))");
    }

    @Test
    public void testRule_11() throws IOException {
        assertCorrect("This is a \"special test\", right?");
    }

    @Test
    public void testRule_12() throws IOException {
        assertCorrect("We discussed this in Chapter 1).");
    }

    @Test
    public void testRule_13() throws IOException {
        assertCorrect("The jury recommended that: (1) Four additional deputies be employed.");
    }

    @Test
    public void testRule_14() throws IOException {
        assertCorrect("We discussed this in section 1a).");
    }

    @Test
    public void testRule_15() throws IOException {
        assertCorrect("We discussed this in section iv).");
    }

    @Test
    public void testRule_16() throws IOException {
        assertCorrect("(Ketab fi Isti'mal al-'Adad al-Hindi)");
    }

    @Test
    public void testRule_17() throws IOException {
        assertCorrect("(al-'Adad al-Hindi)");
    }

    @Test
    public void testRule_18() throws IOException {
        assertCorrect("will-o'-the-wisp");
    }

    @Test
    public void testRule_19() throws IOException {
        assertCorrect("cat-o’-nine-tails");
    }

    @Test
    public void testRule_20() throws IOException {
        assertCorrect("a) item one\nb) item two\nc) item three");
    }

    @Test
    public void testRule_21() throws IOException {
        assertCorrectText("\n\n" + "a) New York\n" + "b) Boston\n");
    }

    @Test
    public void testRule_22() throws IOException {
        assertCorrectText("\n\n" + "1.) New York\n" + "2.) Boston\n");
    }

    @Test
    public void testRule_23() throws IOException {
        assertCorrectText("\n\n" + "XII.) New York\n" + "XIII.) Boston\n");
    }

    @Test
    public void testRule_24() throws IOException {
        assertCorrectText("\n\n" + "A) New York\n" + "B) Boston\n" + "C) Foo\n");
    }

    @Test
    public void testRule_25() throws IOException {
        assertIncorrect("(This is a test sentence.");
    }

    @Test
    public void testRule_26() throws IOException {
        assertCorrect("This is not so (neither a nor b");
    }

    @Test
    public void testRule_27() throws IOException {
        assertIncorrect("This is not so (neither a nor b.");
    }

    @Test
    public void testRule_28() throws IOException {
        assertIncorrect("This is not so neither a nor b)");
    }

    @Test
    public void testRule_29() throws IOException {
        assertIncorrect("This is not so neither foo nor bar)");
    }

    @Test
    public void testRule_30() throws IOException {
        assertCorrect("Some text (and some funny remark :-) with more text to follow");
    }

    @Test
    public void testRule_31() throws IOException {
        assertIncorrect("Some text (and some funny remark :-) with more text to follow!");
    }

    @Test
    public void testRule_32_testMerged_32() throws IOException {
        RuleMatch[] matches;
        matches = rule.match(Collections.singletonList(lt.getAnalyzedSentence("(This is a test] sentence.")));
        assertEquals(2, matches.length);
        matches = rule.match(Collections.singletonList(lt.getAnalyzedSentence("This [is (a test} sentence.")));
        assertEquals(3, matches.length);
    }
}
