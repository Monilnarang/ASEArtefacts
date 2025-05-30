package org.languagetool.rules.ar;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.TestTools;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class ArabicCommaWhitespaceRuleTest_Parameterized {

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

    @ParameterizedTest
    @MethodSource("Provider_testRule_1to16")
    public void testRule_1to16(String param1, int param2) throws IOException {
        assertMatches(param1, param2);
    }

    static public Stream<Arguments> Provider_testRule_1to16() {
        return Stream.of(arguments("هذه جملة تجريبية.", 0), arguments("هذه, هي, جملة التجربة.", 0), arguments("قل (كيت وكيت) تجربة!.", 0), arguments("تكلف €2,45.", 0), arguments("ثمنها 50,- يورو", 0), arguments("جملة مع علامات الحذف ...", 0), arguments("هذه صورة: .5 وهي صحيحة.", 0), arguments("هذه $1,000,000.", 0), arguments("هذه 1,5.", 0), arguments("هذا ,,فحص''.", 0), arguments("نفّذ ./validate.sh لفحص الملف.", 0), arguments("هذه,\u00A0حقا,\u00A0فراغ غير فاصل.", 0), arguments("هذه،جملة للتجربة.", 1), arguments("هذه ، جملة للتجربة.", 1), arguments("هذه ،تجربة جملة.", 2), arguments("،هذه جملة للتجربة.", 2));
    }
}
