package org.languagetool.tokenizers.fr;

import org.junit.Test;
import org.languagetool.TestTools;
import org.languagetool.language.French;
import org.languagetool.tokenizers.SRXSentenceTokenizer;
import org.languagetool.tokenizers.SentenceTokenizer;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class FrenchSentenceTokenizerTest_Purified {

    private final SentenceTokenizer stokenizer = new SRXSentenceTokenizer(French.getInstance());

    @Test
    public final void testTokenize_1() {
        assertThat(stokenizer.tokenize("Je suis Chris. Comment allez vous ?").size(), is(2));
    }

    @Test
    public final void testTokenize_2() {
        assertThat(stokenizer.tokenize("Je suis Chris?   Comment allez vous ???").size(), is(2));
    }

    @Test
    public final void testTokenize_3() {
        assertThat(stokenizer.tokenize("Je suis Chris ! Comment allez vous ???").size(), is(2));
    }

    @Test
    public final void testTokenize_4() {
        assertThat(stokenizer.tokenize("Je suis Chris ? Comment allez vous ???").size(), is(2));
    }

    @Test
    public final void testTokenize_5() {
        assertThat(stokenizer.tokenize("Je suis Chris. comment allez vous").size(), is(2));
    }

    @Test
    public final void testTokenize_6() {
        assertThat(stokenizer.tokenize("Je suis Chris (...). comment allez vous").size(), is(2));
    }

    @Test
    public final void testTokenize_7() {
        assertThat(stokenizer.tokenize("Je suis Chris (la la la …). comment allez vous").size(), is(2));
    }

    @Test
    public final void testTokenize_8() {
        assertThat(stokenizer.tokenize("Je suis Chris (CHRISTOPHER!). Comment allez vous").size(), is(2));
    }

    @Test
    public final void testTokenize_9() {
        assertThat(stokenizer.tokenize("Je suis Chris... Comment allez vous.").size(), is(2));
    }
}
