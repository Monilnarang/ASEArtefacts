package org.languagetool.rules.spelling.morfologik;

import org.junit.Test;
import java.io.IOException;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class MorfologikSpellerTest_Purified {

    @Test
    public void testGetSuggestions_1_testMerged_1() {
        MorfologikSpeller spellerDist1 = new MorfologikSpeller("/xx/spelling/test.dict", 1);
        assertThat(spellerDist1.getSuggestions("wordone").toString(), is("[]"));
        assertThat(spellerDist1.getSuggestions("wordonex").toString(), is("[wordone/51]"));
        assertThat(spellerDist1.getSuggestions("wordonix").toString(), is("[]"));
    }

    @Test
    public void testGetSuggestions_3_testMerged_2() {
        MorfologikSpeller spellerDist2 = new MorfologikSpeller("/xx/spelling/test.dict", 2);
        assertThat(spellerDist2.getSuggestions("wordone").toString(), is("[]"));
        assertThat(spellerDist2.getSuggestions("wordonex").toString(), is("[wordone/51]"));
        assertThat(spellerDist2.getSuggestions("wordonix").toString(), is("[wordone/77]"));
        assertThat(spellerDist2.getSuggestions("wordoxix").toString(), is("[]"));
    }
}
