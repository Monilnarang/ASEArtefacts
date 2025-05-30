package org.languagetool.rules.spelling.morfologik.suggestions_ordering;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Ignore;
import org.junit.Test;
import org.languagetool.rules.spelling.symspell.implementation.EditDistance;
import java.util.Random;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.languagetool.rules.spelling.morfologik.suggestions_ordering.DetailedDamerauLevenstheinDistance.Distance;

public class DetailedDamerauLevenstheinDistanceTest_Purified {

    private Pair<String, Distance> modifyString(String s, int distance) {
        String result = s;
        Distance history = new Distance();
        for (int i = 0; i < distance; i++) {
            DetailedDamerauLevenstheinDistance.EditOperation op = DetailedDamerauLevenstheinDistance.randomEdit();
            String tmp = op.apply(result);
            if (tmp == null) {
                return null;
            } else {
                result = tmp;
                history = history.track(op);
            }
        }
        return Pair.of(result, history);
    }

    @Test
    @Ignore("WIP")
    public void testDistanceDetails_1() {
        assertEquals(new Distance().delete(), DetailedDamerauLevenstheinDistance.compare("Test", "Tet"));
    }

    @Test
    @Ignore("WIP")
    public void testDistanceDetails_2() {
        assertEquals(new Distance().insert(), DetailedDamerauLevenstheinDistance.compare("Test", "Teste"));
    }

    @Test
    @Ignore("WIP")
    public void testDistanceDetails_3() {
        assertEquals(new Distance().transpose(), DetailedDamerauLevenstheinDistance.compare("Test", "Tets"));
    }

    @Test
    @Ignore("WIP")
    public void testDistanceDetails_4() {
        assertEquals(new Distance().replace(), DetailedDamerauLevenstheinDistance.compare("Test", "Tast"));
    }

    @Test
    @Ignore("WIP")
    public void testDistanceDetails_5() {
        assertEquals(new Distance().replace().insert(), DetailedDamerauLevenstheinDistance.compare("Test", "Taste"));
    }

    @Test
    @Ignore("WIP")
    public void testDistanceDetails_6() {
        assertEquals(new Distance().delete().transpose(), DetailedDamerauLevenstheinDistance.compare("Test", "Tts"));
    }

    @Test
    @Ignore("WIP")
    public void testDistanceDetails_7() {
        assertEquals(new Distance().insert().insert(), DetailedDamerauLevenstheinDistance.compare("Test", "Teeste"));
    }
}
