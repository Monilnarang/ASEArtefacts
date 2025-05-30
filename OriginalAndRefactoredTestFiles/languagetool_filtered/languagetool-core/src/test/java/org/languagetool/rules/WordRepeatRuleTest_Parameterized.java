package org.languagetool.rules;

import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.language.Demo;
import java.io.IOException;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class WordRepeatRuleTest_Parameterized {

    private final Demo demoLanguage = new Demo();

    private final JLanguageTool lt = new JLanguageTool(demoLanguage);

    private final WordRepeatRule rule = new WordRepeatRule(TestTools.getEnglishMessages(), demoLanguage);

    private void assertGood(String s) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(s));
        assertThat(matches.length, is(0));
    }

    private void assertBad(String s) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(s));
        assertThat(matches.length, is(1));
    }

    @ParameterizedTest
    @MethodSource("Provider_test_1to6")
    public void test_1to6(String param1) throws IOException {
        assertGood(param1);
    }

    static public Stream<Arguments> Provider_test_1to6() {
        return Stream.of(arguments("A test"), arguments("A test."), arguments("A test..."), arguments("1 000 000 years"), arguments("010 020 030"), arguments("\uD83D\uDC4D\uD83D\uDC9A\uD83C\uDF32\uD83C\uDF32"));
    }

    @ParameterizedTest
    @MethodSource("Provider_test_7to9")
    public void test_7to9(String param1) throws IOException {
        assertBad(param1);
    }

    static public Stream<Arguments> Provider_test_7to9() {
        return Stream.of(arguments("A A test"), arguments("A a test"), arguments("This is is a test"));
    }
}
