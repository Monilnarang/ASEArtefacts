package org.languagetool.rules.ar;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.language.Arabic;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

public class ArabicHomophonesCheckRuleTest_Purified {

    private ArabicHomophonesRule rule;

    private JLanguageTool lt;

    @Before
    public void setUp() {
        rule = new ArabicHomophonesRule(TestTools.getEnglishMessages());
        lt = new JLanguageTool(new Arabic());
    }

    @Test
    public void testRule_1() throws IOException {
        assertEquals(1, rule.match(lt.getAnalyzedSentence("ضن")).length);
    }

    @Test
    public void testRule_2() throws IOException {
        assertEquals(1, rule.match(lt.getAnalyzedSentence("حاضر")).length);
    }

    @Test
    public void testRule_3() throws IOException {
        assertEquals(1, rule.match(lt.getAnalyzedSentence("حض")).length);
    }
}
