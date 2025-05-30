package org.languagetool.rules.pt;

import org.junit.Test;
import org.languagetool.JLanguageTool;
import org.languagetool.Languages;
import org.languagetool.TestTools;
import org.languagetool.markup.AnnotatedTextBuilder;
import org.languagetool.rules.RuleMatch;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PostReformCompoundRuleTest_Purified {

    JLanguageTool lt = new JLanguageTool(Languages.getLanguageForShortCode("pt-BR"));

    private List<RuleMatch> checkCompound(String text) throws IOException {
        return lt.check(new AnnotatedTextBuilder().addText(text).build(), true, JLanguageTool.ParagraphHandling.NORMAL, null, JLanguageTool.Mode.ALL, JLanguageTool.Level.PICKY);
    }

    private void assertValidCompound(String text) throws IOException {
        assert checkCompound(text).isEmpty();
    }

    private void assertInvalidCompound(String text, String suggestion) throws IOException {
        List<RuleMatch> checkedCompound = checkCompound(text);
        assert checkedCompound.size() == 1;
        List<String> suggestions = checkedCompound.get(0).getSuggestedReplacements();
        assert suggestions.size() == 1;
        assert Objects.equals(suggestions.get(0), suggestion);
    }

    @Test
    public void testPostReformCompounds_1() throws IOException {
        assertValidCompound("super-herói");
    }

    @Test
    public void testPostReformCompounds_2() throws IOException {
        assert checkCompound("super herói").size() == 1;
        assert Objects.equals(checkCompound("super herói").get(0).getSpecificRuleId(), "PT_COMPOUNDS_POST_REFORM_SUPER_HERÓI");
        assert Objects.equals(checkCompound("super herói").get(0).getRule().getDescription(), "Erro na formação da palavra composta \"super herói\"");
        assertInvalidCompound("Super estrela", "Superestrela");
    }

    @Test
    public void testPostReformCompounds_3() throws IOException {
        assert checkCompound("super herói").size() == 1;
        assert Objects.equals(checkCompound("super herói").get(0).getSpecificRuleId(), "PT_COMPOUNDS_POST_REFORM_SUPER_HERÓI");
        assert Objects.equals(checkCompound("super herói").get(0).getRule().getDescription(), "Erro na formação da palavra composta \"super herói\"");
        assertInvalidCompound("web-site", "website");
    }

    @Test
    public void testPostReformCompounds_4() throws IOException {
        assert checkCompound("super herói").size() == 1;
        assert Objects.equals(checkCompound("super herói").get(0).getSpecificRuleId(), "PT_COMPOUNDS_POST_REFORM_SUPER_HERÓI");
        assert Objects.equals(checkCompound("super herói").get(0).getRule().getDescription(), "Erro na formação da palavra composta \"super herói\"");
        assertInvalidCompound("Grã Bretanha", "Grã-Bretanha");
    }

    @Test
    public void testPostReformCompounds_5() throws IOException {
        assert checkCompound("super herói").size() == 1;
        assert Objects.equals(checkCompound("super herói").get(0).getSpecificRuleId(), "PT_COMPOUNDS_POST_REFORM_SUPER_HERÓI");
        assert Objects.equals(checkCompound("super herói").get(0).getRule().getDescription(), "Erro na formação da palavra composta \"super herói\"");
        assertInvalidCompound("ultra-som", "ultrassom");
    }

    @Test
    public void testPostReformCompounds_6() throws IOException {
        assert checkCompound("super herói").size() == 1;
        assert Objects.equals(checkCompound("super herói").get(0).getSpecificRuleId(), "PT_COMPOUNDS_POST_REFORM_SUPER_HERÓI");
        assert Objects.equals(checkCompound("super herói").get(0).getRule().getDescription(), "Erro na formação da palavra composta \"super herói\"");
        assertInvalidCompound("ultra-realismo", "ultrarrealismo");
    }

    @Test
    public void testPostReformCompounds_7() throws IOException {
        assert checkCompound("super herói").size() == 1;
        assert Objects.equals(checkCompound("super herói").get(0).getSpecificRuleId(), "PT_COMPOUNDS_POST_REFORM_SUPER_HERÓI");
        assert Objects.equals(checkCompound("super herói").get(0).getRule().getDescription(), "Erro na formação da palavra composta \"super herói\"");
        assertInvalidCompound("anti semita", "antissemita");
    }

    @Test
    public void testPostReformCompounds_8() throws IOException {
        assert checkCompound("super herói").size() == 1;
        assert Objects.equals(checkCompound("super herói").get(0).getSpecificRuleId(), "PT_COMPOUNDS_POST_REFORM_SUPER_HERÓI");
        assert Objects.equals(checkCompound("super herói").get(0).getRule().getDescription(), "Erro na formação da palavra composta \"super herói\"");
        assertInvalidCompound("arqui-rabino", "arquirrabino");
    }

    @Test
    public void testPostReformCompounds_9() throws IOException {
        assert checkCompound("super herói").size() == 1;
        assert Objects.equals(checkCompound("super herói").get(0).getSpecificRuleId(), "PT_COMPOUNDS_POST_REFORM_SUPER_HERÓI");
        assert Objects.equals(checkCompound("super herói").get(0).getRule().getDescription(), "Erro na formação da palavra composta \"super herói\"");
        assertInvalidCompound("ópera rock", "ópera-rock");
    }
}
