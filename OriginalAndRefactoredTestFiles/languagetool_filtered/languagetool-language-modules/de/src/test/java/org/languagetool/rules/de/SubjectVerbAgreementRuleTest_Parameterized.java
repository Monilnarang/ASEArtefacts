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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class SubjectVerbAgreementRuleTest_Parameterized {

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

    @ParameterizedTest
    @MethodSource("Provider_testPrevChunkIsNominative_1to2")
    public void testPrevChunkIsNominative_1to2(int param1, String param2) throws IOException {
        assertTrue(rule.prevChunkIsNominative(getTokens(param2), param1));
    }

    static public Stream<Arguments> Provider_testPrevChunkIsNominative_1to2() {
        return Stream.of(arguments(2, "Die Katze ist süß"), arguments(4, "Das Fell der Katzen ist süß"));
    }

    @ParameterizedTest
    @MethodSource("Provider_testPrevChunkIsNominative_3to6")
    public void testPrevChunkIsNominative_3to6(int param1, String param2) throws IOException {
        assertFalse(rule.prevChunkIsNominative(getTokens(param2), param1));
    }

    static public Stream<Arguments> Provider_testPrevChunkIsNominative_3to6() {
        return Stream.of(arguments(2, "Dem Mann geht es gut."), arguments(2, "Dem alten Mann geht es gut."), arguments(2, "Beiden Filmen war kein Erfolg beschieden."), arguments(3, "Aber beiden Filmen war kein Erfolg beschieden."));
    }
}
