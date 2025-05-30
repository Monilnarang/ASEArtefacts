package org.languagetool;

import org.hamcrest.CoreMatchers;
import org.junit.Ignore;
import org.junit.Test;
import org.languagetool.language.*;
import org.languagetool.markup.AnnotatedText;
import org.languagetool.markup.AnnotatedTextBuilder;
import org.languagetool.rules.*;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class JLanguageToolTest_Parameterized {

    private static final Language english = Languages.getLanguageForShortCode("en");

    private List<String> getActiveRuleIds(JLanguageTool lt) {
        List<String> ruleIds = new ArrayList<>();
        for (Rule rule : lt.getAllActiveRules()) {
            ruleIds.add(rule.getId());
        }
        return ruleIds;
    }

    class InternalRule extends Rule {

        @Override
        public String getId() {
            return "INTERNAL_RULE";
        }

        @Override
        public String getDescription() {
            return "Internal rule";
        }

        @Override
        public RuleMatch[] match(AnalyzedSentence sentence) throws IOException {
            throw new UnsupportedOperationException();
        }
    }

    class TestRule extends Rule {

        private final int subId;

        public TestRule(int subId) {
            this.subId = subId;
        }

        @Override
        public RuleMatch[] match(AnalyzedSentence sentence) throws IOException {
            return toRuleMatchArray(Collections.emptyList());
        }

        @Override
        public String getFullId() {
            return String.format("TEST_RULE[%d]", subId);
        }

        @Override
        public String getDescription() {
            return "Test rule";
        }

        @Override
        public String getId() {
            return "TEST_RULE";
        }
    }

    private class IgnoreInterval {

        int left, right;

        IgnoreInterval(int left, int right) {
            this.left = left;
            this.right = right;
        }

        boolean contains(int position) {
            return left <= position & position <= right;
        }
    }

    private List<IgnoreInterval> calculateIgnoreIntervals(String message, boolean ignoreQuotes, boolean ignoreBrackets) {
        String ignorePattern = "(<.+>[^<]+</.+>)";
        if (ignoreQuotes) {
            ignorePattern += "|('[^']+')|(\"[^\"]\")";
        }
        if (ignoreBrackets) {
            ignorePattern += "|(\\([^)]+\\))";
        }
        Matcher ignoreMat = Pattern.compile(ignorePattern).matcher(message);
        List<IgnoreInterval> ignoreIntervals = new ArrayList<>();
        if (ignoreMat.find()) {
            for (int i = 0; i < ignoreMat.groupCount(); i++) {
                ignoreIntervals.add(new IgnoreInterval(ignoreMat.start(i), ignoreMat.end(i)));
            }
        }
        return ignoreIntervals;
    }

    private String getRuleMessage(Rule rule, JLanguageTool lt) throws Exception {
        Pattern p = Pattern.compile("<.+>([^<]+)</.+>");
        String example = rule.getIncorrectExamples().get(0).getExample();
        example = p.matcher(example).replaceAll("$1");
        List<AnalyzedSentence> sentences = lt.analyzeText(example);
        RuleMatch[] matches;
        if (rule instanceof TextLevelRule) {
            matches = ((TextLevelRule) rule).match(sentences);
        } else {
            matches = rule.match(sentences.get(0));
        }
        if (matches.length == 0) {
            return null;
        }
        return matches[0].getMessage().replace("<suggestion>", "").replace("</suggestion>", "");
    }

    @ParameterizedTest
    @MethodSource("Provider_testCountLines_1to4")
    public void testCountLines_1to4(int param1, String param2) {
        assertEquals(param1, JLanguageTool.countLineBreaks(param2));
    }

    static public Stream<Arguments> Provider_testCountLines_1to4() {
        return Stream.of(arguments(0, ""), arguments(1, "Hallo,\nnächste Zeile"), arguments(2, "\nZweite\nDritte"), arguments(4, "\nZweite\nDritte\n\n"));
    }
}
