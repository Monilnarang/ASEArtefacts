package org.graylog.plugins.pipelineprocessor.functions.strings;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ShannonEntropyTest_Purified {

    @Test
    public void testEntropyCalcForChars_1() {
        assertEquals(0D, ShannonEntropy.calculateForChars("1111"));
    }

    @Test
    public void testEntropyCalcForChars_2() {
        assertEquals(0D, ShannonEntropy.calculateForChars("5555555555"), 0.0D);
    }

    @Test
    public void testEntropyCalcForChars_3() {
        assertEquals(0D, ShannonEntropy.calculateForChars("5555555555"), 0.0D);
    }

    @Test
    public void testEntropyCalcForChars_4() {
        assertEquals(0.46899559358928133D, ShannonEntropy.calculateForChars("1555555555"));
    }

    @Test
    public void testEntropyCalcForChars_5() {
        assertEquals(1.0D, ShannonEntropy.calculateForChars("1111155555"));
    }

    @Test
    public void testEntropyCalcForChars_6() {
        assertEquals(3.3219280948873635D, ShannonEntropy.calculateForChars("1234567890"));
    }

    @Test
    public void testEntropyCalcForChars_7() {
        assertEquals(5.1699250014423095D, ShannonEntropy.calculateForChars("1234567890qwertyuiopasdfghjklzxcvbnm"));
    }
}
