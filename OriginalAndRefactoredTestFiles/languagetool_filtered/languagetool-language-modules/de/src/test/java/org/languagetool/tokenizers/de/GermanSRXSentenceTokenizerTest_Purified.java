package org.languagetool.tokenizers.de;

import org.junit.Test;
import org.languagetool.Languages;
import org.languagetool.TestTools;
import org.languagetool.tokenizers.SRXSentenceTokenizer;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class GermanSRXSentenceTokenizerTest_Purified {

    private final SRXSentenceTokenizer stokenizer = new SRXSentenceTokenizer(Languages.getLanguageForShortCode("de-DE"));

    @Test
    public void testTokenize_1() {
        assertThat(stokenizer.tokenize("Dies ist ein Satz. \u00A0Noch einer.").size(), is(2));
    }

    @Test
    public void testTokenize_2() {
        assertThat(stokenizer.tokenize("Dies ist ein Satz.   \u00A0Noch einer.").size(), is(2));
    }

    @Test
    public void testTokenize_3() {
        assertThat(stokenizer.tokenize("Dies ist ein Satz.\u00A0 Noch einer.").size(), is(2));
    }

    @Test
    public void testTokenize_4() {
        assertThat(stokenizer.tokenize("Dies ist ein Satz.\u00A0\u00A0\u00A0 Noch einer.").size(), is(2));
    }
}
