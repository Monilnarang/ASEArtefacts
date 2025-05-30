package org.languagetool.rules.de;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.languagetool.*;
import org.languagetool.chunking.GermanChunker;
import org.languagetool.language.German;
import org.languagetool.rules.RuleMatch;

public class SubjectVerbAgreementRuleTest_Purified {

    private static SubjectVerbAgreementRule rule;

    private static JLanguageTool lt;

    @BeforeClass
    public static void setUp() {
        Language german = Languages.getLanguageForShortCode("de-DE");
        rule = new SubjectVerbAgreementRule(TestTools.getMessages("de"), (German) german);
        lt = new JLanguageTool(german);
    }

    private AnalyzedTokenReadings[] getTokens(String s) throws IOException {
        return lt.getAnalyzedSentence(s).getTokensWithoutWhitespace();
    }

    private void assertGood(String input) throws IOException {
        RuleMatch[] matches = getMatches(input);
        if (matches.length != 0) {
            fail("Got unexpected match(es) for '" + input + "': " + Arrays.toString(matches), input);
        }
    }

    private void assertBad(String input) throws IOException {
        int matchCount = getMatches(input).length;
        if (matchCount == 0) {
            fail("Did not get the expected match for '" + input + "'", input);
        }
    }

    private void fail(String message, String input) throws IOException {
        if (!GermanChunker.isDebug()) {
            GermanChunker.setDebug(true);
            getMatches(input);
        }
        Assert.fail(message);
    }

    private RuleMatch[] getMatches(String input) throws IOException {
        return rule.match(lt.getAnalyzedSentence(input));
    }

    @Test
    public void testPrevChunkIsNominative_1() throws IOException {
        assertTrue(rule.prevChunkIsNominative(getTokens("Die Katze ist süß"), 2));
    }

    @Test
    public void testPrevChunkIsNominative_2() throws IOException {
        assertTrue(rule.prevChunkIsNominative(getTokens("Das Fell der Katzen ist süß"), 4));
    }

    @Test
    public void testPrevChunkIsNominative_3() throws IOException {
        assertFalse(rule.prevChunkIsNominative(getTokens("Dem Mann geht es gut."), 2));
    }

    @Test
    public void testPrevChunkIsNominative_4() throws IOException {
        assertFalse(rule.prevChunkIsNominative(getTokens("Dem alten Mann geht es gut."), 2));
    }

    @Test
    public void testPrevChunkIsNominative_5() throws IOException {
        assertFalse(rule.prevChunkIsNominative(getTokens("Beiden Filmen war kein Erfolg beschieden."), 2));
    }

    @Test
    public void testPrevChunkIsNominative_6() throws IOException {
        assertFalse(rule.prevChunkIsNominative(getTokens("Aber beiden Filmen war kein Erfolg beschieden."), 3));
    }
}
