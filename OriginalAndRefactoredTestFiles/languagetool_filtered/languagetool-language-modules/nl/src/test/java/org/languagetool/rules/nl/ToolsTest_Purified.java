package org.languagetool.rules.nl;

import org.junit.Test;
import java.util.Arrays;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ToolsTest_Purified {

    @Test
    public void testBasicConcatenation_1() {
        assertThat(Tools.glueParts(Arrays.asList("huis", "deur")), is("huisdeur"));
    }

    @Test
    public void testBasicConcatenation_2() {
        assertThat(Tools.glueParts(Arrays.asList("tv", "programma")), is("tv-programma"));
    }

    @Test
    public void testBasicConcatenation_3() {
        assertThat(Tools.glueParts(Arrays.asList("auto2", "deurs")), is("auto2-deurs"));
    }

    @Test
    public void testBasicConcatenation_4() {
        assertThat(Tools.glueParts(Arrays.asList("zee", "eend")), is("zee-eend"));
    }

    @Test
    public void testBasicConcatenation_5() {
        assertThat(Tools.glueParts(Arrays.asList("mms", "eend")), is("mms-eend"));
    }

    @Test
    public void testBasicConcatenation_6() {
        assertThat(Tools.glueParts(Arrays.asList("EersteKlas", "service")), is("EersteKlasservice"));
    }

    @Test
    public void testBasicConcatenation_7() {
        assertThat(Tools.glueParts(Arrays.asList("3D", "printer")), is("3Dprinter"));
    }

    @Test
    public void testBasicConcatenation_8() {
        assertThat(Tools.glueParts(Arrays.asList("groot", "moeder", "huis")), is("grootmoederhuis"));
    }

    @Test
    public void testBasicConcatenation_9() {
        assertThat(Tools.glueParts(Arrays.asList("sport", "tv", "uitzending")), is("sport-tv-uitzending"));
    }

    @Test
    public void testBasicConcatenation_10() {
        assertThat(Tools.glueParts(Arrays.asList("auto-", "pilot")), is("auto-pilot"));
    }

    @Test
    public void testBasicConcatenation_11() {
        assertThat(Tools.glueParts(Arrays.asList("foto", "5d", "camera")), is("foto-5dcamera"));
    }

    @Test
    public void testBasicConcatenation_12() {
        assertThat(Tools.glueParts(Arrays.asList("xyZ", "xyz")), is("xyZ-xyz"));
    }

    @Test
    public void testBasicConcatenation_13() {
        assertThat(Tools.glueParts(Arrays.asList("xyZ", "Xyz")), is("xyZ-Xyz"));
    }

    @Test
    public void testBasicConcatenation_14() {
        assertThat(Tools.glueParts(Arrays.asList("xyz", "Xyz")), is("xyz-Xyz"));
    }

    @Test
    public void testBasicConcatenation_15() {
        assertThat(Tools.glueParts(Arrays.asList("xxx-z", "yyy")), is("xxx-z-yyy"));
    }
}
