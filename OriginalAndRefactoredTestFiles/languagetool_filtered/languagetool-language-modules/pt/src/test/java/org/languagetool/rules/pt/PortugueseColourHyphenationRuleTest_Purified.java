package org.languagetool.rules.pt;

import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.TestTools;
import org.languagetool.markup.AnnotatedTextBuilder;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PortugueseColourHyphenationRuleTest_Purified {

    JLanguageTool lt = new JLanguageTool(Languages.getLanguageForShortCode("pt-BR"));

    private List<RuleMatch> checkCompound(String text) throws IOException {
        return lt.check(new AnnotatedTextBuilder().addText(text).build(), true, JLanguageTool.ParagraphHandling.NORMAL, null, JLanguageTool.Mode.ALL, JLanguageTool.Level.PICKY);
    }

    @Test
    public void test_1() throws IOException {
        assertThat(checkCompound("azul-claro").size(), is(0));
    }

    @Test
    public void test_2() throws IOException {
        assertThat(checkCompound("azul claro").size(), is(1));
    }

    @Test
    public void test_3() throws IOException {
        assertThat(checkCompound("azul claro").get(0).getSpecificRuleId(), is("PT_COLOUR_HYPHENATION_AZUL_CLARO"));
    }
}
