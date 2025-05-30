package org.languagetool.tagging.ro;

import org.junit.Test;

public class RomanianTaggerDiacriticsTest_Purified extends AbstractRomanianTaggerTest {

    @Override
    protected RomanianTagger createTagger() {
        return new RomanianTagger("/ro/test_diacritics.dict");
    }

    @Test
    public void testTaggerMerseseram_1() throws Exception {
        assertHasLemmaAndPos("făcusem", "face", "004");
    }

    @Test
    public void testTaggerMerseseram_2() throws Exception {
        assertHasLemmaAndPos("cuțitul", "cuțit", "002");
    }

    @Test
    public void testTaggerMerseseram_3() throws Exception {
        assertHasLemmaAndPos("merseserăm", "merge", "002");
    }

    @Test
    public void testTaggerCuscaCutit_1() throws Exception {
        assertHasLemmaAndPos("cușcă", "cușcă", "001");
    }

    @Test
    public void testTaggerCuscaCutit_2() throws Exception {
        assertHasLemmaAndPos("cuțit", "cuțit", "001");
    }

    @Test
    public void testTaggerCuscaCutit_3() throws Exception {
        assertHasLemmaAndPos("cuțitul", "cuțit", "002");
    }
}
