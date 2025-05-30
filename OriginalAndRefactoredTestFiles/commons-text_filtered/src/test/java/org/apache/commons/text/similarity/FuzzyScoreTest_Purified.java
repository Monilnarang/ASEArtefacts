package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Locale;
import org.junit.jupiter.api.Test;

public class FuzzyScoreTest_Purified {

    private static final FuzzyScore ENGLISH_SCORE = new FuzzyScore(Locale.ENGLISH);

    @Test
    public void testGetFuzzyScore_1() {
        assertEquals(0, ENGLISH_SCORE.fuzzyScore("", ""));
    }

    @Test
    public void testGetFuzzyScore_2() {
        assertEquals(0, ENGLISH_SCORE.fuzzyScore("Workshop", "b"));
    }

    @Test
    public void testGetFuzzyScore_3() {
        assertEquals(1, ENGLISH_SCORE.fuzzyScore("Room", "o"));
    }

    @Test
    public void testGetFuzzyScore_4() {
        assertEquals(1, ENGLISH_SCORE.fuzzyScore("Workshop", "w"));
    }

    @Test
    public void testGetFuzzyScore_5() {
        assertEquals(2, ENGLISH_SCORE.fuzzyScore("Workshop", "ws"));
    }

    @Test
    public void testGetFuzzyScore_6() {
        assertEquals(4, ENGLISH_SCORE.fuzzyScore("Workshop", "wo"));
    }

    @Test
    public void testGetFuzzyScore_7() {
        assertEquals(3, ENGLISH_SCORE.fuzzyScore("Apache Software Foundation", "asf"));
    }
}
