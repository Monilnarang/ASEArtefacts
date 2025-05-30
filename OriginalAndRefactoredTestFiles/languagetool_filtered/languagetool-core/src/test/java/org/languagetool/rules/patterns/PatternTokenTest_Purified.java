package org.languagetool.rules.patterns;

import com.google.common.collect.Sets;
import org.junit.Test;
import org.languagetool.AnalyzedToken;
import static org.junit.Assert.*;
import static org.languagetool.JLanguageTool.*;
import static org.languagetool.rules.patterns.PatternToken.UNKNOWN_TAG;

public class PatternTokenTest_Purified {

    @Test
    public void testSentenceStart_1_testMerged_1() {
        PatternToken patternToken = new PatternToken("", false, false, false);
        patternToken.setPosToken(new PatternToken.PosToken(SENTENCE_START_TAGNAME, false, false));
        assertTrue(patternToken.isSentenceStart());
        patternToken.setPosToken(new PatternToken.PosToken(SENTENCE_START_TAGNAME, false, true));
        assertFalse(patternToken.isSentenceStart());
    }

    @Test
    public void testSentenceStart_5() {
        PatternToken patternToken2 = new PatternToken("bla|blah", false, true, false);
        patternToken2.setPosToken(new PatternToken.PosToken("foo", true, true));
        assertFalse(patternToken2.isSentenceStart());
    }
}
