package org.languagetool.rules;

import org.junit.Test;
import org.languagetool.FakeLanguage;
import org.languagetool.JLanguageTool;
import org.languagetool.Language;
import org.languagetool.TestTools;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class GenericUnpairedBracketsRuleTest_Purified {

    private JLanguageTool lt;

    private void setUpRule(Language language) {
        lt = new JLanguageTool(language);
        for (Rule rule : lt.getAllRules()) {
            lt.disableRule(rule.getId());
        }
        GenericUnpairedBracketsRule rule = new GenericUnpairedBracketsRule(TestTools.getEnglishMessages(), Arrays.asList("»"), Arrays.asList("«"));
        lt.addRule(rule);
    }

    private void assertMatches(int expectedMatches, String input) throws IOException {
        List<RuleMatch> ruleMatches = lt.check(input);
        assertEquals("Expected " + expectedMatches + " matches, got: " + ruleMatches, expectedMatches, ruleMatches.size());
    }

    public static GenericUnpairedBracketsRule getBracketsRule(JLanguageTool lt) {
        for (Rule rule : lt.getAllActiveRules()) {
            if (rule instanceof GenericUnpairedBracketsRule) {
                return (GenericUnpairedBracketsRule) rule;
            }
        }
        throw new RuntimeException("Rule not found: " + GenericUnpairedBracketsRule.class);
    }

    @Test
    public void testRule_1() throws IOException {
        assertMatches(0, "This is »correct«.");
    }

    @Test
    public void testRule_2() throws IOException {
        assertMatches(0, "»Correct«\n»And »here« it ends.«");
    }

    @Test
    public void testRule_3() throws IOException {
        assertMatches(0, "»Correct. This is more than one sentence.«");
    }

    @Test
    public void testRule_4() throws IOException {
        assertMatches(0, "»Correct. This is more than one sentence.«\n»And »here« it ends.«");
    }

    @Test
    public void testRule_5() throws IOException {
        assertMatches(0, "»Correct«\n\n»And here it ends.«\n\nMore text.");
    }

    @Test
    public void testRule_6() throws IOException {
        assertMatches(0, "»Correct, he said. This is the next sentence.« Here's another sentence.");
    }

    @Test
    public void testRule_7() throws IOException {
        assertMatches(0, "»Correct, he said.\n\nThis is the next sentence.« Here's another sentence.");
    }

    @Test
    public void testRule_8() throws IOException {
        assertMatches(0, "»Correct, he said.\n\n\n\nThis is the next sentence.« Here's another sentence.");
    }

    @Test
    public void testRule_9() throws IOException {
        assertMatches(0, "This »is also »correct««.");
    }

    @Test
    public void testRule_10() throws IOException {
        assertMatches(0, "Good.\n\nThis »is also »correct««.");
    }

    @Test
    public void testRule_11() throws IOException {
        assertMatches(0, "Good.\n\n\nThis »is also »correct««.");
    }

    @Test
    public void testRule_12() throws IOException {
        assertMatches(0, "Good.\n\n\n\nThis »is also »correct««.");
    }

    @Test
    public void testRule_13() throws IOException {
        assertMatches(0, "This is funny :-)");
    }

    @Test
    public void testRule_14() throws IOException {
        assertMatches(0, "This is sad :-( isn't it");
    }

    @Test
    public void testRule_15() throws IOException {
        assertMatches(0, "This is funny :)");
    }

    @Test
    public void testRule_16() throws IOException {
        assertMatches(0, "This is sad :( isn't it");
    }

    @Test
    public void testRule_17() throws IOException {
        assertMatches(0, "a) item one\nb) item two");
    }

    @Test
    public void testRule_18() throws IOException {
        assertMatches(0, "a) item one\nb) item two\nc) item three");
    }

    @Test
    public void testRule_19() throws IOException {
        assertMatches(0, "\na) item one\nb) item two\nc) item three");
    }

    @Test
    public void testRule_20() throws IOException {
        assertMatches(0, "\n\na) item one\nb) item two\nc) item three");
    }

    @Test
    public void testRule_21() throws IOException {
        assertMatches(0, "This is a), not b)");
    }

    @Test
    public void testRule_22() throws IOException {
        assertMatches(0, "This is it (a, not b) some more test");
    }

    @Test
    public void testRule_23() throws IOException {
        assertMatches(0, "This is »not an error yet");
    }

    @Test
    public void testRule_24() throws IOException {
        assertMatches(0, "See https://de.wikipedia.org/wiki/Schlammersdorf_(Adelsgeschlecht)");
    }

    @Test
    public void testRule_25() throws IOException {
        assertMatches(1, "This is not correct«");
    }

    @Test
    public void testRule_26() throws IOException {
        assertMatches(1, "This is »not correct.");
    }

    @Test
    public void testRule_27() throws IOException {
        assertMatches(1, "This is »not an error yet\n\nBut now it has become one");
    }

    @Test
    public void testRule_28() throws IOException {
        assertMatches(1, "This is correct.\n\n»But this is not.");
    }

    @Test
    public void testRule_29() throws IOException {
        assertMatches(1, "This is correct.\n\nBut this is not«");
    }

    @Test
    public void testRule_30() throws IOException {
        assertMatches(1, "»This is correct«\n\nBut this is not«");
    }

    @Test
    public void testRule_31() throws IOException {
        assertMatches(1, "»This is correct«\n\nBut this »is« not«");
    }

    @Test
    public void testRule_32() throws IOException {
        assertMatches(1, "This is not correct. No matter if it's more than one sentence«");
    }

    @Test
    public void testRule_33() throws IOException {
        assertMatches(1, "»This is not correct. No matter if it's more than one sentence");
    }

    @Test
    public void testRule_34() throws IOException {
        assertMatches(1, "Correct, he said. This is the next sentence.« Here's another sentence.");
    }

    @Test
    public void testRule_35() throws IOException {
        assertMatches(1, "»Correct, he said. This is the next sentence. Here's another sentence.");
    }

    @Test
    public void testRule_36() throws IOException {
        assertMatches(1, "»Correct, he said. This is the next sentence.\n\nHere's another sentence.");
    }

    @Test
    public void testRule_37() throws IOException {
        assertMatches(1, "»Correct, he said. This is the next sentence.\n\n\n\nHere's another sentence.");
    }
}
