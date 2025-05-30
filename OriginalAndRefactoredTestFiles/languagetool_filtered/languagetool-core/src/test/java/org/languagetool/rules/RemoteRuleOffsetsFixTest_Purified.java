package org.languagetool.rules;

import org.junit.Test;
import org.languagetool.AnalyzedSentence;
import org.languagetool.JLanguageTool;
import org.languagetool.language.Demo;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import static org.junit.Assert.assertEquals;

public class RemoteRuleOffsetsFixTest_Purified {

    private List<Integer> printShifts(String text) {
        int[] shifts = RemoteRule.computeOffsetShifts(text);
        for (int i = 0; i < text.length(); i++) {
        }
        return Arrays.stream(shifts).boxed().collect(Collectors.toList());
    }

    @Test
    public void testShiftCalculation_1() {
        assertEquals(Arrays.asList(0, 2, 3, 4, 5, 6), printShifts("😁foo"));
    }

    @Test
    public void testShiftCalculation_2() {
        assertEquals(Arrays.asList(0, 1, 2, 3, 4, 6, 7, 8, 9, 10, 11), printShifts("foo 😁 bar"));
    }

    @Test
    public void testShiftCalculation_3() {
        assertEquals(Arrays.asList(0, 2, 3, 4, 5, 6, 7, 9, 10, 11, 12, 13, 14, 15), printShifts("😁 foo 😁 bar"));
    }

    @Test
    public void testShiftCalculation_4() {
        assertEquals("1 code point, length 2 / 1", Arrays.asList(0, 2, 3), printShifts("👪"));
    }

    @Test
    public void testShiftCalculation_5() {
        assertEquals("1 code point for each part, length 4 / 2, displayed as 1", Arrays.asList(0, 2, 4, 5, 6), printShifts("👍🏼"));
    }

    @Test
    public void testShiftCalculation_6() {
        assertEquals("normal text", Arrays.asList(0, 1), printShifts("a"));
    }
}
