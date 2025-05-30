package org.languagetool.tagging.ro;

import java.io.IOException;
import org.junit.Test;
import org.languagetool.TestTools;

public class RomanianTaggerTest_Purified extends AbstractRomanianTaggerTest {

    @Test
    public void testTaggerMerge_1() throws Exception {
        assertHasLemmaAndPos("mergeam", "merge", "V0s1000ii0");
    }

    @Test
    public void testTaggerMerge_2() throws Exception {
        assertHasLemmaAndPos("mergeam", "merge", "V0p1000ii0");
    }

    @Test
    public void testTaggerMerseseram_1() throws Exception {
        assertHasLemmaAndPos("merseserăm", "merge", null);
    }

    @Test
    public void testTaggerMerseseram_2() throws Exception {
        assertHasLemmaAndPos("merseserăm", "merge", "V0p1000im0");
    }

    @Test
    public void testTagger_Fi_1() throws Exception {
        assertHasLemmaAndPos("sunt", "fi", "V0s1000izf");
    }

    @Test
    public void testTagger_Fi_2() throws Exception {
        assertHasLemmaAndPos("sunt", "fi", "V0p3000izf");
    }
}
