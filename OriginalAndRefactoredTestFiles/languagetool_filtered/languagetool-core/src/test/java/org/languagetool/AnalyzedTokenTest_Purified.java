package org.languagetool;

import org.junit.Test;
import static org.junit.Assert.*;

public class AnalyzedTokenTest_Purified {

    @Test
    public void testToString_1_testMerged_1() {
        AnalyzedToken testToken = new AnalyzedToken("word", "POS", "lemma");
        assertEquals("lemma/POS", testToken.toString());
        assertEquals("lemma", testToken.getLemma());
    }

    @Test
    public void testToString_3_testMerged_2() {
        AnalyzedToken testToken2 = new AnalyzedToken("word", "POS", null);
        assertEquals("word/POS", testToken2.toString());
        assertEquals(null, testToken2.getLemma());
        assertEquals("word", testToken2.getToken());
    }
}
