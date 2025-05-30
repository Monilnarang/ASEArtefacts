package org.languagetool.rules.patterns;

import org.junit.Test;
import org.languagetool.AnalyzedSentence;
import org.languagetool.AnalyzedToken;
import org.languagetool.AnalyzedTokenReadings;
import org.languagetool.FakeLanguage;
import java.util.Arrays;
import java.util.Collections;
import static org.junit.Assert.assertEquals;
import static org.languagetool.rules.patterns.PatternRuleBuilderHelper.*;
import static org.languagetool.rules.patterns.RuleSet.textLemmaHinted;

public class RuleSetTest_Purified {

    private static final AnalyzedSentence sampleSentence = new AnalyzedSentence(new AnalyzedTokenReadings[] { new AnalyzedTokenReadings(new AnalyzedToken("token", "pos", "lemma")) });

    private static void assertRulesForSentence(RuleSet ruleSet, PatternRule... expected) {
        assertEquals(Arrays.asList(expected), ruleSet.rulesForSentence(sampleSentence));
    }

    private static PatternRule ruleOf(PatternToken token) {
        return new PatternRule("", new FakeLanguage(), Collections.singletonList(token), "", "", "");
    }

    @Test
    public void textHintsAreHonored_1_testMerged_1() {
        PatternRule suitable = ruleOf(csToken("token"));
        assertRulesForSentence(RuleSet.textHinted(Collections.singletonList(suitable)), suitable);
    }

    @Test
    public void textHintsAreHonored_4() {
        assertRulesForSentence(RuleSet.textHinted(Collections.singletonList(ruleOf(csToken("unsuitable")))));
    }

    @Test
    public void lemmaHintsAreHonored_1_testMerged_1() {
        PatternRule suitable = ruleOf(new PatternTokenBuilder().token("lemma").matchInflectedForms().build());
        assertRulesForSentence(textLemmaHinted(Collections.singletonList(suitable)), suitable);
    }

    @Test
    public void lemmaHintsAreHonored_4_testMerged_2() {
        PatternToken unsuitable = new PatternTokenBuilder().csToken("unsuitable").matchInflectedForms().build();
        assertRulesForSentence(textLemmaHinted(Collections.singletonList(ruleOf(unsuitable))));
        PatternRule unrelated = ruleOf(pos("somePos"));
        assertRulesForSentence(textLemmaHinted(Arrays.asList(ruleOf(unsuitable), unrelated)), unrelated);
    }
}
