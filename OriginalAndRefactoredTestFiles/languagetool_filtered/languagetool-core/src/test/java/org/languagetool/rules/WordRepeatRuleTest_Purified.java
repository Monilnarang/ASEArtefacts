package org.languagetool.rules;

import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.language.Demo;
import java.io.IOException;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class WordRepeatRuleTest_Purified {

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

    @Test
    public void test_1() throws IOException {
        assertGood("A test");
    }

    @Test
    public void test_2() throws IOException {
        assertGood("A test.");
    }

    @Test
    public void test_3() throws IOException {
        assertGood("A test...");
    }

    @Test
    public void test_4() throws IOException {
        assertGood("1 000 000 years");
    }

    @Test
    public void test_5() throws IOException {
        assertGood("010 020 030");
    }

    @Test
    public void test_6() throws IOException {
        assertGood("\uD83D\uDC4D\uD83D\uDC9A\uD83C\uDF32\uD83C\uDF32");
    }

    @Test
    public void test_7() throws IOException {
        assertBad("A A test");
    }

    @Test
    public void test_8() throws IOException {
        assertBad("A a test");
    }

    @Test
    public void test_9() throws IOException {
        assertBad("This is is a test");
    }
}
