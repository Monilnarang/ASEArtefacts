package org.languagetool.rules.de;

import org.junit.Test;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertFalse;

public class GermanToolsTest_Purified {

    @Test
    public void testIsVowel_1() {
        assertTrue(GermanTools.isVowel('a'));
    }

    @Test
    public void testIsVowel_2() {
        assertTrue(GermanTools.isVowel('Y'));
    }

    @Test
    public void testIsVowel_3() {
        assertTrue(GermanTools.isVowel('A'));
    }

    @Test
    public void testIsVowel_4() {
        assertTrue(GermanTools.isVowel('ö'));
    }

    @Test
    public void testIsVowel_5() {
        assertFalse(GermanTools.isVowel('b'));
    }
}
