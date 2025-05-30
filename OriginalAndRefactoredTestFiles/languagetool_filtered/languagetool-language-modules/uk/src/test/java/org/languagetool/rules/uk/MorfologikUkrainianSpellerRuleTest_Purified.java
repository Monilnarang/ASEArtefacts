package org.languagetool.rules.uk;

import static org.junit.Assert.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.TestTools;
import org.languagetool.language.Ukrainian;
import org.languagetool.rules.RuleMatch;

public class MorfologikUkrainianSpellerRuleTest_Purified extends AbstractRuleTest {

    @Before
    public void init() throws IOException {
        rule = new MorfologikUkrainianSpellerRule(TestTools.getMessages("uk"), Ukrainian.DEFAULT_VARIANT, null, Collections.emptyList());
    }

    @Test
    public void testMorfologikSpeller_33() throws IOException {
        assertEmptyMatch("Добрий ранок, Україно!");
    }

    @Test
    public void testMorfologikSpeller_1() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("До вас прийде заввідділу!")).length);
    }

    @Test
    public void testMorfologikSpeller_2() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence(",")).length);
    }

    @Test
    public void testMorfologikSpeller_3() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("123454")).length);
    }

    @Test
    public void testMorfologikSpeller_4() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("До нас приїде The Beatles!")).length);
    }

    @Test
    public void testMorfologikSpeller_5() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("піс\u00ADні")).length);
    }

    @Test
    public void testMorfologikSpeller_6() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("піс\u00ADні піс\u00ADні")).length);
    }

    @Test
    public void testMorfologikSpeller_7() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("ось\u2011ось")).length);
    }

    @Test
    public void testMorfologikSpeller_8() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("-ськ-")).length);
    }

    @Test
    public void testMorfologikSpeller_9() throws IOException {
        assertEquals(1, rule.match(lt.getAnalyzedSentence("Халгін-Гол")).length);
    }

    @Test
    public void testMorfologikSpeller_10_testMerged_11() throws IOException {
        RuleMatch[] matches = rule.match(lt.getAnalyzedSentence("Іва́н Петро́вич Котляре́вський"));
        assertEquals(0, matches.length);
        matches = rule.match(lt.getAnalyzedSentence("1998 ро́ку"));
        assertEquals(0, rule.match(lt.getAnalyzedSentence("а")).length);
        matches = rule.match(lt.getAnalyzedSentence("прийдешнiй"));
        assertEquals(1, matches.length);
        assertEquals("прийдешній", matches[0].getSuggestedReplacements().get(0));
        assertEquals(1, rule.match(lt.getAnalyzedSentence("польовою дружино")).length);
        RuleMatch[] match = rule.match(lt.getAnalyzedSentence("Читання віршів Т.Г.Шевченко і Г.Тютюнника"));
        assertEquals(new ArrayList<RuleMatch>(), Arrays.asList(match));
        match = rule.match(lt.getAnalyzedSentence("Читання віршів Т. Г. Шевченко і Г. Тютюнника"));
        match = rule.match(lt.getAnalyzedSentence("Англі́йська мова (англ. English language, English) належить до германської групи"));
        match = rule.match(lt.getAnalyzedSentence("Англі́йська мова (англ English language, English) належить до германської групи"));
        assertEquals(1, match.length);
        match = rule.match(lt.getAnalyzedSentence("100 тис. гривень"));
        match = rule.match(lt.getAnalyzedSentence("100 кв. м"));
        match = rule.match(lt.getAnalyzedSentence("100 км²"));
        match = rule.match(lt.getAnalyzedSentence("100 кв м"));
        assertEquals(1, Arrays.asList(match).size());
        match = rule.match(lt.getAnalyzedSentence("півтора раза"));
        assertEquals(0, match.length);
        match = rule.match(lt.getAnalyzedSentence("УКРА"));
        String sent = "Іва\u0301н Петро\u0301ввич";
        match = rule.match(lt.getAnalyzedSentence(sent));
        assertEquals(sent.indexOf("Петро"), match[0].getFromPos());
        assertEquals(sent.length(), match[0].getToPos());
        sent = "голага́нівська";
        assertEquals(0, match[0].getFromPos());
    }

    @Test
    public void testMorfologikSpeller_15() throws IOException {
        assertEmptyMatch("душе");
    }

    @Test
    public void testCompounds_1() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("Жакет був синьо-жовтого кольору")).length);
    }

    @Test
    public void testCompounds_2() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("Він багато сидів на інтернет-форумах")).length);
    }

    @Test
    public void testCompounds_3() throws IOException {
        assertEquals(1, rule.match(lt.getAnalyzedSentence("Він багато сидів на інтермет-форумах")).length);
    }

    @Test
    public void testCompounds_4() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("екс-креветка")).length);
    }

    @Test
    public void testCompounds_5() throws IOException {
        assertEquals(1, rule.match(lt.getAnalyzedSentence("банд-формування.")).length);
    }

    @Test
    public void testCompounds_6() throws IOException {
        assertEquals(0, rule.match(lt.getAnalyzedSentence("екоблогер")).length);
    }
}
