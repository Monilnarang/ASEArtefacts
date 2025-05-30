package org.languagetool.rules.ar;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.TestTools;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

public class ArabicTransVerbRuleTest_Purified {

    private ArabicTransVerbRule rule;

    private JLanguageTool lt;

    @Before
    public void setUp() throws IOException {
        rule = new ArabicTransVerbRule(TestTools.getEnglishMessages());
        lt = new JLanguageTool(Languages.getLanguageForShortCode("ar"));
    }

    private void assertCorrect(String sentence) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(sentence));
        assertEquals(0, matches.length);
    }

    private void assertIncorrect(String sentence) throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence(sentence));
        assertEquals(1, matches.length);
    }

    @Test
    public void testRule_1() throws IOException {
        assertCorrect("كان أَفَاضَ في الحديث");
    }

    @Test
    public void testRule_2() throws IOException {
        assertIncorrect("كان أفاض من الحديث");
    }

    @Test
    public void testRule_3() throws IOException {
        assertIncorrect("لقد أفاضت من الحديث");
    }

    @Test
    public void testRule_4() throws IOException {
        assertIncorrect("لقد أفاضت الحديث");
    }

    @Test
    public void testRule_5() throws IOException {
        assertIncorrect("كان أفاضها الحديث");
    }

    @Test
    public void testRule_6() throws IOException {
        assertIncorrect("إذ استعجل الأمر");
    }
}
