package org.languagetool.rules.ar;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.TestTools;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

public class ArabicCommaWhitespaceRuleTest_Purified {

    private ArabicCommaWhitespaceRule rule;

    private JLanguageTool lt;

    @Before
    public void setUp() {
        rule = new ArabicCommaWhitespaceRule(TestTools.getEnglishMessages());
        lt = new JLanguageTool(Languages.getLanguageForShortCode("ar"));
    }

    private void assertMatches(String text, int expectedMatches) throws IOException {
        assertEquals(expectedMatches, rule.match(lt.getAnalyzedSentence(text)).length);
    }

    @Test
    public void testRule_1() throws IOException {
        assertMatches("هذه جملة تجريبية.", 0);
    }

    @Test
    public void testRule_2() throws IOException {
        assertMatches("هذه, هي, جملة التجربة.", 0);
    }

    @Test
    public void testRule_3() throws IOException {
        assertMatches("قل (كيت وكيت) تجربة!.", 0);
    }

    @Test
    public void testRule_4() throws IOException {
        assertMatches("تكلف €2,45.", 0);
    }

    @Test
    public void testRule_5() throws IOException {
        assertMatches("ثمنها 50,- يورو", 0);
    }

    @Test
    public void testRule_6() throws IOException {
        assertMatches("جملة مع علامات الحذف ...", 0);
    }

    @Test
    public void testRule_7() throws IOException {
        assertMatches("هذه صورة: .5 وهي صحيحة.", 0);
    }

    @Test
    public void testRule_8() throws IOException {
        assertMatches("هذه $1,000,000.", 0);
    }

    @Test
    public void testRule_9() throws IOException {
        assertMatches("هذه 1,5.", 0);
    }

    @Test
    public void testRule_10() throws IOException {
        assertMatches("هذا ,,فحص''.", 0);
    }

    @Test
    public void testRule_11() throws IOException {
        assertMatches("نفّذ ./validate.sh لفحص الملف.", 0);
    }

    @Test
    public void testRule_12() throws IOException {
        assertMatches("هذه,\u00A0حقا,\u00A0فراغ غير فاصل.", 0);
    }

    @Test
    public void testRule_13() throws IOException {
        assertMatches("هذه،جملة للتجربة.", 1);
    }

    @Test
    public void testRule_14() throws IOException {
        assertMatches("هذه ، جملة للتجربة.", 1);
    }

    @Test
    public void testRule_15() throws IOException {
        assertMatches("هذه ،تجربة جملة.", 2);
    }

    @Test
    public void testRule_16() throws IOException {
        assertMatches("،هذه جملة للتجربة.", 2);
    }
}
