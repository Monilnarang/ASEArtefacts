package org.languagetool.rules.ar;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.tagging.ar.ArabicTagManager;
import static org.junit.Assert.assertEquals;

public class ArabicTagManagerTest_Purified {

    private ArabicTagManager tagManager;

    @Before
    public void setUp() {
        tagManager = new ArabicTagManager();
    }

    @Test
    public void testTagger_1() {
        assertEquals(tagManager.setJar("NJ-;M1I-;---", "K"), "NJ-;M1I-;-K-");
    }

    @Test
    public void testTagger_2() {
        assertEquals(tagManager.setJar("NJ-;M1I-;---", "-"), "NJ-;M1I-;---");
    }

    @Test
    public void testTagger_3() {
        assertEquals(tagManager.setDefinite("NJ-;M1I-;---", "L"), "NJ-;M1I-;--L");
    }

    @Test
    public void testTagger_4() {
        assertEquals(tagManager.setDefinite("NJ-;M1I-;--H", "L"), "NJ-;M1I-;--H");
    }

    @Test
    public void testTagger_5() {
        assertEquals(tagManager.setPronoun("NJ-;M1I-;---", "H"), "NJ-;M1I-;--H");
    }

    @Test
    public void testTagger_6() {
        assertEquals(tagManager.setConjunction("NJ-;M1I-;---", "W"), "NJ-;M1I-;W--");
    }

    @Test
    public void testTagger_7() {
        assertEquals(tagManager.setConjunction("V-1;M1I----;---", "W"), "V-1;M1I----;W--");
    }

    @Test
    public void testTagger_8() {
        assertEquals(tagManager.getConjunctionPrefix("V-1;M1I----;W--"), "و");
    }
}
